/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package no.resheim.reader.library;

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
 * @see no.resheim.reader.library.LibraryFactory
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
	String eNAME = "library";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://resheim.no/reader/library";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "library";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LibraryPackage eINSTANCE = no.resheim.reader.library.impl.LibraryPackageImpl.init();

	/**
	 * The meta object id for the '{@link no.resheim.reader.library.impl.LibraryImpl <em>Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see no.resheim.reader.library.impl.LibraryImpl
	 * @see no.resheim.reader.library.impl.LibraryPackageImpl#getLibrary()
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
	 * The feature id for the '<em><b>Volumes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__VOLUMES = 1;

	/**
	 * The number of structural features of the '<em>Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link no.resheim.reader.library.impl.BookImpl <em>Book</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see no.resheim.reader.library.impl.BookImpl
	 * @see no.resheim.reader.library.impl.LibraryPackageImpl#getBook()
	 * @generated
	 */
	int BOOK = 1;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__PATH = 0;

	/**
	 * The feature id for the '<em><b>Cover Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__COVER_IMAGE = 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__ANNOTATIONS = 2;

	/**
	 * The feature id for the '<em><b>Metadata</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__METADATA = 3;

	/**
	 * The number of structural features of the '<em>Book</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link no.resheim.reader.library.impl.AnnotationImpl <em>Annotation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see no.resheim.reader.library.impl.AnnotationImpl
	 * @see no.resheim.reader.library.impl.LibraryPackageImpl#getAnnotation()
	 * @generated
	 */
	int ANNOTATION = 2;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__LOCATION = 0;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__FORMAT = 1;

	/**
	 * The feature id for the '<em><b>Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__COLOR = 2;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__TEXT = 3;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__TIMESTAMP = 4;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__COMMENT = 5;

	/**
	 * The number of structural features of the '<em>Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link no.resheim.reader.library.AnnotationColor <em>Annotation Color</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see no.resheim.reader.library.AnnotationColor
	 * @see no.resheim.reader.library.impl.LibraryPackageImpl#getAnnotationColor()
	 * @generated
	 */
	int ANNOTATION_COLOR = 3;

	/**
	 * The meta object id for the '{@link no.resheim.reader.library.LocationFormat <em>Location Format</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see no.resheim.reader.library.LocationFormat
	 * @see no.resheim.reader.library.impl.LibraryPackageImpl#getLocationFormat()
	 * @generated
	 */
	int LOCATION_FORMAT = 4;


	/**
	 * Returns the meta object for class '{@link no.resheim.reader.library.Library <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Library</em>'.
	 * @see no.resheim.reader.library.Library
	 * @generated
	 */
	EClass getLibrary();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.reader.library.Library#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see no.resheim.reader.library.Library#getVersion()
	 * @see #getLibrary()
	 * @generated
	 */
	EAttribute getLibrary_Version();

	/**
	 * Returns the meta object for the reference list '{@link no.resheim.reader.library.Library#getVolumes <em>Volumes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Volumes</em>'.
	 * @see no.resheim.reader.library.Library#getVolumes()
	 * @see #getLibrary()
	 * @generated
	 */
	EReference getLibrary_Volumes();

	/**
	 * Returns the meta object for class '{@link no.resheim.reader.library.Book <em>Book</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Book</em>'.
	 * @see no.resheim.reader.library.Book
	 * @generated
	 */
	EClass getBook();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.reader.library.Book#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see no.resheim.reader.library.Book#getPath()
	 * @see #getBook()
	 * @generated
	 */
	EAttribute getBook_Path();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.reader.library.Book#getCoverImage <em>Cover Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cover Image</em>'.
	 * @see no.resheim.reader.library.Book#getCoverImage()
	 * @see #getBook()
	 * @generated
	 */
	EAttribute getBook_CoverImage();

	/**
	 * Returns the meta object for the reference list '{@link no.resheim.reader.library.Book#getAnnotations <em>Annotations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Annotations</em>'.
	 * @see no.resheim.reader.library.Book#getAnnotations()
	 * @see #getBook()
	 * @generated
	 */
	EReference getBook_Annotations();

	/**
	 * Returns the meta object for the reference '{@link no.resheim.reader.library.Book#getMetadata <em>Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Metadata</em>'.
	 * @see no.resheim.reader.library.Book#getMetadata()
	 * @see #getBook()
	 * @generated
	 */
	EReference getBook_Metadata();

	/**
	 * Returns the meta object for class '{@link no.resheim.reader.library.Annotation <em>Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotation</em>'.
	 * @see no.resheim.reader.library.Annotation
	 * @generated
	 */
	EClass getAnnotation();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.reader.library.Annotation#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see no.resheim.reader.library.Annotation#getLocation()
	 * @see #getAnnotation()
	 * @generated
	 */
	EAttribute getAnnotation_Location();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.reader.library.Annotation#getFormat <em>Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Format</em>'.
	 * @see no.resheim.reader.library.Annotation#getFormat()
	 * @see #getAnnotation()
	 * @generated
	 */
	EAttribute getAnnotation_Format();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.reader.library.Annotation#getColor <em>Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Color</em>'.
	 * @see no.resheim.reader.library.Annotation#getColor()
	 * @see #getAnnotation()
	 * @generated
	 */
	EAttribute getAnnotation_Color();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.reader.library.Annotation#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see no.resheim.reader.library.Annotation#getText()
	 * @see #getAnnotation()
	 * @generated
	 */
	EAttribute getAnnotation_Text();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.reader.library.Annotation#getTimestamp <em>Timestamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timestamp</em>'.
	 * @see no.resheim.reader.library.Annotation#getTimestamp()
	 * @see #getAnnotation()
	 * @generated
	 */
	EAttribute getAnnotation_Timestamp();

	/**
	 * Returns the meta object for the attribute '{@link no.resheim.reader.library.Annotation#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see no.resheim.reader.library.Annotation#getComment()
	 * @see #getAnnotation()
	 * @generated
	 */
	EAttribute getAnnotation_Comment();

	/**
	 * Returns the meta object for enum '{@link no.resheim.reader.library.AnnotationColor <em>Annotation Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Annotation Color</em>'.
	 * @see no.resheim.reader.library.AnnotationColor
	 * @generated
	 */
	EEnum getAnnotationColor();

	/**
	 * Returns the meta object for enum '{@link no.resheim.reader.library.LocationFormat <em>Location Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Location Format</em>'.
	 * @see no.resheim.reader.library.LocationFormat
	 * @generated
	 */
	EEnum getLocationFormat();

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
		 * The meta object literal for the '{@link no.resheim.reader.library.impl.LibraryImpl <em>Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see no.resheim.reader.library.impl.LibraryImpl
		 * @see no.resheim.reader.library.impl.LibraryPackageImpl#getLibrary()
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
		 * The meta object literal for the '<em><b>Volumes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY__VOLUMES = eINSTANCE.getLibrary_Volumes();

		/**
		 * The meta object literal for the '{@link no.resheim.reader.library.impl.BookImpl <em>Book</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see no.resheim.reader.library.impl.BookImpl
		 * @see no.resheim.reader.library.impl.LibraryPackageImpl#getBook()
		 * @generated
		 */
		EClass BOOK = eINSTANCE.getBook();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOK__PATH = eINSTANCE.getBook_Path();

		/**
		 * The meta object literal for the '<em><b>Cover Image</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOK__COVER_IMAGE = eINSTANCE.getBook_CoverImage();

		/**
		 * The meta object literal for the '<em><b>Annotations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOOK__ANNOTATIONS = eINSTANCE.getBook_Annotations();

		/**
		 * The meta object literal for the '<em><b>Metadata</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOOK__METADATA = eINSTANCE.getBook_Metadata();

		/**
		 * The meta object literal for the '{@link no.resheim.reader.library.impl.AnnotationImpl <em>Annotation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see no.resheim.reader.library.impl.AnnotationImpl
		 * @see no.resheim.reader.library.impl.LibraryPackageImpl#getAnnotation()
		 * @generated
		 */
		EClass ANNOTATION = eINSTANCE.getAnnotation();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATION__LOCATION = eINSTANCE.getAnnotation_Location();

		/**
		 * The meta object literal for the '<em><b>Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATION__FORMAT = eINSTANCE.getAnnotation_Format();

		/**
		 * The meta object literal for the '<em><b>Color</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATION__COLOR = eINSTANCE.getAnnotation_Color();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATION__TEXT = eINSTANCE.getAnnotation_Text();

		/**
		 * The meta object literal for the '<em><b>Timestamp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATION__TIMESTAMP = eINSTANCE.getAnnotation_Timestamp();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATION__COMMENT = eINSTANCE.getAnnotation_Comment();

		/**
		 * The meta object literal for the '{@link no.resheim.reader.library.AnnotationColor <em>Annotation Color</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see no.resheim.reader.library.AnnotationColor
		 * @see no.resheim.reader.library.impl.LibraryPackageImpl#getAnnotationColor()
		 * @generated
		 */
		EEnum ANNOTATION_COLOR = eINSTANCE.getAnnotationColor();

		/**
		 * The meta object literal for the '{@link no.resheim.reader.library.LocationFormat <em>Location Format</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see no.resheim.reader.library.LocationFormat
		 * @see no.resheim.reader.library.impl.LibraryPackageImpl#getLocationFormat()
		 * @generated
		 */
		EEnum LOCATION_FORMAT = eINSTANCE.getLocationFormat();

	}

} //LibraryPackage
