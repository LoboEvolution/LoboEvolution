/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.js;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.http.HttpRequest;
import org.lobobrowser.http.ReadyState;
import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.js.AbstractScriptableDelegate;
import org.lobobrowser.js.JavaScript;
import org.lobobrowser.util.Urls;
import org.lobobrowser.w3c.events.Event;
import org.lobobrowser.w3c.events.EventListener;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.w3c.dom.Document;
import org.w3c.dom.events.EventException;

/**
 * The Class XMLHttpRequest.
 */
public class XMLHttpRequest extends AbstractScriptableDelegate {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(XMLHttpRequest.class.getName());

	/** The request. */
	private final HttpRequest request;

	/** The pcontext. */
	private final UserAgentContext pcontext;

	/** The scope. */
	private final Scriptable scope;

	/** The code source. */
	private final URL codeSource;

	/** The onreadystatechange. */
	private Function onreadystatechange;

	/** The listener added. */
	private boolean listenerAdded;

	/**
	 * Instantiates a new XML http request.
	 *
	 * @param pcontext
	 *            the pcontext
	 * @param codeSource
	 *            the code source
	 * @param scope
	 *            the scope
	 */
	public XMLHttpRequest(UserAgentContext pcontext, URL codeSource, Scriptable scope) {
		this.request = pcontext.createHttpRequest();
		this.pcontext = pcontext;
		this.scope = scope;
		this.codeSource = codeSource;
	}

	/**
	 * Abort.
	 */
	public void abort() {
		request.abort();
	}

	/**
	 * Gets the all response headers.
	 *
	 * @return the all response headers
	 */
	public String getAllResponseHeaders() {
		return request.getAllResponseHeaders();
	}

	/**
	 * Gets the ready state.
	 *
	 * @return the ready state
	 */
	public ReadyState getReadyState() {
		return request.getReadyState();
	}

	/**
	 * Gets the response bytes.
	 *
	 * @return the response bytes
	 */
	public byte[] getResponseBytes() {
		return request.getResponseBytes();
	}

	/**
	 * Sets the request header.
	 *
	 * @param header
	 *            the header
	 * @param value
	 *            the value
	 */
	public void setRequestHeader(String header, String value) {
		request.setRequestHeader(header, value);
	}

	/**
	 * Gets the response header.
	 *
	 * @param headerName
	 *            the header name
	 * @return the response header
	 */
	public String getResponseHeader(String headerName) {
		return request.getResponseHeader(headerName);
	}

	/**
	 * Gets the response text.
	 *
	 * @return the response text
	 */
	public String getResponseText() {
		return request.getResponseText();
	}

	/**
	 * Gets the response xml.
	 *
	 * @return the response xml
	 */
	public Document getResponseXML() {
		return request.getResponseXML();
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public int getStatus() {
		return request.getStatus();
	}

	/**
	 * Gets the status text.
	 *
	 * @return the status text
	 */
	public String getStatusText() {
		return request.getStatusText();
	}

	/**
	 * Gets the full url.
	 *
	 * @param relativeUrl
	 *            the relative url
	 * @return the full url
	 * @throws MalformedURLException
	 *             the malformed url exception
	 * @throws UnsupportedEncodingException
	 */
	private URL getFullURL(String relativeUrl) throws MalformedURLException, UnsupportedEncodingException {
		return Urls.createURL(this.codeSource, relativeUrl);
	}

	/**
	 * Open.
	 *
	 * @param method
	 *            the method
	 * @param url
	 *            the url
	 * @param asyncFlag
	 *            the async flag
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void open(String method, String url, boolean asyncFlag, String userName, String password)
			throws IOException {
		request.open(method, this.getFullURL(url), asyncFlag, userName, password);
	}

	/**
	 * Open.
	 *
	 * @param method
	 *            the method
	 * @param url
	 *            the url
	 * @param asyncFlag
	 *            the async flag
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void open(String method, String url, boolean asyncFlag) throws IOException {
		request.open(method, this.getFullURL(url), asyncFlag);
	}

	/**
	 * Open.
	 *
	 * @param method
	 *            the method
	 * @param url
	 *            the url
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void open(String method, String url) throws IOException {
		request.open(method, this.getFullURL(url));
	}

	/**
	 * Send.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void send() throws IOException {
		send((String) null);
	}

	/**
	 * Send.
	 *
	 * @param data
	 *            the data
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void send(Document data) throws IOException {
		send(data.getTextContent());
	}

	/**
	 * Send.
	 *
	 * @param content
	 *            the content
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void send(String content) throws IOException {
		request.send(content);
	}

	/**
	 * Gets the onreadystatechange.
	 *
	 * @return the onreadystatechange
	 */
	public Function getOnreadystatechange() {
		synchronized (this) {
			return this.onreadystatechange;
		}
	}

	/**
	 * Adds the event listener.
	 *
	 * @param arg0
	 *            the arg0
	 * @param arg1
	 *            the arg1
	 * @param arg2
	 *            the arg2
	 */
	public void addEventListener(String arg0, EventListener arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	/**
	 * Dispatch event.
	 *
	 * @param arg0
	 *            the arg0
	 * @return true, if successful
	 * @throws EventException
	 *             the event exception
	 */
	public boolean dispatchEvent(Event arg0) throws EventException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Removes the event listener.
	 *
	 * @param arg0
	 *            the arg0
	 * @param arg1
	 *            the arg1
	 * @param arg2
	 *            the arg2
	 */
	public void removeEventListener(String arg0, EventListener arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the timeout.
	 *
	 * @return the timeout
	 */
	public int getTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Sets the timeout.
	 *
	 * @param timeout
	 *            the new timeout
	 */
	public void setTimeout(int timeout) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the with credentials.
	 *
	 * @return the with credentials
	 */
	public boolean getWithCredentials() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Sets the with credentials.
	 *
	 * @param withCredentials
	 *            the new with credentials
	 */
	public void setWithCredentials(boolean withCredentials) {
		// TODO Auto-generated method stub

	}

	/**
	 * Override mime type.
	 *
	 * @param mime
	 *            the mime
	 */
	public void overrideMimeType(String mime) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the response body.
	 *
	 * @return the response body
	 */
	public byte[] getResponseBody() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Sets the onreadystatechange.
	 *
	 * @param value
	 *            the new onreadystatechange
	 */
	public void setOnreadystatechange(final Function value) {
		synchronized (this) {
			this.onreadystatechange = value;
			if ((value != null) && !this.listenerAdded) {
				this.request.addReadyStateChangeListener(new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent arg0) {
						executeReadyStateChange();

					}
				});
				this.listenerAdded = true;
			}
		}
	}

	/**
	 * Execute ready state change.
	 */
	private void executeReadyStateChange() {
		// Not called in GUI thread to ensure consistency of readyState.
		try {
			Function f = XMLHttpRequest.this.getOnreadystatechange();
			if (f != null) {
				Context ctx = Executor.createContext(this.codeSource, this.pcontext);
				try {
					Scriptable newScope = (Scriptable) JavaScript.getInstance().getJavascriptObject(XMLHttpRequest.this,
							this.scope);
					f.call(ctx, newScope, newScope, new Object[0]);
				} finally {
					Context.exit();
				}
			}
		} catch (Exception err) {
			logger.log(Level.WARNING, "Error processing ready state change.", err);
		}
	}
}
