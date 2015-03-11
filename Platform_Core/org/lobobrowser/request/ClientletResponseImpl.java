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
 * Created on Mar 13, 2005
 */
package org.lobobrowser.request;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.ua.ProgressType;
import org.lobobrowser.ua.RequestType;
import org.lobobrowser.util.GenericEventListener;
import org.lobobrowser.util.InputProgressEvent;
import org.lobobrowser.util.MonitoredInputStream;
import org.lobobrowser.util.Strings;
import org.lobobrowser.util.Urls;
import org.lobobrowser.util.io.BufferExceededException;
import org.lobobrowser.util.io.RecordedInputStream;


/**
 * The Class ClientletResponseImpl.
 *
 * @author J. H. S.
 */
public class ClientletResponseImpl implements ClientletResponse {
	
	/** The Constant logger. */
	private static final Logger logger = Logger
			.getLogger(ClientletResponseImpl.class.getName());
	
	/** The Constant MAX_CACHE_BUFFER_SIZE. */
	private static final int MAX_CACHE_BUFFER_SIZE = 10 * 1024 * 1024;

	/** The connection. */
	private final URLConnection connection;
	
	/** The request handler. */
	private final RequestHandler requestHandler;
	
	/** The is cacheable. */
	private final boolean isCacheable;
	
	/** The cache info. */
	private final CacheInfo cacheInfo;
	
	/** The from cache. */
	private final boolean fromCache;
	
	/** The request type. */
	private final RequestType requestType;
	// Security note: This URL must be final.
	/** The response url. */
	private final URL responseURL;

	/** The input stream. */
	private InputStream inputStream;

	/**
	 * Instantiates a new clientlet response impl.
	 *
	 * @param rhandler the rhandler
	 * @param connection the connection
	 * @param responseURL the response url
	 * @param fromCache the from cache
	 * @param cacheInfo the cache info
	 * @param isCacheable the is cacheable
	 * @param requestType the request type
	 */
	public ClientletResponseImpl(RequestHandler rhandler,
			URLConnection connection, URL responseURL, boolean fromCache,
			CacheInfo cacheInfo, boolean isCacheable, RequestType requestType) {
		this.connection = connection;
		this.responseURL = responseURL;
		this.requestHandler = rhandler;
		this.isCacheable = isCacheable;
		this.cacheInfo = cacheInfo;
		this.fromCache = fromCache;
		this.requestType = requestType;
	}

	/**
	 * Instantiates a new clientlet response impl.
	 *
	 * @param rhandler the rhandler
	 * @param url the url
	 * @param fromCache the from cache
	 * @param cacheInfo the cache info
	 * @param isCacheable the is cacheable
	 * @param requestMethod the request method
	 * @param requestType the request type
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public ClientletResponseImpl(RequestHandler rhandler, URL url,
			boolean fromCache, CacheInfo cacheInfo, boolean isCacheable,
			String requestMethod, RequestType requestType) throws IOException {
		this.connection = url.openConnection();
		this.responseURL = url;
		this.requestHandler = rhandler;
		this.isCacheable = isCacheable;
		this.cacheInfo = cacheInfo;
		this.fromCache = fromCache;
		this.requestType = requestType;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletResponse#isNewNavigationAction()
	 */
	public boolean isNewNavigationAction() {
		RequestType rt = this.requestType;
		return rt != RequestType.HISTORY && rt != RequestType.SOFT_RELOAD
				&& rt != RequestType.HARD_RELOAD;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletResponse#matches(java.lang.String, java.lang.String[])
	 */
	public boolean matches(String mimeType, String[] fileExtensions) {
		String responseMimeType = this.getMimeType();
		if (responseMimeType == null
				|| "application/octet-stream"
						.equalsIgnoreCase(responseMimeType)
				|| "content/unknown".equalsIgnoreCase(responseMimeType)) {
			String path = this.responseURL.getPath();
			if (path == null) {
				return false;
			}
			String pathTL = path.toLowerCase();
			for (int i = 0; i < fileExtensions.length; i++) {
				String fileExtensionTL = fileExtensions[i].toLowerCase();
				if (!fileExtensionTL.startsWith(".")) {
					fileExtensionTL = "." + fileExtensionTL;
				}
				if (pathTL.endsWith(fileExtensionTL)) {
					return true;
				}
			}
			return false;
		} else {
			return responseMimeType.equalsIgnoreCase(mimeType);
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletResponse#getLastRequestMethod()
	 */
	public String getLastRequestMethod() {
		return this.requestHandler.getLatestRequestMethod();
	}

	/**
	 * Handle progress.
	 *
	 * @param progressType the progress type
	 * @param url the url
	 * @param method the method
	 * @param value the value
	 * @param max the max
	 */
	public void handleProgress(ProgressType progressType, URL url,
			String method, int value, int max) {
		this.requestHandler.handleProgress(progressType, url, method, value,
				max);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.clientlet.ClientletResponse#isFromCache()
	 */
	public boolean isFromCache() {
		return this.fromCache;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletResponse#isCacheable()
	 */
	public boolean isCacheable() {
		return this.isCacheable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.dom.ClientletResponse#getResponseURL()
	 */
	public URL getResponseURL() {
		// Assumes connection doesn't use internal redirection.
		return this.responseURL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.dom.ClientletResponse#getHeader(String)
	 */
	public String getHeader(String name) {
		return this.connection.getHeaderField(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.dom.ClientletResponse#getHeaders(String, char)
	 */
	public String[] getHeaders(String name) {
		Map<String, List<String>> headers = this.connection.getHeaderFields();
		List<String> valuesList = headers.get(name);
		return valuesList == null ? null : valuesList.toArray(new String[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.dom.ClientletResponse#getHeaderNames()
	 */
	public Iterator getHeaderNames() {
		Map headers = this.connection.getHeaderFields();
		return headers.keySet().iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.dom.ClientletResponse#getInputStream()
	 */
	public InputStream getInputStream() throws IOException {
		if (this.inputStream == null) {
			URLConnection connection = this.connection;
			InputStream in;
			if (connection instanceof HttpURLConnection) {
				in = ((HttpURLConnection) connection).getErrorStream();
				if (in == null) {
					in = connection.getInputStream();
				}
			} else {
				in = connection.getInputStream();
			}
			final int contentLength = connection.getContentLength();
			final int bufferSize = contentLength <= 0 ? 4096 : Math.min(
					contentLength, 8192);
			final URL responseURL = this.getResponseURL();
			// if(logger.isLoggable(Level.INFO))logger.info("getInputStream(): Connection stream is "
			// + in);
			InputStream bis;
			if (this.requestHandler != null) {
				MonitoredInputStream mis = new MonitoredInputStream(in);
				mis.evtProgress.addListener(new GenericEventListener() {
					public void processEvent(EventObject event) {
						InputProgressEvent pe = (InputProgressEvent) event;
						requestHandler
								.handleProgress(
										org.lobobrowser.ua.ProgressType.CONTENT_LOADING,
										responseURL, getLastRequestMethod(),
										pe.getProgress(), contentLength);
					}
				});
				// TODO Buffer size too big if contentLength small
				bis = new BufferedInputStream(mis, bufferSize);
			} else {
				bis = new BufferedInputStream(in, bufferSize);
			}
			if (this.isCacheable) {
				this.inputStream = new RecordedInputStream(bis,
						MAX_CACHE_BUFFER_SIZE);
			} else {
				this.inputStream = bis;
			}
		}
		return this.inputStream;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.dom.ClientletResponse#getContentType()
	 */
	public String getContentType() {
		return this.connection.getContentType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.clientlet.ClientletResponse#getMimeType()
	 */
	public String getMimeType() {
		String contentType = this.getContentType();
		if (contentType == null) {
			return null;
		}
		int scIdx = contentType.indexOf(';');
		return scIdx == -1 ? contentType.trim() : contentType.substring(0,
				scIdx).trim();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletResponse#getContentLength()
	 */
	public int getContentLength() {
		return this.connection.getContentLength();
	}

	/**
	 * Ensure reached eof.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void ensureReachedEOF() throws IOException {
		// Don't get cached inputStream - could be null here.
		InputStream in = this.getInputStream();
		if (in instanceof RecordedInputStream) {
			RecordedInputStream rin = (RecordedInputStream) in;
			if (!rin.hasReachedEOF()) {
				rin.consumeToEOF();
			}
		}
	}

	/**
	 * Gets the stored content.
	 *
	 * @return the stored content
	 */
	public byte[] getStoredContent() {
		// Should call ensureReachedEOF() which will also ensure
		// inputStream is not null.
		InputStream in = this.inputStream;
		if (in instanceof RecordedInputStream) {
			RecordedInputStream rin = (RecordedInputStream) in;
			if (rin.hasReachedEOF()) {
				try {
					return rin.getBytesRead();
				} catch (BufferExceededException bee) {
					logger.warning("getStoredContent(): Recorded stream buffer size exceeded.");
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Gets the default charset.
	 *
	 * @return the default charset
	 */
	private String getDefaultCharset() {
		URL url = this.getResponseURL();
		if (Urls.isLocalFile(url)) {
			String charset = System.getProperty("file.encoding");
			return charset == null ? "ISO-8859-1" : charset;
		} else {
			return "ISO-8859-1";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.clientlet.ClientletResponse#getCharset()
	 */
	public String getCharset() {
		String contentType = this.getContentType();
		if (contentType == null) {
			return this.getDefaultCharset();
		}
		StringTokenizer tok = new StringTokenizer(contentType, ";");
		if (tok.hasMoreTokens()) {
			tok.nextToken();
			while (tok.hasMoreTokens()) {
				String assignment = tok.nextToken().trim();
				int eqIdx = assignment.indexOf('=');
				if (eqIdx != -1) {
					String varName = assignment.substring(0, eqIdx).trim();
					if ("charset".equalsIgnoreCase(varName)) {
						String varValue = assignment.substring(eqIdx + 1);
						return Strings.unquote(varValue.trim());
					}
				}
			}
		}
		return this.getDefaultCharset();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletResponse#isCharsetProvided()
	 */
	public boolean isCharsetProvided() {
		String contentType = this.getContentType();
		if (contentType == null) {
			return false;
		}
		StringTokenizer tok = new StringTokenizer(contentType, ";");
		if (tok.hasMoreTokens()) {
			tok.nextToken();
			while (tok.hasMoreTokens()) {
				String assignment = tok.nextToken().trim();
				int eqIdx = assignment.indexOf('=');
				if (eqIdx != -1) {
					String varName = assignment.substring(0, eqIdx).trim();
					if ("charset".equalsIgnoreCase(varName)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.clientlet.ClientletResponse#getResponseCode()
	 */
	public int getResponseCode() throws IOException {
		if (this.connection instanceof HttpURLConnection) {
			return ((HttpURLConnection) this.connection).getResponseCode();
		} else {
			return 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.clientlet.ClientletResponse#getResponseMessage()
	 */
	public String getResponseMessage() throws IOException {
		if (this.connection instanceof HttpURLConnection) {
			return ((HttpURLConnection) this.connection).getResponseMessage();
		} else {
			return "";
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "ClientletResponseImpl[url=" + this.responseURL + ",method="
				+ this.getLastRequestMethod() + ",mimeType="
				+ this.getMimeType() + ",fromCache=" + this.isFromCache()
				+ ",requestType=" + this.requestType + "]";
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletResponse#getPersistentCachedObject(java.lang.ClassLoader)
	 */
	public Object getPersistentCachedObject(ClassLoader classLoader) {
		CacheInfo cacheInfo = this.cacheInfo;
		return cacheInfo == null ? null : cacheInfo
				.getPersistentObject(classLoader);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletResponse#getTransientCachedObject()
	 */
	public Object getTransientCachedObject() {
		CacheInfo cacheInfo = this.cacheInfo;
		return cacheInfo == null ? null : cacheInfo.getTransientObject();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletResponse#getTransientCachedObjectSize()
	 */
	public int getTransientCachedObjectSize() {
		CacheInfo cacheInfo = this.cacheInfo;
		return cacheInfo == null ? null : cacheInfo.getTransientObjectSize();
	}

	/** The new peristent cached object. */
	private Serializable newPeristentCachedObject;
	
	/** The new transient cached object. */
	private Object newTransientCachedObject;
	
	/** The new transient object size. */
	private int newTransientObjectSize;

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletResponse#setNewPersistentCachedObject(java.io.Serializable)
	 */
	public void setNewPersistentCachedObject(Serializable object) {
		this.newPeristentCachedObject = object;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletResponse#setNewTransientCachedObject(java.lang.Object, int)
	 */
	public void setNewTransientCachedObject(Object object, int approxSize) {
		this.newTransientCachedObject = object;
		this.newTransientObjectSize = approxSize;
	}

	/**
	 * Gets the new persistent cached object.
	 *
	 * @return the new persistent cached object
	 */
	public Serializable getNewPersistentCachedObject() {
		return newPeristentCachedObject;
	}

	/**
	 * Gets the new transient cached object.
	 *
	 * @return the new transient cached object
	 */
	public Object getNewTransientCachedObject() {
		return newTransientCachedObject;
	}

	/**
	 * Gets the new transient object size.
	 *
	 * @return the new transient object size
	 */
	public int getNewTransientObjectSize() {
		return newTransientObjectSize;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletResponse#getDate()
	 */
	public Date getDate() {
		String dateText = this.connection.getHeaderField("Date");
		if (dateText == null) {
			return null;
		}
		try {
			return Urls.PATTERN_RFC1123.parse(dateText);
		} catch (java.text.ParseException pe) {
			logger.warning("getDate(): Bad date '" + dateText + "' from "
					+ this.getResponseURL() + ".");
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletResponse#getRequestType()
	 */
	public RequestType getRequestType() {
		return this.requestType;
	}
}
