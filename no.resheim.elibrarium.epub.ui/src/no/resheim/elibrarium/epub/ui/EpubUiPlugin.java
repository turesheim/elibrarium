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
package no.resheim.elibrarium.epub.ui;

import no.resheim.elibrarium.library.ui.LibraryUIPlugin;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * *
 * 
 * @author Torkild U. Resheim
 * 
 */
public class EpubUiPlugin extends AbstractUIPlugin {

	/** Image for an inactive bookmark */
	public static final String IMG_BOOKMARK_INACTIVE = "img_bookmark_inactive";

	/** Image for an active bookmark */
	public static final String IMG_BOOKMARK_ACTIVE = "img_bookmark_active";

	/** Image for an active bookmark */
	public static final String IMG_BOOKMARK = "img_bookmark";

	public EpubUiPlugin() {
		plugin = this;
	}

	public static final String PLUGIN_ID = "no.resheim.elibrarium.epub.ui";

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static EpubUiPlugin getDefault() {
		return plugin;
	}

	private static EpubUiPlugin plugin;

	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		super.initializeImageRegistry(reg);
		reg.put(IMG_BOOKMARK_ACTIVE, LibraryUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, "icons/bookmark_active.png"));
		reg.put(IMG_BOOKMARK_INACTIVE,
				LibraryUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, "icons/bookmark_inactive.png"));
		reg.put(IMG_BOOKMARK, LibraryUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, "icons/bookmark.png"));
	}

}
