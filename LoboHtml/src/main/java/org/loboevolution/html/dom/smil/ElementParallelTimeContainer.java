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

import org.w3c.dom.DOMException;

/**
 * A parallel container defines a simple parallel time grouping in
 * which multiple elements can play back at the same time. It may have to
 * specify a repeat iteration. (?)
 *
 * @author utente
 * @version $Id: $Id
 */
public interface ElementParallelTimeContainer extends ElementTimeContainer {
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
	 * This method returns the implicit duration in seconds.
	 *
	 * @return The implicit duration in seconds or -1 if the implicit is unknown
	 *         (indefinite?).
	 */
    float getImplicitDuration();

}
