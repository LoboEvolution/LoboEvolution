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

import org.w3c.dom.DOMException;
import org.w3c.dom.NodeList;


/**
 * The select element allows the selection of an option. The contained options
 * can be directly accessed through the select element as a collection. See the
 * SELECT element definition in HTML 4.01.
 * <p>
 * See also the
 * Object Model (DOM) Level 2 HTML Specification</p>.
 */
public interface HTMLSelectElement extends HTMLElement {
	
	/**
	 * The type of this form control. This is the string "select-multiple" when
	 * the multiple attribute is <code>true</code> and the string "select-one"
	 * when <code>false</code>.
	 *
	 * @return the type
	 */
	public String getType();

	/**
	 * The ordinal index of the selected option, starting from 0. The value -1
	 * is returned if no element is selected. If multiple options are selected,
	 * the index of the first selected option is returned.
	 *
	 * @return the selected index
	 */
	public int getSelectedIndex();

	/**
	 * The ordinal index of the selected option, starting from 0. The value -1
	 * is returned if no element is selected. If multiple options are selected,
	 * the index of the first selected option is returned.
	 *
	 * @param selectedIndex the new selected index
	 */
	public void setSelectedIndex(int selectedIndex);

	/**
	 * The current form control value (i.e. the value of the currently selected
	 * option), if multiple options are selected this is the value of the first
	 * selected option.
	 *
	 * @return the value
	 */
	public String getValue();

	/**
	 * The current form control value (i.e. the value of the currently selected
	 * option), if multiple options are selected this is the value of the first
	 * selected option.
	 *
	 * @param value the new value
	 */
	public void setValue(String value);

	/**
	 * The number of options in this <code>SELECT</code>.
	 *
	 * @version DOM Level 2
	 * @return the length
	 */
	public int getLength();

	/**
	 * The number of options in this <code>SELECT</code>.
	 *
	 * @version DOM Level 2
	 * @param length the new length
	 * @exception DOMException                NOT_SUPPORTED_ERR: if setting the length is not allowed by
	 *                the implementation.
	 */
	public void setLength(int length) throws DOMException;

	/**
	 * Returns the <code>FORM</code> element containing this control. Returns
	 * <code>null</code> if this control is not within the context of a form.
	 *
	 * @return the form
	 */
	public HTMLFormElement getForm();

	/**
	 * The collection of <code>OPTION</code> elements contained by this element.
	 *
	 * @version DOM Level 2
	 * @return the options
	 */
	public HTMLOptionsCollection getOptions();

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
	 * If true, multiple <code>OPTION</code> elements may be selected in this
	 * <code>SELECT</code>. See the multiple attribute definition in HTML 4.01.
	 *
	 * @return the multiple
	 */
	public boolean getMultiple();

	/**
	 * If true, multiple <code>OPTION</code> elements may be selected in this
	 * <code>SELECT</code>. See the multiple attribute definition in HTML 4.01.
	 *
	 * @param multiple the new multiple
	 */
	public void setMultiple(boolean multiple);

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
	 * Number of visible rows. See the size attribute definition in HTML 4.01.
	 *
	 * @return the size
	 */
	public int getSize();

	/**
	 * Number of visible rows. See the size attribute definition in HTML 4.01.
	 *
	 * @param size the new size
	 */
	public void setSize(int size);

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
	 * Add a new element to the collection of <code>OPTION</code> elements for
	 * this <code>SELECT</code>. This method is the equivalent of the
	 * <code>appendChild</code> method of the <code>Node</code> interface if the
	 * <code>before</code> parameter is <code>null</code>. It is equivalent to
	 * the <code>insertBefore</code> method on the parent of <code>before</code>
	 * in all other cases. This method may have no effect if the new element is
	 * not an <code>OPTION</code> or an <code>OPTGROUP</code>.
	 *
	 * @param element            The element to add.
	 * @param before            The element to insert before, or <code>null</code> for the
	 *            tail of the list.
	 */
	public void add(HTMLElement element, HTMLElement before);

	/**
	 * Remove an element from the collection of <code>OPTION</code> elements for
	 * this <code>SELECT</code>. Does nothing if no element has the given index.
	 * 
	 * @param index
	 *            The index of the item to remove, starting from 0.
	 */
	public void remove(int index);

	/**
	 * Removes keyboard focus from this element.
	 */
	public void blur();

	/**
	 * Gives keyboard focus to this element.
	 */
	public void focus();

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
	 * Item.
	 *
	 * @param index the index
	 * @return the object
	 */
	public Object item(int index);

	/**
	 * Named item.
	 *
	 * @param name the name
	 * @return the object
	 */
	public Object namedItem(String name);

	/**
	 * Adds the.
	 *
	 * @param element the element
	 */
	public void add(HTMLElement element);

	/**
	 * Adds the.
	 *
	 * @param element the element
	 * @param before the before
	 */
	public void add(HTMLElement element, int before);

	/**
	 * Gets the selected options.
	 *
	 * @return the selected options
	 */
	public HTMLCollection getSelectedOptions();

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
