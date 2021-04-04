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




/**
 * Provides special properties (beyond those of the HTMLElement interface it also has available to it by inheritance) for manipulating &lt;hr&gt; elements.
 *
 *
 *
 */
public interface HTMLHRElement extends HTMLElement {


    /**
     * Sets or retrieves how the object is aligned with adjacent text.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getAlign();

    
    /**
     * <p>setAlign.</p>
     *
     * @param align a {@link java.lang.String} object.
     */
    void setAlign(String align);

    /**
     * <p>getColor.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getColor();

    
    /**
     * <p>setColor.</p>
     *
     * @param color a {@link java.lang.String} object.
     */
    void setColor(String color);

    /**
     * Sets or retrieves whether the horizontal rule is drawn with 3-D shading.
     *
     * @return a boolean.
     */
    @Deprecated
    boolean isNoShade();

    
    /**
     * <p>setNoShade.</p>
     *
     * @param noShade a boolean.
     */
    void setNoShade(boolean noShade);

    /**
     * <p>getSize.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getSize();

    
    /**
     * <p>setSize.</p>
     *
     * @param size a {@link java.lang.String} object.
     */
    void setSize(String size);

    /**
     * Sets or retrieves the width of the object.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getWidth();

    
    /**
     * <p>setWidth.</p>
     *
     * @param width a {@link java.lang.String} object.
     */
    void setWidth(String width);

}
