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
 * Created on Nov 19, 2005
 */
package org.loboevolution.html.renderertable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.dombl.ModelNode;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.layout.LayoutKey;
import org.loboevolution.html.layout.LayoutValue;
import org.loboevolution.html.renderer.BaseElementRenderable;
import org.loboevolution.html.renderer.BoundableRenderable;
import org.loboevolution.html.renderer.DelayedPair;
import org.loboevolution.html.renderer.FrameContext;
import org.loboevolution.html.renderer.PositionedRenderable;
import org.loboevolution.html.renderer.RenderableContainer;
import org.loboevolution.html.renderer.RenderableSpot;
import org.loboevolution.html.renderer.ZIndexComparator;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.RenderThreadState;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.util.Objects;

/**
 * The Class RTable.
 */
public class RTable extends BaseElementRenderable {

	/** The Constant MAX_CACHE_SIZE. */
	private static final int MAX_CACHE_SIZE = 10;

	/** The cached layout. */
	private final Map<LayoutKey, LayoutValue> cachedLayout = new HashMap<LayoutKey, LayoutValue>(5);

	/** The table matrix. */
	private final TableMatrix tableMatrix;

	/** The positioned renderables. */
	private SortedSet<PositionedRenderable> positionedRenderables;

	/** The other ordinal. */
	private int otherOrdinal;

	/** The last layout key. */
	private LayoutKey lastLayoutKey = null;

	/** The last layout value. */
	private LayoutValue lastLayoutValue = null;

	/**
	 * Instantiates a new r table.
	 *
	 * @param modelNode
	 *            the model node
	 * @param pcontext
	 *            the pcontext
	 * @param rcontext
	 *            the rcontext
	 * @param frameContext
	 *            the frame context
	 * @param container
	 *            the container
	 */
	public RTable(HTMLElementImpl modelNode, UserAgentContext pcontext, HtmlRendererContext rcontext,
			FrameContext frameContext, RenderableContainer container) {
		super(container, modelNode, pcontext);
		this.tableMatrix = new TableMatrix(modelNode, pcontext, rcontext, frameContext, this, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.RElement#getVAlign()
	 */
	@Override
	public int getVAlign() {
		// Not used
		return VALIGN_BASELINE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.renderer.BaseElementRenderable#getWidthElement()
	 */
	@Override
	protected int getWidthElement() {
		return this.width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.renderer.BaseElementRenderable#getHeightElement()
	 */
	@Override
	protected int getHeightElement() {
		return this.tableMatrix.getTableHeightWithoutCaption();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.BaseElementRenderable#getStartY()
	 */
	@Override
	protected int getStartY() {
		return this.tableMatrix.getStartYWithoutCaption();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.BaseElementRenderable#paint(java.awt.
	 * Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		RenderState rs = this.modelNode.getRenderState();
		if (rs != null && rs.getVisibility() != RenderState.VISIBILITY_VISIBLE) {
			// Just don't paint it.
			return;
		}
		try {
			this.prePaint(g);
			Dimension size = this.getSize();
			// TODO: No scrollbars
			TableMatrix tm = this.tableMatrix;
			tm.paint(g, size);
			Collection<PositionedRenderable> prs = this.positionedRenderables;
			if (prs != null) {
				Iterator<PositionedRenderable> i = prs.iterator();
				while (i.hasNext()) {
					PositionedRenderable pr = i.next();
					BoundableRenderable r = pr.getRenderable();
					r.paintTranslated(g);
				}
			}
		} finally {
			// Must always call super implementation
			super.paint(g);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.BaseElementRenderable#doLayout(int,
	 * int, boolean)
	 */
	@Override
	public void doLayout(int availWidth, int availHeight, boolean sizeOnly) {
		Map<LayoutKey, LayoutValue> cachedLayout = this.cachedLayout;
		RenderState rs = this.modelNode.getRenderState();
		int whitespace = rs == null ? RenderState.WS_NORMAL : rs.getWhiteSpace();
		Font font = rs == null ? null : rs.getFont();
		// Having whiteSpace == NOWRAP and having a NOWRAP override
		// are not exactly the same thing.
		boolean overrideNoWrap = RenderThreadState.getState().overrideNoWrap;
		LayoutKey layoutKey = new LayoutKey(availWidth, availHeight, whitespace, font, overrideNoWrap);
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
			Collection<PositionedRenderable> prs = this.positionedRenderables;
			if (prs != null) {
				prs.clear();
			}
			this.otherOrdinal = 0;
			this.clearGUIComponents();
			this.clearDelayedPairs();
			this.applyStyle(availWidth, availHeight);
			TableMatrix tm = this.tableMatrix;
			Insets insets = this.getInsets(false, false);
			tm.reset(insets, availWidth, availHeight);
			// TODO: No scrollbars
			tm.build(availWidth, availHeight, sizeOnly);
			tm.doLayout(insets);

			// Import applicable delayed pairs.
			// Only needs to be done if layout was
			// forced. Otherwise, they should've
			// been imported already.
			Collection<?> pairs = this.delayedPairs;
			if (pairs != null) {
				Iterator<?> i = pairs.iterator();
				while (i.hasNext()) {
					DelayedPair pair = (DelayedPair) i.next();
					if (pair.containingBlock == this) {
						this.importDelayedPair(pair);
					}
				}
			}
			layoutValue = new LayoutValue(tm.getTableWidth(), tm.getTableHeight(), false, false);
			if (sizeOnly) {
				if (cachedLayout.size() > MAX_CACHE_SIZE) {
					// Unlikely, but we should ensure it's bounded.
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
		this.width = layoutValue.getWidth();
		this.height = layoutValue.getHeight();
		this.sendGUIComponentsToParent();
		this.sendDelayedPairsToParent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.renderer.BaseElementRenderable#invalidateLayoutLocal
	 * ()
	 */
	@Override
	public void invalidateLayoutLocal() {
		super.invalidateLayoutLocal();
		this.cachedLayout.clear();
		this.lastLayoutKey = null;
		this.lastLayoutValue = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.render.BoundableRenderable#getRenderablePoint(int,
	 * int)
	 */
	@Override
	public RenderableSpot getLowestRenderableSpot(int x, int y) {
		Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			Iterator<PositionedRenderable> i = prs.iterator();
			while (i.hasNext()) {
				PositionedRenderable pr = i.next();
				BoundableRenderable r = pr.getRenderable();
				int childX = x - r.getX();
				int childY = y - r.getY();
				RenderableSpot rs = r.getLowestRenderableSpot(childX, childY);
				if (rs != null) {
					return rs;
				}
			}
		}
		RenderableSpot rs = this.tableMatrix.getLowestRenderableSpot(x, y);
		if (rs != null) {
			return rs;
		}
		return new RenderableSpot(this, x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.render.BoundableRenderable#onMouseClick(java.awt.
	 * event .MouseEvent, int, int)
	 */
	@Override
	public boolean onMouseClick(MouseEvent event, int x, int y) {
		Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			Iterator<PositionedRenderable> i = prs.iterator();
			while (i.hasNext()) {
				PositionedRenderable pr = i.next();
				BoundableRenderable r = pr.getRenderable();
				Rectangle bounds = r.getBounds();
				if (bounds.contains(x, y)) {
					int childX = x - r.getX();
					int childY = y - r.getY();
					if (!r.onMouseClick(event, childX, childY)) {
						return false;
					}
				}
			}
		}
		return this.tableMatrix.onMouseClick(event, x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.renderer.BoundableRenderable#onDoubleClick(java.awt.
	 * event.MouseEvent, int, int)
	 */
	@Override
	public boolean onDoubleClick(MouseEvent event, int x, int y) {
		Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			Iterator<PositionedRenderable> i = prs.iterator();
			while (i.hasNext()) {
				PositionedRenderable pr = i.next();
				BoundableRenderable r = pr.getRenderable();
				Rectangle bounds = r.getBounds();
				if (bounds.contains(x, y)) {
					int childX = x - r.getX();
					int childY = y - r.getY();
					if (!r.onDoubleClick(event, childX, childY)) {
						return false;
					}
				}
			}
		}
		return this.tableMatrix.onDoubleClick(event, x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.render.BoundableRenderable#onMouseDisarmed(java.awt
	 * .event.MouseEvent)
	 */
	@Override
	public boolean onMouseDisarmed(MouseEvent event) {
		return this.tableMatrix.onMouseDisarmed(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.render.BoundableRenderable#onMousePressed(java.awt.
	 * event.MouseEvent, int, int)
	 */
	@Override
	public boolean onMousePressed(MouseEvent event, int x, int y) {
		Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			Iterator<PositionedRenderable> i = prs.iterator();
			while (i.hasNext()) {
				PositionedRenderable pr = i.next();
				BoundableRenderable r = pr.getRenderable();
				Rectangle bounds = r.getBounds();
				if (bounds.contains(x, y)) {
					int childX = x - r.getX();
					int childY = y - r.getY();
					if (!r.onMousePressed(event, childX, childY)) {
						return false;
					}
				}
			}
		}
		return this.tableMatrix.onMousePressed(event, x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.render.BoundableRenderable#onMouseReleased(java.awt
	 * .event.MouseEvent, int, int)
	 */
	@Override
	public boolean onMouseReleased(MouseEvent event, int x, int y) {
		Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			Iterator<PositionedRenderable> i = prs.iterator();
			while (i.hasNext()) {
				PositionedRenderable pr = i.next();
				BoundableRenderable r = pr.getRenderable();
				Rectangle bounds = r.getBounds();
				if (bounds.contains(x, y)) {
					int childX = x - r.getX();
					int childY = y - r.getY();
					if (!r.onMouseReleased(event, childX, childY)) {
						return false;
					}
				}
			}
		}
		return this.tableMatrix.onMouseReleased(event, x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.render.RCollection#getRenderables()
	 */
	@Override
	public Iterator<BoundableRenderable> getRenderables() {
		Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			Collection<BoundableRenderable> c = new LinkedList<BoundableRenderable>();
			Iterator<PositionedRenderable> i = prs.iterator();
			while (i.hasNext()) {
				PositionedRenderable pr = i.next();
				BoundableRenderable r = pr.getRenderable();
				c.add(r);
			}
			Iterator<BoundableRenderable> i2 = this.tableMatrix.getRenderables();
			while (i2.hasNext()) {
				c.add(i2.next());
			}
			return c.iterator();
		} else {
			return this.tableMatrix.getRenderables();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.dombl.UINode#repaint(org.loboevolution.html.dombl.
	 * ModelNode )
	 */
	@Override
	public void repaint(ModelNode modelNode) {
		// Method not implemented
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.render.RenderableContainer#getBackground()
	 */
	@Override
	public Color getPaintedBackgroundColor() {
		return this.container.getPaintedBackgroundColor();
	}

	/**
	 * Adds the positioned renderable.
	 *
	 * @param renderable
	 *            the renderable
	 * @param verticalAlignable
	 *            the vertical alignable
	 * @param isFloat
	 *            the is float
	 */
	private final void addPositionedRenderable(BoundableRenderable renderable, boolean verticalAlignable,
			boolean isFloat) {
		// Expected to be called only in GUI thread.
		SortedSet<PositionedRenderable> others = this.positionedRenderables;
		if (others == null) {
			others = new TreeSet<PositionedRenderable>(new ZIndexComparator());
			this.positionedRenderables = others;
		}
		others.add(new PositionedRenderable(renderable, verticalAlignable, this.otherOrdinal++, isFloat));
		renderable.setParent(this);
		if (renderable instanceof RUIControl) {
			this.container.addComponent(((RUIControl) renderable).getWidget().getComponent());
		}
	}

	/**
	 * Import delayed pair.
	 *
	 * @param pair
	 *            the pair
	 */
	private void importDelayedPair(DelayedPair pair) {
		pair.positionPairChild();
		BoundableRenderable r = pair.child;
		this.addPositionedRenderable(r, false, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RTable[this=" + System.identityHashCode(this) + ",node=" + this.modelNode + "]";
	}
}
