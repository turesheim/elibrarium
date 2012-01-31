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

import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * *
 * 
 * @author Torkild U. Resheim
 * 
 */
public class EPUBPlugin extends AbstractUIPlugin {

	public EPUBPlugin() {
		plugin = this;
	}

	public static final String PLUGIN_ID = "no.resheim.elibrarium.epub.ui";

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static EPUBPlugin getDefault() {
		return plugin;
	}

	private static EPUBPlugin plugin;

}
