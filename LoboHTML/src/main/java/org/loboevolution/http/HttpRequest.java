/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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
/*
 * Created on Nov 19, 2005
 */
package org.loboevolution.http;

import org.loboevolution.common.EventDispatch;
import org.loboevolution.common.IORoutines;
import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;
import org.loboevolution.html.ReadyStateChangeListener;
import org.loboevolution.html.js.xml.XMLDocumentBuilder;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.net.HttpNetwork;
import org.loboevolution.net.ReadyStateType;
import org.loboevolution.net.UserAgent;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>HttpRequest class.</p>
 */
public class HttpRequest {

	private static final Logger logger = Logger.getLogger(HttpRequest.class.getName());

	private URLConnection connection;
	private URL requestURL;

	private final Proxy proxy;
	private final EventDispatch readyEvent = new EventDispatch();
	private ReadyStateType readyState = ReadyStateType.UNSENT;

	private String requestMethod;
	private String requestPassword;
	private String requestUserName;
	protected String responseHeaders;
	private String statusText;

	protected Map<String, List<String>> responseHeadersMap = new HashMap<>();
	private byte[] responseBytes;

	private boolean isAsync;

	private int status;

	/**
	 * <p>Constructor for HttpRequest.</p>
	 * @param proxy a {@link java.net.Proxy} object.
	 */
	public HttpRequest(Proxy proxy) {
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

		responseHeadersMap.clear();
		changeState(ReadyStateType.UNSENT, 0, null, null);
	}

	/**
	 * <p>addReadyStateChangeListener.</p>
	 *
	 * @param listener a {@link org.loboevolution.html.ReadyStateChangeListener} object.
	 */
	public void addReadyStateChangeListener(final ReadyStateChangeListener listener) {
		this.readyEvent.addListener(event -> listener.readyStateChanged());
	}

	/**
	 * <p>getAllResponseHeaders.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public synchronized String getAllResponseHeaders() {
		return this.responseHeaders;
	}

	/**
	 * This is the charset used to post data provided to send. It
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
		return this.readyState.getValue();
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

	/**
	 * <p>getResponseText.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public synchronized String getResponseText() {
		final byte[] bytes = this.responseBytes;
		final URLConnection connection = this.connection;
		String encoding = connection == null ? "ISO-8859-1" : Urls.getCharset(connection);
		if (encoding == null) {
			encoding = "ISO-8859-1";
		}
		try {
			return bytes == null ? null : new String(bytes, encoding);
		} catch (final UnsupportedEncodingException uee) {
			logger.log(Level.WARNING,
					"getResponseText(): Charset '" + encoding + "' did not work. Retrying with ISO-8859-1.", uee);
			return new String(bytes, StandardCharsets.ISO_8859_1);
		}
	}

	/**
	 * <p>getResponseXML.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Document} object.
	 */
	public synchronized Document getResponseXML() {
		final byte[] bytes = this.responseBytes;
		if (bytes == null) {
			return null;
		}
		final InputStream in = new ByteArrayInputStream(bytes);
		try {
			XMLDocumentBuilder builder = new XMLDocumentBuilder();
			return builder.parse(new InputSourceImpl(in, "", StandardCharsets.UTF_8));
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
	 * Opens the request. Call send to complete it.
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

		changeState(ReadyStateType.OPENED, 0, null, null);
	}

	/**
	 * Sends POST content, if any, and causes the request to proceed.
	 * <p>
	 * In the case of asynchronous requests, a new thread is created.
	 *
	 * @param content POST content or null if there's no such content.
	 * @throws java.lang.Exception if any.
	 */
	public void send(final String content, int timeout) throws Exception {
		final URL url = this.requestURL;
		if (url == null) {
			throw new Exception("No URL has been provided.");
		}
		if (this.isAsync) {
			new Thread("SimpleHttpRequest-" + url.getHost()) {
				@Override
				public void run() {
					try {
						sendSync(content, timeout);
					} catch (final Throwable thrown) {
						logger.log(Level.WARNING, "send(): Error in asynchronous request on " + url, thrown);
					}
				}
			}.start();
		} else {
			sendSync(content, timeout);
		}
	}

	/**
	 * This is a synchronous implementation of send method
	 * functionality. It may be overridden to change the behavior of the class.
	 *
	 * @param content POST content if any. It may be null.
	 * @throws java.lang.Exception if any.
	 */
	private void sendSync(String content, int timeout) throws Exception {
		try {
			changeState(ReadyStateType.LOADING, 0, null, null);
			URLConnection c;
			synchronized (this) {
				c = this.connection;
			}
			c.setRequestProperty("User-Agent", UserAgent.getUserAgent());
			c.getHeaderField("Set-Cookie");

			if (Strings.isNotBlank(requestUserName) && Strings.isNotBlank(requestPassword)) {
				String userpass = requestUserName + ":" + requestPassword;
				String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
				c.setRequestProperty("Authorization", basicAuth);
			}

			c.setConnectTimeout(timeout);
			c.setReadTimeout(timeout);

			int istatus;
			String istatusText;
			if (c instanceof HttpURLConnection) {
				final HttpURLConnection hc = (HttpURLConnection) c;
				String method = this.requestMethod;
				if (method == null) {
					throw new IOException("Null method.");
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
			} else {
				istatus = 0;
				istatusText = "";
			}
			synchronized (this) {
				this.responseHeaders = this.getAllResponseHeaders(c);
				this.responseHeadersMap = c.getHeaderFields();
				changeState(ReadyStateType.HEADERS_RECEIVED, istatus, istatusText, null);
			}

			try (InputStream in = HttpNetwork.openConnectionCheckRedirects(c)) {
				final int contentLength = c.getContentLength();
				final byte[] bytes = IORoutines.load(in, contentLength == -1 ? 4096 : contentLength);
				changeState(ReadyStateType.DONE, istatus, istatusText, bytes);
			}

		} finally {
			synchronized (this) {
				this.connection = null;
			}
		}
	}

	private void changeState(ReadyStateType readyState, int status, String statusMessage, byte[] bytes) {
		synchronized (this) {
			this.readyState = readyState;
			this.status = status;
			this.statusText = statusMessage;
			this.responseBytes = bytes;
		}
		this.readyEvent.fireEvent(null);
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
}
