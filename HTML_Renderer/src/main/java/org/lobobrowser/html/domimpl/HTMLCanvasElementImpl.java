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

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

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
import org.lobobrowser.html.w3c.HTMLVideoElement;
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

	/** The Constant DEFAULT_FONT. */
	private static final Font DEFAULT_FONT = FONT_FACTORY.getFont(
			Font.SANS_SERIF, null, null, null, HtmlValues.DEFAULT_FONT_SIZE,
			null, null);

	/** The list rect values. */
	private ArrayList<CanvasInfo> listRectValues;

	/** The list stroke rect values. */
	private ArrayList<CanvasInfo> listStrokeRectValues;

	/** The list text values. */
	private ArrayList<CanvasInfo> listTextValues;

	/** The list stroke text values. */
	private ArrayList<CanvasInfo> listStrokeTextValues;

	/** The linear values. */
	private CanvasInfo linearValues;
	
	/** The linear values. */
	private CanvasInfo canvasInfo;

	/** The fractions. */
	private ArrayList<Float> fractions;

	/** The colors. */
	private ArrayList<Color> colors;

	/** The path. */
	private GeneralPath path;

	/** The color. */
	private Color color;

	/** The color. */
	private Font font;

	/** The global alpha. */
	private Double globalAlpha;

	/** The rotate. */
	private double rotate;

	/** The Scale x. */
	private int scaleX;

	/** The Scale Y. */
	private int scaleY;

	/** The Translate x. */
	private int translateX;

	/** The Translate Y. */
	private int translateY;

	/** The rect values. */
	private int[] rectValues;

	/**
	 * Instantiates a new HTML canvas element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLCanvasElementImpl(String name) {
		super(name);
		globalAlpha = 1.0;
		rotate = 0.0;
		scaleX = 1;
		scaleY = 1;
		translateX = 0;
		translateY = 0;
		color = Color.BLACK;
		font = DEFAULT_FONT;

		if (listRectValues == null) {
			listRectValues = new ArrayList<CanvasInfo>();
		}

		if (listStrokeRectValues == null) {
			listStrokeRectValues = new ArrayList<CanvasInfo>();
		}

		if (listTextValues == null) {
			listTextValues = new ArrayList<CanvasInfo>();
		}

		if (listStrokeTextValues == null) {
			listStrokeTextValues = new ArrayList<CanvasInfo>();
		}
		
		if (linearValues == null) {
			linearValues = new CanvasInfo();
		}
		
		if (canvasInfo == null) {
			canvasInfo = new CanvasInfo();
		}

		if (path == null) {
			path = new GeneralPath();
		}

		if (fractions == null) {
			fractions = new ArrayList<Float>();
		}

		if (colors == null) {
			colors = new ArrayList<Color>();
		}
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
	public Color getFillStyle() {
		return color;
	}

	@Override
	public void setFillStyle(Object col) {
		if (col instanceof CanvasGradient) {
			setFractions(fractions);
			setColors(colors);
		} else {
			color = ColorFactory.getInstance().getColor(col.toString());
		}
	}

	@Override
	public Font getFont() {
		return this.font;
	}

	@Override
	public void setFont(String font) {
		this.font = getFontValue(font);
	}

	@Override
	public Double getGlobalAlpha() {
		return globalAlpha;
	}

	@Override
	public void setGlobalAlpha(Double globalAlpha) {
		this.globalAlpha = globalAlpha;
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
		return canvasInfo.getLineCap();
	}

	@Override
	public void setLineCap(String lineCap) {
		canvasInfo.setLineCap(lineCap);
	}

	@Override
	public String getLineJoin() {
		return canvasInfo.getLineJoin();
	}

	@Override
	public void setLineJoin(String lineJoin) {
		canvasInfo.setLineJoin(lineJoin);

	}

	@Override
	public int getMiterLimit() {
		return canvasInfo.getMiterLimit();
	}

	@Override
	public void setMiterLimit(int miterLimit) {
		canvasInfo.setMiterLimit(miterLimit);
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
		return color;
	}

	@Override
	public void setStrokeStyle(Object col) {
		if (col instanceof CanvasGradient) {
			setFractions(fractions);
			setColors(colors);
		} else {
			color = ColorFactory.getInstance().getColor(col.toString());
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
		// TODO Auto-generated method stub

	}

	@Override
	public void bezierCurveTo(int cp1x, int cp1y, int cp2x, int cp2y, int x,
			int y) {
		path.curveTo(cp1x, cp1y, cp2x, cp2y, x, y);
	}

	@Override
	public void clearRect(int x, int y, int width, int height) {
		// TODO Auto-generated method stub

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
	public CanvasGradient createLinearGradient(Object x0, Object y0, Object x1, Object y1) {
		CanvasInfo ci = new CanvasInfo();
		ci.setLinearX(new Double(x0.toString()));
		ci.setLinearX1(new Double(x1.toString()));
		ci.setLinearY(new Double(y0.toString()));
		ci.setLinearY1(new Double(y1.toString()));
		linearValues = ci;
		return this;
	}

	@Override
	public CanvasPattern createPattern(HTMLCanvasElement canvas,
			String repetitionType) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public CanvasPattern createPattern(HTMLImageElement image,
			String repetitionType) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public CanvasGradient createRadialGradient(int x0, int y0, int r0, int x1,
			int y1, int r1) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void drawImage(HTMLImageElement image, int x, int y) {
		canvasInfo.setImage(image);
		canvasInfo.setX(x);
		canvasInfo.setY(y);
		canvasInfo.setWidth(getWidth());
		canvasInfo.setHeight(getHeight());
		canvasInfo.setMethod(IMAGE);
	}

	@Override
	public void drawImage(HTMLImageElement image, int x, int y, int width,
			int height) {
		canvasInfo.setImage(image);
		canvasInfo.setX(x);
		canvasInfo.setY(y);
		canvasInfo.setWidth(width);
		canvasInfo.setHeight(height);
		canvasInfo.setMethod(IMAGE);
	}

	@Override
	public void drawImage(HTMLImageElement image, int sx, int sy, int sw,
			int sh, int dx, int dy, int dw, int dh) {
		canvasInfo.setImage(image);
		canvasInfo.setSx(sx);
		canvasInfo.setSy(sy);
		canvasInfo.setSw(sw);
		canvasInfo.setSh(sh);
		canvasInfo.setDx(dx);
		canvasInfo.setDy(dy);
		canvasInfo.setDw(dw);
		canvasInfo.setDh(dh);
		canvasInfo.setMethod(IMAGE_CLIP);

	}

	@Override
	public void drawImage(HTMLCanvasElement canvas, int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawImage(HTMLCanvasElement canvas, int x, int y, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawImage(HTMLCanvasElement canvas, int sx, int sy, int sw,
			int sh, int dx, int dy, int dw, int dh) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawImage(HTMLVideoElement video, int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawImage(HTMLVideoElement video, int x, int y, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawImage(HTMLVideoElement video, int sx, int sy, int sw,
			int sh, int dx, int dy, int dw, int dh) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fill() {
		if (RECT == canvasInfo.getMethod()) {
			fillRect(rectValues[0], rectValues[1], rectValues[2], rectValues[3]);
		} else {
			canvasInfo.setMethod(FILL);
			canvasInfo.setFillStyle(color);
			setPath(path);
		}
	}

	@Override
	public void fillRect(int x, int y, int width, int height) {
		
		CanvasInfo ci = new CanvasInfo();
		ci.setX(x);
		ci.setY(y);
		ci.setWidth(width);
		ci.setHeight(height);
		ci.setFillStyle(getFillStyle());
		ci.setRotate(rotate);
		ci.setScaleX(scaleX);
		ci.setScaleY(scaleY);
		ci.setTranslateX(translateX);
		ci.setTranslateY(translateY);
		ci.setGlobalAlpha(globalAlpha);
		listRectValues.add(ci);
		canvasInfo.setMethod(FILL_RECT);
	}

	@Override
	public void fillText(String text, int x, int y) {
		fillText(text, x, y, 0);
	}

	@Override
	public void fillText(String text, int x, int y, int maxWidth) {
		CanvasInfo ci = new CanvasInfo();
		ci.setText(text);
		ci.setX(x);
		ci.setY(y);
		ci.setMaxWidth(maxWidth);
		ci.setFillStyle(getFillStyle());
		ci.setRotate(rotate);
		ci.setScaleX(scaleX);
		ci.setScaleY(scaleY);
		ci.setTranslateX(translateX);
		ci.setTranslateY(translateY);
		ci.setFont(font);

		listTextValues.add(ci);
		canvasInfo.setMethod(FILL_TEXT);
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
		return new DOMTextMetricsImpl(text);
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
		rectValues = new int[4];
		rectValues[0] = x;
		rectValues[1] = y;
		rectValues[2] = width;
		rectValues[3] = height;
		canvasInfo.setMethod(RECT);
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
		if (RECT == canvasInfo.getMethod()) {
			strokeRect(rectValues[0], rectValues[1], rectValues[2],
					rectValues[3], getLineWidth());
		} else {
			canvasInfo.setMethod(STROKE);
			canvasInfo.setStrokeStyle(color);
			setPath(path);
		}
	}

	@Override
	public void strokeRect(int x, int y, int width, int height) {
		strokeRect(x, y, width, height, getLineWidth());

	}

	@Override
	public void strokeRect(int x, int y, int width, int height, int lineWidth) {
		
		CanvasInfo ci = new CanvasInfo();
		ci.setX(x);
		ci.setY(y);
		ci.setWidth(width);
		ci.setHeight(height);
		ci.setLineWidth(lineWidth);
		ci.setStrokeStyle(getStrokeStyle());
		ci.setRotate(rotate);
		ci.setScaleX(scaleX);
		ci.setScaleY(scaleY);
		ci.setTranslateX(translateX);
		ci.setTranslateY(translateY);
		ci.setGlobalAlpha(globalAlpha);

		listStrokeRectValues.add(ci);
		canvasInfo.setMethod(STROKE_RECT);

	}

	@Override
	public void strokeText(String text, int x, int y) {
		strokeText(text, x, y, 0);

	}

	@Override
	public void strokeText(String text, int x, int y, int maxWidth) {
		CanvasInfo ci = new CanvasInfo();
		ci.setText(text);
		ci.setX(x);
		ci.setY(y);
		ci.setMaxWidth(maxWidth);
		ci.setStrokeStyle(getStrokeStyle());
		ci.setRotate(rotate);
		ci.setScaleX(scaleX);
		ci.setScaleY(scaleY);
		ci.setTranslateX(translateX);
		ci.setTranslateY(translateY);
		ci.setFont(font);

		listStrokeTextValues.add(ci);
		canvasInfo.setMethod(STROKE_TEXT);

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

	@Override
	public void addColorStop(String offset, String color) {
		fractions.add(new Float(offset));
		colors.add(ColorFactory.getInstance().getColor(color));
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

	/**
	 * @return the listRectValues
	 */
	public ArrayList<CanvasInfo> getListRectValues() {
		return listRectValues;
	}

	/**
	 * @param listRectValues
	 *            the listRectValues to set
	 */
	public void setListRectValues(ArrayList<CanvasInfo> listRectValues) {
		this.listRectValues = listRectValues;
	}

	/**
	 * @return the listStrokeRectValues
	 */
	public ArrayList<CanvasInfo> getListStrokeRectValues() {
		return listStrokeRectValues;
	}

	/**
	 * @param listStrokeRectValues
	 *            the listStrokeRectValues to set
	 */
	public void setListStrokeRectValues(ArrayList<CanvasInfo> listStrokeRectValues) {
		this.listStrokeRectValues = listStrokeRectValues;
	}

	/**
	 * @return the listTextValues
	 */
	public ArrayList<CanvasInfo> getListTextValues() {
		return listTextValues;
	}

	/**
	 * @param listTextValues
	 *            the listTextValues to set
	 */
	public void setListTextValues(ArrayList<CanvasInfo> listTextValues) {
		this.listTextValues = listTextValues;
	}

	/**
	 * @return the listStrokeTextValues
	 */
	public ArrayList<CanvasInfo> getListStrokeTextValues() {
		return listStrokeTextValues;
	}

	/**
	 * @param listStrokeTextValues
	 *            the listStrokeTextValues to set
	 */
	public void setListStrokeTextValues(ArrayList<CanvasInfo> listStrokeTextValues) {
		this.listStrokeTextValues = listStrokeTextValues;
	}

	/**
	 * @return the linearValues
	 */
	public CanvasInfo getLinearValues() {
		return linearValues;
	}

	/**
	 * @param linearValues
	 *            the linearValues to set
	 */
	public void setLinearValues(CanvasInfo linearValues) {
		this.linearValues = linearValues;
	}

	/**
	 * @return the canvasInfo
	 */
	public CanvasInfo getCanvasInfo() {
		return canvasInfo;
	}

	/**
	 * @param canvasInfo the canvasInfo to set
	 */
	public void setCanvasInfo(CanvasInfo canvasInfo) {
		this.canvasInfo = canvasInfo;
	}

	/**
	 * @return the fractions
	 */
	public ArrayList<Float> getFractions() {
		return fractions;
	}

	/**
	 * @param fractions
	 *            the fractions to set
	 */
	public void setFractions(ArrayList<Float> fractions) {
		this.fractions = fractions;
	}

	/**
	 * @return the colors
	 */
	public ArrayList<Color> getColors() {
		return colors;
	}

	/**
	 * @param colors
	 *            the colors to set
	 */
	public void setColors(ArrayList<Color> colors) {
		this.colors = colors;
	}

	/**
	 * @return the path
	 */
	public GeneralPath getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(GeneralPath path) {
		this.path = path;
	}
	
	/**
	 * @return the lineWidth
	 */
	public int getLineWidth() {
		return canvasInfo.getLineWidth();
	}

	/**
	 * @param lineWidth
	 *            the lineWidth to set
	 */
	public void setLineWidth(int lineWidth) {
		canvasInfo.setLineWidth(lineWidth);
	}
}
