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

import org.loboevolution.apache.xml.dtm.DTM;
import org.htmlunit.cssparser.dom.DOMException;
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
