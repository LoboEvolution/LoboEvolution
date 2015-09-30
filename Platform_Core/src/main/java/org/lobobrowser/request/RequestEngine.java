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
package org.lobobrowser.request;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

import org.lobobrowser.async.AsyncResult;
import org.lobobrowser.async.AsyncResultImpl;
import org.lobobrowser.clientlet.CancelClientletException;
import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.clientlet.ClientletRequest;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.http.Cookie;
import org.lobobrowser.http.Header;
import org.lobobrowser.http.NameValuePair;
import org.lobobrowser.main.ExtensionManager;
import org.lobobrowser.settings.BooleanSettings;
import org.lobobrowser.settings.CacheSettings;
import org.lobobrowser.settings.ConnectionSettings;
import org.lobobrowser.store.CacheManager;
import org.lobobrowser.ua.Parameter;
import org.lobobrowser.ua.ParameterInfo;
import org.lobobrowser.ua.ProgressType;
import org.lobobrowser.ua.RequestType;
import org.lobobrowser.ua.UserAgent;
import org.lobobrowser.util.BoxedObject;
import org.lobobrowser.util.ID;
import org.lobobrowser.util.SSLCertificate;
import org.lobobrowser.util.SimpleThreadPool;
import org.lobobrowser.util.SimpleThreadPoolTask;
import org.lobobrowser.util.Strings;
import org.lobobrowser.util.Urls;
import org.lobobrowser.util.io.Files;
import org.lobobrowser.util.io.IORoutines;

/**
 * The Class RequestEngine.
 */
public final class RequestEngine {

    /** The Constant logger. */
    private static final Logger logger = Logger.getLogger(RequestEngine.class
            .getName());

    /** The Constant loggerInfo. */
    private static final boolean loggerInfo = logger.isLoggable(Level.INFO);

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

    /**
     * Instantiates a new request engine.
     */
    private RequestEngine() {
        // Use few threads to avoid excessive parallelism. Note that
        // downloads are not handled by this thread pool.
        this.threadPool = new SimpleThreadPool("RequestEngineThreadPool", 3, 5,
                60 * 1000);

        // Security: Private fields that require privileged access to get
        // initialized.
        this.cacheSettings = CacheSettings.getInstance();
        this.connectionSettings = ConnectionSettings.getInstance();
        this.booleanSettings = BooleanSettings.getInstance();
    }

    /** The Constant instance. */
    private static final RequestEngine instance = new RequestEngine();

    /** Gets the Constant instance.
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
        Collection<?> cookies = this.cookieStore.getCookies(url.getHost(),
                url.getPath());
        StringBuffer cookieText = new StringBuffer();
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
        AccessControlContext context = sm == null ? null : AccessController
                .getContext();
        this.threadPool.schedule(new RequestHandlerTask(handler, context));
    }

    /** The Constant NORMAL_FORM_ENCODING. */
    private static final String NORMAL_FORM_ENCODING = "application/x-www-form-urlencoded";

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
    private void postData(URLConnection connection, ParameterInfo pinfo,
            String altPostData) throws IOException {
        BooleanSettings boolSettings = this.booleanSettings;
        String encoding = pinfo.getEncoding();
        if ((encoding == null)
                || NORMAL_FORM_ENCODING.equalsIgnoreCase(encoding)) {
            ByteArrayOutputStream bufOut = new ByteArrayOutputStream();
            Parameter[] parameters = pinfo.getParameters();
            boolean firstParam = true;
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
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
                    logger.warning("postData(): Ignoring non-textual parameter "
                            + name
                            + " for POST with encoding "
                            + encoding
                            + ".");
                }
            }
            // Do not add a line break to post content. Some servers
            // can be picky about that (namely, java.net).
            byte[] postContent = bufOut.toByteArray();
            if (loggerInfo) {
                logger.info("postData(): Will post: " + new String(postContent));
            }
            if (connection instanceof HttpURLConnection) {
                if (boolSettings.isHttpUseChunkedEncodingPOST()) {
                    ((HttpURLConnection) connection)
                    .setChunkedStreamingMode(8192);
                } else {
                    ((HttpURLConnection) connection)
                    .setFixedLengthStreamingMode(postContent.length);
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
            MultipartFormDataWriter writer = new MultipartFormDataWriter(
                    mfstream, boundary);
            try {
                if (pinfo != null) {
                    Parameter[] parameters = pinfo.getParameters();
                    for (int i = 0; i < parameters.length; i++) {
                        Parameter parameter = parameters[i];
                        String name = parameter.getName();
                        if (parameter.isText()) {
                            writer.writeText(name, parameter.getTextValue(),
                                    "UTF-8");
                        } else if (parameter.isFile()) {
                            File[] file = parameter.getFileValue();

                            for (int f = 0; f < file.length; f++) {

                                FileInputStream in = new FileInputStream(
                                        file[f]);
                                try {
                                    BufferedInputStream bin = new BufferedInputStream(
                                            in, 8192);
                                    writer.writeFileData(name,
                                            file[f].getName(),
                                            Files.getContentType(file[f]), bin);
                                } finally {
                                    in.close();
                                }
                            }
                        } else {
                            logger.warning("postData(): Skipping parameter "
                                    + name
                                    + " of unknown type for POST with encoding "
                                    + encoding + ".");
                        }
                    }
                }
            } finally {
                writer.send();
            }
            connection.addRequestProperty("Content-Type", encoding
                    + "; boundary=" + boundary);
            if (chunked) {
                if (connection instanceof HttpURLConnection) {
                    ((HttpURLConnection) connection)
                    .setChunkedStreamingMode(8192);
                }
            } else {
                byte[] content = ((ByteArrayOutputStream) mfstream)
                        .toByteArray();
                if (connection instanceof HttpURLConnection) {
                    ((HttpURLConnection) connection)
                    .setFixedLengthStreamingMode(content.length);
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
    private String completeGetUrl(String baseURL, ParameterInfo pinfo,
            String ref) throws Exception {
        String newNoRefURL;
        Parameter[] parameters = pinfo.getParameters();
        if ((parameters != null) && (parameters.length > 0)) {
            StringBuffer sb = new StringBuffer(baseURL);
            int qmIdx = baseURL.indexOf('?');
            char separator = qmIdx == -1 ? '?' : '&';
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].isText()) {
                    sb.append(separator);
                    sb.append(parameters[i].getName());
                    sb.append('=');
                    String paramText = parameters[i].getTextValue();
                    sb.append(URLEncoder.encode(paramText, "UTF-8"));
                    separator = '&';
                } else {
                    logger.warning("completeGetUrl(): Skipping non-textual parameter "
                            + parameters[i].getName() + " in GET request.");
                }
            }
            newNoRefURL = sb.toString();
        } else {
            newNoRefURL = baseURL;
        }
        if ((ref != null) && (ref.length() != 0)) {
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
     * @param lastRequestURL
     *            the last request url
     * @throws ProtocolException
     *             the protocol exception
     */
    private void addRequestProperties(URLConnection connection,
            ClientletRequest request, CacheInfo cacheInfo,
            String requestMethod, URL lastRequestURL) throws ProtocolException {
        UserAgent userAgent = request.getUserAgent();
        connection.addRequestProperty("User-Agent", userAgent.toString());
        connection.addRequestProperty("X-Java-Version",
                userAgent.getJavaVersion());
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
            for (int i = 0; i < headers.length; i++) {
                String headerName = headers[i].getName();
                if (headerName.startsWith("X-")) {
                    connection.addRequestProperty(headerName,
                            headers[i].getValue());
                } else {
                    logger.warning("run(): Ignoring request header: "
                            + headerName);
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
    private CacheInfo getCacheInfo(final RequestHandler rhandler, final URL url)
            throws Exception {
        return AccessController.doPrivileged(new PrivilegedAction<CacheInfo>() {
            // Reason: Caller in context may not have privilege to access
            // the local file system, yet it's necessary to be able to load
            // a cache file.
            @Override
            public CacheInfo run() {
                MemoryCacheEntry entry;
                byte[] persistentContent = null;
                CacheManager cm = CacheManager.getInstance();
                entry = (MemoryCacheEntry) cm.getTransient(url);
                if (entry == null) {
                    if (!"file".equalsIgnoreCase(url.getProtocol())
                            || !Strings.isBlank(url.getHost())) {
                        try {
                            persistentContent = cm.getPersistent(url, false);
                        } catch (IOException ioe) {
                            logger.log(
                                    Level.WARNING,
                                    "getCacheInfo(): Unable to load cache file.",
                                    ioe);
                        }
                    }
                }
                if ((persistentContent == null) && (entry == null)) {
                    return null;
                }
                CacheInfo cinfo = new CacheInfo(entry, persistentContent, url);
                return cinfo;
            }
        });
    }

    /**
     * Cache.
     *
     * @param rhandler
     *            the rhandler
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
    private void cache(final RequestHandler rhandler, final URL url,
            final URLConnection connection, final byte[] content,
            final java.io.Serializable altPersistentObject,
            final Object altObject, final int approxAltObjectSize) {
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            // Reason: Caller might not have permission to access the
            // file system. Yet, caching should be allowed.
            @Override
            public Object run() {
                try {
                    long currentTime = System.currentTimeMillis();
                    if (loggerInfo) {
                        logger.info("cache(): url=" + url + ",content.length="
                                + content.length + ",currentTime="
                                + currentTime);
                    }
                    int actualApproxObjectSize = 0;
                    if (altObject != null) {
                        if (approxAltObjectSize < content.length) {
                            actualApproxObjectSize = content.length;
                        } else {
                            actualApproxObjectSize = approxAltObjectSize;
                        }
                    }
                    Long expiration = Urls.getExpiration(connection,
                            currentTime);
                    List<NameValuePair> headers = Urls.getHeaders(connection);
                    MemoryCacheEntry memEntry = new MemoryCacheEntry(content,
                            headers, expiration, altObject,
                            actualApproxObjectSize);
                    int approxMemEntrySize = content.length
                            + (altObject == null ? 0 : approxAltObjectSize);
                    CacheManager cm = CacheManager.getInstance();
                    cm.putTransient(url, memEntry, approxMemEntrySize);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    try {
                        boolean hadDate = false;
                        boolean hadContentLength = false;
                        for (int counter = 0; true; counter++) {
                            String headerKey = connection
                                    .getHeaderFieldKey(counter);
                            if (headerKey != null) {
                                if (!hadDate
                                        && "date".equalsIgnoreCase(headerKey)) {
                                    hadDate = true;
                                }
                                if (!hadContentLength
                                        && "content-length"
                                        .equalsIgnoreCase(headerKey)) {
                                    hadContentLength = true;
                                }
                            }
                            String headerValue = connection
                                    .getHeaderField(counter);
                            if (headerValue == null) {
                                break;
                            }
                            if (CacheInfo.HEADER_REQUEST_TIME
                                    .equalsIgnoreCase(headerKey)) {
                                continue;
                            }
                            String headerPrefix = (headerKey == null)
                                    || (headerKey.length() == 0) ? ""
                                            : headerKey + ": ";
                            byte[] headerBytes = (headerPrefix + headerValue + "\r\n")
                                    .getBytes("UTF-8");
                            out.write(headerBytes);
                        }
                        if (!hadDate) {
                            String currentDate = Urls.PATTERN_RFC1123
                                    .format(new java.util.Date());
                            byte[] headerBytes = ("Date: " + currentDate + "\r\n")
                                    .getBytes("UTF-8");
                            out.write(headerBytes);
                        }
                        if (!hadContentLength) {
                            byte[] headerBytes = ("Content-Length: "
                                    + content.length + "\r\n")
                                    .getBytes("UTF-8");
                            out.write(headerBytes);
                        }
                        byte[] rtHeaderBytes = (CacheInfo.HEADER_REQUEST_TIME
                                + ": " + currentTime + "\r\n")
                                .getBytes("UTF-8");
                        out.write(rtHeaderBytes);
                        out.write(IORoutines.LINE_BREAK_BYTES);
                        out.write(content);
                    } finally {
                        out.close();
                    }
                    try {
                        cm.putPersistent(url, out.toByteArray(), false);
                    } catch (Exception err) {
                        logger.log(Level.WARNING,
                                "cache(): Unable to cache response content.",
                                err);
                    }
                    if (altPersistentObject != null) {
                        try {
                            ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
                            // No need to buffer - Java API already does.
                            ObjectOutputStream objOut = new ObjectOutputStream(
                                    fileOut);
                            objOut.writeObject(altPersistentObject);
                            objOut.flush();
                            byte[] byteArray = fileOut.toByteArray();
                            if (byteArray.length == 0) {
                                logger.log(Level.WARNING,
                                        "cache(): Serialized content has zero bytes for persistent object "
                                                + altPersistentObject + ".");
                            }
                            cm.putPersistent(url, byteArray, true);
                        } catch (Exception err) {
                            logger.log(
                                    Level.WARNING,
                                    "cache(): Unable to write persistent cached object.",
                                    err);
                        }
                    }
                } catch (Exception err) {
                    logger.log(Level.WARNING, "cache()", err);
                }
                return null;
            }
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
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            buffer.append(entry.getKey() + ": " + entry.getValue());
            buffer.append(System.getProperty("line.separator"));
        }
        logger.info("printRequestHeaders(): Request headers for URI=["
                + connection.getURL() + "]\r\n" + buffer.toString());
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
            public boolean handleException(ClientletResponse response,
                    Throwable exception) throws ClientletException {
                if (exception instanceof ClientletException) {
                    throw (ClientletException) exception;
                } else {
                    throw new ClientletException(exception);
                }
            }

            @Override
            public void processResponse(ClientletResponse response)
                    throws ClientletException, IOException {
                byte[] bytes = org.lobobrowser.util.io.IORoutines.load(
                        response.getInputStream(), 4096);
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
    public AsyncResult<byte[]> loadBytesAsync(final String urlOrPath)
            throws MalformedURLException {
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
            public boolean handleException(ClientletResponse response,
                    Throwable exception) throws ClientletException {
                asyncResult.setException(exception);
                return true;
            }

            @Override
            public void processResponse(ClientletResponse response)
                    throws ClientletException, IOException {
                byte[] bytes = org.lobobrowser.util.io.IORoutines.load(
                        response.getInputStream(), 4096);
                asyncResult.setResult(bytes);
            }
        });
        return asyncResult;
    }

    /**
     * Whether possibly cached request should always be revalidated, i.e. any
     * expiration information is ignored.
     *
     * @param connectionUrl
     *            the connection url
     * @param requestType
     *            the request type
     * @return true, if successful
     */
    private static boolean shouldRevalidateAlways(URL connectionUrl,
            RequestType requestType) {
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

    /** Gets the safe extension manager.
	 *
	 * @return the safe extension manager
	 */
    private ExtensionManager getSafeExtensionManager() {
        return AccessController
                .doPrivileged(new PrivilegedAction<ExtensionManager>() {
                    @Override
                    public ExtensionManager run() {
                        return ExtensionManager.getInstance();
                    }
                });
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
    private URLConnection getURLConnection(URL connectionUrl,
            ClientletRequest request, String protocol, String method,
            RequestHandler rhandler, CacheInfo cacheInfo) throws IOException {
        URLConnection connection;
        if (cacheInfo != null) {
            RequestType requestType = rhandler.getRequestType();
            if (doesNotExpire(requestType)) {
                if (loggerInfo) {
                    if (cacheInfo.hasTransientEntry()) {
                        logger.info("getURLConnection(): FROM-RAM: "
                                + connectionUrl + ".");
                    } else {
                        logger.info("getURLConnection(): FROM-FILE: "
                                + connectionUrl + ".");
                    }
                }
                return cacheInfo.getURLConnection();
            } else if (!shouldRevalidateAlways(connectionUrl, requestType)) {
                Long expires = cacheInfo.getExpires();
                if (expires == null) {
                    Integer defaultOffset = this.cacheSettings
                            .getDefaultCacheExpirationOffset();
                    if (defaultOffset != null) {
                        expires = cacheInfo.getExpiresGivenOffset(defaultOffset
                                .longValue());
                        if (loggerInfo) {
                            Date expiresDate = expires == null ? null
                                    : new Date(expires);
                            logger.info("getURLConnection(): Used default offset for "
                                    + connectionUrl
                                    + ": expires="
                                    + expiresDate);
                        }
                    }
                }
                if (expires != null) {
                    if (expires.longValue() > System.currentTimeMillis()) {
                        if (loggerInfo) {
                            long secondsToExpiration = (expires.longValue() - System
                                    .currentTimeMillis()) / 1000;
                            if (cacheInfo.hasTransientEntry()) {
                                logger.info("getURLConnection(): FROM-RAM: "
                                        + connectionUrl + ". Expires in "
                                        + secondsToExpiration + " seconds.");
                            } else {
                                logger.info("getURLConnection(): FROM-FILE: "
                                        + connectionUrl + ". Expires in "
                                        + secondsToExpiration + " seconds.");
                            }
                        }
                        return cacheInfo.getURLConnection();
                    } else {
                        if (loggerInfo) {
                            logger.info("getURLConnection(): EXPIRED: "
                                    + connectionUrl + ". Expired on "
                                    + new Date(expires) + ".");
                        }
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
        if (isResURL || (host == null) || (host.length() == 0)) {
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
            ((HttpsURLConnection) connection).setHostnameVerifier(rhandler
                    .getHostnameVerifier());
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
        this.addRequestProperties(connection, request, cacheInfo, method,
                connectionUrl);
        // Allow extensions to modify the connection object.
        // Doing it after addRequestProperties() to allow such
        // functionality as altering the Accept header.
        connection = this.getSafeExtensionManager().dispatchPreConnection(
                connection);
        // Print request headers
        if (logger.isLoggable(Level.FINE)) {
            this.printRequestHeaders(connection);
        }
        // POST data if we need to.
        if (isPost) {
            ParameterInfo pinfo = rhandler instanceof RedirectRequestHandler ? null
                    : request.getParameterInfo();
            if (pinfo == null) {
                throw new IllegalStateException(
                        "POST has no parameter information");
            }
            this.postData(connection, pinfo, request.getAltPostData());
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
        return (requestType != RequestType.SOFT_RELOAD)
                && (requestType != RequestType.HARD_RELOAD)
                && (requestType != RequestType.DOWNLOAD);
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
    private void processHandler(final RequestHandler rhandler,
            final int recursionLevel, final boolean trackRequestInfo) {
        // Method must be private.
        boolean linfo = loggerInfo;
        URL baseURL = rhandler.getLatestRequestURL();
        RequestInfo rinfo = null;
        ClientletResponseImpl response = null;
        String method = null;
        try {
            ClientletRequest request = rhandler.getRequest();
            method = rhandler.getLatestRequestMethod().toUpperCase();
            // TODO: Hack: instanceof below
            ParameterInfo pinfo = rhandler instanceof RedirectRequestHandler ? null
                    : request.getParameterInfo();
            boolean isGet = "GET".equals(method);
            URL url;
            if (isGet && (pinfo != null)) {
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
            if ((url.getQuery() != null) && "file".equalsIgnoreCase(protocol)) {
                // Remove query (replace file with path) if "file" protocol.
                String ref = url.getRef();
                String refText = (ref == null) || (ref.length() == 0) ? ""
                        : "#" + ref;
                connectionUrl = new URL(protocol, url.getHost(), url.getPort(),
                        url.getPath() + refText);
            } else {
                connectionUrl = url;
            }
            RequestType requestType = rhandler.getRequestType();
            if (isGet && isOKToRetrieveFromCache(requestType)) {
                cacheInfo = this.getCacheInfo(rhandler, connectionUrl);
            }
            try {

                SSLCertificate.setCertificate();
                URLConnection connection = this.getURLConnection(connectionUrl,
                        request, protocol, method, rhandler, cacheInfo);
                rinfo = new RequestInfo(connection, rhandler);
                InputStream responseIn = null;
                if (trackRequestInfo) {
                    synchronized (this.processingRequests) {
                        this.processingRequests.add(rinfo);
                    }
                }
                try {
                    if (rhandler.isCancelled()) {
                        throw new CancelClientletException("cancelled");
                    }
                    rhandler.handleProgress(ProgressType.CONNECTING, url,
                            method, 0, -1);
                    // Handle response
                    boolean isContentCached = (cacheInfo != null)
                            && cacheInfo.isCacheConnection(connection);
                    boolean isCacheable = false;

                    if ((connection instanceof HttpsURLConnection)
                            && !isContentCached) {

                        HttpsURLConnection hconnection = (HttpsURLConnection) connection;
                        hconnection.setInstanceFollowRedirects(true);
                        int responseCode = hconnection.getResponseCode();

                        if (linfo) {
                            logger.info("run(): ResponseCode=" + responseCode
                                    + " for url=" + connectionUrl);
                        }
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            if (linfo) {
                                logger.info("run(): FROM-HTTP: "
                                        + connectionUrl);
                            }
                            if (this.mayBeCached(hconnection)) {
                                isCacheable = true;
                            } else {
                                if (linfo) {
                                    logger.info("run(): NOT CACHEABLE: "
                                            + connectionUrl);
                                }
                                if (cacheInfo != null) {
                                    cacheInfo.delete();
                                }
                            }
                            responseIn = connection.getInputStream();
                            rinfo.setConnection(connection, responseIn);
                        } else if (responseCode == HttpURLConnection.HTTP_NOT_MODIFIED) {
                            if (cacheInfo == null) {
                                throw new IllegalStateException(
                                        "Cache info missing but it is necessary to process response code "
                                                + responseCode + ".");
                            }
                            if (linfo) {
                                logger.info("run(): FROM-VALIDATION: "
                                        + connectionUrl);
                            }
                            // Disconnect the HTTP connection.
                            hconnection.disconnect();
                            isContentCached = true;
                            // Even though the response is actually from the
                            // cache,
                            // we need to cache it again, if only to update the
                            // request time (used to calculate default
                            // expiration).
                            isCacheable = true;
                            connection = cacheInfo.getURLConnection();
                            responseIn = connection.getInputStream();
                            rinfo.setConnection(connection, responseIn);
                        } else if ((responseCode == HttpURLConnection.HTTP_MOVED_PERM)
                                || (responseCode == HttpURLConnection.HTTP_MOVED_TEMP)
                                || (responseCode == HttpURLConnection.HTTP_SEE_OTHER)) {
                            if (linfo) {
                                logger.info("run(): REDIRECTING: ResponseCode="
                                        + responseCode + " for url=" + url);
                            }
                            RequestHandler newHandler = new RedirectRequestHandler(
                                    rhandler, hconnection);
                            Thread.yield();
                            if (recursionLevel > 5) {
                                throw new ClientletException(
                                        "Exceeded redirect recursion limit.");
                            }
                            this.processHandler(newHandler, recursionLevel + 1,
                                    trackRequestInfo);
                            return;
                        }

                    } else if ((connection instanceof HttpURLConnection)
                            && !isContentCached) {

                        HttpURLConnection hconnection = (HttpURLConnection) connection;
                        hconnection.setInstanceFollowRedirects(false);
                        int responseCode = hconnection.getResponseCode();
                        if (linfo) {
                            logger.info("run(): ResponseCode=" + responseCode
                                    + " for url=" + connectionUrl);
                        }
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            if (linfo) {
                                logger.info("run(): FROM-HTTP: "
                                        + connectionUrl);
                            }
                            if (this.mayBeCached(hconnection)) {
                                isCacheable = true;
                            } else {
                                if (linfo) {
                                    logger.info("run(): NOT CACHEABLE: "
                                            + connectionUrl);
                                }
                                if (cacheInfo != null) {
                                    cacheInfo.delete();
                                }
                            }
                            responseIn = connection.getInputStream();
                            rinfo.setConnection(connection, responseIn);
                        } else if (responseCode == HttpURLConnection.HTTP_NOT_MODIFIED) {
                            if (cacheInfo == null) {
                                throw new IllegalStateException(
                                        "Cache info missing but it is necessary to process response code "
                                                + responseCode + ".");
                            }
                            if (linfo) {
                                logger.info("run(): FROM-VALIDATION: "
                                        + connectionUrl);
                            }
                            // Disconnect the HTTP connection.
                            hconnection.disconnect();
                            isContentCached = true;
                            // Even though the response is actually from the
                            // cache,
                            // we need to cache it again, if only to update the
                            // request time (used to calculate default
                            // expiration).
                            isCacheable = true;
                            connection = cacheInfo.getURLConnection();
                            responseIn = connection.getInputStream();
                            rinfo.setConnection(connection, responseIn);
                        } else if ((responseCode == HttpURLConnection.HTTP_MOVED_PERM)
                                || (responseCode == HttpURLConnection.HTTP_MOVED_TEMP)
                                || (responseCode == HttpURLConnection.HTTP_SEE_OTHER)) {
                            if (linfo) {
                                logger.info("run(): REDIRECTING: ResponseCode="
                                        + responseCode + " for url=" + url);
                            }
                            RequestHandler newHandler = new RedirectRequestHandler(
                                    rhandler, hconnection);
                            Thread.yield();
                            if (recursionLevel > 5) {
                                throw new ClientletException(
                                        "Exceeded redirect recursion limit.");
                            }
                            this.processHandler(newHandler, recursionLevel + 1,
                                    trackRequestInfo);
                            return;
                        }
                    } else {
                        // Force it to throw exception if file does not exist
                        responseIn = connection.getInputStream();
                        rinfo.setConnection(connection, responseIn);
                    }
                    if (rinfo.isAborted()) {
                        throw new CancelClientletException("Stopped");
                    }
                    // Give a change to extensions to post-process the
                    // connection.
                    URLConnection newConnection = this
                            .getSafeExtensionManager().dispatchPostConnection(
                                    connection);
                    if (newConnection != connection) {
                        responseIn = newConnection.getInputStream();
                        connection = newConnection;
                    }
                    // Create clientlet response.
                    response = new ClientletResponseImpl(rhandler, connection,
                            url, isContentCached, cacheInfo, isCacheable,
                            rhandler.getRequestType());
                    rhandler.processResponse(response);
                    if (isCacheable) {
                        // Make sure stream reaches EOF so we don't
                        // get null stored content.
                        response.ensureReachedEOF();
                        byte[] content = response.getStoredContent();
                        if (content != null) {
                            Serializable persObject = response
                                    .getNewPersistentCachedObject();
                            Object altObject = response
                                    .getNewTransientCachedObject();
                            int altObjectSize = response
                                    .getNewTransientObjectSize();
                            this.cache(rhandler, connectionUrl, connection,
                                    content, persObject, altObject,
                                    altObjectSize);
                        } else {
                            logger.warning("processHandler(): Cacheable response not available: "
                                    + connectionUrl);
                        }
                    } else if ((cacheInfo != null)
                            && !cacheInfo.hasTransientEntry()) {
                        // Content that came from cache cannot be cached
                        // again, but a RAM entry was missing.
                        final byte[] persContent = cacheInfo
                                .getPersistentContent();
                        Object altObject = response
                                .getNewTransientCachedObject();
                        int altObjectSize = response
                                .getNewTransientObjectSize();
                        final MemoryCacheEntry newMemEntry = new MemoryCacheEntry(
                                persContent, cacheInfo.getExpires(),
                                cacheInfo.getRequestTime(), altObject,
                                altObjectSize);
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
                        AccessController
                        .doPrivileged(new PrivilegedAction<Object>() {
                            // Reason: Privileges needed to access
                            // CacheManager.
                            @Override
                            public Object run() {
                                CacheManager
                                .getInstance()
                                .putTransient(
                                        connectionUrl,
                                        newMemEntry,
                                        actualApproxObjectSize
                                        + persContent.length);
                                return null;
                            }
                        });
                    }
                } finally {
                    if (trackRequestInfo) {
                        synchronized (this.processingRequests) {
                            this.processingRequests.remove(rinfo);
                        }
                    }
                    if (responseIn != null) {
                        try {
                            responseIn.close();
                        } catch (IOException ioe) {
                            // ignore
                        }
                    }
                    if (connection instanceof HttpURLConnection) {
                        ((HttpURLConnection) connection).disconnect();
                    }
                }
            } finally {
                if (cacheInfo != null) {
                    // This is necessary so that the file stream doesn't
                    // stay open potentially.
                    cacheInfo.dispose();
                }
            }
        } catch (CancelClientletException cce) {
            if (linfo) {
                logger.log(Level.INFO,
                        "run(): Clientlet cancelled: " + baseURL, cce);
            }
        } catch (Throwable exception) {
            if ((rinfo != null) && rinfo.isAborted()) {
                if (linfo) {
                    logger.log(
                            Level.INFO,
                            "run(): Exception ignored because request aborted.",
                            exception);
                }
            } else {
                try {
                    if (!rhandler.handleException(response, exception)) {
                        logger.log(Level.WARNING,
                                "Was unable to handle exception.", exception);
                    }
                } catch (Exception err) {
                    logger.log(Level.WARNING,
                            "Exception handler threw an exception.", err);
                }
            }
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

        /** The input stream. */
        private volatile InputStream inputStream;

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
        RequestInfo(URLConnection connection, RequestHandler rhandler) {
            this.connection = connection;
            this.requestHandler = rhandler;
        }

        /** Checks if is aborted.
		 *
		 * @return true, if is aborted
		 */
        boolean isAborted() {
            return this.isAborted;
        }

        /**
         * Abort.
         */
        void abort() {
            try {
                this.isAborted = true;
                if (this.connection instanceof HttpURLConnection) {
                    ((HttpURLConnection) this.connection).disconnect();
                }
                InputStream in = this.inputStream;
                if (in != null) {
                    in.close();
                }
            } catch (Exception err) {
                logger.log(Level.SEVERE, "abort()", err);
            }
        }

        /** Gets the request handler.
		 *
		 * @return the request handler
		 */
        RequestHandler getRequestHandler() {
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
        void setConnection(URLConnection connection, InputStream in) {
            this.connection = connection;
            this.inputStream = in;
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
        private RequestHandlerTask(RequestHandler handler,
                AccessControlContext accessContext) {
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
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            SecurityManager sm = System.getSecurityManager();
            if ((sm != null) && (this.accessContext != null)) {
                PrivilegedAction<Object> action = new PrivilegedAction<Object>() {
                    @Override
                    public Object run() {
                        processHandler(handler, 0, true);
                        return null;
                    }
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
         * @see org.lobobrowser.util.SimpleThreadPoolTask#cancel()
         */
        @Override
        public void cancel() {
            cancelRequestIfRunning(this.handler);
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return this.handler.hashCode();
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object other) {
            return (other instanceof RequestHandlerTask)
                    && ((RequestHandlerTask) other).handler
                    .equals(this.handler);
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "RequestHandlerTask[host="
                    + this.handler.getLatestRequestURL().getHost() + "]";
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
