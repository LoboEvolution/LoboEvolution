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

import org.w3c.dom.DOMException;

/**
 * The select element allows the selection of an option. The contained options
 * can be directly accessed through the select element as a collection. See the
 * SELECT element definition in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLSelectElement extends HTMLElement {
	/**
	 * Add a new element to the collection of OPTION elements for this
	 * SELECT. This method is the equivalent of the
	 * appendChild method of the Node interface if the
	 * before parameter is null. It is equivalent to the
	 * insertBefore method on the parent of before in all
	 * other cases. This method may have no effect if the new element is not an
	 * OPTION or an OPTGROUP.
	 *
	 * @param element The element to add.
	 * @param before  The element to insert before, or null for the
	 *                tail of the list.
	 * @exception DOMException NOT_FOUND_ERR: Raised if before is not a
	 *                         descendant of the SELECT element.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void add(HTMLElement element, HTMLElement before) throws DOMException;

	/**
	 * Removes keyboard focus from this element.
	 */
	void blur();

	/**
	 * Gives keyboard focus to this element.
	 */
	void focus();

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
	 * The number of options in this SELECT.
	 *
	 * @version DOM Level 2
	 * @return a int.
	 */
	int getLength();

	/**
	 * If true, multiple OPTION elements may be selected in this
	 * SELECT. See the multiple attribute definition in HTML 4.01.
	 *
	 * @return a boolean.
	 */
	boolean getMultiple();

	/**
	 * Form control or object name when submitted with a form. See the name
	 * attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getName();

	/**
	 * The collection of OPTION elements contained by this element.
	 *
	 * @version DOM Level 2
	 * @return a {@link org.loboevolution.html.dom.HTMLOptionsCollection} object.
	 */
	HTMLOptionsCollection getOptions();

	/**
	 * The ordinal index of the selected option, starting from 0. The value -1 is
	 * returned if no element is selected. If multiple options are selected, the
	 * index of the first selected option is returned.
	 *
	 * @return a int.
	 */
	int getSelectedIndex();

	/**
	 * Number of visible rows. See the size attribute definition in HTML 4.01.
	 *
	 * @return a int.
	 */
	int getSize();

	/**
	 * Index that represents the element's position in the tabbing order. See the
	 * tabindex attribute definition in HTML 4.01.
	 *
	 * @return a int.
	 */
	int getTabIndex();

	/**
	 * The type of this form control. This is the string "select-multiple" when the
	 * multiple attribute is true and the string "select-one" when
	 * false.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getType();

	/**
	 * The current form control value (i.e. the value of the currently selected
	 * option), if multiple options are selected this is the value of the first
	 * selected option.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getValue();

	/**
	 * Remove an element from the collection of OPTION elements for
	 * this SELECT. Does nothing if no element has the given index.
	 *
	 * @param index The index of the item to remove, starting from 0.
	 */
	void remove(int index);

	/**
	 * The control is unavailable in this context. See the disabled attribute
	 * definition in HTML 4.01.
	 *
	 * @param disabled a boolean.
	 */
	void setDisabled(boolean disabled);

	/**
	 * The number of options in this SELECT.
	 *
	 * @exception DOMException NOT_SUPPORTED_ERR: if setting the length is not
	 *                         allowed by the implementation.
	 * @version DOM Level 2
	 * @param length a int.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setLength(int length) throws DOMException;

	/**
	 * If true, multiple OPTION elements may be selected in this
	 * SELECT. See the multiple attribute definition in HTML 4.01.
	 *
	 * @param multiple a boolean.
	 */
	void setMultiple(boolean multiple);

	/**
	 * Form control or object name when submitted with a form. See the name
	 * attribute definition in HTML 4.01.
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	void setName(String name);

	/**
	 * The ordinal index of the selected option, starting from 0. The value -1 is
	 * returned if no element is selected. If multiple options are selected, the
	 * index of the first selected option is returned.
	 *
	 * @param selectedIndex a int.
	 */
	void setSelectedIndex(int selectedIndex);

	/**
	 * Index that represents the element's position in the tabbing order. See the
	 * tabindex attribute definition in HTML 4.01.
	 *
	 * @param tabIndex a int.
	 */
	void setTabIndex(int tabIndex);

	/**
	 * The current form control value (i.e. the value of the currently selected
	 * option), if multiple options are selected this is the value of the first
	 * selected option.
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	void setValue(String value);

}
