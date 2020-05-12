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
 * {@link org.mozilla.javascript.ast.XmlRef}. <p>
 *
 * Node type is {@link org.mozilla.javascript.Token#DOT} or {@link org.mozilla.javascript.Token#DOTDOT}.
 *
 * @author utente
 * @version $Id: $Id
 */
public class XmlMemberGet extends InfixExpression {

    {
        type = Token.DOTDOT;
    }

    /**
     * <p>Constructor for XmlMemberGet.</p>
     */
    public XmlMemberGet() {
    }

    /**
     * <p>Constructor for XmlMemberGet.</p>
     *
     * @param pos a int.
     */
    public XmlMemberGet(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for XmlMemberGet.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public XmlMemberGet(int pos, int len) {
        super(pos, len);
    }

    /**
     * <p>Constructor for XmlMemberGet.</p>
     *
     * @param pos a int.
     * @param len a int.
     * @param target a {@link org.mozilla.javascript.ast.AstNode} object.
     * @param ref a {@link org.mozilla.javascript.ast.XmlRef} object.
     */
    public XmlMemberGet(int pos, int len, AstNode target, XmlRef ref) {
        super(pos, len, target, ref);
    }

    /**
     * Constructs a new {@code XmlMemberGet} node.
     * Updates bounds to include {@code target} and {@code ref} nodes.
     *
     * @param target a {@link org.mozilla.javascript.ast.AstNode} object.
     * @param ref a {@link org.mozilla.javascript.ast.XmlRef} object.
     */
    public XmlMemberGet(AstNode target, XmlRef ref) {
        super(target, ref);
    }

    /**
     * <p>Constructor for XmlMemberGet.</p>
     *
     * @param target a {@link org.mozilla.javascript.ast.AstNode} object.
     * @param ref a {@link org.mozilla.javascript.ast.XmlRef} object.
     * @param opPos a int.
     */
    public XmlMemberGet(AstNode target, XmlRef ref, int opPos) {
        super(Token.DOTDOT, target, ref, opPos);
    }

    /**
     * Returns the object on which the XML member-ref expression
     * is being evaluated.  Should never be {@code null}.
     *
     * @return a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public AstNode getTarget() {
        return getLeft();
    }

    /**
     * Sets target object, and sets its parent to this node.
     *
     * @throws java.lang.IllegalArgumentException if {@code target} is {@code null}
     * @param target a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public void setTarget(AstNode target) {
        setLeft(target);
    }

    /**
     * Returns the right-side XML member ref expression.
     * Should never be {@code null} unless the code is malformed.
     *
     * @return a {@link org.mozilla.javascript.ast.XmlRef} object.
     */
    public XmlRef getMemberRef() {
        return (XmlRef)getRight();
    }

    /**
     * Sets the XML member-ref expression, and sets its parent
     * to this node.
     *
     * @throws java.lang.IllegalArgumentException if property is {@code null}
     * @param ref a {@link org.mozilla.javascript.ast.XmlRef} object.
     */
    public void setProperty(XmlRef ref) {
        setRight(ref);
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        sb.append(getLeft().toSource(0));
        sb.append(dotsToString());
        sb.append(getRight().toSource(0));
        return sb.toString();
    }

    /**
     * Gives string representation of inner dots token.
     * @return  String representation of inner dots token (e.g. '.' or '..')
     * @throws IllegalArgumentException on unexpected token type
     */
    private String dotsToString() {
        switch (getType()) {
            case Token.DOT:
                return ".";
            case Token.DOTDOT:
                return "..";
            default:
                throw new IllegalArgumentException("Invalid type of XmlMemberGet: " + getType());
        }
    }
}
