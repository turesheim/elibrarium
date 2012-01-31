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
 * This interface describes the contract between the library and components that
 * require notification when there are library changes.
 * 
 * @author Torkild U. Resheim
 * @see ILibrarian
 */
public interface ILibraryListener {

	/**
	 * A new book has been added to the library.
	 * 
	 * @param book
	 *            the book that has been added
	 */
	public void bookAdded(Book book);

	/**
	 * A book has been removed from the library.
	 * 
	 * @param book
	 *            the book that has been removed
	 */
	public void bookRemoved(Book book);
}
