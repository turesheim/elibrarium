/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package no.resheim.elibrarium.library.impl;

import java.util.Collection;

import no.resheim.elibrarium.library.Book;
import no.resheim.elibrarium.library.Bookmark;
import no.resheim.elibrarium.library.LibraryPackage;
import no.resheim.elibrarium.library.Metadata;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Book</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link no.resheim.elibrarium.library.impl.BookImpl#getBookURN <em>Book URN</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.impl.BookImpl#getBookURL <em>Book URL</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.impl.BookImpl#getCollection <em>Collection</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.impl.BookImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.impl.BookImpl#getAuthor <em>Author</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.impl.BookImpl#getBookmarks <em>Bookmarks</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.impl.BookImpl#getMetadata <em>Metadata</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.impl.BookImpl#getLastHref <em>Last Href</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.impl.BookImpl#getLastLocation <em>Last Location</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BookImpl extends CDOObjectImpl implements Book {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BookImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryPackage.Literals.BOOK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBookURN() {
		return (String)eGet(LibraryPackage.Literals.BOOK__BOOK_URN, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBookURN(String newBookURN) {
		eSet(LibraryPackage.Literals.BOOK__BOOK_URN, newBookURN);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBookURL() {
		return (String)eGet(LibraryPackage.Literals.BOOK__BOOK_URL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBookURL(String newBookURL) {
		eSet(LibraryPackage.Literals.BOOK__BOOK_URL, newBookURL);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCollection() {
		return (String)eGet(LibraryPackage.Literals.BOOK__COLLECTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCollection(String newCollection) {
		eSet(LibraryPackage.Literals.BOOK__COLLECTION, newCollection);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTitle() {
		return (String)eGet(LibraryPackage.Literals.BOOK__TITLE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTitle(String newTitle) {
		eSet(LibraryPackage.Literals.BOOK__TITLE, newTitle);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAuthor() {
		return (String)eGet(LibraryPackage.Literals.BOOK__AUTHOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthor(String newAuthor) {
		eSet(LibraryPackage.Literals.BOOK__AUTHOR, newAuthor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Bookmark> getBookmarks() {
		return (EList<Bookmark>)eGet(LibraryPackage.Literals.BOOK__BOOKMARKS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Metadata> getMetadata() {
		return (EList<Metadata>)eGet(LibraryPackage.Literals.BOOK__METADATA, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLastHref() {
		return (String)eGet(LibraryPackage.Literals.BOOK__LAST_HREF, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastHref(String newLastHref) {
		eSet(LibraryPackage.Literals.BOOK__LAST_HREF, newLastHref);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLastLocation() {
		return (String)eGet(LibraryPackage.Literals.BOOK__LAST_LOCATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastLocation(String newLastLocation) {
		eSet(LibraryPackage.Literals.BOOK__LAST_LOCATION, newLastLocation);
	}

} //BookImpl
