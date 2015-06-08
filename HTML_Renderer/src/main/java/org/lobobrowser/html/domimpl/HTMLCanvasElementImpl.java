/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.domimpl;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.w3c.CanvasRenderingContext;
import org.lobobrowser.html.w3c.HTMLCanvasElement;

/**
 * The Class HTMLCanvasElementImpl.
 */
public class HTMLCanvasElementImpl extends HTMLAbstractUIElement implements
		HTMLCanvasElement {

	/** The list rect values. */
	private ArrayList<int[]> listRectValues;

	/** The list stroke rect values. */
	private ArrayList<int[]> listStrokeRectValues;

	/** The list text values. */
	private ArrayList<Object[]> listTextValues;

	/** The list stroke text values. */
	private ArrayList<Object[]> listStrokeTextValues;

	/** The linear values. */
	private Double[] linearValues;

	/** The fractions. */
	private float[] fractions;

	/** The colors. */
	private Color[] colors;

	/** The path. */
	private GeneralPath path;

	/** The color. */
	private Color color;

	/** The color. */
	private Font font;

	/** The global alpha. */
	private Double globalAlpha;

	/** The method. */
	private int method;

	/** The line width. */
	private int lineWidth;
	
	/** The rotate. */
	private double rotate;
	
	/** The Scale x. */
	private int scaleX;
	
	/** The Scale Y. */
	private int scaleY;
	
	/** The Translate x. */
	private int translateX;
		
	/** The Translate Y. */
	private int translateY;
	
	/** The Line Cap. */
	private String lineCap; 
	
	/** The Line Join. */
	private String lineJoin;
	
	/** The Miter Limit. */
	private int miterLimit;

	/**
	 * Instantiates a new HTML canvas element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLCanvasElementImpl(String name) {
		super(name);
		lineWidth = 1;
		globalAlpha = 0.5;
		rotate = 0.0;
		scaleX = 1;
		scaleY = 1;
		translateX = 0;
		translateY = 0;
		miterLimit = 0;
	}

	@Override
	public int getWidth() {
		String widthText = this.getAttribute(HtmlAttributeProperties.WIDTH);
		if (widthText == null) {
			return 0;
		}
		try {
			return Integer.parseInt(widthText);
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}

	@Override
	public void setWidth(int width) {
		this.setAttribute(HtmlAttributeProperties.WIDTH, String.valueOf(width));
	}

	@Override
	public int getHeight() {
		String heightText = this.getAttribute(HtmlAttributeProperties.HEIGHT);
		if (heightText == null) {
			return 0;
		}
		try {
			return Integer.parseInt(heightText);
		} catch (NumberFormatException nfe) {
			return 0;
		}
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
		return new DOMCanvasImpl(this);
	}

	/**
	 * @return the listRectValues
	 */
	public ArrayList<int[]> getListRectValues() {
		return listRectValues;
	}

	/**
	 * @param listRectValues
	 *            the listRectValues to set
	 */
	public void setListRectValues(ArrayList<int[]> listRectValues) {
		this.listRectValues = listRectValues;
	}

	/**
	 * @return the listStrokeRectValues
	 */
	public ArrayList<int[]> getListStrokeRectValues() {
		return listStrokeRectValues;
	}

	/**
	 * @param listStrokeRectValues
	 *            the listStrokeRectValues to set
	 */
	public void setListStrokeRectValues(ArrayList<int[]> listStrokeRectValues) {
		this.listStrokeRectValues = listStrokeRectValues;
	}

	/**
	 * @return the listTextValues
	 */
	public ArrayList<Object[]> getListTextValues() {
		return listTextValues;
	}

	/**
	 * @param listTextValues
	 *            the listTextValues to set
	 */
	public void setListTextValues(ArrayList<Object[]> listTextValues) {
		this.listTextValues = listTextValues;
	}

	/**
	 * @return the listStrokeTextValues
	 */
	public ArrayList<Object[]> getListStrokeTextValues() {
		return listStrokeTextValues;
	}

	/**
	 * @param listStrokeTextValues
	 *            the listStrokeTextValues to set
	 */
	public void setListStrokeTextValues(ArrayList<Object[]> listStrokeTextValues) {
		this.listStrokeTextValues = listStrokeTextValues;
	}

	/**
	 * @return the linearValues
	 */
	public Double[] getLinearValues() {
		return linearValues;
	}

	/**
	 * @param linearValues
	 *            the linearValues to set
	 */
	public void setLinearValues(Double[] linearValues) {
		this.linearValues = linearValues;
	}

	/**
	 * @return the fractions
	 */
	public float[] getFractions() {
		return fractions;
	}

	/**
	 * @param fractions
	 *            the fractions to set
	 */
	public void setFractions(float[] fractions) {
		this.fractions = fractions;
	}

	/**
	 * @return the colors
	 */
	public Color[] getColors() {
		return colors;
	}

	/**
	 * @param colors
	 *            the colors to set
	 */
	public void setColors(Color[] colors) {
		this.colors = colors;
	}

	/**
	 * @return the path
	 */
	public GeneralPath getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(GeneralPath path) {
		this.path = path;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
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
	 * @return the globalAlpha
	 */
	public Double getGlobalAlpha() {
		return globalAlpha;
	}

	/**
	 * @param globalAlpha
	 *            the globalAlpha to set
	 */
	public void setGlobalAlpha(Double globalAlpha) {
		this.globalAlpha = globalAlpha;
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
	 * @return the rotate
	 */
	public double getRotate() {
		return rotate;
	}

	/**
	 * @param rotate the rotate to set
	 */
	public void setRotate(double rotate) {
		this.rotate = rotate;
	}
	
	/**
	 * @return the scaleX
	 */
	public int getScaleX() {
		return scaleX;
	}

	/**
	 * @param scaleX the scaleX to set
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
	 * @param scaleY the scaleY to set
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
	 * @param translateX the translateX to set
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
	 * @param translateY the translateY to set
	 */
	public void setTranslateY(int translateY) {
		this.translateY = translateY;
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
}
