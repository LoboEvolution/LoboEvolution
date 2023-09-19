
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
 * <p>SVGFEBlendElement interface.</p>
 *
 *
 *
 */
public interface SVGFEBlendElement extends SVGElement, SVGFilterPrimitiveStandardAttributes {
	// Blend Mode Types
	/** Constant SVG_FEBLEND_MODE_UNKNOWN=0 */
    short SVG_FEBLEND_MODE_UNKNOWN = 0;
	/** Constant SVG_FEBLEND_MODE_NORMAL=1 */
    short SVG_FEBLEND_MODE_NORMAL = 1;
	/** Constant SVG_FEBLEND_MODE_MULTIPLY=2 */
    short SVG_FEBLEND_MODE_MULTIPLY = 2;
	/** Constant SVG_FEBLEND_MODE_SCREEN=3 */
    short SVG_FEBLEND_MODE_SCREEN = 3;
	/** Constant SVG_FEBLEND_MODE_DARKEN=4 */
    short SVG_FEBLEND_MODE_DARKEN = 4;
	/** Constant SVG_FEBLEND_MODE_LIGHTEN=5 */
    short SVG_FEBLEND_MODE_LIGHTEN = 5;

	/**
	 * <p>getIn1.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedString} object.
	 */
	SVGAnimatedString getIn1();

	/**
	 * <p>getIn2.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedString} object.
	 */
	SVGAnimatedString getIn2();

	/**
	 * <p>getMode.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getMode();
}
