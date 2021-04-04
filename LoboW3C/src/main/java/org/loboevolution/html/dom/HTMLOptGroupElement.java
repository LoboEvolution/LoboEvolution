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
 * Provides special properties and methods (beyond the regular HTMLElement object interface they also have available to them by inheritance) for manipulating the layout and presentation of &lt;optgroup&gt; elements.
 *
 *
 *
 */
public interface HTMLOptGroupElement extends HTMLElement {

    /**
     * <p>isDisabled.</p>
     *
     * @return a boolean.
     */
    boolean isDisabled();

    /**
     * <p>setDisabled.</p>
     *
     * @param disabled a boolean.
     */
    void setDisabled(boolean disabled);

      /**
       * Retrieves a reference to the form that the object is embedded in.
       *
       * @return a {@link org.loboevolution.html.dom.HTMLFormElement} object.
       */
      HTMLFormElement getForm();

    /**
     * Sets or retrieves a value that you can use to implement your own label functionality for the object.
     *
     * @return a {@link java.lang.String} object.
     */
    String getLabel();

    
    /**
     * <p>setLabel.</p>
     *
     * @param label a {@link java.lang.String} object.
     */
    void setLabel(String label);

}
