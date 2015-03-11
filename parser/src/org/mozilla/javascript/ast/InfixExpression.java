/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;


/**
 * AST node representing an infix (binary operator) expression.
 * The operator is the node's {@link Token} type.
 */
public class InfixExpression extends AstNode {

    /** The left. */
    protected AstNode left;
    
    /** The right. */
    protected AstNode right;
    
    /** The operator position. */
    protected int operatorPosition = -1;

    /**
     * Instantiates a new infix expression.
     */
    public InfixExpression() {
    }

    /**
     * Instantiates a new infix expression.
     *
     * @param pos the pos
     */
    public InfixExpression(int pos) {
        super(pos);
    }

    /**
     * Instantiates a new infix expression.
     *
     * @param pos the pos
     * @param len the len
     */
    public InfixExpression(int pos, int len) {
        super(pos, len);
    }

    /**
     * Instantiates a new infix expression.
     *
     * @param pos the pos
     * @param len the len
     * @param left the left
     * @param right the right
     */
    public InfixExpression(int pos, int len,
                           AstNode left,
                           AstNode right) {
        super(pos, len);
        setLeft(left);
        setRight(right);
    }

    /**
     * Constructs a new {@code InfixExpression}.  Updates bounds to include
     * left and right nodes.
     *
     * @param left the left
     * @param right the right
     */
    public InfixExpression(AstNode left, AstNode right) {
        setLeftAndRight(left, right);
    }

    /**
     * Constructs a new {@code InfixExpression}.
     *
     * @param operator the operator
     * @param left the left
     * @param right the right
     * @param operatorPos the <em>absolute</em> position of the operator
     */
    public InfixExpression(int operator, AstNode left,
                           AstNode right, int operatorPos) {
        setType(operator);
        setOperatorPosition(operatorPos - left.getPosition());
        setLeftAndRight(left, right);
    }

    /**
     * Sets the left and right.
     *
     * @param left the left
     * @param right the right
     */
    public void setLeftAndRight(AstNode left, AstNode right) {
        assertNotNull(left);
        assertNotNull(right);
        // compute our bounds while children have absolute positions
        int beg = left.getPosition();
        int end = right.getPosition() + right.getLength();
        setBounds(beg, end);

        // this updates their positions to be parent-relative
        setLeft(left);
        setRight(right);
    }

    /**
     * Returns operator token &ndash; alias for {@link #getType}.
     *
     * @return the operator
     */
    public int getOperator() {
        return getType();
    }

    /**
     * Sets operator token &ndash; like {@link #setType}, but throws
     * an exception if the operator is invalid.
     *
     * @param operator the new operator
     */
    public void setOperator(int operator) {
        if (!Token.isValidToken(operator))
            throw new IllegalArgumentException("Invalid token: " + operator);
        setType(operator);
    }

    /**
     * Returns the left-hand side of the expression.
     *
     * @return the left
     */
    public AstNode getLeft() {
        return left;
    }

    /**
     * Sets the left-hand side of the expression, and sets its
     * parent to this node.
     *
     * @param left the left-hand side of the expression
     */
    public void setLeft(AstNode left) {
        assertNotNull(left);
        this.left = left;
        // line number should agree with source position
        setLineno(left.getLineno());
        left.setParent(this);
    }

    /**
     * Returns the right-hand side of the expression.
     *
     * @return the right-hand side.  It's usually an
     * {@link AstNode} node, but can also be a {@link FunctionNode}
     * representing Function expressions.
     */
    public AstNode getRight() {
        return right;
    }

    /**
     * Sets the right-hand side of the expression, and sets its parent to this
     * node.
     *
     * @param right the new right
     */
    public void setRight(AstNode right) {
        assertNotNull(right);
        this.right = right;
        right.setParent(this);
    }

    /**
     * Returns relative offset of operator token.
     *
     * @return the operator position
     */
    public int getOperatorPosition() {
        return operatorPosition;
    }

    /**
     * Sets operator token's relative offset.
     *
     * @param operatorPosition offset in parent of operator token
     */
    public void setOperatorPosition(int operatorPosition) {
        this.operatorPosition = operatorPosition;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.AstNode#hasSideEffects()
     */
    @Override
    public boolean hasSideEffects() {
        // the null-checks are for malformed expressions in IDE-mode
        switch (getType()) {
          case Token.COMMA:
              return right != null && right.hasSideEffects();
          case Token.AND:
          case Token.OR:
              return left != null && left.hasSideEffects()
                      || (right != null && right.hasSideEffects());
          default:
              return super.hasSideEffects();
        }
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.AstNode#toSource(int)
     */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        sb.append(left.toSource());
        sb.append(" ");
        sb.append(operatorToString(getType()));
        sb.append(" ");
        sb.append(right.toSource());
        return sb.toString();
    }

    /**
     * Visits this node, the left operand, and the right operand.
     *
     * @param v the v
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            left.visit(v);
            right.visit(v);
        }
    }
}
