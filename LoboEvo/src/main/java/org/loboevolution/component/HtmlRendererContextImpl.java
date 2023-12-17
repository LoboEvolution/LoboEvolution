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
/*
 * Created on Oct 22, 2005
 */
package org.loboevolution.component;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.RecordedInputStream;
import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.config.HtmlRendererConfigImpl;
import org.loboevolution.gui.HtmlContextMenu;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.dom.HTMLAnchorElement;
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.domimpl.HTMLAnchorElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLImageElementImpl;
import org.loboevolution.html.dom.input.FormInput;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.html.parser.DocumentBuilderImpl;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.img.ImageViewer;
import org.loboevolution.info.BookmarkInfo;
import org.loboevolution.net.HttpNetwork;
import org.loboevolution.net.UserAgent;
import org.loboevolution.pdf.PDFViewer;
import org.loboevolution.store.GeneralStore;
import org.loboevolution.store.LinkStore;
import org.loboevolution.store.NavigationStore;
import org.loboevolution.store.TabStore;
import org.xml.sax.InputSource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/**
 * The HtmlRendererContextImpl class implements the
 * {@link HtmlRendererContextImpl} interface. Note that this
 * class provides rudimentary implementations of most callback methods.
 * Overridding some methods in this class will usually be necessary in a
 * professional application.
 * <p>
 * A simple way to load a URL into the {@link HtmlPanel} of the renderer context
 * is to invoke {@link #navigate(String)}.
 */
@Slf4j
public class HtmlRendererContextImpl implements HtmlRendererContext {

	private UserAgentContext bcontext;

	protected URLConnection currentConnection;

	private HtmlPanel htmlPanel;

	private volatile HtmlRendererContext opener;

	private final HtmlRendererContext parentRcontext;

	private final HtmlRendererConfig config;

	private double scrollx;

	private double scrolly;

	/**
	 * Constructs a HtmlRendererContextImpl that is a child of another
	 * {@link HtmlRendererContextImpl}.
	 *
	 * @param parentRcontext   The parent's renderer context.
	 * @param htmlPanel a {@link HtmlPanel} object.
	 * @param config a {@link HtmlRendererConfig} object.
	 */
	public HtmlRendererContextImpl(final HtmlPanel htmlPanel, final HtmlRendererContext parentRcontext, final HtmlRendererConfig config) {
		this.htmlPanel = htmlPanel;
		this.parentRcontext = parentRcontext;
		this.bcontext = parentRcontext == null ? null : parentRcontext.getUserAgentContext();
		this.config = config;
	}

	/**
	 * Constructs a HtmlRendererContextImpl.
	 *
	 * @param htmlPanel a {@link HtmlPanel} object.
	 * @param ucontext a {@link UserAgentContext} object.
	 * @param config a {@link HtmlRendererConfig} object.
	 */
	public HtmlRendererContextImpl(final HtmlPanel htmlPanel, final UserAgentContext ucontext, final HtmlRendererConfig config) {
		this.htmlPanel = htmlPanel;
		this.parentRcontext = null;
		this.bcontext = ucontext;
		this.config = config;
	}

	/** {@inheritDoc} */
	@Override
	public void alert(final String message) {
		JOptionPane.showMessageDialog(this.htmlPanel, message);
	}

	/** {@inheritDoc} */
	@Override
	public void back() {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		final ITabbedPane tabbedPane = bpanel.getTabbedPane();
		final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
		final IToolBar toolbar = browserFrame.getToolbar();
		final JTextField addressBar = toolbar.getAddressBar();
		String url = addressBar.getText();
		tabbedPane.setComponentPopupMenu(bpanel);
		final NavigationStore nh = new NavigationStore();
        final int indexPanel = tabbedPane.getSelectedIndex();
        final List<BookmarkInfo> tabsById = nh.getRecentHost(indexPanel, true);
		for (int i = 0; i < tabsById.size(); i++) {
			final BookmarkInfo info = tabsById.get(i);
			final String tab = info.getUrl();
			if (tab.equals(url) && i > 0) {
				url = tabsById.get(i - 1).getUrl();
			}
		}

        final HtmlPanel hpanel = NavigatorFrame.createHtmlPanel(bpanel, url);
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
        tabbedPane.remove(indexPanel);
        tabbedPane.insertTab(title, null, htmlPanel, title, indexPanel);
		
        browserFrame.getToolbar().getAddressBar().setText(url);
        bpanel.getScroll().getViewport().add((Component)tabbedPane);
        
        TabStore.deleteTab(indexPanel);
        TabStore.insertTab(indexPanel, url, title);
	}

	/** {@inheritDoc} */
	@Override
	public void blur() {
		this.warn("back(): Not overridden");
	}

	/** {@inheritDoc} */
	@Override
	public void close() {
		this.warn("close(): Not overridden");
	}

	/** {@inheritDoc} */
	@Override
	public boolean confirm(final String message) {
		final int retValue = JOptionPane.showConfirmDialog(this.htmlPanel, message, "Confirm",
				JOptionPane.YES_NO_OPTION);
		return retValue == JOptionPane.YES_OPTION;
	}

	/** {@inheritDoc} */
	@Override
	public void error(final String message) {
		if (log.isErrorEnabled()) {
			log.error(message);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void error(final String message, final Throwable throwable) {
		if (log.isErrorEnabled()) {
			log.error(message, throwable);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void focus() {
		this.warn("focus(): Not overridden");
	}

	/** {@inheritDoc} */
	@Override
	public void forward() {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		final ITabbedPane tabbedPane = bpanel.getTabbedPane();
		final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
		final IToolBar toolbar = browserFrame.getToolbar();
		final JTextField addressBar = toolbar.getAddressBar();
		String url = addressBar.getText();
		tabbedPane.setComponentPopupMenu(bpanel);
		final NavigationStore nh = new NavigationStore();
        final int indexPanel = tabbedPane.getSelectedIndex();
        final List<BookmarkInfo> tabsById = nh.getRecentHost(indexPanel, true);
		for (int i = 0; i < tabsById.size(); i++) {
			final BookmarkInfo info = tabsById.get(i);
			final String tab = info.getUrl();
			if (tab.equals(url) && i < tabsById.size() - 1) {
				url = tabsById.get(i + 1).getUrl();
			}
		}

        final HtmlPanel hpanel = NavigatorFrame.createHtmlPanel(bpanel, url);
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
        tabbedPane.remove(indexPanel);
        tabbedPane.insertTab(title, null, htmlPanel, title, indexPanel);
		
        browserFrame.getToolbar().getAddressBar().setText(url);
        bpanel.getScroll().getViewport().add((Component)tabbedPane);
        
        TabStore.deleteTab(indexPanel);
        TabStore.insertTab(indexPanel, url, title);
	}

	/** {@inheritDoc} */
	@Override
	public String getCurrentURL() {
		final HtmlPanel html = htmlPanel;
		final IBrowserPanel panel = html.getBrowserPanel();
		if (panel != null) {
			final IBrowserFrame frame = panel.getBrowserFrame();
			final IToolBar toolbar = frame.getToolbar();
			final JTextField jtf = toolbar.getAddressBar();
			return jtf.getText();
		} else {
			return "";
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getDefaultStatus() {
		this.warn("getDefaultStatus(): Not overridden");
		return "";
	}

	/** {@inheritDoc} */
	@Override
	public HtmlPanel getHtmlPanel() {
		return this.htmlPanel;
	}

	/** {@inheritDoc} */
	@Override
	public HtmlRendererContext getOpener() {
		return this.opener;
	}

	/** {@inheritDoc} */
	@Override
	public HtmlRendererContext getParent() {
		return this.parentRcontext;
	}

	/** {@inheritDoc} */
	@Override
	public HtmlRendererContext getTop() {
		final HtmlRendererContext ancestor = this.parentRcontext;
		if (ancestor == null) {
			return this;
		}
		return ancestor.getTop();
	}

	/** {@inheritDoc} */
	@Override
	public UserAgentContext getUserAgentContext() {
		synchronized (this) {
			if (this.bcontext == null) {
				this.warn("getUserAgentContext(): UserAgentContext not provided in constructor. Creating a simple one.");
				this.bcontext = new UserAgentContext(config);
			}
			return this.bcontext;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean isClosed() {
		this.warn("isClosed(): Not overridden");
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isVisitedLink(final HTMLAnchorElement link) {
		return LinkStore.isVisited(link.getHref());
	}


	/** {@inheritDoc} */
	@Override
	public void linkClicked(final URL url, final boolean isNewTab) {
		final String fullURL = url.toString();
		if (fullURL.endsWith(".pdf")) {
			final PDFViewer viewer = new PDFViewer(true);
			viewer.doOpen(fullURL);
		} else {
			final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
			final ITabbedPane tabbedPane = bpanel.getTabbedPane();
			tabbedPane.setComponentPopupMenu(bpanel);
			final int index;

			if (isNewTab) {
				index = TabStore.getTabs().size();
			} else {
				index = tabbedPane.getIndex();
			}

			final HtmlPanel hpanel = NavigatorFrame.createHtmlPanel(bpanel, fullURL);
			final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
			final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
			if (!isNewTab) {
				tabbedPane.remove(index);
			}
			tabbedPane.insertTab(title, null, hpanel, title, index);
			tabbedPane.setSelectedIndex(index);
			final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
			browserFrame.getToolbar().getAddressBar().setText(fullURL);
			TabStore.insertTab(index, fullURL, title);
			LinkStore.insertLinkVisited(fullURL);
			bpanel.getScroll().getViewport().add((Component)tabbedPane);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void moveInHistory(final int offset) {
		if (log.isWarnEnabled()) {
			log.warn("moveInHistory() does nothing, unless overridden.");
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getPreviousURL() {
		if (log.isWarnEnabled()) {
			log.warn("getPreviousURL() does nothing, unless overridden.");
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getNextURL() {
		if (log.isWarnEnabled()) {
			log.warn("getNextURL() does nothing, unless overridden.");
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public int getHistoryLength() {
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void navigate(final String fullURL) throws Exception {
		final URL href = Urls.createURL(null, fullURL);
		this.navigate(href, "_this");
	}

	/** {@inheritDoc} */
	@Override
	public void navigate(final URL href, final String target) {
		submitForm("GET", href, target, null, null);
	}

	/** {@inheritDoc} */
	@Override
	public boolean onContextMenu(HTMLElement element, final MouseEvent event) {
		final HTMLElementImpl elem = (HTMLElementImpl) element;
		final HTMLImageElementImpl elmImg = new HTMLImageElementImpl();
		final CSSStyleDeclaration style = elem.getCurrentStyle();
		if (style != null && Strings.isNotBlank(style.getBackgroundImage())) {
			final String backgroundImageText = style.getBackgroundImage();
			final String start = "url(";
			final int startIdx = start.length() + 1;
			final int closingIdx = backgroundImageText.lastIndexOf(')') - 1;
			final String quotedUri = backgroundImageText.substring(startIdx, closingIdx);
			elmImg.setSrc(quotedUri);
			element = elmImg;
		}
		final HtmlContextMenu menu = new HtmlContextMenu(element, this);
		
		if (element instanceof HTMLImageElementImpl) {
			final JPopupMenu popupMenuImage = menu.popupMenuImage(htmlPanel.getBrowserPanel());
			popupMenuImage.show(event.getComponent(), event.getX(), event.getY());
			return false;
		} else if (element instanceof HTMLAnchorElementImpl) {
			final JPopupMenu popupMenuLink = menu.popupMenuLink(htmlPanel.getBrowserPanel());
			popupMenuLink.show(event.getComponent(), event.getX(), event.getY());
			return false;
		} else if (!(element instanceof HTMLDocument)) {
			final JPopupMenu popupMenuAbstract = menu.popupMenuAbstractUI();
			popupMenuAbstract.show(event.getComponent(), event.getX(), event.getY());
			return false;
		}
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public HtmlRendererContext open(final URL url, final String windowName, final String windowFeatures, final boolean replace) {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		final ITabbedPane tabbedPane = bpanel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(bpanel);
		final int index = TabStore.getTabs().size();
		final String fullURL = url.toString();
		final HtmlPanel hpanel = NavigatorFrame.createHtmlPanel(bpanel, fullURL);
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
		tabbedPane.insertTab(title, null, hpanel, title, index);
		tabbedPane.setSelectedIndex(index);
		final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
		browserFrame.getToolbar().getAddressBar().setText(fullURL);
		TabStore.insertTab(index, fullURL, title);
		LinkStore.insertLinkVisited(fullURL);
		bpanel.getScroll().getViewport().add((Component)tabbedPane);
		return nodeImpl.getHtmlRendererContext();
	}


	/** {@inheritDoc} */
	@Override
	public void openImageViewer(final URL srcUrl) {
		try {
			final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
			final ITabbedPane tabbedPane = bpanel.getTabbedPane();
			final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
			final BufferedImage img = ImageIO.read(srcUrl);
			final ImageViewer viewer = new ImageViewer(img);
			final String title = "Image Viewer";
			final String fullURL = srcUrl.toString();
			final int index = TabStore.getTabs().size();
			final JPanel jPanel = new JPanel();
			jPanel.add(viewer.getComponent());
			browserFrame.getToolbar().getAddressBar().setText(fullURL);
			tabbedPane.insertTab(title, null, viewer.getComponent(), title, index);
			TabStore.insertTab(index, fullURL, title);
			LinkStore.insertLinkVisited(fullURL);
			bpanel.getScroll().getViewport().add((Component)tabbedPane);
		} catch (final IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void openImageViewer(final String fullURL, final InputStream stream) {
		try {
			final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
			final ITabbedPane tabbedPane = bpanel.getTabbedPane();
			final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
			final BufferedImage img = ImageIO.read(stream);
			final ImageViewer viewer = new ImageViewer(img);
			final String title = "Image Viewer";
			final int index = TabStore.getTabs().size();
			final JPanel jPanel = new JPanel();
			jPanel.add(viewer.getComponent());
			browserFrame.getToolbar().getAddressBar().setText(fullURL);
			tabbedPane.insertTab(title, null, viewer.getComponent(), title, index);
			bpanel.getScroll().getViewport().add((Component)tabbedPane);
		} catch (final IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public String prompt(final String message, final String inputDefault) {
		return JOptionPane.showInputDialog(this.htmlPanel, message);
	}

	/** {@inheritDoc} */
	@Override
	public void reload() {
		final HTMLDocumentImpl document = (HTMLDocumentImpl) this.htmlPanel.getRootNode();
		if (document != null) {
			try {
				final URL url = new URL(document.getDocumentURI());
				this.navigate(url, null);
			} catch (final MalformedURLException throwable) {
				this.warn("reload(): Malformed URL", throwable);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void resizeBy(final double byWidth, final double byHeight) {
		final Window window = getWindow(this.htmlPanel);
		if (window != null) {
			window.setSize(window.getWidth() + (int)byWidth, window.getHeight() + (int)byHeight);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void resizeTo(final double width, final double height) {
		final Window window = getWindow(this.htmlPanel);
		if (window != null) {
			window.setSize((int)width, (int)height);
		}
	}

	/** {@inheritDoc} */
	@Override
	public int getInnerHeight() {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		if (bpanel != null && bpanel.getHeight() > 0) {
			return bpanel.getHeight();
		}
		final Rectangle initialWindowBounds = GeneralStore.getInitialWindowBounds();
		return Double.valueOf(initialWindowBounds.getHeight()).intValue();
	}

	/** {@inheritDoc} */
	@Override
	public int getInnerWidth() {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		if (bpanel != null && bpanel.getWidth() > 0) {
			return bpanel.getWidth();
		}
		final Rectangle initialWindowBounds = GeneralStore.getInitialWindowBounds();
		return Double.valueOf(initialWindowBounds.getWidth()).intValue();
	}

	/** {@inheritDoc} */
	@Override
	public int getOuterHeight() {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		if (bpanel != null && bpanel.getHeight() > 0) {
			return bpanel.getHeight();
		}
		final Rectangle initialWindowBounds = GeneralStore.getInitialWindowBounds();
		return Double.valueOf(initialWindowBounds.getHeight()).intValue();
	}

	/** {@inheritDoc} */
	@Override
	public int getOuterWidth() {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		if (bpanel != null && bpanel.getWidth() > 0) {
			return bpanel.getWidth();
		}
		final Rectangle initialWindowBounds = GeneralStore.getInitialWindowBounds();
		return Double.valueOf(initialWindowBounds.getWidth()).intValue();
	}


	/** {@inheritDoc} */
	@Override
	public void scroll(final double x, final double y) {
		this.htmlPanel.scroll(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public void scrollBy(final double x, final double y) {
		this.htmlPanel.scrollBy(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public double getScrollx() {
		return scrollx;
	}

	/** {@inheritDoc} */
	@Override
	public void setScrollx(final double scrollx) {
		this.scrollx = scrollx;
	}

	/** {@inheritDoc} */
	@Override
	public double getScrolly() {
		return scrolly;
	}

	/** {@inheritDoc} */
	@Override
	public void setScrolly(final double scrolly) {
		this.scrolly = scrolly;
	}

	/** {@inheritDoc} */
	@Override
	public void setDefaultStatus(final String message) {
		this.warn("setDefaultStatus(): Not overridden.");
	}

	/** {@inheritDoc} */
	@Override
	public void setHtmlPanel(final HtmlPanel panel) {
		this.htmlPanel = panel;
	}

	/** {@inheritDoc} */
	@Override
	public void setOpener(final HtmlRendererContext opener) {
		this.opener = opener;
	}

	/** {@inheritDoc} */
	@Override
	public void setStatus(final String message) {
		this.warn("setStatus(): Not overridden");
	}

	/** {@inheritDoc} */
	@Override
    public void setCursor(final Optional<Cursor> cursorOpt) {
        final Cursor cursor = cursorOpt.orElse(Cursor.getDefaultCursor());
        htmlPanel.setCursor(cursor);
    }

	/** {@inheritDoc} */
	@Override
	public void submitForm(final String method, final URL action, final String target, final String enctype, final FormInput[] formInputs) {
		if (target != null) {
			final String actualTarget = target.trim().toLowerCase();
			switch (actualTarget) {
				case "_top":
					getTop().navigate(action, null);
					break;
				case "_parent":
					final HtmlRendererContext parent = getParent();
					if (parent != null) {
						parent.navigate(action, null);
						return;
					}
					break;
				case "_blank":
					open(action, "cobra.blank", "", false);
					break;
				default:
					break;
			}
		}

		try {
			final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
			final ITabbedPane tabbedPane = bpanel.getTabbedPane();
			final int indexPanel = tabbedPane.getSelectedIndex();
			final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
			final HtmlPanel hpanel = NavigatorFrame.createHtmlPanel(bpanel, action.toString());
			final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
			final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
			tabbedPane.remove(indexPanel);
			tabbedPane.insertTab(title, null, htmlPanel, title, indexPanel);
			browserFrame.getToolbar().getAddressBar().setText(action.toString());
			bpanel.getScroll().getViewport().add((Component)tabbedPane);
			submitFormSync(method, action, target, enctype, formInputs);
		} catch (final Exception err) {
			error("navigate(): Error loading or parsing request.", err);
		}

	}

	/** {@inheritDoc} */
	@Override
	public void warn(final String message) {
		if (log.isWarnEnabled()) {
			log.warn(message);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void warn(final String message, final Throwable throwable) {
		if (log.isWarnEnabled()) {
			log.warn(message, throwable);
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getStatus() {
		this.warn("getStatus(): Not overridden");
		return "";
	}

	private static Window getWindow(final Component c) {
		Component current = c;
		while (current != null && !(current instanceof Window)) {
			current = current.getParent();
		}
		return (Window) current;
	}

	private HTMLDocumentImpl createDocument(final InputSource inputSource) throws Exception {
		final HtmlRendererConfig config = new HtmlRendererConfigImpl();
		final DocumentBuilderImpl builder = new DocumentBuilderImpl(getUserAgentContext(), this, config);
		return (HTMLDocumentImpl) builder.createDocument(inputSource);
	}

	private Charset getDocumentCharset(final URLConnection connection) {
		final String encoding = Urls.getCharset(connection);
		return encoding == null ? StandardCharsets.UTF_8 : Charset.forName(encoding);
	}

	private Proxy getProxy() {
		return Proxy.NO_PROXY;
	}

	private void submitFormSync(final String method, final java.net.URL action, final String target, final String enctype,
								final FormInput[] formInputs) throws Exception {
		final String actualMethod = method.toUpperCase();
		final URL resolvedURL;
		if ("GET".equals(actualMethod) && formInputs != null) {
			boolean firstParam = true;
			final URL noRefAction = new URL(action.getProtocol(), action.getHost(), action.getPort(), action.getFile());
			final StringBuilder newUrlBuffer = new StringBuilder(noRefAction.toExternalForm());
			if (action.getQuery() == null) {
				newUrlBuffer.append("?");
			} else {
				newUrlBuffer.append("&");
			}
			for (final FormInput parameter : formInputs) {
				final String name = parameter.getName();
				final String encName = URLEncoder.encode(name, "UTF-8");
				if (parameter.isText()) {
					if (firstParam) {
						firstParam = false;
					} else {
						newUrlBuffer.append("&");
					}
					final String valueStr = parameter.getTextValue();
					final String encValue = URLEncoder.encode(valueStr, "UTF-8");
					newUrlBuffer.append(encName);
					newUrlBuffer.append("=");
					newUrlBuffer.append(encValue);
				} else {
					log.warn("postData(): Ignoring non-textual parameter for GET {} ", name);
				}
			}
			resolvedURL = new java.net.URL(newUrlBuffer.toString());
		} else {
			resolvedURL = action;
		}
		URL urlForLoading;
		if (resolvedURL.getProtocol().equalsIgnoreCase("file")) {
			// Remove query so it works.
			try {
				final String ref = action.getRef();
				final String refText = Strings.isCssBlank(ref) ? "" : "#" + ref;
				urlForLoading = new URL(resolvedURL.getProtocol(), action.getHost(), action.getPort(),
						action.getPath() + refText);
			} catch (final java.net.MalformedURLException throwable) {
				this.warn("malformed", throwable);
				urlForLoading = action;
			}
		} else {
			urlForLoading = resolvedURL;
		}

		System.currentTimeMillis();
		// Using potentially different URL for loading.
		final Proxy proxy = getProxy();
		final boolean isPost = "POST".equals(actualMethod);
		final URLConnection connection = proxy == null || proxy == Proxy.NO_PROXY ? urlForLoading.openConnection()
				: urlForLoading.openConnection(proxy);
		this.currentConnection = connection;
		try {
			connection.setRequestProperty("User-Agent", UserAgent.getUserAgent());
			connection.getHeaderField("Set-Cookie");
			if (connection instanceof HttpURLConnection) {
				final HttpURLConnection hc = (HttpURLConnection) connection;
				hc.setRequestMethod(actualMethod);
				hc.setInstanceFollowRedirects(false);
			}
			if (isPost) {
				connection.setDoOutput(true);
				final ByteArrayOutputStream bufOut = new ByteArrayOutputStream();
				boolean firstParam = true;
				if (formInputs != null) {
					for (final FormInput parameter : formInputs) {
						final String name = parameter.getName();
						final String encName = URLEncoder.encode(name, "UTF-8");
						if (parameter.isText()) {
							if (firstParam) {
								firstParam = false;
							} else {
								bufOut.write((byte) '&');
							}
							final String valueStr = parameter.getTextValue();
							final String encValue = URLEncoder.encode(valueStr, "UTF-8");
							bufOut.write(encName.getBytes(StandardCharsets.UTF_8));
							bufOut.write((byte) '=');
							bufOut.write(encValue.getBytes(StandardCharsets.UTF_8));
						} else {
							log.warn("postData(): Ignoring non-textual parameter for POST {} ", name);
						}
					}
				}
				// Do not add a line break to post content. Some servers
				// can be picky about that (namely, java.net).
				final byte[] postContent = bufOut.toByteArray();
				if (connection instanceof HttpURLConnection) {
					((HttpURLConnection) connection).setFixedLengthStreamingMode(postContent.length);
				}
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				// connection.setRequestProperty("Content-Length",
				// String.valueOf(postContent.length));
				final OutputStream postOut = connection.getOutputStream();
				postOut.write(postContent);
				postOut.flush();
			}
			if (connection instanceof HttpURLConnection) {
				final HttpURLConnection hc = (HttpURLConnection) connection;
				final int responseCode = hc.getResponseCode();
				log.info("process(): HTTP response code {} ", responseCode);

				if (responseCode == HttpURLConnection.HTTP_MOVED_PERM
						|| responseCode == HttpURLConnection.HTTP_MOVED_TEMP
						|| responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
					final String location = hc.getHeaderField("Location");
					if (location == null) {
						log.warn("No Location header in redirect from {} ", action);
					} else {
						final URL href;
						href = Urls.createURL(action, location);
						navigate(href, target);
					}
					return;
				}
			}
			try (final InputStream in = HttpNetwork.openConnectionCheckRedirects(connection)) {
				final RecordedInputStream rin = new RecordedInputStream(in, 1000000);
				final InputStream bin = new BufferedInputStream(rin, 8192);
				final String actualURI = urlForLoading.toExternalForm();
				// Only create document, don't parse.
				final HTMLDocumentImpl document = createDocument(new InputSourceImpl(bin, actualURI, getDocumentCharset(connection)));
				// Set document in HtmlPanel. Safe to call outside GUI thread.
				final HtmlPanel panel = this.htmlPanel;
				panel.setDocument(document, HtmlRendererContextImpl.this);
				final String ref = urlForLoading.getRef();
				if (Strings.isNotBlank(ref)) {
					panel.scrollToElement(ref);
				}
			} catch (final SocketTimeoutException e) {
				log.error("More time elapsed {}", connection.getConnectTimeout());
			}
		} finally {
			this.currentConnection = null;
		}
	}

	/**
	 * <p>isTest.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isTestEnabled() {
		return false;
	}
}
