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
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OwnerDrawLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.mylyn.docs.epub.core.OPSPublication;
import org.eclipse.mylyn.docs.epub.ncx.NavPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.ocpsoft.pretty.time.PrettyTime;

/**
 * Use to provide an outline page for EPUB table of contents.
 * 
 * @author Torkild U. Resheim
 */
public class TOCOutlinePage extends Page implements IContentOutlinePage, ISelectionChangedListener,
		IDoubleClickListener, IPropertyChangeListener {

	private Font titleFont;

	private Font dateFont;

	class AnnotationsContentProvider implements IStructuredContentProvider {

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			if (parent instanceof Book) {
				Bookmark[] bookmarks = (Bookmark[]) ((Book) parent).getBookmarks().toArray();
				return bookmarks;
				// LineEntry[] entries = new LineEntry[bookmarks.length];
				// for (int i = 0; i < bookmarks.length; i++) {
				// Bookmark bookmark = bookmarks[i];
				// entries[i] = new LineEntry(bookmark.getText(), 35);
				// }
				// return entries;
			}
			return new Object[0];
		}

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

	}

	private Action deleteAction;

	private StackLayout layout;

	private TableViewer notes;

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
		titleFont = JFaceResources.getFont("no.resheim.elibrarium.epub.ui.titleFont");
		dateFont = JFaceResources.getFont("no.resheim.elibrarium.epub.ui.dateFont");

		Book book = EpubUtil.getBook(ops);

		pagebook = new Composite(parent, SWT.NONE);
		layout = new StackLayout();
		pagebook.setLayout(layout);

		toc = new TreeViewer(pagebook, getTreeStyle());
		toc.setContentProvider(new TOCContentProvider());
		toc.setLabelProvider(new EpubLabelProvider());
		toc.addSelectionChangedListener(this);
		toc.addDoubleClickListener(this);
		notes = new TableViewer(pagebook, SWT.NONE);
		notes.setContentProvider(new AnnotationsContentProvider());
		notes.addSelectionChangedListener(this);
		notes.addDoubleClickListener(this);
		notes.getTable().setHeaderVisible(false);
		final TableViewerColumn column = new TableViewerColumn(notes, SWT.LEFT);
		installLabelProvider(column);
		installControlAdapter(column);

		notes.getTable().addListener(SWT.EraseItem, new Listener() {
			public void handleEvent(Event event) {
				event.detail &= ~SWT.HOT;
				event.detail &= ~SWT.SELECTED;
			}
		});

		GridData data = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);

		notes.getControl().setLayoutData(data);
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

		JFaceResources.getFontRegistry().addListener(this);
	}

	public void installControlAdapter(final TableViewerColumn column) {
		// Automatically adjust column and table sizes
		pagebook.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				org.eclipse.swt.graphics.Rectangle area = pagebook.getClientArea();
				Point preferredSize = notes.getTable().computeSize(SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2 * notes.getTable().getBorderWidth();
				if (preferredSize.y > area.height + notes.getTable().getHeaderHeight()) {
					// Subtract the scrollbar width from the total column width
					// if a vertical scrollbar will be required
					Point vBarSize = notes.getTable().getVerticalBar().getSize();
					width -= vBarSize.x;
				}
				Point oldSize = notes.getTable().getSize();
				if (oldSize.x > area.width) {
					// table is getting smaller so make the columns
					// smaller first and then resize the table to
					// match the client area width
					column.getColumn().setWidth(width);
					notes.getTable().setSize(area.width, area.height);
				} else {
					// table is getting bigger so make the table
					// bigger first and then make the columns wider
					// to match the client area width
					column.getColumn().setWidth(width);
					notes.getTable().setSize(area.width, area.height);
				}
			}
		});
	}

	public void installLabelProvider(final TableViewerColumn column) {
		final PrettyTime pt = new PrettyTime();
		column.setLabelProvider(new OwnerDrawLabelProvider() {

			@Override
			protected void paint(Event event, Object element) {
				GC gc = event.gc;
				Display display = column.getViewer().getControl().getDisplay();
				Bookmark bookmark = (Bookmark) element;
				String date = pt.format(bookmark.getTimestamp());
				int width = column.getColumn().getWidth();
				// Calculate the size of the date string
				Point size = event.gc.textExtent(date, SWT.DRAW_DELIMITER | SWT.DRAW_TAB);
				int halfHeight = size.y / 2;
				// if ((event.detail & SWT.SELECTED) != 0) {
				// Region region = new Region();
				// gc.getClipping(region);
				// region.translate(10, 10);
				// // region.add(event.x, event.y - halfHeight, width,
				// // event.height - halfHeight);
				// gc.setClipping(region);
				// region.dispose();
				//
				// Rectangle rect = event.getBounds();
				// Color foreground = gc.getForeground();
				// Color background = gc.getBackground();
				// gc.setForeground(display.getSystemColor(SWT.COLOR_LIST_SELECTION));
				// gc.setBackground(display.getSystemColor(SWT.COLOR_LIST_BACKGROUND));
				// gc.fillGradientRectangle(0, rect.y, 500, rect.height, false);
				// // restore colors for subsequent drawing
				// gc.setForeground(foreground);
				// gc.setBackground(background);
				// event.detail &= ~SWT.SELECTED;
				// }
				gc.setFont(dateFont);
				gc.setForeground(JFaceResources.getColorRegistry().get(JFacePreferences.QUALIFIER_COLOR));
				gc.drawText(date, width - size.x, event.y + size.y, true);

				drawUnderline(event, gc, width, size, halfHeight);

				gc.setFont(titleFont);
				gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_LIST_FOREGROUND));
				if (bookmark instanceof Annotation) {
					if ((event.detail & SWT.SELECTED) == 0) {
						gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW));
						gc.drawText(bookmark.getText(), event.x + 1, event.y, false);
					} else {
						gc.drawText(bookmark.getText(), event.x + 1, event.y, true);
					}
				} else {
					int x = width - 9;
					gc.drawImage(EpubUIPlugin.getDefault().getImageRegistry().get(EpubUIPlugin.IMG_BOOKMARK), x,
							event.y + 1);
					gc.drawText(bookmark.getText(), event.x + 1, event.y, true);
				}
			}

			/**
			 * Draws a separator between the rows.
			 */
			public void drawUnderline(Event event, GC gc, int width, Point size, int halfHeight) {
				int y = (event.y + size.y * 2) + halfHeight - 2;
				int center = (width / 2);
				gc.setForeground(JFaceResources.getColorRegistry().get(JFacePreferences.QUALIFIER_COLOR));
				gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
				gc.fillGradientRectangle(center, y, center, 1, false);
				gc.setBackground(JFaceResources.getColorRegistry().get(JFacePreferences.QUALIFIER_COLOR));
				gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
				gc.fillGradientRectangle(0, y, center, 1, false);
			}

			@Override
			protected void measure(Event event, Object element) {
				String text = "A";
				Point size = event.gc.textExtent(text, SWT.DRAW_DELIMITER | SWT.DRAW_TAB);
				event.width = notes.getTable().getColumn(event.index).getWidth();
				// we need two lines of text and some space
				int halfHeight = size.y / 2;
				event.height = size.y * 2 + halfHeight;
			}
		});
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

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		titleFont = JFaceResources.getFont("no.resheim.elibrarium.epub.ui.titleFont");
		dateFont = JFaceResources.getFont("no.resheim.elibrarium.epub.ui.dateFont");
	}
}
