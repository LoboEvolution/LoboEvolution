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

package org.loboevolution.menu.tools.clear;

import java.awt.Component;
import java.io.Serial;
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

	@Serial
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
	public ClearHistoryWindow(final BrowserFrame frame) {
		createAndShowGUI(frame);
	}

	private void createAndShowGUI(final BrowserFrame frame) {
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
