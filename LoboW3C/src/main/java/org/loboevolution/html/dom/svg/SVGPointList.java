/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.svg;


import org.htmlunit.cssparser.dom.DOMException;

/**
 * <p>SVGPointList interface.</p>
 *
 *
 *
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
	 * @throws DOMException if any.
	 */
	void clear();

	/**
	 * <p>initialize.</p>
	 *
	 * @param newItem a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @throws DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGPoint initialize(SVGPoint newItem) throws SVGException;

	/**
	 * <p>getItem.</p>
	 *
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @throws DOMException if any.
	 */
	SVGPoint getItem(int index);

	/**
	 * <p>insertItemBefore.</p>
	 *
	 * @param newItem a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @throws DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGPoint insertItemBefore(SVGPoint newItem, int index) throws SVGException;

	/**
	 * <p>replaceItem.</p>
	 *
	 * @param newItem a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @throws DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGPoint replaceItem(SVGPoint newItem, int index) throws SVGException;

	/**
	 * <p>removeItem.</p>
	 *
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @throws DOMException if any.
	 */
	SVGPoint removeItem(int index);

	/**
	 * <p>appendItem.</p>
	 *
	 * @param newItem a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @throws DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGPoint appendItem(SVGPoint newItem) throws SVGException;
}
