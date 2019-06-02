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

import org.w3c.dom.Document;

/**
 * Inline subwindows. See the IFRAME element definition in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 */
public interface HTMLIFrameElement extends HTMLElement {
	/**
	 * Aligns this object (vertically or horizontally) with respect to its
	 * surrounding text. See the align attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 */
	String getAlign();

	/**
	 * The document this frame contains, if there is any and it is available, or
	 * <code>null</code> otherwise.
	 * 
	 * @since DOM Level 2
	 */
	Document getContentDocument();

	/**
	 * Request frame borders. See the frameborder attribute definition in HTML 4.01.
	 */
	String getFrameBorder();

	/**
	 * Frame height. See the height attribute definition in HTML 4.01.
	 */
	String getHeight();

	/**
	 * URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating a long description of this image or frame. See the longdesc
	 * attribute definition in HTML 4.01.
	 */
	String getLongDesc();

	/**
	 * Frame margin height, in pixels. See the marginheight attribute definition in
	 * HTML 4.01.
	 */
	String getMarginHeight();

	/**
	 * Frame margin width, in pixels. See the marginwidth attribute definition in
	 * HTML 4.01.
	 */
	String getMarginWidth();

	/**
	 * The frame name (object of the <code>target</code> attribute). See the name
	 * attribute definition in HTML 4.01.
	 */
	String getName();

	/**
	 * Specify whether or not the frame should have scrollbars. See the scrolling
	 * attribute definition in HTML 4.01.
	 */
	String getScrolling();

	/**
	 * A URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating the initial frame contents. See the src attribute definition in
	 * HTML 4.01.
	 */
	String getSrc();

	/**
	 * Frame width. See the width attribute definition in HTML 4.01.
	 */
	String getWidth();

	/**
	 * Aligns this object (vertically or horizontally) with respect to its
	 * surrounding text. See the align attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 */
	void setAlign(String align);

	/**
	 * Request frame borders. See the frameborder attribute definition in HTML 4.01.
	 */
	void setFrameBorder(String frameBorder);

	/**
	 * Frame height. See the height attribute definition in HTML 4.01.
	 */
	void setHeight(String height);

	/**
	 * URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating a long description of this image or frame. See the longdesc
	 * attribute definition in HTML 4.01.
	 */
	void setLongDesc(String longDesc);

	/**
	 * Frame margin height, in pixels. See the marginheight attribute definition in
	 * HTML 4.01.
	 */
	void setMarginHeight(String marginHeight);

	/**
	 * Frame margin width, in pixels. See the marginwidth attribute definition in
	 * HTML 4.01.
	 */
	void setMarginWidth(String marginWidth);

	/**
	 * The frame name (object of the <code>target</code> attribute). See the name
	 * attribute definition in HTML 4.01.
	 */
	void setName(String name);

	/**
	 * Specify whether or not the frame should have scrollbars. See the scrolling
	 * attribute definition in HTML 4.01.
	 */
	void setScrolling(String scrolling);

	/**
	 * A URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating the initial frame contents. See the src attribute definition in
	 * HTML 4.01.
	 */
	void setSrc(String src);

	/**
	 * Frame width. See the width attribute definition in HTML 4.01.
	 */
	void setWidth(String width);

}
