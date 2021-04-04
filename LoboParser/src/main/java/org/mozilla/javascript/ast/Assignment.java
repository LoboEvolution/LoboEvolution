/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

/**
 * AST node representing the set of assignment operators such as {@code =},
 * {@code *=} and {@code +=}.
 *
 *
 *
 */
public class Assignment extends InfixExpression {

    /**
     * <p>Constructor for Assignment.</p>
     */
    public Assignment() {
    }

    /**
     * <p>Constructor for Assignment.</p>
     *
     * @param pos a int.
     */
    public Assignment(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for Assignment.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public Assignment(int pos, int len) {
        super(pos, len);
    }

    /**
     * <p>Constructor for Assignment.</p>
     *
     * @param pos a int.
     * @param len a int.
     * @param left a {@link org.mozilla.javascript.ast.AstNode} object.
     * @param right a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public Assignment(int pos, int len, AstNode left, AstNode right) {
        super(pos, len, left, right);
    }

    /**
     * <p>Constructor for Assignment.</p>
     *
     * @param left a {@link org.mozilla.javascript.ast.AstNode} object.
     * @param right a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public Assignment(AstNode left, AstNode right) {
        super(left, right);
    }

    /**
     * <p>Constructor for Assignment.</p>
     *
     * @param operator a int.
     * @param left a {@link org.mozilla.javascript.ast.AstNode} object.
     * @param right a {@link org.mozilla.javascript.ast.AstNode} object.
     * @param operatorPos a int.
     */
    public Assignment(int operator, AstNode left,
                      AstNode right, int operatorPos) {
        super(operator, left, right, operatorPos);
    }
}
