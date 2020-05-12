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
 * The create* and delete* methods on the table allow authors to construct and
 * modify tables. [<a href='http://www.w3.org/TR/1999/REC-html401-19991224'>HTML
 * 4.01</a>] specifies that only one of each of the CAPTION,
 * THEAD, and TFOOT elements may exist in a table.
 * Therefore, if one exists, and the createTHead() or createTFoot() method is
 * called, the method returns the existing THead or TFoot element. See the TABLE
 * element definition in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLTableElement extends HTMLElement {
	/**
	 * Create a new table caption object or return an existing one.
	 *
	 * @return A CAPTION element.
	 */
	HTMLElement createCaption();

	/**
	 * Create a table footer row or return an existing one.
	 *
	 * @return A footer element (TFOOT).
	 */
	HTMLElement createTFoot();

	/**
	 * Create a table header row or return an existing one.
	 *
	 * @return A new table header element (THEAD).
	 */
	HTMLElement createTHead();
	
	/**
	 * Create a table body row or return an existing one.
	 *
	 * @return A new table header element (BODY).
	 */
	HTMLElement createTBody();

	/**
	 * Delete the table caption, if one exists.
	 */
	void deleteCaption();

	/**
	 * Delete a table row.
	 *
	 * @param index The index of the row to be deleted. This index starts from 0 and
	 *              is relative to the logical order (not document order) of all the
	 *              rows contained inside the table. If the index is -1 the last row
	 *              in the table is deleted.
	 * @exception DOMException INDEX_SIZE_ERR: Raised if the specified index is
	 *                         greater than or equal to the number of rows or if the
	 *                         index is a negative number other than -1.
	 * @version DOM Level 2
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void deleteRow(int index) throws DOMException;

	/**
	 * Delete the footer from the table, if one exists.
	 */
	void deleteTFoot();

	/**
	 * Delete the header from the table, if one exists.
	 */
	void deleteTHead();
	
	/**
	 * Delete the body from the table, if one exists.
	 */
	void deleteTBody();

	/**
	 * Specifies the table's position with respect to the rest of the document. See
	 * the align attribute definition in HTML 4.01. This attribute is deprecated in
	 * HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAlign();

	/**
	 * Cell background color. See the bgcolor attribute definition in HTML 4.01.
	 * This attribute is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getBgColor();

	/**
	 * The width of the border around the table. See the border attribute definition
	 * in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getBorder();

	/**
	 * Returns the table's CAPTION, or void if none exists.
	 *
	 * @version DOM Level 2
	 * @return a {@link org.loboevolution.html.dom.HTMLTableCaptionElement} object.
	 */
	HTMLTableCaptionElement getCaption();

	/**
	 * Specifies the horizontal and vertical space between cell content and cell
	 * borders. See the cellpadding attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getCellPadding();

	/**
	 * Specifies the horizontal and vertical separation between cells. See the
	 * cellspacing attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getCellSpacing();

	/**
	 * Specifies which external table borders to render. See the frame attribute
	 * definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getFrame();

	/**
	 * Returns a collection of all the rows in the table, including all in
	 * THEAD, TFOOT, all TBODY elements.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getRows();

	/**
	 * Specifies which internal table borders to render. See the rules attribute
	 * definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getRules();

	/**
	 * Description about the purpose or structure of a table. See the summary
	 * attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getSummary();

	/**
	 * Returns a collection of the table bodies (including implicit ones).
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getTBodies();

	/**
	 * Returns the table's TFOOT, or null if none exists.
	 *
	 * @version DOM Level 2
	 * @return a {@link org.loboevolution.html.dom.HTMLTableSectionElement} object.
	 */
	HTMLTableSectionElement getTFoot();

	/**
	 * Returns the table's THEAD, or null if none exists.
	 *
	 * @version DOM Level 2
	 * @return a {@link org.loboevolution.html.dom.HTMLTableSectionElement} object.
	 */
	HTMLTableSectionElement getTHead();

	/**
	 * Specifies the desired table width. See the width attribute definition in HTML
	 * 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getWidth();

	/**
	 * Insert a new empty row in the table. The new row is inserted immediately
	 * before and in the same section as the current indexth row in the
	 * table. If index is -1 or equal to the number of rows, the new
	 * row is appended. In addition, when the table is empty the row is inserted
	 * into a TBODY which is created and inserted into the table.A
	 * table row cannot be empty according to
	 * [<a href='http://www.w3.org/TR/1999/REC-html401-19991224'>HTML 4.01</a>].
	 *
	 * @param index The row number where to insert a new row. This index starts from
	 *              0 and is relative to the logical order (not document order) of
	 *              all the rows contained inside the table.
	 * @return The newly created row.
	 * @exception DOMException INDEX_SIZE_ERR: Raised if the specified index is
	 *                         greater than the number of rows or if the index is a
	 *                         negative number other than -1.
	 * @version DOM Level 2
	 * @throws org.w3c.dom.DOMException if any.
	 */
	HTMLElement insertRow(int index) throws DOMException;

	/**
	 * Specifies the table's position with respect to the rest of the document. See
	 * the align attribute definition in HTML 4.01. This attribute is deprecated in
	 * HTML 4.01.
	 *
	 * @param align a {@link java.lang.String} object.
	 */
	void setAlign(String align);

	/**
	 * Cell background color. See the bgcolor attribute definition in HTML 4.01.
	 * This attribute is deprecated in HTML 4.01.
	 *
	 * @param bgColor a {@link java.lang.String} object.
	 */
	void setBgColor(String bgColor);

	/**
	 * The width of the border around the table. See the border attribute definition
	 * in HTML 4.01.
	 *
	 * @param border a {@link java.lang.String} object.
	 */
	void setBorder(String border);

	/**
	 * Returns the table's CAPTION, or void if none exists.
	 *
	 * @exception DOMException HIERARCHY_REQUEST_ERR: if the element is not a
	 *                         CAPTION.
	 * @version DOM Level 2
	 * @param caption a {@link org.loboevolution.html.dom.HTMLTableCaptionElement} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setCaption(HTMLTableCaptionElement caption) throws DOMException;

	/**
	 * Specifies the horizontal and vertical space between cell content and cell
	 * borders. See the cellpadding attribute definition in HTML 4.01.
	 *
	 * @param cellPadding a {@link java.lang.String} object.
	 */
	void setCellPadding(String cellPadding);

	/**
	 * Specifies the horizontal and vertical separation between cells. See the
	 * cellspacing attribute definition in HTML 4.01.
	 *
	 * @param cellSpacing a {@link java.lang.String} object.
	 */
	void setCellSpacing(String cellSpacing);

	/**
	 * Specifies which external table borders to render. See the frame attribute
	 * definition in HTML 4.01.
	 *
	 * @param frame a {@link java.lang.String} object.
	 */
	void setFrame(String frame);

	/**
	 * Specifies which internal table borders to render. See the rules attribute
	 * definition in HTML 4.01.
	 *
	 * @param rules a {@link java.lang.String} object.
	 */
	void setRules(String rules);

	/**
	 * Description about the purpose or structure of a table. See the summary
	 * attribute definition in HTML 4.01.
	 *
	 * @param summary a {@link java.lang.String} object.
	 */
	void setSummary(String summary);

	/**
	 * Returns the table's TFOOT, or null if none exists.
	 *
	 * @exception DOMException HIERARCHY_REQUEST_ERR: if the element is not a
	 *                         TFOOT.
	 * @version DOM Level 2
	 * @param tFoot a {@link org.loboevolution.html.dom.HTMLTableSectionElement} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setTFoot(HTMLTableSectionElement tFoot) throws DOMException;

	/**
	 * Returns the table's THEAD, or null if none exists.
	 *
	 * @exception DOMException HIERARCHY_REQUEST_ERR: if the element is not a
	 *                         THEAD.
	 * @version DOM Level 2
	 * @param tHead a {@link org.loboevolution.html.dom.HTMLTableSectionElement} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setTHead(HTMLTableSectionElement tHead) throws DOMException;

	/**
	 * Specifies the desired table width. See the width attribute definition in HTML
	 * 4.01.
	 *
	 * @param width a {@link java.lang.String} object.
	 */
	void setWidth(String width);

}
