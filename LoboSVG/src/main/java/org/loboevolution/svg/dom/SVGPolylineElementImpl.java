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

import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.svg.SVGPoint;
import org.loboevolution.svg.SVGPointList;
import org.loboevolution.svg.SVGPolylineElement;
import org.loboevolution.svg.SVGRect;

import java.awt.geom.GeneralPath;

/**
 * <p>SVGPolylineElementImpl class.</p>
 */
public class SVGPolylineElementImpl extends SVGPolygonElementImpl implements SVGPolylineElement {

	/**
	 * <p>Constructor for SVGPolylineElementImpl.</p>
	 *
	 * @param element a {@link HTMLElement} object.
	 */
	public SVGPolylineElementImpl(final HTMLElement element) {
		super(element);
	}

	@Override
	public SVGRect getBBox() {
		final GeneralPath shape = createShape();
		return new SVGRectImpl(shape.getBounds2D());
	}

	private GeneralPath createShape() {
		final GeneralPath path = new GeneralPath();
		final SVGPointList points = getAnimatedPoints();
		final int numPoints = points.getNumberOfItems();
		for (int i = 0; i < numPoints; i++) {
			final SVGPoint point = points.getItem(i);
			final float x = point.getX();
			final float y = point.getY();
			if (i == 0) {
				path.moveTo(x, y);
			} else {
				path.lineTo(x, y);
			}
		}
		return path;
	}
}
