/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.component;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.loboevolution.store.GeneralStore;
import org.loboevolution.tab.DnDTabbedPane;
import org.loboevolution.tab.TabbedHtml;
import org.loboevolution.tab.TabbedPanePopupMenu;
import org.loboevolution.welcome.WelcomePanel;

/**
 * <p>BrowserPanel class.</p>
 */
public class BrowserPanel extends JPanel implements IBrowserPanel {
	private static final long serialVersionUID = 1L;

	private final JScrollPane scroll;

	private final DnDTabbedPane tabbedPane;

	private IWelcomePanel welcome;
	
	private final BrowserFrame browserFrame;

	/**
	 * <p>Constructor for BrowserPanel.</p>
	 *
	 * @param browserFrame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public BrowserPanel(final BrowserFrame browserFrame) {
		super(new BorderLayout());
		this.browserFrame = browserFrame;
		this.scroll = new JScrollPane();
		this.tabbedPane = new DnDTabbedPane(this);
		this.tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(this));
		final List<String> startupURLs = GeneralStore.getStartupURLs();

		if (startupURLs.isEmpty()) {
			this.welcome = new WelcomePanel(this);
			this.tabbedPane.addTab("Welcome", this.welcome.getWelocome());
		} else if (startupURLs.size() == 1) {
			final TabbedHtml html = new TabbedHtml();
			final String url = startupURLs.get(0);
			html.tab(this, url, 1);
		} else {
			final TabbedHtml html = new TabbedHtml();
			for (int i = 0; i < startupURLs.size(); i++) {
				final String url = startupURLs.get(i);
				html.tab(this, url, i);
			}
		}
		this.scroll.getViewport().add(this.tabbedPane);
		add(this.scroll);
	}

	/**
	 * <p>Getter for the field scroll.</p>
	 *
	 * @return the scroll
	 */
	public JScrollPane getScroll() {
		return this.scroll;
	}

	/**
	 * <p>Getter for the field tabbedPane.</p>
	 *
	 * @return the tabbedPane
	 */
	public DnDTabbedPane getTabbedPane() {
		return this.tabbedPane;
	}

	/**
	 * <p>Getter for the field welcome.</p>
	 *
	 * @return the welcome
	 */
	public IWelcomePanel getWelcome() {
		return this.welcome;
	}
	
	/**
	 * <p>Getter for the field browserFrame.</p>
	 *
	 * @return the browserFrame
	 */
	public BrowserFrame getBrowserFrame() {
		return browserFrame;
	}
}
