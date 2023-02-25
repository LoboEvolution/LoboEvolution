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
		final RCollection originalParent = this.renderable.getOriginalParent();
		final RCollection rparent = renderable.getParent();
		final Point or = originalParent.getOriginRelativeTo(rparent);
		final int pos = this.renderable.getModelNode().getRenderState().getPosition();

		if (isFloat || pos == RenderState.POSITION_ABSOLUTE || pos == RenderState.POSITION_FIXED) {
			final Graphics g2 = gIn.create();
			final Point some = getSome();

			if (some != null) {
				g2.translate(some.x, some.y);
			}

			this.renderable.paintTranslated(g2);
		} else {
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
				final Insets htmlInsets = htmlBlock.getInsetsMarginBorder(htmlBlock.hasHScrollBar,
						htmlBlock.hasVScrollBar);
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
