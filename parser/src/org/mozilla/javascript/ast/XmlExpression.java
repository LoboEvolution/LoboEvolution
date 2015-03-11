/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;


/**
 * AST node for an embedded JavaScript expression within an E4X XML literal.
 * Node type, like {@link XmlLiteral}, is {@link Token#XML}.  The node length
 * includes the curly braces.
 */
public class XmlExpression extends XmlFragment {

    /** The expression. */
    private AstNode expression;
    
    /** The is xml attribute. */
    private boolean isXmlAttribute;

    /**
     * Instantiates a new xml expression.
     */
    public XmlExpression() {
    }

    /**
     * Instantiates a new xml expression.
     *
     * @param pos the pos
     */
    public XmlExpression(int pos) {
        super(pos);
    }

    /**
     * Instantiates a new xml expression.
     *
     * @param pos the pos
     * @param len the len
     */
    public XmlExpression(int pos, int len) {
        super(pos, len);
    }

    /**
     * Instantiates a new xml expression.
     *
     * @param pos the pos
     * @param expr the expr
     */
    public XmlExpression(int pos, AstNode expr) {
        super(pos);
        setExpression(expr);
    }

    /**
     * Returns the expression embedded in {}.
     *
     * @return the expression
     */
    public AstNode getExpression() {
        return expression;
    }

    /**
     * Sets the expression embedded in {}, and sets its parent to this node.
     *
     * @param expression the new expression
     */
    public void setExpression(AstNode expression) {
        assertNotNull(expression);
        this.expression = expression;
        expression.setParent(this);
    }

    /**
     * Returns whether this is part of an xml attribute value.
     *
     * @return true, if is xml attribute
     */
    public boolean isXmlAttribute() {
      return isXmlAttribute;
    }

    /**
     * Sets whether this is part of an xml attribute value.
     *
     * @param isXmlAttribute the new checks if is xml attribute
     */
    public void setIsXmlAttribute(boolean isXmlAttribute) {
      this.isXmlAttribute = isXmlAttribute;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.AstNode#toSource(int)
     */
    @Override
    public String toSource(int depth) {
        return makeIndent(depth) + "{" + expression.toSource(depth) + "}";
    }

    /**
     * Visits this node, then the child expression.
     *
     * @param v the v
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            expression.visit(v);
        }
    }
}
