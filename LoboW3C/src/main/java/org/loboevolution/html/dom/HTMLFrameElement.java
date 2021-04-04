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

import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.js.WindowProxy;

/**
 * <p>HTMLFrameElement interface.</p>
 *
 *
 *
 */
public interface HTMLFrameElement extends HTMLElement {


    /**
     * Retrieves the document object of the page or frame.
     *
     * @return a {@link org.loboevolution.html.node.Document} object.
     */
    @Deprecated
    Document getContentDocument();

    /**
     * Retrieves the object of the specified.
     *
     * @return a {@link org.loboevolution.html.node.js.WindowProxy} object.
     */
    @Deprecated
    WindowProxy getContentWindow();

    /**
     * Sets or retrieves whether to display a border for the frame.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getFrameBorder();

    
    /**
     * <p>setFrameBorder.</p>
     *
     * @param frameBorder a {@link java.lang.String} object.
     */
    void setFrameBorder(String frameBorder);

    /**
     * Sets or retrieves a URI to a long description of the object.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getLongDesc();

    
    /**
     * <p>setLongDesc.</p>
     *
     * @param longDesc a {@link java.lang.String} object.
     */
    void setLongDesc(String longDesc);

    /**
     * Sets or retrieves the top and bottom margin heights before displaying the text in a frame.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getMarginHeight();

    
    /**
     * <p>setMarginHeight.</p>
     *
     * @param marginHeight a {@link java.lang.String} object.
     */
    void setMarginHeight(String marginHeight);

    /**
     * Sets or retrieves the left and right margin widths before displaying the text in a frame.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getMarginWidth();

    
    /**
     * <p>setMarginWidth.</p>
     *
     * @param marginWidth a {@link java.lang.String} object.
     */
    void setMarginWidth(String marginWidth);

    /**
     * Sets or retrieves the frame name.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getName();

    
    /**
     * <p>setName.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    void setName(String name);

    /**
     * Sets or retrieves whether the user can resize the frame.
     *
     * @return a boolean.
     */
    @Deprecated
    boolean isNoResize();

    
    /**
     * <p>setNoResize.</p>
     *
     * @param noResize a boolean.
     */
    void setNoResize(boolean noResize);

    /**
     * Sets or retrieves whether the frame can be scrolled.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getScrolling();

    
    /**
     * <p>setScrolling.</p>
     *
     * @param scrolling a {@link java.lang.String} object.
     */
    void setScrolling(String scrolling);

    /**
     * Sets or retrieves a URL to be loaded by the object.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getSrc();

    
    /**
     * <p>setSrc.</p>
     *
     * @param src a {@link java.lang.String} object.
     */
    void setSrc(String src);

}
