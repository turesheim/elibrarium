/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package no.resheim.elibrarium.library;

import java.util.Date;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Marker</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link no.resheim.elibrarium.library.Marker#getLocation <em>Location</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.Marker#getFormat <em>Format</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.Marker#getTimestamp <em>Timestamp</em>}</li>
 * </ul>
 * </p>
 *
 * @see no.resheim.elibrarium.library.LibraryPackage#getMarker()
 * @model
 * @generated
 */
public interface Marker extends EObject {
	/**
	 * Returns the value of the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' attribute.
	 * @see #setLocation(String)
	 * @see no.resheim.elibrarium.library.LibraryPackage#getMarker_Location()
	 * @model required="true"
	 * @generated
	 */
	String getLocation();

	/**
	 * Sets the value of the '{@link no.resheim.elibrarium.library.Marker#getLocation <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' attribute.
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(String value);

	/**
	 * Returns the value of the '<em><b>Format</b></em>' attribute.
	 * The literals are from the enumeration {@link no.resheim.elibrarium.library.LocationFormat}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Format</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Format</em>' attribute.
	 * @see no.resheim.elibrarium.library.LocationFormat
	 * @see #setFormat(LocationFormat)
	 * @see no.resheim.elibrarium.library.LibraryPackage#getMarker_Format()
	 * @model required="true"
	 * @generated
	 */
	LocationFormat getFormat();

	/**
	 * Sets the value of the '{@link no.resheim.elibrarium.library.Marker#getFormat <em>Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Format</em>' attribute.
	 * @see no.resheim.elibrarium.library.LocationFormat
	 * @see #getFormat()
	 * @generated
	 */
	void setFormat(LocationFormat value);

	/**
	 * Returns the value of the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timestamp</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timestamp</em>' attribute.
	 * @see #setTimestamp(Date)
	 * @see no.resheim.elibrarium.library.LibraryPackage#getMarker_Timestamp()
	 * @model required="true"
	 * @generated
	 */
	Date getTimestamp();

	/**
	 * Sets the value of the '{@link no.resheim.elibrarium.library.Marker#getTimestamp <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timestamp</em>' attribute.
	 * @see #getTimestamp()
	 * @generated
	 */
	void setTimestamp(Date value);

} // Marker
