/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

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

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;

import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.HTMLHtmlElement;
import org.loboevolution.html.dom.domimpl.ModelNode;
import org.loboevolution.html.renderstate.RenderState;

public class PositionedRenderable implements Renderable {

	public static final PositionedRenderable[] EMPTY_ARRAY = new PositionedRenderable[0];

	private BoundableRenderable renderable;

	private boolean verticalAlignable;

	private boolean isFloat;

	private boolean isFixed;

	private int ordinal;

	public PositionedRenderable() {
	}

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

	@Override
	public ModelNode getModelNode() {
		return this.renderable.getModelNode();
	}

	/**
	 * @return the renderable
	 */
	public BoundableRenderable getRenderable() {
		return renderable;
	}

	/**
	 * @return the verticalAlignable
	 */
	public boolean isVerticalAlignable() {
		return verticalAlignable;
	}

	/**
	 * @return the isFloat
	 */
	public boolean isFloat() {
		return isFloat;
	}

	/**
	 * @return the isFixed
	 */
	public boolean isFixed() {
		return isFixed;
	}

	/**
	 * @return the ordinal
	 */
	public int getOrdinal() {
		return ordinal;
	}

	/**
	 * @param renderable the renderable to set
	 */
	public void setRenderable(BoundableRenderable renderable) {
		this.renderable = renderable;
	}

	/**
	 * @param verticalAlignable the verticalAlignable to set
	 */
	public void setVerticalAlignable(boolean verticalAlignable) {
		this.verticalAlignable = verticalAlignable;
	}

	/**
	 * @param isFloat the isFloat to set
	 */
	public void setFloat(boolean isFloat) {
		this.isFloat = isFloat;
	}

	/**
	 * @param isFixed the isFixed to set
	 */
	public void setFixed(boolean isFixed) {
		this.isFixed = isFixed;
	}

	/**
	 * @param ordinal the ordinal to set
	 */
	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}
}