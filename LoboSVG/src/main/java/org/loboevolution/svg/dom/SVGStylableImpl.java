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

import org.loboevolution.common.Nodes;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.node.*;
import org.loboevolution.css.CSSStyleDeclaration;
import org.loboevolution.laf.ColorFactory;
import org.loboevolution.svg.SVGElement;
import org.loboevolution.svg.SVGStylable;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>SVGStylableImpl class.</p>
 */
public class SVGStylableImpl extends SVGElementImpl implements SVGStylable {

	/**
	 * <p>Constructor for SVGStylableImpl.</p>
	 *
	 * @param element a {@link HTMLElement} object.
	 */
	public SVGStylableImpl(HTMLElement element) {
		super(element);
	}

	/**
	 * <p>getFillPaint.</p>
	 *
	 * @param shape a {@link java.awt.Shape} object.
	 * @return a {@link java.awt.Paint} object.
	 */
	public Paint getFillPaint(final Shape shape) {
		final CSSStyleDeclaration style = getStyle();
		Paint fillPaint = null;
		final String fill =  Strings.isNotBlank(style.getFill()) ? style.getFill() : getAttribute("fill");
		final String fillOpacity = Strings.isNotBlank(style.getFillOpacity()) ? style.getFillOpacity() : getAttribute("fill-opacity");
		if (fill!= null && fill.toLowerCase().contains("url")) {
			final int hashIndex = fill.indexOf('#');
			if (hashIndex != -1) {
				final String idElement = fill.substring(hashIndex + 1, fill.length() - 1);
				final Element elementById = (Element) child(idElement);
				if (elementById instanceof SVGGradientElementImpl grad) {
                    fillPaint = grad.gradient(elementById, shape);
				}
			}
		} else {
			final Color color = ColorFactory.getInstance().getColor(fill);
			fillPaint = color;
			if (Strings.isNotBlank(fillOpacity) && color != null) {
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
	public Paint getStrokelPaint(final Shape shape) {
		final CSSStyleDeclaration style = getStyle();
		Paint strokePaint = null;
		final String stroke = Strings.isNotBlank(style.getStroke()) ? style.getStroke() : getAttribute("stroke");
		final String strokeOpacity = Strings.isNotBlank(style.getStrokeOpacity()) ? style.getStrokeOpacity() : getAttribute("stroke-opacity");
		if (stroke!= null && stroke.toLowerCase().contains("url")) {
			final int hashIndex = stroke.indexOf('#');
			if (hashIndex != -1) {
				final String idElement = stroke.substring(hashIndex + 1, stroke.length() - 1);
				final Element elementById = (Element) child(idElement);
				if (elementById instanceof SVGGradientElementImpl grad) {
                    strokePaint = grad.gradient(elementById, shape);
				}
			}
		} else {
			final Color color = ColorFactory.getInstance().getColor(stroke);
			strokePaint = color;
			if (Strings.isNotBlank(strokeOpacity) && color != null) {
				return makeTransparent(color, Float.parseFloat(strokeOpacity));
			}
		}
		return strokePaint;
	}

	/**
	 * <p>getStrokeLineCap.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getStrokeLineCap() {
		final CSSStyleDeclaration style = getStyle();
		final int intLineCap;
		final String strokeLinecap = Strings.isNotBlank(style.getStrokeLineCap()) ? style.getStrokeLineCap() : getAttribute("stroke-linecap");
        intLineCap = switch (Strings.isNotBlank(strokeLinecap) ? strokeLinecap : "") {
            case "round" -> BasicStroke.CAP_ROUND;
            case "square" -> BasicStroke.CAP_SQUARE;
            case "miter" -> BasicStroke.JOIN_MITER;
            default -> BasicStroke.CAP_BUTT;
        };
		return intLineCap;
	}

	/**
	 * <p>getStrokeLinejoin.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getStrokeLinejoin() {
		final CSSStyleDeclaration style = getStyle();
		final int lineJoin;
		final String strokeLinejoin = Strings.isNotBlank(style.getStrokeLineJoin()) ? style.getStrokeLineJoin() : getAttribute("stroke-linejoin");
        lineJoin = switch (Strings.isNotBlank(strokeLinejoin) ? strokeLinejoin : "") {
            case "miter" -> BasicStroke.JOIN_MITER;
            case "round" -> BasicStroke.JOIN_ROUND;
            default -> BasicStroke.JOIN_BEVEL;
        };
		return lineJoin;
	}

	/**
	 * <p>getStrokeWidth.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getStrokeWidth() {
		final CSSStyleDeclaration style = getStyle();
		int strokeWidth = 1;
		final String strokeWidthProp = Strings.isNotBlank(style.getStrokeWidth()) ? style.getStrokeWidth() : getAttribute("stroke-width");
		if (Strings.isNotBlank(strokeWidthProp)) {
			strokeWidth = Integer.parseInt(strokeWidthProp); //HtmlValues.getPixelSize(strokeWidthProp, null, doc.getDefaultView(), 1);
		}
		return strokeWidth;
	}

	/**
	 * <p>getStrokeMiterlimit.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getStrokeMiterlimit() {
		final CSSStyleDeclaration style = getStyle();
		int miterlimit = 4;
		final String strokeMiterlimit = Strings.isNotBlank(style.getStrokeMiterLimit()) ? style.getStrokeMiterLimit() : getAttribute("stroke-miterlimit");
		if (Strings.isNotBlank(strokeMiterlimit)) {
			miterlimit = Integer.parseInt(strokeMiterlimit); //HtmlValues.getPixelSize(strokeMiterlimit, null, doc.getDefaultView(), 4);
		}
		return miterlimit;
	}


	/**
	 * <p>getStrokeDashArray.</p>
	 *
	 * @return an array of {@link float} objects.
	 */
	public float[] getStrokeDashArray() {
		final CSSStyleDeclaration style = getStyle();
		float[] dashArray = null;
		final String dasharray = Strings.isNotBlank(style.getStrokeDashArray()) ? style.getStrokeDashArray() : getAttribute("stroke-dasharray");
		if (Strings.isNotBlank(dasharray) && !"none".equals(dasharray)) {
			final String[] parts = dasharray.split("\\s*,\\s*|\\s+");
			dashArray = new float[parts.length];
			int i = 0;
			for (final String str : parts) {
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
		final CSSStyleDeclaration style = getStyle();
		final String visibility = Strings.isNotBlank(style.getVisibility()) ? style.getVisibility() : getAttribute("visibility");
		return "visible".equals(visibility);
	}

	/**
	 * <p>getDisplay.</p>
	 *
	 * @return a boolean.
	 */
	public boolean getDisplay() {
		final CSSStyleDeclaration style = getStyle();
		final String display = Strings.isNotBlank(style.getDisplay()) ? style.getDisplay() : getAttribute("display");
		return !"none".equals(display);
	}

	/**
	 * <p>getOpacity.</p>
	 *
	 * @return a float.
	 */
	public float getOpacity() {
		final CSSStyleDeclaration style = getStyle();
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
	 * @return a {@link SVGClipPathElementImpl} object.
	 */
	public SVGClipPathElementImpl getClippingPath() {
		final CSSStyleDeclaration style = getStyle();
		final String clipPathString = Strings.isNotBlank(style.getClipPath()) ? style.getClipPath() : getAttribute("clip-path");
		if (clipPathString != null) {
			if (clipPathString.toLowerCase().contains("url")) {
				final int hashIndex = clipPathString.indexOf('#');
				if (hashIndex != -1) {
					final String clipId = clipPathString.substring(hashIndex + 1, clipPathString.length() - 1);
					final Element clipElem = (Element) child(clipId);
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
		final CSSStyleDeclaration style = getStyle();
		final String clipRuleVal = Strings.isNotBlank(style.getClipRule()) ? style.getClipRule() : getAttribute("clip-rule");
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
		final CSSStyleDeclaration style = getStyle();
		final String stopcolor = Strings.isNotBlank(style.getStopColor()) ? style.getStopColor() : this.getAttribute("stop-color");
		if (stopcolor != null) {
			final Color color = ColorFactory.getInstance().getColor(stopcolor);
			return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(255 * Float.parseFloat(getStopOpacity())));
		}
		return new Color(0, 0, 0);
	}

	/**
	 * <p>getStopOpacity.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getStopOpacity() {
		final CSSStyleDeclaration style = getStyle();
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
		final CSSStyleDeclaration style = getStyle();
		return null;/*
		final FontKey key = FontValues.getDefaultFontKey(getHtmlRendererConfig());
		key.setFontStyle(CSSValues.ITALIC.getValue());
		key.setFontVariant(CSSValues.SMALL_CAPS.getValue());
		key.setFontWeight(CSSValues.BOLD.getValue());
		key.setLocales(null);
		key.setSuperscript(null);
		key.setLetterSpacing(0);
		key.setStrikethrough(false);
		key.setUnderline(null);
		return FontFactory.getInstance().getFont(FontValues.getFontKey(key, this, style, null));*/
	}

	public SVGFontElementImpl getFontElement() {
		return new SVGFontElementImpl(null);
	}

	public float getFontSize(final AffineTransform inverseTransform) {
		final CSSStyleDeclaration style = getStyle();
		if (Strings.isNotBlank(style.getFontSize())) {
			final SVGLengthImpl size = new SVGLengthImpl(style.getFontSize());
			return size.getTransformedLength(inverseTransform);
		}
		return 20;
	}

	public float getFontUnitsPerEm() {
		final SVGFontFaceElementImpl fontFace = getFontFace();
		if (fontFace != null) {
			String fontEmString = fontFace.getAttribute("units-per-em");
			if (Strings.isCssBlank(fontEmString)) {
				fontEmString = fontFace.getAttribute("ascent");
			}
			if (Strings.isCssBlank(fontEmString)) {
				fontEmString = fontFace.getAttribute("x-height");
			}
			if (Strings.isCssBlank(fontEmString)) {
				fontEmString = fontFace.getAttribute("cap-height");
			}
			if (Strings.isCssBlank(fontEmString)) {
				fontEmString = fontFace.getAttribute("top-line");
			}
			if (Strings.isCssNotBlank(fontEmString)) {
				return Float.parseFloat(fontEmString);
			}
		}
		return 1.0f;
	}

	public SVGFontFaceElementImpl getFontFace() {
		final AtomicReference<SVGFontFaceElementImpl> fontFace = new AtomicReference<>();
		final NodeList children = getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child instanceof SVGFontFaceElementImpl) {
				fontFace.set((SVGFontFaceElementImpl) child);
			}
		}
		return fontFace.get();
	}

	public float getFontAscent() {
		final SVGFontFaceElementImpl fontFace = getFontFace();
		if (fontFace != null) {
			String fontAscentString = fontFace.getAttribute("ascent");
			if (Strings.isCssBlank(fontAscentString)) {
				fontAscentString = fontFace.getAttribute("x-height");
			}
			if (Strings.isCssBlank(fontAscentString)) {
				fontAscentString = fontFace.getAttribute("cap-height");
			}
			if (Strings.isCssBlank(fontAscentString)) {
				fontAscentString = fontFace.getAttribute("top-line");
			}
			if (Strings.isCssNotBlank(fontAscentString)) {
				return Float.parseFloat(fontAscentString);
			}
		}
		return 1.0f;
	}

	public float getFontDescent() {
		final SVGFontFaceElementImpl fontFace = getFontFace();
		if (fontFace != null) {
			final String fontDescentString = fontFace.getAttribute("descent");
			if (!fontDescentString.isEmpty()) {
				return Float.parseFloat(fontDescentString);
			}
		}
		return 0;
	}

	public float getHorizAdvX() {
		final String horizAdvX = getAttribute("horiz-adv-x");
		if (!horizAdvX.isEmpty()) {
			return Float.parseFloat(horizAdvX);
		}
		return 0;
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

	private Color makeTransparent(final Color color, final Float alpha) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(255 * alpha));
	}


	/**
	 * <p>child.</p>
	 *
	 * @param elementId a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	protected Node child(final String elementId) {
		final SVGElement ownerSVGElement = getOwnerSVGElement();
		final NodeList children = ownerSVGElement.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			final NodeList children1 = child.getChildNodes();
			for (int e = 0; e < children1.getLength(); e++) {
				Node child1 = children1.item(e);
				if (child1 instanceof Element elem1) {
                    final NamedNodeMap attributes2 = elem1.getAttributes();
					if (attributes2 != null) {
						for (final Node nodeAttr : Nodes.iterable(attributes2)) {
							final Attr attr = (Attr) nodeAttr;
							if ("id".equals(attr.getNodeName()) && elementId.equals(attr.getNodeValue())) {
								return child1;
							}
						}
					}
				}
			}

			if (child instanceof Element elem) {
                final NamedNodeMap attributes2 = elem.getAttributes();
				if (attributes2 != null) {
					for (final Node nodeAttr : Nodes.iterable(attributes2)) {
						final Attr attr = (Attr) nodeAttr;
						if ("id".equals(attr.getNodeName()) && elementId.equals(attr.getNodeValue())) {
							return child;
						}
					}
				}
			}
		}
		return null;
	}


	/**
	 * <p>drawStyle.</p>
	 *
	 * @param node a {@link org.loboevolution.html.node.Node} object.
	 */
	protected void drawStyle(final Node node) {
		final CSSStyleDeclaration style = getStyle();
		final SVGElement child = ( SVGElement) node;

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