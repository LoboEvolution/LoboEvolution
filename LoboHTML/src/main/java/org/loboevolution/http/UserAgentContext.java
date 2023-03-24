/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
