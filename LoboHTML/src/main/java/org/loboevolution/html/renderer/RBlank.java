/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
/*
 * Created on May 21, 2005
 */
package org.loboevolution.html.renderer;

import org.loboevolution.html.node.ModelNode;
import org.loboevolution.html.renderstate.RenderState;

import java.awt.*;

final class RBlank extends BaseBoundableRenderable {
	// TODO: Is there a need for RBlank's at all?
	public final int ascentPlusLeading;
	private final FontMetrics fontMetrics;

	/**
	 * <p>Constructor for RBlank.</p>
	 *
	 * @param me a {@link ModelNode} object.
	 * @param fm a {@link java.awt.FontMetrics} object.
	 * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 * @param ascentPlusLeading a {@link java.lang.Integer} object.
	 * @param width a {@link java.lang.Integer} object.
	 * @param height a {@link java.lang.Integer} object.
	 */
	public RBlank(final ModelNode me, final FontMetrics fm, final RenderableContainer container, final int ascentPlusLeading, final int width,
				  final int height) {
		super(container, me);
		this.fontMetrics = fm;
		this.ascentPlusLeading = ascentPlusLeading;
		this.setWidth(width);
		this.setHeight(height);
	}

	/** {@inheritDoc} */
	@Override
	public boolean extractSelectionText(final StringBuilder buffer, final boolean inSelection, final RenderableSpot startPoint,
										final RenderableSpot endPoint) {
		if (this == startPoint.renderable || this == endPoint.renderable) {
			if (inSelection) {
				return false;
			}
		} else if (!inSelection) {
			return false;
		}
		buffer.append(' ');
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public RenderableSpot getLowestRenderableSpot(final int x, final int y) {
		return new RenderableSpot(this, x, y);
	}

	/** {@inheritDoc} */
	@Override
	protected void invalidateLayoutLocal() {
	}

	/** {@inheritDoc} */
	@Override
	public boolean isContainedByNode() {
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g) {
		final RenderState rs = (RenderState)this.modelNode.getRenderState();
		final Color bkg = rs.getTextBackgroundColor();
		if (bkg != null) {
			final Color oldColor = g.getColor();
			try {
				g.setColor(bkg);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
			} finally {
				g.setColor(oldColor);
			}
		}
		final int td = rs.getTextDecorationMask();
		if (td != 0) {
			if ((td & RenderState.MASK_TEXTDECORATION_UNDERLINE) != 0) {
				final int lineOffset = this.ascentPlusLeading + 2;
				g.drawLine(0, lineOffset, this.getWidth(), lineOffset);
			}
			if ((td & RenderState.MASK_TEXTDECORATION_LINE_THROUGH) != 0) {
				final FontMetrics fm = this.fontMetrics;
				final int lineOffset = fm.getLeading() + (fm.getAscent() + fm.getDescent()) / 2;
				g.drawLine(0, lineOffset, this.getWidth(), lineOffset);
			}
			if ((td & RenderState.MASK_TEXTDECORATION_OVERLINE) != 0) {
				final int lineOffset = this.fontMetrics.getLeading();
				g.drawLine(0, lineOffset, this.getWidth(), lineOffset);
			}
			if ((td & RenderState.MASK_TEXTDECORATION_BLINK) != 0) {
				// TODO
			}
		}
		final Color over = rs.getOverlayColor();
		if (over != null) {
			final Color oldColor = g.getColor();
			try {
				g.setColor(over);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
			} finally {
				g.setColor(oldColor);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean paintSelection(final Graphics g, final boolean inSelection, final RenderableSpot startPoint, final RenderableSpot endPoint) {
		if (this == startPoint.renderable || this == endPoint.renderable) {
			if (inSelection) {
				return false;
			}
		} else if (!inSelection) {
			return false;
		}
		g.setColor(SELECTION_COLOR);
		g.setXORMode(SELECTION_XOR);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setPaintMode();
		return true;
	}
}
