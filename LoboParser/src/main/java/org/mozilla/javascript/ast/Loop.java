/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

/**
 * Abstract base type for loops.
 *
 *
 *
 */
public abstract class Loop extends Scope {

    protected AstNode body;
    protected int lp = -1;
    protected int rp = -1;

    /**
     * <p>Constructor for Loop.</p>
     */
    public Loop() {
    }

    /**
     * <p>Constructor for Loop.</p>
     *
     * @param pos a int.
     */
    public Loop(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for Loop.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public Loop(int pos, int len) {
        super(pos, len);
    }

    /**
     * Returns loop body
     *
     * @return a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public AstNode getBody() {
        return body;
    }

    /**
     * Sets loop body.  Sets the parent of the body to this loop node,
     * and updates its offset to be relative.  Extends the length of this
     * node to include the body.
     *
     * @param body a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public void setBody(AstNode body) {
        this.body = body;
        int end = body.getPosition() + body.getLength();
        this.setLength(end - this.getPosition());
        body.setParent(this);
    }

    /**
     * Returns left paren position, -1 if missing
     *
     * @return a int.
     */
    public int getLp() {
        return lp;
    }

    /**
     * Sets left paren position
     *
     * @param lp a int.
     */
    public void setLp(int lp) {
        this.lp = lp;
    }

    /**
     * Returns right paren position, -1 if missing
     *
     * @return a int.
     */
    public int getRp() {
        return rp;
    }

    /**
     * Sets right paren position
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
}
