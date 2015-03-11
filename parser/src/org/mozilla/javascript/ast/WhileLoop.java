/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;


/**
 * While statement.  Node type is {@link Token#WHILE}.
 *
 * <pre><i>WhileStatement</i>:
 *     <b>while</b> <b>(</b> Expression <b>)</b> Statement</pre>
 */
public class WhileLoop extends Loop {

    /** The condition. */
    private AstNode condition;

    {
        type = Token.WHILE;
    }

    /**
     * Instantiates a new while loop.
     */
    public WhileLoop() {
    }

    /**
     * Instantiates a new while loop.
     *
     * @param pos the pos
     */
    public WhileLoop(int pos) {
        super(pos);
    }

    /**
     * Instantiates a new while loop.
     *
     * @param pos the pos
     * @param len the len
     */
    public WhileLoop(int pos, int len) {
        super(pos, len);
    }

    /**
     * Returns loop condition.
     *
     * @return the condition
     */
    public AstNode getCondition() {
        return condition;
    }

    /**
     * Sets loop condition.
     *
     * @param condition the new condition
     */
    public void setCondition(AstNode condition) {
        assertNotNull(condition);
        this.condition = condition;
        condition.setParent(this);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.Scope#toSource(int)
     */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        sb.append("while (");
        sb.append(condition.toSource(0));
        sb.append(") ");
        if (body.getType() == Token.BLOCK) {
            sb.append(body.toSource(depth).trim());
            sb.append("\n");
        } else {
            sb.append("\n").append(body.toSource(depth+1));
        }
        return sb.toString();
    }

    /**
     * Visits this node, the condition, then the body.
     *
     * @param v the v
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            condition.visit(v);
            body.visit(v);
        }
    }
}
