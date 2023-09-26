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

package org.loboevolution.menu.file;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import org.loboevolution.common.Strings;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.BrowserPanel;
import org.loboevolution.component.ITabbedPane;
import org.loboevolution.component.NavigatorFrame;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.store.TabStore;

/**
 * <p>OpenFileAction class.</p>
 */
public class OpenFileAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	/**
	 * <p>Constructor for OpenFileAction.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public OpenFileAction(final BrowserFrame frame) {
		this.frame = frame;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		final BrowserPanel panel = this.frame.getPanel();
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		final int returnValue = fileChooser.showOpenDialog(panel);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			final File selectedFile = fileChooser.getSelectedFile();
			final String url = selectedFile.toURI().toString();
			final int indexPanel = panel.getTabbedPane().getIndex() +1;
			final ITabbedPane tabbedPane = panel.getTabbedPane();
			tabbedPane.setComponentPopupMenu(panel);
			final HtmlPanel hpanel = NavigatorFrame.createHtmlPanel(panel, url);
			final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
			final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";	
			tabbedPane.insertTab(title, null, hpanel, title, indexPanel);
			tabbedPane.setSelectedIndex(indexPanel);
			TabStore.insertTab(indexPanel, url, title);
			NavigationManager.insertHistory(url, title, indexPanel);
		}
	}
}
