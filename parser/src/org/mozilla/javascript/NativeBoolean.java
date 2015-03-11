/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;


/**
 * This class implements the Boolean native object.
 * See ECMA 15.6.
 * @author Norris Boyd
 */
final class NativeBoolean extends IdScriptableObject
{
    
    /** The Constant serialVersionUID. */
    static final long serialVersionUID = -3716996899943880933L;

    /** The Constant BOOLEAN_TAG. */
    private static final Object BOOLEAN_TAG = "Boolean";

    /**
     * Inits the.
     *
     * @param scope the scope
     * @param sealed the sealed
     */
    static void init(Scriptable scope, boolean sealed)
    {
        NativeBoolean obj = new NativeBoolean(false);
        obj.exportAsJSClass(MAX_PROTOTYPE_ID, scope, sealed);
    }

    /**
     * Instantiates a new native boolean.
     *
     * @param b the b
     */
    NativeBoolean(boolean b)
    {
        booleanValue = b;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#getClassName()
     */
    @Override
    public String getClassName()
    {
        return "Boolean";
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#getDefaultValue(java.lang.Class)
     */
    @Override
    public Object getDefaultValue(Class<?> typeHint) {
        // This is actually non-ECMA, but will be proposed
        // as a change in round 2.
        if (typeHint == ScriptRuntime.BooleanClass)
            return ScriptRuntime.wrapBoolean(booleanValue);
        return super.getDefaultValue(typeHint);
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
          case Id_constructor: arity=1; s="constructor"; break;
          case Id_toString:    arity=0; s="toString";    break;
          case Id_toSource:    arity=0; s="toSource";    break;
          case Id_valueOf:     arity=0; s="valueOf";     break;
          default: throw new IllegalArgumentException(String.valueOf(id));
        }
        initPrototypeMethod(BOOLEAN_TAG, id, s, arity);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.IdScriptableObject#execIdCall(org.mozilla.javascript.IdFunctionObject, org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[])
     */
    @Override
    public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope,
                             Scriptable thisObj, Object[] args)
    {
        if (!f.hasTag(BOOLEAN_TAG)) {
            return super.execIdCall(f, cx, scope, thisObj, args);
        }
        int id = f.methodId();

        if (id == Id_constructor) {
            boolean b;
            if (args.length == 0) {
                b = false;
            } else {
                b = args[0] instanceof ScriptableObject &&
                        ((ScriptableObject) args[0]).avoidObjectDetection()
                    ? true
                    : ScriptRuntime.toBoolean(args[0]);
            }
            if (thisObj == null) {
                // new Boolean(val) creates a new boolean object.
                return new NativeBoolean(b);
            }
            // Boolean(val) converts val to a boolean.
            return ScriptRuntime.wrapBoolean(b);
        }

        // The rest of Boolean.prototype methods require thisObj to be Boolean

        if (!(thisObj instanceof NativeBoolean))
            throw incompatibleCallError(f);
        boolean value = ((NativeBoolean)thisObj).booleanValue;

        switch (id) {

          case Id_toString:
            return value ? "true" : "false";

          case Id_toSource:
            return value ? "(new Boolean(true))" : "(new Boolean(false))";

          case Id_valueOf:
            return ScriptRuntime.wrapBoolean(value);
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
// #generated# Last update: 2007-05-09 08:15:31 EDT
        L0: { id = 0; String X = null; int c;
            int s_length = s.length();
            if (s_length==7) { X="valueOf";id=Id_valueOf; }
            else if (s_length==8) {
                c=s.charAt(3);
                if (c=='o') { X="toSource";id=Id_toSource; }
                else if (c=='t') { X="toString";id=Id_toString; }
            }
            else if (s_length==11) { X="constructor";id=Id_constructor; }
            if (X!=null && X!=s && !X.equals(s)) id = 0;
            break L0;
        }
// #/generated#
        return id;
    }

    /** The Constant MAX_PROTOTYPE_ID. */
    private static final int
        Id_constructor          = 1,
        Id_toString             = 2,
        Id_toSource             = 3,
        Id_valueOf              = 4,
        MAX_PROTOTYPE_ID        = 4;

// #/string_id_map#

    /** The boolean value. */
private boolean booleanValue;
}
