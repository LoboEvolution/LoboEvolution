/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
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
import org.loboevolution.net.HttpNetwork;
import org.loboevolution.store.LinkStore;
import org.loboevolution.store.NavigationStore;
import org.loboevolution.store.TabStore;
import org.loboevolution.tab.DnDTabbedPane;
import org.loboevolution.tab.TabbedPanePopupMenu;
import org.loboevolution.html.BrowserFrame;
import org.loboevolution.html.FormInput;
import org.loboevolution.html.HtmlObject;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.domimpl.HTMLAbstractUIElement;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLImageElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLLinkElementImpl;
import org.loboevolution.html.gui.HtmlContextMenu;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.html.parser.DocumentBuilderImpl;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.img.ImageViewer;
import org.xml.sax.InputSource;

/**
 * The <code>HtmlRendererContext</code> class implements the
 * {@link org.loboevolution.http.HtmlRendererContext} interface. Note that this
 * class provides rudimentary implementations of most callback methods.
 * Overridding some of the methods in this class will usually be necessary in a
 * professional application.
 * <p>
 * A simple way to load a URL into the {@link HtmlPanel} of the renderer context
 * is to invoke {@link #navigate(String)}.
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

	/**
	 * Constructs a HtmlRendererContext that is a child of another
	 * <code>{@link HtmlRendererContext}</code>.
	 * @param parentRcontext   The parent's renderer context.
	 */
	public HtmlRendererContext(HtmlPanel htmlPanel, HtmlRendererContext parentRcontext) {
		this.htmlPanel = htmlPanel;
		this.parentRcontext = parentRcontext;
		this.bcontext = parentRcontext == null ? null : parentRcontext.getUserAgentContext();
	}

	/**
	 * Constructs a HtmlRendererContext.
	 */
	public HtmlRendererContext(HtmlPanel htmlPanel, UserAgentContext ucontext) {
		this.htmlPanel = htmlPanel;
		this.parentRcontext = null;
		this.bcontext = ucontext;
	}

	/**
	 * Opens a simple message dialog.
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
        List<String> tabsById = nh.getHostOrdered(indexPanel);
        
        for (int i = 0; i < tabsById.size(); i++) {
            String tab = tabsById.get(i);
            if(tab.equals(url) && i > 0) {
            	url = tabsById.get(i - 1);
            }
        }

        final HtmlPanel hpanel = HtmlPanel.createHtmlPanel(url);
    	hpanel.setBrowserPanel(bpanel);
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
	 */
	public boolean confirm(String message) {
		final int retValue = JOptionPane.showConfirmDialog(this.htmlPanel, message, "Confirm",
				JOptionPane.YES_NO_OPTION);
		return retValue == JOptionPane.YES_OPTION;
	}

	public BrowserFrame createBrowserFrame() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Creates a blank document instance. This method is invoked whenever navigation
	 * or form submission occur. It is provided so it can be overridden to create
	 * specialized document implmentations.
	 * 
	 * @param inputSource The document input source.
	 * @throws Exception
	 */
	protected HTMLDocumentImpl createDocument(InputSource inputSource) throws Exception {
		final DocumentBuilderImpl builder = new DocumentBuilderImpl(getUserAgentContext(), this);
		return (HTMLDocumentImpl) builder.createDocument(inputSource);
	}

	public void error(String message) {
		if (logger.isLoggable(Level.SEVERE)) {
			logger.log(Level.SEVERE, message);
		}
	}

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
        List<String> tabsById = nh.getHostOrdered(indexPanel);
        
		for (int i = 0; i < tabsById.size(); i++) {
			String tab = tabsById.get(i);
			if (tab.equals(url) && i < tabsById.size() - 1) {
				url = tabsById.get(i + 1);
			}
		}

        final HtmlPanel hpanel = HtmlPanel.createHtmlPanel(url);
    	hpanel.setBrowserPanel(bpanel);
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
        tabbedPane.remove(indexPanel);
        tabbedPane.insertTab(title, null, htmlPanel, title, indexPanel);
		
        browserFrame.getToolbar().getAddressBar().setText(url);
        bpanel.getScroll().getViewport().add(tabbedPane);
        
        TabStore.deleteTab(indexPanel);
        TabStore.insertTab(indexPanel, url, title);
	}

	public String getCurrentURL() {
		final Object node = this.htmlPanel.getRootNode();
		if (node instanceof HTMLDocumentImpl) {
			final HTMLDocumentImpl doc = (HTMLDocumentImpl) node;
			return doc.getDocumentURI();
		}
		return null;
	}

	/**
	 * Should return true if and only if the current browser window is closed. This
	 * implementation returns false and should be overridden.
	 */
	public String getDefaultStatus() {
		this.warn("getDefaultStatus(): Not overridden");
		return "";
	}

	/**
	 * This method is invoked by
	 * {@link #submitForm(String, URL, String, String, FormInput[])} to determine
	 * the charset of a document. The charset is determined by looking at the
	 * <code>Content-Type</code> header.
	 * 
	 * @param connection A URL connection.
	 */
	protected String getDocumentCharset(URLConnection connection) {
		final String encoding = Urls.getCharset(connection);
		return encoding == null ? "ISO-8859-1" : encoding;
	}

	/**
	 * Gets a collection of current document frames, by querying the document
	 * currently held by the local {@link org.loboevolution.html.gui.HtmlPanel}
	 * instance.
	 */
	public HTMLCollection getFrames() {
		final Object rootNode = this.htmlPanel.getRootNode();
		if (rootNode instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) rootNode).getFrames();
		} else {
			return null;
		}
	}

	public int getHistoryLength() {
		return 0;
	}

	public HtmlObject getHtmlObject(HTMLElementImpl markupElement) {
		// TODO Auto-generated method stub
		return null;
	}

	public HtmlPanel getHtmlPanel() {
		return this.htmlPanel;
	}

	/**
	 * It should return the name of the browser window, if this renderer context is
	 * for the top frame in the window. This implementation returns a blank string,
	 * so it should be overridden.
	 */
	public String getName() {
		this.warn("getName(): Not overridden");
		return "";
	}

	public String getNextURL() {
		return null;
	}

	public HtmlRendererContext getOpener() {
		return this.opener;
	}

	public HtmlRendererContext getParent() {
		return this.parentRcontext;
	}

	public String getPreviousURL() {
		return null;
	}

	/**
	 * Gets the connection proxy used in {@link #navigate(URL, String)}. This
	 * implementation calls {@link SimpleUserAgentContext#getProxy()} if
	 * {@link #getUserAgentContext()} returns an instance assignable to
	 * {@link SimpleUserAgentContext}. The method may be overridden to provide a
	 * different proxy setting.
	 */
	protected Proxy getProxy() {
		return Proxy.NO_PROXY;
	}

	/**
	 * Gets the source code of the current HTML document.
	 */
	public String getSourceCode() {
		return this.sourceCode;
	}

	public String getStatus() {
		this.warn("getStatus(): Not overridden");
		return "";
	}

	public HtmlRendererContext getTop() {
		final HtmlRendererContext ancestor = this.parentRcontext;
		if (ancestor == null) {
			return this;
		}
		return ancestor.getTop();
	}

	/**
	 * If a {@link org.loboevolution.html.UserAgentContext} instance was provided in
	 * the constructor, then that instance is returned. Otherwise, an instance of
	 * {@link SimpleUserAgentContext} is created and returned.
	 * <p>
	 * The context returned by this method is used by local request facilities and
	 * other parts of the renderer.
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

	public void goToHistoryURL(String url) {
		if (logger.isLoggable(Level.WARNING)) {
			logger.log(Level.WARNING, "goToHistoryURL() does nothing, unless overridden.");
		}
	}

	/**
	 * Should return true if and only if the current browser window is closed. This
	 * implementation returns false and should be overridden.
	 */
	public boolean isClosed() {
		this.warn("isClosed(): Not overridden");
		return false;
	}

	public boolean isImageLoadingEnabled() {
		return true;
	}

	/**
	 * Indicates whether navigation (via
	 * {@link #submitForm(String, URL, String, String, FormInput[])}) should be
	 * asynchronous. This overridable implementation returns <code>true</code>.
	 */
	protected boolean isNavigationAsynchronous() {
		return true;
	}

	public boolean isVisitedLink(HTMLLinkElementImpl link) {
		return LinkStore.isVisited(link.getHref());
	}

	/**
	 * Implements the link click handler by invoking {@link #navigate(URL, String)}.
	 */
	public void linkClicked(URL url, boolean isNewTab) {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		final DnDTabbedPane tabbedPane = bpanel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(bpanel));
		int index = -1;
		
		if(isNewTab) {
			index = TabStore.getTabs().size();
		} else {
			index = tabbedPane.getIndex();
		}
		
		String fullURL = url.toString();
		final HtmlPanel hpanel = HtmlPanel.createHtmlPanel(fullURL);
		hpanel.setBrowserPanel(bpanel);
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
		if(!isNewTab) {
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

	public void moveInHistory(int offset) {
		if (logger.isLoggable(Level.WARNING)) {
			logger.log(Level.WARNING, "moveInHistory() does nothing, unless overridden.");
		}
	}

	/**
	 * Convenience method provided to allow loading a document into the renderer.
	 * 
	 * @param fullURL The absolute URL of the document.
	 * @throws Exception
	 * @see #navigate(URL, String)
	 */
	public void navigate(String fullURL) throws Exception {
		final URL href = Urls.createURL(null, fullURL);
		this.navigate(href, "_this");
	}

	/**
	 * Implements simple navigation with incremental rendering by invoking
	 * {@link #submitForm(String, URL, String, String, FormInput[])} with a
	 * <code>GET</code> request method.
	 */
	public void navigate(final URL href, String target) {
		submitForm("GET", href, target, null, null);
	}
	
	/**
	 * This method must be overridden to implement a context menu.
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
			JPopupMenu popupMenuImage = menu.popupMenuImage();
			popupMenuImage.show(event.getComponent(), event.getX(), event.getY());
			return false;
		} else if (element instanceof HTMLLinkElementImpl) {
			JPopupMenu popupMenuLink = menu.popupMenuLink();
			popupMenuLink.show(event.getComponent(), event.getX(), event.getY());
			return false;
		} else if (element instanceof HTMLAbstractUIElement) {
			JPopupMenu popupMenuAbstract = menu.popupMenuAbstractUI();
			popupMenuAbstract.show(event.getComponent(), event.getX(), event.getY());
			return false;
		}
		return true;
	}
	public boolean onDoubleClick(HTMLElement element, MouseEvent event) {
		return true;
	}

	public boolean onMouseClick(HTMLElement element, MouseEvent event) {
		return true;
	}

	/**
	 * This method can be overridden to receive notifications when the mouse leaves
	 * an element.
	 */
	public void onMouseOut(HTMLElement element, MouseEvent event) {
	}

	/**
	 * This method can be overridden to receive notifications when the mouse first
	 * enters an element.
	 */
	public void onMouseOver(HTMLElement element, MouseEvent event) {
	}

	/**
	 * It should open a new browser window. This implementation does nothing and
	 * should be overridden.
	 * 
	 * @param url            The requested URL.
	 * @param windowName     A window identifier.
	 * @param windowFeatures Window features specified in a format equivalent to
	 *                       that of window.open() in Javascript.
	 * @param replace        Whether an existing window with the same name should be
	 *                       replaced.
	 */
	public HtmlRendererContext open(URL url, String windowName, String windowFeatures, boolean replace) {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		final DnDTabbedPane tabbedPane = bpanel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(bpanel));
		int index = TabStore.getTabs().size();
		String fullURL = url.toString();
		final HtmlPanel hpanel = HtmlPanel.createHtmlPanel(fullURL);
		hpanel.setBrowserPanel(bpanel);
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
			e.printStackTrace();
		}
	}

	/**
	 * Shows a simple prompt dialog.
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

	public void resizeBy(int byWidth, int byHeight) {
		final Window window = getWindow(this.htmlPanel);
		if (window != null) {
			window.setSize(window.getWidth() + byWidth, window.getHeight() + byHeight);
		}
	}

	public void resizeTo(int width, int height) {
		final Window window = getWindow(this.htmlPanel);
		if (window != null) {
			window.setSize(width, height);
		}
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

	public void scrollBy(int x, int y) {
		this.htmlPanel.scrollBy(x, y);
	}

	public void setDefaultStatus(String message) {
		this.warn("setDefaultStatus(): Not overridden.");
	}

	public void setHtmlPanel(HtmlPanel panel) {
		this.htmlPanel = panel;
	}

	public void setOpener(HtmlRendererContext opener) {
		this.opener = opener;
	}

	public void setStatus(String message) {
		this.warn("setStatus(): Not overridden");
	}

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
	 * <code>application/x-www-form-urlencoded</code>.
	 * <li>Navigation is normally asynchronous. See
	 * {@link #isNavigationAsynchronous()}.
	 * </ul>
	 * 
	 * @see #navigate(URL, String)
	 */
	public void submitForm(final String method, final URL action, final String target, final String enctype,
			final FormInput[] formInputs) {
		// This method implements simple incremental rendering.
		if (target != null) {

			final String actualTarget = target.trim().toLowerCase();
			if ("_top".equals(actualTarget)) {
				getTop().navigate(action, null);
				return;
			} else if ("_parent".equals(actualTarget)) {
				final HtmlRendererContext parent = getParent();
				if (parent != null) {
					parent.navigate(action, null);
					return;
				}
			} else if ("_blank".equals(actualTarget)) {
				open(action, "cobra.blank", "", false);
				return;
			} else if ("_this".equals(actualTarget)) {
				// fall through
			} else {
				logger.warning("submitForm(): Link target unrecognized: " + actualTarget);
			}
		}

		// Make request asynchronously.
		if (isNavigationAsynchronous()) {
			new Thread() {
				@Override
				public void run() {
					try {
						HtmlRendererContext.this.submitFormSync(method, action, target, enctype, formInputs);
					} catch (final Exception err) {
						HtmlRendererContext.this.error("navigate(): Error loading or parsing request.", err);
					}
				}
			}.start();
		} else {
			try {
				HtmlRendererContext.this.submitFormSync(method, action, target, enctype, formInputs);
			} catch (final Exception err) {
				HtmlRendererContext.this.error("navigate(): Error loading or parsing request.", err);
			}
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
	 * @throws IOException
	 * @throws             org.xml.sax.SAXException
	 * @see #submitForm(String, URL, String, String, FormInput[])
	 */
	protected void submitFormSync(final String method, final java.net.URL action, final String target, String enctype,
			final FormInput[] formInputs) throws Exception {
		final String actualMethod = method.toUpperCase();
		URL resolvedURL;
		if ("GET".equals(actualMethod) && formInputs != null) {
			boolean firstParam = true;
			// TODO: What about the userInfo part of the URL?
			final URL noRefAction = new URL(action.getProtocol(), action.getHost(), action.getPort(), action.getFile());
			final StringBuffer newUrlBuffer = new StringBuffer(noRefAction.toExternalForm());
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
		final Proxy proxy = HtmlRendererContext.this.getProxy();
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
						HtmlRendererContext.this.navigate(href, target);
					}
					return;
				}
			}
			final InputStream in = HttpNetwork.openConnectionCheckRedirects(connection);
			try {
				HtmlRendererContext.this.sourceCode = null;
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
					HtmlRendererContext.this.sourceCode = rin.getString("ISO-8859-1");
				} catch (final BufferExceededException bee) {
					HtmlRendererContext.this.sourceCode = "[TOO BIG]";
				}
			} finally {
				in.close();
			}
		} finally {
			this.currentConnection = null;
		}
	}

	public void warn(String message) {
		if (logger.isLoggable(Level.WARNING)) {
			logger.log(Level.WARNING, message);
		}
	}

	public void warn(String message, Throwable throwable) {
		if (logger.isLoggable(Level.WARNING)) {
			logger.log(Level.WARNING, message, throwable);
		}
	}
}
