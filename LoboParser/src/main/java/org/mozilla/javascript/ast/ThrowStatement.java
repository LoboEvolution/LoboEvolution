/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

/**
 * Throw statement.  Node type is {@link org.mozilla.javascript.Token#THROW}.
 *
 * <pre><i>ThrowStatement</i> :
 *      <b>throw</b> [<i>no LineTerminator here</i>] Expression ;</pre>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ThrowStatement extends AstNode {

    private AstNode expression;

    {
        type = Token.THROW;
    }

    /**
     * <p>Constructor for ThrowStatement.</p>
     */
    public ThrowStatement() {
    }

    /**
     * <p>Constructor for ThrowStatement.</p>
     *
     * @param pos a int.
     */
    public ThrowStatement(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for ThrowStatement.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public ThrowStatement(int pos, int len) {
        super(pos, len);
    }

    /**
     * <p>Constructor for ThrowStatement.</p>
     *
     * @param expr a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public ThrowStatement(AstNode expr) {
        setExpression(expr);
    }

    /**
     * <p>Constructor for ThrowStatement.</p>
     *
     * @param pos a int.
     * @param expr a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public ThrowStatement(int pos, AstNode expr) {
        super(pos, expr.getLength());
        setExpression(expr);
    }

    /**
     * <p>Constructor for ThrowStatement.</p>
     *
     * @param pos a int.
     * @param len a int.
     * @param expr a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public ThrowStatement(int pos, int len, AstNode expr) {
        super(pos, len);
        setExpression(expr);
    }

    /**
     * Returns the expression being thrown
     *
     * @return a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public AstNode getExpression() {
        return expression;
    }

    /**
     * Sets the expression being thrown, and sets its parent
     * to this node.
     *
     * @throws java.lang.IllegalArgumentException} if expression is {@code null}
     * @param expression a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public void setExpression(AstNode expression) {
        assertNotNull(expression);
        this.expression = expression;
        expression.setParent(this);
    }

    /** {@inheritDoc} */
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
     * {@inheritDoc}
     *
     * Visits this node, then the thrown expression.
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            expression.visit(v);
        }
    }
}
