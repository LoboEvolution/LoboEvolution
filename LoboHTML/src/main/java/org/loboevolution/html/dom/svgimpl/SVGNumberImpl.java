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

import org.loboevolution.html.dom.svg.SVGNumber;

/**
 * <p>SVGNumberImpl class.</p>
 */
public class SVGNumberImpl implements SVGNumber {

	private float value;

	/**
	 * <p>Constructor for SVGNumberImpl.</p>
	 */
	public SVGNumberImpl() {
		this.value= 0;
	}

	/**
	 * <p>Constructor for SVGNumberImpl.</p>
	 *
	 * @param value a {@link java.lang.Float} object.
	 */
	public SVGNumberImpl(float value) {
		this.value = value;
	}

	/**
	 * <p>Constructor for SVGNumberImpl.</p>
	 *
	 * @param strValue a {@link java.lang.String} object.
	 */
	public SVGNumberImpl(String strValue) {
		this.value = Float.parseFloat(strValue);
	}

	/** {@inheritDoc} */
	@Override
	public float getValue() {
		return value;
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(float value) {
		this.value = value;
	}
}
