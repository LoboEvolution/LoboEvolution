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
/*
 * Created on Nov 19, 2005
 */
package org.loboevolution.http;

import lombok.extern.slf4j.Slf4j;
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

/**
 * <p>HttpRequest class.</p>
 */
@Slf4j
public class HttpRequest {

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
	public HttpRequest(final Proxy proxy) {
		this.proxy = proxy;
	}

	/**
	 * <p>abort.</p>
	 */
	public void abort() {
		final URLConnection c;
		synchronized (this) {
			c = this.connection;
		}
		if (c instanceof HttpURLConnection) {
			((HttpURLConnection) c).disconnect();
		} else if (c != null) {
			try {
				c.getInputStream().close();
			} catch (final IOException e) {
				log.error(e.getMessage(), e);
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
	 * @return a {@link java.lang.Integer} object.
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
	public synchronized List<String> getResponseHeader(final String headerName) {
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
			log.warn("getResponseText(): Charset {} did not work. Retrying with ISO-8859-1.", encoding, uee);
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
			final XMLDocumentBuilder builder = new XMLDocumentBuilder();
			return builder.parse(new InputSourceImpl(in, "", StandardCharsets.UTF_8));
		} catch (final Exception err) {
			log.error("Unable to parse response as XML.", err);
			return null;
		}
	}

	/**
	 * <p>Getter for the field status.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
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
	public void open(final String method, final java.net.URL url, final boolean asyncFlag, final String userName) throws IOException {
		this.open(method, url, asyncFlag, userName, null);
	}

	/**
	 * <p>open.</p>
	 *
	 * @param method a {@link java.lang.String} object.
	 * @param url a {@link java.lang.String} object.
	 * @throws java.lang.Exception if any.
	 */
	public void open(final String method, final String url) throws Exception {
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
	public void open(final String method, final String url, final boolean asyncFlag) throws Exception {
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
	public void open(final String method, final URL url) throws Exception {
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
	public void open(final String method, final URL url, final boolean asyncFlag) throws Exception {
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
	public void open(final String method, final URL url, final boolean asyncFlag, final String userName,
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
	public void send(final String content, final int timeout) throws Exception {
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
						log.error("send(): Error in asynchronous request on {} ", url, thrown);
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
	private void sendSync(final String content, final int timeout) throws Exception {
		try {
			changeState(ReadyStateType.LOADING, 0, null, null);
			final URLConnection c;
			synchronized (this) {
				c = this.connection;
			}
			c.setRequestProperty("User-Agent", UserAgent.getUserAgent());
			c.getHeaderField("Set-Cookie");

			if (Strings.isNotBlank(requestUserName) && Strings.isNotBlank(requestPassword)) {
				final String userpass = requestUserName + ":" + requestPassword;
				final String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
				c.setRequestProperty("Authorization", basicAuth);
			}

			c.setConnectTimeout(timeout);
			c.setReadTimeout(timeout);

			final int istatus;
			final String istatusText;
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

			try (final InputStream in = HttpNetwork.openConnectionCheckRedirects(c)) {
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

	private void changeState(final ReadyStateType readyState, final int status, final String statusMessage, final byte[] bytes) {
		synchronized (this) {
			this.readyState = readyState;
			this.status = status;
			this.statusText = statusMessage;
			this.responseBytes = bytes;
		}
		this.readyEvent.fireEvent(null);
	}

	private String getAllResponseHeaders(final URLConnection c) {
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
