/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.jweb.compilation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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

/**
 * The Class JavaResponseFileManager.
 */
public class JavaResponseFileManager implements JavaFileManager {

    /** The Constant logger. */
    private static final Logger logger = Logger
            .getLogger(JavaResponseFileManager.class.getName());

    /** FileObject's by class name. */
    private Map<String, JavaFileObject> outFiles = new HashMap<String, JavaFileObject>();

    /** The class path repository. */
    private final PathRepository classPathRepository;

    /** The source path repository. */
    private final PathRepository sourcePathRepository;

    /** The platform class path repository. */
    private final PathRepository platformClassPathRepository;

    /** The context. */
    private final ClientletContext context;

    /** The compilation unit. */
    private JavaFileObject compilationUnit;

    /**
     * Instantiates a new java response file manager.
     *
     * @param context
     *            the context
     * @param pcpr
     *            the pcpr
     * @param cpr
     *            the cpr
     * @param spr
     *            the spr
     */
    public JavaResponseFileManager(ClientletContext context,
            PathRepository pcpr, PathRepository cpr, PathRepository spr) {
        this.context = context;
        this.platformClassPathRepository = pcpr;
        this.classPathRepository = cpr;
        this.sourcePathRepository = spr;
    }

    /**
     * Sets the compilation unit.
     *
     * @param cu
     *            the new compilation unit
     */
    public void setCompilationUnit(JavaFileObject cu) {
        this.compilationUnit = cu;
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.JavaFileManager#close()
     */
    @Override
    public void close() throws IOException {
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.JavaFileManager#flush()
     */
    @Override
    public void flush() throws IOException {
    }

    /*
     * (non-Javadoc)
     * @see
     * javax.tools.JavaFileManager#getClassLoader(javax.tools.JavaFileManager.Location
     * )
     */
    @Override
    public ClassLoader getClassLoader(Location location) {
        // No loading of compiler plugins.
        return null;
    }

    /*
     * (non-Javadoc)
     * @see
     * javax.tools.JavaFileManager#getFileForInput(javax.tools.JavaFileManager.Location
     * , java.lang.String, java.lang.String)
     */
    @Override
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
            throw new UnsupportedOperationException(
                    "Not expected to be called for location=" + location + ".");
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * javax.tools.JavaFileManager#getFileForOutput(javax.tools.JavaFileManager.
     * Location, java.lang.String, java.lang.String, javax.tools.FileObject)
     */
    @Override
    public FileObject getFileForOutput(Location location, String packageName,
            String relativeName, FileObject sibling) throws IOException {
        if (logger.isLoggable(Level.INFO)) {
            logger.info("getFileForOutput(): location=" + location
                    + ",packageName=" + packageName + ",relativeName="
                    + relativeName);
        }
        throw new UnsupportedOperationException("Not expected to be called");
    }

    /*
     * (non-Javadoc)
     * @see
     * javax.tools.JavaFileManager#getJavaFileForInput(javax.tools.JavaFileManager
     * .Location, java.lang.String, javax.tools.JavaFileObject.Kind)
     */
    @Override
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
            throw new UnsupportedOperationException("location=" + location
                    + ",className=" + className + ",kind=" + kind);
        }
    }

    /**
     * Gets the output file uri.
     *
     * @param fileName
     *            the file name
     * @return the output file uri
     * @throws URISyntaxException
     *             the URI syntax exception
     */
    private java.net.URI getOutputFileURI(String fileName)
            throws URISyntaxException {
        URL responseURL = this.context.getResponse().getResponseURL();
        String host = responseURL.getHost();
        int port = responseURL.getPort();
        String hostPort = (host == null) || (host.length() == 0) ? "" : "//"
                + host + (port == -1 ? "" : (":" + port));
        String outputPath = "/" + JavaResponseFileManager.class.getSimpleName()
                + "-" + fileName;
        // Security: It's important that the URI host be the same as
        // the response host.
        return new URI(responseURL.getProtocol() + ":" + hostPort + outputPath);
    }

    /** The resulting class name. */
    private String resultingClassName;

    /*
     * (non-Javadoc)
     * @see
     * javax.tools.JavaFileManager#getJavaFileForOutput(javax.tools.JavaFileManager
     * .Location, java.lang.String, javax.tools.JavaFileObject.Kind,
     * javax.tools.FileObject)
     */
    @Override
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
            } catch (URISyntaxException use) {
                throw new IllegalStateException("Unexpected URI syntax error.",
                        use);
            }
        } else {
            throw new UnsupportedOperationException("location=" + location
                    + ",className=" + className + ",kind=" + kind + ",sibling="
                    + sibling);
        }
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.JavaFileManager#handleOption(java.lang.String,
     * java.util.Iterator)
     */
    @Override
    public boolean handleOption(String arg0, Iterator<String> arg1) {
        // Don't consume any options.
        return false;
    }

    /*
     * (non-Javadoc)
     * @see
     * javax.tools.JavaFileManager#hasLocation(javax.tools.JavaFileManager.Location)
     */
    @Override
    public boolean hasLocation(Location location) {
        // All locations known?
        return true;
    }

    /*
     * (non-Javadoc)
     * @see
     * javax.tools.JavaFileManager#inferBinaryName(javax.tools.JavaFileManager.Location
     * , javax.tools.JavaFileObject)
     */
    @Override
    public String inferBinaryName(Location location, JavaFileObject file) {
        String fileName = file.getName();
        int lastDotIdx = fileName.lastIndexOf('.');
        String name = lastDotIdx == -1 ? fileName : fileName.substring(0,
                lastDotIdx);
        String result = name.replace('/', '.');
        return result;
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.JavaFileManager#isSameFile(javax.tools.FileObject,
     * javax.tools.FileObject)
     */
    @Override
    public boolean isSameFile(FileObject arg0, FileObject arg1) {
        return arg0.equals(arg1);
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.JavaFileManager#list(javax.tools.JavaFileManager.Location,
     * java.lang.String, java.util.Set, boolean)
     */
    @Override
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
            throw new UnsupportedOperationException("list(): Location="
                    + location + ",packageName=" + packageName + ",kinds="
                    + kinds + ",recurse=" + recurse);
        }
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.OptionChecker#isSupportedOption(java.lang.String)
     */
    @Override
    public int isSupportedOption(String option) {
        return 0;
    }

    /**
     * Gets the builds the result.
     *
     * @param sourceCode
     *            the source code
     * @param directives
     *            the directives
     * @return the builds the result
     * @throws ClientletException
     *             the clientlet exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException
     *             the class not found exception
     */
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

    /**
     * Sets the persisted build result.
     *
     * @param buildResult
     *            the new persisted build result
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public void setPersistedBuildResult(BuildResult buildResult)
            throws IOException {
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
            } catch (URISyntaxException use) {
                throw new IllegalStateException("Unexpected URI syntax error.",
                        use);
            }
        }
    }
}
