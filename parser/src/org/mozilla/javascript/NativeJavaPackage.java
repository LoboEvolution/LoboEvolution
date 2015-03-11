/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.Set;


/**
 * This class reflects Java packages into the JavaScript environment.  We
 * lazily reflect classes and subpackages, and use a caching/sharing
 * system to ensure that members reflected into one JavaPackage appear
 * in all other references to the same package (as with Packages.java.lang
 * and java.lang).
 *
 * @author Mike Shaver
 * @see NativeJavaArray
 * @see NativeJavaObject
 * @see NativeJavaClass
 */

public class NativeJavaPackage extends ScriptableObject
{
    
    /** The Constant serialVersionUID. */
    static final long serialVersionUID = 7445054382212031523L;

    /**
     * Instantiates a new native java package.
     *
     * @param internalUsage the internal usage
     * @param packageName the package name
     * @param classLoader the class loader
     */
    NativeJavaPackage(boolean internalUsage, String packageName,
                      ClassLoader classLoader)
    {
        this.packageName = packageName;
        this.classLoader = classLoader;
    }

    /**
     * Instantiates a new native java package.
     *
     * @param packageName the package name
     * @param classLoader the class loader
     * @deprecated NativeJavaPackage is an internal class, do not use
     * it directly.
     */
    public NativeJavaPackage(String packageName, ClassLoader classLoader) {
        this(false, packageName, classLoader);
    }

    /**
     * Instantiates a new native java package.
     *
     * @param packageName the package name
     * @deprecated NativeJavaPackage is an internal class, do not use
     * it directly.
     */
    public NativeJavaPackage(String packageName) {
        this(false, packageName,
             Context.getCurrentContext().getApplicationClassLoader());
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#getClassName()
     */
    @Override
    public String getClassName() {
        return "JavaPackage";
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#has(java.lang.String, org.mozilla.javascript.Scriptable)
     */
    @Override
    public boolean has(String id, Scriptable start) {
        return true;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#has(int, org.mozilla.javascript.Scriptable)
     */
    @Override
    public boolean has(int index, Scriptable start) {
        return false;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#put(java.lang.String, org.mozilla.javascript.Scriptable, java.lang.Object)
     */
    @Override
    public void put(String id, Scriptable start, Object value) {
        // Can't add properties to Java packages.  Sorry.
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#put(int, org.mozilla.javascript.Scriptable, java.lang.Object)
     */
    @Override
    public void put(int index, Scriptable start, Object value) {
        throw Context.reportRuntimeError0("msg.pkg.int");
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#get(java.lang.String, org.mozilla.javascript.Scriptable)
     */
    @Override
    public Object get(String id, Scriptable start) {
        return getPkgProperty(id, start, true);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#get(int, org.mozilla.javascript.Scriptable)
     */
    @Override
    public Object get(int index, Scriptable start) {
        return NOT_FOUND;
    }

    // set up a name which is known to be a package so we don't
    // need to look for a class by that name
    /**
     * Force package.
     *
     * @param name the name
     * @param scope the scope
     * @return the native java package
     */
    NativeJavaPackage forcePackage(String name, Scriptable scope)
    {
        Object cached = super.get(name, this);
        if (cached != null && cached instanceof NativeJavaPackage) {
            return (NativeJavaPackage) cached;
        } else {
            String newPackage = packageName.length() == 0
                                ? name
                                : packageName + "." + name;
            NativeJavaPackage pkg = new NativeJavaPackage(true, newPackage, classLoader);
            ScriptRuntime.setObjectProtoAndParent(pkg, scope);
            super.put(name, this, pkg);
            return pkg;
        }
    }

    /**
     * Gets the pkg property.
     *
     * @param name the name
     * @param start the start
     * @param createPkg the create pkg
     * @return the pkg property
     */
    synchronized Object getPkgProperty(String name, Scriptable start,
                                       boolean createPkg)
    {
        Object cached = super.get(name, start);
        if (cached != NOT_FOUND)
            return cached;
        if (negativeCache != null && negativeCache.contains(name)) {
            // Performance optimization: see bug 421071
            return null;
        }

        String className = (packageName.length() == 0)
                               ? name : packageName + '.' + name;
        Context cx = Context.getContext();
        ClassShutter shutter = cx.getClassShutter();
        Scriptable newValue = null;
        if (shutter == null || shutter.visibleToScripts(className)) {
            Class<?> cl = null;
            if (classLoader != null) {
                cl = Kit.classOrNull(classLoader, className);
            } else {
                cl = Kit.classOrNull(className);
            }
            if (cl != null) {
                WrapFactory wrapFactory = cx.getWrapFactory();
                newValue = wrapFactory.wrapJavaClass(cx, getTopLevelScope(this), cl);
                newValue.setPrototype(getPrototype());
            }
        }
        if (newValue == null) {
            if (createPkg) {
                NativeJavaPackage pkg;
                pkg = new NativeJavaPackage(true, className, classLoader);
                ScriptRuntime.setObjectProtoAndParent(pkg, getParentScope());
                newValue = pkg;
            } else {
                // add to negative cache
                if (negativeCache == null)
                    negativeCache = new HashSet<String>();
                negativeCache.add(name);
            }
        }
        if (newValue != null) {
            // Make it available for fast lookup and sharing of
            // lazily-reflected constructors and static members.
            super.put(name, start, newValue);
        }
        return newValue;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#getDefaultValue(java.lang.Class)
     */
    @Override
    public Object getDefaultValue(Class<?> ignored) {
        return toString();
    }

    /**
     * Read object.
     *
     * @param in the in
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException the class not found exception
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.classLoader = Context.getCurrentContext().getApplicationClassLoader();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "[JavaPackage " + packageName + "]";
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof NativeJavaPackage) {
            NativeJavaPackage njp = (NativeJavaPackage)obj;
            return packageName.equals(njp.packageName) &&
                   classLoader == njp.classLoader;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return packageName.hashCode() ^
               (classLoader == null ? 0 : classLoader.hashCode());
    }

    /** The package name. */
    private String packageName;
    
    /** The class loader. */
    private transient ClassLoader classLoader;
    
    /** The negative cache. */
    private Set<String> negativeCache = null;
}
