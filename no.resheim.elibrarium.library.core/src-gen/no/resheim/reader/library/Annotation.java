/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package no.resheim.reader.library;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Annotation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link no.resheim.reader.library.Annotation#getColor <em>Color</em>}</li>
 *   <li>{@link no.resheim.reader.library.Annotation#getText <em>Text</em>}</li>
 *   <li>{@link no.resheim.reader.library.Annotation#getComment <em>Comment</em>}</li>
 * </ul>
 * </p>
 *
 * @see no.resheim.reader.library.LibraryPackage#getAnnotation()
 * @model
 * @generated
 */
public interface Annotation extends Marker {
	/**
	 * Returns the value of the '<em><b>Color</b></em>' attribute.
	 * The literals are from the enumeration {@link no.resheim.reader.library.AnnotationColor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Color</em>' attribute.
	 * @see no.resheim.reader.library.AnnotationColor
	 * @see #setColor(AnnotationColor)
	 * @see no.resheim.reader.library.LibraryPackage#getAnnotation_Color()
	 * @model required="true"
	 * @generated
	 */
	AnnotationColor getColor();

	/**
	 * Sets the value of the '{@link no.resheim.reader.library.Annotation#getColor <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Color</em>' attribute.
	 * @see no.resheim.reader.library.AnnotationColor
	 * @see #getColor()
	 * @generated
	 */
	void setColor(AnnotationColor value);

	/**
	 * Returns the value of the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Text</em>' attribute.
	 * @see #setText(String)
	 * @see no.resheim.reader.library.LibraryPackage#getAnnotation_Text()
	 * @model required="true"
	 * @generated
	 */
	String getText();

	/**
	 * Sets the value of the '{@link no.resheim.reader.library.Annotation#getText <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text</em>' attribute.
	 * @see #getText()
	 * @generated
	 */
	void setText(String value);

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
	 * @see no.resheim.reader.library.LibraryPackage#getAnnotation_Comment()
	 * @model
	 * @generated
	 */
	String getComment();

	/**
	 * Sets the value of the '{@link no.resheim.reader.library.Annotation#getComment <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(String value);

} // Annotation
