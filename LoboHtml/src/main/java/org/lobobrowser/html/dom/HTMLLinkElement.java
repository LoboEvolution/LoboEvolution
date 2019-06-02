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

package org.lobobrowser.html.dom;

/**
 * The <code>LINK</code> element specifies a link to an external resource, and
 * defines this document's relationship to that resource (or vice versa). See
 * the LINK element definition in HTML 4.01 (see also the <code>LinkStyle</code>
 * interface in the StyleSheet module
 * [<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>DOM Level
 * 2 Style Sheets and CSS</a>]).
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 */
public interface HTMLLinkElement extends HTMLElement {
	/**
	 * The character encoding of the resource being linked to. See the charset
	 * attribute definition in HTML 4.01.
	 */
	String getCharset();

	/**
	 * Enables/disables the link. This is currently only used for style sheet links,
	 * and may be used to activate or deactivate style sheets.
	 */
	boolean getDisabled();

	/**
	 * The URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>] of
	 * the linked resource. See the href attribute definition in HTML 4.01.
	 */
	String getHref();

	/**
	 * Language code of the linked resource. See the hreflang attribute definition
	 * in HTML 4.01.
	 */
	String getHreflang();

	/**
	 * Designed for use with one or more target media. See the media attribute
	 * definition in HTML 4.01.
	 */
	String getMedia();

	/**
	 * Forward link type. See the rel attribute definition in HTML 4.01.
	 */
	String getRel();

	/**
	 * Reverse link type. See the rev attribute definition in HTML 4.01.
	 */
	String getRev();

	/**
	 * Frame to render the resource in. See the target attribute definition in HTML
	 * 4.01.
	 */
	String getTarget();

	/**
	 * Advisory content type. See the type attribute definition in HTML 4.01.
	 */
	String getType();

	/**
	 * The character encoding of the resource being linked to. See the charset
	 * attribute definition in HTML 4.01.
	 */
	void setCharset(String charset);

	/**
	 * Enables/disables the link. This is currently only used for style sheet links,
	 * and may be used to activate or deactivate style sheets.
	 */
	void setDisabled(boolean disabled);

	/**
	 * The URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>] of
	 * the linked resource. See the href attribute definition in HTML 4.01.
	 */
	void setHref(String href);

	/**
	 * Language code of the linked resource. See the hreflang attribute definition
	 * in HTML 4.01.
	 */
	void setHreflang(String hreflang);

	/**
	 * Designed for use with one or more target media. See the media attribute
	 * definition in HTML 4.01.
	 */
	void setMedia(String media);

	/**
	 * Forward link type. See the rel attribute definition in HTML 4.01.
	 */
	void setRel(String rel);

	/**
	 * Reverse link type. See the rev attribute definition in HTML 4.01.
	 */
	void setRev(String rev);

	/**
	 * Frame to render the resource in. See the target attribute definition in HTML
	 * 4.01.
	 */
	void setTarget(String target);

	/**
	 * Advisory content type. See the type attribute definition in HTML 4.01.
	 */
	void setType(String type);

}
