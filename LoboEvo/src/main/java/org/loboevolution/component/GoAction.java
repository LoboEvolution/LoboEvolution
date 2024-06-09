/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

import lombok.extern.slf4j.Slf4j;
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
import java.io.Serial;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * <p>GoAction class.</p>
 */
@Slf4j
public class GoAction extends AbstractAction {

	@Serial
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

			final URL url = new URI(addressBarText).toURL();
			final String filename = url.getFile();

			final URLConnection connection = url.openConnection();
			connection.addRequestProperty("User-Agent", UserAgent.getUserAgent());
			connection.getHeaderField("Set-Cookie");
			final String mimeType = connection.getContentType();
			switch (MimeType.get(mimeType)) {
				case PDF:
					final PDFViewer viewer = new PDFViewer(true);
					viewer.doOpen(addressBarText, url, connection);
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
					goUrlTxt(addressBarText, connection, getSyntaxConstants(url), filename);
					break;
				case JS:
					goUrlTxt(addressBarText, connection, SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT, filename);
					break;
				case CSS:
					goUrlTxt(addressBarText, connection, SyntaxConstants.SYNTAX_STYLE_CSS, filename);
					break;
				case XML:
					goUrlTxt(addressBarText, connection, SyntaxConstants.SYNTAX_STYLE_XML, filename);
					break;
				case JSON:
					goUrlTxt(addressBarText, connection, SyntaxConstants.SYNTAX_STYLE_JSON, filename);
					break;
				case HTML:
				case XHTML:
				case SVG:
					goURL(addressBarText, connection);
					break;
				default:
					break;
			}
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
			if (!addressBarText.matches("^\\w+?://.*")) {
				final SearchEngineStore searchEngine = new ToolsStore().getSelectedSearchEngine();
				addressBarText = searchEngine.getBaseUrl() + addressBarText;
			}
			goURL(addressBarText, null);
		}
	}

	private void goURL(final String text, final URLConnection connection) {
		final ITabbedPane tabbedPane = panel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(this.panel);
		final int indexPanel = tabbedPane.getSelectedIndex();
		final HtmlPanel htmlPanel;
		if (connection == null) {
			htmlPanel = NavigatorFrame.createHtmlPanel(panel, text);
		} else {
			htmlPanel = NavigatorFrame.createHtmlPanel(panel, text, connection);
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


	private void goUrlTxt(final String fullURL, final URLConnection connection, final String mimeType, final String title) throws Exception {
		final ITabbedPane tabbedPane = panel.getTabbedPane();
		final int indexPanel = tabbedPane.getSelectedIndex();
		final InputStream in = connection.getInputStream();
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
		final String mimeType = switch (extensionTL) {
            case "java" -> SyntaxConstants.SYNTAX_STYLE_JAVA;
            case "php", "ruby" -> SyntaxConstants.SYNTAX_STYLE_RUBY;
            case "bash" -> SyntaxConstants.SYNTAX_STYLE_UNIX_SHELL;
            case "javascript", "js" -> SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT;
            case "css" -> SyntaxConstants.SYNTAX_STYLE_CSS;
            case "csharp" -> SyntaxConstants.SYNTAX_STYLE_CSHARP;
            case "sql" -> SyntaxConstants.SYNTAX_STYLE_SQL;
            case "xml", "xaml" -> SyntaxConstants.SYNTAX_STYLE_XML;
            case "c" -> SyntaxConstants.SYNTAX_STYLE_C;
            case "objc" -> SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS;
            case "python" -> SyntaxConstants.SYNTAX_STYLE_PYTHON;
            case "perl" -> SyntaxConstants.SYNTAX_STYLE_PERL;
            case "json" -> SyntaxConstants.SYNTAX_STYLE_JSON;
            default -> SyntaxConstants.SYNTAX_STYLE_NONE;
        };
        return mimeType;
	}
}
