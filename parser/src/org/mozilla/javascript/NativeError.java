/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */


package org.mozilla.javascript;

/**
 *
 * The class of error objects
 *
 *  ECMA 15.11
 */
final class NativeError extends IdScriptableObject
{
    static final long serialVersionUID = -5338413581437645187L;

    private static final Object ERROR_TAG = "Error";

    private RhinoException stackProvider;

    static void init(Scriptable scope, boolean sealed)
    {
        NativeError obj = new NativeError();
        ScriptableObject.putProperty(obj, "name", "Error");
        ScriptableObject.putProperty(obj, "message", "");
        ScriptableObject.putProperty(obj, "fileName", "");
        ScriptableObject.putProperty(obj, "lineNumber", Integer.valueOf(0));
        obj.setAttributes("name", ScriptableObject.DONTENUM);
        obj.setAttributes("message", ScriptableObject.DONTENUM);
        obj.exportAsJSClass(MAX_PROTOTYPE_ID, scope, sealed);
    }

    static NativeError make(Context cx, Scriptable scope,
                            IdFunctionObject ctorObj, Object[] args)
    {
        Scriptable proto = (Scriptable)(ctorObj.get("prototype", ctorObj));

        NativeError obj = new NativeError();
        obj.setPrototype(proto);
        obj.setParentScope(scope);

        int arglen = args.length;
        if (arglen >= 1) {
            if (args[0] != Undefined.instance) {
                ScriptableObject.putProperty(obj, "message",
                        ScriptRuntime.toString(args[0]));
            }
            if (arglen >= 2) {
                ScriptableObject.putProperty(obj, "fileName", args[1]);
                if (arglen >= 3) {
                    int line = ScriptRuntime.toInt32(args[2]);
                    ScriptableObject.putProperty(obj, "lineNumber",
                            Integer.valueOf(line));
                }
            }
        }
        return obj;
    }

    @Override
    public String getClassName()
    {
        return "Error";
    }

    @Override
    public String toString()
    {
        // According to spec, Error.prototype.toString() may return undefined.
        Object toString = js_toString(this);
        return toString instanceof String ? (String) toString : super.toString();
    }

    @Override
    protected void initPrototypeId(int id)
    {
        String s;
        int arity;
        switch (id) {
          case Id_constructor: arity=1; s="constructor"; break;
          case Id_toString:    arity=0; s="toString";    break;
          case Id_toSource:    arity=0; s="toSource";    break;
          default: throw new IllegalArgumentException(String.valueOf(id));
        }
        initPrototypeMethod(ERROR_TAG, id, s, arity);
    }

    @Override
    protected void fillConstructorProperties(IdFunctionObject ctor)
    {
        addIdFunctionProperty(ctor, ERROR_TAG,
                              ConstructorId_captureStackTrace, "captureStackTrace", 2);
        super.fillConstructorProperties(ctor);
    }

    @Override
    public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope,
                             Scriptable thisObj, Object[] args)
    {
        if (!f.hasTag(ERROR_TAG)) {
            return super.execIdCall(f, cx, scope, thisObj, args);
        }
        int id = f.methodId();
        switch (id) {
          case ConstructorId_captureStackTrace:
            return js_captureStackTrace(cx, scope, args);

          case Id_constructor:
            return make(cx, scope, f, args);

          case Id_toString:
            return js_toString(thisObj);

          case Id_toSource:
            return js_toSource(cx, scope, thisObj);
        }
        throw new IllegalArgumentException(String.valueOf(id));
    }

    public void setStackProvider(RhinoException re) {
        // We go some extra miles to make sure the stack property is only
        // generated on demand, is cached after the first access, and is
        // overwritable like an ordinary property. Hence this setup with
        // the getter and setter below.
        if (stackProvider == null) {
            stackProvider = re;
            try {
                defineProperty("stack", this,
                        NativeError.class.getMethod("getStack", Scriptable.class),
                        NativeError.class.getMethod("setStack", Scriptable.class, Object.class),
                        DONTENUM);
            } catch (NoSuchMethodException nsm) {
                // should not happen
                throw new RuntimeException(nsm);
            }
        }
    }

    public Object getStack() {
        return getStack(this);
    }

    public Object getStack(Scriptable obj) {
        while(obj != null && !(obj instanceof NativeError)) {
            obj = obj.getPrototype();
        }
        if (obj == null) {
            return NOT_FOUND;
        }

        NativeError er = (NativeError) obj;
        if (er.stackProvider == null) {
            return NOT_FOUND;
        }

        Object value = er.stackProvider.getPreparedScriptStackTrace(Context.getCurrentContext(), this, this);
        // We store the stack as local property both to cache it
        // and to make the property writable
        setStack(obj, value);
        return value;
    }

    public void setStack(Object value) {
        setStack(this, value);
    }

    public void setStack(Scriptable obj, Object value) {
        while ((obj != null) && !(obj instanceof NativeError)) {
            obj = obj.getPrototype();
        }
        if (obj != null) {
            NativeError er = (NativeError) obj;
            if (er.stackProvider != null) {
                er.stackProvider = null;
            }
            er.delete("stack");
            er.put("stack", er, value);
        }
    }

    private static Object js_toString(Scriptable thisObj) {
        Object name = ScriptableObject.getProperty(thisObj, "name");
        if (name == NOT_FOUND || name == Undefined.instance) {
            name = "Error";
        } else {
            name = ScriptRuntime.toString(name);
        }
        Object msg = ScriptableObject.getProperty(thisObj, "message");
        if (msg == NOT_FOUND || msg == Undefined.instance) {
            msg = "";
        } else {
            msg = ScriptRuntime.toString(msg);
        }
        if (name.toString().length() == 0) {
            return msg;
        } else if (msg.toString().length() == 0) {
            return name;
        } else {
            return ((String) name) + ": " + ((String) msg);
        }
    }

    private static String js_toSource(Context cx, Scriptable scope,
                                      Scriptable thisObj)
    {
        // Emulation of SpiderMonkey behavior
        Object name = ScriptableObject.getProperty(thisObj, "name");
        Object message = ScriptableObject.getProperty(thisObj, "message");
        Object fileName = ScriptableObject.getProperty(thisObj, "fileName");
        Object lineNumber = ScriptableObject.getProperty(thisObj, "lineNumber");

        StringBuilder sb = new StringBuilder();
        sb.append("(new ");
        if (name == NOT_FOUND) {
            name = Undefined.instance;
        }
        sb.append(ScriptRuntime.toString(name));
        sb.append("(");
        if (message != NOT_FOUND
            || fileName != NOT_FOUND
            || lineNumber != NOT_FOUND)
        {
            if (message == NOT_FOUND) {
                message = "";
            }
            sb.append(ScriptRuntime.uneval(cx, scope, message));
            if (fileName != NOT_FOUND || lineNumber != NOT_FOUND) {
                sb.append(", ");
                if (fileName == NOT_FOUND) {
                    fileName = "";
                }
                sb.append(ScriptRuntime.uneval(cx, scope, fileName));
                if (lineNumber != NOT_FOUND) {
                    int line = ScriptRuntime.toInt32(lineNumber);
                    if (line != 0) {
                        sb.append(", ");
                        sb.append(ScriptRuntime.toString(line));
                    }
                }
            }
        }
        sb.append("))");
        return sb.toString();
    }

    /**
     * This is a V8 extension that causes a native Error object to have a stack trace inserted at the point
     * at which it's called. It takes two arguments -- the first is an Error object and the second
     * is an optional function name for trimming the stack trace. We will only implement the first.
     */
    private static Object js_captureStackTrace(Context cx, Scriptable scope, Object[] args)
    {
        if (args.length < 1) {
            throw ScriptRuntime.typeError("first argument must be an Object");
        }
        Scriptable obj;
        try {
            obj = (Scriptable)args[0];
        } catch (ClassCastException cce) {
            throw ScriptRuntime.typeError("err must be an Object");
        }

        EvaluatorException exc = new EvaluatorException(null);
        exc.fillInStackTrace();

        Object stack = exc.getPreparedScriptStackTrace(cx, scope, obj);
        obj.delete("stack");
        obj.put("stack", obj, stack);

        return Undefined.instance;
    }

    private static String getString(Scriptable obj, String id)
    {
        Object value = ScriptableObject.getProperty(obj, id);
        if (value == NOT_FOUND) return "";
        return ScriptRuntime.toString(value);
    }

    @Override
    protected int findPrototypeId(String s)
    {
        int id;
// #string_id_map#
// #generated# Last update: 2007-05-09 08:15:45 EDT
        L0: { id = 0; String X = null; int c;
            int s_length = s.length();
            if (s_length==8) {
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

    private static final int
        ConstructorId_captureStackTrace = -1,
        Id_constructor    = 1,
        Id_toString       = 2,
        Id_toSource       = 3,

        MAX_PROTOTYPE_ID  = 3;

// #/string_id_map#
}
