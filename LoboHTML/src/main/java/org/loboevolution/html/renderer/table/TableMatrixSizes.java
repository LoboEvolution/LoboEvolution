/*
 * MIT License
 *
 * Copyright (c) 2014 - 2026 LoboEvolution
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

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.css.CSSStyleDeclaration;
import org.loboevolution.html.renderer.RBlock;
import org.loboevolution.html.renderer.info.RLayoutInfo;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.RenderThreadState;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.HtmlLength;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.info.CaptionSizeInfo;
import org.loboevolution.info.SizeInfo;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class TableMatrixSizes {

	private final TableMatrix matrix;

	private int numCols = 0;

	/**
	 * <p>Constructor for TableMatrixSizes.</p>
	 *
	 * @param matrix a {@link org.loboevolution.html.renderer.table.TableMatrix} object.
	 */
	public TableMatrixSizes(final TableMatrix matrix) {
		this.matrix = matrix;
	}

	/**
	 * <p> getWidthLength. </p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param availWidth a {@link java.lang.Integer} object.
	 * @return a {@link org.loboevolution.html.style.HtmlLength} object.
	 */
	static HtmlLength getWidthLength(final HTMLElementImpl element, final int availWidth) {
		try {
			final CSSStyleDeclaration props = element.getCurrentStyle();
			String widthText = props == null ? null : props.getWidth();
			if (widthText == null) {
				final String widthAttr = element.getAttribute("width");
				if (widthAttr == null) {
					return null;
				}
				final HTMLDocumentImpl doc =  (HTMLDocumentImpl)element.getDocumentNode();
				return new HtmlLength(widthAttr, doc);
			} else {

				final HTMLDocumentImpl doc =  (HTMLDocumentImpl)element.getDocumentNode();

				if ("inherit".equals(widthText)) {
					widthText = element.getParentStyle().getWidth();
				}

				final HtmlLength widthLength = new HtmlLength(widthText, doc);
				if (Strings.isNotBlank(props.getMaxWidth()) || Strings.isNotBlank(props.getMinWidth())) {
					int width = widthLength.getLength(availWidth);
					if (Strings.isNotBlank(props.getMaxWidth())) {
						final int maxWidth = HtmlValues.getPixelSize(props.getMaxWidth(), element.getRenderState(), doc.getDefaultView(), 0, availWidth);
						if (width == 0 || width > maxWidth) {
							width = maxWidth;
						}
					}
					if (Strings.isNotBlank(props.getMinWidth())) {
						final int minWidth = HtmlValues.getPixelSize(props.getMinWidth(), element.getRenderState(), doc.getDefaultView(), 0, availWidth);
						if (width == 0 || width < minWidth) {
							width = minWidth;
						}
					}
					return new HtmlLength(width);
				} else {
					final int width = HtmlValues.getPixelSize(widthText, element.getRenderState(), doc.getDefaultView(), 0, availWidth);
					// Return null for auto/undefined width (0 means not specified)
					if (width == 0) {
						return null;
					}
					return new HtmlLength(width);
				}
			}
		} catch (final Exception err) {
			return null;
		}
	}

	/**
	 * Gets the height length.
	 *
	 * @param element     the element
	 * @param availHeight the avail height
	 * @return the height length
	 */
	static HtmlLength getHeightLength(final HTMLElementImpl element, final int availHeight) {
		try {
			final CSSStyleDeclaration props = element.getCurrentStyle();
			String heightText = props == null ? null : props.getHeight();
			if (heightText == null) {
				final String ha = element.getAttribute("height");
				if (ha == null) {
					return null;
				} else {
					final HTMLDocumentImpl doc =  (HTMLDocumentImpl)element.getDocumentNode();
					return new HtmlLength(ha, doc);
				}
			} else {

				final HTMLDocumentImpl doc =  (HTMLDocumentImpl)element.getDocumentNode();

				if ("inherit".equals(heightText)) {
					heightText = element.getParentStyle().getHeight();
				}

				int height = -1;

				if (heightText != null) {
					height = HtmlValues.getPixelSize(heightText, element.getRenderState(), doc.getDefaultView(),  0, availHeight);
				}

				if (props.getMaxHeight() != null) {
					final int maxHeight = HtmlValues.getPixelSize(props.getMaxHeight(), element.getRenderState(), doc.getDefaultView(),  0, availHeight);

					if (height == 0 || height > maxHeight) {
						height = maxHeight;
					}
				}

				if (props.getMinHeight() != null) {
					final int minHeight = HtmlValues.getPixelSize(props.getMinHeight(), element.getRenderState(), doc.getDefaultView(),  0, availHeight);

					if (height == 0 || height < minHeight) {
						height = minHeight;
					}
				}

				return new HtmlLength(height);
			}
		} catch (final Exception err) {
			return null;
		}
	}

	/**
	 * Populates the columnSizes and rowSizes arrays, setting htmlLength in each
	 * element.
	 */
	void createSizeArrays() {
		final List<ArrayList<VirtualCell>> rows = matrix.rows;
		matrix.rowSizes = rowsSize(rows);
		matrix.columnSizes = columnSizes(rows, numCols);

		if (matrix.caption != null) {
			matrix.captionSize = new CaptionSizeInfo();
		}
	}

	private SizeInfo[] rowsSize(final List<ArrayList<VirtualCell>> rows) {
		final int numRows = rows.size();
		final SizeInfo[] rowSizes = new SizeInfo[numRows];
		final List<HTMLElementImpl> rowElements = matrix.rowElements;
		int index = 0;
		for (final ArrayList<VirtualCell> row : rows) {
			final int rs = row.size();
			if (rs > numCols) {
				numCols = rs;
			}
			final SizeInfo rowSizeInfo = new SizeInfo();
			rowSizes[index] = rowSizeInfo;
			final HTMLElementImpl rowElement = rowElements.size() > index ? rowElements.get(index) : null;
			final String rowHeightText = rowElement == null ? null : rowElement.getAttribute("height");
			HtmlLength rowHeightLength = null;
			if (rowHeightText != null) {
				try {
					final HTMLDocumentImpl doc =  (HTMLDocumentImpl)rowElement.getDocumentNode();
					rowHeightLength = new HtmlLength(HtmlValues.getPixelSize(rowHeightText, rowElement.getRenderState(), doc.getDefaultView(), 0));
				} catch (final Exception err) {
					// ignore
				}
			}
			if (rowHeightLength != null) {
				rowSizeInfo.setHtmlLength(rowHeightLength);
			} else {
				HtmlLength bestHeightLength = null;
				for (final VirtualCell virtualCell : row) {
					final VirtualCell vc = virtualCell;
					if (vc != null) {
						final HtmlLength vcHeightLength = vc.getHeightLength();
						if (vcHeightLength != null && vcHeightLength.isPreferredOver(bestHeightLength)) {
							bestHeightLength = vcHeightLength;
						}
					}
				}
				rowSizeInfo.setHtmlLength(bestHeightLength);
			}
			index++;
		}
		return rowSizes;
	}

	private SizeInfo[] columnSizes(final List<ArrayList<VirtualCell>> rows, final int numCols) {
		final SizeInfo[] columnSizes = new SizeInfo[numCols];
		
		// Get col elements from the table using getElementsByTagName
		final HTMLCollection colElements = matrix.tableElement.getElementsByTagName("col");
		final int numColElements = colElements.getLength();
		
		for (int i = 0; i < numCols; i++) {
			HtmlLength bestWidthLength = null;

			// First check <col> elements for explicit width (attribute or CSS)
			if (i < numColElements) {
				final HTMLElementImpl colElement = (HTMLElementImpl) colElements.item(i);
				
				// Check width attribute
				final String colWidth = colElement.getAttribute("width");
				if (colWidth != null) {
					try {
						final HTMLDocumentImpl doc = (HTMLDocumentImpl) colElement.getDocumentNode();
						bestWidthLength = new HtmlLength(HtmlValues.getPixelSize(colWidth, colElement.getRenderState(), doc.getDefaultView(), 0));
					} catch (final Exception err) {
						// ignore
					}
				}
				
				// Also check CSS width property
				if (bestWidthLength == null) {
					final CSSStyleDeclaration currentStyle = colElement.getCurrentStyle();
					if (currentStyle != null) {
						final String cssWidth = currentStyle.getWidth();
						if (Strings.isNotBlank(cssWidth) && !"auto".equals(cssWidth)) {
							try {
								final HTMLDocumentImpl doc = (HTMLDocumentImpl) colElement.getDocumentNode();
								bestWidthLength = new HtmlLength(HtmlValues.getPixelSize(cssWidth, colElement.getRenderState(), doc.getDefaultView(), 0));
							} catch (final Exception err) {
								// ignore
							}
						}
					}
				}
			}

			// Cells with colspan==1 first.
			for (final List<VirtualCell> row : rows) {
				final VirtualCell vc = row.size() > i ? row.get(i) : null;
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
				for (final List<VirtualCell> row : rows) {
					final VirtualCell vc = row.size() > i ? row.get(i) : null;
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
		return columnSizes;
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
	 * @param hasBorder a {@link java.lang.Integer} object.
	 * @param cellSpacingX a {@link java.lang.Integer} object.
	 * @param cellSpacingY a {@link java.lang.Integer} object.
	 * @param availWidth a {@link java.lang.Integer} object.
	 */
	void determineColumnSizes(final int hasBorder, final int cellSpacingX, final int cellSpacingY, final int availWidth) {
		final HtmlLength tableWidthLength = matrix.tableWidthLength;
		int tableWidth;
		final boolean widthKnown;
		if (tableWidthLength != null) {
			tableWidth = tableWidthLength.getLength(availWidth);
			widthKnown = true;
		} else {
			tableWidth = availWidth;
			widthKnown = false;
		}
		final SizeInfo[] columnSizes = matrix.columnSizes;
		final int widthsOfExtras = matrix.widthsOfExtras;
		int cellAvailWidth = tableWidth - widthsOfExtras;
		if (cellAvailWidth < 0) {
			cellAvailWidth = 0;
		}

		determineTentativeSizes(columnSizes, cellAvailWidth);

		// Pre-render cells. This will give the minimum width of each cell,
		// in addition to the minimum height.

		preLayout(hasBorder, cellSpacingX, cellSpacingY, cellAvailWidth);

		// Increases column widths if they are less than minimums of each cell.

		adjustForRenderWidths(columnSizes);

		// Adjust for expected total width

		adjustWidthsForExpectedMax(columnSizes, cellAvailWidth, widthKnown,
				matrix.captionSize != null ? matrix.captionSize.getWidth() : 0, widthsOfExtras);
	}

	void determineRowSizes(final int hasBorder, final int cellSpacing, final int availHeight, final boolean sizeOnly) {
		final HtmlLength tableHeightLength = TableMatrixSizes.getHeightLength(matrix.tableElement, availHeight);
		if (tableHeightLength != null) {
			int tableHeight = tableHeightLength.getLength(availHeight);
			determineRowSizesFixedTH(hasBorder, cellSpacing, tableHeight, sizeOnly);
		} else {
			determineRowSizesFlexibleTH(hasBorder, cellSpacing, sizeOnly);
		}
	}

	private void determineRowSizesFixedTH(final int hasBorder, final int cellSpacing, final int tableHeight,
                                          final boolean sizeOnly) {
		final SizeInfo[] rowSizes = matrix.rowSizes;
		final int heightsOfExtras = matrix.heightsOfExtras;
		int cellAvailHeight = tableHeight - heightsOfExtras;
		if (cellAvailHeight < 0) {
			cellAvailHeight = 0;
		}

		// Look at percentages first

		int heightUsedbyPercent = 0;
		int otherMinSize = 0;
		for (final SizeInfo rowSizeInfo : rowSizes) {
			final HtmlLength heightLength = (HtmlLength) rowSizeInfo.getHtmlLength();
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
			for (final SizeInfo rowSizeInfo : rowSizes) {
				final HtmlLength heightLength = (HtmlLength) rowSizeInfo.getHtmlLength();
				if (heightLength != null && heightLength.getLengthType() == HtmlLength.LENGTH) {
                    final int prevActualSize = rowSizeInfo.getActualSize();
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
		for (final SizeInfo rowSizeInfo : rowSizes) {
			final HtmlLength heightLength = (HtmlLength) rowSizeInfo.getHtmlLength();
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
			for (final SizeInfo rowSizeInfo : rowSizes) {
				final HtmlLength heightLength = (HtmlLength) rowSizeInfo.getHtmlLength();
				if (heightLength != null && heightLength.getLengthType() != HtmlLength.LENGTH) {
                    final int prevActualSize = rowSizeInfo.getActualSize();
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
		for (final SizeInfo rowSizeInfo : rowSizes) {
			final HtmlLength heightLength = (HtmlLength) rowSizeInfo.getHtmlLength();
			if (heightLength == null) {
				int actualSizeInt = remainingHeight / numNoHeightColumns;
				if (actualSizeInt < rowSizeInfo.getMinSize()) {
					actualSizeInt = rowSizeInfo.getMinSize();
				}
				heightUsedByRemaining += actualSizeInt;
				rowSizeInfo.setActualSize(actualSizeInt);
			}
		}

		if (matrix.captionSize != null) {
			heightUsedByRemaining += matrix.captionSize.getHeight();
		}

		// Calculate actual table width

		final int totalUsed = heightUsedByAbsolute + heightUsedbyPercent + heightUsedByRemaining;
		if (totalUsed >= cellAvailHeight) {
			matrix.tableHeight = totalUsed + heightsOfExtras;
		} else {
			// Rows too short; expand them
			final double ratio = (double) cellAvailHeight / totalUsed;
			for (final SizeInfo rowSizeInfo : rowSizes) {
				final int actualSize = rowSizeInfo.getActualSize();
				rowSizeInfo.setActualSize((int) Math.round(actualSize * ratio));
			}
			matrix.tableHeight = tableHeight;
		}

		finalRender(hasBorder, cellSpacing, sizeOnly);
	}

	private void determineRowSizesFlexibleTH(final int hasBorder, final int cellSpacing, final boolean sizeOnly) {
		final SizeInfo[] rowSizes = matrix.rowSizes;
		final int heightsOfExtras = matrix.heightsOfExtras;

		// Look at rows with absolute sizes
		int heightUsedByAbsolute = 0;
		int percentSum = 0;
		for (final SizeInfo rowSizeInfo : rowSizes) {
			final HtmlLength heightLength = (HtmlLength) rowSizeInfo.getHtmlLength();
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
		for (final SizeInfo rowSizeInfo : rowSizes) {
			final HtmlLength widthLength = (HtmlLength) rowSizeInfo.getHtmlLength();
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
		for (final SizeInfo rowSizeInfo : rowSizes) {
			final HtmlLength heightLength = (HtmlLength) rowSizeInfo.getHtmlLength();
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
		matrix.tableHeight = heightUsedByAbsolute + heightUsedByNoSize + heightUsedByPercent + heightsOfExtras;

		// Do a final render to set actual cell sizes
		finalRender(hasBorder, cellSpacing, sizeOnly);
	}

	/**
	 * This method sets the tentative actual sizes of columns (rows) based on
	 * specified witdhs (heights) if available.
	 * 
	 * @param columnSizes a {@link SizeInfo} object.
	 * @param cellAvailWidth a {@link java.lang.Integer} object.
	 */
	private void determineTentativeSizes(final SizeInfo[] columnSizes, final int cellAvailWidth) {

		// Look at percentages first
		int widthUsedByPercent = 0;
		for (final SizeInfo colSizeInfo : columnSizes) {
			final HtmlLength widthLength = (HtmlLength) colSizeInfo.getHtmlLength();
			if (widthLength != null && widthLength.getLengthType() == HtmlLength.LENGTH) {
				final int actualSizeInt = widthLength.getLength(cellAvailWidth);
				widthUsedByPercent += actualSizeInt;
				colSizeInfo.setActualSize(actualSizeInt);
			}
		}

		// Look at columns with absolute sizes
		int widthUsedByAbsolute = 0;
		int numNoWidthColumns = 0;
		for (final SizeInfo colSizeInfo : columnSizes) {
			final HtmlLength widthLength = (HtmlLength) colSizeInfo.getHtmlLength();
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
					for (final SizeInfo sizeInfo : columnSizes) {
						final HtmlLength widthLength = (HtmlLength) sizeInfo.getHtmlLength();
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
						for (final SizeInfo sizeInfo : columnSizes) {
							final HtmlLength widthLength = (HtmlLength) sizeInfo.getHtmlLength();
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
	 * Contracts column sizes according to render sizes.
	 */
private void adjustForRenderWidths(final SizeInfo[] columnSizes) {
		for (final SizeInfo si : columnSizes) {
			if (si.getActualSize() < si.getLayoutSize()) {
				si.setActualSize(si.getLayoutSize());
			}
		}
	}

	private int adjustWidthsForExpectedMax(final SizeInfo[] columnSizes, final int cellAvailWidth, final boolean expand, final int captionWith,
                                           final int widthOfExtras) {
		final int hasBorder = matrix.hasOldStyleBorder;
		final int cellSpacingX = matrix.cellSpacingX;
		final int numCols = columnSizes.length;
		int currentTotal = 0;
		for (final SizeInfo columnSize : columnSizes) {
			currentTotal += columnSize.getActualSize();
		}
		final int difference = currentTotal - cellAvailWidth;
		if (difference > 0 || difference < 0 && expand) {
			// First, try to contract/expand columns with no width
			currentTotal = expandColumns(columnSizes, cellAvailWidth, expand, hasBorder, cellSpacingX, currentTotal,
					numCols, difference);
		}

		if (matrix.captionSize != null) {
			if (cellAvailWidth + widthOfExtras > captionWith) {
				final int differenceCaption = currentTotal - captionWith - widthOfExtras;
				if (differenceCaption < 0) {
					currentTotal = expandColumns(columnSizes, captionWith, expand, hasBorder, cellSpacingX,
							currentTotal, numCols, differenceCaption);
				}
				matrix.captionSize.setWidth(currentTotal + widthOfExtras);
			} else {
				if (currentTotal + widthOfExtras > captionWith) {
					matrix.captionSize.setWidth(currentTotal + widthOfExtras);
				} else {
					currentTotal = expandColumns(columnSizes, captionWith - widthOfExtras, expand, hasBorder,
							cellSpacingX, currentTotal, numCols, currentTotal);
				}
			}
		}

		return currentTotal;
	}

	/**
	 * This method renders each cell using already set actual column widths. It sets
	 * minimum row heights based on matrix.
	 */
	private void finalRender(final int hasBorder, final int cellSpacing, final boolean sizeOnly) {
		// finalRender needs to adjust actualSize of columns and rows
		// given that things might change as we render one last time.
		final List<RTableCell> allCells = matrix.getAllCells();
		final SizeInfo[] colSizes = matrix.columnSizes;
		final SizeInfo[] rowSizes = matrix.rowSizes;
		for (final RTableCell cell : allCells) {
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

		if (matrix.captionSize != null) {
			matrix.caption.doLayout(RLayoutInfo.builder()
							.availWidth(matrix.captionSize.getWidth())
							.availHeight(matrix.captionSize.getHeight())
							.expandWidth(false)
							.expandHeight(false)
							.blockFloatBoundsSource(null)
							.defaultOverflowX(RenderState.OVERFLOW_NONE)
							.defaultOverflowY(RenderState.OVERFLOW_NONE)
							.sizeOnly(false)
							.build());
			// Apply render state padding/margin to BaseElementRenderable fields
			matrix.caption.applyPaddingMarginFromRenderState((RenderState) matrix.caption.getModelNode().getRenderState(), matrix.captionSize.getWidth(), matrix.captionSize.getHeight());
		}
	}

	/**
	 * This method renders each cell using already set actual column widths. It sets
	 * minimum row heights based on matrix.
	 */
	private void preLayout(final int hasBorder, final int cellSpacingX, final int cellSpacingY, final int cellAvailWidth) {
		// TODO: Fix for table without width that has a subtable with width=100%.
		// TODO: Maybe it can be addressed when NOWRAP is implemented.
		// TODO: Maybe it's possible to eliminate this pre-layout altogether.

		final SizeInfo[] colSizes = matrix.columnSizes;
		final SizeInfo[] rowSizes = matrix.rowSizes;

		// Initialize minSize in rows
		for (final SizeInfo rowSize : rowSizes) {
			rowSize.setMinSize(0);
		}

		// Initialize layoutSize in columns
		for (final SizeInfo sizeInfo : colSizes) {
			sizeInfo.setLayoutSize(0);
		}

		final List<RTableCell> allCells = matrix.getAllCells();
		if (matrix.caption != null) {
			matrix.caption.doLayout(RLayoutInfo.builder()
					.availWidth(cellAvailWidth)
					.availHeight(0)
					.expandWidth(false)
					.expandHeight(false)
					.blockFloatBoundsSource(null)
					.defaultOverflowX(0)
					.defaultOverflowY(0)
					.sizeOnly(true)
					.build());
			// Apply render state padding/margin to BaseElementRenderable fields
			matrix.caption.applyPaddingMarginFromRenderState((RenderState) matrix.caption.getModelNode().getRenderState(), cellAvailWidth, 0);
			matrix.captionSize.setHeight(matrix.caption.getHeight());
			matrix.captionSize.setWidth(matrix.caption.getWidth());
		}

		for (final RTableCell cell : allCells) {
			final int col = cell.getVirtualColumn();
			final int colSpan = cell.getColSpan();
			final int cellsTotalWidth;
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

			Dimension size;
			final RenderThreadState state = RenderThreadState.getState();
			final boolean prevOverrideNoWrap = state.overrideNoWrap;
			try {
				if (!prevOverrideNoWrap) {
					state.overrideNoWrap = true; // Always measure intrinsic width (no wrap)
				}
				// For columns without explicit width, measure intrinsic width
				final int layoutWidth = widthDeclared && cellsTotalWidth > 0 ? cellsTotalWidth : cellAvailWidth;
				size = cell.doCellLayout(layoutWidth, 0, false, false, true);
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
	 * <p>
	 * adjustCurrentTotal2.
	 * </p>
	 *
	 * @param columnSizes    an array of {@link org.loboevolution.info.SizeInfo}
	 *                       objects.
	 * @param rowLenght      an array of {@link org.loboevolution.info.SizeInfo}
	 *                       objects.
	 * @param rows           a {@link java.util.ArrayList} object.
	 * @param cellSpacingY   a {@link java.lang.Integer} object.
	 * @param numCols        a {@link java.lang.Integer} object.
	 * @param widthTotal     a {@link java.lang.Integer} object.
	 * @param difference     a {@link java.lang.Integer} object.
	 * @param cellSpacingX   a {@link java.lang.Integer} object.
	 * @param hasBorder      a {@link java.lang.Integer} object.
	 * @param numNoWidth     a {@link java.lang.Integer} object.
	 * @param cellAvailWidth a {@link java.lang.Integer} object.
	 * @param currTotal      a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	private static int adjustCurrentTotal2(final SizeInfo[] columnSizes, final SizeInfo[] rowLenght,
                                           final List<ArrayList<VirtualCell>> rows, final int cellSpacingY, final int numCols, final int widthTotal, final int difference,
                                           final int cellSpacingX, final int hasBorder, final int numNoWidth, final int cellAvailWidth, final int currTotal) {

		int currentTotal = currTotal;
		int expectedPercentWidthTotal = widthTotal - difference;
		if (expectedPercentWidthTotal < 0) {
			expectedPercentWidthTotal = 0;
		}
		final double ratio = widthTotal == 0 ? 0 : (double) expectedPercentWidthTotal / widthTotal;
		int noWidthCount = 0;
		int remainingWidth = cellAvailWidth;
		int remainingColumns = numNoWidth;
		for (int i = 0; i < numCols; i++) {
			final SizeInfo sizeInfo = columnSizes[i];
			if (sizeInfo.getHtmlLength() == null) {
				final int oldActualSize = sizeInfo.getActualSize();
				int newActualSize;
				if (widthTotal == 0) {
					newActualSize = Math.max(sizeInfo.getLayoutSize(), remainingWidth / remainingColumns);
					remainingWidth -= newActualSize;
					remainingColumns--;
				} else if (++noWidthCount == numNoWidth) {
					// Last column without a width.
					final int currentDiff = currentTotal - cellAvailWidth;
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
					TableMatrix.layoutColumn(columnSizes, rowLenght, rows, sizeInfo, i, cellSpacingY, cellSpacingX,
							hasBorder);
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

	private int expandColumns(final SizeInfo[] columnSizes, final int cellAvailWidth, final boolean expand, final int hasBorder,
                              final int cellSpacingX, final int cTotal, final int numCols, final int diff) {
		int currentTotal = cTotal;
		int difference = diff;
		int noWidthTotal = 0;
		int numNoWidth = 0;
		for (int i = 0; i < numCols; i++) {
			if (columnSizes[i].getHtmlLength() == null) {
				numNoWidth++;
				noWidthTotal += columnSizes[i].getActualSize();
			}
		}
		if (numNoWidth > 0) {
			currentTotal = adjustCurrentTotal2(columnSizes, matrix.rowSizes, matrix.rows, cellSpacingX, numCols,
					noWidthTotal, difference, cellSpacingX, hasBorder, numNoWidth, cellAvailWidth, currentTotal);
			difference = currentTotal - cellAvailWidth;
		}

		// See if absolutes need to be contracted
		if (difference > 0 || difference < 0 && expand) {
			int absoluteWidthTotal = 0;
			for (int i = 0; i < numCols; i++) {
				final HtmlLength widthLength = (HtmlLength) columnSizes[i].getHtmlLength();
				if (widthLength != null && widthLength.getLengthType() != HtmlLength.LENGTH) {
					absoluteWidthTotal += columnSizes[i].getActualSize();
				}
			}
			if (absoluteWidthTotal > 0) {
				currentTotal = adjustCurrentTotal(columnSizes, matrix.rowSizes, matrix.rows, matrix.cellSpacingY,
						numCols, absoluteWidthTotal, difference, cellSpacingX, hasBorder, currentTotal);
				difference = currentTotal - cellAvailWidth;
			}

			// See if percentages need to be contracted
			if (difference > 0 || difference < 0 && expand) {
				int percentWidthTotal = 0;
				for (int i = 0; i < numCols; i++) {
					final HtmlLength widthLength = (HtmlLength) columnSizes[i].getHtmlLength();
					if (widthLength != null && widthLength.getLengthType() == HtmlLength.LENGTH) {
						percentWidthTotal += columnSizes[i].getActualSize();
					}
				}
				if (percentWidthTotal > 0) {
					currentTotal = adjustCurrentTotal(columnSizes, matrix.rowSizes, matrix.rows, matrix.cellSpacingY,
							numCols, percentWidthTotal, difference, cellSpacingX, hasBorder, currentTotal);
				}
			}
		}
		return currentTotal;
	}

	/**
	 * <p>
	 * adjustCurrentTotal.
	 * </p>
	 *
	 * @param columnSizes  an array of {@link org.loboevolution.info.SizeInfo}
	 *                     objects.
	 * @param rowLenght    an array of {@link org.loboevolution.info.SizeInfo}
	 *                     objects.
	 * @param rows         a {@link java.util.ArrayList} object.
	 * @param cellSpacingY a {@link java.lang.Integer} object.
	 * @param numCols      a {@link java.lang.Integer} object.
	 * @param widthTotal   a {@link java.lang.Integer} object.
	 * @param difference   a {@link java.lang.Integer} object.
	 * @param cellSpacingX a {@link java.lang.Integer} object.
	 * @param hasBorder    a {@link java.lang.Integer} object.
	 * @param currTotal    a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	int adjustCurrentTotal(final SizeInfo[] columnSizes, final SizeInfo[] rowLenght, final List<ArrayList<VirtualCell>> rows,
                           final int cellSpacingY, final int numCols, final int widthTotal, final int difference, final int cellSpacingX, final int hasBorder,
                           final int currTotal) {

		int currentTotal = currTotal;
		int expectedPercentWidthTotal = widthTotal - difference;
		if (expectedPercentWidthTotal < 0) {
			expectedPercentWidthTotal = 0;
		}
		final double ratio = (double) expectedPercentWidthTotal / widthTotal;
		for (int i = 0; i < numCols; i++) {
			final SizeInfo sizeInfo = columnSizes[i];
			final HtmlLength widthLength = (HtmlLength) columnSizes[i].getHtmlLength();
			if (widthLength != null && widthLength.getLengthType() == HtmlLength.LENGTH) {
				final int oldActualSize = sizeInfo.getActualSize();
				int newActualSize = (int) Math.round(oldActualSize * ratio);
				sizeInfo.setActualSize(newActualSize);
				if (newActualSize < sizeInfo.getLayoutSize()) {
					// See if it actually fits.
					TableMatrix.layoutColumn(columnSizes, rowLenght, rows, sizeInfo, i, cellSpacingY, cellSpacingX,
							hasBorder);
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
}
