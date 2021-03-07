
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
package org.loboevolution.html.dom.svg;



/**
 * <p>SVGLengthList interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGLengthList {
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
	 * @param newItem a {@link org.loboevolution.html.dom.svg.SVGLength} object.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGLength} object.
	 * @throws org.w3c.dom.DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGLength initialize(SVGLength newItem) throws SVGException;

	/**
	 * <p>getItem.</p>
	 *
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGLength} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	SVGLength getItem(int index);

	/**
	 * <p>insertItemBefore.</p>
	 *
	 * @param newItem a {@link org.loboevolution.html.dom.svg.SVGLength} object.
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGLength} object.
	 * @throws org.w3c.dom.DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGLength insertItemBefore(SVGLength newItem, int index) throws SVGException;

	/**
	 * <p>replaceItem.</p>
	 *
	 * @param newItem a {@link org.loboevolution.html.dom.svg.SVGLength} object.
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGLength} object.
	 * @throws org.w3c.dom.DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGLength replaceItem(SVGLength newItem, int index) throws SVGException;

	/**
	 * <p>removeItem.</p>
	 *
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGLength} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	SVGLength removeItem(int index);

	/**
	 * <p>appendItem.</p>
	 *
	 * @param newItem a {@link org.loboevolution.html.dom.svg.SVGLength} object.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGLength} object.
	 * @throws org.w3c.dom.DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGLength appendItem(SVGLength newItem) throws SVGException;
}
