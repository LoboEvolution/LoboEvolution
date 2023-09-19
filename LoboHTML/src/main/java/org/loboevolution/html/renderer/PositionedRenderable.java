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
import org.loboevolution.html.dom.HTMLHtmlElement;
import org.loboevolution.html.dom.nodeimpl.ModelNode;
import org.loboevolution.html.renderstate.RenderState;

import java.awt.*;

/**
 * <p>PositionedRenderable class.</p>
 */
@Data
@Builder
@AllArgsConstructor
public class PositionedRenderable implements Renderable {

	/** Constant EMPTY_ARRAY */
	public static final PositionedRenderable[] EMPTY_ARRAY = new PositionedRenderable[0];

	private BoundableRenderable renderable;

	private boolean verticalAlignable;

	private boolean isFloat;

	private boolean isFixed;

	private int ordinal;


	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics gIn) {
		final int pos = this.renderable.getModelNode().getRenderState().getPosition();
		if (isFloat || pos == RenderState.POSITION_ABSOLUTE || pos == RenderState.POSITION_FIXED) {
			final Graphics g2 = gIn.create();
			final Point some = getSome();

			if (some != null) {
				g2.translate(some.x, some.y);
			}

			this.renderable.paintTranslated(g2);
		} else {
			final RCollection originalParent = this.renderable.getOriginalParent();
			final RCollection rparent = renderable.getParent();
			final Point or = originalParent.getOriginRelativeTo(rparent);
			final Point orNoScroll = originalParent.getOriginRelativeToNoScroll(rparent);
			final Rectangle bounds = getRelativeBounds();
			Graphics g2;
			if (bounds != null) {
				final int tx = bounds.x + orNoScroll.x;
				final int ty = bounds.y + orNoScroll.y;
				g2 = gIn.create(tx, ty, bounds.width, bounds.height);
				g2.translate(-tx, -ty);
			} else {
				g2 = gIn.create();
			}

			g2.translate(or.x, or.y);

			try {
				this.renderable.paintTranslated(g2);
			} finally {
				g2.dispose();
			}
		}
	}

	private Point getSome() {
		final RCollection rparent = renderable.getParent();
		if (!isFixed && rparent.getModelNode() instanceof HTMLDocument) {
			Renderable htmlRenderable = renderable.findHtmlRenderable(rparent);
			if (htmlRenderable instanceof PositionedRenderable) {
				final PositionedRenderable htmlPR = (PositionedRenderable) htmlRenderable;
				htmlRenderable = htmlPR.renderable;
			}
			if (htmlRenderable instanceof RBlock) {
				final RBlock htmlBlock = ((RBlock) htmlRenderable);
				final Point htmlOffset = htmlBlock.bodyLayout.getOrigin();
				final Insets htmlInsets = htmlBlock.getInsetsMarginBorder(htmlBlock.hasHScrollBar, htmlBlock.hasVScrollBar);
				return new Point((int) htmlOffset.getX() - htmlInsets.left, (int) htmlOffset.getY() - htmlInsets.top);
			}
		}

		return null;
	}

	private Rectangle getRelativeBounds() {
		final RCollection origParent = this.renderable.getOriginalParent();
		RCollection current = origParent;
		Rectangle currentBounds = current.getClipBoundsWithoutInsets();
		final RCollection parent = this.renderable.getParent();
		while (current != parent) {
			current = current.getParent();
			if (current.getModelNode() instanceof HTMLHtmlElement) {
				break;
			}
			final Rectangle newBounds = current.getClipBoundsWithoutInsets();
			if (newBounds != null) {
				final Point or = origParent.getOriginRelativeToNoScroll(current);
				newBounds.translate(-or.x, -or.y);
				if (currentBounds == null) {
					currentBounds = newBounds;
				} else {
					currentBounds = currentBounds.intersection(newBounds);
				}
			}
		}
		return currentBounds;
	}

	/** {@inheritDoc} */
	@Override
	public ModelNode getModelNode() {
		return this.renderable.getModelNode();
	}
}
