/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.arrays;

import java.nio.IntBuffer;


/**
 * An implementation of the external array using an array of "int"s. From a JavaScript perspective,
 * only "number" types may be set in the array. Valid values are between 0 and (2^32)-1, inclusive. Negative values
 * will be converted to unsigned "number" objects when accessed from JavaScript, whereas in Java, values
 * must be treated as "int" instances.
 * position() of the specified array will be used for the first object in the array, and "remaining()" will
 * be used to determine the length.
 */

public final class ExternalUnsignedIntArray
    extends ExternalArray
{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 20197124152155786L;

    /** The array. */
    private final IntBuffer array;

    /**
     * Instantiates a new external unsigned int array.
     *
     * @param array the array
     */
    public ExternalUnsignedIntArray(IntBuffer array) {
        this.array = array;
    }

    /**
     * Gets the array.
     *
     * @return the array
     */
    public IntBuffer getArray() {
        return array;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.arrays.ExternalArray#getElement(int)
     */
    protected Object getElement(int index) {
        int val = array.get(array.position() + index);
        return val & 0xffffffffL;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.arrays.ExternalArray#putElement(int, java.lang.Object)
     */
    protected void putElement(int index, Object value) {
        long val = ((Number)value).longValue();
        array.put(array.position() + index, (int)(val & 0xffffffffL));
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.arrays.ExternalArray#getLength()
     */
    public int getLength() {
        return array.remaining();
    }
}
