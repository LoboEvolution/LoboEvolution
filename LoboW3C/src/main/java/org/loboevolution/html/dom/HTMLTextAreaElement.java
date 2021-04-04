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


import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.ValidityState;
import org.loboevolution.jsenum.Direction;

/**
 * Provides special properties and methods for manipulating the layout and presentation of &lt;textarea&gt; elements.
 *
 *
 *
 */
public interface HTMLTextAreaElement extends HTMLElement {

    /**
     * <p>getAutocomplete.</p>
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
     * @return a int.
     */
    int getCols();

    
    /**
     * <p>setCols.</p>
     *
     * @param cols a int.
     */
    void setCols(int cols);

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
     * @return a int.
     */
    int getMaxLength();

    
    /**
     * <p>setMaxLength.</p>
     *
     * @param maxLength a int.
     */
    void setMaxLength(int maxLength);

    
    /**
     * <p>getMinLength.</p>
     *
     * @return a int.
     */
    int getMinLength();

    
    /**
     * <p>setMinLength.</p>
     *
     * @param minLength a int.
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
     * @return a int.
     */
    int getRows();

    
    /**
     * <p>setRows.</p>
     *
     * @param rows a int.
     */
    void setRows(int rows);

    
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
     * @return a int.
     */
    int getSelectionEnd();

    
    /**
     * <p>setSelectionEnd.</p>
     *
     * @param selectionEnd a int.
     */
    void setSelectionEnd(int selectionEnd);

    /**
     * Gets or sets the starting position or offset of a text selection.
     *
     * @return a int.
     */
    int getSelectionStart();

    
    /**
     * <p>setSelectionStart.</p>
     *
     * @param selectionStart a int.
     */
    void setSelectionStart(int selectionStart);

    
    /**
     * <p>getTextLength.</p>
     *
     * @return a int.
     */
    int getTextLength();

    /**
     * Retrieves the type of control.
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
     * @param start a int.
     * @param end a int.
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
     * @param start a int.
     * @param end a int.
     */
    void setSelectionRange(int start, int end);

}
