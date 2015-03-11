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
 * Document base URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC
 * 2396</a>]. See the BASE element definition in HTML 4.01.
 * <p>
 * See also the 
 * Object Model (DOM) Level 2 HTML Specification</p>.
 */
public interface HTMLBaseElement extends HTMLElement {
	
	/**
	 * The base URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC
	 * 2396</a>]. See the href attribute definition in HTML 4.01.
	 *
	 * @return the href
	 */
	public String getHref();

	/**
	 * The base URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC
	 * 2396</a>]. See the href attribute definition in HTML 4.01.
	 *
	 * @param href the new href
	 */
	public void setHref(String href);

	/**
	 * The default target frame. See the target attribute definition in HTML
	 * 4.01.
	 *
	 * @return the target
	 */
	public String getTarget();

	/**
	 * The default target frame. See the target attribute definition in HTML
	 * 4.01.
	 *
	 * @param target the new target
	 */
	public void setTarget(String target);
}
