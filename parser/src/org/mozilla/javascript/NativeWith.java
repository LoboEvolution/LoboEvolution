/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.io.Serializable;


/**
 * This class implements the object lookup required for the
 * <code>with</code> statement.
 * It simply delegates every action to its prototype except
 * for operations on its parent.
 */
public class NativeWith implements Scriptable, IdFunctionCall, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Inits the.
     *
     * @param scope the scope
     * @param sealed the sealed
     */
    static void init(Scriptable scope, boolean sealed)
    {
        NativeWith obj = new NativeWith();

        obj.setParentScope(scope);
        obj.setPrototype(ScriptableObject.getObjectPrototype(scope));

        IdFunctionObject ctor = new IdFunctionObject(obj, FTAG, Id_constructor,
                                         "With", 0, scope);
        ctor.markAsConstructor(obj);
        if (sealed) {
            ctor.sealObject();
        }
        ctor.exportAsScopeProperty();
    }

    /**
     * Instantiates a new native with.
     */
    private NativeWith() {
    }

    /**
     * Instantiates a new native with.
     *
     * @param parent the parent
     * @param prototype the prototype
     */
    protected NativeWith(Scriptable parent, Scriptable prototype) {
        this.parent = parent;
        this.prototype = prototype;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#getClassName()
     */
    public String getClassName() {
        return "With";
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#has(java.lang.String, org.mozilla.javascript.Scriptable)
     */
    public boolean has(String id, Scriptable start)
    {
        return prototype.has(id, prototype);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#has(int, org.mozilla.javascript.Scriptable)
     */
    public boolean has(int index, Scriptable start)
    {
        return prototype.has(index, prototype);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#get(java.lang.String, org.mozilla.javascript.Scriptable)
     */
    public Object get(String id, Scriptable start)
    {
        if (start == this)
            start = prototype;
        return prototype.get(id, start);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#get(int, org.mozilla.javascript.Scriptable)
     */
    public Object get(int index, Scriptable start)
    {
        if (start == this)
            start = prototype;
        return prototype.get(index, start);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#put(java.lang.String, org.mozilla.javascript.Scriptable, java.lang.Object)
     */
    public void put(String id, Scriptable start, Object value)
    {
        if (start == this)
            start = prototype;
        prototype.put(id, start, value);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#put(int, org.mozilla.javascript.Scriptable, java.lang.Object)
     */
    public void put(int index, Scriptable start, Object value)
    {
        if (start == this)
            start = prototype;
        prototype.put(index, start, value);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#delete(java.lang.String)
     */
    public void delete(String id)
    {
        prototype.delete(id);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#delete(int)
     */
    public void delete(int index)
    {
        prototype.delete(index);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#getPrototype()
     */
    public Scriptable getPrototype() {
        return prototype;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#setPrototype(org.mozilla.javascript.Scriptable)
     */
    public void setPrototype(Scriptable prototype) {
        this.prototype = prototype;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#getParentScope()
     */
    public Scriptable getParentScope() {
        return parent;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#setParentScope(org.mozilla.javascript.Scriptable)
     */
    public void setParentScope(Scriptable parent) {
        this.parent = parent;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#getIds()
     */
    public Object[] getIds() {
        return prototype.getIds();
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#getDefaultValue(java.lang.Class)
     */
    public Object getDefaultValue(Class<?> typeHint) {
        return prototype.getDefaultValue(typeHint);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#hasInstance(org.mozilla.javascript.Scriptable)
     */
    public boolean hasInstance(Scriptable value) {
        return prototype.hasInstance(value);
    }

    /**
     * Must return null to continue looping or the final collection result.
     *
     * @param value the value
     * @return the object
     */
    protected Object updateDotQuery(boolean value)
    {
        // NativeWith itself does not support it
        throw new IllegalStateException();
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.IdFunctionCall#execIdCall(org.mozilla.javascript.IdFunctionObject, org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[])
     */
    public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope,
                             Scriptable thisObj, Object[] args)
    {
        if (f.hasTag(FTAG)) {
            if (f.methodId() == Id_constructor) {
                throw Context.reportRuntimeError1("msg.cant.call.indirect", "With");
            }
        }
        throw f.unknown();
    }

    /**
     * Checks if is with function.
     *
     * @param functionObj the function obj
     * @return true, if is with function
     */
    static boolean isWithFunction(Object functionObj)
    {
        if (functionObj instanceof IdFunctionObject) {
            IdFunctionObject f = (IdFunctionObject)functionObj;
            return f.hasTag(FTAG) && f.methodId() == Id_constructor;
        }
        return false;
    }

    /**
     * New with special.
     *
     * @param cx the cx
     * @param scope the scope
     * @param args the args
     * @return the object
     */
    static Object newWithSpecial(Context cx, Scriptable scope, Object[] args)
    {
        ScriptRuntime.checkDeprecated(cx, "With");
        scope = ScriptableObject.getTopLevelScope(scope);
        NativeWith thisObj = new NativeWith();
        thisObj.setPrototype(args.length == 0
                             ? ScriptableObject.getObjectPrototype(scope)
                             : ScriptRuntime.toObject(cx, scope, args[0]));
        thisObj.setParentScope(scope);
        return thisObj;
    }

    /** The Constant FTAG. */
    private static final Object FTAG = "With";

    /** The Constant Id_constructor. */
    private static final int
        Id_constructor = 1;

    /** The prototype. */
    protected Scriptable prototype;
    
    /** The parent. */
    protected Scriptable parent;
}
