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
package org.loboevolution.html.renderer.table;

import org.loboevolution.html.control.LayoutKey;
import org.loboevolution.html.control.LayoutValue;
import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.dom.nodeimpl.ModelNode;
import org.loboevolution.html.renderer.*;
import org.loboevolution.html.renderer.info.RBlockInfo;
import org.loboevolution.html.renderstate.RenderState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * <p>RTable class.</p>
 */
public class RTable extends BaseElementRenderable {

	private static final int MAX_CACHE_SIZE = 10;
	private int otherOrdinal;
	private final Map<LayoutKey, LayoutValue> cachedLayout = new HashMap<LayoutKey, LayoutValue>(5);

	private SortedSet<PositionedRenderable> positionedRenderables;

	private final TableMatrix tableMatrix;

	private LayoutKey lastLayoutKey = null;

	private LayoutValue lastLayoutValue = null;

	/**
	 * <p>Constructor for RTable.</p>
	 *
	 * @param info a {@link org.loboevolution.html.renderer.info.RBlockInfo} object.
	 */
	public RTable(final RBlockInfo info) {
		super(info.getParentContainer(), info.getModelNode(), info.getPcontext());
		this.tableMatrix = new TableMatrix(info, this);
	}

	private void addPositionedRenderable(final BoundableRenderable renderable, final boolean verticalAlignable,
                                         final boolean isFloat, final boolean isFixed) {
		SortedSet<PositionedRenderable> others = this.positionedRenderables;
		if (others == null) {
			others = new TreeSet<>(new ZIndexComparator());
			this.positionedRenderables = others;
		}

		others.add(PositionedRenderable.builder().
				renderable(renderable).
				verticalAlignable(verticalAlignable).
				ordinal(otherOrdinal++).
				isFloat(isFloat).
				isFixed(isFixed).build());

		renderable.setParent(this);
		if (renderable instanceof RUIControl) {
			this.container.addComponent(((RUIControl) renderable).widget.getComponent());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void doLayout(final int availWidth, final int availHeight, final boolean sizeOnly) {
		final Map<LayoutKey, LayoutValue> cachedLayout = this.cachedLayout;
		final RenderState rs = this.modelNode.getRenderState();

		final int whitespace = rs == null ? RenderState.WS_NORMAL : rs.getWhiteSpace();
		final Font font = rs == null ? null : rs.getFont();
		final LayoutKey layoutKey = new LayoutKey(availWidth, availHeight, whitespace, font);
		LayoutValue layoutValue;
		if (sizeOnly) {
			layoutValue = cachedLayout.get(layoutKey);
		} else {
			if (Objects.equals(layoutKey, this.lastLayoutKey)) {
				layoutValue = this.lastLayoutValue;
			} else {
				layoutValue = null;
			}
		}


		if (layoutValue == null) {
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

			final Collection<DelayedPair> pairs = this.delayedPairs;
			if (pairs != null) {
				for (final DelayedPair pair : pairs) {
					if (this == pair.getContainingBlock()) {
						importDelayedPair(pair);
					}
				}
			}

			layoutValue = new LayoutValue(tm.getTableWidth(), tm.getTableHeight());
			if (sizeOnly) {
				if (cachedLayout.size() > MAX_CACHE_SIZE) {
					cachedLayout.clear();
				}
				cachedLayout.put(layoutKey, layoutValue);
				this.lastLayoutKey = null;
				this.lastLayoutValue = null;
			} else {
				this.lastLayoutKey = layoutKey;
				this.lastLayoutValue = layoutValue;
			}

		}

		this.setWidth(layoutValue.width);
		this.setHeight(layoutValue.height);
		sendGUIComponentsToParent();
		sendDelayedPairsToParent();
	}

	/** {@inheritDoc} */
	@Override
	public RenderableSpot getLowestRenderableSpot(final int x, final int y) {
		final Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			for (final PositionedRenderable pr : prs) {
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

	/** {@inheritDoc} */
	@Override
	public Color getPaintedBackgroundColor() {
		return this.container.getPaintedBackgroundColor();
	}

	/** {@inheritDoc} */
	@Override
	public List<Renderable> getRenderables() {
		final SortedSet<PositionedRenderable> prs = this.positionedRenderables;
		final List<Renderable> c = new LinkedList<>();
		if (prs != null) {
			for (final PositionedRenderable pr : prs) {
				final BoundableRenderable r = pr.getRenderable();
				c.add(r);
			}
			final List<RTableCell> renderables = this.tableMatrix.getRenderables();
			c.addAll(renderables);
		} else {
			final List<RTableCell> renderables = this.tableMatrix.getRenderables();
			c.addAll(renderables);
		}
		return c;
	}

	private void importDelayedPair(final DelayedPair pair) {
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
	public boolean onDoubleClick(final MouseEvent event, final int x, final int y) {
		final Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			for (final PositionedRenderable pr : prs) {
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

	/** {@inheritDoc} */
	@Override
	public boolean onMouseClick(final MouseEvent event, final int x, final int y) {
		final Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			for (final PositionedRenderable pr : prs) {
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

	/** {@inheritDoc} */
	@Override
	public boolean onMouseDisarmed(final MouseEvent event) {
		return new TableMatrixEvents(this.tableMatrix.getAllCells()).onMouseDisarmed(event);
	}

	/** {@inheritDoc} */
	@Override
	public boolean onMousePressed(final MouseEvent event, final int x, final int y) {
		final Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			for (final PositionedRenderable pr : prs) {
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

	/** {@inheritDoc} */
	@Override
	public boolean onMouseReleased(final MouseEvent event, final int x, final int y) {
		final Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			for (final PositionedRenderable pr : prs) {
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
				for (final PositionedRenderable pr : prs) {
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
	public void repaint(final ModelNode modelNode) {
		// NOP
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "RTable[this=" + System.identityHashCode(this) + ",node=" + this.modelNode + "]";
	}

}
