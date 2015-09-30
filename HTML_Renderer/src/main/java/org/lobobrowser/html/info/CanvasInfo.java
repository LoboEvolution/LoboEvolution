/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.info;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.LinearGradientPaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.io.Serializable;

import org.lobobrowser.w3c.html.HTMLImageElement;

/**
 * The Class CanvasInfo.
 */
public class CanvasInfo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4173240031155753085L;

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

	/** The rotate. */
	private Double rotate;

	/** The global alpha. */
	private AlphaComposite globalAlpha;

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

	/** The Genearl path. */
	private GeneralPath path;

	/** The linear gradient. */
	private LinearGradientPaint linearGradient;

	/** The Affine Transform. */
	private AffineTransform affineTransform;

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
	 *            the new x
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
	 *            the new y
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
	 *            the new width
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
	 *            the new height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Gets the line width.
	 *
	 * @return the line width
	 */
	public int getLineWidth() {
		return lineWidth;
	}

	/**
	 * Sets the line width.
	 *
	 * @param lineWidth
	 *            the new line width
	 */
	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	/**
	 * Gets the Miter Limit.
	 *
	 * @return the Miter Limit
	 */
	public int getMiterLimit() {
		return miterLimit;
	}

	/**
	 * Sets the Miter Limit.
	 *
	 * @param miterLimit
	 *            the new Miter Limit
	 */
	public void setMiterLimit(int miterLimit) {
		this.miterLimit = miterLimit;
	}

	/**
	 * Gets the max width.
	 *
	 * @return the max width
	 */
	public int getMaxWidth() {
		return maxWidth;
	}

	/**
	 * Sets the max width.
	 *
	 * @param maxWidth
	 *            the new max width
	 */
	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	/**
	 * Gets the Scale x.
	 *
	 * @return the Scale x
	 */
	public int getScaleX() {
		return scaleX;
	}

	/**
	 * Sets the Scale x.
	 *
	 * @param scaleX
	 *            the new Scale x
	 */
	public void setScaleX(int scaleX) {
		this.scaleX = scaleX;
	}

	/**
	 * Gets the Scale Y.
	 *
	 * @return the Scale Y
	 */
	public int getScaleY() {
		return scaleY;
	}

	/**
	 * Sets the Scale Y.
	 *
	 * @param scaleY
	 *            the new Scale Y
	 */
	public void setScaleY(int scaleY) {
		this.scaleY = scaleY;
	}

	/**
	 * Gets the Translate x.
	 *
	 * @return the Translate x
	 */
	public int getTranslateX() {
		return translateX;
	}

	/**
	 * Sets the Translate x.
	 *
	 * @param translateX
	 *            the new Translate x
	 */
	public void setTranslateX(int translateX) {
		this.translateX = translateX;
	}

	/**
	 * Gets the Translate Y.
	 *
	 * @return the Translate Y
	 */
	public int getTranslateY() {
		return translateY;
	}

	/**
	 * Sets the Translate Y.
	 *
	 * @param translateY
	 *            the new Translate Y
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
	 *            the new method
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
	 *            the new sx
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
	 *            the new sy
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
	 *            the new sw
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
	 *            the new sh
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
	 *            the new dx
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
	 *            the new dy
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
	 *            the new dw
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
	 *            the new dh
	 */
	public void setDh(int dh) {
		this.dh = dh;
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
	 *            the new rotate
	 */
	public void setRotate(Double rotate) {
		this.rotate = rotate;
	}

	/**
	 * Gets the global alpha.
	 *
	 * @return the global alpha
	 */
	public AlphaComposite getGlobalAlpha() {
		return globalAlpha;
	}

	/**
	 * Sets the global alpha.
	 *
	 * @param globalAlpha
	 *            the new global alpha
	 */
	public void setGlobalAlpha(AlphaComposite globalAlpha) {
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
	 *            the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the Line Cap.
	 *
	 * @return the Line Cap
	 */
	public String getLineCap() {
		return lineCap;
	}

	/**
	 * Sets the Line Cap.
	 *
	 * @param lineCap
	 *            the new Line Cap
	 */
	public void setLineCap(String lineCap) {
		this.lineCap = lineCap;
	}

	/**
	 * Gets the Line Join.
	 *
	 * @return the Line Join
	 */
	public String getLineJoin() {
		return lineJoin;
	}

	/**
	 * Sets the Line Join.
	 *
	 * @param lineJoin
	 *            the new Line Join
	 */
	public void setLineJoin(String lineJoin) {
		this.lineJoin = lineJoin;
	}

	/**
	 * Gets the stroke style.
	 *
	 * @return the stroke style
	 */
	public Color getStrokeStyle() {
		return strokeStyle;
	}

	/**
	 * Sets the stroke style.
	 *
	 * @param strokeStyle
	 *            the new stroke style
	 */
	public void setStrokeStyle(Color strokeStyle) {
		this.strokeStyle = strokeStyle;
	}

	/**
	 * Gets the fill style.
	 *
	 * @return the fill style
	 */
	public Color getFillStyle() {
		return fillStyle;
	}

	/**
	 * Sets the fill style.
	 *
	 * @param fillStyle
	 *            the new fill style
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
	 *            the new font
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * Gets the html image element.
	 *
	 * @return the html image element
	 */
	public HTMLImageElement getImage() {
		return image;
	}

	/**
	 * Sets the html image element.
	 *
	 * @param image
	 *            the new html image element
	 */
	public void setImage(HTMLImageElement image) {
		this.image = image;
	}

	/**
	 * Gets the Genearl path.
	 *
	 * @return the Genearl path
	 */
	public GeneralPath getPath() {
		return path;
	}

	/**
	 * Sets the Genearl path.
	 *
	 * @param path
	 *            the new Genearl path
	 */
	public void setPath(GeneralPath path) {
		this.path = path;
	}

	/**
	 * Gets the linear gradient.
	 *
	 * @return the linear gradient
	 */
	public LinearGradientPaint getLinearGradient() {
		return linearGradient;
	}

	/**
	 * Sets the linear gradient.
	 *
	 * @param linearGradient
	 *            the new linear gradient
	 */
	public void setLinearGradient(LinearGradientPaint linearGradient) {
		this.linearGradient = linearGradient;
	}

	/**
	 * Gets the Affine Transform.
	 *
	 * @return the Affine Transform
	 */
	public AffineTransform getAffineTransform() {
		return affineTransform;
	}

	/**
	 * Sets the Affine Transform.
	 *
	 * @param affineTransform
	 *            the new Affine Transform
	 */
	public void setAffineTransform(AffineTransform affineTransform) {
		this.affineTransform = affineTransform;
	}
}
