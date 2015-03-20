/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.clientlets.html;

import java.awt.Image;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.lobobrowser.context.NetworkRequestImpl;
import org.lobobrowser.html.HttpRequest;
import org.lobobrowser.html.ReadyStateChangeListener;
import org.lobobrowser.http.Header;
import org.lobobrowser.security.HttpRequestException;
import org.lobobrowser.security.SSLCertificate;
import org.lobobrowser.ua.NetworkRequest;
import org.lobobrowser.ua.NetworkRequestEvent;
import org.lobobrowser.ua.NetworkRequestListener;
import org.w3c.dom.Document;

/**
 * The Class HttpRequestImpl.
 */
public class HttpRequestImpl implements HttpRequest {

	/** The network request. */
	private NetworkRequest networkRequest;

	/** The connection. */
	private HttpURLConnection connection = null;

	/**
	 * Instantiates a new http request impl.
	 *
	 * @param networkRequest
	 *            the network request
	 */
	public HttpRequestImpl(final NetworkRequest networkRequest) {
		super();
		this.networkRequest = networkRequest;
	}

	/*
	 * (non-Javadoc)
	 * @see org.lobobrowser.html.HttpRequest#abort()
	 */
	public void abort() {
		networkRequest.abort();
	}

	/*
	 * (non-Javadoc)
	 * @see org.lobobrowser.html.HttpRequest#getAllResponseHeaders()
	 */
	public String getAllResponseHeaders() {
		return networkRequest.getAllResponseHeaders();
	}

	/*
	 * (non-Javadoc)
	 * @see org.lobobrowser.html.HttpRequest#getReadyState()
	 */
	public int getReadyState() {
		return networkRequest.getReadyState();
	}

	/*
	 * (non-Javadoc)
	 * @see org.lobobrowser.html.HttpRequest#getResponseBytes()
	 */
	public byte[] getResponseBytes() {
		return networkRequest.getResponseBytes();
	}

	/*
	 * (non-Javadoc)
	 * @see org.lobobrowser.html.HttpRequest#getResponseHeader(java.lang.String)
	 */
	public String getResponseHeader(String headerName) {
		return networkRequest.getResponseHeader(headerName);
	}

	/*
	 * (non-Javadoc)
	 * @see org.lobobrowser.html.HttpRequest#getResponseImage()
	 */
	public Image getResponseImage() {
		return networkRequest.getResponseImage();
	}

	/*
	 * (non-Javadoc)
	 * @see org.lobobrowser.html.HttpRequest#getResponseText()
	 */
	public String getResponseText() {
		return networkRequest.getResponseText();
	}

	/*
	 * (non-Javadoc)
	 * @see org.lobobrowser.html.HttpRequest#getResponseXML()
	 */
	public Document getResponseXML() {
		return networkRequest.getResponseXML();
	}

	/*
	 * (non-Javadoc)
	 * @see org.lobobrowser.html.HttpRequest#getStatus()
	 */
	public int getStatus() {
		return networkRequest.getStatus();
	}

	/*
	 * (non-Javadoc)
	 * @see org.lobobrowser.html.HttpRequest#getStatusText()
	 */
	public String getStatusText() {
		return networkRequest.getStatusText();
	}

	/*
	 * (non-Javadoc)
	 * @see org.lobobrowser.html.HttpRequest#open(java.lang.String,
	 * java.lang.String, boolean)
	 */
	public void open(String method, String url, boolean asyncFlag)
			throws IOException {
		networkRequest.open(method, url, asyncFlag);
	}

	/*
	 * (non-Javadoc)
	 * @see org.lobobrowser.html.HttpRequest#open(java.lang.String,
	 * java.lang.String)
	 */
	public void open(String method, String url) throws IOException {
		networkRequest.open(method, url);
	}

	/*
	 * (non-Javadoc)
	 * @see org.lobobrowser.html.HttpRequest#open(java.lang.String,
	 * java.net.URL, boolean, java.lang.String, java.lang.String)
	 */
	public void open(String method, URL url, boolean asyncFlag,
			String userName, String password) throws IOException {
		networkRequest.open(method, url, asyncFlag, userName, password);
	}

	/*
	 * (non-Javadoc)
	 * @see org.lobobrowser.html.HttpRequest#open(java.lang.String,
	 * java.net.URL, boolean, java.lang.String)
	 */
	public void open(String method, URL url, boolean asyncFlag, String userName)
			throws IOException {
		networkRequest.open(method, url, asyncFlag, userName);
	}

	/*
	 * (non-Javadoc)
	 * @see org.lobobrowser.html.HttpRequest#open(java.lang.String,
	 * java.net.URL, boolean)
	 */
	public void open(String method, URL url, boolean asyncFlag)
			throws IOException {
		networkRequest.open(method, url, asyncFlag);
	}

	/*
	 * (non-Javadoc)
	 * @see org.lobobrowser.html.HttpRequest#open(java.lang.String,
	 * java.net.URL)
	 */
	public void open(String method, URL url) throws IOException {
		networkRequest.open(method, url);
	}

	/*
	 * (non-Javadoc)
	 * @see org.lobobrowser.html.HttpRequest#send(java.lang.String)
	 */
	public void send(String content) throws IOException {
		networkRequest.send(content);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.lobobrowser.html.HttpRequest#addReadyStateChangeListener(org.lobobrowser
	 * .html.ReadyStateChangeListener)
	 */
	public void addReadyStateChangeListener(
			final ReadyStateChangeListener listener) {
		networkRequest.addNetworkRequestListener(new NetworkRequestListener() {
			public void readyStateChanged(NetworkRequestEvent event) {
				listener.readyStateChanged();
			}
		});
	}

	/**
	 * create connection
	 *
	 * @return connection
	 */

	private HttpURLConnection createConnection() {

		NetworkRequestImpl request = (NetworkRequestImpl) networkRequest;
		HttpURLConnection connection = null;
		
		try {

			if (request.getReq().getUrl() != null) {
				/*
				 * if (httpProxyHost != null) //TODO connection =
				 * CONNECTION_FACTORY.create(url, createProxy()); else
				 */
				connection = CONNECTION_FACTORY.create(new URL(request.getReq()
						.getUrl()));
				connection
						.setRequestMethod(request.getReq().getMethod().name());
			}
			return connection;
		} catch (IOException e) {
			throw new HttpRequestException(e);
		}
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public HttpURLConnection getConnection() {
		if (connection == null)
			connection = createConnection();
		return connection;
	}

	@Override
	public HttpRequest trustAllCerts() {
		final HttpURLConnection connection = getConnection();
		if (connection instanceof HttpsURLConnection)
			((HttpsURLConnection) connection)
					.setSSLSocketFactory(SSLCertificate.getTrustedFactory());
		return this;
	}

	@Override
	public HttpRequest trustAllHosts() {
		final HttpURLConnection connection = getConnection();
		if (connection instanceof HttpsURLConnection)
			((HttpsURLConnection) connection)
					.setHostnameVerifier(SSLCertificate.getTrustedVerifier());
		return this;
	}

	@Override
	public void setRequestHeader(String header, String value) {

		NetworkRequestImpl nr = new NetworkRequestImpl();

		if (getReadyState() != HttpRequest.STATE_LOADING) {
			throw new IllegalStateException(
					"The AsyncHttpRequest must be opened prior to "
							+ "setting a request header");
		}

		if (header == null || value == null) {
			throw new IllegalArgumentException(
					"Neither the header, nor value, may be null");
		}

		if (header.equalsIgnoreCase("Accept-Charset")
				|| header.equalsIgnoreCase("Accept-Encoding")
				|| header.equalsIgnoreCase("Content-Length")
				|| header.equalsIgnoreCase("Expect")
				|| header.equalsIgnoreCase("Date")
				|| header.equalsIgnoreCase("Host")
				|| header.equalsIgnoreCase("Keep-Alive")
				|| header.equalsIgnoreCase("Referer")
				|| header.equalsIgnoreCase("TE")
				|| header.equalsIgnoreCase("Trailer")
				|| header.equalsIgnoreCase("Transfer-Encoding")
				|| header.equalsIgnoreCase("Upgrade")) {

			return;
		}

		if (header.equalsIgnoreCase("Authorization")
				|| header.equalsIgnoreCase("Content-Base")
				|| header.equalsIgnoreCase("Content-Location")
				|| header.equalsIgnoreCase("Content-MD5")
				|| header.equalsIgnoreCase("Content-Range")
				|| header.equalsIgnoreCase("Content-Type")
				|| header.equalsIgnoreCase("Content-Version")
				|| header.equalsIgnoreCase("Delta-Base")
				|| header.equalsIgnoreCase("Depth")
				|| header.equalsIgnoreCase("Destination")
				|| header.equalsIgnoreCase("ETag")
				|| header.equalsIgnoreCase("Expect")
				|| header.equalsIgnoreCase("From")
				|| header.equalsIgnoreCase("If-Modified-Since")
				|| header.equalsIgnoreCase("If-Range")
				|| header.equalsIgnoreCase("If-Unmodified-Since")
				|| header.equalsIgnoreCase("Max-Forwards")
				|| header.equalsIgnoreCase("MIME-Version")
				|| header.equalsIgnoreCase("Overwrite")
				|| header.equalsIgnoreCase("Proxy-Authorization")
				|| header.equalsIgnoreCase("SOAPAction")
				|| header.equalsIgnoreCase("Timeout")) {

			// replace the current header, if any
			for (Header h : nr.getReq().getHeaders()) {
				if (h.getName().equalsIgnoreCase(header)) {
					nr.getReq().removeHeader(h);
					nr.getReq().setHeader(new Header(header, value));
					break;
				}
			}
		} else {

			boolean appended = false;
			for (Header h : nr.getReq().getHeaders()) {
				if (h.getName().equalsIgnoreCase(header)) {
					nr.getReq().removeHeader(h);
					nr.getReq().setHeader(
							new Header(header, h.getValue() + ", " + value));
					appended = true;
					break;
				}
			}
			if (!appended) {
				nr.getReq().setHeader(new Header(header, value));
			}
		}
	}
}
