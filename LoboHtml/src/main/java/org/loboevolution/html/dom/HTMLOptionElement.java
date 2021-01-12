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
 * A selectable choice. See the OPTION element definition in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLOptionElement extends HTMLElement {
	/**
	 * Represents the value of the HTML selected attribute. The value of this
	 * attribute does not change if the state of the corresponding form control, in
	 * an interactive user agent, changes. See the selected attribute definition in
	 * HTML 4.01.
	 *
	 * @version DOM Level 2
	 * @return a boolean.
	 */
	boolean getDefaultSelected();

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
	 * The index of this OPTION in its parent SELECT ,
	 * starting from 0.
	 *
	 * @version DOM Level 2
	 * @return a int.
	 */
	int getIndex();

	/**
	 * Option label for use in hierarchical menus. See the label attribute
	 * definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getLabel();

	/**
	 * Represents the current state of the corresponding form control, in an
	 * interactive user agent. Changing this attribute changes the state of the form
	 * control, but does not change the value of the HTML selected attribute of the
	 * element.
	 *
	 * @return a boolean.
	 */
	boolean getSelected();

	/**
	 * The text contained within the option element.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getText();

	/**
	 * The current form control value. See the value attribute definition in HTML
	 * 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getValue();

	/**
	 * Represents the value of the HTML selected attribute. The value of this
	 * attribute does not change if the state of the corresponding form control, in
	 * an interactive user agent, changes. See the selected attribute definition in
	 * HTML 4.01.
	 *
	 * @version DOM Level 2
	 * @param defaultSelected a boolean.
	 */
	void setDefaultSelected(boolean defaultSelected);

	/**
	 * The control is unavailable in this context. See the disabled attribute
	 * definition in HTML 4.01.
	 *
	 * @param disabled a boolean.
	 */
	void setDisabled(boolean disabled);

	/**
	 * Option label for use in hierarchical menus. See the label attribute
	 * definition in HTML 4.01.
	 *
	 * @param label a {@link java.lang.String} object.
	 */
	void setLabel(String label);

	/**
	 * Represents the current state of the corresponding form control, in an
	 * interactive user agent. Changing this attribute changes the state of the form
	 * control, but does not change the value of the HTML selected attribute of the
	 * element.
	 *
	 * @param selected a boolean.
	 */
	void setSelected(boolean selected);

	/**
	 * The current form control value. See the value attribute definition in HTML
	 * 4.01.
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	void setValue(String value);

}
