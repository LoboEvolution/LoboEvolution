/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

import org.loboevolution.html.dom.svg.SVGPathSegCurvetoCubicAbs;
import org.loboevolution.html.dom.svg.SVGPoint;
import org.loboevolution.html.dom.svg.SVGPointList;

/**
 * <p>SVGPathSegCurvetoCubicAbsImpl class.</p>
 */
public class SVGPathSegCurvetoCubicAbsImpl implements SVGPathSegCurvetoCubicAbs {

	private float x;

	private float y;

	private float x1;

	private float y1;

	private float x2;

	private float y2;

	private SVGPointList points;

	/**
	 * <p>Constructor for SVGPathSegCurvetoCubicAbsImpl.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param x1 a float.
	 * @param y1 a float.
	 * @param x2 a float.
	 * @param y2 a float.
	 */
	public SVGPathSegCurvetoCubicAbsImpl(float x, float y, float x1, float y1, float x2, float y2) {
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
		return PATHSEG_CURVETO_CUBIC_ABS;
	}

	/** {@inheritDoc} */
	@Override
	public String getPathSegTypeAsLetter() {
		return "C";
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

	public SVGPointList getPoints() {
		return points;
	}

	public void setPoints(SVGPointList Points) {
		this.points = Points;
	}

	private void calculatePoints(SVGPoint startPoint) {
		SVGPointList points = new SVGPointListImpl();
		float startPointX = startPoint.getX();
		float startPointY = startPoint.getY();
		float controlPoint1X = getX1();
		float controlPoint1Y = getY1();
		float controlPoint2X = getX2();
		float controlPoint2Y = getY2();
		float endPointX = getX();
		float endPointY = getY();

		int steps = 20;
		float stepPow1 = (float) (1.0 / steps);
		float stepPow2 = stepPow1 * stepPow1;
		float stepPow3 = stepPow2 * stepPow1;

		float pre1 = 3 * stepPow1;
		float pre2 = 3 * stepPow2;
		float pre3 = stepPow3;
		float pre4 = 6 * stepPow2;
		float pre5 = 6 * stepPow3;

		float tmp1X = startPointX - controlPoint1X * 2 + controlPoint2X;
		float tmp1Y = startPointY - controlPoint1Y * 2 + controlPoint2Y;
		float tmp2X = (controlPoint1X - controlPoint2X) * 3 - startPointX + endPointX;
		float tmp2Y = (controlPoint1Y - controlPoint2Y) * 3 - startPointY + endPointY;

		float fx = startPointX;
		float fy = startPointY;
		float dfx = (controlPoint1X - startPointX) * pre1 + tmp1X * pre2 + tmp2X * pre3;
		float dfy = (controlPoint1Y - startPointY) * pre1 + tmp1Y * pre2 + tmp2Y * pre3;
		float ddfx = tmp1X * pre4 + tmp2X * pre5;
		float ddfy = tmp1Y * pre4 + tmp2Y * pre5;
		float dddfx = tmp2X * pre5;
		float dddfy = tmp2Y * pre5;

		// Add start point
		points.appendItem(startPoint);

		// add points along curve
		for (int c = 1; c < steps; c++) {

			fx += dfx;
			fy += dfy;
			dfx += ddfx;
			dfy += ddfy;
			ddfx += dddfx;
			ddfy += dddfy;
			points.appendItem(new SVGPointImpl(fx, fy));
		}

		points.appendItem(new SVGPointImpl(endPointX, endPointY));
		setPoints(points);
	}

	public float getYAt(float x, SVGPoint startPoint) {

		if (points == null) {
			calculatePoints(startPoint);
		}

		int numPoints = points.getNumberOfItems();
		SVGPoint pointBefore = startPoint;
		for (int i = 1; i < numPoints; i++) {
			SVGPoint pointAfter = points.getItem(i);
			if (x >= pointBefore.getX() && x <= pointAfter.getX()) {
				float percentBetweenPoints = (x - pointBefore.getX()) / (pointAfter.getX() - pointBefore.getX());
				return pointBefore.getY() + percentBetweenPoints * (pointAfter.getY() - pointBefore.getY());
			}
			pointBefore = pointAfter;
		}
		return pointBefore.getY();
	}
}
