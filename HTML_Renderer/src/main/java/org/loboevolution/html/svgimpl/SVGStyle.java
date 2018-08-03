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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

import org.loboevolution.color.ColorFactory;
import org.loboevolution.html.control.BaseControl;
import org.loboevolution.html.info.SVGInfo;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.util.Nodes;
import org.loboevolution.util.Strings;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SVGStyle extends BaseControl{

	private static final long serialVersionUID = 1L;
	
	/** The modelN. */
	private SVGSVGElementImpl modelN;
	
	/** The svgiGroup. */
	private SVGInfo svgiGroup = new SVGInfo();

	public SVGStyle(SVGSVGElementImpl modelNode) {
		super(modelNode);
		this.modelN = modelNode;
	}

	public void drawFillAndStroke(Graphics2D g2d, Shape shape2d, SVGInfo svgi) {

		BasicStroke basicStroke = null;
		Paint strokeColor = null;
		Paint fillColor = Color.BLACK;
		float fillOpacity = 1.0F;
		float strokeOpacity = 1.0F;

		SVGInfo group = getSvgiGroup();

		if (group.getStyle() != null && group.getStyle().getFillOpacity() != null) {
			fillOpacity = Float.parseFloat(group.getStyle().getFillOpacity());
		} else if (svgi.getStyle().getFillOpacity() != null) {
			fillOpacity = Float.parseFloat(svgi.getStyle().getFillOpacity());
		}

		if (group.getStyle() != null && group.getStyle().getStrokeOpacity() != null) {
			strokeOpacity = Float.parseFloat(group.getStyle().getStrokeOpacity());
		} else if (svgi.getStyle().getStrokeOpacity() != null) {
			strokeOpacity = Float.parseFloat(svgi.getStyle().getStrokeOpacity());
		}

		if (group.getStyle() != null && group.getStyle().getOpacity() != null) {
			fillOpacity = Float.parseFloat(group.getStyle().getOpacity());
			strokeOpacity = Float.parseFloat(group.getStyle().getOpacity());
		} else if (svgi.getStyle().getOpacity() != null) {
			fillOpacity = Float.parseFloat(svgi.getStyle().getOpacity());
			strokeOpacity = Float.parseFloat(svgi.getStyle().getOpacity());
		}

		if (group.getStyle() != null && group.getStyle().getStroke() != null
				&& !"none".equalsIgnoreCase(group.getStyle().getStroke())) {
			Color color = ColorFactory.getInstance().getColor(group.getStyle().getStroke());
			strokeColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(255 * strokeOpacity));
			basicStroke = getStroking(g2d, group);
		} else if (svgi.getStyle().getStroke() != null && !"none".equalsIgnoreCase(svgi.getStyle().getStroke())) {
			String stroke = svgi.getStyle().getStroke();
			if (stroke.contains("url")) {
				String idElement = stroke.split("#")[1].replace(")", "").trim();
				Element elementById = modelN.getOwnerDocument().getElementById(idElement);
				if (elementById instanceof SVGRadialGradientElementImpl
						|| elementById instanceof SVGLinearGradientElementImpl) {
					strokeColor = gradient(elementById, shape2d);
				}
			} else {
				Color color = ColorFactory.getInstance().getColor(svgi.getStyle().getStroke());
				strokeColor = new Color(color.getRed(), color.getGreen(), color.getBlue(),
						Math.round(255 * strokeOpacity));
			}
			basicStroke = getStroking(g2d, svgi);
		}

		if (group.getStyle() != null && group.getStyle().getFill() != null
				&& !"none".equalsIgnoreCase(group.getStyle().getFill())) {
			Color color = ColorFactory.getInstance().getColor(group.getStyle().getFill());
			fillColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(255 * fillOpacity));
		} else if (svgi.getStyle().getFill() != null && !"none".equalsIgnoreCase(svgi.getStyle().getFill())) {
			String fill = svgi.getStyle().getFill();
			if (fill.contains("url")) {
				String idElement = fill.split("#")[1].replace(")", "").trim();
				Element elementById = modelN.getOwnerDocument().getElementById(idElement);
				if (elementById instanceof SVGRadialGradientElementImpl
						|| elementById instanceof SVGLinearGradientElementImpl) {
					fillColor = gradient(elementById, shape2d);
				}
			} else {
				Color color = ColorFactory.getInstance().getColor(svgi.getStyle().getFill());
				fillColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(255 * fillOpacity));
			}
		}

		if (svgi.isClip()) {
			g2d.clip(shape2d);

			if (fillColor != null) {
				g2d.setPaint(fillColor);
				g2d.fill(shape2d);
			}

			if (strokeColor != null) {
				g2d.setPaint(strokeColor);
				g2d.draw(basicStroke.createStrokedShape(shape2d));
			}
		} else if (Strings.isNotBlank(svgi.getClipPath())) {
			g2d.setClip(shape2d);
			g2d.setPaint(Color.WHITE);
			g2d.draw(shape2d);
			g2d.fill(shape2d);
		} else {
			if (fillColor != null) {
				g2d.setPaint(fillColor);
				g2d.fill(shape2d);
			}

			if (strokeColor != null) {
				g2d.setPaint(strokeColor);
				g2d.draw(basicStroke.createStrokedShape(shape2d));
			}
		}
	}

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

	public Paint radial(Shape shape, SVGRadialGradientElementImpl radial, float[] fractions, Color[] colors) {

		float x = radial.getCx().getBaseVal().getValue();
		float y = radial.getCy().getBaseVal().getValue();
		float radius = radial.getR().getBaseVal().getValue();
		double w = shape.getBounds2D().getWidth();
		double h = shape.getBounds2D().getHeight();
		Point2D.Float center = new Point2D.Float(x / 100, y / 100);
		double cx = w * center.getX() + shape.getBounds2D().getX();
		double cy = h * center.getY() + shape.getBounds2D().getY();
		final Point2D newCenter = new Point2D.Double(cx, cy);
		double delta = newCenter
				.distance(new Point2D.Double(shape.getBounds2D().getCenterX(), shape.getBounds2D().getCenterY()));
		final double r = Math.sqrt(w * w + h * h) / 2;
		final float newRadius = (float) (delta + r * (radius / 100));

		return new RadialGradientPaint(newCenter, newRadius, newCenter, fractions, colors,
				MultipleGradientPaint.CycleMethod.REFLECT, MultipleGradientPaint.ColorSpaceType.SRGB,
				new AffineTransform());

	}

	public float[] fractions(Element elem) {
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

	public Color[] colors(Element elem) {
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

	public BasicStroke getStroking(Graphics2D g2d, SVGInfo svgi) {

		BasicStroke basicStroke;
		int strokeWidth = 1;
		int intLineCap = BasicStroke.CAP_BUTT;
		int intlineJoin = BasicStroke.JOIN_BEVEL;
		int miterlimit = 4;

		if ("round".equals(svgi.getStyle().getStrokeLineCap())) {
			intLineCap = BasicStroke.CAP_ROUND;

		} else if ("square".equals(svgi.getStyle().getStrokeLineCap())) {
			intLineCap = BasicStroke.CAP_SQUARE;
		}
		if ("round".equals(svgi.getStyle().getStrokeLineCap())) {
			intlineJoin = BasicStroke.JOIN_ROUND;

		} else if ("miter".equals(svgi.getStyle().getStrokeLineCap())) {
			intlineJoin = BasicStroke.JOIN_MITER;
		}

		if (svgi.getStyle().getStrokeWidth() != null) {
			strokeWidth = HtmlValues.getPixelSize(svgi.getStyle().getStrokeWidth(), null, 1);
		}

		if (svgi.getStyle().getStrokeMiterLimit() != null) {
			miterlimit = HtmlValues.getPixelSize(svgi.getStyle().getStrokeMiterLimit(), null, 4);
		}

		if (svgi.getStyle().getStrokeDashArray() == null) {
			basicStroke = new BasicStroke(strokeWidth, intLineCap, intlineJoin, miterlimit);
		} else {
			String parts[] = svgi.getStyle().getStrokeDashArray().split("\\s*,\\s*|\\s+");

			float[] dashArray = new float[parts.length];
			int i = 0;
			for (String str : parts) {
				dashArray[i] = Float.parseFloat(str);
				i++;
			}

			basicStroke = new BasicStroke(strokeWidth, intLineCap, intlineJoin, miterlimit, dashArray, 0.0F);
		}

		if (basicStroke != null) {
			g2d.setStroke(basicStroke);
		}
		return basicStroke;
	}
	
	/**
	 * @return the svgiGroup
	 */
	public SVGInfo getSvgiGroup() {
		return svgiGroup;
	}

	/**
	 * @param svgiGroup
	 *            the svgiGroup to set
	 */
	public void setSvgiGroup(SVGInfo svgiGroup) {
		this.svgiGroup = svgiGroup;
	}
}