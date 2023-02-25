/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
