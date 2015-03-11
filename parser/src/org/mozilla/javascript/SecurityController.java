/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

// API class

package org.mozilla.javascript;


/**
 * This class describes the support needed to implement security.
 * <p>
 * Three main pieces of functionality are required to implement
 * security for JavaScript. First, it must be possible to define
 * classes with an associated security domain. (This security
 * domain may be any object incorporating notion of access
 * restrictions that has meaning to an embedding; for a client-side
 * JavaScript embedding this would typically be
 * java.security.ProtectionDomain or similar object depending on an
 * origin URL and/or a digital certificate.)
 * Next it must be possible to get a security domain object that
 * allows a particular action only if all security domains
 * associated with code on the current Java stack allows it. And
 * finally, it must be possible to execute script code with
 * associated security domain injected into Java stack.
 * <p>
 * These three pieces of functionality are encapsulated in the
 * SecurityController class.
 *
 * @see org.mozilla.javascript.Context#setSecurityController(SecurityController)
 * @see java.lang.ClassLoader
 * @since 1.5 Release 4
 */
public abstract class SecurityController
{
    
    /** The global. */
    private static SecurityController global;

// The method must NOT be public or protected
    /**
 * Global.
 *
 * @return the security controller
 */
static SecurityController global()
    {
        return global;
    }

    /**
     * Check if global {@link SecurityController} was already installed.
     *
     * @return true, if successful
     * @see #initGlobal(SecurityController controller)
     */
    public static boolean hasGlobal()
    {
        return global != null;
    }

    /**
     * Initialize global controller that will be used for all
     * security-related operations. The global controller takes precedence
     * over already installed {@link Context}-specific controllers and cause
     * any subsequent call to
     * {@link Context#setSecurityController(SecurityController)}
     * to throw an exception.
     * <p>
     * The method can only be called once.
     *
     * @param controller the controller
     * @see #hasGlobal()
     */
    public static void initGlobal(SecurityController controller)
    {
        if (controller == null) throw new IllegalArgumentException();
        if (global != null) {
            throw new SecurityException("Cannot overwrite already installed global SecurityController");
        }
        global = controller;
    }

    /**
     * Get class loader-like object that can be used
     * to define classes with the given security context.
     *
     * @param parentLoader parent class loader to delegate search for classes
     *        not defined by the class loader itself
     * @param securityDomain some object specifying the security
     *        context of the code that is defined by the returned class loader.
     * @return the generated class loader
     */
    public abstract GeneratedClassLoader createClassLoader(
        ClassLoader parentLoader, Object securityDomain);

    /**
     * Create {@link GeneratedClassLoader} with restrictions imposed by
     * staticDomain and all current stack frames.
     * The method uses the SecurityController instance associated with the
     * current {@link Context} to construct proper dynamic domain and create
     * corresponding class loader.
     * If no SecurityController is associated with the current {@link Context} ,
     * the method calls {@link Context#createClassLoader(ClassLoader parent)}.
     *
     * @param parent parent class loader. If null,
     *        {@link Context#getApplicationClassLoader()} will be used.
     * @param staticDomain static security domain.
     * @return the generated class loader
     */
    public static GeneratedClassLoader createLoader(
        ClassLoader parent, Object staticDomain)
    {
        Context cx = Context.getContext();
        if (parent == null) {
            parent = cx.getApplicationClassLoader();
        }
        SecurityController sc = cx.getSecurityController();
        GeneratedClassLoader loader;
        if (sc == null) {
            loader = cx.createClassLoader(parent);
        } else {
            Object dynamicDomain = sc.getDynamicSecurityDomain(staticDomain);
            loader = sc.createClassLoader(parent, dynamicDomain);
        }
        return loader;
    }

    /**
     * Gets the static security domain class.
     *
     * @return the static security domain class
     */
    public static Class<?> getStaticSecurityDomainClass() {
        SecurityController sc = Context.getContext().getSecurityController();
        return sc == null ? null : sc.getStaticSecurityDomainClassInternal();
    }

    /**
     * Gets the static security domain class internal.
     *
     * @return the static security domain class internal
     */
    public Class<?> getStaticSecurityDomainClassInternal()
    {
        return null;
    }

    /**
     * Get dynamic security domain that allows an action only if it is allowed
     * by the current Java stack and <i>securityDomain</i>. If
     * <i>securityDomain</i> is null, return domain representing permissions
     * allowed by the current stack.
     *
     * @param securityDomain the security domain
     * @return the dynamic security domain
     */
    public abstract Object getDynamicSecurityDomain(Object securityDomain);

    /**
     * Call {@link
     * Callable#call(Context cx, Scriptable scope, Scriptable thisObj,
     *               Object[] args)}
     * of <i>callable</i> under restricted security domain where an action is
     * allowed only if it is allowed according to the Java stack on the
     * moment of the <i>execWithDomain</i> call and <i>securityDomain</i>.
     * Any call to {@link #getDynamicSecurityDomain(Object)} during
     * execution of <tt>callable.call(cx, scope, thisObj, args)</tt>
     * should return a domain incorporate restrictions imposed by
     * <i>securityDomain</i> and Java stack on the moment of callWithDomain
     * invocation.
     * <p>
     * The method should always be overridden, it is not declared abstract
     * for compatibility reasons.
     *
     * @param securityDomain the security domain
     * @param cx the cx
     * @param callable the callable
     * @param scope the scope
     * @param thisObj the this obj
     * @param args the args
     * @return the object
     */
    public Object callWithDomain(Object securityDomain, Context cx,
                                 final Callable callable, Scriptable scope,
                                 final Scriptable thisObj, final Object[] args)
    {
        return execWithDomain(cx, scope, new Script()
        {
            public Object exec(Context cx, Scriptable scope)
            {
                return callable.call(cx, scope, thisObj, args);
            }

        }, securityDomain);
    }

    /**
     * Exec with domain.
     *
     * @param cx the cx
     * @param scope the scope
     * @param script the script
     * @param securityDomain the security domain
     * @return the object
     * @deprecated The application should not override this method and instead
     * override
     * {@link #callWithDomain(Object securityDomain, Context cx, Callable callable, Scriptable scope, Scriptable thisObj, Object[] args)}.
     */
    public Object execWithDomain(Context cx, Scriptable scope,
                                 Script script, Object securityDomain)
    {
        throw new IllegalStateException("callWithDomain should be overridden");
    }


}
