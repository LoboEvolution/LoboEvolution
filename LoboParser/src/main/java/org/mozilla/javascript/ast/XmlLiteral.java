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
 * AST node for an E4X (Ecma-357) embedded XML literal.  Node type is
 * {@link org.mozilla.javascript.Token#XML}.  The parser generates a simple list of strings and
 * expressions.  In the future we may parse the XML and produce a richer set of
 * nodes, but for now it's just a set of expressions evaluated to produce a
 * string to pass to the {@code XML} constructor function.
 *
 *
 *
 */
public class XmlLiteral extends AstNode {

    private List<XmlFragment> fragments = new ArrayList<XmlFragment>();

    {
        type = Token.XML;
    }

    /**
     * <p>Constructor for XmlLiteral.</p>
     */
    public XmlLiteral() {
    }

    /**
     * <p>Constructor for XmlLiteral.</p>
     *
     * @param pos a int.
     */
    public XmlLiteral(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for XmlLiteral.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public XmlLiteral(int pos, int len) {
        super(pos, len);
    }

    /**
     * Returns fragment list - a list of expression nodes.
     *
     * @return a {@link java.util.List} object.
     */
    public List<XmlFragment> getFragments() {
        return fragments;
    }

    /**
     * Sets fragment list, removing any existing fragments first.
     * Sets the parent pointer for each fragment in the list to this node.
     *
     * @param fragments fragment list.  Replaces any existing fragments.
     * @throws java.lang.IllegalArgumentException} if {@code fragments} is {@code null}
     */
    public void setFragments(List<XmlFragment> fragments) {
        assertNotNull(fragments);
        this.fragments.clear();
        for (XmlFragment fragment : fragments)
            addFragment(fragment);
    }

    /**
     * Adds a fragment to the fragment list.  Sets its parent to this node.
     *
     * @throws java.lang.IllegalArgumentException} if {@code fragment} is {@code null}
     * @param fragment a {@link org.mozilla.javascript.ast.XmlFragment} object.
     */
    public void addFragment(XmlFragment fragment) {
        assertNotNull(fragment);
        fragments.add(fragment);
        fragment.setParent(this);
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder(250);
        for (XmlFragment frag : fragments) {
            sb.append(frag.toSource(0));
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     *
     * Visits this node, then visits each child fragment in lexical order.
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            for (XmlFragment frag : fragments) {
                frag.visit(v);
            }
        }
    }
}
