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
 * Created on Apr 17, 2005
 */
package org.loboevolution.html.control;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.loboevolution.html.dom.domimpl.ModelNode;
import org.loboevolution.html.dom.domimpl.NodeImpl;
import org.loboevolution.html.dom.domimpl.UINode;
import org.loboevolution.html.renderer.BaseElementRenderable;
import org.loboevolution.html.renderer.FrameContext;
import org.loboevolution.html.renderer.RBlockViewport;
import org.loboevolution.html.renderer.Renderable;
import org.loboevolution.html.renderer.RenderableContainer;
import org.loboevolution.html.renderer.RenderableSpot;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.http.UserAgentContext;
import java.util.Objects;

/**
 * <p>RUIControl class.</p>
 *
 * @author J. H. S.
 * @version $Id: $Id
 */
public class RUIControl extends BaseElementRenderable {
	private static class LayoutKey {
		public final int availHeight;
		public final int availWidth;
		public final Font font;
		public final int whitespace;

		public LayoutKey(int availWidth, int availHeight, int whitespace, Font font) {
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
	private final Map<LayoutKey, LayoutValue> cachedLayout = new HashMap<LayoutKey, LayoutValue>();

	protected int declaredHeight = -1;

	protected int declaredWidth = -1;

	private final FrameContext frameContext;

	private LayoutKey lastLayoutKey = null;

	private LayoutValue lastLayoutValue = null;

	protected final ModelNode modelNode;

	public final UIControl widget;

    /**
     * <p>Constructor for RUIControl.</p>
     *
     * @param me a {@link org.loboevolution.html.dom.domimpl.ModelNode} object.
     * @param widget a {@link org.loboevolution.html.control.UIControl} object.
     * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
     * @param frameContext a {@link org.loboevolution.html.renderer.FrameContext} object.
     * @param ucontext a {@link org.loboevolution.http.UserAgentContext} object.
     */
    public RUIControl(final ModelNode me, final UIControl widget, final RenderableContainer container,
            final FrameContext frameContext, final UserAgentContext ucontext) {
		super(container, me, ucontext);
		this.modelNode = me;
		this.widget = widget;
		this.frameContext = frameContext;
		widget.setRUIControl(this);
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
			if (Objects.equals(this.lastLayoutKey, layoutKey)) {
				layoutValue = this.lastLayoutValue;
			} else {
				layoutValue = null;
			}
		}
		if (layoutValue == null) {
			this.applyStyle(availWidth, availHeight);
			final RenderState renderState = this.modelNode.getRenderState();
			Insets paddingInsets = this.paddingInsets;
			if (paddingInsets == null) {
				paddingInsets = RBlockViewport.ZERO_INSETS;
			}
			Insets borderInsets = this.borderInsets;
			if (borderInsets == null) {
				borderInsets = RBlockViewport.ZERO_INSETS;
			}
			Insets marginInsets = this.marginInsets;
			if (marginInsets == null) {
				marginInsets = RBlockViewport.ZERO_INSETS;
			}
			final int actualAvailWidth = availWidth - paddingInsets.left - paddingInsets.right - borderInsets.left - borderInsets.right - marginInsets.left - marginInsets.right;
			final int actualAvailHeight = availHeight - paddingInsets.top - paddingInsets.bottom - borderInsets.top - borderInsets.bottom - marginInsets.top - marginInsets.bottom;
			final Integer dw = this.getDeclaredWidth(renderState, actualAvailWidth);
			final Integer dh = this.getDeclaredHeight(renderState, actualAvailHeight);
			final int declaredWidth = dw == null ? -1 : dw.intValue();
			final int declaredHeight = dh == null ? -1 : dh.intValue();
			this.declaredWidth = declaredWidth;
			this.declaredHeight = declaredHeight;

			final UIControl widget = this.widget;
			widget.reset(availWidth, availHeight);
			final Insets insets = this.getInsets(false, false);
			int finalWidth = declaredWidth == -1 ? -1 : declaredWidth + insets.left + insets.right;
			int finalHeight = declaredHeight == -1 ? -1 : declaredHeight + insets.top + insets.bottom;
			if ((finalWidth == -1) || (finalHeight == -1)) {
				final Dimension size = widget.getPreferredSize();
				if (finalWidth == -1) {
					finalWidth = size.width + insets.left + insets.right;
				}
				if (finalHeight == -1) {
					finalHeight = size.height + insets.top + insets.bottom;
				}
			}
			layoutValue = new LayoutValue(finalWidth, finalHeight);
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
	}

	/** {@inheritDoc} */
	@Override
	public boolean extractSelectionText(StringBuilder buffer, boolean inSelection, RenderableSpot startPoint,
			RenderableSpot endPoint) {
		// No text here
		return inSelection;
	}

	/** {@inheritDoc} */
	@Override
	public void focus() {
		super.focus();
		final java.awt.Component c = this.widget.getComponent();
		c.requestFocus();
	}

	/** {@inheritDoc} */
	@Override
	public Color getBlockBackgroundColor() {
		return this.widget.getBackgroundColor();
	}

	/**
	 * <p>getForegroundColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getForegroundColor() {
		final RenderState rs = this.modelNode.getRenderState();
		return rs == null ? null : rs.getColor();
	}

	/** {@inheritDoc} */
	@Override
	public RenderableSpot getLowestRenderableSpot(int x, int y) {
		// Nothing draggable - return self
		return new RenderableSpot(this, x, y);
	}

	/** {@inheritDoc} */
	@Override
	public Color getPaintedBackgroundColor() {
		return this.container.getPaintedBackgroundColor();
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<Renderable> getRenderables() {
		// No children for GUI controls
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public int getVAlign() {
		return this.widget.getVAlign();
	}

	/**
	 * <p>hasBackground.</p>
	 *
	 * @return a boolean.
	 */
	public boolean hasBackground() {
		return this.backgroundColor != null || this.backgroundImage != null || this.lastBackgroundImageUri != null;
	}

	/** {@inheritDoc} */
	@Override
	public final void invalidateLayoutLocal() {
		// Invalidate widget (some redundancy)
		super.invalidateLayoutLocal();
		this.widget.invalidate();
		// Invalidate cached values
		this.cachedLayout.clear();
		this.lastLayoutKey = null;
		this.lastLayoutValue = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.rendered.BoundableRenderable#invalidateState(org.loboevolution.html.rendered.RenderableContext)
	 */
	/**
	 * <p>invalidateRenderStyle.</p>
	 */
	public void invalidateRenderStyle() {
		// NOP - No RenderStyle below this node.
	}

	/** {@inheritDoc} */
	@Override
	public final void paint(final Graphics g) {
		final RenderState rs = this.modelNode.getRenderState();
		if ((rs != null) && (rs.getVisibility() != RenderState.VISIBILITY_VISIBLE)) {
			// Just don't paint it.
			return;
		}
		// Prepaint borders, background images, etc.
		this.prePaint(g);
		// We need to paint the GUI component.
		// For various reasons, we need to do that
		// instead of letting AWT do it.
		final Insets insets = this.getBorderInsets();
		g.translate(insets.left, insets.top);
		try {
			this.widget.paint(g);
		} finally {
			g.translate(-insets.left, -insets.top);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.rendered.BoundableRenderable#paintSelection(java.awt.
	 * Graphics, boolean, org.loboevolution.html.rendered.RenderablePoint,
	 * org.loboevolution.html.rendered.RenderablePoint)
	 */
	/** {@inheritDoc} */
	@Override
	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
		inSelection = super.paintSelection(g, inSelection, startPoint, endPoint);
		if (inSelection) {
			final Color over = new Color(0, 0, 255, 50);
			if (over != null) {
				final Color oldColor = g.getColor();
				try {
					g.setColor(over);
					g.fillRect(0, 0, this.width, this.height);
				} finally {
					g.setColor(oldColor);
				}
			}
		}
		return inSelection;
	}

	/**
	 * May be called by controls when they wish to modifiy their preferred size
	 * (e.g. an image after it's loaded). This method must be called in the GUI
	 * thread.
	 */
	public final void preferredSizeInvalidated() {
		final int dw = RUIControl.this.declaredWidth;
		final int dh = RUIControl.this.declaredHeight;
		if (dw == -1 || dh == -1) {
			this.frameContext.delayedRelayout((NodeImpl) this.modelNode);
		} else {
			RUIControl.this.repaint();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void repaint(ModelNode modelNode) {
		final Object widget = this.widget;
		if (widget instanceof UINode) {
			((UINode) widget).repaint(modelNode);
		} else {
			this.repaint();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void updateWidgetBounds(int guiX, int guiY) {
		// Overrides
		super.updateWidgetBounds(guiX, guiY);
        final Insets insets = this.getBorderInsets();
		this.widget.setBounds(guiX + insets.left, guiY + insets.top, this.width - insets.left - insets.right,
				this.height - insets.top - insets.bottom);
	}
}
