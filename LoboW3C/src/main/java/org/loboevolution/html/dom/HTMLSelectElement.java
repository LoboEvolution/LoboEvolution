package org.loboevolution.html.dom;

import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.ValidityState;

/**
 * A &lt;select&gt; HTML Element. These elements also share all of the properties and methods of other HTML elements via
 * the HTMLElement interface.
 */
public interface HTMLSelectElement extends HTMLElement {

    /**
     * Gets autocomplete.
     *
     * @return the autocomplete
     */
    
    String getAutocomplete();

    /**
     * Sets autocomplete.
     *
     * @param autocomplete the autocomplete
     */
    
    void setAutocomplete(String autocomplete);

    /**
     * Provides a way to direct a user to a specific field when a document loads. This can provide both direction and
     * convenience for a user, reducing the need to click or tab to a field when a page opens. This attribute is true
     * when present on an element, and false when missing.
     *
     * @return the boolean
     */
    
    boolean isAutofocus();

    /**
     * Sets autofocus.
     *
     * @param autofocus the autofocus
     */
    
    void setAutofocus(boolean autofocus);

    /**
     * Is disabled boolean.
     *
     * @return the boolean
     */
    
    boolean isDisabled();

    /**
     * Sets disabled.
     *
     * @param disabled the disabled
     */
    
    void setDisabled(boolean disabled);

    /**
     * Retrieves a reference to the form that the object is embedded in.
     *
     * @return the form
     */
    
    
    HTMLFormElement getForm();

    /**
     * Gets labels.
     *
     * @return the labels
     */
    
    NodeList getLabels();

    /**
     * Sets or retrieves the number of objects in a collection.
     *
     * @param length the length
     */
    
    void setLength(int length);

    /**
     * Sets or retrieves the Boolean value indicating whether multiple items can be selected from a list.
     *
     * @return the boolean
     */
    
    boolean isMultiple();

    /**
     * Sets multiple.
     *
     * @param multiple the multiple
     */
    
    void setMultiple(boolean multiple);

    /**
     * Sets or retrieves the name of the object.
     *
     * @return the name
     */
    
    String getName();

    /**
     * Sets name.
     *
     * @param name the name
     */
    
    void setName(String name);

    /**
     * Gets options.
     *
     * @return the options
     */
    
    HTMLOptionsCollection getOptions();

    /**
     * When present, marks an element that can't be submitted without a value.
     *
     * @return the boolean
     */
    
    boolean isRequired();

    /**
     * Sets required.
     *
     * @param required the required
     */
    
    void setRequired(boolean required);

    /**
     * Sets or retrieves the index of the selected option in a select object.
     *
     * @return the selected index
     */
    
    int getSelectedIndex();

    /**
     * Sets selected index.
     *
     * @param selectedIndex the selected index
     */
    
    void setSelectedIndex(int selectedIndex);

    /**
     * Gets selected options.
     *
     * @return the selected options
     */
    
    HTMLCollection getSelectedOptions();

    /**
     * Sets or retrieves the number of rows in the list box.
     *
     * @return the size
     */
    
    int getSize();

    /**
     * Sets size.
     *
     * @param size the size
     */
    
    void setSize(int size);

    /**
     * Retrieves the type of select control based on the value of the MULTIPLE attribute.
     *
     * @return the type
     */
    
    String getType();

    /**
     * Returns the error message that would be displayed if the user submits the form, or an empty string if no error
     * message. It also triggers the standard error message, such as "this is a required field". The result is that
     * the user sees validation messages without actually submitting.
     *
     * @return the validation message
     */
    
    String getValidationMessage();

    /**
     * Returns a  ValidityState object that represents the validity states of an element.
     *
     * @return the validity
     */

    ValidityState getValidity();

    /**
     * Sets or retrieves the value which is returned to the server when the form control is submitted.
     *
     * @return the value
     */
    
    String getValue();

    /**
     * Sets value.
     *
     * @param value the value
     */
    
    void setValue(String value);

    /**
     * Returns whether an element will successfully validate based on forms validation rules and constraints.
     *
     * @return the boolean
     */
    
    boolean isWillValidate();

    /**
     * Adds an element to the areas, controlRange, or options collection.
     *
     * @param element Variant of type Number that specifies the index position in the collection where the element is
     *                placed. If no value is given, the method places the element at the end of the collection.
     * @param before  Variant of type Object that specifies an element to insert before, or null to append the object
     *                to the collection.
     */
    void add(Object element, Object before);

    /**
     * Add.
     *
     * @param element the element
     */
    void add(Object element);

    /**
     * Returns whether a form will validate when it is submitted, without having to submit it.
     *
     * @return the boolean
     */
    boolean checkValidity();

    /**
     * Retrieves a select object or an object from an options collection.
     *
     * @param index Variant of type Number that specifies the zero-based index of the object to retrieve when a
     *              collection is returned.
     *
     * @return the element
     */
    
    Element item(int index);

    /**
     * Retrieves a select object or an object from an options collection.
     *
     * @param name A String that specifies the name or id property of the object to retrieve. A collection is
     *             returned           if more than one match is made.
     *
     * @return the html option element
     */
    
    HTMLOptionElement namedItem(String name);

    /**
     * Removes an element from the collection.
     *
     */
    void remove();

    /**
     * Remove.
     *
     * @param element the element
     */
    void remove(Object element);

    /**
     * Report validity boolean.
     *
     * @return the boolean
     */
    boolean reportValidity();

    /**
     * Sets a custom error message that is displayed when a form is submitted.
     *
     * @param error Sets a custom error message that is displayed when a form is submitted.
     */
    void setCustomValidity(String error);

    /**
     * Set.
     *
     * @param name  the name
     * @param value the value
     */
    void set(int name, Element value);

	int getLength();

}
