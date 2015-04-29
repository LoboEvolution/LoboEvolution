/*
 * GNU LESSER GENERAL LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Copyright (c) 2003 World Wide Web Consortium, (Massachusetts Institute of
 * Technology, Institut National de Recherche en Informatique et en Automatique,
 * Keio University). All Rights Reserved. This program is distributed under the
 * W3C's Software Intellectual Property License. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * W3C License http://www.w3.org/Consortium/Legal/ for more details.
 */

package org.lobobrowser.html.w3c;

import org.w3c.dom.Document;

/**
 * Inline subwindows. See the IFRAME element definition in HTML 4.01.
 * <p>
 * See also the Object Model (DOM) Level 2 HTML Specification
 * </p>
 * .
 */
public interface HTMLIFrameElement extends HTMLElement {

    /**
     * Aligns this object (vertically or horizontally) with respect to its
     * surrounding text. See the align attribute definition in HTML 4.01. This
     * attribute is deprecated in HTML 4.01.
     *
     * @return the align
     */
    String getAlign();

    /**
     * Aligns this object (vertically or horizontally) with respect to its
     * surrounding text. See the align attribute definition in HTML 4.01. This
     * attribute is deprecated in HTML 4.01.
     *
     * @param align
     *            the new align
     */
    void setAlign(String align);

    /**
     * Request frame borders. See the frameborder attribute definition in HTML
     * 4.01.
     *
     * @return the frame border
     */
    String getFrameBorder();

    /**
     * Request frame borders. See the frameborder attribute definition in HTML
     * 4.01.
     *
     * @param frameBorder
     *            the new frame border
     */
    void setFrameBorder(String frameBorder);

    /**
     * Frame height. See the height attribute definition in HTML 4.01.
     *
     * @return the height
     */
    String getHeight();

    /**
     * Frame height. See the height attribute definition in HTML 4.01.
     *
     * @param height
     *            the new height
     */
    void setHeight(String height);

    /**
     * URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
     * designating a long description of this image or frame. See the longdesc
     * attribute definition in HTML 4.01.
     *
     * @return the long desc
     */
    String getLongDesc();

    /**
     * URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
     * designating a long description of this image or frame. See the longdesc
     * attribute definition in HTML 4.01.
     *
     * @param longDesc
     *            the new long desc
     */
    void setLongDesc(String longDesc);

    /**
     * Frame margin height, in pixels. See the marginheight attribute definition
     * in HTML 4.01.
     *
     * @return the margin height
     */
    String getMarginHeight();

    /**
     * Frame margin height, in pixels. See the marginheight attribute definition
     * in HTML 4.01.
     *
     * @param marginHeight
     *            the new margin height
     */
    void setMarginHeight(String marginHeight);

    /**
     * Frame margin width, in pixels. See the marginwidth attribute definition
     * in HTML 4.01.
     *
     * @return the margin width
     */
    String getMarginWidth();

    /**
     * Frame margin width, in pixels. See the marginwidth attribute definition
     * in HTML 4.01.
     *
     * @param marginWidth
     *            the new margin width
     */
    void setMarginWidth(String marginWidth);

    /**
     * The frame name (object of the <code>target</code> attribute). See the
     * name attribute definition in HTML 4.01.
     *
     * @return the name
     */
    String getName();

    /**
     * The frame name (object of the <code>target</code> attribute). See the
     * name attribute definition in HTML 4.01.
     *
     * @param name
     *            the new name
     */
    void setName(String name);

    /**
     * Specify whether or not the frame should have scrollbars. See the
     * scrolling attribute definition in HTML 4.01.
     *
     * @return the scrolling
     */
    String getScrolling();

    /**
     * Specify whether or not the frame should have scrollbars. See the
     * scrolling attribute definition in HTML 4.01.
     *
     * @param scrolling
     *            the new scrolling
     */
    void setScrolling(String scrolling);

    /**
     * A URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
     * designating the initial frame contents. See the src attribute definition
     * in HTML 4.01.
     *
     * @return the src
     */
    String getSrc();

    /**
     * A URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
     * designating the initial frame contents. See the src attribute definition
     * in HTML 4.01.
     *
     * @param src
     *            the new src
     */
    void setSrc(String src);

    /**
     * Frame width. See the width attribute definition in HTML 4.01.
     *
     * @return the width
     */
    String getWidth();

    /**
     * Frame width. See the width attribute definition in HTML 4.01.
     *
     * @param width
     *            the new width
     */
    void setWidth(String width);

    /**
     * The document this frame contains, if there is any and it is available, or
     * <code>null</code> otherwise.
     *
     * @return the content document
     * @since DOM Level 2
     */
    Document getContentDocument();

    /*
     * String getSrc(); void setSrc(String src); String
     * getSrcdoc(); void setSrcdoc(String srcdoc); String getName();
     * void setName(String name); DOMSettableTokenList getSandbox();
     * void setSandbox(String sandbox); boolean getSeamless(); public
     * void setSeamless(boolean seamless); String getWidth(); void
     * setWidth(String width); String getHeight(); void
     * setHeight(String height); Document getContentDocument(); Window
     * getContentWindow(); // HTMLIFrameElement-17 String getAlign(); public
     * void setAlign(String align); String getFrameBorder(); void
     * setFrameBorder(String frameBorder); String getLongDesc(); void
     * setLongDesc(String longDesc); String getMarginHeight(); void
     * setMarginHeight(String marginHeight); String getMarginWidth(); public
     * void setMarginWidth(String marginWidth); String getScrolling(); public
     * void setScrolling(String scrolling);
     */

}
