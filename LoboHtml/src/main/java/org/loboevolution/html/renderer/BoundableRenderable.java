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
 *
 * @author utente
 * @version $Id: $Id
 */
public interface BoundableRenderable extends Renderable {

	/**
	 * <p>getSize.</p>
	 *
	 * @return a {@link java.awt.Dimension} object.
	 */
	Dimension getSize();

	/**
	 * <p>getBounds.</p>
	 *
	 * @return a {@link java.awt.Rectangle} object.
	 */
	Rectangle getBounds();

	/**
	 * <p>getVisualBounds.</p>
	 *
	 * @return a {@link java.awt.Rectangle} object.
	 */
	Rectangle getVisualBounds();

	/**
	 * <p>contains.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 * @return a boolean.
	 */
	boolean contains(final int x, final int y);

	/**
	 * <p>getLowestRenderableSpot.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 * @return a {@link org.loboevolution.html.renderer.RenderableSpot} object.
	 */
	RenderableSpot getLowestRenderableSpot(int x, int y);

	/**
	 * <p>getGUIPoint.</p>
	 *
	 * @param clientX a int.
	 * @param clientY a int.
	 * @return a {@link java.awt.Point} object.
	 */
	Point getGUIPoint(int clientX, int clientY);

	/**
	 * <p>getOrigin.</p>
	 *
	 * @return a {@link java.awt.Point} object.
	 */
	Point getOrigin();

	/**
	 * <p>getRenderablePoint.</p>
	 *
	 * @param guiX a int.
	 * @param guiY a int.
	 * @return a {@link java.awt.Point} object.
	 */
	Point getRenderablePoint(int guiX, int guiY);

	/**
	 * <p>getOriginRelativeTo.</p>
	 *
	 * @param ancestor a {@link org.loboevolution.html.renderer.RCollection} object.
	 * @return a {@link java.awt.Point} object.
	 */
	Point getOriginRelativeTo(RCollection ancestor);

	/**
	 * <p>getOriginRelativeToNoScroll.</p>
	 *
	 * @param ancestor a {@link org.loboevolution.html.renderer.RCollection} object.
	 * @return a {@link java.awt.Point} object.
	 */
	Point getOriginRelativeToNoScroll(RCollection ancestor);

	/**
	 * <p>getModelNode.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.domimpl.ModelNode} object.
	 */
	ModelNode getModelNode();

	/**
	 * <p>getOriginalOrCurrentParent.</p>
	 *
	 * @return a {@link org.loboevolution.html.renderer.RCollection} object.
	 */
	RCollection getOriginalOrCurrentParent();

	/**
	 * <p>getOriginalParent.</p>
	 *
	 * @return a {@link org.loboevolution.html.renderer.RCollection} object.
	 */
	RCollection getOriginalParent();

	/**
	 * <p>getParent.</p>
	 *
	 * @return a {@link org.loboevolution.html.renderer.RCollection} object.
	 */
	RCollection getParent();

	/**
	 * <p>getOrdinal.</p>
	 *
	 * @return a int.
	 */
	int getOrdinal();

	/**
	 * <p>getHeight.</p>
	 *
	 * @return a int.
	 */
	int getHeight();

	/**
	 * <p>getWidth.</p>
	 *
	 * @return a int.
	 */
	int getWidth();

	/**
	 * <p>getVisualHeight.</p>
	 *
	 * @return a int.
	 */
	int getVisualHeight();

	/**
	 * <p>getVisualWidth.</p>
	 *
	 * @return a int.
	 */
	int getVisualWidth();

	/**
	 * <p>getX.</p>
	 *
	 * @return a int.
	 */
	int getX();

	/**
	 * <p>getY.</p>
	 *
	 * @return a int.
	 */
	int getY();

	/**
	 * <p>getZIndex.</p>
	 *
	 * @return a int.
	 */
	int getZIndex();

	/**
	 * <p>isContainedByNode.</p>
	 *
	 * @return a boolean.
	 */
	boolean isContainedByNode();

	/**
	 * <p>onDoubleClick.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @return a boolean.
	 */
	boolean onDoubleClick(MouseEvent event, int x, int y);

	/**
	 * <p>onMouseClick.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @return a boolean.
	 */
	boolean onMouseClick(MouseEvent event, int x, int y);

	/**
	 * <p>onMouseDisarmed.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @return a boolean.
	 */
	boolean onMouseDisarmed(MouseEvent event);

	/**
	 * <p>onMouseMoved.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @param triggerEvent a boolean.
	 * @param limit a {@link org.loboevolution.html.dom.domimpl.ModelNode} object.
	 */
	void onMouseMoved(MouseEvent event, int x, int y, boolean triggerEvent, ModelNode limit);

	/**
	 * <p>onMousePressed.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @return a boolean.
	 */
	boolean onMousePressed(MouseEvent event, int x, int y);

	/**
	 * <p>onMouseReleased.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @return a boolean.
	 */
	boolean onMouseReleased(MouseEvent event, int x, int y);

	/**
	 * <p>onRightClick.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @return a boolean.
	 */
	boolean onRightClick(MouseEvent event, int x, int y);

	/**
	 * <p>paintSelection.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param inSelection a boolean.
	 * @param startPoint a {@link org.loboevolution.html.renderer.RenderableSpot} object.
	 * @param endPoint a {@link org.loboevolution.html.renderer.RenderableSpot} object.
	 * @return a boolean.
	 */
	boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint);

	/**
	 * <p>extractSelectionText.</p>
	 *
	 * @param buffer a {@link java.lang.StringBuilder} object.
	 * @param inSelection a boolean.
	 * @param startPoint a {@link org.loboevolution.html.renderer.RenderableSpot} object.
	 * @param endPoint a {@link org.loboevolution.html.renderer.RenderableSpot} object.
	 * @return a boolean.
	 */
	boolean extractSelectionText(StringBuilder buffer, boolean inSelection, RenderableSpot startPoint,
			RenderableSpot endPoint);

	/**
	 * <p>invalidateLayoutUpTree.</p>
	 */
	void invalidateLayoutUpTree();

	/**
	 * <p>onMouseOut.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @param limit a {@link org.loboevolution.html.dom.domimpl.ModelNode} object.
	 */
	void onMouseOut(MouseEvent event, int x, int y, ModelNode limit);

	/**
	 * <p>paintTranslated.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 */
	void paintTranslated(Graphics g);

	/**
	 * <p>relayout.</p>
	 */
	void relayout();

	/**
	 * <p>repaint.</p>
	 */
	void repaint();

	/**
	 * <p>repaint.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 * @param width a int.
	 * @param height a int.
	 */
	void repaint(int x, int y, int width, int height);

	/**
	 * <p>setBounds.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 * @param with a int.
	 * @param height a int.
	 */
	void setBounds(int x, int y, int with, int height);

	/**
	 * <p>setHeight.</p>
	 *
	 * @param height a int.
	 */
	void setHeight(int height);

	/**
	 * <p>setOrdinal.</p>
	 *
	 * @param ordinal a int.
	 */
	void setOrdinal(int ordinal);

	/**
	 * <p>setOrigin.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 */
	void setOrigin(int x, int y);

	/**
	 * <p>setOriginalParent.</p>
	 *
	 * @param origParent a {@link org.loboevolution.html.renderer.RCollection} object.
	 */
	void setOriginalParent(RCollection origParent);

	/**
	 * <p>setParent.</p>
	 *
	 * @param parent a {@link org.loboevolution.html.renderer.RCollection} object.
	 */
	void setParent(RCollection parent);

	/**
	 * <p>setWidth.</p>
	 *
	 * @param width a int.
	 */
	void setWidth(int width);

	/**
	 * <p>setX.</p>
	 *
	 * @param x a int.
	 */
	void setX(int x);

	/**
	 * <p>setY.</p>
	 *
	 * @param y a int.
	 */
	void setY(int y);
}
