package org.lobo.component;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import org.lobo.tab.DnDTabbedPane;
import org.lobo.tab.TabbedPanePopupMenu;
import org.lobo.welcome.WelcomePanel;

public class HomeAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final IBrowserPanel panel;

	public HomeAction(IBrowserPanel panel) {
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		final int indexPanel = this.panel.getTabbedPane().getSelectedIndex();
		this.panel.getTabbedPane().remove(indexPanel);
		final DnDTabbedPane tabbedPane = this.panel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(this.panel));
		tabbedPane.insertTab("Welcome", null, new WelcomePanel(panel), "Welcome", indexPanel);
		tabbedPane.setSelectedIndex(indexPanel);
	}
}