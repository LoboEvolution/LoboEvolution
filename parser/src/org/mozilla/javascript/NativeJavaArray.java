/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.lang.reflect.Array;


/**
 * This class reflects Java arrays into the JavaScript environment.
 *
 * @author Mike Shaver
 * @see NativeJavaClass
 * @see NativeJavaObject
 * @see NativeJavaPackage
 */

public class NativeJavaArray extends NativeJavaObject
{
    
    /** The Constant serialVersionUID. */
    static final long serialVersionUID = -924022554283675333L;

    /* (non-Javadoc)
     * @see org.mozilla.javascript.NativeJavaObject#getClassName()
     */
    @Override
    public String getClassName() {
        return "JavaArray";
    }

    /**
     * Wrap.
     *
     * @param scope the scope
     * @param array the array
     * @return the native java array
     */
    public static NativeJavaArray wrap(Scriptable scope, Object array) {
        return new NativeJavaArray(scope, array);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.NativeJavaObject#unwrap()
     */
    @Override
    public Object unwrap() {
        return array;
    }

    /**
     * Instantiates a new native java array.
     *
     * @param scope the scope
     * @param array the array
     */
    public NativeJavaArray(Scriptable scope, Object array) {
        super(scope, null, ScriptRuntime.ObjectClass);
        Class<?> cl = array.getClass();
        if (!cl.isArray()) {
            throw new RuntimeException("Array expected");
        }
        this.array = array;
        this.length = Array.getLength(array);
        this.cls = cl.getComponentType();
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.NativeJavaObject#has(java.lang.String, org.mozilla.javascript.Scriptable)
     */
    @Override
    public boolean has(String id, Scriptable start) {
        return id.equals("length") || super.has(id, start);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.NativeJavaObject#has(int, org.mozilla.javascript.Scriptable)
     */
    @Override
    public boolean has(int index, Scriptable start) {
        return 0 <= index && index < length;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.NativeJavaObject#get(java.lang.String, org.mozilla.javascript.Scriptable)
     */
    @Override
    public Object get(String id, Scriptable start) {
        if (id.equals("length"))
            return Integer.valueOf(length);
        Object result = super.get(id, start);
        if (result == NOT_FOUND &&
            !ScriptableObject.hasProperty(getPrototype(), id))
        {
            throw Context.reportRuntimeError2(
                "msg.java.member.not.found", array.getClass().getName(), id);
        }
        return result;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.NativeJavaObject#get(int, org.mozilla.javascript.Scriptable)
     */
    @Override
    public Object get(int index, Scriptable start) {
        if (0 <= index && index < length) {
            Context cx = Context.getContext();
            Object obj = Array.get(array, index);
            return cx.getWrapFactory().wrap(cx, this, obj, cls);
        }
        return Undefined.instance;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.NativeJavaObject#put(java.lang.String, org.mozilla.javascript.Scriptable, java.lang.Object)
     */
    @Override
    public void put(String id, Scriptable start, Object value) {
        // Ignore assignments to "length"--it's readonly.
        if (!id.equals("length"))
            throw Context.reportRuntimeError1(
                "msg.java.array.member.not.found", id);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.NativeJavaObject#put(int, org.mozilla.javascript.Scriptable, java.lang.Object)
     */
    @Override
    public void put(int index, Scriptable start, Object value) {
        if (0 <= index && index < length) {
            Array.set(array, index, Context.jsToJava(value, cls));
        }
        else {
            throw Context.reportRuntimeError2(
                "msg.java.array.index.out.of.bounds", String.valueOf(index),
                String.valueOf(length - 1));
        }
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.NativeJavaObject#getDefaultValue(java.lang.Class)
     */
    @Override
    public Object getDefaultValue(Class<?> hint) {
        if (hint == null || hint == ScriptRuntime.StringClass)
            return array.toString();
        if (hint == ScriptRuntime.BooleanClass)
            return Boolean.TRUE;
        if (hint == ScriptRuntime.NumberClass)
            return ScriptRuntime.NaNobj;
        return this;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.NativeJavaObject#getIds()
     */
    @Override
    public Object[] getIds() {
        Object[] result = new Object[length];
        int i = length;
        while (--i >= 0)
            result[i] = Integer.valueOf(i);
        return result;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.NativeJavaObject#hasInstance(org.mozilla.javascript.Scriptable)
     */
    @Override
    public boolean hasInstance(Scriptable value) {
        if (!(value instanceof Wrapper))
            return false;
        Object instance = ((Wrapper)value).unwrap();
        return cls.isInstance(instance);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.NativeJavaObject#getPrototype()
     */
    @Override
    public Scriptable getPrototype() {
        if (prototype == null) {
            prototype =
                ScriptableObject.getArrayPrototype(this.getParentScope());
        }
        return prototype;
    }

    /** The array. */
    Object array;
    
    /** The length. */
    int length;
    
    /** The cls. */
    Class<?> cls;
}
