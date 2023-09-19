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


import org.loboevolution.html.node.ValidityState;

/**
 * Provides special properties and methods (beyond the regular HTMLElement interface it also has available to it by inheritance)
 * for manipulating the layout and presentation of &lt;fieldset&gt; elements.
 */
public interface HTMLFieldSetElement extends HTMLElement {

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
     * <p>getElements.</p>
     *
     * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
     */
    HTMLCollection getElements();

    /**
     * Retrieves a reference to the form that the object is embedded in.
     *
     * @return a {@link org.loboevolution.html.dom.HTMLFormElement} object.
     */
    HTMLFormElement getForm();

    
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
     * Returns the error message that would be displayed if the user submits the form, or an empty string if no error message. It also triggers the standard error message, such as "this is a required field". The result is that the user sees validation messages without actually submitting.
     *
     * @return a {@link java.lang.String} object.
     */
    String getValidationMessage();

    /**
     * Returns a  ValidityState object that represents the validity states of an element.
     *
     * @return a {@link org.loboevolution.html.node.ValidityState} object.
     */
    ValidityState getValidity();

    /**
     * Returns whether an element will successfully validate based on forms validation rules and constraints.
     *
     * @return a boolean.
     */
    boolean isWillValidate();

    /**
     * Returns whether a form will validate when it is submitted, without having to submit it.
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
     * Sets a custom error message that is displayed when a form is submitted.
     *
     * @param error Sets a custom error message that is displayed when a form is submitted.
     */
    void setCustomValidity(String error);

}
