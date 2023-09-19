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
 * <p>SVGFECompositeElement interface.</p>
 *
 *
 *
 */
public interface SVGFECompositeElement extends SVGElement, SVGFilterPrimitiveStandardAttributes {
	// Composite Operators
	/** Constant SVG_FECOMPOSITE_OPERATOR_UNKNOWN=0 */
    short SVG_FECOMPOSITE_OPERATOR_UNKNOWN = 0;
	/** Constant SVG_FECOMPOSITE_OPERATOR_OVER=1 */
    short SVG_FECOMPOSITE_OPERATOR_OVER = 1;
	/** Constant SVG_FECOMPOSITE_OPERATOR_IN=2 */
    short SVG_FECOMPOSITE_OPERATOR_IN = 2;
	/** Constant SVG_FECOMPOSITE_OPERATOR_OUT=3 */
    short SVG_FECOMPOSITE_OPERATOR_OUT = 3;
	/** Constant SVG_FECOMPOSITE_OPERATOR_ATOP=4 */
    short SVG_FECOMPOSITE_OPERATOR_ATOP = 4;
	/** Constant SVG_FECOMPOSITE_OPERATOR_XOR=5 */
    short SVG_FECOMPOSITE_OPERATOR_XOR = 5;
	/** Constant SVG_FECOMPOSITE_OPERATOR_ARITHMETIC=6 */
    short SVG_FECOMPOSITE_OPERATOR_ARITHMETIC = 6;

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
	 * <p>getOperator.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getOperator();

	/**
	 * <p>getK1.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getK1();

	/**
	 * <p>getK2.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getK2();

	/**
	 * <p>getK3.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getK3();

	/**
	 * <p>getK4.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getK4();
}
