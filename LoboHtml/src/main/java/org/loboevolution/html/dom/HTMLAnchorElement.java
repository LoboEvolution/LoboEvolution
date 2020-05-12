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
 * The anchor element. See the A element definition in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLAnchorElement extends HTMLElement {
	/**
	 * Removes keyboard focus from this element.
	 */
	void blur();

	/**
	 * Gives keyboard focus to this element.
	 */
	void focus();

	/**
	 * A single character access key to give access to the form control. See the
	 * accesskey attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAccessKey();

	/**
	 * The character encoding of the linked resource. See the charset attribute
	 * definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getCharset();

	/**
	 * Comma-separated list of lengths, defining an active region geometry. See also
	 * shape for the shape of the region. See the coords attribute
	 * definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getCoords();

	/**
	 * The absolute URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC
	 * 2396</a>] of the linked resource. See the href attribute definition in HTML
	 * 4.01.
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
	 * Anchor name. See the name attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getName();

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
	 * The shape of the active area. The coordinates are given by
	 * coords. See the shape attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getShape();

	/**
	 * Index that represents the element's position in the tabbing order. See the
	 * tabindex attribute definition in HTML 4.01.
	 *
	 * @return a int.
	 */
	int getTabIndex();

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
	 * A single character access key to give access to the form control. See the
	 * accesskey attribute definition in HTML 4.01.
	 *
	 * @param accessKey a {@link java.lang.String} object.
	 */
	void setAccessKey(String accessKey);

	/**
	 * The character encoding of the linked resource. See the charset attribute
	 * definition in HTML 4.01.
	 *
	 * @param charset a {@link java.lang.String} object.
	 */
	void setCharset(String charset);

	/**
	 * Comma-separated list of lengths, defining an active region geometry. See also
	 * shape for the shape of the region. See the coords attribute
	 * definition in HTML 4.01.
	 *
	 * @param coords a {@link java.lang.String} object.
	 */
	void setCoords(String coords);

	/**
	 * The absolute URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC
	 * 2396</a>] of the linked resource. See the href attribute definition in HTML
	 * 4.01.
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
	 * Anchor name. See the name attribute definition in HTML 4.01.
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	void setName(String name);

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
	 * The shape of the active area. The coordinates are given by
	 * coords. See the shape attribute definition in HTML 4.01.
	 *
	 * @param shape a {@link java.lang.String} object.
	 */
	void setShape(String shape);

	/**
	 * Index that represents the element's position in the tabbing order. See the
	 * tabindex attribute definition in HTML 4.01.
	 *
	 * @param tabIndex a int.
	 */
	void setTabIndex(int tabIndex);

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
