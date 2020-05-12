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

import org.w3c.dom.DOMException;

/**
 * <p>SVGPointList interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGPointList {

	/**
	 * <p>getNumberOfItems.</p>
	 *
	 * @return a int.
	 */
	int getNumberOfItems();

	/**
	 * <p>clear.</p>
	 *
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void clear() throws DOMException;

	/**
	 * <p>initialize.</p>
	 *
	 * @param newItem a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @throws org.w3c.dom.DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGPoint initialize(SVGPoint newItem) throws DOMException, SVGException;

	/**
	 * <p>getItem.</p>
	 *
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	SVGPoint getItem(int index) throws DOMException;

	/**
	 * <p>insertItemBefore.</p>
	 *
	 * @param newItem a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @throws org.w3c.dom.DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGPoint insertItemBefore(SVGPoint newItem, int index) throws DOMException, SVGException;

	/**
	 * <p>replaceItem.</p>
	 *
	 * @param newItem a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @throws org.w3c.dom.DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGPoint replaceItem(SVGPoint newItem, int index) throws DOMException, SVGException;

	/**
	 * <p>removeItem.</p>
	 *
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	SVGPoint removeItem(int index) throws DOMException;

	/**
	 * <p>appendItem.</p>
	 *
	 * @param newItem a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @throws org.w3c.dom.DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGPoint appendItem(SVGPoint newItem) throws DOMException, SVGException;
}
