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
package no.resheim.elibrarium.epub.core;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import no.resheim.elibrarium.library.Book;
import no.resheim.elibrarium.library.core.ICollection;
import no.resheim.elibrarium.library.core.ILibrarian;
import no.resheim.elibrarium.library.core.LibraryPlugin;

import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;

public class EPUBCollection implements ICollection, IPreferenceChangeListener {

	private final ListenerList listeners;

	public static final String COLLECTION_ID = "no.resheim.elibrarium.epub";
	
	private static EPUBCollection collection;

	private final FolderScanner scanner;

	private class Scheduler extends JobChangeAdapter {

		FolderScanner scanner;

		public Scheduler(FolderScanner scanner) {
			this.scanner = scanner;
		}

		@Override
		public void done(IJobChangeEvent event) {
			super.done(event);
			if (isDiscoveryEnabled()) {
				IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(EPUBCorePlugin.PLUGIN_ID);
				int minutes = preferences.getInt(PreferenceConstants.SCAN_INTERVAL, PreferenceConstants.DEFAULT_SCAN_INTERVAL);
				scanner.schedule(60000 * minutes);
			}
		}

	}

	private boolean isDiscoveryEnabled(){
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(EPUBCorePlugin.PLUGIN_ID);
		return preferences.getBoolean(PreferenceConstants.SCAN_ENABLE, false);
	}

	public EPUBCollection() {
		listeners = new ListenerList();
		collection = this;
		scanner = new FolderScanner("Scanning");
		scanner.addJobChangeListener(new Scheduler(scanner));
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(EPUBCorePlugin.PLUGIN_ID);
		preferences.addPreferenceChangeListener(this);
		if (isDiscoveryEnabled()) {
			// Wait ten seconds before starting the first time
			scanner.schedule(10000);
		}
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

	/**
	 * Returns the book with the given URN <code>null</code> if the book could
	 * not be found.
	 * 
	 * @param urn
	 *            the book identifier
	 * @return the book if found or <code>null</code>
	 */
	public Book getBook(String urn) {
		for (Book book : getBooks()) {
			if (book.getBookURN().equals(urn)) {
				return book;
			}
		}
		return null;
	}

	public boolean hasBook(File file) {
		for (Book book : getBooks()) {
			if (book.getBookURL().equals(file.toURI().toString())) {
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

	@Override
	public void preferenceChange(PreferenceChangeEvent event) {
		if (event.getKey().equals(PreferenceConstants.SCAN_ENABLE)) {
			if (isDiscoveryEnabled()) {
				scanner.schedule();
			} else {
				scanner.cancel();
			}
		}

	}
}
