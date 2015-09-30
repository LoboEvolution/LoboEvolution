/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Jun 19, 2005
 */
package org.lobobrowser.primary.clientlets;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;

import org.lobobrowser.util.BaseClassLoader;
import org.lobobrowser.util.CollectionUtilities;
import org.lobobrowser.util.GenericURLConnection;
import org.lobobrowser.util.io.IORoutines;

/**
 * The Class ArchiveClassLoader.
 *
 * @author J. H. S.
 */
public class ArchiveClassLoader extends BaseClassLoader {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(ArchiveClassLoader.class.getName());

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

	/** The archive infos. */
	private final ArchiveInfo[] archiveInfos;

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
				classBytes = (byte[]) AccessController.doPrivileged(new PrivilegedAction<Object>() {
					@Override
					public Object run() {
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
		CodeSource cs = new CodeSource(foundAinfo[0].url, new java.security.cert.Certificate[0]);
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
			return AccessController.doPrivileged(new PrivilegedAction<java.net.URL>() {
				@Override
				public URL run() {
					try {
						return new URL(null, "volatile:" + name, new LocalURLStreamHandler(name));
					} catch (MalformedURLException mfu) {
						throw new IllegalStateException(mfu.getMessage());
					}
				}
			});
		} catch (RuntimeException err) {
			logger.log(Level.SEVERE, "findResource()", err);
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
			return CollectionUtilities.getIteratorEnumeration(Collections.EMPTY_LIST.iterator());
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
				in = (InputStream) AccessController.doPrivileged(new PrivilegedAction<Object>() {
					@Override
					public Object run() {
						try {
							ZipEntry entry = jarFile.getEntry(resourceName);
							if (entry == null) {
								return null;
							}
							return jarFile.getInputStream(entry);
						} catch (IOException ioe) {
							return null;
						}
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
