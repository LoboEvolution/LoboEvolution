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

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;

/**
 * A RenderableContainer is either usually a parent block or the root GUI
 * component. It's is a Renderable or GUI component whose layout may be
 * invalidated.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface RenderableContainer {
	/**
	 * <p>addComponent.</p>
	 *
	 * @param component a {@link java.awt.Component} object.
	 * @return a {@link java.awt.Component} object.
	 */
	Component addComponent(Component component);

	/**
	 * <p>addDelayedPair.</p>
	 *
	 * @param pair a {@link org.loboevolution.html.renderer.DelayedPair} object.
	 */
	void addDelayedPair(DelayedPair pair);

	/**
	 * <p>clearDelayedPairs.</p>
	 */
	void clearDelayedPairs();

	/**
	 * <p>focus.</p>
	 */
	void focus();

	/**
	 * <p>getDelayedPairs.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	Collection getDelayedPairs();

	/**
	 * <p>getPaintedBackgroundColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getPaintedBackgroundColor();

	/**
	 * <p>getParentContainer.</p>
	 *
	 * @return a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 */
	RenderableContainer getParentContainer();

	/**
	 * <p>invalidateLayoutUpTree.</p>
	 */
	void invalidateLayoutUpTree();

	/**
	 * <p>relayout.</p>
	 */
	void relayout();

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
	 * <p>updateAllWidgetBounds.</p>
	 */
	void updateAllWidgetBounds();
	
    /**
     * <p>getInsetsMarginBorder.</p>
     *
     * @param hscroll a boolean.
     * @param vscroll a boolean.
     * @return a {@link java.awt.Insets} object.
     */
    Insets getInsetsMarginBorder(final boolean hscroll, final boolean vscroll);
    
    /**
     * <p>getInsets.</p>
     *
     * @param hscroll a boolean.
     * @param vscroll a boolean.
     * @return a {@link java.awt.Insets} object.
     */
    Insets getInsets(final boolean hscroll, final boolean vscroll);
    
    /**
     * <p>getY.</p>
     *
     * @return a int.
     */
    int getY();
    
    /**
     * <p>getInnerWidth.</p>
     *
     * @return a int.
     */
    default int getInnerWidth() {return 0;}

    /**
     * <p>getInnerHeight.</p>
     *
     * @return a int.
     */
    default int getInnerHeight() {return 0;}

    /**
     * <p>getVisualBounds.</p>
     *
     * @return a {@link java.awt.Rectangle} object.
     */
    Rectangle getVisualBounds();
    
    /**
     * <p>getVisualWidth.</p>
     *
     * @return a int.
     */
    int getVisualWidth();
    
    /**
     * <p>getVisualHeight.</p>
     *
     * @return a int.
     */
    int getVisualHeight();
    
    /**
     * <p>getGUIPoint.</p>
     *
     * @param x a int.
     * @param y a int.
     * @return a {@link java.awt.Point} object.
     */
    Point getGUIPoint(int x, int y);
    
    /**
     * <p>translateDescendentPoint.</p>
     *
     * @param descendent a {@link org.loboevolution.html.renderer.BoundableRenderable} object.
     * @param x a int.
     * @param y a int.
     * @return a {@link java.awt.Point} object.
     */
    Point translateDescendentPoint(BoundableRenderable descendent, int x, int y);
}
