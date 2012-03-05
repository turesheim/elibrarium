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
package no.resheim.elibrarium.library.ui;

import no.resheim.elibrarium.library.Annotation;
import no.resheim.elibrarium.library.Book;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;

public class LibraryLabelProvider implements IStyledLabelProvider {

	@Override
	public Image getImage(Object obj) {
		if (obj instanceof Book) {
			return LibraryUIPlugin.getDefault().getImageRegistry().get(LibraryUIPlugin.IMG_BOOK);
		}
		return null;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

	@Override
	public StyledString getStyledText(Object element) {
		if (element instanceof Book) {
			Book book = ((Book) element);
			StyledString string = new StyledString(book.getTitle());
			String decorated = NLS.bind("{0} - {1}", new String[] { book.getTitle(), book.getAuthor() });
			return StyledCellLabelProvider.styleDecoratedString(decorated, StyledString.QUALIFIER_STYLER, string);
		}
		if (element instanceof Annotation) {
			Annotation note = ((Annotation) element);
			StyledString string = new StyledString(note.getText());
			if (note.getComment() != null) {
				String decorated = NLS.bind("{0} - {1}", new String[] { note.getText(), note.getComment() });
				return StyledCellLabelProvider.styleDecoratedString(decorated, StyledString.QUALIFIER_STYLER, string);
			} else {
				return string;
			}
		}
		return new StyledString();

	}

}
