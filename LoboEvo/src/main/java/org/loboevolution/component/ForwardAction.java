package org.loboevolution.component;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JTextField;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.store.NavigationStore;
import org.loboevolution.store.TabStore;
import org.loboevolution.tab.DnDTabbedPane;
import org.loboevolution.tab.TabbedPanePopupMenu;

/**
 * <p>ForwardAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ForwardAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final JTextField addressBar;

	private final IBrowserPanel panel;

	/**
	 * <p>Constructor for ForwardAction.</p>
	 *
	 * @param panel a {@link org.loboevolution.component.IBrowserPanel} object.
	 * @param addressBar a {@link javax.swing.JTextField} object.
	 */
	public ForwardAction(IBrowserPanel panel, JTextField addressBar) {
		this.panel = panel;
		this.addressBar = addressBar;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent evt) {
		NavigationStore nh = new NavigationStore();
		final String url = this.addressBar.getText();
		String text = url;
		final int indexPanel = this.panel.getTabbedPane().getSelectedIndex();
		List<String> tabsById = nh.getHostOrdered(indexPanel);
		
		for (int i = 0; i < tabsById.size(); i++) {
			String tab = tabsById.get(i);
			if(tab.equals(url) && i < tabsById.size()-1) {
				text = tabsById.get(i + 1);
			}
		}
						
		final DnDTabbedPane tabbedPane = this.panel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(this.panel));
		final HtmlPanel htmlPanel = HtmlPanel.createHtmlPanel(panel, text);		
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
