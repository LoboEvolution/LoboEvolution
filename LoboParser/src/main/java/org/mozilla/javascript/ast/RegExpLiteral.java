/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

/**
 * AST node for a RegExp literal.
 * Node type is {@link org.mozilla.javascript.Token#REGEXP}.
 *
 *
 *
 */
public class RegExpLiteral extends AstNode {

    private String value;
    private String flags;

    {
        type = Token.REGEXP;
    }

    /**
     * <p>Constructor for RegExpLiteral.</p>
     */
    public RegExpLiteral() {
    }

    /**
     * <p>Constructor for RegExpLiteral.</p>
     *
     * @param pos a int.
     */
    public RegExpLiteral(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for RegExpLiteral.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public RegExpLiteral(int pos, int len) {
        super(pos, len);
    }

    /**
     * Returns the regexp string without delimiters
     *
     * @return a {@link java.lang.String} object.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the regexp string without delimiters
     *
     * @throws java.lang.IllegalArgumentException} if value is {@code null}
     * @param value a {@link java.lang.String} object.
     */
    public void setValue(String value) {
        assertNotNull(value);
        this.value = value;
    }

    /**
     * Returns regexp flags, {@code null} or "" if no flags specified
     *
     * @return a {@link java.lang.String} object.
     */
    public String getFlags() {
        return flags;
    }

    /**
     * Sets regexp flags.  Can be {@code null} or "".
     *
     * @param flags a {@link java.lang.String} object.
     */
    public void setFlags(String flags) {
        this.flags = flags;
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        return makeIndent(depth) + "/" + value + "/"
                + (flags == null ? "" : flags);
    }

    /**
     * {@inheritDoc}
     *
     * Visits this node.  There are no children to visit.
     */
    @Override
    public void visit(NodeVisitor v) {
        v.visit(this);
    }
}
