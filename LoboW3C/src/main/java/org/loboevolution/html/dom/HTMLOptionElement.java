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
 * &lt;option&gt; elements and inherits all classes and methods of the HTMLElement interface.
 *
 *
 *
 */
public interface HTMLOptionElement extends HTMLElement {

    /**
     * Sets or retrieves the status of an option.
     *
     * @return a boolean.
     */
    boolean isDefaultSelected();

    
    /**
     * <p>setDefaultSelected.</p>
     *
     * @param defaultSelected a boolean.
     */
    void setDefaultSelected(boolean defaultSelected);

    
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
     * Sets or retrieves the ordinal position of an option in a list box.
     *
     * @return a int.
     */
    int getIndex();

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

    /**
     * Sets or retrieves whether the option in the list box is the default item.
     *
     * @return a boolean.
     */
    boolean isSelected();

    
    /**
     * <p>setSelected.</p>
     *
     * @param selected a boolean.
     */
    void setSelected(boolean selected);

    /**
     * Sets or retrieves the text string specified by the option tag.
     *
     * @return a {@link java.lang.String} object.
     */
    String getText();

    
    /**
     * <p>setText.</p>
     *
     * @param text a {@link java.lang.String} object.
     */
    void setText(String text);

    /**
     * Sets or retrieves the value which is returned to the server when the form control is submitted.
     *
     * @return a {@link java.lang.String} object.
     */
    String getValue();

    
    /**
     * <p>setValue.</p>
     *
     * @param value a {@link java.lang.String} object.
     */
    void setValue(String value);

}
