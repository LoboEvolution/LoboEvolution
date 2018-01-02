/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.w3c.html;

/**
 * The Interface HTMLTableCellElement.
 */
public interface HTMLTableCellElement extends HTMLElement {

	/**
	 * Gets the col span.
	 *
	 * @return the col span
	 */
	// HTMLTableCellElement
	public int getColSpan();

	/**
	 * Sets the col span.
	 *
	 * @param colSpan
	 *            the new col span
	 */
	public void setColSpan(int colSpan);

	/**
	 * Gets the row span.
	 *
	 * @return the row span
	 */
	public int getRowSpan();

	/**
	 * Sets the row span.
	 *
	 * @param rowSpan
	 *            the new row span
	 */
	public void setRowSpan(int rowSpan);

	/**
	 * Gets the headers.
	 *
	 * @return the headers
	 */
	public String getHeaders();

	/**
	 * Sets the headers.
	 *
	 * @param headers
	 *            the new headers
	 */
	public void setHeaders(String headers);

	/**
	 * Gets the cell index.
	 *
	 * @return the cell index
	 */
	public int getCellIndex();

	/**
	 * Gets the abbr.
	 *
	 * @return the abbr
	 */
	// HTMLTableCellElement-31
	public String getAbbr();

	/**
	 * Sets the abbr.
	 *
	 * @param abbr
	 *            the new abbr
	 */
	public void setAbbr(String abbr);

	/**
	 * Gets the align.
	 *
	 * @return the align
	 */
	public String getAlign();

	/**
	 * Sets the align.
	 *
	 * @param align
	 *            the new align
	 */
	public void setAlign(String align);

	/**
	 * Gets the axis.
	 *
	 * @return the axis
	 */
	public String getAxis();

	/**
	 * Sets the axis.
	 *
	 * @param axis
	 *            the new axis
	 */
	public void setAxis(String axis);

	/**
	 * Gets the bg color.
	 *
	 * @return the bg color
	 */
	public String getBgColor();

	/**
	 * Sets the bg color.
	 *
	 * @param bgColor
	 *            the new bg color
	 */
	public void setBgColor(String bgColor);

	/**
	 * Gets the ch.
	 *
	 * @return the ch
	 */
	public String getCh();

	/**
	 * Sets the ch.
	 *
	 * @param ch
	 *            the new ch
	 */
	public void setCh(String ch);

	/**
	 * Gets the ch off.
	 *
	 * @return the ch off
	 */
	public String getChOff();

	/**
	 * Sets the ch off.
	 *
	 * @param chOff
	 *            the new ch off
	 */
	public void setChOff(String chOff);

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public String getHeight();

	/**
	 * Sets the height.
	 *
	 * @param height
	 *            the new height
	 */
	public void setHeight(String height);

	/**
	 * Gets the no wrap.
	 *
	 * @return the no wrap
	 */
	public boolean getNoWrap();

	/**
	 * Sets the no wrap.
	 *
	 * @param noWrap
	 *            the new no wrap
	 */
	public void setNoWrap(boolean noWrap);

	/**
	 * Gets the v align.
	 *
	 * @return the v align
	 */
	public String getVAlign();

	/**
	 * Sets the v align.
	 *
	 * @param vAlign
	 *            the new v align
	 */
	public void setVAlign(String vAlign);

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public String getWidth();

	/**
	 * Sets the width.
	 *
	 * @param width
	 *            the new width
	 */
	public void setWidth(String width);

	/**
	 * Sets the scope.
	 *
	 * @param scope
	 *            the new scope
	 */
	void setScope(String scope);

	/**
	 * Gets the scope.
	 *
	 * @return the scope
	 */
	String getScope();
}
