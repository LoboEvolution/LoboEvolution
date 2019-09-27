package org.lobobrowser.http;

import java.net.Proxy;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lobo.net.Cookie;
import org.lobo.store.CookieStore;
import org.lobo.store.GeneralStore;

public class UserAgentContext {
	
	public HttpRequest createHttpRequest() {
		return new HttpRequest(this, Proxy.NO_PROXY);
	}
	
	public boolean isScriptingEnabled() {
		GeneralStore settings = GeneralStore.getNetwork();
		return settings.isJs();
	}
	
	public boolean isExternalCSSEnabled() {
		GeneralStore settings = GeneralStore.getNetwork();
		return settings.isCss();
	}
	
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
	
	public void setCookie(URL url, String cookieSpec) {
		CookieStore.saveCookie(url.toString(), cookieSpec);
	}
	
	public boolean isMedia(String mediaName) {
		final Set<String> mediaNames = new HashSet<String>();
		mediaNames.add("screen");
		mediaNames.add("tv");
		mediaNames.add("tty");
		mediaNames.add("all");
		return mediaNames.contains(mediaName.toLowerCase());
	}
}
