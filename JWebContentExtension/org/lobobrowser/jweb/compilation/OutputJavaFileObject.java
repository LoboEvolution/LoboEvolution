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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;

/**
 * The Class OutputJavaFileObject.
 */
public class OutputJavaFileObject implements JavaFileObject {

    /** The file name. */
    private final String fileName;

    /** The kind. */
    private final Kind kind;

    /** The nesting kind. */
    private final NestingKind nestingKind;

    /** The uri. */
    private final URI uri;

    /** The create time. */
    private final long createTime = System.currentTimeMillis();

    /**
     * Instantiates a new output java file object.
     *
     * @param uri
     *            the uri
     * @param fileName
     *            the file name
     * @param kind
     *            the kind
     * @param nestingKind
     *            the nesting kind
     */
    public OutputJavaFileObject(URI uri, final String fileName,
            final Kind kind, final NestingKind nestingKind) {
        this.fileName = fileName;
        this.kind = kind;
        this.nestingKind = nestingKind;
        this.uri = uri;
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.JavaFileObject#getAccessLevel()
     */
    @Override
    public Modifier getAccessLevel() {
        return Modifier.PUBLIC;
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.JavaFileObject#getKind()
     */
    @Override
    public Kind getKind() {
        return this.kind;
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.JavaFileObject#getNestingKind()
     */
    @Override
    public NestingKind getNestingKind() {
        return this.nestingKind;
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.JavaFileObject#isNameCompatible(java.lang.String,
     * javax.tools.JavaFileObject.Kind)
     */
    @Override
    public boolean isNameCompatible(String simpleName, Kind kind) {
        return PathManager.isNameCompatible(simpleName, kind);
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.FileObject#delete()
     */
    @Override
    public boolean delete() {
        return false;
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.FileObject#getCharContent(boolean)
     */
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors)
            throws IOException {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.FileObject#getLastModified()
     */
    @Override
    public long getLastModified() {
        return this.createTime;
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.FileObject#getName()
     */
    @Override
    public String getName() {
        return this.fileName;
    }

    /*
     * (non-Javadoc)
     * @see javax.tools.FileObject#openInputStream()
     */
    @Override
    public InputStream openInputStream() throws IOException {
        synchronized (this) {
            ByteArrayOutputStream out = this.out;
            if (out == null) {
                throw new java.io.FileNotFoundException();
            } else {
                return new ByteArrayInputStream(out.toByteArray());
            }
        }
    }

    /** The out. */
    private ByteArrayOutputStream out;

    /*
     * (non-Javadoc)
     * @see javax.tools.FileObject#openOutputStream()
     */
    @Override
    public OutputStream openOutputStream() throws IOException {
        synchronized (this) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            this.out = out;
            return out;
        }
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
