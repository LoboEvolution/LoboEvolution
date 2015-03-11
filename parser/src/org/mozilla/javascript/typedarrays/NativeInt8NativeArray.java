/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.typedarrays;

import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;


/**
 * The Class NativeInt8NativeArray.
 */
public class NativeInt8NativeArray
    extends NativeTypedArrayView
{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3349419704390398895L;

    /** The Constant CLASS_NAME. */
    private static final String CLASS_NAME = "Int8Array";

    /**
     * Instantiates a new native int8 native array.
     */
    public NativeInt8NativeArray()
    {
    }

    /**
     * Instantiates a new native int8 native array.
     *
     * @param ab the ab
     * @param off the off
     * @param len the len
     */
    public NativeInt8NativeArray(NativeArrayBuffer ab, int off, int len)
    {
        super(ab, off, len, len);
    }

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
        NativeInt8NativeArray a = new NativeInt8NativeArray();
        a.exportAsJSClass(MAX_PROTOTYPE_ID, scope, sealed);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.typedarrays.NativeTypedArrayView#construct(org.mozilla.javascript.typedarrays.NativeArrayBuffer, int, int)
     */
    @Override
    protected NativeTypedArrayView construct(NativeArrayBuffer ab, int off, int len)
    {
        return new NativeInt8NativeArray(ab, off, len);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.typedarrays.NativeTypedArrayView#getBytesPerElement()
     */
    @Override
    protected int getBytesPerElement()
    {
        return 1;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.typedarrays.NativeTypedArrayView#realThis(org.mozilla.javascript.Scriptable, org.mozilla.javascript.IdFunctionObject)
     */
    @Override
    protected NativeTypedArrayView realThis(Scriptable thisObj, IdFunctionObject f)
    {
        if (!(thisObj instanceof NativeInt8NativeArray)) {
            throw incompatibleCallError(f);
        }
        return (NativeInt8NativeArray)thisObj;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.typedarrays.NativeTypedArrayView#js_get(int)
     */
    @Override
    protected Object js_get(int index)
    {
        if (checkIndex(index)) {
            return Undefined.instance;
        }
        return ByteIo.readInt8(arrayBuffer.buffer, index + offset);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.typedarrays.NativeTypedArrayView#js_set(int, java.lang.Object)
     */
    @Override
    protected Object js_set(int index, Object c)
    {
        if (checkIndex(index)) {
            return Undefined.instance;
        }
        int val = Conversions.toInt8(c);
        ByteIo.writeInt8(arrayBuffer.buffer, index + offset, val);
        return null;
    }
}
