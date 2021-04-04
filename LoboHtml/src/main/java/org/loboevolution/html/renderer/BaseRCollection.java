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

package org.loboevolution.html.renderer;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import org.loboevolution.html.dom.nodeimpl.ModelNode;

abstract class BaseRCollection extends BaseBoundableRenderable implements RCollection {
	private BoundableRenderable renderableWithMouse = null;

	/**
	 * <p>Constructor for BaseRCollection.</p>
	 *
	 * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 * @param modelNode a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 */
	public BaseRCollection(RenderableContainer container, ModelNode modelNode) {
		super(container, modelNode);
	}

	/** {@inheritDoc} */
	@Override
	public void blur() {
		final RCollection parent = this.parent;
		if (parent != null) {
			parent.focus();
		} else {
			// TODO: Remove local focus
		}
	}
	
	  /** {@inheritDoc} */
	  @Override
	  public Rectangle getClipBoundsWithoutInsets() {
	    return getClipBounds();
	  }

	/** {@inheritDoc} */
	@Override
	public boolean extractSelectionText(StringBuilder buffer, boolean inSelection, RenderableSpot startPoint,
			RenderableSpot endPoint) {
		Point checkPoint1 = null;
		Point checkPoint2 = null;
		if (!inSelection) {
			final boolean isStart = startPoint.renderable == this;
			final boolean isEnd = endPoint.renderable == this;
			if (isStart && isEnd) {
				checkPoint1 = startPoint.getPoint();
				checkPoint2 = endPoint.getPoint();
			} else if (isStart) {
				checkPoint1 = startPoint.getPoint();
			} else if (isEnd) {
				checkPoint1 = endPoint.getPoint();
			}
		} else {
			if (startPoint.renderable == this) {
				checkPoint1 = startPoint.getPoint();
			} else if (endPoint.renderable == this) {
				checkPoint1 = endPoint.getPoint();
			}
		}
		final Iterator<Renderable> i = getRenderables();
		if (i != null) {
			while (i.hasNext()) {
		        final Renderable rn = i.next();
		        final Renderable robj = (rn instanceof PositionedRenderable) ? ((PositionedRenderable) rn).getRenderable() : rn;
				if (robj instanceof BoundableRenderable) {
					final BoundableRenderable renderable = (BoundableRenderable) robj;
					if (!inSelection) {
						final Rectangle bounds = renderable.getVisualBounds();
						if (checkPoint1 != null && checkStartSelection(bounds, checkPoint1)) {
							if (checkPoint2 != null) {
								checkPoint1 = checkPoint2;
								checkPoint2 = null;
							} else {
								checkPoint1 = null;
							}
							inSelection = true;
						} else if (checkPoint2 != null && checkStartSelection(bounds, checkPoint2)) {
							checkPoint1 = null;
							checkPoint2 = null;
							inSelection = true;
						}
					} else if (inSelection && checkPoint1 != null
							&& checkEndSelection(renderable.getVisualBounds(), checkPoint1)) {
						return false;
					}
					final boolean newInSelection = renderable.extractSelectionText(buffer, inSelection, startPoint,
							endPoint);
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

	/** {@inheritDoc} */
	@Override
	public void focus() {
		this.container.focus();
	}

	/** {@inheritDoc} */
	public BoundableRenderable getRenderable(int x, int y) {
		final Iterator<Renderable> i = getRenderables();
		if (i != null) {
			while (i.hasNext()) {
				final Renderable rn = i.next();
				final Renderable r = (rn instanceof PositionedRenderable) ? ((PositionedRenderable) rn).getRenderable() : rn;
				if (r instanceof BoundableRenderable) {
					final BoundableRenderable br = (BoundableRenderable) r;
					if (br.contains(x, y)) {
						return br;
					}
				}
			}
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void invalidateLayoutDeep() {
		// TODO: May be pretty inefficient in RLine's
		// if it's true that non-layable components
		// are not in RLine's anymore.
		invalidateLayoutLocal();
		final Iterator<Renderable> renderables = getRenderables();
		if (renderables != null) {
			while (renderables.hasNext()) {
		        final Renderable rn = renderables.next();
		        final Renderable r = (rn instanceof PositionedRenderable) ? ((PositionedRenderable) rn).getRenderable() : rn;
				if (r instanceof RCollection) {
					((RCollection) r).invalidateLayoutDeep();
				}
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void onMouseMoved(MouseEvent event, int x, int y, boolean triggerEvent, ModelNode limit) {
		super.onMouseMoved(event, x, y, triggerEvent, limit);
		final BoundableRenderable oldRenderable = this.renderableWithMouse;
		final Renderable r = getRenderable(x, y);
		final BoundableRenderable newRenderable = r instanceof BoundableRenderable ? (BoundableRenderable) r : null;
		ModelNode newLimit;
		if (isContainedByNode()) {
			newLimit = this.modelNode;
		} else {
			newLimit = limit;
		}
		final boolean changed = oldRenderable != newRenderable;
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

	/** {@inheritDoc} */
	@Override
	public void onMouseOut(MouseEvent event, int x, int y, ModelNode limit) {
		super.onMouseOut(event, x, y, limit);
		final BoundableRenderable oldRenderable = this.renderableWithMouse;
		if (oldRenderable != null) {
			this.renderableWithMouse = null;
			ModelNode newLimit;
			if (isContainedByNode()) {
				newLimit = this.modelNode;
			} else {
				newLimit = limit;
			}
			oldRenderable.onMouseOut(event, x - oldRenderable.getX(), y - oldRenderable.getY(), newLimit);
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean onRightClick(MouseEvent event, int x, int y) {
		final BoundableRenderable br = getRenderable(x, y);
		if (br == null) {
			return HtmlController.getInstance().onContextMenu(this.modelNode, event, x, y);
		} else {
			return br.onRightClick(event, x - br.getX(), y - br.getY());
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
		// TODO: Does this work with renderables that are absolutely positioned?
		Point checkPoint1 = null;
		Point checkPoint2 = null;
		if (!inSelection) {
			final boolean isStart = startPoint.renderable == this;
			final boolean isEnd = endPoint.renderable == this;
			if (isStart && isEnd) {
				checkPoint1 = startPoint.getPoint();
				checkPoint2 = endPoint.getPoint();
			} else if (isStart) {
				checkPoint1 = startPoint.getPoint();
			} else if (isEnd) {
				checkPoint1 = endPoint.getPoint();
			}
		} else {
			if (startPoint.renderable == this) {
				checkPoint1 = startPoint.getPoint();
			} else if (endPoint.renderable == this) {
				checkPoint1 = endPoint.getPoint();
			}
		}
		final Iterator<Renderable> i = getRenderables();
		if (i != null) {
			while (i.hasNext()) {
		        final Renderable rn = i.next();
		        final Renderable robj = (rn instanceof PositionedRenderable) ? ((PositionedRenderable) rn).getRenderable() : rn;
				if (robj instanceof BoundableRenderable) {
					final BoundableRenderable renderable = (BoundableRenderable) robj;
					final Rectangle bounds = renderable.getVisualBounds();
					if (!inSelection) {
						if (checkPoint1 != null && checkStartSelection(bounds, checkPoint1)) {
							if (checkPoint2 != null) {
								checkPoint1 = checkPoint2;
								checkPoint2 = null;
							} else {
								checkPoint1 = null;
							}
							inSelection = true;
						} else if (checkPoint2 != null && checkStartSelection(bounds, checkPoint2)) {
							checkPoint1 = null;
							checkPoint2 = null;
							inSelection = true;
						}
					} else if (inSelection && checkPoint1 != null && checkEndSelection(bounds, checkPoint1)) {
						return false;
					}
					final int offsetX = bounds.x;
					final int offsetY = bounds.y;
					g.translate(offsetX, offsetY);
					try {
						final boolean newInSelection = renderable.paintSelection(g, inSelection, startPoint, endPoint);
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

	/**
	 * {@inheritDoc}
	 *
	 * Updates bounds of all descendent's GUI components, based on root bounds.
	 */
	@Override
	public void updateWidgetBounds(int guiX, int guiY) {
		final Iterator<Renderable> i = getRenderables();
		if (i != null) {
			while (i.hasNext()) {
		        final Renderable rn = i.next();
		        final Renderable r = (rn instanceof PositionedRenderable) ? ((PositionedRenderable) rn).getRenderable() : rn;
				if (r instanceof RCollection) {
					// RUIControl is a RCollection too.
					final RCollection rc = (RCollection) r;
					rc.updateWidgetBounds(guiX + rc.getX(), guiY + rc.getY());
				}
			}
		}
	}
	
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

	private boolean checkStartSelection(Rectangle bounds, Point selectionPoint) {
		if (bounds.y > selectionPoint.y) {
			return true;
		} else return selectionPoint.y >= bounds.y && selectionPoint.y < bounds.y + bounds.height
				&& bounds.x > selectionPoint.x;
	}
}
