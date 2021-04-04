/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

/**
 * Abstract base type for components that comprise an {@link org.mozilla.javascript.ast.XmlLiteral}
 * object. Node type is {@link org.mozilla.javascript.Token#XML}.
 *
 *
 *
 */
public abstract class XmlFragment extends AstNode {

    {
        type = Token.XML;
    }

    /**
     * <p>Constructor for XmlFragment.</p>
     */
    public XmlFragment() {
    }

    /**
     * <p>Constructor for XmlFragment.</p>
     *
     * @param pos a int.
     */
    public XmlFragment(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for XmlFragment.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public XmlFragment(int pos, int len) {
        super(pos, len);
    }
}
