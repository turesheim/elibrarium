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
package no.resheim.elibrarium.library.core;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import no.resheim.elibrarium.library.Library;
import no.resheim.elibrarium.library.LibraryFactory;

import org.eclipse.core.resources.ISaveContext;
import org.eclipse.core.resources.ISaveParticipant;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.net4j.util.lifecycle.Lifecycle;

/**
 * 
 * @author Torkild U. Resheim
 * @since 0.1
 * @deprecated use {@link CdoLibraryCatalog} instead
 */
@Deprecated
public class XmlLibraryCatalog extends Lifecycle implements ILibraryCatalog {

	/**
	 * Participates in the workspace save mechanism and will store the library
	 * to an XML file using EMF persistence mechanisms.
	 * 
	 * @author Torkild U. Resheim
	 */
	private class WorkspaceSaveParticipant implements ISaveParticipant {

		@Override
		public void doneSaving(ISaveContext context) {
			Librarian instance = Librarian.getDefault();
			// delete the old saved state since it is not necessary anymore
			String oldFileName = "library.old";
			File f = instance.getStorageLocation().append(oldFileName).toFile();
			f.delete();
		}

		@Override
		public void prepareToSave(ISaveContext context) throws CoreException {
		}

		@Override
		public void rollback(ISaveContext context) {
			Librarian instance = Librarian.getDefault();
			String oldFileName = "library.old";
			String saveFileName = "library.xml";
			File backup = instance.getStorageLocation().append(oldFileName).toFile();
			File updated = instance.getStorageLocation().append(saveFileName).toFile();
			updated.delete();
			backup.renameTo(updated);
		}

		@Override
		public void saving(ISaveContext context) throws CoreException {
			switch (context.getKind()) {
			case ISaveContext.FULL_SAVE:
				Librarian instance = Librarian.getDefault();
				String oldFileName = "library.old";
				String saveFileName = "library.xml";
				File backup = instance.getStorageLocation().append(oldFileName).toFile();
				File updated = instance.getStorageLocation().append(saveFileName).toFile();
				if (updated.exists()) {
					backup.delete();
					updated.renameTo(backup);
				}
				try {
					writeLibrary(updated);
					context.map(new Path("library"), new Path(saveFileName));
					context.needSaveNumber();
				} catch (IOException e) {
					throw new CoreException(new Status(IStatus.ERROR, Librarian.PLUGIN_ID,
							"Could not store library database", e));
				}
				break;
			case ISaveContext.PROJECT_SAVE:
				break;
			case ISaveContext.SNAPSHOT:
				break;
			}
		}
	}

	private Library library;

	@Override
	public AdapterFactory getAdapterFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Library getLibrary() {
		return library;
	}

	@Override
	public <T extends CDOObject> Object modify(T object, ITransactionalOperation<T> operation) {
		return null;
	}

	@Override
	protected void doActivate() throws Exception {
		if (!restoreState()) {
			library = LibraryFactory.eINSTANCE.createLibrary();
			library.setVersion("3");
		}
	}

	@Override
	protected void doDeactivate() throws Exception {
		// Storing of data will be done trough the WorkspaceSaveParticipant
	}

	/**
	 * Restores the library from it's persisted state using EMF persistence
	 * mechanisms if it exists. It will also register a {@link ISaveParticipant}
	 * for storing the library when requested by the workbench
	 * 
	 * @return
	 */
	private boolean restoreState() {
		try {
			Librarian instance = Librarian.getDefault();
			String oldFileName = "library.old";
			String saveFileName = "library.xml";
			File backup = instance.getStorageLocation().append(oldFileName).toFile();
			File updated = instance.getStorageLocation().append(saveFileName).toFile();
			// If the backup exists we'll use that instead as something went
			// wrong the last time the database was saved.
			if (backup.exists()) {
				updated.delete();
				backup.renameTo(updated);
			}
			if (updated.exists()) {
				readLibrary(updated);
			} else {
				library = LibraryFactory.eINSTANCE.createLibrary();
			}
			// Register for periodic workspace saves
			ISaveParticipant saveParticipant = new WorkspaceSaveParticipant();
			ResourcesPlugin.getWorkspace().addSaveParticipant(Librarian.PLUGIN_ID, saveParticipant);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			library = LibraryFactory.eINSTANCE.createLibrary();
		}
		return false;
	}

	/**
	 * Reads the library from the serialised model. The result of this operation
	 * is placed in the {@link #library} instance.
	 * 
	 * @param file
	 *            the destination file
	 * @throws IOException
	 */
	private void readLibrary(File file) throws IOException {
		// registerLibraryResourceFactory();
		ResourceSet resourceSet = new ResourceSetImpl();
		URI fileURI = URI.createFileURI(file.getAbsolutePath());
		Resource resource = resourceSet.createResource(fileURI);
		Map<Object, Object> loadOptions = new HashMap<Object, Object>();
		loadOptions.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		// Required in order to correctly read in attributes
		loadOptions.put(XMLResource.OPTION_LAX_FEATURE_PROCESSING, Boolean.TRUE);
		// Treat "href" attributes as features
		loadOptions.put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
		// Escape
		loadOptions.put(XMLResource.OPTION_SKIP_ESCAPE_URI, Boolean.FALSE);
		// Do not download any external DTDs.
		Map<String, Object> parserFeatures = new HashMap<String, Object>();
		parserFeatures.put("http://xml.org/sax/features/validation", Boolean.FALSE);
		parserFeatures.put("http://apache.org/xml/features/nonvalidating/load-external-dtd", Boolean.FALSE);
		loadOptions.put(XMLResource.OPTION_PARSER_FEATURES, parserFeatures);
		resource.load(loadOptions);
		// library = (Library) resource.getContents().get(0);
	}

	/**
	 * Serialises the library to a XML file using EMF persistence mechanisms.
	 * 
	 * @param file
	 *            the destination file
	 * @throws IOException
	 */
	private void writeLibrary(File file) throws IOException {
		ResourceSet resourceSet = new ResourceSetImpl();
		URI fileURI = URI.createFileURI(file.getAbsolutePath());
		Resource resource = resourceSet.createResource(fileURI);
		// resource.getContents().add(library);
		Map<Object, Object> saveOptions = new HashMap<Object, Object>();
		// We use extended metadata
		saveOptions.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		// UTF-8 encoding is required per specification
		saveOptions.put(XMLResource.OPTION_ENCODING, "utf-8");
		resource.save(saveOptions);
	}
}
