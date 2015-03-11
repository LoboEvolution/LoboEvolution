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
 */
public abstract class Ref implements Serializable
{
    
    /** The Constant serialVersionUID. */
    static final long serialVersionUID = 4044540354730911424L;
    
    /**
     * Checks for.
     *
     * @param cx the cx
     * @return true, if successful
     */
    public boolean has(Context cx)
    {
        return true;
    }

    /**
     * Gets the.
     *
     * @param cx the cx
     * @return the object
     */
    public abstract Object get(Context cx);

    /**
     * Sets the.
     *
     * @param cx the cx
     * @param value the value
     * @return the object
     */
    public abstract Object set(Context cx, Object value);

    /**
     * Delete.
     *
     * @param cx the cx
     * @return true, if successful
     */
    public boolean delete(Context cx)
    {
        return false;
    }

}

