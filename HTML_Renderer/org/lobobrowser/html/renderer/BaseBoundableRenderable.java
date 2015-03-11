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
 * Created on Apr 17, 2005
 */
package org.lobobrowser.html.renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.html.dombl.ModelNode;


/**
 * The Class BaseBoundableRenderable.
 *
 * @author J. H. S.
 */
public abstract class BaseBoundableRenderable extends BaseRenderable implements
		BoundableRenderable {
	
	/** The Constant logger. */
	protected static final Logger logger = Logger
			.getLogger(BaseBoundableRenderable.class.getName());
	
	/** The Constant SELECTION_COLOR. */
	protected static final Color SELECTION_COLOR = Color.BLUE;
	
	/** The Constant SELECTION_XOR. */
	protected static final Color SELECTION_XOR = Color.LIGHT_GRAY;

	// protected final Rectangle bounds = new Rectangle();
	/** The container. */
	protected final RenderableContainer container;
	
	/** The model node. */
	protected final ModelNode modelNode;

	/** The height. */
	public int x, y, width, height;

	/**
	 * Starts as true because ancestors could be invalidated.
	 */
	protected boolean layoutUpTreeCanBeInvalidated = true;

	/**
	 * Mark layout valid.
	 */
	public void markLayoutValid() {
		this.layoutUpTreeCanBeInvalidated = true;
	}

	/**
	 * Instantiates a new base boundable renderable.
	 *
	 * @param container the container
	 * @param modelNode the model node
	 */
	public BaseBoundableRenderable(RenderableContainer container,
			ModelNode modelNode) {
		this.container = container;
		this.modelNode = modelNode;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#getGUIPoint(int, int)
	 */
	public java.awt.Point getGUIPoint(int clientX, int clientY) {
		Renderable parent = this.getParent();
		if (parent instanceof BoundableRenderable) {
			return ((BoundableRenderable) parent).getGUIPoint(clientX + this.x,
					clientY + this.y);
		} else if (parent == null) {
			return this.container.getGUIPoint(clientX + this.x, clientY
					+ this.y);
		} else {
			throw new IllegalStateException("parent=" + parent);
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#getRenderablePoint(int, int)
	 */
	public Point getRenderablePoint(int guiX, int guiY) {
		Renderable parent = this.getParent();
		if (parent instanceof BoundableRenderable) {
			return ((BoundableRenderable) parent).getRenderablePoint(guiX
					- this.x, guiY - this.y);
		} else if (parent == null) {
			return new Point(guiX - this.x, guiY - this.y);
		} else {
			throw new IllegalStateException("parent=" + parent);
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#getHeight()
	 */
	public int getHeight() {
		return height;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#getWidth()
	 */
	public int getWidth() {
		return width;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#setWidth(int)
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#getX()
	 */
	public int getX() {
		return x;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#getY()
	 */
	public int getY() {
		return y;
	}

	/**
	 * Contains.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if successful
	 */
	public boolean contains(int x, int y) {
		return x >= this.x && y >= this.y && x < this.x + this.width
				&& y < this.y + this.height;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#getBounds()
	 */
	public Rectangle getBounds() {
		return new Rectangle(this.x, this.y, this.width, this.height);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#getSize()
	 */
	public Dimension getSize() {
		return new Dimension(this.width, this.height);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.Renderable#getModelNode()
	 */
	public ModelNode getModelNode() {
		return this.modelNode;
	}

	// /* (non-Javadoc)
	// * @see net.sourceforge.xamj.domimpl.markup.Renderable#getBounds()
	// */
	// public Rectangle getBounds() {
	// return this.bounds;
	// }
	//
	public void setBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#setX(int)
	 */
	public void setX(int x) {
		this.x = x;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#setY(int)
	 */
	public void setY(int y) {
		this.y = y;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#setHeight(int)
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#setOrigin(int, int)
	 */
	public void setOrigin(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Invalidate layout local.
	 */
	protected abstract void invalidateLayoutLocal();

	/**
	 * Invalidates this Renderable and its parent (i.e. all ancestors).
	 */
	public final void invalidateLayoutUpTree() {
		if (this.layoutUpTreeCanBeInvalidated) {
			this.layoutUpTreeCanBeInvalidated = false;
			this.invalidateLayoutLocal();
			// Try original parent first.
			RCollection parent = this.originalParent;
			if (parent == null) {
				parent = this.parent;
				if (parent == null) {
					// Has to be top block
					RenderableContainer rc = this.container;
					if (rc != null) {
						rc.invalidateLayoutUpTree();
					}
				} else {
					parent.invalidateLayoutUpTree();
				}
			} else {
				parent.invalidateLayoutUpTree();
			}
		} else {
		}
	}

	/**
	 * Checks if is valid.
	 *
	 * @return true, if is valid
	 */
	public boolean isValid() {
		return this.layoutUpTreeCanBeInvalidated;
	}

	/**
	 * Relayout impl.
	 *
	 * @param invalidateLocal the invalidate local
	 * @param onlyIfValid the only if valid
	 */
	private final void relayoutImpl(boolean invalidateLocal, boolean onlyIfValid) {
		if (onlyIfValid && !this.layoutUpTreeCanBeInvalidated) {
			return;
		}
		if (invalidateLocal) {
			this.invalidateLayoutUpTree();
		}
		Renderable parent = this.parent;
		if (parent instanceof BaseBoundableRenderable) {
			((BaseBoundableRenderable) parent).relayoutImpl(false, false);
		} else if (parent == null) {
			// Has to be top RBlock.
			this.container.relayout();
		} else {
			if (logger.isLoggable(Level.INFO)) {
				logger.warning("relayout(): Don't know how to relayout " + this
						+ ", parent being " + parent);
			}
		}
	}

	/**
	 * Invalidates the current Renderable (which invalidates its ancestors) and
	 * then requests the top level GUI container to do the layout and repaint.
	 * It's safe to call this method outside the GUI thread.
	 */
	public void relayout() {
		if (EventQueue.isDispatchThread()) {
			this.relayoutImpl(true, false);
		} else {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					relayoutImpl(true, false);
				}
			});
		}
	}

	/**
	 * Relayout if valid.
	 */
	public void relayoutIfValid() {
		if (EventQueue.isDispatchThread()) {
			this.relayoutImpl(true, true);
		} else {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					relayoutImpl(true, true);
				}
			});
		}
	}

	/**
	 * Parent for graphics coordinates.
	 */
	protected RCollection parent;

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#setParent(org.lobobrowser.html.renderer.RCollection)
	 */
	public void setParent(RCollection parent) {
		this.parent = parent;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#getParent()
	 */
	public RCollection getParent() {
		return this.parent;
	}

	/**
	 * Parent for invalidation.
	 */
	protected RCollection originalParent;

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#setOriginalParent(org.lobobrowser.html.renderer.RCollection)
	 */
	public void setOriginalParent(RCollection origParent) {
		this.originalParent = origParent;
	}

	/**
	 * This is the parent based on the original element hierarchy.
	 *
	 * @return the original parent
	 */
	public RCollection getOriginalParent() {
		return this.originalParent;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#getOriginalOrCurrentParent()
	 */
	public RCollection getOriginalOrCurrentParent() {
		RCollection origParent = this.originalParent;
		if (origParent == null) {
			return this.parent;
		}
		return origParent;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#repaint(int, int, int, int)
	 */
	public void repaint(int x, int y, int width, int height) {
		Renderable parent = this.parent;
		if (parent instanceof BoundableRenderable) {
			((BoundableRenderable) parent).repaint(x + this.x, y + this.y,
					width, height);
		} else if (parent == null) {
			// Has to be top RBlock.
			this.container.repaint(x, y, width, height);
		} else {
			if (logger.isLoggable(Level.INFO)) {
				logger.warning("repaint(): Don't know how to repaint " + this
						+ ", parent being " + parent);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#repaint()
	 */
	public void repaint() {
		this.repaint(0, 0, this.width, this.height);
	}

	/**
	 * Gets the block background color.
	 *
	 * @return the block background color
	 */
	public Color getBlockBackgroundColor() {
		return this.container.getPaintedBackgroundColor();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#paintTranslated(java.awt.Graphics)
	 */
	public final void paintTranslated(Graphics g) {
		int x = this.x;
		int y = this.y;
		g.translate(x, y);
		try {
			this.paint(g);
		} finally {
			g.translate(-x, -y);
		}
	}

	/**
	 * Translate descendent point.
	 *
	 * @param descendent the descendent
	 * @param x the x
	 * @param y the y
	 * @return the java.awt. point
	 */
	protected final java.awt.Point translateDescendentPoint(
			BoundableRenderable descendent, int x, int y) {
		while (descendent != this) {
			if (descendent == null) {
				throw new IllegalStateException("Not descendent");
			}
			x += descendent.getX();
			y += descendent.getY();
			// Coordinates are always relative to actual parent?
			descendent = descendent.getParent();
		}
		return new Point(x, y);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#onMouseOut(java.awt.event.MouseEvent, int, int, org.lobobrowser.html.dombl.ModelNode)
	 */
	public void onMouseOut(MouseEvent event, int x, int y, ModelNode limit) {
		if (this.isContainedByNode()) {
			HtmlController.getInstance().onMouseOut(this.modelNode, event, x,
					y, limit);
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#onMouseMoved(java.awt.event.MouseEvent, int, int, boolean, org.lobobrowser.html.dombl.ModelNode)
	 */
	public void onMouseMoved(MouseEvent event, int x, int y,
			boolean triggerEvent, ModelNode limit) {
		if (triggerEvent) {
			if (this.isContainedByNode()) {
				HtmlController.getInstance().onMouseOver(this.modelNode, event,
						x, y, limit);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#getOrigin()
	 */
	public Point getOrigin() {
		return new Point(this.x, this.y);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.BoundableRenderable#getOriginRelativeTo(org.lobobrowser.html.renderer.RCollection)
	 */
	public Point getOriginRelativeTo(RCollection ancestor) {
		int x = this.x;
		int y = this.y;
		RCollection parent = this.parent;
		for (;;) {
			if (parent == null) {
				throw new IllegalArgumentException(
						"Not an ancestor: " + ancestor);
			}
			if (parent == ancestor) {
				return new Point(x, y);
			}
			x += parent.getX();
			y += parent.getY();
			parent = parent.getParent();
		}
	}
}
