/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.node;

/**
 * A set of space-separated tokens. Such a set is returned by Element.classList, HTMLLinkElement.relList, HTMLAnchorElement.relList, HTMLAreaElement.relList, HTMLIframeElement.sandbox, or HTMLOutputElement.htmlFor. It is indexed beginning with 0 as with JavaScript Array objects. DOMTokenList is always case-sensitive.
 *
 *
 *
 */
public interface DOMTokenList {
    
    /**
     * Returns the number of tokens.
     *
     * @return a int.
     */
    int getLength();

    /**
     * Returns the associated set as string.
     * <p>
     * Can be set, to change the associated attribute.
     *
     * @return a {@link java.lang.String} object.
     */
    String getValue();

    
    /**
     * <p>setValue.</p>
     *
     * @param value a {@link java.lang.String} object.
     */
    void setValue(String value);

    /**
     * Adds all arguments passed, except those already present.
     * <p>
     * Throws a "SyntaxError" DOMException if one of the arguments is the empty string.
     * <p>
     * Throws an "InvalidCharacterError" DOMException if one of the arguments contains any ASCII whitespace.
     *
     * @param tokens a {@link java.lang.String} object.
     */
    void add(String tokens);

    /**
     * Returns true if token is present, and false otherwise.
     *
     * @param token a {@link java.lang.String} object.
     * @return a boolean.
     */
    boolean contains(String token);

    /**
     * Returns the token with index index.
     *
     * @param index a int.
     * @return a {@link java.lang.String} object.
     */
    String item(int index);

    /**
     * Removes arguments passed, if they are present.
     * <p>
     * Throws a "SyntaxError" DOMException if one of the arguments is the empty string.
     * <p>
     * Throws an "InvalidCharacterError" DOMException if one of the arguments contains any ASCII whitespace.
     *
     * @param tokens a {@link java.lang.String} object.
     */
    void remove(String tokens);

    /**
     * Replaces token with newToken.
     * <p>
     * Returns true if token was replaced with newToken, and false otherwise.
     * <p>
     * Throws a "SyntaxError" DOMException if one of the arguments is the empty string.
     * <p>
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
     * <p>
     * Returns true if token is now present, and false otherwise.
     * <p>
     * Throws a "SyntaxError" DOMException if token is empty.
     * <p>
     * Throws an "InvalidCharacterError" DOMException if token contains any spaces.
     *
     * @param token a {@link java.lang.String} object.
     * @param force a boolean.
     * @return a boolean.
     */
    boolean toggle(String token, boolean force);

    /**
     * <p>toggle.</p>
     *
     * @param token a {@link java.lang.String} object.
     * @return a boolean.
     */
    boolean toggle(String token);

    /**
     * <p>get.</p>
     *
     * @param index a int.
     * @return a {@link java.lang.String} object.
     */
    String get(int index);

}

