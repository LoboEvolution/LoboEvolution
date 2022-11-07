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

import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xml.dtm.DTM;

/**
 * This class implements an optimized iterator for "." patterns, that is, the self axes without any
 * predicates.
 *
 * @see org.loboevolution.apache.xpath.axes.LocPathIterator
 */
public class SelfIteratorNoPredicate extends LocPathIterator {

  /**
   * Create a SelfIteratorNoPredicate object.
   *
   * @param analysis Analysis bits.
   * @throws org.loboevolution.javax.xml.transform.TransformerException if any
   */
  SelfIteratorNoPredicate(int analysis) throws org.loboevolution.javax.xml.transform.TransformerException {
    super(analysis);
  }

  /** {@inheritDoc} */
  @Override
  public int nextNode() {
    if (m_foundLast) return DTM.NULL;

    int next;

    m_lastFetched = next = (DTM.NULL == m_lastFetched) ? m_context : DTM.NULL;

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
  public int asNode(XPathContext xctxt) {
    return xctxt.getCurrentNode();
  }

  /** {@inheritDoc} */
  @Override
  public int getLastPos(XPathContext xctxt) {
    return 1;
  }
}
