/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2022 Lobo Evolution
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

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.loboevolution.common.IORoutines;
import org.loboevolution.common.Strings;
import org.loboevolution.component.input.Autocomplete;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.img.ImageViewer;
import org.loboevolution.net.MimeType;
import org.loboevolution.net.UserAgent;
import org.loboevolution.pdf.PDFViewer;
import org.loboevolution.store.SearchEngineStore;
import org.loboevolution.store.TabStore;
import org.loboevolution.store.ToolsStore;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <p>GoAction class.</p>
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		String strUrl = this.addressBar.getText();
		try {

			final URL url = new URL(strUrl);
			final String filename = url.getFile();
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
			httpcon.addRequestProperty("User-Agent", UserAgent.getUserAgent());
			httpcon.getHeaderField("Set-Cookie");
			final String mimeType = httpcon.getContentType();
			switch (MimeType.get(mimeType)) {
				case PDF:
					PDFViewer viewer = new PDFViewer(true);
					viewer.doOpen(strUrl);
					break;
				case ICO:
				case BMP:
				case GIF:
				case JPEG:
				case PNG:
				case TIF:
				case WEBP:
					final BufferedImage image = ImageIO.read(url);
					if (image != null) {
						goUrlImage(image, strUrl);
					}
					break;
				case TXT:
					goUrlTxt(strUrl, httpcon, getSyntaxConstants(url), filename);
					break;
				case JS:
					goUrlTxt(strUrl, httpcon, SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT, filename);
					break;
				case CSS:
					goUrlTxt(strUrl, httpcon, SyntaxConstants.SYNTAX_STYLE_CSS, filename);
					break;
				case XML:
					goUrlTxt(strUrl, httpcon, SyntaxConstants.SYNTAX_STYLE_XML, filename);
					break;
				case JSON:
					goUrlTxt(strUrl, httpcon, SyntaxConstants.SYNTAX_STYLE_JSON, filename);
					break;
				case HTML:
				case XHTML:
				case SVG:
					goURL(strUrl, httpcon);
					break;
				default:
					break;
			}
		} catch (Exception e) {
			if (!strUrl.matches("^\\w+?://.*")) {
				SearchEngineStore searchEngine = new ToolsStore().getSelectedSearchEngine();
				strUrl = searchEngine.getBaseUrl() + strUrl;
			}
			goURL(strUrl, null);
		}
	}

	private void goURL(String text, HttpURLConnection httpcon) {
		final ITabbedPane tabbedPane = panel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(this.panel);
		final int indexPanel = tabbedPane.getSelectedIndex();
		HtmlPanel htmlPanel;
		if (httpcon == null) {
			htmlPanel = HtmlPanel.createHtmlPanel(panel, text);
		} else {
			htmlPanel = HtmlPanel.createHtmlPanel(panel, text, httpcon);
		}

		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) htmlPanel.getRootNode();
		final String title = Strings.isNotBlank(htmlPanel.getName()) ? htmlPanel.getName() :
							 Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
		tabbedPane.remove(indexPanel);
		tabbedPane.insertTab(title, null, htmlPanel, title, indexPanel);
		this.addressBar.setText(text);
		panel.getScroll().getViewport().add((Component) tabbedPane);

		TabStore.deleteTab(indexPanel);
		TabStore.insertTab(indexPanel, text, title);
		NavigationManager.insertHistory(text, title, indexPanel);
		addressBar.removeAll();
		Autocomplete.setupAutoComplete(addressBar, TabStore.getUrls());

	}

	private void goUrlImage(BufferedImage img, String fullURL) {
		final ITabbedPane tabbedPane = panel.getTabbedPane();
		final ImageViewer viewer = new ImageViewer(img);
		final String title = "Image Viewer";
		final int indexPanel = tabbedPane.getSelectedIndex();
		JPanel jPanel = new JPanel();
		jPanel.add(viewer.getComponent());
		tabbedPane.remove(indexPanel);
		tabbedPane.insertTab(title, null, viewer.getComponent(), title, indexPanel);
		this.addressBar.setText(fullURL);
		panel.getScroll().getViewport().add((Component) tabbedPane);
		TabStore.deleteTab(indexPanel);
		TabStore.insertTab(indexPanel, fullURL, title);
		addressBar.removeAll();
	}


	private void goUrlTxt(String fullURL, HttpURLConnection httpcon, String mimeType, String title) throws Exception {
		final ITabbedPane tabbedPane = panel.getTabbedPane();
		final int indexPanel = tabbedPane.getSelectedIndex();
		InputStream in = httpcon.getInputStream();
		String source = IORoutines.loadAsText(in, "UTF-8");
		RSyntaxTextArea textArea = new RSyntaxTextArea(source);
		textArea.setSyntaxEditingStyle(mimeType);
		textArea.setEditable(false);
		JScrollPane pane = new JScrollPane(textArea);
		tabbedPane.remove(indexPanel);
		tabbedPane.insertTab(title, null, pane, title, indexPanel);
		this.addressBar.setText(fullURL);
		panel.getScroll().getViewport().add((Component) tabbedPane);
		TabStore.deleteTab(indexPanel);
		TabStore.insertTab(indexPanel, fullURL, title);
		addressBar.removeAll();
	}


	private String getSyntaxConstants(final URL url) {
		final String path = url.getPath();
		final int lastDotIdx = path.lastIndexOf('.');
		final String extension = lastDotIdx == -1 ? "" : path.substring(lastDotIdx + 1);
		final String extensionTL = extension.toLowerCase();
		String mimeType;
		switch (extensionTL) {
			case "java":
				mimeType = SyntaxConstants.SYNTAX_STYLE_JAVA;
				break;
			case "php":
			case "ruby":
				mimeType = SyntaxConstants.SYNTAX_STYLE_RUBY;
				break;
			case "bash":
				mimeType = SyntaxConstants.SYNTAX_STYLE_UNIX_SHELL;
				break;
			case "javascript":
			case "js":
				mimeType = SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT;
				break;
			case "css":
				mimeType = SyntaxConstants.SYNTAX_STYLE_CSS;
				break;
			case "csharp":
				mimeType = SyntaxConstants.SYNTAX_STYLE_CSHARP;
				break;
			case "sql":
				mimeType = SyntaxConstants.SYNTAX_STYLE_SQL;
				break;
			case "xml":
			case "xaml":
				mimeType = SyntaxConstants.SYNTAX_STYLE_XML;
				break;
			case "c":
				mimeType = SyntaxConstants.SYNTAX_STYLE_C;
				break;
			case "objc":
				mimeType = SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS;
				break;
			case "python":
				mimeType = SyntaxConstants.SYNTAX_STYLE_PYTHON;
				break;
			case "perl":
				mimeType = SyntaxConstants.SYNTAX_STYLE_PERL;
				break;
			case "json":
				mimeType = SyntaxConstants.SYNTAX_STYLE_JSON;
				break;
			default:
				mimeType = SyntaxConstants.SYNTAX_STYLE_NONE;
				break;
		}
		return mimeType;
	}
}
