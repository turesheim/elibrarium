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
package no.resheim.elibrarium.epub.ui.preferences;

import no.resheim.elibrarium.epub.core.EpubCollection;
import no.resheim.elibrarium.epub.core.PreferenceConstants;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.PathEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * 
 * @author Torkild U. Resheim
 */
public class ScanPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private static ScopedPreferenceStore preferences;

	public ScanPreferencePage() {
		super(GRID);
		setDescription("Folders can be periodically scanned for EPUB files which automatically will handled and added to the library.");
	}

	@Override
	public void createFieldEditors() {
		addField(new BooleanFieldEditor(PreferenceConstants.SCAN_ENABLE, "&Scan folders for EPUB files",
				getFieldEditorParent()));
		// Add group for scan details
		Group group = new Group(getFieldEditorParent(), SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		group.setText("Folder scanning:");

		IntegerFieldEditor intervalEditor = new IntegerFieldEditor(PreferenceConstants.SCAN_INTERVAL,
				"Minutes between scans:",
				group);
		addField(intervalEditor);

		PathEditor pathEditor = new PathEditor(PreferenceConstants.SCAN_FOLDERS, "Scanned folders",
				"Select a folder to scan", group);
		addField(pathEditor);

		((GridLayout) group.getLayout()).marginLeft = 5;
		((GridLayout) group.getLayout()).marginRight = 5;
		((GridLayout) group.getLayout()).marginTop = 5;
		((GridLayout) group.getLayout()).marginBottom = 5;
	}

	public void init(IWorkbench workbench) {
		if (preferences == null) {
			preferences = new ScopedPreferenceStore(InstanceScope.INSTANCE, EpubCollection.PLUGIN_ID);
		}
		setPreferenceStore(preferences);
	}

}