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
 * Provides special properties (beyond the regular HTMLElement interface it also has available to it by inheritance) for manipulating &lt;basefont&gt; elements.
 *
 *
 *
 */
public interface HTMLBaseFontElement extends HTMLElement {

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
     * Sets or retrieves the font size of the object.
     *
     * @return a double.
     */
    @Deprecated
    double getSize();

    
    /**
     * <p>setSize.</p>
     *
     * @param size a double.
     */
    void setSize(double size);

}
