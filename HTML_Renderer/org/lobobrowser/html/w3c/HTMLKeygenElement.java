package org.lobobrowser.html.w3c;

import org.w3c.dom.NodeList;


/**
 * The Interface HTMLKeygenElement.
 */
public interface HTMLKeygenElement extends HTMLElement {
	// HTMLKeygenElement
	/**
	 * Gets the autofocus.
	 *
	 * @return the autofocus
	 */
	public boolean getAutofocus();

	/**
	 * Sets the autofocus.
	 *
	 * @param autofocus the new autofocus
	 */
	public void setAutofocus(boolean autofocus);

	/**
	 * Gets the challenge.
	 *
	 * @return the challenge
	 */
	public String getChallenge();

	/**
	 * Sets the challenge.
	 *
	 * @param challenge the new challenge
	 */
	public void setChallenge(String challenge);

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLElement#getDisabled()
	 */
	public boolean getDisabled();

	/**
	 * Sets the disabled.
	 *
	 * @param disabled the new disabled
	 */
	public void setDisabled(boolean disabled);

	/**
	 * Gets the form.
	 *
	 * @return the form
	 */
	public HTMLFormElement getForm();

	/**
	 * Gets the keytype.
	 *
	 * @return the keytype
	 */
	public String getKeytype();

	/**
	 * Sets the keytype.
	 *
	 * @param keytype the new keytype
	 */
	public void setKeytype(String keytype);

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
