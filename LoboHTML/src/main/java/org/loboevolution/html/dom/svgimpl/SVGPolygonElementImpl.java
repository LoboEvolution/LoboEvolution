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

package org.loboevolution.html.dom.svgimpl;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.html.dom.svg.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * <p>SVGPolygonElementImpl class.</p>
 */
@Slf4j
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
		final Shape shape = createShape(null);
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
	public Shape createShape(final AffineTransform transform) {
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
		path.closePath();
		return path;
	}

	private SVGPointList constructPointList(final String pointString) {
		final SVGPointListImpl points = new SVGPointListImpl();
		final StringTokenizer st = new StringTokenizer(pointString, " ,", false);
		while (st.hasMoreTokens()) {
			try {
				final float x = Float.parseFloat(st.nextToken());
				final float y = Float.parseFloat(st.nextToken());
				final SVGPoint point = new SVGPointImpl(x, y);
				points.appendItem(point);
			} catch (final NoSuchElementException e) {
				log.info(e.getMessage());
			}
		}
		return points;
	}
}
