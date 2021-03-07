/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.dom.smil;

import org.loboevolution.html.dom.nodeimpl.DOMException;

/**
 * Controls the position, size and scaling of media object elements. See the
 * region element definition .
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SMILRegionElement extends SMILElement, ElementLayout {
	/**
	 * <p>getFit.</p>
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a {@link java.lang.String} object.
	 */
    String getFit();

	/**
	 * <p>setFit.</p>
	 *
	 * @param fit a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
    void setFit(String fit) throws DOMException;

	/**
	 * <p>getTop.</p>
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a {@link java.lang.String} object.
	 */
    String getTop();

	/**
	 * <p>setTop.</p>
	 *
	 * @param top a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
    void setTop(String top) throws DOMException;

	/**
	 * <p>getZIndex.</p>
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a int.
	 */
    int getZIndex();

	/**
	 * <p>setZIndex.</p>
	 *
	 * @param zIndex a int.
	 * @throws org.w3c.dom.DOMException if any.
	 */
    void setZIndex(int zIndex) throws DOMException;

}
