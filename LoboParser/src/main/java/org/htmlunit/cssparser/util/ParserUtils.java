/*
 * Copyright (c) 2019-2023 Ronald Brill.
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
package org.htmlunit.cssparser.util;

/**
 * Util methods.
 *
 * @author Ronald Brill
 */
public final class ParserUtils {

    /** HASH_SEED = 17. */
    public static final int HASH_SEED = 17;

    /** HASH_OFFSET = 37. */
    public static final int HASH_OFFSET = 37;

    private ParserUtils() {
    }

    /**
     * @param seed the seed to be used
     * @param hashcode the hashcode to be used as input
     * @return a hash code calculated based on a given one.
     */
    public static int hashCode(final int seed, final int hashcode) {
        return seed * HASH_OFFSET + hashcode;
    }

    /**
     * @param seed the seed to be used
     * @param b the boolean to be used as input
     * @return a hash code calculated based on a given boolean.
     */
    public static int hashCode(final int seed, final boolean b) {
        return hashCode(seed, b ? 1 : 0);
    }

    /**
     * @param seed the seed to be used
     * @param obj the object to be used as input
     * @return a hash code calculated based on a given object.
     */
    public static int hashCode(final int seed, final Object obj) {
        return hashCode(seed, obj != null ? obj.hashCode() : 0);
    }

    /**
     * @param obj1 the first object
     * @param obj2 the second object
     * @return true if the both objects are equals
     */
    public static boolean equals(final Object obj1, final Object obj2) {
        return obj1 == null ? obj2 == null : obj1.equals(obj2);
    }

    /**
     * Remove the given number of chars from start and end.
     * There is no parameter checking, the caller has to take care of this.
     *
     * @param s the StringBuilder
     * @param left no of chars to be removed from start
     * @param right no of chars to be removed from end
     * @return the trimmed string
     */
    public static String trimBy(final StringBuilder s, final int left, final int right) {
        return s.substring(left, s.length() - right);
    }

    /**
     * Helper that removes the leading "url(", the trailing ")"
     * and surrounding quotes from the given string builder.
     * @param s the StringBuilder
     * @return the trimmed string
     */
    public static String trimUrl(final StringBuilder s) {
        final String s1 = trimBy(s, 4, 1).trim();
        if (s1.length() == 0) {
            return s1;
        }

        final int end = s1.length() - 1;
        final char c0 = s1.charAt(0);
        if ((c0 == '"' && s1.charAt(end) == '"')
            || (c0 == '\'' && s1.charAt(end) == '\'')) {
            return s1.substring(1, end);
        }

        return s1;
    }

}
