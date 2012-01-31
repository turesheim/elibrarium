/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package no.resheim.elibrarium.library;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Library</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link no.resheim.elibrarium.library.Library#getVersion <em>Version</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.Library#getBooks <em>Books</em>}</li>
 * </ul>
 * </p>
 *
 * @see no.resheim.elibrarium.library.LibraryPackage#getLibrary()
 * @model extendedMetaData="namespace='http://resheim.no/elibrarium/library'"
 * @generated
 */
public interface Library extends EObject {
	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see no.resheim.elibrarium.library.LibraryPackage#getLibrary_Version()
	 * @model required="true"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link no.resheim.elibrarium.library.Library#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * Returns the value of the '<em><b>Books</b></em>' containment reference list.
	 * The list contents are of type {@link no.resheim.elibrarium.library.Book}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Books</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Books</em>' containment reference list.
	 * @see no.resheim.elibrarium.library.LibraryPackage#getLibrary_Books()
	 * @model containment="true"
	 *        extendedMetaData="name='book'"
	 * @generated
	 */
	EList<Book> getBooks();

} // Library
