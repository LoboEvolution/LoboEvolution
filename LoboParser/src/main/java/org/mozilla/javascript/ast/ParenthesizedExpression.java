/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

/**
 * AST node for a parenthesized expression.
 * Node type is {@link org.mozilla.javascript.Token#LP}.
 *
 *
 *
 */
public class ParenthesizedExpression extends AstNode {

    private AstNode expression;

    {
        type = Token.LP;
    }

    /**
     * <p>Constructor for ParenthesizedExpression.</p>
     */
    public ParenthesizedExpression() {
    }

    /**
     * <p>Constructor for ParenthesizedExpression.</p>
     *
     * @param pos a int.
     */
    public ParenthesizedExpression(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for ParenthesizedExpression.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public ParenthesizedExpression(int pos, int len) {
        super(pos, len);
    }

    /**
     * <p>Constructor for ParenthesizedExpression.</p>
     *
     * @param expr a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public ParenthesizedExpression(AstNode expr) {
        this(expr != null ? expr.getPosition() : 0,
             expr != null ? expr.getLength() : 1,
             expr);
    }

    /**
     * <p>Constructor for ParenthesizedExpression.</p>
     *
     * @param pos a int.
     * @param len a int.
     * @param expr a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public ParenthesizedExpression(int pos, int len, AstNode expr) {
        super(pos, len);
        setExpression(expr);
    }

    /**
     * Returns the expression between the parens
     *
     * @return a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public AstNode getExpression() {
        return expression;
    }

    /**
     * Sets the expression between the parens, and sets the parent
     * to this node.
     *
     * @param expression the expression between the parens
     * @throws java.lang.IllegalArgumentException} if expression is {@code null}
     */
    public void setExpression(AstNode expression) {
        assertNotNull(expression);
        this.expression = expression;
        expression.setParent(this);
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        return makeIndent(depth) + "(" + expression.toSource(0) + ")";
    }

    /**
     * {@inheritDoc}
     *
     * Visits this node, then the child expression.
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            expression.visit(v);
        }
    }
}
