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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.loboevolution.html.dom.domimpl.HTMLCollectionImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLTableCaptionElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLTableElementImpl;
import org.loboevolution.html.dom.filter.CaptionFilter;
import org.loboevolution.html.dom.filter.ColumnsFilter;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.renderer.FrameContext;
import org.loboevolution.html.renderer.RElement;
import org.loboevolution.html.renderer.RenderableContainer;
import org.loboevolution.html.renderer.RenderableSpot;
import org.loboevolution.html.renderstate.RTableCaption;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlLength;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.info.CaptionSizeInfo;
import org.loboevolution.info.SizeInfo;

class TableMatrix {

	final List<HTMLElementImpl> rowElements = new ArrayList<>();
	final List<ArrayList<VirtualCell>> rows = new ArrayList<>();
	private final List<RTableCell> allCells = new ArrayList<>();

	private final RenderableContainer container;
	private final FrameContext frameContext;
	private final UserAgentContext parserContext;
	private final HtmlRendererContext rendererContext;
	private HTMLTableCaptionElementImpl captionElement;
	private final RElement relement;
	private int tableWidth;
	
	protected SizeInfo[] columnSizes;
	protected SizeInfo[] rowSizes;
	protected CaptionSizeInfo captionSize;
	protected RTableCaption caption;
	protected HtmlLength tableWidthLength;
	protected HTMLElementImpl tableElement;

	protected int cellSpacingX;
	protected int cellSpacingY;
	protected int hasOldStyleBorder;
	protected int heightsOfExtras;
	protected int tableHeight;
	protected int widthsOfExtras;	

	/**
	 * <p>Constructor for TableMatrix.</p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param pcontext a {@link org.loboevolution.http.UserAgentContext} object.
	 * @param rcontext a {@link org.loboevolution.http.HtmlRendererContext} object.
	 * @param frameContext a {@link org.loboevolution.html.renderer.FrameContext} object.
	 * @param tableAsContainer a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 * @param relement a {@link org.loboevolution.html.renderer.RElement} object.
	 */
	public TableMatrix(HTMLElementImpl element, UserAgentContext pcontext, HtmlRendererContext rcontext,
			FrameContext frameContext, RenderableContainer tableAsContainer, RElement relement) {
		this.tableElement = element;
		this.parserContext = pcontext;
		this.rendererContext = rcontext;
		this.frameContext = frameContext;
		this.relement = relement;
		this.container = tableAsContainer;
	}
	
	
	/**
	 * Called on every relayout. Element children might have changed.
	 *
	 * @param insets a {@link java.awt.Insets} object.
	 * @param availWidth a int.
	 * @param availHeight a int.
	 */
	public void reset(Insets insets, int availWidth, int availHeight) {
		TableMatrixSizes size = new TableMatrixSizes(this);
		// TODO: Incorporate into build() and calculate
		// sizes properly based on parameters.
		this.rows.clear();
		this.allCells.clear();
		this.rowElements.clear();
		
		final String borderText = this.tableElement.getAttribute("border");
		int border = HtmlValues.getPixelSize(borderText, null, 0);
		final String cellSpacingText = this.tableElement.getAttribute("cellspacing");
		int cellSpacing = HtmlValues.getPixelSize(cellSpacingText, null, 0);
		this.cellSpacingX = cellSpacing;
		this.cellSpacingY = cellSpacing;

		this.tableWidthLength = TableMatrixSizes.getWidthLength(this.tableElement, availWidth);
		
		HTMLCollectionImpl captionList = new HTMLCollectionImpl(tableElement, new CaptionFilter());
		if (captionList.getLength() > 0) {
			HTMLTableCaptionElementImpl capt = (HTMLTableCaptionElementImpl) captionList.item(0);
			this.captionElement = capt;
			this.caption = new RTableCaption(capt, parserContext, rendererContext, frameContext, container);
		} else {
			this.caption = null;
		}

		populateRows();
		adjustForCellSpans();
		size.createSizeArrays();
		
		final SizeInfo[] columnSizes = this.columnSizes;
		final int numCols = columnSizes.length;
		int widthsOfExtras = insets.left + insets.right + (numCols + 1) * cellSpacing;
		if (border > 0) {
			widthsOfExtras += numCols * 2;
		}
		this.widthsOfExtras = widthsOfExtras;

		// Calculate heights of extras
		final SizeInfo[] rowSizes = this.rowSizes;
		final int numRows = rowSizes.length;
		int heightsOfExtras = insets.top + insets.bottom + (numRows + 1) * cellSpacing;
		if (border > 0) {
			heightsOfExtras += numRows * 2;
		}
		this.heightsOfExtras = heightsOfExtras;
		this.hasOldStyleBorder = border > 0 ? 1 : 0;
	}
	
	
	/**
	 * <p>build.</p>
	 *
	 * @param availWidth a int.
	 * @param availHeight a int.
	 * @param sizeOnly a boolean.
	 */
	public void build(int availWidth, int availHeight, boolean sizeOnly) {
		TableMatrixSizes sizes = new TableMatrixSizes(this);
		final int hasBorder = this.hasOldStyleBorder;
		sizes.determineColumnSizes(hasBorder, this.cellSpacingX, this.cellSpacingY, availWidth);
		sizes.determineRowSizes(hasBorder, this.cellSpacingY, availHeight, sizeOnly);
	}

	/**
	 * Sets bounds of each cell's component, and sumps up table width and height.
	 *
	 * @param insets a {@link java.awt.Insets} object.
	 */
	public final void doLayout(Insets insets) {

		SizeInfo[] rowSizes = this.rowSizes;
		int yoffset = insets.top;
		int cellSpacingY = this.cellSpacingY;
		int hasBorder = this.hasOldStyleBorder;

		if (this.captionSize != null && !isCaptionBotton()) {
			yoffset += this.captionSize.getHeight();
			yoffset += hasBorder;
		}
		for (SizeInfo rowSize : rowSizes) {
			yoffset += cellSpacingY;
			yoffset += hasBorder;
			SizeInfo rowSizeInfo = rowSize;
			rowSizeInfo.setOffset(yoffset);
			yoffset += rowSizeInfo.getActualSize();
			yoffset += hasBorder;
		}
		if (this.captionSize != null && isCaptionBotton()) {
			this.captionSize.setHeightOffset(yoffset + insets.bottom);
			yoffset += this.captionSize.getHeight();
			yoffset += hasBorder;
		}

		this.tableHeight = yoffset + cellSpacingY + insets.bottom;

		// Set colum offsets

		SizeInfo[] colSizes = this.columnSizes;
		int xoffset = insets.left;
		int cellSpacingX = this.cellSpacingX;
		for (SizeInfo colSize : colSizes) {
			xoffset += cellSpacingX;
			xoffset += hasBorder;
			SizeInfo colSizeInfo = colSize;
			colSizeInfo.setOffset(xoffset);
			xoffset += colSizeInfo.getActualSize();
			xoffset += hasBorder;
		}

		this.tableWidth = xoffset + cellSpacingX + insets.right;

		List<RTableCell> allCells = this.allCells;
		for (RTableCell cell : allCells) {
			cell.setCellBounds(colSizes, rowSizes, hasBorder, cellSpacingX, cellSpacingY);
		}
		
		if (this.caption != null) {
			this.caption.setBounds(0, this.captionSize.getHeightOffset(), this.tableWidth, this.captionSize.getHeight());
		}
	}
	
	/**
	 * <p>paint.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param size a {@link java.awt.Dimension} object.
	 */
	public final void paint(Graphics g, Dimension size) {
		final List<RTableCell> allCells = this.allCells;
		for (RTableCell rTableCell : allCells) {
			final RTableCell cell = rTableCell;
			final Graphics newG = g.create(cell.x, cell.y, cell.width, cell.height);
			try {
				cell.paint(newG);
			} finally {
				newG.dispose();
			}
		}
		
		if (this.caption != null) {
			Graphics newG = g.create(this.caption.x, this.caption.y, this.caption.width, this.caption.height);
			try {
				this.caption.paint(newG);
			} finally {
				newG.dispose();
			}
		}

		if (this.hasOldStyleBorder > 0) {
			g.setColor(Color.GRAY);
			for (RTableCell cell : allCells) {
				final int cx = cell.getX() - 1;
				final int cy = cell.getY() - 1;
				final int cwidth = cell.getWidth() + 1;
				final int cheight = cell.getHeight() + 1;
				g.drawRect(cx, cy, cwidth, cheight);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void finalize() throws Throwable {
		super.finalize();
	}

	
	/**
	 * <p>getLowestRenderableSpot.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 * @return a {@link org.loboevolution.html.renderer.RenderableSpot} object.
	 */
	public RenderableSpot getLowestRenderableSpot(int x, int y) {
		final List<RTableCell> allCells = this.allCells;
		for (RTableCell cell : allCells) {
			final Rectangle bounds = cell.getVisualBounds();
			if (bounds.contains(x, y)) {
				final RenderableSpot rp = cell.getLowestRenderableSpot(x - bounds.x, y - bounds.y);
				if (rp != null) {
					return rp;
				}
			}
		}
		return null;
	}
		
	private HTMLElementImpl getParentRow(HTMLElementImpl cellNode) {
		Node parentNode = cellNode.getParentNode();
		for (;;) {
			if (parentNode instanceof HTMLElementImpl) {				
				return (HTMLElementImpl) parentNode;
			}
			if (parentNode instanceof HTMLTableElementImpl) {
				return null;
			}
			parentNode = parentNode.getParentNode();
		}
	}
	
	/**
	 * Populates the rows and allCells collections.
	 */
	private void populateRows() {
		final HTMLElementImpl te = this.tableElement;
		final List<ArrayList<VirtualCell>> rows = this.rows;
		final List<HTMLElementImpl> rowElements = this.rowElements;
		final List<RTableCell> allCells = this.allCells;
		final Map<HTMLElementImpl, ArrayList<VirtualCell>> rowElementToRowArray = new HashMap<>();
		final NodeListImpl cellList = (NodeListImpl)te.getDescendents(new ColumnsFilter(), false);

		cellList.forEach( node-> {
			if (node instanceof HTMLElementImpl) {
				final HTMLElementImpl columnNode = (HTMLElementImpl) node;
				final HTMLElementImpl rowElement = getParentRow(columnNode);
				if (rowElement != null && rowElement.getRenderState().getDisplay() != RenderState.DISPLAY_NONE) {
					RTableCell ac = (RTableCell) columnNode.getUINode();
					if (ac == null) {
						ac = new RTableCell(columnNode, this.parserContext, this.rendererContext, this.frameContext, this.container);
						ac.setParent(this.relement);
						columnNode.setUINode(ac);
					}
					final VirtualCell vc = new VirtualCell(ac, true);
					ac.setTopLeftVirtualCell(vc);
					
					ArrayList<VirtualCell> row = rowElementToRowArray.get(rowElement);
					if (row == null) {
						row = new ArrayList<>();
						rowElementToRowArray.put(rowElement, row);
						rows.add(row);
						rowElements.add(rowElement);
					}

					row.add(vc);
					allCells.add(ac);
				}
			}
		});
	}
	
	
	private boolean isCaptionBotton() {
		if (this.captionElement != null) {
			AbstractCSSProperties props = captionElement.getCurrentStyle();
			String captionSide = props == null ? null : props.getCaptionSide();
			if (props == null) {
				captionSide = this.captionElement.getCaptionSide();
			}
			if (captionSide == null) {
				return false;
			} else {
				return "bottom".equals(captionSide);
			}
		}
		return false;
	}
	
	/**
	 * Based on colspans and rowspans, creates additional virtual cells from actual
	 * table cells.
	 */
	private void adjustForCellSpans() {
		final List<ArrayList<VirtualCell>> rows = this.rows;
		int numRows = rows.size();
		for (int r = 0; r < numRows; r++) {
			final List<VirtualCell> row = rows.get(r);
			int numCols = row.size();
			for (int c = 0; c < numCols; c++) {
				final VirtualCell vc = row.get(c);
				if (vc != null && vc.isTopLeft()) {
					final RTableCell ac = vc.getActualCell();
					int colspan = ac.getColSpan();
					if (colspan < 1) {
						colspan = 1;
					}
					int rowspan = ac.getRowSpan();
					if (rowspan < 1) {
						rowspan = 1;
					}

					// Can't go beyond last row (Fix bug #2022584)
					final int targetRows = r + rowspan;
					if (numRows < targetRows) {
						rowspan = numRows - r;
						ac.setRowSpan(rowspan);
					}

					numRows = rows.size();
					for (int y = 0; y < rowspan; y++) {
						if (colspan > 1 || y > 0) {
							// Get row
							final int nr = r + y;
							final List<VirtualCell> newRow = rows.get(nr);

							// Insert missing cells in row
							final int xstart = y == 0 ? 1 : 0;

							// Insert virtual cells, potentially
							// shifting others to the right.
							for (int cc = xstart; cc < colspan; cc++) {
								final int nc = c + cc;
								while (newRow.size() < nc) {
									newRow.add(null);
								}
								newRow.add(nc, new VirtualCell(ac, false));
							}
							if (row == newRow) {
								numCols = row.size();
							}
						}
					}
				}
			}
		}

		// Adjust row and column of virtual cells
		for (int r = 0; r < numRows; r++) {
			final List<VirtualCell> row = rows.get(r);
			final int numCols = row.size();
			for (int c = 0; c < numCols; c++) {
				final VirtualCell vc = row.get(c);
				if (vc != null) {
					vc.setColumn(c);
					vc.setRow(r);
				}
			}
		}
	}

	static void layoutColumn(SizeInfo[] columnSizes, SizeInfo[] rowLenght,
			List<ArrayList<VirtualCell>> ROWS, SizeInfo colSize, int col, int cellSpacingY, int cellSpacingX,
			int hasBorder) {
		SizeInfo[] rowSizes = rowLenght;
		List<ArrayList<VirtualCell>> rows = ROWS;
		int numRows = rows.size();
		int actualSize = colSize.getActualSize();
		colSize.setLayoutSize(0);
		for (int row = 0; row < numRows;) {
			// SizeInfo rowSize = rowSizes[row];
			List<VirtualCell> columns = rows.get(row);
			VirtualCell vc = null;
			int sizeCol = columns.size();
			if (sizeCol > col) {
				vc = columns.get(col);
				RTableCell ac = vc.getActualCell();
				if (ac != null && ac.getVirtualRow() == row) {
					// Only process actual cells with a row
					// beginning at the current row being processed.
					int colSpan = ac.getColSpan();
					if (colSpan > 1) {
						int firstCol = ac.getVirtualColumn();
						int cellExtras = (colSpan - 1) * (cellSpacingX + 2 * hasBorder);
						int vcActualWidth = cellExtras;
						for (int x = 0; x < colSpan; x++) {
							vcActualWidth += columnSizes[firstCol + x].getActualSize();
						}

						Dimension size = ac.doCellLayout(vcActualWidth, 0, true, true, true);
						int vcRenderWidth = size.width;

						int denominator = vcActualWidth - cellExtras;
						int newTentativeCellWidth;
						if (denominator > 0) {
							newTentativeCellWidth = actualSize * (vcRenderWidth - cellExtras) / denominator;
						} else {
							newTentativeCellWidth = (vcRenderWidth - cellExtras) / colSpan;
						}
						if (newTentativeCellWidth > colSize.getLayoutSize()) {
							colSize.setLayoutSize(newTentativeCellWidth);
						}
						int rowSpan = ac.getRowSpan();
						int vch = (size.height - (rowSpan - 1) * (cellSpacingY + 2 * hasBorder)) / rowSpan;
						for (int y = 0; y < rowSpan; y++) {
							if (rowSizes[row + y].getMinSize() < vch) {
								rowSizes[row + y].setMinSize(vch);
							}
						}
					} else {
						Dimension size = ac.doCellLayout(actualSize, 0, true, true, true);
						if (size.width > colSize.getLayoutSize()) {
							colSize.setLayoutSize(size.width);
						}
						int rowSpan = ac.getRowSpan();
						int vch = (size.height - (rowSpan - 1) * (cellSpacingY + 2 * hasBorder)) / rowSpan;
						for (int y = 0; y < rowSpan; y++) {
							if (rowSizes[row + y].getMinSize() < vch) {
								rowSizes[row + y].setMinSize(vch);
							}
						}

					}
				}
			}
			row++;
		}
	}
	
	
	/**
	 * <p>getAllCells.</p>
	 *
	 * @return the allCells
	 */
	public List<RTableCell> getAllCells() {
		return this.allCells;
	}
	
	/**
	 * <p>getNumColumns.</p>
	 *
	 * @return a int.
	 */
	public int getNumColumns() {
		return this.columnSizes.length;
	}

	/**
	 * <p>getNumRows.</p>
	 *
	 * @return a int.
	 */
	public int getNumRows() {
		return this.rows.size();
	}

	/**
	 * <p>getRenderables.</p>
	 *
	 * @return a {@link java.util.Iterator} object.
	 */
	public Iterator<RTableCell> getRenderables() {
		return this.allCells.iterator();
	}

	/**
	 * <p>Getter for the field tableHeight.</p>
	 *
	 * @return Returns the tableHeight.
	 */
	public int getTableHeight() {
		return this.tableHeight;
	}

	/**
	 * <p>Getter for the field tableWidth.</p>
	 *
	 * @return Returns the tableWidth.
	 */
	public int getTableWidth() {
		return this.tableWidth;
	}
}
