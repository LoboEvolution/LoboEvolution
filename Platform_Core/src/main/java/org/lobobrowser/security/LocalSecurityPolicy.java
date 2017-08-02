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
/*
 * Created on May 31, 2005
 */
package org.lobobrowser.security;

import java.awt.AWTPermission;
import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.net.NetPermission;
import java.net.SocketPermission;
import java.net.URL;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.PrivilegedAction;
import java.security.SecurityPermission;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PropertyPermission;
import java.util.logging.LoggingPermission;

import javax.management.MBeanServerPermission;
import javax.net.ssl.SSLPermission;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.util.Domains;

/**
 * The Class LocalSecurityPolicy.
 */
public class LocalSecurityPolicy extends Policy {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(LocalSecurityPolicy.class);

	/**
	 * Directory where Lobo should save files. Any files saved here have
	 * privileges of a remote file.
	 */
	public static final File STORE_DIRECTORY;

	/** The Constant STORE_DIR_NAME. */
	private static final String STORE_DIR_NAME = ".lobo";

	/** The Constant STORE_DIRECTORY_CANONICAL. */
	private static final String STORE_DIRECTORY_CANONICAL;

	/** The Constant instance. */
	private static final LocalSecurityPolicy instance = new LocalSecurityPolicy();

	/** The Constant BASE_PRIVILEGE. */
	private static final Collection<Permission> BASE_PRIVILEGE = new LinkedList<Permission>();

	static {
		File homeDir = new File(System.getProperty("user.home"));
		File settingsDir = new File(homeDir, STORE_DIR_NAME);
		STORE_DIRECTORY = settingsDir;
		String settingsCanonical = "";
		try {
			settingsCanonical = settingsDir.getCanonicalPath();
		} catch (IOException ioe) {
			logger.log(Level.ERROR, ioe);
		}
		STORE_DIRECTORY_CANONICAL = settingsCanonical;

		Collection<Permission> permissions = BASE_PRIVILEGE;

		permissions.add(new PropertyPermission("*", "read,write"));
		permissions.add(new AWTPermission("*"));
		permissions.add(new HistoryPermission());
		permissions.add(new SocketPermission("*", "connect,resolve,listen,accept"));
		permissions.add(new RuntimePermission("createClassLoader"));
		permissions.add(new RuntimePermission("getClassLoader"));
		permissions.add(new RuntimePermission("exitVM"));
		permissions.add(new RuntimePermission("setIO"));
		permissions.add(new RuntimePermission("setContextClassLoader"));
		permissions.add(new RuntimePermission("enableContextClassLoaderOverride"));
		permissions.add(new RuntimePermission("setFactory"));
		permissions.add(new RuntimePermission("accessClassInPackage.*"));
		permissions.add(new RuntimePermission("defineClassInPackage.*"));
		permissions.add(new RuntimePermission("accessDeclaredMembers"));
		permissions.add(new RuntimePermission("getStackTrace"));
		permissions.add(new RuntimePermission("preferences"));
		permissions.add(new RuntimePermission("modifyThreadGroup"));
		permissions.add(new RuntimePermission("getProtectionDomain"));
		permissions.add(new RuntimePermission("shutdownHooks"));
		permissions.add(new RuntimePermission("modifyThread"));
		permissions.add(new RuntimePermission("queuePrintJob"));
		permissions.add(new RuntimePermission("com.sun.media.jmc.accessMedia"));
		permissions.add(new RuntimePermission("loadLibrary.*"));
		permissions.add(new RuntimePermission("createSecurityManager"));
		permissions.add(new NetPermission("setDefaultAuthenticator"));
		permissions.add(new NetPermission("setCookieHandler"));
		permissions.add(new NetPermission("specifyStreamHandler"));
		permissions.add(new SSLPermission("setHostnameVerifier"));
		permissions.add(new SSLPermission("getSSLSessionContext"));
		permissions.add(new SecurityPermission("putProviderProperty.*"));
		permissions.add(new SecurityPermission("insertProvider.*"));
		permissions.add(new SecurityPermission("removeProvider.*"));
		permissions.add(new LoggingPermission("control", null));
		permissions.add(GenericLocalPermission.EXT_GENERIC);
		permissions.add(new FilePermission("<<ALL FILES>>", "read,write,delete,execute"));
		permissions.add(new MBeanServerPermission("createMBeanServer"));
	}

	/**
	 * Adds permissions to the base set of permissions assigned to privileged
	 * code, i.e. code loaded from the local system rather than a remote
	 * location. This method must be called before a security manager has been
	 * set.
	 *
	 * @param permission
	 *            A <code>Permission</code> instance.
	 */
	public static void addPrivilegedPermission(Permission permission) {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			throw new SecurityException("Call this method before the sercurity manager is set.");
		}
		BASE_PRIVILEGE.add(permission);
	}

	/**
	 * Instantiates a new local security policy.
	 */
	private LocalSecurityPolicy() {
	}

	/**
	 * Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
	public static LocalSecurityPolicy getInstance() {
		return instance;
	}

	/**
	 * Checks for host.
	 *
	 * @param url
	 *            the url
	 * @return true, if successful
	 */
	public static boolean hasHost(java.net.URL url) {
		String host = url.getHost();
		return host != null && !"".equals(host);
	}

	/**
	 * Checks if is local.
	 *
	 * @param url
	 *            the url
	 * @return true, if is local
	 */
	public static boolean isLocal(java.net.URL url) {
		// Should return true only if we are sure
		// the file has either been downloaded by
		// the user, was distributed with the OS,
		// or was distributed with the browser.
		if (url == null) {
			return false;
		}
		String scheme = url.getProtocol();
		if ("http".equalsIgnoreCase(scheme)) {
			return false;
		} else if ("file".equalsIgnoreCase(scheme)) {
			if (hasHost(url)) {
				return false;
			}
			// Files under the settings directory (e.g. cached JARs)
			// are considered remote.
			final String filePath = url.getPath();
			Boolean result = AccessController.doPrivileged((PrivilegedAction<Boolean>) () -> {
				File file = new File(filePath);
				try {
					String canonical = file.getCanonicalPath();
					return !canonical.startsWith(STORE_DIRECTORY_CANONICAL);
				} catch (IOException ioe) {
					logger.log(Level.ERROR, ioe);
					return false;
				}
			});
			return result.booleanValue();
		} else if ("jar".equalsIgnoreCase(scheme)) {
			String path = url.getPath();
			int emIdx = path.lastIndexOf('!');
			String subUrlString = emIdx == -1 ? path : path.substring(0, emIdx);
			try {
				URL subUrl = new URL(subUrlString);
				return isLocal(subUrl);
			} catch (java.net.MalformedURLException mfu) {
				return false;
			}
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.security.Policy#getPermissions(java.security.CodeSource)
	 */
	@Override
	public PermissionCollection getPermissions(CodeSource codesource) {

		if (codesource == null) {
			Permissions permissions = new Permissions();
			for (Permission p : BASE_PRIVILEGE) {
				permissions.add(p);
			}
			return permissions;
		}

		URL location = codesource.getLocation();
		if (location == null) {
			throw new AccessControlException("No location for coodesource=" + codesource);
		}
		boolean isLocal = isLocal(location);
		Permissions permissions = new Permissions();
		if (isLocal) {
			for (Permission p : BASE_PRIVILEGE) {
				permissions.add(p);
			}
			// Custom permissions
			permissions.add(StoreHostPermission.forURL(location));
			permissions.add(new RuntimePermission("com.sun.media.jmc.accessMedia"));
		} else {
			permissions.add(new PropertyPermission("java.version", "read"));
			permissions.add(new PropertyPermission("os.name", "read"));
			permissions.add(new PropertyPermission("line.separator", "read"));
			permissions.add(new SocketPermission(location.getHost(), "connect,resolve"));
			String hostName = location.getHost();
			// Get possible cookie domains for current location
			// and allow managed store access there.
			Collection domains = Domains.getPossibleDomains(hostName);
			Iterator i = domains.iterator();
			while (i.hasNext()) {
				String domain = (String) i.next();
				permissions.add(StoreHostPermission.forHost(domain));
			}
		}
		return permissions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.security.Policy#refresh()
	 */
	@Override
	public void refresh() {
	}
}
