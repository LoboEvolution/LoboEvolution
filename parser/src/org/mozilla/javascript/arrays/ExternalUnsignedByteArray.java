/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.arrays;

import java.nio.ByteBuffer;

/**
 * An implementation of the external array using an array of bytes. From a JavaScript perspective,
 * only "number" types may be set in the array. Valid values are between 0 and 255, inclusive. Negative values
 * will be converted to unsigned "number" objects when accessed from JavaScript, whereas in Java, values
 * must be treated as "byte" instances.
 * position() of the specified array will be used for the first object in the array, and "remaining()" will
 * be used to determine the length.
 */

public final class ExternalUnsignedByteArray
    extends ExternalArray
{
    private static final long serialVersionUID = -6094133552784945651L;

    private final ByteBuffer array;

    public ExternalUnsignedByteArray(ByteBuffer array) {
        this.array = array;
    }

    public ByteBuffer getArray() {
        return array;
    }

    protected Object getElement(int index) {
        return array.get(array.position() + index) & 0xff;
    }

    protected void putElement(int index, Object value) {
        int val = ((Number)value).intValue();
       array.put(array.position() + index, (byte)(val & 0xff));
    }

    public int getLength() {
        return array.remaining();
    }
}
