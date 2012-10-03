/**
 */
package no.resheim.elibrarium.library.impl;

import no.resheim.elibrarium.library.AnnotationColor;
import no.resheim.elibrarium.library.LibraryPackage;
import no.resheim.elibrarium.library.TextAnnotation;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Text Annotation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link no.resheim.elibrarium.library.impl.TextAnnotationImpl#getColor <em>Color</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.impl.TextAnnotationImpl#getComment <em>Comment</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TextAnnotationImpl extends BookmarkImpl implements TextAnnotation {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TextAnnotationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryPackage.Literals.TEXT_ANNOTATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnnotationColor getColor() {
		return (AnnotationColor)eGet(LibraryPackage.Literals.TEXT_ANNOTATION__COLOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setColor(AnnotationColor newColor) {
		eSet(LibraryPackage.Literals.TEXT_ANNOTATION__COLOR, newColor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getComment() {
		return (String)eGet(LibraryPackage.Literals.TEXT_ANNOTATION__COMMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComment(String newComment) {
		eSet(LibraryPackage.Literals.TEXT_ANNOTATION__COMMENT, newComment);
	}

} //TextAnnotationImpl
