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
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5377484970217959212L;

    /** The array. */
    private final ByteBuffer array;

    /**
     * Instantiates a new external byte array.
     *
     * @param array the array
     */
    public ExternalByteArray(ByteBuffer array) {
        this.array = array;
    }

    /**
     * Gets the array.
     *
     * @return the array
     */
    public ByteBuffer getArray() {
        return array;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.arrays.ExternalArray#getElement(int)
     */
    protected Object getElement(int index) {
        return array.get(array.position() + index);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.arrays.ExternalArray#putElement(int, java.lang.Object)
     */
    protected void putElement(int index, Object value) {
        Number num = (Number)value;
        array.put(array.position() + index, num.byteValue());
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.arrays.ExternalArray#getLength()
     */
    public int getLength() {
        return array.remaining();
    }
}
