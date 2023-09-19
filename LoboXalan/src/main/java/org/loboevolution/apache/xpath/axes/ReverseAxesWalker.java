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
