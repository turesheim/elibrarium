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

import java.io.File;
import java.text.MessageFormat;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import no.resheim.elibrarium.epub.core.EpubUtil;
import no.resheim.elibrarium.library.Annotation;
import no.resheim.elibrarium.library.Book;
import no.resheim.elibrarium.library.Bookmark;
import no.resheim.elibrarium.library.core.ILibraryCatalog;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.mylyn.docs.epub.core.OPSPublication;
import org.eclipse.mylyn.docs.epub.opf.Item;
import org.eclipse.mylyn.docs.epub.opf.Itemref;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.widgets.Shell;

/**
 * This type is for paginating EPUB books. It employs two threads - one
 * iterating over the book chapters (or XHTML files) the other is counting the
 * pages of each chapter. The latter will also inject all bookmarks and
 * annotations and update the page number of these.
 * 
 * @author Torkild U. Resheim
 */
public class PaginationJob extends Job {

	private class Paginator implements Runnable, ProgressListener {

		/**
		 * Used to synchronise the two threads working on paginating a chapter.
		 */
		private final CyclicBarrier barrier = new CyclicBarrier(2);

		private String currentHref;

		private final IProgressMonitor monitor;

		private final Itemref ref;

		public Paginator(Itemref ref, IProgressMonitor monitor) {
			this.ref = ref;
			this.monitor = monitor;
		}

		@Override
		public void changed(ProgressEvent event) {
			// ignore
		}

		/**
		 * This event is triggered every time a full HTML file has been loaded
		 * into the browser component. We use it to determine the number of
		 * pages in the chapter.
		 */
		@Override
		public void completed(ProgressEvent event) {
			if (monitor.isCanceled()) {
				return;
			}
			browser.removeProgressListener(this);
			int[] newSizes = new int[chapterSizes.length + 1];
			System.arraycopy(chapterSizes, 0, newSizes, 0, chapterSizes.length);
			newSizes[chapterSizes.length] = paginateChapter();
			chapterSizes = newSizes;
			try {
				// The thread running this code is done and the other barrier
				// is waiting for it.
				barrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Executes JavaScript that will reformat the chapter and obtain
		 * information that is required for browsing it.
		 * 
		 * @return the number of pages in the chapter
		 */
		private int paginateChapter() {
			int pageCount = -1;
			try {
				boolean ok = utility.injectJavaScript(browser);
				if (ok) {
					pageCount = (int) Math.round((Double) browser.evaluate("return pageCount"));
					// Iterate over all bookmarks and annotations in order to
					// determine their page numbers. The data
					// model will be updated at this stage.
					EList<Bookmark> bookmarks = book.getBookmarks();
					for (Bookmark bookmark : bookmarks) {
						if (bookmark.getHref() != null && bookmark.getHref().equals(currentHref)) {
							String id = bookmark.getId();
							if (bookmark instanceof Annotation) {
								int page = (int) Math.round((Double) browser.evaluate("page=markRange('"
										+ bookmark.getLocation() + "','" + id + "');return page;"));
								for (int chapterSize : chapterSizes) {
									page += chapterSize;
								}
								bookmark.setPage(page + 1);
							} else {
								int page = (int) Math.round((Double) browser.evaluate("page=injectIdentifier('"
										+ bookmark.getLocation() + "','" + id + "');return page;"));
								for (int chapterSize : chapterSizes) {
									page += chapterSize;
								}
								final int pageNumber = page;
								// Update the page number
								ILibraryCatalog.INSTANCE.modify(bookmark,
										new ILibraryCatalog.ITransactionalOperation<Bookmark>() {

											@Override
											public Object execute(Bookmark object) {
												object.cdoWriteLock();
												object.setPage(pageNumber);
												return null;
											}
										});
								bookmark.setPage(page + 1);
							}
						}
					} // for
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return pageCount;
		}

		@Override
		public void run() {
			browser.getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					browser.addProgressListener(Paginator.this);
					Item item = ops.getItemById(ref.getIdref());
					currentHref = item.getHref();
					String filePath = ops.getRootFolder().getAbsolutePath() + File.separator + currentHref;
					File file = new File(filePath);
					if (file.exists()) {
						String url = "file:" + filePath;
						browser.setUrl(url);
					} else {
						monitor.setCanceled(true);
					}
				}
			});
			try {
				// This thread is done, wait until the other one is done.
				barrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}

	}

	private final EpubUiUtility utility;

	/**
	 * The book currently open in the editor.
	 */
	private final Book book;

	/**
	 * A private browser instance used for paginating the EPUB.
	 */
	private final Browser browser;

	/**
	 * A list of individual chapter sizes.
	 */
	private int[] chapterSizes = new int[0];

	/**
	 * The OPS publication being paginated.
	 */
	private final OPSPublication ops;

	private final Shell shell;

	/**
	 * The width of the page
	 */
	private int width;

	public PaginationJob(Book book, OPSPublication ops) {
		super(MessageFormat.format("Paginating \"{0}\"", EpubUtil.getFirstTitle(ops)));
		this.ops = ops;
		this.book = book;
		shell = new Shell();
		browser = new Browser(shell, SWT.NONE);
		utility = new EpubUiUtility();
	}

	public int[] getChapterSizes() {
		// Make copy to help avoid threading issues.
		int[] sizes = new int[chapterSizes.length];
		System.arraycopy(chapterSizes, 0, sizes, 0, sizes.length);
		return sizes;
	}

	/**
	 * Returns the total number of pages in the book.
	 * 
	 * @return
	 */
	public int getTotalpages() {
		int total = 0;
		for (int size : chapterSizes) {
			total += size;
		}
		return total;
	}

	/**
	 * Returns the width of the book page.
	 * 
	 * @return the page width
	 */
	public int getWidth() {
		return width;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		long start = System.currentTimeMillis();
		chapterSizes = new int[0];
		EList<Itemref> refs = ops.getOpfPackage().getSpine().getSpineItems();
		ExecutorService es = Executors.newFixedThreadPool(1);
		for (Itemref ref : refs) {
			if (!monitor.isCanceled()) {
				Paginator paginator = new Paginator(ref, monitor);
				es.execute(paginator);
			} else {
				System.out.println("Pagination cancelled");
				break;
			}
		}
		es.shutdown();
		try {
			es.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Pagination done in " + (System.currentTimeMillis() - start) + "ms");
		monitor.done();
		return Status.OK_STATUS;
	}

	public synchronized void update(int width, int height) {
		if (width == 0 || height == 0) {
			throw new IllegalArgumentException("Height or width cannot be 0");
		}
		if (getState() == RUNNING) {

			return;
		}
		browser.setSize(width, height);
		this.schedule();
	}

}
