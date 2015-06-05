/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.domimpl;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

import org.lobobrowser.html.style.CSSValuesProperties;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.html.w3c.CanvasGradient;
import org.lobobrowser.html.w3c.CanvasImageData;
import org.lobobrowser.html.w3c.CanvasPattern;
import org.lobobrowser.html.w3c.CanvasRenderingContext2D;
import org.lobobrowser.html.w3c.HTMLCanvasElement;
import org.lobobrowser.html.w3c.HTMLImageElement;
import org.lobobrowser.html.w3c.HTMLVideoElement;
import org.lobobrowser.html.w3c.TextMetrics;
import org.lobobrowser.util.gui.ColorFactory;
import org.lobobrowser.util.gui.FontFactory;

public class DOMCanvasImpl implements CanvasRenderingContext2D, CanvasGradient,
		CanvasPattern {

	/** The canvas. */
	private HTMLCanvasElementImpl canvas;

	/** The list rect values. */
	private ArrayList<int[]> listRectValues;

	/** The list stroke rect values. */
	private ArrayList<int[]> listStrokeRectValues;

	/** The list text values. */
	private ArrayList<Object[]> listTextValues;
	
	/** The list stroke text values. */
	private ArrayList<Object[]> listStrokeTextValues;

	/** The rect values. */
	private int[] rectValues;

	/** The fractions. */
	private float[] fractions;
	
	/** The colors. */
	private Color[] colors;
	
	/** The General Path. */
	private GeneralPath path;

	/**
	 * Instantiates a new dom canvas impl.
	 *
	 * @param canvas
	 *            the canvas
	 */
	public DOMCanvasImpl(HTMLCanvasElementImpl canvas) {
		this.canvas = canvas;
		if (listRectValues == null) {
			listRectValues = new ArrayList<int[]>();
		}

		if (listStrokeRectValues == null) {
			listStrokeRectValues = new ArrayList<int[]>();
		}

		if (listTextValues == null) {
			listTextValues = new ArrayList<Object[]>();
		}
		
		if (listStrokeTextValues == null) {
			listStrokeTextValues = new ArrayList<Object[]>();
		}
		
		if (path == null) {
			path = new GeneralPath();
		}

		if (fractions == null) {
			fractions = new float[3];
		}

		if (colors == null) {
			colors = new Color[3];
		}
	}

	@Override
	public HTMLCanvasElement getCanvas() {
		return canvas;
	}

	@Override
	public Object getFillStyle() {
		return canvas.getColor();
	}

	@Override
	public void setFillStyle(Object arg) {
		canvas.setColor(ColorFactory.getInstance().getColor(arg.toString()));

	}

	@Override
	public Font getFont() {
		return canvas.getFont();
	}

	@Override
	public void setFont(String font) {
		canvas.setFont(getFontValue(font));
	}

	@Override
	public Double getGlobalAlpha() {
		return canvas.getGlobalAlpha();
	}

	@Override
	public void setGlobalAlpha(Double arg) {
		canvas.setGlobalAlpha(arg);

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLineCap(String arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getLineJoin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLineJoin(String arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLineWidth() {
		return canvas.getLineWidth();
	}

	@Override
	public void setLineWidth(int lineWidth) {
		canvas.setLineWidth(lineWidth);

	}

	@Override
	public int getMiterLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMiterLimit(int arg) {
		// TODO Auto-generated method stub

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
	public Object getStrokeStyle() {
		return canvas.getColor();
	}

	@Override
	public void setStrokeStyle(Object arg) {
		if (arg instanceof CanvasGradient) {
			canvas.setFractions(fractions);
			canvas.setColors(colors);
		} else {
			canvas.setColor(ColorFactory.getInstance().getColor(arg.toString()));
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
	public CanvasGradient createLinearGradient(Object x0, Object y0, Object x1,
			Object y1) {
		Double[] val = { new Double(x0.toString()), new Double(y0.toString()),
				new Double(x1.toString()), new Double(y1.toString()) };
		canvas.setLinearValues(val);
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
		// TODO Auto-generated method stub

	}

	@Override
	public void drawImage(HTMLImageElement image, int x, int y, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawImage(HTMLImageElement image, int sx, int sy, int sw,
			int sh, int dx, int dy, int dw, int dh) {
		// TODO Auto-generated method stub

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
		if (HTMLCanvasElement.FILL_RECT == canvas.getMethod()) {
			fillRect(rectValues[0], rectValues[1], rectValues[2], rectValues[3]);
		} else {
			canvas.setMethod(HTMLCanvasElement.FILL);
			canvas.setPath(path);
		}
	}

	@Override
	public void fillRect(int x, int y, int width, int height) {

		int[] intArray = new int[4];
		intArray[0] = x;
		intArray[1] = y;
		intArray[2] = width;
		intArray[3] = height;

		listRectValues.add(intArray);
		canvas.setListRectValues(listRectValues);
		canvas.setColor(Color.BLACK);
		canvas.setMethod(HTMLCanvasElement.FILL_RECT);
	}

	@Override
	public void fillText(String text, int x, int y) {
		fillText(text, x, y, 0);
	}

	@Override
	public void fillText(String text, int x, int y, int maxWidth) {
		Object[] objArray = new Object[4];
		objArray[0] = text;
		objArray[1] = x;
		objArray[2] = y;
		objArray[3] = maxWidth;

		listTextValues.add(objArray);
		canvas.setListTextValues(listTextValues);
		canvas.setColor(Color.BLACK);
		canvas.setMethod(HTMLCanvasElement.FILL_TEXT);
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
	}

	@Override
	public void restore() {
		// TODO Auto-generated method stub

	}

	@Override
	public void rotate(double angle) {
		canvas.setRotate(angle);

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void scale(int sx, int sy) {
		canvas.setScaleX(sx);
		canvas.setScaleY(sy);
	}

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub

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
		if (HTMLCanvasElement.STROKE_RECT == canvas.getMethod()) {
			strokeRect(rectValues[0], rectValues[1], rectValues[2],
					rectValues[3], canvas.getLineWidth());
		} else {
			canvas.setMethod(HTMLCanvasElement.STROKE);
			canvas.setPath(path);
		}
	}

	@Override
	public void strokeRect(int x, int y, int width, int height) {
		strokeRect(x, y, width, height, canvas.getLineWidth());

	}

	@Override
	public void strokeRect(int x, int y, int width, int height, int lineWidth) {
		int[] intArray = new int[5];
		intArray[0] = x;
		intArray[1] = y;
		intArray[2] = width;
		intArray[3] = height;
		intArray[4] = lineWidth;

		listStrokeRectValues.add(intArray);
		canvas.setListStrokeRectValues(listStrokeRectValues);
		canvas.setColor(Color.BLACK);
		canvas.setMethod(HTMLCanvasElement.STROKE_RECT);

	}

	@Override
	public void strokeText(String text, int x, int y) {
		strokeText(text, x, y, 0);

	}

	@Override
	public void strokeText(String text, int x, int y, int maxWidth) {
		Object[] objArray = new Object[4];
		objArray[0] = text;
		objArray[1] = x;
		objArray[2] = y;
		objArray[3] = maxWidth;

		listStrokeTextValues.add(objArray);
		canvas.setListStrokeTextValues(listStrokeTextValues);
		canvas.setColor(Color.BLACK);
		canvas.setMethod(HTMLCanvasElement.STROKE_TEXT);

	}

	@Override
	public void transform(int m11, int m12, int m21, int m22, int dx, int dy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void translate(int tx, int ty) {
		canvas.setTranslateX(tx);
		canvas.setTranslateY(ty);
	}

	@Override
	public void addColorStop(String offset, String color) {

		for (int i = 0; i < fractions.length; i++) {
			if (fractions[i] == 0) {
				fractions[i] = new Float(offset);
				break;
			}
		}

		for (int i = 0; i < colors.length; i++) {
			if (colors[i] == null) {
				colors[i] = ColorFactory.getInstance().getColor(color);
				break;
			}
		}
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

}
