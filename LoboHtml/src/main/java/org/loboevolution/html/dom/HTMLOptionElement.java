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
