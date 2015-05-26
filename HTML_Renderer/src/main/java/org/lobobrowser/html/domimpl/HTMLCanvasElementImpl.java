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

	/** The method. */
	private int method;

	/** The line width. */
	private int lineWidth;

	/**
	 * Instantiates a new HTML canvas element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLCanvasElementImpl(String name) {
		super(name);
		lineWidth = 1;
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
	 * Gets the list rect values.
	 *
	 * @return the listRectValues
	 */
	public ArrayList<int[]> getListRectValues() {
		return listRectValues;
	}

	/**
	 * Sets the list rect values.
	 *
	 * @param listRectValues
	 *            the listRectValues to set
	 */
	public void setListRectValues(ArrayList<int[]> listRectValues) {
		this.listRectValues = listRectValues;
	}

	/**
	 * Gets the list stroke rect values.
	 *
	 * @return the listStrokeRectValues
	 */
	public ArrayList<int[]> getListStrokeRectValues() {
		return listStrokeRectValues;
	}

	/**
	 * Sets the list stroke rect values.
	 *
	 * @param listStrokeRectValues
	 *            the listStrokeRectValues to set
	 */
	public void setListStrokeRectValues(ArrayList<int[]> listStrokeRectValues) {
		this.listStrokeRectValues = listStrokeRectValues;
	}

	/**
	 * Gets the list text values.
	 *
	 * @return the listTextValues
	 */
	public ArrayList<Object[]> getListTextValues() {
		return listTextValues;
	}

	/**
	 * Sets the list text values.
	 *
	 * @param listTextValues
	 *            the listTextValues to set
	 */
	public void setListTextValues(ArrayList<Object[]> listTextValues) {
		this.listTextValues = listTextValues;
	}

	/**
	 * Gets the linear values.
	 *
	 * @return the linearValues
	 */
	public Double[] getLinearValues() {
		return linearValues;
	}

	/**
	 * Sets the linear values.
	 *
	 * @param linearValues
	 *            the linearValues to set
	 */
	public void setLinearValues(Double[] linearValues) {
		this.linearValues = linearValues;
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
	 * Gets the path.
	 *
	 * @return the path
	 */
	public GeneralPath getPath() {
		return path;
	}

	/**
	 * Sets the path.
	 *
	 * @param path
	 *            the path to set
	 */
	public void setPath(GeneralPath path) {
		this.path = path;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color.
	 *
	 * @param color
	 *            the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Gets the colors.
	 *
	 * @return the colors
	 */
	public Color[] getColors() {
		return colors;
	}

	/**
	 * Sets the colors.
	 *
	 * @param colors
	 *            the colors to set
	 */
	public void setColors(Color[] colors) {
		this.colors = colors;
	}

	/**
	 * Gets the fractions.
	 *
	 * @return the fractions
	 */
	public float[] getFractions() {
		return fractions;
	}

	/**
	 * Sets the fractions.
	 *
	 * @param fractions
	 *            the fractions to set
	 */
	public void setFractions(float[] fractions) {
		this.fractions = fractions;
	}

}
