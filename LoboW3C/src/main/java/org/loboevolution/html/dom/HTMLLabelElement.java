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
 * Gives access to properties specific to &lt;label&gt; elements. It inherits methods and properties from the base HTMLElement interface.
 *
 *
 *
 */
public interface HTMLLabelElement extends HTMLElement {
  
    
    
    /**
     * <p>getControl.</p>
     *
     * @return a {@link org.loboevolution.html.dom.HTMLElement} object.
     */
    HTMLElement getControl();

    /**
     * Retrieves a reference to the form that the object is embedded in.
     *
     * @return a {@link org.loboevolution.html.dom.HTMLFormElement} object.
     */
    HTMLFormElement getForm();

    /**
     * Sets or retrieves the object to which the given label object is assigned.
     *
     * @return a {@link java.lang.String} object.
     */
    String getHtmlFor();

    
    /**
     * <p>setHtmlFor.</p>
     *
     * @param htmlFor a {@link java.lang.String} object.
     */
    void setHtmlFor(String htmlFor);

}
