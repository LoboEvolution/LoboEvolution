/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.dom;

/**
 * The object used to represent the TH and TD
 * elements. See the TD element definition in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLTableCellElement extends HTMLElement {
	/**
	 * Abbreviation for header cells. See the abbr attribute definition in HTML
	 * 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAbbr();

	/**
	 * Horizontal alignment of data in cell. See the align attribute definition in
	 * HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAlign();

	/**
	 * Names group of related headers. See the axis attribute definition in HTML
	 * 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAxis();

	/**
	 * Cell background color. See the bgcolor attribute definition in HTML 4.01.
	 * This attribute is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getBgColor();

	/**
	 * The index of this cell in the row, starting from 0. This index is in document
	 * tree order and not display order.
	 *
	 * @return a int.
	 */
	int getCellIndex();

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
	 * Number of columns spanned by cell. See the colspan attribute definition in
	 * HTML 4.01.
	 *
	 * @return a int.
	 */
	int getColSpan();

	/**
	 * List of id attribute values for header cells. See the headers
	 * attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getHeaders();

	/**
	 * Cell height. See the height attribute definition in HTML 4.01. This attribute
	 * is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getHeight();

	/**
	 * Suppress word wrapping. See the nowrap attribute definition in HTML 4.01.
	 * This attribute is deprecated in HTML 4.01.
	 *
	 * @return a boolean.
	 */
	boolean getNoWrap();

	/**
	 * Number of rows spanned by cell. See the rowspan attribute definition in HTML
	 * 4.01.
	 *
	 * @return a int.
	 */
	int getRowSpan();

	/**
	 * Scope covered by header cells. See the scope attribute definition in HTML
	 * 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getScope();

	/**
	 * Vertical alignment of data in cell. See the valign attribute definition in
	 * HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getVAlign();

	/**
	 * Cell width. See the width attribute definition in HTML 4.01. This attribute
	 * is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getWidth();

	/**
	 * Abbreviation for header cells. See the abbr attribute definition in HTML
	 * 4.01.
	 *
	 * @param abbr a {@link java.lang.String} object.
	 */
	void setAbbr(String abbr);

	/**
	 * Horizontal alignment of data in cell. See the align attribute definition in
	 * HTML 4.01.
	 *
	 * @param align a {@link java.lang.String} object.
	 */
	void setAlign(String align);

	/**
	 * Names group of related headers. See the axis attribute definition in HTML
	 * 4.01.
	 *
	 * @param axis a {@link java.lang.String} object.
	 */
	void setAxis(String axis);

	/**
	 * Cell background color. See the bgcolor attribute definition in HTML 4.01.
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
	 * Number of columns spanned by cell. See the colspan attribute definition in
	 * HTML 4.01.
	 *
	 * @param colSpan a int.
	 */
	void setColSpan(int colSpan);

	/**
	 * List of id attribute values for header cells. See the headers
	 * attribute definition in HTML 4.01.
	 *
	 * @param headers a {@link java.lang.String} object.
	 */
	void setHeaders(String headers);

	/**
	 * Cell height. See the height attribute definition in HTML 4.01. This attribute
	 * is deprecated in HTML 4.01.
	 *
	 * @param height a {@link java.lang.String} object.
	 */
	void setHeight(String height);

	/**
	 * Suppress word wrapping. See the nowrap attribute definition in HTML 4.01.
	 * This attribute is deprecated in HTML 4.01.
	 *
	 * @param noWrap a boolean.
	 */
	void setNoWrap(boolean noWrap);

	/**
	 * Number of rows spanned by cell. See the rowspan attribute definition in HTML
	 * 4.01.
	 *
	 * @param rowSpan a int.
	 */
	void setRowSpan(int rowSpan);

	/**
	 * Scope covered by header cells. See the scope attribute definition in HTML
	 * 4.01.
	 *
	 * @param scope a {@link java.lang.String} object.
	 */
	void setScope(String scope);

	/**
	 * Vertical alignment of data in cell. See the valign attribute definition in
	 * HTML 4.01.
	 *
	 * @param vAlign a {@link java.lang.String} object.
	 */
	void setVAlign(String vAlign);

	/**
	 * Cell width. See the width attribute definition in HTML 4.01. This attribute
	 * is deprecated in HTML 4.01.
	 *
	 * @param width a {@link java.lang.String} object.
	 */
	void setWidth(String width);

}
