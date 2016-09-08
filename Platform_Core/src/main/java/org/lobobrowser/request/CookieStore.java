/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Jun 1, 2005
 */
package org.lobobrowser.request;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lobobrowser.http.Cookie;
import org.lobobrowser.store.RestrictedStore;
import org.lobobrowser.store.StorageManager;
import org.lobobrowser.util.DateUtil;
import org.lobobrowser.util.Domains;
import org.lobobrowser.util.Strings;

/**
 * The Class CookieStore.
 *
 * @author J. H. S.
 */
public class CookieStore {

    /** The Constant COOKIE_PATH_PREFIX. */
    private static final String COOKIE_PATH_PREFIX = ".W$Cookies/";

    /** The Constant COOKIE_PATH_PATTERN. */
    private static final String COOKIE_PATH_PATTERN = "\\.W\\$Cookies/.*";

   /** The Constant instance. */
    private static final CookieStore instance = new CookieStore();

    /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger(CookieStore.class
            .getName());

    /** The transient map by host. */
    private final Map<String, Map<String, CookieValue>> transientMapByHost = new HashMap<String, Map<String, CookieValue>>();

    /**
     * Instantiates a new cookie store.
     */
    private CookieStore() {
    }

    /** Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
    public static CookieStore getInstance() {
        return instance;
    }

    /**
     * Save cookie.
     *
     * @param url
     *            the url
     * @param cookieSpec
     *            the cookie spec
     */
    public void saveCookie(URL url, String cookieSpec) {
        this.saveCookie(url.getHost(), cookieSpec);
    }

    /**
     * Save cookie.
     *
     * @param urlHostName
     *            the url host name
     * @param cookieSpec
     *            the cookie spec
     */
    public void saveCookie(String urlHostName, String cookieSpec) {
        // TODO: SECURITY
        if (logger.isInfoEnabled()) {
            logger.info("saveCookie(): host=" + urlHostName + ",cookieSpec=["
                    + cookieSpec + "]");
        }
        StringTokenizer tok = new StringTokenizer(cookieSpec, ";");
        String cookieName = null;
        String cookieValue = null;
        String domain = null;
        String path = null;
        String expires = null;
        String maxAge = null;
        // String secure = null;
        boolean hasCookieName = false;
        while (tok.hasMoreTokens()) {
            String token = tok.nextToken();
            int idx = token.indexOf('=');
            String name = idx == -1 ? token.trim() : token.substring(0, idx)
                    .trim();
            String value = idx == -1 ? "" : Strings.unquote(token.substring(
                    idx + 1).trim());
            if (!hasCookieName) {
                cookieName = name;
                cookieValue = value;
                hasCookieName = true;
            } else {
                if ("max-age".equalsIgnoreCase(name)) {
                    maxAge = value;
                } else if ("path".equalsIgnoreCase(name)) {
                    path = value;
                } else if ("domain".equalsIgnoreCase(name)) {
                    domain = value;
                } else if ("expires".equalsIgnoreCase(name)) {
                    expires = value;
                } else if ("secure".equalsIgnoreCase(name)) {
                    // TODO: SECURITY
                    // secure = value;
                }
            }
        }
        if (cookieName == null) {
            logger.log(Level.ERROR, "saveCookie(): Invalid cookie spec from '"
                    + urlHostName + "'");
            return;
        }
        if ((path == null) || (path.length() == 0)) {
            path = "/";
        }
        if (domain != null) {
            if ((expires == null) && (maxAge == null)
                    && logger.isInfoEnabled()) {
                logger.log(Level.INFO,
                        "saveCookie(): Not rejecting transient cookie that specifies domain '"
                                + domain + "'.");
            }
            if (!Domains.isValidCookieDomain(domain, urlHostName)) {
                logger.log(Level.WARN,
                        "saveCookie(): Rejecting cookie with invalid domain '"
                                + domain + "' for host '" + urlHostName + "'.");
                return;
            }
        }
        if (domain == null) {
            domain = urlHostName;
        } else if (domain.startsWith(".")) {
            domain = domain.substring(1);
        }
        // TODO: Secure
        Date expiresDate = null;
        if (maxAge != null) {
            try {
                expiresDate = new java.util.Date(System.currentTimeMillis()
                        + (Integer.parseInt(maxAge) * 1000));
            } catch (NumberFormatException nfe) {
                logger.log(Level.WARN,
                        "saveCookie(): Max-age is not formatted correctly: "
                                + maxAge + ".");
            }
        } else if (expires != null) {
        	DateUtil du = new DateUtil();
    		expiresDate = du.determineDateFormat(expires, Locale.US);
        }
        this.saveCookie(domain, path, cookieName, expiresDate, cookieValue);
    }

    /**
     * Save cookie.
     *
     * @param domain
     *            the domain
     * @param path
     *            the path
     * @param name
     *            the name
     * @param expires
     *            the expires
     * @param value
     *            the value
     */
    public void saveCookie(String domain, String path, String name,
            Date expires, String value) {
        // TODO: SECURITY
        if (logger.isInfoEnabled()) {
            logger.info("saveCookie(): domain=" + domain + ",name=" + name
                    + ",expires=" + expires + ",value=[" + value + "].");
        }
        Long expiresLong = expires == null ? null : expires.getTime();
        CookieValue cookieValue = new CookieValue(value, path, domain,
                expiresLong);
        synchronized (this) {
            // Always save a transient cookie. It acts as a cache.
            Map<String, CookieValue> hostMap = this.transientMapByHost
                    .get(domain);
            if (hostMap == null) {
                hostMap = new HashMap<String, CookieValue>(2);
                this.transientMapByHost.put(domain, hostMap);
            }
            hostMap.put(name, cookieValue);
        }
        if (expiresLong != null) {
            try {
                RestrictedStore store = StorageManager.getInstance()
                        .getRestrictedStore(domain, true);
                store.saveObject(this.getPathFromCookieName(name), cookieValue);
            } catch (IOException ioe) {
                logger.log(Level.WARN,
                        "saveCookie(): Unable to save cookie named '" + name
                        + "' with domain '" + domain + "'", ioe);
            }
        }
    }

    /**
     * Gets the path from cookie name.
     *
     * @param cookieName
     *            the cookie name
     * @return the path from cookie name
     */
    private String getPathFromCookieName(String cookieName) {
        return COOKIE_PATH_PREFIX + cookieName;
    }

    /**
     * Gets the cookie name from path.
     *
     * @param path
     *            the path
     * @return the cookie name from path
     */
    private String getCookieNameFromPath(String path) {
        if (!path.startsWith(COOKIE_PATH_PREFIX)) {
            throw new IllegalArgumentException("Invalid path: " + path);
        }
        return path.substring(COOKIE_PATH_PREFIX.length());
    }

    /**
     * Gets cookies belonging exactly to the host name given, not to a broader
     * domain.
     *
     * @param hostName
     *            the host name
     * @param path
     *            the path
     * @return the cookies strict
     */
    private Collection<Cookie> getCookiesStrict(String hostName, String path) {

        if ((path == null) || (path.length() == 0)) {
            path = "/";
        }
        boolean liflag = logger.isInfoEnabled();
        Collection<Cookie> cookies = new LinkedList<Cookie>();
        Set<String> transientCookieNames = new HashSet<String>();
        synchronized (this) {
            Map<String, CookieValue> hostMap = this.transientMapByHost
                    .get(hostName);
            if (hostMap != null) {
                Iterator<Map.Entry<String, CookieValue>> i = hostMap.entrySet()
                        .iterator();
                while (i.hasNext()) {
                    Map.Entry<String, CookieValue> entry = i.next();
                    CookieValue cookieValue = entry.getValue();
                    if (cookieValue.isExpired()) {
                        if (liflag) {
                            logger.info("getCookiesStrict(): Cookie "
                                    + entry.getKey() + " from " + hostName
                                    + " expired: " + cookieValue.getExpires());
                        }
                    } else {
                        if (path.startsWith(cookieValue.getPath())) {
                            String cookieName = entry.getKey();
                            transientCookieNames.add(cookieName);
                            Cookie cookie = new Cookie();
                            cookie.setName(cookieName);
                            cookie.setValue(cookieValue.getValue());
                            cookie.setPath(cookieValue.getPath());
                            cookie.setDomain(cookieValue.getDomain());
                            cookies.add(cookie);
                        } else {
                            if (liflag) {
                                logger.info("getCookiesStrict(): Skipping cookie "
                                        + cookieValue
                                        + " since it does not match path "
                                        + path);
                            }
                        }
                    }
                }
            }
        }
        try {
            RestrictedStore store = StorageManager.getInstance()
                    .getRestrictedStore(hostName, false);
            if (store != null) {
                Collection paths;
                paths = store.getPaths(COOKIE_PATH_PATTERN);
                Iterator pathsIterator = paths.iterator();
                while (pathsIterator.hasNext()) {
                    String filePath = (String) pathsIterator.next();
                    String cookieName = this.getCookieNameFromPath(filePath);
                    if (!transientCookieNames.contains(cookieName)) {
                        CookieValue cookieValue = (CookieValue) store
                                .retrieveObject(filePath);
                        if (cookieValue != null) {
                            if (cookieValue.isExpired()) {
                                if (logger.isInfoEnabled()) {
                                    logger.info("getCookiesStrict(): Cookie "
                                            + cookieName + " from " + hostName
                                            + " expired: "
                                            + cookieValue.getExpires());
                                }
                                store.removeObject(filePath);
                            } else {
                                if (path.startsWith(cookieValue.getPath())) {
                                    // Found one that is not in main memory.
                                    // Cache it.
                                    synchronized (this) {
                                        Map<String, CookieValue> hostMap = this.transientMapByHost
                                                .get(hostName);
                                        if (hostMap == null) {
                                            hostMap = new HashMap<String, CookieValue>();
                                            this.transientMapByHost.put(
                                                    hostName, hostMap);
                                        }
                                        hostMap.put(cookieName, cookieValue);
                                    }
                                    // Now add cookie to the collection.
                                    Cookie cookie = new Cookie();
                                    cookie.setName(cookieName);
                                    cookie.setValue(cookieValue.getValue());
                                    cookie.setPath(cookieValue.getPath());
                                    cookie.setDomain(cookieValue.getDomain());
                                    cookies.add(cookie);
                                } else {
                                    if (logger.isInfoEnabled()) {
                                        logger.info("getCookiesStrict(): Skipping cookie "
                                                + cookieValue
                                                + " since it does not match path "
                                                + path);
                                    }
                                }
                            }
                        } else {
                            logger.warn("getCookiesStrict(): Expected to find cookie named "
                                    + cookieName + " but file is missing.");
                        }
                    }
                }
            }
        } catch (IOException ioe) {
            logger.log(Level.ERROR, "getCookiesStrict()", ioe);
        } catch (ClassNotFoundException cnf) {
            logger.log(Level.ERROR,
                    "getCookiesStrict(): Possible engine versioning error.",
                    cnf);
        }
        return cookies;
    }

    /**
     * Gets the cookies.
     *
     * @param hostName
     *            the host name
     * @param path
     *            the path
     * @return the cookies
     */
    public Collection<Cookie> getCookies(String hostName, String path) {
        // Security provided by RestrictedStore.
        Collection<String> possibleDomains = Domains
                .getPossibleDomains(hostName);
        Collection<Cookie> cookies = new LinkedList<Cookie>();
        for (String domain : possibleDomains) {
            cookies.addAll(this.getCookiesStrict(domain, path));
        }
        if (logger.isInfoEnabled()) {
            logger.info("getCookies(): For host=" + hostName + ", found "
                    + cookies.size() + " cookies: " + cookies);
        }
        return cookies;
    }
}
