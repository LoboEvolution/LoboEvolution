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
import org.loboevolution.apache.xml.dtm.DTMAxisIterator;

/**
 * Walker for a reverse axes.
 *
 * @see <a href="http://www.w3.org/TR/xpath#predicates">XPath 2.4 Predicates</a>
 */
public class ReverseAxesWalker extends AxesWalker {

  /**
   * Construct an AxesWalker using a LocPathIterator.
   *
   * @param locPathIterator The location path iterator that 'owns' this walker.
   */
  ReverseAxesWalker(LocPathIterator locPathIterator, int axis) {
    super(locPathIterator, axis);
  }

  /** {@inheritDoc} */
  @Override
  public void setRoot(int root) {
    super.setRoot(root);
    m_iterator = getDTM(root).getAxisIterator(m_axis);
    m_iterator.setStartNode(root);
  }

  /** {@inheritDoc} */
  @Override
  public void detach() {
    m_iterator = null;
    super.detach();
  }

  /** {@inheritDoc} */
  @Override
  protected int getNextNode() {
    if (m_foundLast) return DTM.NULL;

    int next = m_iterator.next();

    if (m_isFresh) m_isFresh = false;

    if (DTM.NULL == next) this.m_foundLast = true;

    return next;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isReverseAxes() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  protected int getProximityPosition(int predicateIndex) {
    // A negative predicate index seems to occur with
    // (preceding-sibling::*|following-sibling::*)/ancestor::*[position()]/*[position()]
    // -sb
    if (predicateIndex < 0) return -1;

    int count = m_proximityPositions[predicateIndex];

    if (count <= 0) {
      AxesWalker savedWalker = wi().getLastUsedWalker();

      try {
        ReverseAxesWalker clone = (ReverseAxesWalker) this.clone();

        clone.setRoot(this.getRoot());

        clone.setPredicateCount(predicateIndex);

        clone.setPrevWalker(null);
        clone.setNextWalker(null);
        wi().setLastUsedWalker(clone);

        // Count 'em all
        count++;
        while (DTM.NULL != clone.nextNode()) {
          count++;
        }

        m_proximityPositions[predicateIndex] = count;
      } catch (CloneNotSupportedException cnse) {

        // can't happen
      } finally {
        wi().setLastUsedWalker(savedWalker);
      }
    }

    return count;
  }

  /** {@inheritDoc} */
  @Override
  protected void countProximityPosition(int i) {
    if (i < m_proximityPositions.length) m_proximityPositions[i]--;
  }

  /** {@inheritDoc} */
  @Override
  public int getLastPos(XPathContext xctxt) {

    int count = 0;
    AxesWalker savedWalker = wi().getLastUsedWalker();

    try {
      ReverseAxesWalker clone = (ReverseAxesWalker) this.clone();

      clone.setRoot(this.getRoot());

      clone.setPredicateCount(m_predicateIndex);

      clone.setPrevWalker(null);
      clone.setNextWalker(null);
      wi().setLastUsedWalker(clone);

      while (DTM.NULL != clone.nextNode()) {
        count++;
      }
    } catch (CloneNotSupportedException cnse) {

      // can't happen
    } finally {
      wi().setLastUsedWalker(savedWalker);
    }

    return count;
  }

  /** The DTM inner traversal class, that corresponds to the super axis. */
  protected DTMAxisIterator m_iterator;
}
