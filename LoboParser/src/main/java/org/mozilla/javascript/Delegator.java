/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

// API class

package org.mozilla.javascript;

/**
 * This is a helper class for implementing wrappers around Scriptable
 * objects. It implements the Function interface and delegates all
 * invocations to a delegee Scriptable object. The normal use of this
 * class involves creating a sub-class and overriding one or more of
 * the methods.
 *
 * A useful application is the implementation of interceptors,
 * pre/post conditions, debugging.
 *
 * @see Function
 * @see Scriptable
 * Author Matthias Radestock
 *
 */
public class Delegator
    implements Function, SymbolScriptable {

    protected Scriptable obj = null;

    /**
     * Create a Delegator prototype.
     *
     * This constructor should only be used for creating prototype
     * objects of Delegator.
     *
     * @see org.mozilla.javascript.Delegator#construct
     */
    public Delegator() {
    }

    /**
     * Create a new Delegator that forwards requests to a delegee
     * Scriptable object.
     *
     * @param obj the delegee
     * @see org.mozilla.javascript.Scriptable
     */
    public Delegator(Scriptable obj) {
        this.obj = obj;
    }

    /**
     * Crete new Delegator instance.
     * The default implementation calls this.getClass().newInstance().
     *
     * @see #construct(Context cx, Scriptable scope, Object[] args)
     * @return a {@link org.mozilla.javascript.Delegator} object.
     */
    protected Delegator newInstance()
    {
        try {
            return this.getClass().newInstance();
        } catch (Exception ex) {
            throw Context.throwAsScriptRuntimeEx(ex);
        }
    }

    /**
     * Retrieve the delegee.
     *
     * @return the delegee
     */
    public Scriptable getDelegee() {
        return obj;
    }

    /**
     * Set the delegee.
     *
     * @param obj the delegee
     * @see org.mozilla.javascript.Scriptable
     */
    public void setDelegee(Scriptable obj) {
        this.obj = obj;
    }

    /** {@inheritDoc} */
    @Override
    public String getClassName() {
        return getDelegee().getClassName();
    }

    /** {@inheritDoc} */
    @Override
    public Object get(String name, Scriptable start) {
        return getDelegee().get(name,start);
    }

    /** {@inheritDoc} */
    @Override
    public Object get(Symbol key, Scriptable start) {
        final Scriptable delegee = getDelegee();
        if (delegee instanceof SymbolScriptable) {
            return ((SymbolScriptable) delegee).get(key, start);
        }
        return Scriptable.NOT_FOUND;
    }

    /** {@inheritDoc} */
    @Override
    public Object get(int index, Scriptable start) {
        return getDelegee().get(index,start);
    }

    /** {@inheritDoc} */
    @Override
    public boolean has(String name, Scriptable start) {
        return getDelegee().has(name,start);
    }

    /** {@inheritDoc} */
    @Override
    public boolean has(Symbol key, Scriptable start) {
        final Scriptable delegee = getDelegee();
        if (delegee instanceof SymbolScriptable) {
            return ((SymbolScriptable) delegee).has(key, start);
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean has(int index, Scriptable start) {
        return getDelegee().has(index,start);
    }

    /** {@inheritDoc} */
    @Override
    public void put(String name, Scriptable start, Object value) {
        getDelegee().put(name,start,value);
    }

    /** {@inheritDoc} */
    @Override
    public void put(Symbol symbol, Scriptable start, Object value) {
        final Scriptable delegee = getDelegee();
        if (delegee instanceof SymbolScriptable) {
            ((SymbolScriptable) delegee).put(symbol, start, value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void put(int index, Scriptable start, Object value) {
        getDelegee().put(index,start,value);
    }

    /** {@inheritDoc} */
    @Override
    public void delete(String name) {
        getDelegee().delete(name);
    }

    /** {@inheritDoc} */
    @Override
    public void delete(Symbol key) {
        final Scriptable delegee = getDelegee();
        if (delegee instanceof SymbolScriptable) {
            ((SymbolScriptable) delegee).delete(key);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void delete(int index) {
        getDelegee().delete(index);
    }

    /** {@inheritDoc} */
    @Override
    public Scriptable getPrototype() {
        return getDelegee().getPrototype();
    }

    /** {@inheritDoc} */
    @Override
    public void setPrototype(Scriptable prototype) {
        getDelegee().setPrototype(prototype);
    }

    /** {@inheritDoc} */
    @Override
    public Scriptable getParentScope() {
        return getDelegee().getParentScope();
    }

    /** {@inheritDoc} */
    @Override
    public void setParentScope(Scriptable parent) {
        getDelegee().setParentScope(parent);
    }

    /** {@inheritDoc} */
    @Override
    public Object[] getIds() {
        return getDelegee().getIds();
    }

    /**
     * {@inheritDoc}
     *
     * Note that this method does not get forwarded to the delegee if
     * the <code>hint</code> parameter is null,
     * <code>ScriptRuntime.ScriptableClass</code> or
     * <code>ScriptRuntime.FunctionClass</code>. Instead the object
     * itself is returned.
     * @see org.mozilla.javascript.Scriptable#getDefaultValue
     */
    @Override
    public Object getDefaultValue(Class<?> hint) {
        return (hint == null ||
                hint == ScriptRuntime.ScriptableClass ||
                hint == ScriptRuntime.FunctionClass) ?
            this : getDelegee().getDefaultValue(hint);
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasInstance(Scriptable instance) {
        return getDelegee().hasInstance(instance);
    }

    /** {@inheritDoc} */
    @Override
    public Object call(Context cx, Scriptable scope, Scriptable thisObj,
                       Object[] args)
    {
        return ((Function)getDelegee()).call(cx,scope,thisObj,args);
    }

    /**
     * {@inheritDoc}
     *
     * Note that if the <code>delegee</code> is <code>null</code>,
     * this method creates a new instance of the Delegator itself
     * rathert than forwarding the call to the
     * <code>delegee</code>. This permits the use of Delegator
     * prototypes.
     * @see Function#construct(Context, Scriptable, Object[])
     */
    @Override
    public Scriptable construct(Context cx, Scriptable scope, Object[] args)
    {
        Scriptable myDelegee = getDelegee();
        if (myDelegee == null) {
            //this little trick allows us to declare prototype objects for Delegators
            Delegator n = newInstance();
            Scriptable delegee;
            if (args.length == 0) {
                delegee = new NativeObject();
            } else {
                delegee = ScriptRuntime.toObject(cx, scope, args[0]);
            }
            n.setDelegee(delegee);
            return n;
        }
        return ((Function)myDelegee).construct(cx, scope, args);
    }
}
