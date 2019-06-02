package org.lobobrowser.http;

import java.net.CookieHandler;
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

public class UserAgentContext {
	private static final Logger logger = Logger.getLogger(UserAgentContext.class.getName());
	private static final Set<String> mediaNames = new HashSet<String>();

	static {
		// Media names claimed by this context.
		final Set<String> mn = mediaNames;
		mn.add("screen");
		mn.add("tv");
		mn.add("tty");
		mn.add("all");
	}

	private String appCodeName = "Cobra";

	private String appMinorVersion = "0";

	private String appName = "Cobra";

	private String appVersion = "1";

	private boolean externalCSSEnabled = true;

	private String product = "Cobra";

	private Proxy proxy = java.net.Proxy.NO_PROXY;

	private boolean scriptingEnabled = true;

	private int scriptingOptimizationLevel = -1;

	private String userAgent = "Mozilla/4.0 (compatible; MSIE 6.0;) Cobra/Simple";

	private String vendor = "The Lobo Project";

	/**
	 * Creates a {@link org.lobobrowser.http.SimpleHttpRequest} instance. The
	 * {@link org.lobobrowser.http.HttpRequest}</code> object returned by this
	 * method is used to load images, scripts, style sheets, and to implement the
	 * Javascript XMLHttpRequest class. Override if a custom mechanism to make
	 * requests is needed.
	 */
	public HttpRequest createHttpRequest() {
		return new HttpRequest(this, getProxy());
	}

	/**
	 * Returns the application "code name." This implementation returns the value of
	 * a local field.
	 * 
	 * @see #setAppCodeName(String)
	 */
	public String getAppCodeName() {
		return this.appCodeName;
	}

	/**
	 * Gets the "minor version" of the application. This implementation returns the
	 * value of a local field.
	 * 
	 * @see #setAppMinorVersion(String)
	 */
	public String getAppMinorVersion() {
		return this.appMinorVersion;
	}

	/**
	 * Gets the application name. This implementation returns the value of a local
	 * field.
	 * 
	 * @see #setAppName(String)
	 */
	public String getAppName() {
		return this.appName;
	}

	/**
	 * Gets the major application version. This implementation returns the value of
	 * a local field.
	 * 
	 * @see #setAppVersion(String)
	 */
	public String getAppVersion() {
		return this.appVersion;
	}

	/**
	 * Get the browser language. This implementation returns the language of the
	 * default locale. It may be overridden to provide a different value.
	 */
	public String getBrowserLanguage() {
		return Locale.getDefault().getLanguage();
	}

	/**
	 * This implementation uses the default <code>java.net.CookieHandler</code>, if
	 * any, to get cookie information for the given URL. If no cookie handler is
	 * available, this method returns the empty string.
	 */
	public String getCookie(URL url) {
		final CookieHandler handler = java.net.CookieHandler.getDefault();
		if (handler == null) {
			return "";
		}
		Map<?, ?> results;
		try {
			results = handler.get(url.toURI(), new HashMap<String, List<String>>());
		} catch (final Exception err) {
			logger.log(Level.WARNING, "getCookie()", err);
			return "";
		}
		if (results == null) {
			return "";
		}
		final StringBuffer buffer = new StringBuffer();
		final Iterator<?> i = results.entrySet().iterator();
		boolean firstTime = true;
		while (i.hasNext()) {
			final Map.Entry entry = (Map.Entry) i.next();
			final String key = (String) entry.getKey();
			if ("Cookie".equalsIgnoreCase(key) || "Cookie2".equalsIgnoreCase(key)) {
				final List<?> list = (List<?>) entry.getValue();
				final Iterator<?> li = list.iterator();
				while (li.hasNext()) {
					final String value = (String) li.next();
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

	/**
	 * Returns the value of Java property <code>os.name</code>. It may be overridden
	 * to provide a different value.
	 */
	public String getPlatform() {
		return System.getProperty("os.name");
	}

	public String getProduct() {
		return this.product;
	}

	/**
	 * Gets the connection proxy used in requests created by
	 * {@link #createHttpRequest()} by default. This implementation returns the
	 * value of a local field.
	 * 
	 * @see #setProxy(java.net.Proxy)
	 */
	public Proxy getProxy() {
		return this.proxy;
	}

	/**
	 * Gets the Rhino optimization level. This implementation returns the value of a
	 * local field defaulting to <code>-1</code>.
	 * 
	 * @see #setScriptingOptimizationLevel(int)
	 */
	public int getScriptingOptimizationLevel() {
		return this.scriptingOptimizationLevel;
	}

	/**
	 * Returns <code>null</code>. This method must be overridden if JavaScript code
	 * is untrusted.
	 */
	public Policy getSecurityPolicy() {
		return null;
	}

	/**
	 * Gets the User-Agent string. This implementation returns the value of a local
	 * field.
	 * 
	 * @see #setUserAgent(String)
	 */
	public String getUserAgent() {
		return this.userAgent;
	}

	public String getVendor() {
		return this.vendor;
	}

	/**
	 * This implementation returns true if and only if
	 * <code>java.net.CookieHandler.getDefault()</code> is returning a non-null
	 * value. The method may be overridden to provide an alternative means of
	 * determining cookie enabling state.
	 */
	public boolean isCookieEnabled() {
		return CookieHandler.getDefault() != null;
	}

	/**
	 * Determines whether external CSS loading should be enabled. This
	 * implementation returns the value of a local field defaulting to
	 * <code>true</code>.
	 * 
	 * @see #setExternalCSSEnabled(boolean)
	 */
	public boolean isExternalCSSEnabled() {
		return this.externalCSSEnabled;
	}

	public boolean isInternalCSSEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * This implementation returns true for certain media names, such as
	 * <code>screen</code>.
	 */
	public boolean isMedia(String mediaName) {
		return mediaNames.contains(mediaName.toLowerCase());
	}

	public boolean isNavigationEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Determines whether scripting should be enabled. This implementation returns
	 * the value of a local field defaulting to <code>true</code>.
	 * 
	 * @see #setScriptingEnabled(boolean)
	 */
	public boolean isScriptingEnabled() {
		return this.scriptingEnabled;
	}

	/**
	 * Sets the application code name normally returned by
	 * {@link #getAppCodeName()}.
	 * 
	 * @param appCodeName An application "code name."
	 */
	public void setAppCodeName(String appCodeName) {
		this.appCodeName = appCodeName;
	}

	/**
	 * Sets the value normally returned by {@link #getAppMinorVersion()}.
	 * 
	 * @param appMinorVersion The application's "minor version."
	 */
	public void setAppMinorVersion(String appMinorVersion) {
		this.appMinorVersion = appMinorVersion;
	}

	/**
	 * Sets the value normally returned by {@link #getAppName()}.
	 * 
	 * @param appName The application name.
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * Sets the value normally returned by {@link #getAppVersion()}.
	 * 
	 * @param appVersion The application version.
	 */
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	/**
	 * This method uses the default CookieHandler, if one is available, to set a
	 * cookie value.
	 */
	public void setCookie(URL url, String cookieSpec) {
		final java.net.CookieHandler handler = java.net.CookieHandler.getDefault();
		if (handler == null) {
			return;
		}
		final Map<String, List<String>> headers = new HashMap<String, List<String>>(2);
		headers.put("Set-Cookie", Collections.singletonList(cookieSpec));
		try {
			handler.put(url.toURI(), headers);
		} catch (final Exception err) {
			logger.log(Level.WARNING, "setCookie()", err);
		}
	}

	/**
	 * Sets the value normally returned by {@link #isExternalCSSEnabled()}.
	 * 
	 * @param enabled A boolean value.
	 */
	public void setExternalCSSEnabled(boolean enabled) {
		this.externalCSSEnabled = enabled;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	/**
	 * Sets the value of the proxy normally returned by {@link #getProxy()}.
	 * 
	 * @param proxy A <code>java.net.Proxy</code> instance.
	 */
	public void setProxy(java.net.Proxy proxy) {
		this.proxy = proxy;
	}

	/**
	 * Sets the value normally returned by {@link #isScriptingEnabled()}.
	 * 
	 * @param enable A boolean value.
	 */
	public void setScriptingEnabled(boolean enable) {
		this.scriptingEnabled = enable;
	}

	/**
	 * Sets the value normally returned by {@link #getScriptingOptimizationLevel()}.
	 * 
	 * @param level A Rhino optimization level.
	 */
	public void setScriptingOptimizationLevel(int level) {
		this.scriptingOptimizationLevel = level;
	}

	/**
	 * Sets the value normally returned by {@link #getUserAgent()}.
	 * 
	 * @param userAgent A User-Agent string.
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
}
