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


import org.loboevolution.html.node.DOMTokenList;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.ValidityState;

/**
 * Provides properties and methods (beyond those inherited from HTMLElement) for manipulating the layout and presentation of &lt;output&gt; elements.
 *
 *
 *
 */
public interface HTMLOutputElement extends HTMLElement {

    
    /**
     * <p>getDefaultValue.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getDefaultValue();

    
    /**
     * <p>setDefaultValue.</p>
     *
     * @param defaultValue a {@link java.lang.String} object.
     */
    void setDefaultValue(String defaultValue);

    
    
    /**
     * <p>getForm.</p>
     *
     * @return a {@link org.loboevolution.html.dom.HTMLFormElement} object.
     */
    HTMLFormElement getForm();

    
    /**
     * <p>getHtmlFor.</p>
     *
     * @return a {@link org.loboevolution.html.node.DOMTokenList} object.
     */
    DOMTokenList getHtmlFor();

    
    /**
     * <p>getLabels.</p>
     *
     * @return a {@link org.loboevolution.html.node.NodeList} object.
     */
    NodeList getLabels();

    
    /**
     * <p>getName.</p>
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
     * <p>getType.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getType();

    
    /**
     * <p>getValidationMessage.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getValidationMessage();

    
    /**
     * <p>getValidity.</p>
     *
     * @return a {@link org.loboevolution.html.node.ValidityState} object.
     */
    ValidityState getValidity();

    
    /**
     * <p>getValue.</p>
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

    
    /**
     * <p>isWillValidate.</p>
     *
     * @return a boolean.
     */
    boolean isWillValidate();

    /**
     * <p>checkValidity.</p>
     *
     * @return a boolean.
     */
    boolean checkValidity();

    /**
     * <p>reportValidity.</p>
     *
     * @return a boolean.
     */
    boolean reportValidity();

    /**
     * <p>setCustomValidity.</p>
     *
     * @param error a {@link java.lang.String} object.
     */
    void setCustomValidity(String error);

}
