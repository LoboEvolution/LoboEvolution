/*
 * GNU LESSER GENERAL LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.html.renderer;

import org.lobobrowser.html.dombl.UINode;

/**
 * A renderer node for elements such as blocks, lists, tables, inputs, images,
 * etc.
 */
public interface RElement extends RCollection, UINode, BoundableRenderable {
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
     * Vertical alignment for elements rendered in a line. Returns one of the
     * constants defined in this class.
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

    /** The Constant VALIGN_TOP. */
    static final int VALIGN_TOP = 0;

    /** The Constant VALIGN_MIDDLE. */
    static final int VALIGN_MIDDLE = 1;

    /** The Constant VALIGN_BOTTOM. */
    static final int VALIGN_BOTTOM = 2;

    /** The Constant VALIGN_ABSMIDDLE. */
    static final int VALIGN_ABSMIDDLE = 3;

    /** The Constant VALIGN_ABSBOTTOM. */
    static final int VALIGN_ABSBOTTOM = 4;

    /** The Constant VALIGN_BASELINE. */
    static final int VALIGN_BASELINE = 5;
}
