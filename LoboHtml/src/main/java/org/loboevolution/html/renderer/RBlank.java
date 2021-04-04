/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import org.loboevolution.html.dom.nodeimpl.ModelNode;
import org.loboevolution.html.renderstate.RenderState;

final class RBlank extends BaseBoundableRenderable {
	// TODO: Is there a need for RBlank's at all?
	public final int ascentPlusLeading;
	private final FontMetrics fontMetrics;

	/**
	 * <p>Constructor for RBlank.</p>
	 *
	 * @param me a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @param fm a {@link java.awt.FontMetrics} object.
	 * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 * @param ascentPlusLeading a int.
	 * @param width a int.
	 * @param height a int.
	 */
	public RBlank(ModelNode me, FontMetrics fm, RenderableContainer container, int ascentPlusLeading, int width,
			int height) {
		super(container, me);
		this.fontMetrics = fm;
		this.ascentPlusLeading = ascentPlusLeading;
		// Dimensions set when constructed.
		this.width = width;
		this.height = height;
	}

	/** {@inheritDoc} */
	@Override
	public boolean extractSelectionText(StringBuilder buffer, boolean inSelection, RenderableSpot startPoint,
			RenderableSpot endPoint) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.rendered.BoundableRenderable#getRenderable(int, int)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sourceforge.xamj.domimpl.markup.Renderable#paint(java.awt.Graphics)
	 */
	/** {@inheritDoc} */
	@Override
	public void paint(Graphics g) {
		final RenderState rs = this.modelNode.getRenderState();
		final Color bkg = rs.getTextBackgroundColor();
		if (bkg != null) {
			final Color oldColor = g.getColor();
			try {
				g.setColor(bkg);
				g.fillRect(0, 0, this.width, this.height);
			} finally {
				g.setColor(oldColor);
			}
		}
		final int td = rs.getTextDecorationMask();
		if (td != 0) {
			if ((td & RenderState.MASK_TEXTDECORATION_UNDERLINE) != 0) {
				final int lineOffset = this.ascentPlusLeading + 2;
				g.drawLine(0, lineOffset, this.width, lineOffset);
			}
			if ((td & RenderState.MASK_TEXTDECORATION_LINE_THROUGH) != 0) {
				final FontMetrics fm = this.fontMetrics;
				final int lineOffset = fm.getLeading() + (fm.getAscent() + fm.getDescent()) / 2;
				g.drawLine(0, lineOffset, this.width, lineOffset);
			}
			if ((td & RenderState.MASK_TEXTDECORATION_OVERLINE) != 0) {
				final int lineOffset = this.fontMetrics.getLeading();
				g.drawLine(0, lineOffset, this.width, lineOffset);
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
				g.fillRect(0, 0, this.width, this.height);
			} finally {
				g.setColor(oldColor);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.rendered.BoundableRenderable#paintSelection(java.awt.
	 * Graphics, boolean, org.loboevolution.html.rendered.RenderablePoint,
	 * org.loboevolution.html.rendered.RenderablePoint)
	 */
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
		g.fillRect(0, 0, this.width, this.height);
		g.setPaintMode();
		return true;
	}
}
