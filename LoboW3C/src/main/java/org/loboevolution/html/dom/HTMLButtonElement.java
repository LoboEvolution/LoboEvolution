package org.loboevolution.html.dom;

import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.ValidityState;

/**
 * Provides properties and methods (beyond the regular HTMLElement interface it
 * also has available to it by inheritance) for manipulating &lt;button&gt;
 * elements.
 */
public interface HTMLButtonElement extends HTMLElement {

	/**
	 * Provides a way to direct a user to a specific field when a document loads.
	 * This can provide both direction and convenience for a user, reducing the need
	 * to click or tab to a field when a page opens. This attribute is true when
	 * present on an element, and false when missing.
	 */

	boolean isAutofocus();

	void setAutofocus(boolean autofocus);

	boolean isDisabled();

	void setDisabled(boolean disabled);

	/**
	 * Retrieves a reference to the form that the object is embedded in.
	 */

	HTMLFormElement getForm();

	/**
	 * Overrides the action attribute (where the data on a form is sent) on the
	 * parent form element.
	 */

	String getFormAction();

	void setFormAction(String formAction);

	/**
	 * Used to override the encoding (formEnctype attribute) specified on the form
	 * element.
	 */

	String getFormEnctype();

	void setFormEnctype(String formEnctype);

	/**
	 * Overrides the submit method attribute previously specified on a form element.
	 */

	String getFormMethod();

	void setFormMethod(String formMethod);

	/**
	 * Overrides any validation or required attributes on a form or form elements to
	 * allow it to be submitted without validation. This can be used to create a
	 * "save draft"-type submit option.
	 */

	boolean isFormNoValidate();

	void setFormNoValidate(boolean formNoValidate);

	/**
	 * Overrides the target attribute on a form element.
	 */

	String getFormTarget();

	void setFormTarget(String formTarget);

	NodeList getLabels();

	/**
	 * Sets or retrieves the name of the object.
	 */

	String getName();

	void setName(String name);

	/**
	 * Gets the classification and default behavior of the button.
	 */

	String getType();

	void setType(String type);

	/**
	 * Returns the error message that would be displayed if the user submits the
	 * form, or an empty string if no error message. It also triggers the standard
	 * error message, such as "this is a required field". The result is that the
	 * user sees validation messages without actually submitting.
	 */

	String getValidationMessage();

	/**
	 * Returns a ValidityState object that represents the validity states of an
	 * element.
	 */

	ValidityState getValidity();

	/**
	 * Sets or retrieves the default or selected value of the control.
	 */

	String getValue();

	void setValue(String value);

	/**
	 * Returns whether an element will successfully validate based on forms
	 * validation rules and constraints.
	 */

	boolean isWillValidate();

	/**
	 * Returns whether a form will validate when it is submitted, without having to
	 * submit it.
	 */
	boolean checkValidity();

	boolean reportValidity();

	/**
	 * Sets a custom error message that is displayed when a form is submitted.
	 *
	 * @param error Sets a custom error message that is displayed when a form is
	 *              submitted.
	 */
	void setCustomValidity(String error);

}
