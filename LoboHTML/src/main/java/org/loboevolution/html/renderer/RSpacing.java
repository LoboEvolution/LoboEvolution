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

import java.awt.*;

/**
 * This is used when there's padding or margins in inline elements.
 */
final class RSpacing extends BaseBoundableRenderable {
	/**
	 * <p>Constructor for RSpacing.</p>
	 *
	 * @param me a {@link org.loboevolution.html.node.ModelNode} object.
	 * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 * @param width a {@link java.lang.Integer} object.
	 * @param height a {@link java.lang.Integer} object.
	 */
	public RSpacing(final ModelNode me, final RenderableContainer container, final int width, final int height) {
		super(container, me);
		this.setWidth(width);
		this.setHeight(height);
	}

	/** {@inheritDoc} */
	@Override
	public boolean extractSelectionText(final StringBuilder buffer, final boolean inSelection, final RenderableSpot startPoint,
                                        final RenderableSpot endPoint) {
		if (this == startPoint.renderable || this == endPoint.renderable) {
            return !inSelection;
		} else return inSelection;
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
		// Nothing to paint in spacing.
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
