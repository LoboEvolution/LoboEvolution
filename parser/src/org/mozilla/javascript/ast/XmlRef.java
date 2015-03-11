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
 *
 * <ol>
 *  <li>optional: an {@code @}  (specifying an attribute access)</li>
 *  <li>optional: a namespace (a {@code Name}) and double-colon</li>
 *  <li>required:  either a {@code Name} or a bracketed [expression]</li>
 * </ol>
 *
 * The property-name expressions (examples:  {@code ns::name}, {@code @name})
 * are represented as {@link XmlPropRef} nodes.  The bracketed-expression
 * versions (examples:  {@code ns::[name]}, {@code @[name]}) become
 * {@link XmlElemRef} nodes.<p>
 *
 * This node type (or more specifically, its subclasses) will
 * sometimes be the right-hand child of a {@link PropertyGet} node or
 * an {@link XmlMemberGet} node.  The {@code XmlRef} node may also
 * be a standalone primary expression with no explicit target, which
 * is valid in certain expression contexts such as
 * {@code company..employee.(@id &lt; 100)} - in this case, the {@code @id}
 * is an {@code XmlRef} that is part of an infix '&lt;' expression
 * whose parent is an {@code XmlDotQuery} node.
 */
public abstract class XmlRef extends AstNode {

    /** The namespace. */
    protected Name namespace;
    
    /** The at pos. */
    protected int atPos = -1;
    
    /** The colon pos. */
    protected int colonPos = -1;

    /**
     * Instantiates a new xml ref.
     */
    public XmlRef() {
    }

    /**
     * Instantiates a new xml ref.
     *
     * @param pos the pos
     */
    public XmlRef(int pos) {
        super(pos);
    }

    /**
     * Instantiates a new xml ref.
     *
     * @param pos the pos
     * @param len the len
     */
    public XmlRef(int pos, int len) {
        super(pos, len);
    }

    /**
     * Return the namespace.  May be {@code @null}.
     *
     * @return the namespace
     */
    public Name getNamespace() {
        return namespace;
    }

    /**
     * Sets namespace, and sets its parent to this node.
     * Can be {@code null}.
     *
     * @param namespace the new namespace
     */
    public void setNamespace(Name namespace) {
        this.namespace = namespace;
        if (namespace != null)
            namespace.setParent(this);
    }

    /**
     * Returns {@code true} if this expression began with an {@code @}-token.
     *
     * @return true, if is attribute access
     */
    public boolean isAttributeAccess() {
        return atPos >= 0;
    }

    /**
     * Returns position of {@code @}-token, or -1 if this is not
     * an attribute-access expression.
     *
     * @return the at pos
     */
    public int getAtPos() {
        return atPos;
    }

    /**
     * Sets position of {@code @}-token, or -1.
     *
     * @param atPos the new at pos
     */
    public void setAtPos(int atPos) {
        this.atPos = atPos;
    }

    /**
     * Returns position of {@code ::} token, or -1 if not present.
     * It will only be present if the namespace node is non-{@code null}.
     *
     * @return the colon pos
     */
    public int getColonPos() {
        return colonPos;
    }

    /**
     * Sets position of {@code ::} token, or -1 if not present.
     *
     * @param colonPos the new colon pos
     */
    public void setColonPos(int colonPos) {
        this.colonPos = colonPos;
    }
}
