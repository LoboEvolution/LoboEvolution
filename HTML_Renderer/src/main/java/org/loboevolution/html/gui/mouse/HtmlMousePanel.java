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
    along with blockPanel program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.gui.mouse;

import java.awt.Adjustable;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import org.loboevolution.html.gui.HtmlBlockPanel;
import org.loboevolution.html.renderer.BoundableRenderable;
import org.loboevolution.html.renderer.FrameContext;
import org.loboevolution.html.renderer.RenderableSpot;
import org.loboevolution.html.rendererblock.RBlock;
import org.loboevolution.html.rendererblock.RBlockViewport;
import org.loboevolution.util.Objects;

public class HtmlMousePanel {

	/** The html mouse block panel. */
	private HtmlBlockPanel blockPanel;

	/** The mouse press target. */
	private BoundableRenderable mousePressTarget;

	public HtmlMousePanel(HtmlBlockPanel bPanel) {
		this.blockPanel = bPanel;
	}

	/**
	 * On mouse click.
	 *
	 * @param event
	 *            the event
	 */
	public void onMouseClick(MouseEvent event, RBlock block) {
		if (block != null) {
			int button = event.getButton();
			int clickCount = event.getClickCount();

			if (button == MouseEvent.BUTTON1 && clickCount == 1) {
				Point point = event.getPoint();
				block.onMouseClick(event, point.x, point.y);
			}
			if (button == MouseEvent.BUTTON1 && clickCount == 2) {
				Point point = event.getPoint();
				block.onDoubleClick(event, point.x, point.y);
			} else if (button == MouseEvent.BUTTON3 && clickCount == 1) {
				block.onRightClick(event, event.getX(), event.getY());
			}
		}
	}

	/**
	 * On mouse pressed.
	 *
	 * @param event
	 *            the event
	 */
	public void onMousePressed(MouseEvent event, RBlock block, FrameContext frameContext) {
		blockPanel.requestFocus();
		if (block != null) {
			Point point = event.getPoint();
			mousePressTarget = block;
			int rx = point.x;
			int ry = point.y;
			block.onMousePressed(event, point.x, point.y);
			RenderableSpot rp = block.getLowestRenderableSpot(rx, ry);
			if (rp != null) {
				frameContext.resetSelection(rp);
			} else {
				frameContext.resetSelection(null);
			}
		}
	}

	/**
	 * On mouse released.
	 *
	 * @param event
	 *            the event
	 */
	public void onMouseReleased(MouseEvent event, RBlock block) {
		if (block != null) {
			Point point = event.getPoint();
			int rx = point.x;
			int ry = point.y;
			if (event.getButton() == MouseEvent.BUTTON1) {
				block.onMouseClick(event, rx, ry);
			}
			block.onMouseReleased(event, rx, ry);
			BoundableRenderable oldTarget = mousePressTarget;
			if (oldTarget != null) {
				mousePressTarget = null;
				if (!Objects.equals(oldTarget, block)) {
					oldTarget.onMouseDisarmed(event);
				}
			}
		} else {
			mousePressTarget = null;
		}
	}

	/**
	 * On mouse exited.
	 *
	 * @param event
	 *            the event
	 */
	public void onMouseExited(MouseEvent event) {
		BoundableRenderable oldTarget = mousePressTarget;
		if (oldTarget != null) {
			mousePressTarget = null;
			oldTarget.onMouseDisarmed(event);
		}
	}

	/**
	 * On mouse dragged.
	 *
	 * @param event
	 *            the event
	 */
	public void onMouseDragged(MouseEvent event, RBlock block, FrameContext frameContext) {
		if (block != null) {
			Point point = event.getPoint();
			RenderableSpot rp = block.getLowestRenderableSpot(point.x, point.y);
			if (rp != null) {
				frameContext.expandSelection(rp);
			}
			block.ensureVisible(point);
		}
	}

	/**
	 * On mouse moved.
	 *
	 * @param event
	 *            the event
	 */
	public void onMouseMoved(MouseEvent event, RBlock block) {
		if (block != null) {
			Point point = event.getPoint();
			block.onMouseMoved(event, point.x, point.y, false, null);
		}
	}

	/**
	 * On key press.
	 *
	 * @param event
	 *            the event
	 */
	public void onKeyPressed(KeyEvent evt, RBlock block) {
		blockPanel.requestFocus();
		if (block != null) {
			block.onKeyPressed(evt);
		}
	}

	/**
	 * On key up.
	 *
	 * @param event
	 *            the event
	 */
	public void onKeyUp(KeyEvent evt, RBlock block) {
		blockPanel.requestFocus();
		if (block != null) {
			block.onKeyUp(evt);
		}
	}

	/**
	 * On mouse wheel moved.
	 *
	 * @param mwe
	 *            the mwe
	 */
	public void onMouseWheelMoved(MouseWheelEvent mwe, RBlock block) {
		RBlockViewport viewport = block.getRBlockViewport();
		RenderableSpot spot = viewport.getLowestRenderableSpot(mwe.getX(), mwe.getY());
		for (BoundableRenderable r = spot.getRenderable(); r != null; r = r.getParent()) {
			if (r instanceof RBlock) {
				block = (RBlock) r;
				RBlockViewport blockViewport = block.getRBlockViewport();
				if (mwe.getWheelRotation() < 0) {
					if (blockViewport.getY() < 0) {
						break;
					}
				} else {
					if (blockViewport.getY() + blockViewport.getHeight() > block.getHeight()) {
						break;
					}
				}
			}
		}

		switch (mwe.getScrollType()) {
		case MouseWheelEvent.WHEEL_UNIT_SCROLL:
			int units = mwe.getWheelRotation() * mwe.getScrollAmount();
			block.scrollByUnits(Adjustable.VERTICAL, units);
			break;
		default:
			break;
		}
	}

	/**
	 * @return the blockPanel
	 */
	public HtmlBlockPanel getBlockPanel() {
		return blockPanel;
	}

	/**
	 * @param blockPanel
	 *            the blockPanel to set
	 */
	public void setBlockPanel(HtmlBlockPanel blockPanel) {
		this.blockPanel = blockPanel;
	}

	/**
	 * @return the mousePressTarget
	 */
	public BoundableRenderable getMousePressTarget() {
		return mousePressTarget;
	}

	/**
	 * @param mousePressTarget
	 *            the mousePressTarget to set
	 */
	public void setMousePressTarget(BoundableRenderable mousePressTarget) {
		this.mousePressTarget = mousePressTarget;
	}
}