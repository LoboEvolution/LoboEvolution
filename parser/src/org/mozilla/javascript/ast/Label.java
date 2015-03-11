/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;


/**
 * AST node representing a label.  It is a distinct node type so it can
 * record its length and position for code-processing tools.
 * Node type is {@link Token#LABEL}.
 */
public class Label extends Jump {

    /** The name. */
    private String name;

    {
        type = Token.LABEL;
    }

    /**
     * Instantiates a new label.
     */
    public Label() {
    }

    /**
     * Instantiates a new label.
     *
     * @param pos the pos
     */
    public Label(int pos) {
        this(pos, -1);
    }

    /**
     * Instantiates a new label.
     *
     * @param pos the pos
     * @param len the len
     */
    public Label(int pos, int len) {
        // can't call super (Jump) for historical reasons
        position = pos;
        length = len;
    }

    /**
     * Instantiates a new label.
     *
     * @param pos the pos
     * @param len the len
     * @param name the name
     */
    public Label(int pos, int len, String name) {
        this(pos, len);
        setName(name);
    }

    /**
     * Returns the label text.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the label text.
     *
     * @param name the new name
     */
    public void setName(String name) {
        name = name == null ? null : name.trim();
        if (name == null || "".equals(name))
            throw new IllegalArgumentException("invalid label name");
        this.name = name;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.Jump#toSource(int)
     */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        sb.append(name);
        sb.append(":\n");
        return sb.toString();
    }

    /**
     * Visits this label.  There are no children to visit.
     *
     * @param v the v
     */
    @Override
    public void visit(NodeVisitor v) {
        v.visit(this);
    }
}
