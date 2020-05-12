/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

/**
 * AST node for an XML-text-only component of an XML literal expression.  This
 * node differs from a {@link org.mozilla.javascript.ast.StringLiteral} in that it does not have quotes for
 * delimiters.
 *
 * @author utente
 * @version $Id: $Id
 */
public class XmlString extends XmlFragment {

    private String xml;

    /**
     * <p>Constructor for XmlString.</p>
     */
    public XmlString() {
    }

    /**
     * <p>Constructor for XmlString.</p>
     *
     * @param pos a int.
     */
    public XmlString(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for XmlString.</p>
     *
     * @param pos a int.
     * @param s a {@link java.lang.String} object.
     */
    public XmlString(int pos, String s) {
        super(pos);
        setXml(s);
    }

    /**
     * Sets the string for this XML component.  Sets the length of the
     * component to the length of the passed string.
     *
     * @param s a string of xml text
     * @throws java.lang.IllegalArgumentException} if {@code s} is {@code null}
     */
    public void setXml(String s) {
        assertNotNull(s);
        xml = s;
        setLength(s.length());
    }

    /**
     * Returns the xml string for this component.
     * Note that it may not be well-formed XML; it is a fragment.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getXml() {
        return xml;
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        return makeIndent(depth) + xml;
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
