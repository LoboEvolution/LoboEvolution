/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

/**
 * With statement.  Node type is {@link org.mozilla.javascript.Token#WITH}.
 *
 * <pre><i>WithStatement</i> :
 *      <b>with</b> ( Expression ) Statement ;</pre>
 *
 * @author utente
 * @version $Id: $Id
 */
public class WithStatement extends AstNode {

    private AstNode expression;
    private AstNode statement;
    private int lp = -1;
    private int rp = -1;

    {
        type = Token.WITH;
    }

    /**
     * <p>Constructor for WithStatement.</p>
     */
    public WithStatement() {
    }

    /**
     * <p>Constructor for WithStatement.</p>
     *
     * @param pos a int.
     */
    public WithStatement(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for WithStatement.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public WithStatement(int pos, int len) {
        super(pos, len);
    }

    /**
     * Returns object expression
     *
     * @return a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public AstNode getExpression() {
        return expression;
    }

    /**
     * Sets object expression (and its parent link)
     *
     * @throws java.lang.IllegalArgumentException} if expression is {@code null}
     * @param expression a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public void setExpression(AstNode expression) {
        assertNotNull(expression);
        this.expression = expression;
        expression.setParent(this);
    }

    /**
     * Returns the statement or block
     *
     * @return a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public AstNode getStatement() {
        return statement;
    }

    /**
     * Sets the statement (and sets its parent link)
     *
     * @throws java.lang.IllegalArgumentException} if statement is {@code null}
     * @param statement a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public void setStatement(AstNode statement) {
        assertNotNull(statement);
        this.statement = statement;
        statement.setParent(this);
    }

    /**
     * Returns left paren offset
     *
     * @return a int.
     */
    public int getLp() {
      return lp;
    }

    /**
     * Sets left paren offset
     *
     * @param lp a int.
     */
    public void setLp(int lp) {
      this.lp = lp;
    }

    /**
     * Returns right paren offset
     *
     * @return a int.
     */
    public int getRp() {
      return rp;
    }

    /**
     * Sets right paren offset
     *
     * @param rp a int.
     */
    public void setRp(int rp) {
      this.rp = rp;
    }

    /**
     * Sets both paren positions
     *
     * @param lp a int.
     * @param rp a int.
     */
    public void setParens(int lp, int rp) {
        this.lp = lp;
        this.rp = rp;
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        sb.append("with (");
        sb.append(expression.toSource(0));
        sb.append(") ");
        if(this.getInlineComment() != null) {
            sb.append(this.getInlineComment().toSource(depth + 1));
        }
        if (statement.getType() == Token.BLOCK) {
            if(this.getInlineComment() != null) {
                sb.append("\n");
            }
            sb.append(statement.toSource(depth).trim());
            sb.append("\n");
        } else {
            sb.append("\n").append(statement.toSource(depth + 1));
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     *
     * Visits this node, then the with-object, then the body statement.
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            expression.visit(v);
            statement.visit(v);
        }
    }
}
