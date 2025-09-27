/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package com.jtattoo.plaf.base;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.IconFactory;
import com.jtattoo.plaf.JTattooUtilities;
import org.loboevolution.common.ArrayUtilities;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * <p>BaseTableHeaderUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BaseTableHeaderUI extends BasicTableHeaderUI {

	// ----------------------------------------------------------------------------------------------------------------------
// inner classes
//----------------------------------------------------------------------------------------------------------------------
	private final class BaseDefaultHeaderRenderer extends DefaultTableCellRenderer {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public BaseDefaultHeaderRenderer() {
			super();
		}

		@Override
		public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus,
                                                       final int row, final int column) {
			return new MyRenderComponent(table, value, isSelected, hasFocus, row, column);
		}

	} // end of class BaseDefaultHeaderRenderer

	private final class MyRenderComponent extends JLabel {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private JTable table = null;
		private int col = 0;

		public MyRenderComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int col) {
			super();
			this.table = table;
			this.col = col;
			if (value != null) {
				setText(value.toString());
			} else {
				setText("");
			}
			setOpaque(false);
			if (table != null && table.getClientProperty("TableHeader.font") != null) {
				setFont((Font) table.getClientProperty("TableHeader.font"));
			} else {
				setFont(UIManager.getFont("TableHeader.font"));
			}
			if (col == rolloverCol) {
				setForeground(AbstractLookAndFeel.getTheme().getRolloverForegroundColor());
			} else {
				setForeground(UIManager.getColor("TableHeader.foreground"));
			}
			setHorizontalAlignment(SwingConstants.CENTER);
			setHorizontalTextPosition(SwingConstants.LEADING);
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));
			if (table != null && UIManager.getLookAndFeel() instanceof AbstractLookAndFeel) {
				final RowSorter rowSorter = table.getRowSorter();
				final List keyList = rowSorter == null ? null : rowSorter.getSortKeys();
				if (ArrayUtilities.isNotBlank(keyList)) {
					final RowSorter.SortKey sortKey = (RowSorter.SortKey) keyList.get(0);
					if (sortKey.getColumn() == table.convertColumnIndexToModel(col)) {
						final IconFactory iconFactory = ((AbstractLookAndFeel) UIManager.getLookAndFeel())
								.getIconFactory();
						if (sortKey.getSortOrder().equals(SortOrder.ASCENDING)) {
							setIcon(iconFactory.getUpArrowIcon());
						} else if (sortKey.getSortOrder().equals(SortOrder.DESCENDING)) {
							setIcon(iconFactory.getDownArrowIcon());
						}
					}
				}
			}
		}

		@Override
		public void paint(final Graphics g) {
			paintBackground(g);
			super.paint(g);
		}

		protected void paintBackground(final Graphics g) {
			// BugFix: 12.12.2013
			// Currently I don't know why header is sometimes null, but if it's null it's
			// better to ignore
			// the background instead of throwing a NPE.
			if (header == null) {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getColHeaderColors(), 0, 0,
						getWidth(), getHeight());
				return;
			}
			int draggedColumn = -1;
			if (header.getTable() != null && header.getDraggedColumn() != null) {
				draggedColumn = header.getColumnModel().getColumnIndex(header.getDraggedColumn().getIdentifier());
			}
			final int w = getWidth();
			final int h = getHeight();
			if (table != null && table.isEnabled() && (col == rolloverCol || col == draggedColumn)) {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getRolloverColors(), 0, 0, w, h);
				if (drawRolloverBar()) {
					g.setColor(AbstractLookAndFeel.getFocusColor());
					g.drawLine(0, 0, w - 1, 0);
					g.drawLine(0, 1, w - 1, 1);
					g.drawLine(0, 2, w - 1, 2);
				}
			} else if (drawAlwaysActive() || JTattooUtilities.isFrameActive(header)) {
				if (header.getBackground() instanceof ColorUIResource) {
					JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getColHeaderColors(), 0, 0, w,
							h);
				} else {
					g.setColor(header.getBackground());
					g.fillRect(0, 0, w, h);
				}
			} else {
				if (header.getBackground() instanceof ColorUIResource) {
					JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getInActiveColors(), 0, 0, w, h);
				} else {
					g.setColor(header.getBackground());
					g.fillRect(0, 0, w, h);
				}
			}
		}

	} // end of class MyRenderComponent

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent h) {
		return new BaseTableHeaderUI();
	}

	private TableCellRenderer originalHeaderRenderer;

	protected MouseAdapter myMouseAdapter = null;

	protected MouseMotionAdapter myMouseMotionAdapter = null;

	protected int rolloverCol = -1;

	/**
	 * <p>drawAlwaysActive.</p>
	 *
	 * @return a boolean.
	 */
	protected boolean drawAlwaysActive() {
		return false;
	}

	/**
	 * <p>drawRolloverBar.</p>
	 *
	 * @return a boolean.
	 */
	protected boolean drawRolloverBar() {
		return false;
	}

	private int getHeaderHeight() {
		if (header == null) {
			return 0;
		}
		int height = 0;
		boolean accomodatedDefault = false;
		final TableColumnModel columnModel = header.getColumnModel();
		for (int column = 0; column < columnModel.getColumnCount(); column++) {
			final TableColumn aColumn = columnModel.getColumn(column);
			final boolean isDefault = aColumn.getHeaderRenderer() == null;

			if (!isDefault || !accomodatedDefault) {
				final Component comp = getHeaderRenderer(column);
				final int rendererHeight = comp.getPreferredSize().height;
				height = Math.max(height, rendererHeight);

				// Configuring the header renderer to calculate its preferred size
				// is expensive. Optimise this by assuming the default renderer
				// always has the same height as the first non-zero height that
				// it returns for a non-null/non-empty value.
				if (isDefault && rendererHeight > 0) {
					Object headerValue = aColumn.getHeaderValue();
					if (headerValue != null) {
						headerValue = headerValue.toString();

						if (headerValue != null && !headerValue.equals("")) {
							accomodatedDefault = true;
						}
					}
				}
			}
		}
		return height + 2;
	}

	/** {@inheritDoc} */
	protected Component getHeaderRenderer(final int col) {
		if (header == null) {
			return null;
		}
		final TableColumn tabCol = header.getColumnModel().getColumn(col);
		TableCellRenderer renderer = tabCol.getHeaderRenderer();
		if (renderer == null) {
			renderer = header.getDefaultRenderer();
		}
		return renderer.getTableCellRendererComponent(header.getTable(), tabCol.getHeaderValue(), false, false, -1,
				col);
	}

	/**
	 * {@inheritDoc}
	 *
	 * Return the preferred size of the header. The preferred height is the maximum
	 * of the preferred heights of all of the components provided by the header
	 * renderers. The preferred width is the sum of the preferred widths of each
	 * column (plus inter-cell spacing).
	 */
	@Override
	public Dimension getPreferredSize(final JComponent c) {
		if (header == null) {
			return new Dimension(0, 0);
		}
		long width = 0;
		final Enumeration<TableColumn> enumeration = header.getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			final TableColumn aColumn = enumeration.nextElement();
			width = width + aColumn.getPreferredWidth();
		}
		if (width > Integer.MAX_VALUE) {
			width = Integer.MAX_VALUE;
		}
		return new Dimension((int) width, getHeaderHeight());
	}

	/** {@inheritDoc} */
	@Override
	public void installListeners() {
		super.installListeners();
		myMouseAdapter = new MouseAdapter() {

			@Override
			public void mouseEntered(final MouseEvent e) {
				if (header == null || header.getTable() == null) {
					return;
				}
				final boolean rolloverEnabled = Boolean.TRUE.equals(header.getClientProperty("rolloverEnabled"));
				final boolean sortingAllowed = header.getTable().getRowSorter() != null;
				if (rolloverEnabled || sortingAllowed || header.getReorderingAllowed()) {
					final int oldRolloverCol = rolloverCol;
					rolloverCol = header.getTable().columnAtPoint(e.getPoint());
					updateRolloverColumn(oldRolloverCol, rolloverCol);
				}
			}

			@Override
			public void mouseExited(final MouseEvent e) {
				if (header == null || header.getTable() == null) {
					return;
				}
				final boolean rolloverEnabled = Boolean.TRUE.equals(header.getClientProperty("rolloverEnabled"));
				final boolean sortingAllowed = header.getTable().getRowSorter() != null;
				if (rolloverEnabled || sortingAllowed || header.getReorderingAllowed()) {
					final int oldRolloverCol = rolloverCol;
					rolloverCol = -1;
					updateRolloverColumn(oldRolloverCol, rolloverCol);
				}
			}

			@Override
			public void mouseReleased(final MouseEvent e) {
				if (header == null || header.getTable() == null) {
					return;
				}
				final boolean rolloverEnabled = Boolean.TRUE.equals(header.getClientProperty("rolloverEnabled"));
				final boolean sortingAllowed = header.getTable().getRowSorter() != null;
				if (rolloverEnabled || sortingAllowed || header.getReorderingAllowed()) {
					final Point pt = e.getPoint();
					SwingUtilities.invokeLater(() -> {
						if (header.getBounds().contains(pt)) {
							final int oldRolloverCol1 = rolloverCol;
							rolloverCol = header.getTable().columnAtPoint(pt);
							updateRolloverColumn(oldRolloverCol1, rolloverCol);
						} else {
							final int oldRolloverCol2 = rolloverCol;
							rolloverCol = -1;
							updateRolloverColumn(oldRolloverCol2, rolloverCol);
						}
					});
				}
			}
		};
		myMouseMotionAdapter = new MouseMotionAdapter() {

			@Override
			public void mouseDragged(final MouseEvent e) {
				if (header == null || header.getTable() == null) {
					return;
				}
				final boolean rolloverEnabled = Boolean.TRUE.equals(header.getClientProperty("rolloverEnabled"));
				final boolean sortingAllowed = header.getTable().getRowSorter() != null;
				if (rolloverEnabled || sortingAllowed || header.getReorderingAllowed()) {
					if (header.getDraggedColumn() != null && header.getDraggedColumn().getIdentifier() != null) {
						rolloverCol = header.getColumnModel().getColumnIndex(header.getDraggedColumn().getIdentifier());
					} else if (header.getResizingColumn() != null) {
						rolloverCol = -1;
					}
				}
			}

			@Override
			public void mouseMoved(final MouseEvent e) {
				if (header == null || header.getTable() == null) {
					return;
				}
				final boolean rolloverEnabled = Boolean.TRUE.equals(header.getClientProperty("rolloverEnabled"));
				final boolean sortingAllowed = header.getTable().getRowSorter() != null;
				if (rolloverEnabled || sortingAllowed || header.getReorderingAllowed()) {
					if (header.getDraggedColumn() == null) {
						final int oldRolloverCol = rolloverCol;
						rolloverCol = header.getTable().columnAtPoint(e.getPoint());
						updateRolloverColumn(oldRolloverCol, rolloverCol);
					}
				}
			}
		};
		header.addMouseListener(myMouseAdapter);
		header.addMouseMotionListener(myMouseMotionAdapter);
	}

	/** {@inheritDoc} */
	@Override
	public void installUI(final JComponent c) {
		super.installUI(c);
		if (header != null) {
			originalHeaderRenderer = header.getDefaultRenderer();
			if (originalHeaderRenderer != null && "sun.swing.table.DefaultTableCellHeaderRenderer"
					.equals(originalHeaderRenderer.getClass().getName())) {
				header.setDefaultRenderer(new BaseDefaultHeaderRenderer());
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g, final JComponent c) {
		if (header == null || header.getColumnModel().getColumnCount() <= 0) {
			return;
		}

		final boolean ltr = header.getComponentOrientation().isLeftToRight();
		final Rectangle clip = g.getClipBounds();
		final Point left = clip.getLocation();
		final Point right = new Point(clip.x + clip.width - 1, clip.y);
		final TableColumnModel cm = header.getColumnModel();
		int cMin = header.columnAtPoint(ltr ? left : right);
		int cMax = header.columnAtPoint(ltr ? right : left);
		// This should never happen.
		if (cMin == -1) {
			cMin = 0;
		}
		// If the table does not have enough columns to fill the view we'll get -1.
		// Replace this with the index of the last column.
		if (cMax == -1) {
			cMax = cm.getColumnCount() - 1;
		}

		final TableColumn draggedColumn = header.getDraggedColumn();
		final Rectangle cellRect = header.getHeaderRect(ltr ? cMin : cMax);
		int columnWidth;
		TableColumn aColumn;
		if (ltr) {
			for (int column = cMin; column <= cMax; column++) {
				aColumn = cm.getColumn(column);
				columnWidth = aColumn.getWidth();
				cellRect.width = columnWidth;
				if (aColumn != draggedColumn) {
					paintCell(g, cellRect, column);
				}
				cellRect.x += columnWidth;
			}
		} else {
			for (int column = cMax; column >= cMin; column--) {
				aColumn = cm.getColumn(column);
				columnWidth = aColumn.getWidth();
				cellRect.width = columnWidth;
				if (aColumn != draggedColumn) {
					paintCell(g, cellRect, column);
				}
				cellRect.x += columnWidth;
			}
		}

		// Paint the dragged column if we are dragging.
		if (draggedColumn != null) {
			final int draggedColumnIndex = viewIndexForColumn(draggedColumn);
			final Rectangle draggedCellRect = header.getHeaderRect(draggedColumnIndex);
			// Draw a gray well in place of the moving column.
			g.setColor(header.getParent().getBackground());
			g.fillRect(draggedCellRect.x, draggedCellRect.y, draggedCellRect.width, draggedCellRect.height);
			draggedCellRect.x += header.getDraggedDistance();

			// Fill the background.
			g.setColor(header.getBackground());
			g.fillRect(draggedCellRect.x, draggedCellRect.y, draggedCellRect.width, draggedCellRect.height);
			paintCell(g, draggedCellRect, draggedColumnIndex);
		}

		// Remove all components in the rendererPane.
		rendererPane.removeAll();
	}

	/**
	 * <p>paintBackground.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param cellRect a {@link java.awt.Rectangle} object.
	 * @param col a {@link java.lang.Integer} object.
	 */
	protected void paintBackground(final Graphics g, final Rectangle cellRect, final int col) {
		final Component component = getHeaderRenderer(col);
		final int x = cellRect.x;
		final int y = cellRect.y;
		final int w = cellRect.width;
		final int h = cellRect.height;
		if (header.getBackground() instanceof ColorUIResource) {
			if (col == rolloverCol && component != null && component.isEnabled()) {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getRolloverColors(), x, y, w, h);
			} else if (drawAlwaysActive() || JTattooUtilities.isFrameActive(header)) {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getColHeaderColors(), x, y, w, h);
			} else {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getInActiveColors(), x, y, w, h);
			}
		} else {
			g.setColor(header.getBackground());
			g.fillRect(x, y, w, h);
		}
	}

	/** {@inheritDoc} */
	protected void paintCell(final Graphics g, final Rectangle cellRect, final int col) {
		if (header == null) {
			return;
		}
		final Component component = getHeaderRenderer(col);
		if (!(component instanceof BaseDefaultHeaderRenderer)) {
			paintBackground(g, cellRect, col);
		}
		rendererPane.paintComponent(g, component, header, cellRect.x, cellRect.y, cellRect.width, cellRect.height,
				true);
	}

	/** {@inheritDoc} */
	@Override
	protected void rolloverColumnUpdated(final int oldColumn, final int newColumn) {
		// Empty to avoid multiple paints
	}

	/** {@inheritDoc} */
	@Override
	public void uninstallListeners() {
		header.removeMouseListener(myMouseAdapter);
		header.removeMouseMotionListener(myMouseMotionAdapter);
		super.uninstallListeners();
	}

	/** {@inheritDoc} */
	@Override
	public void uninstallUI(final JComponent c) {
		if (header != null) {
			if (header.getDefaultRenderer() instanceof BaseDefaultHeaderRenderer) {
				header.setDefaultRenderer(originalHeaderRenderer);
			}
		}
		super.uninstallUI(c);
	}

	/**
	 * <p>updateRolloverColumn.</p>
	 *
	 * @param oldColumn a {@link java.lang.Integer} object.
	 * @param newColumn a {@link java.lang.Integer} object.
	 */
	protected void updateRolloverColumn(final int oldColumn, final int newColumn) {
		if (header == null) {
			return;
		}
		header.repaint(header.getHeaderRect(oldColumn));
		header.repaint(header.getHeaderRect(newColumn));
	}

	private int viewIndexForColumn(final TableColumn aColumn) {
		if (header == null) {
			return -1;
		}
		final TableColumnModel cm = header.getColumnModel();
		for (int column = 0; column < cm.getColumnCount(); column++) {
			if (cm.getColumn(column) == aColumn) {
				return column;
			}
		}
		return -1;
	}

} // end of class BaseTableHeaderUI
