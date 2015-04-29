/*
 * GNU GENERAL LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General License for more details. You should have received
 * a copy of the GNU General License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.w3c;

import java.awt.Font;

/**
 * The public interface CanvasRenderingContext2D.
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
     * @param arg
     *            the new fill style
     */
    void setFillStyle(Object arg);

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
    int getGlobalAlpha();

    /**
     * Sets the global alpha.
     *
     * @param arg
     *            the new global alpha
     */
    void setGlobalAlpha(int arg);

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
     * @param arg
     *            the new stroke style
     */
    void setStrokeStyle(Object arg);

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

    void arc(int x, int y, int radius, int startAngle, int endAngle,
            boolean anticlockwise);

    void arcTo(int x1, int y1, int x2, int y2, int radius);

    void beginPath();

    void bezierCurveTo(int cp1x, int cp1y, int cp2x, int cp2y, int x, int y);

    void clearRect(int x, int y, int width, int height);

    void clearShadow();

    void clip();

    void closePath();

    CanvasGradient createLinearGradient(int x0, int y0, int x1, int y1);

    CanvasPattern createPattern(HTMLCanvasElement canvas, String repetitionType);

    CanvasPattern createPattern(HTMLImageElement image, String repetitionType);

    CanvasGradient createRadialGradient(int x0, int y0, int r0, int x1, int y1,
            int r1);

    void drawImage(HTMLImageElement image, int x, int y);

    void drawImage(HTMLImageElement image, int x, int y, int width, int height);

    void drawImage(HTMLImageElement image, int sx, int sy, int sw, int sh,
            int dx, int dy, int dw, int dh);

    void drawImage(HTMLCanvasElement canvas, int x, int y);

    void drawImage(HTMLCanvasElement canvas, int x, int y, int width, int height);

    void drawImage(HTMLCanvasElement canvas, int sx, int sy, int sw, int sh,
            int dx, int dy, int dw, int dh);

    void drawImage(HTMLVideoElement video, int x, int y);

    void drawImage(HTMLVideoElement video, int x, int y, int width, int height);

    void drawImage(HTMLVideoElement video, int sx, int sy, int sw, int sh,
            int dx, int dy, int dw, int dh);

    void drawImageFromRect(HTMLImageElement image);

    void drawImageFromRect(HTMLImageElement image, int sx);

    void drawImageFromRect(HTMLImageElement image, int sx, int sy);

    void drawImageFromRect(HTMLImageElement image, int sx, int sy, int sw);

    void drawImageFromRect(HTMLImageElement image, int sx, int sy, int sw,
            int sh);

    void drawImageFromRect(HTMLImageElement image, int sx, int sy, int sw,
            int sh, int dx);

    void drawImageFromRect(HTMLImageElement image, int sx, int sy, int sw,
            int sh, int dx, int dy);

    void drawImageFromRect(HTMLImageElement image, int sx, int sy, int sw,
            int sh, int dx, int dy, int dw);

    void drawImageFromRect(HTMLImageElement image, int sx, int sy, int sw,
            int sh, int dx, int dy, int dw, int dh);

    void drawImageFromRect(HTMLImageElement image, int sx, int sy, int sw,
            int sh, int dx, int dy, int dw, int dh, String compositeOperation);

    void fill();

    void fillRect(int x, int y, int width, int height);

    void fillText(String text, int x, int y);

    void fillText(String text, int x, int y, int maxWidth);

    CanvasImageData getImageData(int sx, int sy, int sw, int sh);

    boolean isPointInPath(int x, int y);

    void lineTo(int x, int y);

    Object measureText(String text);

    void moveTo(int x, int y);

    void putImageData(CanvasImageData imagedata, int dx, int dy);

    void putImageData(CanvasImageData imagedata, int dx, int dy, int dirtyX,
            int dirtyY, int dirtyWidth, int dirtyHeight);

    void quadraticCurveTo(int cpx, int cpy, int x, int y);

    void rect(int x, int y, int width, int height);

    void restore();

    void rotate(int angle);

    void save();

    void scale(int sx, int sy);

    /**
     * Sets the alpha.
     *
     * @param alpha
     *            the new alpha
     */
    void setAlpha(int alpha);

    /**
     * Sets the composite operation.
     *
     * @param compositeOperation
     *            the new composite operation
     */
    void setCompositeOperation(String compositeOperation);

    /**
     * Sets the fill color.
     *
     * @param color
     *            the new fill color
     */
    void setFillColor(String color);

    void setFillColor(String color, int alpha);

    /**
     * Sets the fill color.
     *
     * @param grayLevel
     *            the new fill color
     */
    void setFillColor(int grayLevel);

    void setFillColor(int grayLevel, int alpha);

    void setFillColor(int r, int g, int b, int a);

    void setFillColor(int c, int m, int y, int k, int a);

    void setShadow(int width, int height, int blur);

    void setShadow(int width, int height, int blur, String color);

    void setShadow(int width, int height, int blur, String color, int alpha);

    void setShadow(int width, int height, int blur, int grayLevel);

    void setShadow(int width, int height, int blur, int grayLevel, int alpha);

    void setShadow(int width, int height, int blur, int r, int g, int b, int a);

    void setShadow(int width, int height, int blur, int c, int m, int y, int k,
            int a);

    /**
     * Sets the stroke color.
     *
     * @param color
     *            the new stroke color
     */
    void setStrokeColor(String color);

    void setStrokeColor(String color, int alpha);

    /**
     * Sets the stroke color.
     *
     * @param grayLevel
     *            the new stroke color
     */
    void setStrokeColor(int grayLevel);

    void setStrokeColor(int grayLevel, int alpha);

    void setStrokeColor(int r, int g, int b, int a);

    void setStrokeColor(int c, int m, int y, int k, int a);

    void setTransform(int m11, int m12, int m21, int m22, int dx, int dy);

    void stroke();

    void strokeRect(int x, int y, int width, int height);

    void strokeRect(int x, int y, int width, int height, int lineWidth);

    void strokeText(String text, int x, int y);

    void strokeText(String text, int x, int y, int maxWidth);

    void transform(int m11, int m12, int m21, int m22, int dx, int dy);

    void translate(int tx, int ty);

}
