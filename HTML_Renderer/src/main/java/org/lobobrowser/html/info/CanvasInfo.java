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
package org.lobobrowser.html.info;

import java.awt.Color;
import java.awt.Font;

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

	/** The stroke style. */
	private Color strokeStyle;

	/** The fill style. */
	private Color fillStyle;

	/** The text. */
	private String text;

	/** The max width. */
	private int maxWidth;

	/** The font. */
	private Font font;

	/** The Line Cap. */
	private String lineCap;

	/** The Line Join. */
	private String lineJoin;

	/** The Miter Limit. */
	private int miterLimit;

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

	/** The Scale x. */
	private int scaleX;

	/** The Scale Y. */
	private int scaleY;

	/** The Translate x. */
	private int translateX;

	/** The Translate Y. */
	private int translateY;

	/** The global alpha. */
	private Double globalAlpha;

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the lineWidth
	 */
	public int getLineWidth() {
		return lineWidth;
	}

	/**
	 * @param lineWidth
	 *            the lineWidth to set
	 */
	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	/**
	 * @return the strokeStyle
	 */
	public Color getStrokeStyle() {
		return strokeStyle;
	}

	/**
	 * @param strokeStyle
	 *            the strokeStyle to set
	 */
	public void setStrokeStyle(Color strokeStyle) {
		this.strokeStyle = strokeStyle;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the maxWidth
	 */
	public int getMaxWidth() {
		return maxWidth;
	}

	/**
	 * @param maxWidth
	 *            the maxWidth to set
	 */
	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	/**
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * @param font
	 *            the font to set
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	public String getLineCap() {
		return lineCap;
	}

	public void setLineCap(String lineCap) {
		this.lineCap = lineCap;
	}

	public String getLineJoin() {
		return lineJoin;
	}

	public void setLineJoin(String lineJoin) {
		this.lineJoin = lineJoin;

	}

	public int getMiterLimit() {
		return miterLimit;
	}

	public void setMiterLimit(int miterLimit) {
		this.miterLimit = miterLimit;
	}

	/**
	 * @return the fillStyle
	 */
	public Color getFillStyle() {
		return fillStyle;
	}

	/**
	 * @param fillStyle
	 *            the fillStyle to set
	 */
	public void setFillStyle(Color fillStyle) {
		this.fillStyle = fillStyle;
	}

	/**
	 * @return the linearX
	 */
	public Double getLinearX() {
		return linearX;
	}

	/**
	 * @param linearX
	 *            the linearX to set
	 */
	public void setLinearX(Double linearX) {
		this.linearX = linearX;
	}

	/**
	 * @return the linearY
	 */
	public Double getLinearY() {
		return linearY;
	}

	/**
	 * @param linearY
	 *            the linearY to set
	 */
	public void setLinearY(Double linearY) {
		this.linearY = linearY;
	}

	/**
	 * @return the linearX1
	 */
	public Double getLinearX1() {
		return linearX1;
	}

	/**
	 * @param linearX1
	 *            the linearX1 to set
	 */
	public void setLinearX1(Double linearX1) {
		this.linearX1 = linearX1;
	}

	/**
	 * @return the linearY1
	 */
	public Double getLinearY1() {
		return linearY1;
	}

	/**
	 * @param linearY1
	 *            the linearY1 to set
	 */
	public void setLinearY1(Double linearY1) {
		this.linearY1 = linearY1;
	}

	/**
	 * @return the rotate
	 */
	public Double getRotate() {
		return rotate;
	}

	/**
	 * @param rotate
	 *            the rotate to set
	 */
	public void setRotate(Double rotate) {
		this.rotate = rotate;
	}

	/**
	 * @return the scaleX
	 */
	public int getScaleX() {
		return scaleX;
	}

	/**
	 * @param scaleX
	 *            the scaleX to set
	 */
	public void setScaleX(int scaleX) {
		this.scaleX = scaleX;
	}

	/**
	 * @return the scaleY
	 */
	public int getScaleY() {
		return scaleY;
	}

	/**
	 * @param scaleY
	 *            the scaleY to set
	 */
	public void setScaleY(int scaleY) {
		this.scaleY = scaleY;
	}

	/**
	 * @return the translateX
	 */
	public int getTranslateX() {
		return translateX;
	}

	/**
	 * @param translateX
	 *            the translateX to set
	 */
	public void setTranslateX(int translateX) {
		this.translateX = translateX;
	}

	/**
	 * @return the translateY
	 */
	public int getTranslateY() {
		return translateY;
	}

	/**
	 * @param translateY
	 *            the translateY to set
	 */
	public void setTranslateY(int translateY) {
		this.translateY = translateY;
	}

	/**
	 * @return the globalAlpha
	 */
	public Double getGlobalAlpha() {
		return globalAlpha;
	}

	/**
	 * @param globalAlpha the globalAlpha to set
	 */
	public void setGlobalAlpha(Double globalAlpha) {
		this.globalAlpha = globalAlpha;
	}

}
