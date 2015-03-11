/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.typedarrays;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;


/**
 * The Class NativeArrayBuffer.
 */
public class NativeArrayBuffer
    extends IdScriptableObject
{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3110411773054879549L;

    /** The Constant CLASS_NAME. */
    public static final String CLASS_NAME = "ArrayBuffer";

    /** The Constant EMPTY_BUF. */
    private static final byte[] EMPTY_BUF = new byte[0];

    /** The Constant EMPTY_BUFFER. */
    public static final NativeArrayBuffer EMPTY_BUFFER = new NativeArrayBuffer();

    /** The buffer. */
    final byte[] buffer;

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#getClassName()
     */
    @Override
    public String getClassName()
    {
        return CLASS_NAME;
    }

    /**
     * Inits the.
     *
     * @param scope the scope
     * @param sealed the sealed
     */
    public static void init(Scriptable scope, boolean sealed)
    {
        NativeArrayBuffer na = new NativeArrayBuffer();
        na.exportAsJSClass(MAX_PROTOTYPE_ID, scope, sealed);
    }

    /**
     * Instantiates a new native array buffer.
     */
    public NativeArrayBuffer()
    {
        buffer = EMPTY_BUF;
    }

    /**
     * Instantiates a new native array buffer.
     *
     * @param len the len
     */
    public NativeArrayBuffer(int len)
    {
        if (len < 0) {
            throw ScriptRuntime.constructError("RangeError", "Negative array length " + len);
        }
        if (len == 0) {
            buffer = EMPTY_BUF;
        } else {
            buffer = new byte[len];
        }
    }

    /**
     * Gets the length.
     *
     * @return the length
     */
    int getLength() {
        return buffer.length;
    }

    // Actual implementations of actual code

    /**
     * Slice.
     *
     * @param s the s
     * @param e the e
     * @return the native array buffer
     */
    NativeArrayBuffer slice(int s, int e)
    {
        // Handle negative start and and as relative to start
        // Clamp as per the spec to between 0 and length
        int end = Math.max(0, Math.min(buffer.length, (e < 0 ? buffer.length + e : e)));
        int start = Math.min(end, Math.max(0, (s < 0 ? buffer.length + s : s)));
        int len = end - start;

        NativeArrayBuffer newBuf = new NativeArrayBuffer(len);
        System.arraycopy(buffer, start, newBuf.buffer, 0, len);
        return newBuf;
    }

    // Function-calling dispatcher

    /* (non-Javadoc)
     * @see org.mozilla.javascript.IdScriptableObject#execIdCall(org.mozilla.javascript.IdFunctionObject, org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[])
     */
    @Override
    public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope,
                             Scriptable thisObj, Object[] args)
    {
        if (!f.hasTag(CLASS_NAME)) {
            return super.execIdCall(f, cx, scope, thisObj, args);
        }
        int id = f.methodId();
        switch (id) {
        case ConstructorId_isView:
            return (isArg(args, 0) && (args[0] instanceof NativeArrayBufferView));

        case Id_constructor:
            int length = isArg(args, 0) ? ScriptRuntime.toInt32(args[0]) : 0;
            return new NativeArrayBuffer(length);

        case Id_slice:
            NativeArrayBuffer self = realThis(thisObj, f);
            int start = isArg(args, 0) ? ScriptRuntime.toInt32(args[0]) : 0;
            int end = isArg(args, 1) ? ScriptRuntime.toInt32(args[1]) : self.buffer.length;
            return self.slice(start, end);
        }
        throw new IllegalArgumentException(String.valueOf(id));
    }

    /**
     * Real this.
     *
     * @param thisObj the this obj
     * @param f the f
     * @return the native array buffer
     */
    private static NativeArrayBuffer realThis(Scriptable thisObj, IdFunctionObject f)
    {
        if (!(thisObj instanceof NativeArrayBuffer))
            throw incompatibleCallError(f);
        return (NativeArrayBuffer)thisObj;
    }

    /**
     * Checks if is arg.
     *
     * @param args the args
     * @param i the i
     * @return true, if is arg
     */
    private static boolean isArg(Object[] args, int i)
    {
        return ((args.length > i) && !Undefined.instance.equals(args[i]));
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
        case Id_constructor:            arity = 1; s = "constructor"; break;
        case Id_slice:                  arity = 1; s = "slice"; break;
        default: throw new IllegalArgumentException(String.valueOf(id));
        }
        initPrototypeMethod(CLASS_NAME, id, s, arity);
    }

// #string_id_map#

    /* (non-Javadoc)
 * @see org.mozilla.javascript.IdScriptableObject#findPrototypeId(java.lang.String)
 */
@Override
    protected int findPrototypeId(String s)
    {
        int id;
// #generated#
        L0: { id = 0; String X = null;
            int s_length = s.length();
            if (s_length==5) { X="slice";id=Id_slice; }
            else if (s_length==6) { X="isView";id=Id_isView; }
            else if (s_length==11) { X="constructor";id=Id_constructor; }
            if (X!=null && X!=s && !X.equals(s)) id = 0;
            break L0;
        }
// #/generated#
        return id;
    }

    // Table of all functions
    /** The Constant MAX_PROTOTYPE_ID. */
    private static final int
        Id_constructor          = 1,
        Id_slice                = 2,
        Id_isView               = 3,
        MAX_PROTOTYPE_ID        = Id_isView;

// #/string_id_map#

    // Constructor (aka static) functions here

    /** The Constant ConstructorId_isView. */
private static final int ConstructorId_isView = -Id_isView;

    /* (non-Javadoc)
     * @see org.mozilla.javascript.IdScriptableObject#fillConstructorProperties(org.mozilla.javascript.IdFunctionObject)
     */
    @Override
    protected void fillConstructorProperties(IdFunctionObject ctor)
    {
        addIdFunctionProperty(ctor, CLASS_NAME, ConstructorId_isView, "isView", 1);
    }

    // Properties here

    /* (non-Javadoc)
     * @see org.mozilla.javascript.IdScriptableObject#getMaxInstanceId()
     */
    @Override
    protected int getMaxInstanceId()
    {
        return MAX_INSTANCE_ID;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.IdScriptableObject#getInstanceIdName(int)
     */
    @Override
    protected String getInstanceIdName(int id)
    {
        if (id == Id_byteLength) { return "byteLength"; }
        return super.getInstanceIdName(id);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.IdScriptableObject#getInstanceIdValue(int)
     */
    @Override
    protected Object getInstanceIdValue(int id)
    {
        if (id == Id_byteLength) {
            return ScriptRuntime.wrapInt(buffer.length);
        }
        return super.getInstanceIdValue(id);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.IdScriptableObject#findInstanceIdInfo(java.lang.String)
     */
    @Override
    protected int findInstanceIdInfo(String s)
    {
        if ("byteLength".equals(s)) {
            return instanceIdInfo(READONLY | PERMANENT, Id_byteLength);
        }
        return super.findInstanceIdInfo(s);
    }

    // Table of all properties
    /** The Constant MAX_INSTANCE_ID. */
    private static final int
        Id_byteLength           = 1,
        MAX_INSTANCE_ID         = Id_byteLength;
}
