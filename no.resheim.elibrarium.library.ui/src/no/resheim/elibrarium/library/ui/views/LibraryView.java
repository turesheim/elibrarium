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
package no.resheim.elibrarium.library.ui.views;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import no.resheim.elibrarium.library.Book;
import no.resheim.elibrarium.library.core.ILibraryListener;
import no.resheim.elibrarium.library.core.LibraryPlugin;
import no.resheim.elibrarium.library.ui.LibraryUIPlugin;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;

public class LibraryView extends ViewPart implements ILibraryListener {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String VIEW_ID = "no.resheim.elibrarium.library.ui.views.LibraryView";

	private TableViewer viewer;

	private Action doubleClickAction;

	private void refreshView() {
		Runnable update = new Runnable() {
			public void run() {
				if (viewer != null) {
					viewer.refresh();

				}
			};
		};
		Display.getDefault().asyncExec(update);
	}

	class ViewContentProvider implements IStructuredContentProvider {

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			EList<Book> books = LibraryPlugin.getDefault().getLibrary().getBooks();
			return books.toArray();
		}
	}

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			if (obj instanceof Book) {
				return ((Book) obj).getTitle();
			}
			return getText(obj);
		}

		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}

		@Override
		public Image getImage(Object obj) {
			return LibraryUIPlugin.getDefault().getImageRegistry().get(LibraryUIPlugin.IMG_BOOK);
		}
	}

	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public LibraryView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());

		// Register the selection provider
		getSite().setSelectionProvider(viewer);

		makeActions();
		hookContextMenu();
		hookDoubleClickAction();

		// Activate the view specific context
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				((IContextService) PlatformUI.getWorkbench().getService(IContextService.class)).activateContext(VIEW_ID);
			}
		});
		// Set contents
		LibraryPlugin.getDefault().addListener(this);
		viewer.setInput(getViewSite());
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				LibraryView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void fillContextMenu(IMenuManager manager) {
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void makeActions() {

		doubleClickAction = new Action() {
			@Override
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection) selection).getFirstElement();
				if (obj instanceof Book) {
					try {
						File fileToOpen = new File(new URI(((Book) obj).getBookURL()));
						if (fileToOpen.exists() && fileToOpen.isFile()) {
							IFileStore fileStore = EFS.getLocalFileSystem().getStore(fileToOpen.toURI());
							IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
							try {
								IDE.openEditorOnFileStore(page, fileStore);
							} catch (PartInitException e) {
							}
						} else {
						}
					} catch (URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
		IContextService contextService = (IContextService) getSite().getService(IContextService.class);
		contextService.activateContext(VIEW_ID);
	}

	@Override
	public void bookAdded(Book book) {
		refreshView();
	}

	@Override
	public void bookRemoved(Book book) {
		refreshView();
	}
}