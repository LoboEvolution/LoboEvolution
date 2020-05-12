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
 * The FORM element encompasses behavior similar to a collection
 * and an element. It provides direct access to the contained form controls as
 * well as the attributes of the form element. See the FORM element definition
 * in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLFormElement extends HTMLElement {
	/**
	 * List of character sets supported by the server. See the accept-charset
	 * attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAcceptCharset();

	/**
	 * Server-side form handler. See the action attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAction();

	/**
	 * Returns a collection of all form control elements in the form.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getElements();

	/**
	 * The content type of the submitted form, generally
	 * "application/x-www-form-urlencoded". See the enctype attribute definition in
	 * HTML 4.01. The onsubmit even handler is not guaranteed to be triggered when
	 * invoking this method. The behavior is inconsistent for historical reasons and
	 * authors should not rely on a particular one.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getEnctype();

	/**
	 * The number of form controls in the form.
	 *
	 * @return a int.
	 */
	int getLength();

	/**
	 * HTTP method [<a href='http://www.ietf.org/rfc/rfc2616.txt'>IETF RFC 2616</a>]
	 * used to submit form. See the method attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getMethod();

	/**
	 * Names the form.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getName();

	/**
	 * Frame to render the resource in. See the target attribute definition in HTML
	 * 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getTarget();

	/**
	 * Restores a form element's default values. It performs the same action as a
	 * reset button.
	 */
	void reset();

	/**
	 * List of character sets supported by the server. See the accept-charset
	 * attribute definition in HTML 4.01.
	 *
	 * @param acceptCharset a {@link java.lang.String} object.
	 */
	void setAcceptCharset(String acceptCharset);

	/**
	 * Server-side form handler. See the action attribute definition in HTML 4.01.
	 *
	 * @param action a {@link java.lang.String} object.
	 */
	void setAction(String action);

	/**
	 * The content type of the submitted form, generally
	 * "application/x-www-form-urlencoded". See the enctype attribute definition in
	 * HTML 4.01. The onsubmit even handler is not guaranteed to be triggered when
	 * invoking this method. The behavior is inconsistent for historical reasons and
	 * authors should not rely on a particular one.
	 *
	 * @param enctype a {@link java.lang.String} object.
	 */
	void setEnctype(String enctype);

	/**
	 * HTTP method [<a href='http://www.ietf.org/rfc/rfc2616.txt'>IETF RFC 2616</a>]
	 * used to submit form. See the method attribute definition in HTML 4.01.
	 *
	 * @param method a {@link java.lang.String} object.
	 */
	void setMethod(String method);

	/**
	 * Names the form.
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	void setName(String name);

	/**
	 * Frame to render the resource in. See the target attribute definition in HTML
	 * 4.01.
	 *
	 * @param target a {@link java.lang.String} object.
	 */
	void setTarget(String target);

	/**
	 * Submits the form. It performs the same action as a submit button.
	 */
	void submit();

}
