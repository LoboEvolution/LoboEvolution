/*
    GNU LESSER GENERAL private LICENSE
    Copyright (C) 2006 The XAMJ Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General private
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General private License for more details.

    You should have received a copy of the GNU Lesser General private
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.style;

import java.awt.Color;

/**
 * The Class BorderInfo.
 */
public class BorderInfo {
	
	/** The top style. */
	private int topStyle;
	
	/** The left style. */
	private int leftStyle;
	
	/** The bottom style. */
	private int bottomStyle;
	
	/** The right style. */
	private int rightStyle;

	/** The top color. */
	private Color topColor;
	
	/** The left color. */
	private Color leftColor;
	
	/** The bottom color. */
	private Color bottomColor;
	
	/** The right color. */
	private Color rightColor;

	/** The insets. */
	private HtmlInsets insets;

	/**
	 * Gets the top style.
	 *
	 * @return the top style
	 */
	public int getTopStyle() {
		return topStyle;
	}

	/**
	 * Sets the top style.
	 *
	 * @param topStyle the new top style
	 */
	public void setTopStyle(int topStyle) {
		this.topStyle = topStyle;
	}

	/**
	 * Gets the left style.
	 *
	 * @return the left style
	 */
	public int getLeftStyle() {
		return leftStyle;
	}

	/**
	 * Sets the left style.
	 *
	 * @param leftStyle the new left style
	 */
	public void setLeftStyle(int leftStyle) {
		this.leftStyle = leftStyle;
	}

	/**
	 * Gets the bottom style.
	 *
	 * @return the bottom style
	 */
	public int getBottomStyle() {
		return bottomStyle;
	}

	/**
	 * Sets the bottom style.
	 *
	 * @param bottomStyle the new bottom style
	 */
	public void setBottomStyle(int bottomStyle) {
		this.bottomStyle = bottomStyle;
	}

	/**
	 * Gets the right style.
	 *
	 * @return the right style
	 */
	public int getRightStyle() {
		return rightStyle;
	}

	/**
	 * Sets the right style.
	 *
	 * @param rightStyle the new right style
	 */
	public void setRightStyle(int rightStyle) {
		this.rightStyle = rightStyle;
	}

	/**
	 * Gets the top color.
	 *
	 * @return the top color
	 */
	public Color getTopColor() {
		return topColor;
	}

	/**
	 * Sets the top color.
	 *
	 * @param topColor the new top color
	 */
	public void setTopColor(Color topColor) {
		this.topColor = topColor;
	}

	/**
	 * Gets the left color.
	 *
	 * @return the left color
	 */
	public Color getLeftColor() {
		return leftColor;
	}

	/**
	 * Sets the left color.
	 *
	 * @param leftColor the new left color
	 */
	public void setLeftColor(Color leftColor) {
		this.leftColor = leftColor;
	}

	/**
	 * Gets the bottom color.
	 *
	 * @return the bottom color
	 */
	public Color getBottomColor() {
		return bottomColor;
	}

	/**
	 * Sets the bottom color.
	 *
	 * @param bottomColor the new bottom color
	 */
	public void setBottomColor(Color bottomColor) {
		this.bottomColor = bottomColor;
	}

	/**
	 * Gets the right color.
	 *
	 * @return the right color
	 */
	public Color getRightColor() {
		return rightColor;
	}

	/**
	 * Sets the right color.
	 *
	 * @param rightColor the new right color
	 */
	public void setRightColor(Color rightColor) {
		this.rightColor = rightColor;
	}

	/**
	 * Gets the insets.
	 *
	 * @return the insets
	 */
	public HtmlInsets getInsets() {
		return insets;
	}

	/**
	 * Sets the insets.
	 *
	 * @param insets the new insets
	 */
	public void setInsets(HtmlInsets insets) {
		this.insets = insets;
	}
	
	
}
