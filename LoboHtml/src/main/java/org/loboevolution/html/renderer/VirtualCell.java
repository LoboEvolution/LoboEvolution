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
package org.loboevolution.html.renderer;

import org.loboevolution.html.style.HtmlLength;
import org.loboevolution.html.style.HtmlValues;

class VirtualCell {
	private final RTableCell actualCell;
	private int column;
	private final boolean isTopLeft;
	private int row;

	/**
	 * <p>Constructor for VirtualCell.</p>
	 *
	 * @param cell a {@link org.loboevolution.html.renderer.RTableCell} object.
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
		HtmlLength length = heightText == null ? null : new HtmlLength(HtmlValues.getPixelSize(heightText, cell.getModelNode().getRenderState(), 0)); 
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
		HtmlLength length = widthText == null ? null : new HtmlLength(HtmlValues.getPixelSize(widthText, cell.getModelNode().getRenderState(), 0)); 
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
