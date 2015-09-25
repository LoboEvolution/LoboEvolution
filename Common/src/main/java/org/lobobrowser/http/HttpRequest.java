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
package org.lobobrowser.http;

import java.awt.Image;
import java.awt.Toolkit;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import org.lobobrowser.xpath.XPathUtils;
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
    private Document responseXML;
    private boolean asyncFlag;
    private Session s;
    private Exception exception;
    private PropertyChangeListener onReadyStateChange;
    private Request req = new Request(); // the request. Reset in reset(). Never
    // null.
    /* private EventListenerList listenerList = new EventListenerList(); */

    // -------------------------------------------------------- Constructors
    /**
     * Creates a new instance of HttpRequestImpl.
     */
    public HttpRequest() {
    }

    // -------------------------------------------------------- Bean methods
    /**
     * Sets the {@link org.jdesktop.http.Session} to use with this request.
     *
     * @param s
     *            the Session to use. This may be null. If null, then a default
     *            Session will be created as necessary. This default Session
     *            will NOT be available via the getSession() method, but will be
     *            internal to this implementation.
     */
    public void setSession(Session s) {
        Session old = getSession();
        this.s = s;
        firePropertyChange("session", old, getSession());
    }

    /**
     * Returns the {@link org.jdesktop.http.Session} used with this Request.
     *
     * @return the Session. May be null.
     */
    public Session getSession() {
        return s;
    }

    // ------------------------------------ Methods as per the specification
    /**
     * Sets the listener to use for ready-state change event notification. This
     * is functionally equivilent to adding the listener to the list of
     * listeners via #addReadyStateChangeListener. This method exists soley for
     * compliance with the spec.
     *
     * @param listener
     *            the listener
     */
    public void setOnReadyStateChange(PropertyChangeListener listener) {
        PropertyChangeListener old = getOnReadyStateChange();
        removeReadyStateChangeListener(old);
        addReadyStateChangeListener(listener);
        onReadyStateChange = listener;
        firePropertyChange("onReadyStateChange", old, listener);
    }

    /**
     * Returns the ready-state change listener. This method returns whatever is
     * set via the #setOnReadyStateChange method. If listeners were added via
     * #addReadyStateChangeListener, they are not reflected in this method.
     *
     * @return the onReadyStateChange listener.
     */
    public final PropertyChangeListener getOnReadyStateChange() {
        return onReadyStateChange;
    }

    /**
     * <p>
     * Gets the ready state of this HttpRequestImpl.
     * </p>
     *
     * <p>
     * Ready state will be one of the following values:
     * <ul>
     * <li><b>UNINITIALIZED:</b> The initial value.</li>
     * <li><b>OPEN:</b> The open() method has been successfully called.</li>
     * <li><b>SENT:</b> The UA successfully completed the request, but no data
     * has yet been received.</li>
     * <li><b>RECEIVING:</b> Immediately before receiving the message body (if
     * any). All HTTP headers have been received.</li>
     * <li><b>LOADED:</b> The data transfer has been completed.</li>
     * </ul>
     * </p>
     *
     * @return the readyState property. This will never be null.
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
    public void open(String method, URL url, boolean asyncFlag, String username,
            String password) {
        if (worker != null && (readyState == ReadyState.SENT
                || readyState == ReadyState.RECEIVING)) {
            throw new IllegalStateException(
                    "You must abort() the current task, or wait for it to "
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
                    "The HttpRequestImpl must be opened prior to "
                            + "setting a request header");
        }
        // TODO
        // if the header argument doesn't match the "field-name production",
        // throw an illegal argument exception
        // if the value argument doesn't match the "field-value production",
        // throw an illegal argument exception
        if (header == null || value == null) {
            throw new IllegalArgumentException(
                    "Neither the header, nor value, may be null");
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
            // ignore the header
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
                    req.setHeader(
                            new Header(header, h.getValue() + ", " + value));
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
                    "HttpRequestImpl must be in an OPEN state before "
                            + "invokation of the send() method");
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
     * <p>
     * Gets all of the response headers as one long string.
     * </p>
     *
     * <p>
     * If the readyState property has a value other than RECEIVING or LOADED,
     * this method will return null. Otherwise, it will return all the HTTP
     * headers as a single string, with each header line separated by a CR/LF
     * pair. The status line will not be included.
     * </p>
     *
     * @return the response headers as a single string
     */
    public String getAllResponseHeaders() {
        if (readyState == ReadyState.RECEIVING
                || readyState == ReadyState.LOADED) {
            StringBuffer buffer = new StringBuffer();
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
        if (readyState == ReadyState.RECEIVING
                || readyState == ReadyState.LOADED) {
            Header header = worker.response.getHeader(headerLabel);
            return header == null ? null : header.getValue();
        } else {
            return null;
        }
    }

    /**
     * If the readyState property has a value other than RECEIVING or LOADED,
     * reponseText MUST be the empty string. Otherwise, it MUST be the fragment
     * of the entity body received so far (when readyState is RECEIVING) or the
     * complete entity body (when readyState is LOADED), interpreted as a stream
     * of characters.
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
     * If the status property is not available it MUST raise an exception. It
     * MUST be available when readyState is RECEIVING or LOADED. When available,
     * it MUST represent the HTTP status code.
     */
    public int getStatus() {
        if (readyState == ReadyState.RECEIVING
                || readyState == ReadyState.LOADED) {
            return status;
        } else {
            throw new IllegalStateException("You cannot call getStatus() unless"
                    + " readyState == RECEIVING || LOADING");
        }
    }

    /**
     * If the statusText property is not available, it MUST raise an exception.
     * It MUST be available when readyState is RECEIVING or LOADED. When
     * available, it MUST represent the HTTP status text sent by the server
     */
    public String getStatusText() {
        if (readyState == ReadyState.RECEIVING
                || readyState == ReadyState.LOADED) {
            return statusText;
        } else {
            throw new IllegalStateException(
                    "You cannot call getStatusText() unless"
                            + " readyState == RECEIVING || LOADING");
        }
    }

    // --------------------------- Optional methods as per the specification
    /**
     * Specifies whether the request should automatically follow redirects. By
     * default, this is set to false.
     *
     * @param flag
     *            indicates whether to follow redirects automatically
     */
    public void setFollowsRedirects(boolean flag) {
        if (readyState != ReadyState.OPEN) {
            throw new IllegalStateException(
                    "The request must be OPEN before setting the follows redirects flag");
        }
        req.setFollowRedirects(flag);
    }

    /**
     * Returns true if this request should automatically follow redirects, false
     * otherwise.
     *
     * @return whether to follow redirect requests
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
     * If during the processing of this request an exception occured, then this
     * method will return that Exception. Otherwise, it returns null. It MUST
     * return null if in any state other than LOADED
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
     * Adds the given parameter to the set of parameters. These are reset
     * whenever this HttpRequestImpl is reset.
     *
     * @param param
     *            the Parameter to add. This must not be null.
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
     * Gets an array of all the Parameters for this Request. This array will
     * never be null. Ordering of items is not guaranteed. These are reset
     * whenever this HttpRequestImpl is reset.
     *
     * @return the array of Parameters for this request
     */
    public Parameter[] getParameters() {
        return req.getParameters();
    }

    /**
     * Sets the parameters to use with this Request. This replaces whatever
     * parameters may have been previously defined. If null, this array is
     * treated as an empty array. These are reset whenever this HttpRequestImpl
     * is reset.
     *
     * @param params
     *            the Parameters to set for this Request. May be null.
     */
    public void setParameters(Parameter... params) {
        req.setParameters(params);
    }

    // -------------- Event Listener
    public void addReadyStateChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener("readyState", listener);
    }

    public void removeReadyStateChangeListener(
            PropertyChangeListener listener) {
        super.removePropertyChangeListener("readyState", listener);
    }

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
     * This is private because readyState is a read only property. This method
     * ensures that the readyState property change event is fired. This MUST be
     * called on the EDT.
     */
    private void setReadyState(ReadyState state) {
        ReadyState old = this.readyState;
        this.readyState = state;
        firePropertyChange("readyState", old, this.readyState);
    }

    /**
     * This is private because status is a read only property. This method
     * ensures that the status property change event is fired. This MUST be
     * called on the EDT.
     */
    private void setStatus(int status) {
        int old = this.status;
        this.status = status;
        firePropertyChange("status", old, this.status);
    }

    /**
     * This is private because statusText is a read only property. This method
     * ensures that the statusText property change event is fired. This MUST be
     * called on the EDT.
     */
    private void setStatusText(String text) {
        String old = this.statusText;
        this.statusText = text;
        firePropertyChange("statusText", old, this.statusText);
    }

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

    // -------------- Private impl details
    protected class AsyncWorker extends SwingWorker {
        private String data;
        private Session s;
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
            if (!SwingUtilities.isEventDispatchThread()) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        HttpRequest.this.setReadyState(state);
                    }
                });
            } else {
                HttpRequest.this.setReadyState(state);
            }
        }

        @Override
        protected void done() {
            setReadyState(ReadyState.LOADED);
        }
    }

    /**
     * If the readyState attribute has a value other than LOADED, then this
     * method will return null. Otherwise, if the Content-Type contains
     * text/xml, application/xml, or ends in +xml then a Document will be
     * returned. Otherwise, null is returned.
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

    public byte[] getResponseBytes() {
        ByteArrayOutputStream out = this.buffer;
        return out == null ? null : out.toByteArray();
    }
}
