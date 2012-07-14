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
package no.resheim.elibrarium.epub.core;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
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
import org.eclipse.mylyn.docs.epub.core.OPSPublication;

public class FolderScanner extends Job {

	public FolderScanner(String name) {
		super(name);
	}

	/**
	 * Registers all publications found in the given EPUB unless they are
	 * already in the library.
	 * 
	 * @param epubPath
	 *            path to the EPUB file
	 * @throws Exception
	 */
	private void registerBooks(File epubPath) throws Exception {
		EPUB epub = new EPUB();
		epub.unpack(epubPath);
		List<OPSPublication> publications = epub.getOPSPublications();
		for (OPSPublication ops : publications) {
			String title = EpubUtil.getFirstTitle(ops);
			String author = EpubUtil.getFirstAuthor(ops);
			String id = EpubUtil.getIdentifier(ops);
			if (!EpubCorePlugin.getCollection().hasBook(id)) {
				URI uri = epubPath.toURI();
				Book book = LibraryUtil.createNewBook(EpubCorePlugin.COLLECTION_ID, uri, id, title, author);
				// Mark the book as automatically discovered
				Metadata md = LibraryFactory.eINSTANCE.createMetadata();
				md.setKey("discovered");
				md.setKey(Boolean.toString(true));
				book.getMetadata().add(md);
				EpubCorePlugin.getCollection().add(book);
			}

		}
	};

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(EpubCorePlugin.PLUGIN_ID);
		boolean scan = preferences.getBoolean(PreferenceConstants.SCAN_ENABLE, false);
		if (scan){
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
			File[] epubs = folder.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					if (name.toLowerCase().endsWith(".epub")) {
						return true;
					}
					return false;
				}
			});
			for (File file : epubs) {
				if (!EpubCorePlugin.getCollection().hasBook(file)) {
					try {
						registerBooks(file);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			// Also check any sub folders
			File[] folders = folder.listFiles(new FileFilter() {

				@Override
				public boolean accept(File arg0) {
					return arg0.isDirectory();
				}
			});
			for (File file : folders) {
				scanFolder(file);
			}
		}
	}
}
