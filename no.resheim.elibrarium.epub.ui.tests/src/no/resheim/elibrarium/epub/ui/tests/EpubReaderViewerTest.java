/*******************************************************************************
 * Copyright (c) 2013 Torkild U. Resheim.
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *    Torkild U. Resheim - initial API and implementation
 *******************************************************************************/
package no.resheim.elibrarium.epub.ui.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import no.resheim.elibrarium.epub.ui.reader.EpubUiUtility;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * This type is for testing the EPUB reader viewer, or more specifically the
 * Javascript code used in the browser along with how the browser widget renders
 * the test chapters.
 * <p>
 * Screenshots are created for each test and placed in the "screenshots"
 * sub-folder.
 * </p>
 * 
 * @author Torkild U. Resheim
 */
public class EpubReaderViewerTest {

	/**
	 * Action to perform when the browser is done loading content.
	 */
	private abstract class ContentLoadedAction {
		public abstract void onLoad();

		public void verifyImage(Image image) {
			assertImageBasics(image);
		}
	}

	private class ContentLoader implements ProgressListener {

		private ContentLoadedAction action;

		@Override
		public void changed(ProgressEvent event) {
			// ignore
		}

		/**
		 * This event is triggered every time a full HTML file has been loaded
		 * into the browser component.
		 */
		@Override
		public void completed(ProgressEvent event) {
			browser.removeProgressListener(this);
			// Execute the action to perform when page has been loaded
			boolean ok;
			try {
				ok = utility.injectJavaScript(browser);
				Assert.assertTrue("Javascript could not be injected", ok);
				action.onLoad();
				// Finish all UI work
				while (display.readAndDispatch()) {
					display.sleep();
				}
				Image image = saveScreenshot();
				action.verifyImage(image);
				shell.dispose();
			} catch (IOException e) {
				Assert.fail(e.getMessage());
			}
		}

		public void load(String url, ContentLoadedAction action) {
			this.action = action;
			browser.addProgressListener(ContentLoader.this);
			browser.setUrl(url);
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		}
	}

	private static boolean deleteFolder(File folder) {
		if (folder.isDirectory()) {
			String[] children = folder.list();
			for (String element : children) {
				boolean ok = deleteFolder(new File(folder, element));
				if (!ok) {
					return false;
				}
			}
		}
		return folder.delete();
	}

	@BeforeClass
	public static void initialize() {
		deleteFolder(new File("screenshots"));
	}

	private Browser browser;
	private final Display display = Display.getDefault();
	private int imageCount;

	private ContentLoader loader;

	@Rule
	public TestName name = new TestName();

	private final int[] pages = new int[] { 0, 2, 2 };

	private final String[] ranges = new String[] { "0/1/3:0,0/1/3:10", "2/9/3:150,0/11/3:6", "0/3/13/3:3,4/13/3:7" };

	private Shell shell;

	private final String[] texts = new String[] { "First item", "lectus.\n\t\n\tSecond", "fringilla. Donec" };

	private final EpubUiUtility utility = new EpubUiUtility();

	private final int WHITE = 0xFFFFFF;

	/**
	 * Assert that the screenshot (of the browser) does not only contain white
	 * pixels at the left margin. If that is the case we have a offset skew as
	 * described in <a
	 * href="http://github.com/turesheim/elibrarium/issues/issue/15">bug 15:
	 * Offset skew when browsing</a>
	 * 
	 * @param image
	 *            the image to test
	 * @see http://github.com/turesheim/elibrarium/issues/issue/15
	 */
	private void assertImageBasics(Image image) {
		boolean notWhite = false;
		for (int y = 0; y < browser.getSize().y; y++) {
			int i = image.getImageData().getPixel(0, y);
			if (i < WHITE) {
				notWhite = true;
			}
		}
		Assert.assertTrue("Left side of image is only white pixels. Offset skew?", notWhite);
	}

	/**
	 * Loads content into the browser component
	 * 
	 * @param filename
	 * @param action
	 * @throws IOException
	 */
	private void loadContent(final String filename, final ContentLoadedAction action) throws IOException {
		final File file = new File(filename);
		if (!file.exists()) {
			throw new FileNotFoundException(file.toString());
		}
		loader.load(file.toURI().toString(), action);
	}

	/**
	 * Save a screenshot (of the browser) for the running test.
	 */
	private Image saveScreenshot() {
		File dir = new File("screenshots");
		if (!dir.exists()) {
			dir.mkdir();
		}
		GC gc = new GC(browser);
		final Image image = new Image(display, shell.getSize().x, shell.getSize().y);
		gc.copyArea(image, 0, 0);
		gc.dispose();
		ImageLoader il = new ImageLoader();
		il.data = new ImageData[] { image.getImageData() };
		String imageName = dir.getAbsolutePath() + File.separator + name.getMethodName()
				+ (imageCount > 0 ? "+" + imageCount : "") + ".png";
		il.save(imageName, SWT.IMAGE_PNG);
		imageCount++;
		return image;
	}

	@Before
	public void setUp() {
		imageCount = 0;
		loader = new ContentLoader();
		shell = new Shell(display);
		shell.setSize(300, 400);
		shell.setLayout(new FillLayout());
		browser = new Browser(shell, SWT.NONE);
		shell.open();
	}

	@Test
	public void testAnnotations() throws Exception {
		loadContent("testdata/plain-page.xhtml", new ContentLoadedAction() {
			@Override
			public void onLoad() {
				for (int i = 0; i < ranges.length; i++) {
					int page = (int) Math.round((Double) browser.evaluate("page=markRange('" + ranges[i] + "','" + i
							+ "');return page;"));
					String text = (String) browser.evaluate("return markedText;");
					Assert.assertEquals(texts[i], text);
					Assert.assertEquals(pages[i], page);
					// Page 3 is the most interesting in this case
					EpubUiUtility.navigateToPage(browser, 3);
				}
			}
		});
	}

	@Test
	public void testPage_1() throws Exception {
		loadContent("testdata/plain-page.xhtml", new ContentLoadedAction() {

			@Override
			public void onLoad() {
				EpubUiUtility.navigateToPage(browser, 1);
			}
		});
	}

	@Test
	public void testPage_2() throws Exception {
		loadContent("testdata/plain-page.xhtml", new ContentLoadedAction() {
			@Override
			public void onLoad() {
				EpubUiUtility.navigateToPage(browser, 2);
			}
		});
	}

	@Test
	public void testPage_3() throws Exception {
		loadContent("testdata/plain-page.xhtml", new ContentLoadedAction() {
			@Override
			public void onLoad() {
				EpubUiUtility.navigateToPage(browser, 3);
			}
		});
	}

	@Test
	public void testPage_4() throws Exception {
		loadContent("testdata/plain-page.xhtml", new ContentLoadedAction() {
			@Override
			public void onLoad() {
				EpubUiUtility.navigateToPage(browser, 4);
			}
		});
	}

	/**
	 * At the given resolution the test chapter should be rendered in four
	 * pages.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRenderingBasics() throws Exception {
		loadContent("testdata/plain-page.xhtml", new ContentLoadedAction() {
			@Override
			public void onLoad() {
				int count = (int) Math.round((Double) browser.evaluate("return pageCount"));
				Assert.assertEquals(4, count);
				Assert.assertEquals(300, EpubUiUtility.getPageWidth(browser));

			}
		});
	}
}
