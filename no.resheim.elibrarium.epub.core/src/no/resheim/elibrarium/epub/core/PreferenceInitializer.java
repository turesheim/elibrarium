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

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IEclipsePreferences preferences = DefaultScope.INSTANCE.getNode(EPUBCorePlugin.PLUGIN_ID);
		preferences.putBoolean(PreferenceConstants.SCAN_FOLDERS, false);
		preferences.putBoolean(PreferenceConstants.SCAN_NOTIFY, true);
		preferences.putInt(PreferenceConstants.SCAN_INTERVAL, 10);
		preferences.put(PreferenceConstants.SCAN_FOLDERS, "");
	}

}
