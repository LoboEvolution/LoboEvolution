/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

/**
 * Base class for E4X XML attribute-access or property-get expressions.
 * Such expressions can take a variety of forms. The general syntax has
 * three parts:
 * <ol>
 *  <li>optional: an {@code @} (specifying an attribute access)</li>
 *  <li>optional: a namespace (a {@code Name}) and double-colon</li>
 *  <li>required:  either a {@code Name} or a bracketed [expression]</li>
 * </ol>
 *
 * The property-name expressions (examples:  {@code ns::name}, {@code @name})
 * are represented as {@link org.mozilla.javascript.ast.XmlPropRef} nodes.  The bracketed-expression
 * versions (examples:  {@code ns::[name]}, {@code @[name]}) become
 * {@link org.mozilla.javascript.ast.XmlElemRef} nodes.<p>
 *
 * This node type (or more specifically, its subclasses) will
 * sometimes be the right-hand child of a {@link org.mozilla.javascript.ast.PropertyGet} node or
 * an {@link org.mozilla.javascript.ast.XmlMemberGet} node.  The {@code XmlRef} node may also
 * be a standalone primary expression with no explicit target, which
 * is valid in certain expression contexts such as
 * {@code company..employee.(@id &lt; 100)} - in this case, the {@code @id}
 * is an {@code XmlRef} that is part of an infix '&lt;' expression
 * whose parent is an {@code XmlDotQuery} node.
 *
 * @author utente
 * @version $Id: $Id
 */
public abstract class XmlRef extends AstNode {

    protected Name namespace;
    protected int atPos = -1;
    protected int colonPos = -1;

    /**
     * <p>Constructor for XmlRef.</p>
     */
    public XmlRef() {
    }

    /**
     * <p>Constructor for XmlRef.</p>
     *
     * @param pos a int.
     */
    public XmlRef(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for XmlRef.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public XmlRef(int pos, int len) {
        super(pos, len);
    }

    /**
     * Return the namespace.  May be {@code @null}.
     *
     * @return a {@link org.mozilla.javascript.ast.Name} object.
     */
    public Name getNamespace() {
        return namespace;
    }

    /**
     * Sets namespace, and sets its parent to this node.
     * Can be {@code null}.
     *
     * @param namespace a {@link org.mozilla.javascript.ast.Name} object.
     */
    public void setNamespace(Name namespace) {
        this.namespace = namespace;
        if (namespace != null)
            namespace.setParent(this);
    }

    /**
     * Returns {@code true} if this expression began with an {@code @}-token.
     *
     * @return a boolean.
     */
    public boolean isAttributeAccess() {
        return atPos >= 0;
    }

    /**
     * Returns position of {@code @}-token, or -1 if this is not
     * an attribute-access expression.
     *
     * @return a int.
     */
    public int getAtPos() {
        return atPos;
    }

    /**
     * Sets position of {@code @}-token, or -1
     *
     * @param atPos a int.
     */
    public void setAtPos(int atPos) {
        this.atPos = atPos;
    }

    /**
     * Returns position of {@code ::} token, or -1 if not present.
     * It will only be present if the namespace node is non-{@code null}.
     *
     * @return a int.
     */
    public int getColonPos() {
        return colonPos;
    }

    /**
     * Sets position of {@code ::} token, or -1 if not present
     *
     * @param colonPos a int.
     */
    public void setColonPos(int colonPos) {
        this.colonPos = colonPos;
    }
}
