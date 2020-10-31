package org.loboevolution.http;

import org.loboevolution.net.Cookie;
import org.loboevolution.store.CookieStore;
import org.loboevolution.store.GeneralStore;

import java.net.Proxy;
import java.net.URL;
import java.util.List;

/**
 * <p>UserAgentContext class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class UserAgentContext {
	
	/**
	 * <p>createHttpRequest.</p>
	 *
	 * @return a {@link org.loboevolution.http.HttpRequest} object.
	 */
	public HttpRequest createHttpRequest() {
		return new HttpRequest(this, Proxy.NO_PROXY);
	}
	
	/**
	 * <p>isScriptingEnabled.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isScriptingEnabled() {
		GeneralStore settings = GeneralStore.getNetwork();
		return settings.isJs();
	}
	
	/**
	 * <p>isExternalCSSEnabled.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isExternalCSSEnabled() {
		GeneralStore settings = GeneralStore.getNetwork();
		return settings.isCss();
	}
	
	/**
	 * <p>isNavigationEnabled.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isNavigationEnabled() {
		GeneralStore settings = GeneralStore.getNetwork();
		return settings.isNavigation();
	}
	
	/**
	 * <p>getCookie.</p>
	 *
	 * @param url a {@link java.net.URL} object.
	 * @return a {@link java.lang.String} object.
	 */
	public String getCookie(URL url) {
		List<Cookie> cookies = CookieStore.getCookies(url.getHost(), url.getPath());
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
		CookieStore.saveCookie(url.toString(), cookieSpec);
	}
}
