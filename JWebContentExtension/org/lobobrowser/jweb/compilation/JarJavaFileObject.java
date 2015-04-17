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

import java.net.URI;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;

/**
 * The Class JarJavaFileObject.
 */
public class JarJavaFileObject extends JarFileObject implements JavaFileObject {

    /** The kind. */
    private final Kind kind;

    /** The nesting kind. */
    private final NestingKind nestingKind;

    /**
     * Instantiates a new jar java file object.
     *
     * @param jarFile
     *            the jar file
     * @param file
     *            the file
     * @param uri
     *            the uri
     * @param kind
     *            the kind
     * @param nestingKind
     *            the nesting kind
     */
    public JarJavaFileObject(JarFile jarFile, JarEntry file, URI uri,
            Kind kind, NestingKind nestingKind) {
        super(jarFile, file, uri);
        this.kind = kind;
        this.nestingKind = nestingKind;
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
        if (kind == Kind.CLASS) {
            return simpleName.toLowerCase().endsWith(".class");
        } else if (kind == Kind.SOURCE) {
            String sntl = simpleName.toLowerCase();
            return sntl.endsWith(".java") || sntl.endsWith(".fx");
        } else {
            throw new UnsupportedOperationException("simpleName=" + simpleName
                    + ",kind=" + kind);
        }
    }
}
