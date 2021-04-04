/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

/**
 * AST node for a single 'for (foo in bar)' loop construct in a JavaScript 1.7
 * Array comprehension. This node type is almost equivalent to a
 * {@link org.mozilla.javascript.ast.ForInLoop}, except that it has no body statement.
 * Node type is {@link org.mozilla.javascript.Token#FOR}.
 *
 *
 *
 */
public class ArrayComprehensionLoop extends ForInLoop {

    /**
     * <p>Constructor for ArrayComprehensionLoop.</p>
     */
    public ArrayComprehensionLoop() {
    }

    /**
     * <p>Constructor for ArrayComprehensionLoop.</p>
     *
     * @param pos a int.
     */
    public ArrayComprehensionLoop(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for ArrayComprehensionLoop.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public ArrayComprehensionLoop(int pos, int len) {
        super(pos, len);
    }
    
    /**
     * {@inheritDoc}
     *
     * Returns {@code null} for loop body
     */
    @Override
    public AstNode getBody() {
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * Throws an exception on attempts to set the loop body.
     */
    @Override
    public void setBody(AstNode body) {
        throw new UnsupportedOperationException("this node type has no body");
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        return makeIndent(depth)
                + " for "
                + (isForEach()?"each ":"")
                + "("
                + iterator.toSource(0)
                + (isForOf()?" of ":" in ")
                + iteratedObject.toSource(0)
                + ")";
    }

    /**
     * {@inheritDoc}
     *
     * Visits the iterator expression and the iterated object expression.
     * There is no body-expression for this loop type.
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            iterator.visit(v);
            iteratedObject.visit(v);
        }
    }
}
