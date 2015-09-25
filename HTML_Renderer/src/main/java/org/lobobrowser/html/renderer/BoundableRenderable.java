/*
 * GNU LESSER GENERAL LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This library is free software; you can
 * redistribute it and/or modify it under the terms of the GNU Lesser General
 * License as published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version. This library is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General License for more details. You
 * should have received a copy of the GNU Lesser General Public License along
 * with this library; if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA Contact info:
 * lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.renderer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.lobobrowser.html.dombl.ModelNode;

/**
 * The Interface BoundableRenderable.
 */
public interface BoundableRenderable extends Renderable {

	@Override
	ModelNode getModelNode();

	/**
	 * Gets the bounds.
	 *
	 * @return the bounds
	 */
	Rectangle getBounds();

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	Dimension getSize();

	/**
	 * Gets the origin.
	 *
	 * @return the origin
	 */
	Point getOrigin();

	/**
	 * Gets the origin relative to.
	 *
	 * @param ancestor
	 *            the ancestor
	 * @return the origin relative to
	 */
	Point getOriginRelativeTo(RCollection ancestor);

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	RCollection getParent();

	/**
	 * Sets the original parent.
	 *
	 * @param origParent
	 *            the new original parent
	 */
	void setOriginalParent(RCollection origParent);

	/**
	 * Gets the original parent.
	 *
	 * @return the original parent
	 */
	RCollection getOriginalParent();

	/**
	 * Gets the original or current parent.
	 *
	 * @return the original or current parent
	 */
	RCollection getOriginalOrCurrentParent();

	/**
	 * Sets the bounds.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param with
	 *            the with
	 * @param height
	 *            the height
	 */
	void setBounds(int x, int y, int with, int height);

	/**
	 * Sets the origin.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	void setOrigin(int x, int y);

	/**
	 * Sets the x.
	 *
	 * @param x
	 *            the new x
	 */
	void setX(int x);

	/**
	 * Sets the y.
	 *
	 * @param y
	 *            the new y
	 */
	void setY(int y);

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	int getX();

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	int getY();

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	int getHeight();

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	int getWidth();

	/**
	 * Sets the height.
	 *
	 * @param height
	 *            the new height
	 */
	void setHeight(int height);

	/**
	 * Sets the width.
	 *
	 * @param width
	 *            the new width
	 */
	void setWidth(int width);

	/**
	 * Gets the lowest renderable spot.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return the lowest renderable spot
	 */
	RenderableSpot getLowestRenderableSpot(int x, int y);

	/**
	 * Gets the renderable point.
	 *
	 * @param guiX
	 *            the gui x
	 * @param guiY
	 *            the gui y
	 * @return the renderable point
	 */
	Point getRenderablePoint(int guiX, int guiY);

	/**
	 * Repaint.
	 */
	void repaint();

	/**
	 * Returns false if the event is consumed. True to propagate further.
	 *
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	boolean onMousePressed(MouseEvent event, int x, int y);

	/**
	 * On mouse released.
	 *
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	boolean onMouseReleased(MouseEvent event, int x, int y);

	/**
	 * On mouse disarmed.
	 *
	 * @param event
	 *            the event
	 * @return true, if successful
	 */
	boolean onMouseDisarmed(MouseEvent event);

	/**
	 * On mouse click.
	 *
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	boolean onMouseClick(MouseEvent event, int x, int y);

	/**
	 * On double click.
	 *
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	boolean onDoubleClick(MouseEvent event, int x, int y);

	/**
	 * On right click.
	 *
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	boolean onRightClick(MouseEvent event, int x, int y);

	/**
	 * On mouse moved.
	 *
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param triggerEvent
	 *            the trigger event
	 * @param limit
	 *            the limit
	 */
	void onMouseMoved(MouseEvent event, int x, int y, boolean triggerEvent, ModelNode limit);

	/**
	 * On mouse out.
	 *
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param limit
	 *            the limit
	 */
	void onMouseOut(MouseEvent event, int x, int y, ModelNode limit);

	/**
	 * On key pressed.
	 * 
	 * @param event
	 *            the event
	 */
	boolean onKeyPressed(KeyEvent event);

	/**
	 * On key up.
	 * 
	 * @param event
	 *            the event
	 */
	boolean onKeyUp(KeyEvent event);

	/**
	 * On key down.
	 * 
	 * @param event
	 *            the event
	 */
	boolean onKeyDown(KeyEvent event);

	/**
	 * Checks if is contained by node.
	 *
	 * @return true, if is contained by node
	 */
	boolean isContainedByNode();

	/**
	 * Asks the Renderable to paint the selection between two points. Nothing
	 * will be done if the points are outside the Renderable.
	 *
	 * @param g
	 *            the g
	 * @param inSelection
	 *            the in selection
	 * @param startPoint
	 *            the start point
	 * @param endPoint
	 *            the end point
	 * @return True iff it's in selection when finished painting.
	 */
	boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint);

	/**
	 * Paints by either creating a new clipped graphics context corresponding to
	 * the bounds of the Renderable, or by translating the origin.
	 *
	 * @param g
	 *            Parent's Graphics context.
	 */
	void paintTranslated(Graphics g);

	/**
	 * Extract selection text.
	 *
	 * @param buffer
	 *            the buffer
	 * @param inSelection
	 *            the in selection
	 * @param startPoint
	 *            the start point
	 * @param endPoint
	 *            the end point
	 * @return true, if successful
	 */
	boolean extractSelectionText(StringBuffer buffer, boolean inSelection, RenderableSpot startPoint,
			RenderableSpot endPoint);

	/**
	 * Repaint.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	void repaint(int x, int y, int width, int height);

	/**
	 * Relayout.
	 */
	void relayout();

	/**
	 * Sets the parent.
	 *
	 * @param parent
	 *            the new parent
	 */
	void setParent(RCollection parent);

	/**
	 * Gets the GUI point.
	 *
	 * @param clientX
	 *            the client x
	 * @param clientY
	 *            the client y
	 * @return the GUI point
	 */
	Point getGUIPoint(int clientX, int clientY);

	/**
	 * Gets the ordinal.
	 *
	 * @return the ordinal
	 */
	int getOrdinal();

	/**
	 * Sets the ordinal.
	 *
	 * @param ordinal
	 *            the new ordinal
	 */
	void setOrdinal(int ordinal);

	/**
	 * Gets the z index.
	 *
	 * @return the z index
	 */
	int getZIndex();

	/**
	 * Invalidate layout up tree.
	 */
	void invalidateLayoutUpTree();
}
