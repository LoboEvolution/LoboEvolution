/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.html.js.xml;

import org.loboevolution.common.Urls;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.node.Document;
import org.loboevolution.http.HttpRequest;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.loboevolution.js.JavaScript;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import java.net.URL;
import java.util.List;
import java.util.MissingResourceException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>XMLHttpRequest class.</p>
 */
public class XMLHttpRequest extends AbstractScriptableDelegate {

	private static final Logger logger = Logger.getLogger(XMLHttpRequest.class.getName());
	private boolean listenerAdded;
	private final URL codeSource;
	private Function onreadystatechange;
	private final UserAgentContext pcontext;
	private final HttpRequest request;
	private final Scriptable scope;
	private int timeout = 0;

	/**
	 * <p>Constructor for XMLHttpRequest.</p>
	 *
	 * @param pcontext a {@link org.loboevolution.http.UserAgentContext} object.
	 * @param codeSource a {@link java.net.URL} object.
	 * @param scope a {@link org.mozilla.javascript.Scriptable} object.
	 */
	public XMLHttpRequest(UserAgentContext pcontext, java.net.URL codeSource, Scriptable scope) {
		this.request = pcontext.createHttpRequest();
		this.pcontext = pcontext;
		this.scope = scope;
		this.codeSource = codeSource;
	}

	/**
	 * <p>abort.</p>
	 */
	public void abort() {
		this.request.abort();
	}

	private void executeReadyStateChange() {
		try {
			final Function f = getOnreadystatechange();
			if (f != null) {
				final Context ctx = Executor.createContext(this.codeSource, this.pcontext);
				try {
					final Scriptable newScope = (Scriptable) JavaScript.getInstance().getJavascriptObject(XMLHttpRequest.this, this.scope);
					f.call(ctx, newScope, newScope, new Object[0]);
				} catch (MissingResourceException mre) {
					logger.log(Level.WARNING, mre.getMessage());
				} finally {
					Context.exit();
				}
			}
		} catch (final Exception err) {
			logger.log(Level.WARNING, "Error processing ready state change.", err);
		}
	}

	/**
	 * <p>getAllResponseHeaders.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getAllResponseHeaders() {
		return this.request.getAllResponseHeaders();
	}

	private URL getFullURL(String relativeUrl) throws Exception {
		return Urls.createURL(this.codeSource, relativeUrl);
	}

	/**
	 * <p>Getter for the field onreadystatechange.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getOnreadystatechange() {
		synchronized (this) {
			return this.onreadystatechange;
		}
	}

	/**
	 * <p>getReadyState.</p>
	 *
	 * @return a int.
	 */
	public int getReadyState() {
		return this.request.getReadyState();
	}

	/**
	 * <p>getResponseBytes.</p>
	 *
	 * @return an array of {@link byte} objects.
	 */
	public byte[] getResponseBytes() {
		return this.request.getResponseBytes();
	}

	/**
	 * <p>getResponseHeader.</p>
	 *
	 * @param headerName a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 */
	public List<String> getResponseHeader(String headerName) {
		return this.request.getResponseHeader(headerName);
	}

	/**
	 * <p>getResponseText.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getResponseText() {
		return this.request.getResponseText();
	}

	/**
	 * <p>getResponseXML.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Document} object.
	 */
	public Document getResponseXML() {
		return this.request.getResponseXML();
	}

	/**
	 * <p>getStatus.</p>
	 *
	 * @return a int.
	 */
	public int getStatus() {
		return this.request.getStatus();
	}

	/**
	 * <p>getStatusText.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getStatusText() {
		return this.request.getStatusText();
	}

	/**
	 * <p>open.</p>
	 *
	 * @param method a {@link java.lang.String} object.
	 * @param url a {@link java.lang.String} object.
	 * @throws java.lang.Exception if any.
	 */
	public void open(String method, String url) throws Exception {
		this.request.open(method, getFullURL(url));
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
		this.request.open(method, getFullURL(url), asyncFlag);
	}

	/**
	 * <p>open.</p>
	 *
	 * @param method a {@link java.lang.String} object.
	 * @param url a {@link java.lang.String} object.
	 * @param asyncFlag a boolean.
	 * @param userName a {@link java.lang.String} object.
	 * @throws java.lang.Exception if any.
	 */
	public void open(String method, String url, boolean asyncFlag, String userName) throws Exception {
		this.request.open(method, getFullURL(url), asyncFlag, userName);
	}

	/**
	 * <p>open.</p>
	 *
	 * @param method a {@link java.lang.String} object.
	 * @param url a {@link java.lang.String} object.
	 * @param asyncFlag a boolean.
	 * @param userName a {@link java.lang.String} object.
	 * @param password a {@link java.lang.String} object.
	 * @throws java.lang.Exception if any.
	 */
	public void open(String method, String url, boolean asyncFlag, String userName, String password) throws Exception {
		this.request.open(method, getFullURL(url), asyncFlag, userName, password);
	}

	/**
	 * <p>send.</p>
	 *
	 * @param content a {@link java.lang.String} object.
	 * @throws java.lang.Exception if any.
	 */
	public void send(String content) throws Exception {
		this.request.send(content, timeout);
	}
	
	/**
	 * <p>send.</p>
	 *
	 * @throws java.lang.Exception if any.
	 */
	public void send() throws Exception {
		this.request.send(null, timeout);
	}

	/**
	 * <p>Setter for the field onreadystatechange.</p>
	 *
	 * @param value a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOnreadystatechange(final Function value) {
		synchronized (this) {
			this.onreadystatechange = value;
			if (value != null && !this.listenerAdded) {
				this.request.addReadyStateChangeListener(this::executeReadyStateChange);
				this.listenerAdded = true;
			}
		}
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
