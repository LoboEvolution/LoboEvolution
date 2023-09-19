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
 * <p>SVGFEConvolveMatrixElement interface.</p>
 *
 *
 *
 */
public interface SVGFEConvolveMatrixElement extends SVGElement, SVGFilterPrimitiveStandardAttributes {
	// Edge Mode Values
	/** Constant SVG_EDGEMODE_UNKNOWN=0 */
    short SVG_EDGEMODE_UNKNOWN = 0;
	/** Constant SVG_EDGEMODE_DUPLICATE=1 */
    short SVG_EDGEMODE_DUPLICATE = 1;
	/** Constant SVG_EDGEMODE_WRAP=2 */
    short SVG_EDGEMODE_WRAP = 2;
	/** Constant SVG_EDGEMODE_NONE=3 */
    short SVG_EDGEMODE_NONE = 3;

	/**
	 * <p>getOrderX.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedInteger} object.
	 */
	SVGAnimatedInteger getOrderX();

	/**
	 * <p>getOrderY.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedInteger} object.
	 */
	SVGAnimatedInteger getOrderY();

	/**
	 * <p>getKernelMatrix.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumberList} object.
	 */
	SVGAnimatedNumberList getKernelMatrix();

	/**
	 * <p>getDivisor.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getDivisor();

	/**
	 * <p>getBias.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getBias();

	/**
	 * <p>getTargetX.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedInteger} object.
	 */
	SVGAnimatedInteger getTargetX();

	/**
	 * <p>getTargetY.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedInteger} object.
	 */
	SVGAnimatedInteger getTargetY();

	/**
	 * <p>getEdgeMode.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getEdgeMode();

	/**
	 * <p>getKernelUnitLengthX.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getKernelUnitLengthX();

	/**
	 * <p>getKernelUnitLengthY.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getKernelUnitLengthY();

	/**
	 * <p>getPreserveAlpha.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedBoolean} object.
	 */
	SVGAnimatedBoolean getPreserveAlpha();
}
