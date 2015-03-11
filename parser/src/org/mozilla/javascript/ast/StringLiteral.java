/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Token;


/**
 * AST node for a single- or double-quoted string literal.
 * Node type is {@link Token#STRING}.
 */
public class StringLiteral extends AstNode {

    /** The value. */
    private String value;
    
    /** The quote char. */
    private char quoteChar;

    {
        type = Token.STRING;
    }

    /**
     * Instantiates a new string literal.
     */
    public StringLiteral() {
    }

    /**
     * Instantiates a new string literal.
     *
     * @param pos the pos
     */
    public StringLiteral(int pos) {
        super(pos);
    }

    /**
     * Creates a string literal node at the specified position.
     *
     * @param pos the pos
     * @param len the length <em>including</em> the enclosing quotes
     */
    public StringLiteral(int pos, int len) {
        super(pos, len);
    }

    /**
     * Returns the node's value:  the parsed string without the enclosing quotes.
     *
     * @return the node's value, a {@link String} of unescaped characters
     * that includes the delimiter quotes.
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns the string value, optionally including the enclosing quotes.
     *
     * @param includeQuotes the include quotes
     * @return the value
     */
    public String getValue(boolean includeQuotes) {
        if (!includeQuotes)
            return value;
        return quoteChar + value + quoteChar;
    }

    /**
     * Sets the node's value.  Do not include the enclosing quotes.
     *
     * @param value the node's value
     */
    public void setValue(String value) {
        assertNotNull(value);
        this.value = value;
    }

    /**
     * Returns the character used as the delimiter for this string.
     *
     * @return the quote character
     */
    public char getQuoteCharacter() {
        return quoteChar;
    }

    /**
     * Sets the quote character.
     *
     * @param c the new quote character
     */
    public void setQuoteCharacter(char c) {
        quoteChar = c;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.AstNode#toSource(int)
     */
    @Override
    public String toSource(int depth) {
        return new StringBuilder(makeIndent(depth))
                .append(quoteChar)
                .append(ScriptRuntime.escapeString(value, quoteChar))
                .append(quoteChar)
                .toString();
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
