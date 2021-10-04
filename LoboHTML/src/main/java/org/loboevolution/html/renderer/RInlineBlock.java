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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.nodeimpl.ModelNode;
import org.loboevolution.html.renderer.table.RTable;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;

/**
 * <p>RInlineBlock class.</p>
 *
 *
 *
 */
public class RInlineBlock extends BaseElementRenderable {
	private final BaseElementRenderable child;

	/**
	 * <p>Constructor for RInlineBlock.</p>
	 *
	 * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param uacontext a {@link org.loboevolution.http.UserAgentContext} object.
	 * @param rendererContext a {@link org.loboevolution.http.HtmlRendererContext} object.
	 * @param frameContext a {@link org.loboevolution.html.renderer.FrameContext} object.
	 */
	public RInlineBlock(final RenderableContainer container, final HTMLElementImpl modelNode,
			final UserAgentContext uacontext, final HtmlRendererContext rendererContext,
			final FrameContext frameContext) {
		super(container, modelNode, uacontext);
		final int display = modelNode.getRenderState().getDisplay();
		final BaseElementRenderable child = (display == RenderState.DISPLAY_INLINE_TABLE)
				? new RTable(modelNode, userAgentContext, rendererContext, frameContext, this)
				: new RBlock(modelNode, 0, userAgentContext, rendererContext, frameContext, this);
		child.setOriginalParent(this);
		child.setParent(this);
		this.child = child;
	}

	/**
	 * <p>assignDimension.</p>
	 */
	public void assignDimension() {
		this.width = child.getWidth();
		this.height = child.getHeight();
	}

	/**
	 * <p>getRenderables.</p>
	 *
	 * @return a {@link java.util.Iterator} object.
	 */
	public Iterator<Renderable> getRenderables() {
		return ArrayUtilities.singletonIterator(this.child);
	}

	/** {@inheritDoc} */
	public RenderableSpot getLowestRenderableSpot(final int x, final int y) {
		return this.child.getLowestRenderableSpot(x, y);
	}

	/** {@inheritDoc} */
	public boolean onDoubleClick(final MouseEvent event, final int x, final int y) {
		return this.child.onDoubleClick(event, x, y);
	}

	/** {@inheritDoc} */
	public boolean onMouseClick(final MouseEvent event, final int x, final int y) {
		return this.child.onMouseClick(event, x, y);
	}

	/** {@inheritDoc} */
	public boolean onMouseDisarmed(final MouseEvent event) {
		return this.child.onMouseDisarmed(event);
	}

	/** {@inheritDoc} */
	public boolean onMousePressed(final MouseEvent event, final int x, final int y) {
		return this.child.onMousePressed(event, x, y);
	}

	/** {@inheritDoc} */
	public boolean onMouseReleased(final MouseEvent event, final int x, final int y) {
		return this.child.onMouseReleased(event, x, y);
	}

	/** {@inheritDoc} */
	public void paint(final Graphics g) {
		this.child.paint(g);
	}

	/** {@inheritDoc} */
	@Override
	public void repaint(final ModelNode modelNode) {
		this.child.repaint(modelNode);
	}

	/** {@inheritDoc} */
	@Override
	public Color getPaintedBackgroundColor() {
		return this.backgroundColor;
	}

	/** {@inheritDoc} */
	@Override
	protected void doLayout(final int availWidth, final int availHeight, final boolean sizeOnly) {
		this.child.layout(availWidth, availHeight, sizeOnly);
		assignDimension();
	}

    /** {@inheritDoc} */
    @Override
    public void layout(int availWidth, int availHeight, boolean sizeOnly) {
        this.doLayout(availWidth, availHeight, sizeOnly);
    }

	/** {@inheritDoc} */
	@Override
	public Component addComponent(final Component component) {
		this.container.addComponent(component);
		return super.addComponent(component);
	}
}
