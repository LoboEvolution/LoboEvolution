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

import org.loboevolution.html.dom.svg.SVGRect;

import java.awt.geom.Rectangle2D;


/**
 * <p>SVGRectImpl class.</p>
 */
public class SVGRectImpl implements SVGRect {

	private float x;

	private float y;

	private float width;

	private float height;

	/**
	 * <p>Constructor for SVGRectImpl.</p>
	 *
	 * @param x a {@link java.lang.Float} object.
	 * @param y a {@link java.lang.Float} object.
	 * @param width a {@link java.lang.Float} object.
	 * @param height a {@link java.lang.Float} object.
	 */
	public SVGRectImpl(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * <p>Constructor for SVGRectImpl.</p>
	 *
	 * @param rect a {@link java.awt.geom.Rectangle2D} object.
	 */
	public SVGRectImpl(Rectangle2D rect) {
		x = (float) rect.getX();
		y = (float) rect.getY();
		width = (float) rect.getWidth();
		height = (float) rect.getHeight();
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
	public float getWidth() {
		return width;
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(float width) {
		this.width = width;
	}

	/** {@inheritDoc} */
	@Override
	public float getHeight() {
		return height;
	}

	/** {@inheritDoc} */
	@Override
	public void setHeight(float height) {
		this.height = height;
	}

}
