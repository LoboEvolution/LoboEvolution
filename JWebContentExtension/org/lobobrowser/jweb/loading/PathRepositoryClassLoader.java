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
package org.lobobrowser.jweb.loading;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
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
import javax.tools.JavaFileObject;

import org.lobobrowser.clientlet.ClientletContext;
import org.lobobrowser.jweb.compilation.PathRepository;
import org.lobobrowser.util.io.IORoutines;


/**
 * The Class PathRepositoryClassLoader.
 */
public class PathRepositoryClassLoader extends SecureClassLoader {
	// TODO: Check for sealing violations?
	/** The Constant logger. */
	private static final Logger logger = Logger
			.getLogger(PathRepositoryClassLoader.class.getName());
	
	/** The path repository. */
	private final PathRepository pathRepository;
	
	/** The context. */
	private final ClientletContext context;

	/**
	 * Instantiates a new path repository class loader.
	 *
	 * @param context the context
	 * @param parent the parent
	 * @param pr the pr
	 */
	public PathRepositoryClassLoader(ClientletContext context,
			final ClassLoader parent, PathRepository pr) {
		super(parent);
		this.pathRepository = pr;
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see java.lang.ClassLoader#findClass(java.lang.String)
	 */
	@Override
	protected Class<?> findClass(String className)
			throws ClassNotFoundException {
		try {
			JavaFileObject javaFileObject;
			javaFileObject = this.pathRepository.getJavaFileForInput(
					this.context, className, JavaFileObject.Kind.CLASS);
			if (javaFileObject == null) {
				throw new ClassNotFoundException(className);
			}
			InputStream in = javaFileObject.openInputStream();
			try {
				byte[] buffer = IORoutines.load(in, 4096);
				java.net.URI uri = javaFileObject.toUri();
				if (uri == null) {
					throw new NullPointerException(
							"URI not available in " + javaFileObject);
				}
				URL url;
				try {
					url = uri.toURL();
				} catch (MalformedURLException mfu) {
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
		} catch (IOException ioe) {
			if (logger.isLoggable(Level.FINE)) {
				logger.log(Level.FINE, "findClass(): Failed to find "
						+ className + ".", ioe);
			}
			throw new ClassNotFoundException(className, ioe);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.ClassLoader#findResource(java.lang.String)
	 */
	@Override
	protected URL findResource(String resourceName) {
		int slashIdx = resourceName.lastIndexOf('/');
		String packageName = slashIdx == -1 ? "" : resourceName.substring(0,
				slashIdx);
		String relativeName = slashIdx == -1 ? resourceName : resourceName
				.substring(slashIdx + 1);
		try {
			FileObject fileObject = this.pathRepository.getFileForInput(
					this.context, packageName, relativeName);
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
			} catch (MalformedURLException mfu) {
				logger.log(Level.WARNING,
						"findResource(): Bad resource URL for resource named '"
								+ resourceName + "'.");
				return null;
			}
		} catch (IOException ioe) {
			if (logger.isLoggable(Level.INFO)) {
				logger.log(Level.INFO,
						"findResource(): IOException for resource named '"
								+ resourceName + "'.", ioe);
			}
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.ClassLoader#findResources(java.lang.String)
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.ClassLoader#getResourceAsStream(java.lang.String)
	 */
	@Override
	public InputStream getResourceAsStream(String resourceName) {
		int slashIdx = resourceName.lastIndexOf('/');
		String packageName = slashIdx == -1 ? "" : resourceName.substring(0,
				slashIdx);
		String relativeName = slashIdx == -1 ? resourceName : resourceName
				.substring(slashIdx + 1);
		try {
			FileObject fileObject = this.pathRepository.getFileForInput(
					this.context, packageName, relativeName);
			if (fileObject == null) {
				return null;
			}
			return fileObject.openInputStream();
		} catch (IOException ioe) {
			if (logger.isLoggable(Level.INFO)) {
				logger.log(Level.INFO,
						"findResource(): IOException for resource named '"
								+ resourceName + "'.", ioe);
			}
			return null;
		}
	}

	/**
	 * The Class ResourceHandler.
	 */
	private class ResourceHandler extends URLStreamHandler {
		
		/** The file object. */
		private final FileObject fileObject;

		/**
		 * Instantiates a new resource handler.
		 *
		 * @param fileObject the file object
		 */
		public ResourceHandler(final FileObject fileObject) {
			super();
			this.fileObject = fileObject;
		}

		/* (non-Javadoc)
		 * @see java.net.URLStreamHandler#openConnection(java.net.URL)
		 */
		@Override
		protected URLConnection openConnection(URL u) throws IOException {
			return new ResourceURLConnection(u, this.fileObject);
		}
	}

	/**
	 * The Class ResourceURLConnection.
	 */
	private class ResourceURLConnection extends URLConnection {
		
		/** The file object. */
		private FileObject fileObject;

		/**
		 * Instantiates a new resource url connection.
		 *
		 * @param url the url
		 * @param fileObject the file object
		 */
		public ResourceURLConnection(URL url, FileObject fileObject) {
			super(url);
			this.fileObject = fileObject;
		}

		/** The input stream. */
		private InputStream inputStream;

		/* (non-Javadoc)
		 * @see java.net.URLConnection#connect()
		 */
		@Override
		public void connect() throws IOException {
			this.inputStream = this.fileObject.openInputStream();
		}

		/* (non-Javadoc)
		 * @see java.net.URLConnection#getInputStream()
		 */
		@Override
		public InputStream getInputStream() throws IOException {
			if (this.inputStream == null) {
				this.connect();
			}
			return this.inputStream;
		}
	}
}
