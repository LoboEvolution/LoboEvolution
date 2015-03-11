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

import org.w3c.dom.DOMException;


/**
 * A row in a table. See the TR element definition in HTML 4.01.
 * <p>
 * See also the
 * Object Model (DOM) Level 2 HTML Specification</p>.
 */
public interface HTMLTableRowElement extends HTMLElement {
	
	/**
	 * This is in logical order and not in document order. The
	 * <code>rowIndex</code> does take into account sections (
	 * <code>THEAD</code>, <code>TFOOT</code>, or <code>TBODY</code>) within the
	 * table, placing <code>THEAD</code> rows first in the index, followed by
	 * <code>TBODY</code> rows, followed by <code>TFOOT</code> rows.
	 *
	 * @version DOM Level 2
	 * @return the row index
	 */
	public int getRowIndex();

	/**
	 * The index of this row, relative to the current section (
	 * <code>THEAD</code>, <code>TFOOT</code>, or <code>TBODY</code>), starting
	 * from 0.
	 *
	 * @version DOM Level 2
	 * @return the section row index
	 */
	public int getSectionRowIndex();

	/**
	 * The collection of cells in this row.
	 *
	 * @version DOM Level 2
	 * @return the cells
	 */
	public HTMLCollection getCells();

	/**
	 * Horizontal alignment of data within cells of this row. See the align
	 * attribute definition in HTML 4.01.
	 *
	 * @return the align
	 */
	public String getAlign();

	/**
	 * Horizontal alignment of data within cells of this row. See the align
	 * attribute definition in HTML 4.01.
	 *
	 * @param align the new align
	 */
	public void setAlign(String align);

	/**
	 * Background color for rows. See the bgcolor attribute definition in HTML
	 * 4.01. This attribute is deprecated in HTML 4.01.
	 *
	 * @return the bg color
	 */
	public String getBgColor();

	/**
	 * Background color for rows. See the bgcolor attribute definition in HTML
	 * 4.01. This attribute is deprecated in HTML 4.01.
	 *
	 * @param bgColor the new bg color
	 */
	public void setBgColor(String bgColor);

	/**
	 * Alignment character for cells in a column. See the char attribute
	 * definition in HTML 4.01.
	 *
	 * @return the ch
	 */
	public String getCh();

	/**
	 * Alignment character for cells in a column. See the char attribute
	 * definition in HTML 4.01.
	 *
	 * @param ch the new ch
	 */
	public void setCh(String ch);

	/**
	 * Offset of alignment character. See the charoff attribute definition in
	 * HTML 4.01.
	 *
	 * @return the ch off
	 */
	public String getChOff();

	/**
	 * Offset of alignment character. See the charoff attribute definition in
	 * HTML 4.01.
	 *
	 * @param chOff the new ch off
	 */
	public void setChOff(String chOff);

	/**
	 * Vertical alignment of data within cells of this row. See the valign
	 * attribute definition in HTML 4.01.
	 *
	 * @return the v align
	 */
	public String getVAlign();

	/**
	 * Vertical alignment of data within cells of this row. See the valign
	 * attribute definition in HTML 4.01.
	 *
	 * @param vAlign the new v align
	 */
	public void setVAlign(String vAlign);

	/**
	 * Insert an empty <code>TD</code> cell into this row. If <code>index</code>
	 * is -1 or equal to the number of cells, the new cell is appended.
	 *
	 * @version DOM Level 2
	 * @param index            The place to insert the cell, starting from 0.
	 * @return The newly created cell.
	 * @exception DOMException                INDEX_SIZE_ERR: Raised if the specified <code>index</code>
	 *                is greater than the number of cells or if the index is a
	 *                negative number other than -1.
	 */
	public HTMLElement insertCell(int index) throws DOMException;

	/**
	 * Delete a cell from the current row.
	 *
	 * @version DOM Level 2
	 * @param index            The index of the cell to delete, starting from 0. If the index
	 *            is -1 the last cell in the row is deleted.
	 * @exception DOMException                INDEX_SIZE_ERR: Raised if the specified <code>index</code>
	 *                is greater than or equal to the number of cells or if the
	 *                index is a negative number other than -1.
	 */
	public void deleteCell(int index) throws DOMException;

	/**
	 * Insert cell.
	 *
	 * @return the HTML element
	 */
	public HTMLElement insertCell();

}
