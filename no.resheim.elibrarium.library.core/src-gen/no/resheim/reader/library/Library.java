/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package no.resheim.reader.library;

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
 *   <li>{@link no.resheim.reader.library.Library#getVersion <em>Version</em>}</li>
 *   <li>{@link no.resheim.reader.library.Library#getVolumes <em>Volumes</em>}</li>
 * </ul>
 * </p>
 *
 * @see no.resheim.reader.library.LibraryPackage#getLibrary()
 * @model
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
	 * @see no.resheim.reader.library.LibraryPackage#getLibrary_Version()
	 * @model required="true"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link no.resheim.reader.library.Library#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * Returns the value of the '<em><b>Volumes</b></em>' reference list.
	 * The list contents are of type {@link no.resheim.reader.library.Book}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Volumes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Volumes</em>' reference list.
	 * @see no.resheim.reader.library.LibraryPackage#getLibrary_Volumes()
	 * @model
	 * @generated
	 */
	EList<Book> getVolumes();

} // Library
