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
/*
 * Created on Jan 14, 2006
 */
package org.lobobrowser.html.dombl;

import java.awt.Rectangle;

/**
 * A UI node abstraction that is used to send notifications back to the UI and
 * to obtain information the DOM needs from the UI (such as image dimensions).
 */
public interface UINode {

    /**
     * Called.
     *
     * @param modelNode
     *            the model node
     */
    void repaint(ModelNode modelNode);

    /**
     * Gets the bounds.
     *
     * @return the bounds
     */
    Rectangle getBounds();

    /**
     * Gets the bounds relative to block.
     *
     * @return the bounds relative to block
     */
    Rectangle getBoundsRelativeToBlock();

    /**
     * Focus.
     */
    void focus();

    /**
     * Blur.
     */
    void blur();
}
