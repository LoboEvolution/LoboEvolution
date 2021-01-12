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
 * Created on Nov 19, 2005
 */
package org.loboevolution.http;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;

import org.loboevolution.common.EventDispatch;
import org.loboevolution.common.IORoutines;
import org.loboevolution.common.Urls;
import org.loboevolution.html.ReadyStateChangeListener;
import org.loboevolution.net.HttpNetwork;
import org.w3c.dom.Document;

/**
 * <p>HttpRequest class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HttpRequest {

	private static final Logger logger = Logger.getLogger(HttpRequest.class.getName());

	/**
	 * The complete request state. All operations are finished.
	 */
	public static final int STATE_COMPLETE = 4;

	/**
	 * The interactive request state. Downloading response.
	 */
	public static final int STATE_INTERACTIVE = 3;

	/**
	 * The loaded request state. Headers and status are now available.
	 */
	public static final int STATE_LOADED = 2;

	/**
	 * The loading request state. The open method has been called, but
	 * a response has not been received yet.
	 */
	public static final int STATE_LOADING = 1;

	/**
	 * The uninitialized request state.
	 */
	public static final int STATE_UNINITIALIZED = 0;

	/**
	 * The URLConnection is assigned to this field while it is ongoing.
	 */
	protected java.net.URLConnection connection;
	private boolean isAsync;
	private final Proxy proxy;
	private final EventDispatch readyEvent = new EventDispatch();
	private int readyState;

	protected String requestMethod;
	protected String requestPassword;
	private java.net.URL requestURL;
	protected String requestUserName;
	private byte[] responseBytes;

	/**
	 * Response headers are set in this string after a response is received.
	 */
	protected String responseHeaders;

	/**
	 * Response headers are set in this map after a response is received.
	 */
	protected Map<String, List<String>> responseHeadersMap;

	private int status;

	private String statusText;

	/**
	 * <p>Constructor for HttpRequest.</p>
	 *
	 * @param context a {@link org.loboevolution.http.UserAgentContext} object.
	 * @param proxy a {@link java.net.Proxy} object.
	 */
	public HttpRequest(UserAgentContext context, Proxy proxy) {
		this.proxy = proxy;
	}

	/**
	 * <p>abort.</p>
	 */
	public void abort() {
		URLConnection c;
		synchronized (this) {
			c = this.connection;
		}
		if (c instanceof HttpURLConnection) {
			((HttpURLConnection) c).disconnect();
		} else if (c != null) {
			try {
				c.getInputStream().close();
			} catch (final IOException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	/**
	 * <p>addReadyStateChangeListener.</p>
	 *
	 * @param listener a {@link org.loboevolution.html.ReadyStateChangeListener} object.
	 */
	public void addReadyStateChangeListener(final ReadyStateChangeListener listener) {
		this.readyEvent.addListener(event -> listener.readyStateChanged());
	}

	private void changeState(int readyState, int status, String statusMessage, byte[] bytes) {
		synchronized (this) {
			this.readyState = readyState;
			this.status = status;
			this.statusText = statusMessage;
			this.responseBytes = bytes;
		}
		this.readyEvent.fireEvent(null);
	}

	/**
	 * <p>getAllResponseHeaders.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public synchronized String getAllResponseHeaders() {
		return this.responseHeaders;
	}

	private String getAllResponseHeaders(URLConnection c) {
		int idx = 0;
		String value;
		final StringBuilder buf = new StringBuilder();
		while ((value = c.getHeaderField(idx)) != null) {
			final String key = c.getHeaderFieldKey(idx);
			buf.append(key);
			buf.append(": ");
			buf.append(value);
			idx++;
		}
		return buf.toString();
	}

	/**
	 * This is the charset used to post data provided to {@link #send(String)}. It
	 * returns "UTF-8" unless overridden.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	protected String getPostCharset() {
		return "UTF-8";
	}

	/**
	 * <p>Getter for the field readyState.</p>
	 *
	 * @return a int.
	 */
	public synchronized int getReadyState() {
		return this.readyState;
	}

	/**
	 * <p>Getter for the field responseBytes.</p>
	 *
	 * @return an array of {@link byte} objects.
	 */
	public synchronized byte[] getResponseBytes() {
		return this.responseBytes;
	}

	/**
	 * <p>getResponseHeader.</p>
	 *
	 * @param headerName a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 */
	public synchronized List<String> getResponseHeader(String headerName) {
		final Map<String, List<String>> headers = this.responseHeadersMap;
		return headers == null ? null : headers.get(headerName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.HttpRequest#getResponseImage()
	 */
	/**
	 * <p>getResponseImage.</p>
	 *
	 * @return a {@link java.awt.Image} object.
	 */
	public synchronized Image getResponseImage() {
		final byte[] bytes = this.responseBytes;
		if (bytes == null) {
			return null;
		}
		return Toolkit.getDefaultToolkit().createImage(bytes);
	}

	/**
	 * <p>getResponseText.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public synchronized String getResponseText() {
		final byte[] bytes = this.responseBytes;
		final java.net.URLConnection connection = this.connection;
		String encoding = connection == null ? "ISO-8859-1" : Urls.getCharset(connection);
		if (encoding == null) {
			encoding = "ISO-8859-1";
		}
		try {
			return bytes == null ? null : new String(bytes, encoding);
		} catch (final UnsupportedEncodingException uee) {
			logger.log(Level.WARNING,
					"getResponseText(): Charset '" + encoding + "' did not work. Retrying with ISO-8859-1.", uee);
			try {
				return new String(bytes, "ISO-8859-1");
			} catch (final UnsupportedEncodingException uee2) {
				// Ignore this time
				return null;
			}
		}
	}

	/**
	 * <p>getResponseXML.</p>
	 *
	 * @return a {@link org.w3c.dom.Document} object.
	 */
	public synchronized Document getResponseXML() {
		final byte[] bytes = this.responseBytes;
		if (bytes == null) {
			return null;
		}
		final InputStream in = new ByteArrayInputStream(bytes);
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
		} catch (final Exception err) {
			logger.log(Level.WARNING, "Unable to parse response as XML.", err);
			return null;
		}
	}

	/**
	 * <p>Getter for the field status.</p>
	 *
	 * @return a int.
	 */
	public synchronized int getStatus() {
		return this.status;
	}

	/**
	 * <p>Getter for the field statusText.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public synchronized String getStatusText() {
		return this.statusText;
	}

	/**
	 * <p>open.</p>
	 *
	 * @param method a {@link java.lang.String} object.
	 * @param url a {@link java.net.URL} object.
	 * @param asyncFlag a boolean.
	 * @param userName a {@link java.lang.String} object.
	 * @throws java.io.IOException if any.
	 */
	public void open(String method, java.net.URL url, boolean asyncFlag, String userName) throws IOException {
		this.open(method, url, asyncFlag, userName, null);
	}

	/**
	 * <p>open.</p>
	 *
	 * @param method a {@link java.lang.String} object.
	 * @param url a {@link java.lang.String} object.
	 * @throws java.lang.Exception if any.
	 */
	public void open(String method, String url) throws Exception {
		this.open(method, url, true);
	}

	/**
	 * <p>open.</p>
	 *
	 * @param method a {@link java.lang.String} object.
	 * @param url a {@link java.lang.String} object.
	 * @param asyncFlag a boolean.
	 * @throws java.lang.Exception if any.
	 */
	public void open(String method, String url, boolean asyncFlag) throws Exception {
		final URL urlObj = Urls.createURL(null, url);
		this.open(method, urlObj, asyncFlag, null);
	}

	/**
	 * <p>open.</p>
	 *
	 * @param method a {@link java.lang.String} object.
	 * @param url a {@link java.net.URL} object.
	 * @throws java.lang.Exception if any.
	 */
	public void open(String method, URL url) throws Exception {
		this.open(method, url, true, null, null);
	}

	/**
	 * <p>open.</p>
	 *
	 * @param method a {@link java.lang.String} object.
	 * @param url a {@link java.net.URL} object.
	 * @param asyncFlag a boolean.
	 * @throws java.lang.Exception if any.
	 */
	public void open(String method, URL url, boolean asyncFlag) throws Exception {
		this.open(method, url, asyncFlag, null, null);
	}

	/**
	 * Opens the request. Call {@link #send(String)} to complete it.
	 *
	 * @param method    The request method.
	 * @param url       The request URL.
	 * @param asyncFlag Whether the request should be asynchronous.
	 * @param userName  The user name of the request (not supported.)
	 * @param password  The password of the request (not supported.)
	 * @throws java.io.IOException if any.
	 */
	public void open(final String method, final URL url, boolean asyncFlag, final String userName,
			final String password) throws IOException {
		abort();
		final Proxy proxy = this.proxy;
		final URLConnection c = proxy == null || proxy == Proxy.NO_PROXY ? url.openConnection() : url.openConnection(proxy);
		synchronized (this) {
			this.connection = c;
			this.isAsync = asyncFlag;
			this.requestMethod = method;
			this.requestURL = url;
			this.requestUserName = userName;
			this.requestPassword = password;
		}
		changeState(HttpRequest.STATE_LOADING, 0, null, null);
	}

	/**
	 * Sends POST content, if any, and causes the request to proceed.
	 * <p>
	 * In the case of asynchronous requests, a new thread is created.
	 *
	 * @param content POST content or null if there's no such content.
	 * @throws java.lang.Exception if any.
	 */
	public void send(final String content) throws Exception {
		final java.net.URL url = this.requestURL;
		if (url == null) {
			throw new Exception("No URL has been provided.");
		}
		if (this.isAsync) {
			// Should use a thread pool instead
			new Thread("SimpleHttpRequest-" + url.getHost()) {
				@Override
				public void run() {
					try {
						sendSync(content);
					} catch (final Throwable thrown) {
						logger.log(Level.WARNING, "send(): Error in asynchronous request on " + url, thrown);
					}
				}
			}.start();
		} else {
			sendSync(content);
		}
	}

	/**
	 * This is a synchronous implementation of {@link #send(String)} method
	 * functionality. It may be overridden to change the behavior of the class.
	 *
	 * @param content POST content if any. It may be null.
	 * @throws java.lang.Exception if any
	 */
	protected void sendSync(String content) throws Exception {
		try {
			// FireFox posts a "loading" state twice as well.
			changeState(HttpRequest.STATE_LOADING, 0, null, null);
			URLConnection c;
			synchronized (this) {
				c = this.connection;
			}
			c.setRequestProperty("User-Agent", HttpNetwork.getUserAgentValue());
			int istatus;
			String istatusText;
			InputStream err;
			if (c instanceof HttpURLConnection) {
				final HttpURLConnection hc = (HttpURLConnection) c;
				String method = this.requestMethod;
				if (method == null) {
					throw new java.io.IOException("Null method.");
				}
				method = method.toUpperCase();
				hc.setRequestMethod(method);
				if ("POST".equals(method) && content != null) {
					hc.setDoOutput(true);
					final byte[] contentBytes = content.getBytes(getPostCharset());
					hc.setFixedLengthStreamingMode(contentBytes.length);
					final OutputStream out = hc.getOutputStream();
					try {
						out.write(contentBytes);
					} finally {
						out.flush();
					}
				}
				istatus = hc.getResponseCode();
				istatusText = hc.getResponseMessage();
				err = hc.getErrorStream();
			} else {
				istatus = 0;
				istatusText = "";
				err = null;
			}
			synchronized (this) {
				this.responseHeaders = this.getAllResponseHeaders(c);
				this.responseHeadersMap = c.getHeaderFields();
			}
			changeState(HttpRequest.STATE_LOADED, istatus, istatusText, null);
			final InputStream in = err == null ? HttpNetwork.openConnectionCheckRedirects(c) : err;
			final int contentLength = c.getContentLength();
			// TODO: In the "interactive" state, some response text is supposed to be
			// available.
			changeState(HttpRequest.STATE_INTERACTIVE, istatus, istatusText, null);
			final byte[] bytes = IORoutines.load(in, contentLength == -1 ? 4096 : contentLength);
			changeState(HttpRequest.STATE_COMPLETE, istatus, istatusText, bytes);
		} finally {
			synchronized (this) {
				this.connection = null;
			}
		}
	}
}
