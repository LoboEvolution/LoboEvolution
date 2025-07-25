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

import org.loboevolution.svg.SVGPathSegLinetoVerticalAbs;

/**
 * <p>SVGPathSegLinetoVerticalAbsImpl class.</p> 
 */
public class SVGPathSegLinetoVerticalAbsImpl implements SVGPathSegLinetoVerticalAbs {

	private float y;

	/**
	 * <p>Constructor for SVGPathSegLinetoVerticalAbsImpl.</p>
	 *
	 * @param y a float.
	 */
	public SVGPathSegLinetoVerticalAbsImpl(final float y) {
		this.y = y;
	}

	/** {@inheritDoc} */
	@Override
	public short getPathSegType() {
		return PATHSEG_LINETO_VERTICAL_ABS;
	}

	/** {@inheritDoc} */
	@Override
	public String getPathSegTypeAsLetter() {
		return "V";
	}

	/** {@inheritDoc} */
	@Override
	public float getY() {
		return y;
	}

	/** {@inheritDoc} */
	@Override
	public void setY(final float y) {
		this.y = y;
	}
}
