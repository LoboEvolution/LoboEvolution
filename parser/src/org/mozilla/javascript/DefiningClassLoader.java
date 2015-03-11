/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;


/**
 * Load generated classes.
 *
 * @author Norris Boyd
 */
public class DefiningClassLoader extends ClassLoader
    implements GeneratedClassLoader
{
    
    /**
     * Instantiates a new defining class loader.
     */
    public DefiningClassLoader() {
        this.parentLoader = getClass().getClassLoader();
    }

    /**
     * Instantiates a new defining class loader.
     *
     * @param parentLoader the parent loader
     */
    public DefiningClassLoader(ClassLoader parentLoader) {
        this.parentLoader = parentLoader;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.GeneratedClassLoader#defineClass(java.lang.String, byte[])
     */
    public Class<?> defineClass(String name, byte[] data) {
        // Use our own protection domain for the generated classes.
        // TODO: we might want to use a separate protection domain for classes
        // compiled from scripts, based on where the script was loaded from.
        return super.defineClass(name, data, 0, data.length,
                SecurityUtilities.getProtectionDomain(getClass()));
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.GeneratedClassLoader#linkClass(java.lang.Class)
     */
    public void linkClass(Class<?> cl) {
        resolveClass(cl);
    }

    /* (non-Javadoc)
     * @see java.lang.ClassLoader#loadClass(java.lang.String, boolean)
     */
    @Override
    public Class<?> loadClass(String name, boolean resolve)
        throws ClassNotFoundException
    {
        Class<?> cl = findLoadedClass(name);
        if (cl == null) {
            if (parentLoader != null) {
                cl = parentLoader.loadClass(name);
            } else {
                cl = findSystemClass(name);
            }
        }
        if (resolve) {
            resolveClass(cl);
        }
        return cl;
    }

    /** The parent loader. */
    private final ClassLoader parentLoader;
}
