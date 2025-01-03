/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.apache.xml.dtm.ref;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The class ExtendedType represents an extended type object used by ExpandedNameTable.
 */
@Data
@AllArgsConstructor
public final class ExtendedType {
    private int nodeType;
    private String namespace;
    private String localName;
    private int hash;

    /**
     * Create an ExtendedType object from node type, namespace and local name. The hash code is
     * calculated from the node type, namespace and local name.
     *
     * @param nodetype  Type of the node
     * @param namespace Namespace of the node
     * @param localName Local name of the node
     */
    public ExtendedType(final int nodetype, final String namespace, final String localName) {
        this.nodeType = nodetype;
        this.namespace = namespace;
        this.localName = localName;
        this.hash = nodetype + namespace.hashCode() + localName.hashCode();
    }

    /**
     * Redefine this ExtendedType object to represent a different extended type. This is intended to
     * be used ONLY on the hashET object. Using it elsewhere will mess up existing hashtable entries!
     */
    void redefine(final int nodetype, final String namespace, final String localName, final int hash) {
        this.nodeType = nodetype;
        this.namespace = namespace;
        this.localName = localName;
        this.hash = hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return hash;
    }

    /**
     * Test if this ExtendedType object is equal to the given ExtendedType.
     *
     * @param other The other ExtendedType object to test for equality
     * @return true if the two ExtendedType objects are equal.
     */
    public boolean equals(final ExtendedType other) {
        try {
            return other.nodeType == this.nodeType
                    && other.localName.equals(this.localName)
                    && other.namespace.equals(this.namespace);
        } catch (final NullPointerException e) {
            return false;
        }
    }
}
