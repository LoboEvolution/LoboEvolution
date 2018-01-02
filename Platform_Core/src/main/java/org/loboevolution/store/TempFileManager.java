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
package org.loboevolution.store;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;

import org.loboevolution.security.LocalSecurityPolicy;

/**
 * The Class TempFileManager.
 */
public class TempFileManager {

	/** The instance. */
	private static TempFileManager instance;

	/** The Constant GENERAL_PREFIX. */
	private static final String GENERAL_PREFIX = "LOBO-";

	/** The Constant ONE_DAY. */
	private static final long ONE_DAY = 24L * 60 * 60 * 1000;

	/** The Constant ONE_MONTH. */
	private static final long ONE_MONTH = 30L * ONE_DAY;

	/** The Constant THIRTY_YEARS. */
	private static final long THIRTY_YEARS = 30L * 365 * ONE_DAY;

	/** The Constant FILE_PREFIX. */
	private static final String FILE_PREFIX = GENERAL_PREFIX + (System.currentTimeMillis() - THIRTY_YEARS) / 1000 + "-";

	/** The temp directory. */
	private final File TEMP_DIRECTORY;

	/** The reference queue. */
	private final ReferenceQueue<JarFile> REFERENCE_QUEUE = new ReferenceQueue<JarFile>();

	/** The wr by path. */
	private final Map<String, LocalWeakReference> wrByPath = new HashMap<String, LocalWeakReference>();

	/** The counter. */
	private int counter = 0;

	/**
	 * Gets the instance.
	 *
	 * @return the instance
	 */
	public static TempFileManager getInstance() {
		// Do it this way to allow other statics to initialize.
		if (instance == null) {
			synchronized (TempFileManager.class) {
				if (instance == null) {
					instance = new TempFileManager();
				}
			}
		}
		return instance;
	}

	/**
	 * Instantiates a new temp file manager.
	 */
	private TempFileManager() {
		Runtime.getRuntime().addShutdownHook(new ShutdownThread());
		File tempDirectory = new File(LocalSecurityPolicy.STORE_DIRECTORY, "tmp");
		TEMP_DIRECTORY = tempDirectory;
		if (!tempDirectory.exists()) {
			tempDirectory.mkdirs();
		}
		File[] files = tempDirectory.listFiles();
		if (files != null) {
			// Cleanup files theoretically left by previously running instance.
			for (File file : files) {
				String name = file.getName();
				if (name.startsWith(GENERAL_PREFIX) && !name.startsWith(FILE_PREFIX) && file.lastModified() < System.currentTimeMillis() - ONE_MONTH) {
						file.delete();
				}
			}
		}
	}

	/**
	 * Shutdown cleanup.
	 */
	private void shutdownCleanup() {
		File[] files = TEMP_DIRECTORY.listFiles();
		if (files != null) {
			for (File file : files) {
				try {
					String name = file.getName();
					if (name.startsWith(FILE_PREFIX)) {
						String canonical = file.getCanonicalPath();
						synchronized (this) {
							// Need to close these JAR files, otherwise
							// deletion does not happen in Windows.
							LocalWeakReference wr = this.wrByPath.get(canonical);
							JarFile jarFile = wr.get();
							if (jarFile != null) {
								jarFile.close();
							}
						}
						file.delete();
					}
				} catch (IOException ioe) {
					// ignore
				}
			}
		}
	}

	/**
	 * Creates the jar file.
	 *
	 * @param bytes
	 *            the bytes
	 * @return the jar file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public JarFile createJarFile(byte[] bytes) throws IOException {
		// Dequeue and clean up first
		for (;;) {
			Reference<? extends JarFile> ref = REFERENCE_QUEUE.poll();
			if (ref == null) {
				break;
			}
			String canonical = ((LocalWeakReference) ref).canonicalPath;
			new File(canonical).delete();
			synchronized (this) {
				this.wrByPath.remove(canonical);
			}
		}
		File file = this.newTempFile();
		OutputStream out = new FileOutputStream(file);
		try {
			out.write(bytes);
		} finally {
			out.close();
		}
		JarFile jarFile = new JarFile(file);
		String canonical = file.getCanonicalPath();
		LocalWeakReference wr = new LocalWeakReference(jarFile, REFERENCE_QUEUE, canonical);
		synchronized (this) {
			// This serves simply to retain the weak reference.
			this.wrByPath.put(canonical, wr);
		}
		return jarFile;
	}

	/**
	 * New temp file.
	 *
	 * @return the file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public File newTempFile() throws IOException {
		synchronized (this) {
			int newCounter = this.counter++;
			File file = new File(this.TEMP_DIRECTORY, FILE_PREFIX + newCounter);
			return file;
		}
	}

	/**
	 * The Class LocalWeakReference.
	 */
	private static class LocalWeakReference extends WeakReference<JarFile> {

		/** The canonical path. */
		public final String canonicalPath;

		/**
		 * Instantiates a new local weak reference.
		 *
		 * @param referent
		 *            the referent
		 * @param q
		 *            the q
		 * @param canonicalPath
		 *            the canonical path
		 */
		public LocalWeakReference(JarFile referent, ReferenceQueue<? super JarFile> q, String canonicalPath) {
			super(referent, q);
			this.canonicalPath = canonicalPath;
		}
	}

	/**
	 * The Class ShutdownThread.
	 */
	private class ShutdownThread extends Thread {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			shutdownCleanup();
		}
	}
}
