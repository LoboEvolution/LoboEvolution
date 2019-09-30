package org.lobo.component;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JTextField;

import org.lobo.common.Strings;
import org.lobo.store.NavigationStore;
import org.lobo.store.TabStore;
import org.lobo.tab.DnDTabbedPane;
import org.lobo.tab.TabbedPanePopupMenu;
import org.lobobrowser.html.dom.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.gui.HtmlPanel;

public class BackAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final JTextField addressBar;

	private final IBrowserPanel panel;

	public BackAction(IBrowserPanel panel, JTextField addressBar) {
		this.panel = panel;
		this.addressBar = addressBar;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		NavigationStore nh = new NavigationStore();
		final String url = this.addressBar.getText();
		String text = url;
		final int indexPanel = this.panel.getTabbedPane().getSelectedIndex();
		List<String> tabsById = nh.getHostOrdered(indexPanel);
		
		for (int i = 0; i < tabsById.size(); i++) {
			String tab = tabsById.get(i);
			System.out.println(tab + " " + i);
			if(tab.equals(url) && i > 0) {
				text = tabsById.get(i - 1);
			}
		}
						
		final DnDTabbedPane tabbedPane = this.panel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(this.panel));
		final HtmlPanel htmlPanel = HtmlPanel.createHtmlPanel(text);
		htmlPanel.setBrowserPanel(panel);
		
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) htmlPanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
		tabbedPane.remove(indexPanel);
		tabbedPane.insertTab(title, null, htmlPanel, title, indexPanel);
		this.addressBar.setText(text);
		panel.getScroll().getViewport().add(tabbedPane);
		
		TabStore.deleteTab(indexPanel);
		TabStore.insertTab(indexPanel, text, title);
	}
}