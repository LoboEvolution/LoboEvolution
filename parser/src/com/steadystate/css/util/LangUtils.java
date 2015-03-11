/*
 * CSS Parser Project
 *
 * Copyright (C) 1999-2015 David Schweinsberg.  All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * To contact the authors of the library:
 *
 * http://cssparser.sourceforge.net/
 * mailto:davidsch@users.sourceforge.net
 *
 */

package com.steadystate.css.util;


/**
 * The Class LangUtils.
 */
public final class LangUtils {
    
    /** The Constant HASH_SEED. */
    public static final int HASH_SEED = 17;
    
    /** The Constant HASH_OFFSET. */
    public static final int HASH_OFFSET = 37;

    /**
     * Instantiates a new lang utils.
     */
    private LangUtils() {
    }

    /**
     * Hash code.
     *
     * @param seed the seed
     * @param hashcode the hashcode
     * @return the int
     */
    public static int hashCode(final int seed, final int hashcode) {
        return seed * HASH_OFFSET + hashcode;
    }

    /**
     * Hash code.
     *
     * @param seed the seed
     * @param b the b
     * @return the int
     */
    public static int hashCode(final int seed, final boolean b) {
        return hashCode(seed, b ? 1 : 0);
    }

    /**
     * Hash code.
     *
     * @param seed the seed
     * @param obj the obj
     * @return the int
     */
    public static int hashCode(final int seed, final Object obj) {
        return hashCode(seed, obj != null ? obj.hashCode() : 0);
    }

    /**
     * Equals.
     *
     * @param obj1 the obj1
     * @param obj2 the obj2
     * @return true, if successful
     */
    public static boolean equals(final Object obj1, final Object obj2) {
        return obj1 == null ? obj2 == null : obj1.equals(obj2);
    }
}
