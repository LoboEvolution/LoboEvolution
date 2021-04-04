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

package org.loboevolution.component;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.loboevolution.common.Strings;
import org.loboevolution.component.input.Autocomplete;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.img.ImageViewer;
import org.loboevolution.pdf.PDFViewer;
import org.loboevolution.store.TabStore;
import org.loboevolution.tab.DnDTabbedPane;
import org.loboevolution.tab.TabbedPanePopupMenu;

/**
 * <p>GoAction class.</p>
 *
 *
 *
 */
public class GoAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final JTextField addressBar;

	private final IBrowserPanel panel;

	/**
	 * <p>Constructor for GoAction.</p>
	 *
	 * @param panel a {@link org.loboevolution.component.IBrowserPanel} object.
	 * @param addressBar a {@link javax.swing.JTextField} object.
	 */
	public GoAction(IBrowserPanel panel, JTextField addressBar) {
		this.panel = panel;
		this.addressBar = addressBar;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent evt) {
		final String url = this.addressBar.getText();
		try {
			final BufferedImage image = ImageIO.read(new URL(url));
			if (image != null) {
				goUrlImage(image, url);
			} else {
				if (url.endsWith(".pdf")) {
					PDFViewer viewer = new PDFViewer(true);
					viewer.doOpen(url);
				} else {
					goURL(url);
				}
			}
		} catch (IOException e) {
			if (url.endsWith(".pdf")) {
				PDFViewer viewer = new PDFViewer(true);
				viewer.doOpen(url);
			} else {
				goURL(url);
			}
		}
	}

	private void goURL(String text) {
		final DnDTabbedPane tabbedPane = this.panel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(this.panel));
		final int indexPanel = tabbedPane.getSelectedIndex();
		final HtmlPanel htmlPanel = HtmlPanel.createHtmlPanel(panel, text);
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) htmlPanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
		tabbedPane.remove(indexPanel);
		tabbedPane.insertTab(title, null, htmlPanel, title, indexPanel);
		this.addressBar.setText(text);
		panel.getScroll().getViewport().add(tabbedPane);

		TabStore.deleteTab(indexPanel);
		TabStore.insertTab(indexPanel, text, title);
		NavigationManager.insertHistory(text, title, indexPanel);
		addressBar.removeAll();
		Autocomplete.setupAutoComplete(addressBar, TabStore.getUrls());

	}

	private void goUrlImage(BufferedImage img, String fullURL) {
		final DnDTabbedPane tabbedPane = this.panel.getTabbedPane();
		final ImageViewer viewer = new ImageViewer(img);
		final String title = "Image Viewer";
		final int indexPanel = tabbedPane.getSelectedIndex();
		JPanel jPanel = new JPanel();
		jPanel.add(viewer.getComponent());
		tabbedPane.remove(indexPanel);
		tabbedPane.insertTab(title, null, viewer.getComponent(), title, indexPanel);
		this.addressBar.setText(fullURL);
		panel.getScroll().getViewport().add(tabbedPane);
		TabStore.deleteTab(indexPanel);
		TabStore.insertTab(indexPanel, fullURL, title);
		addressBar.removeAll();
	}
}
