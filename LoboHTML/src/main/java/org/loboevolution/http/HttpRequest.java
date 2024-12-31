/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
package org.loboevolution.http;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.EventDispatch;
import org.loboevolution.common.IORoutines;
import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;
import org.loboevolution.html.ReadyStateChangeListener;
import org.loboevolution.html.dom.Blob;
import org.loboevolution.html.dom.nodeimpl.FormDataImpl;
import org.loboevolution.html.js.xml.XMLDocumentBuilder;
import org.loboevolution.html.js.xml.XMLHttpRequestEventTargetImpl;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.FormData;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.net.HttpNetwork;
import org.loboevolution.net.ReadyStateType;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * <p>HttpRequest class.</p>
 */
@Slf4j
public class HttpRequest extends XMLHttpRequestEventTargetImpl {

	private static final String LINE = "\r\n";

	private URLConnection connection;
	private URI requestURI;
	
	@Getter
	@Setter
	private String baseURL;
	private PrintWriter writer;

	private final Proxy proxy;
	private final EventDispatch readyEvent = new EventDispatch();
	private Integer readyState = ReadyStateType.UNSENT.getValue();

	private String requestMethod;
	private String requestPassword;
	private String requestUserName;
	private String responseHeaders;
	private String statusText = "";

	@Getter
	@Setter
    private String mimeType;

	protected Map<String, List<String>> responseHeadersMap = new HashMap<>();
	private byte[] responseBytes;

	private boolean isAsync;

	private int status;

	/**
	 * <p>Constructor for HttpRequest.</p>
	 * @param proxy a {@link java.net.Proxy} object.
	 * @param baseUrl a {@link java.net.URL} object.
	 */
	public HttpRequest(final Proxy proxy, final String baseUrl) {
        super(null);
        this.proxy = proxy;
		setBaseURL(baseUrl);
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
		return bytes == null ? null : new String(bytes, StandardCharsets.UTF_8);
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
	 * @param uri a {@link java.net.URL} object.
	 * @param asyncFlag a boolean.
	 * @param userName a {@link java.lang.String} object.
	 * @throws java.io.IOException if any.
	 */
	public void open(final String method, final URI uri, final boolean asyncFlag, final String userName) throws Exception {
		this.open(method, uri, asyncFlag, userName, null);
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
		URI uri = Urls.createURI(baseURL, url);
		if (uri != null) {
			this.open(method, uri, asyncFlag, null);
		}
	}

	/**
	 * <p>open.</p>
	 *
	 * @param method a {@link java.lang.String} object.
	 * @param uri a {@link java.net.URL} object.
	 * @throws java.lang.Exception if any.
	 */
	public void open(final String method, final URI uri) throws Exception {
		this.open(method, uri, true, null, null);
	}

	/**
	 * <p>open.</p>
	 *
	 * @param method a {@link java.lang.String} object.
	 * @param uri a {@link java.net.URL} object.
	 * @param asyncFlag a boolean.
	 * @throws java.lang.Exception if any.
	 */
	public void open(final String method, final URI uri, final boolean asyncFlag) throws Exception {
		this.open(method, uri, asyncFlag, null, null);
	}

	/**
	 * <p>open.</p>
	 *
	 * @param method a {@link java.lang.String} object.
	 * @param url a {@link java.net.URL} object.
	 * @param async a boolean.
	 * @param username a {@link java.lang.String} object.   
	 * @throws java.lang.Exception if any.
	 */
	public void open(String method, String url, boolean async, String username) throws Exception {
		this.open(method, new URI(url), async, username, null);
	}

	/**
	 * <p>open.</p>
	 *
	 * @param method a {@link java.lang.String} object.
	 * @param url a {@link java.net.URL} object.
	 * @param async a boolean.
	 * @param username a {@link java.lang.String} object.
	 * @param password a {@link java.lang.String} object.
	 * @throws java.lang.Exception if any.
	 */
	public void open(String method, String url, boolean async, String username, String password) throws Exception {
		this.open(method, new URI(url), async, username, password);
	}

	/**
	 * Opens the request. Call send to complete it.
	 *
	 * @param method    The request method.
	 * @param uri       The request URL.
	 * @param asyncFlag Whether the request should be asynchronous.
	 * @param userName  The user name of the request (not supported.)
	 * @param password  The password of the request (not supported.)
	 * @throws java.io.IOException if any.
	 */
	public void open(final String method, final URI uri, final boolean asyncFlag, final String userName,
                     final String password) throws Exception {

		synchronized (this) {
			this.connection = HttpNetwork.getURLConnection(uri,this.proxy, method);
			this.isAsync = asyncFlag;
			this.requestMethod = method;
			this.requestURI = uri;
			this.requestUserName = userName;
			this.requestPassword = password;

			if (getMimeType() != null) {
				connection.setRequestProperty("Content-Type", getMimeType());
			}
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
	public void send(final Object content, final int timeout) throws Exception {
		final URI uri = this.requestURI;
		if (uri == null) {
			throw new Exception("No URL has been provided.");
		}
		if (this.isAsync) {
			new Thread("SimpleHttpRequest-" + uri.getHost()) {
				@Override
				public void run() {
					try {
						sendSync(content, timeout);
					} catch (final Throwable thrown) {
						log.error("send(): Error in asynchronous request on {} ", uri, thrown);
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
     */
	private void sendSync(final Object content, final int timeout) {
		try {
			changeState(ReadyStateType.LOADING, 0, null, null);
			final URLConnection c;
			synchronized (this) {
				c = this.connection;
			}

			if (Strings.isNotBlank(requestUserName) && Strings.isNotBlank(requestPassword)) {
				final String userpass = requestUserName + ":" + requestPassword;
				final String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
				c.setRequestProperty("Authorization", basicAuth);
			}

			c.setConnectTimeout(timeout);
			c.setReadTimeout(timeout);

			if ("POST".equals(requestMethod) && content != null) {
				final URLConnection hc = postURLConnection(content, c);
				ByteArrayOutputStream result = (ByteArrayOutputStream) hc.getOutputStream();
				changeState(ReadyStateType.DONE, HttpURLConnection.HTTP_OK, "", result.toByteArray());
				this.responseHeaders = this.getAllResponseHeaders(c);
				this.responseHeadersMap = c.getHeaderFields();
			}

			try (final InputStream in = HttpNetwork.openConnectionCheckRedirects(c)) {
				final int contentLength = c.getContentLength();
				final byte[] bytes = IORoutines.load(in, contentLength == -1 ? 4096 : contentLength);
				changeState(ReadyStateType.DONE, HttpURLConnection.HTTP_OK, "", bytes);
			}

		} catch (Exception e) {
			log.error("sendSync(): Error send request on {} ", requestURI, e);
		} finally {
			synchronized (this) {
				this.connection = null;
			}
		}
	}

	private URLConnection postURLConnection(Object obj, URLConnection urlConnection) throws IOException {
            if (obj instanceof FormData content) {
                final OutputStream outputStream = urlConnection.getOutputStream();
                writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);

                Iterator<FormDataImpl.Field> it = (Iterator<FormDataImpl.Field>) content.entries();
                while (it.hasNext()) {
                    FormDataImpl.Field field = it.next();
                    String boundary = urlConnection.getRequestProperty("Content-Type").split("boundary=")[1];
                    if (field.getValue() instanceof String) {
                        addFormField(field.getKey(), (String) field.getValue(), boundary);
                        writer.append(",");
                    }

                    if (field.getValue() instanceof Blob) {
                        addFilePart(field.getKey(), (Blob) field.getValue(), field.getFileName(), boundary);
                    }
                }
            }

            if (obj instanceof String content) {
                final byte[] contentBytes = content.getBytes(getPostCharset());
                if (urlConnection instanceof HttpURLConnection hc) {
                    hc.setFixedLengthStreamingMode(contentBytes.length);
                }
                final OutputStream out = urlConnection.getOutputStream();
                try {
                    out.write(contentBytes);
                } finally {
                    out.flush();
                }
            }
		return urlConnection;
	}

	private void addFormField(String name, String value, String boundary) {
		writer.append(name);
		writer.flush();
	}

	private void addFilePart(String fieldName, Blob uploadFile, String fileName, String boundary)
			throws IOException {

		writer.append("--").append(boundary).append(LINE);
		writer.append("Content-Disposition: form-data; name=\"").append(fieldName).append("\"; filename=\"").append(fileName).append("\"").append(LINE);
		writer.append("Content-Type: ").append(URLConnection.guessContentTypeFromName(fileName)).append(LINE);
		writer.append("Content-Transfer-Encoding: binary").append(LINE);
		writer.append(LINE);
		writer.flush();
	}

	private void changeState(final ReadyStateType readyState, final int status, final String statusMessage, final byte[] bytes) {
		synchronized (this) {
			this.readyState = readyState.getValue();
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
