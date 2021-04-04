/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

// API class

package org.mozilla.javascript;

/**
 * <p>ContextListener interface.</p>
 *
 * @deprecated Embeddings that wish to customize newly created
 * {@link org.mozilla.javascript.Context} instances should implement
 * {@link org.mozilla.javascript.ContextFactory.Listener}.
 *
 *
 */
@Deprecated
public interface ContextListener extends ContextFactory.Listener
{

    /**
     * <p>contextEntered.</p>
     *
     * @deprecated Rhino runtime never calls the method.
     * @param cx a {@link org.mozilla.javascript.Context} object.
     */
    @Deprecated
    public void contextEntered(Context cx);

    /**
     * <p>contextExited.</p>
     *
     * @deprecated Rhino runtime never calls the method.
     * @param cx a {@link org.mozilla.javascript.Context} object.
     */
    @Deprecated
    public void contextExited(Context cx);
}
