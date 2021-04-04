/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;

import org.loboevolution.common.Strings;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.BrowserPanel;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.store.TabStore;
import org.loboevolution.tab.DnDTabbedPane;
import org.loboevolution.tab.TabbedPanePopupMenu;
import org.loboevolution.welcome.WelcomePanel;

/**
 * <p>OpenInTabAction class.</p>
 *
 *
 *
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
			comp = HtmlPanel.createHtmlPanel(panel, this.address);
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
		NavigationManager.insertHistory(this.address, title, indexPanel);
	}
}
