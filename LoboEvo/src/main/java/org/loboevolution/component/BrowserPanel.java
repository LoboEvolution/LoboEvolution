/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
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
 *
 * @author utente
 * @version $Id: $Id
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
	public BrowserPanel(BrowserFrame browserFrame) {
		super(new BorderLayout());
		this.browserFrame = browserFrame;
		this.scroll = new JScrollPane();
		this.tabbedPane = new DnDTabbedPane(this);
		this.tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(this));
		final List<String> startupURLs = GeneralStore.getStartupURLs();

		if (startupURLs.size() == 0) {
			this.welcome = new WelcomePanel(this);
			this.tabbedPane.addTab("Welcome", this.welcome.getWelocome());
		} else if (startupURLs.size() == 1) {
			final TabbedHtml html = new TabbedHtml();
			final String url = startupURLs.get(0);
			html.tab(this, url, 1);
		} else if (startupURLs.size() > 0) {
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
