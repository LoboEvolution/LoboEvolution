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
/*
 * Created on Dec 3, 2005
 */
package org.loboevolution.html.renderertable;

import java.awt.Dimension;

import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.info.SizeInfo;
import org.loboevolution.html.renderer.FrameContext;
import org.loboevolution.html.renderer.RenderableContainer;
import org.loboevolution.html.renderer.VirtualCell;
import org.loboevolution.html.rendererblock.RBlock;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.http.UserAgentContext;

/**
 * The Class RTableCell.
 */
public class RTableCell extends RBlock {

	/** The cell element. */
	private final HTMLElementImpl  cellElement;

	/** The top left virtual cell. */
	private VirtualCell topLeftVirtualCell;
	
	/** The col span. */
	private int colSpan = -1;

	/** The row span. */
	private int rowSpan = -1;

	/**
	 * Instantiates a new r table cell.
	 *
	 * @param element
	 *            the element
	 * @param pcontext
	 *            the pcontext
	 * @param rcontext
	 *            the rcontext
	 * @param frameContext
	 *            the frame context
	 * @param tableAsContainer
	 *            the table as container
	 */
	public RTableCell(HTMLElementImpl  element, UserAgentContext pcontext, HtmlRendererContext rcontext,
			FrameContext frameContext, RenderableContainer tableAsContainer) {
		super(element, 0, pcontext, rcontext, frameContext, tableAsContainer);
		this.cellElement = element;
	}

	/**
	 * Do cell layout.
	 *
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param expandWidth
	 *            the expand width
	 * @param expandHeight
	 *            the expand height
	 * @param sizeOnly
	 *            the size only
	 * @return the dimension
	 */
	protected Dimension doCellLayout(int width, int height, boolean expandWidth, boolean expandHeight,
			boolean sizeOnly) {
		return this.doCellLayout(width, height, expandWidth, expandHeight, sizeOnly, true);
	}

	/**
	 * Do cell layout.
	 *
	 * @param width
	 *            The width available, including insets.
	 * @param height
	 *            The height available, including insets.
	 * @param expandWidth
	 *            the expand width
	 * @param expandHeight
	 *            the expand height
	 * @param sizeOnly
	 *            the size only
	 * @param useCache
	 *            Testing parameter. Should always be true.
	 * @return the dimension
	 */
	protected Dimension doCellLayout(int width, int height, boolean expandWidth, boolean expandHeight, boolean sizeOnly,
			boolean useCache) {
		try {

			this.layout(width, height, expandWidth, expandHeight, null, sizeOnly);
			return new Dimension(this.width, this.height);
		} finally {
			this.layoutUpTreeCanBeInvalidated = true;
			this.layoutDeepCanBeInvalidated = true;
		}
	}

	/**
	 * Clear layout cache.
	 */
	public void clearLayoutCache() {
		// test method
		this.cachedLayout.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.renderer.BaseElementRenderable#getDeclaredHeight(org
	 * .loboevolution.html.renderstate.RenderState, int)
	 */
	@Override
	protected Integer getDeclaredHeight(RenderState renderState, int availHeight) {
		// Overridden since height declaration is handled by table.
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.renderer.BaseElementRenderable#getDeclaredWidth(org.
	 * loboevolution.html.renderstate.RenderState, int)
	 */
	@Override
	protected Integer getDeclaredWidth(RenderState renderState, int availWidth) {
		// Overridden since width declaration is handled by table.
		return null;
	}

	/**
	 * Sets the top left virtual cell.
	 *
	 * @param vc
	 *            the new top left virtual cell
	 */
	public void setTopLeftVirtualCell(VirtualCell vc) {
		this.topLeftVirtualCell = vc;
	}

	/**
	 * Gets the top left virtual cell.
	 *
	 * @return the top left virtual cell
	 */
	public VirtualCell getTopLeftVirtualCell() {
		return this.topLeftVirtualCell;
	}

	/**
	 * Gets the virtual column.
	 *
	 * @return the virtual column
	 */
	public int getVirtualColumn() {
		VirtualCell vc = this.topLeftVirtualCell;
		return vc == null ? 0 : vc.getColumn();
	}

	/**
	 * Gets the virtual row.
	 *
	 * @return the virtual row
	 */
	public int getVirtualRow() {
		VirtualCell vc = this.topLeftVirtualCell;
		return vc == null ? 0 : vc.getRow();
	}

	/**
	 * Gets the col span.
	 *
	 * @return the col span
	 */
	public int getColSpan() {
		int cs = this.colSpan;
		if (cs == -1) {
			cs = getColSpan(this.cellElement);
			if (cs < 1) {
				cs = 1;
			}
			this.colSpan = cs;
		}
		return cs;
	}

	/**
	 * Gets the row span.
	 *
	 * @return the row span
	 */
	public int getRowSpan() {
		int rs = this.rowSpan;
		if (rs == -1) {
			rs = getRowSpan(this.cellElement);
			if (rs < 1) {
				rs = 1;
			}
			this.rowSpan = rs;
		}
		return rs;
	}

	/**
	 * Sets the row span.
	 *
	 * @param rowSpan
	 *            the new row span
	 */
	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}

	/**
	 * Gets the height text.
	 *
	 * @return the height text
	 */
	public String getHeightText() {
		AbstractCSSProperties props = this.cellElement.getCurrentStyle();
		String heightText = props == null ? null : props.getHeight();
		if (heightText == null) {
			return this.cellElement.getCurrentStyle().getHeight();
		} else if (INHERIT.equals(heightText)) {
			return this.cellElement.getParentStyle().getHeight();
		} else {
			return heightText;
		}
	}

	/**
	 * Gets the width text.
	 *
	 * @return the width text
	 */
	public String getWidthText() {
		AbstractCSSProperties props = this.cellElement.getCurrentStyle();
		String widthText = props == null ? null : props.getWidth();

		if (widthText == null) {
			return this.cellElement.getCurrentStyle().getWidth();
		} else if (INHERIT.equals(widthText)) {
			return this.cellElement.getParentStyle().getWidth();
		} else {
			return widthText;
		}
	}

	/**
	 * Sets the cell bounds.
	 *
	 * @param colSizes
	 *            the col sizes
	 * @param rowSizes
	 *            the row sizes
	 * @param hasBorder
	 *            the has border
	 * @param cellSpacingX
	 *            the cell spacing x
	 * @param cellSpacingY
	 *            the cell spacing y
	 */
	public void setCellBounds(SizeInfo[] colSizes, SizeInfo[] rowSizes, int hasBorder, int cellSpacingX,
			int cellSpacingY) {
		int vcol = this.getVirtualColumn();
		int vrow = this.getVirtualRow();
		SizeInfo colSize = colSizes[vcol];
		SizeInfo rowSize = rowSizes[vrow];
		int x = colSize.getOffset();
		int y = rowSize.getOffset();
		int width;
		int height;
		int colSpan = this.getColSpan();
		if (colSpan > 1) {
			width = 0;
			for (int i = 0; i < colSpan; i++) {
				int vc = vcol + i;
				width += colSizes[vc].getActualSize();
				if (i + 1 < colSpan) {
					width += cellSpacingX + hasBorder * 2;
				}
			}
		} else {
			width = colSizes[vcol].getActualSize();
		}
		int rowSpan = this.getRowSpan();
		if (rowSpan > 1) {
			height = 0;
			for (int i = 0; i < rowSpan; i++) {
				int vr = vrow + i;
				height += rowSizes[vr].getActualSize();
				if (i + 1 < rowSpan) {
					height += cellSpacingY + hasBorder * 2;
				}
			}
		} else {
			height = rowSizes[vrow].getActualSize();
		}
		this.setBounds(x, y, width, height);
	}
	
	private static int getColSpan(final HTMLElementImpl elem) {
		String colSpanText = elem.getAttribute(COLSPAN);
		return HtmlValues.getPixelSize(colSpanText, null, 1);
	}
	
	private static int getRowSpan(final HTMLElementImpl elem) {
		String rowSpanText = elem.getAttribute(ROWSPAN);
		return HtmlValues.getPixelSize(rowSpanText, null, 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.renderer.BaseElementRenderable#isMarginBoundary()
	 */
	@Override
	protected boolean isMarginBoundary() {
		return true;
	}
}
