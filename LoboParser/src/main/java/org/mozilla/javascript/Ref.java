/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.io.Serializable;

/**
 * Generic notion of reference object that know how to query/modify the
 * target objects based on some property/index.
 *
 * @author utente
 * @version $Id: $Id
 */
public abstract class Ref implements Serializable
{

    private static final long serialVersionUID = 4044540354730911424L;

    /**
     * <p>has.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @return a boolean.
     */
    public boolean has(Context cx)
    {
        return true;
    }

    /**
     * <p>get.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @return a {@link java.lang.Object} object.
     */
    public abstract Object get(Context cx);

    /**
     * <p>set.</p>
     *
     * @deprecated Use {@link #set(Context, Scriptable, Object)} instead
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param value a {@link java.lang.Object} object.
     * @return a {@link java.lang.Object} object.
     */
    @Deprecated
    public abstract Object set(Context cx, Object value);

    /**
     * <p>set.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @param value a {@link java.lang.Object} object.
     * @return a {@link java.lang.Object} object.
     */
    public Object set(Context cx, Scriptable scope, Object value)
    {
        return set(cx, value);
    }

    /**
     * <p>delete.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @return a boolean.
     */
    public boolean delete(Context cx)
    {
        return false;
    }

}

