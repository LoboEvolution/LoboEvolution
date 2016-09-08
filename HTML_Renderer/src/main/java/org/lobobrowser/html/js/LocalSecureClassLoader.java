/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.js;

import java.security.CodeSource;
import java.security.SecureClassLoader;

import org.mozilla.javascript.GeneratedClassLoader;

/**
 * The Class LocalSecureClassLoader.
 */
public class LocalSecureClassLoader extends SecureClassLoader implements
GeneratedClassLoader {

    /** The codesource. */
    private CodeSource codesource;

    /**
     * Instantiates a new local secure class loader.
     *
     * @param parent
     *            the parent
     * @param codesource
     *            the codesource
     */
    public LocalSecureClassLoader(ClassLoader parent, CodeSource codesource) {
        super(parent);
        this.codesource = codesource;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.mozilla.javascript.GeneratedClassLoader#defineClass(java.lang.String,
     * byte[])
     */
    @Override
    public Class defineClass(String name, byte[] b) {
        return this.defineClass(name, b, 0, b.length, codesource);
    }

    /*
     * (non-Javadoc)
     * @see org.mozilla.javascript.GeneratedClassLoader#linkClass(java.lang.Class)
     */
    @Override
    public void linkClass(Class clazz) {
        super.resolveClass(clazz);
    }
}
