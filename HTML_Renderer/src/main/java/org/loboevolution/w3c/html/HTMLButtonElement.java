/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.w3c.html;

import org.w3c.dom.NodeList;

/**
 * The Interface HTMLButtonElement.
 */
public interface HTMLButtonElement extends HTMLElement {

	/**
	 * Gets the autofocus.
	 *
	 * @return the autofocus
	 */
	// HTMLButtonElement
	public boolean getAutofocus();

	/**
	 * Sets the autofocus.
	 *
	 * @param autofocus
	 *            the new autofocus
	 */
	public void setAutofocus(boolean autofocus);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getDisabled()
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
	 * Gets the form action.
	 *
	 * @return the form action
	 */
	public String getFormAction();

	/**
	 * Sets the form action.
	 *
	 * @param formAction
	 *            the new form action
	 */
	public void setFormAction(String formAction);

	/**
	 * Gets the form enctype.
	 *
	 * @return the form enctype
	 */
	public String getFormEnctype();

	/**
	 * Sets the form enctype.
	 *
	 * @param formEnctype
	 *            the new form enctype
	 */
	public void setFormEnctype(String formEnctype);

	/**
	 * Gets the form method.
	 *
	 * @return the form method
	 */
	public String getFormMethod();

	/**
	 * Sets the form method.
	 *
	 * @param formMethod
	 *            the new form method
	 */
	public void setFormMethod(String formMethod);

	/**
	 * Gets the form no validate.
	 *
	 * @return the form no validate
	 */
	public String getFormNoValidate();

	/**
	 * Sets the form no validate.
	 *
	 * @param formNoValidate
	 *            the new form no validate
	 */
	public void setFormNoValidate(String formNoValidate);

	/**
	 * Gets the form target.
	 *
	 * @return the form target
	 */
	public String getFormTarget();

	/**
	 * Sets the form target.
	 *
	 * @param formTarget
	 *            the new form target
	 */
	public void setFormTarget(String formTarget);

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
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(String type);

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue();

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
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
