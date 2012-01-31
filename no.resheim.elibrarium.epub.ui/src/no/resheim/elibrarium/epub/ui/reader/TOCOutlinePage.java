/*******************************************************************************
 * Copyright (c) 2011 Torkild U. Resheim.
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

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.mylyn.docs.epub.core.OPSPublication;
import org.eclipse.mylyn.docs.epub.ncx.NavPoint;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

/**
 * Use to provide an outline page for EPUB table of contents.
 * 
 * @author Torkild U. Resheim
 */
public class TOCOutlinePage extends ContentOutlinePage implements IContentOutlinePage, IDoubleClickListener {

	private final OPSPublication epub;

	private final EPUBReader reader;

	public TOCOutlinePage(OPSPublication epub, EPUBReader reader) {
		this.epub = epub;
		this.reader = reader;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		TreeViewer viewer = getTreeViewer();
		viewer.setContentProvider(new TOCContentProvider());
		viewer.setLabelProvider(new EPUBLabelProvider());
		viewer.addSelectionChangedListener(this);
		viewer.addDoubleClickListener(this);
		viewer.setInput(epub);
	}

	@Override
	public void doubleClick(DoubleClickEvent event) {
		IStructuredSelection s = (IStructuredSelection) event.getSelection();
		Object element = s.getFirstElement();
		if (element instanceof NavPoint) {
			reader.navigateTo((NavPoint) element);
		}
	}
}
