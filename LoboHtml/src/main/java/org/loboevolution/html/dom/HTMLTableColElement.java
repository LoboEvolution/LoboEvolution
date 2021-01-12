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
 * Regroups the COL and COLGROUP elements. See the COL
 * element definition in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLTableColElement extends HTMLElement {
	/**
	 * Horizontal alignment of cell data in column. See the align attribute
	 * definition in HTML 4.01.
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
	 * Indicates the number of columns in a group or affected by a grouping. See the
	 * span attribute definition in HTML 4.01.
	 *
	 * @return a int.
	 */
	int getSpan();

	/**
	 * Vertical alignment of cell data in column. See the valign attribute
	 * definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getVAlign();

	/**
	 * Default column width. See the width attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getWidth();

	/**
	 * Horizontal alignment of cell data in column. See the align attribute
	 * definition in HTML 4.01.
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
	 * Indicates the number of columns in a group or affected by a grouping. See the
	 * span attribute definition in HTML 4.01.
	 *
	 * @param span a int.
	 */
	void setSpan(int span);

	/**
	 * Vertical alignment of cell data in column. See the valign attribute
	 * definition in HTML 4.01.
	 *
	 * @param vAlign a {@link java.lang.String} object.
	 */
	void setVAlign(String vAlign);

	/**
	 * Default column width. See the width attribute definition in HTML 4.01.
	 *
	 * @param width a {@link java.lang.String} object.
	 */
	void setWidth(String width);

}
