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

import java.awt.Font;
import java.util.ArrayList;

import org.lobobrowser.html.w3c.CanvasGradient;
import org.lobobrowser.html.w3c.CanvasImageData;
import org.lobobrowser.html.w3c.CanvasPattern;
import org.lobobrowser.html.w3c.CanvasRenderingContext2D;
import org.lobobrowser.html.w3c.HTMLCanvasElement;
import org.lobobrowser.html.w3c.HTMLImageElement;
import org.lobobrowser.html.w3c.HTMLVideoElement;

public class DomCanvasImpl implements CanvasRenderingContext2D {
	
	/** The canvas. */
	private HTMLCanvasElementImpl canvas;
	
	/** The list rect values. */
	private ArrayList<int[]> listRectValues;
	
	/** The list stroke rect values. */
	private ArrayList<int[]> listStrokeRectValues;
	
	/** The list text values. */
	private ArrayList<Object[]> listTextValues;
	
	/** The rect values. */
	private int[] rectValues;
	
	/** The line width. */
	private int lineWidth;	

	/**
	 * Instantiates a new dom canvas impl.
	 *
	 * @param canvas the canvas
	 */
	public DomCanvasImpl(HTMLCanvasElementImpl canvas) {
		this.canvas = canvas;
		this.lineWidth = 0;
		if (listRectValues == null) {
			listRectValues = new ArrayList<int[]>();
		}
		
		if (listStrokeRectValues == null) {
			listStrokeRectValues = new ArrayList<int[]>();
		}
		
		if (listTextValues == null) {
			listTextValues = new ArrayList<Object[]>();
		}	
	}

    @Override
    public HTMLCanvasElement getCanvas() {
        return canvas;
    }

    @Override
    public Object getFillStyle() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setFillStyle(Object arg) {
        // TODO Auto-generated method stub

    }

    @Override
    public Font getFont() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setFont(String arg) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getGlobalAlpha() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setGlobalAlpha(int arg) {
        // TODO Auto-generated method stub

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
        return this.lineWidth;
    }

    @Override
    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;

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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setStrokeStyle(Object arg) {
        // TODO Auto-generated method stub

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
    public void bezierCurveTo(int cp1x, int cp1y, int cp2x, int cp2y, int x, int y) {
    	// TODO Auto-generated method stub
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
        // TODO Auto-generated method stub

    }

    @Override
    public CanvasGradient createLinearGradient(int x0, int y0, int x1, int y1) {
        // TODO Auto-generated method stub
        return null;
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
    public void drawImageFromRect(HTMLImageElement image) {
        // TODO Auto-generated method stub

    }

    @Override
    public void drawImageFromRect(HTMLImageElement image, int sx) {
        // TODO Auto-generated method stub

    }

    @Override
    public void drawImageFromRect(HTMLImageElement image, int sx, int sy) {
        // TODO Auto-generated method stub

    }

    @Override
    public void drawImageFromRect(HTMLImageElement image, int sx, int sy, int sw) {
        // TODO Auto-generated method stub

    }

    @Override
    public void drawImageFromRect(HTMLImageElement image, int sx, int sy,
            int sw, int sh) {
        // TODO Auto-generated method stub

    }

    @Override
    public void drawImageFromRect(HTMLImageElement image, int sx, int sy,
            int sw, int sh, int dx) {
        // TODO Auto-generated method stub

    }

    @Override
    public void drawImageFromRect(HTMLImageElement image, int sx, int sy,
            int sw, int sh, int dx, int dy) {
        // TODO Auto-generated method stub

    }

    @Override
    public void drawImageFromRect(HTMLImageElement image, int sx, int sy,
            int sw, int sh, int dx, int dy, int dw) {
        // TODO Auto-generated method stub

    }

    @Override
    public void drawImageFromRect(HTMLImageElement image, int sx, int sy,
            int sw, int sh, int dx, int dy, int dw, int dh) {
        // TODO Auto-generated method stub

    }

    @Override
    public void drawImageFromRect(HTMLImageElement image, int sx, int sy,
            int sw, int sh, int dx, int dy, int dw, int dh,
            String compositeOperation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void fill() {
    	fillRect(rectValues[0], rectValues[1], rectValues[2], rectValues[3]);
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
    	
    	int[] intArray = new int[4];
    	intArray[0] = x; intArray[1] = y;
    	intArray[2] = width; intArray[3] = height;
    	    	
    	listRectValues.add(intArray);
    	canvas.setListRectValues(listRectValues);
    	canvas.setMethod(HTMLCanvasElement.FILL_RECT);
    }

    @Override
    public void fillText(String text, int x, int y) {
    	fillText(text, x, y, 0);
    }

    @Override
    public void fillText(String text, int x, int y, int maxWidth) {
    	Object[] objArray = new Object[4];
    	objArray[0] = text; objArray[1] = x;
    	objArray[2] = y; objArray[3] = maxWidth;
    	
    	listTextValues.add(objArray);
    	canvas.setListTextValues(listTextValues);
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
    	// TODO Auto-generated method stub

    }

    @Override
    public Object measureText(String text) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void moveTo(int x, int y) {
    	// TODO Auto-generated method stub
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
        // TODO Auto-generated method stub

    }

    @Override
    public void rect(int x, int y, int width, int height) {
    	rectValues = new int[4];
    	rectValues[0] = x; rectValues[1] = y;
    	rectValues[2] = width; rectValues[3] = height;
    }

    @Override
    public void restore() {
        // TODO Auto-generated method stub

    }

    @Override
    public void rotate(int angle) {
        // TODO Auto-generated method stub

    }

    @Override
    public void save() {
        // TODO Auto-generated method stub

    }

    @Override
    public void scale(int sx, int sy) {
        // TODO Auto-generated method stub

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
    public void setFillColor(String color) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFillColor(String color, int alpha) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFillColor(int grayLevel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFillColor(int grayLevel, int alpha) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFillColor(int r, int g, int b, int a) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFillColor(int c, int m, int y, int k, int a) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setShadow(int width, int height, int blur) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setShadow(int width, int height, int blur, String color) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setShadow(int width, int height, int blur, String color,
            int alpha) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setShadow(int width, int height, int blur, int grayLevel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setShadow(int width, int height, int blur, int grayLevel,
            int alpha) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setShadow(int width, int height, int blur, int r, int g, int b,
            int a) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setShadow(int width, int height, int blur, int c, int m, int y,
            int k, int a) {
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
					rectValues[3], lineWidth);
		}
	}

    @Override
    public void strokeRect(int x, int y, int width, int height) {
    	strokeRect(x, y, width, height, 0);

    }

    @Override
    public void strokeRect(int x, int y, int width, int height, int lineWidth) {
    	int[] intArray = new int[5];
    	intArray[0] = x; intArray[1] = y;
    	intArray[2] = width; intArray[3] = height;
    	intArray[4] = lineWidth;
    	    	
    	listStrokeRectValues.add(intArray);
    	canvas.setListStrokeRectValues(listStrokeRectValues);
    	canvas.setMethod(HTMLCanvasElement.STROKE_RECT);

    }

    @Override
    public void strokeText(String text, int x, int y) {
        // TODO Auto-generated method stub

    }

    @Override
    public void strokeText(String text, int x, int y, int maxWidth) {
        // TODO Auto-generated method stub

    }

    @Override
    public void transform(int m11, int m12, int m21, int m22, int dx, int dy) {
        // TODO Auto-generated method stub

    }

    @Override
    public void translate(int tx, int ty) {
        // TODO Auto-generated method stub

    }

}
