package no.resheim.elibrarium.library.tests;

import java.io.File;
import java.io.FileWriter;

import junit.framework.TestCase;
import no.resheim.elibrarium.library.Book;
import no.resheim.elibrarium.library.LibraryFactory;
import no.resheim.elibrarium.library.core.CdoLibraryCatalog;
import no.resheim.elibrarium.library.core.Librarian;

import org.eclipse.emf.common.util.EList;
import org.junit.Test;

/**
 * This test case exercises the {@link CdoLibraryCatalog} directly by using it's
 * API.
 * 
 * @author Torkild U. Resheim
 * @since 1.0
 */
public class CdoLibraryCatalogTest extends TestCase {
	/** A unique identifier for the test book */
	private static final String BOOK_ID = "urn:uuid:5c7cb470-fccc-11e1-a21f-0800200c9a66";

	private static final String CONFIG_FILE = System.getProperties().getProperty("user.dir") + File.separator
			+ ".elibrarium";

	/** Port to use for the CDO server */
	private static final int SERVER_PORT = 1969;

	private File configurationFile;

	private File storageLocation;

	private boolean deleteFolder(File folder) {
		if (folder.isDirectory()) {
			String[] children = folder.list();
			for (int i = 0; i < children.length; i++) {
				boolean ok = deleteFolder(new File(folder, children[i]));
				if (!ok) {
					return false;
				}
			}
		}
		return folder.delete();
	}

	@Override
	protected void setUp() throws Exception {
		configurationFile = new File(CONFIG_FILE);
		FileWriter fw = new FileWriter(configurationFile);
		storageLocation = new File(System.getProperties().getProperty("user.dir") + File.separator + "test");
		fw.write("storage.location=" + storageLocation.getAbsolutePath() + "\n");
		fw.write("server.port=" + SERVER_PORT + "\n");
		fw.close();
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
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
