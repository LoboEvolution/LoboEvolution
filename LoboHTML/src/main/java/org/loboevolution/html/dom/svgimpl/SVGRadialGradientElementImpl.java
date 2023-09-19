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

package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedLength;
import org.loboevolution.html.dom.svg.SVGRadialGradientElement;

/**
 * <p>SVGRadialGradientElementImpl class.</p>
 */
public class SVGRadialGradientElementImpl extends SVGGradientElementImpl implements SVGRadialGradientElement {

	/**
	 * <p>Constructor for SVGRadialGradientElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGRadialGradientElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getCx() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("cx")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getCy() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("cy")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getR() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("r")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getFx() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("fx")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getFy() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("fy")));
	}
}
