/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

/**
 * AST node representing update operators such as {@code ++}. The type field
 * is set to the appropriate Token type for the operator.  The node length spans
 * from the operator to the end of the operand (for prefix operators) or from
 * the start of the operand to the operator (for postfix).
 */
public class UpdateExpression extends AstNode {

    private AstNode operand;
    private boolean isPostfix;

    /**
     * <p>Constructor for UpdateExpression.</p>
     */
    public UpdateExpression() {
    }

    /**
     * <p>Constructor for UpdateExpression.</p>
     *
     * @param pos a int.
     */
    public UpdateExpression(int pos) {
        super(pos);
    }

    /**
     * Constructs a new postfix UpdateExpression
     *
     * @param pos a int.
     * @param len a int.
     */
    public UpdateExpression(int pos, int len) {
        super(pos, len);
    }

    /**
     * Constructs a new prefix UpdateExpression.
     *
     * @param operator a int.
     * @param operatorPosition a int.
     * @param operand a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public UpdateExpression(int operator, int operatorPosition,
                           AstNode operand) {
        this(operator, operatorPosition, operand, false);
    }

    /**
     * Constructs a new UpdateExpression with the specified operator
     * and operand.  It sets the parent of the operand, and sets its own bounds
     * to encompass the operator and operand.
     *
     * @param operator the node type
     * @param operatorPosition the absolute position of the operator.
     * @param operand the operand expression
     * @param postFix true if the operator follows the operand.  Int
     * @throws java.lang.IllegalArgumentException} if {@code operand} is {@code null}
     */
    public UpdateExpression(int operator, int operatorPosition,
                           AstNode operand, boolean postFix) {
        assertNotNull(operand);
        int beg = postFix ? operand.getPosition() : operatorPosition;
        // JavaScript only has ++ and -- postfix operators, so length is 2
        int end = postFix
                  ? operatorPosition + 2
                  : operand.getPosition() + operand.getLength();
        setBounds(beg, end);
        setOperator(operator);
        setOperand(operand);
        isPostfix = postFix;
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

    /**
     * Returns whether the operator is postfix
     *
     * @return a boolean.
     */
    public boolean isPostfix() {
        return isPostfix;
    }

    /**
     * Returns whether the operator is prefix
     *
     * @return a boolean.
     */
    public boolean isPrefix() {
        return !isPostfix;
    }

    /**
     * Sets whether the operator is postfix
     *
     * @param isPostfix a boolean.
     */
    public void setIsPostfix(boolean isPostfix) {
        this.isPostfix = isPostfix;
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        int type = getType();
        if (!isPostfix) {
            sb.append(operatorToString(type));
        }
        sb.append(operand.toSource());
        if (isPostfix) {
            sb.append(operatorToString(type));
        }
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
