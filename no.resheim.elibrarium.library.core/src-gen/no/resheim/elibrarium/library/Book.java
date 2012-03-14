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
 * A representation of the model object '<em><b>Book</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link no.resheim.elibrarium.library.Book#getBookURN <em>Book URN</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.Book#getBookURL <em>Book URL</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.Book#getCollection <em>Collection</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.Book#getTitle <em>Title</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.Book#getAuthor <em>Author</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.Book#getBookmarks <em>Bookmarks</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.Book#getMetadata <em>Metadata</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.Book#getLastHref <em>Last Href</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.Book#getLastLocation <em>Last Location</em>}</li>
 * </ul>
 * </p>
 *
 * @see no.resheim.elibrarium.library.LibraryPackage#getBook()
 * @model
 * @generated
 */
public interface Book extends EObject {
	/**
	 * Returns the value of the '<em><b>Book URN</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Book URN</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Book URN</em>' attribute.
	 * @see #setBookURN(String)
	 * @see no.resheim.elibrarium.library.LibraryPackage#getBook_BookURN()
	 * @model id="true" required="true"
	 *        extendedMetaData="name='urn'"
	 * @generated
	 */
	String getBookURN();

	/**
	 * Sets the value of the '{@link no.resheim.elibrarium.library.Book#getBookURN <em>Book URN</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Book URN</em>' attribute.
	 * @see #getBookURN()
	 * @generated
	 */
	void setBookURN(String value);

	/**
	 * Returns the value of the '<em><b>Book URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Book URL</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Book URL</em>' attribute.
	 * @see #setBookURL(String)
	 * @see no.resheim.elibrarium.library.LibraryPackage#getBook_BookURL()
	 * @model required="true"
	 *        extendedMetaData="name='url'"
	 * @generated
	 */
	String getBookURL();

	/**
	 * Sets the value of the '{@link no.resheim.elibrarium.library.Book#getBookURL <em>Book URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Book URL</em>' attribute.
	 * @see #getBookURL()
	 * @generated
	 */
	void setBookURL(String value);

	/**
	 * Returns the value of the '<em><b>Collection</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Collection</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Collection</em>' attribute.
	 * @see #setCollection(String)
	 * @see no.resheim.elibrarium.library.LibraryPackage#getBook_Collection()
	 * @model required="true"
	 * @generated
	 */
	String getCollection();

	/**
	 * Sets the value of the '{@link no.resheim.elibrarium.library.Book#getCollection <em>Collection</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Collection</em>' attribute.
	 * @see #getCollection()
	 * @generated
	 */
	void setCollection(String value);

	/**
	 * Returns the value of the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Title</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Title</em>' attribute.
	 * @see #setTitle(String)
	 * @see no.resheim.elibrarium.library.LibraryPackage#getBook_Title()
	 * @model required="true"
	 * @generated
	 */
	String getTitle();

	/**
	 * Sets the value of the '{@link no.resheim.elibrarium.library.Book#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' attribute.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(String value);

	/**
	 * Returns the value of the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Author</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Author</em>' attribute.
	 * @see #setAuthor(String)
	 * @see no.resheim.elibrarium.library.LibraryPackage#getBook_Author()
	 * @model required="true"
	 * @generated
	 */
	String getAuthor();

	/**
	 * Sets the value of the '{@link no.resheim.elibrarium.library.Book#getAuthor <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Author</em>' attribute.
	 * @see #getAuthor()
	 * @generated
	 */
	void setAuthor(String value);

	/**
	 * Returns the value of the '<em><b>Bookmarks</b></em>' containment reference list.
	 * The list contents are of type {@link no.resheim.elibrarium.library.Bookmark}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bookmarks</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bookmarks</em>' containment reference list.
	 * @see no.resheim.elibrarium.library.LibraryPackage#getBook_Bookmarks()
	 * @model containment="true"
	 *        extendedMetaData="name='bookmark'"
	 * @generated
	 */
	EList<Bookmark> getBookmarks();

	/**
	 * Returns the value of the '<em><b>Metadata</b></em>' containment reference list.
	 * The list contents are of type {@link no.resheim.elibrarium.library.Metadata}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metadata</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metadata</em>' containment reference list.
	 * @see no.resheim.elibrarium.library.LibraryPackage#getBook_Metadata()
	 * @model containment="true"
	 *        extendedMetaData="name='metadata'"
	 * @generated
	 */
	EList<Metadata> getMetadata();

	/**
	 * Returns the value of the '<em><b>Last Href</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Href</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Href</em>' attribute.
	 * @see #setLastHref(String)
	 * @see no.resheim.elibrarium.library.LibraryPackage#getBook_LastHref()
	 * @model
	 * @generated
	 */
	String getLastHref();

	/**
	 * Sets the value of the '{@link no.resheim.elibrarium.library.Book#getLastHref <em>Last Href</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Href</em>' attribute.
	 * @see #getLastHref()
	 * @generated
	 */
	void setLastHref(String value);

	/**
	 * Returns the value of the '<em><b>Last Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Location</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Location</em>' attribute.
	 * @see #setLastLocation(String)
	 * @see no.resheim.elibrarium.library.LibraryPackage#getBook_LastLocation()
	 * @model
	 * @generated
	 */
	String getLastLocation();

	/**
	 * Sets the value of the '{@link no.resheim.elibrarium.library.Book#getLastLocation <em>Last Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Location</em>' attribute.
	 * @see #getLastLocation()
	 * @generated
	 */
	void setLastLocation(String value);

} // Book
