/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;


/**
 * AST node representing a parse error or a warning.  Node type is
 * {@link Token#ERROR}.
 */
public class ErrorNode extends AstNode {

    /** The message. */
    private String message;

    {
        type = Token.ERROR;
    }

    /**
     * Instantiates a new error node.
     */
    public ErrorNode() {
    }

    /**
     * Instantiates a new error node.
     *
     * @param pos the pos
     */
    public ErrorNode(int pos) {
        super(pos);
    }

    /**
     * Instantiates a new error node.
     *
     * @param pos the pos
     * @param len the len
     */
    public ErrorNode(int pos, int len) {
        super(pos, len);
    }

    /**
     * Returns error message key.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets error message key.
     *
     * @param message the new message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.AstNode#toSource(int)
     */
    @Override
    public String toSource(int depth) {
        return "";
    }

    /**
     * Error nodes are not visited during normal visitor traversals,
     * but comply with the {@link AstNode#visit} interface.
     *
     * @param v the v
     */
    @Override
    public void visit(NodeVisitor v) {
        v.visit(this);
    }
}
