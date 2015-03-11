/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;


/**
 * AST node for a parenthesized expression.
 * Node type is {@link Token#LP}.
 */
public class ParenthesizedExpression extends AstNode {

    /** The expression. */
    private AstNode expression;

    {
        type = Token.LP;
    }

    /**
     * Instantiates a new parenthesized expression.
     */
    public ParenthesizedExpression() {
    }

    /**
     * Instantiates a new parenthesized expression.
     *
     * @param pos the pos
     */
    public ParenthesizedExpression(int pos) {
        super(pos);
    }

    /**
     * Instantiates a new parenthesized expression.
     *
     * @param pos the pos
     * @param len the len
     */
    public ParenthesizedExpression(int pos, int len) {
        super(pos, len);
    }

    /**
     * Instantiates a new parenthesized expression.
     *
     * @param expr the expr
     */
    public ParenthesizedExpression(AstNode expr) {
        this(expr != null ? expr.getPosition() : 0,
             expr != null ? expr.getLength() : 1,
             expr);
    }

    /**
     * Instantiates a new parenthesized expression.
     *
     * @param pos the pos
     * @param len the len
     * @param expr the expr
     */
    public ParenthesizedExpression(int pos, int len, AstNode expr) {
        super(pos, len);
        setExpression(expr);
    }

    /**
     * Returns the expression between the parens.
     *
     * @return the expression
     */
    public AstNode getExpression() {
        return expression;
    }

    /**
     * Sets the expression between the parens, and sets the parent
     * to this node.
     *
     * @param expression the expression between the parens
     */
    public void setExpression(AstNode expression) {
        assertNotNull(expression);
        this.expression = expression;
        expression.setParent(this);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.AstNode#toSource(int)
     */
    @Override
    public String toSource(int depth) {
        return makeIndent(depth) + "(" + expression.toSource(0) + ")";
    }

    /**
     * Visits this node, then the child expression.
     *
     * @param v the v
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            expression.visit(v);
        }
    }
}
