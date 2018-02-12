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

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import org.loboevolution.html.dombl.ModelNode;
import org.loboevolution.html.renderstate.RenderState;

/**
 * The Class RBlank.
 */
public final class RBlank extends BaseBoundableRenderable {
	
	/** The ascent plus leading. */
	private int ascentPlusLeading;

	/** The font metrics. */
	private final FontMetrics fontMetrics;

	/**
	 * Instantiates a new r blank.
	 *
	 * @param me
	 *            the me
	 * @param fm
	 *            the fm
	 * @param container
	 *            the container
	 * @param ascentPlusLeading
	 *            the ascent plus leading
	 * @param width
	 *            the width
	 * @param height
	 *            the height
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.BaseBoundableRenderable#
	 * invalidateLayoutLocal()
	 */
	@Override
	protected void invalidateLayoutLocal() {
		// Method not implemented
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.xamj.domimpl.markup.Renderable#paint(java.awt.Graphics)
	 */
	@Override
	public final void paint(Graphics g) {
		RenderState rs = this.modelNode.getRenderState();
		Color bkg = rs.getTextBackgroundColor();
		if (bkg != null) {
			Color oldColor = g.getColor();
			try {
				g.setColor(bkg);
				g.fillRect(0, 0, this.width, this.height);
			} finally {
				g.setColor(oldColor);
			}
		}
		int td = rs.getTextDecorationMask();
		if (td != 0) {
			if ((td & RenderState.MASK_TEXTDECORATION_UNDERLINE) != 0) {
				int lineOffset = this.ascentPlusLeading + 2;
				g.drawLine(0, lineOffset, this.width, lineOffset);
			}
			if ((td & RenderState.MASK_TEXTDECORATION_LINE_THROUGH) != 0) {
				FontMetrics fm = this.fontMetrics;
				int lineOffset = fm.getLeading() + (fm.getAscent() + fm.getDescent()) / 2;
				g.drawLine(0, lineOffset, this.width, lineOffset);
			}
			if ((td & RenderState.MASK_TEXTDECORATION_OVERLINE) != 0) {
				int lineOffset = this.fontMetrics.getLeading();
				g.drawLine(0, lineOffset, this.width, lineOffset);
			}
		}
		Color over = rs.getOverlayColor();
		if (over != null) {
			Color oldColor = g.getColor();
			try {
				g.setColor(over);
				g.fillRect(0, 0, width, height);
			} finally {
				g.setColor(oldColor);
			}
		}
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
	 * java .lang.StringBuilder , boolean,
	 * org.loboevolution.html.renderer.RenderableSpot,
	 * org.loboevolution.html.renderer.RenderableSpot)
	 */
	@Override
	public boolean extractSelectionText(StringBuilder buffer, boolean inSelection, RenderableSpot startPoint,
			RenderableSpot endPoint) {
		if (this == startPoint.getRenderable() || this == endPoint.getRenderable()) {
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

	/**
	 * Gets the ascent plus leading.
	 *
	 * @return the ascent plus leading
	 */
	public int getAscentPlusLeading() {
		return ascentPlusLeading;
	}

	/**
	 * Sets the ascent plus leading.
	 *
	 * @param ascentPlusLeading
	 *            the new ascent plus leading
	 */
	public void setAscentPlusLeading(int ascentPlusLeading) {
		this.ascentPlusLeading = ascentPlusLeading;
	}

	/**
	 * Gets the font metrics.
	 *
	 * @return the font metrics
	 */
	public FontMetrics getFontMetrics() {
		return fontMetrics;
	}

}
