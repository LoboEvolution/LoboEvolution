/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

/**
 * AST node representing a parse error or a warning.  Node type is
 * {@link org.mozilla.javascript.Token#ERROR}.
 *
 *
 *
 */
public class ErrorNode extends AstNode {

    private String message;

    {
        type = Token.ERROR;
    }

    /**
     * <p>Constructor for ErrorNode.</p>
     */
    public ErrorNode() {
    }

    /**
     * <p>Constructor for ErrorNode.</p>
     *
     * @param pos a int.
     */
    public ErrorNode(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for ErrorNode.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public ErrorNode(int pos, int len) {
        super(pos, len);
    }

    /**
     * Returns error message key
     *
     * @return a {@link java.lang.String} object.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets error message key
     *
     * @param message a {@link java.lang.String} object.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        return "";
    }

    /**
     * {@inheritDoc}
     *
     * Error nodes are not visited during normal visitor traversals,
     * but comply with the {@link AstNode#visit} interface.
     */
    @Override
    public void visit(NodeVisitor v) {
        v.visit(this);
    }
}
