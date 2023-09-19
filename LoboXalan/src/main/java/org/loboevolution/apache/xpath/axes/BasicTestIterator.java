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

import org.loboevolution.apache.xpath.compiler.Compiler;
import org.loboevolution.apache.xpath.compiler.OpMap;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMFilter;
import org.loboevolution.apache.xml.dtm.DTMIterator;
import org.loboevolution.apache.xml.utils.PrefixResolver;

/**
 * Base for iterators that handle predicates. Does the basic next node logic, so all the derived
 * iterator has to do is get the next node.
 */
public abstract class BasicTestIterator extends LocPathIterator {

  /**
   * Create a LocPathIterator object.
   *
   * @param nscontext The namespace context for this iterator, should be OK if null.
   */
  protected BasicTestIterator(PrefixResolver nscontext) {

    super(nscontext);
  }

  /**
   * Create a LocPathIterator object, including creation of step walkers from the opcode list, and
   * call back into the Compiler to create predicate expressions.
   *
   * @param compiler The Compiler which is creating this expression.
   * @param opPos The position of this iterator in the opcode list from the compiler.
   * @throws org.loboevolution.javax.xml.transform.TransformerException if any
   */
  protected BasicTestIterator(Compiler compiler, int opPos, int analysis)
      throws org.loboevolution.javax.xml.transform.TransformerException {
    super(analysis);

    int firstStepPos = OpMap.getFirstChildPos(opPos);
    int whatToShow = compiler.getWhatToShow(firstStepPos);

    if ((0
            == (whatToShow
                & (DTMFilter.SHOW_ATTRIBUTE
                    | DTMFilter.SHOW_NAMESPACE
                    | DTMFilter.SHOW_ELEMENT
                    | DTMFilter.SHOW_PROCESSING_INSTRUCTION)))
        || (whatToShow == DTMFilter.SHOW_ALL)) initNodeTest(whatToShow);
    else {
      initNodeTest(
          whatToShow, compiler.getStepNS(firstStepPos), compiler.getStepLocalName(firstStepPos));
    }
    initPredicateInfo(compiler, firstStepPos);
  }

  /**
   * Get the next node via getNextXXX. Bottlenecked for derived class override.
   *
   * @return The next node on the axis, or DTM.NULL.
   */
  protected abstract int getNextNode();

  /** {@inheritDoc} */
  @Override
  public int nextNode() {
    if (m_foundLast) {
      m_lastFetched = DTM.NULL;
      return DTM.NULL;
    }

    if (DTM.NULL == m_lastFetched) {
      resetProximityPositions();
    }

    int next;

    try {
      do {
        next = getNextNode();

        if (DTM.NULL != next) {
          if (DTMIterator.FILTER_ACCEPT == acceptNode(next)) break;
          else continue;
        } else break;
      } while (next != DTM.NULL);

      if (DTM.NULL != next) {
        m_pos++;
        return next;
      }
      m_foundLast = true;
      return DTM.NULL;
    } finally {
    }
  }

  /** {@inheritDoc} */
  @Override
  public DTMIterator cloneWithReset() throws CloneNotSupportedException {

    ChildTestIterator clone = (ChildTestIterator) super.cloneWithReset();

    clone.resetProximityPositions();

    return clone;
  }
}
