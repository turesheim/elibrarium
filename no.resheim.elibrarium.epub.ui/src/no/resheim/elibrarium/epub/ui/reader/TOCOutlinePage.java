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
import org.eclipse.jface.resource.FontRegistry;
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
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.mylyn.docs.epub.core.OPSPublication;
import org.eclipse.mylyn.docs.epub.ncx.NavPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.themes.ITheme;
import org.eclipse.ui.themes.IThemeManager;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.ocpsoft.pretty.time.PrettyTime;

/**
 * Use to provide an outline page for EPUB table of contents.
 * 
 * @author Torkild U. Resheim
 */
public class TOCOutlinePage extends Page implements IContentOutlinePage, ISelectionChangedListener,
		IDoubleClickListener, IPropertyChangeListener {

	private static final String TITLE_FONT = "no.resheim.elibrarium.epub.ui.titleFont";

	private static final String DATE_FONT = "no.resheim.elibrarium.epub.ui.dateFont";
	/**
	 * Use to sort bookmarks by page numbers. The bookmark with the lowest
	 * number will come first.
	 */
	private final class BookmarksComparator extends ViewerComparator {
		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			if (e1 instanceof Bookmark && e2 instanceof Bookmark) {
				int page_e1 = ((Bookmark) e1).getPage();
				int page_e2 = ((Bookmark) e2).getPage();
				return page_e1 - page_e2;
			}
			return super.compare(viewer, e1, e2);
		}
	}

	/**
	 * Lists bookmarks
	 */
	private final class BookmarksContentProvider implements IStructuredContentProvider {

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			if (parent instanceof Book) {
				Bookmark[] bookmarks = (Bookmark[]) ((Book) parent).getBookmarks().toArray();
				return bookmarks;
			}
			return new Object[0];
		}

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

	}

	private StackLayout layout;

	private TableViewer bookmarks;

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
	public void dispose() {
		// No longer listen to events from this book
		book.eAdapters().remove(bookAdapter);
		super.dispose();
	}

	@Override
	public void createControl(Composite parent) {
		book = EpubUtil.getBook(ops);

		pagebook = new Composite(parent, SWT.NONE);
		layout = new StackLayout();
		pagebook.setLayout(layout);

		toc = new TreeViewer(pagebook, getTreeStyle());
		toc.setContentProvider(new TOCContentProvider());
		toc.setLabelProvider(new EpubLabelProvider());
		toc.addSelectionChangedListener(this);
		toc.addDoubleClickListener(this);
		bookmarks = new TableViewer(pagebook, SWT.NONE);
		bookmarks.setContentProvider(new BookmarksContentProvider());
		bookmarks.setComparator(new BookmarksComparator());
		bookmarks.addSelectionChangedListener(this);
		bookmarks.addDoubleClickListener(this);
		bookmarks.getTable().setHeaderVisible(false);
		final TableViewerColumn column = new TableViewerColumn(bookmarks, SWT.LEFT);
		// Special drawing of bookmarks table
		installLabelProvider(column);
		// Automatic layout of bookmarks table
		installControlAdapter(column);

		GridData data = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);

		bookmarks.getControl().setLayoutData(data);
		// Create the context menu for the annotations
		hookContextMenu(bookmarks.getControl());

		// Set the input
		toc.setInput(ops);
		toc.expandAll();
		bookmarks.setInput(book);

		// Handles changes in the book and will refresh the bookmarks view.
		bookAdapter = new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				if (!bookmarks.getControl().isDisposed()) {
					bookmarks.getControl().getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							bookmarks.refresh(true);
						}
					});
				}
			}
		};
		book.eAdapters().add(bookAdapter);

		// Start by showing the table of contents
		showTOC.setChecked(true);
		layout.topControl = toc.getControl();
		pagebook.layout();
		JFaceResources.getFontRegistry().addListener(this);
		// Activate UI context for o.e.u.meny contributes items
		activateContext();
	}

	private void installControlAdapter(final TableViewerColumn column) {
		// Automatically adjust column and table sizes
		pagebook.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				org.eclipse.swt.graphics.Rectangle area = pagebook.getClientArea();
				Point preferredSize = bookmarks.getTable().computeSize(SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2 * bookmarks.getTable().getBorderWidth();
				if (preferredSize.y > area.height + bookmarks.getTable().getHeaderHeight()) {
					// Subtract the scrollbar width from the total column width
					// if a vertical scrollbar will be required
					Point vBarSize = bookmarks.getTable().getVerticalBar().getSize();
					width -= vBarSize.x;
				}
				Point oldSize = bookmarks.getTable().getSize();
				if (oldSize.x > area.width) {
					// table is getting smaller so make the columns
					// smaller first and then resize the table to
					// match the client area width
					column.getColumn().setWidth(width);
					bookmarks.getTable().setSize(area.width, area.height);
				} else {
					// table is getting bigger so make the table
					// bigger first and then make the columns wider
					// to match the client area width
					column.getColumn().setWidth(width);
					bookmarks.getTable().setSize(area.width, area.height);
				}
			}
		});
	}

	private void installLabelProvider(final TableViewerColumn column) {
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
				int height = size.y;
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

				// Draw the date
				gc.setFont(getFont(DATE_FONT));
				gc.setForeground(JFaceResources.getColorRegistry().get(JFacePreferences.QUALIFIER_COLOR));
				gc.drawText(date, width - size.x, event.y + size.y, true);
				// Paint the page number of the bookmark
				int pageNumber = bookmark.getPage();
				String page = Integer.toString(pageNumber);
				Point pageSize = event.gc.textExtent(page, SWT.DRAW_DELIMITER | SWT.DRAW_TAB);
				gc.drawText(page, width - 16 - pageSize.x, event.y, true);
				// Draw icon
				if (bookmark instanceof Annotation) {
				} else {
					int x = width - 9;
					gc.drawImage(EpubUIPlugin.getDefault().getImageRegistry().get(EpubUIPlugin.IMG_BOOKMARK), x,
							event.y + 1);
				}
				// Draw title
				gc.setFont(getFont(TITLE_FONT));
				gc.setForeground(display.getSystemColor(SWT.COLOR_LIST_FOREGROUND));
				// Make sure text does not span to far
				Region region = new Region();
				gc.getClipping(region);
				region.subtract(width - 32 - pageSize.x, event.y, width, height);
				gc.setClipping(region);
				region.dispose();
				String text = bookmark.getText();
				if (text == null) {
					text = "<missing text>";
				}
				if (bookmark instanceof Annotation) {
					if ((event.detail & SWT.SELECTED) == 0) {
						gc.setBackground(display.getSystemColor(SWT.COLOR_YELLOW));
						gc.drawText(text, event.x + 1, event.y, false);
					} else {
						gc.drawText(text, event.x + 1, event.y, true);
					}
				} else {
					gc.drawText(text, event.x + 1, event.y, true);
				}
				// Draw separator
				drawUnderline(event, gc, width, size, halfHeight);
			}

			public Font getFont(String fontName) {
				IThemeManager themeManager = PlatformUI.getWorkbench().getThemeManager();
				ITheme currentTheme = themeManager.getCurrentTheme();

				FontRegistry fontRegistry = currentTheme.getFontRegistry();
				Font font = fontRegistry.get(fontName);
				return font;
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
				event.width = bookmarks.getTable().getColumn(event.index).getWidth();
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

	private void hookContextMenu(Control control) {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				TOCOutlinePage.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(bookmarks.getControl());
		control.setMenu(menu);
		getSite().registerContextMenu("no.resheim.elibrarium.bookmarks", menuMgr, bookmarks);
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
				layout.topControl = bookmarks.getControl();
				pagebook.layout();
			}
		};

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

	private static final String VIEW_CONTEXT_ID = "no.resheim.elibrarium.bookmarks"; //$NON-NLS-1$

	private Adapter bookAdapter;

	private Book book;
	/**
	 * Activate a context that this view uses. It will be tied to this view
	 * activation events and will be removed when the view is disposed.
	 */
	private void activateContext() {
		IContextService contextService = (IContextService) getSite()
				.getService(IContextService.class);
		contextService.activateContext(VIEW_CONTEXT_ID);
	}
	@Override
	public void propertyChange(PropertyChangeEvent event) {
//		titleFont = JFaceResources.getFont("no.resheim.elibrarium.epub.ui.titleFont");
//		dateFont = JFaceResources.getFont("no.resheim.elibrarium.epub.ui.dateFont");
	}
}
