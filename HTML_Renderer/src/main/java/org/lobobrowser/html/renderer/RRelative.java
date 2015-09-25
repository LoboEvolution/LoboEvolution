/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The XAMJ Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.renderer;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import org.lobobrowser.html.dombl.ModelNode;
import org.lobobrowser.html.info.FloatingInfo;
import org.lobobrowser.util.CollectionUtilities;

/**
 * The Class RRelative.
 */
public class RRelative extends BaseRCollection {

	/** The child. */
	private final RElement child;

	/** The xoffset. */
	private final int xoffset;

	/** The yoffset. */
	private final int yoffset;

	/**
	 * Instantiates a new r relative.
	 *
	 * @param container
	 *            the container
	 * @param modelNode
	 *            the model node
	 * @param child
	 *            the child
	 * @param xoffset
	 *            the xoffset
	 * @param yoffset
	 *            the yoffset
	 */
	public RRelative(RenderableContainer container, ModelNode modelNode, final RElement child, final int xoffset,
			final int yoffset) {
		super(container, modelNode);
		child.setOriginalParent(this);
		child.setParent(this);
		child.setOrigin(xoffset, yoffset);
		this.child = child;
		this.xoffset = xoffset;
		this.yoffset = yoffset;
	}

	/**
	 * Assign dimension.
	 */
	public void assignDimension() {
		RElement child = this.child;
		this.width = child.getWidth();
		this.height = child.getHeight();
	}

	/**
	 * Gets the exportable floating info.
	 *
	 * @return the exportable floating info
	 */
	public FloatingInfo getExportableFloatingInfo() {
		RElement child = this.child;
		if (child instanceof RBlock) {
			// There are no insets, and hence no shift.
			return ((RBlock) child).getExportableFloatingInfo();
		} else {
			return null;
		}
	}

	/**
	 * Gets the element.
	 *
	 * @return the element
	 */
	public RElement getElement() {
		return this.child;
	}

	/**
	 * Gets the x offset.
	 *
	 * @return the x offset
	 */
	public int getXOffset() {
		return xoffset;
	}

	/**
	 * Gets the y offset.
	 *
	 * @return the y offset
	 */
	public int getYOffset() {
		return yoffset;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.RCollection#getRenderables()
	 */
	@Override
	public Iterator getRenderables() {
		return CollectionUtilities.singletonIterator(this.child);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.BaseBoundableRenderable#
	 * invalidateLayoutLocal()
	 */
	@Override
	protected void invalidateLayoutLocal() {
		// nop
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#getLowestRenderableSpot
	 * (int, int)
	 */
	@Override
	public RenderableSpot getLowestRenderableSpot(int x, int y) {
		return this.child.getLowestRenderableSpot(x - this.xoffset, y - this.yoffset);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#isContainedByNode()
	 */
	@Override
	public boolean isContainedByNode() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#onDoubleClick(java.awt.
	 * event.MouseEvent, int, int)
	 */
	@Override
	public boolean onDoubleClick(MouseEvent event, int x, int y) {
		return this.child.onDoubleClick(event, x - this.xoffset, y - this.yoffset);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#onMouseClick(java.awt.
	 * event .MouseEvent, int, int)
	 */
	@Override
	public boolean onMouseClick(MouseEvent event, int x, int y) {
		return this.child.onMouseClick(event, x - this.xoffset, y - this.yoffset);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#onMouseDisarmed(java.
	 * awt .event.MouseEvent)
	 */
	@Override
	public boolean onMouseDisarmed(MouseEvent event) {
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
	public boolean onMousePressed(MouseEvent event, int x, int y) {
		return this.child.onMousePressed(event, x - this.xoffset, y - this.yoffset);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#onMouseReleased(java.
	 * awt .event.MouseEvent, int, int)
	 */
	@Override
	public boolean onMouseReleased(MouseEvent event, int x, int y) {
		return this.child.onMouseReleased(event, x - this.xoffset, y - this.yoffset);
	}

	@Override
	public boolean onKeyPressed(KeyEvent event) {

		// TODO

		ModelNode me = this.modelNode;
		if (me != null) {

			return HtmlController.getInstance().onKeyPress(this.modelNode, event);

		} else {
			return true;
		}

	}

	@Override
	public boolean onKeyUp(KeyEvent event) {

		// TODO

		ModelNode me = this.modelNode;
		if (me != null) {

			return HtmlController.getInstance().onKeyUp(this.modelNode, event);

		} else {
			return true;
		}

	}

	@Override
	public boolean onKeyDown(KeyEvent event) {

		// TODO

		ModelNode me = this.modelNode;
		if (me != null) {

			return HtmlController.getInstance().onKeyDown(this.modelNode, event);

		} else {
			return true;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.Renderable#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		this.child.paintTranslated(g);
	}
}
