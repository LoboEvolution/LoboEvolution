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
 * Node type is {@link Token#GETELEM}.
 *
 * The node bounds extend from the beginning position of the target through the
 * closing right-bracket.  In the presence of a syntax error, the right bracket
 * position is -1, and the node ends at the end of the element expression.
 */
public class ElementGet extends AstNode {

    /** The target. */
    private AstNode target;
    
    /** The element. */
    private AstNode element;
    
    /** The lb. */
    private int lb = -1;
    
    /** The rb. */
    private int rb = -1;

    {
        type = Token.GETELEM;
    }

    /**
     * Instantiates a new element get.
     */
    public ElementGet() {
    }

    /**
     * Instantiates a new element get.
     *
     * @param pos the pos
     */
    public ElementGet(int pos) {
        super(pos);
    }

    /**
     * Instantiates a new element get.
     *
     * @param pos the pos
     * @param len the len
     */
    public ElementGet(int pos, int len) {
        super(pos, len);
    }

    /**
     * Instantiates a new element get.
     *
     * @param target the target
     * @param element the element
     */
    public ElementGet(AstNode target, AstNode element) {
        setTarget(target);
        setElement(element);
    }

    /**
     * Returns the object on which the element is being fetched.
     *
     * @return the target
     */
    public AstNode getTarget() {
        return target;
    }

    /**
     * Sets target object, and sets its parent to this node.
     *
     * @param target expression evaluating to the object upon which
     * to do the element lookup
     */
    public void setTarget(AstNode target) {
        assertNotNull(target);
        this.target = target;
        target.setParent(this);
    }

    /**
     * Returns the element being accessed.
     *
     * @return the element
     */
    public AstNode getElement() {
        return element;
    }

    /**
     * Sets the element being accessed, and sets its parent to this node.
     *
     * @param element the new element
     */
    public void setElement(AstNode element) {
        assertNotNull(element);
        this.element = element;
        element.setParent(this);
    }

    /**
     * Returns left bracket position.
     *
     * @return the lb
     */
    public int getLb() {
        return lb;
    }

    /**
     * Sets left bracket position.
     *
     * @param lb the new lb
     */
    public void setLb(int lb) {
        this.lb = lb;
    }

    /**
     * Returns right bracket position, -1 if missing.
     *
     * @return the rb
     */
    public int getRb() {
        return rb;
    }

    /**
     * Sets right bracket position, -1 if not present.
     *
     * @param rb the new rb
     */
    public void setRb(int rb) {
        this.rb = rb;
    }

    /**
     * Sets the parens.
     *
     * @param lb the lb
     * @param rb the rb
     */
    public void setParens(int lb, int rb) {
        this.lb = lb;
        this.rb = rb;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.AstNode#toSource(int)
     */
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
     * Visits this node, the target, and the index expression.
     *
     * @param v the v
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            target.visit(v);
            element.visit(v);
        }
    }
}
