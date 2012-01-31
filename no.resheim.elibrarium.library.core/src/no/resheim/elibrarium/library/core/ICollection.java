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

import java.io.InputStream;

import no.resheim.elibrarium.library.Book;

/**
 * Implementors of this interface provide books for the library and take part in
 * the library maintenance workflow. Specifically:
 * <ul>
 * <li>Must add new books to the library.</li>
 * <li>May remove books from the library.</li>
 * <li>Must verify the status of a book in the library.</li>
 * </ul>
 * As the library will only persist the book data required for it to operate,
 * any additional data must be persisted by the {@link ICollection}. Or one can
 * use a set of "key-value" pairs that also are available.
 * 
 * @author Torkild U. Resheim
 */
public interface ICollection {

	/**
	 * Book status.
	 */
	public enum Status {
		/** Nothing has changed */
		OK,
		/** The book is no longer present */
		MISSING,
		/** Book metadata must be updated */
		NEEDS_UPDATE,
		/** Unknown status */
		UNKNOWN
	}

	/**
	 * Used to verify the status of a book on the behalf of the library.
	 * 
	 * @param book
	 *            the book to verify
	 * @return the book status
	 */
	public Status verify(Book book);

	/**
	 * Remove a book from the {@link ICollection}. It is up to the
	 * implementation to also notify the library about the newly added book.
	 * 
	 * @param book
	 *            the book to be removed
	 * @see #add(Book)
	 */
	public void remove(Book book);

	/**
	 * Add a book to the {@link ICollection}. It is up to the implementation to
	 * also notify the library about the newly added book.
	 * 
	 * @param book
	 *            the book to be added
	 * @see #remove(Book)
	 */
	public void add(Book book);

	/**
	 * Returns a stream to the book's cover image. The image should be 768x1024
	 * pixels in size.
	 * 
	 * @param book
	 *            the book to get the cover for
	 * @return the cover image stream
	 */
	public InputStream getCoverImage(Book book);

	/**
	 * Adds a new library listener to be notified when book related events takes
	 * place.
	 * 
	 * @param listener
	 *            the listener to add
	 * @see #removeListener(ILibrarian)
	 */
	public void addListener(ILibrarian listener);

	/**
	 * Removes the provider listener.
	 * 
	 * @param listener
	 *            the listener to remove
	 * @see #addListener(ILibrarian)
	 */
	public void removeListener(ILibrarian listener);

}
