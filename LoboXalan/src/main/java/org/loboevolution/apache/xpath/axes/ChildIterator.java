/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the  "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.loboevolution.apache.xpath.axes;

import org.loboevolution.apache.xml.dtm.Axis;
import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMFilter;

/**
 * This class implements an optimized iterator for "node()" patterns, that is, any children of the
 * context node.
 *
 * @see org.loboevolution.apache.xpath.axes.LocPathIterator
 */
public class ChildIterator extends LocPathIterator {

  /**
   * Create a ChildIterator object.
   *
   * @param analysis Analysis bits of the entire pattern.
   * @throws org.loboevolution.javax.xml.transform.TransformerException if any
   */
  ChildIterator(int analysis) throws org.loboevolution.javax.xml.transform.TransformerException {
    super(analysis);

    // This iterator matches all kinds of nodes
    initNodeTest(DTMFilter.SHOW_ALL);
  }

  /** {@inheritDoc} */
  @Override
  public int asNode(XPathContext xctxt) {
    int current = xctxt.getCurrentNode();

    DTM dtm = xctxt.getDTM(current);

    return dtm.getFirstChild(current);
  }

  /** {@inheritDoc} */
  @Override
  public int nextNode() {
    if (m_foundLast) return DTM.NULL;

    int next;

    m_lastFetched =
        next =
            (DTM.NULL == m_lastFetched)
                ? m_cdtm.getFirstChild(m_context)
                : m_cdtm.getNextSibling(m_lastFetched);

    // m_lastFetched = next;
    if (DTM.NULL != next) {
      m_pos++;
      return next;
    } else {
      m_foundLast = true;

      return DTM.NULL;
    }
  }

  /** {@inheritDoc} */
  @Override
  public int getAxis() {
    return Axis.CHILD;
  }
}
