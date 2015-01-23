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
 * Created on Mar 12, 2005
 */
package org.lobobrowser.store;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.security.LocalSecurityPolicy;
import org.lobobrowser.security.StoreHostPermission;

/**
 * * @author J. H. S.
 */
public class StorageManager implements Runnable {
	private static final Logger logger = Logger.getLogger(StorageManager.class
			.getName());
	private static final long HOST_STORE_QUOTA = 200 * 1024;
	// Note that the installer makes assumptions about these names.
	private static final String HOST_STORE_DIR = "HostStore";
	private static final String CACHE_DIR = "cache";
	private static final String CONTENT_DIR = "content";
	private static final String SETTINGS_DIR = "settings";
	private static final StorageManager instance = new StorageManager();
	private final File storeDirectory;

	public static StorageManager getInstance() throws IOException {
		return instance;
	}

	private StorageManager() {
		this.storeDirectory = LocalSecurityPolicy.STORE_DIRECTORY;
		if (!this.storeDirectory.exists()) {
			this.storeDirectory.mkdirs();
		}
	}

	private boolean threadStarted = false;

	private void ensureThreadStarted() {
		if (!this.threadStarted) {
			synchronized (this) {
				if (!this.threadStarted) {
					Thread t = new Thread(this, "StorageManager");
					t.setDaemon(true);
					t.setPriority(Thread.MIN_PRIORITY);
					t.start();
					this.threadStarted = true;
				}
			}
		}
	}

	public File getAppHome() {
		return this.storeDirectory;
	}

	private static final String NO_HOST = "$NO_HOST$";

	public File getCacheHostDirectory(String hostName) throws IOException {
		CacheManager.getInstance();
		File cacheDir = this.getCacheRoot();
		if (hostName == null || "".equals(hostName)) {
			hostName = NO_HOST;
		}
		return new File(cacheDir, normalizedFileName(hostName));
	}

	public File getContentCacheFile(String hostName, String fileName)
			throws IOException {
		File domainDir = this.getCacheHostDirectory(hostName);
		File xamjDir = new File(domainDir, CONTENT_DIR);
		return new File(xamjDir, fileName);
	}

	public File getCacheRoot() {
		return new File(this.storeDirectory, CACHE_DIR);
	}

	private final Map<String, RestrictedStore> restrictedStoreCache = new HashMap<String, RestrictedStore>();

	public RestrictedStore getRestrictedStore(String hostName,
			final boolean createIfNotExists) throws IOException {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(StoreHostPermission.forHost(hostName));
		}
		if (hostName == null || "".equals(hostName)) {
			hostName = NO_HOST;
		}
		final String normHost = hostName;
		RestrictedStore store;
		synchronized (this) {
			store = (RestrictedStore) this.restrictedStoreCache.get(normHost);
			if (store == null) {
				store = AccessController
						.doPrivileged(new PrivilegedAction<RestrictedStore>() {
							// Reason: Since we are checking StoreHostPermission
							// previously,
							// this is fine.
							public RestrictedStore run() {
								File hostStoreDir = new File(storeDirectory,
										HOST_STORE_DIR);
								File domainDir = new File(hostStoreDir,
										normalizedFileName(normHost));
								if (!createIfNotExists && !domainDir.exists()) {
									return null;
								}
								try {
									return new RestrictedStore(domainDir,
											HOST_STORE_QUOTA);
								} catch (IOException ioe) {
									throw new IllegalStateException(ioe);
								}
							}
						});
				if (store != null) {
					this.restrictedStoreCache.put(normHost, store);
				}
			}
		}
		if (store != null) {
			this.ensureThreadStarted();
		}
		return store;
	}

	public File getSettingsDirectory() {
		return new File(this.storeDirectory, SETTINGS_DIR);
	}

	public void saveSettings(String name, Serializable data) throws IOException {
		File dir = this.getSettingsDirectory();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, name);
		OutputStream out = new FileOutputStream(file);
		try {
			BufferedOutputStream bos = new BufferedOutputStream(out);
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(data);
			oos.flush();
		} finally {
			out.close();
		}
	}

	public Serializable retrieveSettings(String name, ClassLoader classLoader)
			throws IOException, ClassNotFoundException {
		File dir = this.getSettingsDirectory();
		if (!dir.exists()) {
			return null;
		}
		File file = new File(dir, name);
		if (!file.exists()) {
			return null;
		}
		InputStream in = new FileInputStream(file);
		try {
			BufferedInputStream bin = new BufferedInputStream(in);
			ObjectInputStream ois = new ClassLoaderObjectInputStream(bin,
					classLoader);
			try {
				return (Serializable) ois.readObject();
			} catch (InvalidClassException ice) {
				ice.printStackTrace();
				return null;
			}
		} finally {
			in.close();
		}
	}

	
	static String normalizedFileName(String hostName) {
		return hostName;
	}

	static String getHostName(String fileName) {
		return fileName;
	}

	private static final int MANAGED_STORE_UPDATE_DELAY = 1000 * 60 * 5; 

	public void run() {
		for (;;) {
			try {
				Thread.sleep(MANAGED_STORE_UPDATE_DELAY);
				RestrictedStore[] stores;
				synchronized (this) {
					stores = (RestrictedStore[]) this.restrictedStoreCache
							.values().toArray(new RestrictedStore[0]);
				}
				for (int i = 0; i < stores.length; i++) {
					Thread.yield();
					stores[i].updateSizeFile();
				}
			} catch (Throwable err) {
				logger.log(Level.SEVERE, "run()", err);
				try {
					Thread.sleep(MANAGED_STORE_UPDATE_DELAY);
				} catch (InterruptedException ie) {
					// Ignore this time.
				}
			}
		}
	}
}
