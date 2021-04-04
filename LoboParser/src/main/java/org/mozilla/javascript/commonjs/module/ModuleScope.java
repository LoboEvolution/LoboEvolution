/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.commonjs.module;

import java.net.URI;

import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.TopLevel;

/**
 * A top-level module scope. This class provides methods to retrieve the
 * module's source and base URIs in order to resolve relative module IDs
 * and check sandbox constraints.
 *
 *
 *
 */
public class ModuleScope extends TopLevel {
    private static final long serialVersionUID = 1L;
    private final URI uri;
    private final URI base;

    /**
     * <p>Constructor for ModuleScope.</p>
     *
     * @param prototype a {@link org.mozilla.javascript.Scriptable} object.
     * @param uri a {@link java.net.URI} object.
     * @param base a {@link java.net.URI} object.
     */
    public ModuleScope(Scriptable prototype, URI uri, URI base) {
        this.uri = uri;
        this.base = base;
        setPrototype(prototype);
        cacheBuiltins(prototype, false);
    }

    /**
     * <p>Getter for the field <code>uri</code>.</p>
     *
     * @return a {@link java.net.URI} object.
     */
    public URI getUri() {
        return uri;
    }

    /**
     * <p>Getter for the field <code>base</code>.</p>
     *
     * @return a {@link java.net.URI} object.
     */
    public URI getBase() {
        return base;
    }
}
