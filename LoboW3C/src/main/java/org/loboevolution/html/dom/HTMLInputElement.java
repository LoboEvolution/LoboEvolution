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
 * Provides special properties and methods for manipulating the options, layout, and presentation of &lt;input&gt; elements.
 *
 *
 *
 */
public interface HTMLInputElement extends HTMLElement {

    /**
     * Sets or retrieves a comma-separated list of content types.
     *
     * @return a {@link java.lang.String} object.
     */
    String getAccept();
    
    /**
     * <p>setAccept.</p>
     *
     * @param accept a {@link java.lang.String} object.
     */
    void setAccept(String accept);

    /**
     * Sets or retrieves how the object is aligned with adjacent text.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getAlign();

    /**
     * <p>setAlign.</p>
     *
     * @param align a {@link java.lang.String} object.
     */
    @Deprecated
    void setAlign(String align);

    /**
     * Sets or retrieves a text alternative to the graphic.
     *
     * @return a {@link java.lang.String} object.
     */
    String getAlt();

    
    /**
     * <p>setAlt.</p>
     *
     * @param alt a {@link java.lang.String} object.
     */
    void setAlt(String alt);

    /**
     * Specifies whether autocomplete is applied to an editable text field.
     *
     * @return a boolean.
     */
    boolean getAutocomplete();

    
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
     * Sets or retrieves the state of the check box or radio button.
     *
     * @return a boolean.
     */
    boolean isChecked();

    
    /**
     * <p>setChecked.</p>
     *
     * @param checked a boolean.
     */
    void setChecked(boolean checked);

    /**
     * Sets or retrieves the state of the check box or radio button.
     *
     * @return a boolean.
     */
    boolean isDefaultChecked();

    
    /**
     * <p>setDefaultChecked.</p>
     *
     * @param defaultChecked a boolean.
     */
    void setDefaultChecked(boolean defaultChecked);

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
     * Overrides the action attribute (where the data on a form is sent) on the parent form element.
     *
     * @return a {@link java.lang.String} object.
     */
    String getFormAction();

    
    /**
     * <p>setFormAction.</p>
     *
     * @param formAction a {@link java.lang.String} object.
     */
    void setFormAction(String formAction);

    /**
     * Used to override the encoding (formEnctype attribute) specified on the form element.
     *
     * @return a {@link java.lang.String} object.
     */
    String getFormEnctype();

    
    /**
     * <p>setFormEnctype.</p>
     *
     * @param formEnctype a {@link java.lang.String} object.
     */
    void setFormEnctype(String formEnctype);

    /**
     * Overrides the submit method attribute previously specified on a form element.
     *
     * @return a {@link java.lang.String} object.
     */
    String getFormMethod();

    
    /**
     * <p>setFormMethod.</p>
     *
     * @param formMethod a {@link java.lang.String} object.
     */
    void setFormMethod(String formMethod);

    /**
     * Overrides any validation or required attributes on a form or form elements to allow it to be submitted without validation. This can be used to create a "save draft"-type submit option.
     *
     * @return a boolean.
     */
    boolean isFormNoValidate();

    
    /**
     * <p>setFormNoValidate.</p>
     *
     * @param formNoValidate a boolean.
     */
    void setFormNoValidate(boolean formNoValidate);

    /**
     * Overrides the target attribute on a form element.
     *
     * @return a {@link java.lang.String} object.
     */
    String getFormTarget();

    
    /**
     * <p>setFormTarget.</p>
     *
     * @param formTarget a {@link java.lang.String} object.
     */
    void setFormTarget(String formTarget);

    /**
     * Sets or retrieves the height of the object.
     *
     * @return a double.
     */
    double getHeight();

    
    /**
     * <p>setHeight.</p>
     *
     * @param height a double.
     */
    void setHeight(double height);

    
    /**
     * <p>isIndeterminate.</p>
     *
     * @return a boolean.
     */
    boolean isIndeterminate();

    
    /**
     * <p>setIndeterminate.</p>
     *
     * @param indeterminate a boolean.
     */
    void setIndeterminate(boolean indeterminate);

    
    
    /**
     * <p>getLabels.</p>
     *
     * @return a {@link org.loboevolution.html.node.NodeList} object.
     */
    NodeList getLabels();

    /**
     * Specifies the ID of a pre-defined datalist of options for an input element.
     *
     * @return a {@link org.loboevolution.html.dom.HTMLElement} object.
     */
    HTMLElement getList();

    /**
     * Defines the maximum acceptable value for an input element with type="number".When used with the min and step attributes, lets you control the range and increment (such as only even numbers) that the user can enter into an input field.
     *
     * @return a {@link java.lang.String} object.
     */
    String getMax();

    
    /**
     * <p>setMax.</p>
     *
     * @param max a {@link java.lang.String} object.
     */
    void setMax(String max);

    /**
     * Sets or retrieves the maximum number of characters that the user can enter in a text control.
     *
     * @return a double.
     */
    double getMaxLength();

    
    /**
     * <p>setMaxLength.</p>
     *
     * @param maxLength a double.
     */
    void setMaxLength(double maxLength);

    /**
     * Defines the minimum acceptable value for an input element with type="number". When used with the max and step attributes, lets you control the range and increment (such as even numbers only) that the user can enter into an input field.
     *
     * @return a {@link java.lang.String} object.
     */
    String getMin();

    
    /**
     * <p>setMin.</p>
     *
     * @param min a {@link java.lang.String} object.
     */
    void setMin(String min);

    
    /**
     * <p>getMinLength.</p>
     *
     * @return a double.
     */
    double getMinLength();

    
    /**
     * <p>setMinLength.</p>
     *
     * @param minLength a double.
     */
    void setMinLength(double minLength);

    /**
     * Sets or retrieves the Boolean value indicating whether multiple items can be selected from a list.
     *
     * @return a boolean.
     */
    boolean isMultiple();

    
    /**
     * <p>setMultiple.</p>
     *
     * @param multiple a boolean.
     */
    void setMultiple(boolean multiple);

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
     * Gets or sets a string containing a regular expression that the user's input must match.
     *
     * @return a {@link java.lang.String} object.
     */
    String getPattern();

    
    /**
     * <p>setPattern.</p>
     *
     * @param pattern a {@link java.lang.String} object.
     */
    void setPattern(String pattern);

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
     * <p>isReadOnly.</p>
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
     * <p>getSize.</p>
     *
     * @return a int.
     */
    int getSize();

    
    /**
     * <p>setSize.</p>
     *
     * @param size a int.
     */
    void setSize(int size);

    /**
     * The address or URL of the a media resource that is to be considered.
     *
     * @return a {@link java.lang.String} object.
     */
    String getSrc();

    
    /**
     * <p>setSrc.</p>
     *
     * @param src a {@link java.lang.String} object.
     */
    void setSrc(String src);

    /**
     * Defines an increment or jump between values that you want to allow the user to enter. When used with the max and min attributes, lets you control the range and increment (for example, allow only even numbers) that the user can enter into an input field.
     *
     * @return a {@link java.lang.String} object.
     */
    String getStep();

    
    /**
     * <p>setStep.</p>
     *
     * @param step a {@link java.lang.String} object.
     */
    void setStep(String step);

    /**
     * Returns the content type of the object.
     *
     * @return a {@link java.lang.String} object.
     */
    String getType();

    
    /**
     * <p>setType.</p>
     *
     * @param type a {@link java.lang.String} object.
     */
    void setType(String type);

    /**
     * Sets or retrieves the URL, often with a bookmark extension (#name), to use as a client-side image map.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getUseMap();

    
    /**
     * <p>setUseMap.</p>
     *
     * @param useMap a {@link java.lang.String} object.
     */
    void setUseMap(String useMap);

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
     * Returns the value of the data at the cursor's current position.
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
     * Returns the input field value as a number.
     *
     * @return a double.
     */
    double getValueAsNumber();

    
    /**
     * <p>setValueAsNumber.</p>
     *
     * @param valueAsNumber a double.
     */
    void setValueAsNumber(double valueAsNumber);

    /**
     * Sets or retrieves the width of the object.
     *
     * @return a double.
     */
    double getWidth();

    
    /**
     * <p>setWidth.</p>
     *
     * @param width a double.
     */
    void setWidth(double width);

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
     * Makes the selection equal to the current object.
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
     * <p>setRangeText.</p>
     *
     * @param select a {@link java.lang.String} object.
     * @param start a int.
     * @param end a int.
     * @param preserve a {@link java.lang.String} object.
     */
    void setRangeText(String select, int start, int end, String preserve);

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

    /**
     * Decrements a range input control's value by the value given by the Step attribute. If the optional parameter is used, it will decrement the input control's step value multiplied by the parameter's value.
     *
     * @param n Value to decrement the value by.
     */
    void stepDown(double n);

    /**
     * <p>stepDown.</p>
     */
    void stepDown();

    /**
     * Increments a range input control's value by the value given by the Step attribute. If the optional parameter is used, will increment the input control's value by that value.
     *
     * @param n Value to increment the value by.
     */
    void stepUp(double n);

    /**
     * <p>stepUp.</p>
     */
    void stepUp();

}
