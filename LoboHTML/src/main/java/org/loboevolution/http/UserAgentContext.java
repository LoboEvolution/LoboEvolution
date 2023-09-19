/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.http;

import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.info.GeneralInfo;
import org.loboevolution.net.Cookie;

import java.net.Proxy;
import java.net.URL;
import java.util.List;

/**
 * <p>UserAgentContext class.</p>
 */
public class UserAgentContext {

	private GeneralInfo settings;

	private final HtmlRendererConfig config;

	private boolean userAgentEnabled = false;

	public UserAgentContext(HtmlRendererConfig config){
		this.config = config;
		settings = config.getGeneralInfo();
	}

	public UserAgentContext(HtmlRendererConfig config, boolean test) {
		this.config = config;
		settings = !test ? config.getGeneralInfo() : new GeneralInfo();
		if (settings == null) settings = new GeneralInfo();
	}

	/**
	 * <p>createHttpRequest.</p>
	 *
	 * @return a {@link org.loboevolution.http.HttpRequest} object.
	 */
	public HttpRequest createHttpRequest() {
		return new HttpRequest(Proxy.NO_PROXY);
	}
	
	/**
	 * <p>isScriptingEnabled.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isScriptingEnabled() {
		return userAgentEnabled || settings.isJs();
	}
	
	/**
	 * <p>isExternalCSSEnabled.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isExternalCSSEnabled() {
		return userAgentEnabled ||settings.isCss();
	}
	
	/**
	 * <p>isNavigationEnabled.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isNavigationEnabled() {
		return userAgentEnabled ||settings.isNavigation();
	}


	public void setUserAgentEnabled(boolean userAgentEnabled) {
		this.userAgentEnabled = userAgentEnabled;
	}

	/**
	 * <p>isImagesEnabled.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isImagesEnabled() {
		return settings.isImage();
	}

	/**
	 * <p>getCookie.</p>
	 *
	 * @param url a {@link java.net.URL} object.
	 * @return a {@link java.lang.String} object.
	 */
	public String getCookie(URL url) {
		List<Cookie> cookies = config.getCookies(url.getHost(), url.getPath());
		StringBuilder cookieText = new StringBuilder();
		for (Cookie cookie : cookies) {
			cookieText.append(cookie.getName());
			cookieText.append('=');
			cookieText.append(cookie.getValue());
			cookieText.append(';');
		}
		return cookieText.toString();
	}
	
	/**
	 * <p>setCookie.</p>
	 *
	 * @param url a {@link java.net.URL} object.
	 * @param cookieSpec a {@link java.lang.String} object.
	 */
	public void setCookie(URL url, String cookieSpec) {
		config.saveCookie(url.toString(), cookieSpec);
	}
}
