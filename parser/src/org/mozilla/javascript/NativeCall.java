/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;


/**
 * This class implements the activation object.
 * 
 * See ECMA 10.1.6
 *
 * @author Norris Boyd
 * @see org.mozilla.javascript.Arguments
 */
public final class NativeCall extends IdScriptableObject
{
    
    /** The Constant serialVersionUID. */
    static final long serialVersionUID = -7471457301304454454L;

    /** The Constant CALL_TAG. */
    private static final Object CALL_TAG = "Call";

    /**
     * Inits the.
     *
     * @param scope the scope
     * @param sealed the sealed
     */
    static void init(Scriptable scope, boolean sealed)
    {
        NativeCall obj = new NativeCall();
        obj.exportAsJSClass(MAX_PROTOTYPE_ID, scope, sealed);
    }

    /**
     * Instantiates a new native call.
     */
    NativeCall() { }

    /**
     * Instantiates a new native call.
     *
     * @param function the function
     * @param scope the scope
     * @param args the args
     */
    NativeCall(NativeFunction function, Scriptable scope, Object[] args)
    {
        this.function = function;

        setParentScope(scope);
        // leave prototype null

        this.originalArgs = (args == null) ? ScriptRuntime.emptyArgs : args;

        // initialize values of arguments
        int paramAndVarCount = function.getParamAndVarCount();
        int paramCount = function.getParamCount();
        if (paramAndVarCount != 0) {
            for (int i = 0; i < paramCount; ++i) {
                String name = function.getParamOrVarName(i);
                Object val = i < args.length ? args[i]
                                             : Undefined.instance;
                defineProperty(name, val, PERMANENT);
            }
        }

        // initialize "arguments" property but only if it was not overridden by
        // the parameter with the same name
        if (!super.has("arguments", this)) {
            defineProperty("arguments", new Arguments(this), PERMANENT);
        }

        if (paramAndVarCount != 0) {
            for (int i = paramCount; i < paramAndVarCount; ++i) {
                String name = function.getParamOrVarName(i);
                if (!super.has(name, this)) {
                    if (function.getParamOrVarConst(i))
                        defineProperty(name, Undefined.instance, CONST);
                    else
                        defineProperty(name, Undefined.instance, PERMANENT);
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#getClassName()
     */
    @Override
    public String getClassName()
    {
        return "Call";
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.IdScriptableObject#findPrototypeId(java.lang.String)
     */
    @Override
    protected int findPrototypeId(String s)
    {
        return s.equals("constructor") ? Id_constructor : 0;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.IdScriptableObject#initPrototypeId(int)
     */
    @Override
    protected void initPrototypeId(int id)
    {
        String s;
        int arity;
        if (id == Id_constructor) {
            arity=1; s="constructor";
        } else {
            throw new IllegalArgumentException(String.valueOf(id));
        }
        initPrototypeMethod(CALL_TAG, id, s, arity);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.IdScriptableObject#execIdCall(org.mozilla.javascript.IdFunctionObject, org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[])
     */
    @Override
    public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope,
                             Scriptable thisObj, Object[] args)
    {
        if (!f.hasTag(CALL_TAG)) {
            return super.execIdCall(f, cx, scope, thisObj, args);
        }
        int id = f.methodId();
        if (id == Id_constructor) {
            if (thisObj != null) {
                throw Context.reportRuntimeError1("msg.only.from.new", "Call");
            }
            ScriptRuntime.checkDeprecated(cx, "Call");
            NativeCall result = new NativeCall();
            result.setPrototype(getObjectPrototype(scope));
            return result;
        }
        throw new IllegalArgumentException(String.valueOf(id));
    }

    /** The Constant MAX_PROTOTYPE_ID. */
    private static final int
        Id_constructor   = 1,
        MAX_PROTOTYPE_ID = 1;

    /** The function. */
    NativeFunction function;
    
    /** The original args. */
    Object[] originalArgs;

    /** The parent activation call. */
    transient NativeCall parentActivationCall;
}

