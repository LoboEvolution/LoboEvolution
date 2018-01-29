/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.http;

import java.awt.Image;
import java.awt.Toolkit;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import org.loboevolution.xpath.XPathUtils;
import org.w3c.dom.Document;

/**
 * The Class HttpRequestImpl.
 */
public class HttpRequest extends AbstractBean {
	/**
	 * The ReadyState of this HttpRequestImpl.
	 */
	private ReadyState readyState = ReadyState.UNINITIALIZED;
	/**
	 * If the readyState attribute has a value other than RECEIVING or LOADED,
	 * reponseText MUST be the empty string. Otherwise, it MUST be the fragment
	 * of the entity body received so far (when readyState is RECEIVING) or the
	 * complete entity body (when readyState is LOADED), interpreted as a stream
	 * of characters.
	 */
	private String responseText;
	/**
	 * If the status attribute is not available it MUST raise an exception. It
	 * MUST be available when readyState is RECEIVING or LOADED. When available,
	 * it MUST represent the HTTP status code.
	 */
	private int status;
	/**
	 * If the statusText attribute is not available, it MUST raise an exception.
	 * It MUST be available when readyState is RECEIVING or LOADED. When
	 * available, it MUST represent the HTTP status text sent by the server
	 */
	private String statusText;
	/**
	 * Worker class used to actually perform the background tasks.
	 */
	private AsyncWorker worker;
	/**
	 * Flag used to indicate whether the task should be run asynchronously or
	 * not. This is reset in the open() method.
	 */
	/** The image ref. */
	private WeakReference<Image> imageRef;
	/** The buffer. */
	private ByteArrayOutputStream buffer;

	/** The response xml. */
	private Document responseXML;

	/** The async flag. */
	private boolean asyncFlag;

	/** The s. */
	private Session s;

	/** The exception. */
	private Exception exception;

	/** The on ready state change. */
	private PropertyChangeListener onReadyStateChange;

	/** The req. */
	private Request req = new Request(); // the request. Reset in reset(). Never
	
	/**
	 * Sets the session.
	 *
	 * @param s
	 *            the new session
	 */
	public void setSession(Session s) {
		Session old = getSession();
		this.s = s;
		firePropertyChange("session", old, getSession());
	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	public Session getSession() {
		return s;
	}

	// ------------------------------------ Methods as per the specification
	/**
	 * Sets the on ready state change.
	 *
	 * @param listener
	 *            the new on ready state change
	 */
	public void setOnReadyStateChange(PropertyChangeListener listener) {
		PropertyChangeListener old = getOnReadyStateChange();
		removeReadyStateChangeListener(old);
		addReadyStateChangeListener(listener);
		onReadyStateChange = listener;
		firePropertyChange("onReadyStateChange", old, listener);
	}

	/**
	 * Gets the on ready state change.
	 *
	 * @return the on ready state change
	 */
	public final PropertyChangeListener getOnReadyStateChange() {
		return onReadyStateChange;
	}

	/**
	 * Gets the ReadyState of this HttpRequestImpl.
	 *
	 * @return the ReadyState of this HttpRequestImpl
	 */
	public final ReadyState getReadyState() {
		return readyState;
	}

	/**
	 * Initializes the HttpRequestImpl prior to sending a request.
	 */
	public void open(String method, URL url) {
		open(method, url, true);
	}

	/**
	 * Initializes the HttpRequestImpl prior to sending a request.
	 */
	public void open(String method, URL url, boolean asyncFlag) {
		open(method, url, asyncFlag, null, null);
	}

	/**
	 * Initializes the HttpRequestImpl prior to sending a request. Calling this
	 * method initializes HttpRequestImpl by storing the method, uri, asyncFlag,
	 * userName, and password values. This also sets the readyState to OPEN,
	 * resets the response state to their initial values, and resets the request
	 * headers.
	 *
	 * If open() is called when readyState is LOADED, then the entire object is
	 * reset. (huh?)
	 *
	 * NOTE: Private because authentication is not yet implemented (and indeed,
	 * I don't know yet how to implement it.)
	 *
	 * @param method
	 * @param uri
	 * @param asyncFlag
	 *            Defaults to "true"
	 * @param userName
	 *            Defaults to null
	 * @param password
	 *            Defaults to null
	 */
	public void open(String method, URL url, boolean asyncFlag, String username, String password) {
		if (worker != null && (readyState == ReadyState.SENT || readyState == ReadyState.RECEIVING)) {
			throw new IllegalStateException("You must abort() the current task, or wait for it to "
					+ "finish completing before starting a new one!");
		}
		reset();
		this.asyncFlag = asyncFlag;
		this.req.setUrl(url.toString());
		this.req.setMethod(method);
		req.setUsername(username);
		req.setPassword(password);
		setReadyState(ReadyState.OPEN);
	}

	/**
	 * Specifies a request header for the HTTP request.
	 *
	 * @param header
	 * @param value
	 */
	public void setRequestHeader(String header, String value) {
		if (getReadyState() != ReadyState.OPEN) {
			throw new IllegalStateException(
					"The HttpRequestImpl must be opened prior to " + "setting a request header");
		}
		// TODO
		// if the header argument doesn't match the "field-name production",
		// throw an illegal argument exception
		// if the value argument doesn't match the "field-value production",
		// throw an illegal argument exception
		if (header == null || value == null) {
			throw new IllegalArgumentException("Neither the header, nor value, may be null");
		}
		// NOTE: The spec says, nothing should be done if the header argument
		// matches:
		// Accept-Charset, Accept-Encoding, Content-Length, Expect, Date, Host,
		// Keep-Alive,
		// Referer, TE, Trailer, Transfer-Encoding, Upgrade
		// The spec says this for security reasons, but I don't understand why?
		// I'll follow
		// the spec's suggestion until I know more (can always allow more
		// headers, but
		// restricting them is more painful). Note that Session doesn't impose
		// any such
		// restrictions, so you can always set "Accept-Encoding" etc on the
		// Session...
		// except that Session has no way to set these at the moment, except via
		// a Request.
		if ("Authorization".equalsIgnoreCase(header) 
			|| "Content-Base".equalsIgnoreCase(header)
			|| "Content-Location".equalsIgnoreCase(header)
			|| "Content-MD5".equalsIgnoreCase(header)
			|| "Content-Range".equalsIgnoreCase(header) 
			|| "Content-Type".equalsIgnoreCase(header)
			|| "Content-Version".equalsIgnoreCase(header) 
			|| "Delta-Base".equalsIgnoreCase(header)
			|| "Depth".equalsIgnoreCase(header) 
			|| "Destination".equalsIgnoreCase(header)
			|| "ETag".equalsIgnoreCase(header) 
			|| "Expect".equalsIgnoreCase(header)
			|| "From".equalsIgnoreCase(header) 
			|| "If-Modified-Since".equalsIgnoreCase(header)
			|| "If-Range".equalsIgnoreCase(header) 
			|| "If-Unmodified-Since".equalsIgnoreCase(header)
			|| "Max-Forwards".equalsIgnoreCase(header) 
			|| "MIME-Version".equalsIgnoreCase(header)
			|| "Overwrite".equalsIgnoreCase(header) 
			|| "Proxy-Authorization".equalsIgnoreCase(header)
			|| "SOAPAction".equalsIgnoreCase(header) 
			|| "Timeout".equalsIgnoreCase(header)) {
			
			for (Header h : req.getHeaders()) {
				if (h.getName().equalsIgnoreCase(header)) {
					req.removeHeader(h);
					req.setHeader(new Header(header, value));
					break;
				}
			}
		} else if(!"Accept-Charset".equalsIgnoreCase(header) 
				 && !"Accept-Encoding".equalsIgnoreCase(header)
				 && !"Content-Length".equalsIgnoreCase(header)
				 && !"Expect".equalsIgnoreCase(header)
				 && !"Date".equalsIgnoreCase(header)
				 && !"Host".equalsIgnoreCase(header)
				 && !"Keep-Alive".equalsIgnoreCase(header)
				 && !"Referer".equalsIgnoreCase(header)
				 && !"TE".equalsIgnoreCase(header)
				 && !"Trailer".equalsIgnoreCase(header)
				 && !"Transfer-Encoding".equalsIgnoreCase(header)
				 && !"Upgrade".equalsIgnoreCase(header)){
			
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

	/**
	 * Sends the request to the server. If the readyState property has a value
	 * other than OPEN, then an IllegalStateException will be thrown. At the
	 * beginning of this method the readyState will be set to SENT. If the async
	 * flag is set to false, then the method will not return until the request
	 * has completed (ie: the method will block). Otherwise, a background task
	 * is used and this method will return immediately.
	 *
	 * Note: Authors should specify the Content-Type header via setRequestHeader
	 * before invoking send() with an argument.
	 *
	 * Redirects must be followed.
	 *
	 * Proxies should be supported
	 *
	 * Authentication should be supported.
	 *
	 * State management (cookies)?
	 *
	 * caching(?)
	 *
	 * et etc
	 *
	 * Immediately before receiving the message body (if any), the readyState
	 * attribute will be changed to RECEIVING. When the request has completed
	 * loading, the readyState attribute will be set to LOADED. In case of a
	 * HEAD request, readyState will be set to LOADED immediately after having
	 * been set to RECEIVING.
	 */
	public void send() {
		send((String) null);
	}

	/**
	 * @param content
	 */
	public void send(String content) {
		if (readyState != ReadyState.OPEN) {
			throw new IllegalStateException(
					"HttpRequestImpl must be in an OPEN state before " + "invokation of the send() method");
		}
		worker = createAsyncWorker(content);
		worker.sendRequest(getSession(), content);
	}

	/**
	 * @param dom
	 */
	public void send(Document dom) {
		// convert the dom to a String, and send that
		if (dom == null) {
			send((String) null);
		} else {
			send(XPathUtils.toXML(dom));
		}
	}

	/**
	 * Cancels any network activity and resets the object.
	 */
	public void abort() {
		if (worker != null) {
			worker.cancel(true);
			worker = null;
		}
		reset();
	};

	/**
	 * Gets the all response headers.
	 *
	 * @return the all response headers
	 */
	public String getAllResponseHeaders() {
		if (readyState == ReadyState.RECEIVING || readyState == ReadyState.LOADED) {
			StringBuilder buffer = new StringBuilder();
			for (Header header : worker.response.getHeaders()) {
				buffer.append(header.toString());
				buffer.append("\r\n");
			}
			return buffer.toString();
		} else {
			return null;
		}
	}

	/**
	 * <p>
	 * Gets a single response header as a string.
	 * </p>
	 *
	 * <p>
	 * If the readyState property has a value other than RECEIVING or LOADED,
	 * this method will return null. Otherwise, it will represent the value of
	 * the given HTTP header in the data received so far from the last request
	 * sent, as a single string. If more than one header of the given name was
	 * received, then the values will be concatenated, separated from each other
	 * by a comma followed by a single space. If no headers of that name were
	 * received, then it will return the empty String.
	 * </p>
	 *
	 * @param headerLabel
	 *            the label of the response header to retreive.
	 * @return the response header corrosponding to the provided label
	 */
	public String getResponseHeader(String headerLabel) {
		if (readyState == ReadyState.RECEIVING || readyState == ReadyState.LOADED) {
			Header header = worker.response.getHeader(headerLabel);
			return header == null ? null : header.getValue();
		} else {
			return null;
		}
	}

	/**
	 * Gets the if the readyState attribute has a value other than RECEIVING or
	 * LOADED, reponseText MUST be the empty string.
	 *
	 * @return the if the readyState attribute has a value other than RECEIVING
	 *         or LOADED, reponseText MUST be the empty string
	 */
	public String getResponseText() {
		if (readyState == ReadyState.RECEIVING) {
			return responseText == null ? "" : responseText;
		} else if (readyState == ReadyState.LOADED) {
			return responseText;
		} else {
			return "";
		}
	}

	/**
	 * Gets the if the status attribute is not available it MUST raise an
	 * exception.
	 *
	 * @return the if the status attribute is not available it MUST raise an
	 *         exception
	 */
	public int getStatus() {
		if (readyState == ReadyState.RECEIVING || readyState == ReadyState.LOADED) {
			return status;
		} else {
			throw new IllegalStateException(
					"You cannot call getStatus() unless" + " readyState == RECEIVING || LOADING");
		}
	}

	/**
	 * Gets the if the statusText attribute is not available, it MUST raise an
	 * exception.
	 *
	 * @return the if the statusText attribute is not available, it MUST raise
	 *         an exception
	 */
	public String getStatusText() {
		if (readyState == ReadyState.RECEIVING || readyState == ReadyState.LOADED) {
			return statusText;
		} else {
			throw new IllegalStateException(
					"You cannot call getStatusText() unless" + " readyState == RECEIVING || LOADING");
		}
	}

	// --------------------------- Optional methods as per the specification
	/**
	 * Sets the follows redirects.
	 *
	 * @param flag
	 *            the new follows redirects
	 */
	public void setFollowsRedirects(boolean flag) {
		if (readyState != ReadyState.OPEN) {
			throw new IllegalStateException("The request must be OPEN before setting the follows redirects flag");
		}
		req.setFollowRedirects(flag);
	}

	/**
	 * Gets the follow redirects.
	 *
	 * @return the follow redirects
	 */
	public final boolean getFollowRedirects() {
		return req.getFollowRedirects();
	}
	/*
	 * public void setTimeout(long timeout) { if (readyState != ReadyState.OPEN)
	 * { throw new IllegalStateException(
	 * "The request must be OPEN before setting the timeout"); } timeout =
	 * timeout < 0 ? -1 : timeout; } public final long getTimeout() { return
	 * timeout; }
	 */

	// ------------------------------------------------- Convenience methods
	/**
	 * Gets the exception.
	 *
	 * @return the exception
	 */
	public Exception getException() {
		if (readyState == ReadyState.LOADED) {
			return exception;
		} else {
			return null;
		}
	}

	/**
	 * Returns the Parameter with the given name, or null if there is no such
	 * Parameter. These are reset whenever this HttpRequestImpl is reset.
	 *
	 * @param name
	 *            the name to look for. This must not be null.
	 * @return the Parameter with the given name.
	 */
	public Parameter getParameter(String name) {
		return req.getParameter(name);
	}

	/**
	 * Sets the parameter.
	 *
	 * @param param
	 *            the new parameter
	 */
	public void setParameter(Parameter param) {
		req.setParameter(param);
	}

	/**
	 * Adds the given parameter to the set of parameters. These are reset
	 * whenever this HttpRequestImpl is reset. This is a convenience method.
	 *
	 * @param name
	 *            the name of the parameter
	 * @param value
	 *            the value of the parameter
	 */
	public void setParameter(String name, String value) {
		setParameter(new Parameter(name, value));
	}

	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	public Parameter[] getParameters() {
		return req.getParameters();
	}

	/**
	 * Sets the parameters.
	 *
	 * @param params
	 *            the new parameters
	 */
	public void setParameters(Parameter... params) {
		req.setParameters(params);
	}

	// -------------- Event Listener
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
	/*
	 * public void addAsyncRequestListener(AsyncRequestListener listener) {
	 * listenerList.add(AsyncRequestListener.class, listener); } public void
	 * removeAsyncRequestListener(AsyncRequestListener listener) {
	 * listenerList.remove(AsyncRequestListener.class, listener); } public
	 * AsyncRequestListener[] getAsyncRequestListeners() { return
	 * listenerList.getListeners(AsyncRequestListener.class); } TODO
	 */

	// --------------------------------------------------- Protected Methods
	protected AsyncWorker createAsyncWorker(String content) {
		return new AsyncWorker();
	}

	/**
	 * Clears any response state and resets the readyState to UNINITIALIZED. Any
	 * overriding implementations MUST call super.reset() at the end of the
	 * implementation.
	 */
	protected void reset() {
		/*
		 * userName = null; password = null; timeout = -1;
		 */
		exception = null;
		req = new Request();
		req.setFollowRedirects(false);
		String old = responseText;
		responseText = null;
		firePropertyChange("responseText", old, null);
		setStatus(-1);
		setStatusText(null);
		setResponseXML(null);
		setReadyState(ReadyState.UNINITIALIZED);
	}

	/**
	 * Method that provides a hook for subclasses to create concrete types (such
	 * as DOM, JSONObject, etc) when the response has been fully read. There is
	 * no need to call super.handleResponse(txt).
	 */
	protected void handleResponse(String responseText) throws Exception {
	}

	// ---------------------------------------------- Private helper methods
	/**
	 * Sets the ReadyState of this HttpRequestImpl.
	 *
	 * @param state
	 *            the new ReadyState of this HttpRequestImpl
	 */
	private void setReadyState(ReadyState state) {
		ReadyState old = this.readyState;
		this.readyState = state;
		firePropertyChange("readyState", old, this.readyState);
	}

	/**
	 * Sets the if the status attribute is not available it MUST raise an
	 * exception.
	 *
	 * @param status
	 *            the new if the status attribute is not available it MUST raise
	 *            an exception
	 */
	private void setStatus(int status) {
		int old = this.status;
		this.status = status;
		firePropertyChange("status", old, this.status);
	}

	/**
	 * Sets the if the statusText attribute is not available, it MUST raise an
	 * exception.
	 *
	 * @param text
	 *            the new if the statusText attribute is not available, it MUST
	 *            raise an exception
	 */
	private void setStatusText(String text) {
		String old = this.statusText;
		this.statusText = text;
		firePropertyChange("statusText", old, this.statusText);
	}

	/**
	 * Sets the response xml.
	 *
	 * @param dom
	 *            the new response xml
	 */
	private void setResponseXML(Document dom) {
		Document old = this.responseXML;
		this.responseXML = dom;
		firePropertyChange("responseXML", old, this.responseXML);
	}
	/*
	 * private void fireOnLoadEvent() { for (AsyncRequestListener l :
	 * getAsyncRequestListeners()) { l.onLoad(); } } private void fireOnError()
	 * { for (AsyncRequestListener l : getAsyncRequestListeners()) {
	 * l.onError(); } } private void fireOnError() { for (AsyncRequestListener l
	 * : getAsyncRequestListeners()) { l.onError(); } } private void
	 * fireOnProgress() { for (AsyncRequestListener l :
	 * getAsyncRequestListeners()) { l.onProgress(); } } private void
	 * fireOnAbort() { for (AsyncRequestListener l : getAsyncRequestListeners())
	 * { l.onAbort(); } } private void fireOnTimeout() { for
	 * (AsyncRequestListener l : getAsyncRequestListeners()) { l.onTimeout(); }
	 * TODO }
	 */

	/**
	 * The Class AsyncWorker.
	 */
	// -------------- Private impl details
	protected class AsyncWorker extends SwingWorker<Object, Object> {

		/** The data. */
		private String data;

		/** The s. */
		private Session s;

		/** The response. */
		private Response response;

		private void sendRequest(Session s, String data) {
			this.s = s == null ? new Session() : s;
			safeSetReadyState(ReadyState.SENT);
			this.data = data;
			if (asyncFlag) {
				execute(); // puts on queue, async
			} else {
				run(); // blocks
				done();
			}
		}

		@Override
		protected Object doInBackground() throws Exception {
			try {
				// TODO!!!
				// connection timeout
				// another thread is used (java.util.Timer) to keep track of a
				// timeout.
				// if the timeout occurs, then this thread is cancelled.
				// k. Bundle any data that needs to be sent
				response = null;
				req.setBody(data);
				response = s.execute(req);
				// TODO!!! Need to see if there is a way to set to RECEIVING
				// when the first bit of data
				// comes down, instead of waiting until the whole thing is read
				// (which is what I THINK
				// is happening here).
				safeSetReadyState(ReadyState.RECEIVING);
				// grab the resulting data
				responseText = response.getBody();
				handleResponse(responseText); // causes the cached version of
				// the string to be created
				return responseText;
			} catch (Exception e) {
				exception = e;
				return null;
			}
		}

		/**
		 * Helper method which allows me to set the ready state on the EDT
		 */
		protected void safeSetReadyState(final ReadyState state) {
			if (SwingUtilities.isEventDispatchThread()) {
				HttpRequest.this.setReadyState(state);
			} else {
				SwingUtilities.invokeLater(() -> HttpRequest.this.setReadyState(state));
			}
		}

		@Override
		protected void done() {
			setReadyState(ReadyState.LOADED);
		}
	}

	/**
	 * Gets the response xml.
	 *
	 * @return the response xml
	 */
	public Document getResponseXML() {
		if (getReadyState() == ReadyState.LOADED) {
			return responseXML;
		} else {
			return null;
		}
	}

	/**
	 * Gets the response image.
	 *
	 * @return the response image
	 */
	public Image getResponseImage() {
		// A hard reference to the image is not a good idea here.
		// Images will retain their observers, and it's also
		// hard to estimate their actual size.
		WeakReference<Image> imageRef = this.imageRef;
		Image img = imageRef == null ? null : imageRef.get();
		if (img == null) {
			byte[] bytes = this.getResponseBytes();
			if (bytes != null) {
				img = Toolkit.getDefaultToolkit().createImage(bytes);
				this.imageRef = new WeakReference<Image>(img);
			}
		}
		return img;
	}

	/**
	 * Gets the response bytes.
	 *
	 * @return the response bytes
	 */
	public byte[] getResponseBytes() {
		ByteArrayOutputStream out = this.buffer;
		return out == null ? null : out.toByteArray();
	}
}
