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
package no.resheim.elibrarium.library.tests;

import org.junit.Test;

public class BaseLibraryTest {

	// private CDONet4jSessionConfiguration sessionConfiguration = null;
	//
	// /**
	// * Opens a CDOSession, does not register an EPackage with the session.
	// This
	// * should be done by the caller.
	// */
	// protected CDOSession openSession() {
	// if (sessionConfiguration == null) {
	// initialize();
	// }
	// final CDONet4jSession cdoSession =
	// sessionConfiguration.openNet4jSession();
	// cdoSession.getPackageRegistry().putEPackage(LibraryPackage.eINSTANCE);
	// return cdoSession;
	// }
	//
	// /**
	// * Initializes the connection and creates a
	// * {@link CDONet4jSessionConfiguration} which is stored in a member of
	// this
	// * class.
	// */
	// @Before
	// public void initialize() {
	// OMPlatform.INSTANCE.setDebugging(true);
	// OMPlatform.INSTANCE.addLogHandler(PrintLogHandler.CONSOLE);
	// OMPlatform.INSTANCE.addTraceHandler(PrintTraceHandler.CONSOLE);
	//
	// // Prepare container
	// final IManagedContainer container = ContainerUtil.createContainer();
	// Net4jUtil.prepareContainer(container); // Register Net4j factories
	// TCPUtil.prepareContainer(container); // Register TCP factories
	// CDONet4jUtil.prepareContainer(container); // Register CDO factories
	// // LifecycleUtil.activate(container);
	// container.activate();
	//
	// // Create connector
	// final IConnector connector = TCPUtil.getConnector(container,
	// Librarian.DB_SERVER_ADDRESS);
	//
	// // Create configuration
	// sessionConfiguration = CDONet4jUtil.createNet4jSessionConfiguration();
	// sessionConfiguration.setConnector(connector);
	// sessionConfiguration.setRepositoryName(Librarian.CDO_REPOSITORY_ID);
	// }
	//
	// /**
	// * Nullifies the session configuration so that a new test will start with
	// a
	// * new one.
	// */
	// @After
	// public void tearDown() throws Exception {
	// sessionConfiguration = null;
	// }

	@Test
	public void dummyTest() {

	}

}
