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
 * Provides special properties (beyond the regular methods and properties available through the HTMLElement interface they also have available to them by inheritance) for manipulating modification elements, that is &lt;del&gt; and &lt;ins&gt;.
 *
 *
 *
 */
public interface HTMLModElement extends HTMLElement {
 

    /**
     * Sets or retrieves reference information about the object.
     *
     * @return a {@link java.lang.String} object.
     */
    String getCite();

    
    /**
     * <p>setCite.</p>
     *
     * @param cite a {@link java.lang.String} object.
     */
    void setCite(String cite);

    /**
     * Sets or retrieves the date and time of a modification to the object.
     *
     * @return a {@link java.lang.String} object.
     */
    String getDateTime();

    
    /**
     * <p>setDateTime.</p>
     *
     * @param dateTime a {@link java.lang.String} object.
     */
    void setDateTime(String dateTime);

}
