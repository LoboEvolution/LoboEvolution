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
<<<<<<< .mine
import org.lobobrowser.html.w3c.HTMLImageElement;
import org.lobobrowser.html.w3c.TextMetrics;
import org.lobobrowser.util.gui.ColorFactory;
import org.lobobrowser.util.gui.FontFactory;
=======
import org.lobobrowser.html.w3c.HTMLImageElement;
import org.lobobrowser.html.w3c.HTMLVideoElement;
import org.lobobrowser.html.w3c.TextMetrics;
import org.lobobrowser.util.gui.ColorFactory;
import org.lobobrowser.util.gui.FontFactory;
>>>>>>> .r311

/**
 * The Class HTMLCanvasElementImpl.
 */
public class HTMLCanvasElementImpl extends HTMLAbstractUIElement implements
<<<<<<< .mine
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
=======
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
>>>>>>> .r311
	private int scaleX;
<<<<<<< .mine
=======

	/** The Scale Y. */
>>>>>>> .r311
	private int scaleY;
<<<<<<< .mine
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
	
=======

>>>>>>> .r311
<<<<<<< .mine
=======
	/** The Translate x. */
	private int translateX;

	/** The Translate Y. */
	private int translateY;

>>>>>>> .r311
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
<<<<<<< .mine
		listCanvasInfo = new ArrayList<CanvasInfo>();
		lineWidth = 1;
=======
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
>>>>>>> .r311
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

<<<<<<< .mine
	@Override
	public HTMLCanvasElement getCanvas() {
		return this;
=======
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
>>>>>>> .r311
	}

<<<<<<< .mine
	@Override
	public void addColorStop(String offset, String color) {
		fractions.add(new Float(offset));
		colors.add(ColorFactory.getInstance().getColor(color));	
=======
	/**
	 * @param listRectValues
	 *            the listRectValues to set
	 */
	public void setListRectValues(ArrayList<CanvasInfo> listRectValues) {
		this.listRectValues = listRectValues;
>>>>>>> .r311
	}

<<<<<<< .mine
	@Override
	public Color getFillStyle() {
		return fillStyle;
=======
	/**
	 * @return the listStrokeRectValues
	 */
	public ArrayList<CanvasInfo> getListStrokeRectValues() {
		return listStrokeRectValues;
>>>>>>> .r311
	}

<<<<<<< .mine
	@Override
	public void setFillStyle(Object style) {
		if (style instanceof CanvasGradient) {
			
			linearGradient = linearGradient();
		} else {
			fillStyle = ColorFactory.getInstance().getColor(style.toString());
		}
=======
	/**
	 * @param listStrokeRectValues
	 *            the listStrokeRectValues to set
	 */
	public void setListStrokeRectValues(ArrayList<CanvasInfo> listStrokeRectValues) {
		this.listStrokeRectValues = listStrokeRectValues;
>>>>>>> .r311
	}

<<<<<<< .mine
	@Override
	public Font getFont() {
		return font;
=======
	/**
	 * @return the listTextValues
	 */
	public ArrayList<CanvasInfo> getListTextValues() {
		return listTextValues;
>>>>>>> .r311
	}

<<<<<<< .mine
	@Override
	public void setFont(String sfont) {
		font = getFontValue(sfont);
		
=======
	/**
	 * @param listTextValues
	 *            the listTextValues to set
	 */
	public void setListTextValues(ArrayList<CanvasInfo> listTextValues) {
		this.listTextValues = listTextValues;
>>>>>>> .r311
	}

<<<<<<< .mine
	@Override
	public Double getGlobalAlpha() {
		return globalAlpha.doubleValue();
=======
	/**
	 * @return the listStrokeTextValues
	 */
	public ArrayList<CanvasInfo> getListStrokeTextValues() {
		return listStrokeTextValues;
>>>>>>> .r311
	}

<<<<<<< .mine
	@Override
	public void setGlobalAlpha(Double ga) {
		globalAlpha = ga.floatValue();
		
=======
	/**
	 * @param listStrokeTextValues
	 *            the listStrokeTextValues to set
	 */
	public void setListStrokeTextValues(ArrayList<CanvasInfo> listStrokeTextValues) {
		this.listStrokeTextValues = listStrokeTextValues;
>>>>>>> .r311
	}

<<<<<<< .mine
	@Override
	public String getGlobalCompositeOperation() {
		// TODO Auto-generated method stub
		return null;
=======
	/**
	 * @return the linearValues
	 */
	public CanvasInfo getLinearValues() {
		return linearValues;
>>>>>>> .r311
	}

<<<<<<< .mine
	@Override
	public void setGlobalCompositeOperation(String arg) {
		// TODO Auto-generated method stub
		
=======
	/**
	 * @param linearValues
	 *            the linearValues to set
	 */
	public void setLinearValues(CanvasInfo linearValues) {
		this.linearValues = linearValues;
>>>>>>> .r311
	}

<<<<<<< .mine
	@Override
	public String getLineCap() {
		if (intLineCap == BasicStroke.CAP_ROUND) {
			return "round";

		} else if (intLineCap == BasicStroke.CAP_SQUARE) {
			return "square";
		} else {
			return "";
		}
=======
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
>>>>>>> .r311
	}

<<<<<<< .mine
	@Override
	public void setLineCap(String lineCap) {
		if ("round".equals(lineCap)) {
			intLineCap = BasicStroke.CAP_ROUND;

		} else if ("square".equals(lineCap)) {
			intLineCap = BasicStroke.CAP_SQUARE;
		}
=======
	/**
	 * @param fractions
	 *            the fractions to set
	 */
	public void setFractions(ArrayList<Float> fractions) {
		this.fractions = fractions;
>>>>>>> .r311
	}

<<<<<<< .mine
	@Override
	public String getLineJoin() {
		if (intlineJoin == BasicStroke.CAP_ROUND) {
			return "round";

		} else if (intlineJoin == BasicStroke.JOIN_MITER) {
			return "miter";
		} else {
			return "";
		}
=======
	/**
	 * @return the colors
	 */
	public ArrayList<Color> getColors() {
		return colors;
>>>>>>> .r311
	}

<<<<<<< .mine
	@Override
	public void setLineJoin(String lineJoin) {

		if ("round".equals(lineJoin)) {
			intlineJoin = BasicStroke.JOIN_ROUND;

		} else if ("miter".equals(lineJoin)) {
			intlineJoin = BasicStroke.JOIN_MITER;
		}

=======
	/**
	 * @param colors
	 *            the colors to set
	 */
	public void setColors(ArrayList<Color> colors) {
		this.colors = colors;
>>>>>>> .r311
	}

	@Override
	public int getLineWidth() {
		return lineWidth;
	}

	@Override
	public void setLineWidth(int lw) {
		lineWidth = lw;
	}
	
<<<<<<< .mine
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
=======
	/**
	 * @return the lineWidth
	 */
	public int getLineWidth() {
		return canvasInfo.getLineWidth();
>>>>>>> .r311
	}

<<<<<<< .mine
	@Override
	public void setShadowOffsetY(int arg) {
		// TODO Auto-generated method stub
		
=======
	/**
	 * @param lineWidth
	 *            the lineWidth to set
	 */
	public void setLineWidth(int lineWidth) {
		canvasInfo.setLineWidth(lineWidth);
>>>>>>> .r311
	}
<<<<<<< .mine

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
	public void bezierCurveTo(int cp1x, int cp1y, int cp2x, int cp2y, int x, int y) {
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
	public CanvasGradient createLinearGradient(Object x0, Object y0, Object x1, Object y1) {

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
		if(rectWidth > 0 || rectHeight > 0){
			fillRect(rectX, rectY, rectWidth, rectHeight);
		}else{
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
		fillrect.setGlobalAlpha(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, globalAlpha));
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
		if(rectWidth > 0 || rectHeight > 0){
			strokeRect(rectX, rectY, rectWidth, rectHeight, 0);
			
		}else{
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
		strokeRect.setGlobalAlpha(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, globalAlpha));
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
	 * @param listCanvasInfo the listCanvasInfo to set
	 */
	public void setListCanvasInfo(ArrayList<CanvasInfo> listCanvasInfo) {
		this.listCanvasInfo = listCanvasInfo;
	}
=======
>>>>>>> .r311
}
