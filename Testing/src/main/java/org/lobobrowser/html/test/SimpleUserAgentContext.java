/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
package org.lobobrowser.html.test;

import java.net.Proxy;
import java.net.URL;
import java.security.Policy;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.http.HttpRequest;
import org.lobobrowser.http.UserAgentContext;

/**
 * Simple implementation of {@link org.lobobrowser.http.UserAgentContext}. This
 * class is provided for user convenience. Usually this class should be extended
 * in order to provide appropriate user agent information and more robust
 * content loading routines. Its setters can be called to modify certain user
 * agent defaults.
 */
public class SimpleUserAgentContext implements UserAgentContext {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(SimpleUserAgentContext.class.getName());

	/** The Constant mediaNames. */
	private static final Set<String> mediaNames = new HashSet<String>();

	static {
		// Media names claimed by this context.
		Set<String> mn = mediaNames;
		mn.add("screen");
		mn.add("tv");
		mn.add("tty");
		mn.add("all");
	}

	/**
	 * This implementation returns true for certain media names, such as
	 * <code>screen</code>.
	 *
	 * @param mediaName
	 *            the media name
	 * @return true, if is media
	 */
	@Override
	public boolean isMedia(String mediaName) {
		return mediaNames.contains(mediaName.toLowerCase());
	}

	/**
	 * Creates a {@link org.lobobrowser.html.test.SimpleHttpRequest} instance.
	 * The {@link org.lobobrowser.http.HttpRequest} object returned by this
	 * method is used to load images, scripts, style sheets, and to implement
	 * the Javascript XMLHttpRequest class. Override if a custom mechanism to
	 * make requests is needed.
	 *
	 * @return the http request
	 */
	@Override
	public HttpRequest createHttpRequest() {
		return new HttpRequest();// TODO new SimpleHttpRequest(this,
									// this.getProxy());
	}

	/** The proxy. */
	private java.net.Proxy proxy = java.net.Proxy.NO_PROXY;

	/**
	 * Gets the proxy.
	 *
	 * @return the proxy
	 */
	protected java.net.Proxy getProxy() {
		return this.proxy;
	}

	/**
	 * Sets the proxy.
	 *
	 * @param proxy
	 *            the new proxy
	 */
	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

	/** The app code name. */
	private String appCodeName = "Cobra";

	/**
	 * Returns the application "code name." This implementation returns the
	 * value of a local field.
	 *
	 * @return the app code name
	 * @see #setAppCodeName(String)
	 */
	@Override
	public String getAppCodeName() {
		return this.appCodeName;
	}

	/**
	 * Sets the app code name.
	 *
	 * @param appCodeName
	 *            the new app code name
	 */
	public void setAppCodeName(String appCodeName) {
		this.appCodeName = appCodeName;
	}

	/** The app minor version. */
	private String appMinorVersion = "0";

	/**
	 * Gets the "minor version" of the application. This implementation returns
	 * the value of a local field.
	 *
	 * @return the app minor version
	 * @see #setAppMinorVersion(String)
	 */
	@Override
	public String getAppMinorVersion() {
		return this.appMinorVersion;
	}

	/**
	 * Sets the app minor version.
	 *
	 * @param appMinorVersion
	 *            the new app minor version
	 */
	public void setAppMinorVersion(String appMinorVersion) {
		this.appMinorVersion = appMinorVersion;
	}

	/** The app name. */
	private String appName = "Cobra";

	/**
	 * Gets the application name. This implementation returns the value of a
	 * local field.
	 *
	 * @return the app name
	 * @see #setAppName(String)
	 */
	@Override
	public String getAppName() {
		return this.appName;
	}

	/**
	 * Sets the app name.
	 *
	 * @param appName
	 *            the new app name
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/** The app version. */
	private String appVersion = "1";

	/**
	 * Gets the major application version. This implementation returns the value
	 * of a local field.
	 *
	 * @return the app version
	 * @see #setAppVersion(String)
	 */
	@Override
	public String getAppVersion() {
		return this.appVersion;
	}

	/**
	 * Sets the app version.
	 *
	 * @param appVersion
	 *            the new app version
	 */
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	/**
	 * Get the browser language. This implementation returns the language of the
	 * default locale. It may be overridden to provide a different value.
	 *
	 * @return the browser language
	 */
	@Override
	public String getBrowserLanguage() {
		return Locale.getDefault().getLanguage();
	}

	/**
	 * Returns the value of Java property <code>os.name</code>. It may be
	 * overridden to provide a different value.
	 *
	 * @return the platform
	 */
	@Override
	public String getPlatform() {
		return System.getProperty("os.name");
	}

	/** The user agent. */
	private String userAgent = "Mozilla/4.0 (compatible; MSIE 6.0;) Cobra/Simple";

	/**
	 * Gets the User-Agent string. This implementation returns the value of a
	 * local field.
	 *
	 * @return the user agent
	 * @see #setUserAgent(String)
	 */
	@Override
	public String getUserAgent() {
		return this.userAgent;
	}

	/**
	 * Sets the user agent.
	 *
	 * @param userAgent
	 *            the new user agent
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * This implementation returns true if and only if
	 * <code>java.net.CookieHandler.getDefault()</code> is returning a non-null
	 * value. The method may be overridden to provide an alternative means of
	 * determining cookie enabling state.
	 *
	 * @return true, if is cookie enabled
	 */
	@Override
	public boolean isCookieEnabled() {
		return java.net.CookieHandler.getDefault() != null;
	}

	/**
	 * This implementation uses the default <code>java.net.CookieHandler</code>,
	 * if any, to get cookie information for the given URL. If no cookie handler
	 * is available, this method returns the empty string.
	 *
	 * @param url
	 *            the url
	 * @return the cookie
	 */
	@Override
	public String getCookie(URL url) {
		java.net.CookieHandler handler = java.net.CookieHandler.getDefault();
		if (handler == null) {
			return "";
		}
		Map<?, ?> results;
		try {
			results = handler.get(url.toURI(), new HashMap<String, List<String>>());
		} catch (Exception err) {
			logger.log(Level.WARNING, "getCookie()", err);
			return "";
		}
		if (results == null) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		Iterator<?> i = results.entrySet().iterator();
		boolean firstTime = true;
		while (i.hasNext()) {
			Map.Entry entry = (Map.Entry) i.next();
			String key = (String) entry.getKey();
			if ("Cookie".equalsIgnoreCase(key) || "Cookie2".equalsIgnoreCase(key)) {
				List<?> list = (List<?>) entry.getValue();
				Iterator<?> li = list.iterator();
				while (li.hasNext()) {
					String value = (String) li.next();
					if (firstTime) {
						firstTime = false;
					} else {
						buffer.append("; ");
					}
					buffer.append(value);
				}
			}
		}
		return buffer.toString();
	}

	/** The scripting enabled. */
	private boolean scriptingEnabled = true;

	/**
	 * Determines whether scripting should be enabled. This implementation
	 * returns the value of a local field defaulting to <code>true</code>.
	 *
	 * @return true, if is scripting enabled
	 * @see #setScriptingEnabled(boolean)
	 */
	@Override
	public boolean isScriptingEnabled() {
		return this.scriptingEnabled;
	}

	/**
	 * Sets the scripting enabled.
	 *
	 * @param enable
	 *            the new scripting enabled
	 */
	public void setScriptingEnabled(boolean enable) {
		this.scriptingEnabled = enable;
	}

	/**
	 * This method uses the default CookieHandler, if one is available, to set a
	 * cookie value.
	 *
	 * @param url
	 *            the url
	 * @param cookieSpec
	 *            the cookie spec
	 */
	@Override
	public void setCookie(URL url, String cookieSpec) {
		java.net.CookieHandler handler = java.net.CookieHandler.getDefault();
		if (handler == null) {
			return;
		}
		Map<String, List<String>> headers = new HashMap<String, List<String>>(2);
		headers.put("Set-Cookie", Collections.singletonList(cookieSpec));
		try {
			handler.put(url.toURI(), headers);
		} catch (Exception err) {
			logger.log(Level.WARNING, "setCookie()", err);
		}
	}

	/**
	 * Returns <code>null</code>. This method must be overridden if JavaScript
	 * code is untrusted.
	 *
	 * @return the security policy
	 */
	@Override
	public Policy getSecurityPolicy() {
		return null;
	}

	/** The scripting optimization level. */
	private int scriptingOptimizationLevel = -1;

	/**
	 * Gets the Rhino optimization level. This implementation returns the value
	 * of a local field defaulting to <code>-1</code>.
	 *
	 * @return the scripting optimization level
	 * @see #setScriptingOptimizationLevel(int)
	 */
	@Override
	public int getScriptingOptimizationLevel() {
		return this.scriptingOptimizationLevel;
	}

	/**
	 * Sets the scripting optimization level.
	 *
	 * @param level
	 *            the new scripting optimization level
	 */
	public void setScriptingOptimizationLevel(int level) {
		this.scriptingOptimizationLevel = level;
	}

	/** The vendor. */
	private String vendor = "The Lobo Project";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#getVendor()
	 */
	@Override
	public String getVendor() {
		return this.vendor;
	}

	/**
	 * Sets the vendor.
	 *
	 * @param vendor
	 *            the new vendor
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	/** The product. */
	private String product = "Cobra";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#getProduct()
	 */
	@Override
	public String getProduct() {
		return this.product;
	}

	/**
	 * Sets the product.
	 *
	 * @param product
	 *            the new product
	 */
	public void setProduct(String product) {
		this.product = product;
	}

	/** The external css enabled. */
	private boolean externalCSSEnabled = true;

	/**
	 * Determines whether loading of CSS files should be enabled. This
	 * implementation returns the value of a local field defaulting to
	 * <code>true</code>.
	 *
	 * @return true, if is external css enabled
	 * @see #setExternalCSSEnabled(boolean)
	 */
	@Override
	public boolean isExternalCSSEnabled() {
		return this.externalCSSEnabled;
	}

	/**
	 * Sets the external css enabled.
	 *
	 * @param enabled
	 *            the new external css enabled
	 */
	public void setExternalCSSEnabled(boolean enabled) {
		this.externalCSSEnabled = enabled;
	}

	/** The internal css enabled. */
	private boolean internalCSSEnabled = true;

	/**
	 * Determines whether STYLE tags should be processed. This implementation
	 * returns the value of a local field defaulting to <code>true</code>.
	 *
	 * @return true, if is internal css enabled
	 * @see #setInternalCSSEnabled(boolean)
	 */
	@Override
	public boolean isInternalCSSEnabled() {
		return internalCSSEnabled;
	}

	/**
	 * Sets the internal css enabled.
	 *
	 * @param internalCSSEnabled
	 *            the new internal css enabled
	 */
	public void setInternalCSSEnabled(boolean internalCSSEnabled) {
		this.internalCSSEnabled = internalCSSEnabled;
	}
}
