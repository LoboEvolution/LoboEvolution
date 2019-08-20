/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

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
 * Created on Nov 19, 2005
 */
package org.lobobrowser.html.renderer;

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
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.lobobrowser.html.dom.domimpl.HTMLElementImpl;
import org.lobobrowser.html.dom.domimpl.ModelNode;
import org.lobobrowser.html.style.RenderState;
import org.lobobrowser.http.HtmlRendererContext;
import org.lobobrowser.http.UserAgentContext;
import java.util.Objects;

class RTable extends BaseElementRenderable {
	private static class LayoutKey {
		public final int availHeight;
		public final int availWidth;
		public final Font font;
		public final int whitespace;

		public LayoutKey(int availWidth, int availHeight, int whitespace, Font font) {
			super();
			this.availWidth = availWidth;
			this.availHeight = availHeight;
			this.whitespace = whitespace;
			this.font = font;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (!(obj instanceof LayoutKey)) {
				return false;
			}
			final LayoutKey other = (LayoutKey) obj;
			return other.availWidth == this.availWidth && other.availHeight == this.availHeight
					&& other.whitespace == this.whitespace && Objects.equals(other.font, this.font);
		}

		@Override
		public int hashCode() {
			final Font font = this.font;
			return this.availWidth * 1000 + this.availHeight ^ (font == null ? 0 : font.hashCode());
		}
	}

	private static class LayoutValue {
		public final int height;
		public final int width;

		public LayoutValue(int width, int height) {
			this.width = width;
			this.height = height;
		}
	}

	private static final int MAX_CACHE_SIZE = 10;
	private final Map<LayoutKey, LayoutValue> cachedLayout = new HashMap<LayoutKey, LayoutValue>(5);
	private LayoutKey lastLayoutKey = null;
	private LayoutValue lastLayoutValue = null;
	private int otherOrdinal;

	private SortedSet<PositionedRenderable> positionedRenderables;

	private final TableMatrix tableMatrix;

	public RTable(HTMLElementImpl modelNode, UserAgentContext pcontext, HtmlRendererContext rcontext,
			FrameContext frameContext, RenderableContainer container) {
		super(container, modelNode, pcontext);
		this.tableMatrix = new TableMatrix(modelNode, pcontext, rcontext, frameContext, this, this);
	}

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
			this.container.addComponent(((RUIControl) renderable).widget.getComponent());
		}
	}

	@Override
	public void doLayout(int availWidth, int availHeight, boolean sizeOnly) {
		final Map<LayoutKey, LayoutValue> cachedLayout = this.cachedLayout;
		final RenderState rs = this.modelNode.getRenderState();
		final int whitespace = rs == null ? RenderState.WS_NORMAL : rs.getWhiteSpace();
		final Font font = rs == null ? null : rs.getFont();
		final LayoutKey layoutKey = new LayoutKey(availWidth, availHeight, whitespace, font);
		LayoutValue layoutValue;
		if (sizeOnly) {
			layoutValue = (LayoutValue) cachedLayout.get(layoutKey);
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

			// Import applicable delayed pairs.
			// Only needs to be done if layout was
			// forced. Otherwise, they should've
			// been imported already.
			final Collection<?> pairs = this.delayedPairs;
			if (pairs != null) {
				final Iterator<?> i = pairs.iterator();
				while (i.hasNext()) {
					final DelayedPair pair = (DelayedPair) i.next();
					if (pair.containingBlock == this) {
						importDelayedPair(pair);
					}
				}
			}
			layoutValue = new LayoutValue(tm.getTableWidth(), tm.getTableHeight());
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
		this.width = layoutValue.width;
		this.height = layoutValue.height;
		sendGUIComponentsToParent();
		sendDelayedPairsToParent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.renderer.BoundableRenderable#getRenderablePoint(int,
	 * int)
	 */
	@Override
	public RenderableSpot getLowestRenderableSpot(int x, int y) {
		final Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			final Iterator<PositionedRenderable> i = prs.iterator();
			while (i.hasNext()) {
				final PositionedRenderable pr = (PositionedRenderable) i.next();
				final BoundableRenderable r = pr.renderable;
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
	 * @see org.xamjwg.html.renderer.RenderableContainer#getBackground()
	 */
	@Override
	public Color getPaintedBackgroundColor() {
		return this.container.getPaintedBackgroundColor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.renderer.RCollection#getRenderables()
	 */
	@Override
	public Iterator getRenderables() {
		final SortedSet<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			final Collection c = new java.util.LinkedList();
			final Iterator<PositionedRenderable> i = prs.iterator();
			while (i.hasNext()) {
				final PositionedRenderable pr = i.next();
				final BoundableRenderable r = pr.renderable;
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

	@Override
	public int getVAlign() {
		// Not used
		return VALIGN_BASELINE;
	}

	private void importDelayedPair(DelayedPair pair) {
		pair.positionPairChild();
		final BoundableRenderable r = pair.child;
		addPositionedRenderable(r, false, false);
	}

	@Override
	public void invalidateLayoutLocal() {
		super.invalidateLayoutLocal();
		this.cachedLayout.clear();
		this.lastLayoutKey = null;
		this.lastLayoutValue = null;
	}

	@Override
	public boolean onDoubleClick(MouseEvent event, int x, int y) {
		final Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			final Iterator<PositionedRenderable> i = prs.iterator();
			while (i.hasNext()) {
				final PositionedRenderable pr = (PositionedRenderable) i.next();
				final BoundableRenderable r = pr.renderable;
				final Rectangle bounds = r.getBounds();
				if (bounds.contains(x, y)) {
					final int childX = x - r.getX();
					final int childY = y - r.getY();
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
	 * org.xamjwg.html.renderer.BoundableRenderable#onMouseClick(java.awt.event.
	 * MouseEvent, int, int)
	 */
	@Override
	public boolean onMouseClick(MouseEvent event, int x, int y) {
		final Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			final Iterator<PositionedRenderable> i = prs.iterator();
			while (i.hasNext()) {
				final PositionedRenderable pr = (PositionedRenderable) i.next();
				final BoundableRenderable r = pr.renderable;
				final Rectangle bounds = r.getBounds();
				if (bounds.contains(x, y)) {
					final int childX = x - r.getX();
					final int childY = y - r.getY();
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
	 * org.xamjwg.html.renderer.BoundableRenderable#onMouseDisarmed(java.awt.event.
	 * MouseEvent)
	 */
	@Override
	public boolean onMouseDisarmed(MouseEvent event) {
		return this.tableMatrix.onMouseDisarmed(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xamjwg.html.renderer.BoundableRenderable#onMousePressed(java.awt.event.
	 * MouseEvent, int, int)
	 */
	@Override
	public boolean onMousePressed(MouseEvent event, int x, int y) {
		final Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			final Iterator<PositionedRenderable> i = prs.iterator();
			while (i.hasNext()) {
				final PositionedRenderable pr = (PositionedRenderable) i.next();
				final BoundableRenderable r = pr.renderable;
				final Rectangle bounds = r.getBounds();
				if (bounds.contains(x, y)) {
					final int childX = x - r.getX();
					final int childY = y - r.getY();
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
	 * org.xamjwg.html.renderer.BoundableRenderable#onMouseReleased(java.awt.event.
	 * MouseEvent, int, int)
	 */
	@Override
	public boolean onMouseReleased(MouseEvent event, int x, int y) {
		final Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			final Iterator<PositionedRenderable> i = prs.iterator();
			while (i.hasNext()) {
				final PositionedRenderable pr = (PositionedRenderable) i.next();
				final BoundableRenderable r = pr.renderable;
				final Rectangle bounds = r.getBounds();
				if (bounds.contains(x, y)) {
					final int childX = x - r.getX();
					final int childY = y - r.getY();
					if (!r.onMouseReleased(event, childX, childY)) {
						return false;
					}
				}
			}
		}
		return this.tableMatrix.onMouseReleased(event, x, y);
	}

	@Override
	public void paint(Graphics g) {
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
				final Iterator<PositionedRenderable> i = prs.iterator();
				while (i.hasNext()) {
					final PositionedRenderable pr = (PositionedRenderable) i.next();
					final BoundableRenderable r = pr.renderable;
					r.paintTranslated(g);
				}
			}
		} finally {
			// Must always call super implementation
			super.paint(g);
		}
	}

	@Override
	public void repaint(ModelNode modelNode) {
		// NOP
	}

	@Override
	public String toString() {
		return "RTable[this=" + System.identityHashCode(this) + ",node=" + this.modelNode + "]";
	}

}
