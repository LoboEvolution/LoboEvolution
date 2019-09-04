package org.lobo.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.lobo.component.BrowserFrame;
import org.lobo.component.BrowserPanel;
import org.lobo.http.NavigationManager;
import org.lobo.store.TabStore;
import org.lobo.tab.DnDTabbedPane;
import org.lobo.tab.TabbedPanePopupMenu;
import org.lobo.welcome.WelcomePanel;

public class OpenInTabAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final String address;

	private final BrowserFrame frame;

	public OpenInTabAction(BrowserFrame frame, String address) {
		this.frame = frame;
		this.address = address;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final BrowserPanel panel = this.frame.getPanel();
		final int indexPanel = panel.getTabbedPane().getIndex() +1;
		final DnDTabbedPane tabbedPane = panel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(panel));
		tabbedPane.insertTab("New Tab", null, this.address != null ? NavigationManager.getHtmlPanel(this.address) : new WelcomePanel(panel), null, indexPanel);
		tabbedPane.setSelectedIndex(indexPanel);
		TabStore.insertTab(indexPanel, this.address != null ? this.address : "");
	}
}
