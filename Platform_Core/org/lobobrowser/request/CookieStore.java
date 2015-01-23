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
 * Created on Jun 1, 2005
 */
package org.lobobrowser.request;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.store.RestrictedStore;
import org.lobobrowser.store.StorageManager;
import org.lobobrowser.util.Domains;
import org.lobobrowser.util.Strings;

/**
 * @author J. H. S.
 */
public class CookieStore {
	private static final String COOKIE_PATH_PREFIX = ".W$Cookies/";
	private static final String COOKIE_PATH_PATTERN = "\\.W\\$Cookies/.*";
	private static final DateFormat EXPIRES_FORMAT;
	private static final DateFormat EXPIRES_FORMAT_BAK1;
	private static final DateFormat EXPIRES_FORMAT_BAK2;
	private static final CookieStore instance = new CookieStore();

	private static final Logger logger = Logger.getLogger(CookieStore.class
			.getName());

	private final Map<String, Map<String, CookieValue>> transientMapByHost = new HashMap<String, Map<String, CookieValue>>();

	static {
		// Note: Using yy in case years are given as two digits.
		// Note: Must use US locale for cookie dates.
		Locale locale = Locale.US;
		SimpleDateFormat ef1 = new SimpleDateFormat(
				"EEE, dd MMM yy HH:mm:ss 'GMT'", locale);
		SimpleDateFormat ef2 = new SimpleDateFormat(
				"EEE, dd-MMM-yy HH:mm:ss 'GMT'", locale);
		SimpleDateFormat ef3 = new SimpleDateFormat(
				"EEE MMM dd HH:mm:ss yy 'GMT'", locale);
		TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
		ef1.setTimeZone(gmtTimeZone);
		ef2.setTimeZone(gmtTimeZone);
		ef3.setTimeZone(gmtTimeZone);
		EXPIRES_FORMAT = ef1;
		EXPIRES_FORMAT_BAK1 = ef2;
		EXPIRES_FORMAT_BAK2 = ef3;
	}

	private CookieStore() {
	}

	public static CookieStore getInstance() {
		return instance;
	}

	public void saveCookie(URL url, String cookieSpec) {
		this.saveCookie(url.getHost(), cookieSpec);
	}

	public void saveCookie(String urlHostName, String cookieSpec) {
		// TODO: SECURITY
		if (logger.isLoggable(Level.INFO)) {
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
			String name = idx == -1 ? token.trim() : token.substring(0, idx).trim();
			String value = idx == -1 ? "" : Strings.unquote(token.substring(idx + 1).trim());
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
			logger.log(Level.SEVERE, "saveCookie(): Invalid cookie spec from '"
					+ urlHostName + "'");
			return;
		}
		if (path == null || path.length() == 0) {
			path = "/";
		}
		if (domain != null) {
			if (expires == null && maxAge == null && logger.isLoggable(Level.INFO)) {
				logger.log(Level.INFO,
						"saveCookie(): Not rejecting transient cookie that specifies domain '"
								+ domain + "'.");
			}
			if (!Domains.isValidCookieDomain(domain, urlHostName)) {
				logger.log(Level.WARNING,
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
						+ Integer.parseInt(maxAge) * 1000);
			} catch (NumberFormatException nfe) {
				logger.log(Level.WARNING,
						"saveCookie(): Max-age is not formatted correctly: "
								+ maxAge + ".");
			}
		} else if (expires != null) {
			synchronized (EXPIRES_FORMAT) {
				try {
					expiresDate = EXPIRES_FORMAT.parse(expires);
				} catch (Exception pe) {
					if (logger.isLoggable(Level.INFO)) {
						logger.log(Level.INFO,
								"saveCookie(): Bad date format: " + expires
										+ ". Will try again.", pe);
					}
					try {
						expiresDate = EXPIRES_FORMAT_BAK1.parse(expires);
					} catch (Exception pe2) {
						try {
							expiresDate = EXPIRES_FORMAT_BAK2.parse(expires);
						} catch (Exception pe3) {
							logger.log(Level.SEVERE,
									"saveCookie(): Giving up on cookie date format: "
											+ expires, pe3);
							return;
						}
					}
				}
			}
		}
		this.saveCookie(domain, path, cookieName, expiresDate, cookieValue);
	}

	public void saveCookie(String domain, String path, String name, Date expires, String value) {
		// TODO: SECURITY
		if (logger.isLoggable(Level.INFO)) {
			logger.info("saveCookie(): domain=" + domain + ",name=" + name
					+ ",expires=" + expires + ",value=[" + value + "].");
		}
		Long expiresLong = expires == null ? null : expires.getTime();
		CookieValue cookieValue = new CookieValue(value, path, domain,expiresLong);
		synchronized (this) {
			// Always save a transient cookie. It acts as a cache.
			Map<String, CookieValue> hostMap = this.transientMapByHost.get(domain);
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
				logger.log(Level.WARNING,
						"saveCookie(): Unable to save cookie named '" + name
								+ "' with domain '" + domain + "'", ioe);
			}
		}
	}

	private String getPathFromCookieName(String cookieName) {
		return COOKIE_PATH_PREFIX + cookieName;
	}

	private String getCookieNameFromPath(String path) {
		if (!path.startsWith(COOKIE_PATH_PREFIX)) {
			throw new IllegalArgumentException("Invalid path: " + path);
		}
		return path.substring(COOKIE_PATH_PREFIX.length());
	}

	/**
	 * Gets cookies belonging exactly to the host name given, not to a broader
	 * domain.
	 */
	private Collection<Cookie> getCookiesStrict(String hostName, String path) {
		if (path == null || path.length() == 0) {
			path = "/";
		}
		boolean liflag = logger.isLoggable(Level.INFO);
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
							cookies.add(new Cookie(cookieName, cookieValue.getValue(),cookieValue.getPath(),cookieValue.getDomain(),cookieValue.getExpires()));
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
								if (logger.isLoggable(Level.INFO)) {
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
									cookies.add(new Cookie(cookieName, cookieValue.getValue(),cookieValue.getPath(),cookieValue.getDomain(),cookieValue.getExpires()));
								} else {
									if (logger.isLoggable(Level.INFO)) {
										logger.info("getCookiesStrict(): Skipping cookie "
												+ cookieValue
												+ " since it does not match path "
												+ path);
									}
								}
							}
						} else {
							logger.warning("getCookiesStrict(): Expected to find cookie named "
									+ cookieName + " but file is missing.");
						}
					}
				}
			}
		} catch (IOException ioe) {
			logger.log(Level.SEVERE, "getCookiesStrict()", ioe);
		} catch (ClassNotFoundException cnf) {
			logger.log(Level.SEVERE,
					"getCookiesStrict(): Possible engine versioning error.",
					cnf);
		}
		return cookies;
	}

	public Collection<Cookie> getCookies(String hostName, String path) {
		// Security provided by RestrictedStore.
		Collection<String> possibleDomains = Domains
				.getPossibleDomains(hostName);
		Collection<Cookie> cookies = new LinkedList<Cookie>();
		for (String domain : possibleDomains) {
			cookies.addAll(this.getCookiesStrict(domain, path));
		}
		if (logger.isLoggable(Level.INFO)) {
			logger.info("getCookies(): For host=" + hostName + ", found "
					+ cookies.size() + " cookies: " + cookies);
		}
		return cookies;
	}
}
