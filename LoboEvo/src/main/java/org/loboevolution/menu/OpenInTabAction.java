package org.loboevolution.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;

import org.loboevolution.common.Strings;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.BrowserPanel;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.store.TabStore;
import org.loboevolution.tab.DnDTabbedPane;
import org.loboevolution.tab.TabbedPanePopupMenu;
import org.loboevolution.welcome.WelcomePanel;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.gui.HtmlPanel;

/**
 * <p>OpenInTabAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class OpenInTabAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final String address;

	private final BrowserFrame frame;

	/**
	 * <p>Constructor for OpenInTabAction.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 * @param address a {@link java.lang.String} object.
	 */
	public OpenInTabAction(BrowserFrame frame, String address) {
		this.frame = frame;
		this.address = address;
	}

	/** {@inheritDoc} */
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
