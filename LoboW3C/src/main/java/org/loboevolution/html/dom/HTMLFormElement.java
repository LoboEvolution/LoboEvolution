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
 * A &lt;form&gt; element in the DOM; it allows access to and in some cases modification of aspects of the form, as well as access to its component elements.
 *
 *
 *
 */
public interface HTMLFormElement extends HTMLElement {

    /**
     * Sets or retrieves a list of character encodings for input data that must be accepted by the server processing the form.
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
     * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
     */
    HTMLCollection getElements();

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
