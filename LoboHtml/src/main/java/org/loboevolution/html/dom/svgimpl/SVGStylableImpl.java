/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Shape;
import java.util.Iterator;

import org.loboevolution.common.Nodes;
import org.loboevolution.common.Strings;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.dom.svg.SVGElement;
import org.loboevolution.html.dom.svg.SVGSVGElement;
import org.loboevolution.html.dom.svg.SVGStylable;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.laf.ColorFactory;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.laf.FontKey;
import org.loboevolution.laf.LAFSettings;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

/**
 * <p>SVGStylableImpl class.</p>
 *
 *
 *
 */
public class SVGStylableImpl extends SVGElementImpl implements SVGStylable {
	
	/**
	 * <p>Constructor for SVGStylableImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGStylableImpl(String name) {
		super(name);
		this.setStyle(getAttribute("style"));
	}
	
	/** {@inheritDoc} */
	@Override
	public AbstractCSSProperties getStyle() {
		return super.getStyle();
	}

	/**
	 * <p>getFillPaint.</p>
	 *
	 * @param shape a {@link java.awt.Shape} object.
	 * @return a {@link java.awt.Paint} object.
	 */
	public Paint getFillPaint(Shape shape) {
		AbstractCSSProperties style = getStyle();
		Paint fillPaint = null;		
		final String fill =  Strings.isNotBlank(style.getFill()) ? style.getFill() : getAttribute("fill");
		final String fillOpacity = Strings.isNotBlank(style.getFillOpacity()) ? style.getFillOpacity() : getAttribute("fill-opacity");
		if (fill!= null && fill.toLowerCase().indexOf("url") != -1) {
			int hashIndex = fill.indexOf('#');
			if (hashIndex != -1) {
				String idElement = fill.substring(hashIndex + 1, fill.length() - 1);
				Element elementById = (Element) child(idElement);
				if (elementById instanceof SVGGradientElementImpl) {
					SVGGradientElementImpl grad = (SVGGradientElementImpl) elementById;
					fillPaint = grad.gradient(elementById, shape);
				}
			}
		} else {
			final Color color = ColorFactory.getInstance().getColor(fill);
			fillPaint = color;
			if (Strings.isNotBlank(fillOpacity)) {
				return makeTransparent(color, Float.parseFloat(fillOpacity));
			}
		}
		return fillPaint;
	}

	/**
	 * <p>getStrokelPaint.</p>
	 *
	 * @param shape a {@link java.awt.Shape} object.
	 * @return a {@link java.awt.Paint} object.
	 */
	public Paint getStrokelPaint(Shape shape) {
		AbstractCSSProperties style = getStyle();
		Paint strokePaint = null;
		final String stroke = Strings.isNotBlank(style.getStroke()) ? style.getStroke() : getAttribute("stroke");
		final String strokeOpacity = Strings.isNotBlank(style.getStrokeOpacity()) ? style.getStrokeOpacity() : getAttribute("stroke-opacity");
		if (stroke!= null && stroke.toLowerCase().indexOf("url") != -1) {
			int hashIndex = stroke.indexOf('#');
			if (hashIndex != -1) {
				String idElement = stroke.substring(hashIndex + 1, stroke.length() - 1);
				Element elementById = (Element) child(idElement);
				if (elementById instanceof SVGGradientElementImpl) {
					SVGGradientElementImpl grad = (SVGGradientElementImpl) elementById;
					strokePaint = grad.gradient(elementById, shape);
				}
			}
		} else {
			final Color color = ColorFactory.getInstance().getColor(stroke);
			strokePaint = color;
			if (Strings.isNotBlank(strokeOpacity)) {
				return makeTransparent(color, Float.parseFloat(strokeOpacity));
			}
		}
		return strokePaint;
	}
	
	/**
	 * <p>getStrokeLineCap.</p>
	 *
	 * @return a int.
	 */
	public int getStrokeLineCap() {
		AbstractCSSProperties style = getStyle();
		int intLineCap = -1;
		final String strokeLinecap = Strings.isNotBlank(style.getStrokeLineCap()) ? style.getStrokeLineCap() : getAttribute("stroke-linecap");
		switch (Strings.isNotBlank(strokeLinecap) ? strokeLinecap : "") {
		case "round":
			intLineCap = BasicStroke.CAP_ROUND;
			break;
		case "square":
			intLineCap = BasicStroke.CAP_SQUARE;
			break;
		case "miter":
			intLineCap = BasicStroke.JOIN_MITER;
			break;
		default:
			intLineCap = BasicStroke.CAP_BUTT;
			break;
		}
		return intLineCap;
	}
	
	/**
	 * <p>getStrokeLinejoin.</p>
	 *
	 * @return a int.
	 */
	public int getStrokeLinejoin() {
		AbstractCSSProperties style = getStyle();
		int lineJoin = -1;
		final String strokeLinejoin = Strings.isNotBlank(style.getStrokeLineJoin()) ? style.getStrokeLineJoin() : getAttribute("stroke-linejoin");
		switch (Strings.isNotBlank(strokeLinejoin) ? strokeLinejoin : "") {
		case "miter":
			lineJoin = BasicStroke.JOIN_MITER;
			break;
		case "round":
			lineJoin = BasicStroke.JOIN_ROUND;
			break;
		case "bevel":
			lineJoin = BasicStroke.JOIN_BEVEL;
			break;
		default:
			lineJoin = BasicStroke.JOIN_BEVEL;
			break;
		}
		return lineJoin;
	}
	
	/**
	 * <p>getStrokeWidth.</p>
	 *
	 * @return a int.
	 */
	public int getStrokeWidth() {
		AbstractCSSProperties style = getStyle();
		int strokeWidth = 1;
		final String strokeWidthProp = Strings.isNotBlank(style.getStrokeWidth()) ? style.getStrokeWidth() : getAttribute("stroke-width");
		if (Strings.isNotBlank(strokeWidthProp)) {
			strokeWidth = HtmlValues.getPixelSize(strokeWidthProp, null, 1);
		}
		return strokeWidth;
	}
	
	/**
	 * <p>getStrokeMiterlimit.</p>
	 *
	 * @return a int.
	 */
	public int getStrokeMiterlimit() {
		AbstractCSSProperties style = getStyle();
		int miterlimit = 4;
		final String strokeMiterlimit = Strings.isNotBlank(style.getStrokeMiterLimit()) ? style.getStrokeMiterLimit() : getAttribute("stroke-miterlimit");
		if (Strings.isNotBlank(strokeMiterlimit)) {
			miterlimit = HtmlValues.getPixelSize(strokeMiterlimit, null, 4);
		}
		return miterlimit;
	}
	
	/**
	 * <p>getStrokeDashArray.</p>
	 *
	 * @return an array of {@link float} objects.
	 */
	public float[] getStrokeDashArray() {
		AbstractCSSProperties style = getStyle();
		float[] dashArray = null;
		final String dasharray = Strings.isNotBlank(style.getStrokeDashArray()) ? style.getStrokeDashArray() : getAttribute("stroke-dasharray");
		if (Strings.isNotBlank(dasharray)) {
			String[] parts = dasharray.split("\\s*,\\s*|\\s+");
			dashArray = new float[parts.length];
			int i = 0;
			for (String str : parts) {
				dashArray[i] = Float.parseFloat(str);
				i++;
			}
		}
		return dashArray;
	}

	/**
	 * <p>getVisibility.</p>
	 *
	 * @return a boolean.
	 */
	public boolean getVisibility() {
		AbstractCSSProperties style = getStyle();
		final String visibility = Strings.isNotBlank(style.getVisibility()) ? style.getVisibility() : getAttribute("visibility");
		return "visible".equals(visibility);
	}

	/**
	 * <p>getDisplay.</p>
	 *
	 * @return a boolean.
	 */
	public boolean getDisplay() {
		AbstractCSSProperties style = getStyle();
		final String display = Strings.isNotBlank(style.getDisplay()) ? style.getDisplay() : getAttribute("display");
		return !"none".equals(display);
	}
	
	/**
	 * <p>getOpacity.</p>
	 *
	 * @return a float.
	 */
	public float getOpacity() {
		AbstractCSSProperties style = getStyle();
		final String opacityStr = Strings.isNotBlank(style.getOpacity()) ? style.getOpacity() : getAttribute("opacity");
		float opacity = 1;
		if (opacityStr != null) {
			opacity = Float.parseFloat(opacityStr);
		}
		if (opacity > 1) {
			opacity = 1;
		}
		if (opacity < 0) {
			opacity = 0;
		}
		return opacity;
	}
	
	/**
	 * <p>getClippingPath.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svgimpl.SVGClipPathElementImpl} object.
	 */
	public SVGClipPathElementImpl getClippingPath() {
		AbstractCSSProperties style = getStyle();
		String clipPathString = Strings.isNotBlank(style.getClipPath()) ? style.getClipPath() : getAttribute("clip-path");
		if (clipPathString != null) {
			if (clipPathString.toLowerCase().indexOf("url") != -1) {
				int hashIndex = clipPathString.indexOf('#');
				if (hashIndex != -1) {
					String clipId = clipPathString.substring(hashIndex + 1, clipPathString.length() - 1);
					Element clipElem = (Element) child(clipId);
					if (clipElem instanceof SVGClipPathElementImpl) {
						return (SVGClipPathElementImpl) clipElem;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * <p>getClipRule.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getClipRule() {
		AbstractCSSProperties style = getStyle();
		String clipRuleVal = Strings.isNotBlank(style.getClipRule()) ? style.getClipRule() : getAttribute("clip-rule");
		if (clipRuleVal != null) {
			return clipRuleVal;
		} else {
			return "nonzero";
		}
	}
	
	/**
	 * <p>getStopColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getStopColor() {
		AbstractCSSProperties style = getStyle();
		String stopcolor = Strings.isNotBlank(style.getStopColor()) ? style.getStopColor() : this.getAttribute("stop-color");
		Color color = Color.BLACK;
		if (stopcolor != null) {
			color = ColorFactory.getInstance().getColor(stopcolor);
			return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(255 * Float.parseFloat(getStopOpacity())));
		}
		return null;
	}

	/**
	 * <p>getStopOpacity.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getStopOpacity() {
		AbstractCSSProperties style = getStyle();
		String opacity = Strings.isNotBlank(style.getStopOpacity()) ? style.getStopOpacity() : this.getAttribute("stop-opacity");
		if (opacity == null) {
			opacity = "1";
		}
		return opacity;
	}
	
	
	/**
	 * <p>getTextAnchor.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getTextAnchor() {
		return getAttribute("text-anchor");
	}
	
	/**
	 * <p>getFont.</p>
	 *
	 * @return a {@link java.awt.Font} object.
	 */
	public Font getFont() {
		FontKey key = new FontKey();
		key.setFontFamily(Font.SANS_SERIF);
		key.setFontStyle(CSSValues.ITALIC.getValue());
		key.setFontVariant(CSSValues.SMALL_CAPS.getValue());
		key.setFontWeight(CSSValues.BOLD.getValue());
		key.setFontSize(new LAFSettings().getInstance().getFontSize());
		key.setLocales(null);
		key.setSuperscript(null);
		key.setLetterSpacing(0);
		key.setStrikethrough(false);
		key.setUnderline(null);
		return FontFactory.getInstance().getFont(key);
	}
	
	/**
	 * <p>getStroke.</p>
	 *
	 * @return a {@link java.awt.BasicStroke} object.
	 */
	public BasicStroke getStroke() {
		final int strokeWidth = getStrokeWidth();
		final int lineCap = getStrokeLineCap();
		final int lineJoin = getStrokeLinejoin();
		final int miterlimit = getStrokeMiterlimit();
		final float[] dashArray = getStrokeDashArray();
		return new BasicStroke(strokeWidth, lineCap, lineJoin, miterlimit, dashArray, 0f);
	}
	
	private Color makeTransparent(Color color, Float alpha) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(255 * alpha));
	}


	/**
	 * <p>child.</p>
	 *
	 * @param elementId a {@link java.lang.String} object.
	 * @return a {@link org.w3c.dom.Node} object.
	 */
	protected Node child(String elementId) {
		SVGSVGElement ownerSVGElement = getOwnerSVGElement();
		NodeListImpl nodeList = (NodeListImpl)ownerSVGElement.getChildNodes();
		for (Iterator<Node> i= nodeList.iterator(); i.hasNext();) {
			Node child = i.next();
			NodeListImpl nodeList1 = (NodeListImpl)child.getChildNodes();
			for (Iterator<Node> i1= nodeList1.iterator(); i1.hasNext();) {
				Node child1 = i1.next();
				final Element elem1 = (Element) child1;
				NamedNodeMap attributes2 = elem1.getAttributes();
				if (attributes2 != null) {
					for (Attr attr : Nodes.iterable(attributes2)) {
						if ("id".equals(attr.getNodeName()) && elementId.equals(attr.getNodeValue())) {
							return child1;
						}
					}
				}
			}

			final Element elem = (Element) child;
			NamedNodeMap attributes2 = elem.getAttributes();
			if (attributes2 != null) {
				for (Attr attr : Nodes.iterable(attributes2)) {
					if ("id".equals(attr.getNodeName()) && elementId.equals(attr.getNodeValue())) {
						return child;
					}
				}
			}
		}
		return null;
	}
	
	
	/**
	 * <p>drawStyle.</p>
	 *
	 * @param node a {@link org.w3c.dom.Node} object.
	 */
	protected void drawStyle(Node node) {
		AbstractCSSProperties style = getStyle();
		SVGElement child = (SVGElement) node;
		
		final String fill = Strings.isNotBlank(style.getFill()) ? style.getFill() : getAttribute("fill");
		if (Strings.isNotBlank(fill)) {
			child.setAttribute("fill", fill);
		}

		final String fillop = Strings.isNotBlank(style.getFillOpacity()) ? style.getFillOpacity() : getAttribute("fill-opacity");
		if (Strings.isNotBlank(fillop)) {
			child.setAttribute("fill-opacity", fillop);
		}
	
		final String stroke = Strings.isNotBlank(style.getStroke()) ? style.getStroke() : getAttribute("stroke");
		if (Strings.isNotBlank(stroke)) {
			child.setAttribute("stroke", stroke);
		}

		final String strokeop = Strings.isNotBlank(style.getStrokeOpacity()) ? style.getStrokeOpacity() : getAttribute("stroke-opacity");
		if (Strings.isNotBlank(strokeop)) {
			child.setAttribute("stroke-opacity", strokeop);
		}
		
		final String swidth = Strings.isNotBlank(style.getStrokeWidth()) ? style.getStrokeWidth() : getAttribute("stroke-width");
		if (Strings.isNotBlank(swidth)) {
			child.setAttribute("stroke-width", swidth);
		}
		
		final String slinecap = Strings.isNotBlank(style.getStrokeLineCap()) ? style.getStrokeLineCap() : getAttribute("stroke-linecap");
		if (Strings.isNotBlank(slinecap)) {
			child.setAttribute("stroke-linecap", slinecap);
		}
		
		final String slinejoin = Strings.isNotBlank(style.getStrokeLineJoin()) ? style.getStrokeLineJoin() : getAttribute("stroke-linejoin");
		if (Strings.isNotBlank(slinejoin)) {
			child.setAttribute("stroke-linejoin", slinejoin);
		}
		
		final String smiterlimit = Strings.isNotBlank(style.getStrokeMiterLimit()) ? style.getStrokeMiterLimit() : getAttribute("stroke-miterlimit");
		if (Strings.isNotBlank(smiterlimit)) {
			child.setAttribute("stroke-miterlimit", smiterlimit);
		}
		
		final String sdasharray = Strings.isNotBlank(style.getStrokeDashArray()) ? style.getStrokeDashArray() : getAttribute("stroke-dasharray");
		if (Strings.isNotBlank(sdasharray)) {
			child.setAttribute("stroke-dasharray", sdasharray);
		}
		
		final String opacity = Strings.isNotBlank(style.getOpacity()) ? style.getOpacity() : getAttribute("opacity");
		if (Strings.isNotBlank(opacity)) {
			child.setAttribute("opacity", opacity);
		}

		final String display = Strings.isNotBlank(style.getDisplay()) ? style.getDisplay() : getAttribute("display");
		if (Strings.isNotBlank(display)) {
			child.setAttribute("display", display);
		}
		
		final String visibility = Strings.isNotBlank(style.getVisibility()) ? style.getVisibility() : getAttribute("visibility");
		if (Strings.isNotBlank(visibility)) {
			child.setAttribute("visibility", visibility);
		}
		
		final String clipPath = Strings.isNotBlank(style.getClipPath()) ? style.getClipPath() : getAttribute("clip-path");
		if (Strings.isNotBlank(clipPath)) {
			child.setAttribute("clip-path", clipPath);
		}
		
		final String clipRule = Strings.isNotBlank(style.getClipRule()) ? style.getClipRule() : getAttribute("clip-rule");
		if (Strings.isNotBlank(clipRule)) {
			child.setAttribute("clip-rule", clipRule);
		}
		
		final String stopColor = Strings.isNotBlank(style.getStopColor()) ? style.getStopColor() : getAttribute("stop-color");
		if (Strings.isNotBlank(stopColor)) {
			child.setAttribute("stop-color", stopColor);
		}
		
		final String stopOpacity = Strings.isNotBlank(style.getStopOpacity()) ? style.getStopOpacity() : getAttribute("stop-opacity");
		if (Strings.isNotBlank(stopOpacity)) {
			child.setAttribute("stop-opacity", stopOpacity);
		}
	}
}
