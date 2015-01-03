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
package org.lobobrowser.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.lobobrowser.clientlet.Clientlet;
import org.lobobrowser.clientlet.ClientletRequest;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.clientlet.ClientletSelector;
import org.lobobrowser.ua.ConnectionProcessor;
import org.lobobrowser.ua.NavigationEvent;
import org.lobobrowser.ua.NavigationListener;
import org.lobobrowser.ua.NavigationVetoException;
import org.lobobrowser.ua.NavigatorErrorListener;
import org.lobobrowser.ua.NavigatorExceptionEvent;
import org.lobobrowser.ua.NavigatorExtension;
import org.lobobrowser.ua.NavigatorExtensionContext;
import org.lobobrowser.ua.NavigatorWindow;
import org.lobobrowser.ua.UserAgent;
import org.lobobrowser.util.EventDispatch2;

/**
 * Encapsulates a browser extension or plugin.
 */
public class Extension implements Comparable, NavigatorExtensionContext {
	private static final String ATTRIBUTE_EXTENSION_CLASS = "extension.class";
	private static final String ATTRIBUTE_EXTENSION_PRIORITY = "extension.priority";
	private static final String EXTENSION_PROPERTIES_FILE = "lobo-extension.properties";
	private static final String PRIMARY_EXTENSION_FILE_NAME = "primary.jar";

	/**
	 * The minimum priority.
	 */
	public static final int LOW_PRIORITY = 0;

	/**
	 * The highest priority, only available to the default platform extension.
	 */
	public static final int PRIMARY_EXTENSION_PRIORITY = 10;

	/**
	 * The highest priority allowed for non-primary platform extensions.
	 */
	public static final int HIGH_PRIORITY = 9;

	/**
	 * The default priority.
	 */
	public static final int NORMAL_PRIORITY = 5;

	private final int priority;
	private final File extRoot;
	private final JarFile jarFile;
	private final String extClassName;
	private final String extId;
	private final boolean isPrimary;
	private final boolean isLibrary;

	// TODO: Move these collections to ExtensionManager.
	// More efficient. Consider removal of extensions.

	private final Collection<ClientletSelector> clientletSelectors;
	private final Collection<ConnectionProcessor> connectionProcessors;
	private final Collection<NavigationListener> navigationListeners;
	private final EventDispatch2 EVENT = new NavigatorErrorEventDispatch();

	public Extension(File extRoot) throws IOException {
		this.clientletSelectors = new LinkedList<ClientletSelector>();
		this.connectionProcessors = new ArrayList<ConnectionProcessor>();
		this.navigationListeners = new ArrayList<NavigationListener>();
		this.extRoot = extRoot;
		InputStream propsInputStream;
		if (extRoot.isDirectory()) {
			this.isPrimary = false;
			this.jarFile = null;
			this.extId = extRoot.getName();
			File propsFile = new File(extRoot, EXTENSION_PROPERTIES_FILE);
			propsInputStream = propsFile.exists() ? new FileInputStream(
					propsFile) : null;
		} else {
			JarFile jarFile = new JarFile(extRoot);
			this.isPrimary = extRoot.getName().toLowerCase()
					.equals(PRIMARY_EXTENSION_FILE_NAME);
			this.jarFile = jarFile;
			String name = extRoot.getName();
			int dotIdx = name.lastIndexOf('.');
			this.extId = dotIdx == -1 ? name : name.substring(0, dotIdx);
			JarEntry jarEntry = jarFile.getJarEntry(EXTENSION_PROPERTIES_FILE);
			propsInputStream = jarEntry == null ? null : jarFile
					.getInputStream(jarEntry);
		}
		this.isLibrary = propsInputStream == null;
		if (!this.isLibrary) {
			Properties mattribs = new Properties();
			try {
				mattribs.load(propsInputStream);
			} finally {
				propsInputStream.close();
			}
			String extClassName = mattribs
					.getProperty(ATTRIBUTE_EXTENSION_CLASS);
			if (extClassName == null) {
				throw new IOException("Property " + ATTRIBUTE_EXTENSION_CLASS
						+ " missing in " + EXTENSION_PROPERTIES_FILE
						+ ", part of " + extRoot + ".");
			}
			this.extClassName = extClassName;
			String priorityText = mattribs
					.getProperty(ATTRIBUTE_EXTENSION_PRIORITY);
			if (priorityText != null) {
				int tp = Integer.parseInt(priorityText.trim());
				if (tp < LOW_PRIORITY) {
					tp = LOW_PRIORITY;
				} else if (tp > HIGH_PRIORITY) {
					tp = HIGH_PRIORITY;
				}
				this.priority = tp;
			} else {
				this.priority = NORMAL_PRIORITY;
			}
		} else {
			this.extClassName = null;
			this.priority = PRIMARY_EXTENSION_PRIORITY;
		}
	}

	public String getId() {
		return this.extId;
	}

	public URL getCodeSource() throws java.net.MalformedURLException {
		return this.extRoot.toURL();
	}

	public boolean isPrimaryExtension() {
		return this.isPrimary;
	}

	public boolean isLibraryOnly() {
		return this.isLibrary;
	}

	private ClassLoader classLoader;
	private NavigatorExtension platformExtension;

	public void initClassLoader(ClassLoader parentClassLoader)
			throws java.net.MalformedURLException, ClassNotFoundException,
			IllegalAccessException, InstantiationException {
		URL url = this.extRoot.toURL();
		java.net.URL[] urls = new java.net.URL[] { url };
		ExtensionClassLoader classLoader = new ExtensionClassLoader(urls,
				parentClassLoader);
		String extClassName = this.extClassName;
		NavigatorExtension pe = null;
		if (extClassName != null) {
			Class extClass = classLoader.loadClass(extClassName);
			pe = (NavigatorExtension) extClass.newInstance();
		}
		synchronized (this) {
			this.classLoader = classLoader;
			this.platformExtension = pe;
		}
	}

	public ClassLoader getClassLoader() {
		synchronized (this) {
			return this.classLoader;
		}
	}

	/**
	 * Gets the {@link org.lobobrowser.ua.NavigatorExtension} implementation. It
	 * may return <code>null</code> in the case of a library.
	 */
	public NavigatorExtension getNavigatorExtension() {
		synchronized (this) {
			return this.platformExtension;
		}
	}

	public void initExtension() {
		Thread currentThread = Thread.currentThread();
		ClassLoader prevClassLoader = currentThread.getContextClassLoader();
		ClassLoader loader = this.classLoader;
		if (loader != null) {
			currentThread.setContextClassLoader(loader);
		}
		try {
			NavigatorExtension pe = this.platformExtension;
			if (pe != null) {
				pe.init(this);
			}
		} finally {
			currentThread.setContextClassLoader(prevClassLoader);
		}
	}

	public void initExtensionWindow(NavigatorWindow wcontext) {
		Thread currentThread = Thread.currentThread();
		ClassLoader prevClassLoader = currentThread.getContextClassLoader();
		ClassLoader loader = this.classLoader;
		if (loader != null) {
			currentThread.setContextClassLoader(loader);
		}
		try {
			NavigatorExtension pe = this.platformExtension;
			if (pe != null) {
				pe.windowOpening(wcontext);
			}
		} finally {
			currentThread.setContextClassLoader(prevClassLoader);
		}
	}

	public void shutdownExtensionWindow(NavigatorWindow wcontext) {
		Thread currentThread = Thread.currentThread();
		ClassLoader prevClassLoader = currentThread.getContextClassLoader();
		ClassLoader loader = this.classLoader;
		if (loader != null) {
			currentThread.setContextClassLoader(loader);
		}
		try {
			NavigatorExtension pe = this.platformExtension;
			if (pe != null) {
				pe.windowClosing(wcontext);
			}
		} finally {
			currentThread.setContextClassLoader(prevClassLoader);
		}
	}

	public void close() throws java.io.IOException {
		if (this.jarFile != null) {
			this.jarFile.close();
		}
	}

	public void addClientletSelector(ClientletSelector cs) {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(org.lobobrowser.security.GenericLocalPermission.EXT_GENERIC);
		}
		synchronized (this) {
			this.clientletSelectors.add(cs);
		}
	}

	public Clientlet getClientlet(ClientletRequest request,
			ClientletResponse response) {
		// Need to set the class loader in thread context, otherwise
		// some library classes may not be found.
		Thread currentThread = Thread.currentThread();
		ClassLoader prevClassLoader = currentThread.getContextClassLoader();
		ClassLoader loader = this.classLoader;
		if (loader != null) {
			currentThread.setContextClassLoader(loader);
		}
		try {
			synchronized (this) {
				for (ClientletSelector cs : this.clientletSelectors) {
					Clientlet c = cs.select(request, response);
					if (c != null) {
						return c;
					}
				}
			}
			return null;
		} finally {
			currentThread.setContextClassLoader(prevClassLoader);
		}
	}

	public Clientlet getLastResortClientlet(ClientletRequest request,
			ClientletResponse response) {
		Thread currentThread = Thread.currentThread();
		ClassLoader prevClassLoader = currentThread.getContextClassLoader();
		ClassLoader loader = this.classLoader;
		if (loader != null) {
			currentThread.setContextClassLoader(loader);
		}
		try {
			synchronized (this) {
				for (ClientletSelector cs : this.clientletSelectors) {
					Clientlet c = cs.lastResortSelect(request, response);
					if (c != null) {
						return c;
					}
				}
			}
			return null;
		} finally {
			currentThread.setContextClassLoader(prevClassLoader);
		}
	}

	public void addNavigatorErrorListener(NavigatorErrorListener listener) {
		EVENT.addListener(listener);
	}

	public void removeNavigatorErrorListener(NavigatorErrorListener listener) {
		EVENT.removeListener(listener);
	}

	/**
	 * @param event
	 * @return True only if the event was dispatched to at least one listener.
	 */
	public boolean handleError(NavigatorExceptionEvent event) {
		// Expected in GUI thread.
		return EVENT.fireEvent(event);
	}

	public void addURLStreamHandlerFactory(URLStreamHandlerFactory factory) {
		// TODO: Since extensions are intialized in parallel,
		// this is not necessarily done in order of priority.
		org.lobobrowser.main.PlatformStreamHandlerFactory.getInstance()
				.addFactory(factory);
	}

	public UserAgent getUserAgent() {
		return org.lobobrowser.request.UserAgentImpl.getInstance();
	}

	public int compareTo(Object o) {
		// Reverse order based on priority.
		Extension other = (Extension) o;
		int diff = other.priority - this.priority;
		if (diff != 0) {
			return diff;
		}
		return this.extRoot.compareTo(other.extRoot);
	}

	public int hashCode() {
		return this.priority | this.extRoot.hashCode();
	}

	public boolean equals(Object other) {
		if (!(other instanceof Extension)) {
			return false;
		}
		return ((Extension) other).extRoot.equals(this.extRoot);
	}

	public String toString() {
		return "ExtensionInfo[extRoot=" + this.extRoot + ",isLibrary="
				+ this.isLibrary + "]";
	}

	public void addConnectionProcessor(ConnectionProcessor processor) {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(org.lobobrowser.security.GenericLocalPermission.EXT_GENERIC);
		}
		synchronized (this) {
			this.connectionProcessors.add(processor);
		}
	}

	public void addNavigationListener(NavigationListener listener) {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(org.lobobrowser.security.GenericLocalPermission.EXT_GENERIC);
		}
		synchronized (this) {
			this.navigationListeners.add(listener);
		}
	}

	public void removeClientletSelector(ClientletSelector selector) {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(org.lobobrowser.security.GenericLocalPermission.EXT_GENERIC);
		}
		synchronized (this) {
			this.clientletSelectors.remove(selector);
		}
	}

	public void removeConnectionProcessor(ConnectionProcessor processor) {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(org.lobobrowser.security.GenericLocalPermission.EXT_GENERIC);
		}
		synchronized (this) {
			this.connectionProcessors.remove(processor);
		}
	}

	public void removeNavigationListener(NavigationListener listener) {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(org.lobobrowser.security.GenericLocalPermission.EXT_GENERIC);
		}
		synchronized (this) {
			this.navigationListeners.remove(listener);
		}
	}

	void dispatchBeforeNavigate(NavigationEvent event)
			throws NavigationVetoException {
		// Should not be public
		Thread currentThread = Thread.currentThread();
		ClassLoader prevClassLoader = currentThread.getContextClassLoader();
		ClassLoader loader = this.classLoader;
		if (loader != null) {
			currentThread.setContextClassLoader(loader);
		}
		try {
			NavigationListener[] listeners;
			Collection<NavigationListener> nv = this.navigationListeners;
			synchronized (this) {
				if (nv.isEmpty()) {
					return;
				}
				listeners = nv.toArray(NavigationListener.EMPTY_ARRAY);
			}
			for (int i = 0; i < listeners.length; i++) {
				listeners[i].beforeNavigate(event);
			}
		} finally {
			currentThread.setContextClassLoader(prevClassLoader);
		}
	}

	void dispatchBeforeLocalNavigate(NavigationEvent event)
			throws NavigationVetoException {
		// Should not be public
		Thread currentThread = Thread.currentThread();
		ClassLoader prevClassLoader = currentThread.getContextClassLoader();
		ClassLoader loader = this.classLoader;
		if (loader != null) {
			currentThread.setContextClassLoader(loader);
		}
		try {
			NavigationListener[] listeners;
			Collection<NavigationListener> nv = this.navigationListeners;
			synchronized (this) {
				if (nv.isEmpty()) {
					return;
				}
				listeners = nv.toArray(NavigationListener.EMPTY_ARRAY);
			}
			for (int i = 0; i < listeners.length; i++) {
				listeners[i].beforeLocalNavigate(event);
			}
		} finally {
			currentThread.setContextClassLoader(prevClassLoader);
		}
	}

	void dispatchBeforeWindowOpen(NavigationEvent event)
			throws NavigationVetoException {
		// Should not be public
		Thread currentThread = Thread.currentThread();
		ClassLoader prevClassLoader = currentThread.getContextClassLoader();
		ClassLoader loader = this.classLoader;
		if (loader != null) {
			currentThread.setContextClassLoader(loader);
		}
		try {
			NavigationListener[] listeners;
			Collection<NavigationListener> nv = this.navigationListeners;
			synchronized (this) {
				if (nv.isEmpty()) {
					return;
				}
				listeners = nv.toArray(NavigationListener.EMPTY_ARRAY);
			}
			for (int i = 0; i < listeners.length; i++) {
				listeners[i].beforeWindowOpen(event);
			}
		} finally {
			currentThread.setContextClassLoader(prevClassLoader);
		}
	}

	URLConnection dispatchPreConnection(URLConnection connection) {
		// Should not be public
		Thread currentThread = Thread.currentThread();
		ClassLoader prevClassLoader = currentThread.getContextClassLoader();
		ClassLoader loader = this.classLoader;
		if (loader != null) {
			currentThread.setContextClassLoader(loader);
		}
		try {
			ConnectionProcessor[] processors;
			Collection<ConnectionProcessor> cp = this.connectionProcessors;
			synchronized (this) {
				if (cp.isEmpty()) {
					return connection;
				}
				processors = cp.toArray(ConnectionProcessor.EMPTY_ARRAY);
			}
			for (int i = 0; i < processors.length; i++) {
				connection = processors[i].processPreConnection(connection);
			}
			return connection;
		} finally {
			currentThread.setContextClassLoader(prevClassLoader);
		}
	}

	URLConnection dispatchPostConnection(URLConnection connection) {
		// Should not be public
		Thread currentThread = Thread.currentThread();
		ClassLoader prevClassLoader = currentThread.getContextClassLoader();
		ClassLoader loader = this.classLoader;
		if (loader != null) {
			currentThread.setContextClassLoader(loader);
		}
		try {
			ConnectionProcessor[] processors;
			synchronized (this) {
				processors = this.connectionProcessors
						.toArray(ConnectionProcessor.EMPTY_ARRAY);
			}
			for (int i = 0; i < processors.length; i++) {
				connection = processors[i].processPostConnection(connection);
			}
			return connection;
		} finally {
			currentThread.setContextClassLoader(prevClassLoader);
		}
	}

	private static class NavigatorErrorEventDispatch extends EventDispatch2 {
		@Override
		protected void dispatchEvent(EventListener listener, EventObject event) {
			((NavigatorErrorListener) listener)
					.errorOcurred((NavigatorExceptionEvent) event);
		}
	}
}
