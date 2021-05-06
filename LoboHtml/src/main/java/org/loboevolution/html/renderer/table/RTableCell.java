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
/*
 * Created on Dec 3, 2005
 */
package org.loboevolution.html.renderer.table;

import java.awt.Dimension;

import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderer.FrameContext;
import org.loboevolution.html.renderer.RBlock;
import org.loboevolution.html.renderer.RenderableContainer;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.info.SizeInfo;

/**
 * <p>RTableCell class.</p>
 *
 *
 *
 */
public class RTableCell extends RBlock {

	private final HTMLElementImpl cellElement;
	
	private int colSpan = -1;

	private int rowSpan = -1;

	private VirtualCell topLeftVirtualCell;

	/**
	 * <p>Constructor for RTableCell.</p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param pcontext a {@link org.loboevolution.http.UserAgentContext} object.
	 * @param rcontext a {@link org.loboevolution.http.HtmlRendererContext} object.
	 * @param frameContext a {@link org.loboevolution.html.renderer.FrameContext} object.
	 * @param tableAsContainer a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 */
	public RTableCell(HTMLElementImpl element, UserAgentContext pcontext, HtmlRendererContext rcontext,
			FrameContext frameContext, RenderableContainer tableAsContainer) {
		super(element, 0, pcontext, rcontext, frameContext, tableAsContainer);
		this.cellElement = element;
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
	public Dimension doCellLayout(int width, int height, boolean expandWidth, boolean expandHeight,
			boolean sizeOnly) {
		try {
			this.layout(width, height, expandWidth, expandHeight, null, sizeOnly);
			return new Dimension(this.width, this.height);
		} finally {
			this.layoutUpTreeCanBeInvalidated = true;
			this.layoutDeepCanBeInvalidated = true;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * <p>Getter for the field colSpan.</p>
	 *
	 * @return a int.
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
        AbstractCSSProperties props = this.cellElement.getCurrentStyle();
        String heightText = props == null ? null : props.getHeight();
        if (heightText == null) {
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
	 * @return a int.
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
	 * <p>Getter for the field topLeftVirtualCell.</p>
	 *
	 * @return a {@link org.loboevolution.html.renderer.table.VirtualCell} object.
	 */
	public VirtualCell getTopLeftVirtualCell() {
		return this.topLeftVirtualCell;
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
		AbstractCSSProperties props = this.cellElement.getCurrentStyle();
		String widthText = props == null ? null : props.getWidth();
		if (widthText == null) {
        	return this.cellElement.getCurrentStyle().getWidth();
        } else if ("inherit".equals(widthText)) {
			return this.cellElement.getParentStyle().getWidth();
		} else {
            return widthText;
        }
	}

	/**
	 * <p>getCellElement.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public HTMLElementImpl getCellElement() {
		return cellElement;
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
	 * @param hasBorder a int.
	 * @param cellSpacingX a int.
	 * @param cellSpacingY a int.
	 */
	public void setCellBounds(SizeInfo[] colSizes, SizeInfo[] rowSizes, int hasBorder,
			int cellSpacingX, int cellSpacingY) {
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
        String colSpanText = elem.getAttribute("colspan");
		HTMLDocumentImpl doc =  (HTMLDocumentImpl)elem.getDocumentNode();
        return HtmlValues.getPixelSize(colSpanText, null, doc.getWindow(), 1);
    }
    
    private static int getRowSpan(final HTMLElementImpl elem) {
        String rowSpanText = elem.getAttribute("rowspan");
		HTMLDocumentImpl doc =  (HTMLDocumentImpl)elem.getDocumentNode();
        return HtmlValues.getPixelSize(rowSpanText, null, doc.getWindow(), 1);
    }


	/**
	 * <p>Setter for the field rowSpan.</p>
	 *
	 * @param rowSpan a int.
	 */
	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}

	/**
	 * <p>Setter for the field topLeftVirtualCell.</p>
	 *
	 * @param vc a {@link org.loboevolution.html.renderer.table.VirtualCell} object.
	 */
	public void setTopLeftVirtualCell(VirtualCell vc) {
		this.topLeftVirtualCell = vc;
	}
}
