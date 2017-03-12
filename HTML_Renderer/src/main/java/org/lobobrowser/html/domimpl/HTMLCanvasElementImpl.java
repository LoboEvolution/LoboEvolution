/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.domimpl;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Arrays;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.info.CanvasInfo;
import org.lobobrowser.html.style.CSSValuesProperties;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.util.gui.ColorFactory;
import org.lobobrowser.util.gui.FontFactory;
import org.lobobrowser.util.gui.LAFSettings;
import org.lobobrowser.w3c.file.FileCallback;
import org.lobobrowser.w3c.html.CanvasGradient;
import org.lobobrowser.w3c.html.CanvasImageData;
import org.lobobrowser.w3c.html.CanvasPattern;
import org.lobobrowser.w3c.html.CanvasRenderingContext;
import org.lobobrowser.w3c.html.CanvasRenderingContext2D;
import org.lobobrowser.w3c.html.HTMLCanvasElement;
import org.lobobrowser.w3c.html.HTMLImageElement;
import org.lobobrowser.w3c.html.TextMetrics;

/**
 * The Class HTMLCanvasElementImpl.
 */
public class HTMLCanvasElementImpl extends HTMLAbstractUIElement implements
		HTMLCanvasElement, CanvasRenderingContext2D, CanvasPattern {

	/** The Constant FONT_FACTORY. */
	private static final FontFactory FONT_FACTORY = FontFactory.getInstance();

	/** The list canvas info. */
	private ArrayList<CanvasInfo> listCanvasInfo;

	/** The fill style. */
	private Object fillStyle;

	/** The stroke style. */
	private Object strokeStyle;
	
	/** The fill paint. */
	private Paint fillPaint;

	/** The stroke paint. */
	private Paint strokePaint;

	/** The line width. */
	private int lineWidth;

	/** The global alpha. */
	private Float globalAlpha;

	/** The translate x. */
	private int translateX;

	/** The translate y. */
	private int translateY;

	/** The rotate. */
	private Double rotate;

	/** The scale x. */
	private int scaleX;

	/** The scale y. */
	private int scaleY;

	/** The font. */
	private Font font;

	/** The rect x. */
	private int rectX;

	/** The rect y. */
	private int rectY;

	/** The rect width. */
	private int rectWidth;

	/** The rect height. */
	private int rectHeight;

	/** The int line cap. */
	private int intLineCap;

	/** The intline join. */
	private int intlineJoin;

	/** The miter limit. */
	private int miterLimit;

	/** The path. */
	private GeneralPath path;

	/** The Affine Transform. */
	private AffineTransform affineTransform;
	
	/** The global Composite Operation. */
	private String globalCompositeOperation;
	
	/** The text align */
	private String textAlign;
	
	/** The baseline. */
	private String baseline;
	
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
		fillStyle = Color.BLACK;
		strokeStyle = Color.BLACK;
		fillPaint = Color.BLACK;
		strokePaint = Color.BLACK;
		affineTransform = new AffineTransform(1,0,0,1,0,0);
		font = FONT_FACTORY.getFont(Font.SANS_SERIF, null, null, null, LAFSettings.getInstance().getFontSize(), null, null,0,false,0);
		globalCompositeOperation = "source-over";
		textAlign = "left";
		baseline = "alphabetic";
	}

	@Override
	public int getWidth() {
		String widthText = this.getAttribute(HtmlAttributeProperties.WIDTH);
		return HtmlValues.getPixelSize(widthText, null, 1);
	}

	@Override
	public void setWidth(int width) {
		this.setAttribute(HtmlAttributeProperties.WIDTH, String.valueOf(width));
	}

	@Override
	public int getHeight() {
		String heightText = this.getAttribute(HtmlAttributeProperties.HEIGHT);
		return HtmlValues.getPixelSize(heightText, null, 1);
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
	public Object getFillStyle() {
		return fillStyle;
	}

	@Override
	public void setFillStyle(Object style) {
		this.fillStyle = style;
		if (style instanceof CanvasGradient) {
			DOMCanvasGradientImpl cgi = (DOMCanvasGradientImpl) style;
			fillPaint = linearGradient(cgi.getFractions(),
					cgi.getColors(), cgi.getLinearX(), cgi.getLinearX1(),
					cgi.getLinearY(), cgi.getLinearY1());
		} else if (style instanceof CanvasPattern) {
			fillPaint = ((DOMCanvasPatternImpl) style).getPaint();
		} else if (style instanceof String) {
			fillPaint = ColorFactory.getInstance().getColor(style.toString());
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
		setGlobalCompositeOperation(globalCompositeOperation);

	}

	@Override
	public String getGlobalCompositeOperation() {
		return globalCompositeOperation;
	}

	@Override
	public void setGlobalCompositeOperation(String op) {
		globalCompositeOperation = op;

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
	public Object getStrokeStyle() {
		return strokeStyle;
	}

	@Override
	public void setStrokeStyle(Object style) {
		this.strokeStyle = style;
		if (style instanceof CanvasGradient) {
			DOMCanvasGradientImpl cgi = (DOMCanvasGradientImpl) style;
			strokePaint = linearGradient(cgi.getFractions(),
					cgi.getColors(), cgi.getLinearX(), cgi.getLinearX1(),
					cgi.getLinearY(), cgi.getLinearY1());
		} else if (style instanceof CanvasPattern) {
			strokePaint = ((DOMCanvasPatternImpl) style).getPaint();
		} else if (style instanceof String) {
			strokePaint = ColorFactory.getInstance().getColor(style.toString());
		}
	}

	@Override
	public String getTextAlign() {
		return textAlign;
	}

	@Override
	public void setTextAlign(String textAlign) {
		this.textAlign = textAlign;
	}

	@Override
	public String getTextBaseline() {
		return baseline;
	}

	@Override
	public void setTextBaseline(String bs) {
		baseline = bs;

	}

	@Override
	public void arc(int x, int y, int radius, int startAngle, int endAngle, boolean anticlockwise) {
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
		return new DOMCanvasGradientImpl(x0, y0, x1, y1);
	}

	@Override
	public CanvasPattern createPattern(HTMLCanvasElement canvas, String repetitionType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CanvasPattern createPattern(HTMLImageElement image, String repetitionType) {
		// TODO Auto-generated method stub
				return null;
	}
	
	@Override
	public CanvasGradient createRadialGradient(Object x0, Object y0, Object r0, Object x1, Object y1, Object r1) {
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
	public void drawImage(Object image, Integer x, Integer y, Integer width, Integer height) {
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
	public void drawImage(Object image, Integer sx, Integer sy, Integer sw, Integer sh, Integer dx, Integer dy, Integer dw, Integer dh) {
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
			fill.setFillPaint(fillPaint);
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
		fillrect.setFillPaint(fillPaint);
		fillrect.setGlobalAlpha(comosite(globalCompositeOperation));
		fillrect.setTranslateX(translateX);
		fillrect.setTranslateY(translateY);
		fillrect.setRotate(rotate);
		fillrect.setScaleX(scaleX);
		fillrect.setScaleY(scaleY);
		fillrect.setAffineTransform(affineTransform);
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
		fillText.setFillPaint(fillPaint);
		fillText.setFont(font);
		fillText.setMethod(FILL_TEXT);
		fillText.setTranslateX(translateX);
		fillText.setTranslateY(translateY);
		fillText.setRotate(rotate);
		fillText.setScaleX(scaleX);
		fillText.setScaleY(scaleY);
		fillText.setMaxWidth(maxWidth);
		fillText.setTextAlign(textAlign);
		fillText.setBaseline(baseline);
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
		if(path == null)
			path = new GeneralPath();	
		path.lineTo(x, y);
	}

	@Override
	public TextMetrics measureText(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void moveTo(int x, int y) {
		if(path == null)
			path = new GeneralPath();
		path.moveTo(x, y);
	}

	@Override
	public void putImageData(CanvasImageData imagedata, int dx, int dy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putImageData(CanvasImageData imagedata, int dx, int dy, int dirtyX, int dirtyY, int dirtyWidth, int dirtyHeight) {
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
	public void setTransform(Double m11, Double m12, Double m21, Double m22, Double dx, Double dy) {
		transform(m11, m12, m21, m22, dx, dy);
	}
	
	@Override
	public void transform(Double m11, Double m12, Double m21, Double m22, Double dx, Double dy) {
		affineTransform = new AffineTransform(m11, m12, m21, m22, dx, dy);
	}

	@Override
	public void stroke() {
		if (rectWidth > 0 || rectHeight > 0) {
			strokeRect(rectX, rectY, rectWidth, rectHeight, 0);

		} else {
			CanvasInfo stroke = new CanvasInfo();
			stroke.setMethod(STROKE);
			stroke.setPath(path);
			stroke.setStrokePaint(strokePaint);
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
		strokeRect.setStrokePaint(strokePaint);
		strokeRect.setGlobalAlpha(comosite(globalCompositeOperation));
		strokeRect.setTranslateX(translateX);
		strokeRect.setTranslateY(translateY);
		strokeRect.setRotate(rotate);
		strokeRect.setScaleX(scaleX);
		strokeRect.setScaleY(scaleY);
		strokeRect.setAffineTransform(affineTransform);
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
		strokeText.setStrokePaint(strokePaint);
		strokeText.setFont(font);
		strokeText.setMethod(STROKE_TEXT);
		strokeText.setTranslateX(translateX);
		strokeText.setTranslateY(translateY);
		strokeText.setRotate(rotate);
		strokeText.setScaleX(scaleX);
		strokeText.setScaleY(scaleY);
		strokeText.setMaxWidth(maxWidth);
		strokeText.setTextAlign(textAlign);
		strokeText.setBaseline(baseline);
		listCanvasInfo.add(strokeText);

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

		float fontSize = LAFSettings.getInstance().getFontSize();
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
		return FontFactory.getInstance().getFont(fontFamily, fontStyle, fontVariant, fontWeight, fontSize, null, null,0,false,0);

	}

	/**
	 * Linear gradient.
	 *
	 * @return the linear gradient paint
	 */
	private LinearGradientPaint linearGradient(ArrayList<Float> fractions,
			ArrayList<Color> colors, Double linearX, Double linearX1,
			Double linearY, Double linearY1) {

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
	 * Gets the list canvas info.
	 *
	 * @return the list canvas info
	 */
	public ArrayList<CanvasInfo> getListCanvasInfo() {
		return listCanvasInfo;
	}

	/**
	 * Sets the list canvas info.
	 *
	 * @param listCanvasInfo
	 *            the new list canvas info
	 */
	public void setListCanvasInfo(ArrayList<CanvasInfo> listCanvasInfo) {
		this.listCanvasInfo = listCanvasInfo;
	}

	@Override
	public void toBlob(FileCallback callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toBlob(FileCallback callback, String type, Object... args) {
		// TODO Auto-generated method stub
		
	}
	
	
	private AlphaComposite comosite(String op) {
		int c;
		if ("source-atop".equals(op)) {
			c = AlphaComposite.SRC_ATOP;
		} else if ("source-in".equals(op)) {
			c = AlphaComposite.SRC_IN;
		} else if ("source-out".equals(op)) {
			c = AlphaComposite.SRC_OUT;
		} else if ("destination-atop".equals(op)) {
			c = AlphaComposite.DST_ATOP;
		} else if ("destination-in".equals(op)) {
			c = AlphaComposite.DST_IN;
		} else if ("destination-out".equals(op)) {
			c = AlphaComposite.DST_OUT;
		} else if ("destination-over".equals(op)) {
			c = AlphaComposite.DST_OVER;
		} else if ("xor".equals(op)) {
			c = AlphaComposite.XOR;
		} else if ("over".equals(op)) {
			c = AlphaComposite.CLEAR;
		} else {
			c = AlphaComposite.SRC_OVER;
		}
		return AlphaComposite.getInstance(c, globalAlpha);
	}
}
