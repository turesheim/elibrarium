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

import java.net.URI;

import no.resheim.elibrarium.library.Book;
import no.resheim.elibrarium.library.LibraryFactory;

public class LibraryUtil {

	/**
	 * Creates a new book instance.
	 * 
	 * @param id
	 *            the collection identifier
	 * @param uri
	 *            location of the book file
	 * @param urn
	 *            the (external) identifier of the book
	 * @param title
	 *            the title of the book
	 * @param author
	 *            the author of the book
	 * @return the book instance
	 */
	public static Book createNewBook(String id, URI uri, String urn, String title, String author) {
		Book book = LibraryFactory.eINSTANCE.createBook();
		book.setBookURN(urn);
		book.setBookURL(uri.toString());
		book.setTitle(title);
		book.setAuthor(author);
		book.setCollection(id);
		return book;
	}
}
