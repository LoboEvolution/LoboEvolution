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
 * Implements the document object model (DOM) representation of the font element. The HTML Font Element &lt;font&gt; defines the font size, font face and color of text.
 *
 *
 *
 */
public interface HTMLFontElement extends HTMLElement {


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
     * Sets or retrieves the current typeface family.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getFace();

    
    /**
     * <p>setFace.</p>
     *
     * @param face a {@link java.lang.String} object.
     */
    void setFace(String face);

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

}
