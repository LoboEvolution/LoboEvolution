/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

// API class

package org.mozilla.javascript;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * <p>Abstract VMBridge class.</p>
 *
 *
 *
 */
public abstract class VMBridge
{

    static final VMBridge instance = makeInstance();

    private static VMBridge makeInstance()
    {
        String[] classNames = {
            "org.mozilla.javascript.VMBridge_custom",
            "org.mozilla.javascript.jdk18.VMBridge_jdk18",
        };
        for (int i = 0; i != classNames.length; ++i) {
            String className = classNames[i];
            Class<?> cl = Kit.classOrNull(className);
            if (cl != null) {
                VMBridge bridge = (VMBridge)Kit.newInstanceOrNull(cl);
                if (bridge != null) {
                    return bridge;
                }
            }
        }
        throw new IllegalStateException("Failed to create VMBridge instance");
    }

    /**
     * Return a helper object to optimize {@link org.mozilla.javascript.Context} access.
     * <p>
     * The runtime will pass the resulting helper object to the subsequent
     * calls to {@link #getContext(Object contextHelper)} and
     * {@link #setContext(Object contextHelper, Context cx)} methods.
     * In this way the implementation can use the helper to cache
     * information about current thread to make {@link org.mozilla.javascript.Context} access faster.
     *
     * @return a {@link java.lang.Object} object.
     */
    protected abstract Object getThreadContextHelper();

    /**
     * Get {@link org.mozilla.javascript.Context} instance associated with the current thread
     * or null if none.
     *
     * @param contextHelper The result of {@link #getThreadContextHelper()}
     *                      called from the current thread.
     * @return a {@link org.mozilla.javascript.Context} object.
     */
    protected abstract Context getContext(Object contextHelper);

    /**
     * Associate {@link org.mozilla.javascript.Context} instance with the current thread or remove
     * the current association if <code>cx</code> is null.
     *
     * @param contextHelper The result of {@link #getThreadContextHelper()}
     *                      called from the current thread.
     * @param cx a {@link org.mozilla.javascript.Context} object.
     */
    protected abstract void setContext(Object contextHelper, Context cx);

    /**
     * In many JVMSs, public methods in private
     * classes are not accessible by default (Sun Bug #4071593).
     * VMBridge instance should try to workaround that via, for example,
     * calling method.setAccessible(true) when it is available.
     * The implementation is responsible to catch all possible exceptions
     * like SecurityException if the workaround is not available.
     *
     * @return true if it was possible to make method accessible
     *         or false otherwise.
     * @param accessible a {@link java.lang.reflect.AccessibleObject} object.
     */
    protected abstract boolean tryToMakeAccessible(AccessibleObject accessible);

    /**
     * Create helper object to create later proxies implementing the specified
     * interfaces later. Under JDK 1.3 the implementation can look like:
     * <pre>
     * return java.lang.reflect.Proxy.getProxyClass(..., interfaces).
     *     getConstructor(new Class[] {
     *         java.lang.reflect.InvocationHandler.class });
     * </pre>
     *
     * @param interfaces Array with one or more interface class objects.
     * @param cf a {@link org.mozilla.javascript.ContextFactory} object.
     * @return a {@link java.lang.Object} object.
     */
    protected abstract Object getInterfaceProxyHelper(ContextFactory cf,
                                             Class<?>[] interfaces);

    /**
     * Create proxy object for {@link org.mozilla.javascript.InterfaceAdapter}. The proxy should call
     * {@link org.mozilla.javascript.InterfaceAdapter#invoke(ContextFactory, Object, Scriptable,
     *                                Object, Method, Object[])}
     * as implementation of interface methods associated with
     * <code>proxyHelper</code>. {@link java.lang.reflect.Method}
     *
     * @param proxyHelper The result of the previous call to
     *        {@link #getInterfaceProxyHelper(ContextFactory, Class[])}.
     * @param cf a {@link org.mozilla.javascript.ContextFactory} object.
     * @param adapter a {@link org.mozilla.javascript.InterfaceAdapter} object.
     * @param target a {@link java.lang.Object} object.
     * @param topScope a {@link org.mozilla.javascript.Scriptable} object.
     * @return a {@link java.lang.Object} object.
     */
    protected abstract Object newInterfaceProxy(Object proxyHelper,
                                       ContextFactory cf,
                                       InterfaceAdapter adapter,
                                       Object target,
                                       Scriptable topScope);
}
