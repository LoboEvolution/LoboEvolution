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
 * The Interface HTMLOptionElement_Constructor.
 */
public interface HTMLOptionElement_Constructor {

	/**
	 * Creates the instance.
	 *
	 * @return the HTML option element
	 */
	// Constructor
	public HTMLOptionElement createInstance();

	/**
	 * Creates the instance.
	 *
	 * @param text
	 *            the text
	 * @return the HTML option element
	 */
	public HTMLOptionElement createInstance(String text);

	/**
	 * Creates the instance.
	 *
	 * @param text
	 *            the text
	 * @param value
	 *            the value
	 * @return the HTML option element
	 */
	public HTMLOptionElement createInstance(String text, String value);

	/**
	 * Creates the instance.
	 *
	 * @param text
	 *            the text
	 * @param value
	 *            the value
	 * @param defaultSelected
	 *            the default selected
	 * @return the HTML option element
	 */
	public HTMLOptionElement createInstance(String text, String value, boolean defaultSelected);

	/**
	 * Creates the instance.
	 *
	 * @param text
	 *            the text
	 * @param value
	 *            the value
	 * @param defaultSelected
	 *            the default selected
	 * @param selected
	 *            the selected
	 * @return the HTML option element
	 */
	public HTMLOptionElement createInstance(String text, String value, boolean defaultSelected, boolean selected);
}
