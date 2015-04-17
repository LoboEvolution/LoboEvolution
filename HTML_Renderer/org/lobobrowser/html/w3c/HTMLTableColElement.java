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
 * Regroups the <code>COL</code> and <code>COLGROUP</code> elements. See the COL
 * element definition in HTML 4.01.
 * <p>
 * See also the Object Model (DOM) Level 2 HTML Specification
 * </p>
 * .
 */
public interface HTMLTableColElement extends HTMLElement {

    /**
     * Horizontal alignment of cell data in column. See the align attribute
     * definition in HTML 4.01.
     *
     * @return the align
     */
    String getAlign();

    /**
     * Horizontal alignment of cell data in column. See the align attribute
     * definition in HTML 4.01.
     *
     * @param align
     *            the new align
     */
    void setAlign(String align);

    /**
     * Alignment character for cells in a column. See the char attribute
     * definition in HTML 4.01.
     *
     * @return the ch
     */
    String getCh();

    /**
     * Alignment character for cells in a column. See the char attribute
     * definition in HTML 4.01.
     *
     * @param ch
     *            the new ch
     */
    void setCh(String ch);

    /**
     * Offset of alignment character. See the charoff attribute definition in
     * HTML 4.01.
     *
     * @return the ch off
     */
    String getChOff();

    /**
     * Offset of alignment character. See the charoff attribute definition in
     * HTML 4.01.
     *
     * @param chOff
     *            the new ch off
     */
    void setChOff(String chOff);

    /**
     * Indicates the number of columns in a group or affected by a grouping. See
     * the span attribute definition in HTML 4.01.
     *
     * @return the span
     */
    int getSpan();

    /**
     * Indicates the number of columns in a group or affected by a grouping. See
     * the span attribute definition in HTML 4.01.
     *
     * @param span
     *            the new span
     */
    void setSpan(int span);

    /**
     * Vertical alignment of cell data in column. See the valign attribute
     * definition in HTML 4.01.
     *
     * @return the v align
     */
    String getVAlign();

    /**
     * Vertical alignment of cell data in column. See the valign attribute
     * definition in HTML 4.01.
     *
     * @param vAlign
     *            the new v align
     */
    void setVAlign(String vAlign);

    /**
     * Default column width. See the width attribute definition in HTML 4.01.
     *
     * @return the width
     */
    String getWidth();

    /**
     * Default column width. See the width attribute definition in HTML 4.01.
     *
     * @param width
     *            the new width
     */
    void setWidth(String width);

}
