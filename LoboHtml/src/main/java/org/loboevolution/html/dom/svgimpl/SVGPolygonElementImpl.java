/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.dom.svgimpl;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.loboevolution.html.dom.svg.SVGMatrix;
import org.loboevolution.html.dom.svg.SVGPoint;
import org.loboevolution.html.dom.svg.SVGPointList;
import org.loboevolution.html.dom.svg.SVGPolygonElement;

/**
 * <p>SVGPolygonElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGPolygonElementImpl extends SVGGraphic implements SVGPolygonElement {

	/**
	 * <p>Constructor for SVGPolygonElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGPolygonElementImpl(String name) {
		super(name);
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
	public void draw(Graphics2D graphics) {
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
