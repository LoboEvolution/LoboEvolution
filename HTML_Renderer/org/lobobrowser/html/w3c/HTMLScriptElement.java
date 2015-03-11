/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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

package org.lobobrowser.html.w3c;


/**
 * Script statements. See the SCRIPT element definition in HTML 4.01.
 * <p>
 * See also the
 * Object Model (DOM) Level 2 HTML Specification</p>.
 */
public interface HTMLScriptElement extends HTMLElement {
	
	/**
	 * The script content of the element.
	 *
	 * @return the text
	 */
	public String getText();

	/**
	 * The script content of the element.
	 *
	 * @param text the new text
	 */
	public void setText(String text);

	/**
	 * Reserved for future use.
	 *
	 * @return the html for
	 */
	public String getHtmlFor();

	/**
	 * Reserved for future use.
	 *
	 * @param htmlFor the new html for
	 */
	public void setHtmlFor(String htmlFor);

	/**
	 * Reserved for future use.
	 *
	 * @return the event
	 */
	public String getEvent();

	/**
	 * Reserved for future use.
	 *
	 * @param event the new event
	 */
	public void setEvent(String event);

	/**
	 * The character encoding of the linked resource. See the charset attribute
	 * definition in HTML 4.01.
	 *
	 * @return the charset
	 */
	public String getCharset();

	/**
	 * The character encoding of the linked resource. See the charset attribute
	 * definition in HTML 4.01.
	 *
	 * @param charset the new charset
	 */
	public void setCharset(String charset);

	/**
	 * Indicates that the user agent can defer processing of the script. See the
	 * defer attribute definition in HTML 4.01.
	 *
	 * @return the defer
	 */
	public boolean getDefer();

	/**
	 * Indicates that the user agent can defer processing of the script. See the
	 * defer attribute definition in HTML 4.01.
	 *
	 * @param defer the new defer
	 */
	public void setDefer(boolean defer);

	/**
	 * URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating an external script. See the src attribute definition in HTML
	 * 4.01.
	 *
	 * @return the src
	 */
	public String getSrc();

	/**
	 * URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating an external script. See the src attribute definition in HTML
	 * 4.01.
	 *
	 * @param src the new src
	 */
	public void setSrc(String src);

	/**
	 * The content type of the script language. See the type attribute
	 * definition in HTML 4.01.
	 *
	 * @return the type
	 */
	public String getType();

	/**
	 * The content type of the script language. See the type attribute
	 * definition in HTML 4.01.
	 *
	 * @param type the new type
	 */
	public void setType(String type);
}
