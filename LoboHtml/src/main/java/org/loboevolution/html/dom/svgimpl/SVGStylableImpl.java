package org.loboevolution.html.dom.svgimpl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Shape;

import org.loboevolution.common.Nodes;
import org.loboevolution.common.Strings;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.svg.SVGAnimatedString;
import org.loboevolution.html.dom.svg.SVGSVGElement;
import org.loboevolution.html.dom.svg.SVGStylable;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.laf.ColorFactory;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.laf.FontKey;
import org.loboevolution.laf.LAFSettings;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSValue;

public class SVGStylableImpl extends SVGElementImpl implements SVGStylable {

	public SVGStylableImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedString getSvgClassName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CSSValue getPresentationAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Paint getFillPaint(Shape shape) {
		Paint fillPaint = null;
		final String fill = getAttribute("fill");
		final String fillOpacity = getAttribute("fill-opacity");
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

	public Paint getStrokelPaint(Shape shape) {
		Paint strokePaint = null;
		final String stroke = getAttribute("stroke");
		final String strokeOpacity = getAttribute("stroke-opacity");

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

	public boolean getVisibility() {
		final String visibility = getAttribute("visibility");
		return "visible".equals(visibility);
	}

	public boolean getDisplay() {
		final String visibility = getAttribute("display");
		return !"none".equals(visibility);
	}
	
	public float getOpacity() {
		final String opacityStr = getAttribute("opacity");
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
	
	public int getStrokeLineCap() {
		int intLineCap = -1;
		final String strokeLinecap = getAttribute("stroke-linecap");
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
	
	public int getStrokeLinejoin() {
		int lineJoin = -1;
		final String strokeLinejoin = getAttribute("stroke-linejoin");
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
	
	public int getStrokeWidth() {
		int strokeWidth = 1;
		final String strokeWidthProp = getAttribute("stroke-width");
		if (Strings.isNotBlank(strokeWidthProp)) {
			strokeWidth = HtmlValues.getPixelSize(strokeWidthProp, null, 1);
		}
		return strokeWidth;
	}
	
	public int getStrokeMiterlimit() {
		int miterlimit = 4;
		final String strokeMiterlimit = getAttribute("stroke-miterlimit");
		if (Strings.isNotBlank(strokeMiterlimit)) {
			miterlimit = HtmlValues.getPixelSize(strokeMiterlimit, null, 4);
		}
		return miterlimit;
	}
	
	public float[] getStrokeDashArray() {
		float[] dashArray = null;
		final String strokeMiterlimit = getAttribute("stroke-dasharray");
		if (Strings.isNotBlank(strokeMiterlimit)) {
			String parts[] = strokeMiterlimit.split("\\s*,\\s*|\\s+");
			dashArray = new float[parts.length];
			int i = 0;
			for (String str : parts) {
				dashArray[i] = Float.parseFloat(str);
				i++;
			}
		}
		return dashArray;
	}
	
	public SVGClipPathElementImpl getClippingPath() {
		String clipPathString = getAttribute("clip-path");
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
	
	public String getClipRule() {
		String clipRuleVal = getAttribute("clip-rule");
		if (clipRuleVal != null) {
			return clipRuleVal;
		} else {
			return "nonzero";
		}
	}
	
	public Color getStopColor() {
		String stopcolor = this.getAttribute("stop-color");
		Color color = Color.BLACK;
		if (stopcolor != null) {
			color = ColorFactory.getInstance().getColor(stopcolor);
			return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(255 * Float.parseFloat(getStopOpacity())));
		}
		return null;
	}

	public String getStopOpacity() {
		String opacity = this.getAttribute("stop-opacity");
		if (opacity == null) {
			opacity = "1";
		}
		return opacity;
	}
	
	
	public String getTextAnchor() {
		return getAttribute("text-anchor");
	}


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


	protected Node child(String elementId) {
		SVGSVGElement ownerSVGElement = getOwnerSVGElement();
		NodeList nodeList = ownerSVGElement.getChildNodes();
		for (Node child : Nodes.iterable(nodeList)) {
			for (Node child1 : Nodes.iterable(child.getChildNodes())) {
				NamedNodeMap attributes2 = child1.getAttributes();
				if (attributes2 != null) {
					for (Attr attr : Nodes.iterable(attributes2)) {
						if ("id".equals(attr.getNodeName()) && elementId.equals(attr.getNodeValue())) {
							return child1;
						}
					}
				}
			}

			NamedNodeMap attributes2 = child.getAttributes();
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
}