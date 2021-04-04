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
 * Contains descriptive metadata about a document. It inherits all of the properties and methods described in the HTMLElement interface.
 *
 *
 *
 */
public interface HTMLMetaElement extends HTMLElement {
  
    /**
     * Gets or sets meta-information to associate with httpEquiv or name.
     *
     * @return a {@link java.lang.String} object.
     */
    String getContent();

    
    /**
     * <p>setContent.</p>
     *
     * @param content a {@link java.lang.String} object.
     */
    void setContent(String content);

    /**
     * Gets or sets information used to bind the value of a content attribute of a meta element to an HTTP response header.
     *
     * @return a {@link java.lang.String} object.
     */
    String getHttpEquiv();

    
    /**
     * <p>setHttpEquiv.</p>
     *
     * @param httpEquiv a {@link java.lang.String} object.
     */
    void setHttpEquiv(String httpEquiv);

    /**
     * Sets or retrieves the value specified in the content attribute of the meta object.
     *
     * @return a {@link java.lang.String} object.
     */
    String getName();

    
    /**
     * <p>setName.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    void setName(String name);

    /**
     * Sets or retrieves a scheme to be used in interpreting the value of a property specified for the object.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getScheme();

    
    /**
     * <p>setScheme.</p>
     *
     * @param scheme a {@link java.lang.String} object.
     */
    void setScheme(String scheme);

}
