/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Mar 5, 2005
 */
package org.lobobrowser.main;

import java.io.File;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLStreamHandler;
import java.security.Permission;
import java.security.Policy;
import java.util.EventObject;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.lobobrowser.gui.ConsoleModel;
import org.lobobrowser.gui.DefaultWindowFactory;
import org.lobobrowser.gui.FramePanel;
import org.lobobrowser.request.AuthenticatorImpl;
import org.lobobrowser.request.CookieHandlerImpl;
import org.lobobrowser.security.LocalSecurityManager;
import org.lobobrowser.security.LocalSecurityPolicy;
import org.lobobrowser.settings.GeneralSettings;
import org.lobobrowser.util.GenericEventListener;
import org.lobobrowser.util.SimpleThreadPool;
import org.lobobrowser.util.SimpleThreadPoolTask;
import org.lobobrowser.util.Urls;

;
/**
 * A singleton class that is used to initialize a browser session in the current
 * JVM. It can also be used to open a browser window.
 * 
 * @see #getInstance()
 */
public class PlatformInit {
	private static final String NATIVE_DIR_NAME = "native";
	private final SimpleThreadPool threadExecutor;
	private final GeneralSettings generalSettings;

	private PlatformInit() {
		this.threadExecutor = new SimpleThreadPool("MainThreadPool", 2, 10,
				60 * 1000);
		// One way to avoid a security exception.
		this.generalSettings = GeneralSettings.getInstance();
	}

	/**
	 * Intializes security by installing a security policy and a security
	 * manager. Programs that use the browser API should invoke this method (or
	 * {@link #init(boolean, boolean) init}) to prevent web content from having
	 * full access to the user's computer.
	 * 
	 * @see #addPrivilegedPermission(Permission)
	 */
	public void initSecurity() {
		// Set security policy and manager (essential)
		Policy.setPolicy(LocalSecurityPolicy.getInstance());
		System.setSecurityManager(new LocalSecurityManager());
	}

	/**
	 * Initializes the global URLStreamHandlerFactory.
	 * <p>
	 * This method is invoked by {@link #init(boolean, boolean)}.
	 */
	public void initProtocols() {
		// Configure URL protocol handlers
		PlatformStreamHandlerFactory factory = PlatformStreamHandlerFactory
				.getInstance();
		URL.setURLStreamHandlerFactory(factory);
		factory.addFactory(new LocalStreamHandlerFactory());
	}

	/**
	 * Initializes the HTTP authenticator and the cookie handler. This is
	 * essential for the browser to work properly.
	 * <p>
	 * This method is invoked by {@link #init(boolean, boolean)}.
	 */
	public void initHTTP() {
		// Configure authenticator
		Authenticator.setDefault(new AuthenticatorImpl());
		// Configure cookie handler
		CookieHandler.setDefault(new CookieHandlerImpl());
	}

	/**
	 * Initializes the Swing look feel.
	 */
	public void initLookAndFeel() throws Exception {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {

				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}

	}

	public boolean isCodeLocationDirectory() {
		URL codeLocation = this.getClass().getProtectionDomain()
				.getCodeSource().getLocation();
		return Urls.isLocalFile(codeLocation)
				&& codeLocation.getPath().endsWith("/");
	}

	/**
	 * Resets standard output and error streams so they are redirected to the
	 * browser console.
	 * 
	 * @see ConsoleModel
	 */
	public void initConsole() {
		java.io.PrintStream oldOut = System.out;
		ConsoleModel standard = ConsoleModel.getStandard();
		java.io.PrintStream ps = standard.getPrintStream();
		System.setOut(ps);
		System.setErr(ps);
		if (this.isCodeLocationDirectory()) {
			// Should only be shown when running from Eclipse.
			oldOut.println("WARNING: initConsole(): Switching standard output and standard error to application console. If running EntryPoint, pass -debug to avoid this.");
		}
	}

	/**
	 * Initializes platform logging. Note that this method is not implicitly
	 * called by {@link #init(boolean, boolean)}.
	 * 
	 * @param debugOn
	 *            Debugging mode. This determines which one of two different
	 *            logging configurations is used.
	 */
	public void initLogging(boolean debugOn) throws Exception {
		// Set up debugging & console
		String loggingToken = debugOn ? "logging-debug" : "logging";
		InputStream in = this.getClass().getResourceAsStream(
				"/properties/" + loggingToken + ".properties");
		if (in == null) {
			in = this.getClass().getResourceAsStream(
					"properties/" + loggingToken + ".properties");
			if (in == null) {
				throw new java.io.IOException(
						"Unable to locate logging properties file.");
			}
		}
		try {
			java.util.logging.LogManager.getLogManager().readConfiguration(in);
		} finally {
			in.close();
		}
		// Configure log4j
		Logger logger = Logger.getLogger(PlatformInit.class.getName());
		if (logger.isLoggable(Level.INFO)) {
			logger.warning("Entry(): Logger INFO level is enabled.");
			java.util.Properties properties = System.getProperties();
			java.util.Iterator i = properties.entrySet().iterator();
			while (i.hasNext()) {
				Map.Entry entry = (Map.Entry) i.next();
				logger.info("main(): " + entry.getKey() + "="
						+ entry.getValue());
			}
		}
	}

	/**
	 * Initializes browser extensions. Invoking this method is essential to
	 * enable the primary extension and all basic browser functionality. This
	 * method is invoked by {@link #init(boolean, boolean)}.
	 */
	public void initExtensions() {
		ExtensionManager.getInstance().initExtensions();
	}

	/**
	 * Initializes the default window factory such that the JVM exits when all
	 * windows created by the factory are closed by the user.
	 */
	public void initWindowFactory(boolean exitWhenAllWindowsAreClosed) {
		DefaultWindowFactory.getInstance().setExitWhenAllWindowsAreClosed(
				exitWhenAllWindowsAreClosed);
	}

	/**
	 * Initializers the <code>java.library.path</code> property.
	 * <p>
	 * This method is called by {@link #init(boolean, boolean)}.
	 * 
	 * @param dirName
	 *            A directory name relative to the browser application
	 *            directory.
	 */
	public void initNative(String dirName) {
		File appDir = this.getApplicationDirectory();
		File nativeDir = new File(appDir, dirName);
		System.setProperty("java.library.path", nativeDir.getAbsolutePath());
	}

	/**
	 * Initializes some Java properties required by the browser.
	 * <p>
	 * This method is called by {@link #init(boolean, boolean)}.
	 */
	public void initOtherProperties() {
		// Required for array serialization in Java 6.
		System.setProperty("sun.lang.ClassLoader.allowArraySyntax", "true");
		// Don't cache host lookups for ever
		System.setProperty("networkaddress.cache.ttl", "3600");
		System.setProperty("networkaddress.cache.negative.ttl", "1");

	}

	/**
	 * Initializes security, protocols, look feel, console, the default window
	 * factory, extensions and <code>java.library.path</code>. This method
	 * should be invoked before using other functionality in the browser API. If
	 * this method is not called, at the very least
	 * {@link #initOtherProperties()}, {@link #initProtocols()} and
	 * {@link #initExtensions()} should be called.
	 * <p>
	 * Applications that need to install their own security manager and policy
	 * should not call this method.
	 * 
	 * @param exitWhenAllWindowsAreClosed
	 *            Whether the JVM should exit when all windows created by the
	 *            default window factory are closed.
	 * @param initConsole
	 *            If this parameter is <code>true</code>, standard output is
	 *            redirected to a browser console. See
	 *            {@link org.lobobrowser.gui.ConsoleModel}.
	 * @see #initSecurity()
	 * @see #initProtocols()
	 * @see #initExtensions()
	 */
	public void init(boolean exitWhenAllWindowsAreClosed, boolean initConsole)
			throws Exception {
		initOtherProperties();
		initNative(NATIVE_DIR_NAME);
		initSecurity();
		initProtocols();
		initHTTP();
		initLookAndFeel();
		if (initConsole) {
			initConsole();
		}
		initWindowFactory(exitWhenAllWindowsAreClosed);
		initExtensions();
	}

	/**
	 * Opens a window and attempts to render the URL or path given.
	 * 
	 * @param urlOrPath
	 *            A URL or file path.
	 * @throws MalformedURLException
	 */
	public void launch(String urlOrPath) throws MalformedURLException {
		URL url = org.lobobrowser.util.Urls.guessURL(urlOrPath);
		FramePanel.openWindow(null, url, null, new Properties(), "GET", null);
	}

	/**
	 * Opens as many browser windows as there are startup URLs in general
	 * settings.
	 * 
	 * @see org.lobobrowser.settings.GeneralSettings#getStartupURLs()
	 * @throws MalformedURLException
	 */
	public void launch() throws MalformedURLException {
		SecurityManager sm = System.getSecurityManager();
		if (sm == null) {
			Logger logger = Logger.getLogger(PlatformInit.class.getName());
			logger.warning("launch(): Security manager not set!");
		}
		String[] startupURLs = this.generalSettings.getStartupURLs();
		for (String url : startupURLs) {
			this.launch(url);
		}
	}

	private boolean windowHasBeenShown = false;

	/**
	 * Starts the browser by opening the URLs specified in the command-line
	 * arguments provided. Non-option arguments are assumed to be URLs and
	 * opened in separate windows. If no arguments are found, the method
	 * launches URLs from general settings. This method will not return until at
	 * least one window has been shown.
	 * 
	 * @see org.lobobrowser.settings.GeneralSettings#getStartupURLs()
	 */
	public void start(String[] args) throws MalformedURLException {
		DefaultWindowFactory.getInstance().evtWindowShown
				.addListener(new GenericEventListener() {
					public void processEvent(EventObject event) {
						synchronized (PlatformInit.this) {
							windowHasBeenShown = true;
							PlatformInit.this.notifyAll();
						}
					}
				});
		boolean launched = false;
		for (int i = 0; i < args.length; i++) {
			String url = args[i];
			if (!url.startsWith("-")) {
				try {
					launched = true;
					this.launch(url);
				} catch (Exception err) {
					err.printStackTrace(System.err);
				}
			}
		}
		if (!launched) {
			this.launch();
		}
		synchronized (this) {
			while (!this.windowHasBeenShown) {
				try {
					this.wait();
				} catch (InterruptedException ie) {
					// Ignore
				}
			}
		}
	}

	private static final PlatformInit instance = new PlatformInit();

	/**
	 * Gets the singleton instance.
	 */
	public static PlatformInit getInstance() {
		return instance;
	}

	/**
	 * Performs some cleanup and then exits the JVM.
	 */
	public static void shutdown() {
		try {
			ReuseManager.getInstance().shutdown();
		} catch (Exception err) {
			err.printStackTrace(System.err);
		}
		System.exit(0);
	}

	/**
	 * Adds one permission to the base set of permissions assigned to privileged
	 * code, i.e. code loaded from the local system rather than a remote
	 * location. This method must be called before a security manager has been
	 * set, that is, before {@link #init(boolean, boolean)} or
	 * {@link #initSecurity()} are invoked. The purpose of the method is to add
	 * permissions otherwise missing from the security policy installed by this
	 * facility.
	 * 
	 * @param permission
	 *            A <code>Permission</code> instance.
	 */
	public void addPrivilegedPermission(Permission permission) {
		LocalSecurityPolicy.addPrivilegedPermission(permission);
	}

	public void scheduleTask(SimpleThreadPoolTask task) {
		this.threadExecutor.schedule(task);
	}

	private File applicationDirectory;

	public File getApplicationDirectory() {
		File appDir = this.applicationDirectory;
		if (appDir == null) {
			java.security.ProtectionDomain pd = this.getClass()
					.getProtectionDomain();
			java.security.CodeSource cs = pd.getCodeSource();
			URL url = cs.getLocation();
			String jarPath = url.getPath();
			File jarFile;
			try {
				jarFile = new File(url.toURI());
			} catch (URISyntaxException use) {
				throw new IllegalStateException(use);
			} catch (IllegalArgumentException iae) {
				throw new IllegalStateException(
						"Application code source apparently not a local JAR file: "
								+ url
								+ ". Only local JAR files are supported at the moment.",
						iae);
			}
			File installDir = jarFile.getParentFile();
			if (installDir == null) {
				throw new IllegalStateException(
						"Installation directory is missing. Startup JAR path is "
								+ jarPath + ".");
			}
			if (!installDir.exists()) {
				throw new IllegalStateException(
						"Installation directory not found. Startup JAR path is "
								+ jarPath + ". Directory path is "
								+ installDir.getAbsolutePath() + ".");
			}
			appDir = installDir;
			this.applicationDirectory = appDir;

			// Static logger should not be created in this class.
			Logger logger = Logger.getLogger(this.getClass().getName());
			if (logger.isLoggable(Level.INFO)) {
				logger.info("getApplicationDirectory(): url=" + url
						+ ",appDir=" + appDir);
			}
		}
		return appDir;
	}

	private static class LocalStreamHandlerFactory implements
			java.net.URLStreamHandlerFactory {
		public URLStreamHandler createURLStreamHandler(String protocol) {
			if (protocol.equals("res")) {
				return new org.lobobrowser.protocol.res.Handler();
			} else if (protocol.equals("vc")) {
				return new org.lobobrowser.protocol.vc.Handler();
			} else {
				return null;
			}
		}
	}

}
