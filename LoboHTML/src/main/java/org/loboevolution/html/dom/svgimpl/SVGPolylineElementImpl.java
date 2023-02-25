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

import org.loboevolution.html.dom.svg.SVGPoint;
import org.loboevolution.html.dom.svg.SVGPointList;
import org.loboevolution.html.dom.svg.SVGPolylineElement;
import org.loboevolution.html.dom.svg.SVGRect;

import java.awt.geom.GeneralPath;

/**
 * <p>SVGPolylineElementImpl class.</p>
 */
public class SVGPolylineElementImpl extends SVGPolygonElementImpl implements SVGPolylineElement {

	/**
	 * <p>Constructor for SVGPolylineElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGPolylineElementImpl(final String name) {
		super(name);
	}

	@Override
	public SVGRect getBBox() {
		GeneralPath shape = createShape();
		return new SVGRectImpl(shape.getBounds2D());
	}

	private GeneralPath createShape() {
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
		return path;
	}
}
