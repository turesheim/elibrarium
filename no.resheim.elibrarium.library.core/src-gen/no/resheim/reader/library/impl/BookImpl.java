/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package no.resheim.reader.library.impl;

import java.util.Collection;

import no.resheim.reader.library.Annotation;
import no.resheim.reader.library.Book;
import no.resheim.reader.library.Bookmark;
import no.resheim.reader.library.LibraryPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Book</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link no.resheim.reader.library.impl.BookImpl#getPath <em>Path</em>}</li>
 *   <li>{@link no.resheim.reader.library.impl.BookImpl#getCoverImage <em>Cover Image</em>}</li>
 *   <li>{@link no.resheim.reader.library.impl.BookImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link no.resheim.reader.library.impl.BookImpl#getAuthor <em>Author</em>}</li>
 *   <li>{@link no.resheim.reader.library.impl.BookImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link no.resheim.reader.library.impl.BookImpl#getBookmarks <em>Bookmarks</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BookImpl extends EObjectImpl implements Book {
	/**
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected String path = PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getCoverImage() <em>Cover Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoverImage()
	 * @generated
	 * @ordered
	 */
	protected static final String COVER_IMAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCoverImage() <em>Cover Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoverImage()
	 * @generated
	 * @ordered
	 */
	protected String coverImage = COVER_IMAGE_EDEFAULT;

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
	 * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotations()
	 * @generated
	 * @ordered
	 */
	protected EList<Annotation> annotations;

	/**
	 * The cached value of the '{@link #getBookmarks() <em>Bookmarks</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBookmarks()
	 * @generated
	 * @ordered
	 */
	protected EList<Bookmark> bookmarks;

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
	public String getPath() {
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPath(String newPath) {
		String oldPath = path;
		path = newPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.BOOK__PATH, oldPath, path));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCoverImage() {
		return coverImage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCoverImage(String newCoverImage) {
		String oldCoverImage = coverImage;
		coverImage = newCoverImage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.BOOK__COVER_IMAGE, oldCoverImage, coverImage));
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
	public EList<Annotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectResolvingEList<Annotation>(Annotation.class, this, LibraryPackage.BOOK__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Bookmark> getBookmarks() {
		if (bookmarks == null) {
			bookmarks = new EObjectResolvingEList<Bookmark>(Bookmark.class, this, LibraryPackage.BOOK__BOOKMARKS);
		}
		return bookmarks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryPackage.BOOK__PATH:
				return getPath();
			case LibraryPackage.BOOK__COVER_IMAGE:
				return getCoverImage();
			case LibraryPackage.BOOK__TITLE:
				return getTitle();
			case LibraryPackage.BOOK__AUTHOR:
				return getAuthor();
			case LibraryPackage.BOOK__ANNOTATIONS:
				return getAnnotations();
			case LibraryPackage.BOOK__BOOKMARKS:
				return getBookmarks();
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
			case LibraryPackage.BOOK__PATH:
				setPath((String)newValue);
				return;
			case LibraryPackage.BOOK__COVER_IMAGE:
				setCoverImage((String)newValue);
				return;
			case LibraryPackage.BOOK__TITLE:
				setTitle((String)newValue);
				return;
			case LibraryPackage.BOOK__AUTHOR:
				setAuthor((String)newValue);
				return;
			case LibraryPackage.BOOK__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case LibraryPackage.BOOK__BOOKMARKS:
				getBookmarks().clear();
				getBookmarks().addAll((Collection<? extends Bookmark>)newValue);
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
			case LibraryPackage.BOOK__PATH:
				setPath(PATH_EDEFAULT);
				return;
			case LibraryPackage.BOOK__COVER_IMAGE:
				setCoverImage(COVER_IMAGE_EDEFAULT);
				return;
			case LibraryPackage.BOOK__TITLE:
				setTitle(TITLE_EDEFAULT);
				return;
			case LibraryPackage.BOOK__AUTHOR:
				setAuthor(AUTHOR_EDEFAULT);
				return;
			case LibraryPackage.BOOK__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case LibraryPackage.BOOK__BOOKMARKS:
				getBookmarks().clear();
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
			case LibraryPackage.BOOK__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
			case LibraryPackage.BOOK__COVER_IMAGE:
				return COVER_IMAGE_EDEFAULT == null ? coverImage != null : !COVER_IMAGE_EDEFAULT.equals(coverImage);
			case LibraryPackage.BOOK__TITLE:
				return TITLE_EDEFAULT == null ? title != null : !TITLE_EDEFAULT.equals(title);
			case LibraryPackage.BOOK__AUTHOR:
				return AUTHOR_EDEFAULT == null ? author != null : !AUTHOR_EDEFAULT.equals(author);
			case LibraryPackage.BOOK__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case LibraryPackage.BOOK__BOOKMARKS:
				return bookmarks != null && !bookmarks.isEmpty();
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
		result.append(" (path: ");
		result.append(path);
		result.append(", coverImage: ");
		result.append(coverImage);
		result.append(", title: ");
		result.append(title);
		result.append(", author: ");
		result.append(author);
		result.append(')');
		return result.toString();
	}

} //BookImpl
