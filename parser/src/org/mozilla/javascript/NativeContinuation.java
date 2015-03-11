/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;


/**
 * The Class NativeContinuation.
 */
public final class NativeContinuation extends IdScriptableObject
    implements Function
{
    
    /** The Constant serialVersionUID. */
    static final long serialVersionUID = 1794167133757605367L;

    /** The Constant FTAG. */
    private static final Object FTAG = "Continuation";

    /** The implementation. */
    private Object implementation;

    /**
     * Inits the.
     *
     * @param cx the cx
     * @param scope the scope
     * @param sealed the sealed
     */
    public static void init(Context cx, Scriptable scope, boolean sealed)
    {
        NativeContinuation obj = new NativeContinuation();
        obj.exportAsJSClass(MAX_PROTOTYPE_ID, scope, sealed);
    }

    /**
     * Gets the implementation.
     *
     * @return the implementation
     */
    public Object getImplementation()
    {
        return implementation;
    }

    /**
     * Inits the implementation.
     *
     * @param implementation the implementation
     */
    public void initImplementation(Object implementation)
    {
        this.implementation = implementation;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#getClassName()
     */
    @Override
    public String getClassName()
    {
        return "Continuation";
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Function#construct(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, java.lang.Object[])
     */
    public Scriptable construct(Context cx, Scriptable scope, Object[] args)
    {
        throw Context.reportRuntimeError("Direct call is not supported");
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.Function#call(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[])
     */
    public Object call(Context cx, Scriptable scope, Scriptable thisObj,
                       Object[] args)
    {
        return Interpreter.restartContinuation(this, cx, scope, args);
    }

    /**
     * Checks if is continuation constructor.
     *
     * @param f the f
     * @return true, if is continuation constructor
     */
    public static boolean isContinuationConstructor(IdFunctionObject f)
    {
        if (f.hasTag(FTAG) && f.methodId() == Id_constructor) {
            return true;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.IdScriptableObject#initPrototypeId(int)
     */
    @Override
    protected void initPrototypeId(int id)
    {
        String s;
        int arity;
        switch (id) {
          case Id_constructor: arity=0; s="constructor"; break;
          default: throw new IllegalArgumentException(String.valueOf(id));
        }
        initPrototypeMethod(FTAG, id, s, arity);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.IdScriptableObject#execIdCall(org.mozilla.javascript.IdFunctionObject, org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[])
     */
    @Override
    public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope,
                             Scriptable thisObj, Object[] args)
    {
        if (!f.hasTag(FTAG)) {
            return super.execIdCall(f, cx, scope, thisObj, args);
        }
        int id = f.methodId();
        switch (id) {
          case Id_constructor:
            throw Context.reportRuntimeError("Direct call is not supported");
        }
        throw new IllegalArgumentException(String.valueOf(id));
    }

// #string_id_map#

    /* (non-Javadoc)
 * @see org.mozilla.javascript.IdScriptableObject#findPrototypeId(java.lang.String)
 */
@Override
    protected int findPrototypeId(String s)
    {
        int id;
// #generated# Last update: 2007-05-09 08:16:40 EDT
        L0: { id = 0; String X = null;
            if (s.length()==11) { X="constructor";id=Id_constructor; }
            if (X!=null && X!=s && !X.equals(s)) id = 0;
            break L0;
        }
// #/generated#
        return id;
    }

    /** The Constant MAX_PROTOTYPE_ID. */
    private static final int
        Id_constructor          = 1,
        MAX_PROTOTYPE_ID        = 1;

// #/string_id_map#
}
