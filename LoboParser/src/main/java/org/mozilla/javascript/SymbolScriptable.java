/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

/**
 * This interface may be combined with any object that implements Scriptable
 * to add support for properties keyed by Symbol objects (as opposed to
 * String and number objects as in previous versions of JavaScript.
 * It's separated into its own interface so that the addition of Symbol
 * support does not break compatibility for existing code.
 *
 * @since 1.7.8
 *
 *
 */
public interface SymbolScriptable
{
    /**
     * Return the value of the property with the specified key, or
     * NOT_FOUND.
     *
     * @param key a {@link org.mozilla.javascript.Symbol} object.
     * @param start a {@link org.mozilla.javascript.Scriptable} object.
     * @return a {@link java.lang.Object} object.
     */
    Object get(Symbol key, Scriptable start);

    /**
     * Return true if the specified property exists.
     *
     * @param key a {@link org.mozilla.javascript.Symbol} object.
     * @param start a {@link org.mozilla.javascript.Scriptable} object.
     * @return a boolean.
     */
    boolean has(Symbol key, Scriptable start);

    /**
     * Add a new property to to the object.
     *
     * @param key a {@link org.mozilla.javascript.Symbol} object.
     * @param start a {@link org.mozilla.javascript.Scriptable} object.
     * @param value a {@link java.lang.Object} object.
     */
    void put(Symbol key, Scriptable start, Object value);

    /**
     * Delete a property with the specified key.
     *
     * @param key a {@link org.mozilla.javascript.Symbol} object.
     */
    void delete(Symbol key);
}
