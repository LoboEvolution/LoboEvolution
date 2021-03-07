package org.loboevolution.html.dom;


import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.ValidityState;
import org.loboevolution.jsenum.Direction;

/**
 * Provides special properties and methods for manipulating the layout and presentation of &lt;textarea&gt; elements.
 */
public interface HTMLTextAreaElement extends HTMLElement {

    String getAutocomplete();

    
    void setAutocomplete(String autocomplete);

    /**
     * Provides a way to direct a user to a specific field when a document loads. This can provide both direction and convenience for a user, reducing the need to click or tab to a field when a page opens. This attribute is true when present on an element, and false when missing.
     */
    
    boolean isAutofocus();

    
    void setAutofocus(boolean autofocus);

    /**
     * Sets or retrieves the width of the object.
     */
    
    int getCols();

    
    void setCols(int cols);

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

    
    NodeList getLabels();

    /**
     * Sets or retrieves the maximum number of characters that the user can enter in a text control.
     */
    
    int getMaxLength();

    
    void setMaxLength(int maxLength);

    
    int getMinLength();

    
    void setMinLength(int minLength);

    /**
     * Sets or retrieves the name of the object.
     */
    
    String getName();

    
    void setName(String name);

    /**
     * Gets or sets a text string that is displayed in an input field as a hint or prompt to users as the format or type of information they need to enter.The text appears in an input field until the user puts focus on the field.
     */
    
    String getPlaceholder();

    
    void setPlaceholder(String placeholder);

    /**
     * Sets or retrieves the value indicated whether the content of the object is read-only.
     */
    
    boolean isReadOnly();

    
    void setReadOnly(boolean readOnly);

    /**
     * When present, marks an element that can't be submitted without a value.
     */
    
    boolean isRequired();

    
    void setRequired(boolean required);

    /**
     * Sets or retrieves the number of horizontal rows contained in the object.
     */
    
    int getRows();

    
    void setRows(int rows);

    
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

    
    int getTextLength();

    /**
     * Retrieves the type of control.
     */
    
    String getType();

    /**
     * Returns the error message that would be displayed if the user submits the form, or an empty string if no error message. It also triggers the standard error message, such as "this is a required field". The result is that the user sees validation messages without actually submitting.
     */
    
    String getValidationMessage();

    /**
     * Returns a  ValidityState object that represents the validity states of an element.
     */
    
    ValidityState getValidity();

    /**
     * Retrieves or sets the text in the entry field of the textArea element.
     */
    
    String getValue();

    
    void setValue(String value);

    /**
     * Returns whether an element will successfully validate based on forms validation rules and constraints.
     */
    
    boolean isWillValidate();

    /**
     * Sets or retrieves how to handle wordwrapping in the object.
     */
    
    String getWrap();

    
    void setWrap(String wrap);

    /**
     * Returns whether a form will validate when it is submitted, without having to submit it.
     */
    boolean checkValidity();

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

    void setRangeText(String replacement);

    void setRangeText(String replacement, int start, int end);

    /**
     * Sets the start and end positions of a selection in a text field.
     *
     * @param start     The offset into the text field for the start of the selection.
     * @param end       The offset into the text field for the end of the selection.
     * @param direction The direction in which the selection is performed.
     */
    void setSelectionRange(int start, int end, Direction direction);

    void setSelectionRange(int start, int end);

}
