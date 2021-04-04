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

package org.loboevolution.tab;

import org.loboevolution.common.Strings;
import org.loboevolution.component.BrowserPanel;
import org.loboevolution.component.IBrowserFrame;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.store.TabStore;

/**
 * <p>TabbedHtml class.</p>
 *
 *
 *
 */
public class TabbedHtml {

	/**
	 * <p>tab.</p>
	 *
	 * @param panel a {@link org.loboevolution.component.BrowserPanel} object.
	 * @param url a {@link java.lang.String} object.
	 * @param index a int.
	 */
	public void tab(BrowserPanel panel, String url, int index) {
		final DnDTabbedPane tabbedPane = panel.getTabbedPane();
		final HtmlPanel hpanel = HtmlPanel.createHtmlPanel(panel, url);
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";	
		tabbedPane.remove(index);
		tabbedPane.insertTab(title, null, hpanel, title, index);
		tabbedPane.setSelectedIndex(index);
		final IBrowserFrame browserFrame = panel.getBrowserFrame();
		browserFrame.getToolbar().getAddressBar().setText(url);
		TabStore.insertTab(index, url, title);
		NavigationManager.insertHistory(url, title, index);
		panel.getScroll().getViewport().add(tabbedPane);
	}
}
