/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.domimpl;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.LinearGradientPaint;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Arrays;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.info.CanvasInfo;
import org.lobobrowser.html.style.CSSValuesProperties;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.html.w3c.CanvasGradient;
import org.lobobrowser.html.w3c.CanvasImageData;
import org.lobobrowser.html.w3c.CanvasPattern;
import org.lobobrowser.html.w3c.CanvasRenderingContext;
import org.lobobrowser.html.w3c.CanvasRenderingContext2D;
import org.lobobrowser.html.w3c.HTMLCanvasElement;
import org.lobobrowser.html.w3c.HTMLImageElement;
import org.lobobrowser.html.w3c.TextMetrics;
import org.lobobrowser.util.gui.ColorFactory;
import org.lobobrowser.util.gui.FontFactory;

/**
 * The Class HTMLCanvasElementImpl.
 */
public class HTMLCanvasElementImpl extends HTMLAbstractUIElement implements
		HTMLCanvasElement, CanvasRenderingContext2D, CanvasGradient,
		CanvasPattern {

	/** The Constant FONT_FACTORY. */
	private static final FontFactory FONT_FACTORY = FontFactory.getInstance();

	private ArrayList<CanvasInfo> listCanvasInfo;
	private Color fillStyle;
	private Color strokeStyle;
	private int lineWidth;
	private Float globalAlpha;
	private int translateX;
	private int translateY;
	private Double rotate;
	private int scaleX;
	private int scaleY;
	private Font font;
	private int rectX;
	private int rectY;
	private int rectWidth;
	private int rectHeight;
	private int intLineCap;
	private int intlineJoin;
	private int miterLimit;
	private GeneralPath path;
	private LinearGradientPaint linearGradient;
	private ArrayList<Float> fractions;
	private ArrayList<Color> colors;
	private Double linearX;
	private Double linearX1;
	private Double linearY;
	private Double linearY1;

	/**
	 * Instantiates a new HTML canvas element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLCanvasElementImpl(String name) {
		super(name);
		listCanvasInfo = new ArrayList<CanvasInfo>();
		lineWidth = 1;
		globalAlpha = 1.0f;
		translateX = 0;
		translateY = 0;
		rotate = 0.0;
		scaleX = 1;
		scaleY = 1;
		miterLimit = 1;
		intLineCap = BasicStroke.CAP_BUTT;
		intlineJoin = BasicStroke.JOIN_BEVEL;
		font = FONT_FACTORY.getFont(Font.SANS_SERIF, null, null, null,
				HtmlValues.DEFAULT_FONT_SIZE, null, null);
	}

	@Override
	public int getWidth() {
		String widthText = this.getAttribute(HtmlAttributeProperties.WIDTH);
		if (widthText == null) {
			return 0;
		}
		try {
			return Integer.parseInt(widthText);
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}

	@Override
	public void setWidth(int width) {
		this.setAttribute(HtmlAttributeProperties.WIDTH, String.valueOf(width));
	}

	@Override
	public int getHeight() {
		String heightText = this.getAttribute(HtmlAttributeProperties.HEIGHT);
		if (heightText == null) {
			return 0;
		}
		try {
			return Integer.parseInt(heightText);
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}

	@Override
	public void setHeight(int height) {
		this.setAttribute(HtmlAttributeProperties.HEIGHT,
				String.valueOf(height));
	}

	@Override
	public String toDataURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toDataURL(String type, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CanvasRenderingContext getContext(String contextId) {
		return this;
	}

	@Override
	public HTMLCanvasElement getCanvas() {
		return this;
	}

	@Override
	public void addColorStop(String offset, String color) {
		fractions.add(new Float(offset));
		colors.add(ColorFactory.getInstance().getColor(color));
	}

	@Override
	public Color getFillStyle() {
		return fillStyle;
	}

	@Override
	public void setFillStyle(Object style) {
		if (style instanceof CanvasGradient) {
			linearGradient = linearGradient();
		} else {
			fillStyle = ColorFactory.getInstance().getColor(style.toString());
		}
	}

	@Override
	public Font getFont() {
		return font;
	}

	@Override
	public void setFont(String sfont) {
		font = getFontValue(sfont);

	}

	@Override
	public Double getGlobalAlpha() {
		return globalAlpha.doubleValue();
	}

	@Override
	public void setGlobalAlpha(Double ga) {
		globalAlpha = ga.floatValue();

	}

	@Override
	public String getGlobalCompositeOperation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGlobalCompositeOperation(String arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getLineCap() {
		if (intLineCap == BasicStroke.CAP_ROUND) {
			return "round";

		} else if (intLineCap == BasicStroke.CAP_SQUARE) {
			return "square";
		} else {
			return "";
		}
	}

	@Override
	public void setLineCap(String lineCap) {
		if ("round".equals(lineCap)) {
			intLineCap = BasicStroke.CAP_ROUND;

		} else if ("square".equals(lineCap)) {
			intLineCap = BasicStroke.CAP_SQUARE;
		}
	}

	@Override
	public String getLineJoin() {
		if (intlineJoin == BasicStroke.CAP_ROUND) {
			return "round";

		} else if (intlineJoin == BasicStroke.JOIN_MITER) {
			return "miter";
		} else {
			return "";
		}
	}

	@Override
	public void setLineJoin(String lineJoin) {

		if ("round".equals(lineJoin)) {
			intlineJoin = BasicStroke.JOIN_ROUND;

		} else if ("miter".equals(lineJoin)) {
			intlineJoin = BasicStroke.JOIN_MITER;
		}

	}

	@Override
	public int getLineWidth() {
		return lineWidth;
	}

	@Override
	public void setLineWidth(int lw) {
		lineWidth = lw;
	}

	@Override
	public int getMiterLimit() {
		return miterLimit;
	}

	@Override
	public void setMiterLimit(int ml) {
		miterLimit = ml;

	}

	@Override
	public int getShadowBlur() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setShadowBlur(int arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getShadowColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setShadowColor(String arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getShadowOffsetX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setShadowOffsetX(int arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getShadowOffsetY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setShadowOffsetY(int arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public Color getStrokeStyle() {
		return strokeStyle;
	}

	@Override
	public void setStrokeStyle(Object style) {
		if (style instanceof CanvasGradient) {
			linearGradient = linearGradient();
		} else {
			strokeStyle = ColorFactory.getInstance().getColor(style.toString());
		}
	}

	@Override
	public String getTextAlign() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTextAlign(String arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTextBaseline() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTextBaseline(String arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void arc(int x, int y, int radius, int startAngle, int endAngle,
			boolean anticlockwise) {
		// TODO Auto-generated method stub

	}

	@Override
	public void arcTo(int x1, int y1, int x2, int y2, int radius) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beginPath() {
		path = new GeneralPath();

	}

	@Override
	public void bezierCurveTo(int cp1x, int cp1y, int cp2x, int cp2y, int x,
			int y) {
		path.curveTo(cp1x, cp1y, cp2x, cp2y, x, y);
	}

	@Override
	public void clearRect(int x, int y, int width, int height) {
		CanvasInfo clearect = new CanvasInfo();
		clearect.setX(x);
		clearect.setY(y);
		clearect.setWidth(width);
		clearect.setHeight(height);
		clearect.setMethod(CLEAR_RECT);
		listCanvasInfo.add(clearect);

	}

	@Override
	public void clearShadow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clip() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closePath() {
		path.closePath();

	}

	@Override
	public CanvasGradient createLinearGradient(Object x0, Object y0, Object x1,
			Object y1) {

		fractions = new ArrayList<Float>();
		colors = new ArrayList<Color>();
		linearX = new Double(x0.toString());
		linearX1 = new Double(y0.toString());
		linearY = new Double(x1.toString());
		linearY1 = new Double(y1.toString());
		return this;
	}

	@Override
	public CanvasPattern createPattern(HTMLCanvasElement canvas,
			String repetitionType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CanvasPattern createPattern(HTMLImageElement image,
			String repetitionType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CanvasGradient createRadialGradient(int x0, int y0, int r0, int x1,
			int y1, int r1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void drawImage(Object image, Integer x, Integer y) {

		CanvasInfo cimage = new CanvasInfo();

		if (image instanceof HTMLImageElementImpl) {
			cimage.setImage((HTMLImageElementImpl) image);
			cimage.setX(x);
			cimage.setY(y);
			cimage.setWidth(getWidth());
			cimage.setHeight(getHeight());
			cimage.setMethod(IMAGE);
			listCanvasInfo.add(cimage);
		}
	}

	@Override
	public void drawImage(Object image, Integer x, Integer y, Integer width,
			Integer height) {
		CanvasInfo cimage = new CanvasInfo();
		if (image instanceof HTMLImageElementImpl) {
			cimage.setImage((HTMLImageElementImpl) image);
			cimage.setX(x);
			cimage.setY(y);
			cimage.setWidth(width);
			cimage.setHeight(height);
			cimage.setMethod(IMAGE);

		}
		listCanvasInfo.add(cimage);
	}

	@Override
	public void drawImage(Object image, Integer sx, Integer sy, Integer sw,
			Integer sh, Integer dx, Integer dy, Integer dw, Integer dh) {
		CanvasInfo cimage = new CanvasInfo();
		if (image instanceof HTMLImageElementImpl) {
			cimage.setImage((HTMLImageElementImpl) image);
			cimage.setSx(sx);
			cimage.setSy(sy);
			cimage.setSw(sw);
			cimage.setSh(sh);
			cimage.setDx(dx);
			cimage.setDy(dy);
			cimage.setDw(dw);
			cimage.setDh(dh);
			cimage.setMethod(IMAGE_CLIP);
		}
		listCanvasInfo.add(cimage);
	}

	@Override
	public void fill() {
		if (rectWidth > 0 || rectHeight > 0) {
			fillRect(rectX, rectY, rectWidth, rectHeight);
		} else {
			CanvasInfo fill = new CanvasInfo();
			fill.setMethod(FILL);
			fill.setPath(path);
			fill.setFillStyle(fillStyle);
			fill.setLineCap(getLineCap());
			fill.setLineJoin(getLineJoin());
			fill.setLineWidth(lineWidth > 1 ? lineWidth : lineWidth);
			listCanvasInfo.add(fill);
		}

	}

	@Override
	public void fillRect(int x, int y, int width, int height) {
		CanvasInfo fillrect = new CanvasInfo();
		fillrect.setX(x);
		fillrect.setY(y);
		fillrect.setWidth(width);
		fillrect.setHeight(height);
		fillrect.setFillStyle(fillStyle);
		fillrect.setGlobalAlpha(AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, globalAlpha));
		fillrect.setTranslateX(translateX);
		fillrect.setTranslateY(translateY);
		fillrect.setRotate(rotate);
		fillrect.setScaleX(scaleX);
		fillrect.setScaleY(scaleY);
		fillrect.setLinearGradient(linearGradient);
		fillrect.setMethod(FILL_RECT);
		listCanvasInfo.add(fillrect);
	}

	@Override
	public void fillText(String text, int x, int y) {
		fillText(text, x, y, 0);

	}

	@Override
	public void fillText(String text, int x, int y, int maxWidth) {
		CanvasInfo fillText = new CanvasInfo();
		fillText.setText(text);
		fillText.setX(x);
		fillText.setY(y);
		fillText.setFillStyle(fillStyle);
		fillText.setFont(font);
		fillText.setMethod(FILL_TEXT);
		fillText.setTranslateX(translateX);
		fillText.setTranslateY(translateY);
		fillText.setRotate(rotate);
		fillText.setScaleX(scaleX);
		fillText.setScaleY(scaleY);
		fillText.setMaxWidth(maxWidth);
		listCanvasInfo.add(fillText);

	}

	@Override
	public CanvasImageData getImageData(int sx, int sy, int sw, int sh) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPointInPath(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void lineTo(int x, int y) {
		path.lineTo(x, y);

	}

	@Override
	public TextMetrics measureText(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void moveTo(int x, int y) {
		path.moveTo(x, y);

	}

	@Override
	public void putImageData(CanvasImageData imagedata, int dx, int dy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putImageData(CanvasImageData imagedata, int dx, int dy,
			int dirtyX, int dirtyY, int dirtyWidth, int dirtyHeight) {
		// TODO Auto-generated method stub

	}

	@Override
	public void quadraticCurveTo(int cpx, int cpy, int x, int y) {
		path.quadTo(cpx, cpy, x, y);

	}

	@Override
	public void rect(int x, int y, int width, int height) {
		rectX = x;
		rectY = y;
		rectWidth = width;
		rectHeight = height;
	}

	@Override
	public void restore() {
		// TODO Auto-generated method stub

	}

	@Override
	public void rotate(double angle) {
		rotate = angle;

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void scale(int sx, int sy) {
		scaleX = sx;
		scaleY = sy;

	}

	@Override
	public void setCompositeOperation(String compositeOperation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTransform(int m11, int m12, int m21, int m22, int dx, int dy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stroke() {
		if (rectWidth > 0 || rectHeight > 0) {
			strokeRect(rectX, rectY, rectWidth, rectHeight, 0);

		} else {
			CanvasInfo stroke = new CanvasInfo();
			stroke.setMethod(STROKE);
			stroke.setPath(path);
			stroke.setStrokeStyle(strokeStyle);
			stroke.setLineCap(getLineCap());
			stroke.setLineJoin(getLineJoin());
			stroke.setLineWidth(lineWidth > 1 ? lineWidth : lineWidth);
			listCanvasInfo.add(stroke);
		}
	}

	@Override
	public void strokeRect(int x, int y, int width, int height) {
		strokeRect(x, y, width, height, lineWidth);

	}

	@Override
	public void strokeRect(int x, int y, int width, int height, int lineWidth) {
		CanvasInfo strokeRect = new CanvasInfo();
		strokeRect.setX(x);
		strokeRect.setY(y);
		strokeRect.setWidth(width);
		strokeRect.setHeight(height);
		strokeRect.setLineWidth(lineWidth > 1 ? lineWidth : this.lineWidth);
		strokeRect.setStrokeStyle(strokeStyle);
		strokeRect.setGlobalAlpha(AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, globalAlpha));
		strokeRect.setTranslateX(translateX);
		strokeRect.setTranslateY(translateY);
		strokeRect.setRotate(rotate);
		strokeRect.setScaleX(scaleX);
		strokeRect.setScaleY(scaleY);
		strokeRect.setMethod(STROKE_RECT);
		listCanvasInfo.add(strokeRect);
	}

	@Override
	public void strokeText(String text, int x, int y) {
		strokeText(text, x, y, 0);
	}

	@Override
	public void strokeText(String text, int x, int y, int maxWidth) {
		CanvasInfo strokeText = new CanvasInfo();
		strokeText.setText(text);
		strokeText.setX(x);
		strokeText.setY(y);
		strokeText.setStrokeStyle(strokeStyle);
		strokeText.setFont(font);
		strokeText.setMethod(STROKE_TEXT);
		strokeText.setTranslateX(translateX);
		strokeText.setTranslateY(translateY);
		strokeText.setRotate(rotate);
		strokeText.setScaleX(scaleX);
		strokeText.setScaleY(scaleY);
		strokeText.setMaxWidth(maxWidth);
		listCanvasInfo.add(strokeText);

	}

	@Override
	public void transform(int m11, int m12, int m21, int m22, int dx, int dy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void translate(int tx, int ty) {
		translateX = tx;
		translateY = ty;

	}

	/**
	 * Gets the font value.
	 *
	 * @param font
	 *            the font
	 * @return the font value
	 */
	private Font getFontValue(String font) {

		String[] arrFont = font.split(" ");

		float fontSize = 14.0f;
		String fontStyle = CSSValuesProperties.ITALIC;
		String fontVariant = CSSValuesProperties.SMALL_CAPS;
		String fontFamily = Font.SANS_SERIF;
		String fontWeight = CSSValuesProperties.BOLD;

		for (int i = 0; i < arrFont.length; i++) {
			String prop = arrFont[i];
			if (prop.contains("px") || prop.contains("pt")) {
				fontSize = HtmlValues.getFontSize(prop, null);
			}

			if (prop.contains(CSSValuesProperties.NORMAL)
					|| prop.contains(CSSValuesProperties.ITALIC)
					|| prop.contains(CSSValuesProperties.OBLIQUE)) {
				fontStyle = prop;
			}

			if (prop.contains(CSSValuesProperties.NORMAL)
					|| prop.contains(CSSValuesProperties.SMALL_CAPS)) {
				fontVariant = prop;
			}

			if (prop.contains(CSSValuesProperties.NORMAL)
					|| prop.contains(CSSValuesProperties.BOLD)
					|| prop.contains(CSSValuesProperties.BOLDER)
					|| prop.contains(CSSValuesProperties.LIGHTER)) {
				fontWeight = prop;
			}

			if (i == arrFont.length - 1) {
				fontFamily = prop;
			}
		}
		return FontFactory.getInstance().getFont(fontFamily, fontStyle,
				fontVariant, fontWeight, fontSize, null, null);

	}

	private LinearGradientPaint linearGradient() {

		float[] floatArray = new float[fractions.size()];
		int i = 0;

		for (Float f : fractions) {
			floatArray[i++] = (f != null ? f : Float.NaN);
		}

		Color[] colorArray = new Color[colors.size()];
		int a = 0;

		for (Color c : colors) {
			colorArray[a++] = c;
		}

		Arrays.sort(floatArray);

		return new LinearGradientPaint(linearX.floatValue(),
				linearX1.floatValue(), linearY.floatValue(),
				linearY1.floatValue(), floatArray, colorArray);
	}

	/**
	 * @return the listCanvasInfo
	 */
	public ArrayList<CanvasInfo> getListCanvasInfo() {
		return listCanvasInfo;
	}

	/**
	 * @param listCanvasInfo
	 *            the listCanvasInfo to set
	 */
	public void setListCanvasInfo(ArrayList<CanvasInfo> listCanvasInfo) {
		this.listCanvasInfo = listCanvasInfo;
	}
}
