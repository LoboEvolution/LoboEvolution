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
package org.loboevolution.request;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.async.AsyncResult;
import org.loboevolution.async.AsyncResultImpl;
import org.loboevolution.clientlet.CancelClientletException;
import org.loboevolution.clientlet.ClientletException;
import org.loboevolution.clientlet.ClientletRequest;
import org.loboevolution.clientlet.ClientletResponse;
import org.loboevolution.http.Cookie;
import org.loboevolution.http.Header;
import org.loboevolution.http.NameValuePair;
import org.loboevolution.http.SSLCertificate;
import org.loboevolution.http.Urls;
import org.loboevolution.main.ExtensionManager;
import org.loboevolution.settings.BooleanSettings;
import org.loboevolution.settings.CacheSettings;
import org.loboevolution.settings.ConnectionSettings;
import org.loboevolution.store.CacheManager;
import org.loboevolution.ua.Parameter;
import org.loboevolution.ua.ParameterInfo;
import org.loboevolution.ua.ProgressType;
import org.loboevolution.ua.RequestType;
import org.loboevolution.ua.UserAgent;
import org.loboevolution.util.BoxedObject;
import org.loboevolution.util.ID;
import org.loboevolution.util.Objects;
import org.loboevolution.util.SimpleThreadPool;
import org.loboevolution.util.SimpleThreadPoolTask;
import org.loboevolution.util.Strings;
import org.loboevolution.util.io.Files;
import org.loboevolution.util.io.IORoutines;

/**
 * The Class RequestEngine.
 */
public final class RequestEngine {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(RequestEngine.class);
	
	/** The Constant NORMAL_FORM_ENCODING. */
	private static final String NORMAL_FORM_ENCODING = "application/x-www-form-urlencoded";

	/** The thread pool. */
	private final SimpleThreadPool threadPool;

	/** The processing requests. */
	private final Collection<RequestInfo> processingRequests = new HashSet<RequestInfo>();

	/** The cookie store. */
	private final CookieStore cookieStore = CookieStore.getInstance();

	/** The cache settings. */
	private final CacheSettings cacheSettings;

	/** The boolean settings. */
	private final BooleanSettings booleanSettings;

	/** The connection settings. */
	private final ConnectionSettings connectionSettings;
	
	/** The Constant instance. */
	private static final RequestEngine instance = new RequestEngine();

	/**
	 * Instantiates a new request engine.
	 */
	private RequestEngine() {
		// Use few threads to avoid excessive parallelism. Note that
		// downloads are not handled by this thread pool.
		this.threadPool = new SimpleThreadPool("RequestEngineThreadPool", 3, 5, 60 * 1000);

		// Security: Private fields that require privileged access to get
		// initialized.
		this.cacheSettings = CacheSettings.getInstance();
		this.connectionSettings = ConnectionSettings.getInstance();
		this.booleanSettings = BooleanSettings.getInstance();
	}


	/**
	 * Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
	public static RequestEngine getInstance() {
		return instance;
	}

	/**
	 * Gets the cookie.
	 *
	 * @param url
	 *            the url
	 * @return the cookie
	 */
	public String getCookie(URL url) {
		Collection<?> cookies = this.cookieStore.getCookies(url.getHost(), url.getPath());
		StringBuilder cookieText = new StringBuilder();
		Iterator<?> i = cookies.iterator();
		while (i.hasNext()) {
			Cookie cookie = (Cookie) i.next();
			cookieText.append(cookie.getName());
			cookieText.append('=');
			cookieText.append(cookie.getValue());
			cookieText.append(';');
		}
		// Note: Return blank if there are no cookies, not null.
		return cookieText.toString();
	}

	/**
	 * Sets the cookie.
	 *
	 * @param url
	 *            the url
	 * @param cookieSpec
	 *            the cookie spec
	 */
	public void setCookie(URL url, String cookieSpec) {
		this.cookieStore.saveCookie(url, cookieSpec);
	}

	/**
	 * Sets the cookie.
	 *
	 * @param urlHostName
	 *            the url host name
	 * @param cookieSpec
	 *            the cookie spec
	 */
	public void setCookie(String urlHostName, String cookieSpec) {
		this.cookieStore.saveCookie(urlHostName, cookieSpec);
	}

	/**
	 * Cancel all requests.
	 */
	public void cancelAllRequests() {
		this.threadPool.cancelAll();
	}

	/**
	 * Cancel request.
	 *
	 * @param rhToDelete
	 *            the rh to delete
	 */
	public void cancelRequest(RequestHandler rhToDelete) {
		this.threadPool.cancel(new RequestHandlerTask(rhToDelete));
		this.cancelRequestIfRunning(rhToDelete);
	}

	/**
	 * Cancel request if running.
	 *
	 * @param rhToDelete
	 *            the rh to delete
	 */
	public void cancelRequestIfRunning(RequestHandler rhToDelete) {
		rhToDelete.cancel();
		List<RequestInfo> handlersToCancel = new ArrayList<RequestInfo>();
		synchronized (this.processingRequests) {
			Iterator<RequestInfo> ri = this.processingRequests.iterator();
			while (ri.hasNext()) {
				RequestInfo rinfo = ri.next();
				if (rinfo.getRequestHandler() == rhToDelete) {
					handlersToCancel.add(rinfo);
				}
			}
		}
		Iterator<RequestInfo> ri2 = handlersToCancel.iterator();
		while (ri2.hasNext()) {
			RequestInfo rinfo = ri2.next();
			rinfo.abort();
		}
	}

	/**
	 * Schedule request.
	 *
	 * @param handler
	 *            the handler
	 */
	public void scheduleRequest(RequestHandler handler) {
		// Note: Important to create task with current access context if there's
		// a security manager.
		SecurityManager sm = System.getSecurityManager();
		AccessControlContext context = sm == null ? null : AccessController.getContext();
		this.threadPool.schedule(new RequestHandlerTask(handler, context));
	}

	/**
	 * Post data.
	 *
	 * @param connection
	 *            the connection
	 * @param pinfo
	 *            the pinfo
	 * @param altPostData
	 *            the alt post data
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void postData(URLConnection connection, ParameterInfo pinfo) throws IOException {
		BooleanSettings boolSettings = this.booleanSettings;
		String encoding = pinfo.getEncoding();
		if (encoding == null || NORMAL_FORM_ENCODING.equalsIgnoreCase(encoding)) {
			ByteArrayOutputStream bufOut = new ByteArrayOutputStream();
			Parameter[] parameters = pinfo.getParameters();
			boolean firstParam = true;
			for (Parameter parameter : parameters) {
				String name = parameter.getName();
				String encName = URLEncoder.encode(name, "UTF-8");
				if (parameter.isText()) {
					if (firstParam) {
						firstParam = false;
					} else {
						bufOut.write((byte) '&');
					}
					String valueStr = parameter.getTextValue();
					String encValue = URLEncoder.encode(valueStr, "UTF-8");
					bufOut.write(encName.getBytes("UTF-8"));
					bufOut.write((byte) '=');
					bufOut.write(encValue.getBytes("UTF-8"));
				} else {
					logger.warn("postData(): Ignoring non-textual parameter " + name + " for POST with encoding "
							+ encoding + ".");
				}
			}
			// Do not add a line break to post content. Some servers
			// can be picky about that (namely, java.net).
			byte[] postContent = bufOut.toByteArray();
			if (connection instanceof HttpURLConnection) {
				if (boolSettings.isHttpUseChunkedEncodingPOST()) {
					((HttpURLConnection) connection).setChunkedStreamingMode(8192);
				} else {
					((HttpURLConnection) connection).setFixedLengthStreamingMode(postContent.length);
				}
			}
			connection.setRequestProperty("Content-Type", NORMAL_FORM_ENCODING);
			// connection.setRequestProperty("Content-Length",
			// String.valueOf(postContent.length));
			OutputStream postOut = connection.getOutputStream();
			postOut.write(postContent);
			postOut.flush();
		} else if ("multipart/form-data".equalsIgnoreCase(encoding)) {
			long id = ID.generateLong();
			String boundary = "----------------" + id;
			boolean chunked = boolSettings.isHttpUseChunkedEncodingPOST();
			OutputStream mfstream;
			if (chunked) {
				mfstream = connection.getOutputStream();
			} else {
				mfstream = new ByteArrayOutputStream();
			}
			MultipartFormDataWriter writer = new MultipartFormDataWriter(mfstream, boundary);
			try {
				if (pinfo != null) {
					Parameter[] parameters = pinfo.getParameters();
					for (Parameter parameter : parameters) {
						String name = parameter.getName();
						if (parameter.isText()) {
							writer.writeText(name, parameter.getTextValue(), "UTF-8");
						} else if (parameter.isFile()) {
							File[] file = parameter.getFileValue();

							for (File element : file) {

								FileInputStream in = new FileInputStream(element);
								try {
									BufferedInputStream bin = new BufferedInputStream(in, 8192);
									writer.writeFileData(name, element.getName(), Files.getContentType(element), bin);
								} finally {
									in.close();
								}
							}
						} else {
							logger.warn("postData(): Skipping parameter " + name
									+ " of unknown type for POST with encoding " + encoding + ".");
						}
					}
				}
			} finally {
				writer.send();
			}
			connection.addRequestProperty("Content-Type", encoding + "; boundary=" + boundary);
			if (chunked) {
				if (connection instanceof HttpURLConnection) {
					((HttpURLConnection) connection).setChunkedStreamingMode(8192);
				}
			} else {
				byte[] content = ((ByteArrayOutputStream) mfstream).toByteArray();
				if (connection instanceof HttpURLConnection) {
					((HttpURLConnection) connection).setFixedLengthStreamingMode(content.length);
				}
				OutputStream out = connection.getOutputStream();
				out.write(content);
			}
		} else {
			throw new IllegalArgumentException("Unknown encoding: " + encoding);
		}
	}

	/**
	 * Complete get url.
	 *
	 * @param baseURL
	 *            the base url
	 * @param pinfo
	 *            the pinfo
	 * @param ref
	 *            the ref
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	private String completeGetUrl(String baseURL, ParameterInfo pinfo, String ref) throws Exception {
		String newNoRefURL;
		Parameter[] parameters = pinfo.getParameters();
		if (parameters != null && parameters.length > 0) {
			StringBuilder sb = new StringBuilder(baseURL);
			int qmIdx = baseURL.indexOf('?');
			char separator = qmIdx == -1 ? '?' : '&';
			for (Parameter parameter : parameters) {
				if (parameter.isText()) {
					sb.append(separator);
					sb.append(parameter.getName());
					sb.append('=');
					String paramText = parameter.getTextValue();
					sb.append(URLEncoder.encode(paramText, "UTF-8"));
					separator = '&';
				} else {
					logger.warn("completeGetUrl(): Skipping non-textual parameter " + parameter.getName()
							+ " in GET request.");
				}
			}
			newNoRefURL = sb.toString();
		} else {
			newNoRefURL = baseURL;
		}
		if (!Strings.isBlank(ref)) {
			return newNoRefURL + "#" + ref;
		} else {
			return newNoRefURL;
		}
	}

	/**
	 * Adds the request properties.
	 *
	 * @param connection
	 *            the connection
	 * @param request
	 *            the request
	 * @param cacheInfo
	 *            the cache info
	 * @param requestMethod
	 *            the request method
	 * @throws ProtocolException
	 *             the protocol exception
	 */
	private void addRequestProperties(URLConnection connection, ClientletRequest request, CacheInfo cacheInfo, String requestMethod) throws ProtocolException {
		UserAgent userAgent = request.getUserAgent();
		connection.addRequestProperty("User-Agent", userAgent.toString());
		connection.addRequestProperty("X-Java-Version", userAgent.getJavaVersion());
		connection.setRequestProperty("Accept-Encoding", UserAgentContext.GZIP_ENCODING);
		// TODO: Commenting out X-Session-ID. Needs to be privately generated
		// or available with the right permissions only. Extensions should not
		// have access to the private field. This is not doable if extensions
		// should have a permission to open up access to any member.
		//
		// connection.addRequestProperty("X-Session-ID",
		// userAgent.getSessionID(connection.getURL()));
		String referrer = request.getReferrer();
		if (referrer != null) {
			connection.addRequestProperty("Referer", referrer);
		}
		if (cacheInfo != null) {
			String date = cacheInfo.getDateAsText();
			if (date != null) {
				connection.addRequestProperty("If-Modified-Since", date);
			}
		}
		if (connection instanceof HttpURLConnection) {
			HttpURLConnection hconnection = (HttpURLConnection) connection;
			hconnection.setRequestMethod(requestMethod);
		}
		Header[] headers = request.getExtraHeaders();
		if (headers != null) {
			for (Header header : headers) {
				String headerName = header.getName();
				if (headerName.startsWith("X-")) {
					connection.addRequestProperty(headerName, header.getValue());
				} else {
					logger.warn("run(): Ignoring request header: " + headerName);
				}
			}
		}
	}

	/**
	 * Gets the cache info.
	 *
	 * @param rhandler
	 *            the rhandler
	 * @param url
	 *            the url
	 * @return the cache info
	 * @throws Exception
	 *             the exception
	 */
	private CacheInfo getCacheInfo(final URL url) throws Exception {
		return AccessController.doPrivileged((PrivilegedAction<CacheInfo>) () -> {
			MemoryCacheEntry entry;
			byte[] persistentContent = null;
			CacheManager cm = CacheManager.getInstance();
			entry = (MemoryCacheEntry) cm.getTransient(url);
			if (entry == null && (!"file".equalsIgnoreCase(url.getProtocol()) || !Strings.isBlank(url.getHost()))) {
				try {
					persistentContent = cm.getPersistent(url, false);
				} catch (IOException ioe) {
					logger.error("getCacheInfo(): Unable to load cache file.", ioe);
				}
			}
			if (persistentContent == null && entry == null) {
				return null;
			}
			CacheInfo cinfo = new CacheInfo(entry, persistentContent, url);
			return cinfo;
		});
	}

	/**
	 * Cache.
	 *
	 * @param url
	 *            the url
	 * @param connection
	 *            the connection
	 * @param content
	 *            the content
	 * @param altPersistentObject
	 *            the alt persistent object
	 * @param altObject
	 *            the alt object
	 * @param approxAltObjectSize
	 *            the approx alt object size
	 */
	private void cache(final URL url, final URLConnection connection,
			final byte[] content, final Serializable altPersistentObject, final Object altObject,
			final int approxAltObjectSize) {
		AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
			try {
				long currentTime = System.currentTimeMillis();
				int actualApproxObjectSize = 0;
				if (altObject != null) {
					if (approxAltObjectSize < content.length) {
						actualApproxObjectSize = content.length;
					} else {
						actualApproxObjectSize = approxAltObjectSize;
					}
				}
				Long expiration = Urls.getExpiration(connection, currentTime);
				List<NameValuePair> headers = Urls.getHeaders(connection);
				MemoryCacheEntry memEntry = new MemoryCacheEntry(content, headers, expiration, altObject,
						actualApproxObjectSize);
				int approxMemEntrySize = content.length + (altObject == null ? 0 : approxAltObjectSize);
				CacheManager cm = CacheManager.getInstance();
				cm.putTransient(url, memEntry, approxMemEntrySize);
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				try {
					boolean hadDate = false;
					boolean hadContentLength = false;
					for (int counter = 0; true; counter++) {
						String headerKey = connection.getHeaderFieldKey(counter);
						if (headerKey != null) {
							if (!hadDate && "date".equalsIgnoreCase(headerKey)) {
								hadDate = true;
							}
							if (!hadContentLength && "content-length".equalsIgnoreCase(headerKey)) {
								hadContentLength = true;
							}
						}
						String headerValue = connection.getHeaderField(counter);
						if (headerValue == null) {
							break;
						}
						if (CacheInfo.HEADER_REQUEST_TIME.equalsIgnoreCase(headerKey)) {
							continue;
						}
						String headerPrefix = Strings.isBlank(headerKey) ? "" : headerKey + ": ";
						byte[] headerBytes1 = (headerPrefix + headerValue + "\r\n").getBytes("UTF-8");
						out.write(headerBytes1);
					}
					if (!hadDate) {
						String currentDate = Urls.PATTERN_RFC1123.format(new Date());
						byte[] headerBytes2 = ("Date: " + currentDate + "\r\n").getBytes("UTF-8");
						out.write(headerBytes2);
					}
					if (!hadContentLength) {
						byte[] headerBytes3 = ("Content-Length: " + content.length + "\r\n").getBytes("UTF-8");
						out.write(headerBytes3);
					}
					byte[] rtHeaderBytes = (CacheInfo.HEADER_REQUEST_TIME + ": " + currentTime + "\r\n")
							.getBytes("UTF-8");
					out.write(rtHeaderBytes);
					out.write(IORoutines.LINE_BREAK_BYTES);
					out.write(content);
				} finally {
					out.close();
				}
				try {
					cm.putPersistent(url, out.toByteArray(), false);
				} catch (Exception err1) {
					logger.error("cache(): Unable to cache response content.", err1);
				}
				if (altPersistentObject != null) {
					try {
						ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
						// No need to buffer - Java API already does.
						ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
						objOut.writeObject(altPersistentObject);
						objOut.flush();
						byte[] byteArray = fileOut.toByteArray();
						if (byteArray.length == 0) {
							logger.error("cache(): Serialized content has zero bytes for persistent object "
									+ altPersistentObject + ".");
						}
						cm.putPersistent(url, byteArray, true);
					} catch (Exception err2) {
						logger.error("cache(): Unable to write persistent cached object.", err2);
					}
				}
			} catch (Exception err3) {
				logger.error("cache()", err3);
			}
			return null;
		});
	}

	/**
	 * May be cached.
	 *
	 * @param connection
	 *            the connection
	 * @return true, if successful
	 */
	private boolean mayBeCached(HttpURLConnection connection) {
		String cacheControl = connection.getHeaderField("Cache-Control");
		if (cacheControl != null) {
			StringTokenizer tok = new StringTokenizer(cacheControl, ",");
			while (tok.hasMoreTokens()) {
				String token = tok.nextToken().trim();
				if ("no-cache".equalsIgnoreCase(token)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Prints the request headers.
	 *
	 * @param connection
	 *            the connection
	 */
	private void printRequestHeaders(URLConnection connection) {
		Map<String, List<String>> headers = connection.getRequestProperties();
		StringBuilder buffer = new StringBuilder();
		for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
			buffer.append(entry.getKey() + ": " + entry.getValue());
			buffer.append(System.getProperty("line.separator"));
		}
	}

	/**
	 * Inline request.
	 *
	 * @param rhandler
	 *            the rhandler
	 */
	public void inlineRequest(RequestHandler rhandler) {
		// Security checked by low-level APIs in this case.
		this.processHandler(rhandler, 0, false);
	}

	/**
	 * Load bytes.
	 *
	 * @param urlOrPath
	 *            the url or path
	 * @return the byte[]
	 * @throws Exception
	 *             the exception
	 */
	public byte[] loadBytes(String urlOrPath) throws Exception {
		return this.loadBytes(Urls.guessURL(urlOrPath));
	}

	/**
	 * Load bytes.
	 *
	 * @param url
	 *            the url
	 * @return the byte[]
	 * @throws Exception
	 *             the exception
	 */
	public byte[] loadBytes(final URL url) throws Exception {
		final BoxedObject boxed = new BoxedObject();
		this.inlineRequest(new SimpleRequestHandler(url, RequestType.ELEMENT) {
			@Override
			public boolean handleException(ClientletResponse response, Throwable exception) throws ClientletException {
				if (exception instanceof ClientletException) {
					throw (ClientletException) exception;
				} else {
					throw new ClientletException(exception);
				}
			}

			@Override
			public void processResponse(ClientletResponse response) throws ClientletException, IOException {
				byte[] bytes = IORoutines.load(response.getInputStream(), 4096);
				boxed.setObject(bytes);
			}
		});
		return (byte[]) boxed.getObject();
	}

	/**
	 * Load bytes async.
	 *
	 * @param urlOrPath
	 *            the url or path
	 * @return the async result
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	public AsyncResult<byte[]> loadBytesAsync(final String urlOrPath) throws MalformedURLException {
		return this.loadBytesAsync(Urls.guessURL(urlOrPath));
	}

	/**
	 * Load bytes async.
	 *
	 * @param url
	 *            the url
	 * @return the async result
	 */
	public AsyncResult<byte[]> loadBytesAsync(final URL url) {
		final AsyncResultImpl<byte[]> asyncResult = new AsyncResultImpl<byte[]>();
		this.scheduleRequest(new SimpleRequestHandler(url, RequestType.ELEMENT) {
			@Override
			public boolean handleException(ClientletResponse response, Throwable exception) throws ClientletException {
				asyncResult.setException(exception);
				return true;
			}

			@Override
			public void processResponse(ClientletResponse response) throws ClientletException, IOException {
				byte[] bytes = IORoutines.load(response.getInputStream(), 4096);
				asyncResult.setResult(bytes);
			}
		});
		return asyncResult;
	}

	/**
	 * Whether possibly cached request should always be revalidated, i.e. any
	 * expiration information is ignored.
	 *
	 * @param requestType
	 *            the request type
	 * @return true, if successful
	 */
	private static boolean shouldRevalidateAlways(RequestType requestType) {
		return requestType == RequestType.ADDRESS_BAR;
	}

	/**
	 * Whether the request type should always be obtained from cache if it is
	 * there.
	 *
	 * @param requestType
	 *            the request type
	 * @return true, if successful
	 */
	private static boolean doesNotExpire(RequestType requestType) {
		return requestType == RequestType.HISTORY;
	}

	/**
	 * Gets the safe extension manager.
	 *
	 * @return the safe extension manager
	 */
	private ExtensionManager getSafeExtensionManager() {
		return AccessController.doPrivileged((PrivilegedAction<ExtensionManager>) () -> ExtensionManager.getInstance());
	}

	/**
	 * Gets the URL connection.
	 *
	 * @param connectionUrl
	 *            the connection url
	 * @param request
	 *            the request
	 * @param protocol
	 *            the protocol
	 * @param method
	 *            the method
	 * @param rhandler
	 *            the rhandler
	 * @param cacheInfo
	 *            the cache info
	 * @return the URL connection
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private URLConnection getURLConnection(URL connectionUrl, ClientletRequest request, String protocol, String method,
			RequestHandler rhandler, CacheInfo cacheInfo) throws IOException {
		URLConnection connection;
		if (cacheInfo != null) {
			RequestType requestType = rhandler.getRequestType();
			if (doesNotExpire(requestType)) {
				return cacheInfo.getURLConnection();
			} else if (!shouldRevalidateAlways(requestType)) {
				Long expires = cacheInfo.getExpires();
				if (expires == null) {
					Integer defaultOffset = this.cacheSettings.getDefaultCacheExpirationOffset();
					if (defaultOffset != null) {
						expires = cacheInfo.getExpiresGivenOffset(defaultOffset.longValue());
					}
				}
				if (expires != null) {
					if (expires.longValue() > System.currentTimeMillis()) {
						return cacheInfo.getURLConnection();
					}
				}
				// If the document has expired, the cache may still
				// be used, but only after validation.
			}
		}
		boolean isPost = "POST".equalsIgnoreCase(method);
		final String host = connectionUrl.getHost();
		boolean isResURL = "res".equalsIgnoreCase(protocol);
		SSLCertificate.setCertificate();
		if (isResURL || Strings.isBlank(host)) {
			connection = connectionUrl.openConnection();
		} else {
			Proxy proxy = this.connectionSettings.getProxy(host);
			if (proxy == Proxy.NO_PROXY) {
				// Workaround for JRE 1.5.0.
				connection = connectionUrl.openConnection();
			} else {
				connection = connectionUrl.openConnection(proxy);
			}
		}
		if (connection instanceof HttpsURLConnection) {
			((HttpsURLConnection) connection).setHostnameVerifier(rhandler.getHostnameVerifier());
		}
		if (isPost) {
			connection.setDoOutput(true);
		}
		connection.setUseCaches(false);
		if (connection instanceof HttpURLConnection) {
			HttpURLConnection hconnection = (HttpURLConnection) connection;
			hconnection.setConnectTimeout(60000);
			hconnection.setReadTimeout(90000);
		}
		this.addRequestProperties(connection, request, cacheInfo, method);
		// Allow extensions to modify the connection object.
		// Doing it after addRequestProperties() to allow such
		// functionality as altering the Accept header.
		connection = this.getSafeExtensionManager().dispatchPreConnection(connection);
		// Print request headers
		if (logger.isInfoEnabled()) {
			this.printRequestHeaders(connection);
		}
		// POST data if we need to.
		if (isPost) {
			ParameterInfo pinfo = rhandler instanceof RedirectRequestHandler ? null : request.getParameterInfo();
			if (pinfo == null) {
				throw new IllegalStateException("POST has no parameter information");
			}
			this.postData(connection, pinfo);
		}
		return connection;
	}

	/**
	 * Checks if is OK to retrieve from cache.
	 *
	 * @param requestType
	 *            the request type
	 * @return true, if is OK to retrieve from cache
	 */
	private static boolean isOKToRetrieveFromCache(RequestType requestType) {
		return requestType != RequestType.SOFT_RELOAD && requestType != RequestType.HARD_RELOAD
				&& requestType != RequestType.DOWNLOAD;
	}

	/**
	 * Process handler.
	 *
	 * @param rhandler
	 *            the rhandler
	 * @param recursionLevel
	 *            the recursion level
	 * @param trackRequestInfo
	 *            the track request info
	 */
	private void processHandler(final RequestHandler rhandler, final int recursionLevel,
			final boolean trackRequestInfo) {
		URL baseURL = rhandler.getLatestRequestURL();
		RequestInfo rinfo = null;
		ClientletResponseImpl response = null;
		String method = null;
		try {
			ClientletRequest request = rhandler.getRequest();
			method = rhandler.getLatestRequestMethod().toUpperCase();
			// TODO: Hack: instanceof below
			ParameterInfo pinfo = rhandler instanceof RedirectRequestHandler ? null : request.getParameterInfo();
			boolean isGet = "GET".equals(method);
			URL url;
			if (isGet && pinfo != null) {
				String ref = baseURL.getRef();
				String noRefForm = Urls.getNoRefForm(baseURL);
				String newURLText = completeGetUrl(noRefForm, pinfo, ref);
				url = new URL(newURLText);
			} else {
				url = baseURL;
			}
			CacheInfo cacheInfo = null;
			String protocol = url.getProtocol();
			final URL connectionUrl;
			if (url.getQuery() != null && "file".equalsIgnoreCase(protocol)) {
				// Remove query (replace file with path) if "file" protocol.
				String ref = url.getRef();
				String refText = Strings.isBlank(ref) ? "" : "#" + ref;
				connectionUrl = new URL(protocol, url.getHost(), url.getPort(), url.getPath() + refText);
			} else {
				connectionUrl = url;
			}
			RequestType requestType = rhandler.getRequestType();
			if (isGet && isOKToRetrieveFromCache(requestType)) {
				cacheInfo = this.getCacheInfo(connectionUrl);
			}
			try {

				if (!"http:".equals(connectionUrl.toString())) {
					SSLCertificate.setCertificate();
					URLConnection connection = this.getURLConnection(connectionUrl, request, protocol, method, rhandler, cacheInfo);

					String redirect = connection.getHeaderField("Location");
					if (redirect != null) {
						connection = new URL(redirect).openConnection();
					}

					rinfo = new RequestInfo(connection, rhandler);
					if (trackRequestInfo) {
						synchronized (this.processingRequests) {
							this.processingRequests.add(rinfo);
						}
					}
					try {
						if (rhandler.isCancelled()) {
							throw new CancelClientletException("cancelled");
						}
						rhandler.handleProgress(ProgressType.CONNECTING, url, method, 0, -1);
						// Handle response
						boolean isContentCached = cacheInfo != null && cacheInfo.isCacheConnection(connection);
						boolean isCacheable = false;

						Integer responseCode = null;
						HttpURLConnection hconnection = null;
						if (connection instanceof HttpsURLConnection && !isContentCached) {
							hconnection = (HttpsURLConnection) connection;
							hconnection.setInstanceFollowRedirects(true);
							responseCode = hconnection.getResponseCode();
						} else if (connection instanceof HttpURLConnection && !isContentCached) {
							hconnection = (HttpURLConnection) connection;
							hconnection.setInstanceFollowRedirects(false);
							responseCode = hconnection.getResponseCode();
						}
						
						if(responseCode != null) {
							
							switch (responseCode) {
							case HttpURLConnection.HTTP_OK:
								if (this.mayBeCached(hconnection)) {
									isCacheable = true;
								} else {
									if (cacheInfo != null) {
										cacheInfo.delete();
									}
								}
								rinfo.setConnection(connection);
								break;
							case HttpURLConnection.HTTP_NOT_MODIFIED:
								if (cacheInfo == null) {
									String error = "Cache info missing but it is necessary to process response code " + responseCode + ".";
									throw new IllegalStateException(error);
								}
								
								hconnection.disconnect();
								isContentCached = true;
								isCacheable = true;
								connection = cacheInfo.getURLConnection();
								rinfo.setConnection(connection);
								break;
							case HttpURLConnection.HTTP_MOVED_PERM:
							case HttpURLConnection.HTTP_MOVED_TEMP:
							case HttpURLConnection.HTTP_SEE_OTHER:
								RequestHandler newHandler = new RedirectRequestHandler(rhandler, hconnection);
								Thread.yield();
								if (recursionLevel > 5) {
									throw new ClientletException("Exceeded redirect recursion limit.");
								}
								this.processHandler(newHandler, recursionLevel + 1, trackRequestInfo);
								return;
							default:
								break;
							}
						} else {
							rinfo.setConnection(connection);
						}
						
						if (rinfo.isAborted()) {
							throw new CancelClientletException("Stopped");
						}
						// Give a change to extensions to post-process the
						// connection.
						URLConnection newConnection = this.getSafeExtensionManager().dispatchPostConnection(connection);
						if (!Objects.equals(newConnection, connection)) {
							connection = newConnection;
						}
						// Create clientlet response.
						response = new ClientletResponseImpl(rhandler, connection, url, isContentCached, cacheInfo,
								isCacheable, rhandler.getRequestType());
						rhandler.processResponse(response);
						if (isCacheable) {
							// Make sure stream reaches EOF so we don't
							// get null stored content.
							response.ensureReachedEOF();
							byte[] content = response.getStoredContent();
							if (content != null) {
								Serializable persObject = response.getNewPersistentCachedObject();
								Object altObject = response.getNewTransientCachedObject();
								int altObjectSize = response.getNewTransientObjectSize();
								this.cache(connectionUrl, connection, content, persObject, altObject, altObjectSize);
							} else {
								logger.warn("processHandler(): Cacheable response not available: " + connectionUrl);
							}
						} else if (cacheInfo != null && !cacheInfo.hasTransientEntry()) {
							// Content that came from cache cannot be cached
							// again, but a RAM entry was missing.
							final byte[] persContent = cacheInfo.getPersistentContent();
							Object altObject = response.getNewTransientCachedObject();
							int altObjectSize = response.getNewTransientObjectSize();
							final MemoryCacheEntry newMemEntry = new MemoryCacheEntry(persContent,
									cacheInfo.getExpires(), cacheInfo.getRequestTime(), altObject, altObjectSize);
							final int actualApproxObjectSize;
							if (altObject != null) {
								if (altObjectSize < persContent.length) {
									actualApproxObjectSize = persContent.length;
								} else {
									actualApproxObjectSize = altObjectSize;
								}
							} else {
								actualApproxObjectSize = 0;
							}
							AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
								CacheManager.getInstance().putTransient(connectionUrl, newMemEntry,
										actualApproxObjectSize + persContent.length);
								return null;
							});
						}
					} finally {
						if (trackRequestInfo) {
							synchronized (this.processingRequests) {
								this.processingRequests.remove(rinfo);
							}
						}
						if (connection instanceof HttpURLConnection) {
							((HttpURLConnection) connection).disconnect();
						}
					}
				}
			} finally {
				if (cacheInfo != null) {
					// This is necessary so that the file stream doesn't
					// stay open potentially.
					cacheInfo.dispose();
				}
			}
		} catch (Throwable exception) {
			logger.error( exception);
		} finally {
			rhandler.handleProgress(ProgressType.DONE, baseURL, method, 0, 0);
		}
	}

	/**
	 * The Class RequestInfo.
	 */
	private static class RequestInfo {

		/** The request handler. */
		private final RequestHandler requestHandler;

		/** The is aborted. */
		private volatile boolean isAborted = false;

		/** The connection. */
		private volatile URLConnection connection;

		/**
		 * Instantiates a new request info.
		 *
		 * @param connection
		 *            the connection
		 * @param rhandler
		 *            the rhandler
		 */
		public RequestInfo(URLConnection connection, RequestHandler rhandler) {
			this.connection = connection;
			this.requestHandler = rhandler;
		}

		/**
		 * Checks if is aborted.
		 *
		 * @return true, if is aborted
		 */
		public boolean isAborted() {
			return this.isAborted;
		}

		/**
		 * Abort.
		 */
		public void abort() {
			this.isAborted = true;
			if (this.connection instanceof HttpURLConnection) {
				((HttpURLConnection) this.connection).disconnect();
			}
		}

		/**
		 * Gets the request handler.
		 *
		 * @return the request handler
		 */
		public RequestHandler getRequestHandler() {
			return this.requestHandler;
		}

		/**
		 * Sets the connection.
		 *
		 * @param connection
		 *            the connection
		 * @param in
		 *            the in
		 */
		public void setConnection(URLConnection connection) {
			this.connection = connection;
		}
	}

	/**
	 * The Class RequestHandlerTask.
	 */
	private class RequestHandlerTask implements SimpleThreadPoolTask {

		/** The handler. */
		private final RequestHandler handler;

		/** The access context. */
		private final AccessControlContext accessContext;

		/**
		 * Instantiates a new request handler task.
		 *
		 * @param handler
		 *            the handler
		 * @param accessContext
		 *            the access context
		 */
		private RequestHandlerTask(RequestHandler handler, AccessControlContext accessContext) {
			this.handler = handler;
			this.accessContext = accessContext;
		}

		/**
		 * Instantiates a new request handler task.
		 *
		 * @param handler
		 *            the handler
		 */
		private RequestHandlerTask(RequestHandler handler) {
			this.handler = handler;
			this.accessContext = null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			SecurityManager sm = System.getSecurityManager();
			if (sm != null && this.accessContext != null) {
				PrivilegedAction<Object> action = () -> {
					processHandler(handler, 0, true);
					return null;
				};
				// This way we ensure scheduled requests have the same
				// protection as inline requests, particularly in relation
				// to file and host name checks.
				AccessController.doPrivileged(action, this.accessContext);
			} else {
				processHandler(this.handler, 0, true);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.loboevolution.util.SimpleThreadPoolTask#cancel()
		 */
		@Override
		public void cancel() {
			cancelRequestIfRunning(this.handler);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return this.handler.hashCode();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object other) {
			return other instanceof RequestHandlerTask && ((RequestHandlerTask) other).handler.equals(this.handler);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "RequestHandlerTask[host=" + this.handler.getLatestRequestURL().getHost() + "]";
		}
	}

	/**
	 * Checks if is file.
	 *
	 * @param url
	 *            the url
	 * @return true, if is file
	 */
	public boolean isFile(String url) {
		for (int i = url.length() - 1; i > 0; i--) {
			if (url.substring(i).contains(".")) {
				return true;
			}
		}
		return false;
	}
}
