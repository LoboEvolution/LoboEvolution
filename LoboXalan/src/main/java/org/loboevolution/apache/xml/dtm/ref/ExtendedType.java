/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

/** The class ExtendedType represents an extended type object used by ExpandedNameTable. */
public final class ExtendedType {
  private int nodetype;
  private String namespace;
  private String localName;
  private int hash;

  /**
   * Create an ExtendedType object from node type, namespace and local name. The hash code is
   * calculated from the node type, namespace and local name.
   *
   * @param nodetype Type of the node
   * @param namespace Namespace of the node
   * @param localName Local name of the node
   */
  public ExtendedType(int nodetype, String namespace, String localName) {
    this.nodetype = nodetype;
    this.namespace = namespace;
    this.localName = localName;
    this.hash = nodetype + namespace.hashCode() + localName.hashCode();
  }

  /**
   * Create an ExtendedType object from node type, namespace, local name and a given hash code.
   *
   * @param nodetype Type of the node
   * @param namespace Namespace of the node
   * @param localName Local name of the node
   * @param hash The given hash code
   */
  public ExtendedType(int nodetype, String namespace, String localName, int hash) {
    this.nodetype = nodetype;
    this.namespace = namespace;
    this.localName = localName;
    this.hash = hash;
  }

  /**
   * Redefine this ExtendedType object to represent a different extended type. This is intended to
   * be used ONLY on the hashET object. Using it elsewhere will mess up existing hashtable entries!
   */
  void redefine(int nodetype, String namespace, String localName, int hash) {
    this.nodetype = nodetype;
    this.namespace = namespace;
    this.localName = localName;
    this.hash = hash;
  }

  /** {@inheritDoc} */
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
  public boolean equals(ExtendedType other) {
    try {
      return other.nodetype == this.nodetype
          && other.localName.equals(this.localName)
          && other.namespace.equals(this.namespace);
    } catch (NullPointerException e) {
      return false;
    }
  }

  /** @return the node type */
  public int getNodeType() {
    return nodetype;
  }

  /** @return the local name */
  public String getLocalName() {
    return localName;
  }

  /** @return the namespace */
  public String getNamespace() {
    return namespace;
  }
}
