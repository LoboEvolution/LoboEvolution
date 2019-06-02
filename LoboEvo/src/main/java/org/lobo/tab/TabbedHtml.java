package org.lobo.tab;

import org.lobo.component.BrowserPanel;
import org.lobo.http.NavigationManager;

public class TabbedHtml {

	public void tab(BrowserPanel panel, String url, int indexPanel) {
		final DnDTabbedPane tabbedPane = panel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(panel));
		tabbedPane.insertTab("New Tab", null, NavigationManager.getHtmlPanel(url), null, indexPanel);
		tabbedPane.setSelectedIndex(indexPanel);
	}
}