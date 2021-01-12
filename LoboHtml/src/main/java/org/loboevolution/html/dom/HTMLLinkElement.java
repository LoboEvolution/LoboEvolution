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
 * The LINK element specifies a link to an external resource, and
 * defines this document's relationship to that resource (or vice versa). See
 * the LINK element definition in HTML 4.01 (see also the LinkStyle
 * interface in the StyleSheet module
 * [<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>DOM Level
 * 2 Style Sheets and CSS</a>]).
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLLinkElement extends HTMLElement {
	/**
	 * The character encoding of the resource being linked to. See the charset
	 * attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getCharset();

	/**
	 * Enables/disables the link. This is currently only used for style sheet links,
	 * and may be used to activate or deactivate style sheets.
	 *
	 * @return a boolean.
	 */
	boolean getDisabled();

	/**
	 * The URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>] of
	 * the linked resource. See the href attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getHref();

	/**
	 * Language code of the linked resource. See the hreflang attribute definition
	 * in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getHreflang();

	/**
	 * Designed for use with one or more target media. See the media attribute
	 * definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getMedia();

	/**
	 * Forward link type. See the rel attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getRel();

	/**
	 * Reverse link type. See the rev attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getRev();

	/**
	 * Frame to render the resource in. See the target attribute definition in HTML
	 * 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getTarget();

	/**
	 * Advisory content type. See the type attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getType();

	/**
	 * The character encoding of the resource being linked to. See the charset
	 * attribute definition in HTML 4.01.
	 *
	 * @param charset a {@link java.lang.String} object.
	 */
	void setCharset(String charset);

	/**
	 * Enables/disables the link. This is currently only used for style sheet links,
	 * and may be used to activate or deactivate style sheets.
	 *
	 * @param disabled a boolean.
	 */
	void setDisabled(boolean disabled);

	/**
	 * The URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>] of
	 * the linked resource. See the href attribute definition in HTML 4.01.
	 *
	 * @param href a {@link java.lang.String} object.
	 */
	void setHref(String href);

	/**
	 * Language code of the linked resource. See the hreflang attribute definition
	 * in HTML 4.01.
	 *
	 * @param hreflang a {@link java.lang.String} object.
	 */
	void setHreflang(String hreflang);

	/**
	 * Designed for use with one or more target media. See the media attribute
	 * definition in HTML 4.01.
	 *
	 * @param media a {@link java.lang.String} object.
	 */
	void setMedia(String media);

	/**
	 * Forward link type. See the rel attribute definition in HTML 4.01.
	 *
	 * @param rel a {@link java.lang.String} object.
	 */
	void setRel(String rel);

	/**
	 * Reverse link type. See the rev attribute definition in HTML 4.01.
	 *
	 * @param rev a {@link java.lang.String} object.
	 */
	void setRev(String rev);

	/**
	 * Frame to render the resource in. See the target attribute definition in HTML
	 * 4.01.
	 *
	 * @param target a {@link java.lang.String} object.
	 */
	void setTarget(String target);

	/**
	 * Advisory content type. See the type attribute definition in HTML 4.01.
	 *
	 * @param type a {@link java.lang.String} object.
	 */
	void setType(String type);

}
