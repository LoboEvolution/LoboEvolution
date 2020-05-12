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

import org.w3c.dom.Document;

/**
 * Inline subwindows. See the IFRAME element definition in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLIFrameElement extends HTMLElement {
	/**
	 * Aligns this object (vertically or horizontally) with respect to its
	 * surrounding text. See the align attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAlign();

	/**
	 * The document this frame contains, if there is any and it is available, or
	 * null otherwise.
	 *
	 * @since DOM Level 2
	 * @return a {@link org.w3c.dom.Document} object.
	 */
	Document getContentDocument();

	/**
	 * Request frame borders. See the frameborder attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getFrameBorder();

	/**
	 * Frame height. See the height attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getHeight();

	/**
	 * URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating a long description of this image or frame. See the longdesc
	 * attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getLongDesc();

	/**
	 * Frame margin height, in pixels. See the marginheight attribute definition in
	 * HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getMarginHeight();

	/**
	 * Frame margin width, in pixels. See the marginwidth attribute definition in
	 * HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getMarginWidth();

	/**
	 * The frame name (object of the target attribute). See the name
	 * attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getName();

	/**
	 * Specify whether or not the frame should have scrollbars. See the scrolling
	 * attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getScrolling();

	/**
	 * A URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating the initial frame contents. See the src attribute definition in
	 * HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getSrc();

	/**
	 * Frame width. See the width attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getWidth();

	/**
	 * Aligns this object (vertically or horizontally) with respect to its
	 * surrounding text. See the align attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @param align a {@link java.lang.String} object.
	 */
	void setAlign(String align);

	/**
	 * Request frame borders. See the frameborder attribute definition in HTML 4.01.
	 *
	 * @param frameBorder a {@link java.lang.String} object.
	 */
	void setFrameBorder(String frameBorder);

	/**
	 * Frame height. See the height attribute definition in HTML 4.01.
	 *
	 * @param height a {@link java.lang.String} object.
	 */
	void setHeight(String height);

	/**
	 * URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating a long description of this image or frame. See the longdesc
	 * attribute definition in HTML 4.01.
	 *
	 * @param longDesc a {@link java.lang.String} object.
	 */
	void setLongDesc(String longDesc);

	/**
	 * Frame margin height, in pixels. See the marginheight attribute definition in
	 * HTML 4.01.
	 *
	 * @param marginHeight a {@link java.lang.String} object.
	 */
	void setMarginHeight(String marginHeight);

	/**
	 * Frame margin width, in pixels. See the marginwidth attribute definition in
	 * HTML 4.01.
	 *
	 * @param marginWidth a {@link java.lang.String} object.
	 */
	void setMarginWidth(String marginWidth);

	/**
	 * The frame name (object of the target attribute). See the name
	 * attribute definition in HTML 4.01.
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	void setName(String name);

	/**
	 * Specify whether or not the frame should have scrollbars. See the scrolling
	 * attribute definition in HTML 4.01.
	 *
	 * @param scrolling a {@link java.lang.String} object.
	 */
	void setScrolling(String scrolling);

	/**
	 * A URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating the initial frame contents. See the src attribute definition in
	 * HTML 4.01.
	 *
	 * @param src a {@link java.lang.String} object.
	 */
	void setSrc(String src);

	/**
	 * Frame width. See the width attribute definition in HTML 4.01.
	 *
	 * @param width a {@link java.lang.String} object.
	 */
	void setWidth(String width);

}
