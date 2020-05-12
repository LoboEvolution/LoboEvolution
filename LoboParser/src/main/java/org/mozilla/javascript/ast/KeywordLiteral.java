/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

/**
 * AST node for keyword literals:  currently, {@code this},
 * {@code null}, {@code true}, {@code false}, and {@code debugger}.
 * Node type is one of
 * {@link org.mozilla.javascript.Token#THIS},
 * {@link org.mozilla.javascript.Token#NULL},
 * {@link org.mozilla.javascript.Token#TRUE},
 * {@link org.mozilla.javascript.Token#FALSE}, or
 * {@link org.mozilla.javascript.Token#DEBUGGER}.
 *
 * @author utente
 * @version $Id: $Id
 */
public class KeywordLiteral extends AstNode {

    /**
     * <p>Constructor for KeywordLiteral.</p>
     */
    public KeywordLiteral() {
    }

    /**
     * <p>Constructor for KeywordLiteral.</p>
     *
     * @param pos a int.
     */
    public KeywordLiteral(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for KeywordLiteral.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public KeywordLiteral(int pos, int len) {
        super(pos, len);
    }

    /**
     * Constructs a new KeywordLiteral
     *
     * @param nodeType the token type
     * @param pos a int.
     * @param len a int.
     */
    public KeywordLiteral(int pos, int len, int nodeType) {
        super(pos, len);
        setType(nodeType);
    }

    /**
     * {@inheritDoc}
     *
     * Sets node token type
     */
    @Override
    public KeywordLiteral setType(int nodeType) {
        if (!(nodeType == Token.THIS
              || nodeType == Token.NULL
              || nodeType == Token.TRUE
              || nodeType == Token.FALSE
              || nodeType == Token.DEBUGGER))
            throw new IllegalArgumentException("Invalid node type: "
                                               + nodeType);
        type = nodeType;
        return this;
    }

    /**
     * Returns true if the token type is {@link org.mozilla.javascript.Token#TRUE} or
     * {@link org.mozilla.javascript.Token#FALSE}.
     *
     * @return a boolean.
     */
    public boolean isBooleanLiteral() {
        return type == Token.TRUE || type == Token.FALSE;
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        switch (getType()) {
        case Token.THIS:
            sb.append("this");
            break;
        case Token.NULL:
            sb.append("null");
            break;
        case Token.TRUE:
            sb.append("true");
            break;
        case Token.FALSE:
            sb.append("false");
            break;
        case Token.DEBUGGER:
            sb.append("debugger;\n");
            break;
        default:
            throw new IllegalStateException("Invalid keyword literal type: "
                                            + getType());
        }
        return sb.toString();
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
