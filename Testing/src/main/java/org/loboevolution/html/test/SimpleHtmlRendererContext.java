/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Oct 22, 2005
 */
package org.loboevolution.html.test;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Optional;

import javax.swing.JOptionPane;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.html.BrowserFrame;
import org.loboevolution.html.FormInput;
import org.loboevolution.html.HtmlObject;
import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.dombl.FrameNode;
import org.loboevolution.html.domimpl.HTMLAnchorElementImpl;
import org.loboevolution.html.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.html.parser.DocumentBuilderImpl;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.http.SSLCertificate;
import org.loboevolution.http.Urls;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.util.Strings;
import org.loboevolution.util.io.BufferExceededException;
import org.loboevolution.util.io.IORoutines;
import org.loboevolution.util.io.RecordedInputStream;
import org.loboevolution.w3c.html.HTMLAnchorElement;
import org.loboevolution.w3c.html.HTMLCollection;
import org.loboevolution.w3c.html.HTMLElement;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * The <code>SimpleHtmlRendererContext</code> class implements the
 * {@link org.loboevolution.html.HtmlRendererContext} interface. Note that this
 * class provides rudimentary implementations of most callback methods.
 * Overridding some of the methods in this class will usually be necessary in a
 * professional application.
 * <p>
 * A simple way to load a URL into the {@link HtmlPanel} of the renderer context
 * is to invoke {@link #navigate(String)}.
 */
public class SimpleHtmlRendererContext implements HtmlRendererContext {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(SimpleHtmlRendererContext.class);

	/** The html panel. */
	private final HtmlPanel htmlPanel;

	/** The parent rcontext. */
	private final HtmlRendererContext parentRcontext;
	
	/** The source code. */
	private volatile String sourceCode;

	/** The bcontext. */
	private UserAgentContext bcontext = null;

	/** The opener. */
	private volatile HtmlRendererContext opener;
	
	/**
	 * The connection currently opened by openSync() if any.
	 */
	protected URLConnection currentConnection;

	/**
	 * Constructs a SimpleHtmlRendererContext.
	 *
	 * @param contextComponent
	 *            The component that will render HTML.
	 * @param ucontext
	 *            the ucontext
	 * @see SimpleUserAgentContext
	 */
	public SimpleHtmlRendererContext(HtmlPanel contextComponent, UserAgentContext ucontext) {
		super();
		this.htmlPanel = contextComponent;
		this.parentRcontext = null;
		this.bcontext = ucontext;
	}

	/**
	 * Constructs a SimpleHtmlRendererContext that is a child of another
	 * <code>{@link HtmlRendererContext}</code>.
	 *
	 * @param contextComponent
	 *            The component that will render HTML.
	 * @param parentRcontext
	 *            The parent's renderer context.
	 */
	public SimpleHtmlRendererContext(HtmlPanel contextComponent, HtmlRendererContext parentRcontext) {
		super();
		this.htmlPanel = contextComponent;
		this.parentRcontext = parentRcontext;
		this.bcontext = parentRcontext == null ? null : parentRcontext.getUserAgentContext();
	}

	/**
	 * Gets the html panel.
	 *
	 * @return the html panel
	 */
	public HtmlPanel getHtmlPanel() {
		return this.htmlPanel;
	}

	/**
	 * Gets the source code.
	 *
	 * @return the source code
	 */
	public String getSourceCode() {
		return this.sourceCode;
	}

	/**
	 * Gets a collection of current document frames, by querying the document
	 * currently held by the local {@link org.loboevolution.html.gui.HtmlPanel}
	 * instance.
	 *
	 * @return the frames
	 */
	@Override
	public HTMLCollection getFrames() {
		Object rootNode = this.htmlPanel.getRootNode();
		if (rootNode instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) rootNode).getFrames();
		} else {
			return null;
		}
	}

	/**
	 * Implements reload as navigation to current URL. Override to implement a
	 * more robust reloading mechanism.
	 */
	@Override
	public void reload() {
		HTMLDocumentImpl document = (HTMLDocumentImpl) this.htmlPanel.getRootNode();
		if (document != null) {
			try {
				URL url = new URL(document.getDocumentURI());
				this.navigate(url, null);
			} catch (MalformedURLException throwable) {
				this.warn("reload(): Malformed URL", throwable);
			}
		}
	}

	/**
	 * Implements the link click handler by invoking
	 * {@link #navigate(URL, String)}.
	 *
	 * @param linkNode
	 *            the link node
	 * @param url
	 *            the url
	 * @param target
	 *            the target
	 */
	@Override
	public void linkClicked(HTMLElement linkNode, URL url, String target) {
		this.navigate(url, target);
	}

	/**
	 * Gets the proxy.
	 *
	 * @return the proxy
	 */
	protected Proxy getProxy() {
		Object ucontext = this.getUserAgentContext();
		if (ucontext instanceof SimpleUserAgentContext) {
			return ((SimpleUserAgentContext) ucontext).getProxy();
		}
		return Proxy.NO_PROXY;
	}

	/**
	 * Implements simple navigation with incremental rendering by invoking
	 * {@link #submitForm(String, URL, String, String, FormInput[])} with a
	 * <code>GET</code> request method.
	 *
	 * @param href
	 *            the href
	 * @param target
	 *            the target
	 */
	@Override
	public void navigate(final URL href, String target) {
		this.submitForm("GET", href, target, null, null);
	}

	/**
	 * Convenience method provided to allow loading a document into the
	 * renderer.
	 *
	 * @param fullURL
	 *            The absolute URL of the document.
	 * @throws MalformedURLException
	 *             the malformed url exception
	 * @throws UnsupportedEncodingException
	 * @see #navigate(URL, String)
	 */
	public void navigate(String fullURL) throws MalformedURLException, UnsupportedEncodingException {
		URL href = Urls.createURL(null, fullURL);
		this.navigate(href, "_this");
	}

	/**
	 * Implements simple navigation and form submission with incremental
	 * rendering and target processing, including frame lookup. Should be
	 * overridden to allow for more robust browser navigation and form
	 * submission.
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
	 * @param method
	 *            the method
	 * @param action
	 *            the action
	 * @param target
	 *            the target
	 * @param enctype
	 *            the enctype
	 * @param formInputs
	 *            the form inputs
	 * @see #navigate(URL, String)
	 */
	@Override
	public void submitForm(final String method, final URL action, final String target, final String enctype,
			final FormInput[] formInputs) {
		// This method implements simple incremental rendering.
		if (target != null) {
			HtmlRendererContext topCtx = this.getTop();
			HTMLCollection frames = topCtx.getFrames();
			if (frames != null) {
				Node frame = frames.namedItem(target);
				if (logger.isInfoEnabled()) {
					logger.info("submitForm(): Frame matching target=" + target + " is " + frame);
				}
				if (frame instanceof FrameNode) {
					BrowserFrame bframe = ((FrameNode) frame).getBrowserFrame();
					if (bframe == null) {
						throw new IllegalStateException("Frame node without a BrowserFrame instance: " + frame);
					}
					if (bframe.getHtmlRendererContext() != this) {
						bframe.loadURL(action);
						return;
					}
				}
			}
			String actualTarget = target.trim().toLowerCase();
			if ("_top".equals(actualTarget)) {
				this.getTop().navigate(action, null);
				return;
			} else if ("_parent".equals(actualTarget)) {
				HtmlRendererContext parent = this.getParent();
				if (parent != null) {
					parent.navigate(action, null);
					return;
				}
			} else if ("_blank".equals(actualTarget)) {
				this.open(action, "cobra.blank", "", false);
				return;
			} else {
				logger.warn("submitForm(): Link target unrecognized: " + actualTarget);
			}
		}

		// Make request asynchronously.
		if (this.isNavigationAsynchronous()) {
			new Thread() {
				@Override
				public void run() {
					try {
						SimpleHtmlRendererContext.this.submitFormSync(method, action, target, enctype, formInputs);
					} catch (Exception err) {
						SimpleHtmlRendererContext.this.error("navigate(): Error loading or parsing request.", err);
					}
				}
			}.start();
		} else {
			try {
				SimpleHtmlRendererContext.this.submitFormSync(method, action, target, enctype, formInputs);
			} catch (Exception err) {
				SimpleHtmlRendererContext.this.error("navigate(): Error loading or parsing request.", err);
			}
		}
	}

	/**
	 * Checks if is navigation asynchronous.
	 *
	 * @return true, if is navigation asynchronous
	 */
	protected boolean isNavigationAsynchronous() {
		return true;
	}

	/**
	 * Submits a form and/or navigates by making a <i>synchronous</i> request.
	 * This method is invoked by
	 * {@link #submitForm(String, URL, String, String, FormInput[])}.
	 *
	 * @param method
	 *            The request method.
	 * @param action
	 *            The action URL.
	 * @param target
	 *            The target identifier.
	 * @param enctype
	 *            The encoding type.
	 * @param formInputs
	 *            The form inputs.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SAXException
	 *             the SAX exception
	 * @see #submitForm(String, URL, String, String, FormInput[])
	 */
	protected void submitFormSync(final String method, final URL action, final String target, String enctype,
			final FormInput[] formInputs) throws IOException, SAXException {
		final String actualMethod = method.toUpperCase();
		URL resolvedURL;
		if ("GET".equals(actualMethod) && formInputs != null) {
			boolean firstParam = true;
			// TODO: What about the userInfo part of the URL?
			URL noRefAction = new URL(action.getProtocol(), action.getHost(), action.getPort(), action.getFile());
			StringBuilder newUrlBuffer = new StringBuilder(noRefAction.toExternalForm());
			if (action.getQuery() == null) {
				newUrlBuffer.append("?");
			} else {
				newUrlBuffer.append("&");
			}
			for (FormInput parameter : formInputs) {
				String name = parameter.getName();
				String encName = URLEncoder.encode(name, "UTF-8");
				if (parameter.isText()) {
					if (firstParam) {
						firstParam = false;
					} else {
						newUrlBuffer.append("&");
					}
					String valueStr = parameter.getTextValue();
					String encValue = URLEncoder.encode(valueStr, "UTF-8");
					newUrlBuffer.append(encName);
					newUrlBuffer.append("=");
					newUrlBuffer.append(encValue);
				} else {
					logger.warn("postData(): Ignoring non-textual parameter " + name + " for GET.");
				}
			}
			resolvedURL = new URL(newUrlBuffer.toString());
		} else {
			resolvedURL = action;
		}
		URL urlForLoading;
		if (resolvedURL.getProtocol().equalsIgnoreCase("file")) {
			// Remove query so it works.
			try {
				String ref = action.getRef();
				String refText = Strings.isBlank(ref) ? "" : "#" + ref;
				urlForLoading = new URL(resolvedURL.getProtocol(), action.getHost(), action.getPort(),
						action.getPath() + refText);
			} catch (MalformedURLException throwable) {
				this.warn("malformed", throwable);
				urlForLoading = action;
			}
		} else {
			urlForLoading = resolvedURL;
		}
		if (logger.isInfoEnabled()) {
			logger.info("process(): Loading URI=[" + urlForLoading + "].");
		}
		long time0 = System.currentTimeMillis();
		// Using potentially different URL for loading.
		Proxy proxy = SimpleHtmlRendererContext.this.getProxy();
		boolean isPost = "POST".equals(actualMethod);
		SSLCertificate.setCertificate();
		URLConnection connection = proxy == null || proxy == Proxy.NO_PROXY ? urlForLoading.openConnection()
				: urlForLoading.openConnection(proxy);
		this.currentConnection = connection;
		try {
			connection.setRequestProperty("User-Agent", getUserAgentContext().getUserAgent());
			connection.setRequestProperty("Cookie", "");
			connection.setRequestProperty("Accept-Encoding", UserAgentContext.GZIP_ENCODING);
			if (connection instanceof HttpURLConnection) {
				HttpURLConnection hc = (HttpURLConnection) connection;
				hc.setRequestMethod(actualMethod);
				hc.setInstanceFollowRedirects(false);
			}
			if (isPost) {
				connection.setDoOutput(true);
				ByteArrayOutputStream bufOut = new ByteArrayOutputStream();
				boolean firstParam = true;
				if (formInputs != null) {
					for (FormInput parameter : formInputs) {
						String name = parameter.getName();
						String encName = URLEncoder.encode(name, "UTF-8");
						if (parameter.isText()) {
							if (firstParam) {
								firstParam = false;
							} else {
								bufOut.write((byte) '&');
							}
							String valueStr = parameter.getTextValue();
							String encValue = URLEncoder.encode(valueStr, "UTF-8");
							bufOut.write(encName.getBytes("UTF-8"));
							bufOut.write((byte) '=');
							bufOut.write(encValue.getBytes("UTF-8"));
						} else {
							logger.warn("postData(): Ignoring non-textual parameter " + name + " for POST.");
						}
					}
				}
				// Do not add a line break to post content. Some servers
				// can be picky about that (namely, java.net).
				byte[] postContent = bufOut.toByteArray();
				if (connection instanceof HttpURLConnection) {
					((HttpURLConnection) connection).setFixedLengthStreamingMode(postContent.length);
				}
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				// connection.setRequestProperty("Content-Length",
				// String.valueOf(postContent.length));
				OutputStream postOut = connection.getOutputStream();
				postOut.write(postContent);
				postOut.flush();
			}
			if (connection instanceof HttpURLConnection) {
				HttpURLConnection hc = (HttpURLConnection) connection;
				int responseCode = hc.getResponseCode();
				if (logger.isInfoEnabled()) {
					logger.info("process(): HTTP response code: " + responseCode);
				}
				if (responseCode == HttpURLConnection.HTTP_MOVED_PERM
						|| responseCode == HttpURLConnection.HTTP_MOVED_TEMP
						|| responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
					String location = hc.getHeaderField("Location");
					if (location == null) {
						logger.warn("No Location header in redirect from " + action + ".");
					} else {
						URL href;
						href = Urls.createURL(action, location);
						SimpleHtmlRendererContext.this.navigate(href, target);
					}
					return;
				}
			}
			InputStream in = IORoutines.getInputStream(connection);
			try {
				SimpleHtmlRendererContext.this.sourceCode = null;
				long time1 = System.currentTimeMillis();
				RecordedInputStream rin = new RecordedInputStream(in, 1000000);
				InputStream bin = new BufferedInputStream(rin, 8192);
				String actualURI = urlForLoading.toExternalForm();
				// Only create document, don't parse.
				HTMLDocumentImpl document = this.createDocument(new InputSourceImpl(bin, actualURI, getDocumentCharset(connection)));
				// Set document in HtmlPanel. Safe to call outside GUI thread.
				HtmlPanel panel = htmlPanel;
				panel.setDocument(document, SimpleHtmlRendererContext.this);
				// Now start loading.
				document.load();
				long time2 = System.currentTimeMillis();
				if (logger.isInfoEnabled()) {
					logger.info("Parsed URI=[" + urlForLoading + "]: Parse elapsed: " + (time2 - time1)
							+ " ms. Connection elapsed: " + (time1 - time0) + " ms.");
				}
				String ref = urlForLoading.getRef();
				if (Strings.isNotBlank(ref)) {
					panel.scrollToElement(ref);
				}
				try {
					SimpleHtmlRendererContext.this.sourceCode = rin.getString("UTF-8");
				} catch (BufferExceededException bee) {
					SimpleHtmlRendererContext.this.sourceCode = "[TOO BIG]";
				}
			} finally {
				in.close();
			}
		} finally {
			this.currentConnection = null;
		}
	}

	/**
	 * Creates a blank document instance. This method is invoked whenever
	 * navigation or form submission occur. It is provided so it can be
	 * overridden to create specialized document implmentations.
	 *
	 * @param inputSource
	 *            The document input source.
	 * @return the HTML document impl
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SAXException
	 *             the SAX exception
	 */
	protected HTMLDocumentImpl createDocument(org.xml.sax.InputSource inputSource) throws IOException, SAXException {
		DocumentBuilderImpl builder = new DocumentBuilderImpl(this.getUserAgentContext(), SimpleHtmlRendererContext.this);
		return (HTMLDocumentImpl) builder.createDocument(inputSource);
	}

	/**
	 * This method is invoked by
	 * {@link #submitForm(String, URL, String, String, FormInput[])} to
	 * determine the charset of a document. The charset is determined by looking
	 * at the <code>Content-Type</code> header.
	 *
	 * @param connection
	 *            A URL connection.
	 * @return the document charset
	 */
	protected String getDocumentCharset(URLConnection connection) {
		String encoding = Urls.getCharset(connection);
		return encoding == null ? "UTF-8" : encoding;
	}

	// Methods useful to Window below:

	/**
	 * Opens a simple message dialog.
	 *
	 * @param message
	 *            the message
	 */
	@Override
	public void alert(String message) {
		JOptionPane.showMessageDialog(this.htmlPanel, message);
	}

	/**
	 * It should give up focus on the current browser window. This
	 * implementation does nothing and should be overridden.
	 */
	@Override
	public void blur() {
		this.warn("back(): Not overridden");
	}

	/**
	 * It should close the current browser window. This implementation does
	 * nothing and should be overridden.
	 */
	@Override
	public void close() {
		this.warn("close(): Not overridden");
	}

	/**
	 * Opens a simple confirmation window.
	 *
	 * @param message
	 *            the message
	 * @return true, if successful
	 */
	@Override
	public boolean confirm(String message) {
		int retValue = JOptionPane.showConfirmDialog(htmlPanel, message, "Confirm", JOptionPane.YES_NO_OPTION);
		return retValue == JOptionPane.YES_OPTION;
	}

	/**
	 * It should request focus for the current browser window. This
	 * implementation does nothing and should be overridden.
	 */
	@Override
	public void focus() {
		this.warn("focus(): Not overridden");
	}

	/**
	 * It should open a new browser window. This implementation does nothing and
	 * should be overridden.
	 *
	 * @param url
	 *            The requested URL.
	 * @param windowName
	 *            A window identifier.
	 * @param windowFeatures
	 *            Window features specified in a format equivalent to that of
	 *            window.open() in Javascript.
	 * @param replace
	 *            Whether an existing window with the same name should be
	 *            replaced.
	 * @return the html renderer context
	 */
	@Override
	public HtmlRendererContext open(URL url, String windowName, String windowFeatures, boolean replace) {
		this.warn("open(): Not overridden");
		return null;
	}

	/**
	 * Shows a simple prompt dialog.
	 *
	 * @param message
	 *            the message
	 * @param inputDefault
	 *            the input default
	 * @return the string
	 */
	@Override
	public String prompt(String message, String inputDefault) {
		return JOptionPane.showInputDialog(htmlPanel, message);
	}

	/**
	 * Changes the origin of the HTML block's scrollable area according to the
	 * position given.
	 * <p>
	 * This method may be called outside of the GUI thread. The operation is
	 * scheduled immediately in that thread as needed.
	 *
	 * @param x
	 *            The new x coordinate for the origin.
	 * @param y
	 *            The new y coordinate for the origin.
	 */
	@Override
	public void scroll(int x, int y) {
		this.htmlPanel.scroll(x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlRendererContext#scrollBy(int, int)
	 */
	@Override
	public void scrollBy(int x, int y) {
		this.htmlPanel.scrollBy(x, y);
	}

	/**
	 * Should return true if and only if the current browser window is closed.
	 * This implementation returns false and should be overridden.
	 *
	 * @return true, if is closed
	 */
	@Override
	public boolean isClosed() {
		this.warn("isClosed(): Not overridden");
		return false;
	}

	/**
	 * Should return true if and only if the current browser window is closed.
	 * This implementation returns false and should be overridden.
	 *
	 * @return the default status
	 */
	@Override
	public String getDefaultStatus() {
		this.warn("getDefaultStatus(): Not overridden");
		return "";
	}

	/**
	 * It should return the name of the browser window, if this renderer context
	 * is for the top frame in the window. This implementation returns a blank
	 * string, so it should be overridden.
	 *
	 * @return the name
	 */
	@Override
	public String getName() {
		this.warn("getName(): Not overridden");
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlRendererContext#getParent()
	 */
	@Override
	public HtmlRendererContext getParent() {
		return this.parentRcontext;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlRendererContext#getOpener()
	 */
	@Override
	public HtmlRendererContext getOpener() {
		return this.opener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.HtmlRendererContext#setOpener(org.loboevolution.html.
	 * HtmlRendererContext)
	 */
	@Override
	public void setOpener(HtmlRendererContext opener) {
		this.opener = opener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlRendererContext#getStatus()
	 */
	@Override
	public String getStatus() {
		this.warn("getStatus(): Not overridden");
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlRendererContext#setStatus(java.lang.String)
	 */
	@Override
	public void setStatus(String message) {
		this.warn("setStatus(): Not overridden");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlRendererContext#getTop()
	 */
	@Override
	public HtmlRendererContext getTop() {
		HtmlRendererContext ancestor = this.parentRcontext;
		if (ancestor == null) {
			return this;
		}
		return ancestor.getTop();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlRendererContext#createBrowserFrame()
	 */
	@Override
	public BrowserFrame createBrowserFrame() {
		return new SimpleBrowserFrame(this);
	}

	/**
	 * Warn.
	 *
	 * @param message
	 *            the message
	 * @param throwable
	 *            the throwable
	 */
	public void warn(String message, Throwable throwable) {
		if (logger.isWarnEnabled()) {
			logger.error(message, throwable);
		}
	}

	/**
	 * Error.
	 *
	 * @param message
	 *            the message
	 * @param throwable
	 *            the throwable
	 */
	public void error(String message, Throwable throwable) {
		if (logger.isErrorEnabled()) {
			logger.error( message, throwable);
		}
	}

	/**
	 * Warn.
	 *
	 * @param message
	 *            the message
	 */
	public void warn(String message) {
		if (logger.isWarnEnabled()) {
			logger.error(message);
		}
	}

	/**
	 * Error.
	 *
	 * @param message
	 *            the message
	 */
	public void error(String message) {
		if (logger.isErrorEnabled()) {
			logger.error( message);
		}
	}

	/**
	 * Returns <code>null</code>. This method should be overridden to provide
	 * OBJECT, EMBED or APPLET functionality.
	 *
	 * @param element
	 *            the element
	 * @return the html object
	 */
	@Override
	public HtmlObject getHtmlObject(HTMLElement element) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlRendererContext#setDefaultStatus(java.lang.
	 * String)
	 */
	@Override
	public void setDefaultStatus(String message) {
		this.warn("setDefaultStatus(): Not overridden.");
	}
	
	

	/**
	 * If a {@link org.loboevolution.http.UserAgentContext} instance was provided
	 * in the constructor, then that instance is returned. Otherwise, an
	 * instance of {@link SimpleUserAgentContext} is created and returned.
	 * <p>
	 * The context returned by this method is used by local request facilities
	 * and other parts of the renderer.
	 *
	 * @return the user agent context
	 */
	@Override
	public UserAgentContext getUserAgentContext() {
		synchronized (this) {
			if (this.bcontext == null) {
				this.warn(
						"getUserAgentContext(): UserAgentContext not provided in constructor. Creating a simple one.");
				this.bcontext = new SimpleUserAgentContext();
			}
			return this.bcontext;
		}
	}

	/**
	 * Should be overridden to return true if the link has been visited.
	 *
	 * @param link
	 *            the link
	 * @return true, if is visited link
	 */
	@Override
	public boolean isVisitedLink(HTMLAnchorElement link) {
		return false;
	}

	/**
	 * This method must be overridden to implement a context menu.
	 *
	 * @param element
	 *            the element
	 * @param event
	 *            the event
	 * @return true, if successful
	 */
	@Override
	public boolean onContextMenu(HTMLElement element, MouseEvent event) {
		return true;
	}

	/**
	 * This method can be overridden to receive notifications when the mouse
	 * leaves an element.
	 *
	 * @param element
	 *            the element
	 * @param event
	 *            the event
	 */
	@Override
	public void onMouseOut(HTMLElement element, MouseEvent event) {
		if (element instanceof HTMLAnchorElementImpl) {
			setStatus(null);
		}
	}
	
	/**
	 * This method can be overridden to receive notifications when the mouse
	 * first enters an element.
	 *
	 * @param element
	 *            the element
	 * @param event
	 *            the event
	 */
	@Override
	public void onMouseOver(HTMLElement element, MouseEvent event) {
		if (element instanceof HTMLAnchorElementImpl) {
			HTMLAnchorElementImpl linkElement = (HTMLAnchorElementImpl) element;
			setStatus(linkElement.getAbsoluteHref());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlRendererContext#isImageLoadingEnabled()
	 */
	@Override
	public boolean isImageLoadingEnabled() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.HtmlRendererContext#onDoubleClick(org.loboevolution.
	 * html .w3c.HTMLElement, java.awt.event.MouseEvent)
	 */
	@Override
	public boolean onDoubleClick(HTMLElement element, MouseEvent event) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.HtmlRendererContext#onMouseClick(org.loboevolution.
	 * html .w3c.HTMLElement, java.awt.event.MouseEvent)
	 */
	@Override
	public boolean onMouseClick(HTMLElement element, MouseEvent event) {
		return true;
	}

	/**
	 * Gets the window.
	 *
	 * @param c
	 *            the c
	 * @return the window
	 */
	private static Window getWindow(Component c) {
		Component current = c;
		while (current != null && !(current instanceof Window)) {
			current = current.getParent();
		}
		return (Window) current;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlRendererContext#resizeBy(int, int)
	 */
	@Override
	public void resizeBy(int byWidth, int byHeight) {
		Window window = getWindow(this.htmlPanel);
		if (window != null) {
			window.setSize(window.getWidth() + byWidth, window.getHeight() + byHeight);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlRendererContext#resizeTo(int, int)
	 */
	@Override
	public void resizeTo(int width, int height) {
		Window window = getWindow(this.htmlPanel);
		if (window != null) {
			window.setSize(width, height);
		}
	}

	/**
	 * It should navigate back one page. This implementation does nothing and
	 * should be overridden.
	 */
	@Override
	public void back() {
		if (logger.isWarnEnabled()) {
			logger.error("back() does nothing, unless overridden.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlRendererContext#forward()
	 */
	@Override
	public void forward() {
		if (logger.isWarnEnabled()) {
			logger.error("forward() does nothing, unless overridden.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlRendererContext#getCurrentURL()
	 */
	@Override
	public String getCurrentURL() {
		Object node = this.htmlPanel.getRootNode();
		if (node instanceof HTMLDocumentImpl) {
			HTMLDocumentImpl doc = (HTMLDocumentImpl) node;
			return doc.getDocumentURI();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlRendererContext#getHistoryLength()
	 */
	@Override
	public int getHistoryLength() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlRendererContext#getNextURL()
	 */
	@Override
	public String getNextURL() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlRendererContext#getPreviousURL()
	 */
	@Override
	public String getPreviousURL() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.HtmlRendererContext#goToHistoryURL(java.lang.String)
	 */
	@Override
	public void goToHistoryURL(String url) {
		if (logger.isWarnEnabled()) {
			logger.error("goToHistoryURL() does nothing, unless overridden.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlRendererContext#moveInHistory(int)
	 */
	@Override
	public void moveInHistory(int offset) {
		if (logger.isWarnEnabled()) {
			logger.error("moveInHistory() does nothing, unless overridden.");
		}
	}

	@Override
	public void setCursor(final Optional<Cursor> cursorOpt) {
		Cursor cursor = cursorOpt.orElse(Cursor.getDefaultCursor());
		htmlPanel.setCursor(cursor);
	}
}
