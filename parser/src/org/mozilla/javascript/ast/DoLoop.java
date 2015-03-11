/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;


/**
 * Do statement.  Node type is {@link Token#DO}.
 *
 * <pre><i>DoLoop</i>:
 * <b>do</b> Statement <b>while</b> <b>(</b> Expression <b>)</b> <b>;</b></pre>
 */
public class DoLoop extends Loop {

    /** The condition. */
    private AstNode condition;
    
    /** The while position. */
    private int whilePosition = -1;

    {
        type = Token.DO;
    }

    /**
     * Instantiates a new do loop.
     */
    public DoLoop() {
    }

    /**
     * Instantiates a new do loop.
     *
     * @param pos the pos
     */
    public DoLoop(int pos) {
        super(pos);
    }

    /**
     * Instantiates a new do loop.
     *
     * @param pos the pos
     * @param len the len
     */
    public DoLoop(int pos, int len) {
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
     * Sets loop condition, and sets its parent to this node.
     *
     * @param condition the new condition
     */
    public void setCondition(AstNode condition) {
        assertNotNull(condition);
        this.condition = condition;
        condition.setParent(this);
    }

    /**
     * Returns source position of "while" keyword.
     *
     * @return the while position
     */
    public int getWhilePosition() {
        return whilePosition;
    }

    /**
     * Sets source position of "while" keyword.
     *
     * @param whilePosition the new while position
     */
    public void setWhilePosition(int whilePosition) {
        this.whilePosition = whilePosition;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.Scope#toSource(int)
     */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        sb.append("do ");
        sb.append(body.toSource(depth).trim());
        sb.append(" while (");
        sb.append(condition.toSource(0));
        sb.append(");\n");
        return sb.toString();
    }

    /**
     * Visits this node, the body, and then the while-expression.
     *
     * @param v the v
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            body.visit(v);
            condition.visit(v);
        }
    }
}
