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
 * Unordered list. See the UL element definition in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLUListElement extends HTMLElement {
	/**
	 * Reduce spacing between list items. See the compact attribute definition in
	 * HTML 4.01. This attribute is deprecated in HTML 4.01.
	 *
	 * @return a boolean.
	 */
	boolean getCompact();

	/**
	 * Bullet style. See the type attribute definition in HTML 4.01. This attribute
	 * is deprecated in HTML 4.01.
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
	 * Bullet style. See the type attribute definition in HTML 4.01. This attribute
	 * is deprecated in HTML 4.01.
	 *
	 * @param type a {@link java.lang.String} object.
	 */
	void setType(String type);

}
