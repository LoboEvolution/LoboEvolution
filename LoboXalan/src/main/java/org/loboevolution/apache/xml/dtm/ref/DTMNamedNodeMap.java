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

import org.loboevolution.apache.xml.dtm.DTM;
import com.gargoylesoftware.css.dom.DOMException;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

/**
 * DTMNamedNodeMap is a quickie (as opposed to quick) implementation of the DOM's NamedNodeMap
 * interface, intended to support DTMProxy's getAttributes() call.
 *
 * <p>***** Note: this does _not_ current attempt to cache any of the data; if you ask for attribute
 * 27 and then 28, you'll have to rescan the first 27. It should probably at least keep track of the
 * last one retrieved, and possibly buffer the whole array.
 *
 * <p>***** Also note that there's no fastpath for the by-name query; we search linearly until we
 * find it or fail to find it. Again, that could be optimized at some cost in object
 * creation/storage.
 */
public class DTMNamedNodeMap implements NamedNodeMap {

  /** The DTM for this node. */
  final DTM dtm;

  /** The DTM element handle. */
  final int element;

  /** The number of nodes in this map. */
  short m_count = -1;

  /**
   * Create a getAttributes NamedNodeMap for a given DTM element node
   *
   * @param dtm The DTM Reference, must be non-null.
   * @param element The DTM element handle.
   */
  public DTMNamedNodeMap(DTM dtm, int element) {
    this.dtm = dtm;
    this.element = element;
  }

  /** {@inheritDoc} */
  @Override
  public int getLength() {

    if (m_count == -1) {
      short count = 0;

      for (int n = dtm.getFirstAttribute(element); n != -1; n = dtm.getNextAttribute(n)) {
        ++count;
      }

      m_count = count;
    }

    return m_count;
  }

  /** {@inheritDoc} */
  @Override
  public Attr getNamedItem(String name) {

    for (int n = dtm.getFirstAttribute(element); n != DTM.NULL; n = dtm.getNextAttribute(n)) {
      if (dtm.getNodeName(n).equals(name)) return (Attr) dtm.getNode(n);
    }

    return null;
  }

  /** {@inheritDoc} */
  @Override
  public Attr item(int i) {

    int count = 0;

    for (int n = dtm.getFirstAttribute(element); n != -1; n = dtm.getNextAttribute(n)) {
      if (count == i) return (Attr) dtm.getNode(n);
      else ++count;
    }

    return null;
  }

  /** {@inheritDoc} */
  @Override
  public Attr removeNamedItem(String name) {
    throw new DTMException(DTMException.NO_MODIFICATION_ALLOWED_ERR);
  }

  /** {@inheritDoc} */
  @Override
  public Attr getNamedItemNS(String namespaceURI, String localName) {
    Node retNode = null;
    for (int n = dtm.getFirstAttribute(element); n != DTM.NULL; n = dtm.getNextAttribute(n)) {
      if (localName.equals(dtm.getLocalName(n))) {
        String nsURI = dtm.getNamespaceURI(n);
        if ((namespaceURI == null && nsURI == null)
            || (namespaceURI != null && namespaceURI.equals(nsURI))) {
          retNode = dtm.getNode(n);
          break;
        }
      }
    }
    return (Attr) retNode;
  }

  /** {@inheritDoc} */
  @Override
  public Node setNamedItemNS(Node arg) throws DOMException {
    throw new DTMException(DTMException.NO_MODIFICATION_ALLOWED_ERR);
  }

  /** {@inheritDoc} */
  @Override
  public Attr removeNamedItemNS(String namespaceURI, String localName) throws DOMException {
    throw new DTMException(DTMException.NO_MODIFICATION_ALLOWED_ERR);
  }

  @Override
  public Attr setNamedItem(Node attr) {
    throw new DTMException(DTMException.NO_MODIFICATION_ALLOWED_ERR);
  }

  /** Simple implementation of DOMException. */
  public static class DTMException extends DOMException {

    /**
     * Constructor DTMException
     *
     * @param code the code
     */
    public DTMException(short code) {
      super(code, "");
    }
  }
}
