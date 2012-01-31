package no.resheim.elibrarium.epub.core;

import java.io.InputStream;
import java.util.List;

import no.resheim.elibrarium.library.Book;
import no.resheim.elibrarium.library.core.ICollection;
import no.resheim.elibrarium.library.core.ILibrarian;
import no.resheim.elibrarium.library.core.LibraryPlugin;

import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;

public class EPUBCollection implements ICollection {

	private final ListenerList listeners;

	public static final String COLLECTION_ID = "no.resheim.elibrarium.epub";
	
	private static EPUBCollection collection;

	public EPUBCollection() {
		listeners = new ListenerList();
		collection = this;
	}

	public static EPUBCollection getCollection() {
		if (collection == null) {
			collection = new EPUBCollection();
		}
		return collection;
	}

	@Override
	public void addListener(ILibrarian listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(ILibrarian listener) {
		listeners.remove(listener);
	}

	@Override
	public Status verify(Book book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getCoverImage(Book book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(final Book book) {
		if (hasBook(book.getBookURN())) {
			Object[] l = listeners.getListeners();
			for (int i = 0; i < l.length; i++) {
				final ILibrarian listener = (ILibrarian) l[i];
				SafeRunner.run(new ISafeRunnable() {

					@Override
					public void handleException(Throwable exception) {
						// TODO Auto-generated method stub
					}

					@Override
					public void run() throws Exception {
						listener.removeBook(book);
					}
				});
			}
		}
	}

	private List<Book> getBooks() {
		return LibraryPlugin.getDefault().getBooksByCollection(COLLECTION_ID);
	}

	/**
	 * Checks if the book is in the collection or not.
	 * 
	 * @param urn
	 *            the book identifier
	 * @return <code>true</code> if the book is in the collection
	 */
	public boolean hasBook(String urn) {
		for (Book book : getBooks()) {
			if (book.getBookURN().equals(urn)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void add(final Book book) {
		if (!hasBook(book.getBookURN())) {
			Object[] l = listeners.getListeners();
			for (int i = 0; i < l.length; i++) {
				final ILibrarian listener = (ILibrarian) l[i];
				SafeRunner.run(new ISafeRunnable() {

					@Override
					public void handleException(Throwable exception) {
						exception.printStackTrace();
					}

					@Override
					public void run() throws Exception {
						listener.addBook(book);
					}
				});
			}
		}
	}
}
