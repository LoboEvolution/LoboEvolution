package org.loboevolution.html.dom.svgimpl;

import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

import org.loboevolution.common.Nodes;
import org.loboevolution.html.dom.svg.SVGAnimatedEnumeration;
import org.loboevolution.html.dom.svg.SVGAnimatedString;
import org.loboevolution.html.dom.svg.SVGAnimatedTransformList;
import org.loboevolution.html.dom.svg.SVGGradientElement;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>SVGGradientElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGGradientElementImpl extends SVGGraphic implements SVGGradientElement {

	/**
	 * <p>Constructor for SVGGradientElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGGradientElementImpl(String name) {
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
	 * @param gradient a {@link org.w3c.dom.Element} object.
	 * @param shape2d a {@link java.awt.Shape} object.
	 * @return a {@link java.awt.Paint} object.
	 */
	public Paint gradient(Element gradient, Shape shape2d) {
		if (gradient instanceof SVGRadialGradientElementImpl) {
			SVGRadialGradientElementImpl radial = (SVGRadialGradientElementImpl) gradient;
			return radial(shape2d, radial, fractions(radial), colors(radial));
		}

		if (gradient instanceof SVGLinearGradientElementImpl) {
			SVGLinearGradientElementImpl linear = (SVGLinearGradientElementImpl) gradient;
			return new LinearGradientPaint(linear.getX1().getBaseVal().getValue(),
					linear.getY1().getBaseVal().getValue(), linear.getX2().getBaseVal().getValue(),
					linear.getY2().getBaseVal().getValue(), fractions(linear), colors(linear));
		}
		return null;
	}

	private Paint radial(Shape shape, SVGRadialGradientElementImpl radial, float[] fractions, Color[] colors) {
		float x = radial.getCx().getBaseVal().getValue();
		float y = radial.getCy().getBaseVal().getValue();
		float radius = radial.getR().getBaseVal().getValue();
		double w = shape.getBounds2D().getWidth();
		double h = shape.getBounds2D().getHeight();
		Point2D.Float center = new Point2D.Float(x / 100, y / 100);
		double cx = w * center.getX() + shape.getBounds2D().getX();
		double cy = h * center.getY() + shape.getBounds2D().getY();
		final Point2D newCenter = new Point2D.Double(cx, cy);
		double delta = newCenter.distance(new Point2D.Double(shape.getBounds2D().getCenterX(), shape.getBounds2D().getCenterY()));
		final double r = Math.sqrt(w * w + h * h) / 2;
		final float newRadius = (float) (delta + r * (radius / 100));

		return new RadialGradientPaint(newCenter, newRadius, newCenter, fractions, colors,
				MultipleGradientPaint.CycleMethod.REFLECT, MultipleGradientPaint.ColorSpaceType.SRGB,
				new AffineTransform());

	}

	private float[] fractions(Element elem) {
		ArrayList<Float> fractions = new ArrayList<Float>();
		NodeList childNodes = elem.getChildNodes();
		for (Node n : Nodes.iterable(childNodes)) {
			if (n instanceof SVGStopElementImpl) {
				SVGStopElementImpl stop = (SVGStopElementImpl) n;
				fractions.add(stop.getOffset().getBaseVal());
			}
		}
		float[] floatArray = new float[fractions.size()];
		int i = 0;

		for (Float f : fractions) {
			floatArray[i++] = f != null ? f : Float.NaN;
		}
		Arrays.sort(floatArray);
		return floatArray;
	}

	private Color[] colors(Element elem) {
		ArrayList<Color> colors = new ArrayList<Color>();
		NodeList childNodes = elem.getChildNodes();
		for (Node n : Nodes.iterable(childNodes)) {
			if (n instanceof SVGStopElementImpl) {
				SVGStopElementImpl stop = (SVGStopElementImpl) n;
				colors.add(stop.getStopColor());
			}
		}
		Color[] colorArray = new Color[colors.size()];
		int i = 0;
		for (Color c : colors) {
			colorArray[i++] = c;
		}
		return colorArray;
	}
}
