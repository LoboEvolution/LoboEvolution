/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

/**
 * AST node for an empty statement.  Node type is {@link org.mozilla.javascript.Token#EMPTY}.
 *
 *
 *
 */
public class EmptyStatement extends AstNode {

    {
        type = Token.EMPTY;
    }

    /**
     * <p>Constructor for EmptyStatement.</p>
     */
    public EmptyStatement() {
    }

    /**
     * <p>Constructor for EmptyStatement.</p>
     *
     * @param pos a int.
     */
    public EmptyStatement(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for EmptyStatement.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public EmptyStatement(int pos, int len) {
        super(pos, len);
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth)).append(";\n");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     *
     * Visits this node.  There are no children.
     */
    @Override
    public void visit(NodeVisitor v) {
        v.visit(this);
    }
}
