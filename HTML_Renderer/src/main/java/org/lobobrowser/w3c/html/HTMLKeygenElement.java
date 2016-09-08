/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.w3c.html;

import org.w3c.dom.NodeList;


/**
 * The Interface HTMLKeygenElement.
 */
public interface HTMLKeygenElement extends HTMLElement {
	
	/**
	 * Gets the autofocus.
	 *
	 * @return the autofocus
	 */
	// HTMLKeygenElement
	public boolean getAutofocus();

	/**
	 * Sets the autofocus.
	 *
	 * @param autofocus
	 *            the new autofocus
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
	 * @param challenge
	 *            the new challenge
	 */
	public void setChallenge(String challenge);

	/* (non-Javadoc)
	 * @see org.lobobrowser.w3c.html.HTMLElement#getDisabled()
	 */
	@Override
	public boolean getDisabled();

	/**
	 * Sets the disabled.
	 *
	 * @param disabled
	 *            the new disabled
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
	 * @param keytype
	 *            the new keytype
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
	 * @param name
	 *            the new name
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
	 * @param error
	 *            the new custom validity
	 */
	public void setCustomValidity(String error);

	/**
	 * Gets the labels.
	 *
	 * @return the labels
	 */
	public NodeList getLabels();
}
