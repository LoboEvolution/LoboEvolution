/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.renderer;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import org.lobobrowser.html.dombl.ModelNode;
import org.lobobrowser.html.domimpl.DOMEventImpl;

/**
 * The Class BaseRCollection.
 */
public abstract class BaseRCollection extends BaseBoundableRenderable implements RCollection {
	
	/** The renderable with mouse. */
	private BoundableRenderable renderableWithMouse = null;

	/**
	 * Instantiates a new base r collection.
	 *
	 * @param container
	 *            the container
	 * @param modelNode
	 *            the model node
	 */
	public BaseRCollection(RenderableContainer container, ModelNode modelNode) {
		super(container, modelNode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.RCollection#focus()
	 */
	@Override
	public void focus() {
		this.container.focus();
		// TODO: Plus local focus
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.RCollection#blur()
	 */
	@Override
	public void blur() {
		RCollection parent = this.parent;
		if (parent != null) {
			parent.focus();
		} else {
			// TODO: Remove local focus
		}
	}

	/**
	 * Updates bounds of all descendent's GUI components, based on root bounds.
	 *
	 * @param guiX
	 *            the gui x
	 * @param guiY
	 *            the gui y
	 */
	@Override
	public void updateWidgetBounds(int guiX, int guiY) {
		Iterator i = this.getRenderables();
		if (i != null) {
			while (i.hasNext()) {
				Object r = i.next();
				if (r instanceof RCollection) {
					// RUIControl is a RCollection too.
					RCollection rc = (RCollection) r;
					rc.updateWidgetBounds(guiX + rc.getX(), guiY + rc.getY());
				}
			}
		}
	}

	/**
	 * Check start selection.
	 *
	 * @param bounds
	 *            the bounds
	 * @param selectionPoint
	 *            the selection point
	 * @return true, if successful
	 */
	private boolean checkStartSelection(Rectangle bounds, Point selectionPoint) {
		if (bounds.y > selectionPoint.y) {
			return true;
		} else if (selectionPoint.y >= bounds.y && selectionPoint.y < bounds.y + bounds.height
				&& bounds.x > selectionPoint.x) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check end selection.
	 *
	 * @param bounds
	 *            the bounds
	 * @param selectionPoint
	 *            the selection point
	 * @return true, if successful
	 */
	private boolean checkEndSelection(Rectangle bounds, Point selectionPoint) {
		if (bounds.y > selectionPoint.y) {
			return true;
		} else if (selectionPoint.y >= bounds.y && selectionPoint.y < bounds.y + bounds.height
				&& selectionPoint.x < bounds.x) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#paintSelection(java.awt
	 * .Graphics, boolean, org.lobobrowser.html.renderer.RenderableSpot,
	 * org.lobobrowser.html.renderer.RenderableSpot)
	 */
	@Override
	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
		// TODO: Does this work with renderables that are absolutely positioned?
		Point checkPoint1 = null;
		Point checkPoint2 = null;
		if (!inSelection) {
			boolean isStart = startPoint.getRenderable() == this;
			boolean isEnd = endPoint.getRenderable() == this;
			if (isStart && isEnd) {
				checkPoint1 = startPoint.getPoint();
				checkPoint2 = endPoint.getPoint();
			} else if (isStart) {
				checkPoint1 = startPoint.getPoint();
			} else if (isEnd) {
				checkPoint1 = endPoint.getPoint();
			}
		} else {
			if (startPoint.getRenderable() == this) {
				checkPoint1 = startPoint.getPoint();
			} else if (endPoint.getRenderable() == this) {
				checkPoint1 = endPoint.getPoint();
			}
		}
		Iterator i = this.getRenderables();
		if (i != null) {
			while (i.hasNext()) {
				Object robj = i.next();
				if (robj instanceof BoundableRenderable) {
					BoundableRenderable renderable = (BoundableRenderable) robj;
					Rectangle bounds = renderable.getBounds();
					if (!inSelection) {
						if (checkPoint1 != null && this.checkStartSelection(bounds, checkPoint1)) {
							if (checkPoint2 != null) {
								checkPoint1 = checkPoint2;
								checkPoint2 = null;
							} else {
								checkPoint1 = null;
							}
							inSelection = true;
						} else if (checkPoint2 != null && this.checkStartSelection(bounds, checkPoint2)) {
							checkPoint1 = null;
							checkPoint2 = null;
							inSelection = true;
						}
					} else if (inSelection && checkPoint1 != null && this.checkEndSelection(bounds, checkPoint1)) {
						return false;
					}
					int offsetX = bounds.x;
					int offsetY = bounds.y;
					g.translate(offsetX, offsetY);
					try {
						boolean newInSelection = renderable.paintSelection(g, inSelection, startPoint, endPoint);
						if (inSelection && !newInSelection) {
							return false;
						}
						inSelection = newInSelection;
					} finally {
						g.translate(-offsetX, -offsetY);
					}
				}
			}
		}
		if (inSelection && checkPoint1 != null) {
			return false;
		} else if (!inSelection && (checkPoint1 != null || checkPoint2 != null)
				&& !(checkPoint1 != null && checkPoint2 != null)) {
			// Has to have started not being in selection,
			// but we must start selecting without having
			// selected anything in the block then.
			return true;
		}
		return inSelection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#extractSelectionText(
	 * java .lang.StringBuffer, boolean,
	 * org.lobobrowser.html.renderer.RenderableSpot,
	 * org.lobobrowser.html.renderer.RenderableSpot)
	 */
	@Override
	public boolean extractSelectionText(StringBuffer buffer, boolean inSelection, RenderableSpot startPoint,
			RenderableSpot endPoint) {
		Point checkPoint1 = null;
		Point checkPoint2 = null;
		if (!inSelection) {
			boolean isStart = startPoint.getRenderable() == this;
			boolean isEnd = endPoint.getRenderable() == this;
			if (isStart && isEnd) {
				checkPoint1 = startPoint.getPoint();
				checkPoint2 = endPoint.getPoint();
			} else if (isStart) {
				checkPoint1 = startPoint.getPoint();
			} else if (isEnd) {
				checkPoint1 = endPoint.getPoint();
			}
		} else {
			if (startPoint.getRenderable() == this) {
				checkPoint1 = startPoint.getPoint();
			} else if (endPoint.getRenderable() == this) {
				checkPoint1 = endPoint.getPoint();
			}
		}
		Iterator i = this.getRenderables();
		if (i != null) {
			while (i.hasNext()) {
				Object robj = i.next();
				if (robj instanceof BoundableRenderable) {
					BoundableRenderable renderable = (BoundableRenderable) robj;
					if (!inSelection) {
						Rectangle bounds = renderable.getBounds();
						if (checkPoint1 != null && this.checkStartSelection(bounds, checkPoint1)) {
							if (checkPoint2 != null) {
								checkPoint1 = checkPoint2;
								checkPoint2 = null;
							} else {
								checkPoint1 = null;
							}
							inSelection = true;
						} else if (checkPoint2 != null && this.checkStartSelection(bounds, checkPoint2)) {
							checkPoint1 = null;
							checkPoint2 = null;
							inSelection = true;
						}
					} else if (inSelection && checkPoint1 != null
							&& this.checkEndSelection(renderable.getBounds(), checkPoint1)) {
						return false;
					}
					boolean newInSelection = renderable.extractSelectionText(buffer, inSelection, startPoint, endPoint);
					if (inSelection && !newInSelection) {
						return false;
					}
					inSelection = newInSelection;
				}
			}
		}
		if (inSelection && checkPoint1 != null) {
			return false;
		} else if (!inSelection && (checkPoint1 != null || checkPoint2 != null)
				&& !(checkPoint1 != null && checkPoint2 != null)) {
			// Has to have started not being in selection,
			// but we must start selecting without having
			// selected anything in the block then.
			return true;
		}
		return inSelection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.RCollection#invalidateLayoutDeep()
	 */
	@Override
	public void invalidateLayoutDeep() {
		// TODO: May be pretty inefficient in RLine's
		// if it's true that non-layable components
		// are not in RLine's anymore.
		this.invalidateLayoutLocal();
		Iterator renderables = this.getRenderables();
		if (renderables != null) {
			while (renderables.hasNext()) {
				Object r = renderables.next();
				if (r instanceof RCollection) {
					((RCollection) r).invalidateLayoutDeep();
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BaseBoundableRenderable#onMouseMoved(java.
	 * awt .event.MouseEvent, int, int, boolean,
	 * org.lobobrowser.html.dombl.ModelNode)
	 */
	@Override
	public void onMouseMoved(MouseEvent event, int x, int y, boolean triggerEvent, ModelNode limit) {
		super.onMouseMoved(event, x, y, triggerEvent, limit);
		BoundableRenderable oldRenderable = this.renderableWithMouse;
		Renderable r = this.getRenderable(x, y);
		BoundableRenderable newRenderable = r instanceof BoundableRenderable ? (BoundableRenderable) r : null;
		ModelNode newLimit;
		if (this.isContainedByNode()) {
			newLimit = this.modelNode;
		} else {
			newLimit = limit;
		}
		boolean changed = oldRenderable != newRenderable;
		if (changed) {
			if (oldRenderable != null) {
				oldRenderable.onMouseOut(event, x - oldRenderable.getX(), y - oldRenderable.getY(), newLimit);
			}
			this.renderableWithMouse = newRenderable;
		}
		// Must recurse always
		if (newRenderable != null) {
			newRenderable.onMouseMoved(event, x - newRenderable.getX(), y - newRenderable.getY(), changed, newLimit);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BaseBoundableRenderable#onMouseOut(java.awt
	 * .event.MouseEvent, int, int, org.lobobrowser.html.dombl.ModelNode)
	 */
	@Override
	public void onMouseOut(MouseEvent event, int x, int y, ModelNode limit) {
		super.onMouseOut(event, x, y, limit);
		BoundableRenderable oldRenderable = this.renderableWithMouse;
		if (oldRenderable != null) {
			this.renderableWithMouse = null;
			ModelNode newLimit;
			if (this.isContainedByNode()) {
				newLimit = this.modelNode;
			} else {
				newLimit = limit;
			}
			oldRenderable.onMouseOut(event, x - oldRenderable.getX(), y - oldRenderable.getY(), newLimit);
		}
	}

	/**
	 * Gets the renderable.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return the renderable
	 */
	public BoundableRenderable getRenderable(int x, int y) {
		Iterator i = this.getRenderables();
		if (i != null) {
			while (i.hasNext()) {
				Object r = i.next();
				if (r instanceof BoundableRenderable) {
					BoundableRenderable br = (BoundableRenderable) r;

					if (br instanceof RBlockViewport) {
						return br;
					}

					int bx = br.getX();
					int by = br.getY();
					if (y >= by && y < by + br.getHeight() && x >= bx && x < bx + br.getWidth()) {
						return br;
					}
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#onRightClick(java.awt.
	 * event .MouseEvent, int, int)
	 */
	@Override
	public boolean onRightClick(MouseEvent event, int x, int y) {
		BoundableRenderable br = this.getRenderable(x, y);
		if (br == null) {
			return DOMEventImpl.getInstance().onContextMenu(this.modelNode, event, x, y);
		} else {
			return br.onRightClick(event, x - br.getX(), y - br.getY());
		}
	}
}
