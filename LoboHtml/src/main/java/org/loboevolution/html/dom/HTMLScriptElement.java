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
 * Script statements. See the SCRIPT element definition in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLScriptElement extends HTMLElement {
	/**
	 * The character encoding of the linked resource. See the charset attribute
	 * definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getCharset();

	/**
	 * Indicates that the user agent can defer processing of the script. See the
	 * defer attribute definition in HTML 4.01.
	 *
	 * @return a boolean.
	 */
	boolean getDefer();

	/**
	 * Reserved for future use.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getEvent();

	/**
	 * Reserved for future use.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getHtmlFor();

	/**
	 * URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating an external script. See the src attribute definition in HTML
	 * 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getSrc();

	/**
	 * The script content of the element.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getText();

	/**
	 * The content type of the script language. See the type attribute definition in
	 * HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getType();

	/**
	 * The character encoding of the linked resource. See the charset attribute
	 * definition in HTML 4.01.
	 *
	 * @param charset a {@link java.lang.String} object.
	 */
	void setCharset(String charset);

	/**
	 * Indicates that the user agent can defer processing of the script. See the
	 * defer attribute definition in HTML 4.01.
	 *
	 * @param defer a boolean.
	 */
	void setDefer(boolean defer);

	/**
	 * Reserved for future use.
	 *
	 * @param event a {@link java.lang.String} object.
	 */
	void setEvent(String event);

	/**
	 * Reserved for future use.
	 *
	 * @param htmlFor a {@link java.lang.String} object.
	 */
	void setHtmlFor(String htmlFor);

	/**
	 * URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating an external script. See the src attribute definition in HTML
	 * 4.01.
	 *
	 * @param src a {@link java.lang.String} object.
	 */
	void setSrc(String src);

	/**
	 * The script content of the element.
	 *
	 * @param text a {@link java.lang.String} object.
	 */
	void setText(String text);

	/**
	 * The content type of the script language. See the type attribute definition in
	 * HTML 4.01.
	 *
	 * @param type a {@link java.lang.String} object.
	 */
	void setType(String type);

}
