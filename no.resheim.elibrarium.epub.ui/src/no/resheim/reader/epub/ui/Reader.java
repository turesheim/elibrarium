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
package no.resheim.reader.epub.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil.FeatureEList;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.mylyn.docs.epub.core.EPUB;
import org.eclipse.mylyn.docs.epub.core.OPSPublication;
import org.eclipse.mylyn.docs.epub.dc.Title;
import org.eclipse.mylyn.docs.epub.ncx.NavPoint;
import org.eclipse.mylyn.docs.epub.opf.Item;
import org.eclipse.mylyn.docs.epub.opf.Itemref;
import org.eclipse.mylyn.docs.epub.opf.Reference;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

// TODO: Determine the number of pages in the spine when opening or resizing
// TODO: Show chapter title in top of page
// TODO: Synchronize with outline
// TODO: Support Eclipse location (previous/next)
// TODO: Support Eclipse search features
// TODO: Ctrl+O for quick outline
// TODO: Inhibit opening of external links

/**
 * 
 * @author Torkild U. Resheim
 */
public class Reader extends EditorPart {

	private enum Direction {
		BACKWARD, FORWARD, INITIAL
	}

	private class PaginationJobListener extends JobChangeAdapter {

		@Override
		public void done(IJobChangeEvent event) {
			updateLabels();
		}

	}

	private class MarkTextItem {

		private String range;

		public MenuItem menuItem;

		public MarkTextItem(Menu parent, int style) {
			menuItem = new MenuItem(parent, style);
			menuItem.setText("Mark text");
			installListener();
		}

		public void setData(String range) {
			this.range = range;
		}

		private void installListener() {
			menuItem.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					browser.execute("markText('" + range + "');");
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
		}

	}

	/**
	 * 
	 * @author Torkild U. Resheim
	 */
	private class MarkTextHandler extends BrowserFunction {

		@Override
		public Object function(Object[] arguments) {
			final String range = (String) arguments[0];
			// Pop up the selected text menu
			// markTextItem.setData(range);
			// menu.setVisible(true);
			return super.function(arguments);
		}

		public MarkTextHandler(Browser browser) {
			super(browser, "javaMarkTextHandler");
		}

	}

	protected static final String PROPERTY_TITLE = "title"; //$NON-NLS-1$

	private static final EStructuralFeature TEXT = XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text();

	public static final String WEB_BROWSER_EDITOR_ID = "org.eclipse.ui.browser.editor"; //$NON-NLS-1$

	protected Browser browser;

	private String currentHref;

	private Direction direction = Direction.INITIAL;

	protected Image image;

	protected String initialURL;

	private Label label;

	private Object myOutlinePage;

	private OPSPublication ops;

	private int pageCount;

	private int pageWidth;

	PaginationJob paginationJob;

	boolean paginationRequired = true;

	private File unpackFolder;

	private Menu menu;

	private MarkTextItem markTextItem;

	/**
	 * WebBrowserEditor constructor comment.
	 */
	public Reader() {
		super();
	}

	private void browseChapter(Direction direction) {
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
			String url = "file:" + ops.getRootFolder().getAbsolutePath() + File.separator + item.getHref();
			setCurrentHref(item.getHref());
			browser.setUrl(url);
			setPartName(getTitle(ops));
			updateLabels();
		}
	}

	/**
	 * Navigates to the given page number of the chapter.
	 * 
	 * @param page
	 *            the page number to go to
	 */
	private void browseToPage(int page) {
		String js = "var bodyID = document.getElementsByTagName('body')[0];" + "bodyID.scrollLeft = "
				+ (pageWidth * (page - 1)) + ";";
		if (!browser.execute(js)) {
			throw new RuntimeException("Could not browse to page " + page + " of chapter");
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
				result[0] = getEditorSite().getPage().closeEditor(Reader.this, false);
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
		// We rely on having a WebKit based browser for now
		browser = new Browser(c, SWT.WEBKIT);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		label = new Label(c, SWT.CENTER);
		// label.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
		GridData gd = new GridData(SWT.CENTER, SWT.BOTTOM, true, false);
		gd.minimumWidth = 500;
		label.setLayoutData(gd);
		label.setText(" ");

		installResizeListener();
		installInjector();
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

		MarkTextHandler markTextHandler = new MarkTextHandler(browser);
		menu = new Menu(browser);
		markTextItem = new MarkTextItem(menu, SWT.PUSH);

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
		// Delete the temporary folder where we keep the unpacked content.
		if (unpackFolder.exists()) {
			deleteFolder(unpackFolder);
		}
		if (image != null && !image.isDisposed())
			image.dispose();
		image = null;

		super.dispose();
		// mark this instance as disposed to avoid stale references
		// disposed = true;
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
			if (myOutlinePage == null) {
				myOutlinePage = new TOCOutlinePage(ops, this);
			}
			return myOutlinePage;
		}
		return super.getAdapter(required);
	}

	private String getCurentHref() {
		return currentHref;
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
		if (ref.indexOf('#') > -1) {
			ref = ref.substring(0, ref.indexOf('#'));
		}
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
		// First try the first TEXT type page
		EList<Reference> references = ops.getOpfPackage().getGuide().getGuideItems();
		for (Reference reference : references) {
			if (reference.getType().equals("text")) {
				String url = "file:" + ops.getRootFolder().getAbsolutePath() + File.separator + reference.getHref();
				setCurrentHref(reference.getHref());
				return url;
			}
		}
		// Then try the first page in the spine
		EList<Itemref> items = ops.getOpfPackage().getSpine().getSpineItems();
		for (Itemref itemref : items) {
			if (itemref.getLinear() == null || Boolean.parseBoolean(itemref.getLinear())) {
				Item item = ops.getItemById(itemref.getIdref());
				String url = "file:" + ops.getRootFolder().getAbsolutePath() + File.separator + item.getHref();
				setCurrentHref(item.getHref());
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
				unpackFolder = epub.unpack(path.toFile());
				// Use the first OPS publication we find
				ops = epub.getOPSPublications().get(0);
				initialURL = getFirstPublicationPage();
				setPartName(getTitle(ops));
				paginationJob = new PaginationJob(ops);
				paginationJob.addJobChangeListener(new PaginationJobListener());
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
	};

	private void installInjector() {
		browser.addProgressListener(new ProgressListener() {
			@Override
			public void changed(ProgressEvent event) {
			}

			@Override
			public void completed(ProgressEvent event) {
				int page = 1;
				paginateChapter();
				switch (direction) {
				case FORWARD:
					page = 1;
					break;
				case BACKWARD:
					page = pageCount;
					break;
				default:
					page = 1;
					break;
				}
				browseToPage(page);
				// Size may be 0,0 when the view is first opened so we want to
				// delay until the browser is resized.
				if (paginationRequired && browser.getSize().x > 0) {
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
	}

	private void installResizeListener() {
		browser.addControlListener(new ControlListener() {

			public void controlMoved(ControlEvent e) {
			}

			public void controlResized(ControlEvent e) {
				paginateChapter();
				paginationJob.update(browser.getSize().x, browser.getSize().y);
				updateLabels();
			}
		});
	}

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
			if (!getCurentHref().equals(ref)) {
				setCurrentHref(ref);
				direction = Direction.INITIAL;
				browser.setUrl(url);
				setPartName(getTitle(ops));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Browse to the next page in the reading order. If already on the last page
	 * of the chapter, the first page of the next chapter will be shown.
	 */
	public void nextPage() {
		int page = getCurrentChapterPage();
		if (page < pageCount) {
			browseToPage(++page);
		} else if (page >= pageCount) {
			browseChapter(Direction.FORWARD);
		}
	}

	public void openInExternalBrowser(String url) {
		throw new RuntimeException("Not implemented");
	}

	/**
	 * Executes JavaScript that will reformat the chapter and obtain information
	 * that is required for browsing it.
	 * 
	 * @return
	 */
	private void paginateChapter() {
		StringBuilder sb = new StringBuilder();
		sb.append("desiredWidth = " + browser.getSize().x + ";");
		sb.append("desiredHeight = " + browser.getSize().y + "-20;");
		try {
			readJS("rangy-core.js", sb);
			readJS("rangy-serializer.js", sb);
			readJS("rangy-cssclassapplier.js", sb);
			readJS("injected.js", sb);
			boolean ok = browser.execute(sb.toString());
			if (ok) {
				pageCount = (int) Math.round((Double) browser.evaluate("return pageCount"));
				pageWidth = (int) Math.round((Double) browser.evaluate("return desiredWidth"));
			} else {
				System.err.println("Could not paginate");
				// throw new RuntimeException("Could not paginate");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readJS(String filename, StringBuilder sb) throws IOException {
		String in;
		BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(filename)));
		while ((in = br.readLine()) != null) {
			sb.append(in);
			sb.append('\n');
		}
	}

	/**
	 * Browse to the previous page in the reading order. If already on the first
	 * page of the chapter, the last page of the previous chapter will be shown.
	 */
	public void previousPage() {
		int page = getCurrentChapterPage();
		if (page > 1) {
			browseToPage(--page);
		} else {
			browseChapter(Direction.BACKWARD);
		}
	}

	private void setCurrentHref(String href) {
		currentHref = href;
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
		label.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				synchronized (paginationJob) {
					if (paginationJob.getState() == Job.NONE) {
						label.setText("" + getCurrentPage() + " of " + paginationJob.getTotalpages());
					} else {
						label.setText("Paginating...");
					}
				}
			}
		});

	}
}
