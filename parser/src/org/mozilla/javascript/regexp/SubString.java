/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.regexp;


/**
 * A utility class for lazily instantiated substrings.
 */
public class SubString {

    /**
     * Instantiates a new sub string.
     */
    public SubString()
    {
    }

    /**
     * Instantiates a new sub string.
     *
     * @param str the str
     */
    public SubString(String str)
    {
        this.str = str;
        index = 0;
        length = str.length();
    }

    /**
     * Instantiates a new sub string.
     *
     * @param source the source
     * @param start the start
     * @param len the len
     */
    public SubString(String source, int start, int len)
    {
        str = source;
        index = start;
        length = len;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return str == null
               ? ""
               : str.substring(index, index + length);
    }

    /** The Constant emptySubString. */
    public static final SubString emptySubString = new SubString();

    /** The str. */
    String str;
    
    /** The index. */
    int    index;
    
    /** The length. */
    int    length;
}

