/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.html.renderer;

import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.html.dom.nodeimpl.ModelNode;
import org.loboevolution.html.renderer.info.RBlockInfo;
import org.loboevolution.html.renderer.table.RTable;
import org.loboevolution.html.renderstate.RenderState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Iterator;

/**
 * <p>RInlineBlock class.</p>
 */
public class RInlineBlock extends BaseElementRenderable {

	private final BaseElementRenderable child;

	/**
	 * <p>Constructor for RInlineBlock.</p>
	 *
	 * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 * @param info a {@link org.loboevolution.html.renderer.info.RBlockInfo} object.
	 */
	public RInlineBlock(final RenderableContainer container, RBlockInfo info) {
		super(container, info.getModelNode(), info.getPcontext());
		final int display = modelNode.getRenderState().getDisplay();
		final BaseElementRenderable child = (display == RenderState.DISPLAY_INLINE_TABLE) ? new RTable(info) : new RBlock(info);
		child.setOriginalParent(this);
		child.setParent(this);
		this.child = child;
	}

	/**
	 * <p>assignDimension.</p>
	 */
	public void assignDimension() {
		this.setWidth(child.getWidth());
		this.setHeight(child.getHeight());
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
