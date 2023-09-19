/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
