/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;


/**
 * AST node for an E4X XML {@code [expr]} member-ref expression.
 * The node type is {@link Token#REF_MEMBER}.
 *
 * Syntax:
 *
 * <pre> @<i><sub>opt</sub></i> ns:: <i><sub>opt</sub></i> [ expr ]</pre>
 *
 * Examples include {@code ns::[expr]}, {@code @ns::[expr]}, {@code @[expr]},
 * {@code *::[expr]} and {@code @*::[expr]}.
 *
 * Note that the form {@code [expr]} (i.e. no namespace or
 * attribute-qualifier) is not a legal {@code XmlElemRef} expression,
 * since it's already used for standard JavaScript {@link ElementGet}
 * array-indexing.  Hence, an {@code XmlElemRef} node always has
 * either the attribute-qualifier, a non-{@code null} namespace node,
 * or both.<p>
 *
 * The node starts at the {@code @} token, if present.  Otherwise it starts
 * at the namespace name.  The node bounds extend through the closing
 * right-bracket, or if it is missing due to a syntax error, through the
 * end of the index expression.
 */
public class XmlElemRef extends XmlRef {

    /** The index expr. */
    private AstNode indexExpr;
    
    /** The lb. */
    private int lb = -1;
    
    /** The rb. */
    private int rb = -1;

    {
        type = Token.REF_MEMBER;
    }

    /**
     * Instantiates a new xml elem ref.
     */
    public XmlElemRef() {
    }

    /**
     * Instantiates a new xml elem ref.
     *
     * @param pos the pos
     */
    public XmlElemRef(int pos) {
        super(pos);
    }

    /**
     * Instantiates a new xml elem ref.
     *
     * @param pos the pos
     * @param len the len
     */
    public XmlElemRef(int pos, int len) {
        super(pos, len);
    }

    /**
     * Returns index expression: the 'expr' in {@code @[expr]}
     * or {@code @*::[expr]}.
     *
     * @return the expression
     */
    public AstNode getExpression() {
        return indexExpr;
    }

    /**
     * Sets index expression, and sets its parent to this node.
     *
     * @param expr the new expression
     */
    public void setExpression(AstNode expr) {
        assertNotNull(expr);
        indexExpr = expr;
        expr.setParent(this);
    }

    /**
     * Returns left bracket position, or -1 if missing.
     *
     * @return the lb
     */
    public int getLb() {
        return lb;
    }

    /**
     * Sets left bracket position, or -1 if missing.
     *
     * @param lb the new lb
     */
    public void setLb(int lb) {
        this.lb = lb;
    }

    /**
     * Returns left bracket position, or -1 if missing.
     *
     * @return the rb
     */
    public int getRb() {
        return rb;
    }

    /**
     * Sets right bracket position, -1 if missing.
     *
     * @param rb the new rb
     */
    public void setRb(int rb) {
        this.rb = rb;
    }

    /**
     * Sets both bracket positions.
     *
     * @param lb the lb
     * @param rb the rb
     */
    public void setBrackets(int lb, int rb) {
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
        if (isAttributeAccess()) {
            sb.append("@");
        }
        if (namespace != null) {
            sb.append(namespace.toSource(0));
            sb.append("::");
        }
        sb.append("[");
        sb.append(indexExpr.toSource(0));
        sb.append("]");
        return sb.toString();
    }

    /**
     * Visits this node, then the namespace if provided, then the
     * index expression.
     *
     * @param v the v
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            if (namespace != null) {
                namespace.visit(v);
            }
            indexExpr.visit(v);
        }
    }
}
