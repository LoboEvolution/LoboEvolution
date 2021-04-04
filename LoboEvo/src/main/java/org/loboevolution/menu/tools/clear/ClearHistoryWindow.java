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

package org.loboevolution.menu.tools.clear;

import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.gui.SwingTasks;

import com.jtattoo.plaf.lobo.LoboButton;
import com.jtattoo.plaf.lobo.LoboCheckBox;
import com.jtattoo.plaf.lobo.LoboPanel;

/**
 * <p>ClearHistoryWindow class.</p>
 *
 *
 *
 */
public class ClearHistoryWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	/** The bookmark panel. */
	private LoboCheckBox bookmark;

	/** The cache anel. */
	private LoboCheckBox cache;

	/** The cookie panel. */
	private LoboCheckBox cookie;

	/** The history button. */
	private JButton historyButton;

	/** The navigation panel. */
	private LoboCheckBox navigation;	

	/**
	 * <p>Constructor for ClearHistoryWindow.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public ClearHistoryWindow(BrowserFrame frame) {
		createAndShowGUI(frame);
	}

	private void createAndShowGUI(BrowserFrame frame) {
		this.cache = new LoboCheckBox("Cache");
		this.cookie = new LoboCheckBox("Cookies");
		this.navigation = new LoboCheckBox("Navigation");
		this.bookmark = new LoboCheckBox("Bookmarks");

		final LoboButton historyButton = new LoboButton();
		historyButton.setAction(new ClearDataAction(this.cache, this.cookie, this.navigation, this.bookmark));
		historyButton.setText("Delete Now");
		this.historyButton = historyButton;
		add(getHistoryBox());
	}
	
	/**
	 * Gets the history box.
	 *
	 * @return the history box
	 */
	private Component getHistoryBox() {
		final LoboPanel groupBox = new LoboPanel("Clear History");
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.add(cache);
		groupBox.add(cookie);
		groupBox.add(navigation);
		groupBox.add(bookmark);
		groupBox.add(SwingTasks.createVerticalFill());
		groupBox.add(historyButton);
		return groupBox;
	}
}
