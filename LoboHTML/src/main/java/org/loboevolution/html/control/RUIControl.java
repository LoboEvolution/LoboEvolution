/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
package org.loboevolution.html.control;

import org.loboevolution.html.node.UINode;
import org.loboevolution.html.node.ModelNode;
import org.loboevolution.html.renderer.*;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.http.UserAgentContext;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Objects;

/**
 * <p>RUIControl class.</p>
 */
public class RUIControl extends BaseElementRenderable {

	private static final int MAX_CACHE_SIZE = 10;
	private final Map<LayoutKey, LayoutValue> cachedLayout = new HashMap<>(5);

	protected int declaredHeight = -1;

	protected int declaredWidth = -1;

	private LayoutKey lastLayoutKey = null;

	private LayoutValue lastLayoutValue = null;

	protected final ModelNode modelNode;

	public final UIControl widget;

    /**
     * <p>Constructor for RUIControl.</p>
     *
     * @param me a {@link ModelNode} object.
     * @param widget a {@link org.loboevolution.html.control.UIControl} object.
     * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
     * @param ucontext a {@link org.loboevolution.http.UserAgentContext} object.
     */
    public RUIControl(final ModelNode me, final UIControl widget, final RenderableContainer container,
					  final UserAgentContext ucontext) {
		super(container, me, ucontext);
		this.modelNode = me;
		this.widget = widget;
		widget.setRUIControl(this);
	}

	/** {@inheritDoc} */
	@Override
	public void doLayout(final int availWidth, final int availHeight, final boolean sizeOnly) {
		final Map<LayoutKey, LayoutValue> cachedLayout = this.cachedLayout;
		final RenderState rs = (RenderState)this.modelNode.getRenderState();
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

			final Insets paddingInsets = this.paddingInsets == null ? RBlockViewport.ZERO_INSETS : this.paddingInsets;
			final Insets borderInsets = this.borderInsets == null ? RBlockViewport.ZERO_INSETS : this.borderInsets;
			final Insets marginInsets = this.marginInsets == null ? RBlockViewport.ZERO_INSETS : this.marginInsets;

			final int paddingWidth = paddingInsets.left - paddingInsets.right;
			final int borderWidth = borderInsets.left - borderInsets.right;
			final int marginWidth = marginInsets.left - marginInsets.right;

			final int paddingHeight = paddingInsets.top - paddingInsets.bottom;
			final int borderHeight = borderInsets.top - borderInsets.bottom;
			final int marginHeight = marginInsets.top - marginInsets.bottom;

			final int actualAvailWidth = availWidth - paddingWidth - borderWidth - marginWidth;
			final int actualAvailHeight = availHeight - paddingHeight - borderHeight - marginHeight;
			final Integer dw = this.getDeclaredWidth(actualAvailWidth);
			final Integer dh = this.getDeclaredHeight(actualAvailHeight);
			final int declaredWidth = dw == null ? -1 : dw;
			final int declaredHeight = dh == null ? -1 : dh;
			this.declaredWidth = declaredWidth;
			this.declaredHeight = declaredHeight;

			final UIControl widget = this.widget;
			widget.reset(availWidth, availHeight);
			final Insets insets = this.getInsets(false, false);
			int finalWidth = declaredWidth == -1 ? -1 : declaredWidth + insets.left + insets.right;
			int finalHeight = declaredHeight == -1 ? -1 : declaredHeight + insets.top + insets.bottom;

			final Dimension size = widget.getPreferredSize();
			if (finalWidth == -1) {
				finalWidth = size.width + insets.left + insets.right;
			}
			if (finalHeight == -1) {
				finalHeight = size.height + insets.top + insets.bottom;
			}

			layoutValue = new LayoutValue(finalWidth, finalHeight);

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
	}

	/** {@inheritDoc} */
	@Override
	public boolean extractSelectionText(final StringBuilder buffer, final boolean inSelection, final RenderableSpot startPoint,
										final RenderableSpot endPoint) {
		// No text here
		return inSelection;
	}

	/** {@inheritDoc} */
	@Override
	public void focus() {
		super.focus();
		final Component c = this.widget.getComponent();
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
		final RenderState rs = (RenderState)this.modelNode.getRenderState();
		return rs == null ? null : rs.getColor();
	}

	/** {@inheritDoc} */
	@Override
	public RenderableSpot getLowestRenderableSpot(final int x, final int y) {
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
	public List<Renderable> getRenderables() {
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

	/** {@inheritDoc} */
	@Override
	public final void paint(final Graphics g) {
		final RenderState rs = (RenderState)this.modelNode.getRenderState();
		if ((rs != null) && (rs.getVisibility() == RenderState.VISIBILITY_VISIBLE)) {
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
	}

	/** {@inheritDoc} */
	@Override
	public boolean paintSelection(final Graphics g, final boolean isSelection, final RenderableSpot startPoint, final RenderableSpot endPoint) {
		final boolean inSelection = super.paintSelection(g, isSelection, startPoint, endPoint);
		if (inSelection) {
			final Color over = new Color(0, 0, 255, 50);
			final Color oldColor = g.getColor();
			try {
				g.setColor(over);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
			} finally {
				g.setColor(oldColor);
			}
		}
		return inSelection;
	}

	/** {@inheritDoc} */
	@Override
	public void repaint(final ModelNode modelNode) {
		final Object widget = this.widget;
		if (widget instanceof UINode) {
			((UINode) widget).repaint(modelNode);
		} else {
			this.repaint();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void updateWidgetBounds(final int guiX, final int guiY) {
		super.updateWidgetBounds(guiX, guiY);
        final Insets insets = this.getBorderInsets();
		final int width = this.getWidth() - insets.left - insets.right;
		final int height = this.getHeight() - insets.top - insets.bottom;
		this.widget.setBounds(guiX + insets.left, guiY + insets.top, width, height);
	}
}
