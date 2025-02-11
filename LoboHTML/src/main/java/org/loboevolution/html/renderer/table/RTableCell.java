/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
/*
 * Created on Dec 3, 2005
 */
package org.loboevolution.html.renderer.table;

import lombok.Getter;
import lombok.Setter;
import org.loboevolution.html.dom.HTMLTableCellElement;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.css.CSSStyleDeclaration;
import org.loboevolution.html.renderer.RBlock;
import org.loboevolution.html.renderer.info.RBlockInfo;
import org.loboevolution.html.renderer.info.RLayoutInfo;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.info.SizeInfo;

import java.awt.*;

/**
 * <p>RTableCell class.</p>
 */
@Getter
@Setter
public class RTableCell extends RBlock {

	private final HTMLElementImpl cellElement;
	private int colSpan = -1;
	private int rowSpan = -1;
	private VirtualCell topLeftVirtualCell;

	/**
	 * <p>Constructor for RTableCell.</p>
	 *
	 * @param info a {@link org.loboevolution.html.renderer.info.RBlockInfo} object.
	 */
	public RTableCell(final RBlockInfo info) {
		super(info);
		this.cellElement = (HTMLElementImpl)info.getModelNode();
	}

	/**
	 * <p>doCellLayout.</p>
	 *
	 * @param width  The width available, including insets.
	 * @param height The height available, including insets.
	 * @param expandWidth a boolean.
	 * @param expandHeight a boolean.
	 * @param sizeOnly a boolean.
	 * @return a {@link java.awt.Dimension} object.
	 */
	public Dimension doCellLayout(final int width, final int height, final boolean expandWidth, final boolean expandHeight,
								  final boolean sizeOnly) {

		this.layout(RLayoutInfo.builder()
				.availWidth(width)
				.availHeight(height)
				.expandWidth(expandWidth)
				.expandHeight(expandHeight)
				.blockFloatBoundsSource(null)
				.defaultOverflowX(defaultOverflowX)
				.defaultOverflowY(defaultOverflowY)
				.sizeOnly(sizeOnly)
				.build());
		return new Dimension(this.getWidth(), this.getHeight());
	}

	/**
	 * <p>Getter for the field colSpan.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getColSpan() {
		int cs = this.colSpan;
		if (cs == -1) {
			cs = getColSpan(cellElement);
			this.colSpan = cs;
		}
		return cs;
	}

	/**
	 * <p>getHeightText.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getHeightText() {
        final CSSStyleDeclaration props = this.cellElement.getCurrentStyle();
        final String heightText = props == null ? null : props.getHeight();
        if (heightText == null) {
			if (this.cellElement instanceof HTMLTableCellElement htmlTableCellElement) {
                if (htmlTableCellElement.getHeight() != null) {
					return htmlTableCellElement.getHeight();
				}
			}
            return this.cellElement.getCurrentStyle().getHeight();
        } else if ("inherit".equals(heightText)) {
			return this.cellElement.getParentStyle().getWidth();
		} else {
            return heightText;
        }
	}

	/**
	 * <p>Getter for the field rowSpan.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getRowSpan() {
		int rs = this.rowSpan;
		if (rs == -1) {
			rs = getRowSpan(cellElement);
			this.rowSpan = rs;
		}
		return rs;
	}

	/**
	 * <p>getVirtualColumn.</p>
	 *
	 * @return Returns the virtualColumn.
	 */
	public int getVirtualColumn() {
		final VirtualCell vc = this.topLeftVirtualCell;
		return vc == null ? 0 : vc.getColumn();
	}

	/**
	 * <p>getVirtualRow.</p>
	 *
	 * @return Returns the virtualRow.
	 */
	public int getVirtualRow() {
		final VirtualCell vc = this.topLeftVirtualCell;
		return vc == null ? 0 : vc.getRow();
	}

	/**
	 * <p>getWidthText.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getWidthText() {
		final CSSStyleDeclaration props = this.cellElement.getCurrentStyle();
		final String widthText = props == null ? null : props.getWidth();
		if (widthText == null) {
			if (this.cellElement instanceof HTMLTableCellElement htmlTableCellElement) {
                if (htmlTableCellElement.getWidth() != null) {
					return htmlTableCellElement.getWidth();
				}
			}
			return this.cellElement.getCurrentStyle().getWidth();
		} else if ("inherit".equals(widthText)) {
			return this.cellElement.getParentStyle().getWidth();
		} else {
			return widthText;
		}
	}

	/** {@inheritDoc} */
	@Override
	protected boolean isMarginBoundary() {
		return true;
	}

	/**
	 * <p>setCellBounds.</p>
	 *
	 * @param colSizes an array of {@link org.loboevolution.info.SizeInfo} objects.
	 * @param rowSizes an array of {@link org.loboevolution.info.SizeInfo} objects.
	 * @param hasBorder a {@link java.lang.Integer} object.
	 * @param cellSpacingX a {@link java.lang.Integer} object.
	 * @param cellSpacingY a {@link java.lang.Integer} object.
	 */
	public void setCellBounds(final SizeInfo[] colSizes, final SizeInfo[] rowSizes, final int hasBorder,
							  final int cellSpacingX, final int cellSpacingY) {
		final int vcol = getVirtualColumn();
		final int vrow = getVirtualRow();
		final SizeInfo colSize = colSizes[vcol];
		final SizeInfo rowSize = rowSizes[vrow];
		final int x = colSize.getOffset();
		final int y = rowSize.getOffset();
		int width;
		int height;
		final int colSpan = getColSpan();
		if (colSpan > 1) {
			width = 0;
			for (int i = 0; i < colSpan; i++) {
				final int vc = vcol + i;
				width += colSizes[vc].getActualSize();
				if (i + 1 < colSpan) {
					width += cellSpacingX + hasBorder * 2;
				}
			}
		} else {
			width = colSizes[vcol].getActualSize();
		}
		final int rowSpan = getRowSpan();
		if (rowSpan > 1) {
			height = 0;
			for (int i = 0; i < rowSpan; i++) {
				final int vr = vrow + i;
				height += rowSizes[vr].getActualSize();
				if (i + 1 < rowSpan) {
					height += cellSpacingY + hasBorder * 2;
				}
			}
		} else {
			height = rowSizes[vrow].getActualSize();
		}
		setBounds(x, y, width, height);
	}
	
    private static int getColSpan(final HTMLElementImpl elem) {
        final String colSpanText = elem.getAttribute("colspan");
		final HTMLDocumentImpl doc =  (HTMLDocumentImpl)elem.getDocumentNode();
        return HtmlValues.getPixelSize(colSpanText, null, doc.getDefaultView(), 1);
    }
    
    private static int getRowSpan(final HTMLElementImpl elem) {
        final String rowSpanText = elem.getAttribute("rowspan");
		final HTMLDocumentImpl doc =  (HTMLDocumentImpl)elem.getDocumentNode();
        return HtmlValues.getPixelSize(rowSpanText, null, doc.getDefaultView(), 1);
    }
}