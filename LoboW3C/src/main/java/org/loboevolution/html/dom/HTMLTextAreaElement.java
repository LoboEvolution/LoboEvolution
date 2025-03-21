/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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


import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.ValidityState;
import org.loboevolution.type.Direction;

/**
 * Provides special properties and methods for manipulating the layout and presentation of textarea elements.
 */
public interface HTMLTextAreaElement extends HTMLElement {

    /**
     * <p>getAutocomplete.</p>
     *
     * @return a {@link java.lang.Boolean} object.
     */
    boolean isAutocomplete();

    /**
     * <p>setAutocomplete.</p>
     *
     * @param autocomplete a {@link java.lang.String} object.
     */
    void setAutocomplete(String autocomplete);

    /**
     * Provides a way to direct a user to a specific field when a document loads. This can provide both direction and convenience for a user, reducing the need to click or tab to a field when a page opens. This attribute is true when present on an element, and false when missing.
     *
     * @return a boolean.
     */
    boolean isAutofocus();


    /**
     * <p>setAutofocus.</p>
     *
     * @param autofocus a boolean.
     */
    void setAutofocus(boolean autofocus);

    /**
     * Sets or retrieves the width of the object.
     *
     * @return a {@link java.lang.Integer} object.
     */
    int getCols();


    /**
     * <p>setCols.</p>
     *
     * @param cols a {@link java.lang.Integer} object.
     */
    void setCols(Integer cols);

    /**
     * Sets or retrieves the initial contents of the object.
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
     * <p>getDirName.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getDirName();


    /**
     * <p>setDirName.</p>
     *
     * @param dirName a {@link java.lang.String} object.
     */
    void setDirName(String dirName);


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
     * <p>getLabels.</p>
     *
     * @return a {@link org.loboevolution.html.node.NodeList} object.
     */
    NodeList getLabels();

    /**
     * Sets or retrieves the maximum number of characters that the user can enter in a text control.
     *
     * @return a {@link java.lang.Integer} object.
     */
    int getMaxLength();


    /**
     * <p>setMaxLength.</p>
     *
     * @param maxLength a {@link java.lang.Integer} object.
     */
    void setMaxLength(int maxLength);


    /**
     * <p>getMinLength.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    int getMinLength();


    /**
     * <p>setMinLength.</p>
     *
     * @param minLength a {@link java.lang.Integer} object.
     */
    void setMinLength(int minLength);

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
     * Gets or sets a text string that is displayed in an input field as a hint or prompt to users as the format or type of information they need to enter.The text appears in an input field until the user puts focus on the field.
     *
     * @return a {@link java.lang.String} object.
     */
    String getPlaceholder();


    /**
     * <p>setPlaceholder.</p>
     *
     * @param placeholder a {@link java.lang.String} object.
     */
    void setPlaceholder(String placeholder);

    /**
     * Sets or retrieves the value indicated whether the content of the object is read-only.
     *
     * @return a boolean.
     */
    boolean isReadOnly();


    /**
     * <p>setReadOnly.</p>
     *
     * @param readOnly a boolean.
     */
    void setReadOnly(boolean readOnly);

    /**
     * When present, marks an element that can't be submitted without a value.
     *
     * @return a boolean.
     */
    boolean isRequired();


    /**
     * <p>setRequired.</p>
     *
     * @param required a boolean.
     */
    void setRequired(boolean required);

    /**
     * Sets or retrieves the number of horizontal rows contained in the object.
     *
     * @return a {@link java.lang.Integer} object.
     */
    int getRows();


    /**
     * <p>setRows.</p>
     *
     * @param rows a {@link java.lang.Integer} object.
     */
    void setRows(Integer rows);


    /**
     * <p>getSelectionDirection.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getSelectionDirection();


    /**
     * <p>setSelectionDirection.</p>
     *
     * @param selectionDirection a {@link java.lang.String} object.
     */
    void setSelectionDirection(String selectionDirection);

    /**
     * Gets or sets the end position or offset of a text selection.
     *
     * @return a {@link java.lang.Integer} object.
     */
    int getSelectionEnd();


    /**
     * <p>setSelectionEnd.</p>
     *
     * @param selectionEnd a {@link java.lang.Integer} object.
     */
    void setSelectionEnd(int selectionEnd);

    /**
     * Gets or sets the starting position or offset of a text selection.
     *
     * @return a {@link java.lang.Integer} object.
     */
    int getSelectionStart();

    /**
     * <p>setSelectionStart.</p>
     *
     * @param selectionStart a {@link java.lang.Integer} object.
     */
    void setSelectionStart(int selectionStart);

    /**
     * <p>getTextLength.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    int getTextLength();


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
     * Retrieves or sets the text in the entry field of the textArea element.
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
     * Returns whether an element will successfully validate based on forms validation rules and constraints.
     *
     * @return a boolean.
     */
    boolean isWillValidate();

    /**
     * Sets or retrieves how to handle wordwrapping in the object.
     *
     * @return a {@link java.lang.String} object.
     */
    String getWrap();


    /**
     * <p>setWrap.</p>
     *
     * @param wrap a {@link java.lang.String} object.
     */
    void setWrap(String wrap);

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
     * Highlights the input area of a form element.
     */
    void select();

    /**
     * Sets a custom error message that is displayed when a form is submitted.
     *
     * @param error Sets a custom error message that is displayed when a form is submitted.
     */
    void setCustomValidity(String error);

    /**
     * <p>setRangeText.</p>
     *
     * @param replacement a {@link java.lang.String} object.
     */
    void setRangeText(String replacement);

    /**
     * <p>setRangeText.</p>
     *
     * @param replacement a {@link java.lang.String} object.
     * @param start       a {@link java.lang.Integer} object.
     * @param end         a {@link java.lang.Integer} object.
     */
    void setRangeText(String replacement, int start, int end);

    /**
     * Sets the start and end positions of a selection in a text field.
     *
     * @param start     The offset into the text field for the start of the selection.
     * @param end       The offset into the text field for the end of the selection.
     * @param direction The direction in which the selection is performed.
     */
    void setSelectionRange(int start, int end, Direction direction);

    /**
     * <p>setSelectionRange.</p>
     *
     * @param start a {@link java.lang.Integer} object.
     * @param end   a {@link java.lang.Integer} object.
     */
    void setSelectionRange(int start, int end);

}
