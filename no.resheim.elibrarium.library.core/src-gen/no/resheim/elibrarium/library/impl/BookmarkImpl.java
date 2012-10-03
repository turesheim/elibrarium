/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package no.resheim.elibrarium.library.impl;

import java.util.Date;

import no.resheim.elibrarium.library.Bookmark;
import no.resheim.elibrarium.library.LibraryPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bookmark</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link no.resheim.elibrarium.library.impl.BookmarkImpl#getId <em>Id</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.impl.BookmarkImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.impl.BookmarkImpl#getPage <em>Page</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.impl.BookmarkImpl#getHref <em>Href</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.impl.BookmarkImpl#getTimestamp <em>Timestamp</em>}</li>
 *   <li>{@link no.resheim.elibrarium.library.impl.BookmarkImpl#getText <em>Text</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BookmarkImpl extends CDOObjectImpl implements Bookmark {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BookmarkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryPackage.Literals.BOOKMARK;
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
	public String getId() {
		return (String)eGet(LibraryPackage.Literals.BOOKMARK__ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eSet(LibraryPackage.Literals.BOOKMARK__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLocation() {
		return (String)eGet(LibraryPackage.Literals.BOOKMARK__LOCATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocation(String newLocation) {
		eSet(LibraryPackage.Literals.BOOKMARK__LOCATION, newLocation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getPage() {
		return (Integer)eGet(LibraryPackage.Literals.BOOKMARK__PAGE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPage(int newPage) {
		eSet(LibraryPackage.Literals.BOOKMARK__PAGE, newPage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHref() {
		return (String)eGet(LibraryPackage.Literals.BOOKMARK__HREF, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHref(String newHref) {
		eSet(LibraryPackage.Literals.BOOKMARK__HREF, newHref);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getTimestamp() {
		return (Date)eGet(LibraryPackage.Literals.BOOKMARK__TIMESTAMP, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimestamp(Date newTimestamp) {
		eSet(LibraryPackage.Literals.BOOKMARK__TIMESTAMP, newTimestamp);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getText() {
		return (String)eGet(LibraryPackage.Literals.BOOKMARK__TEXT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setText(String newText) {
		eSet(LibraryPackage.Literals.BOOKMARK__TEXT, newText);
	}

} //BookmarkImpl
