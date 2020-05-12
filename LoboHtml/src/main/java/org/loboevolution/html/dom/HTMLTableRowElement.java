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

import org.w3c.dom.DOMException;

/**
 * A row in a table. See the TR element definition in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLTableRowElement extends HTMLElement {
	/**
	 * Delete a cell from the current row.
	 *
	 * @param index The index of the cell to delete, starting from 0. If the index
	 *              is -1 the last cell in the row is deleted.
	 * @exception DOMException INDEX_SIZE_ERR: Raised if the specified
	 *                         index is greater than or equal to the
	 *                         number of cells or if the index is a negative number
	 *                         other than -1.
	 * @version DOM Level 2
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void deleteCell(int index) throws DOMException;

	/**
	 * Horizontal alignment of data within cells of this row. See the align
	 * attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAlign();

	/**
	 * Background color for rows. See the bgcolor attribute definition in HTML 4.01.
	 * This attribute is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getBgColor();

	/**
	 * The collection of cells in this row.
	 *
	 * @version DOM Level 2
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getCells();

	/**
	 * Alignment character for cells in a column. See the char attribute definition
	 * in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getCh();

	/**
	 * Offset of alignment character. See the charoff attribute definition in HTML
	 * 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getChOff();

	/**
	 * This is in logical order and not in document order. The rowIndex
	 * does take into account sections ( THEAD, TFOOT, or
	 * TBODY) within the table, placing THEAD rows first
	 * in the index, followed by TBODY rows, followed by
	 * TFOOT rows.
	 *
	 * @version DOM Level 2
	 * @return a int.
	 */
	int getRowIndex();

	/**
	 * The index of this row, relative to the current section ( THEAD,
	 * TFOOT, or TBODY), starting from 0.
	 *
	 * @version DOM Level 2
	 * @return a int.
	 */
	int getSectionRowIndex();

	/**
	 * Vertical alignment of data within cells of this row. See the valign attribute
	 * definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getVAlign();

	/**
	 * Insert an empty TD cell into this row. If index is
	 * -1 or equal to the number of cells, the new cell is appended.
	 *
	 * @param index The place to insert the cell, starting from 0.
	 * @return The newly created cell.
	 * @exception DOMException INDEX_SIZE_ERR: Raised if the specified
	 *                         index is greater than the number of
	 *                         cells or if the index is a negative number other than
	 *                         -1.
	 * @version DOM Level 2
	 * @throws org.w3c.dom.DOMException if any.
	 */
	HTMLElement insertCell(int index) throws DOMException;

	/**
	 * Horizontal alignment of data within cells of this row. See the align
	 * attribute definition in HTML 4.01.
	 *
	 * @param align a {@link java.lang.String} object.
	 */
	void setAlign(String align);

	/**
	 * Background color for rows. See the bgcolor attribute definition in HTML 4.01.
	 * This attribute is deprecated in HTML 4.01.
	 *
	 * @param bgColor a {@link java.lang.String} object.
	 */
	void setBgColor(String bgColor);

	/**
	 * Alignment character for cells in a column. See the char attribute definition
	 * in HTML 4.01.
	 *
	 * @param ch a {@link java.lang.String} object.
	 */
	void setCh(String ch);

	/**
	 * Offset of alignment character. See the charoff attribute definition in HTML
	 * 4.01.
	 *
	 * @param chOff a {@link java.lang.String} object.
	 */
	void setChOff(String chOff);

	/**
	 * Vertical alignment of data within cells of this row. See the valign attribute
	 * definition in HTML 4.01.
	 *
	 * @param vAlign a {@link java.lang.String} object.
	 */
	void setVAlign(String vAlign);

}
