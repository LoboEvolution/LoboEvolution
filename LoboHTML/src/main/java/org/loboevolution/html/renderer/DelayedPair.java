/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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

	private Integer width;

	private Integer height;

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
				x = parent.getInnerWidth() - (childWidth + right);

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
				y = parent.getInnerHeight()  - ((childHeight * 2) + bottom);
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
		return width;
	}

	private Integer getHeight() {
		return height;
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
