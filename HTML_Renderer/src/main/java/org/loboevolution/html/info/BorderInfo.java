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

import java.awt.Color;
import java.io.Serializable;

import org.loboevolution.html.style.HtmlInsets;

/**
 * The Class BorderInfo.
 */
public class BorderInfo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8796719780439373804L;

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
	private transient HtmlInsets insets;

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
	 * @param topStyle
	 *            the new top style
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
	 * @param leftStyle
	 *            the new left style
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
	 * @param bottomStyle
	 *            the new bottom style
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
	 * @param rightStyle
	 *            the new right style
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
		Color c = this.topColor;
		return c == null ? Color.LIGHT_GRAY : c;
	}

	/**
	 * Sets the top color.
	 *
	 * @param topColor
	 *            the new top color
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
		Color c = this.leftColor;
		return c == null ? Color.LIGHT_GRAY : c;
	}

	/**
	 * Sets the left color.
	 *
	 * @param leftColor
	 *            the new left color
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
		Color c = this.bottomColor;
		return c == null ? Color.LIGHT_GRAY : c;
	}

	/**
	 * Sets the bottom color.
	 *
	 * @param bottomColor
	 *            the new bottom color
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
		Color c = this.rightColor;
		return c == null ? Color.LIGHT_GRAY : c;
	}

	/**
	 * Sets the right color.
	 *
	 * @param rightColor
	 *            the new right color
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
	 * @param insets
	 *            the new insets
	 */
	public void setInsets(HtmlInsets insets) {
		this.insets = insets;
	}

}
