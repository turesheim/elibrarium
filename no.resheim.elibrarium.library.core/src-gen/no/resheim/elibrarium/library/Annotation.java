/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package no.resheim.elibrarium.library;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Annotation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link no.resheim.elibrarium.library.Annotation#getColor <em>Color</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.Annotation#getComment <em>Comment</em>}</li>
 * </ul>
 * </p>
 *
 * @see no.resheim.elibrarium.library.LibraryPackage#getAnnotation()
 * @model
 * @generated
 */
public interface Annotation extends Bookmark {
	/**
	 * Returns the value of the '<em><b>Color</b></em>' attribute.
	 * The literals are from the enumeration {@link no.resheim.elibrarium.library.AnnotationColor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Color</em>' attribute.
	 * @see no.resheim.elibrarium.library.AnnotationColor
	 * @see #setColor(AnnotationColor)
	 * @see no.resheim.elibrarium.library.LibraryPackage#getAnnotation_Color()
	 * @model required="true"
	 * @generated
	 */
	AnnotationColor getColor();

	/**
	 * Sets the value of the '{@link no.resheim.elibrarium.library.Annotation#getColor <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Color</em>' attribute.
	 * @see no.resheim.elibrarium.library.AnnotationColor
	 * @see #getColor()
	 * @generated
	 */
	void setColor(AnnotationColor value);

	/**
	 * Returns the value of the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comment</em>' attribute.
	 * @see #setComment(String)
	 * @see no.resheim.elibrarium.library.LibraryPackage#getAnnotation_Comment()
	 * @model
	 * @generated
	 */
	String getComment();

	/**
	 * Sets the value of the '{@link no.resheim.elibrarium.library.Annotation#getComment <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(String value);

} // Annotation
