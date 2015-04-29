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

/**
 * Embedded image. See the IMG element definition in HTML 4.01.
 * <p>
 * See also the Object Model (DOM) Level 2 HTML Specification
 * </p>
 * .
 */
public interface HTMLImageElement extends HTMLElement {

    /**
     * The name of the element (for backwards compatibility).
     *
     * @return the name
     */
    String getName();

    /**
     * The name of the element (for backwards compatibility).
     *
     * @param name
     *            the new name
     */
    void setName(String name);

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
     * Alternate text for user agents not rendering the normal content of this
     * element. See the alt attribute definition in HTML 4.01.
     *
     * @return the alt
     */
    String getAlt();

    /**
     * Alternate text for user agents not rendering the normal content of this
     * element. See the alt attribute definition in HTML 4.01.
     *
     * @param alt
     *            the new alt
     */
    void setAlt(String alt);

    /**
     * Width of border around image. See the border attribute definition in HTML
     * 4.01. This attribute is deprecated in HTML 4.01. Note that the type of
     * this attribute was <code>DOMString</code> in DOM Level 1 HTML [<a
     * href='http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001'>DOM Level
     * 1</a>] .
     *
     * @return the border
     */
    String getBorder();

    /**
     * Width of border around image. See the border attribute definition in HTML
     * 4.01. This attribute is deprecated in HTML 4.01. Note that the type of
     * this attribute was <code>DOMString</code> in DOM Level 1 HTML [<a
     * href='http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001'>DOM Level
     * 1</a>] .
     *
     * @param border
     *            the new border
     */
    void setBorder(String border);

    /**
     * Height of the image in pixels. See the height attribute definition in
     * HTML 4.01. Note that the type of this attribute was
     * <code>DOMString</code> in DOM Level 1 HTML [<a
     * href='http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001'>DOM Level
     * 1</a>].
     *
     * @version DOM Level 2
     * @return the height
     */
    int getHeight();

    /**
     * Height of the image in pixels. See the height attribute definition in
     * HTML 4.01. Note that the type of this attribute was
     * <code>DOMString</code> in DOM Level 1 HTML [<a
     * href='http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001'>DOM Level
     * 1</a>].
     *
     * @version DOM Level 2
     * @param height
     *            the new height
     */
    void setHeight(int height);

    /**
     * Horizontal space to the left and right of this image in pixels. See the
     * hspace attribute definition in HTML 4.01. This attribute is deprecated in
     * HTML 4.01. Note that the type of this attribute was
     * <code>DOMString</code> in DOM Level 1 HTML [<a
     * href='http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001'>DOM Level
     * 1</a>].
     *
     * @version DOM Level 2
     * @return the hspace
     */
    int getHspace();

    /**
     * Horizontal space to the left and right of this image in pixels. See the
     * hspace attribute definition in HTML 4.01. This attribute is deprecated in
     * HTML 4.01. Note that the type of this attribute was
     * <code>DOMString</code> in DOM Level 1 HTML [<a
     * href='http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001'>DOM Level
     * 1</a>].
     *
     * @version DOM Level 2
     * @param hspace
     *            the new hspace
     */
    void setHspace(int hspace);

    /**
     * Use server-side image map. See the ismap attribute definition in HTML
     * 4.01.
     *
     * @return the checks if is map
     */
    boolean getIsMap();

    /**
     * Use server-side image map. See the ismap attribute definition in HTML
     * 4.01.
     *
     * @param isMap
     *            the new checks if is map
     */
    void setIsMap(boolean isMap);

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
     * URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
     * designating the source of this image. See the src attribute definition in
     * HTML 4.01.
     *
     * @return the src
     */
    String getSrc();

    /**
     * URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
     * designating the source of this image. See the src attribute definition in
     * HTML 4.01.
     *
     * @param src
     *            the new src
     */
    void setSrc(String src);

    /**
     * Use client-side image map. See the usemap attribute definition in HTML
     * 4.01.
     *
     * @return the use map
     */
    String getUseMap();

    /**
     * Use client-side image map. See the usemap attribute definition in HTML
     * 4.01.
     *
     * @param useMap
     *            the new use map
     */
    void setUseMap(String useMap);

    /**
     * Vertical space above and below this image in pixels. See the vspace
     * attribute definition in HTML 4.01. This attribute is deprecated in HTML
     * 4.01. Note that the type of this attribute was "DOMString" in DOM Level 1
     * HTML [<a href='http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001'>DOM
     * Level 1</a>].
     *
     * @version DOM Level 2
     * @return the vspace
     */
    int getVspace();

    /**
     * Vertical space above and below this image in pixels. See the vspace
     * attribute definition in HTML 4.01. This attribute is deprecated in HTML
     * 4.01. Note that the type of this attribute was "DOMString" in DOM Level 1
     * HTML [<a href='http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001'>DOM
     * Level 1</a>].
     *
     * @version DOM Level 2
     * @param vspace
     *            the new vspace
     */
    void setVspace(int vspace);

    /**
     * The width of the image in pixels. See the width attribute definition in
     * HTML 4.01. Note that the type of this attribute was
     * <code>DOMString</code> in DOM Level 1 HTML [<a
     * href='http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001'>DOM Level
     * 1</a>].
     *
     * @version DOM Level 2
     * @return the width
     */
    int getWidth();

    /**
     * The width of the image in pixels. See the width attribute definition in
     * HTML 4.01. Note that the type of this attribute was
     * <code>DOMString</code> in DOM Level 1 HTML [<a
     * href='http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001'>DOM Level
     * 1</a>].
     *
     * @version DOM Level 2
     * @param width
     *            the new width
     */
    void setWidth(int width);

    /**
     * Gets the natural width.
     *
     * @return the natural width
     */
    int getNaturalWidth();

    /**
     * Gets the natural height.
     *
     * @return the natural height
     */
    int getNaturalHeight();

    /**
     * Gets the complete.
     *
     * @return the complete
     */
    boolean getComplete();
}
