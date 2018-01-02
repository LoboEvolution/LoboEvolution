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
package org.loboevolution.html.layout;

import java.awt.Font;

import org.loboevolution.html.renderer.FloatingBoundsSource;
import org.loboevolution.util.Objects;

/**
 * The Class LayoutKey.
 */
public class LayoutKey {

	/** The avail width. */
	private int availWidth;

	/** The avail height. */
	private int availHeight;

	/** The whitespace. */
	private int whitespace;

	/** The font. */
	private Font font;

	/** The override no wrap. */
	private boolean overrideNoWrap;

	/** The float bounds source. */
	private FloatingBoundsSource floatBoundsSource;

	/** The default overflow x. */
	private int defaultOverflowX;

	/** The default overflow y. */
	private int defaultOverflowY;

	/** The expand width. */
	private boolean expandWidth;

	/** The expand height. */
	private boolean expandHeight;

	/** The use declared size. */
	private boolean useDeclaredSize;

	/**
	 * Instantiates a new layout key.
	 *
	 * @param availWidth
	 *            the avail width
	 * @param availHeight
	 *            the avail height
	 */
	public LayoutKey(int availWidth, int availHeight) {
		super();
		this.availWidth = availWidth;
		this.availHeight = availHeight;
	}

	/**
	 * Instantiates a new layout key.
	 *
	 * @param availWidth
	 *            the avail width
	 * @param availHeight
	 *            the avail height
	 * @param whitespace
	 *            the whitespace
	 * @param font
	 *            the font
	 * @param overrideNoWrap
	 *            the override no wrap
	 */
	public LayoutKey(int availWidth, int availHeight, int whitespace, Font font, boolean overrideNoWrap) {
		super();
		this.availWidth = availWidth;
		this.availHeight = availHeight;
		this.whitespace = whitespace;
		this.font = font;
		this.overrideNoWrap = overrideNoWrap;
	}

	/**
	 * Instantiates a new layout key.
	 *
	 * @param availWidth
	 *            the avail width
	 * @param availHeight
	 *            the avail height
	 * @param expandWidth
	 *            the expand width
	 * @param expandHeight
	 *            the expand height
	 * @param floatBoundsSource
	 *            the float bounds source
	 * @param defaultOverflowX
	 *            the default overflow x
	 * @param defaultOverflowY
	 *            the default overflow y
	 * @param whitespace
	 *            the whitespace
	 * @param font
	 *            the font
	 * @param overrideNoWrap
	 *            the override no wrap
	 */
	public LayoutKey(int availWidth, int availHeight, boolean expandWidth, boolean expandHeight,
			FloatingBoundsSource floatBoundsSource, int defaultOverflowX, int defaultOverflowY, int whitespace,
			Font font, boolean overrideNoWrap) {
		super();
		this.availWidth = availWidth;
		this.availHeight = availHeight;
		this.floatBoundsSource = floatBoundsSource;
		this.defaultOverflowX = defaultOverflowX;
		this.defaultOverflowY = defaultOverflowY;
		this.whitespace = whitespace;
		this.font = font;
		this.expandWidth = expandWidth;
		this.expandHeight = expandHeight;
		this.useDeclaredSize = true;
		this.overrideNoWrap = overrideNoWrap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof LayoutKey)) {
			return false;
		}
		LayoutKey other = (LayoutKey) obj;
		return other.availWidth == this.availWidth && other.availHeight == this.availHeight
				&& other.defaultOverflowX == this.defaultOverflowX && other.defaultOverflowY == this.defaultOverflowY
				&& other.whitespace == this.whitespace && other.expandWidth == this.expandWidth
				&& other.expandHeight == this.expandHeight && other.useDeclaredSize == this.useDeclaredSize
				&& other.overrideNoWrap == this.overrideNoWrap && Objects.equals(other.font, this.font)
				&& Objects.equals(other.floatBoundsSource, this.floatBoundsSource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		Font font = this.font;
		return this.availWidth * 1000 + this.availHeight ^ (font == null ? 0 : font.hashCode())
				^ (this.expandWidth ? 2 : 0) ^ (this.expandHeight ? 1 : 0) ^ this.whitespace << 2;
	}

	/**
	 * Gets the avail width.
	 *
	 * @return the avail width
	 */
	public int getAvailWidth() {
		return availWidth;
	}

	/**
	 * Sets the avail width.
	 *
	 * @param availWidth
	 *            the new avail width
	 */
	public void setAvailWidth(int availWidth) {
		this.availWidth = availWidth;
	}

	/**
	 * Gets the avail height.
	 *
	 * @return the avail height
	 */
	public int getAvailHeight() {
		return availHeight;
	}

	/**
	 * Sets the avail height.
	 *
	 * @param availHeight
	 *            the new avail height
	 */
	public void setAvailHeight(int availHeight) {
		this.availHeight = availHeight;
	}

	/**
	 * Gets the whitespace.
	 *
	 * @return the whitespace
	 */
	public int getWhitespace() {
		return whitespace;
	}

	/**
	 * Sets the whitespace.
	 *
	 * @param whitespace
	 *            the new whitespace
	 */
	public void setWhitespace(int whitespace) {
		this.whitespace = whitespace;
	}

	/**
	 * Gets the font.
	 *
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * Sets the font.
	 *
	 * @param font
	 *            the new font
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * Checks if is override no wrap.
	 *
	 * @return the override no wrap
	 */
	public boolean isOverrideNoWrap() {
		return overrideNoWrap;
	}

	/**
	 * Sets the override no wrap.
	 *
	 * @param overrideNoWrap
	 *            the new override no wrap
	 */
	public void setOverrideNoWrap(boolean overrideNoWrap) {
		this.overrideNoWrap = overrideNoWrap;
	}

	/**
	 * Gets the float bounds source.
	 *
	 * @return the float bounds source
	 */
	public FloatingBoundsSource getFloatBoundsSource() {
		return floatBoundsSource;
	}

	/**
	 * Sets the float bounds source.
	 *
	 * @param floatBoundsSource
	 *            the new float bounds source
	 */
	public void setFloatBoundsSource(FloatingBoundsSource floatBoundsSource) {
		this.floatBoundsSource = floatBoundsSource;
	}

	/**
	 * Gets the default overflow x.
	 *
	 * @return the default overflow x
	 */
	public int getDefaultOverflowX() {
		return defaultOverflowX;
	}

	/**
	 * Sets the default overflow x.
	 *
	 * @param defaultOverflowX
	 *            the new default overflow x
	 */
	public void setDefaultOverflowX(int defaultOverflowX) {
		this.defaultOverflowX = defaultOverflowX;
	}

	/**
	 * Gets the default overflow y.
	 *
	 * @return the default overflow y
	 */
	public int getDefaultOverflowY() {
		return defaultOverflowY;
	}

	/**
	 * Sets the default overflow y.
	 *
	 * @param defaultOverflowY
	 *            the new default overflow y
	 */
	public void setDefaultOverflowY(int defaultOverflowY) {
		this.defaultOverflowY = defaultOverflowY;
	}

	/**
	 * Checks if is expand width.
	 *
	 * @return the expand width
	 */
	public boolean isExpandWidth() {
		return expandWidth;
	}

	/**
	 * Sets the expand width.
	 *
	 * @param expandWidth
	 *            the new expand width
	 */
	public void setExpandWidth(boolean expandWidth) {
		this.expandWidth = expandWidth;
	}

	/**
	 * Checks if is expand height.
	 *
	 * @return the expand height
	 */
	public boolean isExpandHeight() {
		return expandHeight;
	}

	/**
	 * Sets the expand height.
	 *
	 * @param expandHeight
	 *            the new expand height
	 */
	public void setExpandHeight(boolean expandHeight) {
		this.expandHeight = expandHeight;
	}

	/**
	 * Checks if is use declared size.
	 *
	 * @return the use declared size
	 */
	public boolean isUseDeclaredSize() {
		return useDeclaredSize;
	}

	/**
	 * Sets the use declared size.
	 *
	 * @param useDeclaredSize
	 *            the new use declared size
	 */
	public void setUseDeclaredSize(boolean useDeclaredSize) {
		this.useDeclaredSize = useDeclaredSize;
	}
}
