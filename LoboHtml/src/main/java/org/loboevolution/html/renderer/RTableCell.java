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

import java.awt.Dimension;

import org.loboevolution.info.SizeInfo;
import org.loboevolution.html.dom.HTMLTableCellElement;
import org.loboevolution.html.dom.domimpl.HTMLTableCellElementImpl;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;

class RTableCell extends RBlock {
	
	private final HTMLTableCellElementImpl cellElement;
	
	private int colSpan = -1;

	private int rowSpan = -1;

	private VirtualCell topLeftVirtualCell;

	/**
	 * <p>Constructor for RTableCell.</p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLTableCellElementImpl} object.
	 * @param pcontext a {@link org.loboevolution.http.UserAgentContext} object.
	 * @param rcontext a {@link org.loboevolution.http.HtmlRendererContext} object.
	 * @param frameContext a {@link org.loboevolution.html.renderer.FrameContext} object.
	 * @param tableAsContainer a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 */
	public RTableCell(HTMLTableCellElementImpl element, UserAgentContext pcontext, HtmlRendererContext rcontext,
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
	protected Dimension doCellLayout(int width, int height, boolean expandWidth, boolean expandHeight,
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
	 * @return a {@link org.loboevolution.html.renderer.VirtualCell} object.
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
        String heightText = props == null ? null : props.getHeight();
        if (heightText == null) {
        	return this.cellElement.getCurrentStyle().getWidth();
        } else {
            return heightText;
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
	
    private static int getColSpan(final HTMLTableCellElement elem) {
        String colSpanText = elem.getAttribute("colspan");
        return HtmlValues.getPixelSize(colSpanText, null, 1);
    }
    
    private static int getRowSpan(final HTMLTableCellElement elem) {
        String rowSpanText = elem.getAttribute("rowspan");
        return HtmlValues.getPixelSize(rowSpanText, null, 1);
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
	 * @param vc a {@link org.loboevolution.html.renderer.VirtualCell} object.
	 */
	public void setTopLeftVirtualCell(VirtualCell vc) {
		this.topLeftVirtualCell = vc;
	}
}
