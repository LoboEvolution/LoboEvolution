/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Apr 17, 2005
 */
package org.lobobrowser.html.renderer;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.dombl.ModelNode;
import org.lobobrowser.html.domimpl.DOMNodeImpl;
import org.lobobrowser.html.renderstate.RenderState;

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
     * @param container
     *            the container
     * @param modelNode
     *            the model node
     */
    public BaseBoundableRenderable(RenderableContainer container,
            ModelNode modelNode) {
        this.container = container;
        this.modelNode = modelNode;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderer.BoundableRenderable#getGUIPoint(int, int)
     */
    @Override
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

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.renderer.BoundableRenderable#getRenderablePoint(int,
     * int)
     */
    @Override
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

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderer.BoundableRenderable#getHeight()
     */
    @Override
    public int getHeight() {
        return height;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderer.BoundableRenderable#getWidth()
     */
    @Override
    public int getWidth() {
        return width;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderer.BoundableRenderable#setWidth(int)
     */
    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderer.BoundableRenderable#getX()
     */
    @Override
    public int getX() {
        return x;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderer.BoundableRenderable#getY()
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * Contains.
     *
     * @param x
     *            the x
     * @param y
     *            the y
     * @return true, if successful
     */
    public boolean contains(int x, int y) {
        return (x >= this.x) && (y >= this.y) && (x < (this.x + this.width))
                && (y < (this.y + this.height));
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderer.BoundableRenderable#getBounds()
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderer.BoundableRenderable#getSize()
     */
    @Override
    public Dimension getSize() {
        return new Dimension(this.width, this.height);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderer.Renderable#getModelNode()
     */
    @Override
    public ModelNode getModelNode() {
        return this.modelNode;
    }

    // /* (non-Javadoc)
    // * @see net.sourceforge.xamj.domimpl.markup.Renderable#getBounds()
    // */
    // public Rectangle getBounds() {
    // return this.bounds;
    //}
    //
    @Override
    public void setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderer.BoundableRenderable#setX(int)
     */
    @Override
    public void setX(int x) {
        this.x = x;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderer.BoundableRenderable#setY(int)
     */
    @Override
    public void setY(int y) {
        this.y = y;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderer.BoundableRenderable#setHeight(int)
     */
    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderer.BoundableRenderable#setOrigin(int, int)
     */
    @Override
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
    @Override
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

    /** Checks if is valid.
	 *
	 * @return true, if is valid
	 */
    public boolean isValid() {
        return this.layoutUpTreeCanBeInvalidated;
    }

    /**
     * Relayout impl.
     *
     * @param invalidateLocal
     *            the invalidate local
     * @param onlyIfValid
     *            the only if valid
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
    @Override
    public void relayout() {
        if (SwingUtilities.isEventDispatchThread()) {
            this.relayoutImpl(true, false);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
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
        if (SwingUtilities.isEventDispatchThread()) {
            this.relayoutImpl(true, true);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
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

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.renderer.BoundableRenderable#setParent(org.lobobrowser
     * .html.renderer.RCollection)
     */
    @Override
    public void setParent(RCollection parent) {
        this.parent = parent;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderer.BoundableRenderable#getParent()
     */
    @Override
    public RCollection getParent() {
        return this.parent;
    }

    /**
     * Parent for invalidation.
     */
    protected RCollection originalParent;

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderer.BoundableRenderable#setOriginalParent(org.
     * lobobrowser.html.renderer.RCollection)
     */
    @Override
    public void setOriginalParent(RCollection origParent) {
        this.originalParent = origParent;
    }

    /**
     * This is the parent based on the original element hierarchy.
     *
     * @return the original parent
     */
    @Override
    public RCollection getOriginalParent() {
        return this.originalParent;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.renderer.BoundableRenderable#getOriginalOrCurrentParent
     * ()
     */
    @Override
    public RCollection getOriginalOrCurrentParent() {
        RCollection origParent = this.originalParent;
        if (origParent == null) {
            return this.parent;
        }
        return origParent;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderer.BoundableRenderable#repaint(int, int, int,
     * int)
     */
    @Override
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

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderer.BoundableRenderable#repaint()
     */
    @Override
    public void repaint() {
        this.repaint(0, 0, this.width, this.height);
    }

    /** Gets the block background color.
	 *
	 * @return the block background color
	 */
    public Color getBlockBackgroundColor() {
        return this.container.getPaintedBackgroundColor();
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.renderer.BoundableRenderable#paintTranslated(java.awt
     * .Graphics)
     */
    @Override
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
     * @param descendent
     *            the descendent
     * @param x
     *            the x
     * @param y
     *            the y
     * @return the java.awt. point
     */
    protected final Point translateDescendentPoint(
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

    
    @Override
    public void onMouseOut(MouseEvent event, int x, int y, ModelNode limit) {
        if (this.isContainedByNode()) {
            HtmlController.getInstance().onMouseOut(this.modelNode, event, x, y, limit);
            resetCursorOnMouseOut(this.modelNode, limit);
        }
    }


    @Override
    public void onMouseMoved(MouseEvent event, int x, int y,
            boolean triggerEvent, ModelNode limit) {
        if (triggerEvent) {
            if (this.isContainedByNode()) {
                HtmlController.getInstance().onMouseOver(this.modelNode, event, x, y, limit);
                setMouseOnMouseOver(this, this.modelNode, limit);
            }
        }
    }
    
    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderer.BoundableRenderable#getOrigin()
     */
    @Override
    public Point getOrigin() {
        return new Point(this.x, this.y);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.renderer.BoundableRenderable#getOriginRelativeTo(org
     * .lobobrowser.html.renderer.RCollection)
     */
    @Override
    public Point getOriginRelativeTo(RCollection ancestor) {
        int x = this.x;
        int y = this.y;
        RCollection parent = this.parent;
        for (;;) {
            if (parent == null) {
                throw new IllegalArgumentException("Not an ancestor: "
                        + ancestor);
            }
            if (parent == ancestor) {
                return new Point(x, y);
            }
            x += parent.getX();
            y += parent.getY();
            parent = parent.getParent();
        }
    }
    
    private static void resetCursorOnMouseOut(final ModelNode nodeStart, final ModelNode limit) {
        Optional<Cursor> foundCursorOpt = Optional.empty();
        ModelNode node = limit;
        while (node != null) {
          if (node instanceof DOMNodeImpl) {
            final DOMNodeImpl uiElement = (DOMNodeImpl) node;

            final RenderState rs = uiElement.getRenderState();
            final Optional<Cursor> cursorOpt = rs.getCursor();
            foundCursorOpt = cursorOpt;
            if (cursorOpt.isPresent()) {
              break;
            }

          }
          node = node.getParentModelNode();
        }

        if (nodeStart instanceof DOMNodeImpl) {
          final DOMNodeImpl uiElement = (DOMNodeImpl) nodeStart;
          final HtmlRendererContext rcontext = uiElement.getHtmlRendererContext();
          rcontext.setCursor(foundCursorOpt);
        }
      }
    
    private static void setMouseOnMouseOver(final BaseBoundableRenderable renderable, final ModelNode nodeStart, final ModelNode limit) {
        {
          ModelNode node = nodeStart;
          while (node != null) {
            if (node == limit) {
              break;
            }
            if (node instanceof DOMNodeImpl) {
              DOMNodeImpl uiElement = (DOMNodeImpl) node;
              HtmlRendererContext rcontext = uiElement.getHtmlRendererContext();
              RenderState rs = uiElement.getRenderState();
              Optional<Cursor> cursorOpt = rs.getCursor();
              if (cursorOpt.isPresent()) {
                rcontext.setCursor(cursorOpt);
                break;
              } else {
                if (node.getParentModelNode() == limit) {                    
                  if (renderable instanceof RWord || renderable instanceof RBlank) {
                    rcontext.setCursor(Optional.of(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR)));
                  } 
                }
              }
            }
            node = node.getParentModelNode();
          }

        }
      }
}
