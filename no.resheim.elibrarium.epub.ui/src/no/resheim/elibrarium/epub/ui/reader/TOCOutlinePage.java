/*******************************************************************************
 * Copyright (c) 2011, 2012 Torkild U. Resheim.
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

import no.resheim.elibrarium.epub.core.EpubUtil;
import no.resheim.elibrarium.epub.ui.EpubUIPlugin;
import no.resheim.elibrarium.library.Annotation;
import no.resheim.elibrarium.library.Book;
import no.resheim.elibrarium.library.Bookmark;
import no.resheim.elibrarium.library.ui.AnnotationViewer;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.mylyn.docs.epub.core.OPSPublication;
import org.eclipse.mylyn.docs.epub.ncx.NavPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

/**
 * Use to provide an outline page for EPUB table of contents.
 * 
 * @author Torkild U. Resheim
 */
public class TOCOutlinePage extends Page implements IContentOutlinePage, ISelectionChangedListener,
		IDoubleClickListener {

	class AnnotationsContentProvider implements IStructuredContentProvider {

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			if (parent instanceof Book) {
				return ((Book) parent).getBookmarks().toArray();
			}
			return new Object[0];
		}

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

	}

	private Action deleteAction;

	private StackLayout layout;

	private AnnotationViewer notes;

	private final OPSPublication ops;

	private Composite pagebook;

	private final EpubReader reader;

	private final ListenerList selectionChangedListeners = new ListenerList();

	private IAction showAnnotations;

	private IAction showTOC;

	private TreeViewer toc;

	public TOCOutlinePage(OPSPublication epub, EpubReader reader) {
		this.ops = epub;
		this.reader = reader;
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}

	@Override
	public void createControl(Composite parent) {

		Book book = EpubUtil.getBook(ops);

		pagebook = new Composite(parent, SWT.NONE);
		layout = new StackLayout();
		pagebook.setLayout(layout);

		toc = new TreeViewer(pagebook, getTreeStyle());
		toc.setContentProvider(new TOCContentProvider());
		toc.setLabelProvider(new EpubLabelProvider());
		toc.addSelectionChangedListener(this);
		toc.addDoubleClickListener(this);

		notes = new AnnotationViewer(pagebook, SWT.FULL_SELECTION);
		notes.setContentProvider(new AnnotationsContentProvider());
		notes.setLabelProvider(new EpubLabelProvider());
		notes.addSelectionChangedListener(this);
		notes.addDoubleClickListener(this);

		// Create the context menu for the annotations
		hookContextMenu();

		// Set the input
		toc.setInput(ops);
		toc.expandAll();
		notes.setInput(book);

		Adapter adapter = new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				notes.refresh();
			}
		};
		book.eAdapters().add(adapter);

		// Start by showing the table of contents
		showTOC.setChecked(true);
		layout.topControl = toc.getControl();
		pagebook.layout();
	}

	@Override
	public void doubleClick(DoubleClickEvent event) {
		IStructuredSelection s = (IStructuredSelection) event.getSelection();
		Object element = s.getFirstElement();
		if (element instanceof NavPoint) {
			reader.navigateTo((NavPoint) element);
		} else if (element instanceof Annotation) {
			reader.navigateTo((Annotation) element);
		} else if (element instanceof Bookmark) {
			reader.navigateTo((Bookmark) element);
		}
	}

	private void fillContextMenu(IMenuManager manager) {
		ISelection selection = notes.getSelection();
		if (selection instanceof IStructuredSelection) {
			Object o = ((IStructuredSelection) selection).getFirstElement();
			if (o instanceof Annotation) {
				manager.add(deleteAction);
			}
		}
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	/**
	 * Fires a selection changed event.
	 * 
	 * @param selection
	 *            the new selection
	 */
	protected void fireSelectionChanged(ISelection selection) {
		// create an event
		final SelectionChangedEvent event = new SelectionChangedEvent(this, selection);

		// fire the event
		Object[] listeners = selectionChangedListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			final ISelectionChangedListener l = (ISelectionChangedListener) listeners[i];
			SafeRunner.run(new SafeRunnable() {
				public void run() {
					l.selectionChanged(event);
				}
			});
		}
	}

	/*
	 * (non-Javadoc) Method declared on IPage (and Page).
	 */
	@Override
	public Control getControl() {
		if (pagebook == null) {
			return null;
		}
		return pagebook;
	}

	/*
	 * (non-Javadoc) Method declared on ISelectionProvider.
	 */
	public ISelection getSelection() {
		if (toc == null) {
			return StructuredSelection.EMPTY;
		}
		return toc.getSelection();
	}

	/**
	 * A hint for the styles to use while constructing the TreeViewer.
	 * <p>
	 * Subclasses may override.
	 * </p>
	 * 
	 * @return the tree styles to use. By default, SWT.MULTI | SWT.H_SCROLL |
	 *         SWT.V_SCROLL
	 * @since 3.6
	 */
	protected int getTreeStyle() {
		return SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL;
	}

	/**
	 * Returns this page's tree viewer.
	 * 
	 * @return this page's tree viewer, or <code>null</code> if
	 *         <code>createControl</code> has not been called yet
	 */
	protected TreeViewer getTreeViewer() {
		return toc;
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				TOCOutlinePage.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(notes.getControl());
		notes.getControl().setMenu(menu);
		getSite().registerContextMenu("duh", menuMgr, notes);
	}

	@Override
	public void init(IPageSite pageSite) {
		super.init(pageSite);
		pageSite.setSelectionProvider(this);
		makeActions();
		IToolBarManager toolbar = getSite().getActionBars().getToolBarManager();
		toolbar.add(showTOC);
		toolbar.add(showAnnotations);
	}

	private void makeActions() {

		showTOC = new Action("Contents", IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				layout.topControl = toc.getControl();
				pagebook.layout();
			}
		};
		showAnnotations = new Action("Notes", IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				layout.topControl = notes.getControl();
				pagebook.layout();
			}
		};
		deleteAction = new Action() {
			@Override
			public void run() {
				ISelection selection = notes.getSelection();
				if (selection instanceof IStructuredSelection) {
					Object o = ((IStructuredSelection) selection).getFirstElement();
					if (o instanceof Annotation) {
						EpubUtil.getBook(ops).getBookmarks().remove(o);
					}
				}
			}
		};
		deleteAction.setText("Delete");
		deleteAction.setToolTipText("Deletes the note");


		showTOC.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(EpubUIPlugin.PLUGIN_ID,
				"icons/contents.gif"));
		showAnnotations.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(EpubUIPlugin.PLUGIN_ID,
				"icons/marking.gif"));
	}

	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.remove(listener);
	}

	public void selectionChanged(SelectionChangedEvent event) {
		fireSelectionChanged(event.getSelection());
	}

	/**
	 * Sets focus to a part in the page.
	 */
	@Override
	public void setFocus() {
		toc.getControl().setFocus();
	}

	public void setSelection(ISelection selection) {
		if (toc != null) {
			toc.setSelection(selection);
		}
	}
}
