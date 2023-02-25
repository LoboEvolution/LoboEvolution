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
import org.loboevolution.apache.xml.dtm.DTMIterator;
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
public class DTMNodeList extends DTMNodeListBase {
  private DTMIterator m_iter;

  /**
   * Public constructor: Wrap a DTMNodeList around an existing and preconfigured DTMIterator
   *
   * <p>WARNING: THIS HAS THE SIDE EFFECT OF ISSUING setShouldCacheNodes(true) AGAINST THE
   * DTMIterator.
   *
   * @param dtmIterator the iterator to get the nodes from
   */
  public DTMNodeList(DTMIterator dtmIterator) {
    if (dtmIterator != null) {
      int pos = dtmIterator.getCurrentPos();
      try {
        m_iter = dtmIterator.cloneWithReset();
      } catch (CloneNotSupportedException cnse) {
        m_iter = dtmIterator;
      }
      m_iter.setShouldCacheNodes(true);
      m_iter.runTo(-1);
      m_iter.setCurrentPos(pos);
    }
  }

  /**
   * Access the wrapped DTMIterator. I'm not sure whether anyone will need this or not, but let's
   * write it and think about it.
   *
   * @return the wrapped DTMIterator
   */
  public DTMIterator getDTMIterator() {
    return m_iter;
  }

  /** {@inheritDoc} */
  @Override
  public Node item(int index) {
    if (m_iter != null) {
      int handle = m_iter.item(index);
      if (handle == DTM.NULL) {
        return null;
      }
      return m_iter.getDTM(handle).getNode(handle);
    } else {
      return null;
    }
  }

  /** {@inheritDoc} */
  @Override
  public int getLength() {
    return (m_iter != null) ? m_iter.getLength() : 0;
  }
}
