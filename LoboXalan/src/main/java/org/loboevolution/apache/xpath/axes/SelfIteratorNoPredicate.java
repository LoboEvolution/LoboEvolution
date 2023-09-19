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
