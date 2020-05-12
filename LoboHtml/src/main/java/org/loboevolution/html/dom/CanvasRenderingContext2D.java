/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution
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
    
    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom;

import java.awt.Font;

/**
 * The public interface CanvasRenderingContext2D.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface CanvasRenderingContext2D extends CanvasRenderingContext {

	/**
	 * Gets the fill style.
	 *
	 * @return the fill style
	 */
	Object getFillStyle();

	/**
	 * Sets the fill style.
	 *
	 * @param style
	 *            the new fill style
	 */
	void setFillStyle(Object style);

	/**
	 * Gets the font.
	 *
	 * @return the font
	 */
	Font getFont();

	/**
	 * Sets the font.
	 *
	 * @param arg
	 *            the new font
	 */
	void setFont(String arg);

	/**
	 * Gets the global alpha.
	 *
	 * @return the global alpha
	 */
	Double getGlobalAlpha();

	/**
	 * Sets the global alpha.
	 *
	 * @param arg
	 *            the new global alpha
	 */
	void setGlobalAlpha(Double arg);

	/**
	 * Gets the global composite operation.
	 *
	 * @return the global composite operation
	 */
	String getGlobalCompositeOperation();

	/**
	 * Sets the global composite operation.
	 *
	 * @param arg
	 *            the new global composite operation
	 */
	void setGlobalCompositeOperation(String arg);

	/**
	 * Gets the line cap.
	 *
	 * @return the line cap
	 */
	String getLineCap();

	/**
	 * Sets the line cap.
	 *
	 * @param arg
	 *            the new line cap
	 */
	void setLineCap(String arg);

	/**
	 * Gets the line join.
	 *
	 * @return the line join
	 */
	String getLineJoin();

	/**
	 * Sets the line join.
	 *
	 * @param arg
	 *            the new line join
	 */
	void setLineJoin(String arg);

	/**
	 * Gets the line width.
	 *
	 * @return the line width
	 */
	int getLineWidth();

	/**
	 * Sets the line width.
	 *
	 * @param arg
	 *            the new line width
	 */
	void setLineWidth(int arg);

	/**
	 * Gets the miter limit.
	 *
	 * @return the miter limit
	 */
	int getMiterLimit();

	/**
	 * Sets the miter limit.
	 *
	 * @param arg
	 *            the new miter limit
	 */
	void setMiterLimit(int arg);

	/**
	 * Gets the shadow blur.
	 *
	 * @return the shadow blur
	 */
	int getShadowBlur();

	/**
	 * Sets the shadow blur.
	 *
	 * @param arg
	 *            the new shadow blur
	 */
	void setShadowBlur(int arg);

	/**
	 * Gets the shadow color.
	 *
	 * @return the shadow color
	 */
	String getShadowColor();

	/**
	 * Sets the shadow color.
	 *
	 * @param arg
	 *            the new shadow color
	 */
	void setShadowColor(String arg);

	/**
	 * Gets the shadow offset x.
	 *
	 * @return the shadow offset x
	 */
	int getShadowOffsetX();

	/**
	 * Sets the shadow offset x.
	 *
	 * @param arg
	 *            the new shadow offset x
	 */
	void setShadowOffsetX(int arg);

	/**
	 * Gets the shadow offset y.
	 *
	 * @return the shadow offset y
	 */
	int getShadowOffsetY();

	/**
	 * Sets the shadow offset y.
	 *
	 * @param arg
	 *            the new shadow offset y
	 */
	void setShadowOffsetY(int arg);

	/**
	 * Gets the stroke style.
	 *
	 * @return the stroke style
	 */
	Object getStrokeStyle();

	/**
	 * Sets the stroke style.
	 *
	 * @param style
	 *            the new stroke style
	 */
	void setStrokeStyle(Object style);

	/**
	 * Gets the text align.
	 *
	 * @return the text align
	 */
	String getTextAlign();

	/**
	 * Sets the text align.
	 *
	 * @param arg
	 *            the new text align
	 */
	void setTextAlign(String arg);

	/**
	 * Gets the text baseline.
	 *
	 * @return the text baseline
	 */
	String getTextBaseline();

	/**
	 * Sets the text baseline.
	 *
	 * @param arg
	 *            the new text baseline
	 */
	void setTextBaseline(String arg);
	

	/**
	 * <p>ellipse.</p>
	 *
	 * @param x             the x
	 * @param y             the y
	 * @param radiusX       the radiusX
	 * @param radiusY       the radiusY
	 * @param rotation      the rotation
	 * @param startAngle    the start angle
	 * @param endAngle      the end angle
	 */
	void ellipse(int x, int y, int radiusX, int radiusY, int rotation, int startAngle, int endAngle);
	
	/**
	 * <p>ellipse.</p>
	 *
	 * @param x             the x
	 * @param y             the y
	 * @param radiusX       the radiusX
	 * @param radiusY       the radiusY
	 * @param rotation      the rotation
	 * @param startAngle    the start angle
	 * @param endAngle      the end angle
	 * @param anticlockwise is anti-clockwise
	 */
	void ellipse(int x, int y, int radiusX, int radiusY, int rotation, int startAngle, int endAngle, boolean anticlockwise);


	/**
	 * Arc.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param radius
	 *            the radius
	 * @param startAngle
	 *            the start angle
	 * @param endAngle
	 *            the end angle
	 */
	void arc(int x, int y, int radius, int startAngle, int endAngle);

	/**
	 * Arc.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param radius
	 *            the radius
	 * @param startAngle
	 *            the start angle
	 * @param endAngle
	 *            the end angle
	 * @param anticlockwise
	 *            the anticlockwise
	 */
	void arc(int x, int y, int radius, int startAngle, int endAngle, boolean anticlockwise);

	/**
	 * Arc to.
	 *
	 * @param x1
	 *            the x1
	 * @param y1
	 *            the y1
	 * @param x2
	 *            the x2
	 * @param y2
	 *            the y2
	 * @param radius
	 *            the radius
	 */
	void arcTo(int x1, int y1, int x2, int y2, int radius);

	/**
	 * Begin path.
	 */
	void beginPath();

	/**
	 * Bezier curve to.
	 *
	 * @param cp1x
	 *            the cp1x
	 * @param cp1y
	 *            the cp1y
	 * @param cp2x
	 *            the cp2x
	 * @param cp2y
	 *            the cp2y
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	void bezierCurveTo(int cp1x, int cp1y, int cp2x, int cp2y, int x, int y);

	/**
	 * Clear rect.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	void clearRect(int x, int y, int width, int height);

	/**
	 * Clear shadow.
	 */
	void clearShadow();

	/**
	 * Clip.
	 */
	void clip();

	/**
	 * Close path.
	 */
	void closePath();

	/**
	 * Creates the linear gradient.
	 *
	 * @param x0
	 *            the x0
	 * @param y0
	 *            the y0
	 * @param x1
	 *            the x1
	 * @param y1
	 *            the y1
	 * @return the canvas gradient
	 */
	CanvasGradient createLinearGradient(Object x0, Object y0, Object x1, Object y1);

	/**
	 * Creates the pattern.
	 *
	 * @param canvas
	 *            the canvas
	 * @param repetitionType
	 *            the repetition type
	 * @return the canvas pattern
	 */
	CanvasPattern createPattern(HTMLCanvasElement canvas, String repetitionType);

	/**
	 * Creates the pattern.
	 *
	 * @param image
	 *            the image
	 * @param repetitionType
	 *            the repetition type
	 * @return the canvas pattern
	 */
	CanvasPattern createPattern(HTMLImageElement image, String repetitionType);

	/**
	 * Creates the radial gradient.
	 *
	 * @param x0
	 *            the x0
	 * @param y0
	 *            the y0
	 * @param r0
	 *            the r0
	 * @param x1
	 *            the x1
	 * @param y1
	 *            the y1
	 * @param r1
	 *            the r1
	 * @return the canvas gradient
	 */
	CanvasGradient createRadialGradient(Object x0, Object y0, Object r0, Object x1, Object y1, Object r1);

	/**
	 * Draw image.
	 *
	 * @param image
	 *            the image
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	void drawImage(Object image, Integer x, Integer y);

	/**
	 * Draw image.
	 *
	 * @param image
	 *            the image
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	void drawImage(Object image, Integer x, Integer y, Integer width, Integer height);

	/**
	 * Draw image.
	 *
	 * @param image
	 *            the image
	 * @param sx
	 *            the sx
	 * @param sy
	 *            the sy
	 * @param sw
	 *            the sw
	 * @param sh
	 *            the sh
	 * @param dx
	 *            the dx
	 * @param dy
	 *            the dy
	 * @param dw
	 *            the dw
	 * @param dh
	 *            the dh
	 */
	void drawImage(Object image, Integer sx, Integer sy, Integer sw, Integer sh, Integer dx, Integer dy, Integer dw,
			Integer dh);

	/**
	 * Fill.
	 */
	void fill();

	/**
	 * Fill rect.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	void fillRect(int x, int y, int width, int height);

	/**
	 * Fill text.
	 *
	 * @param text
	 *            the text
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	void fillText(String text, int x, int y);

	/**
	 * Fill text.
	 *
	 * @param text
	 *            the text
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param maxWidth
	 *            the max width
	 */
	void fillText(String text, int x, int y, int maxWidth);

	/**
	 * Gets the image data.
	 *
	 * @param sx
	 *            the sx
	 * @param sy
	 *            the sy
	 * @param sw
	 *            the sw
	 * @param sh
	 *            the sh
	 * @return the image data
	 */
	ImageData getImageData(int sx, int sy, int sw, int sh);

	/**
	 * Checks if is point in path.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if is point in path
	 */
	boolean isPointInPath(int x, int y);

	/**
	 * Line to.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	void lineTo(int x, int y);

	/**
	 * Measure text.
	 *
	 * @param text
	 *            the text
	 * @return the object
	 */
	TextMetrics measureText(String text);

	/**
	 * Move to.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	void moveTo(int x, int y);

	/**
	 * Put image data.
	 *
	 * @param imagedata
	 *            the imagedata
	 * @param dx
	 *            the dx
	 * @param dy
	 *            the dy
	 */
	void putImageData(ImageData imagedata, int dx, int dy);

	/**
	 * Put image data.
	 *
	 * @param imagedata
	 *            the imagedata
	 * @param dx
	 *            the dx
	 * @param dy
	 *            the dy
	 * @param dirtyX
	 *            the dirty x
	 * @param dirtyY
	 *            the dirty y
	 * @param dirtyWidth
	 *            the dirty width
	 * @param dirtyHeight
	 *            the dirty height
	 */
	void putImageData(ImageData imagedata, int dx, int dy, int dirtyX, int dirtyY, int dirtyWidth,
			int dirtyHeight);

	/**
	 * Quadratic curve to.
	 *
	 * @param cpx
	 *            the cpx
	 * @param cpy
	 *            the cpy
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	void quadraticCurveTo(int cpx, int cpy, int x, int y);

	/**
	 * Rect.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	void rect(int x, int y, int width, int height);

	/**
	 * Restore.
	 */
	void restore();

	/**
	 * Rotate.
	 *
	 * @param angle
	 *            the angle
	 */
	void rotate(double angle);

	/**
	 * Save.
	 */
	void save();

	/**
	 * Scale.
	 *
	 * @param sx
	 *            the sx
	 * @param sy
	 *            the sy
	 */
	void scale(int sx, int sy);

	/**
	 * Sets the transform.
	 *
	 * @param m11
	 *            the m11
	 * @param m12
	 *            the m12
	 * @param m21
	 *            the m21
	 * @param m22
	 *            the m22
	 * @param dx
	 *            the dx
	 * @param dy
	 *            the dy
	 */
	void setTransform(Double m11, Double m12, Double m21, Double m22, Double dx, Double dy);

	/**
	 * Stroke.
	 */
	void stroke();

	/**
	 * Stroke rect.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	void strokeRect(int x, int y, int width, int height);

	/**
	 * Stroke rect.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param lineWidth
	 *            the line width
	 */
	void strokeRect(int x, int y, int width, int height, int lineWidth);

	/**
	 * Stroke text.
	 *
	 * @param text
	 *            the text
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	void strokeText(String text, int x, int y);

	/**
	 * Stroke text.
	 *
	 * @param text
	 *            the text
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param maxWidth
	 *            the max width
	 */
	void strokeText(String text, int x, int y, int maxWidth);

	/**
	 * Transform.
	 *
	 * @param m11
	 *            the m11
	 * @param m12
	 *            the m12
	 * @param m21
	 *            the m21
	 * @param m22
	 *            the m22
	 * @param dx
	 *            the dx
	 * @param dy
	 *            the dy
	 */
	void transform(Double m11, Double m12, Double m21, Double m22, Double dx, Double dy);

	/**
	 * Translate.
	 *
	 * @param tx
	 *            the tx
	 * @param ty
	 *            the ty
	 */
	void translate(int tx, int ty);

}
