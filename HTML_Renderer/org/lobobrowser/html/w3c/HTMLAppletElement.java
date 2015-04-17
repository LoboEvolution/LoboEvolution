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
 * An embedded Java applet. See the APPLET element definition in HTML 4.01.
 */
public interface HTMLAppletElement extends HTMLElement {

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
     * element. See the alt attribute definition in HTML 4.01. This attribute is
     * deprecated in HTML 4.01.
     *
     * @return the alt
     */
    String getAlt();

    /**
     * Alternate text for user agents not rendering the normal content of this
     * element. See the alt attribute definition in HTML 4.01. This attribute is
     * deprecated in HTML 4.01.
     *
     * @param alt
     *            the new alt
     */
    void setAlt(String alt);

    /**
     * Comma-separated archive list. See the archive attribute definition in
     * HTML 4.01. This attribute is deprecated in HTML 4.01.
     *
     * @return the archive
     */
    String getArchive();

    /**
     * Comma-separated archive list. See the archive attribute definition in
     * HTML 4.01. This attribute is deprecated in HTML 4.01.
     *
     * @param archive
     *            the new archive
     */
    void setArchive(String archive);

    /**
     * Applet class file. See the code attribute definition in HTML 4.01. This
     * attribute is deprecated in HTML 4.01.
     *
     * @return the code
     */
    String getCode();

    /**
     * Applet class file. See the code attribute definition in HTML 4.01. This
     * attribute is deprecated in HTML 4.01.
     *
     * @param code
     *            the new code
     */
    void setCode(String code);

    /**
     * Optional base URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC
     * 2396</a>] for applet. See the codebase attribute definition in HTML 4.01.
     * This attribute is deprecated in HTML 4.01.
     *
     * @return the code base
     */
    String getCodeBase();

    /**
     * Optional base URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC
     * 2396</a>] for applet. See the codebase attribute definition in HTML 4.01.
     * This attribute is deprecated in HTML 4.01.
     *
     * @param codeBase
     *            the new code base
     */
    void setCodeBase(String codeBase);

    /**
     * Override height. See the height attribute definition in HTML 4.01. This
     * attribute is deprecated in HTML 4.01.
     *
     * @return the height
     */
    String getHeight();

    /**
     * Override height. See the height attribute definition in HTML 4.01. This
     * attribute is deprecated in HTML 4.01.
     *
     * @param height
     *            the new height
     */
    void setHeight(String height);

    /**
     * Horizontal space, in pixels, to the left and right of this image, applet,
     * or object. See the hspace attribute definition in HTML 4.01. This
     * attribute is deprecated in HTML 4.01.
     *
     * @version DOM Level 2
     * @return the hspace
     */
    int getHspace();

    /**
     * Horizontal space, in pixels, to the left and right of this image, applet,
     * or object. See the hspace attribute definition in HTML 4.01. This
     * attribute is deprecated in HTML 4.01.
     *
     * @version DOM Level 2
     * @param hspace
     *            the new hspace
     */
    void setHspace(int hspace);

    /**
     * The name of the applet. See the name attribute definition in HTML 4.01.
     * This attribute is deprecated in HTML 4.01.
     *
     * @return the name
     */
    String getName();

    /**
     * The name of the applet. See the name attribute definition in HTML 4.01.
     * This attribute is deprecated in HTML 4.01.
     *
     * @param name
     *            the new name
     */
    void setName(String name);

    /**
     * The value of the "object" attribute. See the object attribute definition
     * in HTML 4.01. This attribute is deprecated in HTML 4.01.
     *
     * @version DOM Level 2
     * @return the object
     */
    String getObject();

    /**
     * The value of the "object" attribute. See the object attribute definition
     * in HTML 4.01. This attribute is deprecated in HTML 4.01.
     *
     * @version DOM Level 2
     * @param object
     *            the new object
     */
    void setObject(String object);

    /**
     * Vertical space, in pixels, above and below this image, applet, or object.
     * See the vspace attribute definition in HTML 4.01. This attribute is
     * deprecated in HTML 4.01.
     *
     * @version DOM Level 2
     * @return the vspace
     */
    int getVspace();

    /**
     * Vertical space, in pixels, above and below this image, applet, or object.
     * See the vspace attribute definition in HTML 4.01. This attribute is
     * deprecated in HTML 4.01.
     *
     * @version DOM Level 2
     * @param vspace
     *            the new vspace
     */
    void setVspace(int vspace);

    /**
     * Override width. See the width attribute definition in HTML 4.01. This
     * attribute is deprecated in HTML 4.01.
     *
     * @return the width
     */
    String getWidth();

    /**
     * Override width. See the width attribute definition in HTML 4.01. This
     * attribute is deprecated in HTML 4.01.
     *
     * @param width
     *            the new width
     */
    void setWidth(String width);

}
