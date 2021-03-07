package org.loboevolution.html.dom;


import org.loboevolution.html.node.ValidityState;

/**
 * Provides special properties and methods (beyond the regular HTMLElement interface it also has available to it by inheritance) for manipulating the layout and presentation of &lt;fieldset&gt; elements.
 */
public interface HTMLFieldSetElement extends HTMLElement {


    
    boolean isDisabled();

    
    void setDisabled(boolean disabled);

    
    HTMLCollection getElements();

    /**
     * Retrieves a reference to the form that the object is embedded in.
     */
    
    
    HTMLFormElement getForm();

    
    String getName();

    
    void setName(String name);

    
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
     * Returns whether an element will successfully validate based on forms validation rules and constraints.
     */
    
    boolean isWillValidate();

    /**
     * Returns whether a form will validate when it is submitted, without having to submit it.
     */
    boolean checkValidity();

    boolean reportValidity();

    /**
     * Sets a custom error message that is displayed when a form is submitted.
     *
     * @param error Sets a custom error message that is displayed when a form is submitted.
     */
    void setCustomValidity(String error);

}
