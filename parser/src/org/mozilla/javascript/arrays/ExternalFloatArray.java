/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.arrays;

import java.nio.FloatBuffer;

/**
 * An implementation of the external array using an array of "float"s. Only "number" types may be set in
 * the array.
 * position() of the specified array will be used for the first object in the array, and "remaining()" will
 * be used to determine the length.
 */

public final class ExternalFloatArray
    extends ExternalArray
{
    private static final long serialVersionUID = 3786769656861013570L;

    private final FloatBuffer array;

    public ExternalFloatArray(FloatBuffer array) {
        this.array = array;
    }

    public FloatBuffer getArray() {
        return array;
    }

    protected Object getElement(int index) {
        return array.get(array.position() + index);
    }

    protected void putElement(int index, Object value) {
        float val = ((Number)value).floatValue();
        array.put(array.position() + index, val);
    }

    public int getLength() {
        return array.remaining();
    }
}
