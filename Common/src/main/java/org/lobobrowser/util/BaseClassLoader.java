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
/*
 * Created on Jun 19, 2005
 */
package org.lobobrowser.util;

import java.security.SecureClassLoader;

/**
 * Base class for all project class loaders.
 *
 * @author J. H. S.
 */
public abstract class BaseClassLoader extends SecureClassLoader {
    /**
     * Instantiates a new base class loader.
     *
     * @param parent
     *            the parent
     */
    public BaseClassLoader(ClassLoader parent) {
        super(parent);
    }
    
    /**
     * Instantiates a new base class loader.
     */
    public BaseClassLoader() {
        super();
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.ClassLoader#loadClass(String, boolean)
     */
    @Override
    public synchronized Class loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        return super.loadClass(name, resolve);
    }
}
