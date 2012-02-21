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

import no.resheim.elibrarium.library.Book;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil.FeatureEList;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.mylyn.docs.epub.core.OPSPublication;
import org.eclipse.mylyn.docs.epub.dc.Creator;
import org.eclipse.mylyn.docs.epub.dc.DCType;
import org.eclipse.mylyn.docs.epub.dc.Identifier;
import org.eclipse.mylyn.docs.epub.dc.Title;
import org.eclipse.mylyn.docs.epub.opf.Role;

/**
 * Various utility methods for handling EPUB content.
 * 
 * @author Torkild U. Resheim
 */
public class EPUBUtil {

	private static final EStructuralFeature TEXT = XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text();

	/**
	 * Returns the book corresponding to the given OPS publication if it can be
	 * found in the library.
	 * 
	 * @param ops
	 *            the OPS publication
	 * @return the book or <code>null</code>
	 */
	public static Book getBook(OPSPublication ops) {
		String id = EPUBUtil.getIdentifier(ops);
		if (!EPUBCorePlugin.getCollection().hasBook(id)) {
			return null;
		} else {
			return EPUBCorePlugin.getCollection().getBook(id);
		}
	}

	/**
	 * Returns the name of the first <i>creator</i> with the role of
	 * <i>author</i> if any. If no author is found the first creator or
	 * <code>null</code> is returned.
	 * 
	 * @param ops
	 * @return
	 */
	public static String getFirstAuthor(OPSPublication ops) {
		EList<Creator> creators = ops.getOpfPackage().getMetadata().getCreators();
		if (creators.size() > 0) {
			for (Creator creator : creators) {
				if (creator.getRole().equals(Role.AUTHOR)){
					return getText(creator);
				}
			}
			return getText(creators.get(0));
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static String getIdentifier(OPSPublication ops) {
		Identifier identifier = ops.getIdentifier();
		FeatureMap fm = identifier.getMixed();
		Object o = fm.get(TEXT, false);
		if (o instanceof FeatureEList) {
			if (((FeatureEList) o).size() > 0) {
				return ((FeatureEList) o).get(0).toString();
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static String getFirstTitle(OPSPublication ops) {
		EList<Title> titles = ops.getOpfPackage().getMetadata().getTitles();
		if (titles.size() > 0) {
			FeatureMap fm = titles.get(0).getMixed();
			Object o = fm.get(TEXT, false);
			if (o instanceof FeatureEList) {
				if (((FeatureEList) o).size() > 0) {
					return ((FeatureEList) o).get(0).toString();
				}
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private static String getText(DCType attribute) {
		FeatureMap fm = attribute.getMixed();
		Object o = fm.get(TEXT, false);
		if (o instanceof FeatureEList) {
			if (((FeatureEList) o).size() > 0) {
				return ((FeatureEList) o).get(0).toString();
			}
		}
		return null;
	}
}
