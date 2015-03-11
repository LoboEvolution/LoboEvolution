/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;


/**
 * A variable declaration or initializer, part of a {@link VariableDeclaration}
 * expression.  The variable "target" can be a simple name or a destructuring
 * form.  The initializer, if present, can be any expression.<p>
 *
 * Node type is one of {@link Token#VAR}, {@link Token#CONST}, or
 * {@link Token#LET}.
 */
public class VariableInitializer extends AstNode {

    /** The target. */
    private AstNode target;
    
    /** The initializer. */
    private AstNode initializer;

    {
        type = Token.VAR;
    }

    /**
     * Sets the node type.
     *
     * @param nodeType the new node type
     */
    public void setNodeType(int nodeType) {
        if (nodeType != Token.VAR
            && nodeType != Token.CONST
            && nodeType != Token.LET)
            throw new IllegalArgumentException("invalid node type");
        setType(nodeType);
    }

    /**
     * Instantiates a new variable initializer.
     */
    public VariableInitializer() {
    }

    /**
     * Instantiates a new variable initializer.
     *
     * @param pos the pos
     */
    public VariableInitializer(int pos) {
        super(pos);
    }

    /**
     * Instantiates a new variable initializer.
     *
     * @param pos the pos
     * @param len the len
     */
    public VariableInitializer(int pos, int len) {
        super(pos, len);
    }


    /**
     * Returns true if this is a destructuring assignment.  If so, the
     * initializer must be non-{@code null}.
     * @return {@code true} if the {@code target} field is a destructuring form
     * (an {@link ArrayLiteral} or {@link ObjectLiteral} node)
     */
    public boolean isDestructuring() {
        return !(target instanceof Name);
    }

    /**
     * Returns the variable name or destructuring form.
     *
     * @return the target
     */
    public AstNode getTarget() {
        return target;
    }

    /**
     * Sets the variable name or destructuring form, and sets
     * its parent to this node.
     *
     * @param target the new target
     */
    public void setTarget(AstNode target) {
        // Don't throw exception if target is an "invalid" node type.
        // See mozilla/js/tests/js1_7/block/regress-350279.js
        if (target == null)
            throw new IllegalArgumentException("invalid target arg");
        this.target = target;
        target.setParent(this);
    }

    /**
     * Returns the initial value, or {@code null} if not provided.
     *
     * @return the initializer
     */
    public AstNode getInitializer() {
        return initializer;
    }

    /**
     * Sets the initial value expression, and sets its parent to this node.
     * @param initializer the initial value.  May be {@code null}.
     */
    public void setInitializer(AstNode initializer) {
        this.initializer = initializer;
        if (initializer != null)
            initializer.setParent(this);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.AstNode#toSource(int)
     */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        sb.append(target.toSource(0));
        if (initializer != null) {
            sb.append (" = ");
            sb.append(initializer.toSource(0));
        }
        return sb.toString();
    }

    /**
     * Visits this node, then the target expression, then the initializer
     * expression if present.
     *
     * @param v the v
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            target.visit(v);
            if (initializer != null) {
                initializer.visit(v);
            }
        }
    }
}
