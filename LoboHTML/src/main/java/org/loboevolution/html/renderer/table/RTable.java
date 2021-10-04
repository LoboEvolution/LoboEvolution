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
package org.loboevolution.html.renderer.table;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.SortedSet;
import java.util.TreeSet;

import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.nodeimpl.ModelNode;
import org.loboevolution.html.renderer.BaseElementRenderable;
import org.loboevolution.html.renderer.BoundableRenderable;
import org.loboevolution.html.renderer.DelayedPair;
import org.loboevolution.html.renderer.FrameContext;
import org.loboevolution.html.renderer.PositionedRenderable;
import org.loboevolution.html.renderer.RenderableContainer;
import org.loboevolution.html.renderer.RenderableSpot;
import org.loboevolution.html.renderer.ZIndexComparator;

import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;

/**
 * <p>RTable class.</p>
 *
 *
 *
 */
public class RTable extends BaseElementRenderable {

	private int otherOrdinal;

	private SortedSet<PositionedRenderable> positionedRenderables;

	private final TableMatrix tableMatrix;

	/**
	 * <p>Constructor for RTable.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param pcontext a {@link org.loboevolution.http.UserAgentContext} object.
	 * @param rcontext a {@link org.loboevolution.http.HtmlRendererContext} object.
	 * @param frameContext a {@link org.loboevolution.html.renderer.FrameContext} object.
	 * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 */
	public RTable(HTMLElementImpl modelNode, UserAgentContext pcontext, HtmlRendererContext rcontext,
			FrameContext frameContext, RenderableContainer container) {
		super(container, modelNode, pcontext);
		this.tableMatrix = new TableMatrix(modelNode, pcontext, rcontext, frameContext, this, this);
	}

	private void addPositionedRenderable(final BoundableRenderable renderable, final boolean verticalAlignable,
                                         final boolean isFloat, final boolean isFixed) {
		SortedSet<PositionedRenderable> others = this.positionedRenderables;
		if (others == null) {
			others = new TreeSet<>(new ZIndexComparator());
			this.positionedRenderables = others;
		}
		
		final PositionedRenderable posRes = new PositionedRenderable();
		posRes.setRenderable(renderable);
		posRes.setVerticalAlignable(verticalAlignable);
		posRes.setOrdinal(otherOrdinal++);
		posRes.setFloat(isFloat);
		posRes.setFixed(isFixed);
		others.add(posRes);
		renderable.setParent(this);
		if (renderable instanceof RUIControl) {
			this.container.addComponent(((RUIControl) renderable).widget.getComponent());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void doLayout(int availWidth, int availHeight, boolean sizeOnly) {
		final Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			prs.clear();
		}
		this.otherOrdinal = 0;
		clearGUIComponents();
		clearDelayedPairs();
		applyStyle(availWidth, availHeight);
		final TableMatrix tm = this.tableMatrix;
		final Insets insets = getInsets(false, false);
		tm.reset(insets, availWidth, availHeight);
		// TODO: No scrollbars
		tm.build(availWidth, availHeight, sizeOnly);
		tm.doLayout(insets);

		// Import applicable delayed pairs.
		// Only needs to be done if layout was
		// forced. Otherwise, they should've
		// been imported already.
		final Collection<DelayedPair> pairs = this.delayedPairs;
		if (pairs != null) {
			for (DelayedPair pair : pairs) {
				if (this == pair.getContainingBlock()) {
					importDelayedPair(pair);
				}
			}
		}

		this.width = tm.getTableWidth();
		this.height = tm.getTableHeight();
		sendGUIComponentsToParent();
		sendDelayedPairsToParent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.rendered.BoundableRenderable#getRenderablePoint(int,
	 * int)
	 */
	/** {@inheritDoc} */
	@Override
	public RenderableSpot getLowestRenderableSpot(int x, int y) {
		final Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			for (PositionedRenderable pr : prs) {
				final BoundableRenderable r = pr.getRenderable();
				final int childX = x - r.getX();
				final int childY = y - r.getY();
				final RenderableSpot rs = r.getLowestRenderableSpot(childX, childY);
				if (rs != null) {
					return rs;
				}
			}
		}
		final RenderableSpot rs = this.tableMatrix.getLowestRenderableSpot(x, y);
		if (rs != null) {
			return rs;
		}
		return new RenderableSpot(this, x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.rendered.RenderableContainer#getBackground()
	 */
	/** {@inheritDoc} */
	@Override
	public Color getPaintedBackgroundColor() {
		return this.container.getPaintedBackgroundColor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.rendered.RCollection#getRenderables()
	 */
	/** {@inheritDoc} */
	@Override
	public Iterator getRenderables() {
		final SortedSet<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			final Collection c = new LinkedList();
			for (PositionedRenderable pr : prs) {
				final BoundableRenderable r = pr.getRenderable();
				c.add(r);
			}
			final Iterator i2 = this.tableMatrix.getRenderables();
			while (i2.hasNext()) {
				c.add(i2.next());
			}
			return c.iterator();
		} else {
			return this.tableMatrix.getRenderables();
		}
	}

	private void importDelayedPair(DelayedPair pair) {
	    final BoundableRenderable r = pair.positionPairChild();
	    this.addPositionedRenderable(r, false, false, pair.isFixed());
	}

	/** {@inheritDoc} */
	@Override
	public void invalidateLayoutLocal() {
		super.invalidateLayoutLocal();
	}

	/** {@inheritDoc} */
	@Override
	public boolean onDoubleClick(final MouseEvent event, int x, int y) {
		final Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			for (PositionedRenderable pr : prs) {
				final BoundableRenderable r = pr.getRenderable();
				final Rectangle bounds = r.getVisualBounds();
				if (bounds.contains(x, y)) {
					final int childX = x - r.getX();
					final int childY = y - r.getY();
					if (!r.onDoubleClick(event, childX, childY)) {
						return false;
					}
				}
			}
		}
		
		
		return new TableMatrixEvents(this.tableMatrix.getAllCells()).onDoubleClick(event, x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.rendered.BoundableRenderable#onMouseClick(java.awt.event.
	 * MouseEvent, int, int)
	 */
	/** {@inheritDoc} */
	@Override
	public boolean onMouseClick(final MouseEvent event, int x, int y) {
		final Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			for (PositionedRenderable pr : prs) {
				final BoundableRenderable r = pr.getRenderable();
				final Rectangle bounds = r.getVisualBounds();
				if (bounds.contains(x, y)) {
					final int childX = x - r.getX();
					final int childY = y - r.getY();
					if (!r.onMouseClick(event, childX, childY)) {
						return false;
					}
				}
			}
		}
		return new TableMatrixEvents(this.tableMatrix.getAllCells()).onMouseClick(event, x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.rendered.BoundableRenderable#onMouseDisarmed(java.awt.event.
	 * MouseEvent)
	 */
	/** {@inheritDoc} */
	@Override
	public boolean onMouseDisarmed(final MouseEvent event) {
		return new TableMatrixEvents(this.tableMatrix.getAllCells()).onMouseDisarmed(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.rendered.BoundableRenderable#onMousePressed(java.awt.event.
	 * MouseEvent, int, int)
	 */
	/** {@inheritDoc} */
	@Override
	public boolean onMousePressed(final MouseEvent event, int x, int y) {
		final Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			for (PositionedRenderable pr : prs) {
				final BoundableRenderable r = pr.getRenderable();
				final Rectangle bounds = r.getVisualBounds();
				if (bounds.contains(x, y)) {
					final int childX = x - r.getX();
					final int childY = y - r.getY();
					if (!r.onMousePressed(event, childX, childY)) {
						return false;
					}
				}
			}
		}
		return new TableMatrixEvents(this.tableMatrix.getAllCells()).onMousePressed(event, x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.rendered.BoundableRenderable#onMouseReleased(java.awt.event.
	 * MouseEvent, int, int)
	 */
	/** {@inheritDoc} */
	@Override
	public boolean onMouseReleased(final MouseEvent event, int x, int y) {
		final Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			for (PositionedRenderable pr : prs) {
				final BoundableRenderable r = pr.getRenderable();
				final Rectangle bounds = r.getVisualBounds();
				if (bounds.contains(x, y)) {
					final int childX = x - r.getX();
					final int childY = y - r.getY();
					if (!r.onMouseReleased(event, childX, childY)) {
						return false;
					}
				}
			}
		}
		return new TableMatrixEvents(this.tableMatrix.getAllCells()).onMouseReleased(event, x, y);
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g) {
		final RenderState rs = this.modelNode.getRenderState();
		if (rs != null && rs.getVisibility() != RenderState.VISIBILITY_VISIBLE) {
			// Just don't paint it.
			return;
		}
		try {
			prePaint(g);
			final Dimension size = getSize();
			// TODO: No scrollbars
			final TableMatrix tm = this.tableMatrix;
			tm.paint(g, size);
			final Collection<PositionedRenderable> prs = this.positionedRenderables;
			if (prs != null) {
				for (PositionedRenderable pr : prs) {
					final BoundableRenderable r = pr.getRenderable();
					r.paintTranslated(g);
				}
			}
		} finally {
			// Must always call super implementation
			super.paint(g);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void repaint(ModelNode modelNode) {
		// NOP
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "RTable[this=" + System.identityHashCode(this) + ",node=" + this.modelNode + "]";
	}

}
