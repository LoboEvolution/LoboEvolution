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

    Contact info: lobochief@users.sourceforge.net
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
	public final int availWidth;
	
	/** The avail height. */
	public final int availHeight;
	
	/** The float bounds source. */
	public final FloatingBoundsSource floatBoundsSource;
	
	/** The default overflow x. */
	public final int defaultOverflowX;
	
	/** The default overflow y. */
	public final int defaultOverflowY;
	
	/** The whitespace. */
	public final int whitespace;
	
	/** The font. */
	public final Font font;
	
	/** The expand width. */
	public final boolean expandWidth;
	
	/** The expand height. */
	public final boolean expandHeight;
	
	/** The use declared size. */
	public final boolean useDeclaredSize;
	
	/** The override no wrap. */
	public final boolean overrideNoWrap;

	/**
	 * Instantiates a new layout key.
	 *
	 * @param availWidth the avail width
	 * @param availHeight the avail height
	 * @param expandWidth the expand width
	 * @param expandHeight the expand height
	 * @param floatBoundsSource the float bounds source
	 * @param defaultOverflowX the default overflow x
	 * @param defaultOverflowY the default overflow y
	 * @param whitespace the whitespace
	 * @param font the font
	 * @param overrideNoWrap the override no wrap
	 */
	public LayoutKey(int availWidth, int availHeight, boolean expandWidth,
			boolean expandHeight, FloatingBoundsSource floatBoundsSource,
			int defaultOverflowX, int defaultOverflowY, int whitespace,
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

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof LayoutKey)) {
			return false;
		}
		LayoutKey other = (LayoutKey) obj;
		return other.availWidth == this.availWidth
				&& other.availHeight == this.availHeight
				&& other.defaultOverflowX == this.defaultOverflowX
				&& other.defaultOverflowY == this.defaultOverflowY
				&& other.whitespace == this.whitespace
				&& other.expandWidth == this.expandWidth
				&& other.expandHeight == this.expandHeight
				&& other.useDeclaredSize == this.useDeclaredSize
				&& other.overrideNoWrap == this.overrideNoWrap
				&& Objects.equals(other.font, this.font)
				&& Objects.equals(other.floatBoundsSource,
						this.floatBoundsSource);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		Font font = this.font;
		return (this.availWidth * 1000 + this.availHeight)
				^ (font == null ? 0 : font.hashCode())
				^ (this.expandWidth ? 2 : 0) ^ (this.expandHeight ? 1 : 0)
				^ (this.whitespace << 2);
	}
}
