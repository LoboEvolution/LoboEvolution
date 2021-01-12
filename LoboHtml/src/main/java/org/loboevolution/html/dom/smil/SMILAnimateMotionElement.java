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
 * This interface present the animationMotion element in SMIL.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SMILAnimateMotionElement extends SMILAnimateElement {
	/**
	 * Specifies the curve that describes the attribute value as a function of
	 * time. Check with the SVG spec for better support
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a {@link java.lang.String} object.
	 */
    String getPath();

	/**
	 * <p>setPath.</p>
	 *
	 * @param path a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
    void setPath(String path) throws DOMException;

	/**
	 * Specifies the origin of motion for the animation.
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a {@link java.lang.String} object.
	 */
    String getOrigin();

	/**
	 * <p>setOrigin.</p>
	 *
	 * @param origin a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
    void setOrigin(String origin) throws DOMException;

}
