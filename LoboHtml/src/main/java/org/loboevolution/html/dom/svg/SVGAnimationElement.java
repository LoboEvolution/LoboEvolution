/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.svg;

import org.loboevolution.html.dom.smil.ElementTimeControl;
import org.w3c.dom.DOMException;

/**
 * <p>SVGAnimationElement interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGAnimationElement extends SVGElement, SVGTests, SVGExternalResourcesRequired, ElementTimeControl {

	/**
	 * <p>getTargetElement.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGElement} object.
	 */
	SVGElement getTargetElement();

	/**
	 * <p>getStartTime.</p>
	 *
	 * @return a float.
	 */
	float getStartTime();

	/**
	 * <p>getCurrentTime.</p>
	 *
	 * @return a float.
	 */
	float getCurrentTime();

	/**
	 * <p>getSimpleDuration.</p>
	 *
	 * @return a float.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	float getSimpleDuration() throws DOMException;
}
