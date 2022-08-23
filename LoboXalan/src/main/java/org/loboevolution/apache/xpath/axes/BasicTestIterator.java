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
