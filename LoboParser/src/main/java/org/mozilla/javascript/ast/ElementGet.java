/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

/**
 * AST node for an indexed property reference, such as {@code foo['bar']} or
 * {@code foo[2]}.  This is sometimes called an "element-get" operation, hence
 * the name of the node.<p>
 *
 * Node type is {@link org.mozilla.javascript.Token#GETELEM}.<p>
 *
 * The node bounds extend from the beginning position of the target through the
 * closing right-bracket.  In the presence of a syntax error, the right bracket
 * position is -1, and the node ends at the end of the element expression.
 *
 * @author utente
 * @version $Id: $Id
 */
public class ElementGet extends AstNode {

    private AstNode target;
    private AstNode element;
    private int lb = -1;
    private int rb = -1;

    {
        type = Token.GETELEM;
    }

    /**
     * <p>Constructor for ElementGet.</p>
     */
    public ElementGet() {
    }

    /**
     * <p>Constructor for ElementGet.</p>
     *
     * @param pos a int.
     */
    public ElementGet(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for ElementGet.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public ElementGet(int pos, int len) {
        super(pos, len);
    }

    /**
     * <p>Constructor for ElementGet.</p>
     *
     * @param target a {@link org.mozilla.javascript.ast.AstNode} object.
     * @param element a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public ElementGet(AstNode target, AstNode element) {
        setTarget(target);
        setElement(element);
    }

    /**
     * Returns the object on which the element is being fetched.
     *
     * @return a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public AstNode getTarget() {
        return target;
    }

    /**
     * Sets target object, and sets its parent to this node.
     *
     * @param target expression evaluating to the object upon which
     * to do the element lookup
     * @throws java.lang.IllegalArgumentException if target is {@code null}
     */
    public void setTarget(AstNode target) {
        assertNotNull(target);
        this.target = target;
        target.setParent(this);
    }

    /**
     * Returns the element being accessed
     *
     * @return a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public AstNode getElement() {
        return element;
    }

    /**
     * Sets the element being accessed, and sets its parent to this node.
     *
     * @throws java.lang.IllegalArgumentException if element is {@code null}
     * @param element a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public void setElement(AstNode element) {
        assertNotNull(element);
        this.element = element;
        element.setParent(this);
    }

    /**
     * Returns left bracket position
     *
     * @return a int.
     */
    public int getLb() {
        return lb;
    }

    /**
     * Sets left bracket position
     *
     * @param lb a int.
     */
    public void setLb(int lb) {
        this.lb = lb;
    }

    /**
     * Returns right bracket position, -1 if missing
     *
     * @return a int.
     */
    public int getRb() {
        return rb;
    }

    /**
     * Sets right bracket position, -1 if not present
     *
     * @param rb a int.
     */
    public void setRb(int rb) {
        this.rb = rb;
    }

    /**
     * <p>setParens.</p>
     *
     * @param lb a int.
     * @param rb a int.
     */
    public void setParens(int lb, int rb) {
        this.lb = lb;
        this.rb = rb;
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        sb.append(target.toSource(0));
        sb.append("[");
        sb.append(element.toSource(0));
        sb.append("]");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     *
     * Visits this node, the target, and the index expression.
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            target.visit(v);
            element.visit(v);
        }
    }
}
