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

import org.loboevolution.html.dom.nodeimpl.ModelNode;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * A renderer node with well-defined bounds. Most renderer nodes implement this
 * interface.
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
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @return a boolean.
	 */
	boolean contains(final int x, final int y);

	/**
	 * <p>getLowestRenderableSpot.</p>
	 *
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @return a {@link org.loboevolution.html.renderer.RenderableSpot} object.
	 */
	RenderableSpot getLowestRenderableSpot(int x, int y);

	/**
	 * <p>getGUIPoint.</p>
	 *
	 * @param clientX a {@link java.lang.Integer} object.
	 * @param clientY a {@link java.lang.Integer} object.
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
	 * @param guiX a {@link java.lang.Integer} object.
	 * @param guiY a {@link java.lang.Integer} object.
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
	 * <p>getOriginRelativeToAbs.</p>
	 *
	 * @param ancestor a {@link org.loboevolution.html.renderer.RCollection} object.
	 * @return a {@link java.awt.Point} object.
	 */
	Point getOriginRelativeToAbs(RCollection ancestor);

	/**
	 * <p>getModelNode.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
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
	 * @return a {@link java.lang.Integer} object.
	 */
	int getOrdinal();

	/**
	 * <p>getHeight.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	int getHeight();

	/**
	 * <p>getWidth.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	int getWidth();

	/**
	 * <p>getVisualHeight.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	int getVisualHeight();

	/**
	 * <p>getVisualWidth.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	int getVisualWidth();

	/**
	 * <p>getX.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	int getX();

	/**
	 * <p>getY.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	int getY();

	/**
	 * <p>getZIndex.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
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
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @return a boolean.
	 */
	boolean onDoubleClick(final MouseEvent event, int x, int y);

	/**
	 * <p>onMouseClick.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @return a boolean.
	 */
	boolean onMouseClick(final MouseEvent event, int x, int y);

	/**
	 * <p>onMouseDisarmed.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @return a boolean.
	 */
	boolean onMouseDisarmed(final MouseEvent event);

	/**
	 * <p>onMouseMoved.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param triggerEvent a boolean.
	 * @param limit a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 */
	void onMouseMoved(final MouseEvent event, int x, int y, boolean triggerEvent, ModelNode limit);

	/**
	 * <p>onMousePressed.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @return a boolean.
	 */
	boolean onMousePressed(final MouseEvent event, int x, int y);

	/**
	 * <p>onMouseReleased.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @return a boolean.
	 */
	boolean onMouseReleased(final MouseEvent event, int x, int y);

	/**
	 * <p>onRightClick.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @return a boolean.
	 */
	boolean onRightClick(final MouseEvent event, int x, int y);

	void onMouseScroll();

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
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param limit a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 */
	void onMouseOut(final MouseEvent event, int x, int y, ModelNode limit);

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
	 * <p>isDelegated.</p>
	 * @return a boolean. */
	boolean isDelegated();

	/**
	 * <p>repaint.</p>
	 *
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param width a {@link java.lang.Integer} object.
	 * @param height a {@link java.lang.Integer} object.
	 */
	void repaint(int x, int y, int width, int height);

	/**
	 * <p>setBounds.</p>
	 *
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param with a {@link java.lang.Integer} object.
	 * @param height a {@link java.lang.Integer} object.
	 */
	void setBounds(int x, int y, int with, int height);

	/**
	 * <p>setHeight.</p>
	 *
	 * @param height a {@link java.lang.Integer} object.
	 */
	void setHeight(int height);

	/**
	 * <p>setOrdinal.</p>
	 *
	 * @param ordinal a {@link java.lang.Integer} object.
	 */
	void setOrdinal(int ordinal);

	/**
	 * <p>setOrigin.</p>
	 *
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
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
	 * @param width a {@link java.lang.Integer} object.
	 */
	void setWidth(final int width);

	/**
	 * <p>setX.</p>
	 *
	 * @param x a {@link java.lang.Integer} object.
	 */
	void setX(int x);

	/**
	 * <p>setY.</p>
	 *
	 * @param y a {@link java.lang.Integer} object.
	 */
	void setY(int y);

	/**
	 * <p>setParent.</p>
	 *
	 * @param pDelegator a {@link org.loboevolution.html.renderer.BoundableRenderable} object.
	 */
	void setDelegator(final BoundableRenderable pDelegator);

	RRectangle getRectangle();

}
