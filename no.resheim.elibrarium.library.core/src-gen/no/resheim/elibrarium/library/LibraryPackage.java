/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package no.resheim.elibrarium.library;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see no.resheim.elibrarium.library.LibraryFactory
 * @model kind="package"
 * @generated
 */
public interface LibraryPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "library"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://resheim.no/elibrarium/library"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "library"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LibraryPackage eINSTANCE = no.resheim.elibrarium.library.impl.LibraryPackageImpl.init();

	/**
	 * The meta object id for the '{@link no.resheim.elibrarium.library.impl.LibraryImpl <em>Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see no.resheim.elibrarium.library.impl.LibraryImpl
	 * @see no.resheim.elibrarium.library.impl.LibraryPackageImpl#getLibrary()
	 * @generated
	 */
	int LIBRARY = 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__VERSION = 0;

	/**
	 * The feature id for the '<em><b>Books</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__BOOKS = 1;

	/**
	 * The number of structural features of the '<em>Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link no.resheim.elibrarium.library.impl.BookImpl <em>Book</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see no.resheim.elibrarium.library.impl.BookImpl
	 * @see no.resheim.elibrarium.library.impl.LibraryPackageImpl#getBook()
	 * @generated
	 */
	int BOOK = 1;

	/**
	 * The feature id for the '<em><b>Book URN</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__BOOK_URN = 0;

	/**
	 * The feature id for the '<em><b>Book URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__BOOK_URL = 1;

	/**
	 * The feature id for the '<em><b>Collection</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__COLLECTION = 2;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__TITLE = 3;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__AUTHOR = 4;

	/**
	 * The feature id for the '<em><b>Bookmarks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__BOOKMARKS = 5;

	/**
	 * The feature id for the '<em><b>Metadata</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__METADATA = 6;

	/**
	 * The feature id for the '<em><b>Last Href</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__LAST_HREF = 7;

	/**
	 * The feature id for the '<em><b>Last Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__LAST_LOCATION = 8;

	/**
	 * The number of structural features of the '<em>Book</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link no.resheim.elibrarium.library.impl.BookmarkImpl <em>Bookmark</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see no.resheim.elibrarium.library.impl.BookmarkImpl
	 * @see no.resheim.elibrarium.library.impl.LibraryPackageImpl#getBookmark()
	 * @generated
	 */
	int BOOKMARK = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOKMARK__ID = 0;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOKMARK__LOCATION = 1;

	/**
	 * The feature id for the '<em><b>Href</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOKMARK__HREF = 2;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOKMARK__TIMESTAMP = 3;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOKMARK__TEXT = 4;

	/**
	 * The number of structural features of the '<em>Bookmark</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOKMARK_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link no.resheim.elibrarium.library.impl.AnnotationImpl <em>Annotation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see no.resheim.elibrarium.library.impl.AnnotationImpl
	 * @see no.resheim.elibrarium.library.impl.LibraryPackageImpl#getAnnotation()
	 * @generated
	 */
	int ANNOTATION = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__ID = BOOKMARK__ID;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__LOCATION = BOOKMARK__LOCATION;

	/**
	 * The feature id for the '<em><b>Href</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__HREF = BOOKMARK__HREF;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__TIMESTAMP = BOOKMARK__TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__TEXT = BOOKMARK__TEXT;

	/**
	 * The feature id for the '<em><b>Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__COLOR = BOOKMARK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__COMMENT = BOOKMARK_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_FEATURE_COUNT = BOOKMARK_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link no.resheim.elibrarium.library.impl.MetadataImpl <em>Metadata</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see no.resheim.elibrarium.library.impl.MetadataImpl
	 * @see no.resheim.elibrarium.library.impl.LibraryPackageImpl#getMetadata()
	 * @generated
	 */
	int METADATA = 4;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Metadata</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link no.resheim.elibrarium.library.AnnotationColor <em>Annotation Color</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see no.resheim.elibrarium.library.AnnotationColor
	 * @see no.resheim.elibrarium.library.impl.LibraryPackageImpl#getAnnotationColor()
	 * @generated
	 */
	int ANNOTATION_COLOR = 5;


	/**
	 * Returns the meta object for class '{@link no.resheim.elibrarium.library.Library <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Library</em>'.
	 * @see no.resheim.elibrarium.library.Library
	 * @generated
	 */
	EClass getLibrary();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.elibrarium.library.Library#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see no.resheim.elibrarium.library.Library#getVersion()
	 * @see #getLibrary()
	 * @generated
	 */
	EAttribute getLibrary_Version();

	/**
	 * Returns the meta object for the containment reference list '{@link no.resheim.elibrarium.library.Library#getBooks <em>Books</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Books</em>'.
	 * @see no.resheim.elibrarium.library.Library#getBooks()
	 * @see #getLibrary()
	 * @generated
	 */
	EReference getLibrary_Books();

	/**
	 * Returns the meta object for class '{@link no.resheim.elibrarium.library.Book <em>Book</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Book</em>'.
	 * @see no.resheim.elibrarium.library.Book
	 * @generated
	 */
	EClass getBook();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.elibrarium.library.Book#getBookURN <em>Book URN</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Book URN</em>'.
	 * @see no.resheim.elibrarium.library.Book#getBookURN()
	 * @see #getBook()
	 * @generated
	 */
	EAttribute getBook_BookURN();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.elibrarium.library.Book#getBookURL <em>Book URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Book URL</em>'.
	 * @see no.resheim.elibrarium.library.Book#getBookURL()
	 * @see #getBook()
	 * @generated
	 */
	EAttribute getBook_BookURL();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.elibrarium.library.Book#getCollection <em>Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Collection</em>'.
	 * @see no.resheim.elibrarium.library.Book#getCollection()
	 * @see #getBook()
	 * @generated
	 */
	EAttribute getBook_Collection();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.elibrarium.library.Book#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see no.resheim.elibrarium.library.Book#getTitle()
	 * @see #getBook()
	 * @generated
	 */
	EAttribute getBook_Title();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.elibrarium.library.Book#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Author</em>'.
	 * @see no.resheim.elibrarium.library.Book#getAuthor()
	 * @see #getBook()
	 * @generated
	 */
	EAttribute getBook_Author();

	/**
	 * Returns the meta object for the containment reference list '{@link no.resheim.elibrarium.library.Book#getBookmarks <em>Bookmarks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Bookmarks</em>'.
	 * @see no.resheim.elibrarium.library.Book#getBookmarks()
	 * @see #getBook()
	 * @generated
	 */
	EReference getBook_Bookmarks();

	/**
	 * Returns the meta object for the containment reference list '{@link no.resheim.elibrarium.library.Book#getMetadata <em>Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Metadata</em>'.
	 * @see no.resheim.elibrarium.library.Book#getMetadata()
	 * @see #getBook()
	 * @generated
	 */
	EReference getBook_Metadata();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.elibrarium.library.Book#getLastHref <em>Last Href</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Href</em>'.
	 * @see no.resheim.elibrarium.library.Book#getLastHref()
	 * @see #getBook()
	 * @generated
	 */
	EAttribute getBook_LastHref();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.elibrarium.library.Book#getLastLocation <em>Last Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Location</em>'.
	 * @see no.resheim.elibrarium.library.Book#getLastLocation()
	 * @see #getBook()
	 * @generated
	 */
	EAttribute getBook_LastLocation();

	/**
	 * Returns the meta object for class '{@link no.resheim.elibrarium.library.Bookmark <em>Bookmark</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bookmark</em>'.
	 * @see no.resheim.elibrarium.library.Bookmark
	 * @generated
	 */
	EClass getBookmark();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.elibrarium.library.Bookmark#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see no.resheim.elibrarium.library.Bookmark#getId()
	 * @see #getBookmark()
	 * @generated
	 */
	EAttribute getBookmark_Id();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.elibrarium.library.Bookmark#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see no.resheim.elibrarium.library.Bookmark#getLocation()
	 * @see #getBookmark()
	 * @generated
	 */
	EAttribute getBookmark_Location();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.elibrarium.library.Bookmark#getHref <em>Href</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Href</em>'.
	 * @see no.resheim.elibrarium.library.Bookmark#getHref()
	 * @see #getBookmark()
	 * @generated
	 */
	EAttribute getBookmark_Href();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.elibrarium.library.Bookmark#getTimestamp <em>Timestamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timestamp</em>'.
	 * @see no.resheim.elibrarium.library.Bookmark#getTimestamp()
	 * @see #getBookmark()
	 * @generated
	 */
	EAttribute getBookmark_Timestamp();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.elibrarium.library.Bookmark#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see no.resheim.elibrarium.library.Bookmark#getText()
	 * @see #getBookmark()
	 * @generated
	 */
	EAttribute getBookmark_Text();

	/**
	 * Returns the meta object for class '{@link no.resheim.elibrarium.library.Annotation <em>Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotation</em>'.
	 * @see no.resheim.elibrarium.library.Annotation
	 * @generated
	 */
	EClass getAnnotation();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.elibrarium.library.Annotation#getColor <em>Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Color</em>'.
	 * @see no.resheim.elibrarium.library.Annotation#getColor()
	 * @see #getAnnotation()
	 * @generated
	 */
	EAttribute getAnnotation_Color();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.elibrarium.library.Annotation#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see no.resheim.elibrarium.library.Annotation#getComment()
	 * @see #getAnnotation()
	 * @generated
	 */
	EAttribute getAnnotation_Comment();

	/**
	 * Returns the meta object for class '{@link no.resheim.elibrarium.library.Metadata <em>Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metadata</em>'.
	 * @see no.resheim.elibrarium.library.Metadata
	 * @generated
	 */
	EClass getMetadata();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.elibrarium.library.Metadata#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see no.resheim.elibrarium.library.Metadata#getKey()
	 * @see #getMetadata()
	 * @generated
	 */
	EAttribute getMetadata_Key();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.elibrarium.library.Metadata#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see no.resheim.elibrarium.library.Metadata#getValue()
	 * @see #getMetadata()
	 * @generated
	 */
	EAttribute getMetadata_Value();

	/**
	 * Returns the meta object for enum '{@link no.resheim.elibrarium.library.AnnotationColor <em>Annotation Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Annotation Color</em>'.
	 * @see no.resheim.elibrarium.library.AnnotationColor
	 * @generated
	 */
	EEnum getAnnotationColor();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	LibraryFactory getLibraryFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link no.resheim.elibrarium.library.impl.LibraryImpl <em>Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see no.resheim.elibrarium.library.impl.LibraryImpl
		 * @see no.resheim.elibrarium.library.impl.LibraryPackageImpl#getLibrary()
		 * @generated
		 */
		EClass LIBRARY = eINSTANCE.getLibrary();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY__VERSION = eINSTANCE.getLibrary_Version();

		/**
		 * The meta object literal for the '<em><b>Books</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY__BOOKS = eINSTANCE.getLibrary_Books();

		/**
		 * The meta object literal for the '{@link no.resheim.elibrarium.library.impl.BookImpl <em>Book</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see no.resheim.elibrarium.library.impl.BookImpl
		 * @see no.resheim.elibrarium.library.impl.LibraryPackageImpl#getBook()
		 * @generated
		 */
		EClass BOOK = eINSTANCE.getBook();

		/**
		 * The meta object literal for the '<em><b>Book URN</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOK__BOOK_URN = eINSTANCE.getBook_BookURN();

		/**
		 * The meta object literal for the '<em><b>Book URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOK__BOOK_URL = eINSTANCE.getBook_BookURL();

		/**
		 * The meta object literal for the '<em><b>Collection</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOK__COLLECTION = eINSTANCE.getBook_Collection();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOK__TITLE = eINSTANCE.getBook_Title();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOK__AUTHOR = eINSTANCE.getBook_Author();

		/**
		 * The meta object literal for the '<em><b>Bookmarks</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOOK__BOOKMARKS = eINSTANCE.getBook_Bookmarks();

		/**
		 * The meta object literal for the '<em><b>Metadata</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOOK__METADATA = eINSTANCE.getBook_Metadata();

		/**
		 * The meta object literal for the '<em><b>Last Href</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOK__LAST_HREF = eINSTANCE.getBook_LastHref();

		/**
		 * The meta object literal for the '<em><b>Last Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOK__LAST_LOCATION = eINSTANCE.getBook_LastLocation();

		/**
		 * The meta object literal for the '{@link no.resheim.elibrarium.library.impl.BookmarkImpl <em>Bookmark</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see no.resheim.elibrarium.library.impl.BookmarkImpl
		 * @see no.resheim.elibrarium.library.impl.LibraryPackageImpl#getBookmark()
		 * @generated
		 */
		EClass BOOKMARK = eINSTANCE.getBookmark();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOKMARK__ID = eINSTANCE.getBookmark_Id();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOKMARK__LOCATION = eINSTANCE.getBookmark_Location();

		/**
		 * The meta object literal for the '<em><b>Href</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOKMARK__HREF = eINSTANCE.getBookmark_Href();

		/**
		 * The meta object literal for the '<em><b>Timestamp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOKMARK__TIMESTAMP = eINSTANCE.getBookmark_Timestamp();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOKMARK__TEXT = eINSTANCE.getBookmark_Text();

		/**
		 * The meta object literal for the '{@link no.resheim.elibrarium.library.impl.AnnotationImpl <em>Annotation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see no.resheim.elibrarium.library.impl.AnnotationImpl
		 * @see no.resheim.elibrarium.library.impl.LibraryPackageImpl#getAnnotation()
		 * @generated
		 */
		EClass ANNOTATION = eINSTANCE.getAnnotation();

		/**
		 * The meta object literal for the '<em><b>Color</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATION__COLOR = eINSTANCE.getAnnotation_Color();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATION__COMMENT = eINSTANCE.getAnnotation_Comment();

		/**
		 * The meta object literal for the '{@link no.resheim.elibrarium.library.impl.MetadataImpl <em>Metadata</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see no.resheim.elibrarium.library.impl.MetadataImpl
		 * @see no.resheim.elibrarium.library.impl.LibraryPackageImpl#getMetadata()
		 * @generated
		 */
		EClass METADATA = eINSTANCE.getMetadata();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METADATA__KEY = eINSTANCE.getMetadata_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METADATA__VALUE = eINSTANCE.getMetadata_Value();

		/**
		 * The meta object literal for the '{@link no.resheim.elibrarium.library.AnnotationColor <em>Annotation Color</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see no.resheim.elibrarium.library.AnnotationColor
		 * @see no.resheim.elibrarium.library.impl.LibraryPackageImpl#getAnnotationColor()
		 * @generated
		 */
		EEnum ANNOTATION_COLOR = eINSTANCE.getAnnotationColor();

	}

} //LibraryPackage
