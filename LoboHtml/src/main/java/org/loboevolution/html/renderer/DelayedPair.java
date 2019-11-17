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
package org.loboevolution.html.renderer;

import java.awt.Insets;

import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.HtmlValues;

public class DelayedPair {

	private RenderableContainer containingBlock;

	private BoundableRenderable child;

	private RenderableContainer immediateContainingBlock;

	private RenderState rs;

	private String left;

	private String top;

	private String bottom;

	private String right;

	private int initX;

	private int initY;

	private String width;

	private String height;

	private boolean isFixed;

	public DelayedPair() {
	}

	public void positionPairChild() {
		final RenderableContainer parent = this.containingBlock;
		final BoundableRenderable child = this.child;
		Integer x = this.getLeft();
		Integer y = this.getTop();
	    Integer width = getWidth();
	    Integer height = getHeight();
		final Integer right = this.getRight();
		final Integer bottom = this.getBottom();
		if (right != null) {
			if (x != null) {
				width = parent.getInnerWidth() - (x + right);
			} else {
		        final int childWidth = width == null? child.getWidth() : width;
		        x = parent.getInnerWidth() - (childWidth + right);
			}
		}
		if (bottom != null) {
			if (y != null) {
				height = parent.getInnerHeight() - (y + bottom);
			} else {
		        final int childHeight = height == null? child.getHeight() : height;
		        y = parent.getInnerHeight() - (childHeight + bottom);
			}
		}

		child.setX(x == null ? initX : x);

		if (y != null) {
			child.setY(y);
		} else {
			if (this.immediateContainingBlock != parent) {
				final Insets immediateInsets = this.immediateContainingBlock.getInsets(false, false);
				child.setY((this.initY + (this.immediateContainingBlock.getY() + immediateInsets.top)) - parent.getY());
			} else {
				child.setY(this.initY);
			}
		}
		if (width != null) {
			child.setWidth(width);
		}
		if (height != null) {
			child.setHeight(height);
		}
	}

	private Integer getLeft() {
		return helperGetPixelSize(left, rs, 0, containingBlock.getInnerWidth());
	}

	private Integer getRight() {
		return helperGetPixelSize(right, rs, 0, containingBlock.getInnerWidth());
	}

	private Integer getTop() {
		return helperGetPixelSize(top, rs, 0, containingBlock.getInnerHeight());
	}

	private Integer getBottom() {
		return helperGetPixelSize(bottom, rs, 0, containingBlock.getInnerHeight());
	}

	private Integer getWidth() {
		return helperGetPixelSize(width, rs, 0, containingBlock.getInnerWidth());
	}

	private Integer getHeight() {
		return helperGetPixelSize(height, rs, 0, containingBlock.getInnerHeight());
	}

	private static Integer helperGetPixelSize(final String spec, final RenderState rs, final int errorValue,
			final int avail) {
		if (spec != null) {
			return "auto".equals(spec) ? null : HtmlValues.getPixelSize(spec, rs, errorValue, avail);
		} else {
			return null;
		}
	}

	/**
	 * @return the containingBlock
	 */
	public RenderableContainer getContainingBlock() {
		return containingBlock;
	}

	/**
	 * @return the child
	 */
	public BoundableRenderable getChild() {
		return child;
	}

	/**
	 * @return the immediateContainingBlock
	 */
	public RenderableContainer getImmediateContainingBlock() {
		return immediateContainingBlock;
	}

	/**
	 * @return the rs
	 */
	public RenderState getRs() {
		return rs;
	}

	/**
	 * @return the initX
	 */
	public int getInitX() {
		return initX;
	}

	/**
	 * @return the initY
	 */
	public int getInitY() {
		return initY;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * @return the isFixed
	 */
	public boolean isFixed() {
		return isFixed;
	}

	/**
	 * @param containingBlock the containingBlock to set
	 */
	public void setContainingBlock(RenderableContainer containingBlock) {
		this.containingBlock = containingBlock;
	}

	/**
	 * @param child the child to set
	 */
	public void setChild(BoundableRenderable child) {
		this.child = child;
	}

	/**
	 * @param immediateContainingBlock the immediateContainingBlock to set
	 */
	public void setImmediateContainingBlock(RenderableContainer immediateContainingBlock) {
		this.immediateContainingBlock = immediateContainingBlock;
	}

	/**
	 * @param rs the rs to set
	 */
	public void setRs(RenderState rs) {
		this.rs = rs;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(String left) {
		this.left = left;
	}

	/**
	 * @param top the top to set
	 */
	public void setTop(String top) {
		this.top = top;
	}

	/**
	 * @param bottom the bottom to set
	 */
	public void setBottom(String bottom) {
		this.bottom = bottom;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(String right) {
		this.right = right;
	}

	/**
	 * @param initX the initX to set
	 */
	public void setInitX(int initX) {
		this.initX = initX;
	}

	/**
	 * @param initY the initY to set
	 */
	public void setInitY(int initY) {
		this.initY = initY;
	}

	/**
	 * @param isFixed the isFixed to set
	 */
	public void setFixed(boolean isFixed) {
		this.isFixed = isFixed;
	}
}
