/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
package org.lobobrowser.http;

import java.net.URL;

/**
 * Provides information about the user agent (browser) driving the parser and/or
 * renderer.
 * <p>
 * A simple implementation of this interface is provided in
 * {@link org.lobobrowser.html.test.SimpleUserAgentContext}.
 *
 * @see HtmlRendererContext#getUserAgentContext()
 * @see org.lobobrowser.html.parser.DocumentBuilderImpl#DocumentBuilderImpl(UserAgentContext)
 */
public interface UserAgentContext {

	String DEFAULT_USER_AGENT = "Mozilla/5.0 (compatible;) Cobra/0.97+";

	/**
	 * Creates an instance of {@link org.lobobrowser.http.HttpRequest} which can
	 * be used by the renderer to load images, scripts, external style sheets,
	 * and implement the Javascript XMLHttpRequest class (AJAX).
	 *
	 * @return the http request
	 */
	HttpRequest createHttpRequest();

	/**
	 * Gets the app code name.
	 *
	 * @return the app code name
	 */
	String getAppCodeName();

	/**
	 * Gets the app name.
	 *
	 * @return the app name
	 */
	String getAppName();

	/**
	 * Gets the app version.
	 *
	 * @return the app version
	 */
	String getAppVersion();

	/**
	 * Gets the app minor version.
	 *
	 * @return the app minor version
	 */
	String getAppMinorVersion();

	/**
	 * Gets the browser language.
	 *
	 * @return the browser language
	 */
	String getBrowserLanguage();

	/**
	 * Checks if is cookie enabled.
	 *
	 * @return true, if is cookie enabled
	 */
	boolean isCookieEnabled();

	/**
	 * Checks if is scripting enabled.
	 *
	 * @return true, if is scripting enabled
	 */
	boolean isScriptingEnabled();

	/**
	 * Checks if is external css enabled.
	 *
	 * @return true, if is external css enabled
	 */
	boolean isExternalCSSEnabled();

	/**
	 * Checks if is internal css enabled.
	 *
	 * @return true, if is internal css enabled
	 */
	boolean isInternalCSSEnabled();

	/**
	 * Gets the platform.
	 *
	 * @return the platform
	 */
	String getPlatform();

	/**
	 * Gets the user agent.
	 *
	 * @return the user agent
	 */
	String getUserAgent();

	/**
	 * Method used to implement Javascript <code>document.cookie</code>
	 * property.
	 *
	 * @param url
	 *            the url
	 * @return the cookie
	 */
	String getCookie(URL url);

	/**
	 * Method used to implement <code>document.cookie</code> property.
	 *
	 * @param url
	 *            the url
	 * @param cookieSpec
	 *            Specification of cookies, as they would appear in the
	 *            Set-Cookie header value of HTTP.
	 */
	void setCookie(URL url, String cookieSpec);

	/**
	 * Gets the security policy.
	 *
	 * @return the security policy
	 */
	java.security.Policy getSecurityPolicy();

	/**
	 * Gets the scripting optimization level.
	 *
	 * @return the scripting optimization level
	 */
	int getScriptingOptimizationLevel();

	/**
	 * Returns true if the current media matches the name provided.
	 *
	 * @param mediaName
	 *            Media name, which may be <code>screen</code>, <code>tty</code>
	 *            , etc. (See <a href=
	 *            "http://www.w3.org/TR/REC-html40/types.html#type-media-descriptors"
	 *            >HTML Specification</a>).
	 * @return true, if is media
	 */
	boolean isMedia(String mediaName);

	/**
	 * Gets the vendor.
	 *
	 * @return the vendor
	 */
	String getVendor();

	/**
	 * Gets the product.
	 *
	 * @return the product
	 */
	String getProduct();
}
