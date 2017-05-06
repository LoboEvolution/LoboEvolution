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
package org.lobobrowser.html.info;

import java.awt.Font;
import java.io.Serializable;

import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.w3c.svg.SVGPathSegList;
import org.lobobrowser.w3c.svg.SVGPointList;
import org.lobobrowser.w3c.svg.SVGTransformList;

public class SVGInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The x. */
	private float x;
	
	/** The x1. */
	private float x1;

	/** The x2. */
	private float x2;

	/** The y. */
	private float y;

	/** The y1. */
	private float y1;
	
	/** The y2. */
	private float y2;

	/** The r. */
	private float r;

	/** The width. */
	private float width;

	/** The height. */
	private float height;

	/** The rx. */
	private float rx;

	/** The ry. */
	private float ry;

	/** The method. */
	private int method;
	
	/** href. */
	private String href;
	
	/** The style. */
	private AbstractCSS2Properties style;
	
	/** The poilist. */
	private SVGPointList poilist;
	
	/** The transformList. */
	private SVGTransformList transformList;
	
	/** The pathSegList. */
	private SVGPathSegList pathSegList;
	
	/** The font. */
	private Font font;
	
	/** The text. */
	private String text;
	
	/** The textAnchor. */
	private String textAnchor;

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return the x1
	 */
	public float getX1() {
		return x1;
	}

	/**
	 * @param x1 the x1 to set
	 */
	public void setX1(float x1) {
		this.x1 = x1;
	}

	/**
	 * @return the x2
	 */
	public float getX2() {
		return x2;
	}

	/**
	 * @param x2 the x2 to set
	 */
	public void setX2(float x2) {
		this.x2 = x2;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @return the y1
	 */
	public float getY1() {
		return y1;
	}

	/**
	 * @param y1 the y1 to set
	 */
	public void setY1(float y1) {
		this.y1 = y1;
	}

	/**
	 * @return the y2
	 */
	public float getY2() {
		return y2;
	}

	/**
	 * @param y2 the y2 to set
	 */
	public void setY2(float y2) {
		this.y2 = y2;
	}

	/**
	 * @return the r
	 */
	public float getR() {
		return r;
	}

	/**
	 * @param r the r to set
	 */
	public void setR(float r) {
		this.r = r;
	}

	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * @return the rx
	 */
	public float getRx() {
		return rx;
	}

	/**
	 * @param rx the rx to set
	 */
	public void setRx(float rx) {
		this.rx = rx;
	}

	/**
	 * @return the ry
	 */
	public float getRy() {
		return ry;
	}

	/**
	 * @param ry the ry to set
	 */
	public void setRy(float ry) {
		this.ry = ry;
	}

	/**
	 * @return the method
	 */
	public int getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(int method) {
		this.method = method;
	}
	
	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * @return the style
	 */
	public AbstractCSS2Properties getStyle() {
		return style;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(AbstractCSS2Properties style) {
		this.style = style;
	}

	/**
	 * @return the poilist
	 */
	public SVGPointList getPoilist() {
		return poilist;
	}

	/**
	 * @param poilist the poilist to set
	 */
	public void setPoilist(SVGPointList poilist) {
		this.poilist = poilist;
	}

	/**
	 * @return the transformList
	 */
	public SVGTransformList getTransformList() {
		return transformList;
	}

	/**
	 * @param transformList the transformList to set
	 */
	public void setTransformList(SVGTransformList transformList) {
		this.transformList = transformList;
	}

	/**
	 * @return the pathSegList
	 */
	public SVGPathSegList getPathSegList() {
		return pathSegList;
	}

	/**
	 * @param pathSegList the pathSegList to set
	 */
	public void setPathSegList(SVGPathSegList pathSegList) {
		this.pathSegList = pathSegList;
	}

	/**
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * @param font the font to set
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the textAnchor
	 */
	public String getTextAnchor() {
		return textAnchor;
	}

	/**
	 * @param textAnchor the textAnchor to set
	 */
	public void setTextAnchor(String textAnchor) {
		this.textAnchor = textAnchor;
	}
}
