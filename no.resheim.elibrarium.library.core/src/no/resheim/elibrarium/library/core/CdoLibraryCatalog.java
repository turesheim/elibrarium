/*******************************************************************************
 * Copyright (c) 2012 Torkild U. Resheim.
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

import no.resheim.elibrarium.library.Library;
import no.resheim.elibrarium.library.LibraryFactory;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOAdapterPolicy;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.util.WrappedException;
import org.eclipse.net4j.util.container.ContainerUtil;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.lifecycle.Lifecycle;

/**
 * 
 * @author Torkild U. Resheim
 * @since 1.0
 */
public class CdoLibraryCatalog extends Lifecycle implements ILibraryCatalog {

	static final CdoLibraryCatalog INSTANCE = new CdoLibraryCatalog();

	// private final AdapterFactory adapterFactory;

	private CDONet4jSession session;

	private CDOView view;

	private Library library;

	private CdoLibraryCatalog() {
		// adapterFactory = new
		// ComposedAdapterFactory(EMFEditPlugin.getComposedAdapterFactoryDescriptorRegistry());
	}

	/** Catalogue path */
	private final String path = "/library";

	public AdapterFactory getAdapterFactory() {
		return null;
		// return adapterFactory;
	}

	public synchronized Library getLibrary() {
		if (library == null) {
			String name = Librarian.CDO_REPOSITORY_ID + path;
			if (!view.hasResource(name)) {
				CDOTransaction transaction = session.openTransaction();
				Library newInstance = LibraryFactory.eINSTANCE.createLibrary();
				try {
					CDOResource resource = transaction.createResource(name);
					resource.getContents().add(newInstance);
					transaction.commit();
				} catch (CommitException ex) {
					throw WrappedException.wrap(ex);
				} finally {
					transaction.close();
				}
			}
			CDOResource resource = view.getResource(name);
			library = (Library) resource.getContents().get(0);
		}
		return library;
	}

	/**
	 * Modifies the given object within a transaction which is immediately
	 * committed to the database if successful.
	 */
	public <T extends CDOObject> Object modify(T object, ITransactionalOperation<T> operation) {
		CDOTransaction transaction = session.openTransaction();

		try {
			T transactionalObject = transaction.getObject(object);
			Object result = operation.execute(transactionalObject);
			transaction.commit();

			if (result instanceof CDOObject) {
				return view.getObject((CDOObject) result);
			}

			return result;
		} catch (CommitException ex) {
			throw WrappedException.wrap(ex);
		} finally {
			transaction.close();
		}
	}

	@Override
	protected void doActivate() throws Exception {
		super.doActivate();
		String repository = Librarian.CDO_REPOSITORY_ID;

		// Prepare container
		final IManagedContainer container = ContainerUtil.createContainer();
		Net4jUtil.prepareContainer(container); // Register Net4j factories
		TCPUtil.prepareContainer(container); // Register TCP factories
		CDONet4jUtil.prepareContainer(container); // Register CDO factories
		container.activate();
		IConnector connector = Net4jUtil.getConnector(container, "tcp", Librarian.getDbServerAddress());

		CDONet4jSessionConfiguration config = CDONet4jUtil.createNet4jSessionConfiguration();
		config.setConnector(connector);
		config.setRepositoryName(repository);

		session = config.openNet4jSession();
		view = session.openView();
		view.options().addChangeSubscriptionPolicy(CDOAdapterPolicy.ALL);
	}

	@Override
	protected void doDeactivate() throws Exception {
		session.close();
		session = null;
		view = null;
	}
}
