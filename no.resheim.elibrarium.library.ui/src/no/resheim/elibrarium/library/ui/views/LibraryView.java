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
import java.util.Collections;
import java.util.Date;

import no.resheim.elibrarium.library.Book;
import no.resheim.elibrarium.library.core.ILibraryListener;
import no.resheim.elibrarium.library.core.LibraryPlugin;
import no.resheim.elibrarium.library.ui.LibraryLabelProvider;
import no.resheim.elibrarium.library.ui.LibraryUIPlugin;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.DecoratingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.mylyn.commons.notifications.ui.AbstractUiNotification;
import org.eclipse.mylyn.commons.notifications.ui.NotificationsUi;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;

@SuppressWarnings("restriction")
public class LibraryView extends ViewPart implements ILibraryListener {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String VIEW_ID = "no.resheim.elibrarium.library.ui.views.LibraryView";

	private TableViewer viewer;

	private Action doubleClickAction;

	private void refreshView() {
		getSite().getShell().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (viewer != null) {
					viewer.refresh();

				}
			}
		});
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

	class NameSorter extends ViewerSorter {
		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			if (e1 instanceof Book && e2 instanceof Book) {
				String t1 = ((Book) e1).getTitle().toLowerCase();
				String t2 = ((Book) e2).getTitle().toLowerCase();
				if (t1.startsWith("the ")) {
					t1 = t1.substring(4);
				}
				if (t2.startsWith("the ")) {
					t2 = t2.substring(4);
				}
				int result = t1.compareTo(t2);
				if (result != 0) {
					return result;
				}
			}
			// fall back to comparing by label
			return super.compare(viewer, e1, e2);
		}
	}

	/**
	 * The constructor.
	 */
	public LibraryView() {
	}

	public class TableDecoratingLabelProvider extends DecoratingLabelProvider implements ITableLabelProvider {

		ITableLabelProvider provider;

		ILabelDecorator decorator;

		/**
		 * @param provider
		 * @param decorator
		 */
		public TableDecoratingLabelProvider(ILabelProvider provider, ILabelDecorator decorator) {
			super(provider, decorator);
			this.provider = (ITableLabelProvider) provider;
			this.decorator = decorator;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java
		 * .lang.Object, int)
		 */
		public Image getColumnImage(Object element, int columnIndex) {
			Image image = provider.getColumnImage(element, columnIndex);
			if (decorator != null) {
				Image decorated = decorator.decorateImage(image, element);
				if (decorated != null) {
					return decorated;
				}
			}
			return image;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.
		 * lang.Object, int)
		 */
		public String getColumnText(Object element, int columnIndex) {
			String text = provider.getColumnText(element, columnIndex);
			if (decorator != null) {
				String decorated = decorator.decorateText(text, element);
				if (decorated != null) {
					return decorated;
				}
			}
			return text;
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setSorter(new NameSorter());

		viewer.setLabelProvider(new DecoratingStyledCellLabelProvider(new LibraryLabelProvider(), null, null));
		// Register the selection provider
		getSite().setSelectionProvider(viewer);

		makeActions();
		hookContextMenu();
		hookDoubleClickAction();

		// Activate the view specific context
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				((IContextService) PlatformUI.getWorkbench().getService(IContextService.class))
						.activateContext(VIEW_ID);
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
	public void bookAdded(final Book book) {
		refreshView();
		AbstractUiNotification notification = new AbstractUiNotification("no.resheim.elibrarium.library.ui.event") {

			@SuppressWarnings("rawtypes")
			@Override
			public Object getAdapter(Class adapter) {
				return null;
			}

			@Override
			public String getLabel() {
				return "Book added";
			}

			@Override
			public String getDescription() {
				return "Added \"" + book.getTitle() + "\" to library.";
			}

			@Override
			public Date getDate() {
				return new Date(System.currentTimeMillis());
			}

			@Override
			public Image getNotificationImage() {
				return null;
			}

			@Override
			public Image getNotificationKindImage() {
				return LibraryUIPlugin.getDefault().getImageRegistry().get(LibraryUIPlugin.IMG_BOOK);
			}

			@Override
			public void open() {
				// TODO Auto-generated method stub

			}
		};
		NotificationsUi.getService().notify(Collections.singletonList(notification));
	}

	@Override
	public void bookRemoved(Book book) {
		refreshView();
	}
}