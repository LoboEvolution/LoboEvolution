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

package org.loboevolution.svg.dom;

import org.loboevolution.svg.SVGAnimatedLengthList;
import org.loboevolution.svg.SVGLengthList;

/**
 * <p>SVGAnimatedLengthListImpl class.</p> 
 */
public class SVGAnimatedLengthListImpl implements SVGAnimatedLengthList {

	private final SVGLengthList lenght;

	/**
	 * <p>Constructor for SVGAnimatedLengthListImpl.</p>
	 *
	 * @param lenght a {@link SVGLengthList} object.
	 */
	public SVGAnimatedLengthListImpl(final SVGLengthList lenght) {
		this.lenght = lenght;
	}

	/** {@inheritDoc} */
	@Override
	public SVGLengthList getBaseVal() {
		return lenght;
	}

	/** {@inheritDoc} */
	@Override
	public SVGLengthList getAnimVal() {
		return lenght;
	}
}
