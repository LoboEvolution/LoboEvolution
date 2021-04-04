/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

/**
 * AST node representing an E4X {@code foo.(bar)} query expression.
 * The node type (operator) is {@link org.mozilla.javascript.Token#DOTQUERY}.
 * Its {@code getLeft} node is the target ("foo" in the example),
 * and the {@code getRight} node is the filter expression node.<p>
 *
 * This class exists separately from {@link org.mozilla.javascript.ast.InfixExpression} largely because it
 * has different printing needs.  The position of the left paren is just after
 * the dot (operator) position, and the right paren is the final position in the
 * bounds of the node.  If the right paren is missing, the node ends at the end
 * of the filter expression.
 *
 *
 *
 */
public class XmlDotQuery extends InfixExpression {

    private int rp = -1;

    {
        type = Token.DOTQUERY;
    }

    /**
     * <p>Constructor for XmlDotQuery.</p>
     */
    public XmlDotQuery() {
    }

    /**
     * <p>Constructor for XmlDotQuery.</p>
     *
     * @param pos a int.
     */
    public XmlDotQuery(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for XmlDotQuery.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public XmlDotQuery(int pos, int len) {
        super(pos, len);
    }

    /**
     * Returns right-paren position, -1 if missing.<p>
     *
     * Note that the left-paren is automatically the character
     * immediately after the "." in the operator - no whitespace is
     * permitted between the dot and lp by the scanner.
     *
     * @return a int.
     */
    public int getRp() {
        return rp;
    }

    /**
     * Sets right-paren position
     *
     * @param rp a int.
     */
    public void setRp(int rp) {
        this.rp = rp;
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        sb.append(getLeft().toSource(0));
        sb.append(".(");
        sb.append(getRight().toSource(0));
        sb.append(")");
        return sb.toString();
    }
}
