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

public class LayoutKey {
	public final int availWidth;
	public final int availHeight;
	public final FloatingBoundsSource floatBoundsSource;
	public final int defaultOverflowX;
	public final int defaultOverflowY;
	public final int whitespace;
	public final Font font;
	public final boolean expandWidth;
	public final boolean expandHeight;
	public final boolean useDeclaredSize;
	public final boolean overrideNoWrap;

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

	public int hashCode() {
		Font font = this.font;
		return (this.availWidth * 1000 + this.availHeight)
				^ (font == null ? 0 : font.hashCode())
				^ (this.expandWidth ? 2 : 0) ^ (this.expandHeight ? 1 : 0)
				^ (this.whitespace << 2);
	}
}
