/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

import java.awt.*;
import java.util.List;

/**
 * A RenderableContainer is either usually a parent block or the root GUI
 * component. It is a Renderable or GUI component whose layout may be
 * invalidated.
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
	 * @return a {@link java.util.List} object.
	 */
	List<DelayedPair> getDelayedPairs();

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
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param width a {@link java.lang.Integer} object.
	 * @param height a {@link java.lang.Integer} object.
	 */
	void repaint(int x, final int y, final int width, final int height);

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
     * @return a {@link java.lang.Integer} object.
     */
    int getY();
    
    /**
     * <p>getInnerWidth.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    default int getInnerWidth() {return 0;}

    /**
     * <p>getInnerHeight.</p>
     *
     * @return a {@link java.lang.Integer} object.
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
     * @return a {@link java.lang.Integer} object.
     */
    int getVisualWidth();
    
    /**
     * <p>getVisualHeight.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    int getVisualHeight();
    
    /**
     * <p>getGUIPoint.</p>
     *
     * @param x a {@link java.lang.Integer} object.
     * @param y a {@link java.lang.Integer} object.
     * @return a {@link java.awt.Point} object.
     */
    Point getGUIPoint(int x, final int y);
    
    /**
     * <p>translateDescendentPoint.</p>
     *
     * @param descendent a {@link org.loboevolution.html.renderer.BoundableRenderable} object.
     * @param x a {@link java.lang.Integer} object.
     * @param y a {@link java.lang.Integer} object.
     * @return a {@link java.awt.Point} object.
     */
    Point translateDescendentPoint(BoundableRenderable descendent, final int x, final int y);

	/**
	 * <p>getOriginRelativeToAbs.</p>
	 *
	 * @param bodyLayout a {@link org.loboevolution.html.renderer.RCollection} object.
	 * @return a {@link java.awt.Point} object.
	 */
	Point getOriginRelativeToAbs(RCollection bodyLayout);
}
