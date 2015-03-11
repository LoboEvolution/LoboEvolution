/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;


/**
 * AST node for E4X ".@" and ".." expressions, such as
 * {@code foo..bar}, {@code foo..@bar}, {@code @foo.@bar}, and
 * {@code foo..@ns::*}.  The right-hand node is always an
 * {@link XmlRef}. <p>
 *
 * Node type is {@link Token#DOT} or {@link Token#DOTDOT}.
 */
public class XmlMemberGet extends InfixExpression {

    {
        type = Token.DOTDOT;
    }

    /**
     * Instantiates a new xml member get.
     */
    public XmlMemberGet() {
    }

    /**
     * Instantiates a new xml member get.
     *
     * @param pos the pos
     */
    public XmlMemberGet(int pos) {
        super(pos);
    }

    /**
     * Instantiates a new xml member get.
     *
     * @param pos the pos
     * @param len the len
     */
    public XmlMemberGet(int pos, int len) {
        super(pos, len);
    }

    /**
     * Instantiates a new xml member get.
     *
     * @param pos the pos
     * @param len the len
     * @param target the target
     * @param ref the ref
     */
    public XmlMemberGet(int pos, int len, AstNode target, XmlRef ref) {
        super(pos, len, target, ref);
    }

    /**
     * Constructs a new {@code XmlMemberGet} node.
     * Updates bounds to include {@code target} and {@code ref} nodes.
     *
     * @param target the target
     * @param ref the ref
     */
    public XmlMemberGet(AstNode target, XmlRef ref) {
        super(target, ref);
    }

    /**
     * Instantiates a new xml member get.
     *
     * @param target the target
     * @param ref the ref
     * @param opPos the op pos
     */
    public XmlMemberGet(AstNode target, XmlRef ref, int opPos) {
        super(Token.DOTDOT, target, ref, opPos);
    }

    /**
     * Returns the object on which the XML member-ref expression
     * is being evaluated.  Should never be {@code null}.
     *
     * @return the target
     */
    public AstNode getTarget() {
        return getLeft();
    }

    /**
     * Sets target object, and sets its parent to this node.
     *
     * @param target the new target
     */
    public void setTarget(AstNode target) {
        setLeft(target);
    }

    /**
     * Returns the right-side XML member ref expression.
     * Should never be {@code null} unless the code is malformed.
     *
     * @return the member ref
     */
    public XmlRef getMemberRef() {
        return (XmlRef)getRight();
    }

    /**
     * Sets the XML member-ref expression, and sets its parent
     * to this node.
     *
     * @param ref the new property
     */
    public void setProperty(XmlRef ref) {
        setRight(ref);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.InfixExpression#toSource(int)
     */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        sb.append(getLeft().toSource(0));
        sb.append(operatorToString(getType()));
        sb.append(getRight().toSource(0));
        return sb.toString();
    }
}
