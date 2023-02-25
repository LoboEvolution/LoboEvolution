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
import org.loboevolution.html.node.Node;

/**
 * <code>DTMNodeList</code> gives us an implementation of the DOM's NodeList interface wrapped
 * around a DTM Iterator. The author considers this something of an abominations, since NodeList was
 * not intended to be a general purpose "list of nodes" API and is generally considered by the DOM
 * WG to have be a mistake... but I'm told that some of the XPath/XSLT folks say they must have this
 * solution.
 *
 * <p>Please note that this is not necessarily equivlaent to a DOM NodeList operating over the same
 * document. In particular:
 *
 * <ul>
 *   <li>If there are several Text nodes in logical succession (ie, across CDATASection and
 *       EntityReference boundaries), we will return only the first; the caller is responsible for
 *       stepping through them. (%REVIEW% Provide a convenience routine here to assist, pending
 *       proposed DOM Level 3 getAdjacentText() operation?)
 *   <li>Since the whole XPath/XSLT architecture assumes that the source document is not altered
 *       while we're working with it, we do not promise to implement the DOM NodeList's "live view"
 *       response to document mutation.
 * </ul>
 *
 * <p>State: In progress!!
 */
public class DTMChildIterNodeList extends DTMNodeListBase {
  private final int m_firstChild;
  private final DTM m_parentDTM;

  // ================================================================
  // Methods unique to this class
  /**
   * Public constructor: Create a NodeList to support DTMNodeProxy.getChildren().
   *
   * <p>Unfortunately AxisIterators and DTMIterators don't share an API, so I can't use the existing
   * Axis.CHILD iterator. Rather than create Yet Another Class, let's set up a special case of this
   * one.
   *
   * @param parentDTM The DTM containing this node
   * @param parentHandle DTM node-handle integer
   */
  public DTMChildIterNodeList(DTM parentDTM, int parentHandle) {
    m_parentDTM = parentDTM;
    m_firstChild = parentDTM.getFirstChild(parentHandle);
  }

  /** {@inheritDoc} */
  @Override
  public Node item(int index) {
    int handle = m_firstChild;
    while (--index >= 0 && handle != DTM.NULL) {
      handle = m_parentDTM.getNextSibling(handle);
    }
    if (handle == DTM.NULL) {
      return null;
    }
    return m_parentDTM.getNode(handle);
  }

  /** {@inheritDoc} */
  @Override
  public int getLength() {
    int count = 0;
    for (int handle = m_firstChild;
        handle != DTM.NULL;
        handle = m_parentDTM.getNextSibling(handle)) {
      ++count;
    }
    return count;
  }
}
