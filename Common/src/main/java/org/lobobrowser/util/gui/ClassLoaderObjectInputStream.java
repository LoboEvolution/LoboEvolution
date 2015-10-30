/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.util.gui;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * The Class ClassLoaderObjectInputStream.
 */
public class ClassLoaderObjectInputStream extends ObjectInputStream {

    /** The class loader. */
    private final ClassLoader classLoader;

    /**
     * Instantiates a new class loader object input stream.
     *
     * @param in
     *            the in
     * @param classLoader
     *            the class loader
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public ClassLoaderObjectInputStream(InputStream in, ClassLoader classLoader)
            throws IOException {
        super(in);
        this.classLoader = classLoader;
    }

    /*
     * (non-Javadoc)
     * @see java.io.ObjectInputStream#resolveClass(java.io.ObjectStreamClass)
     */
    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException,
    ClassNotFoundException {
        return Class.forName(desc.getName(), false, this.classLoader);
    }
}
