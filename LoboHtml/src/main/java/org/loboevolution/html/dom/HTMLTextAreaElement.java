/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

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

package org.loboevolution.html.dom;

/**
 * Multi-line text field. See the TEXTAREA element definition in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 */
public interface HTMLTextAreaElement extends HTMLElement {
	/**
	 * Removes keyboard focus from this element.
	 */
	void blur();

	/**
	 * Gives keyboard focus to this element.
	 */
	void focus();

	/**
	 * A single character access key to give access to the form control. See the
	 * accesskey attribute definition in HTML 4.01.
	 */
	String getAccessKey();

	/**
	 * Width of control (in characters). See the cols attribute definition in HTML
	 * 4.01.
	 */
	int getCols();

	/**
	 * Represents the contents of the element. The value of this attribute does not
	 * change if the contents of the corresponding form control, in an interactive
	 * user agent, changes.
	 * 
	 * @version DOM Level 2
	 */
	String getDefaultValue();

	/**
	 * The control is unavailable in this context. See the disabled attribute
	 * definition in HTML 4.01.
	 */
	boolean getDisabled();

	/**
	 * Returns the <code>FORM</code> element containing this control. Returns
	 * <code>null</code> if this control is not within the context of a form.
	 */
	HTMLFormElement getForm();

	/**
	 * Form control or object name when submitted with a form. See the name
	 * attribute definition in HTML 4.01.
	 */
	String getName();

	/**
	 * This control is read-only. See the readonly attribute definition in HTML
	 * 4.01.
	 */
	boolean getReadOnly();

	/**
	 * Number of text rows. See the rows attribute definition in HTML 4.01.
	 */
	int getRows();

	/**
	 * Index that represents the element's position in the tabbing order. See the
	 * tabindex attribute definition in HTML 4.01.
	 */
	int getTabIndex();

	/**
	 * The type of this form control. This the string "textarea".
	 */
	String getType();

	/**
	 * Represents the current contents of the corresponding form control, in an
	 * interactive user agent. Changing this attribute changes the contents of the
	 * form control, but does not change the contents of the element. If the
	 * entirety of the data can not fit into a single <code>DOMString</code>, the
	 * implementation may truncate the data.
	 */
	String getValue();

	/**
	 * Select the contents of the <code>TEXTAREA</code>.
	 */
	void select();

	/**
	 * A single character access key to give access to the form control. See the
	 * accesskey attribute definition in HTML 4.01.
	 */
	void setAccessKey(String accessKey);

	/**
	 * Width of control (in characters). See the cols attribute definition in HTML
	 * 4.01.
	 */
	void setCols(int cols);

	/**
	 * Represents the contents of the element. The value of this attribute does not
	 * change if the contents of the corresponding form control, in an interactive
	 * user agent, changes.
	 * 
	 * @version DOM Level 2
	 */
	void setDefaultValue(String defaultValue);

	/**
	 * The control is unavailable in this context. See the disabled attribute
	 * definition in HTML 4.01.
	 */
	void setDisabled(boolean disabled);

	/**
	 * Form control or object name when submitted with a form. See the name
	 * attribute definition in HTML 4.01.
	 */
	void setName(String name);

	/**
	 * This control is read-only. See the readonly attribute definition in HTML
	 * 4.01.
	 */
	void setReadOnly(boolean readOnly);

	/**
	 * Number of text rows. See the rows attribute definition in HTML 4.01.
	 */
	void setRows(int rows);

	/**
	 * Index that represents the element's position in the tabbing order. See the
	 * tabindex attribute definition in HTML 4.01.
	 */
	void setTabIndex(int tabIndex);

	/**
	 * Represents the current contents of the corresponding form control, in an
	 * interactive user agent. Changing this attribute changes the contents of the
	 * form control, but does not change the contents of the element. If the
	 * entirety of the data can not fit into a single <code>DOMString</code>, the
	 * implementation may truncate the data.
	 */
	void setValue(String value);

}
