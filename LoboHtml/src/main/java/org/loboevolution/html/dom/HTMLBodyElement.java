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
 * The HTML document body. This element is always present in the DOM API, even
 * if the tags are not present in the source document. See the BODY element
 * definition in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLBodyElement extends HTMLElement {
	/**
	 * Color of active links (after mouse-button down, but before mouse-button up).
	 * See the alink attribute definition in HTML 4.01. This attribute is deprecated
	 * in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getALink();

	/**
	 * URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>] of the
	 * background texture tile image. See the background attribute definition in
	 * HTML 4.01. This attribute is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getBackground();

	/**
	 * Document background color. See the bgcolor attribute definition in HTML 4.01.
	 * This attribute is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getBgColor();

	/**
	 * Color of links that are not active and unvisited. See the link attribute
	 * definition in HTML 4.01. This attribute is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getLink();

	/**
	 * Document text color. See the text attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getText();

	/**
	 * Color of links that have been visited by the user. See the vlink attribute
	 * definition in HTML 4.01. This attribute is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getVLink();

	/**
	 * Color of active links (after mouse-button down, but before mouse-button up).
	 * See the alink attribute definition in HTML 4.01. This attribute is deprecated
	 * in HTML 4.01.
	 *
	 * @param aLink a {@link java.lang.String} object.
	 */
	void setALink(String aLink);

	/**
	 * URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>] of the
	 * background texture tile image. See the background attribute definition in
	 * HTML 4.01. This attribute is deprecated in HTML 4.01.
	 *
	 * @param background a {@link java.lang.String} object.
	 */
	void setBackground(String background);

	/**
	 * Document background color. See the bgcolor attribute definition in HTML 4.01.
	 * This attribute is deprecated in HTML 4.01.
	 *
	 * @param bgColor a {@link java.lang.String} object.
	 */
	void setBgColor(String bgColor);

	/**
	 * Color of links that are not active and unvisited. See the link attribute
	 * definition in HTML 4.01. This attribute is deprecated in HTML 4.01.
	 *
	 * @param link a {@link java.lang.String} object.
	 */
	void setLink(String link);

	/**
	 * Document text color. See the text attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @param text a {@link java.lang.String} object.
	 */
	void setText(String text);

	/**
	 * Color of links that have been visited by the user. See the vlink attribute
	 * definition in HTML 4.01. This attribute is deprecated in HTML 4.01.
	 *
	 * @param vLink a {@link java.lang.String} object.
	 */
	void setVLink(String vLink);

}
