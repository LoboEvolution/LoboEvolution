/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Apr 17, 2005
 */
package org.loboevolution.html.renderer;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.dom.nodeimpl.ModelNode;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.renderstate.RenderState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Author J. H. S.
 */
@Slf4j
abstract class BaseBoundableRenderable extends RRectangle implements BoundableRenderable {

	/** Constant SELECTION_COLOR */
	protected static final Color SELECTION_COLOR = Color.BLUE;

	/** Constant SELECTION_XOR */
	protected static final Color SELECTION_XOR = Color.LIGHT_GRAY;

	protected final RenderableContainer container;

	@Setter
	private BoundableRenderable delegator;

	protected boolean layoutUpTreeCanBeInvalidated = true;

	protected final ModelNode modelNode;

	protected RCollection originalParent;

	protected RCollection parent;

	/**
	 * <p>Constructor for BaseBoundableRenderable.</p>
	 *
	 * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 * @param modelNode a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 */
	public BaseBoundableRenderable(final RenderableContainer container, final ModelNode modelNode) {
		this.container = container;
		this.modelNode = modelNode;
	}

	/** {@inheritDoc} */
	public boolean contains(final int x, final int y) {
		final int mx = this.getX();
		final int my = this.getY();
		return (x >= mx) && (y >= my) && (x < (mx + this.getVisualWidth())) && (y < (my + this.getVisualHeight()));
	}

	/**
	 * <p>getBlockBackgroundColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getBlockBackgroundColor() {
		return this.container.getPaintedBackgroundColor();
	}

	/** {@inheritDoc} */
	@Override
	public Rectangle getBounds() {
		return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	/**
	 * <p>getVisualHeight.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getVisualHeight() {
		return getHeight();
	}

	/**
	 * <p>getVisualWidth.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getVisualWidth() {
		return getWidth();
	}

	/**
	 * <p>getVisualBounds.</p>
	 *
	 * @return a {@link java.awt.Rectangle} object.
	 */
	public Rectangle getVisualBounds() {
		return new Rectangle(getX(), getY(), getVisualWidth(), getVisualHeight());
	}

	/** {@inheritDoc} */
	@Override
	public Point getGUIPoint(final int clientX, final int clientY) {
		final BoundableRenderable parent = getParent();
		if (parent == null) {
			return this.container.getGUIPoint(clientX + this.getX(), clientY + this.getY());
		} else {
			return parent.getGUIPoint(clientX + this.getX(), clientY + this.getY());
		}
	}

	/** {@inheritDoc} */
	@Override
	public ModelNode getModelNode() {
		return this.modelNode;
	}

	/** {@inheritDoc} */
	@Override
	public Point getOrigin() {
		return new Point(this.getX(), this.getY());
	}

	/** {@inheritDoc} */
	@Override
	public RCollection getOriginalOrCurrentParent() {
		final RCollection origParent = this.originalParent;
		if (origParent == null) {
			return this.parent;
		}
		return origParent;
	}

	/**
	 * {@inheritDoc}
	 *
	 * This is the parent based on the original element hierarchy.
	 */
	@Override
	public RCollection getOriginalParent() {
		return this.originalParent;
	}

	/** {@inheritDoc} */
	@Override
	public Point getOriginRelativeTo(final RCollection ancestor) {
		int x = this.getX();
		int y = this.getY();
		RCollection parent = this.parent;
		for (;;) {
			if (parent == null) {
				return new Point(x, y);
			}
			if (parent == ancestor) {
				return new Point(x, y);
			}
			x += parent.getX();
			y += parent.getY();
			parent = parent.getParent();
		}
	}

	/** {@inheritDoc} */
	@Override
	public Point getOriginRelativeToNoScroll(final RCollection ancestor) {
		if (ancestor == this) {
			return new Point(0, 0);
		}

		int x = this.getX();
		int y = this.getY();

		RCollection parent = this.parent;
		for (;;) {
			if (parent == null) {
				return new Point(x, y);
			}
			if (parent == ancestor) {
				return new Point(x, y);
			}
			x += parent.getX();
			y += parent.getY();
			parent = parent.getParent();
		}
	}

	/** {@inheritDoc} */
	public Point getOriginRelativeToAbs(final RCollection ancestor) {
		if (ancestor == this) {
			return new Point(0, 0);
		}

		int x = this.getX();
		int y = this.getY();

		int nextX = 0;
		int nextY = 0;

		RCollection parent = this.parent;
		for (;;) {
			if (parent == null) {
				return new Point(x, y);
			}
			if (parent == ancestor) {
				return new Point(x, y);
			}
			x += nextX;
			y += nextY;
			nextX = parent.getX();
			nextY = parent.getY();
			parent = parent.getParent();
		}
	}

	/** {@inheritDoc} */
	@Override
	public RCollection getParent() {
		return this.parent;
	}

	/** {@inheritDoc} */
	@Override
	public Point getRenderablePoint(final int guiX, final int guiY) {
		final BoundableRenderable parent = getParent();
		if (parent == null) {
			return new Point(guiX - this.getX(), guiY - this.getY());
		} else {
			return parent.getRenderablePoint(guiX - this.getX(), guiY - this.getY());
		}
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getSize() {
		return new Dimension(this.getWidth(), this.getHeight());
	}

	/**
	 * <p>invalidateLayoutLocal.</p>
	 */
	protected abstract void invalidateLayoutLocal();

	/**
	 * {@inheritDoc}
	 *
	 * Invalidates this Renderable and its parent (i.e. all ancestors).
	 */
	@Override
	public final void invalidateLayoutUpTree() {
		if (this.layoutUpTreeCanBeInvalidated) {
			this.layoutUpTreeCanBeInvalidated = false;
			invalidateLayoutLocal();
			// Try original parent first.
			RCollection parent = this.originalParent;
			if (parent == null) {
				parent = this.parent;
				if (parent == null) {
					// Has to be top block
					final RenderableContainer rc = this.container;
					if (rc != null) {
						rc.invalidateLayoutUpTree();
					}
				} else {
					parent.invalidateLayoutUpTree();
				}
			} else {
				parent.invalidateLayoutUpTree();
			}
		}
	}

	/**
	 * <p>isValid.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isValid() {
		return this.layoutUpTreeCanBeInvalidated;
	}

	/**
	 * <p>markLayoutValid.</p>
	 */
	public void markLayoutValid() {
		this.layoutUpTreeCanBeInvalidated = true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean onDoubleClick(final MouseEvent event, final int x, final int y) {
		final ModelNode me = this.modelNode;
		if (me != null) {
			return HtmlController.getInstance().onDoubleClick(me, event, x, y);
		} else {
			return true;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean onMouseClick(final MouseEvent event, final int x, final int y) {
		final ModelNode me = this.modelNode;
		if (me != null) {
			return HtmlController.getInstance().onMouseClick(me, event, x, y);
		} else {
			return true;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean onMouseDisarmed(final MouseEvent event) {
		final ModelNode me = this.modelNode;
		if (me != null) {
			return HtmlController.getInstance().onMouseDisarmed(me, event);
		} else {
			return true;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean onMousePressed(final MouseEvent event, final int x, final int y) {
		final ModelNode me = this.modelNode;
		if (me != null) {
			return HtmlController.getInstance().onMouseDown(me, event, x, y);
		} else {
			return true;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean onMouseReleased(final MouseEvent event, final int x, final int y) {
		final ModelNode me = this.modelNode;
		if (me != null) {
			return HtmlController.getInstance().onMouseUp(me, event, x, y);
		} else {
			return true;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean onRightClick(final MouseEvent event, final int x, final int y) {
		final ModelNode me = this.modelNode;
		if (me != null) {
			return HtmlController.getInstance().onContextMenu(me, event, x, y);
		} else {
			return true;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void onMouseMoved(final MouseEvent event, final int x, final int y, final boolean triggerEvent, final ModelNode limit) {
		if (triggerEvent) {
			if (isContainedByNode()) {
				HtmlController.getInstance().onMouseOver(this.modelNode, event, x, y, limit);
				resetCursorOnMouseOut(this.modelNode, limit);
			} else {
				HtmlController.getInstance().onMouseMoved(this.modelNode, event, x, y, limit);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void onMouseOut(final MouseEvent event, final int x, final int y, final ModelNode limit) {
		if (isContainedByNode()) {
			HtmlController.getInstance().onMouseOut(this.modelNode, event, x, y, limit);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void onMouseScroll() {
        if (this.modelNode != null) {
			HtmlController.getInstance().onMouseScroll(this.modelNode);
		}
	}

	private static void resetCursorOnMouseOut(final ModelNode nodeStart, final ModelNode limit) {
		Cursor foundCursorOpt = null;
		ModelNode node = limit;
		while (node != null) {

			if (node instanceof NodeImpl uiElement) {
                final RenderState rs = uiElement.getRenderState();
				final Cursor cursorOpt = rs.getCursor();
				foundCursorOpt = cursorOpt;
				if (cursorOpt == null) {
					break;
				}
			}
			node = node.getParentModelNode();
		}

		if (nodeStart instanceof NodeImpl uiElement) {
            final HtmlRendererContext rcontext = uiElement.getHtmlRendererContext();
			rcontext.setCursor(foundCursorOpt);
		}
	}

	/** {@inheritDoc} */
	@Override
	public final void paintTranslated(final Graphics g) {
		final int x = this.getX();
		final int y = this.getY();
		g.translate(x, y);
		try {
			paint(g);
		} finally {
			g.translate(-x, -y);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * Invalidates the current Renderable (which invalidates its ancestors) and then
	 * requests the top level GUI container to do the layout and repaint. It's safe
	 * to call this method outside the GUI thread.
	 */
	@Override
	public void relayout() {
		if (SwingUtilities.isEventDispatchThread()) {
			relayoutImpl(true, false);
		} else {
			SwingUtilities.invokeLater(() -> relayoutImpl(true, false));
		}
	}

	/**
	 * <p>relayoutIfValid.</p>
	 */
	public void relayoutIfValid() {
		if (SwingUtilities.isEventDispatchThread()) {
			relayoutImpl(true, true);
		} else {
			SwingUtilities.invokeLater(() -> relayoutImpl(true, true));
		}
	}

	private void relayoutImpl(final boolean invalidateLocal, final boolean onlyIfValid) {
		if (onlyIfValid && !this.layoutUpTreeCanBeInvalidated) {
			return;
		}
		if (invalidateLocal) {
			invalidateLayoutUpTree();
		}
		final Renderable parent = this.parent;
		if (parent instanceof BaseBoundableRenderable) {
			((BaseBoundableRenderable) parent).relayoutImpl(false, false);
		} else if (parent == null) {
			// Has to be top RBlock.
			this.container.relayout();
		} else {
			log.warn("relayout(): Don't know how to relayout {} parent being {}", this, parent);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void repaint() {
		this.repaint(0, 0, this.getWidth(), this.getHeight());
	}

	/** {@inheritDoc} */
	@Override
	public void repaint(final int x, final int y, final int width, final int height) {

		if (isDelegated()) {
			delegator.repaint(x, y, width, height);
			return;
		}

		final BoundableRenderable parent = this.parent;
		if (parent == null) {
			this.container.repaint(x, y, width, height);
		} else {
			parent.repaint(x + this.getX(), y + this.getY(), width, height);
		}
	}


	/** {@inheritDoc} */
	@Override
	public void setOriginalParent(final RCollection origParent) {
		this.originalParent = origParent;
	}

	/** {@inheritDoc} */
	@Override
	public void setParent(final RCollection parent) {
		this.parent = parent;
	}

	public boolean isDelegated() {
		return delegator != null;
	}

	@Override
	public RRectangle getRectangle() {
		return this;
	}
}
