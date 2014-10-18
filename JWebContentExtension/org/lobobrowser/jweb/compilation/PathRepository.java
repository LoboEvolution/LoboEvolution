/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.jweb.compilation;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.lang.model.element.NestingKind;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

import org.lobobrowser.clientlet.ClientletContext;
import org.lobobrowser.store.TempFileManager;
import org.lobobrowser.ua.NetworkRequest;
import org.lobobrowser.util.Urls;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PathRepository {
	private static final Logger logger = Logger.getLogger(PathRepository.class
			.getName());
	private final Collection<URL> directoryPaths;
	private final Collection<JarInfo> jarFiles;
	private final String[] entryList;
	private final boolean useListingXML;

	public PathRepository(final URL[] paths, boolean useListingXML,
			String[] entryList) {
		this.useListingXML = useListingXML;
		this.entryList = entryList;
		Collection<URL> dp = new ArrayList<URL>();
		Collection<JarInfo> jf = new ArrayList<JarInfo>();
		this.directoryPaths = dp;
		this.jarFiles = jf;
		for (int i = 0; i < paths.length; i++) {
			try {
				URL url = paths[i];
				boolean isDirectory;
				boolean isLocalFile = Urls.isLocalFile(url);
				java.io.File file = isLocalFile ? getFile(url) : null;
				if (isLocalFile) {
					isDirectory = file.isDirectory();
				} else {
					String path = url.getPath();
					isDirectory = path.endsWith("/");
				}
				// Non-existent files should be ignored here.
				if (file != null && !file.exists()) {
					if (logger.isLoggable(Level.INFO)) {
						logger.info("PathRepository(): Ignoring non-existent file: "
								+ file);
					}
					continue;
				}
				if (isDirectory) {
					dp.add(url);
				} else {
					jf.add(new JarInfo(url));
				}
			} catch (Exception err) {
				logger.log(Level.SEVERE, "PathRepository()", err);
			}
		}
	}

	private static java.io.File getFile(URL url) throws java.io.IOException {
		try {
			return new java.io.File(url.toURI());
		} catch (java.net.URISyntaxException us) {
			try {
				return new java.io.File(URLDecoder.decode(url.getPath(),
						"UTF-8"));
			} catch (java.io.UnsupportedEncodingException ue) {
				throw new java.io.FileNotFoundException("Bad file URL: " + url);
			}
		}
	}

	private JarFile createJarFile(ClientletContext context, URL url,
			boolean isLocalFile, java.io.File file) throws java.io.IOException {
		if (isLocalFile) {
			return new JarFile(file);
		} else {
			NetworkRequest nr = context.createNetworkRequest();
			nr.open("GET", url, false);
			nr.send(null);
			if (nr.getReadyState() != NetworkRequest.STATE_COMPLETE) {
				throw new java.io.IOException("Failed to load JAR file for "
						+ url + ".");
			}
			int status = nr.getStatus();
			if (status != 0 && status != 200) {
				throw new java.io.IOException(
						"Failed to load JAR file because of server status "
								+ status + " on " + url + ".");
			}
			return TempFileManager.getInstance().createJarFile(
					nr.getResponseBytes());
		}
	}

	public JavaFileObject getJavaFileForInput(ClientletContext context,
			String className, Kind kind) throws java.io.IOException {
		String suffix;
		if (Kind.CLASS.equals(kind)) {
			suffix = ".class";
		} else if (Kind.SOURCE.equals(kind)) {
			suffix = ".java";
		} else {
			return null;
		}
		String classAsPath = className.replace('.', '/') + suffix;
		for (JarInfo jinfo : this.jarFiles) {
			JavaFileObject jfo = jinfo.getJavaFileForInput(context,
					classAsPath, kind);
			if (jfo != null) {
				return jfo;
			}
		}
		for (URL dirURL : this.directoryPaths) {
			JavaFileObject jfo = this.getJavaFileForInput(context, dirURL,
					classAsPath, kind);
			if (jfo != null) {
				return jfo;
			}
		}
		throw new java.io.FileNotFoundException("className=" + className
				+ ",kind=" + kind);
	}

	public FileObject getFileForInput(ClientletContext context,
			String packageName, String resourceName) throws java.io.IOException {
		String resourceAsPath = packageName.replace(".", "/") + "/"
				+ resourceName;
		for (JarInfo jinfo : this.jarFiles) {
			FileObject jfo = jinfo.getFileForInput(context, resourceAsPath);
			if (jfo != null) {
				return jfo;
			}
		}
		for (URL dirURL : this.directoryPaths) {
			FileObject jfo = this.getFileForInput(context, dirURL,
					resourceAsPath);
			if (jfo != null) {
				return jfo;
			}
		}
		throw new java.io.FileNotFoundException("packageName=" + packageName
				+ ",resourceName=" + resourceName);
	}

	private JavaFileObject getJavaFileForInput(ClientletContext context,
			URL dirURL, String classAsPath, Kind kind) {
		try {
			URL fullURL = Urls.createURL(dirURL, classAsPath);
			URLJavaFileObject jfo = new URLJavaFileObject(context, fullURL,
					classAsPath, kind,
					PathManager.getNestingKindForName(classAsPath));
			jfo.openInputStream();
			return jfo;
		} catch (java.io.IOException ioe) {
			if (logger.isLoggable(Level.FINE)) {
				logger.log(Level.FINE, "getJavaFileForInput()", ioe);
			}
			return null;
		}
	}

	private FileObject getFileForInput(ClientletContext context, URL dirURL,
			String resourcePath) {
		try {
			URL fullURL = Urls.createURL(dirURL, resourcePath);
			URLFileObject jfo = new URLFileObject(context, fullURL,
					resourcePath);
			jfo.openInputStream();
			return jfo;
		} catch (java.io.IOException ioe) {
			if (logger.isLoggable(Level.FINE)) {
				logger.log(Level.FINE, "getJavaFileForInput()", ioe);
			}
			return null;
		}
	}

	public List<JavaFileObject> list(ClientletContext context,
			String packageName, Set<Kind> kinds, boolean recurse)
			throws java.io.IOException {
		String packagePath = packageName.replace('.', '/');
		Collection<JarInfo> jarFiles = this.jarFiles;
		List<JavaFileObject> list = new ArrayList<JavaFileObject>();
		for (JarInfo jinfo : jarFiles) {
			jinfo.populateList(context, list, packagePath, kinds, recurse);
		}
		Collection<URL> directories = this.directoryPaths;
		for (URL dirURL : directories) {
			this.populateFromURL(context, list, dirURL, packagePath, kinds,
					recurse);
		}
		if (logger.isLoggable(Level.INFO)) {
			logger.info("list(): packageName=" + packageName + ",list.size()="
					+ list.size());
		}
		return list;
	}

	private void populateFromDirectory(List<JavaFileObject> list,
			java.io.File directory, String packagePath, Set<Kind> kinds,
			boolean recurse) {
		java.io.File currentDir = new java.io.File(directory, packagePath);
		java.io.File[] files = currentDir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				java.io.File file = files[i];
				String name = file.getName();
				String fullName = "".equals(packagePath) ? name : packagePath
						+ java.io.File.separator + name;
				if (file.isDirectory()) {
					if (recurse) {
						this.populateFromDirectory(list, directory, fullName,
								kinds, recurse);
					}
				} else {
					Kind kind = getKind(name);
					if (kinds.contains(kind)) {
						NestingKind nestingKind = PathManager
								.getNestingKindForName(name);
						String normalizedFullName;
						if (java.io.File.separatorChar != '/') {
							normalizedFullName = fullName.replace(
									java.io.File.separatorChar, '/');
						} else {
							normalizedFullName = fullName;
						}
						list.add(new LocalJavaFileObject(file,
								normalizedFullName, kind, nestingKind));
					}
				}
			}
		}
	}

	private void populateFromEntryList(ClientletContext context,
			List<JavaFileObject> list, URL dirURL, String[] entryList,
			String packagePath, Set<Kind> kinds, boolean recurse) {
		try {
			int length = entryList.length;
			for (int i = 0; i < length; i++) {
				String entryPath = entryList[i];
				int slashIdx = entryPath.lastIndexOf('/');
				String entryPackagePath = slashIdx == -1 ? "" : entryPath
						.substring(0, slashIdx);
				boolean match = recurse ? entryPackagePath
						.startsWith(packagePath) : entryPackagePath
						.equals(packagePath);
				if (match) {
					Kind kind = getKind(entryPath);
					if (kinds.contains(kind)) {
						NestingKind nestingKind = PathManager
								.getNestingKindForName(entryPath);
						URL entryURL = Urls.createURL(dirURL, entryPath);
						list.add(new URLJavaFileObject(context, entryURL,
								entryPath, kind, nestingKind));
					}
				}
			}
		} catch (Exception err) {
			if (logger.isLoggable(Level.INFO)) {
				logger.log(Level.INFO, "populateFromEntryList(): dirURL="
						+ dirURL + ",packagePath=" + packagePath, err);
			}
		}
	}

	private void populateFromListingXML(ClientletContext context,
			List<JavaFileObject> list, URL dirURL, String packagePath,
			Set<Kind> kinds, boolean recurse) {
		try {
			URL startURL = Urls.createURL(dirURL, packagePath + "/");
			URL listingURL = Urls.createURL(startURL, "listing.xml");
			NetworkRequest nw = context.createNetworkRequest();
			nw.open("GET", listingURL, false);
			nw.send(null);
			if (nw.getReadyState() != NetworkRequest.STATE_COMPLETE) {
				if (logger.isLoggable(Level.INFO)) {
					logger.log(Level.INFO,
							"populateFromURL(): Response not in ready state for "
									+ listingURL + ".");
				}
				return;
			}
			int status = nw.getStatus();
			if (status != 0 && status != 200) {
				if (logger.isLoggable(Level.INFO)) {
					logger.log(Level.INFO,
							"populateFromURL(): Response status is " + status
									+ " for " + listingURL + ".");
				}
				return;
			}
			Document document = nw.getResponseXML();
			if (document == null) {
				if (logger.isLoggable(Level.INFO)) {
					logger.log(Level.INFO,
							"populateFromURL(): Could not obtain DOM for "
									+ listingURL + ".");
				}
				return;
			}
			org.w3c.dom.Element docElement = document.getDocumentElement();
			NodeList children = docElement.getChildNodes();
			int length = children.getLength();
			for (int i = 0; i < length; i++) {
				Node child = children.item(i);
				if (child instanceof org.w3c.dom.Element) {
					org.w3c.dom.Element ce = (org.w3c.dom.Element) child;
					String name = ce.getAttribute("name");
					if (name == null || name.length() == 0) {
						logger.warning("populateFromURL(): listing.xml entry has no name attribute: "
								+ listingURL + ".");
						continue;
					}
					String fullName = "".equals(packagePath) ? name
							: packagePath + "/" + name;
					if (name.endsWith("/")) {
						if (recurse) {
							String newppath = fullName.substring(0,
									fullName.length() - 1);
							this.populateFromURL(context, list, dirURL,
									newppath, kinds, recurse);
						}
					} else {
						Kind kind = getKind(name);
						if (kinds.contains(kind)) {
							NestingKind nestingKind = PathManager
									.getNestingKindForName(name);
							URL entryURL = Urls.createURL(startURL, name);
							list.add(new URLJavaFileObject(context, entryURL,
									fullName, kind, nestingKind));
						}
					}
				}
			}
		} catch (Exception err) {
			if (logger.isLoggable(Level.INFO)) {
				logger.log(Level.INFO, "populateFromListingXML(): dirURL="
						+ dirURL + ",packagePath=" + packagePath, err);
			}
		}
	}

	private void populateFromURL(ClientletContext context,
			List<JavaFileObject> list, URL dirURL, String packagePath,
			Set<Kind> kinds, boolean recurse) {
		if (Urls.isLocalFile(dirURL)) {
			if (java.io.File.separatorChar != '/') {
				packagePath = packagePath.replace('/',
						java.io.File.separatorChar);
			}
			populateFromDirectory(list, new java.io.File(dirURL.getFile()),
					packagePath, kinds, recurse);
			return;
		}
		if (this.useListingXML) {
			this.populateFromListingXML(context, list, dirURL, packagePath,
					kinds, recurse);
		}
		String[] entryList = this.entryList;
		if (entryList != null) {
			this.populateFromEntryList(context, list, dirURL, entryList,
					packagePath, kinds, recurse);
		}
	}

	private static Kind getKind(String name) {
		if (name.endsWith(".class")) {
			return Kind.CLASS;
		} else if (name.endsWith(".java") || name.endsWith(".fx")) {
			return Kind.SOURCE;
		} else if (name.endsWith(".html") || name.endsWith(".htm")) {
			return Kind.HTML;
		} else {
			return Kind.OTHER;
		}
	}

	/** Closes all JarFile instances created locally. */
	public void close() {
		for (JarInfo jarInfo : this.jarFiles) {
			try {
				jarInfo.close();
			} catch (Exception err) {
				logger.log(Level.WARNING, "close()", err);
			}
		}
	}

	private static URI createJarEntryURI(URL jarURL, String entryName)
			throws URISyntaxException {
		if (!entryName.startsWith("/")) {
			entryName = "/" + entryName;
		}
		String jarURLText;
		if (Urls.isLocalFile(jarURL)) {
			jarURLText = new java.io.File(jarURL.getFile()).toURI().toString();
		} else {
			jarURLText = jarURL.toExternalForm();
		}
		return new URI("jar:" + jarURLText + "!" + entryName);
	}

	private static URI createJarEntryURI(String jarURLText, String entryName)
			throws URISyntaxException {
		if (!entryName.startsWith("/")) {
			entryName = "/" + entryName;
		}
		return new URI("jar:" + jarURLText + "!" + entryName);
	}

	static class JarInfo {
		public final URL jarURL;
		private final Map<String, List<EntryInfo>> entriesByPackagePath = new HashMap<String, List<EntryInfo>>();
		private JarFile cachedJarFile;
		private boolean populated = false;

		public JarInfo(URL jarURL) {
			super();
			this.jarURL = jarURL;
		}

		public void close() throws java.io.IOException {
			JarFile jarFile = this.cachedJarFile;
			if (jarFile != null) {
				jarFile.close();
			}
		}

		private JarFile getJarFile(ClientletContext context)
				throws java.io.IOException {
			JarFile jf;
			synchronized (this) {
				jf = this.cachedJarFile;
				if (jf != null) {
					return jf;
				}
			}
			URL jarURL = this.jarURL;
			if (Urls.isLocalFile(jarURL)) {
				java.io.File file = PathRepository.getFile(jarURL);
				jf = new JarFile(file);
			} else {
				NetworkRequest nr = context.createNetworkRequest();
				nr.open("GET", this.jarURL, false);
				nr.send(null);
				if (nr.getReadyState() != NetworkRequest.STATE_COMPLETE) {
					throw new java.io.IOException(
							"Failed to load JAR file for " + this.jarURL + ".");
				}
				int status = nr.getStatus();
				if (status != 0 && status != 200) {
					throw new java.io.IOException(
							"Failed to load JAR file because of server status "
									+ status + " on " + this.jarURL + ".");
				}
				jf = TempFileManager.getInstance().createJarFile(
						nr.getResponseBytes());
			}
			synchronized (this) {
				// Let's try to be consistent on instance returned here.
				JarFile coincidental = this.cachedJarFile;
				if (coincidental != null) {
					return coincidental;
				}
				this.cachedJarFile = jf;
			}
			return jf;
		}

		public JavaFileObject getJavaFileForInput(ClientletContext context,
				String classAsPath, Kind kind) throws java.io.IOException {
			JarFile jarFile = this.getJarFile(context);
			JarEntry entry = jarFile.getJarEntry(classAsPath);
			if (entry == null) {
				return null;
			}
			try {
				java.net.URI uri = createJarEntryURI(this.jarURL,
						entry.getName());
				return new JarJavaFileObject(jarFile, entry, uri, kind,
						PathManager.getNestingKindForName(classAsPath));
			} catch (java.net.URISyntaxException use) {
				throw new java.lang.IllegalStateException(
						"Unexpected URI syntax error.", use);
			}
		}

		public FileObject getFileForInput(ClientletContext context,
				String resourcePath) throws java.io.IOException {
			JarFile jarFile = this.getJarFile(context);
			JarEntry entry = jarFile.getJarEntry(resourcePath);
			if (entry == null) {
				return null;
			}
			try {
				java.net.URI uri = createJarEntryURI(this.jarURL,
						entry.getName());
				return new JarFileObject(jarFile, entry, uri);
			} catch (java.net.URISyntaxException use) {
				throw new java.lang.IllegalStateException(
						"Unexpected URI syntax error.", use);
			}
		}

		private void populate(ClientletContext context, JarFile jarFile)
				throws java.io.IOException {
			Map<String, List<EntryInfo>> entriesByPackagePath = this.entriesByPackagePath;
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				String name = entry.getName();
				int lastSlashIdx = name.lastIndexOf('/');
				String packagePath = lastSlashIdx == -1 ? "" : name.substring(
						0, lastSlashIdx);
				List<EntryInfo> subEntries = entriesByPackagePath
						.get(packagePath);
				if (subEntries == null) {
					subEntries = new ArrayList<EntryInfo>();
					entriesByPackagePath.put(packagePath, subEntries);
				}
				subEntries.add(new EntryInfo(entry, getKind(name)));
			}
		}

		public void populateList(ClientletContext context,
				List<JavaFileObject> list, String packagePath, Set<Kind> kinds,
				boolean recurse) throws java.io.IOException {
			JarFile jarFile = this.getJarFile(context);
			synchronized (this) {
				if (!this.populated) {
					this.populate(context, jarFile);
					this.populated = true;
				}
			}
			URL jarURL = this.jarURL;
			String jarURLText;
			if (Urls.isLocalFile(jarURL)) {
				jarURLText = new java.io.File(jarURL.getFile()).toURI()
						.toString();
			} else {
				jarURLText = jarURL.toExternalForm();
			}
			if (recurse) {
				for (Map.Entry<String, List<EntryInfo>> entry : this.entriesByPackagePath
						.entrySet()) {
					if (entry.getKey().startsWith(packagePath)) {
						List<EntryInfo> entryInfos = entry.getValue();
						for (EntryInfo entryInfo : entryInfos) {
							JarEntry jarEntry = entryInfo.jarEntry;
							String name = jarEntry.getName();
							Kind kind = getKind(name);
							if (kinds.contains(kind)) {
								NestingKind nk = PathManager
										.getNestingKindForName(name);
								try {
									URI uri = createJarEntryURI(jarURLText,
											name);
									list.add(new JarJavaFileObject(jarFile,
											jarEntry, uri, kind, nk));
								} catch (java.net.URISyntaxException use) {
									logger.log(Level.WARNING, "populateList()",
											use);
								}
							}
						}
					}
				}
			} else {
				List<EntryInfo> entryInfos = this.entriesByPackagePath
						.get(packagePath);
				if (entryInfos != null) {
					for (EntryInfo entryInfo : entryInfos) {
						JarEntry jarEntry = entryInfo.jarEntry;
						String name = jarEntry.getName();
						Kind kind = getKind(name);
						if (kinds.contains(kind)) {
							NestingKind nk = PathManager
									.getNestingKindForName(name);
							try {
								URI uri = createJarEntryURI(jarURL, name);
								list.add(new JarJavaFileObject(jarFile,
										jarEntry, uri, kind, nk));
							} catch (java.net.URISyntaxException use) {
								logger.log(Level.WARNING, "populateList()", use);
							}
						}
					}
				}
			}
		}
	}

	static class EntryInfo {
		public final Kind kind;
		public final JarEntry jarEntry;

		public EntryInfo(final JarEntry jarEntry, final Kind kind) {
			this.kind = kind;
			this.jarEntry = jarEntry;
		}
	}
}
