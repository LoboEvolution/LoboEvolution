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
 */
public class ModuleScope extends TopLevel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The uri. */
    private final URI uri;
    
    /** The base. */
    private final URI base;

    /**
     * Instantiates a new module scope.
     *
     * @param prototype the prototype
     * @param uri the uri
     * @param base the base
     */
    public ModuleScope(Scriptable prototype, URI uri, URI base) {
        this.uri = uri;
        this.base = base;
        setPrototype(prototype);
        cacheBuiltins();
    }

    /**
     * Gets the uri.
     *
     * @return the uri
     */
    public URI getUri() {
        return uri;
    }

    /**
     * Gets the base.
     *
     * @return the base
     */
    public URI getBase() {
        return base;
    }
}
