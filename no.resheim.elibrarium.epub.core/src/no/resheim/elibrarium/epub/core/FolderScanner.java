/*******************************************************************************
 * Copyright (c) 2012, 2014 Torkild U. Resheim.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Torkild U. Resheim - initial API and implementation
 *******************************************************************************/
package no.resheim.elibrarium.epub.core;

import java.io.File;
import java.io.FileFilter;
import java.net.URI;
import java.util.List;

import no.resheim.elibrarium.library.Book;
import no.resheim.elibrarium.library.LibraryFactory;
import no.resheim.elibrarium.library.Metadata;
import no.resheim.elibrarium.library.core.LibraryUtil;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.mylyn.docs.epub.core.EPUB;
import org.eclipse.mylyn.docs.epub.core.ILogger;
import org.eclipse.mylyn.docs.epub.core.Publication;

public class FolderScanner extends Job {

	class ScannerLogger implements ILogger {

		@Override
		public void log(String message) {
			log(message, Severity.INFO);
		}

		@Override
		public void log(String message, Severity severity) {
			switch (severity) {
			case ERROR:
				System.out.print("[ERROR] ");
				break;
			case DEBUG:
				System.out.print("[DEBUG] ");
				break;
			case INFO:
				System.out.print("[INFO ] ");
				break;
			case VERBOSE:
				System.out.print("[VERBO] ");
				break;
			case WARNING:
				System.out.print("[WARN ] ");
				break;
			default:
				break;
			}
			System.out.println(message);
		}
	}

	private final ILogger logger;

	public FolderScanner(String name) {
		super(name);
		logger = new ScannerLogger();
	}

	/**
	 * Registers all publications found in the given EPUB unless they are
	 * already in the library.
	 *
	 * @param epubPath
	 *            path to the EPUB file
	 * @throws Exception
	 */
	private void registerBook(File epubPath) throws Exception {
		EPUB epub = new EPUB(logger);
		epub.unpack(epubPath);
		List<Publication> publications = epub.getOPSPublications();
		for (Publication ops : publications) {
			String title = EpubUtil.getFirstTitle(ops);
			String author = EpubUtil.getFirstAuthor(ops);
			String id = EpubUtil.getIdentifier(ops);
			if (!EpubCollection.getCollection().hasBook(id)) {
				URI uri = epubPath.toURI();
				Book book = LibraryUtil.createNewBook(EpubCollection.COLLECTION_ID, uri, id, title, author);
				// Mark the book as automatically discovered
				Metadata md = LibraryFactory.eINSTANCE.createMetadata();
				md.setKey("discovered");
				md.setKey(Boolean.toString(true));
				book.getMetadata().add(md);
				EpubCollection.getCollection().add(book);
			}
		}
	};

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(EpubCollection.PLUGIN_ID);
		boolean scan = preferences.getBoolean(PreferenceConstants.SCAN_ENABLE, false);
		if (scan) {
			String paths = preferences.get(PreferenceConstants.SCAN_FOLDERS, "");
			String[] folders = paths.split(File.pathSeparator);
			for (String string : folders) {
				File folder = new File(string);
				scanFolder(folder);
			}
		}
		return Status.OK_STATUS;
	}

	public void scanFolder(File folder) {
		if (folder.isDirectory()) {
			// Locate all EPUB's and handle these
			File[] epubs = folder.listFiles(new FileFilter() {

				@Override
				public boolean accept(File file) {
					if (file.isFile()) {
						String name = file.getName();
						if (name.endsWith(".epub")) {
							return true;
						}
					}
					return false;
				}
			});
			// Locate all subfolders and handle those
			for (File file : epubs) {
				if (!EpubCollection.getCollection().hasBook(file)) {
					try {
						registerBook(file);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			// Also check any sub folders
			File[] folders = folder.listFiles(new FileFilter() {

				@Override
				public boolean accept(File file) {
					return file.isDirectory();
				}
			});
			for (File file : folders) {
				scanFolder(file);
			}
		}
	}
}
