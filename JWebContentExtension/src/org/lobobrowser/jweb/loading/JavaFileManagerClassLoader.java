/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

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
package org.lobobrowser.jweb.loading;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.security.CodeSource;
import java.security.SecureClassLoader;
import java.util.Collections;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;

import org.lobobrowser.util.io.IORoutines;

public class JavaFileManagerClassLoader extends SecureClassLoader {
	// TODO: Check for sealing violations?
	private static final Logger logger = Logger
			.getLogger(JavaFileManagerClassLoader.class.getName());
	private final JavaFileManager fileManager;

	public JavaFileManagerClassLoader(final ClassLoader parent,
			final JavaFileManager fileManager) {
		super(parent);
		this.fileManager = fileManager;
	}

	@Override
	protected Class<?> findClass(String className)
			throws ClassNotFoundException {
		try {
			JavaFileObject javaFileObject;
			try {
				javaFileObject = this.fileManager.getJavaFileForInput(
						javax.tools.StandardLocation.CLASS_PATH, className,
						javax.tools.JavaFileObject.Kind.CLASS);
				if (javaFileObject == null) {
					throw new java.io.FileNotFoundException();
				}
			} catch (java.io.IOException ioe) {
				javaFileObject = this.fileManager.getJavaFileForInput(
						javax.tools.StandardLocation.CLASS_OUTPUT, className,
						javax.tools.JavaFileObject.Kind.CLASS);
				if (javaFileObject == null) {
					throw new ClassNotFoundException(className);
				}
			}
			java.io.InputStream in = javaFileObject.openInputStream();
			try {
				byte[] buffer = IORoutines.load(in, 4096);
				java.net.URI uri = javaFileObject.toUri();
				if (uri == null) {
					throw new java.lang.NullPointerException(
							"URI not available in " + javaFileObject);
				}
				URL url;
				try {
					url = uri.toURL();
				} catch (java.net.MalformedURLException mfu) {
					logger.log(Level.WARNING, "findClass(): Malformed URL: "
							+ uri + ".");
					throw new ClassNotFoundException(className, mfu);
				}
				CodeSource codeSource = new CodeSource(url,
						(java.security.cert.Certificate[]) null);
				return this.defineClass(className, buffer, 0, buffer.length,
						codeSource);
			} finally {
				in.close();
			}
		} catch (java.io.IOException ioe) {
			if (logger.isLoggable(Level.INFO)) {
				logger.log(Level.INFO, "findClass(): Failed to find "
						+ className + ".", ioe);
			}
			throw new ClassNotFoundException(className, ioe);
		}
	}

	@Override
	protected URL findResource(String resourceName) {
		int slashIdx = resourceName.lastIndexOf('/');
		String packageName = slashIdx == -1 ? "" : resourceName.substring(0,
				slashIdx);
		String relativeName = slashIdx == -1 ? resourceName : resourceName
				.substring(slashIdx + 1);
		try {
			FileObject fileObject = this.fileManager.getFileForInput(
					StandardLocation.CLASS_PATH, packageName, relativeName);
			if (fileObject == null) {
				return null;
			}
			java.net.URI uri = fileObject.toUri();
			try {
				URL url = new URL(null, uri.toString(), new ResourceHandler(
						fileObject));
				if (logger.isLoggable(Level.INFO)) {
					logger.info("findResource(): For resource name '"
							+ resourceName + "' returning " + url + ".");
				}
				return url;
			} catch (java.net.MalformedURLException mfu) {
				logger.log(Level.WARNING,
						"findResource(): Bad resource URL for resource named '"
								+ resourceName + "'.");
				return null;
			}
		} catch (java.io.IOException ioe) {
			if (logger.isLoggable(Level.INFO)) {
				logger.log(Level.INFO,
						"findResource(): IOException for resource named '"
								+ resourceName + "'.", ioe);
			}
			return null;
		}
	}

	@Override
	protected Enumeration<URL> findResources(String name) throws IOException {
		if (logger.isLoggable(Level.WARNING)) {
			logger.log(
					Level.WARNING,
					"findResources(): Always returns a single resource if any, just like findResource().");
		}
		URL url = this.findResource(name);
		if (url == null) {
			return java.util.Collections.enumeration(Collections
					.<URL> emptyList());
		}
		return java.util.Collections.enumeration(java.util.Collections
				.singletonList(url));
	}

	@Override
	public InputStream getResourceAsStream(String resourceName) {
		int slashIdx = resourceName.lastIndexOf('/');
		String packageName = slashIdx == -1 ? "" : resourceName.substring(0,
				slashIdx);
		String relativeName = slashIdx == -1 ? resourceName : resourceName
				.substring(slashIdx + 1);
		try {
			FileObject fileObject = this.fileManager.getFileForInput(
					StandardLocation.CLASS_PATH, packageName, relativeName);
			if (fileObject == null) {
				return null;
			}
			return fileObject.openInputStream();
		} catch (java.io.IOException ioe) {
			if (logger.isLoggable(Level.INFO)) {
				logger.log(Level.INFO,
						"findResource(): IOException for resource named '"
								+ resourceName + "'.", ioe);
			}
			return null;
		}
	}

	private class ResourceHandler extends URLStreamHandler {
		private final FileObject fileObject;

		public ResourceHandler(final FileObject fileObject) {
			super();
			this.fileObject = fileObject;
		}

		@Override
		protected URLConnection openConnection(URL u) throws IOException {
			return new ResourceURLConnection(u, this.fileObject);
		}
	}

	private class ResourceURLConnection extends URLConnection {
		private FileObject fileObject;

		public ResourceURLConnection(URL url, FileObject fileObject) {
			super(url);
			this.fileObject = fileObject;
		}

		private InputStream inputStream;

		@Override
		public void connect() throws IOException {
			this.inputStream = this.fileObject.openInputStream();
		}

		@Override
		public InputStream getInputStream() throws IOException {
			if (this.inputStream == null) {
				this.connect();
			}
			return this.inputStream;
		}
	}
}
