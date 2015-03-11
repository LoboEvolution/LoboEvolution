/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;


/**
 * Throw statement.  Node type is {@link Token#THROW}.
 *
 * <pre><i>ThrowStatement</i> :
 *      <b>throw</b> [<i>no LineTerminator here</i>] Expression ;</pre>
 */
public class ThrowStatement extends AstNode {

    /** The expression. */
    private AstNode expression;

    {
        type = Token.THROW;
    }

    /**
     * Instantiates a new throw statement.
     */
    public ThrowStatement() {
    }

    /**
     * Instantiates a new throw statement.
     *
     * @param pos the pos
     */
    public ThrowStatement(int pos) {
        super(pos);
    }

    /**
     * Instantiates a new throw statement.
     *
     * @param pos the pos
     * @param len the len
     */
    public ThrowStatement(int pos, int len) {
        super(pos, len);
    }

    /**
     * Instantiates a new throw statement.
     *
     * @param expr the expr
     */
    public ThrowStatement(AstNode expr) {
        setExpression(expr);
    }

    /**
     * Instantiates a new throw statement.
     *
     * @param pos the pos
     * @param expr the expr
     */
    public ThrowStatement(int pos, AstNode expr) {
        super(pos, expr.getLength());
        setExpression(expr);
    }

    /**
     * Instantiates a new throw statement.
     *
     * @param pos the pos
     * @param len the len
     * @param expr the expr
     */
    public ThrowStatement(int pos, int len, AstNode expr) {
        super(pos, len);
        setExpression(expr);
    }

    /**
     * Returns the expression being thrown.
     *
     * @return the expression
     */
    public AstNode getExpression() {
        return expression;
    }

    /**
     * Sets the expression being thrown, and sets its parent
     * to this node.
     *
     * @param expression the new expression
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
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        sb.append("throw");
        sb.append(" ");
        sb.append(expression.toSource(0));
        sb.append(";\n");
        return sb.toString();
    }

    /**
     * Visits this node, then the thrown expression.
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
