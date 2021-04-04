/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

/**
 * While statement.  Node type is {@link org.mozilla.javascript.Token#WHILE}.
 *
 * <pre><i>WhileStatement</i>:
 *     <b>while</b> <b>(</b> Expression <b>)</b> Statement</pre>
 *
 *
 *
 */
public class WhileLoop extends Loop {

    private AstNode condition;

    {
        type = Token.WHILE;
    }

    /**
     * <p>Constructor for WhileLoop.</p>
     */
    public WhileLoop() {
    }

    /**
     * <p>Constructor for WhileLoop.</p>
     *
     * @param pos a int.
     */
    public WhileLoop(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for WhileLoop.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public WhileLoop(int pos, int len) {
        super(pos, len);
    }

    /**
     * Returns loop condition
     *
     * @return a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public AstNode getCondition() {
        return condition;
    }

    /**
     * Sets loop condition
     *
     * @throws java.lang.IllegalArgumentException} if condition is {@code null}
     * @param condition a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public void setCondition(AstNode condition) {
        assertNotNull(condition);
        this.condition = condition;
        condition.setParent(this);
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        sb.append("while (");
        sb.append(condition.toSource(0));
        sb.append(") ");
        if(this.getInlineComment() != null) {
            sb.append(this.getInlineComment().toSource(depth + 1)).append("\n");
        }
        if (body.getType() == Token.BLOCK) {
            sb.append(body.toSource(depth).trim());
            sb.append("\n");
        } else {
            if(this.getInlineComment() == null) {
                sb.append("\n");
            }
            sb.append(body.toSource(depth+1));
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     *
     * Visits this node, the condition, then the body.
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            condition.visit(v);
            body.visit(v);
        }
    }
}
