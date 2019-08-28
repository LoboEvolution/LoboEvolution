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

import java.awt.Dimension;

import org.lobo.info.SizeInfo;
import org.lobobrowser.html.dom.domimpl.HTMLTableCellElementImpl;
import org.lobobrowser.html.style.AbstractCSSProperties;
import org.lobobrowser.html.style.RenderState;
import org.lobobrowser.http.HtmlRendererContext;
import org.lobobrowser.http.UserAgentContext;

class RTableCell extends RBlock {
	private final HTMLTableCellElementImpl cellElement;
	private int colSpan = -1;

	private int rowSpan = -1;

	private VirtualCell topLeftVirtualCell;
	// private int cellPadding;

//	public void setCellPadding(int value) {
//		this.cellPadding = value;
//	}

	/**
	 * @param element
	 */
	public RTableCell(HTMLTableCellElementImpl element, UserAgentContext pcontext, HtmlRendererContext rcontext,
			FrameContext frameContext, RenderableContainer tableAsContainer) {
		super(element, 0, pcontext, rcontext, frameContext, tableAsContainer);
		this.cellElement = element;
	}

	/**
	 * @param width  The width available, including insets.
	 * @param height The height available, including insets.
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

	@Override
	public void finalize() throws Throwable {
		super.finalize();
	}

	public int getColSpan() {
		int cs = this.colSpan;
		if (cs == -1) {
			cs = this.cellElement.getColSpan();
			if (cs < 1) {
				cs = 1;
			}
			this.colSpan = cs;
		}
		return cs;
	}

	@Override
	protected Integer getDeclaredHeight(RenderState renderState, int availHeight) {
		// Overridden since height declaration is handled by table.
		return null;
	}

	@Override
	protected Integer getDeclaredWidth(RenderState renderState, int availWidth) {
		// Overridden since width declaration is handled by table.
		return null;
	}

	public String getHeightText() {
        AbstractCSSProperties props = this.cellElement.getCurrentStyle();
        String heightText = props == null ? null : props.getHeight();
        if (heightText == null) {
            return this.cellElement.getHeight();
        } else {
            return heightText;
        }
	}

	public int getRowSpan() {
		int rs = this.rowSpan;
		if (rs == -1) {
			rs = this.cellElement.getRowSpan();
			if (rs < 1) {
				rs = 1;
			}
			this.rowSpan = rs;
		}
		return rs;
	}

	public VirtualCell getTopLeftVirtualCell() {
		return this.topLeftVirtualCell;
	}

	/**
	 * @return Returns the virtualColumn.
	 */
	public int getVirtualColumn() {
		final VirtualCell vc = this.topLeftVirtualCell;
		return vc == null ? 0 : vc.getColumn();
	}

	/**
	 * @return Returns the virtualRow.
	 */
	public int getVirtualRow() {
		final VirtualCell vc = this.topLeftVirtualCell;
		return vc == null ? 0 : vc.getRow();
	}

	public String getWidthText() {
        AbstractCSSProperties props = this.cellElement.getCurrentStyle();
        String heightText = props == null ? null : props.getHeight();
        if (heightText == null) {
            return this.cellElement.getHeight();
        } else {
            return heightText;
        }
	}

	@Override
	protected boolean isMarginBoundary() {
		return true;
	}

	public void setCellBounds(SizeInfo[] colSizes, SizeInfo[] rowSizes, int hasBorder,
			int cellSpacingX, int cellSpacingY) {
		final int vcol = getVirtualColumn();
		final int vrow = getVirtualRow();
		final SizeInfo colSize = colSizes[vcol];
		final SizeInfo rowSize = rowSizes[vrow];
		final int x = colSize.offset;
		final int y = rowSize.offset;
		int width;
		int height;
		final int colSpan = getColSpan();
		if (colSpan > 1) {
			width = 0;
			for (int i = 0; i < colSpan; i++) {
				final int vc = vcol + i;
				width += colSizes[vc].actualSize;
				if (i + 1 < colSpan) {
					width += cellSpacingX + hasBorder * 2;
				}
			}
		} else {
			width = colSizes[vcol].actualSize;
		}
		final int rowSpan = getRowSpan();
		if (rowSpan > 1) {
			height = 0;
			for (int i = 0; i < rowSpan; i++) {
				final int vr = vrow + i;
				height += rowSizes[vr].actualSize;
				if (i + 1 < rowSpan) {
					height += cellSpacingY + hasBorder * 2;
				}
			}
		} else {
			height = rowSizes[vrow].actualSize;
		}
		setBounds(x, y, width, height);
	}

	// public Dimension layoutMinWidth() {
//		
//		return this.panel.layoutMinWidth();
//		
//	}
//
//	

	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}

	public void setTopLeftVirtualCell(VirtualCell vc) {
		this.topLeftVirtualCell = vc;
	}
}
