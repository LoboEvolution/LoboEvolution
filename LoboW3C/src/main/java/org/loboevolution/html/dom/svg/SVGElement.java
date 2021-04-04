/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.svg;


import org.loboevolution.html.node.Element;

/**
 * <p>SVGElement interface.</p>
 *
 *
 *
 */
public interface SVGElement extends Element {

	/**
	 * <p>getId.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getId();

	/**
	 * {@inheritDoc}
	 *
	 * <p>setId.</p>
	 */
	void setId(String id);

	/**
	 * <p>getOwnerSVGElement.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGSVGElement} object.
	 */
	SVGSVGElement getOwnerSVGElement();
	
	/**
	 * <p>setOwnerSVGElement.</p>
	 *
	 * @param elem a {@link org.loboevolution.html.dom.svg.SVGSVGElement} object.
	 */
	void setOwnerSVGElement(SVGSVGElement elem);

	/**
	 * <p>getViewportElement.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGElement} object.
	 */
	SVGElement getViewportElement();
}
