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

import no.resheim.elibrarium.library.Book;

/**
 * This interface describes the contract between the {@link LibraryPlugin},
 * instances of {@link ICollection} and various UI components manipulating the
 * library.
 * 
 * @author Torkild U. Resheim
 */
public interface ILibrarian {

	/**
	 * Notifies the {@link ILibrarian} that a new book should be added.
	 * 
	 * @param book
	 *            the book that has been added
	 */
	public void addBook(Book book);

	/**
	 * Notifies the {@link ILibrarian} that a book should be permanently
	 * removed.
	 * 
	 * @param book
	 *            the book that has been removed
	 */
	public void removeBook(Book book);
}
