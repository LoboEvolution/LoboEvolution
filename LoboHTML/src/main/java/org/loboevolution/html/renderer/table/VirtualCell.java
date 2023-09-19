/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.renderer.table;

import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
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
		HTMLElementImpl elem = cell.getCellElement();
		final HTMLDocumentImpl doc =  (HTMLDocumentImpl)elem.getOwnerDocument();
		HtmlLength length = heightText == null ? null : new HtmlLength(heightText, doc);
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
		HTMLElementImpl elem = cell.getCellElement();
		final HTMLDocumentImpl doc =  (HTMLDocumentImpl)elem.getOwnerDocument();
		HtmlLength length = widthText == null ? null : new HtmlLength(widthText, doc);
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
