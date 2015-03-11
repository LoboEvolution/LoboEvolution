package org.lobobrowser.primary.clientlets.html;

import java.net.URL;
import java.security.Policy;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.html.HttpRequest;
import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.request.RequestEngine;
import org.lobobrowser.request.UserAgentImpl;
import org.lobobrowser.security.LocalSecurityPolicy;
import org.lobobrowser.ua.NavigatorFrame;


/**
 * The Class UserAgentContextImpl.
 */
public class UserAgentContextImpl implements UserAgentContext {
	
	/** The Constant logger. */
	private static final Logger logger = Logger
			.getLogger(UserAgentContextImpl.class.getName());
	
	/** The Constant mediaNames. */
	private static final Set<String> mediaNames = new HashSet<String>();
	
	/** The frame. */
	private final NavigatorFrame frame;

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
	 * @param frame the frame
	 */
	public UserAgentContextImpl(final NavigatorFrame frame) {
		this.frame = frame;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.UserAgentContext#isMedia(java.lang.String)
	 */
	public boolean isMedia(String mediaName) {
		return mediaNames.contains(mediaName.toLowerCase());
	}

	/**
	 * Warn.
	 *
	 * @param message the message
	 * @param throwable the throwable
	 */
	public void warn(String message, Throwable throwable) {
		logger.log(Level.WARNING, message, throwable);
	}

	/**
	 * Error.
	 *
	 * @param message the message
	 * @param throwable the throwable
	 */
	public void error(String message, Throwable throwable) {
		logger.log(Level.SEVERE, message, throwable);
	}

	/**
	 * Warn.
	 *
	 * @param message the message
	 */
	public void warn(String message) {
		logger.warning(message);
	}

	/**
	 * Error.
	 *
	 * @param message the message
	 */
	public void error(String message) {
		logger.log(Level.SEVERE, message);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.UserAgentContext#createHttpRequest()
	 */
	public HttpRequest createHttpRequest() {
		return new HttpRequestImpl(this.frame.createNetworkRequest());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#getAppCodeName()
	 */
	public String getAppCodeName() {
		return UserAgentImpl.getInstance().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#getAppMinorVersion()
	 */
	public String getAppMinorVersion() {
		return UserAgentImpl.getInstance().getMinorVersion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#getAppName()
	 */
	public String getAppName() {
		return UserAgentImpl.getInstance().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#getAppVersion()
	 */
	public String getAppVersion() {
		return UserAgentImpl.getInstance().getMajorVersion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#getBrowserLanguage()
	 */
	public String getBrowserLanguage() {
		return "EN"; // TODO
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#getPlatform()
	 */
	public String getPlatform() {
		return System.getProperty("os.name");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#getUserAgent()
	 */
	public String getUserAgent() {
		return UserAgentImpl.getInstance().getUserAgentString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.UserAgentContext#isCookieEnabled()
	 */
	public boolean isCookieEnabled() {
		// TODO: Settings
		return true;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.UserAgentContext#getCookie(java.net.URL)
	 */
	public String getCookie(URL url) {
		// Requires privileges.
		return RequestEngine.getInstance().getCookie(url);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.UserAgentContext#isScriptingEnabled()
	 */
	public boolean isScriptingEnabled() {
		// TODO: Settings
		return true;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.UserAgentContext#setCookie(java.net.URL, java.lang.String)
	 */
	public void setCookie(URL url, String cookieSpec) {
		// Requires privileges.
		RequestEngine.getInstance().setCookie(url, cookieSpec);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.UserAgentContext#getSecurityPolicy()
	 */
	public Policy getSecurityPolicy() {
		return LocalSecurityPolicy.getInstance();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.UserAgentContext#getScriptingOptimizationLevel()
	 */
	public int getScriptingOptimizationLevel() {
		// TODO: Settings
		return -1;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.UserAgentContext#getVendor()
	 */
	public String getVendor() {
		return "The Lobo Project";
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.UserAgentContext#getProduct()
	 */
	public String getProduct() {
		return this.getAppName();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.UserAgentContext#isExternalCSSEnabled()
	 */
	public boolean isExternalCSSEnabled() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.UserAgentContext#isInternalCSSEnabled()
	 */
	public boolean isInternalCSSEnabled() {
		return true;
	}
}
