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
 * The object used to represent the <code>TH</code> and <code>TD</code>
 * elements. See the TD element definition in HTML 4.01.
 * <p>
 * See also the Object Model (DOM) Level 2 HTML Specification
 * </p>
 * .
 */
public interface HTMLTableCellElement extends HTMLElement {

    /**
     * The index of this cell in the row, starting from 0. This index is in
     * document tree order and not display order.
     *
     * @return the cell index
     */
    int getCellIndex();

    /**
     * Abbreviation for header cells. See the abbr attribute definition in HTML
     * 4.01.
     *
     * @return the abbr
     */
    String getAbbr();

    /**
     * Abbreviation for header cells. See the abbr attribute definition in HTML
     * 4.01.
     *
     * @param abbr
     *            the new abbr
     */
    void setAbbr(String abbr);

    /**
     * Horizontal alignment of data in cell. See the align attribute definition
     * in HTML 4.01.
     *
     * @return the align
     */
    String getAlign();

    /**
     * Horizontal alignment of data in cell. See the align attribute definition
     * in HTML 4.01.
     *
     * @param align
     *            the new align
     */
    void setAlign(String align);

    /**
     * Names group of related headers. See the axis attribute definition in HTML
     * 4.01.
     *
     * @return the axis
     */
    String getAxis();

    /**
     * Names group of related headers. See the axis attribute definition in HTML
     * 4.01.
     *
     * @param axis
     *            the new axis
     */
    void setAxis(String axis);

    /**
     * Cell background color. See the bgcolor attribute definition in HTML 4.01.
     * This attribute is deprecated in HTML 4.01.
     *
     * @return the bg color
     */
    String getBgColor();

    /**
     * Cell background color. See the bgcolor attribute definition in HTML 4.01.
     * This attribute is deprecated in HTML 4.01.
     *
     * @param bgColor
     *            the new bg color
     */
    void setBgColor(String bgColor);

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
     * Number of columns spanned by cell. See the colspan attribute definition
     * in HTML 4.01.
     *
     * @return the col span
     */
    int getColSpan();

    /**
     * Number of columns spanned by cell. See the colspan attribute definition
     * in HTML 4.01.
     *
     * @param colSpan
     *            the new col span
     */
    void setColSpan(int colSpan);

    /**
     * List of <code>id</code> attribute values for header cells. See the
     * headers attribute definition in HTML 4.01.
     *
     * @return the headers
     */
    String getHeaders();

    /**
     * List of <code>id</code> attribute values for header cells. See the
     * headers attribute definition in HTML 4.01.
     *
     * @param headers
     *            the new headers
     */
    void setHeaders(String headers);

    /**
     * Cell height. See the height attribute definition in HTML 4.01. This
     * attribute is deprecated in HTML 4.01.
     *
     * @return the height
     */
    String getHeight();

    /**
     * Cell height. See the height attribute definition in HTML 4.01. This
     * attribute is deprecated in HTML 4.01.
     *
     * @param height
     *            the new height
     */
    void setHeight(String height);

    /**
     * Suppress word wrapping. See the nowrap attribute definition in HTML 4.01.
     * This attribute is deprecated in HTML 4.01.
     *
     * @return the no wrap
     */
    boolean getNoWrap();

    /**
     * Suppress word wrapping. See the nowrap attribute definition in HTML 4.01.
     * This attribute is deprecated in HTML 4.01.
     *
     * @param noWrap
     *            the new no wrap
     */
    void setNoWrap(boolean noWrap);

    /**
     * Number of rows spanned by cell. See the rowspan attribute definition in
     * HTML 4.01.
     *
     * @return the row span
     */
    int getRowSpan();

    /**
     * Number of rows spanned by cell. See the rowspan attribute definition in
     * HTML 4.01.
     *
     * @param rowSpan
     *            the new row span
     */
    void setRowSpan(int rowSpan);

    /**
     * Scope covered by header cells. See the scope attribute definition in HTML
     * 4.01.
     *
     * @return the scope
     */
    String getScope();

    /**
     * Scope covered by header cells. See the scope attribute definition in HTML
     * 4.01.
     *
     * @param scope
     *            the new scope
     */
    void setScope(String scope);

    /**
     * Vertical alignment of data in cell. See the valign attribute definition
     * in HTML 4.01.
     *
     * @return the v align
     */
    String getVAlign();

    /**
     * Vertical alignment of data in cell. See the valign attribute definition
     * in HTML 4.01.
     *
     * @param vAlign
     *            the new v align
     */
    void setVAlign(String vAlign);

    /**
     * Cell width. See the width attribute definition in HTML 4.01. This
     * attribute is deprecated in HTML 4.01.
     *
     * @return the width
     */
    String getWidth();

    /**
     * Cell width. See the width attribute definition in HTML 4.01. This
     * attribute is deprecated in HTML 4.01.
     *
     * @param width
     *            the new width
     */
    void setWidth(String width);

}
