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
 * A representation of the model object '<em><b>Book</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link no.resheim.reader.library.Book#getPath <em>Path</em>}</li>
 *   <li>{@link no.resheim.reader.library.Book#getCoverImage <em>Cover Image</em>}</li>
 *   <li>{@link no.resheim.reader.library.Book#getAnnotations <em>Annotations</em>}</li>
 * </ul>
 * </p>
 *
 * @see no.resheim.reader.library.LibraryPackage#getBook()
 * @model
 * @generated
 */
public interface Book extends EObject {
	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see no.resheim.reader.library.LibraryPackage#getBook_Path()
	 * @model required="true"
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link no.resheim.reader.library.Book#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

	/**
	 * Returns the value of the '<em><b>Cover Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cover Image</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cover Image</em>' attribute.
	 * @see #setCoverImage(String)
	 * @see no.resheim.reader.library.LibraryPackage#getBook_CoverImage()
	 * @model required="true"
	 * @generated
	 */
	String getCoverImage();

	/**
	 * Sets the value of the '{@link no.resheim.reader.library.Book#getCoverImage <em>Cover Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cover Image</em>' attribute.
	 * @see #getCoverImage()
	 * @generated
	 */
	void setCoverImage(String value);

	/**
	 * Returns the value of the '<em><b>Annotations</b></em>' reference list.
	 * The list contents are of type {@link no.resheim.reader.library.Annotation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Annotations</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotations</em>' reference list.
	 * @see no.resheim.reader.library.LibraryPackage#getBook_Annotations()
	 * @model
	 * @generated
	 */
	EList<Annotation> getAnnotations();

} // Book
