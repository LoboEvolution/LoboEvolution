/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.node;

/**
 * A set of space-separated tokens. Such a set is returned by Element.classList
 */
public interface DOMTokenList {

    /**
     * Returns the number of tokens.
     * @return a int.
     */
    int getLength();

    /**
     * Returns the associated set as string.
     * Can be set, to change the associated attribute.
     * @return a {@link java.lang.String} object.
     */
    String getValue();

    /**
     * <p>setValue.</p>
     * @param value a {@link java.lang.String} object.
     */
    void setValue(String value);

    /**
     * Adds all arguments passed, except those already present.
     * Throws a "SyntaxError" DOMException if one of the arguments is the empty string.
     * Throws an "InvalidCharacterError" DOMException if one of the arguments contains any ASCII whitespace.
     * @param tokens a {@link java.lang.String} object.
     */
    void add(String tokens);

    /**
     * Returns true if token is present, and false otherwise.
     * @param token a {@link java.lang.String} object.
     * @return a boolean.
     */
    boolean contains(String token);

    /**
     * Returns the token with index index.
     * @param index a int.
     * @return a {@link java.lang.String} object.
     */
    String item(int index);

    /**
     * Removes arguments passed, if they are present.
     * Throws a "SyntaxError" DOMException if one of the arguments is the empty string.
     * Throws an "InvalidCharacterError" DOMException if one of the arguments contains any ASCII whitespace.
     * @param tokens a {@link java.lang.String} object.
     */
    void remove(String tokens);

    /**
     * Replaces token with newToken.
     * Returns true if token was replaced with newToken, and false otherwise.
     * Throws a "SyntaxError" DOMException if one of the arguments is the empty string.
     * Throws an "InvalidCharacterError" DOMException if one of the arguments contains any ASCII whitespace.
     *
     * @param oldToken a {@link java.lang.String} object.
     * @param newToken a {@link java.lang.String} object.
     * @return a boolean.
     */
    boolean replace(String oldToken, String newToken);

    /**
     * Returns true if token is in the associated attribute's supported tokens. Returns false otherwise.
     * <p>
     * Throws a TypeError if the associated attribute has no supported tokens defined.
     *
     * @param token a {@link java.lang.String} object.
     * @return a boolean.
     */
    boolean supports(String token);

    /**
     * If force is not given, "toggles" token, removing it if it's present and adding it if it's not present. If force is true, adds token (same as add()). If force is false, removes token (same as remove()).
     * Returns true if token is now present, and false otherwise.
     * Throws a "SyntaxError" DOMException if token is empty.
     * Throws an "InvalidCharacterError" DOMException if token contains any spaces.
     * @param token a {@link java.lang.String} object.
     * @param force a boolean.
     * @return a boolean.
     */
    boolean toggle(String token, boolean force);

    /**
     * <p>toggle.</p>
     * @param token a {@link java.lang.String} object.
     * @return a boolean.
     */
    boolean toggle(String token);

}

