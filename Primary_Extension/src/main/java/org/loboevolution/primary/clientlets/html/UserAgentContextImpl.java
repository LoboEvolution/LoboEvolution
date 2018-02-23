/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.primary.clientlets.html;

import java.net.URL;
import java.security.AccessController;
import java.security.Policy;
import java.security.PrivilegedAction;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.http.HttpRequest;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.request.RequestEngine;
import org.loboevolution.request.UserAgentImpl;
import org.loboevolution.security.LocalSecurityPolicy;
import org.loboevolution.settings.GeneralSettings;

/**
 * The Class UserAgentContextImpl.
 */
public class UserAgentContextImpl implements UserAgentContext {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(UserAgentContextImpl.class);

	/** The Constant mediaNames. */
	private static final Set<String> mediaNames = new HashSet<String>();

	static {
		// Media names supported here
		Set<String> mn = mediaNames;
		mn.add("screen");
		mn.add("tv");
		mn.add("tty");
		mn.add("all");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.UserAgentContext#isMedia(java.lang.String)
	 */
	@Override
	public boolean isMedia(String mediaName) {
		return mediaNames.contains(mediaName.toLowerCase());
	}

	/**
	 * Warn.
	 *
	 * @param message
	 *            the message
	 * @param throwable
	 *            the throwable
	 */
	public void warn(String message, Throwable throwable) {
		logger.error(message, throwable);
	}

	/**
	 * Error.
	 *
	 * @param message
	 *            the message
	 * @param throwable
	 *            the throwable
	 */
	public void error(String message, Throwable throwable) {
		logger.error( message, throwable);
	}

	/**
	 * Warn.
	 *
	 * @param message
	 *            the message
	 */
	public void warn(String message) {
		logger.warn(message);
	}

	/**
	 * Error.
	 *
	 * @param message
	 *            the message
	 */
	public void error(String message) {
		logger.error( message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.UserAgentContext#createHttpRequest()
	 */
	@Override
	public HttpRequest createHttpRequest() {
		return new HttpRequest();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.UserAgentContext#getAppCodeName()
	 */
	@Override
	public String getAppCodeName() {
		return UserAgentImpl.getInstance().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.UserAgentContext#getAppMinorVersion()
	 */
	@Override
	public String getAppMinorVersion() {
		return UserAgentImpl.getInstance().getMinorVersion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.UserAgentContext#getAppName()
	 */
	@Override
	public String getAppName() {
		return UserAgentImpl.getInstance().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.UserAgentContext#getAppVersion()
	 */
	@Override
	public String getAppVersion() {
		return UserAgentImpl.getInstance().getMajorVersion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.UserAgentContext#getBrowserLanguage()
	 */
	@Override
	public String getBrowserLanguage() {
		return "EN"; // TODO
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.UserAgentContext#getPlatform()
	 */
	@Override
	public String getPlatform() {
		return System.getProperty("os.name");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.UserAgentContext#getUserAgent()
	 */
	@Override
	public String getUserAgent() {
		return UserAgentImpl.getInstance().getUserAgentString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.UserAgentContext#isCookieEnabled()
	 */
	@Override
	public boolean isCookieEnabled() {
		// TODO: Settings
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.UserAgentContext#getCookie(java.net.URL)
	 */
	@Override
	public String getCookie(URL url) {
		// Requires privileges.
		return RequestEngine.getInstance().getCookie(url);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.UserAgentContext#isScriptingEnabled()
	 */
	@Override
	public boolean isScriptingEnabled() {
		GeneralSettings settings = AccessController
				.doPrivileged((PrivilegedAction<GeneralSettings>) () -> GeneralSettings.getInstance());
		return settings.isSpoofJS();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.UserAgentContext#setCookie(java.net.URL,
	 * java.lang.String)
	 */
	@Override
	public void setCookie(URL url, String cookieSpec) {
		// Requires privileges.
		RequestEngine.getInstance().setCookie(url, cookieSpec);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.UserAgentContext#getSecurityPolicy()
	 */
	@Override
	public Policy getSecurityPolicy() {
		return LocalSecurityPolicy.getInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.UserAgentContext#getScriptingOptimizationLevel()
	 */
	@Override
	public int getScriptingOptimizationLevel() {
		// TODO: Settings
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.UserAgentContext#getVendor()
	 */
	@Override
	public String getVendor() {
		return "The Lobo Evolution";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.UserAgentContext#getProduct()
	 */
	@Override
	public String getProduct() {
		return this.getAppName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.UserAgentContext#isExternalCSSEnabled()
	 */
	@Override
	public boolean isExternalCSSEnabled() {
		GeneralSettings settings = AccessController
				.doPrivileged((PrivilegedAction<GeneralSettings>) () -> GeneralSettings.getInstance());
		return settings.isSpoofCSS();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.UserAgentContext#isInternalCSSEnabled()
	 */
	@Override
	public boolean isInternalCSSEnabled() {
		GeneralSettings settings = AccessController
				.doPrivileged((PrivilegedAction<GeneralSettings>) () -> GeneralSettings.getInstance());
		return settings.isSpoofCSS();
	}
}
