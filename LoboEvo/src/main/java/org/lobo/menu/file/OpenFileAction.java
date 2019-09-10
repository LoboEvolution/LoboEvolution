package org.lobo.menu.file;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import org.lobo.common.Strings;
import org.lobo.component.BrowserFrame;
import org.lobo.component.BrowserPanel;
import org.lobo.http.NavigationManager;
import org.lobo.store.TabStore;
import org.lobo.tab.DnDTabbedPane;
import org.lobo.tab.TabbedPanePopupMenu;
import org.lobobrowser.html.dom.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.gui.HtmlPanel;

public class OpenFileAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	public OpenFileAction(BrowserFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final BrowserPanel panel = this.frame.getPanel();
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		final int returnValue = fileChooser.showOpenDialog(panel);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			final File selectedFile = fileChooser.getSelectedFile();
			final String url = selectedFile.toURI().toString();
			final int indexPanel = panel.getTabbedPane().getIndex() +1;
			final DnDTabbedPane tabbedPane = panel.getTabbedPane();
			tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(panel));
			HtmlPanel hpanel = NavigationManager.getHtmlPanel(url, indexPanel);
			final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
			final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";	
			tabbedPane.insertTab(title, null, hpanel, title, indexPanel);
			tabbedPane.setSelectedIndex(indexPanel);
			TabStore.insertTab(indexPanel, url, title);
		}
	}
}
