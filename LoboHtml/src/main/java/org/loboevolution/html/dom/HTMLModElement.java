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
 * Notice of modification to part of a document. See the INS and DEL element
 * definitions in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLModElement extends HTMLElement {
	/**
	 * A URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating a document that describes the reason for the change. See the cite
	 * attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getCite();

	/**
	 * The date and time of the change. See the datetime attribute definition in
	 * HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getDateTime();

	/**
	 * A URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating a document that describes the reason for the change. See the cite
	 * attribute definition in HTML 4.01.
	 *
	 * @param cite a {@link java.lang.String} object.
	 */
	void setCite(String cite);

	/**
	 * The date and time of the change. See the datetime attribute definition in
	 * HTML 4.01.
	 *
	 * @param dateTime a {@link java.lang.String} object.
	 */
	void setDateTime(String dateTime);

}
