package org.loboevolution.html.js;

import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.common.Urls;
import org.loboevolution.http.HttpRequest;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.loboevolution.js.JavaScript;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.w3c.dom.Document;

public class XMLHttpRequest extends AbstractScriptableDelegate {
	// TODO: See reference:
	// http://www.xulplanet.com/references/objref/XMLHttpRequest.html

	private static final Logger logger = Logger.getLogger(XMLHttpRequest.class.getName());
	private final java.net.URL codeSource;
	private boolean listenerAdded;
	private Function onreadystatechange;
	private final UserAgentContext pcontext;

	private final HttpRequest request;

	private final Scriptable scope;

	public XMLHttpRequest(UserAgentContext pcontext, java.net.URL codeSource, Scriptable scope) {
		this.request = pcontext.createHttpRequest();
		this.pcontext = pcontext;
		this.scope = scope;
		this.codeSource = codeSource;
	}

	public void abort() {
		this.request.abort();
	}

	private void executeReadyStateChange() {
		// Not called in GUI thread to ensure consistency of readyState.
		try {
			final Function f = XMLHttpRequest.this.getOnreadystatechange();
			if (f != null) {
				final Context ctx = Executor.createContext(this.codeSource, this.pcontext);
				try {
					final Scriptable newScope = (Scriptable) JavaScript.getInstance()
							.getJavascriptObject(XMLHttpRequest.this, this.scope);
					f.call(ctx, newScope, newScope, new Object[0]);
				} finally {
					Context.exit();
				}
			}
		} catch (final Exception err) {
			logger.log(Level.WARNING, "Error processing ready state change.", err);
		}
	}

	public String getAllResponseHeaders() {
		return this.request.getAllResponseHeaders();
	}

	private URL getFullURL(String relativeUrl) throws Exception {
		return Urls.createURL(this.codeSource, relativeUrl);
	}

	public Function getOnreadystatechange() {
		synchronized (this) {
			return this.onreadystatechange;
		}
	}

	public int getReadyState() {
		return this.request.getReadyState();
	}

	public byte[] getResponseBytes() {
		return this.request.getResponseBytes();
	}

	public List<String> getResponseHeader(String headerName) {
		return this.request.getResponseHeader(headerName);
	}

	public String getResponseText() {
		return this.request.getResponseText();
	}

	public Document getResponseXML() {
		return this.request.getResponseXML();
	}

	public int getStatus() {
		return this.request.getStatus();
	}

	public String getStatusText() {
		return this.request.getStatusText();
	}

	public void open(String method, String url) throws Exception {
		this.request.open(method, getFullURL(url));
	}

	public void open(String method, String url, boolean asyncFlag) throws Exception {
		this.request.open(method, getFullURL(url), asyncFlag);
	}

	public void open(String method, String url, boolean asyncFlag, String userName) throws Exception {
		this.request.open(method, getFullURL(url), asyncFlag, userName);
	}

	public void open(String method, String url, boolean asyncFlag, String userName, String password) throws Exception {
		this.request.open(method, getFullURL(url), asyncFlag, userName, password);
	}

	public void send(String content) throws Exception {
		this.request.send(content);
	}

	public void setOnreadystatechange(final Function value) {
		synchronized (this) {
			this.onreadystatechange = value;
			if (value != null && !this.listenerAdded) {
				this.request.addReadyStateChangeListener(() -> executeReadyStateChange());
				this.listenerAdded = true;
			}
		}
	}

}
