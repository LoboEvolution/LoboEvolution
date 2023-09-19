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
 * <p>SVGViewSpec interface.</p>
 *
 *
 *
 */
public interface SVGViewSpec extends SVGZoomAndPan, SVGFitToViewBox {
	/**
	 * <p>getTransform.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGTransformList} object.
	 */
	SVGTransformList getTransform();

	/**
	 * <p>getViewTarget.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGElement} object.
	 */
	SVGElement getViewTarget();

	/**
	 * <p>getViewBoxString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getViewBoxString();

	/**
	 * <p>getPreserveAspectRatioString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getPreserveAspectRatioString();

	/**
	 * <p>getTransformString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getTransformString();

	/**
	 * <p>getViewTargetString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getViewTargetString();
}
