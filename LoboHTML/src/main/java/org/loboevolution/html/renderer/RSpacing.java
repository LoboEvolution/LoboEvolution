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
/*
 * Created on May 21, 2005
 */
package org.loboevolution.html.renderer;

import org.loboevolution.html.dom.nodeimpl.ModelNode;

import java.awt.*;

/**
 * This is used when there's padding or margins in inline elements.
 */
final class RSpacing extends BaseBoundableRenderable {
	/**
	 * <p>Constructor for RSpacing.</p>
	 *
	 * @param me a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 * @param width a int.
	 * @param height a int.
	 */
	public RSpacing(ModelNode me, RenderableContainer container, int width, int height) {
		super(container, me);
		this.setWidth(width);
		this.setHeight(height);
	}

	/** {@inheritDoc} */
	@Override
	public boolean extractSelectionText(StringBuilder buffer, boolean inSelection, RenderableSpot startPoint,
			RenderableSpot endPoint) {
		if (this == startPoint.renderable || this == endPoint.renderable) {
            return !inSelection;
		} else return inSelection;
    }

	/** {@inheritDoc} */
	@Override
	public RenderableSpot getLowestRenderableSpot(int x, int y) {
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
	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
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
