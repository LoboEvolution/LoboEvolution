/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution
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
package org.loboevolution.html.info;

import java.awt.Font;
import java.io.Serializable;

import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.svgimpl.SVGAnimationImpl;
import org.loboevolution.w3c.svg.SVGLengthList;
import org.loboevolution.w3c.svg.SVGPathSegList;
import org.loboevolution.w3c.svg.SVGPointList;
import org.loboevolution.w3c.svg.SVGTransformList;

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

	/** The fx. */
	private float fx;

	/** The fy. */
	private float fy;

	/** The method. */
	private int method;

	/** href. */
	private String href;

	/** The style. */
	private transient AbstractCSSProperties style;

	/** The poilist. */
	private transient SVGPointList poilist;

	/** The transformList. */
	private transient SVGTransformList transformList;

	/** The dyList. */
	private transient SVGLengthList dyList;

	/** The dxList. */
	private transient SVGLengthList dxList;

	/** The pathSegList. */
	private transient SVGPathSegList pathSegList;

	/** The animate. */
	private transient SVGAnimationImpl animate;

	/** The font. */
	private transient Font font;

	/** The text. */
	private String text;

	/** The textAnchor. */
	private String textAnchor;

	/** The clipPath. */
	private String clipPath;

	/** The id. */
	private String id;

	private boolean isClip;

	public SVGInfo() {
	}

	public SVGInfo(String id, int method, float x, float y, float height, float width, float rx, float ry,
			AbstractCSSProperties style, boolean isClip, String clipPath, SVGTransformList transformList,
			SVGAnimationImpl animate) {
		this.method = method;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.rx = rx;
		this.ry = ry;
		this.style = style;
		this.isClip = isClip;
		this.clipPath = clipPath;
		this.transformList = transformList;
		this.animate = animate;
		this.id = id;
	}

	public SVGInfo(String id, int method, float x, float y, float rx, float ry, AbstractCSSProperties style,
			boolean isClip, String clipPath, SVGTransformList transformList, SVGAnimationImpl animate) {
		this.method = method;
		this.x = x;
		this.y = y;
		this.rx = rx;
		this.ry = ry;
		this.style = style;
		this.isClip = isClip;
		this.clipPath = clipPath;
		this.transformList = transformList;
		this.animate = animate;
		this.id = id;
	}

	public SVGInfo(String id, int method, float x, float y, float r, AbstractCSSProperties style, boolean isClip,
			String clipPath, SVGTransformList transformList, SVGAnimationImpl animate) {
		this.method = method;
		this.x = x;
		this.y = y;
		this.r = r;
		this.style = style;
		this.isClip = isClip;
		this.clipPath = clipPath;
		this.transformList = transformList;
		this.animate = animate;
		this.id = id;
	}

	public SVGInfo(String id, int method, float x1, float y1, float x2, float y2, AbstractCSSProperties style,
			String clipPath, boolean isClip, SVGTransformList transformList, SVGAnimationImpl animate) {
		this.method = method;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.style = style;
		this.isClip = isClip;
		this.clipPath = clipPath;
		this.transformList = transformList;
		this.animate = animate;
		this.id = id;
	}

	public SVGInfo(String id, int method, SVGPointList poilist, AbstractCSSProperties style, boolean isClip,
			String clipPath, SVGTransformList transformList, SVGAnimationImpl animate) {
		this.method = method;
		this.style = style;
		this.isClip = isClip;
		this.clipPath = clipPath;
		this.transformList = transformList;
		this.poilist = poilist;
		this.animate = animate;
		this.id = id;
	}

	public SVGInfo(String id, int method, SVGPathSegList pathSegList, AbstractCSSProperties style, boolean isClip,
			String clipPath, SVGTransformList transformList, SVGAnimationImpl animate) {
		this.method = method;
		this.style = style;
		this.isClip = isClip;
		this.clipPath = clipPath;
		this.transformList = transformList;
		this.pathSegList = pathSegList;
		this.animate = animate;
		this.id = id;
	}

	public SVGInfo(AbstractCSSProperties style, SVGTransformList transformList) {
		this.style = style;
		this.transformList = transformList;
	}

	public SVGInfo(String id, int method, float x, float y, Font font, String text, String textAnchor,
			SVGLengthList dyList, SVGLengthList dxList, AbstractCSSProperties style, String clipPath, boolean isClip,
			SVGTransformList transformList, SVGAnimationImpl animate) {
		this.method = method;
		this.x = x;
		this.y = y;
		this.font = font;
		this.text = text;
		this.textAnchor = textAnchor;
		this.dyList = dyList;
		this.dxList = dxList;
		this.style = style;
		this.isClip = isClip;
		this.clipPath = clipPath;
		this.transformList = transformList;
		this.animate = animate;
		this.id = id;
	}

	public SVGInfo(int method, float x, float y, String href, AbstractCSSProperties style, String clipPath,
			boolean isClip, SVGTransformList transformList) {
		this.method = method;
		this.x = x;
		this.y = y;
		this.href = href;
		this.style = style;
		this.isClip = isClip;
		this.clipPath = clipPath;
		this.transformList = transformList;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
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
	 * @param x1
	 *            the x1 to set
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
	 * @param x2
	 *            the x2 to set
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
	 * @param y
	 *            the y to set
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
	 * @param y1
	 *            the y1 to set
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
	 * @param y2
	 *            the y2 to set
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
	 * @param r
	 *            the r to set
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
	 * @param width
	 *            the width to set
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
	 * @param height
	 *            the height to set
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
	 * @param rx
	 *            the rx to set
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
	 * @return the fx
	 */
	public float getFx() {
		return fx;
	}

	/**
	 * @param fx
	 *            the fx to set
	 */
	public void setFx(float fx) {
		this.fx = fx;
	}

	/**
	 * @return the fy
	 */
	public float getFy() {
		return fy;
	}

	/**
	 * @param fy
	 *            the fy to set
	 */
	public void setFy(float fy) {
		this.fy = fy;
	}

	/**
	 * @param ry
	 *            the ry to set
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
	 * @param method
	 *            the method to set
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
	 * @param href
	 *            the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * @return the style
	 */
	public AbstractCSSProperties getStyle() {
		return style;
	}

	/**
	 * @param style
	 *            the style to set
	 */
	public void setStyle(AbstractCSSProperties style) {
		this.style = style;
	}

	/**
	 * @return the poilist
	 */
	public SVGPointList getPoilist() {
		return poilist;
	}

	/**
	 * @param poilist
	 *            the poilist to set
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
	 * @param transformList
	 *            the transformList to set
	 */
	public void setTransformList(SVGTransformList transformList) {
		this.transformList = transformList;
	}

	/**
	 * @return the dyList
	 */
	public SVGLengthList getDyList() {
		return dyList;
	}

	/**
	 * @param dyList
	 *            the dyList to set
	 */
	public void setDyList(SVGLengthList dyList) {
		this.dyList = dyList;
	}

	/**
	 * @return the dxList
	 */
	public SVGLengthList getDxList() {
		return dxList;
	}

	/**
	 * @param dxList
	 *            the dxList to set
	 */
	public void setDxList(SVGLengthList dxList) {
		this.dxList = dxList;
	}

	/**
	 * @return the pathSegList
	 */
	public SVGPathSegList getPathSegList() {
		return pathSegList;
	}

	/**
	 * @param pathSegList
	 *            the pathSegList to set
	 */
	public void setPathSegList(SVGPathSegList pathSegList) {
		this.pathSegList = pathSegList;
	}

	/**
	 * @return the animate
	 */
	public SVGAnimationImpl getAnimate() {
		return animate;
	}

	/**
	 * @param animate
	 *            the animate to set
	 */
	public void setAnimate(SVGAnimationImpl animate) {
		this.animate = animate;
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
	 * @return the textAnchor
	 */
	public String getTextAnchor() {
		return textAnchor;
	}

	/**
	 * @param textAnchor
	 *            the textAnchor to set
	 */
	public void setTextAnchor(String textAnchor) {
		this.textAnchor = textAnchor;
	}

	/**
	 * @return the clipPath
	 */
	public String getClipPath() {
		return clipPath;
	}

	/**
	 * @param clipPath
	 *            the clipPath to set
	 */
	public void setClipPath(String clipPath) {
		this.clipPath = clipPath;
	}

	/**
	 * @return the isClip
	 */
	public boolean isClip() {
		return isClip;
	}

	/**
	 * @param isClip
	 *            the isClip to set
	 */
	public void setClip(boolean isClip) {
		this.isClip = isClip;
	}
}
