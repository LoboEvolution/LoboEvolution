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

package org.loboevolution.tab;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 * <p>TabbedPaneUI class.</p>
 *
 *
 *
 */
public class TabbedPaneUI extends BasicTabbedPaneUI {

	private static final int ADDED_TAB_HEIGTH = 2;

	private static final int ADDED_TAB_WIDTH = 10;

	private static final Color SELECTED_TAB_COLOR = Color.BLACK;

	private static final int SPACE_BETWEEN_TAB = 1;

	private static final Color TAB_COLOR = Color.BLACK;

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new TabbedPaneUI((JTabbedPane) c);
	}

	/**
	 * <p>Constructor for TabbedPaneUI.</p>
	 *
	 * @param tabbedPane a {@link javax.swing.JTabbedPane} object.
	 */
	public TabbedPaneUI(JTabbedPane tabbedPane) {
		// FONT: BOLD
		tabbedPane.setFont(tabbedPane.getFont().deriveFont(Font.BOLD));

		// FONT: WHITE
		tabbedPane.setForeground(Color.WHITE);

		tabbedPane.setBackground(SELECTED_TAB_COLOR);

		tabbedPane.setBorder(new SubtleSquareBorder(true));
	}

	// overrided to add spaces between tabs.
	/** {@inheritDoc} */
	@Override
	protected LayoutManager createLayoutManager() {
		if (this.tabPane.getTabLayoutPolicy() == JTabbedPane.SCROLL_TAB_LAYOUT) {
			return super.createLayoutManager();

		} else {
			return new TabbedPaneLayout() {

				@Override
				protected void padSelectedTab(int tabPlacement, int selectedIndex) {
					for (final Rectangle rect : TabbedPaneUI.this.rects) {
						final Rectangle selRect = rect;
						final Insets padInsets = getSelectedTabPadInsets(tabPlacement);
						selRect.x += padInsets.left;
						selRect.width -= padInsets.left + padInsets.right;
						selRect.y -= padInsets.top;
						selRect.height += padInsets.top + padInsets.bottom;
					}
				}
			};
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void installDefaults() {
		super.installDefaults();

		// changed to add more space each side of the tab title.
		this.tabInsets.left = this.tabInsets.left + ADDED_TAB_WIDTH;
		this.tabInsets.right = this.tabInsets.right + ADDED_TAB_WIDTH;
		this.tabInsets.top = this.tabInsets.top + ADDED_TAB_HEIGTH;
		this.tabInsets.bottom = this.tabInsets.bottom + ADDED_TAB_HEIGTH;

		// changed to define the spacing between tabs.
		this.selectedTabPadInsets.left = SPACE_BETWEEN_TAB;
		this.selectedTabPadInsets.right = SPACE_BETWEEN_TAB;
	}

	// overrided to paint the selected tab with a different color.
	/** {@inheritDoc} */
	@Override
	protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
			boolean isSelected) {
		// tabpane background color is not used to paint the unselected tabs
		// because we wants a different color for the background of the tabs
		// and the background of the tabpane (the line just below the tabs)
		g.setColor(!isSelected ? TAB_COLOR : SELECTED_TAB_COLOR);
		switch (tabPlacement) {
		case LEFT:
			g.fillRect(x + 1, y + 1, w - 1, h - 3);
			break;
		case RIGHT:
			g.fillRect(x, y + 1, w - 2, h - 3);
			break;
		case BOTTOM:
			g.fillRect(x + 1, y, w - 3, h - 1);
			break;
		case TOP:
		default:
			g.fillRect(x + 1, y + 1, w - 3, h - 1);
		}
	}
}
