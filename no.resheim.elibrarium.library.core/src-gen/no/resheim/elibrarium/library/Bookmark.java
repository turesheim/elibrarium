/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package no.resheim.elibrarium.library;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bookmark</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link no.resheim.elibrarium.library.Bookmark#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see no.resheim.elibrarium.library.LibraryPackage#getBookmark()
 * @model
 * @generated
 */
public interface Bookmark extends Marker {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link no.resheim.elibrarium.library.BookmarkType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see no.resheim.elibrarium.library.BookmarkType
	 * @see #setType(BookmarkType)
	 * @see no.resheim.elibrarium.library.LibraryPackage#getBookmark_Type()
	 * @model required="true"
	 * @generated
	 */
	BookmarkType getType();

	/**
	 * Sets the value of the '{@link no.resheim.elibrarium.library.Bookmark#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see no.resheim.elibrarium.library.BookmarkType
	 * @see #getType()
	 * @generated
	 */
	void setType(BookmarkType value);

} // Bookmark
