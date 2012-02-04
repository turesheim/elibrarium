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

public class PreferenceConstants {

	/**
	 * Whether or not to scan folders for EPUB files.
	 */
	public static final String SCAN_ENABLE = "SCAN_ENABLE";

	/**
	 * List of folders to scan for EPUB files.
	 */
	public static final String SCAN_FOLDERS = "SCAN_FOLDERS";

	/**
	 * The number of minutes between consequent folder scans.
	 */
	public static final String SCAN_INTERVAL = "SCAN_INTERVAL";

	/**
	 * Whether or not to notify in UI when scan has resulted in newly discovered
	 * publications.
	 */
	public static final String SCAN_NOTIFY = "SCAN_NOTIFY";

	/**
	 * Default scan interval.
	 */
	public static final int DEFAULT_SCAN_INTERVAL = 10;
}
