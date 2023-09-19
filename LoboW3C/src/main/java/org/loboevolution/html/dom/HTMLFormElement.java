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

/**
 * A &lt;form&gt; element in the DOM; it allows access to and in some cases modification of aspects of the form,
 * as well as access to its component elements.
 */
public interface HTMLFormElement extends HTMLElement {

    /**
     * Sets or retrieves a list of character encodings for input data that must be accepted by the server
     * processing the form.
     *
     * @return a {@link java.lang.String} object.
     */
    String getAcceptCharset();

    
    /**
     * <p>setAcceptCharset.</p>
     *
     * @param acceptCharset a {@link java.lang.String} object.
     */
    void setAcceptCharset(String acceptCharset);

    /**
     * Sets or retrieves the URL to which the form content is sent for processing.
     *
     * @return a {@link java.lang.String} object.
     */
    String getAction();

    
    /**
     * <p>setAction.</p>
     *
     * @param action a {@link java.lang.String} object.
     */
    void setAction(String action);

    /**
     * Specifies whether autocomplete is applied to an editable text field.
     *
     * @return a {@link java.lang.String} object.
     */
    String getAutocomplete();

    
    /**
     * <p>setAutocomplete.</p>
     *
     * @param autocomplete a {@link java.lang.String} object.
     */
    void setAutocomplete(String autocomplete);

    /**
     * Retrieves a collection, in source order, of all controls in a given form.
     *
     * @return a {@link org.loboevolution.html.dom.HTMLFormControlsCollection} object.
     */
    HTMLFormControlsCollection getElements();

    /**
     * Sets or retrieves the MIME encoding for the form.
     *
     * @return a {@link java.lang.String} object.
     */
    String getEncoding();

    
    /**
     * <p>setEncoding.</p>
     *
     * @param encoding a {@link java.lang.String} object.
     */
    void setEncoding(String encoding);

    /**
     * Sets or retrieves the encoding type for the form.
     *
     * @return a {@link java.lang.String} object.
     */
    String getEnctype();

    
    /**
     * <p>setEnctype.</p>
     *
     * @param enctype a {@link java.lang.String} object.
     */
    void setEnctype(String enctype);

    /**
     * Sets or retrieves the number of objects in a collection.
     *
     * @return a int.
     */
    int getLength();

    /**
     * Sets or retrieves how to send the form data to the server.
     *
     * @return a {@link java.lang.String} object.
     */
    String getMethod();

    
    /**
     * <p>setMethod.</p>
     *
     * @param method a {@link java.lang.String} object.
     */
    void setMethod(String method);

    /**
     * Sets or retrieves the name of the object.
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
     * Designates a form that is not validated when submitted.
     *
     * @return a boolean.
     */
    boolean isNoValidate();

    
    /**
     * <p>setNoValidate.</p>
     *
     * @param noValidate a boolean.
     */
    void setNoValidate(boolean noValidate);

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
     * Fires when the user resets a form.
     */
    void reset();

    /**
     * Fires when a FORM is about to be submitted.
     */
    void submit();

}
