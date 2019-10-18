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
/*
 * Created on May 21, 2005
 */
package org.loboevolution.html.renderer;

import java.awt.Graphics;

import org.loboevolution.html.dom.domimpl.ModelNode;

/**
 * This is used when there's padding or margins in inline elements.
 */
final class RSpacing extends BaseBoundableRenderable {
	public RSpacing(ModelNode me, RenderableContainer container, int width, int height) {
		super(container, me);
		// Dimensions set when constructed.
		this.width = width;
		this.height = height;
	}

	@Override
	public boolean extractSelectionText(StringBuffer buffer, boolean inSelection, RenderableSpot startPoint,
			RenderableSpot endPoint) {
		if (this == startPoint.renderable || this == endPoint.renderable) {
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
	 * @see org.xamjwg.html.renderer.BoundableRenderable#getRenderable(int, int)
	 */
	@Override
	public RenderableSpot getLowestRenderableSpot(int x, int y) {
		return new RenderableSpot(this, x, y);
	}

	@Override
	protected void invalidateLayoutLocal() {
	}

	@Override
	public boolean isContainedByNode() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sourceforge.xamj.domimpl.markup.Renderable#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		// Nothing to paint in spacing.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.renderer.BoundableRenderable#paintSelection(java.awt.
	 * Graphics, boolean, org.xamjwg.html.renderer.RenderablePoint,
	 * org.xamjwg.html.renderer.RenderablePoint)
	 */
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
		g.fillRect(0, 0, this.width, this.height);
		g.setPaintMode();
		return true;
	}
}
