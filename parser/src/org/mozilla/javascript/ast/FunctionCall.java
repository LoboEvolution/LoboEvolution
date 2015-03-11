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
 * AST node for a function call.  Node type is {@link Token#CALL}.
 */
public class FunctionCall extends AstNode {

    /** The Constant NO_ARGS. */
    protected static final List<AstNode> NO_ARGS =
        Collections.unmodifiableList(new ArrayList<AstNode>());

    /** The target. */
    protected AstNode target;
    
    /** The arguments. */
    protected List<AstNode> arguments;
    
    /** The lp. */
    protected int lp = -1;
    
    /** The rp. */
    protected int rp = -1;

    {
        type = Token.CALL;
    }

    /**
     * Instantiates a new function call.
     */
    public FunctionCall() {
    }

    /**
     * Instantiates a new function call.
     *
     * @param pos the pos
     */
    public FunctionCall(int pos) {
        super(pos);
    }

    /**
     * Instantiates a new function call.
     *
     * @param pos the pos
     * @param len the len
     */
    public FunctionCall(int pos, int len) {
        super(pos, len);
    }

    /**
     * Returns node evaluating to the function to call.
     *
     * @return the target
     */
    public AstNode getTarget() {
        return target;
    }

    /**
     * Sets node evaluating to the function to call, and sets
     * its parent to this node.
     *
     * @param target node evaluating to the function to call.
     */
    public void setTarget(AstNode target) {
        assertNotNull(target);
        this.target = target;
        target.setParent(this);
    }

    /**
     * Returns function argument list.
     *
     * @return function argument list, or an empty immutable list if
     *         there are no arguments.
     */
    public List<AstNode> getArguments() {
        return arguments != null ? arguments : NO_ARGS;
    }

    /**
     * Sets function argument list.
     *
     * @param arguments function argument list.  Can be {@code null},
     *        in which case any existing args are removed.
     */
    public void setArguments(List<AstNode> arguments) {
        if (arguments == null) {
            this.arguments = null;
        } else {
            if (this.arguments != null)
                this.arguments.clear();
            for (AstNode arg : arguments) {
                addArgument(arg);
            }
        }
    }

    /**
     * Adds an argument to the list, and sets its parent to this node.
     *
     * @param arg the argument node to add to the list
     */
    public void addArgument(AstNode arg) {
        assertNotNull(arg);
        if (arguments == null) {
            arguments = new ArrayList<AstNode>();
        }
        arguments.add(arg);
        arg.setParent(this);
    }

    /**
     * Returns left paren position, -1 if missing.
     *
     * @return the lp
     */
    public int getLp() {
        return lp;
    }

    /**
     * Sets left paren position.
     *
     * @param lp left paren position
     */
    public void setLp(int lp) {
        this.lp = lp;
    }

    /**
     * Returns right paren position, -1 if missing.
     *
     * @return the rp
     */
    public int getRp() {
        return rp;
    }

    /**
     * Sets right paren position.
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
        sb.append(target.toSource(0));
        sb.append("(");
        if (arguments != null) {
            printList(arguments, sb);
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Visits this node, the target object, and the arguments.
     *
     * @param v the v
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            target.visit(v);
            for (AstNode arg : getArguments()) {
                arg.visit(v);
            }
        }
    }
}
