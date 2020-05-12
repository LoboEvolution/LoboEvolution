/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import java.util.ArrayList;
import java.util.List;

import org.mozilla.javascript.Token;

/**
 * A list of one or more var, const or let declarations.
 * Node type is {@link org.mozilla.javascript.Token#VAR}, {@link org.mozilla.javascript.Token#CONST} or
 * {@link org.mozilla.javascript.Token#LET}.<p>
 *
 * If the node is for {@code var} or {@code const}, the node position
 * is the beginning of the {@code var} or {@code const} keyword.
 * For {@code let} declarations, the node position coincides with the
 * first {@link org.mozilla.javascript.ast.VariableInitializer} child.<p>
 *
 * A standalone variable declaration in a statement context returns {@code true}
 * from its {@link #isStatement()} method.
 *
 * @author utente
 * @version $Id: $Id
 */
public class VariableDeclaration extends AstNode {

    private List<VariableInitializer> variables = new ArrayList<VariableInitializer>();
    private boolean isStatement;

    {
        type = Token.VAR;
    }

    /**
     * <p>Constructor for VariableDeclaration.</p>
     */
    public VariableDeclaration() {
    }

    /**
     * <p>Constructor for VariableDeclaration.</p>
     *
     * @param pos a int.
     */
    public VariableDeclaration(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for VariableDeclaration.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public VariableDeclaration(int pos, int len) {
        super(pos, len);
    }

    /**
     * Returns variable list.  Never {@code null}.
     *
     * @return a {@link java.util.List} object.
     */
    public List<VariableInitializer> getVariables() {
        return variables;
    }

    /**
     * Sets variable list
     *
     * @throws java.lang.IllegalArgumentException if variables list is {@code null}
     * @param variables a {@link java.util.List} object.
     */
    public void setVariables(List<VariableInitializer> variables) {
        assertNotNull(variables);
        this.variables.clear();
        for (VariableInitializer vi : variables) {
            addVariable(vi);
        }
    }

    /**
     * Adds a variable initializer node to the child list.
     * Sets initializer node's parent to this node.
     *
     * @throws java.lang.IllegalArgumentException if v is {@code null}
     * @param v a {@link org.mozilla.javascript.ast.VariableInitializer} object.
     */
    public void addVariable(VariableInitializer v) {
        assertNotNull(v);
        variables.add(v);
        v.setParent(this);
    }

    /**
     * {@inheritDoc}
     *
     * Sets the node type and returns this node.
     */
    @Override
    public org.mozilla.javascript.Node setType(int type) {
        if (type != Token.VAR
            && type != Token.CONST
            && type != Token.LET)
            throw new IllegalArgumentException("invalid decl type: " + type);
        return super.setType(type);
    }

    /**
     * Returns true if this is a {@code var} (not
     * {@code const} or {@code let}) declaration.
     *
     * @return true if {@code declType} is {@link org.mozilla.javascript.Token#VAR}
     */
    public boolean isVar() {
        return type == Token.VAR;
    }

    /**
     * Returns true if this is a {@link org.mozilla.javascript.Token#CONST} declaration.
     *
     * @return a boolean.
     */
    public boolean isConst() {
        return type == Token.CONST;
    }

    /**
     * Returns true if this is a {@link org.mozilla.javascript.Token#LET} declaration.
     *
     * @return a boolean.
     */
    public boolean isLet() {
        return type == Token.LET;
    }

    /**
     * Returns true if this node represents a statement.
     *
     * @return a boolean.
     */
    public boolean isStatement() {
        return isStatement;
    }

    /**
     * Set or unset the statement flag.
     *
     * @param isStatement a boolean.
     */
    public void setIsStatement(boolean isStatement) {
        this.isStatement = isStatement;
    }

    private String declTypeName() {
        return Token.typeToName(type).toLowerCase();
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        sb.append(declTypeName());
        sb.append(" ");
        printList(variables, sb);
        if (isStatement()) {
            sb.append(";");
        }
        if(this.getInlineComment() != null) {
            sb.append(this.getInlineComment().toSource(depth)).append("\n");
        } else if (isStatement()) {
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     *
     * Visits this node, then each {@link VariableInitializer} child.
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            for (AstNode var : variables) {
                var.visit(v);
            }
        }
    }
}
