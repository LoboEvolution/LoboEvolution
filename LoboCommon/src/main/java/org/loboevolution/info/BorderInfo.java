/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
package org.loboevolution.info;

import java.awt.Color;

public class BorderInfo {
	
	private Color bottomColor;
	
	private Color leftColor;

	private Color rightColor;
	
	private Color topColor;
	
	private Object insets;
	
	private int leftStyle;
	
	private int bottomStyle;
	
	private int rightStyle;
	
	private int topStyle;

	/**
	 * @return the bottomColor
	 */
	public Color getBottomColor() {
		return bottomColor;
	}

	/**
	 * @return the leftColor
	 */
	public Color getLeftColor() {
		return leftColor;
	}

	/**
	 * @return the rightColor
	 */
	public Color getRightColor() {
		return rightColor;
	}

	/**
	 * @return the topColor
	 */
	public Color getTopColor() {
		return topColor;
	}

	/**
	 * @return the insets
	 */
	public Object getInsets() {
		return insets;
	}

	/**
	 * @return the leftStyle
	 */
	public int getLeftStyle() {
		return leftStyle;
	}

	/**
	 * @return the bottomStyle
	 */
	public int getBottomStyle() {
		return bottomStyle;
	}

	/**
	 * @return the rightStyle
	 */
	public int getRightStyle() {
		return rightStyle;
	}

	/**
	 * @return the topStyle
	 */
	public int getTopStyle() {
		return topStyle;
	}

	/**
	 * @param bottomColor the bottomColor to set
	 */
	public void setBottomColor(Color bottomColor) {
		this.bottomColor = bottomColor;
	}

	/**
	 * @param leftColor the leftColor to set
	 */
	public void setLeftColor(Color leftColor) {
		this.leftColor = leftColor;
	}

	/**
	 * @param rightColor the rightColor to set
	 */
	public void setRightColor(Color rightColor) {
		this.rightColor = rightColor;
	}

	/**
	 * @param topColor the topColor to set
	 */
	public void setTopColor(Color topColor) {
		this.topColor = topColor;
	}

	/**
	 * @param insets the insets to set
	 */
	public void setInsets(Object insets) {
		this.insets = insets;
	}

	/**
	 * @param leftStyle the leftStyle to set
	 */
	public void setLeftStyle(int leftStyle) {
		this.leftStyle = leftStyle;
	}

	/**
	 * @param bottomStyle the bottomStyle to set
	 */
	public void setBottomStyle(int bottomStyle) {
		this.bottomStyle = bottomStyle;
	}

	/**
	 * @param rightStyle the rightStyle to set
	 */
	public void setRightStyle(int rightStyle) {
		this.rightStyle = rightStyle;
	}

	/**
	 * @param topStyle the topStyle to set
	 */
	public void setTopStyle(int topStyle) {
		this.topStyle = topStyle;
	}	
	

}
