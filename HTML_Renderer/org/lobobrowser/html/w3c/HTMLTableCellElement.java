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


/**
 * The object used to represent the <code>TH</code> and <code>TD</code>
 * elements. See the TD element definition in HTML 4.01.
 * <p>
 * See also the
 * Object Model (DOM) Level 2 HTML Specification</p>.
 */
public interface HTMLTableCellElement extends HTMLElement {
	
	/**
	 * The index of this cell in the row, starting from 0. This index is in
	 * document tree order and not display order.
	 *
	 * @return the cell index
	 */
	public int getCellIndex();

	/**
	 * Abbreviation for header cells. See the abbr attribute definition in HTML
	 * 4.01.
	 *
	 * @return the abbr
	 */
	public String getAbbr();

	/**
	 * Abbreviation for header cells. See the abbr attribute definition in HTML
	 * 4.01.
	 *
	 * @param abbr the new abbr
	 */
	public void setAbbr(String abbr);

	/**
	 * Horizontal alignment of data in cell. See the align attribute definition
	 * in HTML 4.01.
	 *
	 * @return the align
	 */
	public String getAlign();

	/**
	 * Horizontal alignment of data in cell. See the align attribute definition
	 * in HTML 4.01.
	 *
	 * @param align the new align
	 */
	public void setAlign(String align);

	/**
	 * Names group of related headers. See the axis attribute definition in HTML
	 * 4.01.
	 *
	 * @return the axis
	 */
	public String getAxis();

	/**
	 * Names group of related headers. See the axis attribute definition in HTML
	 * 4.01.
	 *
	 * @param axis the new axis
	 */
	public void setAxis(String axis);

	/**
	 * Cell background color. See the bgcolor attribute definition in HTML 4.01.
	 * This attribute is deprecated in HTML 4.01.
	 *
	 * @return the bg color
	 */
	public String getBgColor();

	/**
	 * Cell background color. See the bgcolor attribute definition in HTML 4.01.
	 * This attribute is deprecated in HTML 4.01.
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
	 * Number of columns spanned by cell. See the colspan attribute definition
	 * in HTML 4.01.
	 *
	 * @return the col span
	 */
	public int getColSpan();

	/**
	 * Number of columns spanned by cell. See the colspan attribute definition
	 * in HTML 4.01.
	 *
	 * @param colSpan the new col span
	 */
	public void setColSpan(int colSpan);

	/**
	 * List of <code>id</code> attribute values for header cells. See the
	 * headers attribute definition in HTML 4.01.
	 *
	 * @return the headers
	 */
	public String getHeaders();

	/**
	 * List of <code>id</code> attribute values for header cells. See the
	 * headers attribute definition in HTML 4.01.
	 *
	 * @param headers the new headers
	 */
	public void setHeaders(String headers);

	/**
	 * Cell height. See the height attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @return the height
	 */
	public String getHeight();

	/**
	 * Cell height. See the height attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @param height the new height
	 */
	public void setHeight(String height);

	/**
	 * Suppress word wrapping. See the nowrap attribute definition in HTML 4.01.
	 * This attribute is deprecated in HTML 4.01.
	 *
	 * @return the no wrap
	 */
	public boolean getNoWrap();

	/**
	 * Suppress word wrapping. See the nowrap attribute definition in HTML 4.01.
	 * This attribute is deprecated in HTML 4.01.
	 *
	 * @param noWrap the new no wrap
	 */
	public void setNoWrap(boolean noWrap);

	/**
	 * Number of rows spanned by cell. See the rowspan attribute definition in
	 * HTML 4.01.
	 *
	 * @return the row span
	 */
	public int getRowSpan();

	/**
	 * Number of rows spanned by cell. See the rowspan attribute definition in
	 * HTML 4.01.
	 *
	 * @param rowSpan the new row span
	 */
	public void setRowSpan(int rowSpan);

	/**
	 * Scope covered by header cells. See the scope attribute definition in HTML
	 * 4.01.
	 *
	 * @return the scope
	 */
	public String getScope();

	/**
	 * Scope covered by header cells. See the scope attribute definition in HTML
	 * 4.01.
	 *
	 * @param scope the new scope
	 */
	public void setScope(String scope);

	/**
	 * Vertical alignment of data in cell. See the valign attribute definition
	 * in HTML 4.01.
	 *
	 * @return the v align
	 */
	public String getVAlign();

	/**
	 * Vertical alignment of data in cell. See the valign attribute definition
	 * in HTML 4.01.
	 *
	 * @param vAlign the new v align
	 */
	public void setVAlign(String vAlign);

	/**
	 * Cell width. See the width attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @return the width
	 */
	public String getWidth();

	/**
	 * Cell width. See the width attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @param width the new width
	 */
	public void setWidth(String width);

	
}
