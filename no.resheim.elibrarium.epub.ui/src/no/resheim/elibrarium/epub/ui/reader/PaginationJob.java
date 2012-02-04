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

import java.io.File;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import no.resheim.elibrarium.epub.core.EPUBUtil;

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
 * 
 * @author Torkild U. Resheim
 */
public class PaginationJob extends Job {

	private class Paginator implements Runnable, ProgressListener {
		
		/**
		 * Used to synchronise the two threads working on paginating a chapter.
		 */
		private final CyclicBarrier barrier = new CyclicBarrier(2);
		
		private final Itemref ref;

		private final IProgressMonitor monitor;

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
			StringBuilder sb = new StringBuilder();
			sb.append("try {");
			sb.append("desiredWidth=" + browser.getSize().x + ";");
			sb.append("htmlID=document.getElementsByTagName('html')[0];");
			sb.append("bodyID=document.getElementsByTagName('body')[0];");
			sb.append("desiredHeight = " + browser.getSize().y + "-20;");
			sb.append("totalHeight=bodyID.offsetHeight;");
			sb.append("pageCount=Math.floor(totalHeight/desiredHeight)+1;");
			sb.append("width=desiredWidth*pageCount;");
			sb.append("bodyID.style.width=width+'px';");
			sb.append("bodyID.style.height=desiredHeight+'px';");
			sb.append("bodyID.style.WebkitColumnCount=pageCount;");
			sb.append("function adjustImages() {");
			sb.append("  var imgs, i;");
			sb.append("  imgs=bodyID.getElementsByTagName('img');");
			sb.append("  for(i=0;i<imgs.length;i++) {");
			sb.append("    imgs[i].style.maxWidth = (desiredWidth-10)+'px';");
			sb.append("    imgs[i].style.maxWidth = (desiredWidth-10)+'px';");
			sb.append("  }");
			sb.append("}");
			sb.append("adjustImages();");
			sb.append("bodyID.style.overflow='hidden'");
			sb.append("}catch(e){document.write(e);}");
			boolean ok = browser.execute(sb.toString());
			if (ok) {
				int count = (int) Math.round((Double) browser.evaluate("return pageCount"));
				int[] newSizes = new int[chapterSizes.length + 1];
				System.arraycopy(chapterSizes, 0, newSizes, 0, chapterSizes.length);
				newSizes[chapterSizes.length] = count;
				chapterSizes = newSizes;
				try {
					barrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			} else {
				throw new RuntimeException("Could not paginate");
			}
		}

		@Override
		public void run() {
			browser.getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					browser.addProgressListener(Paginator.this);
					Item item = ops.getItemById(ref.getIdref());
					String filePath = ops.getRootFolder().getAbsolutePath() + File.separator + item.getHref();
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
				barrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * A private browser instance used for paginating the EPUB.
	 */
	private final Browser browser;

	/**
	 * A list of individual chapter sizes.
	 */
	private int[] chapterSizes = new int[0];

	/**
	 * The width of the page
	 */
	private int width;

	/**
	 * Returns the width of the book page.
	 * 
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	private OPSPublication ops;

	private final Shell shell;

	public PaginationJob(OPSPublication ops) {
		super("Paginating " + EPUBUtil.getFirstTitle(ops));
		this.ops = ops;
		shell = new Shell();
		browser = new Browser(shell, SWT.NONE);
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

	public void setPublication(OPSPublication ops) {
		this.ops = ops;
	}

	public void update(int width, int height) {
		if (width == 0 || height == 0) {
			throw new IllegalArgumentException("Height or width cannot be 0");
		}
		if (getState() == RUNNING) {
			return;
		}
		browser.setSize(width, height);
		this.schedule(200);
	}

	public int[] getChapterSizes() {
		// Make copy to help avoid threading issues.
		int[] sizes = new int[chapterSizes.length];
		System.arraycopy(chapterSizes, 0, sizes, 0, sizes.length);
		return sizes;
	}

}
