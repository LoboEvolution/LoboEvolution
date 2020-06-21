package org.loboevolution.menu.file;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import org.loboevolution.common.Strings;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.BrowserPanel;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.store.TabStore;
import org.loboevolution.tab.DnDTabbedPane;
import org.loboevolution.tab.TabbedPanePopupMenu;

/**
 * <p>OpenFileAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class OpenFileAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	/**
	 * <p>Constructor for OpenFileAction.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public OpenFileAction(BrowserFrame frame) {
		this.frame = frame;
	}

	/** {@inheritDoc} */
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
