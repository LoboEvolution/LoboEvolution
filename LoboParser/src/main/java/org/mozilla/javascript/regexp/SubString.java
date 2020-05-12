/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.regexp;

/**
 * A utility class for lazily instantiated substrings.
 *
 * @author utente
 * @version $Id: $Id
 */
public class SubString {

    /**
     * <p>Constructor for SubString.</p>
     */
    public SubString()
    {
    }

    /**
     * <p>Constructor for SubString.</p>
     *
     * @param str a {@link java.lang.String} object.
     */
    public SubString(String str)
    {
        this.str = str;
        index = 0;
        length = str.length();
    }

    /**
     * <p>Constructor for SubString.</p>
     *
     * @param source a {@link java.lang.String} object.
     * @param start a int.
     * @param len a int.
     */
    public SubString(String source, int start, int len)
    {
        str = source;
        index = start;
        length = len;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return str == null
               ? ""
               : str.substring(index, index + length);
    }

    String str;
    int    index;
    int    length;
}

