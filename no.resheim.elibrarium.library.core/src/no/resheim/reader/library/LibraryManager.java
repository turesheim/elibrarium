package no.resheim.reader.library;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import no.resheim.reader.library.util.LibraryResourceImpl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


public class LibraryManager implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		registerLibraryResourceFactory();

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

	public Library getLibrary() {
		return library;
	}

	/**
	 * Registers a new resource factory for the library data structure. This is
	 * normally done through Eclipse extension points but we also need to be
	 * able to create this factory without the Eclipse runtime.
	 */
	private void registerLibraryResourceFactory() {
		// Register package so that it is available even without the Eclipse
		// runtime
		@SuppressWarnings("unused")
		LibraryPackage packageInstance = LibraryPackage.eINSTANCE;

		// Register the file suffix
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(".xml", new XMLResourceFactoryImpl() {

			@Override
			public Resource createResource(URI uri) {
				LibraryResourceImpl xmiResource = new LibraryResourceImpl(uri);
				Map<Object, Object> loadOptions = xmiResource.getDefaultLoadOptions();
				Map<Object, Object> saveOptions = xmiResource.getDefaultSaveOptions();
				// We use extended metadata
				saveOptions.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
				loadOptions.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
				// Required in order to correctly read in attributes
				loadOptions.put(XMLResource.OPTION_LAX_FEATURE_PROCESSING, Boolean.TRUE);
				// Treat "href" attributes as features
				loadOptions.put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
				// UTF-8 encoding is required per specification
				saveOptions.put(XMLResource.OPTION_ENCODING, "utf-8");
				// Do not download any external DTDs.
				Map<String, Object> parserFeatures = new HashMap<String, Object>();
				parserFeatures.put("http://xml.org/sax/features/validation", Boolean.FALSE);
				parserFeatures.put("http://apache.org/xml/features/nonvalidating/load-external-dtd", Boolean.FALSE);
				loadOptions.put(XMLResource.OPTION_PARSER_FEATURES, parserFeatures);
				return xmiResource;
			}

		});
	}

	/**
	 * Reads the library from the serialised model. The result of this operation
	 * is placed in the {@link #library} instance.
	 * 
	 * @param workingFolder
	 *            the folder where the EPUB was unpacked
	 * @throws IOException
	 */
	private void readLibrary(File workingFolder) throws IOException {
		File metaFolder = new File(workingFolder.getAbsolutePath() + File.separator + "META-INF");
		File containerFile = new File(metaFolder.getAbsolutePath() + File.separator + "library.xml");
		ResourceSet resourceSet = new ResourceSetImpl();
		URI fileURI = URI.createFileURI(containerFile.getAbsolutePath());
		Resource resource = resourceSet.createResource(fileURI);
		resource.load(null);
		library = (Library) resource.getContents().get(0);
	}

	Library library;

	/**
	 * Creates a new folder named META-INF and writes the required (as per the
	 * OPS specification) <b>container.xml</b> in that folder. This is part of
	 * the packing procedure.
	 * 
	 * @param workingFolder
	 *            the root folder
	 */
	private void writeLibrary(File workingFolder) throws IOException {
		File metaFolder = new File(workingFolder.getAbsolutePath() + File.separator + "META-INF");
		if (metaFolder.mkdir()) {
			File containerFile = new File(metaFolder.getAbsolutePath() + File.separator + "library.xml");
			ResourceSet resourceSet = new ResourceSetImpl();
			// Register the packages to make it available during loading.
			URI fileURI = URI.createFileURI(containerFile.getAbsolutePath());
			Resource resource = resourceSet.createResource(fileURI);
			resource.getContents().add(library);
			resource.save(null);
		}
	}

}
