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
 * Document base URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC
 * 2396</a>]. See the BASE element definition in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLBaseElement extends HTMLElement {
	/**
	 * The base URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC
	 * 2396</a>]. See the href attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getHref();

	/**
	 * The default target frame. See the target attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getTarget();

	/**
	 * The base URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC
	 * 2396</a>]. See the href attribute definition in HTML 4.01.
	 *
	 * @param href a {@link java.lang.String} object.
	 */
	void setHref(String href);

	/**
	 * The default target frame. See the target attribute definition in HTML 4.01.
	 *
	 * @param target a {@link java.lang.String} object.
	 */
	void setTarget(String target);

}
