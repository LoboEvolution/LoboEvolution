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

import org.w3c.dom.Document;


/**
 * Create a frame. See the FRAME element definition in HTML 4.01.
 * <p>
 * See also the
 * Object Model (DOM) Level 2 HTML Specification</p>.
 */
public interface HTMLFrameElement extends HTMLElement {
	
	/**
	 * Request frame borders. See the frameborder attribute definition in HTML
	 * 4.01.
	 *
	 * @return the frame border
	 */
	public String getFrameBorder();

	/**
	 * Request frame borders. See the frameborder attribute definition in HTML
	 * 4.01.
	 *
	 * @param frameBorder the new frame border
	 */
	public void setFrameBorder(String frameBorder);

	/**
	 * URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating a long description of this image or frame. See the longdesc
	 * attribute definition in HTML 4.01.
	 *
	 * @return the long desc
	 */
	public String getLongDesc();

	/**
	 * URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating a long description of this image or frame. See the longdesc
	 * attribute definition in HTML 4.01.
	 *
	 * @param longDesc the new long desc
	 */
	public void setLongDesc(String longDesc);

	/**
	 * Frame margin height, in pixels. See the marginheight attribute definition
	 * in HTML 4.01.
	 *
	 * @return the margin height
	 */
	public String getMarginHeight();

	/**
	 * Frame margin height, in pixels. See the marginheight attribute definition
	 * in HTML 4.01.
	 *
	 * @param marginHeight the new margin height
	 */
	public void setMarginHeight(String marginHeight);

	/**
	 * Frame margin width, in pixels. See the marginwidth attribute definition
	 * in HTML 4.01.
	 *
	 * @return the margin width
	 */
	public String getMarginWidth();

	/**
	 * Frame margin width, in pixels. See the marginwidth attribute definition
	 * in HTML 4.01.
	 *
	 * @param marginWidth the new margin width
	 */
	public void setMarginWidth(String marginWidth);

	/**
	 * The frame name (object of the <code>target</code> attribute). See the
	 * name attribute definition in HTML 4.01.
	 *
	 * @return the name
	 */
	public String getName();

	/**
	 * The frame name (object of the <code>target</code> attribute). See the
	 * name attribute definition in HTML 4.01.
	 *
	 * @param name the new name
	 */
	public void setName(String name);

	/**
	 * When true, forbid user from resizing frame. See the noresize attribute
	 * definition in HTML 4.01.
	 *
	 * @return the no resize
	 */
	public boolean getNoResize();

	/**
	 * When true, forbid user from resizing frame. See the noresize attribute
	 * definition in HTML 4.01.
	 *
	 * @param noResize the new no resize
	 */
	public void setNoResize(boolean noResize);

	/**
	 * Specify whether or not the frame should have scrollbars. See the
	 * scrolling attribute definition in HTML 4.01.
	 *
	 * @return the scrolling
	 */
	public String getScrolling();

	/**
	 * Specify whether or not the frame should have scrollbars. See the
	 * scrolling attribute definition in HTML 4.01.
	 *
	 * @param scrolling the new scrolling
	 */
	public void setScrolling(String scrolling);

	/**
	 * A URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating the initial frame contents. See the src attribute definition
	 * in HTML 4.01.
	 *
	 * @return the src
	 */
	public String getSrc();

	/**
	 * A URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating the initial frame contents. See the src attribute definition
	 * in HTML 4.01.
	 *
	 * @param src the new src
	 */
	public void setSrc(String src);

	/**
	 * The document this frame contains, if there is any and it is available, or
	 * <code>null</code> otherwise.
	 *
	 * @return the content document
	 * @since DOM Level 2
	 */
	public Document getContentDocument();

}
