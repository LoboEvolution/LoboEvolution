/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.domfilter.CaptionFilter;
import org.lobobrowser.html.domfilter.ColumnsFilter;
import org.lobobrowser.html.domimpl.DOMNodeImpl;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.domimpl.HTMLTableCaptionElementImpl;
import org.lobobrowser.html.domimpl.HTMLTableCellElementImpl;
import org.lobobrowser.html.domimpl.HTMLTableElementImpl;
import org.lobobrowser.html.domimpl.HTMLTableRowElementImpl;
import org.lobobrowser.html.info.CaptionSizeInfo;
import org.lobobrowser.html.info.SizeInfo;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.CSSValuesProperties;
import org.lobobrowser.html.style.HtmlLength;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.html.style.RenderThreadState;
import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.w3c.html.HTMLTableRowElement;

/**
 * The Class TableMatrix.
 */
public class TableMatrix implements CSSValuesProperties{
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(TableMatrix.class);

	/** The rows. */
	private final ArrayList<ArrayList<VirtualCell>> ROWS = new ArrayList<ArrayList<VirtualCell>>();

	/** The all cells. */
	private final ArrayList<BoundableRenderable> ALL_CELLS = new ArrayList<BoundableRenderable>();

	/** The row elements. */
	private final ArrayList<HTMLTableRowElementImpl> ROW_ELEMENTS = new ArrayList<HTMLTableRowElementImpl>();

	/** The caption. */
	private RTableCaption caption;

	/** The caption element. */
	private HTMLTableCaptionElementImpl captionElement;

	/** The table element. */
	private final HTMLElementImpl tableElement;

	/** The parser context. */
	private final UserAgentContext parserContext;

	/** The renderer context. */
	private final HtmlRendererContext rendererContext;

	/** The frame context. */
	private final FrameContext frameContext;

	/** The relement. */
	private final RElement relement;

	/** The container. */
	private final RenderableContainer container;

	/** The column sizes. */
	private SizeInfo[] columnSizes;

	/** The row sizes. */
	private SizeInfo[] rowSizes;

	/** The caption size. */
	private CaptionSizeInfo captionSize;

	/** The table width. */
	private int tableWidth;

	/** The table height. */
	private int tableHeight;

	/** The has old style border. */
	private int hasOldStyleBorder;

	/** The cell spacing y. */
	private int cellSpacingY;

	/** The cell spacing x. */
	private int cellSpacingX;

	/** The widths of extras. */
	private int widthsOfExtras;

	/** The heights of extras. */
	private int heightsOfExtras;

	/** The table width length. */
	private HtmlLength tableWidthLength;

	/**
	 * Instantiates a new table matrix.
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
	 * @param relement
	 *            the relement
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
	 * Gets the num rows.
	 *
	 * @return the num rows
	 */
	public int getNumRows() {
		return this.ROWS.size();
	}

	/**
	 * Gets the num columns.
	 *
	 * @return the num columns
	 */
	public int getNumColumns() {
		return this.columnSizes.length;
	}

	/**
	 * Gets the table height.
	 *
	 * @return the table height
	 */
	public int getTableHeight() {
		return this.tableHeight;
	}

	/**
	 * Gets the table width.
	 *
	 * @return the table width
	 */
	public int getTableWidth() {
		return this.tableWidth;
	}

	/**
	 * Gets the table height without caption.
	 *
	 * @return the table height without caption
	 */
	public int getTableHeightWithoutCaption() {
		if (this.captionSize != null) {
			return this.tableHeight - this.captionSize.getHeight();
		} else {
			return this.tableHeight;
		}
	}

	/**
	 * Gets the start y without caption.
	 *
	 * @return the start y without caption
	 */
	public int getStartYWithoutCaption() {
		if ((this.captionSize != null) && !isCaptionBotton()) {
			return this.captionSize.getHeight();
		}
		return 0;
	}
	
	/**
	 * Called on every relayout. Element children might have changed.
	 *
	 * @param insets
	 *            the insets
	 * @param availWidth
	 *            the avail width
	 * @param availHeight
	 *            the avail height
	 */
	public void reset(Insets insets, int availWidth, int availHeight) {
		// TODO: Incorporate into build() and calculate
		// sizes properly based on parameters.
		ROWS.clear();
		ALL_CELLS.clear();
		ROW_ELEMENTS.clear();
		// TODO: Does it need this old-style border?
		String borderText = this.tableElement.getAttribute(HtmlAttributeProperties.BORDER);
		int border = 0;
		if (borderText != null) {
			try {
				border = Integer.parseInt(borderText);
				if (border < 0) {
					border = 0;
				}
			} catch (NumberFormatException nfe) {
				// ignore
			}
		}
		String cellSpacingText = this.tableElement.getAttribute(HtmlAttributeProperties.CELLSPACING);
		int cellSpacing = 1;
		if (cellSpacingText != null) {
			try {
				// TODO: cellSpacing can be a percentage as well
				cellSpacing = Integer.parseInt(cellSpacingText);
				if (cellSpacing < 0) {
					cellSpacing = 0;
				}
			} catch (NumberFormatException nfe) {
				// ignore
			}
		}
		this.cellSpacingX = cellSpacing;
		this.cellSpacingY = cellSpacing;

		this.tableWidthLength = TableMatrix.getWidthLength(this.tableElement, availWidth);

		this.populateRows();
		this.adjustForCellSpans();
		this.createSizeArrays();

		// Calculate widths of extras
		SizeInfo[] columnSizes = this.columnSizes;
		int numCols = columnSizes.length;
		int widthsOfExtras = insets.left + insets.right + ((numCols + 1) * cellSpacing);
		if (border > 0) {
			widthsOfExtras += (numCols * 2);
		}
		this.widthsOfExtras = widthsOfExtras;

		// Calculate heights of extras
		SizeInfo[] rowSizes = this.rowSizes;
		int numRows = rowSizes.length;
		int heightsOfExtras = insets.top + insets.bottom + ((numRows + 1) * cellSpacing);
		if (border > 0) {
			heightsOfExtras += (numRows * 2);
		}
		this.heightsOfExtras = heightsOfExtras;
		this.hasOldStyleBorder = border > 0 ? 1 : 0;
	}

	/**
	 * Builds the.
	 *
	 * @param availWidth
	 *            the avail width
	 * @param availHeight
	 *            the avail height
	 * @param sizeOnly
	 *            the size only
	 */
	public void build(int availWidth, int availHeight, boolean sizeOnly) {
		int hasBorder = this.hasOldStyleBorder;
		this.determineColumnSizes(hasBorder, this.cellSpacingX, this.cellSpacingY, availWidth);
		this.determineRowSizes(hasBorder, this.cellSpacingY, availHeight, sizeOnly);
	}

	/**
	 * Gets the parent row.
	 *
	 * @param cellNode
	 *            the cell node
	 * @return the parent row
	 */
	private final HTMLTableRowElementImpl getParentRow(HTMLTableCellElementImpl cellNode) {
		org.w3c.dom.Node parentNode = cellNode.getParentNode();
		for (;;) {
			if (parentNode instanceof HTMLTableRowElementImpl) {
				return (HTMLTableRowElementImpl) parentNode;
			}
			if (parentNode instanceof HTMLTableElementImpl) {
				return null;
			}
			parentNode = parentNode.getParentNode();
		}
	}

	/**
	 * Gets the width length.
	 *
	 * @param element
	 *            the element
	 * @param availWidth
	 *            the avail width
	 * @return the width length
	 */
	private static HtmlLength getWidthLength(HTMLElementImpl element, int availWidth) {
		try {
			AbstractCSS2Properties props = element.getCurrentStyle();
			String widthText = props == null ? null : props.getWidth();
			if (widthText == null) {
				String widthAttr = element.getAttribute(HtmlAttributeProperties.WIDTH);
				if (widthAttr == null) {
					return null;
				}
				return new HtmlLength(widthAttr);
			} else {
				
				if(INHERIT.equals(widthText)){
					widthText = element.getParentStyle().getWidth();
				}
				
				int width = -1;
				
				if (widthText !=null){
					width = HtmlValues.getPixelSize(widthText, element.getRenderState(), 0, availWidth);
				}
				
				if (props.getMaxWidth() != null) {
					int maxWidth = HtmlValues.getPixelSize(props.getMaxWidth(), element.getRenderState(), 0, availWidth);
					
					if (width == 0 || width > maxWidth) {
						width = maxWidth;
					}
				}
				
				if (props.getMinWidth() != null) {
					int minWidth = HtmlValues.getPixelSize(props.getMinWidth(), element.getRenderState(), 0, availWidth);
					
					if (width == 0 || width < minWidth) {
						width = minWidth;
					}
				}
				
				return new HtmlLength(width);
			}
		} catch (Exception err) {
			return null;
		}
	}

	/**
	 * Gets the height length.
	 *
	 * @param element
	 *            the element
	 * @param availHeight
	 *            the avail height
	 * @return the height length
	 */
	private static HtmlLength getHeightLength(HTMLElementImpl element, int availHeight) {
		try {
			AbstractCSS2Properties props = element.getCurrentStyle();
			String heightText = props == null ? null : props.getHeight();
			if (heightText == null) {
				String ha = element.getAttribute(HtmlAttributeProperties.HEIGHT);
				if (ha == null) {
					return null;
				} else {
					return new HtmlLength(ha);
				}
			} else {
				
				if(INHERIT.equals(heightText)){
					heightText = element.getParentStyle().getHeight();
				}
				
				int height = -1;
				
				if (heightText !=null){
					height = HtmlValues.getPixelSize(heightText, element.getRenderState(), 0, availHeight);
				}
				
				if (props.getMaxWidth() != null) {
					int maxHeight = HtmlValues.getPixelSize(props.getMaxHeight(), element.getRenderState(), 0, availHeight);
					
					if (height == 0 || height > maxHeight) {
						height = maxHeight;
					}
				}
				
				if (props.getMinHeight() != null) {
					int minHeight = HtmlValues.getPixelSize(props.getMinHeight(), element.getRenderState(), 0, availHeight);
					
					if (height == 0 || height < minHeight) {
						height = minHeight;
					}
				}
				
				return new HtmlLength(HtmlValues.getPixelSize(heightText, element.getRenderState(), 0, availHeight));
			}
		} catch (Exception err) {
			return null;
		}
	}

	/**
	 * Populates the ROWS and ALL_CELLS collections.
	 */
	private void populateRows() {
		HTMLElementImpl te = this.tableElement;
		ArrayList<ArrayList<VirtualCell>> rows = this.ROWS;
		ArrayList<HTMLTableRowElementImpl> rowElements = this.ROW_ELEMENTS;
		ArrayList<BoundableRenderable> allCells = this.ALL_CELLS;
		Map<HTMLTableRowElementImpl, ArrayList<VirtualCell>> rowElementToRowArray = new HashMap<HTMLTableRowElementImpl, ArrayList<VirtualCell>>(2);
		ArrayList<DOMNodeImpl> cellList = te.getDescendents( new ColumnsFilter(), false);
		ArrayList<VirtualCell> currentNullRow = null;
		Iterator<DOMNodeImpl> ci = cellList.iterator();
		while (ci.hasNext()) {
			HTMLTableCellElementImpl columnNode = (HTMLTableCellElementImpl) ci.next();
			HTMLTableRowElementImpl rowElement = this.getParentRow(columnNode);
			if ((rowElement != null) && (rowElement.getRenderState().getDisplay() == RenderState.DISPLAY_NONE)) {
				// Skip row [ 2047122 ]
				continue;
			}
			ArrayList<VirtualCell> row;
			if (rowElement != null) {
				currentNullRow = null;
				row = rowElementToRowArray.get(rowElement);
				if (row == null) {
					row = new ArrayList<VirtualCell>();
					rowElementToRowArray.put(rowElement, row);
					rows.add(row);
					rowElements.add(rowElement);
				}
			} else {
				// Doesn't have a TR parent. Let's add a ROW just for itself.
				// Both IE and FireFox have this behavior.
				if (currentNullRow != null) {
					row = currentNullRow;
				} else {
					row = new ArrayList<VirtualCell>();
					currentNullRow = row;
					rows.add(row);
					// Null TR element must be added to match.
					rowElements.add(null);
				}
			}
			RTableCell ac = (RTableCell) columnNode.getUINode();
			if (ac == null) {
				// Saved UI nodes must be reused, because they
				// can contain a collection of GUI components.
				ac = new RTableCell(columnNode, this.parserContext, this.rendererContext, this.frameContext,
						this.container);
				ac.setParent(this.relement);
				columnNode.setUINode(ac);
			}
			VirtualCell vc = new VirtualCell(ac, true);
			ac.setTopLeftVirtualCell(vc);
			row.add(vc);
			allCells.add(ac);
		}

		ArrayList<DOMNodeImpl> captionList = te.getDescendents(new CaptionFilter(), false);
		if (captionList.size() > 0) {
			HTMLTableCaptionElementImpl capt = (HTMLTableCaptionElementImpl) captionList.get(0);
			this.captionElement = capt;
			this.caption = new RTableCaption(capt, this.parserContext, this.rendererContext, this.frameContext,
					this.container);
		} else {
			this.caption = null;
		}
	}

	/**
	 * Based on colspans and rowspans, creates additional virtual cells from
	 * actual table cells.
	 */
	private void adjustForCellSpans() {
		ArrayList<ArrayList<VirtualCell>> rows = this.ROWS;
		int numRows = rows.size();
		for (int r = 0; r < numRows; r++) {
			ArrayList<VirtualCell> row = rows.get(r);
			int numCols = row.size();
			for (int c = 0; c < numCols; c++) {
				VirtualCell vc = (VirtualCell) row.get(c);
				if ((vc != null) && vc.isTopLeft()) {
					RTableCell ac = vc.getActualCell();
					int colspan = ac.getColSpan();
					if (colspan < 1) {
						colspan = 1;
					}
					int rowspan = ac.getRowSpan();
					if (rowspan < 1) {
						rowspan = 1;
					}

					// Can't go beyond last row (Fix bug #2022584)
					int targetRows = r + rowspan;
					if (numRows < targetRows) {
						rowspan = numRows - r;
						ac.setRowSpan(rowspan);
					}

					numRows = rows.size();
					for (int y = 0; y < rowspan; y++) {
						if ((colspan > 1) || (y > 0)) {
							// Get row
							int nr = r + y;
							ArrayList<VirtualCell> newRow = rows.get(nr);

							// Insert missing cells in row
							int xstart = y == 0 ? 1 : 0;

							// Insert virtual cells, potentially
							// shifting others to the right.
							for (int cc = xstart; cc < colspan; cc++) {
								int nc = c + cc;
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
			ArrayList<VirtualCell> row = rows.get(r);
			int numCols = row.size();
			for (int c = 0; c < numCols; c++) {
				VirtualCell vc = (VirtualCell) row.get(c);
				if (vc != null) {
					vc.setColumn(c);
					vc.setRow(r);
				}
			}
		}
	}

	/**
	 * Populates the columnSizes and rowSizes arrays, setting htmlLength in each
	 * element.
	 */
	private void createSizeArrays() {
		ArrayList<ArrayList<VirtualCell>> rows = this.ROWS;
		int numRows = rows.size();
		SizeInfo[] rowSizes = new SizeInfo[numRows];
		this.rowSizes = rowSizes;
		int numCols = 0;
		ArrayList<HTMLTableRowElementImpl> rowElements = this.ROW_ELEMENTS;
		for (int i = 0; i < numRows; i++) {
			ArrayList<VirtualCell> row = rows.get(i);
			int rs = row.size();
			if (rs > numCols) {
				numCols = rs;
			}
			SizeInfo rowSizeInfo = new SizeInfo();
			rowSizes[i] = rowSizeInfo;
			HTMLTableRowElement rowElement;
			try {
				rowElement = rowElements.get(i);
				// Possible rowElement is null because TD does not have TR
				// parent
			} catch (IndexOutOfBoundsException iob) {
				// Possible if rowspan expands beyond that
				rowElement = null;
			}
			// TODO: TR.height an IE quirk?
			String rowHeightText = rowElement == null ? null : rowElement.getAttribute(HtmlAttributeProperties.HEIGHT);
			HtmlLength rowHeightLength = null;
			if (rowHeightText != null) {
				try {
					rowHeightLength = new HtmlLength(rowHeightText);
				} catch (Exception err) {
					// ignore
				}
			}
			if (rowHeightLength != null) {
				rowSizeInfo.setHtmlLength(rowHeightLength);
			} else {
				HtmlLength bestHeightLength = null;
				for (int x = 0; x < rs; x++) {
					VirtualCell vc = (VirtualCell) row.get(x);
					if (vc != null) {
						HtmlLength vcHeightLength = vc.getHeightLength();
						if ((vcHeightLength != null) && vcHeightLength.isPreferredOver(bestHeightLength)) {
							bestHeightLength = vcHeightLength;
						}
					}
				}
				rowSizeInfo.setHtmlLength(bestHeightLength);
			}
		}

		SizeInfo[] columnSizes = new SizeInfo[numCols];
		this.columnSizes = columnSizes;
		for (int i = 0; i < numCols; i++) {
			HtmlLength bestWidthLength = null;

			// Cells with colspan==1 first.
			for (int y = 0; y < numRows; y++) {
				ArrayList<VirtualCell> row = rows.get(y);
				VirtualCell vc;
				try {
					vc = (VirtualCell) row.get(i);
				} catch (IndexOutOfBoundsException iob) {
					vc = null;
				}
				if (vc != null) {
					RTableCell ac = vc.getActualCell();
					if (ac.getColSpan() == 1) {
						HtmlLength vcWidthLength = vc.getWidthLength();
						if ((vcWidthLength != null) && vcWidthLength.isPreferredOver(bestWidthLength)) {
							bestWidthLength = vcWidthLength;
						}
					}
				}
			}
			// Now cells with colspan>1.
			if (bestWidthLength == null) {
				for (int y = 0; y < numRows; y++) {
					ArrayList<VirtualCell> row = rows.get(y);
					VirtualCell vc;
					try {
						vc = (VirtualCell) row.get(i);
					} catch (IndexOutOfBoundsException iob) {
						vc = null;
					}
					if (vc != null) {
						RTableCell ac = vc.getActualCell();
						if (ac.getColSpan() > 1) {
							HtmlLength vcWidthLength = vc.getWidthLength();
							if ((vcWidthLength != null) && vcWidthLength.isPreferredOver(bestWidthLength)) {
								bestWidthLength = vcWidthLength;
							}
						}
					}
				}
			}
			SizeInfo colSizeInfo = new SizeInfo();
			colSizeInfo.setHtmlLength(bestWidthLength);
			columnSizes[i] = colSizeInfo;
		}

		if (this.caption != null) {
			this.captionSize = new CaptionSizeInfo();
		}
	}

	/**
	 * Determines the size of each column, and the table width. Does the
	 * following:
	 * <ol>
	 * <li>Determine tentative widths. This is done by looking at declared
	 * column widths, any table width, and filling in the blanks. No rendering
	 * is done. The tentative width of columns with no declared width is zero.
	 *
	 * <li>Render all cell blocks. It uses the tentative widths from the
	 * previous step as a desired width. The resulting width is considered a
	 * sort of minimum. If the column width is not defined, use a NOWRAP
	 * override flag to render.
	 *
	 * <li>Check if cell widths are too narrow for the rendered width. In the
	 * case of columns without a declared width, check if they are too wide.
	 *
	 * <li>Finally, adjust widths considering the expected max table size.
	 * Columns are layed out again if necessary to determine if they can really
	 * be shrunk.
	 * </ol>
	 *
	 * @param hasBorder
	 *            the has border
	 * @param cellSpacingX
	 *            the cell spacing x
	 * @param cellSpacingY
	 *            the cell spacing y
	 * @param availWidth
	 *            the avail width
	 */
	private void determineColumnSizes(int hasBorder, int cellSpacingX, int cellSpacingY, int availWidth) {
		HtmlLength tableWidthLength = this.tableWidthLength;
		int tableWidth;
		boolean widthKnown;
		if (tableWidthLength != null) {
			tableWidth = tableWidthLength.getLength(availWidth);
			widthKnown = true;
		} else {
			tableWidth = availWidth;
			widthKnown = false;
		}
		SizeInfo[] columnSizes = this.columnSizes;
		int widthsOfExtras = this.widthsOfExtras;
		int cellAvailWidth = tableWidth - widthsOfExtras;
		if (cellAvailWidth < 0) {
			// tableWidth += (-cellAvailWidth);
			cellAvailWidth = 0;
		}

		this.determineTentativeSizes(columnSizes, cellAvailWidth);
		this.preLayout(hasBorder, cellSpacingX, cellSpacingY);

		// Increases column widths if they are less than minimums of each cell.
		// por stupid finction maybe actualSize set in previous function - not
		// using layoutSize
		this.adjustForRenderWidths(columnSizes);

		// Adjust for expected total width

		this.adjustWidthsForExpectedMax(columnSizes, cellAvailWidth, widthKnown,
				captionSize != null ? captionSize.getWidth() : 0, widthsOfExtras);
	}

	/**
	 * This method sets the tentative actual sizes of columns (rows) based on
	 * specified witdhs (heights) if available.
	 *
	 * @param columnSizes
	 *            the column sizes
	 * @param cellAvailWidth
	 *            the cell avail width
	 */
	private void determineTentativeSizes(SizeInfo[] columnSizes, int cellAvailWidth) {
		int numCols = columnSizes.length;

		// Look at percentages first
		int widthUsedByPercent = 0;
		for (int i = 0; i < numCols; i++) {
			SizeInfo colSizeInfo = columnSizes[i];
			HtmlLength widthLength = colSizeInfo.getHtmlLength();
			if ((widthLength != null) && (widthLength.getLengthType() == HtmlLength.LENGTH)) {
				int actualSizeInt = widthLength.getLength(cellAvailWidth);
				widthUsedByPercent += actualSizeInt;
				colSizeInfo.setActualSize(actualSizeInt);
			}
		}

		// Look at columns with absolute sizes
		int widthUsedByAbsolute = 0;
		int numNoWidthColumns = 0;
		for (int i = 0; i < numCols; i++) {
			SizeInfo colSizeInfo = columnSizes[i];
			HtmlLength widthLength = colSizeInfo.getHtmlLength();
			if ((widthLength != null) && (widthLength.getLengthType() != HtmlLength.LENGTH)) {
				// TODO: MULTI-LENGTH not supported
				int actualSizeInt = widthLength.getRawValue();
				widthUsedByAbsolute += actualSizeInt;
				colSizeInfo.setActualSize(actualSizeInt);
			} else if (widthLength == null) {
				numNoWidthColumns++;
			}
		}

		// Contract if necessary. This is done again later, but this is
		// an optimization, as it may prevent re-layout. It is only done
		// if all columns have some kind of declared width.

		if (numNoWidthColumns == 0) {
			int totalWidthUsed = widthUsedByPercent + widthUsedByAbsolute;
			int difference = totalWidthUsed - cellAvailWidth;
			// See if absolutes need to be contracted
			if (difference > 0) {
				if (widthUsedByAbsolute > 0) {
					int expectedAbsoluteWidthTotal = widthUsedByAbsolute - difference;
					if (expectedAbsoluteWidthTotal < 0) {
						expectedAbsoluteWidthTotal = 0;
					}
					double ratio = (double) expectedAbsoluteWidthTotal / widthUsedByAbsolute;
					for (int i = 0; i < numCols; i++) {
						SizeInfo sizeInfo = columnSizes[i];
						HtmlLength widthLength = columnSizes[i].getHtmlLength();
						if ((widthLength != null) && (widthLength.getLengthType() != HtmlLength.LENGTH)) {
							int oldActualSize = sizeInfo.getActualSize();
							int newActualSize = (int) Math.round(oldActualSize * ratio);
							sizeInfo.setActualSize(newActualSize);
							totalWidthUsed += (newActualSize - oldActualSize);
						}
					}
					difference = totalWidthUsed - cellAvailWidth;
				}

				// See if percentages need to be contracted
				if (difference > 0) {
					if (widthUsedByPercent > 0) {
						int expectedPercentWidthTotal = widthUsedByPercent - difference;
						if (expectedPercentWidthTotal < 0) {
							expectedPercentWidthTotal = 0;
						}
						double ratio = (double) expectedPercentWidthTotal / widthUsedByPercent;
						for (int i = 0; i < numCols; i++) {
							SizeInfo sizeInfo = columnSizes[i];
							HtmlLength widthLength = columnSizes[i].getHtmlLength();
							if ((widthLength != null) && (widthLength.getLengthType() == HtmlLength.LENGTH)) {
								int oldActualSize = sizeInfo.getActualSize();
								int newActualSize = (int) Math.round(oldActualSize * ratio);
								sizeInfo.setActualSize(newActualSize);
								totalWidthUsed += (newActualSize - oldActualSize);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Contracts column sizes according to render sizes.
	 *
	 * @param columnSizes
	 *            the column sizes
	 */
	private void adjustForRenderWidths(SizeInfo[] columnSizes) {
		int numCols = columnSizes.length;
		for (int i = 0; i < numCols; i++) {
			SizeInfo si = columnSizes[i];
			if (si.getActualSize() < si.getLayoutSize()) {
				si.setActualSize(si.getLayoutSize());
			}
		}
	}

	/**
	 * Layout column.
	 *
	 * @param columnSizes
	 *            the column sizes
	 * @param colSize
	 *            the col size
	 * @param col
	 *            the col
	 * @param cellSpacingX
	 *            the cell spacing x
	 * @param hasBorder
	 *            the has border
	 */
	private void layoutColumn(SizeInfo[] columnSizes, SizeInfo colSize, int col, int cellSpacingX, int hasBorder) {
		SizeInfo[] rowSizes = this.rowSizes;
		ArrayList<ArrayList<VirtualCell>> rows = this.ROWS;
		int numRows = rows.size();
		int actualSize = colSize.getActualSize();
		colSize.setLayoutSize(0);
		for (int row = 0; row < numRows;) {
			// SizeInfo rowSize = rowSizes[row];
			ArrayList<VirtualCell> columns = rows.get(row);
			VirtualCell vc = null;
			try {
				vc = (VirtualCell) columns.get(col);
			} catch (IndexOutOfBoundsException iob) {
				vc = null;
			}
			RTableCell ac = vc == null ? null : vc.getActualCell();
			if (ac != null) {
				if (ac.getVirtualRow() == row) {
					// Only process actual cells with a row
					// beginning at the current row being processed.
					int colSpan = ac.getColSpan();
					if (colSpan > 1) {
						int firstCol = ac.getVirtualColumn();
						int cellExtras = (colSpan - 1) * (cellSpacingX + (2 * hasBorder));
						int vcActualWidth = cellExtras;
						for (int x = 0; x < colSpan; x++) {
							vcActualWidth += columnSizes[firstCol + x].getActualSize();
						}
						// TODO: better height possible

						Dimension size = ac.doCellLayout(vcActualWidth, 0, true, true, true);
						int vcRenderWidth = size.width;

						int denominator = (vcActualWidth - cellExtras);
						int newTentativeCellWidth;
						if (denominator > 0) {
							newTentativeCellWidth = (actualSize * (vcRenderWidth - cellExtras)) / denominator;
						} else {
							newTentativeCellWidth = (vcRenderWidth - cellExtras) / colSpan;
						}
						if (newTentativeCellWidth > colSize.getLayoutSize()) {
							colSize.setLayoutSize(newTentativeCellWidth);
						}
						int rowSpan = ac.getRowSpan();
						int vch = (size.height - ((rowSpan - 1) * (this.cellSpacingY + (2 * hasBorder)))) / rowSpan;
						for (int y = 0; y < rowSpan; y++) {
							if (rowSizes[row + y].getMinSize() < vch) {
								rowSizes[row + y].setMinSize(vch);
							}
						}
					} else {
						// TODO: better height possible
						Dimension size = ac.doCellLayout(actualSize, 0, true, true, true);
						if (size.width > colSize.getLayoutSize()) {
							colSize.setLayoutSize(size.width);
						}
						int rowSpan = ac.getRowSpan();
						int vch = (size.height - ((rowSpan - 1) * (this.cellSpacingY + (2 * hasBorder)))) / rowSpan;
						for (int y = 0; y < rowSpan; y++) {
							if (rowSizes[row + y].getMinSize() < vch) {
								rowSizes[row + y].setMinSize(vch);
							}
						}
					}
				}
			}
			// row = (ac == null ? row + 1 : ac.getVirtualRow() +
			// ac.getRowSpan());
			row++;
		}
	}

	/**
	 * Adjust widths for expected max.
	 *
	 * @param columnSizes
	 *            the column sizes
	 * @param cellAvailWidth
	 *            the cell avail width
	 * @param expand
	 *            the expand
	 * @param captionWith
	 *            the caption with
	 * @param widthOfExtras
	 *            the width of extras
	 * @return the int
	 */
	private int adjustWidthsForExpectedMax(SizeInfo[] columnSizes, int cellAvailWidth, boolean expand, int captionWith,
			int widthOfExtras) {
		int hasBorder = this.hasOldStyleBorder;
		int cellSpacingX = this.cellSpacingX;
		int currentTotal = 0;
		int numCols = columnSizes.length;
		for (int i = 0; i < numCols; i++) {
			currentTotal += columnSizes[i].getActualSize();
		}
		int difference = currentTotal - cellAvailWidth;
		if ((difference > 0) || ((difference < 0) && expand)) {
			// First, try to contract/expand columns with no width
			currentTotal = expandColumns(columnSizes, cellAvailWidth, expand, hasBorder, cellSpacingX, currentTotal,
					numCols, difference);
		}
		if (this.captionSize != null) {
			if ((cellAvailWidth + widthOfExtras) > captionWith) {
				int differenceCaption = currentTotal - captionWith - widthOfExtras;
				if (differenceCaption < 0) {
					currentTotal = expandColumns(columnSizes, captionWith, expand, hasBorder, cellSpacingX,
							currentTotal, numCols, differenceCaption);
				}
				this.captionSize.setWidth(currentTotal + widthOfExtras);
			} else {
				if ((currentTotal + widthOfExtras) > captionWith) {
					this.captionSize.setWidth(currentTotal + widthOfExtras);
				} else {
					currentTotal = expandColumns(columnSizes, captionWith - widthOfExtras, expand, hasBorder,
							cellSpacingX, currentTotal, numCols, currentTotal);
				}
			}
		}
		return currentTotal;
	}

	/**
	 * Expand columns.
	 *
	 * @param columnSizes
	 *            the column sizes
	 * @param cellAvailWidth
	 *            the cell avail width
	 * @param expand
	 *            the expand
	 * @param hasBorder
	 *            the has border
	 * @param cellSpacingX
	 *            the cell spacing x
	 * @param currentTotal
	 *            the current total
	 * @param numCols
	 *            the num cols
	 * @param difference
	 *            the difference
	 * @return the int
	 */
	private int expandColumns(SizeInfo[] columnSizes, int cellAvailWidth, boolean expand, int hasBorder,
			int cellSpacingX, int currentTotal, int numCols, int difference) {
		int noWidthTotal = 0;
		int numNoWidth = 0;
		for (int i = 0; i < numCols; i++) {
			if (columnSizes[i].getHtmlLength() == null) {
				numNoWidth++;
				noWidthTotal += columnSizes[i].getActualSize();
			}
		}
		if (noWidthTotal > 0) {
			// TODO: This is not shrinking correctly.
			int expectedNoWidthTotal = noWidthTotal - difference;
			if (expectedNoWidthTotal < 0) {
				expectedNoWidthTotal = 0;
			}
			double ratio = (double) expectedNoWidthTotal / noWidthTotal;
			int noWidthCount = 0;
			for (int i = 0; i < numCols; i++) {
				SizeInfo sizeInfo = columnSizes[i];
				if (sizeInfo.getHtmlLength() == null) {
					int oldActualSize = sizeInfo.getActualSize();
					int newActualSize;
					if (++noWidthCount == numNoWidth) {
						// Last column without a width.
						int currentDiff = currentTotal - cellAvailWidth;
						newActualSize = oldActualSize - currentDiff;
						if (newActualSize < 0) {
							newActualSize = 0;
						}
					} else {
						newActualSize = (int) Math.round(oldActualSize * ratio);
					}
					sizeInfo.setActualSize(newActualSize);
					if (newActualSize < sizeInfo.getLayoutSize()) {
						// See if it actually fits.
						this.layoutColumn(columnSizes, sizeInfo, i, cellSpacingX, hasBorder);
						if (newActualSize < sizeInfo.getLayoutSize()) {
							// Didn't fit.
							newActualSize = sizeInfo.getLayoutSize();
							sizeInfo.setActualSize(newActualSize);
						}
					}
					currentTotal += (newActualSize - oldActualSize);
				}
			}
			difference = currentTotal - cellAvailWidth;
		}

		// See if absolutes need to be contracted
		if ((difference > 0) || ((difference < 0) && expand)) {
			int absoluteWidthTotal = 0;
			for (int i = 0; i < numCols; i++) {
				HtmlLength widthLength = columnSizes[i].getHtmlLength();
				if ((widthLength != null) && (widthLength.getLengthType() != HtmlLength.LENGTH)) {
					absoluteWidthTotal += columnSizes[i].getActualSize();
				}
			}
			if (absoluteWidthTotal > 0) {
				int expectedAbsoluteWidthTotal = absoluteWidthTotal - difference;
				if (expectedAbsoluteWidthTotal < 0) {
					expectedAbsoluteWidthTotal = 0;
				}
				double ratio = (double) expectedAbsoluteWidthTotal / absoluteWidthTotal;
				for (int i = 0; i < numCols; i++) {
					SizeInfo sizeInfo = columnSizes[i];
					HtmlLength widthLength = columnSizes[i].getHtmlLength();
					if ((widthLength != null) && (widthLength.getLengthType() != HtmlLength.LENGTH)) {
						int oldActualSize = sizeInfo.getActualSize();
						int newActualSize = (int) Math.round(oldActualSize * ratio);
						sizeInfo.setActualSize(newActualSize);
						if (newActualSize < sizeInfo.getLayoutSize()) {
							// See if it actually fits.
							this.layoutColumn(columnSizes, sizeInfo, i, cellSpacingX, hasBorder);
							if (newActualSize < sizeInfo.getLayoutSize()) {
								// Didn't fit.
								newActualSize = sizeInfo.getLayoutSize();
								sizeInfo.setActualSize(newActualSize);
							}
						}
						currentTotal += (newActualSize - oldActualSize);
					}
				}
				difference = currentTotal - cellAvailWidth;
			}

			// See if percentages need to be contracted
			if ((difference > 0) || ((difference < 0) && expand)) {
				int percentWidthTotal = 0;
				for (int i = 0; i < numCols; i++) {
					HtmlLength widthLength = columnSizes[i].getHtmlLength();
					if ((widthLength != null) && (widthLength.getLengthType() == HtmlLength.LENGTH)) {
						percentWidthTotal += columnSizes[i].getActualSize();
					}
				}
				if (percentWidthTotal > 0) {
					int expectedPercentWidthTotal = percentWidthTotal - difference;
					if (expectedPercentWidthTotal < 0) {
						expectedPercentWidthTotal = 0;
					}
					double ratio = (double) expectedPercentWidthTotal / percentWidthTotal;
					for (int i = 0; i < numCols; i++) {
						SizeInfo sizeInfo = columnSizes[i];
						HtmlLength widthLength = columnSizes[i].getHtmlLength();
						if ((widthLength != null) && (widthLength.getLengthType() == HtmlLength.LENGTH)) {
							int oldActualSize = sizeInfo.getActualSize();
							int newActualSize = (int) Math.round(oldActualSize * ratio);
							sizeInfo.setActualSize(newActualSize);
							if (newActualSize < sizeInfo.getLayoutSize()) {
								// See if it actually fits.
								this.layoutColumn(columnSizes, sizeInfo, i, cellSpacingX, hasBorder);
								if (newActualSize < sizeInfo.getLayoutSize()) {
									// Didn't fit.
									newActualSize = sizeInfo.getLayoutSize();
									sizeInfo.setActualSize(newActualSize);
								}
							}
							currentTotal += (newActualSize - oldActualSize);
						}
					}
				}
			}
		}
		return currentTotal;
	}

	/**
	 * This method renders each cell using already set actual column widths. It
	 * sets minimum row heights based on this.
	 *
	 * @param hasBorder
	 *            the has border
	 * @param cellSpacingX
	 *            the cell spacing x
	 * @param cellSpacingY
	 *            the cell spacing y
	 */
	private final void preLayout(int hasBorder, int cellSpacingX,
			int cellSpacingY/* , boolean tableWidthKnown */) {
		// TODO: Fix for table without width that has a subtable with
		// width=100%.
		// TODO: Maybe it can be addressed when NOWRAP is implemented.
		// TODO: Maybe it's possible to eliminate this pre-layout altogether.

		SizeInfo[] colSizes = this.columnSizes;
		SizeInfo[] rowSizes = this.rowSizes;

		// Initialize minSize in rows
		int numRows = rowSizes.length;
		for (int i = 0; i < numRows; i++) {
			rowSizes[i].setMinSize(0);
		}

		// Initialize layoutSize in columns
		int numCols = colSizes.length;
		for (int i = 0; i < numCols; i++) {
			colSizes[i].setLayoutSize(0);
		}

		ArrayList<BoundableRenderable> allCells = this.ALL_CELLS;
		int numCells = allCells.size();
		if (caption != null) {
			caption.doLayout(0, 0, true, true, null, 0, 0, true, true);
			captionSize.setHeight(caption.height);
			captionSize.setWidth(caption.width);
		}
		for (int i = 0; i < numCells; i++) {
			RTableCell cell = (RTableCell) allCells.get(i);
			int col = cell.getVirtualColumn();
			int colSpan = cell.getColSpan();
			int cellsTotalWidth;
			int cellsUsedWidth;
			boolean widthDeclared = false;
			if (colSpan > 1) {
				cellsUsedWidth = 0;
				for (int x = 0; x < colSpan; x++) {
					SizeInfo colSize = colSizes[col + x];
					if (colSize.getHtmlLength() != null) {
						widthDeclared = true;
					}
					cellsUsedWidth += colSize.getActualSize();
				}
				cellsTotalWidth = cellsUsedWidth + ((colSpan - 1) * (cellSpacingX + (2 * hasBorder)));
			} else {
				SizeInfo colSize = colSizes[col];
				if (colSize.getHtmlLength() != null) {
					widthDeclared = true;
				}
				cellsUsedWidth = cellsTotalWidth = colSize.getActualSize();
			}

			// TODO: A tentative height could be used here: Height of
			// table divided by number of rows.

			java.awt.Dimension size;
			RenderThreadState state = RenderThreadState.getState();
			boolean prevOverrideNoWrap = state.overrideNoWrap;
			try {
				if (!prevOverrideNoWrap) {
					state.overrideNoWrap = !widthDeclared;
				}
				size = cell.doCellLayout(cellsTotalWidth, 0, true, true, true);
			} finally {
				state.overrideNoWrap = prevOverrideNoWrap;
			}
			// Set render widths
			int cellLayoutWidth = size.width;
			if (colSpan > 1) {
				if (cellsUsedWidth > 0) {
					double ratio = (double) cellLayoutWidth / cellsUsedWidth;
					for (int x = 0; x < colSpan; x++) {
						SizeInfo si = colSizes[col + x];
						int newLayoutSize = (int) Math.round(si.getActualSize() * ratio);
						if (si.getLayoutSize() < newLayoutSize) {
							si.setLayoutSize(newLayoutSize);
						}
					}
				} else {
					int newLayoutSize = cellLayoutWidth / colSpan;
					for (int x = 0; x < colSpan; x++) {
						SizeInfo si = colSizes[col + x];
						if (si.getLayoutSize() < newLayoutSize) {
							si.setLayoutSize(newLayoutSize);
						}
					}
				}
			} else {
				SizeInfo colSizeInfo = colSizes[col];
				if (colSizeInfo.getLayoutSize() < cellLayoutWidth) {
					colSizeInfo.setLayoutSize(cellLayoutWidth);
				}
			}

			// Set minimum heights
			int actualCellHeight = size.height;
			int row = cell.getVirtualRow();
			int rowSpan = cell.getRowSpan();
			if (rowSpan > 1) {
				int vch = (actualCellHeight - ((rowSpan - 1) * (cellSpacingY + (2 * hasBorder)))) / rowSpan;
				for (int y = 0; y < rowSpan; y++) {
					if (rowSizes[row + y].getMinSize() < vch) {
						rowSizes[row + y].setMinSize(vch);
					}
				}
			} else {
				if (rowSizes[row].getMinSize() < actualCellHeight) {
					rowSizes[row].setMinSize(actualCellHeight);
				}
			}
		}
	}

	/**
	 * Determine row sizes.
	 *
	 * @param hasBorder
	 *            the has border
	 * @param cellSpacing
	 *            the cell spacing
	 * @param availHeight
	 *            the avail height
	 * @param sizeOnly
	 *            the size only
	 */
	private void determineRowSizes(int hasBorder, int cellSpacing, int availHeight, boolean sizeOnly) {
		HtmlLength tableHeightLength = TableMatrix.getHeightLength(this.tableElement, availHeight);
		int tableHeight;
		if (tableHeightLength != null) {
			tableHeight = tableHeightLength.getLength(availHeight);
			this.determineRowSizesFixedTH(hasBorder, cellSpacing, availHeight, tableHeight, sizeOnly);
		} else {
			// zbytocne
			// tableHeight = heightsOfExtras;
			// for(int row = 0; row < numRows; row++) {
			// tableHeight += rowSizes[row].minSize;
			// }
			this.determineRowSizesFlexibleTH(hasBorder, cellSpacing, sizeOnly);
		}
	}

	/**
	 * Determine row sizes fixed th.
	 *
	 * @param hasBorder
	 *            the has border
	 * @param cellSpacing
	 *            the cell spacing
	 * @param availHeight
	 *            the avail height
	 * @param tableHeight
	 *            the table height
	 * @param sizeOnly
	 *            the size only
	 */
	private void determineRowSizesFixedTH(int hasBorder, int cellSpacing, int availHeight, int tableHeight,
			boolean sizeOnly) {
		SizeInfo[] rowSizes = this.rowSizes;
		int numRows = rowSizes.length;
		int heightsOfExtras = this.heightsOfExtras;
		int cellAvailHeight = tableHeight - heightsOfExtras;
		if (cellAvailHeight < 0) {
			cellAvailHeight = 0;
		}

		// Look at percentages first

		int heightUsedbyPercent = 0;
		int otherMinSize = 0;
		for (int i = 0; i < numRows; i++) {
			SizeInfo rowSizeInfo = rowSizes[i];
			HtmlLength heightLength = rowSizeInfo.getHtmlLength();
			if ((heightLength != null) && (heightLength.getLengthType() == HtmlLength.LENGTH)) {
				int actualSizeInt = heightLength.getLength(cellAvailHeight);
				if (actualSizeInt < rowSizeInfo.getMinSize()) {
					actualSizeInt = rowSizeInfo.getMinSize();
				}
				heightUsedbyPercent += actualSizeInt;
				rowSizeInfo.setActualSize(actualSizeInt);
			} else {
				otherMinSize += rowSizeInfo.getMinSize();
			}
		}

		// Check if rows with percent are bigger than they should be

		if ((heightUsedbyPercent + otherMinSize) > cellAvailHeight) {
			double ratio = (double) (cellAvailHeight - otherMinSize) / heightUsedbyPercent;
			for (int i = 0; i < numRows; i++) {
				SizeInfo rowSizeInfo = rowSizes[i];
				HtmlLength heightLength = rowSizeInfo.getHtmlLength();
				if ((heightLength != null) && (heightLength.getLengthType() == HtmlLength.LENGTH)) {
					int actualSize = rowSizeInfo.getActualSize();
					int prevActualSize = actualSize;
					int newActualSize = (int) Math.round(prevActualSize * ratio);
					if (newActualSize < rowSizeInfo.getMinSize()) {
						newActualSize = rowSizeInfo.getMinSize();
					}
					heightUsedbyPercent += (newActualSize - prevActualSize);
					rowSizeInfo.setActualSize(newActualSize);
				}
			}
		}

		// Look at rows with absolute sizes

		int heightUsedByAbsolute = 0;
		int noHeightMinSize = 0;
		int numNoHeightColumns = 0;
		for (int i = 0; i < numRows; i++) {
			SizeInfo rowSizeInfo = rowSizes[i];
			HtmlLength heightLength = rowSizeInfo.getHtmlLength();
			if ((heightLength != null) && (heightLength.getLengthType() != HtmlLength.LENGTH)) {
				// TODO: MULTI-LENGTH not supported
				int actualSizeInt = heightLength.getRawValue();
				if (actualSizeInt < rowSizeInfo.getMinSize()) {
					actualSizeInt = rowSizeInfo.getMinSize();
				}
				heightUsedByAbsolute += actualSizeInt;
				rowSizeInfo.setActualSize(actualSizeInt);
			} else if (heightLength == null) {
				numNoHeightColumns++;
				noHeightMinSize += rowSizeInfo.getMinSize();
			}
		}
		// numNoHeightColumns++;
		// noHeightMinSize += captionSize.height.actualSize;

		// Check if absolute sizing is too much

		if ((heightUsedByAbsolute + heightUsedbyPercent + noHeightMinSize) > cellAvailHeight) {
			double ratio = (double) (cellAvailHeight - noHeightMinSize - heightUsedbyPercent) / heightUsedByAbsolute;
			for (int i = 0; i < numRows; i++) {
				SizeInfo rowSizeInfo = rowSizes[i];
				HtmlLength heightLength = rowSizeInfo.getHtmlLength();
				if ((heightLength != null) && (heightLength.getLengthType() != HtmlLength.LENGTH)) {
					int actualSize = rowSizeInfo.getActualSize();
					int prevActualSize = actualSize;
					int newActualSize = (int) Math.round(prevActualSize * ratio);
					if (newActualSize < rowSizeInfo.getMinSize()) {
						newActualSize = rowSizeInfo.getMinSize();
					}
					heightUsedByAbsolute += (newActualSize - prevActualSize);
					rowSizeInfo.setActualSize(newActualSize);
				}
			}
		}

		// Assign all rows without heights now

		int remainingHeight = cellAvailHeight - heightUsedByAbsolute - heightUsedbyPercent;
		int heightUsedByRemaining = 0;

		for (int i = 0; i < numRows; i++) {
			SizeInfo rowSizeInfo = rowSizes[i];
			HtmlLength heightLength = rowSizeInfo.getHtmlLength();
			if (heightLength == null) {
				int actualSizeInt = remainingHeight / numNoHeightColumns;
				if (actualSizeInt < rowSizeInfo.getMinSize()) {
					actualSizeInt = rowSizeInfo.getMinSize();
				}
				heightUsedByRemaining += actualSizeInt;
				rowSizeInfo.setActualSize(actualSizeInt);
			}
		}
		if (captionSize != null /* && captionSize.height.htmlLength == null */) {
			// captionSize.height.actualSize = captionSize.height.minSize;
			heightUsedByRemaining += captionSize.getHeight();
		}

		// Calculate actual table width

		int totalUsed = heightUsedByAbsolute + heightUsedbyPercent + heightUsedByRemaining;
		if (totalUsed >= cellAvailHeight) {
			this.tableHeight = totalUsed + heightsOfExtras;
		} else {
			// Rows too short; expand them
			double ratio = (double) cellAvailHeight / totalUsed;
			for (int i = 0; i < numRows; i++) {
				SizeInfo rowSizeInfo = rowSizes[i];
				int actualSize = rowSizeInfo.getActualSize();
				rowSizeInfo.setActualSize((int) Math.round(actualSize * ratio));
			}
			this.tableHeight = tableHeight;
		}

		// TODO:
		// This final render is probably unnecessary. Avoid exponential
		// rendering
		// by setting a single height of subcell. Verify that IE only sets
		// height
		// of subcells when height of row or table are specified.

		this.finalRender(hasBorder, cellSpacing, sizeOnly);
	}

	/**
	 * Determine row sizes flexible th.
	 *
	 * @param hasBorder
	 *            the has border
	 * @param cellSpacing
	 *            the cell spacing
	 * @param sizeOnly
	 *            the size only
	 */
	private void determineRowSizesFlexibleTH(int hasBorder, int cellSpacing, boolean sizeOnly) {
		SizeInfo[] rowSizes = this.rowSizes;
		// SizeInfo captionSizeHeight = this.captionSize.height;
		int numRows = rowSizes.length;
		// int heightsOfExtras = this.heightsOfExtras;

		// Look at rows with absolute sizes
		int heightUsedByAbsolute = 0;
		int percentSum = 0;
		for (int i = 0; i < numRows; i++) {
			SizeInfo rowSizeInfo = rowSizes[i];
			HtmlLength heightLength = rowSizeInfo.getHtmlLength();
			if ((heightLength != null) && (heightLength.getLengthType() == HtmlLength.PIXELS)) {
				// TODO: MULTI-LENGTH not supported
				int actualSizeInt = heightLength.getRawValue();
				if (actualSizeInt < rowSizeInfo.getMinSize()) {
					actualSizeInt = rowSizeInfo.getMinSize();
				}
				heightUsedByAbsolute += actualSizeInt;
				rowSizeInfo.setActualSize(actualSizeInt);
			} else if ((heightLength != null) && (heightLength.getLengthType() == HtmlLength.LENGTH)) {
				percentSum += heightLength.getRawValue();
			}
		}

		// Look at rows with no specified heights
		int heightUsedByNoSize = 0;

		// Set sizes to in row height
		for (int i = 0; i < numRows; i++) {
			SizeInfo rowSizeInfo = rowSizes[i];
			HtmlLength widthLength = rowSizeInfo.getHtmlLength();
			if (widthLength == null) {
				int actualSizeInt = rowSizeInfo.getMinSize();
				heightUsedByNoSize += actualSizeInt;
				rowSizeInfo.setActualSize(actualSizeInt);
			}
		}
		/*
		 * if(this.captionSize != null){int actualSizeInt =
		 * this.captionSize.height.minSize; heightUsedByNoSize += actualSizeInt;
		 * this.captionSize.height.actualSize = actualSizeInt;}
		 */

		// Calculate actual total cell width
		int expectedTotalCellHeight = (int) Math
				.round((heightUsedByAbsolute + heightUsedByNoSize) / (1 - (percentSum / 100.0)));

		for (int i = 0; i < numRows; i++) {
			SizeInfo rowSizeInfo = rowSizes[i];
			HtmlLength heightLength = rowSizeInfo.getHtmlLength();
			if ((heightLength != null) && (heightLength.getLengthType() == HtmlLength.LENGTH)) {
				int actualSizeInt = heightLength.getLength(expectedTotalCellHeight);
				if (actualSizeInt < rowSizeInfo.getMinSize()) {
					actualSizeInt = rowSizeInfo.getMinSize();
				}
				rowSizeInfo.setActualSize(actualSizeInt);
			}
		}

		// Do a final render to set actual cell sizes
		this.finalRender(hasBorder, cellSpacing, sizeOnly);
	}

	/**
	 * This method renders each cell using already set actual column widths. It
	 * sets minimum row heights based on this.
	 *
	 * @param hasBorder
	 *            the has border
	 * @param cellSpacing
	 *            the cell spacing
	 * @param sizeOnly
	 *            the size only
	 */
	private final void finalRender(int hasBorder, int cellSpacing, boolean sizeOnly) {
		// finalRender needs to adjust actualSize of columns and rows
		// given that things might change as we render one last time.
		ArrayList<BoundableRenderable> allCells = this.ALL_CELLS;
		SizeInfo[] colSizes = this.columnSizes;
		SizeInfo[] rowSizes = this.rowSizes;
		int numCells = allCells.size();
		for (int i = 0; i < numCells; i++) {
			RTableCell cell = (RTableCell) allCells.get(i);
			int col = cell.getVirtualColumn();
			int colSpan = cell.getColSpan();
			int totalCellWidth;
			if (colSpan > 1) {
				totalCellWidth = (colSpan - 1) * (cellSpacing + (2 * hasBorder));
				for (int x = 0; x < colSpan; x++) {
					totalCellWidth += colSizes[col + x].getActualSize();
				}
			} else {
				totalCellWidth = colSizes[col].getActualSize();
			}
			int row = cell.getVirtualRow();
			int rowSpan = cell.getRowSpan();
			int totalCellHeight;
			if (rowSpan > 1) {
				totalCellHeight = (rowSpan - 1) * (cellSpacing + (2 * hasBorder));
				for (int y = 0; y < rowSpan; y++) {
					totalCellHeight += rowSizes[row + y].getActualSize();
				}
			} else {
				totalCellHeight = rowSizes[row].getActualSize();
			}

			Dimension size = cell.doCellLayout(totalCellWidth, totalCellHeight, true, true, sizeOnly);
			if (size.width > totalCellWidth) {
				if (colSpan == 1) {
					colSizes[col].setActualSize(size.width);
				} else {
					colSizes[col].setActualSize(colSizes[col].getActualSize() + (size.width - totalCellWidth));
				}
			}
			if (size.height > totalCellHeight) {
				if (rowSpan == 1) {
					rowSizes[row].setActualSize(size.width);
				} else {
					rowSizes[row].setActualSize(colSizes[col].getActualSize() + (size.width - totalCellWidth));
				}
			}
		}
		if (this.captionSize != null) {
			this.caption.doLayout(this.captionSize.getWidth(), this.captionSize.getHeight(), true, true, null,
					RenderState.OVERFLOW_NONE, RenderState.OVERFLOW_NONE, sizeOnly, true);
		}
	}

	/**
	 * Sets bounds of each cell's component, and sumps up table width and
	 * height.
	 *
	 * @param insets
	 *            the insets
	 */
	public final void doLayout(Insets insets) {

		// Set row offsets

		SizeInfo[] rowSizes = this.rowSizes;
		int numRows = rowSizes.length;
		int yoffset = insets.top;
		int cellSpacingY = this.cellSpacingY;
		int hasBorder = this.hasOldStyleBorder;

		if ((this.captionSize != null) && !isCaptionBotton()) {
			yoffset += this.captionSize.getHeight();
			yoffset += hasBorder;
		}
		for (int i = 0; i < numRows; i++) {
			yoffset += cellSpacingY;
			yoffset += hasBorder;
			SizeInfo rowSizeInfo = rowSizes[i];
			rowSizeInfo.setOffset(yoffset);
			yoffset += rowSizeInfo.getActualSize();
			yoffset += hasBorder;
		}
		if ((this.captionSize != null) && isCaptionBotton()) {
			this.captionSize.setHeightOffset(yoffset + insets.bottom);
			yoffset += this.captionSize.getHeight();
			yoffset += hasBorder;
		}

		this.tableHeight = yoffset + cellSpacingY + insets.bottom;

		// Set colum offsets

		SizeInfo[] colSizes = this.columnSizes;
		int numColumns = colSizes.length;
		int xoffset = insets.left;
		int cellSpacingX = this.cellSpacingX;
		for (int i = 0; i < numColumns; i++) {
			xoffset += cellSpacingX;
			xoffset += hasBorder;
			SizeInfo colSizeInfo = colSizes[i];
			colSizeInfo.setOffset(xoffset);
			xoffset += colSizeInfo.getActualSize();
			xoffset += hasBorder;
		}

		this.tableWidth = xoffset + cellSpacingX + insets.right;

		// Set offsets of each cell

		ArrayList<BoundableRenderable> allCells = this.ALL_CELLS;
		int numCells = allCells.size();
		for (int i = 0; i < numCells; i++) {
			RTableCell cell = (RTableCell) allCells.get(i);
			cell.setCellBounds(colSizes, rowSizes, hasBorder, cellSpacingX, cellSpacingY);
		}
		if (this.caption != null) {
			this.caption.setBounds(0, this.captionSize.getHeightOffset(), this.tableWidth,
					this.captionSize.getHeight());
		}
	}

	/**
	 * Checks if is caption botton.
	 *
	 * @return true, if is caption botton
	 */
	private boolean isCaptionBotton() {
		if (this.captionElement != null) {
			AbstractCSS2Properties props = captionElement.getCurrentStyle();
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
	 * Paint.
	 *
	 * @param g
	 *            the g
	 * @param size
	 *            the size
	 */
	public final void paint(Graphics g, Dimension size) {
		ArrayList<BoundableRenderable> allCells = this.ALL_CELLS;
		int numCells = allCells.size();
		for (int i = 0; i < numCells; i++) {
			RTableCell cell = (RTableCell) allCells.get(i);
			// Should clip table cells, just in case.
			// just test
			Graphics newG = g.create(cell.x, cell.y, cell.width, cell.height);
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
			for (int i = 0; i < numCells; i++) {
				RTableCell cell = (RTableCell) allCells.get(i);
				int cx = cell.getX() - 1;
				int cy = cell.getY() - 1;
				int cwidth = cell.getWidth() + 1;
				int cheight = cell.getHeight() + 1;
				g.drawRect(cx, cy, cwidth, cheight);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.render.BoundableRenderable#getRenderablePoint(int,
	 * int)
	 */
	/**
	 * Gets the lowest renderable spot.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return the lowest renderable spot
	 */
	public RenderableSpot getLowestRenderableSpot(int x, int y) {
		ArrayList<BoundableRenderable> allCells = this.ALL_CELLS;
		int numCells = allCells.size();
		for (int i = 0; i < numCells; i++) {
			RTableCell cell = (RTableCell) allCells.get(i);
			Rectangle bounds = cell.getBounds();
			if (bounds.contains(x, y)) {
				RenderableSpot rp = cell.getLowestRenderableSpot(x - bounds.x, y - bounds.y);
				if (rp != null) {
					return rp;
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.render.BoundableRenderable#onMouseClick(java.awt.
	 * event .MouseEvent, int, int)
	 */
	/**
	 * On mouse click.
	 *
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	public boolean onMouseClick(MouseEvent event, int x, int y) {
		ArrayList<BoundableRenderable> allCells = this.ALL_CELLS;
		int numCells = allCells.size();
		for (int i = 0; i < numCells; i++) {
			RTableCell cell = (RTableCell) allCells.get(i);
			Rectangle bounds = cell.getBounds();
			if (bounds.contains(x, y)) {
				if (!cell.onMouseClick(event, x - bounds.x, y - bounds.y)) {
					return false;
				}
				break;
			}
		}
		return true;
	}

	/**
	 * On double click.
	 *
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	public boolean onDoubleClick(MouseEvent event, int x, int y) {
		ArrayList<BoundableRenderable> allCells = this.ALL_CELLS;
		int numCells = allCells.size();
		for (int i = 0; i < numCells; i++) {
			RTableCell cell = (RTableCell) allCells.get(i);
			Rectangle bounds = cell.getBounds();
			if (bounds.contains(x, y)) {
				if (!cell.onDoubleClick(event, x - bounds.x, y - bounds.y)) {
					return false;
				}
				break;
			}
		}
		return true;
	}

	/** The armed renderable. */
	private BoundableRenderable armedRenderable;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.render.BoundableRenderable#onMouseDisarmed(java.awt
	 * .event.MouseEvent)
	 */
	/**
	 * On mouse disarmed.
	 *
	 * @param event
	 *            the event
	 * @return true, if successful
	 */
	public boolean onMouseDisarmed(MouseEvent event) {
		BoundableRenderable ar = this.armedRenderable;
		if (ar != null) {
			this.armedRenderable = null;
			return ar.onMouseDisarmed(event);
		} else {
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.render.BoundableRenderable#onMousePressed(java.awt.
	 * event.MouseEvent, int, int)
	 */
	/**
	 * On mouse pressed.
	 *
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	public boolean onMousePressed(MouseEvent event, int x, int y) {
		ArrayList<BoundableRenderable> allCells = this.ALL_CELLS;
		int numCells = allCells.size();
		for (int i = 0; i < numCells; i++) {
			RTableCell cell = (RTableCell) allCells.get(i);
			Rectangle bounds = cell.getBounds();
			if (bounds.contains(x, y)) {
				if (!cell.onMousePressed(event, x - bounds.x, y - bounds.y)) {
					this.armedRenderable = cell;
					return false;
				}
				break;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.render.BoundableRenderable#onMouseReleased(java.awt
	 * .event.MouseEvent, int, int)
	 */
	/**
	 * On mouse released.
	 *
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	public boolean onMouseReleased(MouseEvent event, int x, int y) {
		ArrayList<BoundableRenderable> allCells = this.ALL_CELLS;
		int numCells = allCells.size();
		boolean found = false;
		for (int i = 0; i < numCells; i++) {
			RTableCell cell = (RTableCell) allCells.get(i);
			Rectangle bounds = cell.getBounds();
			if (bounds.contains(x, y)) {
				found = true;
				BoundableRenderable oldArmedRenderable = this.armedRenderable;
				if ((oldArmedRenderable != null) && (cell != oldArmedRenderable)) {
					oldArmedRenderable.onMouseDisarmed(event);
					this.armedRenderable = null;
				}
				if (!cell.onMouseReleased(event, x - bounds.x, y - bounds.y)) {
					return false;
				}
				break;
			}
		}
		if (!found) {
			BoundableRenderable oldArmedRenderable = this.armedRenderable;
			if (oldArmedRenderable != null) {
				oldArmedRenderable.onMouseDisarmed(event);
				this.armedRenderable = null;
			}
		}
		return true;
	}

	/**
	 * Gets the renderables.
	 *
	 * @return the renderables
	 */
	public Iterator<BoundableRenderable> getRenderables() {
		return this.ALL_CELLS.iterator();
	}
}
