package org.loboevolution.html.dom;

import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.ValidityState;
import org.loboevolution.jsenum.Direction;


/**
 * Provides special properties and methods for manipulating the options, layout, and presentation of &lt;input&gt; elements.
 */
public interface HTMLInputElement extends HTMLElement {

    /**
     * Sets or retrieves a comma-separated list of content types.
     */
    
    String getAccept();
    
    void setAccept(String accept);

    /**
     * Sets or retrieves how the object is aligned with adjacent text.
     */
    @Deprecated
    String getAlign();

    @Deprecated
    void setAlign(String align);

    /**
     * Sets or retrieves a text alternative to the graphic.
     */
    
    String getAlt();

    
    void setAlt(String alt);

    /**
     * Specifies whether autocomplete is applied to an editable text field.
     */
    
    boolean getAutocomplete();

    
    void setAutocomplete(String autocomplete);

    /**
     * Provides a way to direct a user to a specific field when a document loads. This can provide both direction and convenience for a user, reducing the need to click or tab to a field when a page opens. This attribute is true when present on an element, and false when missing.
     */
    
    boolean isAutofocus();

    
    void setAutofocus(boolean autofocus);

    /**
     * Sets or retrieves the state of the check box or radio button.
     */
    
    boolean isChecked();

    
    void setChecked(boolean checked);

    /**
     * Sets or retrieves the state of the check box or radio button.
     */
    
    boolean isDefaultChecked();

    
    void setDefaultChecked(boolean defaultChecked);

    /**
     * Sets or retrieves the initial contents of the object.
     */
    
    String getDefaultValue();

    
    void setDefaultValue(String defaultValue);

    
    String getDirName();

    
    void setDirName(String dirName);

    
    boolean isDisabled();

    
    void setDisabled(boolean disabled);


    /**
     * Retrieves a reference to the form that the object is embedded in.
     */
        HTMLFormElement getForm();

    /**
     * Overrides the action attribute (where the data on a form is sent) on the parent form element.
     */
    
    String getFormAction();

    
    void setFormAction(String formAction);

    /**
     * Used to override the encoding (formEnctype attribute) specified on the form element.
     */
    
    String getFormEnctype();

    
    void setFormEnctype(String formEnctype);

    /**
     * Overrides the submit method attribute previously specified on a form element.
     */
    
    String getFormMethod();

    
    void setFormMethod(String formMethod);

    /**
     * Overrides any validation or required attributes on a form or form elements to allow it to be submitted without validation. This can be used to create a "save draft"-type submit option.
     */
    
    boolean isFormNoValidate();

    
    void setFormNoValidate(boolean formNoValidate);

    /**
     * Overrides the target attribute on a form element.
     */
    
    String getFormTarget();

    
    void setFormTarget(String formTarget);

    /**
     * Sets or retrieves the height of the object.
     */
    
    double getHeight();

    
    void setHeight(double height);

    
    boolean isIndeterminate();

    
    void setIndeterminate(boolean indeterminate);

    
    
    NodeList getLabels();

    /**
     * Specifies the ID of a pre-defined datalist of options for an input element.
     */
    
    
    HTMLElement getList();

    /**
     * Defines the maximum acceptable value for an input element with type="number".When used with the min and step attributes, lets you control the range and increment (such as only even numbers) that the user can enter into an input field.
     */
    
    String getMax();

    
    void setMax(String max);

    /**
     * Sets or retrieves the maximum number of characters that the user can enter in a text control.
     */
    
    double getMaxLength();

    
    void setMaxLength(double maxLength);

    /**
     * Defines the minimum acceptable value for an input element with type="number". When used with the max and step attributes, lets you control the range and increment (such as even numbers only) that the user can enter into an input field.
     */
    
    String getMin();

    
    void setMin(String min);

    
    double getMinLength();

    
    void setMinLength(double minLength);

    /**
     * Sets or retrieves the Boolean value indicating whether multiple items can be selected from a list.
     */
    
    boolean isMultiple();

    
    void setMultiple(boolean multiple);

    /**
     * Sets or retrieves the name of the object.
     */
    
    String getName();

    
    void setName(String name);

    /**
     * Gets or sets a string containing a regular expression that the user's input must match.
     */
    
    String getPattern();

    
    void setPattern(String pattern);

    /**
     * Gets or sets a text string that is displayed in an input field as a hint or prompt to users as the format or type of information they need to enter.The text appears in an input field until the user puts focus on the field.
     */
    
    String getPlaceholder();

    
    void setPlaceholder(String placeholder);

    
    boolean isReadOnly();

    
    void setReadOnly(boolean readOnly);

    /**
     * When present, marks an element that can't be submitted without a value.
     */
    
    boolean isRequired();

    
    void setRequired(boolean required);

    
    
    String getSelectionDirection();

    
    void setSelectionDirection(String selectionDirection);

    /**
     * Gets or sets the end position or offset of a text selection.
     */
    
    
    int getSelectionEnd();

    
    void setSelectionEnd(int selectionEnd);

    /**
     * Gets or sets the starting position or offset of a text selection.
     */
    
    
    int getSelectionStart();

    
    void setSelectionStart(int selectionStart);

    
    int getSize();

    
    void setSize(int size);

    /**
     * The address or URL of the a media resource that is to be considered.
     */
    
    String getSrc();

    
    void setSrc(String src);

    /**
     * Defines an increment or jump between values that you want to allow the user to enter. When used with the max and min attributes, lets you control the range and increment (for example, allow only even numbers) that the user can enter into an input field.
     */
    
    String getStep();

    
    void setStep(String step);

    /**
     * Returns the content type of the object.
     */
    
    String getType();

    
    void setType(String type);

    /**
     * Sets or retrieves the URL, often with a bookmark extension (#name), to use as a client-side image map.
     */
    @Deprecated
    
    String getUseMap();

    
    void setUseMap(String useMap);

    /**
     * Returns the error message that would be displayed if the user submits the form, or an empty string if no error message. It also triggers the standard error message, such as "this is a required field". The result is that the user sees validation messages without actually submitting.
     */
    
    String getValidationMessage();

    /**
     * Returns a  ValidityState object that represents the validity states of an element.
     */
    
    ValidityState getValidity();

    /**
     * Returns the value of the data at the cursor's current position.
     */
    
    String getValue();

    
    void setValue(String value);

    /**
     * Returns the input field value as a number.
     */
    
    double getValueAsNumber();

    
    void setValueAsNumber(double valueAsNumber);

    /**
     * Sets or retrieves the width of the object.
     */
    
    double getWidth();

    
    void setWidth(double width);

    /**
     * Returns whether an element will successfully validate based on forms validation rules and constraints.
     */
    
    boolean isWillValidate();

    /**
     * Returns whether a form will validate when it is submitted, without having to submit it.
     */
    boolean checkValidity();

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

    void setRangeText(String replacement);

    void setRangeText(String replacement, int start, int end);
    
    void setRangeText(String select, int start, int end, String preserve);

    /**
     * Sets the start and end positions of a selection in a text field.
     *
     * @param start     The offset into the text field for the start of the selection.
     * @param end       The offset into the text field for the end of the selection.
     * @param direction The direction in which the selection is performed.
     */
    void setSelectionRange(int start, int end, Direction direction);

    void setSelectionRange(int start, int end);

    /**
     * Decrements a range input control's value by the value given by the Step attribute. If the optional parameter is used, it will decrement the input control's step value multiplied by the parameter's value.
     *
     * @param n Value to decrement the value by.
     */
    void stepDown(double n);

    void stepDown();

    /**
     * Increments a range input control's value by the value given by the Step attribute. If the optional parameter is used, will increment the input control's value by that value.
     *
     * @param n Value to increment the value by.
     */
    void stepUp(double n);

    void stepUp();

}
