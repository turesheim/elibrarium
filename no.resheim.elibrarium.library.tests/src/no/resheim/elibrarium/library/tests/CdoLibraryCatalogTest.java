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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;

import no.resheim.elibrarium.library.Book;
import no.resheim.elibrarium.library.LibraryFactory;
import no.resheim.elibrarium.library.core.CdoLibraryCatalog;
import no.resheim.elibrarium.library.core.Librarian;

import org.eclipse.emf.common.util.EList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This test case exercises the {@link CdoLibraryCatalog} directly by using it's
 * API.
 * 
 * @author Torkild U. Resheim
 */
public class CdoLibraryCatalogTest {
	/** A unique identifier for the test book */
	private static final String BOOK_ID = "urn:uuid:5c7cb470-fccc-11e1-a21f-0800200c9a66";

	private static final String CONFIG_FILE = System.getProperties().getProperty("user.dir") + File.separator
			+ ".elibrarium";

	/** Port to use for the CDO server when testing */
	private static final int SERVER_PORT = 1969;

	private static File configurationFile;

	private static File storageLocation;

	private static boolean deleteFolder(File folder) {
		if (folder.isDirectory()) {
			String[] children = folder.list();
			for (String element : children) {
				boolean ok = deleteFolder(new File(folder, element));
				if (!ok) {
					return false;
				}
			}
		}
		return folder.delete();
	}

	@BeforeClass
	public static void setUp() throws Exception {
		System.out.println("Writing configuration file for Elibrarium test server.");
		configurationFile = new File(CONFIG_FILE);
		FileWriter fw = new FileWriter(configurationFile);
		storageLocation = new File(System.getProperties().getProperty("user.dir") + File.separator + "test");
		fw.write("storage.location=" + storageLocation.getAbsolutePath() + "\n");
		fw.write("server.port=" + SERVER_PORT + "\n");
		fw.close();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		// Delete the configuration file
		if (configurationFile.exists()) {
			configurationFile.delete();
		}
		// Clean out the generated data
		deleteFolder(storageLocation);
	}

	/**
	 * Simple test that uses the librarian, adds a book, retrieves it and
	 * subsequently deletes it.
	 */
	@Test
	public void testSimplePersist() throws Exception {

		// Create a new book and store it in the library
		{
			final Book book = LibraryFactory.eINSTANCE.createBook();
			book.setAuthor("Nomen Nescio");
			book.setBookURN(BOOK_ID);
			book.setTitle("My bestest book");
			Librarian.getDefault().addBook(book);
		}
		// Read back the book and test whether or not it's OK
		{
			EList<Book> books = Librarian.getDefault().getLibrary().getBooks();
			assertEquals(1, books.size());
			Book book = books.get(0);
			assertEquals("My bestest book", book.getTitle());
			assertEquals("Nomen Nescio", book.getAuthor());
			assertEquals(BOOK_ID, book.getBookURN());
		}
		// Remove the book
		{
			EList<Book> books = Librarian.getDefault().getLibrary().getBooks();
			Librarian.getDefault().removeBook(books.get(0));
			assertEquals(0, books.size());
		}
	}
}
