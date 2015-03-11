/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
package org.lobobrowser.html.renderer;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import org.lobobrowser.html.dombl.ModelNode;
import org.lobobrowser.html.renderstate.RenderState;


/**
 * The Class RBlank.
 */
final class RBlank extends BaseBoundableRenderable {
	// TODO: Is there a need for RBlank's at all?
	/** The ascent plus leading. */
	public final int ascentPlusLeading;
	
	/** The font metrics. */
	private final FontMetrics fontMetrics;

	/**
	 * Instantiates a new r blank.
	 *
	 * @param me the me
	 * @param fm the fm
	 * @param container the container
	 * @param ascentPlusLeading the ascent plus leading
	 * @param width the width
	 * @param height the height
	 */
	public RBlank(ModelNode me, FontMetrics fm, RenderableContainer container,
			int ascentPlusLeading, int width, int height) {
		super(container, me);
		this.fontMetrics = fm;
		this.ascentPlusLeading = ascentPlusLeading;
		// Dimensions set when constructed.
		this.width = width;
		this.height = height;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BaseBoundableRenderable#invalidateLayoutLocal()
	 */
	protected void invalidateLayoutLocal() {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#onMouseClick(java.awt.event.MouseEvent, int, int)
	 */
	public boolean onMouseClick(MouseEvent event, int x, int y) {
		ModelNode me = this.modelNode;
		if (me != null) {
			return HtmlController.getInstance().onMouseClick(me, event, x, y);
		} else {
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#onDoubleClick(java.awt.event.MouseEvent, int, int)
	 */
	public boolean onDoubleClick(MouseEvent event, int x, int y) {
		ModelNode me = this.modelNode;
		if (me != null) {
			return HtmlController.getInstance().onDoubleClick(me, event, x, y);
		} else {
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#onMousePressed(java.awt.event.MouseEvent, int, int)
	 */
	public boolean onMousePressed(MouseEvent event, int x, int y) {
		ModelNode me = this.modelNode;
		if (me != null) {
			return HtmlController.getInstance().onMouseDown(me, event, x, y);
		} else {
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#onMouseReleased(java.awt.event.MouseEvent, int, int)
	 */
	public boolean onMouseReleased(MouseEvent event, int x, int y) {
		ModelNode me = this.modelNode;
		if (me != null) {
			return HtmlController.getInstance().onMouseUp(me, event, x, y);
		} else {
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#onMouseDisarmed(java.awt.event.MouseEvent)
	 */
	public boolean onMouseDisarmed(MouseEvent event) {
		ModelNode me = this.modelNode;
		if (me != null) {
			return HtmlController.getInstance().onMouseDisarmed(me, event);
		} else {
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.xamj.domimpl.markup.Renderable#paint(java.awt.Graphics)
	 */
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
				int lineOffset = fm.getLeading()
						+ (fm.getAscent() + fm.getDescent()) / 2;
				g.drawLine(0, lineOffset, this.width, lineOffset);
			}
			if ((td & RenderState.MASK_TEXTDECORATION_OVERLINE) != 0) {
				int lineOffset = this.fontMetrics.getLeading();
				g.drawLine(0, lineOffset, this.width, lineOffset);
			}
			if ((td & RenderState.MASK_TEXTDECORATION_BLINK) != 0) {
				// TODO
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
	 * org.lobobrowser.html.render.BoundableRenderable#paintSelection(java.awt.
	 * Graphics, boolean, org.lobobrowser.html.render.RenderablePoint,
	 * org.lobobrowser.html.render.RenderablePoint)
	 */
	public boolean paintSelection(Graphics g, boolean inSelection,
			RenderableSpot startPoint, RenderableSpot endPoint) {
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

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#extractSelectionText(java.lang.StringBuffer, boolean, org.lobobrowser.html.renderer.RenderableSpot, org.lobobrowser.html.renderer.RenderableSpot)
	 */
	public boolean extractSelectionText(StringBuffer buffer,
			boolean inSelection, RenderableSpot startPoint,
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
	 * @see org.lobobrowser.html.render.BoundableRenderable#getRenderable(int, int)
	 */
	public RenderableSpot getLowestRenderableSpot(int x, int y) {
		return new RenderableSpot(this, x, y);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#isContainedByNode()
	 */
	public boolean isContainedByNode() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#onRightClick(java.awt.event.MouseEvent, int, int)
	 */
	public boolean onRightClick(MouseEvent event, int x, int y) {
		ModelNode me = this.modelNode;
		if (me != null) {
			return HtmlController.getInstance().onContextMenu(me, event, x, y);
		} else {
			return true;
		}
	}
}
