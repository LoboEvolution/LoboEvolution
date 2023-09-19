
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

/**
 * <p>SVGLocatable interface.</p>
 *
 *
 *
 */
public interface SVGLocatable {
	/**
	 * <p>getNearestViewportElement.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGElement} object.
	 */
	SVGElement getNearestViewportElement();

	/**
	 * <p>getFarthestViewportElement.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGElement} object.
	 */
	SVGElement getFarthestViewportElement();

	/**
	 * <p>getBBox.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGRect} object.
	 */
	SVGRect getBBox();

	/**
	 * <p>getCTM.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 */
	SVGMatrix getCTM();

	/**
	 * <p>getScreenCTM.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 */
	SVGMatrix getScreenCTM();

	/**
	 * <p>getTransformToElement.</p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.svg.SVGElement} object.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGMatrix getTransformToElement(SVGElement element) throws SVGException;
}
