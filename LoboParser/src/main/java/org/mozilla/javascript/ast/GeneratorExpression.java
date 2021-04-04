/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import java.util.ArrayList;
import java.util.List;

import org.mozilla.javascript.Token;

/**
 * <p>GeneratorExpression class.</p>
 *
 *
 *
 */
public class GeneratorExpression extends Scope {
    
    private AstNode result;
    private List<GeneratorExpressionLoop> loops =
        new ArrayList<GeneratorExpressionLoop>();
    private AstNode filter;
    private int ifPosition = -1;
    private int lp = -1;
    private int rp = -1;
    
    {
        type = Token.GENEXPR;
    }

    /**
     * <p>Constructor for GeneratorExpression.</p>
     */
    public GeneratorExpression() {
    }

    /**
     * <p>Constructor for GeneratorExpression.</p>
     *
     * @param pos a int.
     */
    public GeneratorExpression(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for GeneratorExpression.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public GeneratorExpression(int pos, int len) {
        super(pos, len);
    }
    
    /**
     * Returns result expression node (just after opening bracket)
     *
     * @return a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public AstNode getResult() {
        return result;
    }

    /**
     * Sets result expression, and sets its parent to this node.
     *
     * @throws java.lang.IllegalArgumentException if result is {@code null}
     * @param result a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public void setResult(AstNode result) {
        assertNotNull(result);
        this.result = result;
        result.setParent(this);
    }

    /**
     * Returns loop list
     *
     * @return a {@link java.util.List} object.
     */
    public List<GeneratorExpressionLoop> getLoops() {
        return loops;
    }

    /**
     * Sets loop list
     *
     * @throws java.lang.IllegalArgumentException if loops is {@code null}
     * @param loops a {@link java.util.List} object.
     */
    public void setLoops(List<GeneratorExpressionLoop> loops) {
        assertNotNull(loops);
        this.loops.clear();
        for (GeneratorExpressionLoop acl : loops) {
            addLoop(acl);
        }
    }

    /**
     * Adds a child loop node, and sets its parent to this node.
     *
     * @throws java.lang.IllegalArgumentException if acl is {@code null}
     * @param acl a {@link org.mozilla.javascript.ast.GeneratorExpressionLoop} object.
     */
    public void addLoop(GeneratorExpressionLoop acl) {
        assertNotNull(acl);
        loops.add(acl);
        acl.setParent(this);
    }
    
    /**
     * Returns filter expression, or {@code null} if not present
     *
     * @return a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public AstNode getFilter() {
        return filter;
    }

    /**
     * Sets filter expression, and sets its parent to this node.
     * Can be {@code null}.
     *
     * @param filter a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public void setFilter(AstNode filter) {
        this.filter = filter;
        if (filter != null)
            filter.setParent(this);
    }

    /**
     * Returns position of 'if' keyword, -1 if not present
     *
     * @return a int.
     */
    public int getIfPosition() {
        return ifPosition;
    }

    /**
     * Sets position of 'if' keyword
     *
     * @param ifPosition a int.
     */
    public void setIfPosition(int ifPosition) {
        this.ifPosition = ifPosition;
    }

    /**
     * Returns filter left paren position, or -1 if no filter
     *
     * @return a int.
     */
    public int getFilterLp() {
        return lp;
    }

    /**
     * Sets filter left paren position, or -1 if no filter
     *
     * @param lp a int.
     */
    public void setFilterLp(int lp) {
        this.lp = lp;
    }

    /**
     * Returns filter right paren position, or -1 if no filter
     *
     * @return a int.
     */
    public int getFilterRp() {
        return rp;
    }

    /**
     * Sets filter right paren position, or -1 if no filter
     *
     * @param rp a int.
     */
    public void setFilterRp(int rp) {
        this.rp = rp;
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder(250);
        sb.append("(");
        sb.append(result.toSource(0));
        for (GeneratorExpressionLoop loop : loops) {
            sb.append(loop.toSource(0));
        }
        if (filter != null) {
            sb.append(" if (");
            sb.append(filter.toSource(0));
            sb.append(")");
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     *
     * Visits this node, the result expression, the loops, and the optional
     * filter.
     */
    @Override
    public void visit(NodeVisitor v) {
        if (!v.visit(this)) {
            return;
        }
        result.visit(v);
        for (GeneratorExpressionLoop loop : loops) {
            loop.visit(v);
        }
        if (filter != null) {
            filter.visit(v);
        }
    }
}
