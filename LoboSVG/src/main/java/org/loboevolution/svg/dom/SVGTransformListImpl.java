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

import org.loboevolution.svg.SVGException;
import org.loboevolution.svg.SVGMatrix;
import org.loboevolution.svg.SVGTransform;
import org.loboevolution.svg.SVGTransformList;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <p>SVGTransformListImpl class.</p>
 */
public class SVGTransformListImpl implements SVGTransformList {
	
	private List<SVGTransform> transList;

	/**
	 * <p>Constructor for SVGTransformListImpl.</p>
	 */
	public SVGTransformListImpl() {
		transList = new ArrayList<>();
	}

	/** {@inheritDoc} */
	@Override
	public int getNumberOfItems() {
		return transList.size();
	}

	/** {@inheritDoc} */
	@Override
	public void clear() {
		transList.clear();
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform initialize(final SVGTransform newItem) throws SVGException {
		transList = new ArrayList<>();
		transList.add(newItem);
		return newItem;
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform getItem(final int index) {
		return transList.get(index);
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform insertItemBefore(final SVGTransform newItem, final int index) throws SVGException {

        transList.remove(newItem);

		if (index < 0) {
			transList.addFirst(newItem);
		} else if (index > getNumberOfItems() - 1) { // insert at end
			transList.add(newItem);
		} else {
			transList.add(index, newItem);
		}
		return newItem;
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform replaceItem(final SVGTransform newItem, final int index) throws SVGException {

        transList.remove(newItem);

		if (index < 0 || index > getNumberOfItems() - 1) {
			return null;
		}

		transList.remove(index);
		transList.add(index, newItem);
		return newItem;
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform removeItem(final int index) {
		return transList.remove(index);
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform appendItem(final SVGTransform newItem) throws SVGException {
		transList.add(newItem);
		return newItem;
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform createSVGTransformFromMatrix(final SVGMatrix matrix) {
		final SVGTransform transform = new SVGTransformImpl();
		transform.setMatrix(matrix);
		return transform;
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform consolidate() {
		final int numTransforms = getNumberOfItems();
		if (numTransforms == 0) {
			return null;
		}
		final SVGTransform transform = getItem(0);
		SVGMatrix result = transform.getMatrix();
		for (final SVGTransform svgTransform : transList) {
			result = result.multiply(svgTransform.getMatrix());
		}
		final SVGTransform newTransform = new SVGTransformImpl();
		newTransform.setMatrix(result);
		initialize(newTransform);
		return newTransform;
	}

	/**
	 * <p>getAffineTransform.</p>
	 *
	 * @return a {@link java.awt.geom.AffineTransform} object.
	 */
	protected AffineTransform getAffineTransform() {
		final int numTransforms = getNumberOfItems();
		if (numTransforms == 0) {
			return new AffineTransform();
		}

		SVGTransform transform = getItem(0);
		SVGMatrix result = transform.getMatrix();
		for (int i = 1; i < numTransforms; i++) {
			transform = getItem(i);
			result = result.multiply(transform.getMatrix());
		}
		return new AffineTransform(result.getA(), result.getB(), result.getC(), result.getD(), result.getE(), result.getF());
	}

	/**
	 * <p>createTransformList.</p>
	 * @param transformStr a {@link java.lang.String} object.
	 * @return a {@link SVGTransformList} object.
	 */
	public static SVGTransformList createTransformList(final String transformStr) {
		String transformString = transformStr;
		final String SCALE = "scale";
		final String TRANSLATE = "translate";
		final String MATRIX = "matrix";
		final String ROTATE = "rotate";
		final String SKEW_X = "skewX";
		final String SKEW_Y = "skewY";

		if (transformString == null) {
			return null;
		}

		transformString = transformString.trim();
		final SVGTransformListImpl transformList = new SVGTransformListImpl();
		final StringTokenizer st = new StringTokenizer(transformString, "()", false);
		while (st.hasMoreTokens()) {
			final String transformType = st.nextToken().trim();
			if (!st.hasMoreTokens()) {
				break;
			}
			final String transformArgs = st.nextToken().trim();
			if (transformType.contains(MATRIX)) {
				final StringTokenizer st1 = new StringTokenizer(transformArgs, ", ", false);
				final int numArgs = st1.countTokens();
				if (numArgs == 6) {
					final float a = Float.parseFloat(st1.nextToken());
					final float b = Float.parseFloat(st1.nextToken());
					final float c = Float.parseFloat(st1.nextToken());
					final float d = Float.parseFloat(st1.nextToken());
					final float e = Float.parseFloat(st1.nextToken());
					final float f = Float.parseFloat(st1.nextToken());
					final SVGTransformImpl transform = new SVGTransformImpl(SVGTransform.SVG_TRANSFORM_MATRIX);
					final SVGMatrixImpl matrix = new SVGMatrixImpl(a, b, c, d, e, f);
					transform.setMatrix(matrix);
					transformList.appendItem(transform);
				}
			} else if (transformType.contains(TRANSLATE)) {
				final StringTokenizer st1 = new StringTokenizer(transformArgs, ", ", false);
				final int numArgs = st1.countTokens();
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
				final SVGTransformImpl transform = new SVGTransformImpl(SVGTransform.SVG_TRANSFORM_TRANSLATE);
				transform.setTranslate(tx, ty);
				transformList.appendItem(transform);
			} else if (transformType.contains(SCALE)) {
				final StringTokenizer st1 = new StringTokenizer(transformArgs, ", ", false);
				final int numArgs = st1.countTokens();
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
				final SVGTransformImpl transform = new SVGTransformImpl(SVGTransform.SVG_TRANSFORM_SCALE);
				transform.setScale(sx, sy);
				transformList.appendItem(transform);
			} else if (transformType.contains(ROTATE)) {
				final StringTokenizer st1 = new StringTokenizer(transformArgs, ", ", false);
				final int numArgs = st1.countTokens();
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
				final SVGTransformImpl transform = new SVGTransformImpl(SVGTransform.SVG_TRANSFORM_ROTATE);
				transform.setRotate(angle, cx, cy);
				transformList.appendItem(transform);
			} else if (transformType.contains(SKEW_X)) {
				final float skewAngle = Float.parseFloat(transformArgs);
				final SVGTransformImpl transform = new SVGTransformImpl(SVGTransform.SVG_TRANSFORM_SKEWX);
				transform.setSkewX(skewAngle);
				transformList.appendItem(transform);
			} else if (transformType.contains(SKEW_Y)) {
				final float skewAngle = Float.parseFloat(transformArgs);
				final SVGTransformImpl transform = new SVGTransformImpl(SVGTransform.SVG_TRANSFORM_SKEWY);
				transform.setSkewY(skewAngle);
				transformList.appendItem(transform);
			}
		}
		return transformList;
	}
}
