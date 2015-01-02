/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.arrays;

import java.nio.DoubleBuffer;

/**
 * An implementation of the external array using an array of "double"s. Only "number" types may be set in
 * the array.
 * position() of the specified array will be used for the first object in the array, and "remaining()" will
 * be used to determine the length.
 */

public final class ExternalDoubleArray
    extends ExternalArray
{
    private static final long serialVersionUID = -773914084068347275L;

    private final DoubleBuffer array;

    public ExternalDoubleArray(DoubleBuffer array) {
        this.array = array;
    }

    public DoubleBuffer getArray() {
        return array;
    }

    protected Object getElement(int index) {
        return array.get(array.position() + index);
    }

    protected void putElement(int index, Object value) {
        double val = ((Number)value).doubleValue();
        array.put(array.position() + index, val);
    }

    public int getLength() {
        return array.remaining();
    }
}
