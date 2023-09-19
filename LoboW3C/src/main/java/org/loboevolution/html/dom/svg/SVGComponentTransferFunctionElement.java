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
 * <p>SVGComponentTransferFunctionElement interface.</p>
 *
 *
 *
 */
public interface SVGComponentTransferFunctionElement extends SVGElement {
	// Component Transfer Types
	/** Constant SVG_FECOMPONENTTRANSFER_TYPE_UNKNOWN=0 */
    short SVG_FECOMPONENTTRANSFER_TYPE_UNKNOWN = 0;
	/** Constant SVG_FECOMPONENTTRANSFER_TYPE_IDENTITY=1 */
    short SVG_FECOMPONENTTRANSFER_TYPE_IDENTITY = 1;
	/** Constant SVG_FECOMPONENTTRANSFER_TYPE_TABLE=2 */
    short SVG_FECOMPONENTTRANSFER_TYPE_TABLE = 2;
	/** Constant SVG_FECOMPONENTTRANSFER_TYPE_DISCRETE=3 */
    short SVG_FECOMPONENTTRANSFER_TYPE_DISCRETE = 3;
	/** Constant SVG_FECOMPONENTTRANSFER_TYPE_LINEAR=4 */
    short SVG_FECOMPONENTTRANSFER_TYPE_LINEAR = 4;
	/** Constant SVG_FECOMPONENTTRANSFER_TYPE_GAMMA=5 */
    short SVG_FECOMPONENTTRANSFER_TYPE_GAMMA = 5;

	/**
	 * <p>getType.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getType();

	/**
	 * <p>getTableValues.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumberList} object.
	 */
	SVGAnimatedNumberList getTableValues();

	/**
	 * <p>getSlope.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getSlope();

	/**
	 * <p>getIntercept.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getIntercept();

	/**
	 * <p>getAmplitude.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getAmplitude();

	/**
	 * <p>getExponent.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getExponent();

	/**
	 * <p>getOffset.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getOffset();
}
