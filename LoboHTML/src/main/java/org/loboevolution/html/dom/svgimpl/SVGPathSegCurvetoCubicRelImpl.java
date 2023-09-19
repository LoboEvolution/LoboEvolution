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

import org.loboevolution.html.dom.svg.SVGPathSegCurvetoCubicRel;

/**
 * <p>SVGPathSegCurvetoCubicRelImpl class.</p>
 *
 *
 *
 */
public class SVGPathSegCurvetoCubicRelImpl implements SVGPathSegCurvetoCubicRel {

	private float x;

	private float y;

	private float x1;

	private float y1;

	private float x2;

	private float y2;

	/**
	 * <p>Constructor for SVGPathSegCurvetoCubicRelImpl.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param x1 a float.
	 * @param y1 a float.
	 * @param x2 a float.
	 * @param y2 a float.
	 */
	public SVGPathSegCurvetoCubicRelImpl(float x, float y, float x1, float y1, float x2, float y2) {
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	/** {@inheritDoc} */
	@Override
	public short getPathSegType() {
		return PATHSEG_CURVETO_CUBIC_REL;
	}

	/** {@inheritDoc} */
	@Override
	public String getPathSegTypeAsLetter() {
		return "c";
	}

	/** {@inheritDoc} */
	@Override
	public float getX() {
		return x;
	}

	/** {@inheritDoc} */
	@Override
	public void setX(float x) {
		this.x = x;
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

	/** {@inheritDoc} */
	@Override
	public float getX1() {
		return x1;
	}

	/** {@inheritDoc} */
	@Override
	public void setX1(float x1) {
		this.x1 = x1;
	}

	/** {@inheritDoc} */
	@Override
	public float getY1() {
		return y1;
	}

	/** {@inheritDoc} */
	@Override
	public void setY1(float y1) {
		this.y1 = y1;
	}

	/** {@inheritDoc} */
	@Override
	public float getX2() {
		return x2;
	}

	/** {@inheritDoc} */
	@Override
	public void setX2(float x2) {
		this.x2 = x2;
	}

	/** {@inheritDoc} */
	@Override
	public float getY2() {
		return y2;
	}

	/** {@inheritDoc} */
	@Override
	public void setY2(float y2) {
		this.y2 = y2;
	}
}
