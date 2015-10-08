/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.layout;

import java.awt.Font;

import org.lobobrowser.html.renderer.FloatingBoundsSource;
import org.lobobrowser.util.Objects;

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
		return (other.availWidth == this.availWidth) && (other.availHeight == this.availHeight)
				&& (other.defaultOverflowX == this.defaultOverflowX)
				&& (other.defaultOverflowY == this.defaultOverflowY) && (other.whitespace == this.whitespace)
				&& (other.expandWidth == this.expandWidth) && (other.expandHeight == this.expandHeight)
				&& (other.useDeclaredSize == this.useDeclaredSize) && (other.overrideNoWrap == this.overrideNoWrap)
				&& Objects.equals(other.font, this.font)
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
		return ((this.availWidth * 1000) + this.availHeight) ^ (font == null ? 0 : font.hashCode())
				^ (this.expandWidth ? 2 : 0) ^ (this.expandHeight ? 1 : 0) ^ (this.whitespace << 2);
	}

	/**
	 * @return the availWidth
	 */
	public int getAvailWidth() {
		return availWidth;
	}

	/**
	 * @param availWidth
	 *            the availWidth to set
	 */
	public void setAvailWidth(int availWidth) {
		this.availWidth = availWidth;
	}

	/**
	 * @return the availHeight
	 */
	public int getAvailHeight() {
		return availHeight;
	}

	/**
	 * @param availHeight
	 *            the availHeight to set
	 */
	public void setAvailHeight(int availHeight) {
		this.availHeight = availHeight;
	}

	/**
	 * @return the whitespace
	 */
	public int getWhitespace() {
		return whitespace;
	}

	/**
	 * @param whitespace
	 *            the whitespace to set
	 */
	public void setWhitespace(int whitespace) {
		this.whitespace = whitespace;
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
	 * @return the overrideNoWrap
	 */
	public boolean isOverrideNoWrap() {
		return overrideNoWrap;
	}

	/**
	 * @param overrideNoWrap
	 *            the overrideNoWrap to set
	 */
	public void setOverrideNoWrap(boolean overrideNoWrap) {
		this.overrideNoWrap = overrideNoWrap;
	}

	/**
	 * @return the floatBoundsSource
	 */
	public FloatingBoundsSource getFloatBoundsSource() {
		return floatBoundsSource;
	}

	/**
	 * @param floatBoundsSource
	 *            the floatBoundsSource to set
	 */
	public void setFloatBoundsSource(FloatingBoundsSource floatBoundsSource) {
		this.floatBoundsSource = floatBoundsSource;
	}

	/**
	 * @return the defaultOverflowX
	 */
	public int getDefaultOverflowX() {
		return defaultOverflowX;
	}

	/**
	 * @param defaultOverflowX
	 *            the defaultOverflowX to set
	 */
	public void setDefaultOverflowX(int defaultOverflowX) {
		this.defaultOverflowX = defaultOverflowX;
	}

	/**
	 * @return the defaultOverflowY
	 */
	public int getDefaultOverflowY() {
		return defaultOverflowY;
	}

	/**
	 * @param defaultOverflowY
	 *            the defaultOverflowY to set
	 */
	public void setDefaultOverflowY(int defaultOverflowY) {
		this.defaultOverflowY = defaultOverflowY;
	}

	/**
	 * @return the expandWidth
	 */
	public boolean isExpandWidth() {
		return expandWidth;
	}

	/**
	 * @param expandWidth
	 *            the expandWidth to set
	 */
	public void setExpandWidth(boolean expandWidth) {
		this.expandWidth = expandWidth;
	}

	/**
	 * @return the expandHeight
	 */
	public boolean isExpandHeight() {
		return expandHeight;
	}

	/**
	 * @param expandHeight
	 *            the expandHeight to set
	 */
	public void setExpandHeight(boolean expandHeight) {
		this.expandHeight = expandHeight;
	}

	/**
	 * @return the useDeclaredSize
	 */
	public boolean isUseDeclaredSize() {
		return useDeclaredSize;
	}

	/**
	 * @param useDeclaredSize
	 *            the useDeclaredSize to set
	 */
	public void setUseDeclaredSize(boolean useDeclaredSize) {
		this.useDeclaredSize = useDeclaredSize;
	}
}
