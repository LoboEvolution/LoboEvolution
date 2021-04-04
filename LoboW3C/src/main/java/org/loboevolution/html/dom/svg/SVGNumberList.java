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



/**
 * <p>SVGNumberList interface.</p>
 *
 *
 *
 */
public interface SVGNumberList {
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
	void clear();

	/**
	 * <p>initialize.</p>
	 *
	 * @param newItem a {@link org.loboevolution.html.dom.svg.SVGNumber} object.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGNumber} object.
	 * @throws org.w3c.dom.DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGNumber initialize(SVGNumber newItem) throws SVGException;

	/**
	 * <p>getItem.</p>
	 *
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGNumber} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	SVGNumber getItem(int index);

	/**
	 * <p>insertItemBefore.</p>
	 *
	 * @param newItem a {@link org.loboevolution.html.dom.svg.SVGNumber} object.
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGNumber} object.
	 * @throws org.w3c.dom.DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGNumber insertItemBefore(SVGNumber newItem, int index) throws SVGException;

	/**
	 * <p>replaceItem.</p>
	 *
	 * @param newItem a {@link org.loboevolution.html.dom.svg.SVGNumber} object.
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGNumber} object.
	 * @throws org.w3c.dom.DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGNumber replaceItem(SVGNumber newItem, int index) throws SVGException;

	/**
	 * <p>removeItem.</p>
	 *
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGNumber} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	SVGNumber removeItem(int index);

	/**
	 * <p>appendItem.</p>
	 *
	 * @param newItem a {@link org.loboevolution.html.dom.svg.SVGNumber} object.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGNumber} object.
	 * @throws org.w3c.dom.DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGNumber appendItem(SVGNumber newItem) throws SVGException;
}
