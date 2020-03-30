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
package org.loboevolution.html.renderer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import org.loboevolution.html.dom.domimpl.ModelNode;

/**
 * A renderer node with well-defined bounds. Most renderer nodes implement this
 * interface.
 */
public interface BoundableRenderable extends Renderable {

	Dimension getSize();

	Rectangle getBounds();

	Rectangle getVisualBounds();

	boolean contains(final int x, final int y);

	RenderableSpot getLowestRenderableSpot(int x, int y);

	Point getGUIPoint(int clientX, int clientY);

	Point getOrigin();

	Point getRenderablePoint(int guiX, int guiY);

	Point getOriginRelativeTo(RCollection ancestor);

	Point getOriginRelativeToNoScroll(RCollection ancestor);

	ModelNode getModelNode();

	RCollection getOriginalOrCurrentParent();

	RCollection getOriginalParent();

	RCollection getParent();

	int getOrdinal();

	int getHeight();

	int getWidth();

	int getVisualHeight();

	int getVisualWidth();

	int getX();

	int getY();

	int getZIndex();

	boolean isContainedByNode();

	boolean onDoubleClick(MouseEvent event, int x, int y);

	boolean onMouseClick(MouseEvent event, int x, int y);

	boolean onMouseDisarmed(MouseEvent event);

	void onMouseMoved(MouseEvent event, int x, int y, boolean triggerEvent, ModelNode limit);

	boolean onMousePressed(MouseEvent event, int x, int y);

	boolean onMouseReleased(MouseEvent event, int x, int y);

	boolean onRightClick(MouseEvent event, int x, int y);

	boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint);

	boolean extractSelectionText(StringBuilder buffer, boolean inSelection, RenderableSpot startPoint,
			RenderableSpot endPoint);

	void invalidateLayoutUpTree();

	void onMouseOut(MouseEvent event, int x, int y, ModelNode limit);

	void paintTranslated(Graphics g);

	void relayout();

	void repaint();

	void repaint(int x, int y, int width, int height);

	void setBounds(int x, int y, int with, int height);

	void setHeight(int height);

	void setOrdinal(int ordinal);

	void setOrigin(int x, int y);

	void setOriginalParent(RCollection origParent);

	void setParent(RCollection parent);

	void setWidth(int width);

	void setX(int x);

	void setY(int y);
}
