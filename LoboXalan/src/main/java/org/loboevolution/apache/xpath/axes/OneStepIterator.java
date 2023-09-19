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

import org.loboevolution.apache.xpath.Expression;
import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xpath.compiler.Compiler;
import org.loboevolution.apache.xpath.compiler.OpMap;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMAxisIterator;
import org.loboevolution.apache.xml.dtm.DTMIterator;

/**
 * This class implements a general iterator for those LocationSteps with only one step, and perhaps
 * a predicate.
 */
public class OneStepIterator extends ChildTestIterator {

  /** The traversal axis from where the nodes will be filtered. */
  protected int m_axis;

  /** The DTM inner traversal class, that corresponds to the super axis. */
  protected DTMAxisIterator m_iterator;

  /**
   * Create a OneStepIterator object.
   *
   * @param compiler A reference to the Compiler that contains the op map.
   * @param opPos The position within the op map, which contains the location path expression for
   *     this itterator.
   * @throws org.loboevolution.javax.xml.transform.TransformerException if any
   */
  OneStepIterator(Compiler compiler, int opPos, int analysis)
      throws org.loboevolution.javax.xml.transform.TransformerException {
    super(compiler, opPos, analysis);
    int firstStepPos = OpMap.getFirstChildPos(opPos);

    m_axis = WalkerFactory.getAxisFromStep(compiler, firstStepPos);
  }

  /** {@inheritDoc} */
  @Override
  public void setRoot(int context, Object environment) {
    super.setRoot(context, environment);
    if (m_axis > -1) m_iterator = m_cdtm.getAxisIterator(m_axis);
    m_iterator.setStartNode(m_context);
  }

  /** {@inheritDoc} */
  @Override
  public void detach() {
    if (m_allowDetach) {
      if (m_axis > -1) m_iterator = null;

      // Always call the superclass detach last!
      super.detach();
    }
  }

  /** {@inheritDoc} */
  @Override
  protected int getNextNode() {
    return m_lastFetched = m_iterator.next();
  }

  /** {@inheritDoc} */
  @Override
  public Object clone() throws CloneNotSupportedException {
    // Do not access the location path itterator during this operation!

    OneStepIterator clone = (OneStepIterator) super.clone();

    if (m_iterator != null) {
      clone.m_iterator = m_iterator.cloneIterator();
    }
    return clone;
  }

  /** {@inheritDoc} */
  @Override
  public DTMIterator cloneWithReset() throws CloneNotSupportedException {

    OneStepIterator clone = (OneStepIterator) super.cloneWithReset();
    clone.m_iterator = m_iterator;

    return clone;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isReverseAxes() {
    return m_iterator.isReverse();
  }

  /** {@inheritDoc} */
  @Override
  protected int getProximityPosition(int predicateIndex) {
    if (!isReverseAxes()) return super.getProximityPosition(predicateIndex);

    // A negative predicate index seems to occur with
    // (preceding-sibling::*|following-sibling::*)/ancestor::*[position()]/*[position()]
    // -sb
    if (predicateIndex < 0) return -1;

    if (m_proximityPositions[predicateIndex] <= 0) {
      XPathContext xctxt = getXPathContext();
      try {
        OneStepIterator clone = (OneStepIterator) this.clone();

        int root = getRoot();
        xctxt.pushCurrentNode(root);
        clone.setRoot(root, xctxt);

        // clone.setPredicateCount(predicateIndex);
        clone.m_predCount = predicateIndex;

        // Count 'em all
        int count = 1;

        while (DTM.NULL != (clone.nextNode())) {
          count++;
        }

        m_proximityPositions[predicateIndex] += count;
      } catch (CloneNotSupportedException cnse) {

        // can't happen
      } finally {
        xctxt.popCurrentNode();
      }
    }

    return m_proximityPositions[predicateIndex];
  }

  /** {@inheritDoc} */
  @Override
  public int getLength() {
    if (!isReverseAxes()) return super.getLength();

    // Tell if this is being called from within a predicate.
    boolean isPredicateTest = this == m_execContext.getSubContextList();

    // If we have already calculated the length, and the current predicate
    // is the first predicate, then return the length. We don't cache
    // the anything but the length of the list to the first predicate.
    if (-1 != m_length && isPredicateTest && m_predicateIndex < 1) return m_length;

    int count = 0;

    XPathContext xctxt = getXPathContext();
    try {
      OneStepIterator clone = (OneStepIterator) this.cloneWithReset();

      int root = getRoot();
      xctxt.pushCurrentNode(root);
      clone.setRoot(root, xctxt);

      clone.m_predCount = m_predicateIndex;

      while (DTM.NULL != (clone.nextNode())) {
        count++;
      }
    } catch (CloneNotSupportedException cnse) {
      // can't happen
    } finally {
      xctxt.popCurrentNode();
    }
    if (isPredicateTest && m_predicateIndex < 1) m_length = count;

    return count;
  }

  /** {@inheritDoc} */
  @Override
  protected void countProximityPosition(int i) {
    if (!isReverseAxes()) super.countProximityPosition(i);
    else if (i < m_proximityPositions.length) m_proximityPositions[i]--;
  }

  /** {@inheritDoc} */
  @Override
  public void reset() {

    super.reset();
    if (null != m_iterator) m_iterator.reset();
  }

  /** {@inheritDoc} */
  @Override
  public int getAxis() {
    return m_axis;
  }

  /** {@inheritDoc} */
  @Override
  public boolean deepEquals(Expression expr) {
    if (!super.deepEquals(expr)) return false;

    if (m_axis != ((OneStepIterator) expr).m_axis) return false;

    return true;
  }
}
