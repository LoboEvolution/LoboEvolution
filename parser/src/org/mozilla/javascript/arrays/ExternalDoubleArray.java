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
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -773914084068347275L;

    /** The array. */
    private final DoubleBuffer array;

    /**
     * Instantiates a new external double array.
     *
     * @param array the array
     */
    public ExternalDoubleArray(DoubleBuffer array) {
        this.array = array;
    }

    /**
     * Gets the array.
     *
     * @return the array
     */
    public DoubleBuffer getArray() {
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
        double val = ((Number)value).doubleValue();
        array.put(array.position() + index, val);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.arrays.ExternalArray#getLength()
     */
    public int getLength() {
        return array.remaining();
    }
}
