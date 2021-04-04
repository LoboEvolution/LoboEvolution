/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.typedarrays;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

/**
 * An array view that stores 8-bit quantities and implements the JavaScript "Int8Array" interface.
 * It also implements List&lt;Byte&gt; for direct manipulation in Java.
 *
 *
 *
 */
public class NativeInt8Array
    extends NativeTypedArrayView<Byte>
{
    private static final long serialVersionUID = -3349419704390398895L;

    private static final String CLASS_NAME = "Int8Array";

    /**
     * <p>Constructor for NativeInt8Array.</p>
     */
    public NativeInt8Array()
    {
    }

    /**
     * <p>Constructor for NativeInt8Array.</p>
     *
     * @param ab a {@link org.mozilla.javascript.typedarrays.NativeArrayBuffer} object.
     * @param off a int.
     * @param len a int.
     */
    public NativeInt8Array(NativeArrayBuffer ab, int off, int len)
    {
        super(ab, off, len, len);
    }

    /**
     * <p>Constructor for NativeInt8Array.</p>
     *
     * @param len a int.
     */
    public NativeInt8Array(int len)
    {
        this(new NativeArrayBuffer(len), 0, len);
    }

    /** {@inheritDoc} */
    @Override
    public String getClassName()
    {
        return CLASS_NAME;
    }

    /**
     * <p>init.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @param sealed a boolean.
     */
    public static void init(Context cx, Scriptable scope, boolean sealed)
    {
        NativeInt8Array a = new NativeInt8Array();
        a.exportAsJSClass(MAX_PROTOTYPE_ID, scope, sealed);
    }

    /** {@inheritDoc} */
    @Override
    protected NativeInt8Array construct(NativeArrayBuffer ab, int off, int len)
    {
        return new NativeInt8Array(ab, off, len);
    }

    /** {@inheritDoc} */
    @Override
    public int getBytesPerElement()
    {
        return 1;
    }

    /** {@inheritDoc} */
    @Override
    protected NativeInt8Array realThis(Scriptable thisObj, IdFunctionObject f)
    {
        return ensureType(thisObj, NativeInt8Array.class, f);
    }

    /** {@inheritDoc} */
    @Override
    protected Object js_get(int index)
    {
        if (checkIndex(index)) {
            return Undefined.instance;
        }
        return ByteIo.readInt8(arrayBuffer.buffer, index + offset);
    }

    /** {@inheritDoc} */
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

    // List implementation (much of it handled by the superclass)

    /** {@inheritDoc} */
    @Override
    public Byte get(int i)
    {
        if (checkIndex(i)) {
            throw new IndexOutOfBoundsException();
        }
        return (Byte)js_get(i);
    }

    /** {@inheritDoc} */
    @Override
    public Byte set(int i, Byte aByte)
    {
        if (checkIndex(i)) {
            throw new IndexOutOfBoundsException();
        }
        return (Byte)js_set(i, aByte);
    }
}
