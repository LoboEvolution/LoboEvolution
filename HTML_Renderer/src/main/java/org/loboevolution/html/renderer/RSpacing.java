/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on May 21, 2005
 */
package org.loboevolution.html.renderer;

import java.awt.Graphics;
import org.loboevolution.html.dombl.ModelNode;

/**
 * This is used when there's padding or margins in inline elements.
 */
public final class RSpacing extends BaseBoundableRenderable {

	/**
	 * Instantiates a new r spacing.
	 *
	 * @param me
	 *            the me
	 * @param container
	 *            the container
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	public RSpacing(ModelNode me, RenderableContainer container, int width, int height) {
		super(container, me);
		// Dimensions set when constructed.
		this.width = width;
		this.height = height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.BaseBoundableRenderable#
	 * invalidateLayoutLocal()
	 */
	@Override
	protected void invalidateLayoutLocal() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.xamj.domimpl.markup.Renderable#paint(java.awt.Graphics)
	 */
	@Override
	public final void paint(Graphics g) {
		// Nothing to paint in spacing.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.render.BoundableRenderable#paintSelection(java.awt.
	 * Graphics, boolean, org.loboevolution.html.render.RenderablePoint,
	 * org.loboevolution.html.render.RenderablePoint)
	 */
	@Override
	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
		if (this == startPoint.getRenderable() || this == endPoint.getRenderable()) {
			if (inSelection) {
				return false;
			}
		} else if (!inSelection) {
			return false;
		}
		g.setColor(SELECTION_COLOR);
		g.setXORMode(SELECTION_XOR);
		g.fillRect(0, 0, this.width, this.height);
		g.setPaintMode();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.renderer.BoundableRenderable#extractSelectionText(
	 * java .lang.StringBuffer, boolean,
	 * org.loboevolution.html.renderer.RenderableSpot,
	 * org.loboevolution.html.renderer.RenderableSpot)
	 */
	@Override
	public boolean extractSelectionText(StringBuffer buffer, boolean inSelection, RenderableSpot startPoint,
			RenderableSpot endPoint) {
		if (this == startPoint.getRenderable() || this == endPoint.getRenderable()) {
			if (inSelection) {
				return false;
			}
		} else if (!inSelection) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.render.BoundableRenderable#getRenderable(int,
	 * int)
	 */
	@Override
	public RenderableSpot getLowestRenderableSpot(int x, int y) {
		return new RenderableSpot(this, x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.renderer.BoundableRenderable#isContainedByNode()
	 */
	@Override
	public boolean isContainedByNode() {
		return true;
	}
}