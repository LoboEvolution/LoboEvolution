/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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

/**
 * The Interface HTMLFormElement.
 */
public interface HTMLFormElement extends HTMLElement {

	/**
	 * Gets the accept charset.
	 *
	 * @return the accept charset
	 */
	// HTMLFormElement
	public String getAcceptCharset();

	/**
	 * Sets the accept charset.
	 *
	 * @param acceptCharset
	 *            the new accept charset
	 */
	public void setAcceptCharset(String acceptCharset);

	/**
	 * Gets the action.
	 *
	 * @return the action
	 */
	public String getAction();

	/**
	 * Sets the action.
	 *
	 * @param action
	 *            the new action
	 */
	public void setAction(String action);

	/**
	 * Gets the autocomplete.
	 *
	 * @return the autocomplete
	 */
	public boolean getAutocomplete();

	/**
	 * Sets the autocomplete.
	 *
	 * @param autocomplete
	 *            the new autocomplete
	 */
	public void setAutocomplete(String autocomplete);

	/**
	 * Gets the enctype.
	 *
	 * @return the enctype
	 */
	public String getEnctype();

	/**
	 * Sets the enctype.
	 *
	 * @param enctype
	 *            the new enctype
	 */
	public void setEnctype(String enctype);

	/**
	 * Gets the encoding.
	 *
	 * @return the encoding
	 */
	public String getEncoding();

	/**
	 * Sets the encoding.
	 *
	 * @param encoding
	 *            the new encoding
	 */
	public void setEncoding(String encoding);

	/**
	 * Gets the method.
	 *
	 * @return the method
	 */
	public String getMethod();

	/**
	 * Sets the method.
	 *
	 * @param method
	 *            the new method
	 */
	public void setMethod(String method);

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
	 * Gets the no validate.
	 *
	 * @return the no validate
	 */
	public boolean getNoValidate();

	/**
	 * Sets the no validate.
	 *
	 * @param noValidate
	 *            the new no validate
	 */
	public void setNoValidate(boolean noValidate);

	/**
	 * Gets the target.
	 *
	 * @return the target
	 */
	public String getTarget();

	/**
	 * Sets the target.
	 *
	 * @param target
	 *            the new target
	 */
	public void setTarget(String target);

	/**
	 * Gets the elements.
	 *
	 * @return the elements
	 */
	public HTMLCollection getElements();

	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public int getLength();

	/**
	 * Gets the element.
	 *
	 * @param index
	 *            the index
	 * @return the element
	 */
	public Object getElement(int index);

	/**
	 * Gets the element.
	 *
	 * @param name
	 *            the name
	 * @return the element
	 */
	public Object getElement(String name);

	/**
	 * Submit.
	 */
	public void submit();

	/**
	 * Reset.
	 */
	public void reset();

	/**
	 * Check validity.
	 *
	 * @return true, if successful
	 */
	public boolean checkValidity();

	/**
	 * Dispatch form input.
	 */
	void dispatchFormInput();

	/**
	 * Sets the autocomplete.
	 *
	 * @param autocomplete
	 *            the new autocomplete
	 */
	void setAutocomplete(boolean autocomplete);

	/**
	 * Named item.
	 *
	 * @param name
	 *            the name
	 * @return the object
	 */
	Object namedItem(String name);

	/**
	 * Item.
	 *
	 * @param index
	 *            the index
	 * @return the object
	 */
	Object item(int index);
}
