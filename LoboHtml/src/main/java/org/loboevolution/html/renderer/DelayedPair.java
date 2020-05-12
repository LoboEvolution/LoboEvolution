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
import java.awt.Point;

import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.HtmlValues;

/**
 * <p>DelayedPair class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
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

	/**
	 * <p>Constructor for DelayedPair.</p>
	 */
	public DelayedPair() {
	}

	/**
	 * <p>positionPairChild.</p>
	 */
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

	    final Point tp = containingBlock.translateDescendentPoint((BoundableRenderable)(immediateContainingBlock), initX, initY);
	    if (this.immediateContainingBlock != parent) {
	        final Insets immediateInsets = this.immediateContainingBlock.getInsetsMarginBorder(false, false);
	        tp.translate(immediateInsets.left, immediateInsets.top);
	    }

	    child.setX((x == null ? tp.x : x));
	    child.setY((y == null ? tp.y : y));

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
	 * <p>Getter for the field containingBlock.</p>
	 *
	 * @return the containingBlock
	 */
	public RenderableContainer getContainingBlock() {
		return containingBlock;
	}

	/**
	 * <p>Getter for the field child.</p>
	 *
	 * @return the child
	 */
	public BoundableRenderable getChild() {
		return child;
	}

	/**
	 * <p>Getter for the field immediateContainingBlock.</p>
	 *
	 * @return the immediateContainingBlock
	 */
	public RenderableContainer getImmediateContainingBlock() {
		return immediateContainingBlock;
	}

	/**
	 * <p>Getter for the field rs.</p>
	 *
	 * @return the rs
	 */
	public RenderState getRs() {
		return rs;
	}

	/**
	 * <p>Getter for the field initX.</p>
	 *
	 * @return the initX
	 */
	public int getInitX() {
		return initX;
	}

	/**
	 * <p>Getter for the field initY.</p>
	 *
	 * @return the initY
	 */
	public int getInitY() {
		return initY;
	}

	/**
	 * <p>Setter for the field width.</p>
	 *
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * <p>Setter for the field height.</p>
	 *
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * <p>isFixed.</p>
	 *
	 * @return the isFixed
	 */
	public boolean isFixed() {
		return isFixed;
	}

	/**
	 * <p>Setter for the field containingBlock.</p>
	 *
	 * @param containingBlock the containingBlock to set
	 */
	public void setContainingBlock(RenderableContainer containingBlock) {
		this.containingBlock = containingBlock;
	}

	/**
	 * <p>Setter for the field child.</p>
	 *
	 * @param child the child to set
	 */
	public void setChild(BoundableRenderable child) {
		this.child = child;
	}

	/**
	 * <p>Setter for the field immediateContainingBlock.</p>
	 *
	 * @param immediateContainingBlock the immediateContainingBlock to set
	 */
	public void setImmediateContainingBlock(RenderableContainer immediateContainingBlock) {
		this.immediateContainingBlock = immediateContainingBlock;
	}

	/**
	 * <p>Setter for the field rs.</p>
	 *
	 * @param rs the rs to set
	 */
	public void setRs(RenderState rs) {
		this.rs = rs;
	}

	/**
	 * <p>Setter for the field left.</p>
	 *
	 * @param left the left to set
	 */
	public void setLeft(String left) {
		this.left = left;
	}

	/**
	 * <p>Setter for the field top.</p>
	 *
	 * @param top the top to set
	 */
	public void setTop(String top) {
		this.top = top;
	}

	/**
	 * <p>Setter for the field bottom.</p>
	 *
	 * @param bottom the bottom to set
	 */
	public void setBottom(String bottom) {
		this.bottom = bottom;
	}

	/**
	 * <p>Setter for the field right.</p>
	 *
	 * @param right the right to set
	 */
	public void setRight(String right) {
		this.right = right;
	}

	/**
	 * <p>Setter for the field initX.</p>
	 *
	 * @param initX the initX to set
	 */
	public void setInitX(int initX) {
		this.initX = initX;
	}

	/**
	 * <p>Setter for the field initY.</p>
	 *
	 * @param initY the initY to set
	 */
	public void setInitY(int initY) {
		this.initY = initY;
	}

	/**
	 * <p>setFixed.</p>
	 *
	 * @param isFixed the isFixed to set
	 */
	public void setFixed(boolean isFixed) {
		this.isFixed = isFixed;
	}
}
