/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

import org.loboevolution.common.Strings;
import org.loboevolution.component.IBrowserFrame;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.component.ITabbedPane;
import org.loboevolution.store.TabStore;
import org.sexydock.tabs.jhrome.JhromeTabbedPaneUI;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * <p>DnDTabbedPane class.</p>
 */
public class DnDTabbedPane extends JTabbedPane implements ITabbedPane {

	private static final long serialVersionUID = 1L;

	private IBrowserPanel browserPanel;

	private int index;
	
	/**
	 * <p>Constructor for DnDTabbedPane.</p>
	 *
	 * @param browserPanel a {@link org.loboevolution.component.IBrowserPanel} object.
	 */
	public DnDTabbedPane(IBrowserPanel browserPanel) {
		init(browserPanel);
	}

	private void init(IBrowserPanel browserPanel) {
		setUI(new JhromeTabbedPaneUI());
		putClientProperty(JhromeTabbedPaneUI.NEW_TAB_BUTTON_VISIBLE, false);
		putClientProperty(JhromeTabbedPaneUI.TAB_CLOSE_BUTTONS_VISIBLE, true);
		putClientProperty(JhromeTabbedPaneUI.ANIMATION_FACTOR, 0.95);

		this.browserPanel = browserPanel;
		final ChangeListener changeListener = changeEvent -> {
			final JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
			IBrowserFrame browserFrame = browserPanel.getBrowserFrame();
			String uri = TabStore.getTab(sourceTabbedPane.getSelectedIndex());
			if (browserFrame.getToolbar() != null && Strings.isNotBlank(uri)) {
				browserFrame.getToolbar().getAddressBar().setText(uri);
			}
			setIndex(sourceTabbedPane.getSelectedIndex());
		};
		addChangeListener(changeListener);
	}

	/**
	 * <p>Getter for the field index.</p>
	 *
	 * @return the index
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * <p>Setter for the field index.</p>
	 *
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * <p>Getter for the field browserPanel.</p>
	 *
	 * @return the browserPanel
	 */
	public IBrowserPanel getBrowserPanel() {
		return browserPanel;
	}

	@Override
	public void setComponentPopupMenu(final IBrowserPanel panel) {
		super.setComponentPopupMenu(new TabbedPanePopupMenu(panel));
	}

	@Override
	public void insertTab(
			final String title,
			final Icon icon,
			final Component component,
			final String tip,
			final int index) {
		super.insertTab(title, icon, component, tip, index);
	}
}
