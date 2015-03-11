/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Copyright (c) 2003 World Wide Web Consortium,
 * (Massachusetts Institute of Technology, Institut National de
 * Recherche en Informatique et en Automatique, Keio University). All
 * Rights Reserved. This program is distributed under the W3C's Software
 * Intellectual Property License. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 * See W3C License http://www.w3.org/Consortium/Legal/ for more details.
 */

package org.lobobrowser.html.w3c;

import org.w3c.dom.NodeList;


/**
 * Multi-line text field. See the TEXTAREA element definition in HTML 4.01.
 * <p>
 * See also the
 * Object Model (DOM) Level 2 HTML Specification</p>.
 */
public interface HTMLTextAreaElement extends HTMLElement {
	
	/**
	 * Represents the contents of the element. The value of this attribute does
	 * not change if the contents of the corresponding form control, in an
	 * interactive user agent, changes.
	 *
	 * @version DOM Level 2
	 * @return the default value
	 */
	public String getDefaultValue();

	/**
	 * Represents the contents of the element. The value of this attribute does
	 * not change if the contents of the corresponding form control, in an
	 * interactive user agent, changes.
	 *
	 * @version DOM Level 2
	 * @param defaultValue the new default value
	 */
	public void setDefaultValue(String defaultValue);

	/**
	 * Returns the <code>FORM</code> element containing this control. Returns
	 * <code>null</code> if this control is not within the context of a form.
	 *
	 * @return the form
	 */
	public HTMLFormElement getForm();

	/**
	 * A single character access key to give access to the form control. See the
	 * accesskey attribute definition in HTML 4.01.
	 *
	 * @return the access key
	 */
	public String getAccessKey();

	/**
	 * A single character access key to give access to the form control. See the
	 * accesskey attribute definition in HTML 4.01.
	 *
	 * @param accessKey the new access key
	 */
	public void setAccessKey(String accessKey);

	/**
	 * Width of control (in characters). See the cols attribute definition in
	 * HTML 4.01.
	 *
	 * @return the cols
	 */
	public int getCols();

	/**
	 * Width of control (in characters). See the cols attribute definition in
	 * HTML 4.01.
	 *
	 * @param cols the new cols
	 */
	public void setCols(int cols);

	/**
	 * The control is unavailable in this context. See the disabled attribute
	 * definition in HTML 4.01.
	 *
	 * @return the disabled
	 */
	public boolean getDisabled();

	/**
	 * The control is unavailable in this context. See the disabled attribute
	 * definition in HTML 4.01.
	 *
	 * @param disabled the new disabled
	 */
	public void setDisabled(boolean disabled);

	/**
	 * Form control or object name when submitted with a form. See the name
	 * attribute definition in HTML 4.01.
	 *
	 * @return the name
	 */
	public String getName();

	/**
	 * Form control or object name when submitted with a form. See the name
	 * attribute definition in HTML 4.01.
	 *
	 * @param name the new name
	 */
	public void setName(String name);

	/**
	 * This control is read-only. See the readonly attribute definition in HTML
	 * 4.01.
	 *
	 * @return the read only
	 */
	public boolean getReadOnly();

	/**
	 * This control is read-only. See the readonly attribute definition in HTML
	 * 4.01.
	 *
	 * @param readOnly the new read only
	 */
	public void setReadOnly(boolean readOnly);

	/**
	 * Number of text rows. See the rows attribute definition in HTML 4.01.
	 *
	 * @return the rows
	 */
	public int getRows();

	/**
	 * Number of text rows. See the rows attribute definition in HTML 4.01.
	 *
	 * @param rows the new rows
	 */
	public void setRows(int rows);

	/**
	 * Index that represents the element's position in the tabbing order. See
	 * the tabindex attribute definition in HTML 4.01.
	 *
	 * @return the tab index
	 */
	public int getTabIndex();

	/**
	 * Index that represents the element's position in the tabbing order. See
	 * the tabindex attribute definition in HTML 4.01.
	 *
	 * @param tabIndex the new tab index
	 */
	public void setTabIndex(int tabIndex);

	/**
	 * The type of this form control. This the string "textarea".
	 *
	 * @return the type
	 */
	public String getType();

	/**
	 * Represents the current contents of the corresponding form control, in an
	 * interactive user agent. Changing this attribute changes the contents of
	 * the form control, but does not change the contents of the element. If the
	 * entirety of the data can not fit into a single <code>DOMString</code>,
	 * the implementation may truncate the data.
	 *
	 * @return the value
	 */
	public String getValue();

	/**
	 * Represents the current contents of the corresponding form control, in an
	 * interactive user agent. Changing this attribute changes the contents of
	 * the form control, but does not change the contents of the element. If the
	 * entirety of the data can not fit into a single <code>DOMString</code>,
	 * the implementation may truncate the data.
	 *
	 * @param value the new value
	 */
	public void setValue(String value);

	/**
	 * Removes keyboard focus from this element.
	 */
	public void blur();

	/**
	 * Gives keyboard focus to this element.
	 */
	public void focus();

	/**
	 * Select the contents of the <code>TEXTAREA</code>.
	 */
	public void select();

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
	 * Gets the max length.
	 *
	 * @return the max length
	 */
	public int getMaxLength();

	/**
	 * Sets the max length.
	 *
	 * @param maxLength the new max length
	 */
	public void setMaxLength(int maxLength);

	/**
	 * Gets the placeholder.
	 *
	 * @return the placeholder
	 */
	public String getPlaceholder();

	/**
	 * Sets the placeholder.
	 *
	 * @param placeholder the new placeholder
	 */
	public void setPlaceholder(String placeholder);

	/**
	 * Gets the required.
	 *
	 * @return the required
	 */
	public boolean getRequired();

	/**
	 * Sets the required.
	 *
	 * @param required the new required
	 */
	public void setRequired(boolean required);

	/**
	 * Gets the wrap.
	 *
	 * @return the wrap
	 */
	public String getWrap();

	/**
	 * Sets the wrap.
	 *
	 * @param wrap the new wrap
	 */
	public void setWrap(String wrap);

	/**
	 * Gets the text length.
	 *
	 * @return the text length
	 */
	public int getTextLength();

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

	/**
	 * Gets the selection start.
	 *
	 * @return the selection start
	 */
	public int getSelectionStart();

	/**
	 * Sets the selection start.
	 *
	 * @param selectionStart the new selection start
	 */
	public void setSelectionStart(int selectionStart);

	/**
	 * Gets the selection end.
	 *
	 * @return the selection end
	 */
	public int getSelectionEnd();

	/**
	 * Sets the selection end.
	 *
	 * @param selectionEnd the new selection end
	 */
	public void setSelectionEnd(int selectionEnd);

	/**
	 * Sets the selection range.
	 *
	 * @param start the start
	 * @param end the end
	 */
	public void setSelectionRange(int start, int end);
}
