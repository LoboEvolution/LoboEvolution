/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

/**
 * AST node representing unary operators such as {@code typeof} and {@code delete}.
 * The type field is set to the appropriate Token type for the operator.
 * The node length spans from the operator to the end of the operand.<p>
 *
 * The {@code default xml namespace = &lt;expr&gt;} statement in E4X
 * (JavaScript 1.6) is represented as a {@code UnaryExpression} of node
 * type {@link org.mozilla.javascript.Token#DEFAULTNAMESPACE}, wrapped with an
 * {@link org.mozilla.javascript.ast.ExpressionStatement}.
 *
 *
 *
 */
public class UnaryExpression extends AstNode {

    private AstNode operand;

    /**
     * <p>Constructor for UnaryExpression.</p>
     */
    public UnaryExpression() {
    }

    /**
     * <p>Constructor for UnaryExpression.</p>
     *
     * @param pos a int.
     */
    public UnaryExpression(int pos) {
        super(pos);
    }

    /**
     * Constructs a new UnaryExpression
     *
     * @param pos a int.
     * @param len a int.
     */
    public UnaryExpression(int pos, int len) {
        super(pos, len);
    }

    /**
     * Constructs a new UnaryExpression with the specified operator
     * and operand.  It sets the parent of the operand, and sets its own bounds
     * to encompass the operator and operand.
     *
     * @param operator the node type
     * @param operatorPosition the absolute position of the operator.
     * @param operand the operand expression
     * @throws java.lang.IllegalArgumentException} if {@code operand} is {@code null}
     */
    public UnaryExpression(int operator, int operatorPosition, AstNode operand) {
        assertNotNull(operand);
        int beg = operand.getPosition();
        // JavaScript only has ++ and -- postfix operators, so length is 2
        int end = operand.getPosition() + operand.getLength();
        setBounds(beg, end);
        setOperator(operator);
        setOperand(operand);
    }

    /**
     * Returns operator token &ndash; alias for {@link #getType}
     *
     * @return a int.
     */
    public int getOperator() {
        return type;
    }

    /**
     * Sets operator &ndash; same as {@link #setType}, but throws an
     * exception if the operator is invalid
     *
     * @throws java.lang.IllegalArgumentException if operator is not a valid
     * Token code
     * @param operator a int.
     */
    public void setOperator(int operator) {
        if (!Token.isValidToken(operator))
            throw new IllegalArgumentException("Invalid token: " + operator);
        setType(operator);
    }

    /**
     * <p>Getter for the field <code>operand</code>.</p>
     *
     * @return a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public AstNode getOperand() {
        return operand;
    }

    /**
     * Sets the operand, and sets its parent to be this node.
     *
     * @throws java.lang.IllegalArgumentException} if {@code operand} is {@code null}
     * @param operand a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public void setOperand(AstNode operand) {
        assertNotNull(operand);
        this.operand = operand;
        operand.setParent(this);
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        int type = getType();
        sb.append(operatorToString(type));
        if (type == Token.TYPEOF || type == Token.DELPROP || type == Token.VOID) {
            sb.append(" ");
        }
        sb.append(operand.toSource());

        return sb.toString();
    }

    /**
     * {@inheritDoc}
     *
     * Visits this node, then the operand.
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            operand.visit(v);
        }
    }
}
