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
 * Contains the base URI for a document. This object inherits all of the properties and methods as described in the HTMLElement interface.
 *
 *
 *
 */
public interface HTMLBaseElement extends HTMLElement {


    /**
     * Gets or sets the baseline URL on which relative links are based.
     *
     * @return a {@link java.lang.String} object.
     */
    String getHref();

    
    /**
     * <p>setHref.</p>
     *
     * @param href a {@link java.lang.String} object.
     */
    void setHref(String href);

    /**
     * Sets or retrieves the window or frame at which to target content.
     *
     * @return a {@link java.lang.String} object.
     */
    String getTarget();

    
    /**
     * <p>setTarget.</p>
     *
     * @param target a {@link java.lang.String} object.
     */
    void setTarget(String target);

}
