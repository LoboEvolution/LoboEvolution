/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.URL;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.SecureClassLoader;
import java.util.Map;
import java.util.WeakHashMap;


/**
 * The Class SecureCaller.
 *
 * @author Attila Szegedi
 */
public abstract class SecureCaller
{
    
    /** The Constant secureCallerImplBytecode. */
    private static final byte[] secureCallerImplBytecode = loadBytecode();

    // We're storing a CodeSource -> (ClassLoader -> SecureRenderer), since we
    // need to have one renderer per class loader. We're using weak hash maps
    // and soft references all the way, since we don't want to interfere with
    // cleanup of either CodeSource or ClassLoader objects.
    /** The Constant callers. */
    private static final Map<CodeSource,Map<ClassLoader,SoftReference<SecureCaller>>>
    callers =
        new WeakHashMap<CodeSource,Map<ClassLoader,SoftReference<SecureCaller>>>();

    /**
     * Call.
     *
     * @param callable the callable
     * @param cx the cx
     * @param scope the scope
     * @param thisObj the this obj
     * @param args the args
     * @return the object
     */
    public abstract Object call(Callable callable, Context cx,
            Scriptable scope, Scriptable thisObj, Object[] args);

    /**
     * Call the specified callable using a protection domain belonging to the
     * specified code source.
     *
     * @param codeSource the code source
     * @param callable the callable
     * @param cx the cx
     * @param scope the scope
     * @param thisObj the this obj
     * @param args the args
     * @return the object
     */
    static Object callSecurely(final CodeSource codeSource, Callable callable,
            Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
    {
        final Thread thread = Thread.currentThread();
        // Run in doPrivileged as we might be checked for "getClassLoader"
        // runtime permission
        final ClassLoader classLoader = (ClassLoader)AccessController.doPrivileged(
            new PrivilegedAction<Object>() {
                public Object run() {
                    return thread.getContextClassLoader();
                }
            });
        Map<ClassLoader,SoftReference<SecureCaller>> classLoaderMap;
        synchronized(callers)
        {
            classLoaderMap = callers.get(codeSource);
            if(classLoaderMap == null)
            {
                classLoaderMap = new WeakHashMap<ClassLoader,SoftReference<SecureCaller>>();
                callers.put(codeSource, classLoaderMap);
            }
        }
        SecureCaller caller;
        synchronized(classLoaderMap)
        {
            SoftReference<SecureCaller> ref = classLoaderMap.get(classLoader);
            if (ref != null) {
                caller = ref.get();
            } else {
                caller = null;
            }
            if (caller == null) {
                try
                {
                    // Run in doPrivileged as we'll be checked for
                    // "createClassLoader" runtime permission
                    caller = (SecureCaller)AccessController.doPrivileged(
                            new PrivilegedExceptionAction<Object>()
                    {
                        public Object run() throws Exception
                        {
                            ClassLoader effectiveClassLoader;
                            Class<?> thisClass = getClass();
                            if(classLoader.loadClass(thisClass.getName()) != thisClass) {
                                effectiveClassLoader = thisClass.getClassLoader();
                            } else {
                                effectiveClassLoader = classLoader;
                            }
                            SecureClassLoaderImpl secCl =
                                new SecureClassLoaderImpl(effectiveClassLoader);
                            Class<?> c = secCl.defineAndLinkClass(
                                    SecureCaller.class.getName() + "Impl",
                                    secureCallerImplBytecode, codeSource);
                            return c.newInstance();
                        }
                    });
                    classLoaderMap.put(classLoader, new SoftReference<SecureCaller>(caller));
                }
                catch(PrivilegedActionException ex)
                {
                    throw new UndeclaredThrowableException(ex.getCause());
                }
            }
        }
        return caller.call(callable, cx, scope, thisObj, args);
    }

    /**
     * The Class SecureClassLoaderImpl.
     */
    private static class SecureClassLoaderImpl extends SecureClassLoader
    {
        
        /**
         * Instantiates a new secure class loader impl.
         *
         * @param parent the parent
         */
        SecureClassLoaderImpl(ClassLoader parent)
        {
            super(parent);
        }

        /**
         * Define and link class.
         *
         * @param name the name
         * @param bytes the bytes
         * @param cs the cs
         * @return the class
         */
        Class<?> defineAndLinkClass(String name, byte[] bytes, CodeSource cs)
        {
            Class<?> cl = defineClass(name, bytes, 0, bytes.length, cs);
            resolveClass(cl);
            return cl;
        }
    }

    /**
     * Load bytecode.
     *
     * @return the byte[]
     */
    private static byte[] loadBytecode()
    {
        return (byte[])AccessController.doPrivileged(new PrivilegedAction<Object>()
        {
            public Object run()
            {
                return loadBytecodePrivileged();
            }
        });
    }

    /**
     * Load bytecode privileged.
     *
     * @return the byte[]
     */
    private static byte[] loadBytecodePrivileged()
    {
        URL url = SecureCaller.class.getResource("SecureCallerImpl.clazz");
        try
        {
            InputStream in = url.openStream();
            try
            {
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                for(;;)
                {
                    int r = in.read();
                    if(r == -1)
                    {
                        return bout.toByteArray();
                    }
                    bout.write(r);
                }
            }
            finally
            {
                in.close();
            }
        }
        catch(IOException e)
        {
            throw new UndeclaredThrowableException(e);
        }
    }
}
