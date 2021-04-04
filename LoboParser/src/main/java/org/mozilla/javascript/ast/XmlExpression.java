/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

/**
 * AST node for an embedded JavaScript expression within an E4X XML literal.
 * Node type, like {@link org.mozilla.javascript.ast.XmlLiteral}, is {@link org.mozilla.javascript.Token#XML}.  The node length
 * includes the curly braces.
 *
 *
 *
 */
public class XmlExpression extends XmlFragment {

    private AstNode expression;
    private boolean isXmlAttribute;

    /**
     * <p>Constructor for XmlExpression.</p>
     */
    public XmlExpression() {
    }

    /**
     * <p>Constructor for XmlExpression.</p>
     *
     * @param pos a int.
     */
    public XmlExpression(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for XmlExpression.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public XmlExpression(int pos, int len) {
        super(pos, len);
    }

    /**
     * <p>Constructor for XmlExpression.</p>
     *
     * @param pos a int.
     * @param expr a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public XmlExpression(int pos, AstNode expr) {
        super(pos);
        setExpression(expr);
    }

    /**
     * Returns the expression embedded in {}
     *
     * @return a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public AstNode getExpression() {
        return expression;
    }

    /**
     * Sets the expression embedded in {}, and sets its parent to this node.
     *
     * @throws java.lang.IllegalArgumentException if {@code expression} is {@code null}
     * @param expression a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public void setExpression(AstNode expression) {
        assertNotNull(expression);
        this.expression = expression;
        expression.setParent(this);
    }

    /**
     * Returns whether this is part of an xml attribute value
     *
     * @return a boolean.
     */
    public boolean isXmlAttribute() {
      return isXmlAttribute;
    }

    /**
     * Sets whether this is part of an xml attribute value
     *
     * @param isXmlAttribute a boolean.
     */
    public void setIsXmlAttribute(boolean isXmlAttribute) {
      this.isXmlAttribute = isXmlAttribute;
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        return makeIndent(depth) + "{" + expression.toSource(depth) + "}";
    }

    /**
     * {@inheritDoc}
     *
     * Visits this node, then the child expression.
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            expression.visit(v);
        }
    }
}
