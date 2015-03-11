package org.lobobrowser.html.w3c;

import org.w3c.dom.NodeList;


/**
 * The Interface HTMLOutputElement.
 */
public interface HTMLOutputElement extends HTMLElement {
	// HTMLOutputElement
	/**
	 * Gets the html for.
	 *
	 * @return the html for
	 */
	public DOMSettableTokenList getHtmlFor();

	/**
	 * Sets the html for.
	 *
	 * @param htmlFor the new html for
	 */
	public void setHtmlFor(String htmlFor);

	/**
	 * Gets the form.
	 *
	 * @return the form
	 */
	public HTMLFormElement getForm();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name);

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType();

	/**
	 * Gets the default value.
	 *
	 * @return the default value
	 */
	public String getDefaultValue();

	/**
	 * Sets the default value.
	 *
	 * @param defaultValue the new default value
	 */
	public void setDefaultValue(String defaultValue);

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue();

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value);

	/**
	 * Gets the will validate.
	 *
	 * @return the will validate
	 */
	public boolean getWillValidate();

	/**
	 * Gets the validity.
	 *
	 * @return the validity
	 */
	public ValidityState getValidity();

	/**
	 * Gets the validation message.
	 *
	 * @return the validation message
	 */
	public String getValidationMessage();

	/**
	 * Check validity.
	 *
	 * @return true, if successful
	 */
	public boolean checkValidity();

	/**
	 * Sets the custom validity.
	 *
	 * @param error the new custom validity
	 */
	public void setCustomValidity(String error);

	/**
	 * Gets the labels.
	 *
	 * @return the labels
	 */
	public NodeList getLabels();
}
