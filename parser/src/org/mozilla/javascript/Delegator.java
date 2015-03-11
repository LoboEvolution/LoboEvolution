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
 * @author Matthias Radestock
 * @see Function
 * @see Scriptable
 */

public class Delegator implements Function {

    /** The obj. */
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
     * @return the delegator
     * @see #construct(Context cx, Scriptable scope, Object[] args)
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
    
    /**
     * Gets the class name.
     *
     * @return the class name
     * @see org.mozilla.javascript.Scriptable#getClassName
     */
    public String getClassName() {
        return obj.getClassName();
    }
    
    /**
     * Gets the.
     *
     * @param name the name
     * @param start the start
     * @return the object
     * @see org.mozilla.javascript.Scriptable#get(String, Scriptable)
     */
    public Object get(String name, Scriptable start) {
        return obj.get(name,start);
    }
    
    /**
     * Gets the.
     *
     * @param index the index
     * @param start the start
     * @return the object
     * @see org.mozilla.javascript.Scriptable#get(int, Scriptable)
     */
    public Object get(int index, Scriptable start) {
        return obj.get(index,start);
        }
    
    /**
     * Checks for.
     *
     * @param name the name
     * @param start the start
     * @return true, if successful
     * @see org.mozilla.javascript.Scriptable#has(String, Scriptable)
     */
    public boolean has(String name, Scriptable start) {
        return obj.has(name,start);
        }
    
    /**
     * Checks for.
     *
     * @param index the index
     * @param start the start
     * @return true, if successful
     * @see org.mozilla.javascript.Scriptable#has(int, Scriptable)
     */
    public boolean has(int index, Scriptable start) {
        return obj.has(index,start);
        }
    
    /**
     * Put.
     *
     * @param name the name
     * @param start the start
     * @param value the value
     * @see org.mozilla.javascript.Scriptable#put(String, Scriptable, Object)
     */
    public void put(String name, Scriptable start, Object value) {
        obj.put(name,start,value);
    }
    
    /**
     * Put.
     *
     * @param index the index
     * @param start the start
     * @param value the value
     * @see org.mozilla.javascript.Scriptable#put(int, Scriptable, Object)
     */
    public void put(int index, Scriptable start, Object value) {
        obj.put(index,start,value);
    }
    
    /**
     * Delete.
     *
     * @param name the name
     * @see org.mozilla.javascript.Scriptable#delete(String)
     */
    public void delete(String name) {
        obj.delete(name);
    }
    
    /**
     * Delete.
     *
     * @param index the index
     * @see org.mozilla.javascript.Scriptable#delete(int)
     */
    public void delete(int index) {
        obj.delete(index);
    }
    
    /**
     * Gets the prototype.
     *
     * @return the prototype
     * @see org.mozilla.javascript.Scriptable#getPrototype
     */
    public Scriptable getPrototype() {
        return obj.getPrototype();
    }
    
    /**
     * Sets the prototype.
     *
     * @param prototype the new prototype
     * @see org.mozilla.javascript.Scriptable#setPrototype
     */
    public void setPrototype(Scriptable prototype) {
        obj.setPrototype(prototype);
    }
    
    /**
     * Gets the parent scope.
     *
     * @return the parent scope
     * @see org.mozilla.javascript.Scriptable#getParentScope
     */
    public Scriptable getParentScope() {
        return obj.getParentScope();
    }
    
    /**
     * Sets the parent scope.
     *
     * @param parent the new parent scope
     * @see org.mozilla.javascript.Scriptable#setParentScope
     */
    public void setParentScope(Scriptable parent) {
        obj.setParentScope(parent);
    }
    
    /**
     * Gets the ids.
     *
     * @return the ids
     * @see org.mozilla.javascript.Scriptable#getIds
     */
    public Object[] getIds() {
        return obj.getIds();
    }
    /**
     * Note that this method does not get forwarded to the delegee if
     * the <code>hint</code> parameter is null,
     * <code>ScriptRuntime.ScriptableClass</code> or
     * <code>ScriptRuntime.FunctionClass</code>. Instead the object
     * itself is returned.
     *
     * @param hint the type hint
     * @return the default value
     *
     * @see org.mozilla.javascript.Scriptable#getDefaultValue
     */
    public Object getDefaultValue(Class<?> hint) {
        return (hint == null ||
                hint == ScriptRuntime.ScriptableClass ||
                hint == ScriptRuntime.FunctionClass) ?
            this : obj.getDefaultValue(hint);
    }
    
    /**
     * Checks for instance.
     *
     * @param instance the instance
     * @return true, if successful
     * @see org.mozilla.javascript.Scriptable#hasInstance
     */
    public boolean hasInstance(Scriptable instance) {
        return obj.hasInstance(instance);
    }
    
    /**
     * Call.
     *
     * @param cx the cx
     * @param scope the scope
     * @param thisObj the this obj
     * @param args the args
     * @return the object
     * @see org.mozilla.javascript.Function#call
     */
    public Object call(Context cx, Scriptable scope, Scriptable thisObj,
                       Object[] args)
    {
        return ((Function)obj).call(cx,scope,thisObj,args);
    }

    /**
     * Note that if the <code>delegee</code> is <code>null</code>,
     * this method creates a new instance of the Delegator itself
     * rathert than forwarding the call to the
     * <code>delegee</code>. This permits the use of Delegator
     * prototypes.
     *
     * @param cx the current Context for this thread
     * @param scope an enclosing scope of the caller except
     *              when the function is called from a closure.
     * @param args the array of arguments
     * @return the allocated object
     *
     * @see Function#construct(Context, Scriptable, Object[])
     */
    public Scriptable construct(Context cx, Scriptable scope, Object[] args)
    {
        if (obj == null) {
            //this little trick allows us to declare prototype objects for
            //Delegators
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
        else {
            return ((Function)obj).construct(cx,scope,args);
        }
    }
}
