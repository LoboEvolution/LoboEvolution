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

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.loboevolution.common.IORoutines;
import org.loboevolution.common.Strings;
import org.loboevolution.component.input.Autocomplete;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.gui.HtmlPanel;
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
	public GoAction(final IBrowserPanel panel, final JTextField addressBar) {
		this.panel = panel;
		this.addressBar = addressBar;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(final ActionEvent evt) {
		String addressBarText = this.addressBar.getText();
		try {

			final URL url = new URL(addressBarText);
			final String filename = url.getFile();
			final HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
			httpcon.addRequestProperty("User-Agent", UserAgent.getUserAgent());
			httpcon.getHeaderField("Set-Cookie");
			final String mimeType = httpcon.getContentType();
			switch (MimeType.get(mimeType)) {
				case PDF:
					final PDFViewer viewer = new PDFViewer(true);
					viewer.doOpen(addressBarText);
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
						goUrlImage(image, addressBarText);
					}
					break;
				case TXT:
					goUrlTxt(addressBarText, httpcon, getSyntaxConstants(url), filename);
					break;
				case JS:
					goUrlTxt(addressBarText, httpcon, SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT, filename);
					break;
				case CSS:
					goUrlTxt(addressBarText, httpcon, SyntaxConstants.SYNTAX_STYLE_CSS, filename);
					break;
				case XML:
					goUrlTxt(addressBarText, httpcon, SyntaxConstants.SYNTAX_STYLE_XML, filename);
					break;
				case JSON:
					goUrlTxt(addressBarText, httpcon, SyntaxConstants.SYNTAX_STYLE_JSON, filename);
					break;
				case HTML:
				case XHTML:
				case SVG:
					goURL(addressBarText, httpcon);
					break;
				default:
					break;
			}
		} catch (final Exception e) {
			if (!addressBarText.matches("^\\w+?://.*")) {
				final SearchEngineStore searchEngine = new ToolsStore().getSelectedSearchEngine();
				addressBarText = searchEngine.getBaseUrl() + addressBarText;
			}
			goURL(addressBarText, null);
		}
	}

	private void goURL(final String text, final HttpURLConnection httpcon) {
		final ITabbedPane tabbedPane = panel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(this.panel);
		final int indexPanel = tabbedPane.getSelectedIndex();
		final HtmlPanel htmlPanel;
		if (httpcon == null) {
			htmlPanel = NavigatorFrame.createHtmlPanel(panel, text);
		} else {
			htmlPanel = NavigatorFrame.createHtmlPanel(panel, text, httpcon);
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

	private void goUrlImage(final BufferedImage img, final String fullURL) {
		final ITabbedPane tabbedPane = panel.getTabbedPane();
		final ImageViewer viewer = new ImageViewer(img);
		final String title = "Image Viewer";
		final int indexPanel = tabbedPane.getSelectedIndex();
		final JPanel jPanel = new JPanel();
		jPanel.add(viewer.getComponent());
		tabbedPane.remove(indexPanel);
		tabbedPane.insertTab(title, null, viewer.getComponent(), title, indexPanel);
		this.addressBar.setText(fullURL);
		panel.getScroll().getViewport().add((Component) tabbedPane);
		TabStore.deleteTab(indexPanel);
		TabStore.insertTab(indexPanel, fullURL, title);
		addressBar.removeAll();
	}


	private void goUrlTxt(final String fullURL, final HttpURLConnection httpcon, final String mimeType, final String title) throws Exception {
		final ITabbedPane tabbedPane = panel.getTabbedPane();
		final int indexPanel = tabbedPane.getSelectedIndex();
		final InputStream in = httpcon.getInputStream();
		final String source = IORoutines.loadAsText(in, "UTF-8");
		final RSyntaxTextArea textArea = new RSyntaxTextArea(source);
		textArea.setSyntaxEditingStyle(mimeType);
		textArea.setEditable(false);
		final JScrollPane pane = new JScrollPane(textArea);
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
		final String mimeType;
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
