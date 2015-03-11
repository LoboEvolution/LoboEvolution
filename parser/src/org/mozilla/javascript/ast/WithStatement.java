/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;


/**
 * With statement.  Node type is {@link Token#WITH}.
 *
 * <pre><i>WithStatement</i> :
 *      <b>with</b> ( Expression ) Statement ;</pre>
 */
public class WithStatement extends AstNode {

    /** The expression. */
    private AstNode expression;
    
    /** The statement. */
    private AstNode statement;
    
    /** The lp. */
    private int lp = -1;
    
    /** The rp. */
    private int rp = -1;

    {
        type = Token.WITH;
    }

    /**
     * Instantiates a new with statement.
     */
    public WithStatement() {
    }

    /**
     * Instantiates a new with statement.
     *
     * @param pos the pos
     */
    public WithStatement(int pos) {
        super(pos);
    }

    /**
     * Instantiates a new with statement.
     *
     * @param pos the pos
     * @param len the len
     */
    public WithStatement(int pos, int len) {
        super(pos, len);
    }

    /**
     * Returns object expression.
     *
     * @return the expression
     */
    public AstNode getExpression() {
        return expression;
    }

    /**
     * Sets object expression (and its parent link).
     *
     * @param expression the new expression
     */
    public void setExpression(AstNode expression) {
        assertNotNull(expression);
        this.expression = expression;
        expression.setParent(this);
    }

    /**
     * Returns the statement or block.
     *
     * @return the statement
     */
    public AstNode getStatement() {
        return statement;
    }

    /**
     * Sets the statement (and sets its parent link).
     *
     * @param statement the new statement
     */
    public void setStatement(AstNode statement) {
        assertNotNull(statement);
        this.statement = statement;
        statement.setParent(this);
    }

    /**
     * Returns left paren offset.
     *
     * @return the lp
     */
    public int getLp() {
      return lp;
    }

    /**
     * Sets left paren offset.
     *
     * @param lp the new lp
     */
    public void setLp(int lp) {
      this.lp = lp;
    }

    /**
     * Returns right paren offset.
     *
     * @return the rp
     */
    public int getRp() {
      return rp;
    }

    /**
     * Sets right paren offset.
     *
     * @param rp the new rp
     */
    public void setRp(int rp) {
      this.rp = rp;
    }

    /**
     * Sets both paren positions.
     *
     * @param lp the lp
     * @param rp the rp
     */
    public void setParens(int lp, int rp) {
        this.lp = lp;
        this.rp = rp;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.AstNode#toSource(int)
     */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        sb.append("with (");
        sb.append(expression.toSource(0));
        sb.append(") ");
        if (statement.getType() == Token.BLOCK) {
            sb.append(statement.toSource(depth).trim());
            sb.append("\n");
        } else {
            sb.append("\n").append(statement.toSource(depth + 1));
        }
        return sb.toString();
    }

    /**
     * Visits this node, then the with-object, then the body statement.
     *
     * @param v the v
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            expression.visit(v);
            statement.visit(v);
        }
    }
}
