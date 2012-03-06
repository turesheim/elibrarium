/*******************************************************************************
 * Copyright (c) 2011,2012 Torkild U. Resheim.
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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.util.Date;
import java.util.UUID;

import no.resheim.elibrarium.epub.core.EPUBCorePlugin;
import no.resheim.elibrarium.epub.core.EPUBUtil;
import no.resheim.elibrarium.library.Annotation;
import no.resheim.elibrarium.library.AnnotationColor;
import no.resheim.elibrarium.library.Book;
import no.resheim.elibrarium.library.LibraryFactory;
import no.resheim.elibrarium.library.Marker;
import no.resheim.elibrarium.library.core.LibraryPlugin;
import no.resheim.elibrarium.library.core.LibraryUtil;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil.FeatureEList;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.mylyn.docs.epub.core.EPUB;
import org.eclipse.mylyn.docs.epub.core.OPSPublication;
import org.eclipse.mylyn.docs.epub.dc.Title;
import org.eclipse.mylyn.docs.epub.ncx.NavPoint;
import org.eclipse.mylyn.docs.epub.opf.Item;
import org.eclipse.mylyn.docs.epub.opf.Itemref;
import org.eclipse.mylyn.docs.epub.opf.Reference;
import org.eclipse.mylyn.docs.epub.opf.Type;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

/**
 * 
 * @author Torkild U. Resheim
 */
public class EPUBReader extends EditorPart {

	/**
	 * Listens to changes in the browser widget's size and starts paginating the
	 * current chapter and the entire book 500ms after the last resize event.
	 */
	private class ResizeListener implements ControlListener, Runnable, Listener {

		private static final int RESIZE_DELAY = 500;

		private long lastEvent = 0;

		private boolean mouse = true;

		public void controlMoved(ControlEvent e) {
		}

		public void controlResized(ControlEvent e) {
			lastEvent = System.currentTimeMillis();
			Display.getDefault().timerExec(RESIZE_DELAY, this);
		}

		private void paginate() {
			if (browser.getSize().x > 0 && browser.getSize().y > 0) {
				paginateChapter();
				paginationJob.update(browser.getSize().x, browser.getSize().y);
				updateLabels();
			}
		}

		@Override
		public void run() {
			if ((lastEvent + RESIZE_DELAY) < System.currentTimeMillis() && mouse) {
					paginate();
				} else {
				Display.getDefault().timerExec(RESIZE_DELAY, this);
				}
		}
		@Override
		public void handleEvent(Event event) {
			mouse = event.type == SWT.MouseUp;
		}

	}

	/**
	 * The direction of browsing.
	 */
	private enum Direction {
		BACKWARD, FORWARD, INITIAL, PAGE
	}

	/**
	 * Handles text selections. The function(Object[]) method is called from
	 * JavaScript executing in the browser when the user releases the mouse
	 * button.
	 */
	private class MarkTextHandler extends BrowserFunction {

		public MarkTextHandler(Browser browser) {
			super(browser, "javaMarkTextHandler");
		}

		@Override
		public Object function(Object[] arguments) {

			currentColor = null;
			currentRange = null;
			currentText = null;

			final String range = (String) arguments[0];
			final String text = (String) arguments[1];
			if (text.length() > 0) {
				currentRange = range;
				currentText = text;
			}
			if ((Boolean) arguments[2]) {
				currentColor = AnnotationColor.YELLOW;
			}
			return super.function(arguments);
		}

	}

	/**
	 * Performs selection of text in the browser.
	 */
	private class MarkTextItem {

		public MenuItem menuItem;

		public MarkTextItem(Menu parent, int style) {
			menuItem = new MenuItem(parent, style);
			menuItem.setText("Mark text");
			installListener();
		}

		private void installListener() {
			menuItem.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}

				@Override
				public void widgetSelected(SelectionEvent e) {
					String id = UUID.randomUUID().toString();
					if (browser.execute("markRange('" + currentRange + "','" + id + "');")) {
						Annotation annotation = LibraryFactory.eINSTANCE.createAnnotation();
						annotation.setId(id);
						annotation.setTimestamp(new Date());
						annotation.setLocation(currentRange);
						annotation.setText(currentText);
						annotation.setColor(AnnotationColor.YELLOW);
						annotation.setHref(currentHref);
						currentBook.getAnnotations().add(annotation);
					} else {
						System.err.println("Could not mark range!");
					}
				}
			});
		}

	}

	/**
	 * Listens to the pagination job and updates labels when it is done.
	 */
	private class PaginationJobListener extends JobChangeAdapter {

		@Override
		public void done(IJobChangeEvent event) {
			updateLabels();
		}

	}

	private class RemoveMarkedItem {

		public MenuItem menuItem;

		public RemoveMarkedItem(Menu parent, int style) {
			menuItem = new MenuItem(parent, style);
			menuItem.setText("Remove");
			installListener();
		}

		private void installListener() {
			menuItem.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}

				@Override
				public void widgetSelected(SelectionEvent e) {
					//XXX: Does not work!
//					if (browser.execute("unmarkRange('" + currentRange + "');")) {
//					} else {
//						System.err.println("Could not remove mark");
//					}
				}
			});
		}

	}

	protected static final String PROPERTY_TITLE = "title"; //$NON-NLS-1$

	private static final EStructuralFeature TEXT = XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text();

	public static final String WEB_BROWSER_EDITOR_ID = "org.eclipse.ui.browser.editor"; //$NON-NLS-1$

	protected Browser browser;

	private Book currentBook;

	private AnnotationColor currentColor;

	/** The current href, excluding the anchor */
	private String currentHref;

	/** The current anchor (may be null) */
	private String currentAnchor;

	private String currentRange;

	private String currentText;

	/** Used to navigate to a certain page */
	private Direction direction = Direction.INITIAL;

	/** Page to navigate to if direction is PAGE */
	private int page;

	private boolean disposed;

	protected Image image;

	protected String initialURL;

	private Label label;

	private Menu menu;

	private Object outlinePage;

	private OPSPublication ops;

	private int pageCount;

	private int pageWidth;

	PaginationJob paginationJob;

	boolean paginationRequired = true;

	private ResizeListener resizeListener;

	private Label header;

	public EPUBReader() {
		super();
	}

	/**
	 * Browses to the next or previous chapter depending on the direction.
	 * 
	 * @param direction
	 *            the browsing direction.
	 */
	private void navigateChapter(Direction direction) {
		this.direction = direction;
		int currentChapter = getCurrentChapter();
		switch (direction) {
		case FORWARD:
			currentChapter++;
			break;
		case BACKWARD:
			currentChapter--;
			break;
		default:
			break;
		}
		EList<Itemref> spineItems = ops.getOpfPackage().getSpine().getSpineItems();
		if (currentChapter > 0 && currentChapter <= spineItems.size()) {
			Item item = ops.getItemById(spineItems.get(currentChapter - 1).getIdref());
			openItem(item);
		}
	}

	/**
	 * Navigates to the given page number of the chapter.
	 * 
	 * @param page
	 *            the page number to go to
	 */
	private void navigateToPage(int page) {
		String title = (String) browser.evaluate("title = navigateToPage(" + page + ");return title;");
		// Fake a small caps effect.
		title = title.toUpperCase();
		StringReader sr = new StringReader(title);
		StringBuilder sb = new StringBuilder();
		int c = -1;
		try {
			while ((c = sr.read()) > -1) {
				sb.append((char) c);
				sb.append(' ');
			}
			header.setText(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		updateLabels();
	}

	/**
	 * Close the editor correctly.
	 */
	public boolean close() {
		final boolean[] result = new boolean[1];
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				result[0] = getEditorSite().getPage().closeEditor(EPUBReader.this, false);
			}
		});
		return result[0];
	}

	/*
	 * Creates the SWT controls for this workbench part.
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite c = new Composite(parent, SWT.NONE);
		c.setLayout(new GridLayout());
		c.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

		GridData gd = new GridData(SWT.CENTER, SWT.BOTTOM, true, false);
		header = new Label(c, SWT.CENTER);
		header.setLayoutData(gd);
		header.setText(" ");
		header.setForeground(JFaceResources.getColorRegistry().get(JFacePreferences.QUALIFIER_COLOR));

		// We rely on having a WebKit based browser for now
		browser = new Browser(c, SWT.WEBKIT);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		label = new Label(c, SWT.CENTER);
		gd.minimumWidth = 500;
		label.setLayoutData(gd);
		label.setText(" ");
		label.setForeground(JFaceResources.getColorRegistry().get(JFacePreferences.QUALIFIER_COLOR));

		// Install listener to figure out when we need to re-paginate
		resizeListener = new ResizeListener();
		browser.addControlListener(resizeListener);
		browser.getDisplay().addFilter(SWT.MouseDown, resizeListener);
		browser.getDisplay().addFilter(SWT.MouseUp, resizeListener);

		// Various javascript
		installInjector();

		// Handle key-presses
		installKeyListener();

		// These events are triggered when the location of the browser has
		// changed, that is when the URL has changed. It may or may not be
		// within the document currently open. It does normally not change when
		// browsing between pages unless the chapter has been changed.
		browser.addLocationListener(new LocationListener() {

			@Override
			public void changed(LocationEvent event) {
				updateLabels();
			}

			@Override
			public void changing(LocationEvent event) {
			}
		});

		browser.setCapture(false);
		if (browser != null) {
			if (initialURL != null) {
				browser.setUrl(initialURL);
			}
		}

		new MarkTextHandler(browser);

		// Create a new menu for the browser. We want this dynamically populated
		// so all items are removed when the menu is shown and it will be
		// re-populated.
		menu = new Menu(browser);
		browser.setMenu(menu);
		menu.addListener(SWT.Show, new Listener() {
			public void handleEvent(Event event) {
				MenuItem[] menuItems = menu.getItems();
				for (int i = 0; i < menuItems.length; i++) {
					menuItems[i].dispose();
				}
				populateMenu();
			}
		});

	}

	private boolean deleteFolder(File folder) {
		if (folder.isDirectory()) {
			String[] children = folder.list();
			for (int i = 0; i < children.length; i++) {
				boolean ok = deleteFolder(new File(folder, children[i]));
				if (!ok) {
					return false;
				}
			}
		}
		return folder.delete();
	}

	@Override
	public void dispose() {
		paginationJob.cancel();
		// Delete the temporary folder where we keep the unpacked content.
		// if (unpackFolder.exists()) {
		// deleteFolder(unpackFolder);
		// }
		if (image != null && !image.isDisposed())
			image.dispose();
		image = null;
		disposed = true;
		super.dispose();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// do nothing
	}

	@Override
	public void doSaveAs() {
		// do nothing
	}

	public IActionBars getActionBars() {
		return getEditorSite().getActionBars();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class required) {
		if (IContentOutlinePage.class.equals(required)) {
			if (outlinePage == null) {
				outlinePage = new TOCOutlinePage(ops, this);
			}
			return outlinePage;
		}
		return super.getAdapter(required);
	}

	/**
	 * Determines the chapter number of the item currently displayed. If
	 * <b>-1</b> is returned, the item is not in the spine.
	 * 
	 * @return the current chapter, starting from chapter one.
	 */
	private int getCurrentChapter() {
		int currentChapter = -1;
		EList<Itemref> spineItems = ops.getOpfPackage().getSpine().getSpineItems();
		EList<Item> items = ops.getOpfPackage().getManifest().getItems();
		String ref = currentHref;
		for (Item item : items) {
			if (item.getHref().equals(ref)) {
				for (int c = 0; c < spineItems.size(); c++) {
					if (spineItems.get(c).getIdref().equals(item.getId())) {
						currentChapter = c + 1;
						break;
					}
				}
			}
		}
		return currentChapter;
	}

	/**
	 * Determines the current page in the chapter using the content viewport
	 * position. Since this offset may not be exactly the same as the start of
	 * the page we need to do a bit of calculation.
	 * 
	 * @return
	 */
	private int getCurrentChapterPage() {
		int page = 1;
		int offset = (int) Math.round((Double) browser
				.evaluate("bodyID = document.getElementsByTagName('body')[0];return bodyID.scrollLeft"));
		if (offset != 0) {
			for (int i = 0; i < pageCount; i++) {
				if (offset <= i * pageWidth) {
					page = i + 1;
					break;
				}
			}
		}
		return page;
	}

	private int getCurrentPage() {
		int page = 0;
		int chapterPage = getCurrentChapterPage();
		int chapter = getCurrentChapter();
		int[] chapterSizes = paginationJob.getChapterSizes();
		if (chapterSizes.length < chapter) {
			return 0;
		}
		for (int i = 0; i < chapter - 1; i++) {
			page += chapterSizes[i];
		}
		page += chapterPage;
		return page;
	}

	/**
	 * Returns the URL of the first text page of the publication. That is
	 * excluding any cover page etc.
	 * 
	 * @return URL of the first text page
	 */
	private String getFirstPublicationPage() {
		// First try the first TEXT type page if there is a guide.
		if (ops.getOpfPackage().getGuide() != null) {
			EList<Reference> references = ops.getOpfPackage().getGuide().getGuideItems();
			for (Reference reference : references) {
				if (reference.getType().equals(Type.TEXT)) {
					String url = "file:" + ops.getRootFolder().getAbsolutePath() + File.separator + reference.getHref();
					return url;
				}
			}
		}
		// Then try the first page in the spine
		EList<Itemref> items = ops.getOpfPackage().getSpine().getSpineItems();
		for (Itemref itemref : items) {
			if (itemref.getLinear() == null || Boolean.parseBoolean(itemref.getLinear())) {
				Item item = ops.getItemById(itemref.getIdref());
				String url = "file:" + ops.getRootFolder().getAbsolutePath() + File.separator + item.getHref();
				return url;
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public String getTitle(OPSPublication epub) {
		EList<Title> titles = epub.getOpfPackage().getMetadata().getTitles();
		if (titles.size() > 0) {
			FeatureMap fm = titles.get(0).getMixed();
			Object o = fm.get(TEXT, false);
			if (o instanceof FeatureEList) {
				if (((FeatureEList) o).size() > 0) {
					return ((FeatureEList) o).get(0).toString();
				}
			}
		}
		return "";
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (input instanceof IPathEditorInput) {
			IPathEditorInput pei = (IPathEditorInput) input;
			IPath path = pei.getPath();
			EPUB epub = new EPUB();
			try {
				// If the EPUB has already been unpacked it's contents will be
				// used as it is unless the modification dates differ.
				IPath storageLocation = LibraryPlugin.getDefault().getStorageLocation();
				File rootFolder = storageLocation.append(path.lastSegment()).toFile();
				if (rootFolder.lastModified() != path.toFile().lastModified()) {
					deleteFolder(rootFolder);
				}
				epub.unpack(path.toFile(), rootFolder);
				// Use the first OPS publication we find
				ops = epub.getOPSPublications().get(0);
				initialURL = getFirstPublicationPage();
				setPartName(getTitle(ops));
				paginationJob = new PaginationJob(ops);
				paginationJob.setUser(false);
				paginationJob.setPriority(Job.LONG);
				paginationJob.addJobChangeListener(new PaginationJobListener());
				registerBook(path, ops);
				installBookListener(currentBook);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			IPathEditorInput pinput = (IPathEditorInput) input.getAdapter(IPathEditorInput.class);
			if (pinput != null) {
				init(site, pinput);
			} else {
				throw new PartInitException(NLS.bind("Invalid editor input", input.getName()));
			}
		}

		setSite(site);
		setInput(input);
	}

	/**
	 * Install a listener that will respond to changes in the book.
	 * 
	 * @param book
	 *            the book to listen to
	 */
	private void installBookListener(Book book) {
		Adapter adapter = new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				EReference ref = (EReference) notification.getFeature();
				if (ref.getName().equals("annotations")) {
					// An annotation has been removed. Due to difficulties
					// simply removing the marking using JavaScript we reload
					// the URL and set the page to the current page. That will
					// refresh the contents, although cause a bit of flickering.
					if (notification.getEventType() == Notification.REMOVE) {
						page = getCurrentChapterPage();
						direction = Direction.PAGE;
						browser.setUrl(browser.getUrl());
					}
				}
			}
		};
		book.eAdapters().add(adapter);
	}

	private void installInjector() {
		browser.addProgressListener(new ProgressListener() {
			@Override
			public void changed(ProgressEvent event) {
			}

			@Override
			public void completed(ProgressEvent event) {
				// Ignore this one.
				if (browser.getUrl().equals("about:blank")) {
					return;
				}

				// Detect the current href and anchor
				String url = browser.getUrl().substring(browser.getUrl().lastIndexOf('/') + 1);
				if (url.indexOf('#') > -1) {
					currentHref = url.substring(0, url.indexOf('#'));
					currentAnchor = url.substring(url.indexOf('#') + 1);
				} else {
					currentHref = url;
					currentAnchor = null;
				}

				// Do the pagination of the chapter
				paginateChapter();

				// Add annotations and markers.
				EList<Annotation> annotations = currentBook.getAnnotations();
				for (Annotation annotation : annotations) {
					if (annotation.getHref() != null && annotation.getHref().equals(currentHref)) {
						String id = annotation.getId();
						if (!browser.execute("markRange('" + annotation.getLocation() + "','" + id + "');")) {
							System.err.println("Could not create marker identified by " + id + " at "
									+ annotation.getLocation());
						}
					}
				}

				// And adjust offset so that it matches the one of the anchor if
				// one has been specified.
				if (currentAnchor != null) {
					if (browser.execute("setOffsetToElement('" + currentAnchor + "')")) {
						System.out.println("Adjusted offset for " + currentAnchor);
					} else {
						System.err.println("Could not correct position " + currentAnchor);
					}
				}

				// Navigate to a a certain page.
				switch (direction) {
				case FORWARD:
					navigateToPage(1);
					break;
				case BACKWARD:
					navigateToPage(pageCount);
					break;
				case PAGE:
					navigateToPage(page);
					break;
				default:
					break;
				}
				direction = Direction.INITIAL;

				// Size may be 0,0 when the view is first opened so we want to
				// delay until the browser is resized.
				if (paginationRequired && browser.getSize().x > 0 && browser.getSize().y > 0) {
					paginationJob.update(browser.getSize().x, browser.getSize().y);
					paginationRequired = false;
				}
			}
		});
	}

	/**
	 * Installs key handling for the reader.
	 */
	private void installKeyListener() {
		browser.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.ARROW_RIGHT) {
					e.doit = false;
				}
				if (e.keyCode == SWT.ARROW_LEFT) {
					e.doit = false;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.ARROW_RIGHT) {
					nextPage();
					e.doit = false;
				}
				if (e.keyCode == SWT.ARROW_LEFT) {
					previousPage();
					e.doit = false;
				}
			}
		});
	};

	/*
	 * (non-Javadoc) Returns whether the contents of this editor have changed
	 * since the last save operation.
	 */
	@Override
	public boolean isDirty() {
		return false;
	}

	/*
	 * (non-Javadoc) Returns whether the "save as" operation is supported by
	 * this editor.
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	private void updateHeading(NavPoint navPoint) {
		FeatureMap fm = navPoint.getNavLabels().get(0).getText().getMixed();
		Object o = fm.get(TEXT, false);
		if (o instanceof FeatureEList) {
			if (((FeatureEList) o).size() > 0) {
				String label = ((FeatureEList) o).get(0).toString();
				System.out.println(label);
			}
		}

	}

	/**
	 * Use to navigate to a specific {@link NavPoint}.
	 * 
	 * @param navPoint
	 *            the point to navigate to
	 */
	public void navigateTo(NavPoint navPoint) {
		try {
			String ref = navPoint.getContent().getSrc();
			String url = "file:" + ops.getRootFolder().getAbsolutePath() + File.separator + ref;
			direction = Direction.INITIAL;
			// XXX: Clear URL to force reload
			browser.setText("<html/>");
			browser.setUrl(url);
			updateHeading(navPoint);
			setPartName(getTitle(ops));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void navigateTo(Marker marker) {
		String ref = marker.getHref();
		String url = "file:" + ops.getRootFolder().getAbsolutePath() + File.separator + ref + "#" + marker.getId();
		direction = Direction.INITIAL;
		// XXX: Clear URL to force reload
		browser.setText("<html/>");
		browser.setUrl(url);
		setPartName(getTitle(ops));
	}

	/**
	 * Browse to the next page in the reading order. If already on the last page
	 * of the chapter, the first page of the next chapter will be shown.
	 */
	public void nextPage() {
		int page = getCurrentChapterPage();
		if (page < pageCount) {
			navigateToPage(++page);
		} else if (page >= pageCount) {
			navigateChapter(Direction.FORWARD);
		}
	}

	public void openInExternalBrowser(String url) {
		throw new RuntimeException("Not implemented");
	}

	private void openItem(Item item) {
		String url = "file:" + ops.getRootFolder().getAbsolutePath() + File.separator + item.getHref();
		browser.setUrl(url);
		setPartName(getTitle(ops));
		updateLabels();
	}

	/**
	 * Executes JavaScript that will reformat the chapter and obtain information
	 * that is required for browsing it.
	 */
	private void paginateChapter() {
		StringBuilder sb = new StringBuilder();
		sb.append("desiredWidth = " + browser.getSize().x + ";");
		sb.append("desiredHeight = " + (browser.getSize().y - 20) + ";");
		try {
			readJS("rangy-core.js", sb);
			readJS("rangy-serializer.js", sb);
			readJS("rangy-cssclassapplier.js", sb);
			readJS("jquery-1.7.1.min.js", sb);
			readJS("injected.js", sb);
			boolean ok = browser.execute(sb.toString());
			if (ok) {
				pageCount = (int) Math.round((Double) browser.evaluate("return pageCount"));
				pageWidth = (int) Math.round((Double) browser.evaluate("return desiredWidth"));
			} else {
				System.err.println("Could not paginate");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void populateMenu() {
		if (currentColor != null) {
			new RemoveMarkedItem(menu, SWT.PUSH);
		} else if (currentRange != null) {
			// Allow text to be marked
			new MarkTextItem(menu, SWT.PUSH);
		}
	}

	/**
	 * Browse to the previous page in the reading order. If already on the first
	 * page of the chapter, the last page of the previous chapter will be shown.
	 */
	public void previousPage() {
		int page = getCurrentChapterPage();
		if (page > 1) {
			navigateToPage(--page);
		} else {
			navigateChapter(Direction.BACKWARD);
		}
	}

	/**
	 * Reads the (JavaScript) file and appends the content to the given buffer.
	 * 
	 * @param filename
	 *            file to read
	 * @param sb
	 *            buffer to add to
	 * @throws IOException
	 */
	private void readJS(String filename, StringBuilder sb) throws IOException {
		String in;
		BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(filename)));
		while ((in = br.readLine()) != null) {
			sb.append(in);
			sb.append('\n');
		}
	}

	private void registerBook(IPath path, OPSPublication ops) {
		String title = EPUBUtil.getFirstTitle(ops);
		String author = EPUBUtil.getFirstAuthor(ops);
		String id = EPUBUtil.getIdentifier(ops);
		if (!EPUBCorePlugin.getCollection().hasBook(id)) {
			URI uri = path.toFile().toURI();
			currentBook = LibraryUtil.createNewBook(EPUBCorePlugin.COLLECTION_ID, uri, id, title, author);
			EPUBCorePlugin.getCollection().add(currentBook);
		} else {
			currentBook = EPUBCorePlugin.getCollection().getBook(id);
		}
	}

	/*
	 * Asks this part to take focus within the workbench.
	 */
	@Override
	public void setFocus() {
		if (browser != null)
			browser.setFocus();
	}

	private void updateLabels() {
		if (!disposed) {
			label.getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					synchronized (paginationJob) {
						if (paginationJob.getState() == Job.NONE) {
							label.setText("Page " + getCurrentPage() + " of " + paginationJob.getTotalpages());
						} else {
							label.setText("Paginating...");
						}
					}
				}
			});
		}

	}
}
