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
package com.steadystate.css.parser;

/**
 * Util methods used by the parser and lexer.
 *
 * @author RBRi
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
     * @param s the StringBuilder
     * @return
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
