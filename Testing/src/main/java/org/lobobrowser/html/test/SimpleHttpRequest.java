/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Nov 19, 2005
 */
package org.lobobrowser.html.test;

import java.awt.Image;
import java.awt.Toolkit;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.http.AbstractBean;
import org.lobobrowser.http.Header;
import org.lobobrowser.http.Method;
import org.lobobrowser.http.ReadyState;
import org.lobobrowser.http.Request;
import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.util.SSLCertificate;
import org.lobobrowser.util.Urls;
import org.lobobrowser.util.io.IORoutines;
import org.w3c.dom.Document;

/**
 * The <code>SimpleHttpRequest</code> class implements the
 * {@link org.lobobrowser.http.HttpRequest} interface. The
 * <code>HttpRequest</code> implementation provided by this class is simple,
 * with no caching. It creates a new thread for each new asynchronous request.
 *
 * @author J. H. S.
 */
public class SimpleHttpRequest extends AbstractBean {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(SimpleHttpRequest.class);

	/** The ready state. */
	private ReadyState readyState;

	/** The status. */
	private int status;

	/** The status text. */
	private String statusText;

	/** The response bytes. */
	private byte[] responseBytes;

	/** The context. */
	private final UserAgentContext context;

	/** The proxy. */
	private final Proxy proxy;

	/** The req. */
	private Request req;

	/** The is async. */
	private boolean isAsync;

	/**
	 * The <code>URLConnection</code> is assigned to this field while it is
	 * ongoing.
	 */
	protected URLConnection connection;

	/**
	 * Response headers are set in this map after a response is received.
	 */
	protected Map responseHeadersMap;

	/**
	 * Response headers are set in this string after a response is received.
	 */
	protected String responseHeaders;

	/**
	 * Instantiates a new simple http request.
	 *
	 * @param context
	 *            the context
	 * @param proxy
	 *            the proxy
	 */
	public SimpleHttpRequest(UserAgentContext context, Proxy proxy) {
		super();
		this.context = context;
		this.proxy = proxy;
		req = new Request();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.HttpRequest#getReadyState()
	 */

	/**
	 * Gets the ready state.
	 *
	 * @return the ready state
	 */
	public synchronized ReadyState getReadyState() {
		return this.readyState;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.HttpRequest#getResponseText()
	 */

	/**
	 * Gets the response text.
	 *
	 * @return the response text
	 */
	public synchronized String getResponseText() {
		byte[] bytes = this.responseBytes;
		URLConnection connection = this.connection;
		String encoding = connection == null ? "UTF-8" : Urls.getCharset(connection);
		if (encoding == null) {
			encoding = "UTF-8";
		}
		try {
			return bytes == null ? null : new String(bytes, encoding);
		} catch (UnsupportedEncodingException uee) {
			logger.error("getResponseText(): Charset '" + encoding + "' did not work. Retrying with UTF-8.", uee);
			try {
				return new String(bytes, "UTF-8");
			} catch (UnsupportedEncodingException uee2) {
				// Ignore this time
				return null;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.HttpRequest#getResponseXML()
	 */

	/**
	 * Gets the response xml.
	 *
	 * @return the response xml
	 */
	public synchronized Document getResponseXML() {
		byte[] bytes = this.responseBytes;
		if (bytes == null) {
			return null;
		}
		InputStream in = new ByteArrayInputStream(bytes);
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
		} catch (Exception err) {
			logger.error("Unable to parse response as XML.", err);
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.HttpRequest#getResponseBytes()
	 */

	/**
	 * Gets the response bytes.
	 *
	 * @return the response bytes
	 */
	public synchronized byte[] getResponseBytes() {
		return this.responseBytes;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.HttpRequest#getResponseImage()
	 */

	/**
	 * Gets the response image.
	 *
	 * @return the response image
	 */
	public synchronized Image getResponseImage() {
		byte[] bytes = this.responseBytes;
		if (bytes == null) {
			return null;
		}
		return Toolkit.getDefaultToolkit().createImage(bytes);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.HttpRequest#getStatus()
	 */

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public synchronized int getStatus() {
		return this.status;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.HttpRequest#getStatusText()
	 */

	/**
	 * Gets the status text.
	 *
	 * @return the status text
	 */
	public synchronized String getStatusText() {
		return this.statusText;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.HttpRequest#abort()
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
			} catch (IOException ioe) {
				logger.error(ioe.getMessage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.HttpRequest#getAllResponseHeaders()
	 */

	/**
	 * Gets the all response headers.
	 *
	 * @return the all response headers
	 */
	public synchronized String getAllResponseHeaders() {
		return this.responseHeaders;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.HttpRequest#getResponseHeader(java.lang.String)
	 */

	public synchronized String getResponseHeader(String headerName) {
		Map headers = this.responseHeadersMap;
		return headers == null ? null : (String) headers.get(headerName);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.HttpRequest#open(java.lang.String,
	 * java.net.URL)
	 */

	public void open(Method method, URL url) {
		this.open(method, url, true, null, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.HttpRequest#open(java.lang.String,
	 * java.net.URL, boolean)
	 */

	public void open(Method method, URL url, boolean asyncFlag) {
		this.open(method, url, asyncFlag, null, null);
	}

	/**
	 * Opens the request. Call {@link #send(String)} to complete it.
	 *
	 * @param method
	 *            The request method.
	 * @param url
	 *            The request URL.
	 * @param asyncFlag
	 *            Whether the request should be asynchronous.
	 * @param userName
	 *            The user name of the request (not supported.)
	 * @param password
	 *            The password of the request (not supported.)
	 */

	public void open(final Method method, final URL url, boolean asyncFlag, final String userName,
			final String password) {
		this.abort();
		Proxy proxy = this.proxy;
		SSLCertificate.setCertificate();
		URLConnection c;
		try {
			c = proxy == null || proxy == Proxy.NO_PROXY ? url.openConnection() : url.openConnection(proxy);

			synchronized (this) {
				this.connection = c;
				this.isAsync = asyncFlag;

				req.setUsername(userName);
				req.setPassword(password);
				req.setUrl(url.toString());

				if (method.equals(Method.GET)) {
					req.setMethod(Method.GET);
				} else {
					req.setMethod(Method.POST);
				}
			}
			this.changeState(ReadyState.LOADING, 0, null, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends POST content, if any, and causes the request to proceed.
	 * <p>
	 * In the case of asynchronous requests, a new thread is created.
	 *
	 * @param content
	 *            POST content or <code>null</code> if there's no such content.
	 */

	public void send(final String content) {
		try {
			final URL url = new URL(req.getUrl());

			if (this.isAsync) {
				// Should use a thread pool instead
				new Thread("SimpleHttpRequest-" + url.getHost()) {

					@Override
					public void run() {
						try {
							sendSync(content);
						} catch (Throwable thrown) {
							logger.error("send(): Error in asynchronous request on " + url, thrown);
						}
					}
				}.start();
			} else {
				sendSync(content);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the post charset.
	 *
	 * @return the post charset
	 */
	protected String getPostCharset() {
		return "UTF-8";
	}

	/**
	 * This is a synchronous implementation of {@link #send(String)} method
	 * functionality. It may be overridden to change the behavior of the class.
	 *
	 * @param content
	 *            POST content if any. It may be <code>null</code>.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void sendSync(String content) throws IOException {
		try {
			// FireFox posts a "loading" state twice as well.
			this.changeState(ReadyState.LOADING, 0, null, null);
			URLConnection c;
			synchronized (this) {
				c = this.connection;
			}
			c.setRequestProperty("User-Agent", this.context.getUserAgent());
			int istatus;
			String istatusText;
			InputStream err;
			if (c instanceof HttpURLConnection) {
				HttpURLConnection hc = (HttpURLConnection) c;
				String method = req.getMethod();
				if (method == null) {
					throw new IOException("Null method.");
				}
				method = method.toUpperCase();
				hc.setRequestMethod(method);
				if ("POST".equals(method) && content != null) {
					hc.setDoOutput(true);
					byte[] contentBytes = content.getBytes(this.getPostCharset());
					hc.setFixedLengthStreamingMode(contentBytes.length);
					OutputStream out = hc.getOutputStream();
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
			this.changeState(ReadyState.LOADED, istatus, istatusText, null);
			InputStream in = err == null ? c.getInputStream() : err;
			int contentLength = c.getContentLength();
			this.changeState(ReadyState.INTERACTIVE, istatus, istatusText, null);
			byte[] bytes = IORoutines.load(in, contentLength == -1 ? 4096 : contentLength);
			this.changeState(ReadyState.COMPLETE, istatus, istatusText, bytes);
		} finally {
			synchronized (this) {
				this.connection = null;
			}
		}
	}

	public void addReadyStateChangeListener(PropertyChangeListener listener) {
		super.addPropertyChangeListener("readyState", listener);
	}

	public void removeReadyStateChangeListener(PropertyChangeListener listener) {
		super.removePropertyChangeListener("readyState", listener);
	}

	/**
	 * Gets the ready state change listeners.
	 *
	 * @return the ready state change listeners
	 */
	public PropertyChangeListener[] getReadyStateChangeListeners() {
		return super.getPropertyChangeListeners("readyState");
	}

	/**
	 * Change state.
	 *
	 * @param readyState
	 *            the ready state
	 * @param status
	 *            the status
	 * @param statusMessage
	 *            the status message
	 * @param bytes
	 *            the bytes
	 */
	private void changeState(ReadyState readyState, int status, String statusMessage, byte[] bytes) {
		synchronized (this) {
			this.readyState = readyState;
			this.status = status;
			this.statusText = statusMessage;
			this.responseBytes = bytes;
		}
	}

	/**
	 * Gets the all response headers.
	 *
	 * @param c
	 *            the c
	 * @return the all response headers
	 */
	private String getAllResponseHeaders(URLConnection c) {
		int idx = 0;
		String value;
		StringBuffer buf = new StringBuffer();
		while ((value = c.getHeaderField(idx)) != null) {
			String key = c.getHeaderFieldKey(idx);
			buf.append(key);
			buf.append(": ");
			buf.append(value);
			idx++;
		}
		return buf.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.HttpRequest#setRequestHeader(java.lang.String,
	 * java.lang.String)
	 */

	/**
	 * Specifies a request header for the HTTP request.
	 *
	 * @param header
	 * @param value
	 */
	public void setRequestHeader(String header, String value) {
		if (getReadyState() != ReadyState.LOADING) {
			throw new IllegalStateException(
					"The AsyncHttpRequest must be opened prior to " + "setting a request header");
		}

		if (header == null || value == null) {
			throw new IllegalArgumentException("Neither the header, nor value, may be null");
		}

		if (header.equalsIgnoreCase("Accept-Charset") || header.equalsIgnoreCase("Accept-Encoding")
				|| header.equalsIgnoreCase("Content-Length") || header.equalsIgnoreCase("Expect")
				|| header.equalsIgnoreCase("Date") || header.equalsIgnoreCase("Host")
				|| header.equalsIgnoreCase("Keep-Alive") || header.equalsIgnoreCase("Referer")
				|| header.equalsIgnoreCase("TE") || header.equalsIgnoreCase("Trailer")
				|| header.equalsIgnoreCase("Transfer-Encoding") || header.equalsIgnoreCase("Upgrade")) {

			return;
		}

		if (header.equalsIgnoreCase("Authorization") || header.equalsIgnoreCase("Content-Base")
				|| header.equalsIgnoreCase("Content-Location") || header.equalsIgnoreCase("Content-MD5")
				|| header.equalsIgnoreCase("Content-Range") || header.equalsIgnoreCase("Content-Type")
				|| header.equalsIgnoreCase("Content-Version") || header.equalsIgnoreCase("Delta-Base")
				|| header.equalsIgnoreCase("Depth") || header.equalsIgnoreCase("Destination")
				|| header.equalsIgnoreCase("ETag") || header.equalsIgnoreCase("Expect")
				|| header.equalsIgnoreCase("From") || header.equalsIgnoreCase("If-Modified-Since")
				|| header.equalsIgnoreCase("If-Range") || header.equalsIgnoreCase("If-Unmodified-Since")
				|| header.equalsIgnoreCase("Max-Forwards") || header.equalsIgnoreCase("MIME-Version")
				|| header.equalsIgnoreCase("Overwrite") || header.equalsIgnoreCase("Proxy-Authorization")
				|| header.equalsIgnoreCase("SOAPAction") || header.equalsIgnoreCase("Timeout")) {

			// replace the current header, if any
			for (Header h : req.getHeaders()) {
				if (h.getName().equalsIgnoreCase(header)) {
					req.removeHeader(h);
					req.setHeader(new Header(header, value));
					break;
				}
			}
		} else {
			// append the value to the header, if one is already specified.
			// Else,
			// just add it as a new header
			boolean appended = false;
			for (Header h : req.getHeaders()) {
				if (h.getName().equalsIgnoreCase(header)) {
					req.removeHeader(h);
					req.setHeader(new Header(header, h.getValue() + ", " + value));
					appended = true;
					break;
				}
			}
			if (!appended) {
				req.setHeader(new Header(header, value));
			}
		}
	}
}
