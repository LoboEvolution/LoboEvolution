/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.html.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.Point;
import java.util.Collection;

/**
 * A RenderableContainer is either usually a parent block or the root GUI
 * component. It's is a Renderable or GUI component whose layout may be
 * invalidated.
 */
public interface RenderableContainer {
	// Insets getInsets();
	/**
	 * Adds the component.
	 *
	 * @param component
	 *            the component
	 * @return the component
	 */
	Component addComponent(Component component);

	// void remove(Component component);
	/**
	 * Invalidate layout up tree.
	 */
	void invalidateLayoutUpTree();

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
	 * Update all widget bounds.
	 */
	void updateAllWidgetBounds();

	/**
	 * Gets the painted background color.
	 *
	 * @return the painted background color
	 */
	Color getPaintedBackgroundColor();

	/**
	 * Gets the GUI point.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return the GUI point
	 */
	Point getGUIPoint(int x, int y);

	/**
	 * Focus.
	 */
	void focus();

	/**
	 * Adds the delayed pair.
	 *
	 * @param pair
	 *            the pair
	 */
	void addDelayedPair(DelayedPair pair);

	/**
	 * Gets the delayed pairs.
	 *
	 * @return the delayed pairs
	 */
	Collection getDelayedPairs();

	/**
	 * Gets the parent container.
	 *
	 * @return the parent container
	 */
	RenderableContainer getParentContainer();

	/**
	 * Clear delayed pairs.
	 */
	void clearDelayedPairs();

	int getHeight();

	int getWidth();

	int getY();

	Insets getInsets(final boolean hscroll, final boolean vscroll);

	default public int getInnerWidth() {
		final Insets insets = getInsets(false, false);
		return getWidth() - (insets.left + insets.right);
	}

	default public int getInnerHeight() {
		final Insets insets = getInsets(false, false);
		return getHeight() - (insets.top + insets.bottom);
	}

}
