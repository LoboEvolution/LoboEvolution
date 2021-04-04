/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

/**
 * AST node for a single name:value entry in an Object literal.
 * For simple entries, the node type is {@link org.mozilla.javascript.Token#COLON}, and
 * the name (left side expression) is either a {@link org.mozilla.javascript.ast.Name}, a
 * {@link org.mozilla.javascript.ast.StringLiteral} or a {@link org.mozilla.javascript.ast.NumberLiteral}.
 *
 * <p>This node type is also used for getter/setter properties in object
 * literals.  In this case the node bounds include the "get" or "set"
 * keyword.  The left-hand expression in this case is always a
 * {@link org.mozilla.javascript.ast.Name}, and the overall node type is {@link org.mozilla.javascript.Token#GET} or
 * {@link org.mozilla.javascript.Token#SET}, as appropriate.</p>
 *
 * The {@code operatorPosition} field is meaningless if the node is
 * a getter or setter.
 *
 * <pre><i>ObjectProperty</i> :
 *       PropertyName <b>:</b> AssignmentExpression
 * <i>PropertyName</i> :
 *       Identifier
 *       StringLiteral
 *       NumberLiteral</pre>
 *
 *
 *
 */
public class ObjectProperty extends InfixExpression {

    {
        type = Token.COLON;
    }

    /**
     * Sets the node type.  Must be one of
     * {@link org.mozilla.javascript.Token#COLON}, {@link org.mozilla.javascript.Token#GET}, or {@link org.mozilla.javascript.Token#SET}.
     *
     * @throws java.lang.IllegalArgumentException if {@code nodeType} is invalid
     * @param nodeType a int.
     */
    public void setNodeType(int nodeType) {
        if (nodeType != Token.COLON
            && nodeType != Token.GET
            && nodeType != Token.SET
            && nodeType != Token.METHOD)
            throw new IllegalArgumentException("invalid node type: "
                                               + nodeType);
        setType(nodeType);
    }

    /**
     * <p>Constructor for ObjectProperty.</p>
     */
    public ObjectProperty() {
    }

    /**
     * <p>Constructor for ObjectProperty.</p>
     *
     * @param pos a int.
     */
    public ObjectProperty(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for ObjectProperty.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public ObjectProperty(int pos, int len) {
        super(pos, len);
    }

    /**
     * Marks this node as a "getter" property.
     */
    public void setIsGetterMethod() {
        type = Token.GET;
    }

    /**
     * Returns true if this is a getter function.
     *
     * @return a boolean.
     */
    public boolean isGetterMethod() {
        return type == Token.GET;
    }

    /**
     * Marks this node as a "setter" property.
     */
    public void setIsSetterMethod() {
        type = Token.SET;
    }

    /**
     * Returns true if this is a setter function.
     *
     * @return a boolean.
     */
    public boolean isSetterMethod() {
        return type == Token.SET;
    }

    /**
     * <p>setIsNormalMethod.</p>
     */
    public void setIsNormalMethod() {
        type = Token.METHOD;
    }

    /**
     * <p>isNormalMethod.</p>
     *
     * @return a boolean.
     */
    public boolean isNormalMethod() {
        return type == Token.METHOD;
    }

    /**
     * <p>isMethod.</p>
     *
     * @return a boolean.
     */
    public boolean isMethod() {
        return isGetterMethod() || isSetterMethod() || isNormalMethod();
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(makeIndent(depth+1));
        if (isGetterMethod()) {
            sb.append("get ");
        } else if (isSetterMethod()) {
            sb.append("set ");
        }
        sb.append(left.toSource(getType()==Token.COLON ? 0 : depth));
        if (type == Token.COLON) {
            sb.append(": ");
        }
        sb.append(right.toSource(getType()==Token.COLON ? 0 : depth+1));
        return sb.toString();
    }
}
