/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

import lombok.Getter;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.dom.HTMLTableElement;
import org.loboevolution.html.dom.domimpl.*;
import org.loboevolution.html.dom.filter.CaptionFilter;
import org.loboevolution.html.dom.filter.ColumnsFilter;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.Node;
import org.loboevolution.css.CSSStyleDeclaration;
import org.loboevolution.html.renderer.*;
import org.loboevolution.html.renderer.info.RBlockInfo;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.HtmlLength;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.info.CaptionSizeInfo;
import org.loboevolution.info.SizeInfo;

import java.awt.*;
import java.util.List;
import java.util.*;

class TableMatrix {

	final List<HTMLElementImpl> rowElements = new ArrayList<>();
	final List<ArrayList<VirtualCell>> rows = new ArrayList<>();
	@Getter
	private final List<RTableCell> allCells = new ArrayList<>();

	private final RenderableContainer container;
	private final FrameContext frameContext;
	private final UserAgentContext parserContext;
	private final HtmlRendererContext rendererContext;
	private HTMLElementImpl captionElement;
	private final RElement relement;
	@Getter
	private int tableWidth;
	
	protected SizeInfo[] columnSizes;
	protected SizeInfo[] rowSizes;
	protected CaptionSizeInfo captionSize;
	protected RBlock caption;
	protected HtmlLength tableWidthLength;
	protected HTMLElementImpl tableElement;

	protected int cellSpacingX;
	protected int cellSpacingY;
	protected int hasOldStyleBorder;
	protected int heightsOfExtras;
	@Getter
	protected int tableHeight;
	protected int widthsOfExtras;	

	/**
	 * <p>Constructor for TableMatrix.</p>
	 *
	 * @param info a {@link org.loboevolution.html.renderer.info.RBlockInfo} object.
	 */
	public TableMatrix(final RBlockInfo info, final RElement relement) {
		this.tableElement = (HTMLElementImpl)info.getModelNode();
		this.parserContext = info.getPcontext();
		this.rendererContext = info.getRcontext();
		this.frameContext = info.getFrameContext();
		this.relement = relement;
		this.container = info.getParentContainer();
	}
	
	
	/**
	 * Called on every relayout. Element children might have changed.
	 *
	 * @param insets a {@link java.awt.Insets} object.
	 * @param availWidth a {@link java.lang.Integer} object.
	 * @param availHeight a {@link java.lang.Integer} object.
	 */
	public void reset(final Insets insets, final int availWidth, final int availHeight) {
		final TableMatrixSizes size = new TableMatrixSizes(this);
		// TODO: Incorporate into build() and calculate
		// sizes properly based on parameters.
		this.rows.clear();
		this.allCells.clear();
		this.rowElements.clear();
		final HTMLDocumentImpl doc =  (HTMLDocumentImpl)this.tableElement.getDocumentNode();
		final String borderText;
		if(tableElement instanceof HTMLTableElement){
			borderText = ((HTMLTableElement) tableElement).getBorder();
		} else{
			borderText = tableElement.getAttribute("border");
		}

		final int border = HtmlValues.getPixelSize(borderText, null, doc.getDefaultView(), 0);
		final String cellSpacingText = this.tableElement.getAttribute("cellspacing");
		final int cellSpacing = HtmlValues.getPixelSize(cellSpacingText, null, doc.getDefaultView(), 0);
		this.cellSpacingX = cellSpacing;
		this.cellSpacingY = cellSpacing;

		this.tableWidthLength = TableMatrixSizes.getWidthLength(this.tableElement, availWidth);
		
		final HTMLCollectionImpl captionList = new HTMLCollectionImpl(tableElement, new CaptionFilter());
		if (captionList.getLength() > 0) {
			final HTMLElementImpl capt = (HTMLElementImpl) captionList.item(0);
			this.captionElement = capt;
			this.caption = new RBlock(RBlockInfo.builder()
					.modelNode(capt)
					.listNesting(0)
					.pcontext(parserContext)
					.rcontext(rendererContext)
					.frameContext(frameContext)
					.parentContainer(container)
					.build());
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
	 * @param availWidth a {@link java.lang.Integer} object.
	 * @param availHeight a {@link java.lang.Integer} object.
	 * @param sizeOnly a boolean.
	 */
	public void build(final int availWidth, final int availHeight, final boolean sizeOnly) {
		final TableMatrixSizes sizes = new TableMatrixSizes(this);
		final int hasBorder = this.hasOldStyleBorder;
		sizes.determineColumnSizes(hasBorder, this.cellSpacingX, this.cellSpacingY, availWidth);
		sizes.determineRowSizes(hasBorder, this.cellSpacingY, availHeight, sizeOnly);
	}

	/**
	 * Sets bounds of each cell's component, and sumps up table width and height.
	 *
	 * @param insets a {@link java.awt.Insets} object.
	 */
	public final void doLayout(final Insets insets) {

		final SizeInfo[] rowSizes = this.rowSizes;
		int yoffset = insets.top;
		final int cellSpacingY = this.cellSpacingY;
		final int hasBorder = this.hasOldStyleBorder;

		if (this.captionSize != null && !isCaptionBotton()) {
			yoffset += this.captionSize.getHeight();
			yoffset += hasBorder;
		}
		for (final SizeInfo rowSizeInfo : rowSizes) {
			yoffset += cellSpacingY;
			yoffset += hasBorder;
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

		final SizeInfo[] colSizes = this.columnSizes;
		int xoffset = insets.left;
		final int cellSpacingX = this.cellSpacingX;
		for (final SizeInfo colSizeInfo : colSizes) {
			xoffset += cellSpacingX;
			xoffset += hasBorder;
			colSizeInfo.setOffset(xoffset);
			xoffset += colSizeInfo.getActualSize();
			xoffset += hasBorder;
		}

		this.tableWidth = xoffset + cellSpacingX + insets.right;

		final List<RTableCell> allCells = this.allCells;
		for (final RTableCell cell : allCells) {
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
	public final void paint(final Graphics g, final Dimension size) {
		final List<RTableCell> allCells = this.allCells;
		for (final RTableCell cell : allCells) {
			final Graphics newG = g.create(cell.getX(), cell.getY(), cell.getWidth(), cell.getHeight());
			try {
				cell.paint(newG);
			} finally {
				newG.dispose();
			}
		}
		
		if (this.caption != null) {
			final Graphics newG = g.create(this.caption.getX(), this.caption.getY(), this.caption.getWidth(), this.caption.getHeight());
			try {
				this.caption.paint(newG);
			} finally {
				newG.dispose();
			}
		}

		if (this.hasOldStyleBorder > 0) {
			g.setColor(Color.GRAY);
			for (final RTableCell cell : allCells) {
				final int cx = cell.getX() - 1;
				final int cy = cell.getY() - 1;
				final int cwidth = cell.getWidth() + 1;
				final int cheight = cell.getHeight() + 1;
				g.drawRect(cx, cy, cwidth, cheight);
			}
		}
	}

	
	/**
	 * <p>getLowestRenderableSpot.</p>
	 *
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @return a {@link org.loboevolution.html.renderer.RenderableSpot} object.
	 */
	public RenderableSpot getLowestRenderableSpot(final int x, final int y) {
		final List<RTableCell> allCells = this.allCells;
		for (final RTableCell cell : allCells) {
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
		
	private HTMLElementImpl getParentRow(final HTMLElementImpl cellNode) {
		Node parentNode = cellNode.getParentNode();
		for (;;) {
			if (parentNode instanceof HTMLTableElementImpl) {
				return null;
			}

			if (parentNode instanceof HTMLElementImpl) {
				return (HTMLElementImpl) parentNode;
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
			if (node instanceof HTMLElementImpl columnNode) {
                final HTMLElementImpl rowElement = getParentRow(columnNode);
				if (rowElement != null && rowElement.getRenderState().getDisplay() != RenderState.DISPLAY_NONE) {
					RTableCell ac = (RTableCell) columnNode.getUINode();
					if (ac == null) {
						ac = new RTableCell(RBlockInfo.builder()
								.modelNode(columnNode)
								.pcontext(this.parserContext)
								.rcontext(this.rendererContext)
								.frameContext(this.frameContext)
								.parentContainer(container)
								.build());
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
			final CSSStyleDeclaration props = captionElement.getCurrentStyle();
			String captionSide = props == null ? null : props.getCaptionSide();
			if (props == null) {
				if(captionElement instanceof HTMLTableCaptionElementImpl)
					captionSide = ((HTMLTableCaptionElementImpl)captionElement).getCaptionSide();
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

	static void layoutColumn(final SizeInfo[] columnSizes, final SizeInfo[] rowLenght,
                             final List<ArrayList<VirtualCell>> ROWS, final SizeInfo colSize, final int col, final int cellSpacingY, final int cellSpacingX,
                             final int hasBorder) {
		final SizeInfo[] rowSizes = rowLenght;
		final List<ArrayList<VirtualCell>> rows = ROWS;
		final int numRows = rows.size();
		final int actualSize = colSize.getActualSize();
		colSize.setLayoutSize(0);
		for (int row = 0; row < numRows;) {
			// SizeInfo rowSize = rowSizes[row];
			final List<VirtualCell> columns = rows.get(row);
			VirtualCell vc;
			final int sizeCol = columns.size();
			if (sizeCol > col) {
				vc = columns.get(col);
				final RTableCell ac = vc.getActualCell();
				if (ac != null && ac.getVirtualRow() == row) {
					// Only process actual cells with a row
					// beginning at the current row being processed.
					final int colSpan = ac.getColSpan();
					if (colSpan > 1) {
						final int firstCol = ac.getVirtualColumn();
						final int cellExtras = (colSpan - 1) * (cellSpacingX + 2 * hasBorder);
						int vcActualWidth = cellExtras;
						for (int x = 0; x < colSpan; x++) {
							vcActualWidth += columnSizes[firstCol + x].getActualSize();
						}

						final Dimension size = ac.doCellLayout(vcActualWidth, 0, true, true, true);
						final int vcRenderWidth = size.width;

						final int denominator = vcActualWidth - cellExtras;
						final int newTentativeCellWidth;
						if (denominator > 0) {
							newTentativeCellWidth = actualSize * (vcRenderWidth - cellExtras) / denominator;
						} else {
							newTentativeCellWidth = (vcRenderWidth - cellExtras) / colSpan;
						}
						if (newTentativeCellWidth > colSize.getLayoutSize()) {
							colSize.setLayoutSize(newTentativeCellWidth);
						}
						final int rowSpan = ac.getRowSpan();
						final int vch = (size.height - (rowSpan - 1) * (cellSpacingY + 2 * hasBorder)) / rowSpan;
						for (int y = 0; y < rowSpan; y++) {
							if (rowSizes[row + y].getMinSize() < vch) {
								rowSizes[row + y].setMinSize(vch);
							}
						}
					} else {
						final Dimension size = ac.doCellLayout(actualSize, 0, true, true, true);
						if (size.width > colSize.getLayoutSize()) {
							colSize.setLayoutSize(size.width);
						}
						final int rowSpan = ac.getRowSpan();
						final int vch = (size.height - (rowSpan - 1) * (cellSpacingY + 2 * hasBorder)) / rowSpan;
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
	 * <p>getNumColumns.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getNumColumns() {
		return this.columnSizes.length;
	}

	/**
	 * <p>getNumRows.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getNumRows() {
		return this.rows.size();
	}

	/**
	 * <p>getRenderables.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<RTableCell> getRenderables() {
		return this.allCells;
	}
}
