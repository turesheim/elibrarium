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
package no.resheim.elibrarium.library.ui;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;


public class LibraryUIPlugin extends AbstractUIPlugin {

	public static final String IMG_BOOK = "img_book";

	public static final String PLUGIN_ID = "no.resheim.elibrarium.library.ui";

	private static LibraryUIPlugin plugin;

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static LibraryUIPlugin getDefault() {
		return plugin;
	}
	public LibraryUIPlugin() {
		plugin = this;
	}

	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		super.initializeImageRegistry(reg);
		reg.put(IMG_BOOK, LibraryUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, "icons/book.gif"));
	}

}
