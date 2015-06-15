/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License; or (at your option) any later version. This program is distributed
 * in the hope that it will be useful; but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not;
 * write to the Free Software Foundation; Inc.; 51 Franklin St; Fifth Floor;
 * Boston; MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.info;

import java.awt.Color;
import java.awt.Font;

import org.lobobrowser.html.w3c.HTMLImageElement;

/**
 * The Class CanvasInfo.
 */
public class CanvasInfo {

	/** The x. */
	private int x;

	/** The y. */
	private int y;

	/** The width. */
	private int width;

	/** The height. */
	private int height;

	/** The line width. */
	private int lineWidth;

	/** The Miter Limit. */
	private int miterLimit;

	/** The max width. */
	private int maxWidth;

	/** The Scale x. */
	private int scaleX;

	/** The Scale Y. */
	private int scaleY;

	/** The Translate x. */
	private int translateX;

	/** The Translate Y. */
	private int translateY;

	/** The method. */
	private int method;

	/** The sx. */
	private int sx;

	/** The sy. */
	private int sy;

	/** The sw. */
	private int sw;

	/** The sh. */
	private int sh;

	/** The dx. */
	private int dx;

	/** The dy. */
	private int dy;

	/** The dw. */
	private int dw;

	/** The dh. */
	private int dh;

	/** The linear x. */
	private Double linearX;

	/** The linear y. */
	private Double linearY;

	/** The linear x1. */
	private Double linearX1;

	/** The linear y1. */
	private Double linearY1;

	/** The rotate. */
	private Double rotate;

	/** The global alpha. */
	private Double globalAlpha;

	/** The text. */
	private String text;

	/** The Line Cap. */
	private String lineCap;

	/** The Line Join. */
	private String lineJoin;

	/** The stroke style. */
	private Color strokeStyle;

	/** The fill style. */
	private Color fillStyle;

	/** The font. */
	private Font font;

	/** The html image element. */
	private HTMLImageElement image;

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x.
	 *
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y.
	 *
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width.
	 *
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 *
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Gets the line width.
	 *
	 * @return the lineWidth
	 */
	public int getLineWidth() {
		return lineWidth;
	}

	/**
	 * Sets the line width.
	 *
	 * @param lineWidth
	 *            the lineWidth to set
	 */
	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	/**
	 * Gets the miter limit.
	 *
	 * @return the miterLimit
	 */
	public int getMiterLimit() {
		return miterLimit;
	}

	/**
	 * Sets the miter limit.
	 *
	 * @param miterLimit
	 *            the miterLimit to set
	 */
	public void setMiterLimit(int miterLimit) {
		this.miterLimit = miterLimit;
	}

	/**
	 * Gets the max width.
	 *
	 * @return the maxWidth
	 */
	public int getMaxWidth() {
		return maxWidth;
	}

	/**
	 * Sets the max width.
	 *
	 * @param maxWidth
	 *            the maxWidth to set
	 */
	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	/**
	 * Gets the scale x.
	 *
	 * @return the scaleX
	 */
	public int getScaleX() {
		return scaleX;
	}

	/**
	 * Sets the scale x.
	 *
	 * @param scaleX
	 *            the scaleX to set
	 */
	public void setScaleX(int scaleX) {
		this.scaleX = scaleX;
	}

	/**
	 * Gets the scale y.
	 *
	 * @return the scaleY
	 */
	public int getScaleY() {
		return scaleY;
	}

	/**
	 * Sets the scale y.
	 *
	 * @param scaleY
	 *            the scaleY to set
	 */
	public void setScaleY(int scaleY) {
		this.scaleY = scaleY;
	}

	/**
	 * Gets the translate x.
	 *
	 * @return the translateX
	 */
	public int getTranslateX() {
		return translateX;
	}

	/**
	 * Sets the translate x.
	 *
	 * @param translateX
	 *            the translateX to set
	 */
	public void setTranslateX(int translateX) {
		this.translateX = translateX;
	}

	/**
	 * Gets the translate y.
	 *
	 * @return the translateY
	 */
	public int getTranslateY() {
		return translateY;
	}

	/**
	 * Sets the translate y.
	 *
	 * @param translateY
	 *            the translateY to set
	 */
	public void setTranslateY(int translateY) {
		this.translateY = translateY;
	}

	/**
	 * Gets the method.
	 *
	 * @return the method
	 */
	public int getMethod() {
		return method;
	}

	/**
	 * Sets the method.
	 *
	 * @param method
	 *            the method to set
	 */
	public void setMethod(int method) {
		this.method = method;
	}

	/**
	 * Gets the sx.
	 *
	 * @return the sx
	 */
	public int getSx() {
		return sx;
	}

	/**
	 * Sets the sx.
	 *
	 * @param sx
	 *            the sx to set
	 */
	public void setSx(int sx) {
		this.sx = sx;
	}

	/**
	 * Gets the sy.
	 *
	 * @return the sy
	 */
	public int getSy() {
		return sy;
	}

	/**
	 * Sets the sy.
	 *
	 * @param sy
	 *            the sy to set
	 */
	public void setSy(int sy) {
		this.sy = sy;
	}

	/**
	 * Gets the sw.
	 *
	 * @return the sw
	 */
	public int getSw() {
		return sw;
	}

	/**
	 * Sets the sw.
	 *
	 * @param sw
	 *            the sw to set
	 */
	public void setSw(int sw) {
		this.sw = sw;
	}

	/**
	 * Gets the sh.
	 *
	 * @return the sh
	 */
	public int getSh() {
		return sh;
	}

	/**
	 * Sets the sh.
	 *
	 * @param sh
	 *            the sh to set
	 */
	public void setSh(int sh) {
		this.sh = sh;
	}

	/**
	 * Gets the dx.
	 *
	 * @return the dx
	 */
	public int getDx() {
		return dx;
	}

	/**
	 * Sets the dx.
	 *
	 * @param dx
	 *            the dx to set
	 */
	public void setDx(int dx) {
		this.dx = dx;
	}

	/**
	 * Gets the dy.
	 *
	 * @return the dy
	 */
	public int getDy() {
		return dy;
	}

	/**
	 * Sets the dy.
	 *
	 * @param dy
	 *            the dy to set
	 */
	public void setDy(int dy) {
		this.dy = dy;
	}

	/**
	 * Gets the dw.
	 *
	 * @return the dw
	 */
	public int getDw() {
		return dw;
	}

	/**
	 * Sets the dw.
	 *
	 * @param dw
	 *            the dw to set
	 */
	public void setDw(int dw) {
		this.dw = dw;
	}

	/**
	 * Gets the dh.
	 *
	 * @return the dh
	 */
	public int getDh() {
		return dh;
	}

	/**
	 * Sets the dh.
	 *
	 * @param dh
	 *            the dh to set
	 */
	public void setDh(int dh) {
		this.dh = dh;
	}

	/**
	 * Gets the linear x.
	 *
	 * @return the linearX
	 */
	public Double getLinearX() {
		return linearX;
	}

	/**
	 * Sets the linear x.
	 *
	 * @param linearX
	 *            the linearX to set
	 */
	public void setLinearX(Double linearX) {
		this.linearX = linearX;
	}

	/**
	 * Gets the linear y.
	 *
	 * @return the linearY
	 */
	public Double getLinearY() {
		return linearY;
	}

	/**
	 * Sets the linear y.
	 *
	 * @param linearY
	 *            the linearY to set
	 */
	public void setLinearY(Double linearY) {
		this.linearY = linearY;
	}

	/**
	 * Gets the linear x1.
	 *
	 * @return the linearX1
	 */
	public Double getLinearX1() {
		return linearX1;
	}

	/**
	 * Sets the linear x1.
	 *
	 * @param linearX1
	 *            the linearX1 to set
	 */
	public void setLinearX1(Double linearX1) {
		this.linearX1 = linearX1;
	}

	/**
	 * Gets the linear y1.
	 *
	 * @return the linearY1
	 */
	public Double getLinearY1() {
		return linearY1;
	}

	/**
	 * Sets the linear y1.
	 *
	 * @param linearY1
	 *            the linearY1 to set
	 */
	public void setLinearY1(Double linearY1) {
		this.linearY1 = linearY1;
	}

	/**
	 * Gets the rotate.
	 *
	 * @return the rotate
	 */
	public Double getRotate() {
		return rotate;
	}

	/**
	 * Sets the rotate.
	 *
	 * @param rotate
	 *            the rotate to set
	 */
	public void setRotate(Double rotate) {
		this.rotate = rotate;
	}

	/**
	 * Gets the global alpha.
	 *
	 * @return the globalAlpha
	 */
	public Double getGlobalAlpha() {
		return globalAlpha;
	}

	/**
	 * Sets the global alpha.
	 *
	 * @param globalAlpha
	 *            the globalAlpha to set
	 */
	public void setGlobalAlpha(Double globalAlpha) {
		this.globalAlpha = globalAlpha;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the line cap.
	 *
	 * @return the lineCap
	 */
	public String getLineCap() {
		return lineCap;
	}

	/**
	 * Sets the line cap.
	 *
	 * @param lineCap
	 *            the lineCap to set
	 */
	public void setLineCap(String lineCap) {
		this.lineCap = lineCap;
	}

	/**
	 * Gets the line join.
	 *
	 * @return the lineJoin
	 */
	public String getLineJoin() {
		return lineJoin;
	}

	/**
	 * Sets the line join.
	 *
	 * @param lineJoin
	 *            the lineJoin to set
	 */
	public void setLineJoin(String lineJoin) {
		this.lineJoin = lineJoin;
	}

	/**
	 * Gets the stroke style.
	 *
	 * @return the strokeStyle
	 */
	public Color getStrokeStyle() {
		return strokeStyle;
	}

	/**
	 * Sets the stroke style.
	 *
	 * @param strokeStyle
	 *            the strokeStyle to set
	 */
	public void setStrokeStyle(Color strokeStyle) {
		this.strokeStyle = strokeStyle;
	}

	/**
	 * Gets the fill style.
	 *
	 * @return the fillStyle
	 */
	public Color getFillStyle() {
		return fillStyle;
	}

	/**
	 * Sets the fill style.
	 *
	 * @param fillStyle
	 *            the fillStyle to set
	 */
	public void setFillStyle(Color fillStyle) {
		this.fillStyle = fillStyle;
	}

	/**
	 * Gets the font.
	 *
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * Sets the font.
	 *
	 * @param font
	 *            the font to set
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public HTMLImageElement getImage() {
		return image;
	}

	/**
	 * Sets the image.
	 *
	 * @param image
	 *            the image to set
	 */
	public void setImage(HTMLImageElement image) {
		this.image = image;
	}

}
