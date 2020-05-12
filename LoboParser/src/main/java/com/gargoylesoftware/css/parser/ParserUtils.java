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
package com.gargoylesoftware.css.parser;

/**
 * Util methods used by the parser and lexer.
 *
 * @author Ronald Brill
 * @version $Id: $Id
 */
public final class ParserUtils {

    private ParserUtils() {
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
     *
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
