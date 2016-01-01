/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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

import org.lobobrowser.html.domimpl.DOMNodeImpl;

/**
 * Provides direct access to the GUI component where the document is rendered,
 * typically a browser frame of some sort.
 */
public interface FrameContext {

    /**
     * Reset selection.
     *
     * @param rpoint
     *            the rpoint
     */
    void resetSelection(RenderableSpot rpoint);

    /**
     * Expand selection.
     *
     * @param rpoint
     *            the rpoint
     */
    void expandSelection(RenderableSpot rpoint);

    /**
     * Delayed relayout.
     *
     * @param node
     *            the node
     */
    void delayedRelayout(DOMNodeImpl node);
}
