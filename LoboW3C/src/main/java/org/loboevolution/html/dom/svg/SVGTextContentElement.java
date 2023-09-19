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
 * <p>SVGTextContentElement interface.</p>
 *
 *
 *
 */
public interface SVGTextContentElement
		extends SVGElement, SVGTests, SVGLangSpace, SVGExternalResourcesRequired, SVGStylable {
	// lengthAdjust Types
	/** Constant LENGTHADJUST_UNKNOWN=0 */
    short LENGTHADJUST_UNKNOWN = 0;
	/** Constant LENGTHADJUST_SPACING=1 */
    short LENGTHADJUST_SPACING = 1;
	/** Constant LENGTHADJUST_SPACINGANDGLYPHS=2 */
    short LENGTHADJUST_SPACINGANDGLYPHS = 2;

	/**
	 * <p>getTextLength.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getTextLength();

	/**
	 * <p>getLengthAdjust.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getLengthAdjust();

	/**
	 * <p>getNumberOfChars.</p>
	 *
	 * @return a int.
	 */
	int getNumberOfChars();

	/**
	 * <p>getComputedTextLength.</p>
	 *
	 * @return a float.
	 */
	float getComputedTextLength();

	/**
	 * <p>getSubStringLength.</p>
	 *
	 * @param charnum a int.
	 * @param nchars a int.
	 * @return a float.
	 * @throws DOMException if any.
	 */
	float getSubStringLength(int charnum, int nchars);

	/**
	 * <p>getStartPositionOfChar.</p>
	 *
	 * @param charnum a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @throws DOMException if any.
	 */
	SVGPoint getStartPositionOfChar(int charnum);

	/**
	 * <p>getEndPositionOfChar.</p>
	 *
	 * @param charnum a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @throws DOMException if any.
	 */
	SVGPoint getEndPositionOfChar(int charnum);

	/**
	 * <p>getExtentOfChar.</p>
	 *
	 * @param charnum a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGRect} object.
	 * @throws DOMException if any.
	 */
	SVGRect getExtentOfChar(int charnum);

	/**
	 * <p>getRotationOfChar.</p>
	 *
	 * @param charnum a int.
	 * @return a float.
	 * @throws DOMException if any.
	 */
	float getRotationOfChar(int charnum);

	/**
	 * <p>getCharNumAtPosition.</p>
	 *
	 * @param point a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @return a int.
	 */
	int getCharNumAtPosition(SVGPoint point);

	/**
	 * <p>selectSubString.</p>
	 *
	 * @param charnum a int.
	 * @param nchars a int.
	 * @throws DOMException if any.
	 */
	void selectSubString(int charnum, int nchars);
}
