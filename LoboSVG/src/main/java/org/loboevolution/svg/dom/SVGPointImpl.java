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

import org.loboevolution.svg.SVGMatrix;
import org.loboevolution.svg.SVGPoint;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * <p>SVGPointImpl class.</p>
 */
public class SVGPointImpl implements SVGPoint {

	private float x;

	private float y;

	/**
	 * <p>Constructor for SVGPointImpl.</p>
	 */
	public SVGPointImpl() {
		x = 0;
		y = 0;
	}

	/**
	 * <p>Constructor for SVGPointImpl.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 */
	public SVGPointImpl(final float x, final float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * <p>Constructor for SVGPointImpl.</p>
	 *
	 * @param point a {@link SVGPoint} object.
	 */
	public SVGPointImpl(final SVGPoint point) {
		x = point.getX();
		y = point.getY();
	}

	/** {@inheritDoc} */
	@Override
	public float getX() {
		return x;
	}

	/** {@inheritDoc} */
	@Override
	public void setX(final float x) {
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
	public SVGPoint matrixTransform(final SVGMatrix matrix) {
		final AffineTransform transform = matrix.getAffineTransform();
		final Point2D srcPoint = new Point2D.Double(x, y);
		final Point2D dstPoint = new Point2D.Double();
		transform.transform(srcPoint, dstPoint);
		return new SVGPointImpl((float) dstPoint.getX(), (float) dstPoint.getY());
	}

}
