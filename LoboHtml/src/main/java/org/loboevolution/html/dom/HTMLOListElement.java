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
 * Ordered list. See the OL element definition in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLOListElement extends HTMLElement {
	/**
	 * Reduce spacing between list items. See the compact attribute definition in
	 * HTML 4.01. This attribute is deprecated in HTML 4.01.
	 *
	 * @return a boolean.
	 */
	boolean getCompact();

	/**
	 * Starting sequence number. See the start attribute definition in HTML 4.01.
	 * This attribute is deprecated in HTML 4.01.
	 *
	 * @return a int.
	 */
	int getStart();

	/**
	 * Numbering style. See the type attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getType();

	/**
	 * Reduce spacing between list items. See the compact attribute definition in
	 * HTML 4.01. This attribute is deprecated in HTML 4.01.
	 *
	 * @param compact a boolean.
	 */
	void setCompact(boolean compact);

	/**
	 * Starting sequence number. See the start attribute definition in HTML 4.01.
	 * This attribute is deprecated in HTML 4.01.
	 *
	 * @param start a int.
	 */
	void setStart(int start);

	/**
	 * Numbering style. See the type attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @param type a {@link java.lang.String} object.
	 */
	void setType(String type);

}
