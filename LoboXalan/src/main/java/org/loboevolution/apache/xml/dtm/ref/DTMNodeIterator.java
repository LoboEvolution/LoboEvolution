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

import org.loboevolution.apache.xml.utils.WrappedRuntimeException;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMDOMException;
import org.loboevolution.apache.xml.dtm.DTMIterator;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.traversal.NodeFilter;
import org.loboevolution.html.node.traversal.NodeIterator;

/**
 * <code>DTMNodeIterator</code> gives us an implementation of the DTMNodeIterator which returns DOM
 * nodes.
 *
 * <p>Please note that this is not necessarily equivlaent to a DOM NodeIterator operating over the
 * same document. In particular:
 *
 * <ul>
 *   <li>If there are several Text nodes in logical succession (ie, across CDATASection and
 *       EntityReference boundaries), we will return only the first; the caller is responsible for
 *       stepping through them. (%REVIEW% Provide a convenience routine here to assist, pending
 *       proposed DOM Level 3 getAdjacentText() operation?)
 *   <li>Since the whole XPath/XSLT architecture assumes that the source document is not altered
 *       while we're working with it, we do not promise to implement the DOM NodeIterator's
 *       "maintain current position" response to document mutation.
 *   <li>Since our design for XPath NodeIterators builds a stateful filter directly into the
 *       traversal object, getNodeFilter() is not supported.
 * </ul>
 *
 * <p>State: In progress!!
 */
public class DTMNodeIterator implements NodeIterator {
  private final DTMIterator dtm_iter;
  private boolean valid = true;

  // ================================================================
  // Methods unique to this class

  /**
   * Public constructor: Wrap a DTMNodeIterator around an existing and preconfigured DTMIterator
   *
   * @param dtmIterator the iterator to be cloned
   */
  public DTMNodeIterator(DTMIterator dtmIterator) {
    try {
      dtm_iter = (DTMIterator) dtmIterator.clone();
    } catch (CloneNotSupportedException cnse) {
      throw new WrappedRuntimeException(cnse);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void detach() {
    // Theoretically, we could release dtm_iter at this point. But
    // some of the operations may still want to consult it even though
    // navigation is now invalid.
    valid = false;
  }

  /** {@inheritDoc} */
  @Override
  public NodeFilter getFilter() {
    throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
  }

  /** {@inheritDoc} */
  @Override
  public Node getRoot() {
    int handle = dtm_iter.getRoot();
    return dtm_iter.getDTM(handle).getNode(handle);
  }

  /** {@inheritDoc} */
  @Override
  public int getWhatToShow() {
    return dtm_iter.getWhatToShow();
  }

  /** {@inheritDoc} */
  @Override
  public Node nextNode() throws DOMException {
    if (!valid) throw new DTMDOMException(DOMException.INVALID_STATE_ERR);

    int handle = dtm_iter.nextNode();
    if (handle == DTM.NULL) return null;
    return dtm_iter.getDTM(handle).getNode(handle);
  }

  /** {@inheritDoc} */
  @Override
  public Node previousNode() {
    if (!valid) throw new DTMDOMException(DOMException.INVALID_STATE_ERR);

    int handle = dtm_iter.previousNode();
    if (handle == DTM.NULL) return null;
    return dtm_iter.getDTM(handle).getNode(handle);
  }
}
