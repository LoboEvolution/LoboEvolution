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

package org.loboevolution.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;

import org.loboevolution.common.Strings;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.BrowserPanel;
import org.loboevolution.component.ITabbedPane;
import org.loboevolution.component.NavigatorFrame;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.store.TabStore;
import org.loboevolution.welcome.WelcomePanel;

/**
 * <p>OpenInTabAction class.</p>
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
	public void actionPerformed(final ActionEvent e) {
		final BrowserPanel panel = this.frame.getPanel();
		final int indexPanel = panel.getTabbedPane().getIndex() +1;
		final ITabbedPane tabbedPane = panel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(panel);
		JComponent comp = null;
		String title = "";
		
		if (this.address != null) {
			comp = NavigatorFrame.createHtmlPanel(panel, this.address);
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
