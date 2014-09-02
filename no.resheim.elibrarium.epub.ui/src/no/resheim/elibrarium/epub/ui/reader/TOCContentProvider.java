/*******************************************************************************
 * Copyright (c) 2011, 2014 Torkild U. Resheim.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Torkild U. Resheim - initial API and implementation
 *******************************************************************************/
package no.resheim.elibrarium.epub.ui.reader;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.mylyn.docs.epub.core.OPSPublication;
import org.eclipse.mylyn.docs.epub.core.Publication;
import org.eclipse.mylyn.docs.epub.ncx.NavMap;
import org.eclipse.mylyn.docs.epub.ncx.NavPoint;
import org.eclipse.mylyn.docs.epub.ncx.Ncx;

/**
 * Provides EPUB table of contents as tree content for use in i a
 * {@link TreeViewer}.
 *
 * @author Torkild U. Resheim
 */
public class TOCContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof OPSPublication) {
			Object toc = ((Publication) inputElement).getTableOfContents();
			if (toc instanceof Ncx) {
				NavMap map = ((Ncx) toc).getNavMap();
				if (map != null) {
					EList<NavPoint> list = map.getNavPoints();
					if (list != null) {
						return list.toArray();
					}
				}
			}
		}
		return new Object[0];
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof NavPoint) {
			return ((NavPoint) parentElement).getNavPoints().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof NavPoint) {
			return ((NavPoint) element).getNavPoints().size() > 0;
		}
		if (element instanceof Ncx) {
			return ((Ncx) element).getNavMap().getNavPoints().size() > 0;
		}
		return false;
	}

}
