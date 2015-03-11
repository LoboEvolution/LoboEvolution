/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;


/**
 * AST node for a RegExp literal.
 * Node type is {@link Token#REGEXP}.
 */
public class RegExpLiteral extends AstNode {

    /** The value. */
    private String value;
    
    /** The flags. */
    private String flags;

    {
        type = Token.REGEXP;
    }

    /**
     * Instantiates a new reg exp literal.
     */
    public RegExpLiteral() {
    }

    /**
     * Instantiates a new reg exp literal.
     *
     * @param pos the pos
     */
    public RegExpLiteral(int pos) {
        super(pos);
    }

    /**
     * Instantiates a new reg exp literal.
     *
     * @param pos the pos
     * @param len the len
     */
    public RegExpLiteral(int pos, int len) {
        super(pos, len);
    }

    /**
     * Returns the regexp string without delimiters.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the regexp string without delimiters.
     *
     * @param value the new value
     */
    public void setValue(String value) {
        assertNotNull(value);
        this.value = value;
    }

    /**
     * Returns regexp flags, {@code null} or "" if no flags specified.
     *
     * @return the flags
     */
    public String getFlags() {
        return flags;
    }

    /**
     * Sets regexp flags.  Can be {@code null} or "".
     *
     * @param flags the new flags
     */
    public void setFlags(String flags) {
        this.flags = flags;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.AstNode#toSource(int)
     */
    @Override
    public String toSource(int depth) {
        return makeIndent(depth) + "/" + value + "/"
                + (flags == null ? "" : flags);
    }

    /**
     * Visits this node.  There are no children to visit.
     *
     * @param v the v
     */
    @Override
    public void visit(NodeVisitor v) {
        v.visit(this);
    }
}
