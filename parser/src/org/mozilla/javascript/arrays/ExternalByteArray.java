/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.arrays;

import java.nio.ByteBuffer;

/**
 * An implementation of the external array using an array of bytes. From a JavaScript perspective,
 * only "number" types may be set in the array. Valid values are between -128 and 127, inclusive.
 * position() of the specified array will be used for the first object in the array, and "remaining()" will
 * be used to determine the length.
 */

public final class ExternalByteArray
    extends ExternalArray
{
    private static final long serialVersionUID = 5377484970217959212L;

    private final ByteBuffer array;

    public ExternalByteArray(ByteBuffer array) {
        this.array = array;
    }

    public ByteBuffer getArray() {
        return array;
    }

    protected Object getElement(int index) {
        return array.get(array.position() + index);
    }

    protected void putElement(int index, Object value) {
        Number num = (Number)value;
        array.put(array.position() + index, num.byteValue());
    }

    public int getLength() {
        return array.remaining();
    }
}
