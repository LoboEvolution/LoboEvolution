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
 * The Interface HTMLTableElement.
 */
public interface HTMLTableElement extends HTMLElement {

	/**
	 * Gets the caption.
	 *
	 * @return the caption
	 */

	public HTMLTableCaptionElement getCaption();

	/**
	 * Sets the caption.
	 *
	 * @param caption
	 *            the new caption
	 */
	public void setCaption(HTMLTableCaptionElement caption);

	/**
	 * Creates the caption.
	 *
	 * @return the HTML element
	 */
	public HTMLElement createCaption();

	/**
	 * Delete caption.
	 */
	public void deleteCaption();

	/**
	 * Gets the t head.
	 *
	 * @return the t head
	 */
	public HTMLTableSectionElement getTHead();

	/**
	 * Sets the t head.
	 *
	 * @param tHead
	 *            the new t head
	 */
	public void setTHead(HTMLTableSectionElement tHead);

	/**
	 * Creates the t head.
	 *
	 * @return the HTML element
	 */
	public HTMLElement createTHead();

	/**
	 * Delete t head.
	 */
	public void deleteTHead();

	/**
	 * Gets the t foot.
	 *
	 * @return the t foot
	 */
	public HTMLTableSectionElement getTFoot();

	/**
	 * Sets the t foot.
	 *
	 * @param tFoot
	 *            the new t foot
	 */
	public void setTFoot(HTMLTableSectionElement tFoot);

	/**
	 * Creates the t foot.
	 *
	 * @return the HTML element
	 */
	public HTMLElement createTFoot();

	/**
	 * Delete t foot.
	 */
	public void deleteTFoot();

	/**
	 * Gets the t bodies.
	 *
	 * @return the t bodies
	 */
	public HTMLCollection getTBodies();

	/**
	 * Creates the t body.
	 *
	 * @return the HTML element
	 */
	public HTMLElement createTBody();

	/**
	 * delete the t body.
	 *
	 * @return the HTML element
	 */
	public HTMLElement deleteTBody();

	/**
	 * Gets the rows.
	 *
	 * @return the rows
	 */
	public HTMLCollection getRows();

	/**
	 * Insert row.
	 *
	 * @return the HTML element
	 */
	public HTMLElement insertRow();

	/**
	 * Insert row.
	 *
	 * @param index
	 *            the index
	 * @return the HTML element
	 */
	public HTMLElement insertRow(int index);

	/**
	 * Delete row.
	 *
	 * @param index
	 *            the index
	 */
	public void deleteRow(int index);

	/**
	 * Gets the border.
	 *
	 * @return the border
	 */
	public String getBorder();

	/**
	 * Sets the border.
	 *
	 * @param border
	 *            the new border
	 */
	public void setBorder(String border);

	/**
	 * Gets the align.
	 *
	 * @return the align
	 */
	// HTMLTableElement-29
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
	 * Gets the cell padding.
	 *
	 * @return the cell padding
	 */
	public String getCellPadding();

	/**
	 * Sets the cell padding.
	 *
	 * @param cellPadding
	 *            the new cell padding
	 */
	public void setCellPadding(String cellPadding);

	/**
	 * Gets the cell spacing.
	 *
	 * @return the cell spacing
	 */
	public String getCellSpacing();

	/**
	 * Sets the cell spacing.
	 *
	 * @param cellSpacing
	 *            the new cell spacing
	 */
	public void setCellSpacing(String cellSpacing);

	/**
	 * Gets the frame.
	 *
	 * @return the frame
	 */
	public String getFrame();

	/**
	 * Sets the frame.
	 *
	 * @param frame
	 *            the new frame
	 */
	public void setFrame(String frame);

	/**
	 * Gets the rules.
	 *
	 * @return the rules
	 */
	public String getRules();

	/**
	 * Sets the rules.
	 *
	 * @param rules
	 *            the new rules
	 */
	public void setRules(String rules);

	/**
	 * Gets the summary.
	 *
	 * @return the summary
	 */
	public String getSummary();

	/**
	 * Sets the summary.
	 *
	 * @param summary
	 *            the new summary
	 */
	public void setSummary(String summary);

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
}
