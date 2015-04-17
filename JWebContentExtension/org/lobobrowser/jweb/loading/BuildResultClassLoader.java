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
package org.lobobrowser.jweb.loading;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.CodeSource;
import java.security.SecureClassLoader;
import java.util.Collections;
import java.util.Enumeration;
// import java.util.logging.*;

import org.lobobrowser.jweb.compilation.BuildResult;
import org.lobobrowser.jweb.compilation.OutputFileInfo;

/**
 * The Class BuildResultClassLoader.
 */
public class BuildResultClassLoader extends SecureClassLoader {
    // TODO: Check for sealing violations?
    // private static final Logger logger =
    // Logger.getLogger(BuildResultClassLoader.class.getName());
    /** The build result. */
    private final BuildResult buildResult;

    /** The code location. */
    private final URL codeLocation;

    /**
     * Instantiates a new builds the result class loader.
     *
     * @param parent
     *            the parent
     * @param codeLocation
     *            the code location
     * @param buildResult
     *            the build result
     */
    public BuildResultClassLoader(ClassLoader parent, URL codeLocation,
            BuildResult buildResult) {
        super(parent);
        this.buildResult = buildResult;
        this.codeLocation = codeLocation;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.ClassLoader#findClass(java.lang.String)
     */
    @Override
    protected Class<?> findClass(String className)
            throws ClassNotFoundException {
        OutputFileInfo ofi = this.buildResult.outputFiles.get(className);
        if (ofi == null) {
            throw new ClassNotFoundException(className);
        }
        byte[] buffer = ofi.bytes;
        CodeSource codeSource = new CodeSource(this.codeLocation,
                (java.security.cert.Certificate[]) null);
        return this
                .defineClass(className, buffer, 0, buffer.length, codeSource);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.ClassLoader#findResource(java.lang.String)
     */
    @Override
    protected URL findResource(String resourceName) {
        // No resources in build result
        return null;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.ClassLoader#findResources(java.lang.String)
     */
    @Override
    protected Enumeration<URL> findResources(String name) throws IOException {
        return java.util.Collections.enumeration(Collections.<URL> emptyList());
    }

    /*
     * (non-Javadoc)
     * @see java.lang.ClassLoader#getResourceAsStream(java.lang.String)
     */
    @Override
    public InputStream getResourceAsStream(String resourceName) {
        // No resources in build result
        return null;
    }
}
