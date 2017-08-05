/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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

import org.lobobrowser.html.dombl.UINode;

/**
 * A renderer node for elements such as blocks, lists, tables, inputs, images,
 * etc.
 */
public interface RElement extends RCollection, UINode, BoundableRenderable {
	
	/** The Constant VALIGN_TOP. */
	public static final int VALIGN_TOP = 0;

	/** The Constant VALIGN_MIDDLE. */
	public static final int VALIGN_MIDDLE = 1;

	/** The Constant VALIGN_BOTTOM. */
	public static final int VALIGN_BOTTOM = 2;

	/** The Constant VALIGN_ABSMIDDLE. */
	public static final int VALIGN_ABSMIDDLE = 3;

	/** The Constant VALIGN_ABSBOTTOM. */
	public static final int VALIGN_ABSBOTTOM = 4;

	/** The Constant VALIGN_BASELINE. */
	public static final int VALIGN_BASELINE = 5;
	
	/**
	 * Lays out the subtree below the RElement. The RElement is expected to set
	 * its own dimensions, but not its origin.
	 *
	 * @param availWidth
	 *            The available width from the parent's canvas.
	 * @param availHeight
	 *            The available height from the parent's canvas.
	 * @param sizeOnly
	 *            Whether the layout is for sizing determination only.
	 */
	void layout(int availWidth, int availHeight, boolean sizeOnly);

	/**
	 * Gets the v align.
	 *
	 * @return the v align
	 */
	int getVAlign();

	/**
	 * Gets the margin top.
	 *
	 * @return the margin top
	 */
	int getMarginTop();

	/**
	 * Gets the margin left.
	 *
	 * @return the margin left
	 */
	int getMarginLeft();

	/**
	 * Gets the margin bottom.
	 *
	 * @return the margin bottom
	 */
	int getMarginBottom();

	/**
	 * Gets the margin right.
	 *
	 * @return the margin right
	 */
	int getMarginRight();

	/**
	 * Gets the collapsible margin top.
	 *
	 * @return the collapsible margin top
	 */
	int getCollapsibleMarginTop();

	/**
	 * Gets the collapsible margin bottom.
	 *
	 * @return the collapsible margin bottom
	 */
	int getCollapsibleMarginBottom();
}
