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

import org.w3c.dom.DOMException;

/**
 * The create* and delete* methods on the table allow authors to construct and
 * modify tables. [<a href='http://www.w3.org/TR/1999/REC-html401-19991224'>HTML
 * 4.01</a>] specifies that only one of each of the <code>CAPTION</code>,
 * <code>THEAD</code>, and <code>TFOOT</code> elements may exist in a table.
 * Therefore, if one exists, and the createTHead() or createTFoot() method is
 * called, the method returns the existing THead or TFoot element. See the TABLE
 * element definition in HTML 4.01.
 * <p>
 * See also the Object Model (DOM) Level 2 HTML Specification
 * </p>
 * .
 */
public interface HTMLTableElement extends HTMLElement {

    /**
     * Returns the table's <code>CAPTION</code>, or void if none exists.
     *
     * @version DOM Level 2
     * @return the caption
     */
    HTMLTableCaptionElement getCaption();

    /**
     * Returns the table's <code>CAPTION</code>, or void if none exists.
     *
     * @version DOM Level 2
     * @param caption
     *            the new caption
     * @exception DOMException
     *                HIERARCHY_REQUEST_ERR: if the element is not a
     *                <code>CAPTION</code>.
     */
    void setCaption(HTMLTableCaptionElement caption) throws DOMException;

    /**
     * Returns the table's <code>THEAD</code>, or <code>null</code> if none
     * exists.
     *
     * @version DOM Level 2
     * @return the t head
     */
    HTMLTableSectionElement getTHead();

    /**
     * Returns the table's <code>THEAD</code>, or <code>null</code> if none
     * exists.
     *
     * @version DOM Level 2
     * @param tHead
     *            the new t head
     * @exception DOMException
     *                HIERARCHY_REQUEST_ERR: if the element is not a
     *                <code>THEAD</code>.
     */
    void setTHead(HTMLTableSectionElement tHead) throws DOMException;

    /**
     * Returns the table's <code>TFOOT</code>, or <code>null</code> if none
     * exists.
     *
     * @version DOM Level 2
     * @return the t foot
     */
    HTMLTableSectionElement getTFoot();

    /**
     * Returns the table's <code>TFOOT</code>, or <code>null</code> if none
     * exists.
     *
     * @version DOM Level 2
     * @param tFoot
     *            the new t foot
     * @exception DOMException
     *                HIERARCHY_REQUEST_ERR: if the element is not a
     *                <code>TFOOT</code>.
     */
    void setTFoot(HTMLTableSectionElement tFoot) throws DOMException;

    /**
     * Returns a collection of all the rows in the table, including all in
     * <code>THEAD</code>, <code>TFOOT</code>, all <code>TBODY</code> elements.
     *
     * @return the rows
     */
    HTMLCollection getRows();

    /**
     * Returns a collection of the table bodies (including implicit ones).
     *
     * @return the t bodies
     */
    HTMLCollection getTBodies();

    /**
     * Specifies the table's position with respect to the rest of the document.
     * See the align attribute definition in HTML 4.01. This attribute is
     * deprecated in HTML 4.01.
     *
     * @return the align
     */
    String getAlign();

    /**
     * Specifies the table's position with respect to the rest of the document.
     * See the align attribute definition in HTML 4.01. This attribute is
     * deprecated in HTML 4.01.
     *
     * @param align
     *            the new align
     */
    void setAlign(String align);

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
     * The width of the border around the table. See the border attribute
     * definition in HTML 4.01.
     *
     * @return the border
     */
    String getBorder();

    /**
     * The width of the border around the table. See the border attribute
     * definition in HTML 4.01.
     *
     * @param border
     *            the new border
     */
    void setBorder(String border);

    /**
     * Specifies the horizontal and vertical space between cell content and cell
     * borders. See the cellpadding attribute definition in HTML 4.01.
     *
     * @return the cell padding
     */
    String getCellPadding();

    /**
     * Specifies the horizontal and vertical space between cell content and cell
     * borders. See the cellpadding attribute definition in HTML 4.01.
     *
     * @param cellPadding
     *            the new cell padding
     */
    void setCellPadding(String cellPadding);

    /**
     * Specifies the horizontal and vertical separation between cells. See the
     * cellspacing attribute definition in HTML 4.01.
     *
     * @return the cell spacing
     */
    String getCellSpacing();

    /**
     * Specifies the horizontal and vertical separation between cells. See the
     * cellspacing attribute definition in HTML 4.01.
     *
     * @param cellSpacing
     *            the new cell spacing
     */
    void setCellSpacing(String cellSpacing);

    /**
     * Specifies which external table borders to render. See the frame attribute
     * definition in HTML 4.01.
     *
     * @return the frame
     */
    String getFrame();

    /**
     * Specifies which external table borders to render. See the frame attribute
     * definition in HTML 4.01.
     *
     * @param frame
     *            the new frame
     */
    void setFrame(String frame);

    /**
     * Specifies which internal table borders to render. See the rules attribute
     * definition in HTML 4.01.
     *
     * @return the rules
     */
    String getRules();

    /**
     * Specifies which internal table borders to render. See the rules attribute
     * definition in HTML 4.01.
     *
     * @param rules
     *            the new rules
     */
    void setRules(String rules);

    /**
     * Description about the purpose or structure of a table. See the summary
     * attribute definition in HTML 4.01.
     *
     * @return the summary
     */
    String getSummary();

    /**
     * Description about the purpose or structure of a table. See the summary
     * attribute definition in HTML 4.01.
     *
     * @param summary
     *            the new summary
     */
    void setSummary(String summary);

    /**
     * Specifies the desired table width. See the width attribute definition in
     * HTML 4.01.
     *
     * @return the width
     */
    String getWidth();

    /**
     * Specifies the desired table width. See the width attribute definition in
     * HTML 4.01.
     *
     * @param width
     *            the new width
     */
    void setWidth(String width);

    /**
     * Create a table header row or return an existing one.
     *
     * @return A new table header element (<code>THEAD</code>).
     */
    HTMLElement createTHead();

    /**
     * Delete the header from the table, if one exists.
     */
    void deleteTHead();

    /**
     * Create a table footer row or return an existing one.
     *
     * @return A footer element (<code>TFOOT</code>).
     */
    HTMLElement createTFoot();

    /**
     * Delete the footer from the table, if one exists.
     */
    void deleteTFoot();

    /**
     * Create a new table caption object or return an existing one.
     *
     * @return A <code>CAPTION</code> element.
     */
    HTMLElement createCaption();

    /**
     * Delete the table caption, if one exists.
     */
    void deleteCaption();

    /**
     * Insert a new empty row in the table. The new row is inserted immediately
     * before and in the same section as the current <code>index</code>th row in
     * the table. If <code>index</code> is -1 or equal to the number of rows,
     * the new row is appended. In addition, when the table is empty the row is
     * inserted into a <code>TBODY</code> which is created and inserted into the
     * table.A table row cannot be empty according to [<a
     * href='http://www.w3.org/TR/1999/REC-html401-19991224'>HTML 4.01</a>].
     *
     * @version DOM Level 2
     * @param index
     *            The row number where to insert a new row. This index starts
     *            from 0 and is relative to the logical order (not document
     *            order) of all the rows contained inside the table.
     * @return The newly created row.
     * @exception DOMException
     *                INDEX_SIZE_ERR: Raised if the specified index is greater
     *                than the number of rows or if the index is a negative
     *                number other than -1.
     */
    HTMLElement insertRow(int index) throws DOMException;

    /**
     * Delete a table row.
     *
     * @version DOM Level 2
     * @param index
     *            The index of the row to be deleted. This index starts from 0
     *            and is relative to the logical order (not document order) of
     *            all the rows contained inside the table. If the index is -1
     *            the last row in the table is deleted.
     * @exception DOMException
     *                INDEX_SIZE_ERR: Raised if the specified index is greater
     *                than or equal to the number of rows or if the index is a
     *                negative number other than -1.
     */
    void deleteRow(int index) throws DOMException;

    /**
     * Insert row.
     *
     * @return the HTML element
     */
    HTMLElement insertRow();

}
