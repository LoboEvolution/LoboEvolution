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
 * Created on Jun 19, 2005
 */
package org.loboevolution.primary.clientlets;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.PrivilegedAction;
import java.security.cert.Certificate;
import java.util.Collections;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.util.BaseClassLoader;
import org.loboevolution.util.CollectionUtilities;
import org.loboevolution.util.GenericURLConnection;
import org.loboevolution.util.io.IORoutines;

/**
 * The Class ArchiveClassLoader.
 *
 * @author J. H. S.
 */
public class ArchiveClassLoader extends BaseClassLoader {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(ArchiveClassLoader.class);
	
	/** The archive infos. */
	private final ArchiveInfo[] archiveInfos;

	/**
	 * The Class LocalURLStreamHandler.
	 *
	 * @author J. H. S.
	 */
	public class LocalURLStreamHandler extends java.net.URLStreamHandler {

		/** The resource name. */
		private final String resourceName;

		/**
		 * Instantiates a new local url stream handler.
		 *
		 * @param resourceName
		 *            the resource name
		 */
		public LocalURLStreamHandler(String resourceName) {
			super();
			this.resourceName = resourceName;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.net.URLStreamHandler#openConnection(URL)
		 */
		@Override
		protected URLConnection openConnection(URL u) throws IOException {
			return new GenericURLConnection(u, getResourceAsStreamImpl(this.resourceName));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.net.URLStreamHandler#openConnection(URL, java.net.Proxy)
		 */
		@Override
		protected URLConnection openConnection(URL u, Proxy p) throws IOException {
			return this.openConnection(u);
		}
	}

	/**
	 * Instantiates a new archive class loader.
	 *
	 * @param archiveInfos
	 *            the archive infos
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	ArchiveClassLoader(java.util.Collection<Object[]> archiveInfos) throws IOException {
		super(ArchiveClassLoader.class.getClassLoader());
		this.archiveInfos = archiveInfos.toArray(ArchiveInfo.EMPTY_ARRAY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.ClassLoader#findClass(String)
	 */
	@Override
	protected Class findClass(String arg0) throws ClassNotFoundException {
		final String subPath = arg0.replace('.', '/') + ".class";
		ArchiveInfo[] ainfos = this.archiveInfos;
		int len = ainfos.length;
		byte[] classBytes = null;
		final ArchiveInfo[] foundAinfo = new ArchiveInfo[1];
		for (int i = 0; i < len; i++) {
			final ArchiveInfo ainfo = ainfos[i];
			try {
				final JarFile jarFile = ainfo.getJarFile();
				classBytes = (byte[]) AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
					try {
						ZipEntry entry = jarFile.getEntry(subPath);
						if (entry == null) {
							return null;
						}
						InputStream in = jarFile.getInputStream(entry);
						try {
							byte[] bytes = IORoutines.loadExact(in, (int) entry.getSize());
							foundAinfo[0] = ainfo;
							return bytes;
						} finally {
							in.close();
						}
					} catch (IOException ioe) {
						return null;
					}
				});
			} catch (IOException ioe2) {
				continue;
			}
			if (classBytes != null) {
				break;
			}
		}
		if (classBytes == null) {
			throw new ClassNotFoundException("I/O error or entry not found: " + subPath);
		}
		// TODO Signers Certificates
		CodeSource cs = new CodeSource(foundAinfo[0].url, new Certificate[0]);
		return this.defineClass(arg0, classBytes, 0, classBytes.length, cs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.ClassLoader#findResource(String)
	 */
	@Override
	protected URL findResource(final String name) {
		try {
			return AccessController.doPrivileged((PrivilegedAction<URL>) () -> {
				try {
					return new URL(null, "volatile:" + name, new LocalURLStreamHandler(name));
				} catch (MalformedURLException mfu) {
					throw new IllegalStateException(mfu);
				}
			});
		} catch (RuntimeException err) {
			logger.error( "findResource()", err);
			throw err;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.ClassLoader#findResources(String)
	 */
	@Override
	protected Enumeration findResources(String name) throws IOException {
		URL url = this.findResource(name);
		if (url != null) {
			return CollectionUtilities.getIteratorEnumeration(Collections.singletonList(url).iterator());
		} else {
			return CollectionUtilities.getIteratorEnumeration(Collections.emptyList().iterator());
		}
	}

	/**
	 * Gets the resource as stream impl.
	 *
	 * @param resourceName
	 *            the resource name
	 * @return the resource as stream impl
	 */
	private InputStream getResourceAsStreamImpl(final String resourceName) {
		ArchiveInfo[] ainfos = this.archiveInfos;
		int len = ainfos.length;
		InputStream in = null;
		for (int i = 0; i < len; i++) {
			final ArchiveInfo ainfo = ainfos[i];
			try {
				final JarFile jarFile = ainfo.getJarFile();
				in = (InputStream) AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
					try {
						ZipEntry entry = jarFile.getEntry(resourceName);
						if (entry == null) {
							return null;
						}
						return jarFile.getInputStream(entry);
					} catch (IOException ioe) {
						return null;
					}
				});
			} catch (IOException ioe2) {
				continue;
			}
			if (in != null) {
				break;
			}
		}
		if (in == null) {
			ClassLoader parent = this.getParent();
			return parent != null ? parent.getResourceAsStream(resourceName) : null;
		} else {
			return in;
		}
	}
}
