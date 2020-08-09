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
package org.loboevolution.html.renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.loboevolution.common.Nodes;
import org.loboevolution.html.dom.NodeFilter;
import org.loboevolution.html.dom.domimpl.HTMLCollectionImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLTableCaptionElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLTableElementImpl;
import org.loboevolution.html.dom.filter.CaptionFilter;
import org.loboevolution.html.dom.filter.ColumnsFilter;
import org.loboevolution.html.renderstate.RTableCaption;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.RenderThreadState;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlLength;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.info.CaptionSizeInfo;
import org.loboevolution.info.SizeInfo;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class TableMatrix {

	private static final NodeFilter COLUMNS_FILTER = new ColumnsFilter();
	
	private final List<HTMLElementImpl> ROW_ELEMENTS = new ArrayList<HTMLElementImpl>();
	private final List<ArrayList<VirtualCell>> ROWS = new ArrayList<ArrayList<VirtualCell>>();
	private final List<RTableCell> ALL_CELLS = new ArrayList<RTableCell>();
	
	private SizeInfo[] columnSizes;
	private SizeInfo[] rowSizes;
	private CaptionSizeInfo captionSize;
	
	private final RenderableContainer container;
	private final FrameContext frameContext;
	private final UserAgentContext parserContext;
	private final HtmlRendererContext rendererContext;

	private int cellSpacingX;
	private int cellSpacingY;
	private int hasOldStyleBorder;
	private int heightsOfExtras;
	private int tableHeight;
	private int tableWidth;
	private int widthsOfExtras;

	private final RElement relement;
	private final HTMLElementImpl tableElement;
	private HTMLTableCaptionElementImpl captionElement;
	private BoundableRenderable armedRenderable;

	private RTableCaption caption;
	private HtmlLength tableWidthLength;
	

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
	 * <p>getWidthLength.</p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param availWidth a int.
	 * @return a {@link org.loboevolution.html.style.HtmlLength} object.
	 */
	public static HtmlLength getWidthLength(HTMLElementImpl element, int availWidth) {
		try {
			AbstractCSSProperties props = element.getCurrentStyle();
			String widthText = props == null ? null : props.getWidth();
			if (widthText == null) {
				String widthAttr = element.getAttribute("width");
				if (widthAttr == null) {
					return null;
				}
				return new HtmlLength(widthAttr);
			} else {

				if ("inherit".equals(widthText)) {
					widthText = element.getParentStyle().getWidth();
				}

				int width = -1;

				if (widthText != null) {
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
	public static HtmlLength getHeightLength(HTMLElementImpl element, int availHeight) {
		try {
			AbstractCSSProperties props = element.getCurrentStyle();
			String heightText = props == null ? null : props.getHeight();
			if (heightText == null) {
				String ha = element.getAttribute("height");
				if (ha == null) {
					return null;
				} else {
					return new HtmlLength(ha);
				}
			} else {

				if ("inherit".equals(heightText)) {
					heightText = element.getParentStyle().getHeight();
				}

				int height = -1;

				if (heightText != null) {
					height = HtmlValues.getPixelSize(heightText, element.getRenderState(), 0, availHeight);
				}

				if (props.getMaxHeight() != null) {
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
	 * Based on colspans and rowspans, creates additional virtual cells from actual
	 * table cells.
	 */
	private void adjustForCellSpans() {
		final List<ArrayList<VirtualCell>> rows = this.ROWS;
		int numRows = rows.size();
		for (int r = 0; r < numRows; r++) {
			final List<VirtualCell> row = rows.get(r);
			int numCols = row.size();
			for (int c = 0; c < numCols; c++) {
				final VirtualCell vc = (VirtualCell) row.get(c);
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
							final List<VirtualCell> newRow = (ArrayList<VirtualCell>) rows.get(nr);

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
				final VirtualCell vc = (VirtualCell) row.get(c);
				if (vc != null) {
					vc.setColumn(c);
					vc.setRow(r);
				}
			}
		}
	}

	/**
	 * Contracts column sizes according to render sizes.
	 */
	private void adjustForRenderWidths(SizeInfo[] columnSizes, int hasBorder, int cellSpacing,
			boolean tableWidthKnown) {
		final int numCols = columnSizes.length;
		for (int i = 0; i < numCols; i++) {
			final SizeInfo si = columnSizes[i];
			if (si.getActualSize() < si.getLayoutSize()) {
				si.setActualSize(si.getLayoutSize());
			}
		}
	}

	private int adjustWidthsForExpectedMax(SizeInfo[] columnSizes, int cellAvailWidth, boolean expand, int captionWith, int widthOfExtras) {
		final int hasBorder = this.hasOldStyleBorder;
		final int cellSpacingX = this.cellSpacingX;
		int currentTotal = 0;
		final int numCols = columnSizes.length;
		for (int i = 0; i < numCols; i++) {
			currentTotal += columnSizes[i].getActualSize();
		}
		int difference = currentTotal - cellAvailWidth;
		if (difference > 0 || difference < 0 && expand) {
			// First, try to contract/expand columns with no width
			currentTotal = expandColumns(columnSizes, cellAvailWidth, expand, hasBorder, cellSpacingX, currentTotal, numCols, difference);
		}
		
		if (this.captionSize != null) {
			if (cellAvailWidth + widthOfExtras > captionWith) {
				int differenceCaption = currentTotal - captionWith - widthOfExtras;
				if (differenceCaption < 0) {
					currentTotal = expandColumns(columnSizes, captionWith, expand, hasBorder, cellSpacingX, currentTotal, numCols, differenceCaption);
				}
				this.captionSize.setWidth(currentTotal + widthOfExtras);
			} else {
				if (currentTotal + widthOfExtras > captionWith) {
					this.captionSize.setWidth(currentTotal + widthOfExtras);
				} else {
					currentTotal = expandColumns(columnSizes, captionWith - widthOfExtras, expand, hasBorder, cellSpacingX, currentTotal, numCols, currentTotal);
				}
			}
		}
		
		return currentTotal;
	}

	/**
	 * <p>build.</p>
	 *
	 * @param availWidth a int.
	 * @param availHeight a int.
	 * @param sizeOnly a boolean.
	 */
	public void build(int availWidth, int availHeight, boolean sizeOnly) {
		final int hasBorder = this.hasOldStyleBorder;
		determineColumnSizes(hasBorder, this.cellSpacingX, this.cellSpacingY, availWidth);
		determineRowSizes(hasBorder, this.cellSpacingY, availHeight, sizeOnly);
	}

	/**
	 * Populates the columnSizes and rowSizes arrays, setting htmlLength in each
	 * element.
	 */
	private void createSizeArrays() {
		final List<ArrayList<VirtualCell>> rows = this.ROWS;
		final int numRows = rows.size();
		final SizeInfo[] rowSizes = new SizeInfo[numRows];
		this.rowSizes = rowSizes;
		int numCols = 0;
		final List<HTMLElementImpl> rowElements = this.ROW_ELEMENTS;
		for (int i = 0; i < numRows; i++) {
			final List<VirtualCell> row = rows.get(i);
			final int rs = row.size();
			if (rs > numCols) {
				numCols = rs;
			}
			final SizeInfo rowSizeInfo = new SizeInfo();
			rowSizes[i] = rowSizeInfo;
			HTMLElementImpl rowElement = rowElements.size() > i ? (HTMLElementImpl)rowElements.get(i) : null;
			final String rowHeightText = rowElement == null ? null : rowElement.getAttribute("height");
			HtmlLength rowHeightLength = null;
			if (rowHeightText != null) {
				try {
                    rowHeightLength = new HtmlLength(HtmlValues.getPixelSize(rowHeightText, rowElement.getRenderState(), 0));
				} catch (final Exception err) {
					// ignore
				}
			}
			if (rowHeightLength != null) {
				rowSizeInfo.setHtmlLength(rowHeightLength);
			} else {
				HtmlLength bestHeightLength = null;
				for (int x = 0; x < rs; x++) {
					final VirtualCell vc = (VirtualCell) row.get(x);
					if (vc != null) {
						final HtmlLength vcHeightLength = vc.getHeightLength();
						if (vcHeightLength != null && vcHeightLength.isPreferredOver(bestHeightLength)) {
							bestHeightLength = vcHeightLength;
						}
					}
				}
				rowSizeInfo.setHtmlLength(bestHeightLength);
			}
		}
		final SizeInfo[] columnSizes = new SizeInfo[numCols];
		this.columnSizes = columnSizes;
		for (int i = 0; i < numCols; i++) {
			HtmlLength bestWidthLength = null;

			// Cells with colspan==1 first.
			for (int y = 0; y < numRows; y++) {
				final List<VirtualCell> row = rows.get(y);
				VirtualCell vc = row.size() > i ? row.get(i) : null;
				if (vc != null) {
					final RTableCell ac = vc.getActualCell();
					if (ac.getColSpan() == 1) {
						final HtmlLength vcWidthLength = vc.getWidthLength();
						if (vcWidthLength != null && vcWidthLength.isPreferredOver(bestWidthLength)) {
							bestWidthLength = vcWidthLength;
						}
					}
				}
			}
			// Now cells with colspan>1.
			if (bestWidthLength == null) {
				for (int y = 0; y < numRows; y++) {
					final List<VirtualCell> row = rows.get(y);
					VirtualCell vc = row.size() > i ? row.get(i) : null;
					if (vc != null) {
						final RTableCell ac = vc.getActualCell();
						if (ac.getColSpan() > 1) {
							final HtmlLength vcWidthLength = vc.getWidthLength();
							if (vcWidthLength != null && vcWidthLength.isPreferredOver(bestWidthLength)) {
								bestWidthLength = vcWidthLength;
							}
						}
					}
				}
			}
			final SizeInfo colSizeInfo = new SizeInfo();
			colSizeInfo.setHtmlLength(bestWidthLength);
			columnSizes[i] = colSizeInfo;
		}
		
		if (this.caption != null) {
			this.captionSize = new CaptionSizeInfo();
		}
	}

	/**
	 * Determines the size of each column, and the table width. Does the following:
	 * <ol>
	 * <li>Determine tentative widths. This is done by looking at declared column
	 * widths, any table width, and filling in the blanks. No rendering is done. The
	 * tentative width of columns with no declared width is zero.
	 * 
	 * <li>Render all cell blocks. It uses the tentative widths from the previous
	 * step as a desired width. The resulting width is considered a sort of minimum.
	 * If the column width is not defined, use a NOWRAP override flag to render.
	 * 
	 * <li>Check if cell widths are too narrow for the rendered width. In the case
	 * of columns without a declared width, check if they are too wide.
	 * 
	 * <li>Finally, adjust widths considering the expected max table size. Columns
	 * are layed out again if necessary to determine if they can really be shrunk.
	 * </ol>
	 * 
	 * @param renderState
	 * @param border
	 * @param cellSpacingX
	 * @param cellSpacingY
	 * @param availWidth
	 */
	private void determineColumnSizes(int hasBorder, int cellSpacingX, int cellSpacingY, int availWidth) {
		final HtmlLength tableWidthLength = this.tableWidthLength;
		int tableWidth;
		boolean widthKnown;
		if (tableWidthLength != null) {
			tableWidth = tableWidthLength.getLength(availWidth);
			widthKnown = true;
		} else {
			tableWidth = availWidth;
			widthKnown = false;
		}
		final SizeInfo[] columnSizes = this.columnSizes;
		final int widthsOfExtras = this.widthsOfExtras;
		int cellAvailWidth = tableWidth - widthsOfExtras;
		if (cellAvailWidth < 0) {
			tableWidth += -cellAvailWidth;
			cellAvailWidth = 0;
		}

		// Determine tentative column widths based on specified cell widths

		determineTentativeSizes(columnSizes, widthsOfExtras, cellAvailWidth, widthKnown);

		// Pre-render cells. This will give the minimum width of each cell,
		// in addition to the minimum height.

		preLayout(hasBorder, cellSpacingX, cellSpacingY, widthKnown);

		// Increases column widths if they are less than minimums of each cell.

		adjustForRenderWidths(columnSizes, hasBorder, cellSpacingX, widthKnown);

		// Adjust for expected total width

		adjustWidthsForExpectedMax(columnSizes, cellAvailWidth, widthKnown,
				captionSize != null ? captionSize.getWidth() : 0, widthsOfExtras);
	}

	private void determineRowSizes(int hasBorder, int cellSpacing, int availHeight, boolean sizeOnly) {
		final HtmlLength tableHeightLength = TableMatrix.getHeightLength(this.tableElement, availHeight);
		int tableHeight;
		final SizeInfo[] rowSizes = this.rowSizes;
		final int numRows = rowSizes.length;
		final int heightsOfExtras = this.heightsOfExtras;
		if (tableHeightLength != null) {
			tableHeight = tableHeightLength.getLength(availHeight);
			determineRowSizesFixedTH(hasBorder, cellSpacing, availHeight, tableHeight, sizeOnly);
		} else {
			tableHeight = heightsOfExtras;
			for (int row = 0; row < numRows; row++) {
				tableHeight += rowSizes[row].getMinSize();
			}
			determineRowSizesFlexibleTH(hasBorder, cellSpacing, availHeight, sizeOnly);
		}
	}

	private void determineRowSizesFixedTH(int hasBorder, int cellSpacing, int availHeight, int tableHeight,
			boolean sizeOnly) {
		final SizeInfo[] rowSizes = this.rowSizes;
		final int numRows = rowSizes.length;
		final int heightsOfExtras = this.heightsOfExtras;
		int cellAvailHeight = tableHeight - heightsOfExtras;
		if (cellAvailHeight < 0) {
			cellAvailHeight = 0;
		}

		// Look at percentages first

		int heightUsedbyPercent = 0;
		int otherMinSize = 0;
		for (int i = 0; i < numRows; i++) {
			final SizeInfo rowSizeInfo = rowSizes[i];
			final HtmlLength heightLength = (HtmlLength)rowSizeInfo.getHtmlLength();
			if (heightLength != null && heightLength.getLengthType() == HtmlLength.LENGTH) {
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

		if (heightUsedbyPercent + otherMinSize > cellAvailHeight) {
			final double ratio = (double) (cellAvailHeight - otherMinSize) / heightUsedbyPercent;
			for (int i = 0; i < numRows; i++) {
				final SizeInfo rowSizeInfo = rowSizes[i];
				final HtmlLength heightLength = (HtmlLength)rowSizeInfo.getHtmlLength();
				if (heightLength != null && heightLength.getLengthType() == HtmlLength.LENGTH) {
					final int actualSize = rowSizeInfo.getActualSize();
					final int prevActualSize = actualSize;
					int newActualSize = (int) Math.round(prevActualSize * ratio);
					if (newActualSize < rowSizeInfo.getMinSize()) {
						newActualSize = rowSizeInfo.getMinSize();
					}
					heightUsedbyPercent += newActualSize - prevActualSize;
					rowSizeInfo.setActualSize(newActualSize);
				}
			}
		}

		// Look at rows with absolute sizes

		int heightUsedByAbsolute = 0;
		int noHeightMinSize = 0;
		int numNoHeightColumns = 0;
		for (int i = 0; i < numRows; i++) {
			final SizeInfo rowSizeInfo = rowSizes[i];
			final HtmlLength heightLength = (HtmlLength)rowSizeInfo.getHtmlLength();
			if (heightLength != null && heightLength.getLengthType() != HtmlLength.LENGTH) {
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

		if (heightUsedByAbsolute + heightUsedbyPercent + noHeightMinSize > cellAvailHeight) {
			final double ratio = (double) (cellAvailHeight - noHeightMinSize - heightUsedbyPercent)
					/ heightUsedByAbsolute;
			for (int i = 0; i < numRows; i++) {
				final SizeInfo rowSizeInfo = rowSizes[i];
				final HtmlLength heightLength = (HtmlLength)rowSizeInfo.getHtmlLength();
				if (heightLength != null && heightLength.getLengthType() != HtmlLength.LENGTH) {
					final int actualSize = rowSizeInfo.getActualSize();
					final int prevActualSize = actualSize;
					int newActualSize = (int) Math.round(prevActualSize * ratio);
					if (newActualSize < rowSizeInfo.getMinSize()) {
						newActualSize = rowSizeInfo.getMinSize();
					}
					heightUsedByAbsolute += newActualSize - prevActualSize;
					rowSizeInfo.setActualSize(newActualSize);
				}
			}
		}

		// Assign all rows without heights now

		final int remainingHeight = cellAvailHeight - heightUsedByAbsolute - heightUsedbyPercent;
		int heightUsedByRemaining = 0;
		for (int i = 0; i < numRows; i++) {
			final SizeInfo rowSizeInfo = rowSizes[i];
			final HtmlLength heightLength = (HtmlLength)rowSizeInfo.getHtmlLength();
			if (heightLength == null) {
				int actualSizeInt = remainingHeight / numNoHeightColumns;
				if (actualSizeInt < rowSizeInfo.getMinSize()) {
					actualSizeInt = rowSizeInfo.getMinSize();
				}
				heightUsedByRemaining += actualSizeInt;
				rowSizeInfo.setActualSize(actualSizeInt);
			}
		}
		
		if (captionSize != null) {
			heightUsedByRemaining += captionSize.getHeight();
		}

		// Calculate actual table width

		final int totalUsed = heightUsedByAbsolute + heightUsedbyPercent + heightUsedByRemaining;
		if (totalUsed >= cellAvailHeight) {
			this.tableHeight = totalUsed + heightsOfExtras;
		} else {
			// Rows too short; expand them
			final double ratio = (double) cellAvailHeight / totalUsed;
			for (int i = 0; i < numRows; i++) {
				final SizeInfo rowSizeInfo = rowSizes[i];
				final int actualSize = rowSizeInfo.getActualSize();
				rowSizeInfo.setActualSize((int) Math.round(actualSize * ratio));
			}
			this.tableHeight = tableHeight;
		}

		finalRender(hasBorder, cellSpacing, sizeOnly);
	}

	private void determineRowSizesFlexibleTH(int hasBorder, int cellSpacing, int availHeight, boolean sizeOnly) {
		final SizeInfo[] rowSizes = this.rowSizes;
		final int numRows = rowSizes.length;
		final int heightsOfExtras = this.heightsOfExtras;

		// Look at rows with absolute sizes
		int heightUsedByAbsolute = 0;
		int percentSum = 0;
		for (int i = 0; i < numRows; i++) {
			final SizeInfo rowSizeInfo = rowSizes[i];
			final HtmlLength heightLength = (HtmlLength)rowSizeInfo.getHtmlLength();
			if (heightLength != null && heightLength.getLengthType() == HtmlLength.PIXELS) {
				int actualSizeInt = heightLength.getRawValue();
				if (actualSizeInt < rowSizeInfo.getMinSize()) {
					actualSizeInt = rowSizeInfo.getMinSize();
				}
				heightUsedByAbsolute += actualSizeInt;
				rowSizeInfo.setActualSize(actualSizeInt);
			} else if (heightLength != null && heightLength.getLengthType() == HtmlLength.LENGTH) {
				percentSum += heightLength.getRawValue();
			}
		}

		// Look at rows with no specified heights
		int heightUsedByNoSize = 0;

		// Set sizes to in row height
		for (int i = 0; i < numRows; i++) {
			final SizeInfo rowSizeInfo = rowSizes[i];
			final HtmlLength widthLength = (HtmlLength)rowSizeInfo.getHtmlLength();
			if (widthLength == null) {
				final int actualSizeInt = rowSizeInfo.getMinSize();
				heightUsedByNoSize += actualSizeInt;
				rowSizeInfo.setActualSize(actualSizeInt);
			}
		}

		// Calculate actual total cell width
		final int expectedTotalCellHeight = (int) Math
				.round((heightUsedByAbsolute + heightUsedByNoSize) / (1 - percentSum / 100.0));

		// Set widths of columns with percentages
		int heightUsedByPercent = 0;
		for (int i = 0; i < numRows; i++) {
			final SizeInfo rowSizeInfo = rowSizes[i];
			final HtmlLength heightLength = (HtmlLength)rowSizeInfo.getHtmlLength();
			if (heightLength != null && heightLength.getLengthType() == HtmlLength.LENGTH) {
				int actualSizeInt = heightLength.getLength(expectedTotalCellHeight);
				if (actualSizeInt < rowSizeInfo.getMinSize()) {
					actualSizeInt = rowSizeInfo.getMinSize();
				}
				heightUsedByPercent += actualSizeInt;
				rowSizeInfo.setActualSize(actualSizeInt);
			}
		}

		// Set width of table
		this.tableHeight = heightUsedByAbsolute + heightUsedByNoSize + heightUsedByPercent + heightsOfExtras;

		// Do a final render to set actual cell sizes
		finalRender(hasBorder, cellSpacing, sizeOnly);
	}

	/**
	 * This method sets the tentative actual sizes of columns (rows) based on
	 * specified witdhs (heights) if available.
	 * 
	 * @param columnSizes
	 * @param widthsOfExtras
	 * @param cellAvailWidth
	 */
	private void determineTentativeSizes(SizeInfo[] columnSizes, int widthsOfExtras, int cellAvailWidth,
			boolean setNoWidthColumns) {
		final int numCols = columnSizes.length;

		// Look at percentages first
		int widthUsedByPercent = 0;
		for (int i = 0; i < numCols; i++) {
			final SizeInfo colSizeInfo = columnSizes[i];
			final HtmlLength widthLength = (HtmlLength)colSizeInfo.getHtmlLength();
			if (widthLength != null && widthLength.getLengthType() == HtmlLength.LENGTH) {
				final int actualSizeInt = widthLength.getLength(cellAvailWidth);
				widthUsedByPercent += actualSizeInt;
				colSizeInfo.setActualSize(actualSizeInt);
			}
		}

		// Look at columns with absolute sizes
		int widthUsedByAbsolute = 0;
		int numNoWidthColumns = 0;
		for (int i = 0; i < numCols; i++) {
			final SizeInfo colSizeInfo = columnSizes[i];
			final HtmlLength widthLength = (HtmlLength)colSizeInfo.getHtmlLength();
			if (widthLength != null && widthLength.getLengthType() != HtmlLength.LENGTH) {
				final int actualSizeInt = widthLength.getRawValue();
				widthUsedByAbsolute += actualSizeInt;
				colSizeInfo.setActualSize(actualSizeInt);
			} else if (widthLength == null) {
				numNoWidthColumns++;
			}
		}

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
					final double ratio = (double) expectedAbsoluteWidthTotal / widthUsedByAbsolute;
					for (int i = 0; i < numCols; i++) {
						final SizeInfo sizeInfo = columnSizes[i];
						final HtmlLength widthLength = (HtmlLength)columnSizes[i].getHtmlLength();
						if (widthLength != null && widthLength.getLengthType() != HtmlLength.LENGTH) {
							final int oldActualSize = sizeInfo.getActualSize();
							final int newActualSize = (int) Math.round(oldActualSize * ratio);
							sizeInfo.setActualSize(newActualSize);
							totalWidthUsed += newActualSize - oldActualSize;
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
						final double ratio = (double) expectedPercentWidthTotal / widthUsedByPercent;
						for (int i = 0; i < numCols; i++) {
							final SizeInfo sizeInfo = columnSizes[i];
							final HtmlLength widthLength = (HtmlLength)columnSizes[i].getHtmlLength();
							if (widthLength != null && widthLength.getLengthType() == HtmlLength.LENGTH) {
								final int oldActualSize = sizeInfo.getActualSize();
								final int newActualSize = (int) Math.round(oldActualSize * ratio);
								sizeInfo.setActualSize(newActualSize);
								totalWidthUsed += newActualSize - oldActualSize;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Sets bounds of each cell's component, and sumps up table width and height.
	 *
	 * @param insets a {@link java.awt.Insets} object.
	 */
	public final void doLayout(Insets insets) {

		// Set row offsets

		SizeInfo[] rowSizes = this.rowSizes;
		int numRows = rowSizes.length;
		int yoffset = insets.top;
		int cellSpacingY = this.cellSpacingY;
		int hasBorder = this.hasOldStyleBorder;

		if (this.captionSize != null && !isCaptionBotton()) {
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
		if (this.captionSize != null && isCaptionBotton()) {
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

		List<RTableCell> allCells = this.ALL_CELLS;
		for (RTableCell cell : allCells) {
			cell.setCellBounds(colSizes, rowSizes, hasBorder, cellSpacingX, cellSpacingY);
		}
		
		if (this.caption != null) {
			this.caption.setBounds(0, this.captionSize.getHeightOffset(), this.tableWidth, this.captionSize.getHeight());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * This method renders each cell using already set actual column widths. It sets
	 * minimum row heights based on this.
	 */
	private final void finalRender(int hasBorder, int cellSpacing, boolean sizeOnly) {
		// finalRender needs to adjust actualSize of columns and rows
		// given that things might change as we render one last time.
		final List<RTableCell> allCells = this.ALL_CELLS;
		final SizeInfo[] colSizes = this.columnSizes;
		final SizeInfo[] rowSizes = this.rowSizes;
		final int numCells = allCells.size();
		for (int i = 0; i < numCells; i++) {
			final RTableCell cell = (RTableCell) allCells.get(i);
			final int col = cell.getVirtualColumn();
			final int colSpan = cell.getColSpan();
			int totalCellWidth;
			if (colSpan > 1) {
				totalCellWidth = (colSpan - 1) * (cellSpacing + 2 * hasBorder);
				for (int x = 0; x < colSpan; x++) {
					totalCellWidth += colSizes[col + x].getActualSize();
				}
			} else {
				totalCellWidth = colSizes[col].getActualSize();
			}
			final int row = cell.getVirtualRow();
			final int rowSpan = cell.getRowSpan();
			int totalCellHeight;
			if (rowSpan > 1) {
				totalCellHeight = (rowSpan - 1) * (cellSpacing + 2 * hasBorder);
				for (int y = 0; y < rowSpan; y++) {
					totalCellHeight += rowSizes[row + y].getActualSize();
				}
			} else {
				totalCellHeight = rowSizes[row].getActualSize();
			}
			final Dimension size = cell.doCellLayout(totalCellWidth, totalCellHeight, true, true, sizeOnly);
			if (size.width > totalCellWidth) {
				if (colSpan == 1) {
					colSizes[col].setActualSize(size.width);
				} else {
					colSizes[col].setActualSize(colSizes[col].getActualSize() + size.width - totalCellWidth);
				}
			}
			if (size.height > totalCellHeight) {
				if (rowSpan == 1) {
					rowSizes[row].setActualSize(size.height);
				} else {
					rowSizes[row].setActualSize(colSizes[row].getActualSize() + size.height - totalCellHeight);
				}
			}
		}
		
		if (this.captionSize != null) {
			this.caption.doLayout(this.captionSize.getWidth(), this.captionSize.getHeight(), true, true, null,
					RenderState.OVERFLOW_NONE, RenderState.OVERFLOW_NONE, true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.rendered.BoundableRenderable#getRenderablePoint(int,
	 * int)
	 */
	/**
	 * <p>getLowestRenderableSpot.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 * @return a {@link org.loboevolution.html.renderer.RenderableSpot} object.
	 */
	public RenderableSpot getLowestRenderableSpot(int x, int y) {
		final List<RTableCell> allCells = this.ALL_CELLS;
		final int numCells = allCells.size();
		for (int i = 0; i < numCells; i++) {
			final RTableCell cell = (RTableCell) allCells.get(i);
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
		return this.ROWS.size();
	}

	private final HTMLElementImpl getParentRow(HTMLElementImpl cellNode) {
		org.w3c.dom.Node parentNode = cellNode.getParentNode();
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
	 * <p>getRenderables.</p>
	 *
	 * @return a {@link java.util.Iterator} object.
	 */
	public Iterator<RTableCell> getRenderables() {
		return this.ALL_CELLS.iterator();
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

	/**
	 * <p>onDoubleClick.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @return a boolean.
	 */
	public boolean onDoubleClick(MouseEvent event, int x, int y) {
		final List<RTableCell> allCells = this.ALL_CELLS;
		final int numCells = allCells.size();
		for (int i = 0; i < numCells; i++) {
			final RTableCell cell = (RTableCell) allCells.get(i);
			final Rectangle bounds = cell.getVisualBounds();
			if (bounds.contains(x, y)) {
				if (!cell.onDoubleClick(event, x - bounds.x, y - bounds.y)) {
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
	 * org.loboevolution.html.rendered.BoundableRenderable#onMouseClick(java.awt.event.
	 * MouseEvent, int, int)
	 */
	/**
	 * <p>onMouseClick.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @return a boolean.
	 */
	public boolean onMouseClick(MouseEvent event, int x, int y) {
		final List<RTableCell> allCells = this.ALL_CELLS;
		final int numCells = allCells.size();
		for (int i = 0; i < numCells; i++) {
			final RTableCell cell = (RTableCell) allCells.get(i);
			final Rectangle bounds = cell.getVisualBounds();
			if (bounds.contains(x, y)) {
				if (!cell.onMouseClick(event, x - bounds.x, y - bounds.y)) {
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
	 * org.loboevolution.html.rendered.BoundableRenderable#onMouseDisarmed(java.awt.event.
	 * MouseEvent)
	 */
	/**
	 * <p>onMouseDisarmed.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @return a boolean.
	 */
	public boolean onMouseDisarmed(MouseEvent event) {
		final BoundableRenderable ar = this.armedRenderable;
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
	 * org.loboevolution.html.rendered.BoundableRenderable#onMousePressed(java.awt.event.
	 * MouseEvent, int, int)
	 */
	/**
	 * <p>onMousePressed.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @return a boolean.
	 */
	public boolean onMousePressed(MouseEvent event, int x, int y) {
		final List<RTableCell> allCells = this.ALL_CELLS;
		final int numCells = allCells.size();
		for (int i = 0; i < numCells; i++) {
			final RTableCell cell = (RTableCell) allCells.get(i);
			final Rectangle bounds = cell.getVisualBounds();
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
	 * org.loboevolution.html.rendered.BoundableRenderable#onMouseReleased(java.awt.event.
	 * MouseEvent, int, int)
	 */
	/**
	 * <p>onMouseReleased.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @return a boolean.
	 */
	public boolean onMouseReleased(MouseEvent event, int x, int y) {
		final List<RTableCell> allCells = this.ALL_CELLS;
		final int numCells = allCells.size();
		boolean found = false;
		for (int i = 0; i < numCells; i++) {
			final RTableCell cell = (RTableCell) allCells.get(i);
			final Rectangle bounds = cell.getVisualBounds();
			if (bounds.contains(x, y)) {
				found = true;
				final BoundableRenderable oldArmedRenderable = this.armedRenderable;
				if (oldArmedRenderable != null && cell != oldArmedRenderable) {
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
			final BoundableRenderable oldArmedRenderable = this.armedRenderable;
			if (oldArmedRenderable != null) {
				oldArmedRenderable.onMouseDisarmed(event);
				this.armedRenderable = null;
			}
		}
		return true;
	}

	/**
	 * <p>paint.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param size a {@link java.awt.Dimension} object.
	 */
	public final void paint(Graphics g, Dimension size) {
		final List<RTableCell> allCells = this.ALL_CELLS;
		final int numCells = allCells.size();
		for (int i = 0; i < numCells; i++) {
			final RTableCell cell = (RTableCell) allCells.get(i);
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
			for (int i = 0; i < numCells; i++) {
				final RTableCell cell = (RTableCell) allCells.get(i);
				final int cx = cell.getX() - 1;
				final int cy = cell.getY() - 1;
				final int cwidth = cell.getWidth() + 1;
				final int cheight = cell.getHeight() + 1;
				g.drawRect(cx, cy, cwidth, cheight);
			}
		}
	}

	/**
	 * Populates the ROWS and ALL_CELLS collections.
	 */
	private void populateRows() {
		final HTMLElementImpl te = this.tableElement;
		final List<ArrayList<VirtualCell>> rows = this.ROWS;
		final List<HTMLElementImpl> rowElements = this.ROW_ELEMENTS;
		final List<RTableCell> allCells = this.ALL_CELLS;
		final Map<HTMLElementImpl, ArrayList<VirtualCell>> rowElementToRowArray = new HashMap<HTMLElementImpl, ArrayList<VirtualCell>>();
		final NodeList cellList = te.getDescendents(COLUMNS_FILTER, false);
		ArrayList<VirtualCell> currentNullRow = null;
		for (Node node : Nodes.iterable(cellList)) {
			if (node instanceof HTMLElementImpl) {
				final HTMLElementImpl columnNode = (HTMLElementImpl) node;
				final HTMLElementImpl rowElement = getParentRow(columnNode);
				if (rowElement != null && rowElement.getRenderState().getDisplay() == RenderState.DISPLAY_NONE) {
					// Skip row [ 2047122 ]
					continue;
				}
				ArrayList<VirtualCell> row;
				if (rowElement != null) {
					currentNullRow = null;
					row = (ArrayList<VirtualCell>) rowElementToRowArray.get(rowElement);
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
				final VirtualCell vc = new VirtualCell(ac, true);
				ac.setTopLeftVirtualCell(vc);
				row.add(vc);
				allCells.add(ac);
			}
		}
	}

	/**
	 * This method renders each cell using already set actual column widths. It sets
	 * minimum row heights based on this.
	 */
	private final void preLayout(int hasBorder, int cellSpacingX, int cellSpacingY, boolean tableWidthKnown) {
		// TODO: Fix for table without width that has a subtable with width=100%.
		// TODO: Maybe it can be addressed when NOWRAP is implemented.
		// TODO: Maybe it's possible to eliminate this pre-layout altogether.

		final SizeInfo[] colSizes = this.columnSizes;
		final SizeInfo[] rowSizes = this.rowSizes;

		// Initialize minSize in rows
		final int numRows = rowSizes.length;
		for (int i = 0; i < numRows; i++) {
			rowSizes[i].setMinSize(0);
		}

		// Initialize layoutSize in columns
		final int numCols = colSizes.length;
		for (int i = 0; i < numCols; i++) {
			colSizes[i].setLayoutSize(0);
		}

		final List<RTableCell> allCells = this.ALL_CELLS;
		final int numCells = allCells.size();
		if (caption != null) {
			caption.doLayout(0, 0, true, true, null, 0, 0, true);
			captionSize.setHeight(caption.height);
			captionSize.setWidth(caption.width);
		}
		
		for (int i = 0; i < numCells; i++) {
			final RTableCell cell = (RTableCell) allCells.get(i);
			final int col = cell.getVirtualColumn();
			final int colSpan = cell.getColSpan();
			int cellsTotalWidth;
			int cellsUsedWidth;
			boolean widthDeclared = false;
			if (colSpan > 1) {
				cellsUsedWidth = 0;
				for (int x = 0; x < colSpan; x++) {
					final SizeInfo colSize = colSizes[col + x];
					if (colSize.getHtmlLength() != null) {
						widthDeclared = true;
					}
					cellsUsedWidth += colSize.getActualSize();
				}
				cellsTotalWidth = cellsUsedWidth + (colSpan - 1) * (cellSpacingX + 2 * hasBorder);
			} else {
				final SizeInfo colSize = colSizes[col];
				if (colSize.getHtmlLength() != null) {
					widthDeclared = true;
				}
				cellsUsedWidth = cellsTotalWidth = colSize.getActualSize();
			}

			// TODO: A tentative height could be used here: Height of
			// table divided by number of rows.

			java.awt.Dimension size;
			final RenderThreadState state = RenderThreadState.getState();
			final boolean prevOverrideNoWrap = state.overrideNoWrap;
			try {
				if (!prevOverrideNoWrap) {
					state.overrideNoWrap = !widthDeclared;
				}
				size = cell.doCellLayout(cellsTotalWidth, 0, true, true, true);
			} finally {
				state.overrideNoWrap = prevOverrideNoWrap;
			}
			// Set render widths
			final int cellLayoutWidth = size.width;
			if (colSpan > 1) {
				if (cellsUsedWidth > 0) {
					final double ratio = (double) cellLayoutWidth / cellsUsedWidth;
					for (int x = 0; x < colSpan; x++) {
						final SizeInfo si = colSizes[col + x];
						final int newLayoutSize = (int) Math.round(si.getActualSize() * ratio);
						if (si.getLayoutSize() < newLayoutSize) {
							si.setLayoutSize(newLayoutSize);
						}
					}
				} else {
					final int newLayoutSize = cellLayoutWidth / colSpan;
					for (int x = 0; x < colSpan; x++) {
						final SizeInfo si = colSizes[col + x];
						if (si.getLayoutSize() < newLayoutSize) {
							si.setLayoutSize(newLayoutSize);
						}
					}
				}
			} else {
				final SizeInfo colSizeInfo = colSizes[col];
				if (colSizeInfo.getLayoutSize() < cellLayoutWidth) {
					colSizeInfo.setLayoutSize(cellLayoutWidth);
				}
			}

			// Set minimum heights
			final int actualCellHeight = size.height;
			final int row = cell.getVirtualRow();
			final int rowSpan = cell.getRowSpan();
			if (rowSpan > 1) {
				final int vch = (actualCellHeight - (rowSpan - 1) * (cellSpacingY + 2 * hasBorder)) / rowSpan;
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
	 * Called on every relayout. Element children might have changed.
	 *
	 * @param insets a {@link java.awt.Insets} object.
	 * @param availWidth a int.
	 * @param availHeight a int.
	 */
	public void reset(Insets insets, int availWidth, int availHeight) {
		// TODO: Incorporate into build() and calculate
		// sizes properly based on parameters.
		this.ROWS.clear();
		this.ALL_CELLS.clear();
		this.ROW_ELEMENTS.clear();
		
		final String borderText = this.tableElement.getAttribute("border");
		int border = HtmlValues.getPixelSize(borderText, null, 0);
		final String cellSpacingText = this.tableElement.getAttribute("cellspacing");
		int cellSpacing = HtmlValues.getPixelSize(cellSpacingText, null, 0);
		this.cellSpacingX = cellSpacing;
		this.cellSpacingY = cellSpacing;

		this.tableWidthLength = TableMatrix.getWidthLength(this.tableElement, availWidth);
		
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
		createSizeArrays();
		
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
			currentTotal = adjustCurrentTotal2(columnSizes, this.rowSizes, this.ROWS, cellSpacingX, numCols,
														   noWidthTotal, difference, cellSpacingX, hasBorder, numNoWidth, cellAvailWidth, currentTotal);
			difference = currentTotal - cellAvailWidth;
		}

		// See if absolutes need to be contracted
		if (difference > 0 || difference < 0 && expand) {
			int absoluteWidthTotal = 0;
			for (int i = 0; i < numCols; i++) {
				HtmlLength widthLength = (HtmlLength)columnSizes[i].getHtmlLength();
				if (widthLength != null && widthLength.getLengthType() != HtmlLength.LENGTH) {
					absoluteWidthTotal += columnSizes[i].getActualSize();
				}
			}
			if (absoluteWidthTotal > 0) {
				currentTotal = adjustCurrentTotal(columnSizes, this.rowSizes, this.ROWS, cellSpacingY, numCols, absoluteWidthTotal, difference, cellSpacingX, hasBorder, currentTotal);
				difference = currentTotal - cellAvailWidth;
			}

			// See if percentages need to be contracted
			if (difference > 0 || difference < 0 && expand) {
				int percentWidthTotal = 0;
				for (int i = 0; i < numCols; i++) {
					HtmlLength widthLength = (HtmlLength)columnSizes[i].getHtmlLength();
					if (widthLength != null && widthLength.getLengthType() == HtmlLength.LENGTH) {
						percentWidthTotal += columnSizes[i].getActualSize();
					}
				}
				if (percentWidthTotal > 0) {
					currentTotal = adjustCurrentTotal(columnSizes, this.rowSizes, this.ROWS, cellSpacingY, numCols, percentWidthTotal, difference, cellSpacingX, hasBorder, currentTotal);
				}
			}
		}
		return currentTotal;
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
	 * <p>adjustCurrentTotal.</p>
	 *
	 * @param columnSizes an array of {@link org.loboevolution.info.SizeInfo} objects.
	 * @param rowLenght an array of {@link org.loboevolution.info.SizeInfo} objects.
	 * @param ROWS a {@link java.util.ArrayList} object.
	 * @param cellSpacingY a int.
	 * @param numCols a int.
	 * @param widthTotal a int.
	 * @param difference a int.
	 * @param cellSpacingX a int.
	 * @param hasBorder a int.
	 * @param currTotal a int.
	 * @return a int.
	 */
	public static int adjustCurrentTotal(SizeInfo[] columnSizes, SizeInfo[] rowLenght,
			List<ArrayList<VirtualCell>> ROWS, int cellSpacingY, int numCols,
			int widthTotal, int difference, int cellSpacingX, int hasBorder, int currTotal) {
		
		int currentTotal = currTotal;
		int expectedPercentWidthTotal = widthTotal - difference;
		if (expectedPercentWidthTotal < 0) {
			expectedPercentWidthTotal = 0;
		}
		double ratio = (double) expectedPercentWidthTotal / widthTotal;
		for (int i = 0; i < numCols; i++) {
			SizeInfo sizeInfo = columnSizes[i];
			HtmlLength widthLength = (HtmlLength)columnSizes[i].getHtmlLength();
			if (widthLength != null && widthLength.getLengthType() == HtmlLength.LENGTH) {
				int oldActualSize = sizeInfo.getActualSize();
				int newActualSize = (int) Math.round(oldActualSize * ratio);
				sizeInfo.setActualSize(newActualSize);
				if (newActualSize < sizeInfo.getLayoutSize()) {
					// See if it actually fits.
					layoutColumn(columnSizes, rowLenght, ROWS, sizeInfo, i, cellSpacingY, cellSpacingX, hasBorder);
					if (newActualSize < sizeInfo.getLayoutSize()) {
						// Didn't fit.
						newActualSize = sizeInfo.getLayoutSize();
						sizeInfo.setActualSize(newActualSize);
					}
				}
				currentTotal += newActualSize - oldActualSize;
			}
		}
		return currentTotal;
	}
	
	/**
	 * <p>adjustCurrentTotal2.</p>
	 *
	 * @param columnSizes an array of {@link org.loboevolution.info.SizeInfo} objects.
	 * @param rowLenght an array of {@link org.loboevolution.info.SizeInfo} objects.
	 * @param ROWS a {@link java.util.ArrayList} object.
	 * @param cellSpacingY a int.
	 * @param numCols a int.
	 * @param widthTotal a int.
	 * @param difference a int.
	 * @param cellSpacingX a int.
	 * @param hasBorder a int.
	 * @param numNoWidth a int.
	 * @param cellAvailWidth a int.
	 * @param currTotal a int.
	 * @return a int.
	 */
	public static int adjustCurrentTotal2(SizeInfo[] columnSizes, SizeInfo[] rowLenght,
			List<ArrayList<VirtualCell>> ROWS, int cellSpacingY, int numCols,
			int widthTotal, int difference, int cellSpacingX, int hasBorder, int numNoWidth, int cellAvailWidth, int currTotal) {
		
		int currentTotal = currTotal;
		int expectedPercentWidthTotal = widthTotal - difference;
		if (expectedPercentWidthTotal < 0) {
			expectedPercentWidthTotal = 0;
		}
		double ratio = (double) expectedPercentWidthTotal / widthTotal;
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
					layoutColumn(columnSizes, rowLenght, ROWS, sizeInfo, i, cellSpacingY, cellSpacingX, hasBorder);
					if (newActualSize < sizeInfo.getLayoutSize()) {
						// Didn't fit.
						newActualSize = sizeInfo.getLayoutSize();
						sizeInfo.setActualSize(newActualSize);
					}
				}
				currentTotal += newActualSize - oldActualSize;
			}
		}
		return currentTotal;
	}
	
	private static void layoutColumn(SizeInfo[] columnSizes, SizeInfo[] rowLenght,
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
}
