package no.resheim.elibrarium.library.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.resheim.elibrarium.library.Book;
import no.resheim.elibrarium.library.Library;
import no.resheim.elibrarium.library.LibraryFactory;

import org.eclipse.core.resources.ISaveContext;
import org.eclipse.core.resources.ISaveParticipant;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.osgi.framework.BundleContext;

public class LibraryPlugin extends Plugin implements ILibrarian {

	private class WorkspaceSaveParticipant implements ISaveParticipant {

		@Override
		public void doneSaving(ISaveContext context) {
			LibraryPlugin instance = LibraryPlugin.getDefault();
			// delete the old saved state since it is not necessary anymore
			String oldFileName = "library.old";
			File f = instance.getStorageLocation().append(oldFileName).toFile();
			f.delete();
		}

		@Override
		public void prepareToSave(ISaveContext context) throws CoreException {
		}

		@Override
		public void rollback(ISaveContext context) {
			LibraryPlugin instance = LibraryPlugin.getDefault();
			String oldFileName = "library.old";
			String saveFileName = "library.xml";
			File backup = instance.getStorageLocation().append(oldFileName).toFile();
			File updated = instance.getStorageLocation().append(saveFileName).toFile();
			updated.delete();
			backup.renameTo(updated);
		}

		@Override
		public void saving(ISaveContext context) throws CoreException {
			switch (context.getKind()) {
			case ISaveContext.FULL_SAVE:
				LibraryPlugin instance = LibraryPlugin.getDefault();
				String oldFileName = "library.old";
				String saveFileName = "library.xml";
				File backup = instance.getStorageLocation().append(oldFileName).toFile();
				File updated = instance.getStorageLocation().append(saveFileName).toFile();
				if (updated.exists()) {
					backup.delete();
					updated.renameTo(backup);
				}
				try {
					instance.writeLibrary(updated);
					context.map(new Path("library"), new Path(saveFileName));
					context.needSaveNumber();
				} catch (IOException e) {
					throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID, "Could not store library database", e));
				}
				break;
			case ISaveContext.PROJECT_SAVE:
				break;
			case ISaveContext.SNAPSHOT:
				break;
			}
		}
	}

	private static final String LIBRARY_VERSION = "1.0";

	private static LibraryPlugin plugin;

	public static final String PLUGIN_ID = "no.resheim.elibrarium.library.core";

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static LibraryPlugin getDefault() {
		return plugin;
	}

	private final ArrayList<ICollection> collection;

	private Library library;

	private final ListenerList listeners;

	public LibraryPlugin() {
		collection = new ArrayList<ICollection>();
		listeners = new ListenerList();
		plugin = this;
	}

	@Override
	public synchronized void addBook(final Book book) {
		library.getBooks().add(book);
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

	@Override
	public synchronized void removeBook(final Book book) {
		library.getBooks().remove(book);
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

	public void addListener(ILibraryListener listener) {
		listeners.add(listener);
	}

	public void removeListener(ILibraryListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Returns the identified by the external identifier.
	 * 
	 * @param urn
	 *            the book identifier
	 * @return a list of books
	 */
	public synchronized Book getBookByURN(String urn) {
		EList<Book> items = library.getBooks();
		for (Book item : items) {
			if (item.getBookURN().equals(urn)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public synchronized List<Book> getBooksByCollection(String id) {
		ArrayList<Book> books = new ArrayList<Book>();
		EList<Book> items = library.getBooks();
		for (Book item : items) {
			if (item.getCollection().equals(id)) {
				books.add(item);
			}
		}
		return books;
	}

	public synchronized Library getLibrary() {
		return library;
	}

	/**
	 * Reads the library from the serialised model. The result of this operation
	 * is placed in the {@link #library} instance.
	 * 
	 * @param file
	 *            the destination file
	 * @throws IOException
	 */
	private void readLibrary(File file) throws IOException {
		// registerLibraryResourceFactory();
		ResourceSet resourceSet = new ResourceSetImpl();
		URI fileURI = URI.createFileURI(file.getAbsolutePath());
		Resource resource = resourceSet.createResource(fileURI);
		Map<Object, Object> loadOptions = new HashMap<Object, Object>();
		loadOptions.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		// Required in order to correctly read in attributes
		loadOptions.put(XMLResource.OPTION_LAX_FEATURE_PROCESSING, Boolean.TRUE);
		// Treat "href" attributes as features
		loadOptions.put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
		// Escape
		loadOptions.put(XMLResource.OPTION_SKIP_ESCAPE_URI, Boolean.FALSE);
		// Do not download any external DTDs.
		Map<String, Object> parserFeatures = new HashMap<String, Object>();
		parserFeatures.put("http://xml.org/sax/features/validation", Boolean.FALSE);
		parserFeatures.put("http://apache.org/xml/features/nonvalidating/load-external-dtd", Boolean.FALSE);
		loadOptions.put(XMLResource.OPTION_PARSER_FEATURES, parserFeatures);
		resource.load(loadOptions);
		library = (Library) resource.getContents().get(0);
	}

	public void addCollection(ICollection provider) {
		collection.add(provider);
		provider.addListener(this);
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		if (!restoreState()) {
			library = LibraryFactory.eINSTANCE.createLibrary();
			library.setVersion(LIBRARY_VERSION);
		}
	}

	/**
	 * Returns the root folder of where Elibrarium stores it's data files. On OS
	 * X this is <i>~/Library/Elibrarium</i>. On other platforms it is at
	 * ~/Elibrarium.
	 * 
	 * @return the path to the storage location.
	 * @throws IOException
	 */
	public IPath getStorageLocation() {
		String root = System.getProperty("user.home");
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("mac") > -1) {
			root = root + File.separator + "Library" + File.separator;
		}
		File file = new File(root + File.separator + "Elibrarium");
		return new Path(file.getAbsolutePath());

	}

	private boolean restoreState() {
		try {
			LibraryPlugin instance = LibraryPlugin.getDefault();
			String oldFileName = "library.old";
			String saveFileName = "library.xml";
			File backup = instance.getStorageLocation().append(oldFileName).toFile();
			File updated = instance.getStorageLocation().append(saveFileName).toFile();
			// If the backup exists we'll use that instead as something went
			// wrong the last time the database was saved.
			if (backup.exists()) {
				updated.delete();
				backup.renameTo(updated);
			}
			if (updated.exists()) {
				readLibrary(updated);
			}
			ISaveParticipant saveParticipant = new WorkspaceSaveParticipant();
			ResourcesPlugin.getWorkspace().addSaveParticipant(PLUGIN_ID, saveParticipant);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Serialises the library to a XML file.
	 * 
	 * @param file
	 *            the destination file
	 * @throws IOException
	 */
	private void writeLibrary(File file) throws IOException {
		ResourceSet resourceSet = new ResourceSetImpl();
		URI fileURI = URI.createFileURI(file.getAbsolutePath());
		Resource resource = resourceSet.createResource(fileURI);
		resource.getContents().add(library);
		Map<Object, Object> saveOptions = new HashMap<Object, Object>();
		// We use extended metadata
		saveOptions.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		// UTF-8 encoding is required per specification
		saveOptions.put(XMLResource.OPTION_ENCODING, "utf-8");
		resource.save(saveOptions);
	}

}
