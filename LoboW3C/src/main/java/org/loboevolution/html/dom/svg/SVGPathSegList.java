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
 * <p>SVGPathSegList interface.</p>
 *
 *
 *
 */
public interface SVGPathSegList {
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
	 * @param newItem a {@link org.loboevolution.html.dom.svg.SVGPathSeg} object.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSeg} object.
	 * @throws org.w3c.dom.DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGPathSeg initialize(SVGPathSeg newItem) throws SVGException;

	/**
	 * <p>getItem.</p>
	 *
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSeg} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	SVGPathSeg getItem(int index);

	/**
	 * <p>insertItemBefore.</p>
	 *
	 * @param newItem a {@link org.loboevolution.html.dom.svg.SVGPathSeg} object.
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSeg} object.
	 * @throws org.w3c.dom.DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGPathSeg insertItemBefore(SVGPathSeg newItem, int index) throws SVGException;

	/**
	 * <p>replaceItem.</p>
	 *
	 * @param newItem a {@link org.loboevolution.html.dom.svg.SVGPathSeg} object.
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSeg} object.
	 * @throws org.w3c.dom.DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGPathSeg replaceItem(SVGPathSeg newItem, int index) throws SVGException;

	/**
	 * <p>removeItem.</p>
	 *
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSeg} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	SVGPathSeg removeItem(int index);

	/**
	 * <p>appendItem.</p>
	 *
	 * @param newItem a {@link org.loboevolution.html.dom.svg.SVGPathSeg} object.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSeg} object.
	 * @throws org.w3c.dom.DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGPathSeg appendItem(SVGPathSeg newItem) throws SVGException;
}
