/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
package org.lobobrowser.html.renderer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import org.lobobrowser.html.dombl.ModelNode;


/**
 * A renderer node with well-defined bounds. Most renderer nodes implement this
 * interface.
 */
public interface BoundableRenderable extends Renderable {
	
	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.Renderable#getModelNode()
	 */
	public ModelNode getModelNode();

	/**
	 * Gets the bounds.
	 *
	 * @return the bounds
	 */
	public Rectangle getBounds();

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public Dimension getSize();

	/**
	 * Gets the origin.
	 *
	 * @return the origin
	 */
	public Point getOrigin();

	/**
	 * Gets the origin relative to.
	 *
	 * @param ancestor the ancestor
	 * @return the origin relative to
	 */
	public Point getOriginRelativeTo(RCollection ancestor);

	/**
	 * Gets the parent where the renderable is rendered.
	 *
	 * @return the parent
	 */
	public RCollection getParent();

	/**
	 * Sets the original parent.
	 *
	 * @param origParent the new original parent
	 */
	public void setOriginalParent(RCollection origParent);

	/**
	 * Gets the parent set with {@link #setOriginalParent(RCollection)}. It
	 * represents the parent where the renderable would have been originally
	 * rendered according to the DOM. This will be non-null only if
	 * {@link #getParent()} is not the parent where this renderable would have
	 * been originally rendered.
	 *
	 * @return the original parent
	 */
	public RCollection getOriginalParent();

	/**
	 * Returns {@link #getOriginalParent()} if not null. Otherwise it returns
	 * {@link #getParent()}.
	 *
	 * @return the original or current parent
	 */
	public RCollection getOriginalOrCurrentParent();

	/**
	 * Sets the bounds.
	 *
	 * @param x the x
	 * @param y the y
	 * @param with the with
	 * @param height the height
	 */
	public void setBounds(int x, int y, int with, int height);

	/**
	 * Sets the origin.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void setOrigin(int x, int y);

	/**
	 * Sets the x.
	 *
	 * @param x the new x
	 */
	public void setX(int x);

	/**
	 * Sets the y.
	 *
	 * @param y the new y
	 */
	public void setY(int y);

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX();

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY();

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight();

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth();

	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(int height);

	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(int width);

	/**
	 * Gets the lowest renderable spot.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the lowest renderable spot
	 */
	public RenderableSpot getLowestRenderableSpot(int x, int y);

	/**
	 * Gets the renderable point.
	 *
	 * @param guiX the gui x
	 * @param guiY the gui y
	 * @return the renderable point
	 */
	public Point getRenderablePoint(int guiX, int guiY);

	/**
	 * Repaint.
	 */
	public void repaint();

	/**
	 * Returns false if the event is consumed. True to propagate further.
	 *
	 * @param event the event
	 * @param x the x
	 * @param y the y
	 * @return true, if successful
	 */
	public boolean onMousePressed(MouseEvent event, int x, int y);

	/**
	 * On mouse released.
	 *
	 * @param event the event
	 * @param x the x
	 * @param y the y
	 * @return true, if successful
	 */
	public boolean onMouseReleased(MouseEvent event, int x, int y);

	/**
	 * On mouse disarmed.
	 *
	 * @param event the event
	 * @return true, if successful
	 */
	public boolean onMouseDisarmed(MouseEvent event);

	/**
	 * On mouse click.
	 *
	 * @param event the event
	 * @param x the x
	 * @param y the y
	 * @return true, if successful
	 */
	public boolean onMouseClick(MouseEvent event, int x, int y);

	/**
	 * On double click.
	 *
	 * @param event the event
	 * @param x the x
	 * @param y the y
	 * @return true, if successful
	 */
	public boolean onDoubleClick(MouseEvent event, int x, int y);

	/**
	 * On right click.
	 *
	 * @param event the event
	 * @param x the x
	 * @param y the y
	 * @return true, if successful
	 */
	public boolean onRightClick(MouseEvent event, int x, int y);

	/**
	 * On mouse moved.
	 *
	 * @param event the event
	 * @param x the x
	 * @param y the y
	 * @param triggerEvent the trigger event
	 * @param limit the limit
	 */
	public void onMouseMoved(MouseEvent event, int x, int y,
			boolean triggerEvent, ModelNode limit);

	/**
	 * On mouse out.
	 *
	 * @param event the event
	 * @param x the x
	 * @param y the y
	 * @param limit the limit
	 */
	public void onMouseOut(MouseEvent event, int x, int y, ModelNode limit);

	/**
	 * Returns true if the renderable is fully contained by its modelNode, but
	 * said modelNode does not fully contain an ancestor renderable.
	 *
	 * @return true, if is contained by node
	 */
	public boolean isContainedByNode();

	/**
	 * Asks the Renderable to paint the selection between two points. Nothing
	 * will be done if the points are outside the Renderable.
	 *
	 * @param g the g
	 * @param inSelection the in selection
	 * @param startPoint the start point
	 * @param endPoint the end point
	 * @return True iff it's in selection when finished painting.
	 */
	public boolean paintSelection(Graphics g, boolean inSelection,
			RenderableSpot startPoint, RenderableSpot endPoint);

	/**
	 * Paints by either creating a new clipped graphics context corresponding to
	 * the bounds of the Renderable, or by translating the origin.
	 * 
	 * @param g
	 *            Parent's Graphics context.
	 */
	public void paintTranslated(Graphics g);

	/**
	 * Extract selection text.
	 *
	 * @param buffer the buffer
	 * @param inSelection the in selection
	 * @param startPoint the start point
	 * @param endPoint the end point
	 * @return true, if successful
	 */
	public boolean extractSelectionText(StringBuffer buffer,
			boolean inSelection, RenderableSpot startPoint,
			RenderableSpot endPoint);

	/**
	 * Repaint.
	 *
	 * @param x the x
	 * @param y the y
	 * @param width the width
	 * @param height the height
	 */
	public void repaint(int x, int y, int width, int height);

	/**
	 * Relayout.
	 */
	public void relayout();

	/**
	 * Sets the parent.
	 *
	 * @param parent the new parent
	 */
	public void setParent(RCollection parent);

	/**
	 * Gets the GUI point.
	 *
	 * @param clientX the client x
	 * @param clientY the client y
	 * @return the GUI point
	 */
	public java.awt.Point getGUIPoint(int clientX, int clientY);

	/**
	 * Gets the ordinal.
	 *
	 * @return the ordinal
	 */
	public int getOrdinal();

	/**
	 * Sets the ordinal.
	 *
	 * @param ordinal the new ordinal
	 */
	public void setOrdinal(int ordinal);

	/**
	 * Gets the z index.
	 *
	 * @return the z index
	 */
	public int getZIndex();

	/**
	 * Invalidate layout up tree.
	 */
	public void invalidateLayoutUpTree();
}
