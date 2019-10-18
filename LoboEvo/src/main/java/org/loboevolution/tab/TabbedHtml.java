package org.loboevolution.tab;

import org.loboevolution.common.Strings;
import org.loboevolution.component.BrowserPanel;
import org.loboevolution.component.IBrowserFrame;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.store.TabStore;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.gui.HtmlPanel;

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