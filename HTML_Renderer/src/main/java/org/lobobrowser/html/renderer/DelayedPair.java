/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.renderer;

import java.awt.Insets;

import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.style.HtmlValues;

/**
 * The Class DelayedPair.
 */
public class DelayedPair {

	/** The containing block. */
	public final RenderableContainer containingBlock;

	/** The immediate containing block. */
	private final RenderableContainer immediateContainingBlock;

	/** The child. */
	public final BoundableRenderable child;

	/** The left. */
	private final String left;

	/** The top. */
	private final String top;

	/** The bottom. */
	private final String bottom;

	/** The right. */
	private final String right;

	/** The rs. */
	private final RenderState rs;

	/** The curr y. */
	private final int currY;

	public DelayedPair(final RenderableContainer immediateContainingBlock, final RenderableContainer containingBlock,
			final BoundableRenderable child, final String left, final String right, final String top,
			final String bottom, final RenderState rs, final int currY) {
		this.immediateContainingBlock = immediateContainingBlock;
		this.containingBlock = containingBlock;
		this.child = child;
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
		this.rs = rs;
		this.currY = currY;
	}

	private static Integer helperGetPixelSize(final String spec, final RenderState rs, final int errorValue,
			final int avail) {
		if (spec != null) {
			return HtmlValues.getPixelSize(spec, rs, errorValue, avail);
		} else {
			return null;
		}
	}

	/**
	 * Gets the left.
	 *
	 * @return the left
	 */
	public Integer getLeft() {
		return helperGetPixelSize(left, rs, 0, containingBlock.getInnerWidth());
	}

	/**
	 * Gets the right.
	 *
	 * @return the right
	 */
	public Integer getRight() {
		return helperGetPixelSize(right, rs, 0, containingBlock.getInnerWidth());
	}

	/**
	 * Gets the top.
	 *
	 * @return the top
	 */
	public Integer getTop() {
		return helperGetPixelSize(top, rs, 0, containingBlock.getInnerHeight());
	}

	/**
	 * Gets the bottom.
	 *
	 * @return the bottom
	 */
	public Integer getBottom() {
		return helperGetPixelSize(bottom, rs, 0, containingBlock.getInnerHeight());
	}

	public void positionPairChild() {
		final RenderableContainer parent = this.containingBlock;
		final BoundableRenderable child = this.child;
		Integer x = this.getLeft();
		Integer y = this.getTop();
		Integer width = null;
		Integer height = null;
		final Integer right = this.getRight();
		final Integer bottom = this.getBottom();
		if (right != null) {
			if (x != null) {
				width = parent.getInnerWidth() - (x + right);
			} else {
				x = parent.getInnerWidth() - (child.getWidth() + right);
			}
		}
		if (bottom != null) {
			if (y != null) {
				height = parent.getInnerHeight() - (y + bottom);
			} else {
				y = parent.getInnerHeight() - child.getHeight() - bottom;
			}
		}
		if (x != null) {
			child.setX(x);
		} else {
			child.setX(0);
		}
		if (y != null) {
			child.setY(y);
		} else {
			if (this.immediateContainingBlock != parent) {
				final Insets immediateInsets = this.immediateContainingBlock.getInsets(false, false);
				child.setY(this.currY + this.immediateContainingBlock.getY() + immediateInsets.top - parent.getY());
			} else {
				child.setY(this.currY);
			}
		}
		if (width != null) {
			child.setWidth(width);
		}
		if (height != null) {
			child.setHeight(height);
		}
	}

}
