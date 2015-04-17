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

import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.lobobrowser.clientlet.ClientletContext;
import org.lobobrowser.jweb.loading.BuildResultClassLoader;
import org.lobobrowser.jweb.loading.PathRepositoryClassLoader;

/**
 * The Class BuildResult.
 */
public class BuildResult implements java.io.Serializable {
    // Keep in mind that this class is persisted in disk and
    // also in main memory. In particular, a java.lang.Class instance
    // should not be retained here, unless we can more
    // precisely calculate the size of its closure.

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 22574500300001000L;

    /** The class name. */
    public final String className;

    /** The source code. */
    public final String sourceCode;

    /** The directives. */
    public final Properties directives;
    /** Map of class names to compiled .class bytes */
    public final Map<String, OutputFileInfo> outputFiles;

    /**
     * Instantiates a new builds the result.
     *
     * @param className
     *            the class name
     * @param outputFiles
     *            the output files
     * @param sourceCode
     *            the source code
     * @param directives
     *            the directives
     */
    public BuildResult(final String className,
            final Map<String, OutputFileInfo> outputFiles, String sourceCode,
            Properties directives) {
        this.className = className;
        this.outputFiles = outputFiles;
        this.sourceCode = sourceCode;
        this.directives = directives;
    }

    /**
     * Gets the estimated transient size.
     *
     * @return the estimated transient size
     */
    public int getEstimatedTransientSize() {
        int size = 512;
        for (Map.Entry<String, OutputFileInfo> entry : this.outputFiles
                .entrySet()) {
            byte[] bytes = entry.getValue().bytes;
            size += (bytes.length + 128);
        }
        return size;
    }

    /**
     * Builds the class loader.
     *
     * @param context
     *            the context
     * @param codeLocation
     *            the code location
     * @param systemLoader
     *            the system loader
     * @param classPathRepository
     *            the class path repository
     * @return the class loader
     */
    public ClassLoader buildClassLoader(ClientletContext context,
            URL codeLocation, ClassLoader systemLoader,
            PathRepository classPathRepository) {
        ClassLoader parentLoader = new PathRepositoryClassLoader(context,
                systemLoader, classPathRepository);
        ClassLoader finalLoader = new BuildResultClassLoader(parentLoader,
                codeLocation, this);
        return finalLoader;
    }

    /**
     * Load class.
     *
     * @param loader
     *            the loader
     * @return the class
     * @throws ClassNotFoundException
     *             the class not found exception
     */
    public Class loadClass(ClassLoader loader) throws ClassNotFoundException {
        // Creation of secure classloaders is what provides security here.
        return Class.forName(this.className, true, loader);
    }
}
