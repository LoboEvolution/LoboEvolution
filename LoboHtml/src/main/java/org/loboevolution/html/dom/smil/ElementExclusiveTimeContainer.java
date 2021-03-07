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
import org.loboevolution.html.node.NodeList;

/**
 * This interface defines a time container with semantics based upon par, but
 * with the additional constraint that only one child element may play at a
 * time.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface ElementExclusiveTimeContainer extends ElementTimeContainer {
	/**
	 * Controls the end of the container. Need to address thr id-ref value.
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a {@link java.lang.String} object.
	 */
    String getEndSync();

	/**
	 * <p>setEndSync.</p>
	 *
	 * @param endSync a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
    void setEndSync(String endSync) throws DOMException;

	/**
	 * This should support another method to get the ordered collection of
	 * paused elements (the paused stack) at a given point in time.
	 *
	 * @return All paused elements at the current time.
	 */
    NodeList getPausedElements();

}
