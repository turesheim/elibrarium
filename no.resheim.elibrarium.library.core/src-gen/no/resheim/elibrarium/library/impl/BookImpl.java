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
public class BookImpl extends EObjectImpl implements Book {
	/**
	 * The default value of the '{@link #getBookURN() <em>Book URN</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBookURN()
	 * @generated
	 * @ordered
	 */
	protected static final String BOOK_URN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBookURN() <em>Book URN</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBookURN()
	 * @generated
	 * @ordered
	 */
	protected String bookURN = BOOK_URN_EDEFAULT;

	/**
	 * The default value of the '{@link #getBookURL() <em>Book URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBookURL()
	 * @generated
	 * @ordered
	 */
	protected static final String BOOK_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBookURL() <em>Book URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBookURL()
	 * @generated
	 * @ordered
	 */
	protected String bookURL = BOOK_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getCollection() <em>Collection</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCollection()
	 * @generated
	 * @ordered
	 */
	protected static final String COLLECTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCollection() <em>Collection</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCollection()
	 * @generated
	 * @ordered
	 */
	protected String collection = COLLECTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected static final String TITLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected String title = TITLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected static final String AUTHOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected String author = AUTHOR_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBookmarks() <em>Bookmarks</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBookmarks()
	 * @generated
	 * @ordered
	 */
	protected EList<Bookmark> bookmarks;

	/**
	 * The cached value of the '{@link #getMetadata() <em>Metadata</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetadata()
	 * @generated
	 * @ordered
	 */
	protected EList<Metadata> metadata;

	/**
	 * The default value of the '{@link #getLastHref() <em>Last Href</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastHref()
	 * @generated
	 * @ordered
	 */
	protected static final String LAST_HREF_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastHref() <em>Last Href</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastHref()
	 * @generated
	 * @ordered
	 */
	protected String lastHref = LAST_HREF_EDEFAULT;

	/**
	 * The default value of the '{@link #getLastLocation() <em>Last Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastLocation()
	 * @generated
	 * @ordered
	 */
	protected static final String LAST_LOCATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastLocation() <em>Last Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastLocation()
	 * @generated
	 * @ordered
	 */
	protected String lastLocation = LAST_LOCATION_EDEFAULT;

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
	public String getBookURN() {
		return bookURN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBookURN(String newBookURN) {
		String oldBookURN = bookURN;
		bookURN = newBookURN;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.BOOK__BOOK_URN, oldBookURN, bookURN));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBookURL() {
		return bookURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBookURL(String newBookURL) {
		String oldBookURL = bookURL;
		bookURL = newBookURL;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.BOOK__BOOK_URL, oldBookURL, bookURL));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCollection() {
		return collection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCollection(String newCollection) {
		String oldCollection = collection;
		collection = newCollection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.BOOK__COLLECTION, oldCollection, collection));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTitle(String newTitle) {
		String oldTitle = title;
		title = newTitle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.BOOK__TITLE, oldTitle, title));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthor(String newAuthor) {
		String oldAuthor = author;
		author = newAuthor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.BOOK__AUTHOR, oldAuthor, author));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Bookmark> getBookmarks() {
		if (bookmarks == null) {
			bookmarks = new EObjectContainmentEList<Bookmark>(Bookmark.class, this, LibraryPackage.BOOK__BOOKMARKS);
		}
		return bookmarks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Metadata> getMetadata() {
		if (metadata == null) {
			metadata = new EObjectContainmentEList<Metadata>(Metadata.class, this, LibraryPackage.BOOK__METADATA);
		}
		return metadata;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLastHref() {
		return lastHref;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastHref(String newLastHref) {
		String oldLastHref = lastHref;
		lastHref = newLastHref;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.BOOK__LAST_HREF, oldLastHref, lastHref));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLastLocation() {
		return lastLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastLocation(String newLastLocation) {
		String oldLastLocation = lastLocation;
		lastLocation = newLastLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.BOOK__LAST_LOCATION, oldLastLocation, lastLocation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryPackage.BOOK__BOOKMARKS:
				return ((InternalEList<?>)getBookmarks()).basicRemove(otherEnd, msgs);
			case LibraryPackage.BOOK__METADATA:
				return ((InternalEList<?>)getMetadata()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryPackage.BOOK__BOOK_URN:
				return getBookURN();
			case LibraryPackage.BOOK__BOOK_URL:
				return getBookURL();
			case LibraryPackage.BOOK__COLLECTION:
				return getCollection();
			case LibraryPackage.BOOK__TITLE:
				return getTitle();
			case LibraryPackage.BOOK__AUTHOR:
				return getAuthor();
			case LibraryPackage.BOOK__BOOKMARKS:
				return getBookmarks();
			case LibraryPackage.BOOK__METADATA:
				return getMetadata();
			case LibraryPackage.BOOK__LAST_HREF:
				return getLastHref();
			case LibraryPackage.BOOK__LAST_LOCATION:
				return getLastLocation();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case LibraryPackage.BOOK__BOOK_URN:
				setBookURN((String)newValue);
				return;
			case LibraryPackage.BOOK__BOOK_URL:
				setBookURL((String)newValue);
				return;
			case LibraryPackage.BOOK__COLLECTION:
				setCollection((String)newValue);
				return;
			case LibraryPackage.BOOK__TITLE:
				setTitle((String)newValue);
				return;
			case LibraryPackage.BOOK__AUTHOR:
				setAuthor((String)newValue);
				return;
			case LibraryPackage.BOOK__BOOKMARKS:
				getBookmarks().clear();
				getBookmarks().addAll((Collection<? extends Bookmark>)newValue);
				return;
			case LibraryPackage.BOOK__METADATA:
				getMetadata().clear();
				getMetadata().addAll((Collection<? extends Metadata>)newValue);
				return;
			case LibraryPackage.BOOK__LAST_HREF:
				setLastHref((String)newValue);
				return;
			case LibraryPackage.BOOK__LAST_LOCATION:
				setLastLocation((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case LibraryPackage.BOOK__BOOK_URN:
				setBookURN(BOOK_URN_EDEFAULT);
				return;
			case LibraryPackage.BOOK__BOOK_URL:
				setBookURL(BOOK_URL_EDEFAULT);
				return;
			case LibraryPackage.BOOK__COLLECTION:
				setCollection(COLLECTION_EDEFAULT);
				return;
			case LibraryPackage.BOOK__TITLE:
				setTitle(TITLE_EDEFAULT);
				return;
			case LibraryPackage.BOOK__AUTHOR:
				setAuthor(AUTHOR_EDEFAULT);
				return;
			case LibraryPackage.BOOK__BOOKMARKS:
				getBookmarks().clear();
				return;
			case LibraryPackage.BOOK__METADATA:
				getMetadata().clear();
				return;
			case LibraryPackage.BOOK__LAST_HREF:
				setLastHref(LAST_HREF_EDEFAULT);
				return;
			case LibraryPackage.BOOK__LAST_LOCATION:
				setLastLocation(LAST_LOCATION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case LibraryPackage.BOOK__BOOK_URN:
				return BOOK_URN_EDEFAULT == null ? bookURN != null : !BOOK_URN_EDEFAULT.equals(bookURN);
			case LibraryPackage.BOOK__BOOK_URL:
				return BOOK_URL_EDEFAULT == null ? bookURL != null : !BOOK_URL_EDEFAULT.equals(bookURL);
			case LibraryPackage.BOOK__COLLECTION:
				return COLLECTION_EDEFAULT == null ? collection != null : !COLLECTION_EDEFAULT.equals(collection);
			case LibraryPackage.BOOK__TITLE:
				return TITLE_EDEFAULT == null ? title != null : !TITLE_EDEFAULT.equals(title);
			case LibraryPackage.BOOK__AUTHOR:
				return AUTHOR_EDEFAULT == null ? author != null : !AUTHOR_EDEFAULT.equals(author);
			case LibraryPackage.BOOK__BOOKMARKS:
				return bookmarks != null && !bookmarks.isEmpty();
			case LibraryPackage.BOOK__METADATA:
				return metadata != null && !metadata.isEmpty();
			case LibraryPackage.BOOK__LAST_HREF:
				return LAST_HREF_EDEFAULT == null ? lastHref != null : !LAST_HREF_EDEFAULT.equals(lastHref);
			case LibraryPackage.BOOK__LAST_LOCATION:
				return LAST_LOCATION_EDEFAULT == null ? lastLocation != null : !LAST_LOCATION_EDEFAULT.equals(lastLocation);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (bookURN: "); //$NON-NLS-1$
		result.append(bookURN);
		result.append(", bookURL: "); //$NON-NLS-1$
		result.append(bookURL);
		result.append(", collection: "); //$NON-NLS-1$
		result.append(collection);
		result.append(", title: "); //$NON-NLS-1$
		result.append(title);
		result.append(", author: "); //$NON-NLS-1$
		result.append(author);
		result.append(", lastHref: "); //$NON-NLS-1$
		result.append(lastHref);
		result.append(", lastLocation: "); //$NON-NLS-1$
		result.append(lastLocation);
		result.append(')');
		return result.toString();
	}

} //BookImpl
