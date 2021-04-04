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

/**
 * Provides properties and methods (beyond the regular HTMLElement interface it
 * also has available to it by inheritance) for manipulating &lt;button&gt;
 * elements.
 *
 *
 *
 */
public interface HTMLButtonElement extends HTMLElement {

	/**
	 * Provides a way to direct a user to a specific field when a document loads.
	 * This can provide both direction and convenience for a user, reducing the need
	 * to click or tab to a field when a page opens. This attribute is true when
	 * present on an element, and false when missing.
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
	 * Overrides the action attribute (where the data on a form is sent) on the
	 * parent form element.
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
	 * Used to override the encoding (formEnctype attribute) specified on the form
	 * element.
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
	 * Overrides any validation or required attributes on a form or form elements to
	 * allow it to be submitted without validation. This can be used to create a
	 * "save draft"-type submit option.
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
	 * <p>getLabels.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.NodeList} object.
	 */
	NodeList getLabels();

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
	 * Gets the classification and default behavior of the button.
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
	 * Returns the error message that would be displayed if the user submits the
	 * form, or an empty string if no error message. It also triggers the standard
	 * error message, such as "this is a required field". The result is that the
	 * user sees validation messages without actually submitting.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getValidationMessage();

	/**
	 * Returns a ValidityState object that represents the validity states of an
	 * element.
	 *
	 * @return a {@link org.loboevolution.html.node.ValidityState} object.
	 */
	ValidityState getValidity();

	/**
	 * Sets or retrieves the default or selected value of the control.
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
	 * Returns whether an element will successfully validate based on forms
	 * validation rules and constraints.
	 *
	 * @return a boolean.
	 */
	boolean isWillValidate();

	/**
	 * Returns whether a form will validate when it is submitted, without having to
	 * submit it.
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
	 * Sets a custom error message that is displayed when a form is submitted.
	 *
	 * @param error Sets a custom error message that is displayed when a form is
	 *              submitted.
	 */
	void setCustomValidity(String error);

}
