/*******************************************************************************
 * Copyright (c) 2012, 2013 Torkild U. Resheim.
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Torkild U. Resheim - initial API and implementation
 *******************************************************************************/
package no.resheim.elibrarium.library.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import no.resheim.elibrarium.library.Book;
import no.resheim.elibrarium.library.Library;
import no.resheim.elibrarium.library.core.ILibraryCatalog.ITransactionalOperation;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.emf.cdo.server.CDOServerUtil;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.server.db.CDODBUtil;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.emf.cdo.server.net4j.CDONet4jServerUtil;
import org.eclipse.emf.common.util.EList;
import org.eclipse.net4j.acceptor.IAcceptor;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBAdapter;
import org.eclipse.net4j.db.IDBConnectionProvider;
import org.eclipse.net4j.db.h2.H2Adapter;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.OMBundle;
import org.eclipse.net4j.util.om.OMPlatform;
import org.eclipse.net4j.util.om.log.OMLogger;
import org.eclipse.net4j.util.om.trace.OMTracer;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Server;
import org.osgi.framework.BundleContext;

public class Librarian extends Plugin implements ILibrarian {
	
	/** Identifier of this plug-in */
	public static final String PLUGIN_ID = "no.resheim.elibrarium.library.core";

	/** The default CDO server port */
	private static int serverPort = 1409;

	public static final OMBundle BUNDLE = OMPlatform.INSTANCE.bundle(PLUGIN_ID, Librarian.class);

	/** Identifier of the CDO repository used */
	public static final String CDO_REPOSITORY_ID = "elibrarium";

	private static IAcceptor acceptor;

	public static final OMTracer DEBUG = BUNDLE.tracer("debug"); //$NON-NLS-1$

	public static final OMLogger LOG = BUNDLE.logger();

	private static Librarian plugin;

	private static IRepository repository;

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Librarian getDefault() {
		return plugin;
	}

	public static int getServerPort() {
		return serverPort;
	}

	/**
	 * Returns port of the HTTP based database console.
	 */
	public static int getConsolePort() {
		return getServerPort() + 1;
	}

	private final ArrayList<ICollection> collection;

	private final ListenerList listeners;

	private String storageLocation = null;

	public Librarian() {
		collection = new ArrayList<ICollection>();
		listeners = new ListenerList();
		plugin = this;
	}

	@Override
	public synchronized void addBook(final Book book) {
		// Create a new transaction for modifying the library
		ITransactionalOperation<Library> operation = new ITransactionalOperation<Library>() {
			@Override
			public Object execute(Library object) {
				object.cdoWriteLock().lock();
				EList<Book> books = object.getBooks();
				books.add(book);
				return null;
			}
		};
		// Execute the transaction
		ILibraryCatalog.INSTANCE.modify(ILibraryCatalog.INSTANCE.getLibrary(), operation);

		// Notify listeners about the change
		Object[] l = listeners.getListeners();
		for (int i = 0; i < l.length; i++) {
			final ILibraryListener listener = (ILibraryListener) l[i];
			SafeRunner.run(new ISafeRunnable() {

				@Override
				public void handleException(Throwable exception) {
					exception.printStackTrace();
				}

				@Override
				public void run() throws Exception {
					listener.bookAdded(book);
				}
			});
		}
	}

	public void addCollection(ICollection provider) {
		collection.add(provider);
		provider.addListener(this);
	}

	public void addListener(ILibraryListener listener) {
		listeners.add(listener);
	}

	private void configure() {
		File user = new File(System.getProperties().getProperty("user.dir") + File.separator + ".elibrarium");
		File home = new File(System.getProperties().getProperty("user.home") + File.separator + ".elibrarium");
		try {
			if (user.exists()) {
				readConfiguration(user);
			} else if (home.exists()) {
				readConfiguration(home);
			}
		} catch (IOException e) {
		}
		if (storageLocation == null) {
			String root = System.getProperty("user.home");
			String os = System.getProperty("os.name").toLowerCase();
			if (os.indexOf("mac") > -1) {
				root = root + File.separator + "Library" + File.separator;
			}
			storageLocation = new File(root + File.separator + "Elibrarium").getAbsolutePath();
		}

	}

	/**
	 * Start the h2 database server.
	 * 
	 * @throws Exception
	 * @see {@link #getStorageLocation()}
	 */
	protected void doStart() throws Exception {
		LOG.info("Elibrarium server starting");
		JdbcDataSource dataSource = new JdbcDataSource();
		// XXX: This is a workaround for not handling inter-process transactions
		// through CDO. It also solves several other problems, such as what
		// happens when the database server dies.
		//
		// http://h2database.com/html/features.html#auto_mixed_mode
		// String url = "jdbc:h2:" + getStorageLocation() + File.separator +
		// "h2db;AUTO_SERVER=TRUE";
		String url = "jdbc:h2:" + getStorageLocation() + File.separator + "h2db";
		LOG.info("- Hibernate database URL = " + url);
		dataSource.setURL(url);

		IMappingStrategy mappingStrategy = CDODBUtil.createHorizontalMappingStrategy(true);
		IDBAdapter dbAdapter = new H2Adapter();
		IDBConnectionProvider dbConnectionProvider = DBUtil.createConnectionProvider(dataSource);
		IStore store = CDODBUtil.createStore(mappingStrategy, dbAdapter, dbConnectionProvider);

		Map<String, String> props = new HashMap<String, String>();
		props.put(IRepository.Props.OVERRIDE_UUID, CDO_REPOSITORY_ID);
		props.put(IRepository.Props.SUPPORTING_AUDITS, "true");
		props.put(IRepository.Props.SUPPORTING_BRANCHES, "false");

		repository = CDOServerUtil.createRepository(CDO_REPOSITORY_ID, store, props);
		CDOServerUtil.addRepository(IPluginContainer.INSTANCE, repository);
		CDONet4jServerUtil.prepareContainer(IPluginContainer.INSTANCE);

		acceptor = (IAcceptor) IPluginContainer.INSTANCE.getElement("org.eclipse.net4j.acceptors", "tcp",
				getDbServerAddress());
		LOG.info("- CDO acceptor started at " + getDbServerAddress());
		startConsole();
		LOG.info("Elibrarium server started");
	}

	/**
	 * Returns the TCP address of the H2 server.
	 */
	public static String getDbServerAddress() {
		return "127.0.0.1:" + getServerPort();
	}

	/**
	 * Stop the CDO persistence server.
	 * 
	 * @throws Exception
	 */
	protected void doStop() throws Exception {
		LOG.info("Elibrarium stopping");
		LifecycleUtil.deactivate(repository);
		LifecycleUtil.deactivate(acceptor);
		LOG.info("Elibrarium stopped");
	}

	/**
	 * Returns the identified by the external identifier.
	 * 
	 * @param urn
	 *            the book identifier
	 * @return a list of books
	 * @since 1.0
	 */
	public synchronized Book getBookByURN(String urn) {
		EList<Book> items = ILibraryCatalog.INSTANCE.getLibrary().getBooks();
		for (Book item : items) {
			if (item.getBookURN().equals(urn)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * Returns a list of all books found in the collection with the given
	 * identifier.
	 * 
	 * @param id
	 *            the collection identifier
	 * @return a list of books
	 * @since 1.0
	 */
	public synchronized List<Book> getBooksByCollection(String id) {
		ArrayList<Book> books = new ArrayList<Book>();
		EList<Book> items = ILibraryCatalog.INSTANCE.getLibrary().getBooks();
		for (Book item : items) {
			if (item.getCollection().equals(id)) {
				books.add(item);
			}
		}
		return books;
	}

	/**
	 * 
	 * @return the shared library instance
	 * @since 1.0
	 */
	public synchronized Library getLibrary() {
		return ILibraryCatalog.INSTANCE.getLibrary();
	}

	/**
	 * Returns the root folder of where Elibrarium stores it's data files. On OS
	 * X this is normally <i>~/Library/Elibrarium</i>. On other platforms it is
	 * normally at ~/Elibrarium.
	 * 
	 * @return the path to the storage location.
	 * @throws IOException
	 */
	public IPath getStorageLocation() {
		return new Path(storageLocation);

	}

	private void readConfiguration(File file) throws FileNotFoundException, IOException {
		Properties p = new Properties();
		p.load(new FileReader(file));
		storageLocation = p.getProperty("storage.location");
		serverPort = Integer.parseInt(p.getProperty("server.port"));
		LOG.info("Using configuration file at " + file.getAbsolutePath());
		LOG.info("- storage.location = " + storageLocation);
		LOG.info("- server.port=" + serverPort);
	}

	@Override
	public synchronized void removeBook(final Book book) {
		// Create a new transaction for modifying the library
		ITransactionalOperation<Library> operation = new ITransactionalOperation<Library>() {
			@Override
			public Object execute(Library object) {
				object.cdoWriteLock().lock();
				EList<Book> books = object.getBooks();
				books.remove(book);
				return null;
			}
		};
		// Execute the transaction
		ILibraryCatalog.INSTANCE.modify(ILibraryCatalog.INSTANCE.getLibrary(), operation);
		Object[] l = listeners.getListeners();
		for (int i = 0; i < l.length; i++) {
			final ILibraryListener listener = (ILibraryListener) l[i];
			SafeRunner.run(new ISafeRunnable() {

				@Override
				public void handleException(Throwable exception) {
					exception.printStackTrace();
				}

				@Override
				public void run() throws Exception {
					listener.bookRemoved(book);
				}
			});
		}
	}

	public void removeListener(ILibraryListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		configure();
		doStart();
		CdoLibraryCatalog.INSTANCE.activate();
	}

	/**
	 * Start a h2 web-based console for debugging and data browsing.
	 */
	private void startConsole() {
		try {
			Server.createWebServer(new String[] { "-webPort", String.valueOf(getConsolePort()) }).start();
			LOG.info("- Started h2 console at http://127.0.0.1:" + getConsolePort());
		} catch (SQLException e) {
			LOG.info("- Could not start h2 console");
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		doStop();
		CdoLibraryCatalog.INSTANCE.deactivate();
	}
}
