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
package org.lobobrowser.jweb.compilation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardLocation;

import org.lobobrowser.clientlet.ClientletContext;
import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.util.io.IORoutines;

public class JavaResponseFileManager implements JavaFileManager {
	private static final Logger logger = Logger
			.getLogger(JavaResponseFileManager.class.getName());

	/** FileObject's by class name */
	private Map<String, JavaFileObject> outFiles = new HashMap<String, JavaFileObject>();

	private final PathRepository classPathRepository;
	private final PathRepository sourcePathRepository;
	private final PathRepository platformClassPathRepository;
	private final ClientletContext context;
	private JavaFileObject compilationUnit;

	public JavaResponseFileManager(ClientletContext context,
			PathRepository pcpr, PathRepository cpr, PathRepository spr) {
		this.context = context;
		this.platformClassPathRepository = pcpr;
		this.classPathRepository = cpr;
		this.sourcePathRepository = spr;
	}

	public void setCompilationUnit(JavaFileObject cu) {
		this.compilationUnit = cu;
	}

	public void close() throws IOException {
	}

	public void flush() throws IOException {
	}

	public ClassLoader getClassLoader(Location location) {
		// No loading of compiler plugins.
		return null;
	}

	public FileObject getFileForInput(Location location, String packageName,
			String relativeName) throws IOException {
		if (logger.isLoggable(Level.INFO)) {
			logger.info("getFileForInput(): location=" + location
					+ ",packageName=" + packageName + ",relativeName="
					+ relativeName);
		}
		if (StandardLocation.SOURCE_PATH.equals(location)) {
			return this.sourcePathRepository.getFileForInput(this.context,
					packageName, relativeName);
		} else if (StandardLocation.CLASS_PATH.equals(location)) {
			return this.classPathRepository.getFileForInput(this.context,
					packageName, relativeName);
		} else {
			throw new java.lang.UnsupportedOperationException(
					"Not expected to be called for location=" + location + ".");
		}
	}

	public FileObject getFileForOutput(Location location, String packageName,
			String relativeName, FileObject sibling) throws IOException {
		if (logger.isLoggable(Level.INFO)) {
			logger.info("getFileForOutput(): location=" + location
					+ ",packageName=" + packageName + ",relativeName="
					+ relativeName);
		}
		throw new java.lang.UnsupportedOperationException(
				"Not expected to be called");
	}

	public JavaFileObject getJavaFileForInput(Location location,
			String className, Kind kind) throws IOException {
		if (logger.isLoggable(Level.INFO)) {
			logger.info("getJavaFileForInput(): location=" + location
					+ ",className=" + className + ",kind=" + kind);
		}
		if (StandardLocation.SOURCE_PATH.equals(location)) {
			return this.sourcePathRepository.getJavaFileForInput(this.context,
					className, kind);
		} else if (StandardLocation.CLASS_PATH.equals(location)) {
			return this.classPathRepository.getJavaFileForInput(this.context,
					className, kind);
		} else if (StandardLocation.CLASS_OUTPUT.equals(location)) {
			FileObject outFile;
			synchronized (this) {
				outFile = this.outFiles.get(className);
			}
			if (!(outFile instanceof JavaFileObject)) {
				throw new java.io.IOException(
						"No generated file found for class " + className + ".");
			}
			JavaFileObject jfo = (JavaFileObject) outFile;
			if (!kind.equals(jfo.getKind())) {
				throw new java.io.IOException(
						"No generated file found for class " + className
								+ " whose kind is " + kind + ".");
			}
			return jfo;
		} else {
			throw new java.lang.UnsupportedOperationException("location="
					+ location + ",className=" + className + ",kind=" + kind);
		}
	}

	private java.net.URI getOutputFileURI(String fileName)
			throws java.net.URISyntaxException {
		java.net.URL responseURL = this.context.getResponse().getResponseURL();
		String host = responseURL.getHost();
		int port = responseURL.getPort();
		String hostPort = host == null || host.length() == 0 ? "" : "//" + host
				+ (port == -1 ? "" : (":" + port));
		String outputPath = "/" + JavaResponseFileManager.class.getSimpleName()
				+ "-" + fileName;
		// Security: It's important that the URI host be the same as
		// the response host.
		return new java.net.URI(responseURL.getProtocol() + ":" + hostPort
				+ outputPath);
	}

	private String resultingClassName;

	public JavaFileObject getJavaFileForOutput(Location location,
			String className, Kind kind, FileObject sibling) throws IOException {
		if (logger.isLoggable(Level.INFO)) {
			logger.info("getJavaFileForOutput(): location=" + location
					+ ",className=" + className + ",kind=" + kind);
		}
		if (StandardLocation.CLASS_OUTPUT.equals(location)) {
			if (!Kind.CLASS.equals(kind)) {
				throw new UnsupportedOperationException(
						"Cannot obtain output Java file for kind " + kind
								+ " when location is " + location + ".");
			}
			if (this.isSameFile(sibling, this.compilationUnit)) {
				this.resultingClassName = className;
			}
			String fileName = className.replace('.', '/') + ".class";

			try {
				// Security: It's important that the URI host be the same as
				// the response host.
				java.net.URI uri = this.getOutputFileURI(fileName);
				JavaFileObject outFile = new OutputJavaFileObject(uri,
						fileName, kind,
						PathManager.getNestingKindForName(fileName));
				synchronized (this.outFiles) {
					this.outFiles.put(className, outFile);
				}
				return outFile;
			} catch (java.net.URISyntaxException use) {
				throw new IllegalStateException("Unexpected URI syntax error.",
						use);
			}
		} else {
			throw new java.lang.UnsupportedOperationException("location="
					+ location + ",className=" + className + ",kind=" + kind
					+ ",sibling=" + sibling);
		}
	}

	public boolean handleOption(String arg0, Iterator<String> arg1) {
		// Don't consume any options.
		return false;
	}

	public boolean hasLocation(Location location) {
		// All locations known?
		return true;
	}

	public String inferBinaryName(Location location, JavaFileObject file) {
		String fileName = file.getName();
		int lastDotIdx = fileName.lastIndexOf('.');
		String name = lastDotIdx == -1 ? fileName : fileName.substring(0,
				lastDotIdx);
		String result = name.replace('/', '.');
		return result;
	}

	public boolean isSameFile(FileObject arg0, FileObject arg1) {
		return arg0.equals(arg1);
	}

	public Iterable<JavaFileObject> list(Location location, String packageName,
			Set<Kind> kinds, boolean recurse) throws IOException {
		if (logger.isLoggable(Level.INFO)) {
			logger.info("list(): location=" + location + ",packageName="
					+ packageName + ",kinds=" + kinds + ",recurse=" + recurse);
		}
		if (location.equals(StandardLocation.PLATFORM_CLASS_PATH)) {
			return this.platformClassPathRepository.list(this.context,
					packageName, kinds, recurse);
		} else if (location.equals(StandardLocation.CLASS_PATH)) {
			return this.classPathRepository.list(this.context, packageName,
					kinds, recurse);
		} else if (location.equals(StandardLocation.SOURCE_PATH)) {
			return this.sourcePathRepository.list(this.context, packageName,
					kinds, recurse);
		} else {
			throw new java.lang.UnsupportedOperationException(
					"list(): Location=" + location + ",packageName="
							+ packageName + ",kinds=" + kinds + ",recurse="
							+ recurse);
		}
	}

	public int isSupportedOption(String option) {
		return 0;
	}

	public BuildResult getBuildResult(String sourceCode, Properties directives)
			throws ClientletException, IOException, ClassNotFoundException {
		String rcn = this.resultingClassName;
		if (rcn == null) {
			throw new ClientletException(
					"No resulting compiled response was found.");
		}
		Map<String, JavaFileObject> outFiles = this.outFiles;
		Map<String, OutputFileInfo> classBytes = new HashMap<String, OutputFileInfo>();
		for (Map.Entry<String, JavaFileObject> entry : outFiles.entrySet()) {
			JavaFileObject jfo = entry.getValue();
			InputStream in = jfo.openInputStream();
			try {
				byte[] bytes = IORoutines.load(in, 4096);
				classBytes.put(entry.getKey(), new OutputFileInfo(
						jfo.getName(), bytes));
			} finally {
				in.close();
			}
		}
		return new BuildResult(rcn, classBytes, sourceCode, directives);
	}

	public void setPersistedBuildResult(BuildResult buildResult)
			throws java.io.IOException {
		this.resultingClassName = buildResult.className;
		Map<String, JavaFileObject> outFiles = this.outFiles;
		for (Map.Entry<String, OutputFileInfo> entry : buildResult.outputFiles
				.entrySet()) {
			OutputFileInfo ofi = entry.getValue();
			String fileName = ofi.fileName;
			try {
				// Security: It's important that the URI host be the same as
				// the response host.
				java.net.URI uri = this.getOutputFileURI(fileName);
				JavaFileObject jfo = new OutputJavaFileObject(uri, fileName,
						Kind.CLASS, PathManager.getNestingKindForName(fileName));
				OutputStream out = jfo.openOutputStream();
				try {
					out.write(ofi.bytes);
					out.flush();
				} finally {
					out.close();
				}
				synchronized (outFiles) {
					outFiles.put(entry.getKey(), jfo);
				}
			} catch (java.net.URISyntaxException use) {
				throw new IllegalStateException("Unexpected URI syntax error.",
						use);
			}
		}
	}
}
