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

package org.loboevolution.svg;

import org.loboevolution.SVGAnimatedTransformListImpl;
import org.loboevolution.common.Strings;
import org.loboevolution.css.CSSStyleDeclaration;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.StringTokenizer;

/**
 * <p>SVGTransformableImpl class.</p>
 */
public abstract class SVGTransformableImpl extends SVGLocatableImpl implements SVGTransformable {

    /**
     * <p>Constructor for SVGTransformableImpl.</p>
     *
     * @param name a {@link String} object.
     */
    public SVGTransformableImpl(final String name) {
        super(name);
    }


    /**
     * {@inheritDoc}
     */
    @Override
	public SVGAnimatedTransformList getTransform() {
		CSSStyleDeclaration style = getStyle();
		String transformString = Strings.isNotBlank(style.getTransform()) ? style.getTransform() : this.getAttribute("transform");
		if (transformString != null) {
			SVGTransformList createTransformList = createTransformList(transformString);
			return new SVGAnimatedTransformListImpl((SVGTransformListImpl) createTransformList);
		}
		return null;
	}

	/**
	 * <p>drawable.</p>
	 *
	 * @param graphics a {@link java.awt.Graphics2D} object.
	 * @param shape a {@link java.awt.Shape} object.
	 */
	protected void drawable(final Graphics2D graphics, final Shape shape) {
		final Paint fillPaint = getFillPaint(shape);
		final Paint strokePaint = getStrokelPaint(shape);
		final BasicStroke stroke = getStroke();
		final SVGClipPathElementImpl clipPath = getClippingPath();
		final SVGAnimatedTransformList animateTransformList = getTransform();
		graphics.setStroke(stroke);

		if (clipPath != null) {
			final Shape clipShape = clipPath.getClippingShape(this);
			if (clipShape != null) {
				graphics.setClip(clipShape);
			}
		}

		if (animateTransformList != null) {
			final SVGTransformList transformList = animateTransformList.getBaseVal();
			if (transformList != null) {
				transform(graphics, transformList);
			}
		}

		if (fillPaint == null && strokePaint == null) {
			graphics.setPaint(Color.BLACK);
			graphics.fill(shape);
		}

		if (fillPaint != null) {
			graphics.setPaint(fillPaint);
			graphics.fill(shape);
		}

		if (strokePaint != null) {
			graphics.setPaint(strokePaint);
			graphics.draw(shape);
		}

		super.draw(graphics);
	}

	private void transform(final Graphics2D graphics, final SVGTransformList transformList) {
		final int numPoints = transformList.getNumberOfItems();
		for (int i = 0; i < numPoints; i++) {
			final SVGTransform point = transformList.getItem(i);
			final SVGMatrixImpl mtrx = (SVGMatrixImpl) point.getMatrix();
			final AffineTransform affine = mtrx.getAffineTransform();
			graphics.transform(affine);
		}
	}

	private SVGTransformList createTransformList(final String transformStr) {
		SVGTransform transform;
		String transformString = transformStr;

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
			System.out.println("transformType " + transformType);
			switch (transformType) {
				case "matrix":
					final StringTokenizer st1 = new StringTokenizer(transformArgs, ", ", false);
					final int numArgs = st1.countTokens();
					if (numArgs == 6) {
						final float a = Float.parseFloat(st1.nextToken());
						final float b = Float.parseFloat(st1.nextToken());
						final float c = Float.parseFloat(st1.nextToken());
						final float d = Float.parseFloat(st1.nextToken());
						final float e = Float.parseFloat(st1.nextToken());
						final float f = Float.parseFloat(st1.nextToken());
						transform = new SVGTransformImpl();
						final SVGMatrix matrix = new SVGMatrixImpl(a, b, c, d, e, f);
						transform.setMatrix(matrix);
						transformList.appendItem(transform);
					}
					break;
				case "translate":
					transform = getSvgTransform1(transformArgs);
					transformList.appendItem(transform);
					break;
				case "scale":
					transform = getSvgTransform(transformArgs);
					transformList.appendItem(transform);
					break;
				case "rotate":
					transform = getTransform(transformArgs);
					transformList.appendItem(transform);
					break;
				case "skewX":
					transform = new SVGTransformImpl();
					transform.setSkewX(Float.parseFloat(transformArgs));
					transformList.appendItem(transform);
					break;
				case "skewY":
					transform = new SVGTransformImpl();
					transform.setSkewY(Float.parseFloat(transformArgs));
					transformList.appendItem(transform);
					break;
				default:
					break;
			}
		}
		return transformList;
	}

	private static SVGTransformImpl getSvgTransform1(String transformArgs) {
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
		final SVGTransformImpl transform = new SVGTransformImpl();
		transform.setTranslate(tx, ty);
		return transform;
	}

	private static SVGTransformImpl getTransform(String transformArgs) {
		final StringTokenizer st1 = new StringTokenizer(transformArgs, ", ", false);
		final int numArgs = st1.countTokens();
		float angle = 0;
		float cx = 0;
		float cy = 0;
		System.out.println("numArgs " + numArgs);
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
		final SVGTransformImpl transform = new SVGTransformImpl();
		transform.setRotate(angle, cx, cy);
		return transform;
	}

	private static SVGTransformImpl getSvgTransform(String transformArgs) {
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
		final SVGTransformImpl transform = new SVGTransformImpl();
		transform.setScale(sx, sy);
		return transform;
	}
}