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
 * Try/catch/finally statement.  Node type is {@link org.mozilla.javascript.Token#TRY}.
 *
 * <pre><i>TryStatement</i> :
 *        <b>try</b> Block Catch
 *        <b>try</b> Block Finally
 *        <b>try</b> Block Catch Finally
 * <i>Catch</i> :
 *        <b>catch</b> ( <i><b>Identifier</b></i> ) Block
 * <i>Finally</i> :
 *        <b>finally</b> Block</pre>
 *
 * @author utente
 * @version $Id: $Id
 */
public class TryStatement extends AstNode {

    private static final List<CatchClause> NO_CATCHES =
        Collections.unmodifiableList(new ArrayList<CatchClause>());

    private AstNode tryBlock;
    private List<CatchClause> catchClauses;
    private AstNode finallyBlock;
    private int finallyPosition = -1;

    {
        type = Token.TRY;
    }

    /**
     * <p>Constructor for TryStatement.</p>
     */
    public TryStatement() {
    }

    /**
     * <p>Constructor for TryStatement.</p>
     *
     * @param pos a int.
     */
    public TryStatement(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for TryStatement.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public TryStatement(int pos, int len) {
        super(pos, len);
    }

    /**
     * <p>Getter for the field tryBlock.</p>
     *
     * @return a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public AstNode getTryBlock() {
        return tryBlock;
    }

    /**
     * Sets try block.  Also sets its parent to this node.
     *
     * @throws java.lang.IllegalArgumentException} if {@code tryBlock} is {@code null}
     * @param tryBlock a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public void setTryBlock(AstNode tryBlock) {
        assertNotNull(tryBlock);
        this.tryBlock = tryBlock;
        tryBlock.setParent(this);
    }

    /**
     * Returns list of {@link org.mozilla.javascript.ast.CatchClause} nodes.  If there are no catch
     * clauses, returns an immutable empty list.
     *
     * @return a {@link java.util.List} object.
     */
    public List<CatchClause> getCatchClauses() {
        return catchClauses != null ? catchClauses : NO_CATCHES;
    }

    /**
     * Sets list of {@link org.mozilla.javascript.ast.CatchClause} nodes.  Also sets their parents
     * to this node.  May be {@code null}.  Replaces any existing catch
     * clauses for this node.
     *
     * @param catchClauses a {@link java.util.List} object.
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
     * @throws java.lang.IllegalArgumentException} if {@code clause} is {@code null}
     * @param clause a {@link org.mozilla.javascript.ast.CatchClause} object.
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
     * Returns finally block, or {@code null} if not present
     *
     * @return a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public AstNode getFinallyBlock() {
        return finallyBlock;
    }

    /**
     * Sets finally block, and sets its parent to this node.
     * May be {@code null}.
     *
     * @param finallyBlock a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public void setFinallyBlock(AstNode finallyBlock) {
        this.finallyBlock = finallyBlock;
        if (finallyBlock != null)
            finallyBlock.setParent(this);
    }

    /**
     * Returns position of {@code finally} keyword, if present, or -1
     *
     * @return a int.
     */
    public int getFinallyPosition() {
        return finallyPosition;
    }

    /**
     * Sets position of {@code finally} keyword, if present, or -1
     *
     * @param finallyPosition a int.
     */
    public void setFinallyPosition(int finallyPosition) {
        this.finallyPosition = finallyPosition;
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder(250);
        sb.append(makeIndent(depth));
        sb.append("try ");
        if(this.getInlineComment() != null) {
            sb.append(this.getInlineComment().toSource(depth + 1)).append("\n");
        }
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
     * {@inheritDoc}
     *
     * Visits this node, then the try-block, then any catch clauses,
     * and then any finally block.
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
