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

import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.dom.svg.SVGAnimatedEnumeration;
import org.loboevolution.html.dom.svg.SVGAnimatedString;
import org.loboevolution.html.dom.svg.SVGAnimatedTransformList;
import org.loboevolution.html.dom.svg.SVGGradientElement;
import org.loboevolution.html.node.Element;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p>SVGGradientElementImpl class.</p>
 */
public class SVGGradientElementImpl extends SVGStylableImpl implements SVGGradientElement {

	/**
	 * <p>Constructor for SVGGradientElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGGradientElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedString getHref() {
		String href = this.getAttribute("xlink:href");
		if (href == null) {
			href = this.getAttribute("href");
		}
		return new SVGAnimatedStringImpl(href);
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedEnumeration getGradientUnits() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedTransformList getGradientTransform() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedEnumeration getSpreadMethod() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * <p>gradient.</p>
	 *
	 * @param gradient a {@link org.loboevolution.html.node.Element} object.
	 * @param shape2d a {@link java.awt.Shape} object.
	 * @return a {@link java.awt.Paint} object.
	 */
	public Paint gradient(final Element gradient, final Shape shape2d) {
		if (gradient instanceof SVGRadialGradientElementImpl radial) {
            return radial(shape2d, radial, fractions(radial), colors(radial));
		}

		if (gradient instanceof SVGLinearGradientElementImpl linear) {
            return new LinearGradientPaint(linear.getX1().getBaseVal().getValue(),
					linear.getY1().getBaseVal().getValue(), linear.getX2().getBaseVal().getValue(),
					linear.getY2().getBaseVal().getValue(), fractions(linear), colors(linear));
		}
		return null;
	}

	private Paint radial(final Shape shape, final SVGRadialGradientElementImpl radial, final float[] fractions, final Color[] colors) {
		final float x = radial.getCx().getBaseVal().getValue();
		final float y = radial.getCy().getBaseVal().getValue();
		final float radius = radial.getR().getBaseVal().getValue();
		final double w = shape.getBounds2D().getWidth();
		final double h = shape.getBounds2D().getHeight();
		final Point2D.Float center = new Point2D.Float(x / 100, y / 100);
		final double cx = w * center.getX() + shape.getBounds2D().getX();
		final double cy = h * center.getY() + shape.getBounds2D().getY();
		final Point2D newCenter = new Point2D.Double(cx, cy);
		final double delta = newCenter.distance(new Point2D.Double(shape.getBounds2D().getCenterX(), shape.getBounds2D().getCenterY()));
		final double r = Math.sqrt(w * w + h * h) / 2;
		final float newRadius = (float) (delta + r * (radius / 100));
		return new RadialGradientPaint(newCenter, newRadius, newCenter, fractions, colors,
				MultipleGradientPaint.CycleMethod.REFLECT, MultipleGradientPaint.ColorSpaceType.SRGB,
				new AffineTransform());

	}

	private float[] fractions(final Element elem) {
		final ArrayList<Float> fractions = new ArrayList<>();
		final NodeListImpl children = (NodeListImpl)elem.getChildNodes();
		children.forEach(child -> {
			if (child instanceof SVGStopElementImpl stop) {
                fractions.add(stop.getOffset().getBaseVal());
			}
		});
		final float[] floatArray = new float[fractions.size()];
		int i = 0;

		for (final Float f : fractions) {
			floatArray[i++] = f != null ? f : Float.NaN;
		}
		Arrays.sort(floatArray);
		return floatArray;
	}

	private Color[] colors(final Element elem) {
		final ArrayList<Color> colors = new ArrayList<>();
		final NodeListImpl children = (NodeListImpl)elem.getChildNodes();
		children.forEach(child -> {
			if (child instanceof SVGStopElementImpl stop) {
                final Color stopColor = stop.getStopColor();
				colors.add(stopColor);
			}
		});
		final Color[] colorArray = new Color[colors.size()];
		int i = 0;
		for (final Color c : colors) {
			colorArray[i++] = c;
		}
		return colorArray;
	}
}
