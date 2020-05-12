/*
 * Copyright (c) 2019 Ronald Brill.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.css.util;

/**
 * Some utils.
 *
 * @author Ronald Brill
 * @version $Id: $Id
 */
public final class LangUtils {

    /** HASH_SEED = 17. */
    public static final int HASH_SEED = 17;

    /** HASH_OFFSET = 37. */
    public static final int HASH_OFFSET = 37;

    private LangUtils() {
    }

    /**
     * <p>hashCode.</p>
     *
     * @param seed the seed to be used
     * @param hashcode the hashcode to be used as input
     * @return a hash code calculated based on a given one.
     */
    public static int hashCode(final int seed, final int hashcode) {
        return seed * HASH_OFFSET + hashcode;
    }

    /**
     * <p>hashCode.</p>
     *
     * @param seed the seed to be used
     * @param b the boolean to be used as input
     * @return a hash code calculated based on a given boolean.
     */
    public static int hashCode(final int seed, final boolean b) {
        return hashCode(seed, b ? 1 : 0);
    }

    /**
     * <p>hashCode.</p>
     *
     * @param seed the seed to be used
     * @param obj the object to be used as input
     * @return a hash code calculated based on a given object.
     */
    public static int hashCode(final int seed, final Object obj) {
        return hashCode(seed, obj != null ? obj.hashCode() : 0);
    }

    /**
     * <p>equals.</p>
     *
     * @param obj1 the first object
     * @param obj2 the second object
     * @return true if the both objects are equals
     */
    public static boolean equals(final Object obj1, final Object obj2) {
        return obj1 == null ? obj2 == null : obj1.equals(obj2);
    }
}
