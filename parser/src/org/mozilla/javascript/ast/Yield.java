/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;


/**
 * AST node for JavaScript 1.7 {@code yield} expression or statement.
 * Node type is {@link Token#YIELD}.
 *
 * <pre><i>Yield</i> :
 *   <b>yield</b> [<i>no LineTerminator here</i>] [non-paren Expression] ;</pre>
 */
public class Yield extends AstNode {

    /** The value. */
    private AstNode value;

    {
        type = Token.YIELD;
    }

    /**
     * Instantiates a new yield.
     */
    public Yield() {
    }

    /**
     * Instantiates a new yield.
     *
     * @param pos the pos
     */
    public Yield(int pos) {
        super(pos);
    }

    /**
     * Instantiates a new yield.
     *
     * @param pos the pos
     * @param len the len
     */
    public Yield(int pos, int len) {
        super(pos, len);
    }

    /**
     * Instantiates a new yield.
     *
     * @param pos the pos
     * @param len the len
     * @param value the value
     */
    public Yield(int pos, int len, AstNode value) {
        super(pos, len);
        setValue(value);
    }

    /**
     * Returns yielded expression, {@code null} if none.
     *
     * @return the value
     */
    public AstNode getValue() {
        return value;
    }

    /**
     * Sets yielded expression, and sets its parent to this node.
     * @param expr the value to yield. Can be {@code null}.
     */
    public void setValue(AstNode expr) {
        this.value = expr;
        if (expr != null)
            expr.setParent(this);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.AstNode#toSource(int)
     */
    @Override
    public String toSource(int depth) {
        return value == null
                ? "yield"
                : "yield " + value.toSource(0);
    }

    /**
     * Visits this node, and if present, the yielded value.
     *
     * @param v the v
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this) && value != null) {
            value.visit(v);
        }
    }
}
