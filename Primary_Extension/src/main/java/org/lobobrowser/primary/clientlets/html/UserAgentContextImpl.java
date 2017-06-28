/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
package org.lobobrowser.primary.clientlets.html;

import java.net.URL;
import java.security.AccessController;
import java.security.Policy;
import java.security.PrivilegedAction;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.http.HttpRequest;
import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.request.RequestEngine;
import org.lobobrowser.request.UserAgentImpl;
import org.lobobrowser.security.LocalSecurityPolicy;
import org.lobobrowser.settings.GeneralSettings;
import org.lobobrowser.ua.NavigatorFrame;

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

	/**
	 * Instantiates a new user agent context impl.
	 *
	 * @param frame
	 *            the frame
	 */
	public UserAgentContextImpl(final NavigatorFrame frame) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#isMedia(java.lang.String)
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
		logger.log(Level.ERROR, message, throwable);
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
		logger.log(Level.ERROR, message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#createHttpRequest()
	 */
	@Override
	public HttpRequest createHttpRequest() {
		return new HttpRequest();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#getAppCodeName()
	 */
	@Override
	public String getAppCodeName() {
		return UserAgentImpl.getInstance().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#getAppMinorVersion()
	 */
	@Override
	public String getAppMinorVersion() {
		return UserAgentImpl.getInstance().getMinorVersion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#getAppName()
	 */
	@Override
	public String getAppName() {
		return UserAgentImpl.getInstance().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#getAppVersion()
	 */
	@Override
	public String getAppVersion() {
		return UserAgentImpl.getInstance().getMajorVersion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#getBrowserLanguage()
	 */
	@Override
	public String getBrowserLanguage() {
		return "EN"; // TODO
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#getPlatform()
	 */
	@Override
	public String getPlatform() {
		return System.getProperty("os.name");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#getUserAgent()
	 */
	@Override
	public String getUserAgent() {
		return UserAgentImpl.getInstance().getUserAgentString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#isCookieEnabled()
	 */
	@Override
	public boolean isCookieEnabled() {
		// TODO: Settings
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#getCookie(java.net.URL)
	 */
	@Override
	public String getCookie(URL url) {
		// Requires privileges.
		return RequestEngine.getInstance().getCookie(url);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#isScriptingEnabled()
	 */
	@Override
	public boolean isScriptingEnabled() {
		GeneralSettings settings = AccessController.doPrivileged(new PrivilegedAction<GeneralSettings>() {
			@Override
			public GeneralSettings run() {
				return GeneralSettings.getInstance();
			}
		});
		return settings.isSpoofJS();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#setCookie(java.net.URL,
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
	 * @see org.lobobrowser.html.UserAgentContext#getSecurityPolicy()
	 */
	@Override
	public Policy getSecurityPolicy() {
		return LocalSecurityPolicy.getInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.UserAgentContext#getScriptingOptimizationLevel()
	 */
	@Override
	public int getScriptingOptimizationLevel() {
		// TODO: Settings
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#getVendor()
	 */
	@Override
	public String getVendor() {
		return "The Lobo Evolution";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#getProduct()
	 */
	@Override
	public String getProduct() {
		return this.getAppName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#isExternalCSSEnabled()
	 */
	@Override
	public boolean isExternalCSSEnabled() {
		GeneralSettings settings = AccessController.doPrivileged(new PrivilegedAction<GeneralSettings>() {
			@Override
			public GeneralSettings run() {
				return GeneralSettings.getInstance();
			}
		});
		return settings.isSpoofCSS();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#isInternalCSSEnabled()
	 */
	@Override
	public boolean isInternalCSSEnabled() {
		GeneralSettings settings = AccessController.doPrivileged(new PrivilegedAction<GeneralSettings>() {
			@Override
			public GeneralSettings run() {
				return GeneralSettings.getInstance();
			}
		});
		return settings.isSpoofCSS();
	}
}
