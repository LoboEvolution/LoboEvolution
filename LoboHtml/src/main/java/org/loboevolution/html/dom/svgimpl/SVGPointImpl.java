/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.svgimpl;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import org.loboevolution.html.dom.svg.SVGMatrix;
import org.loboevolution.html.dom.svg.SVGPoint;

/**
 * <p>SVGPointImpl class.</p>
 *
 *
 *
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
	public SVGPointImpl(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * <p>Constructor for SVGPointImpl.</p>
	 *
	 * @param point a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 */
	public SVGPointImpl(SVGPoint point) {
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
	public void setY(float y) {
		this.y = y;
	}

	/** {@inheritDoc} */
	@Override
	public SVGPoint matrixTransform(SVGMatrix matrix) {
		AffineTransform transform = matrix.getAffineTransform();
		Point2D srcPoint = new Point2D.Double(x, y);
		Point2D dstPoint = new Point2D.Double();
		transform.transform(srcPoint, dstPoint);
		return new SVGPointImpl((float) dstPoint.getX(), (float) dstPoint.getY());
	}

}
