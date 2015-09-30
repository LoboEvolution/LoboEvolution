/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.w3c.html;

import org.w3c.dom.NodeList;


/**
 * The Interface HTMLSelectElement.
 */
public interface HTMLSelectElement extends HTMLElement {
	
	/**
	 * Gets the autofocus.
	 *
	 * @return the autofocus
	 */
	// HTMLSelectElement
	public boolean getAutofocus();

	/**
	 * Sets the autofocus.
	 *
	 * @param autofocus
	 *            the new autofocus
	 */
	public void setAutofocus(boolean autofocus);

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
	 * Gets the multiple.
	 *
	 * @return the multiple
	 */
	public boolean getMultiple();

	/**
	 * Sets the multiple.
	 *
	 * @param multiple
	 *            the new multiple
	 */
	public void setMultiple(boolean multiple);

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
	 * Gets the required.
	 *
	 * @return the required
	 */
	public boolean getRequired();

	/**
	 * Sets the required.
	 *
	 * @param required
	 *            the new required
	 */
	public void setRequired(boolean required);

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize();

	/**
	 * Sets the size.
	 *
	 * @param size
	 *            the new size
	 */
	public void setSize(int size);

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType();

	/**
	 * Gets the options.
	 *
	 * @return the options
	 */
	public HTMLOptionsCollection getOptions();

	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public int getLength();

	/**
	 * Sets the length.
	 *
	 * @param length
	 *            the new length
	 */
	public void setLength(int length);

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
	public void add(HTMLElement element, HTMLElement before);

	/**
	 * Adds the.
	 *
	 * @param element the element
	 * @param before the before
	 */
	public void add(HTMLElement element, int before);

	/**
	 * Removes the.
	 *
	 * @param index the index
	 */
	public void remove(int index);

	/**
	 * Gets the selected options.
	 *
	 * @return the selected options
	 */
	public HTMLCollection getSelectedOptions();

	/**
	 * Gets the selected index.
	 *
	 * @return the selected index
	 */
	public int getSelectedIndex();

	/**
	 * Sets the selected index.
	 *
	 * @param selectedIndex
	 *            the new selected index
	 */
	public void setSelectedIndex(int selectedIndex);

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
