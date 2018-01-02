/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.w3c.html;

/**
 * The Interface HTMLTableRowElement.
 */
public interface HTMLTableRowElement extends HTMLElement {

	/**
	 * Gets the row index.
	 *
	 * @return the row index
	 */
	// HTMLTableRowElement
	public int getRowIndex();

	/**
	 * Gets the section row index.
	 *
	 * @return the section row index
	 */
	public int getSectionRowIndex();

	/**
	 * Gets the cells.
	 *
	 * @return the cells
	 */
	public HTMLCollection getCells();

	/**
	 * Insert cell.
	 *
	 * @return the HTML element
	 */
	public HTMLElement insertCell();

	/**
	 * Insert cell.
	 *
	 * @param index
	 *            the index
	 * @return the HTML element
	 */
	public HTMLElement insertCell(int index);

	/**
	 * Delete cell.
	 *
	 * @param index
	 *            the index
	 */
	public void deleteCell(int index);

	/**
	 * Gets the align.
	 *
	 * @return the align
	 */
	// HTMLTableRowElement-32
	public String getAlign();

	/**
	 * Sets the align.
	 *
	 * @param align
	 *            the new align
	 */
	public void setAlign(String align);

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
}
