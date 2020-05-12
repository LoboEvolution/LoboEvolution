/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.io.Serializable;

/**
 * This class implements the object lookup required for the
 * with statement.
 * It simply delegates every action to its prototype except
 * for operations on its parent.
 *
 * @author utente
 * @version $Id: $Id
 */
public class NativeWith implements Scriptable, SymbolScriptable, IdFunctionCall, Serializable {
    private static final long serialVersionUID = 1L;

    static void init(Scriptable scope, boolean sealed) {
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

    private NativeWith() {
    }

    /**
     * <p>Constructor for NativeWith.</p>
     *
     * @param parent a {@link org.mozilla.javascript.Scriptable} object.
     * @param prototype a {@link org.mozilla.javascript.Scriptable} object.
     */
    protected NativeWith(Scriptable parent, Scriptable prototype) {
        this.parent = parent;
        this.prototype = prototype;
    }

    /** {@inheritDoc} */
    @Override
    public String getClassName() {
        return "With";
    }

    /** {@inheritDoc} */
    @Override
    public boolean has(String id, Scriptable start)
    {
        return prototype.has(id, prototype);
    }

    /** {@inheritDoc} */
    @Override
    public boolean has(Symbol key, Scriptable start)
    {
        if (prototype instanceof SymbolScriptable) {
            return ((SymbolScriptable)prototype).has(key, prototype);
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean has(int index, Scriptable start)
    {
        return prototype.has(index, prototype);
    }

    /** {@inheritDoc} */
    @Override
    public Object get(String id, Scriptable start)
    {
        if (start == this) {
            start = prototype;
        }
        return prototype.get(id, start);
    }

    /** {@inheritDoc} */
    @Override
    public Object get(Symbol key, Scriptable start)
    {
        if (start == this) {
            start = prototype;
        }
        if (prototype instanceof SymbolScriptable) {
            return ((SymbolScriptable)prototype).get(key, start);
        }
        return Scriptable.NOT_FOUND;
    }

    /** {@inheritDoc} */
    @Override
    public Object get(int index, Scriptable start)
    {
        if (start == this) {
            start = prototype;
        }
        return prototype.get(index, start);
    }

    /** {@inheritDoc} */
    @Override
    public void put(String id, Scriptable start, Object value)
    {
        if (start == this)
            start = prototype;
        prototype.put(id, start, value);
    }

    /** {@inheritDoc} */
    @Override
    public void put(Symbol symbol, Scriptable start, Object value)
    {
        if (start == this) {
            start = prototype;
        }
        if (prototype instanceof SymbolScriptable) {
            ((SymbolScriptable)prototype).put(symbol, start, value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void put(int index, Scriptable start, Object value)
    {
        if (start == this)
            start = prototype;
        prototype.put(index, start, value);
    }

    /** {@inheritDoc} */
    @Override
    public void delete(String id)
    {
        prototype.delete(id);
    }

    /** {@inheritDoc} */
    @Override
    public void delete(Symbol key)
    {
        if (prototype instanceof SymbolScriptable) {
            ((SymbolScriptable)prototype).delete(key);
        }
    }


    /** {@inheritDoc} */
    @Override
    public void delete(int index)
    {
        prototype.delete(index);
    }

    /** {@inheritDoc} */
    @Override
    public Scriptable getPrototype() {
        return prototype;
    }

    /** {@inheritDoc} */
    @Override
    public void setPrototype(Scriptable prototype) {
        this.prototype = prototype;
    }

    /** {@inheritDoc} */
    @Override
    public Scriptable getParentScope() {
        return parent;
    }

    /** {@inheritDoc} */
    @Override
    public void setParentScope(Scriptable parent) {
        this.parent = parent;
    }

    /** {@inheritDoc} */
    @Override
    public Object[] getIds() {
        return prototype.getIds();
    }

    /** {@inheritDoc} */
    @Override
    public Object getDefaultValue(Class<?> typeHint) {
        return prototype.getDefaultValue(typeHint);
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasInstance(Scriptable value) {
        return prototype.hasInstance(value);
    }

    /**
     * Must return null to continue looping or the final collection result.
     *
     * @param value a boolean.
     * @return a {@link java.lang.Object} object.
     */
    protected Object updateDotQuery(boolean value)
    {
        // NativeWith itself does not support it
        throw new IllegalStateException();
    }

    /** {@inheritDoc} */
    @Override
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

    static boolean isWithFunction(Object functionObj)
    {
        if (functionObj instanceof IdFunctionObject) {
            IdFunctionObject f = (IdFunctionObject)functionObj;
            return f.hasTag(FTAG) && f.methodId() == Id_constructor;
        }
        return false;
    }

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

    private static final Object FTAG = "With";

    private static final int
        Id_constructor = 1;

    protected Scriptable prototype;
    protected Scriptable parent;
}
