package org.lobo.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import org.lobo.common.Strings;
import org.lobo.http.NavigationManager;
import org.lobo.store.TabStore;
import org.lobo.tab.DnDTabbedPane;
import org.lobo.tab.TabbedPanePopupMenu;
import org.lobobrowser.html.dom.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.gui.HtmlPanel;

public class GoAction implements ActionListener {

	private final JTextField addressBar;

	private final IBrowserPanel panel;

	public GoAction(IBrowserPanel panel, JTextField addressBar) {
		this.panel = panel;
		this.addressBar = addressBar;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		final int indexPanel = this.panel.getTabbedPane().getIndex();
		this.panel.getTabbedPane().remove(indexPanel);
		final DnDTabbedPane tabbedPane = this.panel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(this.panel));
		HtmlPanel htmlPanel = NavigationManager.getHtmlPanel(this.addressBar.getText());
		htmlPanel.setBrowserPanel(panel);
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) htmlPanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";	
		tabbedPane.insertTab(title, null, htmlPanel, title, indexPanel);
		tabbedPane.setSelectedIndex(indexPanel);
		TabStore.deleteTab(indexPanel);
		TabStore.insertTab(indexPanel, this.addressBar.getText(), title);
	}
}