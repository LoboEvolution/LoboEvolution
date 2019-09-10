package org.lobo.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;

import org.lobo.common.Strings;
import org.lobo.component.BrowserFrame;
import org.lobo.component.BrowserPanel;
import org.lobo.http.NavigationManager;
import org.lobo.store.TabStore;
import org.lobo.tab.DnDTabbedPane;
import org.lobo.tab.TabbedPanePopupMenu;
import org.lobo.welcome.WelcomePanel;
import org.lobobrowser.html.dom.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.gui.HtmlPanel;

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
		JComponent comp = null;
		String title = "";
		
		if(this.address != null) {
			comp = NavigationManager.getHtmlPanel(this.address, indexPanel);
			final HtmlPanel hpanel = (HtmlPanel)comp;
			final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
			title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";	
		} else {
			comp = new WelcomePanel(panel);
			title = "Welcome";
		}
		
		tabbedPane.insertTab(title, null, comp, title, indexPanel);
		tabbedPane.setSelectedIndex(indexPanel);
		TabStore.insertTab(indexPanel, this.address, title);
	}
}
