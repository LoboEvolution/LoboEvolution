/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.io.Serializable;


/**
 * <p>This class represents a string composed of two components, each of which
 * may be a <code>java.lang.String</code> or another ConsString.</p>
 *
 * <p>This string representation is optimized for concatenation using the "+"
 * operator. Instead of immediately copying both components to a new character
 * array, ConsString keeps references to the original components and only
 * converts them to a String if either toString() is called or a certain depth
 * level is reached.</p>
 *
 * <p>Note that instances of this class are only immutable if both parts are
 * immutable, i.e. either Strings or ConsStrings that are ultimately composed
 * of Strings.</p>
 *
 * <p>Both the name and the concept are borrowed from V8.</p>
 */
public class ConsString implements CharSequence, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8432806714471372570L;

    /** The s2. */
    private CharSequence s1, s2;
    
    /** The length. */
    private final int length;
    
    /** The depth. */
    private int depth;

    /**
     * Instantiates a new cons string.
     *
     * @param str1 the str1
     * @param str2 the str2
     */
    public ConsString(CharSequence str1, CharSequence str2) {
        s1 = str1;
        s2 = str2;
        length = str1.length() + str2.length();
        depth = 1;
        if (str1 instanceof ConsString) {
            depth += ((ConsString)str1).depth;
        }
        if (str2 instanceof ConsString) {
            depth += ((ConsString)str2).depth;
        }
        // Don't let it grow too deep, can cause stack overflows
        if (depth > 2000) {
            flatten();
        }
    }

    // Replace with string representation when serializing
    /**
     * Write replace.
     *
     * @return the object
     */
    private Object writeReplace() {
        return this.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return depth == 0 ? (String)s1 : flatten();
    }

    /**
     * Flatten.
     *
     * @return the string
     */
    private synchronized String flatten() {
        if (depth > 0) {
            StringBuilder b = new StringBuilder(length);
            appendTo(b);
            s1 = b.toString();
            s2 = "";
            depth = 0;
        }
        return (String)s1;
    }

    /**
     * Append to.
     *
     * @param b the b
     */
    private synchronized void appendTo(StringBuilder b) {
        appendFragment(s1, b);
        appendFragment(s2, b);
    }

    /**
     * Append fragment.
     *
     * @param s the s
     * @param b the b
     */
    private static void appendFragment(CharSequence s, StringBuilder b) {
        if (s instanceof ConsString) {
            ((ConsString)s).appendTo(b);
        } else {
            b.append(s);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.CharSequence#length()
     */
    public int length() {
        return length;
    }

    /* (non-Javadoc)
     * @see java.lang.CharSequence#charAt(int)
     */
    public char charAt(int index) {
        String str = depth == 0 ? (String)s1 : flatten();
        return str.charAt(index);
    }

    /* (non-Javadoc)
     * @see java.lang.CharSequence#subSequence(int, int)
     */
    public CharSequence subSequence(int start, int end) {
        String str = depth == 0 ? (String)s1 : flatten();
        return str.substring(start, end);
    }

}
