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
/*
 * Created on Mar 5, 2005
 */
package org.loboevolution.main;

import java.io.File;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.security.CodeSource;
import java.security.Permission;
import java.security.Policy;
import java.security.ProtectionDomain;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.swing.UIManager;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.font.LAFSettings;
import org.loboevolution.gui.DefaultWindowFactory;
import org.loboevolution.gui.FramePanel;
import org.loboevolution.request.AuthenticatorImpl;
import org.loboevolution.request.CookieManager;
import org.loboevolution.security.LocalSecurityManager;
import org.loboevolution.security.LocalSecurityPolicy;
import org.loboevolution.settings.GeneralSettings;
import org.loboevolution.util.SimpleThreadPool;
import org.loboevolution.util.SimpleThreadPoolTask;
import org.loboevolution.util.Urls;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.jtattoo.plaf.aero.AeroLookAndFeel;
import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import com.jtattoo.plaf.fast.FastLookAndFeel;
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import com.jtattoo.plaf.luna.LunaLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.jtattoo.plaf.noire.NoireLookAndFeel;
import com.jtattoo.plaf.smart.SmartLookAndFeel;
import com.jtattoo.plaf.texture.TextureLookAndFeel;

/**
 * A singleton class that is used to initialize a browser session in the current
 * JVM. It can also be used to open a browser window.
 *
 * @see #getInstance()
 */
public class PlatformInit {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(PlatformInit.class);

	/** The Constant NATIVE_DIR_NAME. */
	private static final String NATIVE_DIR_NAME = "native";

	/** The thread executor. */
	private final SimpleThreadPool threadExecutor;

	/** The general settings. */
	private final GeneralSettings generalSettings;
	
	/** The application directory. */
	private File applicationDirectory;

	/** The window has been shown. */
	private boolean windowHasBeenShown = false;
	
	/** The Constant instance. */
	private static final PlatformInit instance = new PlatformInit();

	/**
	 * Instantiates a new platform init.
	 */
	private PlatformInit() {
		this.threadExecutor = new SimpleThreadPool("MainThreadPool", 2, 10, 60 * 1000);
		// One way to avoid a security exception.
		this.generalSettings = GeneralSettings.getInstance();
	}

	/**
	 * Intializes security by installing a security policy and a security
	 * manager. Programs that use the browser API should invoke this method (or
	 * {@link #init(boolean, boolean) init }) to prevent web content from having
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
		PlatformStreamHandlerFactory factory = PlatformStreamHandlerFactory.getInstance();
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
		CookieHandler.setDefault(new CookieManager());
	}

	/**
	 * Initializes the Swing look feel.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void initLookAndFeel() throws Exception {
		LAFSettings settings = LAFSettings.getInstance();
		Properties props = new Properties();
		props.put("logoString", "Lobo Evolution");

		if (settings.isAcryl()) {
			AcrylLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
		}

		if (settings.isAero()) {
			AeroLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
		}

		if (settings.isAluminium()) {
			AluminiumLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		}

		if (settings.isBernstein()) {
			BernsteinLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
		}

		if (settings.isFast()) {
			FastLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
		}

		if (settings.isGraphite()) {
			GraphiteLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		}

		if (settings.isHiFi()) {
			HiFiLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		}

		if (settings.isLuna()) {
			LunaLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
		}

		if (settings.isMcWin()) {
			McWinLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		}

		if (settings.isNoire()) {
			NoireLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
		}

		if (settings.isSmart()) {
			SmartLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
		}

		if (settings.isTexture()) {
			TextureLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
		}
	}

	/**
	 * Checks if is code location directory.
	 *
	 * @return true, if is code location directory
	 */
	public boolean isCodeLocationDirectory() {
		URL codeLocation = this.getClass().getProtectionDomain().getCodeSource().getLocation();
		return Urls.isLocalFile(codeLocation) && codeLocation.getPath().endsWith("/");
	}

	/**
	 * Initializes platform logging. Note that this method is not implicitly
	 * called by {@link #init(boolean, boolean)}.
	 *
	 * @param debugOn
	 *            Debugging mode. This determines which one of two different
	 *            logging configurations is used.
	 * @throws Exception
	 *             the exception
	 */
	public void initLogging() throws Exception {

		// Configure log4j2

		Logger logger = LogManager.getLogger(PlatformInit.class);
		if (logger.isInfoEnabled()) {
			logger.warn("Entry(): Logger INFO level is enabled.");
			Properties properties = System.getProperties();
			Iterator i = properties.entrySet().iterator();
			while (i.hasNext()) {
				Map.Entry entry = (Map.Entry) i.next();
				logger.info("main(): " + entry.getKey() + "=" + entry.getValue());
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
	 *
	 * @param exitWhenAllWindowsAreClosed
	 *            the exit when all windows are closed
	 */
	public void initWindowFactory(boolean exitWhenAllWindowsAreClosed) {
		DefaultWindowFactory.getInstance().setExitWhenAllWindowsAreClosed(exitWhenAllWindowsAreClosed);
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
		System.setProperty("networkaddress.cache.ttl", "3600");
		System.setProperty("networkaddress.cache.negative.ttl", "1");
		System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");

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
	 *            {@link org.loboevolution.gui.ConsoleModel}.
	 * @throws Exception
	 *             the exception
	 * @see #initSecurity()
	 * @see #initProtocols()
	 * @see #initExtensions()
	 */
	public void init(boolean exitWhenAllWindowsAreClosed) throws Exception {
		initOtherProperties();
		initNative(NATIVE_DIR_NAME);
		initSecurity();
		initProtocols();
		initHTTP();
		initLookAndFeel();
		initWindowFactory(exitWhenAllWindowsAreClosed);
		initExtensions();
	}

	/**
	 * Opens a window and attempts to render the URL or path given.
	 *
	 * @param urlOrPath
	 *            A URL or file path.
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	public void launch(String urlOrPath) throws MalformedURLException {
		URL url = Urls.guessURL(urlOrPath);
		FramePanel.openWindow(null, url, null, new Properties(), "GET", null);
	}

	/**
	 * Opens as many browser windows as there are startup URLs in general
	 * settings.
	 *
	 * @throws MalformedURLException
	 *             the malformed url exception
	 * @see org.loboevolution.settings.GeneralSettings#getStartupURLs()
	 */
	public void launch() throws MalformedURLException {
		SecurityManager sm = System.getSecurityManager();
		if (sm == null) {
			Logger logger = LogManager.getLogger(PlatformInit.class);
			logger.warn("launch(): Security manager not set!");
		}
		String[] startupURLs = this.generalSettings.getStartupURLs();
		for (String url : startupURLs) {
			this.launch(url);
		}
	}
	
	/**
	 * Starts the browser by opening the URLs specified in the command-line
	 * arguments provided. Non-option arguments are assumed to be URLs and
	 * opened in separate windows. If no arguments are found, the method
	 * launches URLs from general settings. This method will not return until at
	 * least one window has been shown.
	 *
	 * @param args
	 *            the args
	 * @throws MalformedURLException
	 *             the malformed url exception
	 * @see org.loboevolution.settings.GeneralSettings#getStartupURLs()
	 */
	public void start(String[] args) throws MalformedURLException {
		DefaultWindowFactory.getInstance().evtWindowShown.addListener(event -> {
			synchronized (PlatformInit.this) {
				windowHasBeenShown = true;
				PlatformInit.this.notifyAll();
			}
		});
		boolean launched = false;
		for (String arg : args) {
			String url = arg;
			if (!url.startsWith("-")) {
				try {
					launched = true;
					this.launch(url);
				} catch (Exception err) {
					logger.log(Level.ERROR, err);
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

	/**
	 * Gets the Constant instance.
	 *
	 * @return the Constant instance
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
			logger.log(Level.ERROR, err);
		}
		Runtime.getRuntime().exit(0);
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

	/**
	 * Schedule task.
	 *
	 * @param task
	 *            the task
	 */
	public void scheduleTask(SimpleThreadPoolTask task) {
		this.threadExecutor.schedule(task);
	}

	/**
	 * Gets the application directory.
	 *
	 * @return the application directory
	 */
	public File getApplicationDirectory() {
		File appDir = this.applicationDirectory;
		if (appDir == null) {
			ProtectionDomain pd = this.getClass().getProtectionDomain();
			CodeSource cs = pd.getCodeSource();
			URL url = cs.getLocation();
			String jarPath = url.getPath();
			File jarFile;
			try {
				jarFile = new File(url.toURI());
			} catch (URISyntaxException use) {
				throw new IllegalStateException(use);
			} catch (IllegalArgumentException iae) {
				throw new IllegalStateException("Application code source apparently not a local JAR file: " + url
						+ ". Only local JAR files are supported at the moment.", iae);
			}
			File installDir = jarFile.getParentFile();
			if (installDir == null) {
				throw new IllegalStateException(
						"Installation directory is missing. Startup JAR path is " + jarPath + ".");
			}
			if (!installDir.exists()) {
				throw new IllegalStateException("Installation directory not found. Startup JAR path is " + jarPath
						+ ". Directory path is " + installDir.getAbsolutePath() + ".");
			}
			appDir = installDir;
			this.applicationDirectory = appDir;

			// Static logger should not be created in this class.
			Logger logger = LogManager.getLogger(this.getClass().getName());
			if (logger.isInfoEnabled()) {
				logger.info("getApplicationDirectory(): url=" + url + ",appDir=" + appDir);
			}
		}
		return appDir;
	}

	/**
	 * A factory for creating LocalStreamHandler objects.
	 */
	private static class LocalStreamHandlerFactory implements URLStreamHandlerFactory {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.net.URLStreamHandlerFactory#createURLStreamHandler(java.lang.
		 * String)
		 */
		@Override
		public URLStreamHandler createURLStreamHandler(String protocol) {
			switch (protocol) {
			case "res": 
				return new org.loboevolution.protocol.res.Handler(); 
			case "vc": 
				return new org.loboevolution.protocol.vc.Handler();
			default:
				return null;
			}
		}
	}
}