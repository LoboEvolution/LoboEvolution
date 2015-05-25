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

	private ArrayList<int[]> listRectValues;
	private ArrayList<int[]> listStrokeRectValues;
	private ArrayList<Object[]> listTextValues;
	private GeneralPath path;
	private Color color;
	private int method;

	/**
	 * Instantiates a new HTML canvas element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLCanvasElementImpl(String name) {
		super(name);
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
		return new DomCanvasImpl(this);
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

}
