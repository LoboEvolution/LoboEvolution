/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.dom;

/**
 * Multi-line text field. See the TEXTAREA element definition in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
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
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAccessKey();

	/**
	 * Width of control (in characters). See the cols attribute definition in HTML
	 * 4.01.
	 *
	 * @return a int.
	 */
	int getCols();

	/**
	 * Represents the contents of the element. The value of this attribute does not
	 * change if the contents of the corresponding form control, in an interactive
	 * user agent, changes.
	 *
	 * @version DOM Level 2
	 * @return a {@link java.lang.String} object.
	 */
	String getDefaultValue();

	/**
	 * The control is unavailable in this context. See the disabled attribute
	 * definition in HTML 4.01.
	 *
	 * @return a boolean.
	 */
	boolean getDisabled();

	/**
	 * Returns the FORM element containing this control. Returns
	 * null if this control is not within the context of a form.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLFormElement} object.
	 */
	HTMLFormElement getForm();

	/**
	 * Form control or object name when submitted with a form. See the name
	 * attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getName();

	/**
	 * This control is read-only. See the readonly attribute definition in HTML
	 * 4.01.
	 *
	 * @return a boolean.
	 */
	boolean getReadOnly();

	/**
	 * Number of text rows. See the rows attribute definition in HTML 4.01.
	 *
	 * @return a int.
	 */
	int getRows();

	/**
	 * Index that represents the element's position in the tabbing order. See the
	 * tabindex attribute definition in HTML 4.01.
	 *
	 * @return a int.
	 */
	int getTabIndex();

	/**
	 * The type of this form control. This the string "textarea".
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getType();

	/**
	 * Represents the current contents of the corresponding form control, in an
	 * interactive user agent. Changing this attribute changes the contents of the
	 * form control, but does not change the contents of the element. If the
	 * entirety of the data can not fit into a single DOMString, the
	 * implementation may truncate the data.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getValue();
	
	
	int getMaxLength();

	/**
	 * Select the contents of the TEXTAREA.
	 */
	void select();

	/**
	 * A single character access key to give access to the form control. See the
	 * accesskey attribute definition in HTML 4.01.
	 *
	 * @param accessKey a {@link java.lang.String} object.
	 */
	void setAccessKey(String accessKey);

	/**
	 * Width of control (in characters). See the cols attribute definition in HTML
	 * 4.01.
	 *
	 * @param cols a int.
	 */
	void setCols(int cols);

	/**
	 * Represents the contents of the element. The value of this attribute does not
	 * change if the contents of the corresponding form control, in an interactive
	 * user agent, changes.
	 *
	 * @version DOM Level 2
	 * @param defaultValue a {@link java.lang.String} object.
	 */
	void setDefaultValue(String defaultValue);

	/**
	 * The control is unavailable in this context. See the disabled attribute
	 * definition in HTML 4.01.
	 *
	 * @param disabled a boolean.
	 */
	void setDisabled(boolean disabled);

	/**
	 * Form control or object name when submitted with a form. See the name
	 * attribute definition in HTML 4.01.
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	void setName(String name);

	/**
	 * This control is read-only. See the readonly attribute definition in HTML
	 * 4.01.
	 *
	 * @param readOnly a boolean.
	 */
	void setReadOnly(boolean readOnly);

	/**
	 * Number of text rows. See the rows attribute definition in HTML 4.01.
	 *
	 * @param rows a int.
	 */
	void setRows(int rows);

	/**
	 * Index that represents the element's position in the tabbing order. See the
	 * tabindex attribute definition in HTML 4.01.
	 *
	 * @param tabIndex a int.
	 */
	void setTabIndex(int tabIndex);

	/**
	 * Represents the current contents of the corresponding form control, in an
	 * interactive user agent. Changing this attribute changes the contents of the
	 * form control, but does not change the contents of the element. If the
	 * entirety of the data can not fit into a single DOMString, the
	 * implementation may truncate the data.
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	void setValue(String value);

}
