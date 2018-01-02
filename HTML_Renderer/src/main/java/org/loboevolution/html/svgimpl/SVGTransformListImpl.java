/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.svgimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.loboevolution.w3c.svg.SVGException;
import org.loboevolution.w3c.svg.SVGMatrix;
import org.loboevolution.w3c.svg.SVGTransform;
import org.loboevolution.w3c.svg.SVGTransformList;
import org.w3c.dom.DOMException;

public class SVGTransformListImpl implements SVGTransformList {

	public static String SCALE = "scale";
	public static String TRANSLATE = "translate";
	public static String MATRIX = "matrix";
	public static String ROTATE = "rotate";
	public static String SKEW_X = "skewX";
	public static String SKEW_Y = "skewY";
	
	private List<SVGTransform> points;

	public SVGTransformListImpl() {
		this(new SVGTransform[0]);
	}

	public SVGTransformListImpl(SVGTransform[] points) {
		this.points = new ArrayList<SVGTransform>();
		for (SVGTransform s : points) {
			this.points.add(s);
		}
	}

	@Override
	public int getNumberOfItems() {
		return points.size();
	}

	@Override
	public void clear() throws DOMException {
		points.clear();
	}

	@Override
	public SVGTransform initialize(SVGTransform newItem) throws DOMException, SVGException {
		points = new ArrayList<SVGTransform>();
		points.add(newItem);
		return newItem;
	}

	@Override
	public SVGTransform getItem(int index) throws DOMException {
		return points.get(index);
	}

	@Override
	public SVGTransform insertItemBefore(SVGTransform newItem, int index) throws DOMException, SVGException {
		points.add(index, newItem);
		return newItem;
	}

	@Override
	public SVGTransform replaceItem(SVGTransform newItem, int index) throws DOMException, SVGException {
		points.remove(index);
		points.add(index, newItem);
		return newItem;
	}

	@Override
	public SVGTransform removeItem(int index) throws DOMException {
		SVGTransform item = points.get(index);
		points.remove(index);
		return item;
	}

	@Override
	public SVGTransform appendItem(SVGTransform newItem) throws DOMException, SVGException {
		points.add(newItem);
		return newItem;
	}

	@Override
	public SVGTransform createSVGTransformFromMatrix(SVGMatrix matrix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGTransform consolidate() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static SVGTransformList createTransformList(String transformString) {

		if (transformString == null) {
			return null;
		}

		transformString = transformString.trim();
		SVGTransformListImpl transformList = new SVGTransformListImpl();
		StringTokenizer st = new StringTokenizer(transformString, "()", false);

		while (st.hasMoreTokens()) {

			String transformType = st.nextToken().trim();
			if (!st.hasMoreTokens()) {
				break;
			}

			String transformArgs = st.nextToken().trim();
			if (transformType.equals(MATRIX)) {
				StringTokenizer st1 = new StringTokenizer(transformArgs, ", ", false);
				int numArgs = st1.countTokens();
				if (numArgs == 6) {
					float a = Float.parseFloat(st1.nextToken());
					float b = Float.parseFloat(st1.nextToken());
					float c = Float.parseFloat(st1.nextToken());
					float d = Float.parseFloat(st1.nextToken());
					float e = Float.parseFloat(st1.nextToken());
					float f = Float.parseFloat(st1.nextToken());
					SVGTransformImpl transform = new SVGTransformImpl(SVGTransform.SVG_TRANSFORM_MATRIX);
					SVGMatrixImpl matrix = new SVGMatrixImpl(a, b, c, d, e, f);
					transform.setMatrix(matrix);
					transformList.appendItem(transform);
				}
			} else if (transformType.equals(TRANSLATE)) {
				StringTokenizer st1 = new StringTokenizer(transformArgs, ", ", false);
				int numArgs = st1.countTokens();
				float tx = 0;
				float ty = 0;
				if (numArgs == 1) {
					tx = Float.parseFloat(st1.nextToken());
				} else if (numArgs == 2) {
					tx = Float.parseFloat(st1.nextToken());
					ty = Float.parseFloat(st1.nextToken());
				} else {
					if (numArgs > 2) {
						tx = Float.parseFloat(st1.nextToken());
						ty = Float.parseFloat(st1.nextToken());
					}
				}
				SVGTransformImpl transform = new SVGTransformImpl(SVGTransform.SVG_TRANSFORM_TRANSLATE);
				transform.setTranslate(tx, ty);
				transformList.appendItem(transform);
			} else if (transformType.equals(SCALE)) {
				StringTokenizer st1 = new StringTokenizer(transformArgs, ", ", false);
				int numArgs = st1.countTokens();
				float sx = 0;
				float sy = 0;
				if (numArgs == 1) {
					sx = Float.parseFloat(st1.nextToken());
					sy = sx;
				} else if (numArgs == 2) {
					sx = Float.parseFloat(st1.nextToken());
					sy = Float.parseFloat(st1.nextToken());
				} else {
					if (numArgs > 2) {
						sx = Float.parseFloat(st1.nextToken());
						sy = Float.parseFloat(st1.nextToken());
					}
				}
				SVGTransformImpl transform = new SVGTransformImpl(SVGTransform.SVG_TRANSFORM_SCALE);
				transform.setScale(sx, sy);
				transformList.appendItem(transform);
			} else if (transformType.equals(ROTATE)) {
				StringTokenizer st1 = new StringTokenizer(transformArgs, ", ", false);
				int numArgs = st1.countTokens();
				float angle = 0;
				float cx = 0;
				float cy = 0;
				if (numArgs == 1) {
					angle = Float.parseFloat(st1.nextToken());
				} else if (numArgs == 3) {
					angle = Float.parseFloat(st1.nextToken());
					cx = Float.parseFloat(st1.nextToken());
					cy = Float.parseFloat(st1.nextToken());
				} else {
					if (numArgs == 2) {
						angle = Float.parseFloat(st1.nextToken());
					} else if (numArgs > 3) {
						angle = Float.parseFloat(st1.nextToken());
						cx = Float.parseFloat(st1.nextToken());
						cy = Float.parseFloat(st1.nextToken());
					}
				}
				SVGTransformImpl transform = new SVGTransformImpl(SVGTransform.SVG_TRANSFORM_ROTATE);
				transform.setRotate(angle, cx, cy);
				transformList.appendItem(transform);
			} else if (transformType.equals(SKEW_X)) {
				float skewAngle = Float.parseFloat(transformArgs);
				SVGTransformImpl transform = new SVGTransformImpl(SVGTransform.SVG_TRANSFORM_SKEWX);
				transform.setSkewX(skewAngle);
				transformList.appendItem(transform);
			} else if (transformType.equals(SKEW_Y)) {
				float skewAngle = Float.parseFloat(transformArgs);
				SVGTransformImpl transform = new SVGTransformImpl(SVGTransform.SVG_TRANSFORM_SKEWY);
				transform.setSkewY(skewAngle);
				transformList.appendItem(transform);
			}
		}
		return transformList;
	}
}
