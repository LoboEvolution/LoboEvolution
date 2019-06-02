package org.lobo.component;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.lobo.menu.tools.pref.GeneralSettings;
import org.lobo.tab.DnDTabbedPane;
import org.lobo.tab.TabbedHtml;
import org.lobo.tab.TabbedPanePopupMenu;
import org.lobo.welcome.WelcomePanel;

public class BrowserPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JScrollPane scroll;

	private final DnDTabbedPane tabbedPane;

	private WelcomePanel welcome;

	public BrowserPanel() {

		super(new BorderLayout());
		this.scroll = new JScrollPane();
		this.tabbedPane = new DnDTabbedPane();
		this.tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(this));
		final List<String> startupURLs = GeneralSettings.getStartupURLs();

		if (startupURLs.size() == 0) {
			this.welcome = new WelcomePanel(this);
			this.tabbedPane.addTab("Welcome", this.welcome);
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
	 * @return the scroll
	 */
	public JScrollPane getScroll() {
		return this.scroll;
	}

	/**
	 * @return the tabbedPane
	 */
	public DnDTabbedPane getTabbedPane() {
		return this.tabbedPane;
	}

	/**
	 * @return the welcome
	 */
	public WelcomePanel getWelcome() {
		return this.welcome;
	}
}
