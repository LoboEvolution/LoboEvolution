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
 * Created on Dec 3, 2005
 */
package org.lobobrowser.html.renderer;

import org.lobobrowser.html.style.HtmlLength;

class VirtualCell {
	private final RTableCell actualCell;
	private int column;
	private final boolean isTopLeft;
	private int row;

	/**
	 * @param cell
	 */
	public VirtualCell(RTableCell cell, boolean isTopLeft) {
		this.actualCell = cell;
		this.isTopLeft = isTopLeft;
	}

	/**
	 * @return Returns the actualCell.
	 */
	public RTableCell getActualCell() {
		return this.actualCell;
	}

	/**
	 * @return Returns the column.
	 */
	public int getColumn() {
		return this.column;
	}

	public HtmlLength getHeightLength() {
		final RTableCell cell = this.actualCell;
		final String heightText = cell.getHeightText();
		HtmlLength length;
		try {
			length = heightText == null ? null : new HtmlLength(heightText);
		} catch (final Exception err) {
			length = null;
		}
		if (length != null) {
			length.divideBy(cell.getRowSpan());
		}
		return length;
	}

	/**
	 * @return Returns the row.
	 */
	public int getRow() {
		return this.row;
	}

	public HtmlLength getWidthLength() {
		final RTableCell cell = this.actualCell;
		final String widthText = cell.getWidthText();
		HtmlLength length;
		try {
			length = widthText == null ? null : new HtmlLength(widthText);
		} catch (final Exception err) {
			length = null;
		}
		if (length != null) {
			length.divideBy(cell.getColSpan());
		}
		return length;
	}

	public boolean isTopLeft() {
		return this.isTopLeft;
	}

	/**
	 * @param column The column to set.
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * @param row The row to set.
	 */
	public void setRow(int row) {
		this.row = row;
	}

//	public Dimension layoutMinWidth() {
//
//		ActualCell cell = this.actualCell;
//
//		Dimension ad = cell.layoutMinWidth();
//
//		int colspan = cell.getColSpan();
//
//		int rowspan = cell.getRowSpan();
//
//		if(colspan == 1 && rowspan == 1) {
//
//			return ad;
//
//		}
//
//		else {
//
//			return new Dimension(ad.width / colspan, ad.height / rowspan);
//
//		}
//
//	}
}
