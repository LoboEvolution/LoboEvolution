/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
import org.lobobrowser.http.Method;
import org.lobobrowser.http.Request;
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


/**
 * The Class NetworkRequestImpl.
 */
public class NetworkRequestImpl implements NetworkRequest {
	// TODO: Class not thread safe?
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(NetworkRequestImpl.class.getName());
	
	/** The ready state change. */
	private final EventDispatch READY_STATE_CHANGE = new EventDispatch();
	
	/** The ready state. */
	private volatile int readyState = NetworkRequest.STATE_UNINITIALIZED;
	
	/** The local response. */
	private volatile LocalResponse localResponse;
	
	/** The req. */
	private Request req = new Request();
	
	/** The is asynchronous. */
	private boolean isAsynchronous = false;
	
	/**
	 * Instantiates a new network request impl.
	 */
	public NetworkRequestImpl() {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.ua.NetworkRequest#getReadyState()
	 */
	public int getReadyState() {
		return this.readyState;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.ua.NetworkRequest#getResponseText()
	 */
	public String getResponseText() {
		LocalResponse lr = this.localResponse;
		return lr == null ? null : lr.getResponseText();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.ua.NetworkRequest#getResponseXML()
	 */
	public Document getResponseXML() {
		LocalResponse lr = this.localResponse;
		return lr == null ? null : lr.getResponseXML();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.ua.NetworkRequest#getResponseImage()
	 */
	public Image getResponseImage() {
		LocalResponse lr = this.localResponse;
		return lr == null ? null : lr.getResponseImage();
	}
	
	/* (non-Javadoc)
	 * @see org.lobobrowser.ua.NetworkRequest#getResponseBytes()
	 */
	public byte[] getResponseBytes() {
		LocalResponse lr = this.localResponse;
		return lr == null ? null : lr.getResponseBytes();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.ua.NetworkRequest#getStatus()
	 */
	public int getStatus() {
		try {
			LocalResponse lr = this.localResponse;
			return lr == null ? NetworkRequest.STATE_UNINITIALIZED : lr
					.getStatus();
		} catch (IOException ioe) {
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.ua.NetworkRequest#getStatusText()
	 */
	public String getStatusText() {
		try {
			LocalResponse lr = this.localResponse;
			return lr == null ? null : lr.getStatusText();
		} catch (IOException ioe) {
			return null;
		}
	}

	/** The current request handler. */
	private volatile RequestHandler currentRequestHandler;

	/* (non-Javadoc)
	 * @see org.lobobrowser.ua.NetworkRequest#abort()
	 */
	public void abort() {
		RequestHandler rhToDelete = this.currentRequestHandler;
		if (rhToDelete != null) {
			RequestEngine.getInstance().cancelRequest(rhToDelete);
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.ua.NetworkRequest#getAllResponseHeaders()
	 */
	public String getAllResponseHeaders() {
		LocalResponse lr = this.localResponse;
		return lr == null ? null : lr.getAllResponseHeaders();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.ua.NetworkRequest#getResponseHeader(java.lang.String)
	 */
	public String getResponseHeader(String headerName) {
		LocalResponse lr = this.localResponse;
		return lr == null ? null : lr.getResponseHeader(headerName);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.ua.NetworkRequest#open(java.lang.String, java.lang.String)
	 */
	public void open(String method, String url) throws IOException {
		this.open(method, url, true);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.ua.NetworkRequest#open(java.lang.String, java.net.URL)
	 */
	public void open(String method, URL url) {
		this.open(method, url, true, null, null);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.ua.NetworkRequest#open(java.lang.String, java.net.URL, boolean)
	 */
	public void open(String method, URL url, boolean asyncFlag) {
		this.open(method, url, asyncFlag, null, null);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.ua.NetworkRequest#open(java.lang.String, java.lang.String, boolean)
	 */
	public void open(String method, String url, boolean asyncFlag)
			throws IOException {
		URL urlObj = Urls.createURL(null, url);
		this.open(method, urlObj, asyncFlag, null, null);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.ua.NetworkRequest#open(java.lang.String, java.net.URL, boolean, java.lang.String)
	 */
	public void open(String method, URL url, boolean asyncFlag,
			String userName) {
		this.open(method, url, asyncFlag, userName, null);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.ua.NetworkRequest#open(java.lang.String, java.net.URL, boolean, java.lang.String, java.lang.String)
	 */
	public void open(String method, URL url, boolean asyncFlag, String userName, String password) {
		this.isAsynchronous = asyncFlag;
		
		req.setUsername(userName);
		req.setPassword(password);
		req.setUrl(url.toString());
		
		if(method.equalsIgnoreCase(Method.GET.name()))
			req.setMethod(Method.GET);
		else
			req.setMethod(Method.POST);
		
		this.changeReadyState(NetworkRequest.STATE_LOADING);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.ua.NetworkRequest#send(java.lang.String)
	 */
	public void send(String content) throws IOException {
		try {
			RequestHandler rhandler = new LocalRequestHandler(new URL(req.getUrl()), req.getMethod().name(), content);
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

	/* (non-Javadoc)
	 * @see org.lobobrowser.ua.NetworkRequest#addNetworkRequestListener(org.lobobrowser.ua.NetworkRequestListener)
	 */
	public void addNetworkRequestListener(final NetworkRequestListener listener) {
		this.READY_STATE_CHANGE.addListener(new GenericEventListener() {
			public void processEvent(EventObject event) {
				listener.readyStateChanged((NetworkRequestEvent) event);
			}
		});
	}

	/**
	 * Change ready state.
	 *
	 * @param newState the new state
	 */
	private void changeReadyState(int newState) {
		this.readyState = newState;
		this.READY_STATE_CHANGE.fireEvent(new NetworkRequestEvent(this,
				newState));
	}

	/**
	 * Sets the response.
	 *
	 * @param response the new response
	 */
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
			ClientletContext threadContext = ClientletAccess.getCurrentClientletContext();
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
	
	/**
	 * Gets the req.
	 *
	 * @return the req
	 */
	public Request getReq() {
		return req;
	}

	/**
	 * Sets the req.
	 *
	 * @param req the new req
	 */
	public void setReq(Request req) {
		this.req = req;
	}

	/**
	 * The Class LocalRequestHandler.
	 */
	private class LocalRequestHandler extends SimpleRequestHandler {
		
		/** The method. */
		private final String method;

		/**
		 * Instantiates a new local request handler.
		 *
		 * @param url the url
		 * @param method the method
		 * @param altPostData the alt post data
		 */
		public LocalRequestHandler(URL url, String method, String altPostData) {
			super(url, method, altPostData, RequestType.ELEMENT);
			this.method = method;
		}

		/* (non-Javadoc)
		 * @see org.lobobrowser.request.SimpleRequestHandler#getLatestRequestMethod()
		 */
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
	}

	/**
	 * The Class CacheableResponse.
	 */
	private static class CacheableResponse {
		
		/** The image ref. */
		private WeakReference<Image> imageRef;
		
		/** The buffer. */
		private java.io.ByteArrayOutputStream buffer;
		
		/** The document. */
		private Document document;
		
		/** The text content. */
		private String textContent;
		
		/** The complete. */
		private boolean complete;

		/**
		 * Instantiates a new cacheable response.
		 */
		public CacheableResponse() {
		}

		/**
		 * Gets the estimated size.
		 *
		 * @return the estimated size
		 */
		public int getEstimatedSize() {
			ByteArrayOutputStream out = this.buffer;
			int factor = 3;
			// Note that when this is called, no one has
			// necessarily called getResponseText().
			return (out == null ? 0 : out.size()) * factor + 512;
		}

		/**
		 * New local response.
		 *
		 * @param response the response
		 * @return the local response
		 */
		public LocalResponse newLocalResponse(ClientletResponse response) {
			return new LocalResponse(response, this);
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
			if (img == null && this.complete) {
				byte[] bytes = this.getResponseBytes();
				if (bytes != null) {
					img = Toolkit.getDefaultToolkit().createImage(bytes);
					this.imageRef = new WeakReference<Image>(img);
				}
			}
			return img;
		}

		/**
		 * Gets the response text.
		 *
		 * @param charset the charset
		 * @return the response text
		 */
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
		 * Gets the response bytes.
		 *
		 * @return Returns the responseBytes.
		 */
		public byte[] getResponseBytes() {
			ByteArrayOutputStream out = this.buffer;
			return out == null ? null : out.toByteArray();
		}

		/**
		 * Gets the response xml.
		 *
		 * @return the response xml
		 */
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

	/**
	 * The Class LocalResponse.
	 */
	private static class LocalResponse {
		
		/** The cresponse. */
		private final ClientletResponse cresponse;
		
		/** The cacheable. */
		private final CacheableResponse cacheable;

		// Caching fields:
		/** The headers. */
		private Map headers;

		/**
		 * Instantiates a new local response.
		 *
		 * @param response the response
		 */
		public LocalResponse(ClientletResponse response) {
			this.cresponse = response;
			this.cacheable = new CacheableResponse();
		}

		/**
		 * Instantiates a new local response.
		 *
		 * @param response the response
		 * @param cacheable the cacheable
		 */
		public LocalResponse(ClientletResponse response,
				CacheableResponse cacheable) {
			this.cresponse = response;
			this.cacheable = cacheable;
		}

		/**
		 * Gets the cacheable response.
		 *
		 * @return the cacheable response
		 */
		public CacheableResponse getCacheableResponse() {
			CacheableResponse c = this.cacheable;
			if (!c.complete) {
				return null;
			}
			return c;
		}

		/**
		 * Write bytes.
		 *
		 * @param bytes the bytes
		 * @param offset the offset
		 * @param length the length
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		public void writeBytes(byte[] bytes, int offset, int length)
				throws IOException {
			ByteArrayOutputStream out = this.cacheable.buffer;
			if (out == null) {
				out = new ByteArrayOutputStream();
				this.cacheable.buffer = out;
			}
			out.write(bytes, offset, length);
		}

		/**
		 * Sets the complete.
		 *
		 * @param complete the new complete
		 */
		public void setComplete(boolean complete) {
			this.cacheable.complete = complete;
		}

		/**
		 * Gets the headers.
		 *
		 * @return the headers
		 */
		public Map getHeaders() {
			Map h = this.headers;
			if (h == null) {
				h = this.getHeadersImpl();
				this.headers = h;
			}
			return h;
		}

		/**
		 * Gets the headers impl.
		 *
		 * @return the headers impl
		 */
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

		/**
		 * Gets the status.
		 *
		 * @return Returns the status.
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		public int getStatus() throws IOException {
			return this.cresponse.getResponseCode();
		}

		/**
		 * Gets the status text.
		 *
		 * @return Returns the statusText.
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		public String getStatusText() throws IOException {
			return this.cresponse.getResponseMessage();
		}

		/**
		 * Gets the response header.
		 *
		 * @param headerName the header name
		 * @return the response header
		 */
		public String getResponseHeader(String headerName) {
			return (String) this.getHeaders().get(headerName.toLowerCase());
		}

		/**
		 * Gets the all response headers.
		 *
		 * @return the all response headers
		 */
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

		/**
		 * Gets the response text.
		 *
		 * @return the response text
		 */
		public String getResponseText() {
			return this.cacheable.getResponseText(this.cresponse.getCharset());
		}

		/**
		 * Gets the response xml.
		 *
		 * @return the response xml
		 */
		public Document getResponseXML() {
			return this.cacheable.getResponseXML();
		}

		/**
		 * Gets the response image.
		 *
		 * @return the response image
		 */
		public Image getResponseImage() {
			return this.cacheable.getResponseImage();
		}

		/**
		 * Gets the response bytes.
		 *
		 * @return the response bytes
		 */
		public byte[] getResponseBytes() {
			return this.cacheable.getResponseBytes();
		}
	}
}
