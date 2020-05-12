/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Node;
import org.mozilla.javascript.Token;

/**
 * A block statement delimited by curly braces.  The node position is the
 * position of the open-curly, and the length extends to the position of
 * the close-curly.  Node type is {@link org.mozilla.javascript.Token#BLOCK}.
 *
 * <pre><i>Block</i> :
 *     <b>{</b> Statement* <b>}</b></pre>
 *
 * @author utente
 * @version $Id: $Id
 */
public class Block extends AstNode {

    {
        this.type = Token.BLOCK;
    }

    /**
     * <p>Constructor for Block.</p>
     */
    public Block() {
    }

    /**
     * <p>Constructor for Block.</p>
     *
     * @param pos a int.
     */
    public Block(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for Block.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public Block(int pos, int len) {
        super(pos, len);
    }

    /**
     * Alias for {@link #addChild}.
     *
     * @param statement a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public void addStatement(AstNode statement) {
        addChild(statement);
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        sb.append("{\n");
        for (Node kid : this) {
            AstNode astNodeKid = (AstNode)kid;
            sb.append(astNodeKid.toSource(depth+1));
            if(astNodeKid.getType() == Token.COMMENT) {
                sb.append("\n");
            }
        }
        sb.append(makeIndent(depth));
        sb.append("}");
        if(this.getInlineComment() != null) {
            sb.append(this.getInlineComment().toSource(depth));
        }
        sb.append("\n");
        return sb.toString();
    }

    /** {@inheritDoc} */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            for (Node kid : this) {
                ((AstNode)kid).visit(v);
            }
        }
    }
}
