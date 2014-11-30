/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Feb 4, 2006
 */
package org.lobobrowser.context;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;

import org.lobobrowser.clientlet.ClientletAccess;
import org.lobobrowser.clientlet.ClientletContext;
import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.request.RequestEngine;
import org.lobobrowser.request.RequestHandler;
import org.lobobrowser.request.SimpleRequestHandler;
import org.lobobrowser.ua.NavigatorProgressEvent;
import org.lobobrowser.ua.NetworkRequest;
import org.lobobrowser.ua.NetworkRequestEvent;
import org.lobobrowser.ua.NetworkRequestListener;
import org.lobobrowser.ua.ProgressType;
import org.lobobrowser.ua.RequestType;
import org.lobobrowser.util.EventDispatch;
import org.lobobrowser.util.GenericEventListener;
import org.lobobrowser.util.Urls;
import org.w3c.dom.Document;

public class NetworkRequestImpl implements NetworkRequest {
	// TODO: Class not thread safe?
	private static final Logger logger = Logger
			.getLogger(NetworkRequestImpl.class.getName());
	private final EventDispatch READY_STATE_CHANGE = new EventDispatch();
	private volatile int readyState = NetworkRequest.STATE_UNINITIALIZED;
	private volatile LocalResponse localResponse;

	public NetworkRequestImpl() {
	}

	public int getReadyState() {
		return this.readyState;
	}

	public String getResponseText() {
		LocalResponse lr = this.localResponse;
		return lr == null ? null : lr.getResponseText();
	}

	public Document getResponseXML() {
		LocalResponse lr = this.localResponse;
		return lr == null ? null : lr.getResponseXML();
	}

	public Image getResponseImage() {
		LocalResponse lr = this.localResponse;
		return lr == null ? null : lr.getResponseImage();
	}

	// public java.util.jar.JarFile getResponseJarFile() throws
	// java.io.IOException {
	// LocalResponse lr = this.localResponse;
	// return lr == null ? null : lr.getResponseJarFile();
	// }

	public byte[] getResponseBytes() {
		LocalResponse lr = this.localResponse;
		return lr == null ? null : lr.getResponseBytes();
	}

	public int getStatus() {
		try {
			LocalResponse lr = this.localResponse;
			return lr == null ? NetworkRequest.STATE_UNINITIALIZED : lr
					.getStatus();
		} catch (java.io.IOException ioe) {
			return 0;
		}
	}

	public String getStatusText() {
		try {
			LocalResponse lr = this.localResponse;
			return lr == null ? null : lr.getStatusText();
		} catch (java.io.IOException ioe) {
			return null;
		}
	}

	private volatile RequestHandler currentRequestHandler;

	public void abort() {
		RequestHandler rhToDelete = this.currentRequestHandler;
		if (rhToDelete != null) {
			RequestEngine.getInstance().cancelRequest(rhToDelete);
		}
	}

	public String getAllResponseHeaders() {
		LocalResponse lr = this.localResponse;
		return lr == null ? null : lr.getAllResponseHeaders();
	}

	public String getResponseHeader(String headerName) {
		LocalResponse lr = this.localResponse;
		return lr == null ? null : lr.getResponseHeader(headerName);
	}

	public void open(String method, String url) throws IOException {
		this.open(method, url, true);
	}

	public void open(String method, URL url) {
		this.open(method, url, true, null, null);
	}

	public void open(String method, URL url, boolean asyncFlag) {
		this.open(method, url, asyncFlag, null, null);
	}

	public void open(String method, String url, boolean asyncFlag)
			throws IOException {
		URL urlObj = Urls.createURL(null, url);
		this.open(method, urlObj, asyncFlag, null, null);
	}

	public void open(String method, java.net.URL url, boolean asyncFlag,
			String userName) {
		this.open(method, url, asyncFlag, userName, null);
	}

	private boolean isAsynchronous = false;
	private String requestMethod;
	private java.net.URL requestURL;
	private String requestUserName;
	private String requestPassword;

	public void open(String method, java.net.URL url, boolean asyncFlag,
			String userName, String password) {
		this.isAsynchronous = asyncFlag;
		this.requestMethod = method;
		this.requestURL = url;
		this.requestUserName = userName;
		this.requestPassword = password;
		this.changeReadyState(NetworkRequest.STATE_LOADING);
	}

	public void send(String content) throws IOException {
		try {
			RequestHandler rhandler = new LocalRequestHandler(this.requestURL,
					this.requestMethod, content);
			this.currentRequestHandler = rhandler;
			try {
				// TODO: Username and password support
				if (this.isAsynchronous) {
					RequestEngine.getInstance().scheduleRequest(rhandler);
				} else {
					RequestEngine.getInstance().inlineRequest(rhandler);
				}
			} finally {
				this.currentRequestHandler = null;
			}
		} catch (Exception err) {
			logger.log(Level.SEVERE, "open()", err);
		}
	}

	public void addNetworkRequestListener(final NetworkRequestListener listener) {
		this.READY_STATE_CHANGE.addListener(new GenericEventListener() {
			public void processEvent(EventObject event) {
				listener.readyStateChanged((NetworkRequestEvent) event);
			}
		});
	}

	private void changeReadyState(int newState) {
		this.readyState = newState;
		this.READY_STATE_CHANGE.fireEvent(new NetworkRequestEvent(this,
				newState));
	}

	private void setResponse(ClientletResponse response) {
		if (response.isFromCache()) {
			Object cachedResponse = response.getTransientCachedObject();
			if (cachedResponse instanceof CacheableResponse) {
				// It can be of a different type.
				CacheableResponse cr = (CacheableResponse) cachedResponse;
				this.changeReadyState(NetworkRequest.STATE_LOADING);
				this.localResponse = cr.newLocalResponse(response);
				this.changeReadyState(NetworkRequest.STATE_LOADED);
				this.changeReadyState(NetworkRequest.STATE_INTERACTIVE);
				this.changeReadyState(NetworkRequest.STATE_COMPLETE);
				return;
			}
		}
		try {
			this.changeReadyState(NetworkRequest.STATE_LOADING);
			LocalResponse newResponse = new LocalResponse(response);
			this.localResponse = newResponse;
			this.changeReadyState(NetworkRequest.STATE_LOADED);
			int cl = response.getContentLength();
			InputStream in = response.getInputStream();
			int bufferSize = cl == -1 ? 8192 : Math.min(cl, 8192);
			byte[] buffer = new byte[bufferSize];
			int numRead;
			int readSoFar = 0;
			boolean firstTime = true;
			ClientletContext threadContext = ClientletAccess
					.getCurrentClientletContext();
			NavigatorProgressEvent prevProgress = null;
			if (threadContext != null) {
				prevProgress = threadContext.getProgressEvent();
			}
			try {
				long lastProgress = 0;
				while ((numRead = in.read(buffer)) != -1) {
					if (numRead == 0) {
						if (logger.isLoggable(Level.INFO)) {
							logger.info("setResponse(): Read zero bytes from "
									+ response.getResponseURL());
						}
						break;
					}
					readSoFar += numRead;
					if (threadContext != null) {
						long currentTime = System.currentTimeMillis();
						if (currentTime - lastProgress > 500) {
							lastProgress = currentTime;
							threadContext.setProgressEvent(
									ProgressType.CONTENT_LOADING, readSoFar,
									cl, response.getResponseURL());
						}
					}
					newResponse.writeBytes(buffer, 0, numRead);
					if (firstTime) {
						firstTime = false;
						this.changeReadyState(NetworkRequest.STATE_INTERACTIVE);
					}
				}
			} finally {
				if (threadContext != null) {
					threadContext.setProgressEvent(prevProgress);
				}
			}
			newResponse.setComplete(true);
			// The following should return non-null if the response is complete.
			CacheableResponse cacheable = newResponse.getCacheableResponse();
			if (cacheable != null) {
				response.setNewTransientCachedObject(cacheable,
						cacheable.getEstimatedSize());
			}
			this.changeReadyState(NetworkRequest.STATE_COMPLETE);
		} catch (IOException ioe) {
			logger.log(Level.WARNING, "setResponse()", ioe);
			this.localResponse = null;
			this.changeReadyState(NetworkRequest.STATE_COMPLETE);
		}
	}

	private class LocalRequestHandler extends SimpleRequestHandler {
		private final String method;

		public LocalRequestHandler(URL url, String method, String altPostData) {
			super(url, method, altPostData, RequestType.ELEMENT);
			this.method = method;
		}

		@Override
		public String getLatestRequestMethod() {
			return this.method;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.sourceforge.xamj.http.BaseRequestHandler#handleException(java
		 * .net.URL, java.lang.Exception)
		 */
		@Override
		public boolean handleException(ClientletResponse response,
				Throwable exception) throws ClientletException {
			logger.log(Level.WARNING,
					"handleException(): url=" + this.getLatestRequestURL()
							+ ",response=[" + response + "]", exception);
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.sourceforge.xamj.http.BaseRequestHandler#processResponse(org.
		 * xamjwg.clientlet.ClientletResponse)
		 */
		public void processResponse(ClientletResponse response)
				throws ClientletException, IOException {
			NetworkRequestImpl.this.setResponse(response);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.sourceforge.xamj.http.RequestHandler#handleProgress(int,
		 * java.net.URL, int, int)
		 */
		public void handleProgress(
				org.lobobrowser.ua.ProgressType progressType, URL url,
				int value, int max) {
		}
	}

	private static class CacheableResponse {
		private WeakReference<Image> imageRef;
		private java.io.ByteArrayOutputStream buffer;
		private Document document;
		private String textContent;
		private boolean complete;

		public CacheableResponse() {
		}

		public int getEstimatedSize() {
			ByteArrayOutputStream out = this.buffer;
			int factor = 3;
			// Note that when this is called, no one has
			// necessarily called getResponseText().
			return (out == null ? 0 : out.size()) * factor + 512;
		}

		public LocalResponse newLocalResponse(ClientletResponse response) {
			return new LocalResponse(response, this);
		}

		public Image getResponseImage() {
			// A hard reference to the image is not a good idea here.
			// Images will retain their observers, and it's also
			// hard to estimate their actual size.
			WeakReference<Image> imageRef = this.imageRef;
			Image img = imageRef == null ? null : imageRef.get();
			if (img == null && this.complete) {
				byte[] bytes = this.getResponseBytes();
				if (bytes != null) {
					img = Toolkit.getDefaultToolkit().createImage(bytes);
					this.imageRef = new WeakReference<Image>(img);
				}
			}
			return img;
		}

		public String getResponseText(String charset) {
			String responseText = this.textContent;
			if (responseText != null) {
				return responseText;
			}
			byte[] bytes = this.getResponseBytes();
			if (bytes == null) {
				return null;
			}
			try {
				responseText = new String(bytes, charset);
			} catch (UnsupportedEncodingException uee) {
				logger.log(Level.WARNING, "getResponseText()", uee);
				try {
					responseText = new String(bytes, "ISO-8859-1");
				} catch (UnsupportedEncodingException uee2) {
					// ignore
					responseText = null;
				}
			}
			this.textContent = responseText;
			return responseText;
		}

		/**
		 * @return Returns the responseBytes.
		 */
		public byte[] getResponseBytes() {
			ByteArrayOutputStream out = this.buffer;
			return out == null ? null : out.toByteArray();
		}

		public Document getResponseXML() {
			Document doc = this.document;
			if (doc == null && this.complete) {
				byte[] bytes = this.getResponseBytes();
				if (bytes != null) {
					InputStream in = new ByteArrayInputStream(bytes);
					try {
						doc = DocumentBuilderFactory.newInstance()
								.newDocumentBuilder().parse(in);
					} catch (Exception err) {
						logger.log(Level.SEVERE, "getResponseXML()", err);
					}
					this.document = doc;
				}
			}
			return doc;
		}
	}

	private static class LocalResponse {
		private final ClientletResponse cresponse;
		private final CacheableResponse cacheable;

		// Caching fields:
		private Map headers;

		/**
		 * @param status
		 * @param text
		 * @param bytes
		 * @param headers
		 */
		public LocalResponse(ClientletResponse response) {
			this.cresponse = response;
			this.cacheable = new CacheableResponse();
		}

		public LocalResponse(ClientletResponse response,
				CacheableResponse cacheable) {
			this.cresponse = response;
			this.cacheable = cacheable;
		}

		public CacheableResponse getCacheableResponse() {
			CacheableResponse c = this.cacheable;
			if (!c.complete) {
				return null;
			}
			return c;
		}

		public void writeBytes(byte[] bytes, int offset, int length)
				throws java.io.IOException {
			ByteArrayOutputStream out = this.cacheable.buffer;
			if (out == null) {
				out = new ByteArrayOutputStream();
				this.cacheable.buffer = out;
			}
			out.write(bytes, offset, length);
		}

		public void setComplete(boolean complete) {
			this.cacheable.complete = complete;
		}

		public Map getHeaders() {
			Map h = this.headers;
			if (h == null) {
				h = this.getHeadersImpl();
				this.headers = h;
			}
			return h;
		}

		private Map getHeadersImpl() {
			Map<String, String> headers = new HashMap<String, String>();
			ClientletResponse cresponse = this.cresponse;
			Iterator headerNames = cresponse.getHeaderNames();
			while (headerNames.hasNext()) {
				String headerName = (String) headerNames.next();
				if (headerName != null) {
					String[] values = cresponse.getHeaders(headerName);
					if (values != null && values.length > 0) {
						headers.put(headerName.toLowerCase(), values[0]);
					}
				}
			}
			return headers;
		}

		public int getLength() {
			ByteArrayOutputStream out = this.cacheable.buffer;
			return out == null ? 0 : out.size();
		}

		/**
		 * @return Returns the status.
		 */
		public int getStatus() throws IOException {
			return this.cresponse.getResponseCode();
		}

		/**
		 * @return Returns the statusText.
		 */
		public String getStatusText() throws IOException {
			return this.cresponse.getResponseMessage();
		}

		public String getResponseHeader(String headerName) {
			return (String) this.getHeaders().get(headerName.toLowerCase());
		}

		public String getAllResponseHeaders() {
			ClientletResponse cresponse = this.cresponse;
			Iterator headerNames = cresponse.getHeaderNames();
			StringBuffer allHeadersBuf = new StringBuffer();
			while (headerNames.hasNext()) {
				String headerName = (String) headerNames.next();
				if (headerName != null) {
					String[] values = cresponse.getHeaders(headerName);
					for (int i = 0; i < values.length; i++) {
						allHeadersBuf.append(headerName);
						allHeadersBuf.append(": ");
						allHeadersBuf.append(values[i]);
						allHeadersBuf.append("\r\n");
					}
				}
			}
			return allHeadersBuf.toString();
		}

		public String getResponseText() {
			return this.cacheable.getResponseText(this.cresponse.getCharset());
		}

		public Document getResponseXML() {
			return this.cacheable.getResponseXML();
		}

		public Image getResponseImage() {
			return this.cacheable.getResponseImage();
		}

		public byte[] getResponseBytes() {
			// TODO: OPTIMIZATION: When the response comes from the RAM cache,
			// there's no need to build a custom buffer here.
			return this.cacheable.getResponseBytes();
		}
	}
}
