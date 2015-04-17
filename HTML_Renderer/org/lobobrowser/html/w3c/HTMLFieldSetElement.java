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
 * Organizes form controls into logical groups. See the FIELDSET element
 * definition in HTML 4.01.
 * <p>
 * See also the Object Model (DOM) Level 2 HTML Specification
 * </p>
 * .
 */
public interface HTMLFieldSetElement extends HTMLElement {

    /**
     * Returns the <code>FORM</code> element containing this control. Returns
     * <code>null</code> if this control is not within the context of a form.
     *
     * @return the form
     */
    HTMLFormElement getForm();

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLElement#getDisabled()
     */
    @Override
    boolean getDisabled();

    /**
     * Sets the disabled.
     *
     * @param disabled
     *            the new disabled
     */
    void setDisabled(boolean disabled);

    /**
     * Gets the name.
     *
     * @return the name
     */
    String getName();

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    void setName(String name);

    /**
     * Gets the type.
     *
     * @return the type
     */
    String getType();

    /**
     * Gets the elements.
     *
     * @return the elements
     */
    HTMLFormControlsCollection getElements();

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
