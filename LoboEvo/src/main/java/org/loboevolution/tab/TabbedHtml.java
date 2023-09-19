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

package org.loboevolution.tab;

import org.loboevolution.common.Strings;
import org.loboevolution.component.BrowserPanel;
import org.loboevolution.component.IBrowserFrame;
import org.loboevolution.component.ITabbedPane;
import org.loboevolution.component.NavigatorFrame;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.store.TabStore;

import java.awt.*;

/**
 * <p>TabbedHtml class.</p>
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
		final ITabbedPane tabbedPane = panel.getTabbedPane();
		final HtmlPanel hpanel = NavigatorFrame.createHtmlPanel(panel, url);
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";	
		tabbedPane.remove(index);
		tabbedPane.insertTab(title, null, hpanel, title, index);
		tabbedPane.setSelectedIndex(index);
		final IBrowserFrame browserFrame = panel.getBrowserFrame();
		browserFrame.getToolbar().getAddressBar().setText(url);
		TabStore.insertTab(index, url, title);
		NavigationManager.insertHistory(url, title, index);
		panel.getScroll().getViewport().add((Component) tabbedPane);
	}
}
