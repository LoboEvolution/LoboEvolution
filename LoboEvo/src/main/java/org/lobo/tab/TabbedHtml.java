package org.lobo.tab;

import org.lobo.common.Strings;
import org.lobo.component.BrowserPanel;
import org.lobo.component.IBrowserFrame;
import org.lobo.http.NavigationManager;
import org.lobo.store.TabStore;
import org.lobobrowser.html.dom.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.gui.HtmlPanel;

public class TabbedHtml {

	public void tab(BrowserPanel panel, String url, int index) {
		final DnDTabbedPane tabbedPane = panel.getTabbedPane();
		final HtmlPanel hpanel = NavigationManager.getHtmlPanel(url, index);
		hpanel.setBrowserPanel(panel);
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";	
		tabbedPane.remove(index);
		tabbedPane.insertTab(title, null, hpanel, title, index);
		tabbedPane.setSelectedIndex(index);
		final IBrowserFrame browserFrame = panel.getBrowserFrame();
		browserFrame.getToolbar().getAddressBar().setText(url);
		TabStore.insertTab(index, url, title);
		panel.getScroll().getViewport().add(tabbedPane);
	}
}