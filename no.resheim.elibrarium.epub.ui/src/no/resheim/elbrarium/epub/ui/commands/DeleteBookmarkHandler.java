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
package no.resheim.elbrarium.epub.ui.commands;

import no.resheim.elibrarium.library.Book;
import no.resheim.elibrarium.library.Bookmark;
import no.resheim.elibrarium.library.core.ILibraryCatalog;
import no.resheim.elibrarium.library.core.ILibraryCatalog.ITransactionalOperation;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class DeleteBookmarkHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Bookmark bookmark = getSelection(event);
		if (bookmark.eContainer() instanceof Book) {
			Book book = (Book) bookmark.eContainer();
			// Wrap the operation of removing a bookmark into a transaction
			ILibraryCatalog.INSTANCE.modify(book, new ITransactionalOperation<Book>() {
				@Override
				public Object execute(Book object) {
					object.cdoWriteLock().lock();
					object.getBookmarks().remove(bookmark);
					return null;
				}
			});
		}
		return null;
	}

	protected Bookmark getSelection(ExecutionEvent event) {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			Object o = ((IStructuredSelection) selection).getFirstElement();
			if (o != null && o instanceof Bookmark) {
				return ((Bookmark) o);
			}
		}
		return null;
	}

}
