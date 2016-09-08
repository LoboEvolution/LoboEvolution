/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.dombl.ModelNode;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.util.CollectionUtilities;

/**
 * The Class RInlineBlock.
 */
public class RInlineBlock extends BaseElementRenderable {

	/** The child. */
	private final RBlock child;

	/**
	 * Instantiates a new r inline block.
	 *
	 * @param container
	 *            the container
	 * @param modelNode
	 *            the model node
	 * @param uacontext
	 *            the uacontext
	 * @param rendererContext
	 *            the renderer context
	 * @param frameContext
	 *            the frame context
	 */
	public RInlineBlock(final RenderableContainer container, final HTMLElementImpl modelNode,
			final UserAgentContext uacontext, final HtmlRendererContext rendererContext,
			final FrameContext frameContext) {
		super(container, modelNode, uacontext);
		final RBlock child = new RBlock(modelNode, 0, userAgentContext, rendererContext, frameContext, this);
		child.setOriginalParent(this);
		child.setParent(this);
		this.child = child;
	}

	/**
	 * Assign dimension.
	 */
	public void assignDimension() {
		this.width = child.getWidth();
		this.height = child.getHeight();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.RCollection#getRenderables()
	 */
	@Override
	public Iterator<? extends Renderable> getRenderables() {
		return CollectionUtilities.singletonIterator(this.child);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#getLowestRenderableSpot
	 * (int, int)
	 */
	@Override
	public RenderableSpot getLowestRenderableSpot(final int x, final int y) {
		return this.child.getLowestRenderableSpot(x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#onDoubleClick(java.awt.
	 * event.MouseEvent, int, int)
	 */
	@Override
	public boolean onDoubleClick(final MouseEvent event, final int x, final int y) {
		return this.child.onDoubleClick(event, x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#onMouseClick(java.awt.
	 * event .MouseEvent, int, int)
	 */
	@Override
	public boolean onMouseClick(final MouseEvent event, final int x, final int y) {
		return this.child.onMouseClick(event, x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#onMouseDisarmed(java.
	 * awt .event.MouseEvent)
	 */
	@Override
	public boolean onMouseDisarmed(final MouseEvent event) {
		return this.child.onMouseDisarmed(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#onMousePressed(java.awt
	 * .event.MouseEvent, int, int)
	 */
	@Override
	public boolean onMousePressed(final MouseEvent event, final int x, final int y) {
		return this.child.onMousePressed(event, x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#onMouseReleased(java.
	 * awt .event.MouseEvent, int, int)
	 */
	@Override
	public boolean onMouseReleased(final MouseEvent event, final int x, final int y) {
		return this.child.onMouseReleased(event, x, y);
	}

	@Override
	public boolean onKeyPressed(KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onKeyUp(KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onKeyDown(KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.BaseElementRenderable#paint(java.awt.
	 * Graphics)
	 */
	@Override
	public void paint(final Graphics g) {
		this.child.paint(g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.RElement#getVAlign()
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
	 * org.lobobrowser.html.dombl.UINode#repaint(org.lobobrowser.html.dombl.
	 * ModelNode )
	 */
	@Override
	public void repaint(final ModelNode modelNode) {
		this.child.repaint(modelNode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.RenderableContainer#
	 * getPaintedBackgroundColor()
	 */
	@Override
	public Color getPaintedBackgroundColor() {
		return this.backgroundColor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.BaseElementRenderable#doLayout(int,
	 * int, boolean)
	 */
	@Override
	protected void doLayout(final int availWidth, final int availHeight, final boolean sizeOnly) {
		this.child.layout(availWidth, availHeight, false, false, null, sizeOnly);
		assignDimension();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BaseElementRenderable#addComponent(java.awt
	 * .Component)
	 */
	@Override
	public Component addComponent(final Component component) {
		this.container.addComponent(component);
		return super.addComponent(component);
	}

}
