/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either version 2 of the
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
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.tools.FileObject;

/**
 * The Class JarFileObject.
 */
public class JarFileObject implements FileObject {

    /** The jar entry. */
    private final JarEntry jarEntry;

    /** The jar file. */
    private final JarFile jarFile;

    /** The uri. */
    private final URI uri;

    /**
     * Instantiates a new jar file object.
     *
     * @param jarFile
     *            the jar file
     * @param file
     *            the file
     * @param uri
     *            the uri
     */
    public JarFileObject(JarFile jarFile, JarEntry file, URI uri) {
        this.jarFile = jarFile;
        this.jarEntry = file;
        this.uri = uri;
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.FileObject#delete()
     */
    @Override
    public boolean delete() {
        throw new UnsupportedOperationException("No deletion in JAR file.");
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.FileObject#getCharContent(boolean)
     */
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors)
            throws IOException {
        InputStream in = this.openInputStream();
        try {
            return org.lobobrowser.util.io.IORoutines.loadAsText(in,
                    System.getProperty("file.encoding"), 4096);
        } finally {
            in.close();
        }
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.FileObject#getLastModified()
     */
    @Override
    public long getLastModified() {
        return this.jarEntry.getTime();
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.FileObject#getName()
     */
    @Override
    public String getName() {
        return this.jarEntry.getName();
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.FileObject#openInputStream()
     */
    @Override
    public InputStream openInputStream() throws IOException {
        return this.jarFile.getInputStream(this.jarEntry);
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.FileObject#openOutputStream()
     */
    @Override
    public OutputStream openOutputStream() throws IOException {
        throw new UnsupportedOperationException("No writing into JAR files.");
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.FileObject#openReader(boolean)
     */
    @Override
    public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
        return new InputStreamReader(this.openInputStream());
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.FileObject#openWriter()
     */
    @Override
    public Writer openWriter() throws IOException {
        return new OutputStreamWriter(this.openOutputStream());
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.FileObject#toUri()
     */
    @Override
    public URI toUri() {
        return this.uri;
    }
}
