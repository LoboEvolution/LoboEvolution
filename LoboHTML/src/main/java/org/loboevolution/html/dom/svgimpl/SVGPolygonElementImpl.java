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

import org.loboevolution.html.dom.svg.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * <p>SVGPolygonElementImpl class.</p>
 */
public class SVGPolygonElementImpl extends SVGGraphic implements SVGPolygonElement {

	/**
	 * <p>Constructor for SVGPolygonElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGPolygonElementImpl(final String name) {
		super(name);
	}

	@Override
	public SVGRect getBBox() {
		Shape shape = createShape(null);
		return new SVGRectImpl(shape.getBounds2D());
	}

	/** {@inheritDoc} */
	@Override
	public SVGPointList getPoints() {
		return constructPointList(getAttribute("points"));
	}

	/** {@inheritDoc} */
	@Override
	public SVGPointList getAnimatedPoints() {
		return constructPointList(getAttribute("points"));
	}

	/** {@inheritDoc} */
	@Override
	public void draw(final Graphics2D graphics) {
		final SVGMatrix ctm = getCTM();
		final Shape shape = createShape(ctm.getAffineTransform());
		animate(this);
		drawable(graphics, shape);
	}

	/** {@inheritDoc} */
	@Override
	public Shape createShape(AffineTransform transform) {
		GeneralPath path = new GeneralPath();
		SVGPointList points = getAnimatedPoints();
		int numPoints = points.getNumberOfItems();
		for (int i = 0; i < numPoints; i++) {
			SVGPoint point = points.getItem(i);
			float x = point.getX();
			float y = point.getY();
			if (i == 0) {
				path.moveTo(x, y);
			} else {
				path.lineTo(x, y);
			}
		}
		path.closePath();
		return path;
	}

	private SVGPointList constructPointList(String pointString) {
		SVGPointListImpl points = new SVGPointListImpl();
		StringTokenizer st = new StringTokenizer(pointString, " ,", false);
		while (st.hasMoreTokens()) {
			try {
				float x = Float.parseFloat(st.nextToken());
				float y = Float.parseFloat(st.nextToken());
				SVGPoint point = new SVGPointImpl(x, y);
				points.appendItem(point);
			} catch (NoSuchElementException e) {
			}
		}
		return points;
	}
}
