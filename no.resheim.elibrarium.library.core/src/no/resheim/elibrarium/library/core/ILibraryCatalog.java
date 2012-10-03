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

import no.resheim.elibrarium.library.Library;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.notify.AdapterFactory;

/**
 * The library catalogue holds all library information and is automatically
 * persisting these data. Changes to the library must be done using the
 * {@link #modify(CDOObject, ITransactionalOperation)} method in order to ensure
 * integrity.
 * 
 * @author Torkild U. Resheim
 */
public interface ILibraryCatalog {
	/**
	 * The static instance of the catalogue. Change to XmlLibraryCatalog if the
	 * old style persistence should be used.
	 */
	public static final ILibraryCatalog INSTANCE = CdoLibraryCatalog.INSTANCE;

	public AdapterFactory getAdapterFactory();

	/**
	 * Returns the library. Each catalogue has exactly one instance.
	 * 
	 * @return the library
	 */
	public Library getLibrary();

	public <T extends CDOObject> Object modify(T object, ITransactionalOperation<T> operation);

	public interface ITransactionalOperation<T extends CDOObject> {
		public Object execute(T object);
	}
}
