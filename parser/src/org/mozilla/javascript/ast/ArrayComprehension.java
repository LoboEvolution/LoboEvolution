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
 * AST node for a JavaScript 1.7 Array comprehension.
 * Node type is {@link Token#ARRAYCOMP}.
 */
public class ArrayComprehension extends Scope {

    /** The result. */
    private AstNode result;
    
    /** The loops. */
    private List<ArrayComprehensionLoop> loops =
        new ArrayList<ArrayComprehensionLoop>();
    
    /** The filter. */
    private AstNode filter;
    
    /** The if position. */
    private int ifPosition = -1;
    
    /** The lp. */
    private int lp = -1;
    
    /** The rp. */
    private int rp = -1;

    {
        type = Token.ARRAYCOMP;
    }

    /**
     * Instantiates a new array comprehension.
     */
    public ArrayComprehension() {
    }

    /**
     * Instantiates a new array comprehension.
     *
     * @param pos the pos
     */
    public ArrayComprehension(int pos) {
        super(pos);
    }

    /**
     * Instantiates a new array comprehension.
     *
     * @param pos the pos
     * @param len the len
     */
    public ArrayComprehension(int pos, int len) {
        super(pos, len);
    }

    /**
     * Returns result expression node (just after opening bracket).
     *
     * @return the result
     */
    public AstNode getResult() {
        return result;
    }

    /**
     * Sets result expression, and sets its parent to this node.
     *
     * @param result the new result
     */
    public void setResult(AstNode result) {
        assertNotNull(result);
        this.result = result;
        result.setParent(this);
    }

    /**
     * Returns loop list.
     *
     * @return the loops
     */
    public List<ArrayComprehensionLoop> getLoops() {
        return loops;
    }

    /**
     * Sets loop list.
     *
     * @param loops the new loops
     */
    public void setLoops(List<ArrayComprehensionLoop> loops) {
        assertNotNull(loops);
        this.loops.clear();
        for (ArrayComprehensionLoop acl : loops) {
            addLoop(acl);
        }
    }

    /**
     * Adds a child loop node, and sets its parent to this node.
     *
     * @param acl the acl
     */
    public void addLoop(ArrayComprehensionLoop acl) {
        assertNotNull(acl);
        loops.add(acl);
        acl.setParent(this);
    }

    /**
     * Returns filter expression, or {@code null} if not present.
     *
     * @return the filter
     */
    public AstNode getFilter() {
        return filter;
    }

    /**
     * Sets filter expression, and sets its parent to this node.
     * Can be {@code null}.
     *
     * @param filter the new filter
     */
    public void setFilter(AstNode filter) {
        this.filter = filter;
        if (filter != null)
            filter.setParent(this);
    }

    /**
     * Returns position of 'if' keyword, -1 if not present.
     *
     * @return the if position
     */
    public int getIfPosition() {
        return ifPosition;
    }

    /**
     * Sets position of 'if' keyword.
     *
     * @param ifPosition the new if position
     */
    public void setIfPosition(int ifPosition) {
        this.ifPosition = ifPosition;
    }

    /**
     * Returns filter left paren position, or -1 if no filter.
     *
     * @return the filter lp
     */
    public int getFilterLp() {
        return lp;
    }

    /**
     * Sets filter left paren position, or -1 if no filter.
     *
     * @param lp the new filter lp
     */
    public void setFilterLp(int lp) {
        this.lp = lp;
    }

    /**
     * Returns filter right paren position, or -1 if no filter.
     *
     * @return the filter rp
     */
    public int getFilterRp() {
        return rp;
    }

    /**
     * Sets filter right paren position, or -1 if no filter.
     *
     * @param rp the new filter rp
     */
    public void setFilterRp(int rp) {
        this.rp = rp;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.Scope#toSource(int)
     */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder(250);
        sb.append("[");
        sb.append(result.toSource(0));
        for (ArrayComprehensionLoop loop : loops) {
            sb.append(loop.toSource(0));
        }
        if (filter != null) {
            sb.append(" if (");
            sb.append(filter.toSource(0));
            sb.append(")");
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Visits this node, the result expression, the loops, and the optional
     * filter.
     *
     * @param v the v
     */
    @Override
    public void visit(NodeVisitor v) {
        if (!v.visit(this)) {
            return;
        }
        result.visit(v);
        for (ArrayComprehensionLoop loop : loops) {
            loop.visit(v);
        }
        if (filter != null) {
            filter.visit(v);
        }
    }
}
