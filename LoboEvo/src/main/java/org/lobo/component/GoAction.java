package org.lobo.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import org.lobo.http.NavigationManager;
import org.lobo.tab.DnDTabbedPane;
import org.lobo.tab.TabbedPanePopupMenu;

public class GoAction implements ActionListener {

	private final JTextField addressBar;

	private final BrowserPanel panel;

	public GoAction(BrowserPanel panel, JTextField addressBar) {
		this.panel = panel;
		this.addressBar = addressBar;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		final int indexPanel = this.panel.getTabbedPane().getIndex();
		this.panel.getTabbedPane().remove(indexPanel);
		final DnDTabbedPane tabbedPane = this.panel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(this.panel));
		tabbedPane.insertTab("New Tab", null, NavigationManager.getHtmlPanel(this.addressBar.getText()), null,
				indexPanel);
		tabbedPane.setSelectedIndex(indexPanel);
	}
}