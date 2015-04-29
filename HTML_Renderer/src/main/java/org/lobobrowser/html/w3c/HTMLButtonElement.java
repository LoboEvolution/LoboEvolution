/*
 * GNU LESSER GENERAL LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Copyright (c) 2003 World Wide Web Consortium, (Massachusetts Institute of
 * Technology, Institut National de Recherche en Informatique et en Automatique,
 * Keio University). All Rights Reserved. This program is distributed under the
 * W3C's Software Intellectual Property License. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * W3C License http://www.w3.org/Consortium/Legal/ for more details.
 */

package org.lobobrowser.html.w3c;

/**
 * Push button. See the BUTTON element definition in HTML 4.01.
 * <p>
 * See also the Object Model (DOM) Level 2 HTML Specification
 * </p>
 * .
 */
public interface HTMLButtonElement extends HTMLElement {

    /**
     * Returns the <code>FORM</code> element containing this control. Returns
     * <code>null</code> if this control is not within the context of a form.
     *
     * @return the form
     */
    HTMLFormElement getForm();

    /**
     * A single character access key to give access to the form control. See the
     * accesskey attribute definition in HTML 4.01.
     *
     * @return the access key
     */
    @Override
    String getAccessKey();

    /**
     * A single character access key to give access to the form control. See the
     * accesskey attribute definition in HTML 4.01.
     *
     * @param accessKey
     *            the new access key
     */
    @Override
    void setAccessKey(String accessKey);

    /**
     * The control is unavailable in this context. See the disabled attribute
     * definition in HTML 4.01.
     *
     * @return the disabled
     */
    @Override
    boolean getDisabled();

    /**
     * The control is unavailable in this context. See the disabled attribute
     * definition in HTML 4.01.
     *
     * @param disabled
     *            the new disabled
     */
    void setDisabled(boolean disabled);

    /**
     * Form control or object name when submitted with a form. See the name
     * attribute definition in HTML 4.01.
     *
     * @return the name
     */
    String getName();

    /**
     * Form control or object name when submitted with a form. See the name
     * attribute definition in HTML 4.01.
     *
     * @param name
     *            the new name
     */
    void setName(String name);

    /**
     * Index that represents the element's position in the tabbing order. See
     * the tabindex attribute definition in HTML 4.01.
     *
     * @return the tab index
     */
    @Override
    int getTabIndex();

    /**
     * Index that represents the element's position in the tabbing order. See
     * the tabindex attribute definition in HTML 4.01.
     *
     * @param tabIndex
     *            the new tab index
     */
    @Override
    void setTabIndex(int tabIndex);

    /**
     * The type of button (all lower case). See the type attribute definition in
     * HTML 4.01.
     *
     * @return the type
     */
    String getType();

    /**
     * The current form control value. See the value attribute definition in
     * HTML 4.01.
     *
     * @return the value
     */
    String getValue();

    /**
     * The current form control value. See the value attribute definition in
     * HTML 4.01.
     *
     * @param value
     *            the new value
     */
    void setValue(String value);

    /**
     * Gets the autofocus.
     *
     * @return the autofocus
     */
    boolean getAutofocus();

    /**
     * Sets the autofocus.
     *
     * @param autofocus
     *            the new autofocus
     */
    void setAutofocus(boolean autofocus);

    /**
     * Gets the form action.
     *
     * @return the form action
     */
    String getFormAction();

    /**
     * Sets the form action.
     *
     * @param formAction
     *            the new form action
     */
    void setFormAction(String formAction);

    /**
     * Gets the form enctype.
     *
     * @return the form enctype
     */
    String getFormEnctype();

    /**
     * Sets the form enctype.
     *
     * @param formEnctype
     *            the new form enctype
     */
    void setFormEnctype(String formEnctype);

    /**
     * Gets the form method.
     *
     * @return the form method
     */
    String getFormMethod();

    /**
     * Sets the form method.
     *
     * @param formMethod
     *            the new form method
     */
    void setFormMethod(String formMethod);

    /**
     * Gets the form no validate.
     *
     * @return the form no validate
     */
    String getFormNoValidate();

    /**
     * Sets the form no validate.
     *
     * @param formNoValidate
     *            the new form no validate
     */
    void setFormNoValidate(String formNoValidate);

    /**
     * Gets the form target.
     *
     * @return the form target
     */
    String getFormTarget();

    /**
     * Sets the form target.
     *
     * @param formTarget
     *            the new form target
     */
    void setFormTarget(String formTarget);

    /**
     * Sets the type.
     *
     * @param type
     *            the new type
     */
    void setType(String type);

    /**
     * Gets the will validate.
     *
     * @return the will validate
     */
    boolean getWillValidate();

    /**
     * Gets the validity.
     *
     * @return the validity
     */
    ValidityState getValidity();

    /**
     * Gets the validation message.
     *
     * @return the validation message
     */
    String getValidationMessage();

    /**
     * Check validity.
     *
     * @return true, if successful
     */
    boolean checkValidity();

    /**
     * Sets the custom validity.
     *
     * @param error
     *            the new custom validity
     */
    void setCustomValidity(String error);
}
