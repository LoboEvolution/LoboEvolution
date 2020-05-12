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
 * A labeled statement.  A statement can have more than one label.  In
 * this AST representation, all labels for a statement are collapsed into
 * the "labels" list of a single {@link org.mozilla.javascript.ast.LabeledStatement} node.
 *
 * <p>Node type is {@link org.mozilla.javascript.Token#EXPR_VOID}.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class LabeledStatement extends AstNode {

    private List<Label> labels = new ArrayList<Label>();  // always at least 1
    private AstNode statement;

    {
        type = Token.EXPR_VOID;
    }

    /**
     * <p>Constructor for LabeledStatement.</p>
     */
    public LabeledStatement() {
    }

    /**
     * <p>Constructor for LabeledStatement.</p>
     *
     * @param pos a int.
     */
    public LabeledStatement(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for LabeledStatement.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public LabeledStatement(int pos, int len) {
        super(pos, len);
    }

    /**
     * Returns label list
     *
     * @return a {@link java.util.List} object.
     */
    public List<Label> getLabels() {
        return labels;
    }

    /**
     * Sets label list, setting the parent of each label
     * in the list.  Replaces any existing labels.
     *
     * @throws java.lang.IllegalArgumentException} if labels is {@code null}
     * @param labels a {@link java.util.List} object.
     */
    public void setLabels(List<Label> labels) {
        assertNotNull(labels);
        if (this.labels != null)
            this.labels.clear();
        for (Label l : labels) {
            addLabel(l);
        }
    }

    /**
     * Adds a label and sets its parent to this node.
     *
     * @throws java.lang.IllegalArgumentException} if label is {@code null}
     * @param label a {@link org.mozilla.javascript.ast.Label} object.
     */
    public void addLabel(Label label) {
        assertNotNull(label);
        labels.add(label);
        label.setParent(this);
    }

    /**
     * Returns the labeled statement
     *
     * @return a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public AstNode getStatement() {
        return statement;
    }

    /**
     * Returns label with specified name from the label list for
     * this labeled statement.  Returns {@code null} if there is no
     * label with that name in the list.
     *
     * @param name a {@link java.lang.String} object.
     * @return a {@link org.mozilla.javascript.ast.Label} object.
     */
    public Label getLabelByName(String name) {
        for (Label label : labels) {
            if (name.equals(label.getName())) {
                return label;
            }
        }
        return null;
    }

    /**
     * Sets the labeled statement, and sets its parent to this node.
     *
     * @throws java.lang.IllegalArgumentException if {@code statement} is {@code null}
     * @param statement a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public void setStatement(AstNode statement) {
        assertNotNull(statement);
        this.statement = statement;
        statement.setParent(this);
    }

    /**
     * <p>getFirstLabel.</p>
     *
     * @return a {@link org.mozilla.javascript.ast.Label} object.
     */
    public Label getFirstLabel() {
        return labels.get(0);
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasSideEffects() {
        // just to avoid the default case for EXPR_VOID in AstNode
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        for (Label label : labels) {
            sb.append(label.toSource(depth));  // prints newline
        }
        sb.append(statement.toSource(depth + 1));
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     *
     * Visits this node, then each label in the label-list, and finally the
     * statement.
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            for (AstNode label : labels) {
                label.visit(v);
            }
            statement.visit(v);
        }
    }
}
