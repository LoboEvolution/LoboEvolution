/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.renderer.table;

import org.loboevolution.html.style.HtmlLength;

/**
 * <p>VirtualCell class.</p>
 *
 *
 *
 */
public class VirtualCell {
	private final RTableCell actualCell;
	private int column;
	private final boolean isTopLeft;
	private int row;

	/**
	 * <p>Constructor for VirtualCell.</p>
	 *
	 * @param cell a {@link org.loboevolution.html.renderer.table.RTableCell} object.
	 * @param isTopLeft a boolean.
	 */
	public VirtualCell(RTableCell cell, boolean isTopLeft) {
		this.actualCell = cell;
		this.isTopLeft = isTopLeft;
	}

	/**
	 * <p>Getter for the field actualCell.</p>
	 *
	 * @return Returns the actualCell.
	 */
	public RTableCell getActualCell() {
		return this.actualCell;
	}

	/**
	 * <p>Getter for the field column.</p>
	 *
	 * @return Returns the column.
	 */
	public int getColumn() {
		return this.column;
	}

	/**
	 * <p>getHeightLength.</p>
	 *
	 * @return a {@link org.loboevolution.html.style.HtmlLength} object.
	 */
	public HtmlLength getHeightLength() {
		final RTableCell cell = this.actualCell;
		final String heightText = cell.getHeightText();
		HtmlLength length = heightText == null ? null : new HtmlLength(heightText); 
		if (length != null) {
			length.divideBy(cell.getRowSpan());
		}
		return length;
	}

	/**
	 * <p>Getter for the field row.</p>
	 *
	 * @return Returns the row.
	 */
	public int getRow() {
		return this.row;
	}

	/**
	 * <p>getWidthLength.</p>
	 *
	 * @return a {@link org.loboevolution.html.style.HtmlLength} object.
	 */
	public HtmlLength getWidthLength() {
		final RTableCell cell = this.actualCell;
		final String widthText = cell.getWidthText();
		HtmlLength length = widthText == null ? null : new HtmlLength(widthText); 
		if (length != null) {
			length.divideBy(cell.getColSpan());
		}
		return length;
	}

	/**
	 * <p>isTopLeft.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isTopLeft() {
		return this.isTopLeft;
	}

	/**
	 * <p>Setter for the field column.</p>
	 *
	 * @param column The column to set.
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * <p>Setter for the field row.</p>
	 *
	 * @param row The row to set.
	 */
	public void setRow(int row) {
		this.row = row;
	}
}
