/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.renderer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.nodeimpl.ModelNode;
import org.loboevolution.html.node.js.Window;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.HtmlValues;

import java.awt.*;

/**
 * <p>DelayedPair class.</p>
 */
@Data
@Builder
@AllArgsConstructor
public class DelayedPair {

	private RenderableContainer containingBlock;

	private BoundableRenderable child;

	private RenderableContainer immediateContainingBlock;

	private ModelNode modelNode;

	private RenderState rs;

	private String left;

	private String top;

	private String bottom;

	private String right;

	private int initX;

	private int initY;

	private String width;

	private String height;

	private int availContentWidth;

	private int availContentHeight;

	private boolean isFixed;

	private boolean isRelative;

	/**
	 * <p>positionPairChild.</p>
	 *
	 * @return a {@link org.loboevolution.html.renderer.BoundableRenderable} object.
	 */
	public BoundableRenderable positionPairChild() {
		final RenderableContainer parent = this.containingBlock;
		if (isRelative) {
			final RElement rChild = (RElement) this.child;
			rChild.setupRelativePosition(this.immediateContainingBlock);
			TranslatedRenderable tr = new TranslatedRenderable(rChild);
			rChild.setDelegator(tr);
			return tr;
		}

		final BoundableRenderable child = this.child;
		final Point tp = immediateContainingBlock.getOriginRelativeToAbs((RCollection) parent);
		tp.translate(initX, initY);

		if (this.immediateContainingBlock != parent) {
			final Insets immediateInsets = this.immediateContainingBlock.getInsetsMarginBorder(false, false);
			tp.translate(immediateInsets.left, immediateInsets.top);
		}

		Integer x = this.getLeft();
		Integer y = this.getTop();

		final Integer width = getWidth();
		final Integer height = getHeight();
		final Integer right = this.getRight();
		final Integer bottom = this.getBottom();

		if (right != null) {
			if (x != null) {
				child.setWidth(parent.getInnerWidth() - (x + right));
			} else {
				if (width != null) {
					child.setWidth(width);
				}
				final int childWidth = child.getWidth();
				x = availContentWidth - (childWidth + right);
			}
		} else {
			if (width != null) {
				child.setWidth(width);
			}
		}

		if (bottom != null) {
			if (y != null) {
				child.setHeight(parent.getInnerHeight() - (y + bottom));
			} else {
				if (height != null) {
					child.setHeight(height);
				}
				final int childHeight = child.getHeight();
				y = availContentHeight - (childHeight + bottom);
			}
		} else {
			if (height != null) {
				child.setHeight(height);
			}
		}

		child.setX((x == null ? tp.x : x));
		child.setY((y == null ? tp.y : y));

		return child;
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

	private Integer helperGetPixelSize(final String spec, final RenderState rs, final int errorValue,
			final int avail) {
		if (spec != null) {
			Window window = null;
			ModelNode node  = getModelNode();
			if (node instanceof HTMLDocument) {
				HTMLDocumentImpl doc = (HTMLDocumentImpl)node;
				window = doc.getDefaultView();
			}
			return "auto".equals(spec) ? null : HtmlValues.getPixelSize(spec, rs, window, errorValue, avail);
		} else {
			return null;
		}
	}
}
