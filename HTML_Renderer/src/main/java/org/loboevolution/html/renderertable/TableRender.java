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

package org.loboevolution.html.renderertable;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import org.loboevolution.html.HtmlAttributeProperties;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.info.SizeInfo;
import org.loboevolution.html.renderer.VirtualCell;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.CSSValuesProperties;
import org.loboevolution.html.style.HtmlLength;
import org.loboevolution.html.style.HtmlValues;

public final class TableRender implements HtmlAttributeProperties, CSSValuesProperties {
	
	private TableRender() {
		super();
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
	public static HtmlLength getWidthLength(HTMLElementImpl element, int availWidth) {
		try {
			AbstractCSSProperties props = element.getCurrentStyle();
			String widthText = props == null ? null : props.getWidth();
			if (widthText == null) {
				String widthAttr = element.getAttribute(WIDTH);
				if (widthAttr == null) {
					return null;
				}
				return new HtmlLength(widthAttr);
			} else {

				if (INHERIT.equals(widthText)) {
					widthText = element.getParentStyle().getWidth();
				}

				int width = -1;

				if (widthText != null) {
					width = HtmlValues.getPixelSize(widthText, element.getRenderState(), 0, availWidth);
				}

				if (props.getMaxWidth() != null) {
					int maxWidth = HtmlValues.getPixelSize(props.getMaxWidth(), element.getRenderState(), 0,
							availWidth);

					if (width == 0 || width > maxWidth) {
						width = maxWidth;
					}
				}

				if (props.getMinWidth() != null) {
					int minWidth = HtmlValues.getPixelSize(props.getMinWidth(), element.getRenderState(), 0,
							availWidth);

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
				String ha = element.getAttribute(HEIGHT);
				if (ha == null) {
					return null;
				} else {
					return new HtmlLength(ha);
				}
			} else {

				if (INHERIT.equals(heightText)) {
					heightText = element.getParentStyle().getHeight();
				}

				int height = -1;

				if (heightText != null) {
					height = HtmlValues.getPixelSize(heightText, element.getRenderState(), 0, availHeight);
				}

				if (props.getMaxHeight() != null) {
					int maxHeight = HtmlValues.getPixelSize(props.getMaxHeight(), element.getRenderState(), 0,
							availHeight);

					if (height == 0 || height > maxHeight) {
						height = maxHeight;
					}
				}

				if (props.getMinHeight() != null) {
					int minHeight = HtmlValues.getPixelSize(props.getMinHeight(), element.getRenderState(), 0,
							availHeight);

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
	
	public static int adjustTotalWidths(SizeInfo[] columnSizes, int numCols, int widthUsed, int difference, int totalWidth)  {
		
		int totalWidthUsed = totalWidth;
		int expectedPercentWidthTotal = widthUsed - difference;
		if (expectedPercentWidthTotal < 0) {
			expectedPercentWidthTotal = 0;
		}
		double ratio = (double) expectedPercentWidthTotal / widthUsed;
		for (int i = 0; i < numCols; i++) {
			SizeInfo sizeInfo = columnSizes[i];
			HtmlLength widthLength = columnSizes[i].getHtmlLength();
			if (widthLength != null && widthLength.getLengthType() == HtmlLength.LENGTH) {
				int oldActualSize = sizeInfo.getActualSize();
				int newActualSize = (int) Math.round(oldActualSize * ratio);
				sizeInfo.setActualSize(newActualSize);
				totalWidthUsed += newActualSize - oldActualSize;
			}
		}
		return totalWidthUsed;
	}
	
	public static int adjustCurrentTotal(SizeInfo[] columnSizes, SizeInfo[] rowLenght,
			ArrayList<ArrayList<VirtualCell>> ROWS, int cellSpacingY, int numCols,
			int widthTotal, int difference, int cellSpacingX, int hasBorder, int currTotal) {
		
		int currentTotal = currTotal;
		int expectedPercentWidthTotal = widthTotal - difference;
		if (expectedPercentWidthTotal < 0) {
			expectedPercentWidthTotal = 0;
		}
		double ratio = (double) expectedPercentWidthTotal / widthTotal;
		for (int i = 0; i < numCols; i++) {
			SizeInfo sizeInfo = columnSizes[i];
			HtmlLength widthLength = columnSizes[i].getHtmlLength();
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
	
	public static int adjustCurrentTotal2(SizeInfo[] columnSizes, SizeInfo[] rowLenght,
			ArrayList<ArrayList<VirtualCell>> ROWS, int cellSpacingY, int numCols,
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
	
	public static HtmlLength getBestWidthLength(List<ArrayList<VirtualCell>> rows, int numRows, int index, String op) {
		HtmlLength bestWidthLength = null;
		for (int y = 0; y < numRows; y++) {
			ArrayList<VirtualCell> row = rows.get(y);
			VirtualCell vc;
			int size = row.size();
			if (size > index && index > -1) {
				vc = row.get(index);
				RTableCell ac = vc.getActualCell();
				if (("eq".equals(op) && ac.getColSpan() == 1) || (">".equals(op) && ac.getColSpan() > 1)) {
					HtmlLength vcWidthLength = vc.getWidthLength();
					if (vcWidthLength != null && vcWidthLength.isPreferredOver(bestWidthLength)) {
						bestWidthLength = vcWidthLength;
					}
				}
			}
		}
		return bestWidthLength;
	}
	
	/**
	 * Layout column.
	 */
	private static void layoutColumn(SizeInfo[] columnSizes, SizeInfo[] rowLenght, ArrayList<ArrayList<VirtualCell>> ROWS,
			SizeInfo colSize, int col, int cellSpacingY, int cellSpacingX, int hasBorder) {
		SizeInfo[] rowSizes = rowLenght;
		ArrayList<ArrayList<VirtualCell>> rows = ROWS;
		int numRows = rows.size();
		int actualSize = colSize.getActualSize();
		colSize.setLayoutSize(0);
		for (int row = 0; row < numRows;) {
			// SizeInfo rowSize = rowSizes[row];
			ArrayList<VirtualCell> columns = rows.get(row);
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
						// TODO: better height possible

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
						// TODO: better height possible
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
