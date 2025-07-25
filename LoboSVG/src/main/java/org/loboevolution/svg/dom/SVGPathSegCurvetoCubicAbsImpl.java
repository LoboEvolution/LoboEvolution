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

import lombok.Getter;
import lombok.Setter;
import org.loboevolution.svg.SVGPathSegCurvetoCubicAbs;
import org.loboevolution.svg.SVGPoint;
import org.loboevolution.svg.SVGPointList;

/**
 * <p>SVGPathSegCurvetoCubicAbsImpl class.</p>
 */
@Getter
@Setter
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
	public SVGPathSegCurvetoCubicAbsImpl(final float x, final float y, final float x1, final float y1, final float x2, final float y2) {
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

	private void calculatePoints(final SVGPoint startPoint) {
		final SVGPointList points = new SVGPointListImpl();
		final float startPointX = startPoint.getX();
		final float startPointY = startPoint.getY();
		final float controlPoint1X = getX1();
		final float controlPoint1Y = getY1();
		final float controlPoint2X = getX2();
		final float controlPoint2Y = getY2();
		final float endPointX = getX();
		final float endPointY = getY();

		final int steps = 20;
		final float stepPow1 = (float) (1.0 / steps);
		final float stepPow2 = stepPow1 * stepPow1;
		final float stepPow3 = stepPow2 * stepPow1;

		final float pre1 = 3 * stepPow1;
		final float pre2 = 3 * stepPow2;
        final float pre4 = 6 * stepPow2;
		final float pre5 = 6 * stepPow3;

		final float tmp1X = startPointX - controlPoint1X * 2 + controlPoint2X;
		final float tmp1Y = startPointY - controlPoint1Y * 2 + controlPoint2Y;
		final float tmp2X = (controlPoint1X - controlPoint2X) * 3 - startPointX + endPointX;
		final float tmp2Y = (controlPoint1Y - controlPoint2Y) * 3 - startPointY + endPointY;

		float fx = startPointX;
		float fy = startPointY;
		float dfx = (controlPoint1X - startPointX) * pre1 + tmp1X * pre2 + tmp2X * stepPow3;
		float dfy = (controlPoint1Y - startPointY) * pre1 + tmp1Y * pre2 + tmp2Y * stepPow3;
		float ddfx = tmp1X * pre4 + tmp2X * pre5;
		float ddfy = tmp1Y * pre4 + tmp2Y * pre5;
		final float dddfx = tmp2X * pre5;
		final float dddfy = tmp2Y * pre5;

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

	public float getYAt(final float x, final SVGPoint startPoint) {

		if (points == null) {
			calculatePoints(startPoint);
		}

		final int numPoints = points.getNumberOfItems();
		SVGPoint pointBefore = startPoint;
		for (int i = 1; i < numPoints; i++) {
			final SVGPoint pointAfter = points.getItem(i);
			if (x >= pointBefore.getX() && x <= pointAfter.getX()) {
				final float percentBetweenPoints = (x - pointBefore.getX()) / (pointAfter.getX() - pointBefore.getX());
				return pointBefore.getY() + percentBetweenPoints * (pointAfter.getY() - pointBefore.getY());
			}
			pointBefore = pointAfter;
		}
		return pointBefore.getY();
	}
}
