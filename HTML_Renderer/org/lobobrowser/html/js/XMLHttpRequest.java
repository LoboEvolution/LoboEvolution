package org.lobobrowser.html.js;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.html.HttpRequest;
import org.lobobrowser.html.ReadyStateChangeListener;
import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.js.AbstractScriptableDelegate;
import org.lobobrowser.js.JavaScript;
import org.lobobrowser.util.Urls;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.w3c.dom.Document;
import org.w3c.dom.events.EventException;
import org.w3c.dom.events.EventListener;

public class XMLHttpRequest extends AbstractScriptableDelegate {

	private static final Logger logger = Logger.getLogger(XMLHttpRequest.class.getName());
	private final HttpRequest request;
	private final UserAgentContext pcontext;
	private final Scriptable scope;
	private final URL codeSource;
	private Function onreadystatechange;
	private boolean listenerAdded;

	public XMLHttpRequest(UserAgentContext pcontext, URL codeSource, Scriptable scope) {
		this.request = pcontext.createHttpRequest();
		this.pcontext = pcontext;
		this.scope = scope;
		this.codeSource = codeSource;
	}

	public void abort() {
		request.abort();
	}

	public String getAllResponseHeaders() {
		return request.getAllResponseHeaders();
	}

	public int getReadyState() {
		return request.getReadyState();
	}

	public byte[] getResponseBytes() {
		return request.getResponseBytes();
	}

	public void setRequestHeader(String header, String value) {
		request.setRequestHeader(header, value);
	}

	public String getResponseHeader(String headerName) {
		return request.getResponseHeader(headerName);
	}

	public String getResponseText() {
		return request.getResponseText();
	}

	public Document getResponseXML() {
		return request.getResponseXML();
	}

	public int getStatus() {
		return request.getStatus();
	}

	public String getStatusText() {
		return request.getStatusText();
	}

	private URL getFullURL(String relativeUrl)
			throws MalformedURLException {
		return Urls.createURL(this.codeSource, relativeUrl);
	}

	public void open(String method, String url, boolean asyncFlag,
			String userName, String password) throws IOException {
		request.open(method, this.getFullURL(url), asyncFlag, userName,
				password);
	}

	public void open(String method, String url, boolean asyncFlag,
			String userName) throws IOException {
		request.open(method, this.getFullURL(url), asyncFlag, userName);
	}

	public void open(String method, String url, boolean asyncFlag)
			throws IOException {
		request.open(method, this.getFullURL(url), asyncFlag);
	}
	
	public void open(String method, String url) throws IOException {
		request.open(method, this.getFullURL(url));
	}
	
	public void send() throws IOException {
        send((String)null);
    }
	
	public void send(Document data) throws IOException {
        send(data.getTextContent());
    }
	
	public void send(String content) throws IOException {
		request.send(content);
	}

	public Function getOnreadystatechange() {
		synchronized (this) {
			return this.onreadystatechange;
		}
	}
	
	public void addEventListener(String arg0, EventListener arg1, boolean arg2) {
		// TODO Auto-generated method stub
		
	}

	public boolean dispatchEvent(Event arg0) throws EventException {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeEventListener(String arg0, EventListener arg1,boolean arg2) {
		// TODO Auto-generated method stub
		
	}

	public int getTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setTimeout(int timeout) {
		// TODO Auto-generated method stub
		
	}

	public boolean getWithCredentials() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setWithCredentials(boolean withCredentials) {
		// TODO Auto-generated method stub
		
	}
	
	public void overrideMimeType(String mime) {
		// TODO Auto-generated method stub
		
	}

	public byte[] getResponseBody() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setOnreadystatechange(final Function value) {
		synchronized (this) {
			this.onreadystatechange = value;
			if (value != null && !this.listenerAdded) {
				this.request
						.addReadyStateChangeListener(new ReadyStateChangeListener() {
							public void readyStateChanged() {
								// Not called in GUI thread to ensure
								// consistency of readyState.
								executeReadyStateChange();
							}
						});
				this.listenerAdded = true;
			}
		}
	}

	private void executeReadyStateChange() {
		// Not called in GUI thread to ensure consistency of readyState.
		try {
			Function f = XMLHttpRequest.this.getOnreadystatechange();
			if (f != null) {
				Context ctx = Executor.createContext(this.codeSource,
						this.pcontext);
				try {
					Scriptable newScope = (Scriptable) JavaScript.getInstance()
							.getJavascriptObject(XMLHttpRequest.this,
									this.scope);
					f.call(ctx, newScope, newScope, new Object[0]);
				} finally {
					Context.exit();
				}
			}
		} catch (Exception err) {
			logger.log(Level.WARNING, "Error processing ready state change.",
					err);
		}
	}

}
