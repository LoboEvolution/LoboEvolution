/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.mozilla.javascript.Token;


/**
 * Try/catch/finally statement.  Node type is {@link Token#TRY}.
 *
 * <pre><i>TryStatement</i> :
 *        <b>try</b> Block Catch
 *        <b>try</b> Block Finally
 *        <b>try</b> Block Catch Finally
 * <i>Catch</i> :
 *        <b>catch</b> ( <i><b>Identifier</b></i> ) Block
 * <i>Finally</i> :
 *        <b>finally</b> Block</pre>
 */
public class TryStatement extends AstNode {

    /** The Constant NO_CATCHES. */
    private static final List<CatchClause> NO_CATCHES =
        Collections.unmodifiableList(new ArrayList<CatchClause>());

    /** The try block. */
    private AstNode tryBlock;
    
    /** The catch clauses. */
    private List<CatchClause> catchClauses;
    
    /** The finally block. */
    private AstNode finallyBlock;
    
    /** The finally position. */
    private int finallyPosition = -1;

    {
        type = Token.TRY;
    }

    /**
     * Instantiates a new try statement.
     */
    public TryStatement() {
    }

    /**
     * Instantiates a new try statement.
     *
     * @param pos the pos
     */
    public TryStatement(int pos) {
        super(pos);
    }

    /**
     * Instantiates a new try statement.
     *
     * @param pos the pos
     * @param len the len
     */
    public TryStatement(int pos, int len) {
        super(pos, len);
    }

    /**
     * Gets the try block.
     *
     * @return the try block
     */
    public AstNode getTryBlock() {
        return tryBlock;
    }

    /**
     * Sets try block.  Also sets its parent to this node.
     *
     * @param tryBlock the new try block
     */
    public void setTryBlock(AstNode tryBlock) {
        assertNotNull(tryBlock);
        this.tryBlock = tryBlock;
        tryBlock.setParent(this);
    }

    /**
     * Returns list of {@link CatchClause} nodes.  If there are no catch
     * clauses, returns an immutable empty list.
     *
     * @return the catch clauses
     */
    public List<CatchClause> getCatchClauses() {
        return catchClauses != null ? catchClauses : NO_CATCHES;
    }

    /**
     * Sets list of {@link CatchClause} nodes.  Also sets their parents
     * to this node.  May be {@code null}.  Replaces any existing catch
     * clauses for this node.
     *
     * @param catchClauses the new catch clauses
     */
    public void setCatchClauses(List<CatchClause> catchClauses) {
        if (catchClauses == null) {
            this.catchClauses = null;
        } else {
            if (this.catchClauses != null)
                this.catchClauses.clear();
            for (CatchClause cc : catchClauses) {
                addCatchClause(cc);
            }
        }
    }

    /**
     * Add a catch-clause to the end of the list, and sets its parent to
     * this node.
     *
     * @param clause the clause
     */
    public void addCatchClause(CatchClause clause) {
        assertNotNull(clause);
        if (catchClauses == null) {
            catchClauses = new ArrayList<CatchClause>();
        }
        catchClauses.add(clause);
        clause.setParent(this);
    }

    /**
     * Returns finally block, or {@code null} if not present.
     *
     * @return the finally block
     */
    public AstNode getFinallyBlock() {
        return finallyBlock;
    }

    /**
     * Sets finally block, and sets its parent to this node.
     * May be {@code null}.
     *
     * @param finallyBlock the new finally block
     */
    public void setFinallyBlock(AstNode finallyBlock) {
        this.finallyBlock = finallyBlock;
        if (finallyBlock != null)
            finallyBlock.setParent(this);
    }

    /**
     * Returns position of {@code finally} keyword, if present, or -1.
     *
     * @return the finally position
     */
    public int getFinallyPosition() {
        return finallyPosition;
    }

    /**
     * Sets position of {@code finally} keyword, if present, or -1.
     *
     * @param finallyPosition the new finally position
     */
    public void setFinallyPosition(int finallyPosition) {
        this.finallyPosition = finallyPosition;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.AstNode#toSource(int)
     */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder(250);
        sb.append(makeIndent(depth));
        sb.append("try ");
        sb.append(tryBlock.toSource(depth).trim());
        for (CatchClause cc : getCatchClauses()) {
            sb.append(cc.toSource(depth));
        }
        if (finallyBlock != null) {
            sb.append(" finally ");
            sb.append(finallyBlock.toSource(depth));
        }
        return sb.toString();
    }

    /**
     * Visits this node, then the try-block, then any catch clauses,
     * and then any finally block.
     *
     * @param v the v
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            tryBlock.visit(v);
            for (CatchClause cc : getCatchClauses()) {
                cc.visit(v);
            }
            if (finallyBlock != null) {
                finallyBlock.visit(v);
            }
        }
    }
}
