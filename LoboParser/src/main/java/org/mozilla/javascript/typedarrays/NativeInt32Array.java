/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.typedarrays;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

/**
 * An array view that stores 32-bit quantities and implements the JavaScript "Int32Array" interface.
 * It also implements List&lt;Integer&gt; for direct manipulation in Java.
 *
 * @author utente
 * @version $Id: $Id
 */
public class NativeInt32Array
    extends NativeTypedArrayView<Integer>
{
    private static final long serialVersionUID = -8963461831950499340L;

    private static final String CLASS_NAME = "Int32Array";
    private static final int BYTES_PER_ELEMENT = 4;

    /**
     * <p>Constructor for NativeInt32Array.</p>
     */
    public NativeInt32Array()
    {
    }

    /**
     * <p>Constructor for NativeInt32Array.</p>
     *
     * @param ab a {@link org.mozilla.javascript.typedarrays.NativeArrayBuffer} object.
     * @param off a int.
     * @param len a int.
     */
    public NativeInt32Array(NativeArrayBuffer ab, int off, int len)
    {
        super(ab, off, len, len * BYTES_PER_ELEMENT);
    }

    /**
     * <p>Constructor for NativeInt32Array.</p>
     *
     * @param len a int.
     */
    public NativeInt32Array(int len)
    {
        this(new NativeArrayBuffer(len * BYTES_PER_ELEMENT), 0, len);
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
        NativeInt32Array a = new NativeInt32Array();
        a.exportAsJSClass(MAX_PROTOTYPE_ID, scope, sealed);
    }

    /** {@inheritDoc} */
    @Override
    protected NativeInt32Array construct(NativeArrayBuffer ab, int off, int len)
    {
        return new NativeInt32Array(ab, off, len);
    }

    /** {@inheritDoc} */
    @Override
    public int getBytesPerElement()
    {
        return BYTES_PER_ELEMENT;
    }

    /** {@inheritDoc} */
    @Override
    protected NativeInt32Array realThis(Scriptable thisObj, IdFunctionObject f)
    {
        if (!(thisObj instanceof NativeInt32Array)) {
            throw incompatibleCallError(f);
        }
        return (NativeInt32Array)thisObj;
    }

    /** {@inheritDoc} */
    @Override
    protected Object js_get(int index)
    {
        if (checkIndex(index)) {
            return Undefined.instance;
        }
        return ByteIo.readInt32(arrayBuffer.buffer, (index * BYTES_PER_ELEMENT) + offset, useLittleEndian());
    }

    /** {@inheritDoc} */
    @Override
    protected Object js_set(int index, Object c)
    {
        if (checkIndex(index)) {
            return Undefined.instance;
        }
        int val = ScriptRuntime.toInt32(c);
        ByteIo.writeInt32(arrayBuffer.buffer, (index * BYTES_PER_ELEMENT) + offset, val, useLittleEndian());
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public Integer get(int i)
    {
        if (checkIndex(i)) {
            throw new IndexOutOfBoundsException();
        }
        return (Integer)js_get(i);
    }

    /** {@inheritDoc} */
    @Override
    public Integer set(int i, Integer aByte)
    {
        if (checkIndex(i)) {
            throw new IndexOutOfBoundsException();
        }
        return (Integer)js_set(i, aByte);
    }
}
