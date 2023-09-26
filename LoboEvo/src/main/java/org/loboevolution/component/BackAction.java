/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.component;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JTextField;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.info.BookmarkInfo;
import org.loboevolution.store.NavigationStore;
import org.loboevolution.store.TabStore;

/**
 * <p>BackAction class.</p>
 */
public class BackAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final JTextField addressBar;

	private final IBrowserPanel panel;

	/**
	 * <p>Constructor for BackAction.</p>
	 *
	 * @param panel a {@link org.loboevolution.component.IBrowserPanel} object.
	 * @param addressBar a {@link javax.swing.JTextField} object.
	 */
	public BackAction(final IBrowserPanel panel, final JTextField addressBar) {
		this.panel = panel;
		this.addressBar = addressBar;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent evt) {
		final NavigationStore nh = new NavigationStore();
		final String url = this.addressBar.getText();
		String text = url;
		final int indexPanel = this.panel.getTabbedPane().getSelectedIndex();		
		final List<BookmarkInfo> tabsById = nh.getRecentHost(indexPanel, true);
		for (int i = 0; i < tabsById.size(); i++) {
			final BookmarkInfo info = tabsById.get(i);
			final String tab = info.getUrl();
			if (tab.equals(url) && i > 0) {
				text = tabsById.get(i - 1).getUrl();
			}
		}
						
		final ITabbedPane tabbedPane = this.panel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(this.panel);
		final HtmlPanel htmlPanel = NavigatorFrame.createHtmlPanel(panel, text);
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) htmlPanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
		tabbedPane.remove(indexPanel);
		tabbedPane.insertTab(title, null, htmlPanel, title, indexPanel);
		this.addressBar.setText(text);
		panel.getScroll().getViewport().add((Component) tabbedPane);
		
		TabStore.deleteTab(indexPanel);
		TabStore.insertTab(indexPanel, text, title);
	}
}
