/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom;

import org.loboevolution.html.node.js.WindowEventHandlers;

/**
 * Provides special properties (beyond those of the regular HTMLElement interface they also inherit) for manipulating &lt;frameset&gt; elements.
 *
 *
 *
 */
public interface HTMLFrameSetElement extends HTMLElement, WindowEventHandlers {


    /**
     * Sets or retrieves the frame widths of the object.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getCols();

    
    /**
     * <p>setCols.</p>
     *
     * @param cols a {@link java.lang.String} object.
     */
    void setCols(String cols);

    /**
     * Sets or retrieves the frame heights of the object.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getRows();

    
    /**
     * <p>setRows.</p>
     *
     * @param rows a {@link java.lang.String} object.
     */
    void setRows(String rows);

}
