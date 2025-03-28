/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
package org.loboevolution.svg;

/**
 * <p>SVGPatternElement interface.</p>
 */
public interface SVGPatternElement extends SVGElement, SVGURIReference, SVGTests, SVGLangSpace,
		SVGExternalResourcesRequired, SVGStylable, SVGFitToViewBox, SVGUnitTypes {
	/**
	 * <p>getPatternUnits.</p>
	 *
	 * @return a {@link SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getPatternUnits();

	/**
	 * <p>getPatternContentUnits.</p>
	 *
	 * @return a {@link SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getPatternContentUnits();

	/**
	 * <p>getPatternTransform.</p>
	 *
	 * @return a {@link SVGAnimatedTransformList} object.
	 */
	SVGAnimatedTransformList getPatternTransform();

	/**
	 * <p>getX.</p>
	 *
	 * @return a {@link SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getX();

	/**
	 * <p>getY.</p>
	 *
	 * @return a {@link SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getY();

	/**
	 * <p>getWidth.</p>
	 *
	 * @return a {@link SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getWidth();

	/**
	 * <p>getHeight.</p>
	 *
	 * @return a {@link SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getHeight();
}
