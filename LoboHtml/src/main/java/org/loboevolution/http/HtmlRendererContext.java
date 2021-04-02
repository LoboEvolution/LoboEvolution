/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
/*
 * Created on Oct 22, 2005
 */
package org.loboevolution.http;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.loboevolution.common.BufferExceededException;
import org.loboevolution.common.RecordedInputStream;
import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;
import org.loboevolution.component.IBrowserFrame;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.component.IToolBar;
import org.loboevolution.html.HtmlObject;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLImageElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLLinkElementImpl;
import org.loboevolution.html.dom.input.FormInput;
import org.loboevolution.html.gui.HtmlContextMenu;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.html.parser.DocumentBuilderImpl;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.img.ImageViewer;
import org.loboevolution.info.BookmarkInfo;
import org.loboevolution.net.HttpNetwork;
import org.loboevolution.pdf.PDFViewer;
import org.loboevolution.store.LinkStore;
import org.loboevolution.store.NavigationStore;
import org.loboevolution.store.TabStore;
import org.loboevolution.tab.DnDTabbedPane;
import org.loboevolution.tab.TabbedPanePopupMenu;
import org.xml.sax.InputSource;

/**
 * The HtmlRendererContext class implements the
 * {@link org.loboevolution.http.HtmlRendererContext} interface. Note that this
 * class provides rudimentary implementations of most callback methods.
 * Overridding some of the methods in this class will usually be necessary in a
 * professional application.
 * <p>
 * A simple way to load a URL into the {@link org.loboevolution.html.gui.HtmlPanel} of the renderer context
 * is to invoke {@link #navigate(String)}.
 *
 * @author utente
 * @version $Id: $Id
 */
public class HtmlRendererContext {
	private static final Logger logger = Logger.getLogger(HtmlRendererContext.class.getName());

	private static java.awt.Window getWindow(Component c) {
		Component current = c;
		while (current != null && !(current instanceof java.awt.Window)) {
			current = current.getParent();
		}
		return (java.awt.Window) current;
	}

	private UserAgentContext bcontext = null;

	protected URLConnection currentConnection;

	private HtmlPanel htmlPanel;

	private volatile HtmlRendererContext opener;

	private final HtmlRendererContext parentRcontext;

	private volatile String sourceCode;

	private boolean test = false;

	/**
	 * Constructs a HtmlRendererContext that is a child of another
	 * {@link org.loboevolution.http.HtmlRendererContext}.
	 *
	 * @param parentRcontext   The parent's renderer context.
	 * @param htmlPanel a {@link org.loboevolution.html.gui.HtmlPanel} object.
	 */
	public HtmlRendererContext(HtmlPanel htmlPanel, HtmlRendererContext parentRcontext) {
		this.htmlPanel = htmlPanel;
		this.parentRcontext = parentRcontext;
		this.bcontext = parentRcontext == null ? null : parentRcontext.getUserAgentContext();
	}

	/**
	 * Constructs a HtmlRendererContext.
	 *
	 * @param htmlPanel a {@link org.loboevolution.html.gui.HtmlPanel} object.
	 * @param ucontext a {@link org.loboevolution.http.UserAgentContext} object.
	 */
	public HtmlRendererContext(HtmlPanel htmlPanel, UserAgentContext ucontext) {
		this.htmlPanel = htmlPanel;
		this.parentRcontext = null;
		this.bcontext = ucontext;
	}

	/**
	 * Opens a simple message dialog.
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public void alert(String message) {
		JOptionPane.showMessageDialog(this.htmlPanel, message);
	}

	/**
	 * It should navigate back one page. This implementation does nothing and should
	 * be overridden.
	 */
	public void back() {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		final DnDTabbedPane tabbedPane = bpanel.getTabbedPane();
		final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
		final IToolBar toolbar = browserFrame.getToolbar();
		final JTextField addressBar = toolbar.getAddressBar();
		String url = addressBar.getText();
		tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(bpanel));
		NavigationStore nh = new NavigationStore();
        final int indexPanel = tabbedPane.getSelectedIndex();
        List<BookmarkInfo> tabsById = nh.getRecentHost(indexPanel, true);
		for (int i = 0; i < tabsById.size(); i++) {
			BookmarkInfo info = tabsById.get(i);
			String tab = info.getUrl();
			if(tab.equals(url) && i > 0) {
				url = tabsById.get(i - 1).getUrl();
			}
		}

        final HtmlPanel hpanel = HtmlPanel.createHtmlPanel(bpanel, url);
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
        tabbedPane.remove(indexPanel);
        tabbedPane.insertTab(title, null, htmlPanel, title, indexPanel);
		
        browserFrame.getToolbar().getAddressBar().setText(url);
        bpanel.getScroll().getViewport().add(tabbedPane);
        
        TabStore.deleteTab(indexPanel);
        TabStore.insertTab(indexPanel, url, title);
	}

	/**
	 * It should give up focus on the current browser window. This implementation
	 * does nothing and should be overridden.
	 */
	public void blur() {
		this.warn("back(): Not overridden");
	}

	/**
	 * It should close the current browser window. This implementation does nothing
	 * and should be overridden.
	 */
	public void close() {
		this.warn("close(): Not overridden");
	}

	/**
	 * Opens a simple confirmation window.
	 *
	 * @param message a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public boolean confirm(String message) {
		final int retValue = JOptionPane.showConfirmDialog(this.htmlPanel, message, "Confirm",
				JOptionPane.YES_NO_OPTION);
		return retValue == JOptionPane.YES_OPTION;
	}

	/**
	 * Creates a blank document instance. This method is invoked whenever navigation
	 * or form submission occur. It is provided so it can be overridden to create
	 * specialized document implmentations.
	 *
	 * @param inputSource The document input source.
	 * @throws java.lang.Exception if any
	 * @return a {@link org.loboevolution.html.dom.domimpl.HTMLDocumentImpl} object.
	 */
	protected HTMLDocumentImpl createDocument(InputSource inputSource) throws Exception {
		final DocumentBuilderImpl builder = new DocumentBuilderImpl(getUserAgentContext(), this);
		return (HTMLDocumentImpl) builder.createDocument(inputSource);
	}

	/**
	 * <p>error.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public void error(String message) {
		if (logger.isLoggable(Level.SEVERE)) {
			logger.log(Level.SEVERE, message);
		}
	}

	/**
	 * <p>error.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param throwable a {@link java.lang.Throwable} object.
	 */
	public void error(String message, Throwable throwable) {
		if (logger.isLoggable(Level.SEVERE)) {
			logger.log(Level.SEVERE, message, throwable);
		}
	}

	/**
	 * It should request focus for the current browser window. This implementation
	 * does nothing and should be overridden.
	 */
	public void focus() {
		this.warn("focus(): Not overridden");
	}

	/**
	 * <p>forward.</p>
	 */
	public void forward() {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		final DnDTabbedPane tabbedPane = bpanel.getTabbedPane();
		final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
		final IToolBar toolbar = browserFrame.getToolbar();
		final JTextField addressBar = toolbar.getAddressBar();
		String url = addressBar.getText();
		tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(bpanel));
		NavigationStore nh = new NavigationStore();
        final int indexPanel = tabbedPane.getSelectedIndex();
        List<BookmarkInfo> tabsById = nh.getRecentHost(indexPanel, true);
		for (int i = 0; i < tabsById.size(); i++) {
			BookmarkInfo info = tabsById.get(i);
			String tab = info.getUrl();
			if (tab.equals(url) && i < tabsById.size() - 1) {
				url = tabsById.get(i + 1).getUrl();
			}
		}

        final HtmlPanel hpanel = HtmlPanel.createHtmlPanel(bpanel, url);
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
        tabbedPane.remove(indexPanel);
        tabbedPane.insertTab(title, null, htmlPanel, title, indexPanel);
		
        browserFrame.getToolbar().getAddressBar().setText(url);
        bpanel.getScroll().getViewport().add(tabbedPane);
        
        TabStore.deleteTab(indexPanel);
        TabStore.insertTab(indexPanel, url, title);
	}

	/**
	 * <p>getCurrentURL.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getCurrentURL() {
		HtmlPanel html = htmlPanel;
		IBrowserPanel panel = html.getBrowserPanel();
		IBrowserFrame frame = panel.getBrowserFrame();
		IToolBar toolbar = frame.getToolbar();
		JTextField jtf = toolbar.getAddressBar();
		return jtf.getText();
	}

	/**
	 * Should return true if and only if the current browser window is closed. This
	 * implementation returns false and should be overridden.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getDefaultStatus() {
		this.warn("getDefaultStatus(): Not overridden");
		return "";
	}

	/**
	 * This method is invoked by
	 * {@link #submitForm(String, URL, String, String, FormInput[])} to determine
	 * the charset of a document. The charset is determined by looking at the
	 * Content-Type header.
	 *
	 * @param connection A URL connection.
	 * @return a {@link java.lang.String} object.
	 */
	protected String getDocumentCharset(URLConnection connection) {
		final String encoding = Urls.getCharset(connection);
		return encoding == null ? "ISO-8859-1" : encoding;
	}


	/**
	 * <p>getHistoryLength.</p>
	 *
	 * @return a int.
	 */
	public int getHistoryLength() {
		return 0;
	}

	/**
	 * <p>getHtmlObject.</p>
	 *
	 * @param markupElement a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @return a {@link org.loboevolution.html.HtmlObject} object.
	 */
	public HtmlObject getHtmlObject(HTMLElementImpl markupElement) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <p>Getter for the field htmlPanel.</p>
	 *
	 * @return a {@link org.loboevolution.html.gui.HtmlPanel} object.
	 */
	public HtmlPanel getHtmlPanel() {
		return this.htmlPanel;
	}

	/**
	 * It should return the name of the browser window, if this renderer context is
	 * for the top frame in the window. This implementation returns a blank string,
	 * so it should be overridden.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		this.warn("getName(): Not overridden");
		return "";
	}

	/**
	 * <p>getNextURL.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getNextURL() {
		return null;
	}

	/**
	 * <p>Getter for the field opener.</p>
	 *
	 * @return a {@link org.loboevolution.http.HtmlRendererContext} object.
	 */
	public HtmlRendererContext getOpener() {
		return this.opener;
	}

	/**
	 * <p>getParent.</p>
	 *
	 * @return a {@link org.loboevolution.http.HtmlRendererContext} object.
	 */
	public HtmlRendererContext getParent() {
		return this.parentRcontext;
	}

	/**
	 * <p>getPreviousURL.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPreviousURL() {
		return null;
	}

	/**
	 * Gets the connection proxy used in {@link #navigate(URL, String)}. If
	 * {@link #getUserAgentContext()} returns an instance assignable to
	 * {@link UserAgentContext}. The method may be overridden to provide a
	 * different proxy setting.
	 *
	 * @return a {@link java.net.Proxy} object.
	 */
	protected Proxy getProxy() {
		return Proxy.NO_PROXY;
	}

	/**
	 * Gets the source code of the current HTML document.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSourceCode() {
		return this.sourceCode;
	}

	/**
	 * <p>getStatus.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getStatus() {
		this.warn("getStatus(): Not overridden");
		return "";
	}

	/**
	 * <p>getTop.</p>
	 *
	 * @return a {@link org.loboevolution.http.HtmlRendererContext} object.
	 */
	public HtmlRendererContext getTop() {
		final HtmlRendererContext ancestor = this.parentRcontext;
		if (ancestor == null) {
			return this;
		}
		return ancestor.getTop();
	}

	/**
	 * If a {@link org.loboevolution.http.UserAgentContext} instance was provided in
	 * the constructor, then that instance is returned. Otherwise, an instance of
	 * {@link UserAgentContext} is created and returned.
	 * <p>
	 * The context returned by this method is used by local request facilities and
	 * other parts of the renderer.
	 *
	 * @return a {@link org.loboevolution.http.UserAgentContext} object.
	 */
	public UserAgentContext getUserAgentContext() {
		synchronized (this) {
			if (this.bcontext == null) {
				this.warn("getUserAgentContext(): UserAgentContext not provided in constructor. Creating a simple one.");
				this.bcontext = new UserAgentContext();
			}
			return this.bcontext;
		}
	}

	/**
	 * <p>goToHistoryURL.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 */
	public void goToHistoryURL(String url) {
		if (logger.isLoggable(Level.WARNING)) {
			logger.log(Level.WARNING, "goToHistoryURL() does nothing, unless overridden.");
		}
	}

	/**
	 * Should return true if and only if the current browser window is closed. This
	 * implementation returns false and should be overridden.
	 *
	 * @return a boolean.
	 */
	public boolean isClosed() {
		this.warn("isClosed(): Not overridden");
		return false;
	}

	/**
	 * <p>isImageLoadingEnabled.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isImageLoadingEnabled() {
		return true;
	}

	/**
	 * <p>isVisitedLink.</p>
	 *
	 * @param link a {@link org.loboevolution.html.dom.domimpl.HTMLLinkElementImpl} object.
	 * @return a boolean.
	 */
	public boolean isVisitedLink(HTMLLinkElementImpl link) {
		return LinkStore.isVisited(link.getHref());
	}

	/**
	 * Implements the link click handler by invoking {@link #navigate(URL, String)}.
	 *
	 * @param url a {@link java.net.URL} object.
	 * @param isNewTab a boolean.
	 */
	public void linkClicked(URL url, boolean isNewTab) {
		String fullURL = url.toString();
		if (fullURL.endsWith(".pdf")) {
			PDFViewer viewer = new PDFViewer(true);
			viewer.doOpen(fullURL);
		} else {
			final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
			final DnDTabbedPane tabbedPane = bpanel.getTabbedPane();
			tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(bpanel));
			int index = -1;

			if (isNewTab) {
				index = TabStore.getTabs().size();
			} else {
				index = tabbedPane.getIndex();
			}

			final HtmlPanel hpanel = HtmlPanel.createHtmlPanel(bpanel, fullURL);
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
			bpanel.getScroll().getViewport().add(tabbedPane);
		}
	}

	/**
	 * <p>moveInHistory.</p>
	 *
	 * @param offset a int.
	 */
	public void moveInHistory(int offset) {
		if (logger.isLoggable(Level.WARNING)) {
			logger.log(Level.WARNING, "moveInHistory() does nothing, unless overridden.");
		}
	}

	/**
	 * Convenience method provided to allow loading a document into the renderer.
	 *
	 * @param fullURL The absolute URL of the document.
	 * @throws java.lang.Exception if any
	 * @see #navigate(URL, String)
	 */
	public void navigate(String fullURL) throws Exception {
		final URL href = Urls.createURL(null, fullURL);
		this.navigate(href, "_this");
	}

	/**
	 * Implements simple navigation with incremental rendering by invoking
	 * {@link #submitForm(String, URL, String, String, FormInput[])} with a
	 * GET request method.
	 *
	 * @param href a {@link java.net.URL} object.
	 * @param target a {@link java.lang.String} object.
	 */
	public void navigate(final URL href, String target) {
		submitForm("GET", href, target, null, null);
	}
	
	/**
	 * This method must be overridden to implement a context menu.
	 *
	 * @param element a {@link org.loboevolution.html.dom.HTMLElement} object.
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @return a boolean.
	 */
	public boolean onContextMenu(HTMLElement element, MouseEvent event) {
		HTMLElementImpl elem = (HTMLElementImpl) element;
		HTMLImageElementImpl elmImg = new HTMLImageElementImpl();
		if (elem.getCurrentStyle() != null && Strings.isNotBlank(elem.getCurrentStyle().getBackgroundImage())) {
			final String backgroundImageText = elem.getCurrentStyle().getBackgroundImage();
			String start = "url(";
			int startIdx = start.length() + 1;
			int closingIdx = backgroundImageText.lastIndexOf(')') - 1;
			String quotedUri = backgroundImageText.substring(startIdx, closingIdx);
			elmImg.setSrc(quotedUri);
			element = elmImg;
		}
		HtmlContextMenu menu = new HtmlContextMenu(element, this);
		
		if (element instanceof HTMLImageElementImpl) {
			JPopupMenu popupMenuImage = menu.popupMenuImage(htmlPanel.getBrowserPanel());
			popupMenuImage.show(event.getComponent(), event.getX(), event.getY());
			return false;
		} else if (element instanceof HTMLLinkElementImpl) {
			JPopupMenu popupMenuLink = menu.popupMenuLink(htmlPanel.getBrowserPanel());
			popupMenuLink.show(event.getComponent(), event.getX(), event.getY());
			return false;
		} else if (element instanceof HTMLElementImpl) {
			JPopupMenu popupMenuAbstract = menu.popupMenuAbstractUI();
			popupMenuAbstract.show(event.getComponent(), event.getX(), event.getY());
			return false;
		}
		return true;
	}
	/**
	 * <p>onDoubleClick.</p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.HTMLElement} object.
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @return a boolean.
	 */
	public boolean onDoubleClick(HTMLElement element, MouseEvent event) {
		return true;
	}

	/**
	 * <p>onMouseClick.</p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.HTMLElement} object.
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @return a boolean.
	 */
	public boolean onMouseClick(HTMLElement element, MouseEvent event) {
		return true;
	}

	/**
	 * This method can be overridden to receive notifications when the mouse leaves
	 * an element.
	 *
	 * @param element a {@link org.loboevolution.html.dom.HTMLElement} object.
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 */
	public void onMouseOut(HTMLElement element, MouseEvent event) {
	}

	/**
	 * This method can be overridden to receive notifications when the mouse first
	 * enters an element.
	 *
	 * @param element a {@link org.loboevolution.html.dom.HTMLElement} object.
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 */
	public void onMouseOver(HTMLElement element, MouseEvent event) {
	}

	/**
	 * It should open a new browser window. This implementation does nothing and
	 * should be overridden.
	 *
	 * @param url            The requested URL.
	 * @param windowName     A window identifier.
	 * @param windowFeatures WindowImpl features specified in a format equivalent to
	 *                       that of window.open() in Javascript.
	 * @param replace        Whether an existing window with the same name should be
	 *                       replaced.
	 * @return a {@link org.loboevolution.http.HtmlRendererContext} object.
	 */
	public HtmlRendererContext open(URL url, String windowName, String windowFeatures, boolean replace) {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		final DnDTabbedPane tabbedPane = bpanel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(bpanel));
		int index = TabStore.getTabs().size();
		String fullURL = url.toString();
		final HtmlPanel hpanel = HtmlPanel.createHtmlPanel(bpanel, fullURL);
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
		tabbedPane.insertTab(title, null, hpanel, title, index);
		tabbedPane.setSelectedIndex(index);
		final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
		browserFrame.getToolbar().getAddressBar().setText(fullURL);
		TabStore.insertTab(index, fullURL, title);
		LinkStore.insertLinkVisited(fullURL);
		bpanel.getScroll().getViewport().add(tabbedPane);
		return nodeImpl.getHtmlRendererContext();
	}
	

	/**
	 * <p>openImageViewer.</p>
	 *
	 * @param srcUrl a {@link java.net.URL} object.
	 */
	public void openImageViewer(URL srcUrl) {
		try {
			final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
			final DnDTabbedPane tabbedPane = bpanel.getTabbedPane();
			final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
			final BufferedImage img = ImageIO.read(srcUrl);
			final ImageViewer viewer = new ImageViewer(img);
			final String title = "Image Viewer";
			String fullURL = srcUrl.toString();
			int index = TabStore.getTabs().size();
			JPanel jPanel = new JPanel();
			jPanel.add(viewer.getComponent());
			browserFrame.getToolbar().getAddressBar().setText(fullURL);
			tabbedPane.insertTab(title, null, viewer.getComponent(), title, index);
			TabStore.insertTab(index, fullURL, title);
			LinkStore.insertLinkVisited(fullURL);
			bpanel.getScroll().getViewport().add(tabbedPane);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	/**
	 * <p>openImageViewer.</p>
	 *
	 * @param fullURL a {@link java.lang.String} object.
	 * @param stream a {@link java.io.InputStream} object.
	 */
	public void openImageViewer(String fullURL, InputStream stream) {
		try {
			final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
			final DnDTabbedPane tabbedPane = bpanel.getTabbedPane();
			final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
			final BufferedImage img = ImageIO.read(stream);
			final ImageViewer viewer = new ImageViewer(img);
			final String title = "Image Viewer";
			int index = TabStore.getTabs().size();
			JPanel jPanel = new JPanel();
			jPanel.add(viewer.getComponent());
			browserFrame.getToolbar().getAddressBar().setText(fullURL);
			tabbedPane.insertTab(title, null, viewer.getComponent(), title, index);
			bpanel.getScroll().getViewport().add(tabbedPane);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * Shows a simple prompt dialog.
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param inputDefault a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public String prompt(String message, String inputDefault) {
		return JOptionPane.showInputDialog(this.htmlPanel, message);
	}

	/**
	 * Implements reload as navigation to current URL. Override to implement a more
	 * robust reloading mechanism.
	 */
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

	/**
	 * <p>resizeBy.</p>
	 *
	 * @param byWidth a int.
	 * @param byHeight a int.
	 */
	public void resizeBy(int byWidth, int byHeight) {
		final Window window = getWindow(this.htmlPanel);
		if (window != null) {
			window.setSize(window.getWidth() + byWidth, window.getHeight() + byHeight);
		}
	}

	/**
	 * <p>resizeTo.</p>
	 *
	 * @param width a int.
	 * @param height a int.
	 */
	public void resizeTo(int width, int height) {
		final Window window = getWindow(this.htmlPanel);
		if (window != null) {
			window.setSize(width, height);
		}
	}
	
	/**
	 * <p> getInnerHeight.</p>
	 */
	public int getInnerHeight() {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		if (bpanel != null) {
			return bpanel.getHeight();
		}
		return -1;
	}
	
	/**
	 * <p> getInnerWidth.</p>
	 */
	public int getInnerWidth() {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		if (bpanel != null) {
			return bpanel.getWidth();
		}
		return -1;
	}
	
	/**
	 * <p> getOuterHeight.</p>
	 */
	public int getOuterHeight() {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		if (bpanel != null) {
			return bpanel.getHeight();
		}
		return -1;
	}
	
	/**
	 * <p> getOuterWidth.</p>
	 */
	public int getOuterWidth() {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		if (bpanel != null) {
			return bpanel.getWidth();
		}
		return -1;
	}


	/**
	 * Changes the origin of the HTML block's scrollable area according to the
	 * position given.
	 * <p>
	 * This method may be called outside of the GUI thread. The operation is
	 * scheduled immediately in that thread as needed.
	 *
	 * @param x The new x coordinate for the origin.
	 * @param y The new y coordinate for the origin.
	 */
	public void scroll(int x, int y) {
		this.htmlPanel.scroll(x, y);
	}

	/**
	 * <p>scrollBy.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 */
	public void scrollBy(int x, int y) {
		this.htmlPanel.scrollBy(x, y);
	}

	/**
	 * <p>setDefaultStatus.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public void setDefaultStatus(String message) {
		this.warn("setDefaultStatus(): Not overridden.");
	}

	/**
	 * <p>Setter for the field htmlPanel.</p>
	 *
	 * @param panel a {@link org.loboevolution.html.gui.HtmlPanel} object.
	 */
	public void setHtmlPanel(HtmlPanel panel) {
		this.htmlPanel = panel;
	}

	/**
	 * <p>Setter for the field opener.</p>
	 *
	 * @param opener a {@link org.loboevolution.http.HtmlRendererContext} object.
	 */
	public void setOpener(HtmlRendererContext opener) {
		this.opener = opener;
	}

	/**
	 * <p>setStatus.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public void setStatus(String message) {
		this.warn("setStatus(): Not overridden");
	}

    /**
     * <p>setCursor.</p>
     *
     * @param cursorOpt a {@link java.util.Optional} object.
     */
    public void setCursor(Optional<Cursor> cursorOpt) {
        Cursor cursor = cursorOpt.orElse(Cursor.getDefaultCursor());
        htmlPanel.setCursor(cursor);
    }

	/**
	 * Implements simple navigation and form submission with incremental rendering
	 * and target processing, including frame lookup. Should be overridden to allow
	 * for more robust browser navigation and form submission.
	 * <p>
	 * <b>Notes:</b>
	 * <ul>
	 * <li>Document encoding is defined by
	 * {@link #getDocumentCharset(URLConnection)}.
	 * <li>Caching is not implemented.
	 * <li>Cookies are not implemented.
	 * <li>Incremental rendering is not optimized for ignorable document change
	 * notifications.
	 * <li>Other HTTP features are not implemented.
	 * <li>The only form encoding type supported is
	 * application/x-www-form-urlencoded.
	 * <li>Navigation is normally asynchronous.
	 * </ul>
	 *
	 * @see #navigate(URL, String
	 *
	 * )
	 * @param method a {@link java.lang.String} object.
	 * @param action a {@link java.net.URL} object.
	 * @param target a {@link java.lang.String} object.
	 * @param enctype a {@link java.lang.String} object.
	 * @param formInputs an array of {@link org.loboevolution.html.dom.input.FormInput} objects.
	 */
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
			final DnDTabbedPane tabbedPane = bpanel.getTabbedPane();
			final int indexPanel = tabbedPane.getSelectedIndex();
			final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
			final HtmlPanel hpanel = HtmlPanel.createHtmlPanel(bpanel, action.toString());
			final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
			final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
			tabbedPane.remove(indexPanel);
			tabbedPane.insertTab(title, null, htmlPanel, title, indexPanel);
			browserFrame.getToolbar().getAddressBar().setText(action.toString());
			bpanel.getScroll().getViewport().add(tabbedPane);
			submitFormSync(method, action, target, enctype, formInputs);
		} catch (final Exception err) {
			error("navigate(): Error loading or parsing request.", err);
		}

	}

	/**
	 * Submits a form and/or navigates by making a <i>synchronous</i> request. This
	 * method is invoked by
	 * {@link #submitForm(String, URL, String, String, FormInput[])}.
	 *
	 * @param method     The request method.
	 * @param action     The action URL.
	 * @param target     The target identifier.
	 * @param enctype    The encoding type.
	 * @param formInputs The form inputs.
	 * @see #submitForm(String, URL, String, String, FormInput[])
	 * @throws java.lang.Exception if any.
	 */
	protected void submitFormSync(final String method, final java.net.URL action, final String target, String enctype,
			final FormInput[] formInputs) throws Exception {
		final String actualMethod = method.toUpperCase();
		URL resolvedURL;
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
					logger.warning("postData(): Ignoring non-textual parameter " + name + " for GET.");
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
				final String refText = ref == null || ref.length() == 0 ? "" : "#" + ref;
				urlForLoading = new URL(resolvedURL.getProtocol(), action.getHost(), action.getPort(),
						action.getPath() + refText);
			} catch (final java.net.MalformedURLException throwable) {
				this.warn("malformed", throwable);
				urlForLoading = action;
			}
		} else {
			urlForLoading = resolvedURL;
		}
		if (logger.isLoggable(Level.INFO)) {
			logger.info("process(): Loading URI=[" + urlForLoading + "].");
		}
		System.currentTimeMillis();
		// Using potentially different URL for loading.
		final Proxy proxy = getProxy();
		final boolean isPost = "POST".equals(actualMethod);
		final URLConnection connection = proxy == null || proxy == Proxy.NO_PROXY ? urlForLoading.openConnection()
				: urlForLoading.openConnection(proxy);
		this.currentConnection = connection;
		try {
			connection.setRequestProperty("User-Agent", HttpNetwork.getUserAgentValue());
			connection.setRequestProperty("Cookie", "");
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
							bufOut.write(encName.getBytes("UTF-8"));
							bufOut.write((byte) '=');
							bufOut.write(encValue.getBytes("UTF-8"));
						} else {
							logger.warning("postData(): Ignoring non-textual parameter " + name + " for POST.");
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
				if (logger.isLoggable(Level.INFO)) {
					logger.info("process(): HTTP response code: " + responseCode);
				}
				if (responseCode == HttpURLConnection.HTTP_MOVED_PERM
						|| responseCode == HttpURLConnection.HTTP_MOVED_TEMP
						|| responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
					final String location = hc.getHeaderField("Location");
					if (location == null) {
						logger.warning("No Location header in redirect from " + action + ".");
					} else {
						java.net.URL href;
						href = Urls.createURL(action, location);
						navigate(href, target);
					}
					return;
				}
			}
			try (InputStream in = HttpNetwork.openConnectionCheckRedirects(connection)) {
				sourceCode = null;
				final RecordedInputStream rin = new RecordedInputStream(in, 1000000);
				final InputStream bin = new BufferedInputStream(rin, 8192);
				final String actualURI = urlForLoading.toExternalForm();
				// Only create document, don't parse.
				final HTMLDocumentImpl document = createDocument(
						new InputSourceImpl(bin, actualURI, getDocumentCharset(connection)));
				// Set document in HtmlPanel. Safe to call outside GUI thread.
				final HtmlPanel panel = this.htmlPanel;
				panel.setDocument(document, HtmlRendererContext.this);
				// Now start loading.
				document.load();
				final String ref = urlForLoading.getRef();
				if (ref != null && ref.length() != 0) {
					panel.scrollToElement(ref);
				}
				try {
					sourceCode = rin.getString("ISO-8859-1");
				} catch (final BufferExceededException bee) {
					sourceCode = "[TOO BIG]";
				}
			} catch (SocketTimeoutException e) {
				logger.log(Level.SEVERE, "More than " + connection.getConnectTimeout() + " elapsed.");
		    }
		} finally {
			this.currentConnection = null;
		}
	}

	/**
	 * <p>warn.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public void warn(String message) {
		if (logger.isLoggable(Level.WARNING)) {
			logger.log(Level.WARNING, message);
		}
	}

	/**
	 * <p>warn.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param throwable a {@link java.lang.Throwable} object.
	 */
	public void warn(String message, Throwable throwable) {
		if (logger.isLoggable(Level.WARNING)) {
			logger.log(Level.WARNING, message, throwable);
		}
	}

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}
}
