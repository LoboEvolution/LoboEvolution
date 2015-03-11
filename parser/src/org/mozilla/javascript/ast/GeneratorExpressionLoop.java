/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;


/**
 * The Class GeneratorExpressionLoop.
 */
public class GeneratorExpressionLoop extends ForInLoop {
    
    /**
     * Instantiates a new generator expression loop.
     */
    public GeneratorExpressionLoop() {
    }

    /**
     * Instantiates a new generator expression loop.
     *
     * @param pos the pos
     */
    public GeneratorExpressionLoop(int pos) {
        super(pos);
    }

    /**
     * Instantiates a new generator expression loop.
     *
     * @param pos the pos
     * @param len the len
     */
    public GeneratorExpressionLoop(int pos, int len) {
        super(pos, len);
    }
    
    /**
     * Returns whether the loop is a for-each loop.
     *
     * @return true, if is for each
     */
    @Override
    public boolean isForEach() {
        return false;
    }

    /**
     * Sets whether the loop is a for-each loop.
     *
     * @param isForEach the new checks if is for each
     */
    @Override
    public void setIsForEach(boolean isForEach) {
        throw new UnsupportedOperationException("this node type does not support for each");
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.ForInLoop#toSource(int)
     */
    @Override
    public String toSource(int depth) {
        return makeIndent(depth)
                + " for "
                + (isForEach()?"each ":"")
                + "("
                + iterator.toSource(0)
                + " in "
                + iteratedObject.toSource(0)
                + ")";
    }

    /**
     * Visits the iterator expression and the iterated object expression.
     * There is no body-expression for this loop type.
     *
     * @param v the v
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            iterator.visit(v);
            iteratedObject.visit(v);
        }
    }
}
