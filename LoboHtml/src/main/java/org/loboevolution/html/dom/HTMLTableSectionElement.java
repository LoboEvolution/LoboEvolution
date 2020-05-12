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
 * The THEAD, TFOOT, and TBODY elements.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLTableSectionElement extends HTMLElement {
	/**
	 * Delete a row from this section.
	 *
	 * @param index The index of the row to be deleted, or -1 to delete the last
	 *              row. This index starts from 0 and is relative only to the rows
	 *              contained inside this section, not all the rows in the table.
	 * @exception DOMException INDEX_SIZE_ERR: Raised if the specified index is
	 *                         greater than or equal to the number of rows or if the
	 *                         index is a negative number other than -1.
	 * @version DOM Level 2
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void deleteRow(int index) throws DOMException;

	/**
	 * Horizontal alignment of data in cells. See the align attribute
	 * for HTMLTheadElement for details.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAlign();

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
	 * The collection of rows in this table section.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getRows();

	/**
	 * Vertical alignment of data in cells. See the valign attribute
	 * for HTMLTheadElement for details.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getVAlign();

	/**
	 * Insert a row into this section. The new row is inserted immediately before
	 * the current indexth row in this section. If index
	 * is -1 or equal to the number of rows in this section, the new row is
	 * appended.
	 *
	 * @param index The row number where to insert a new row. This index starts from
	 *              0 and is relative only to the rows contained inside this
	 *              section, not all the rows in the table.
	 * @return The newly created row.
	 * @exception DOMException INDEX_SIZE_ERR: Raised if the specified index is
	 *                         greater than the number of rows of if the index is a
	 *                         negative number other than -1.
	 * @version DOM Level 2
	 * @throws org.w3c.dom.DOMException if any.
	 */
	HTMLElement insertRow(int index) throws DOMException;

	/**
	 * Horizontal alignment of data in cells. See the align attribute
	 * for HTMLTheadElement for details.
	 *
	 * @param align a {@link java.lang.String} object.
	 */
	void setAlign(String align);

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
	 * Vertical alignment of data in cells. See the valign attribute
	 * for HTMLTheadElement for details.
	 *
	 * @param vAlign a {@link java.lang.String} object.
	 */
	void setVAlign(String vAlign);

}
