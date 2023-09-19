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
import org.loboevolution.apache.xpath.compiler.OpCodes;
import org.loboevolution.apache.xpath.compiler.OpMap;
import org.loboevolution.apache.xpath.patterns.NodeTest;
import org.loboevolution.apache.xml.dtm.Axis;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMAxisTraverser;
import org.loboevolution.apache.xml.dtm.DTMFilter;
import org.loboevolution.apache.xml.dtm.DTMIterator;

/**
 * This class implements an optimized iterator for descendant, descendant-or-self, or "//foo"
 * patterns.
 *
 * @see org.loboevolution.apache.xpath.axes.LocPathIterator
 */
public class DescendantIterator extends LocPathIterator {

  /**
   * Create a DescendantIterator object.
   *
   * @param compiler A reference to the Compiler that contains the op map.
   * @param opPos The position within the op map, which contains the location path expression for
   *     this itterator.
   * @throws org.loboevolution.javax.xml.transform.TransformerException if any
   */
  DescendantIterator(Compiler compiler, int opPos, int analysis)
      throws org.loboevolution.javax.xml.transform.TransformerException {

    super(analysis);

    int firstStepPos = OpMap.getFirstChildPos(opPos);
    int stepType = compiler.getOp(firstStepPos);

    boolean orSelf = OpCodes.FROM_DESCENDANTS_OR_SELF == stepType;
    boolean fromRoot = false;
    if (OpCodes.FROM_SELF == stepType) {
      orSelf = true;
      // firstStepPos += 8;
    } else if (OpCodes.FROM_ROOT == stepType) {
      fromRoot = true;
      // Ugly code... will go away when AST work is done.
      int nextStepPos = compiler.getNextStepPos(firstStepPos);
      if (compiler.getOp(nextStepPos) == OpCodes.FROM_DESCENDANTS_OR_SELF) orSelf = true;
      // firstStepPos += 8;
    }

    // Find the position of the last step.
    int nextStepPos = firstStepPos;
    while (true) {
      nextStepPos = compiler.getNextStepPos(nextStepPos);
      if (nextStepPos > 0) {
        int stepOp = compiler.getOp(nextStepPos);
        if (OpCodes.ENDOP != stepOp) firstStepPos = nextStepPos;
        else break;
      } else break;
    }

    // Fix for http://nagoya.apache.org/bugzilla/show_bug.cgi?id=1336
    if ((analysis & WalkerFactory.BIT_CHILD) != 0) orSelf = false;

    if (fromRoot) {
      if (orSelf) m_axis = Axis.DESCENDANTSORSELFFROMROOT;
      else m_axis = Axis.DESCENDANTSFROMROOT;
    } else if (orSelf) m_axis = Axis.DESCENDANTORSELF;
    else m_axis = Axis.DESCENDANT;

    int whatToShow = compiler.getWhatToShow(firstStepPos);

    if ((0
            == (whatToShow
                & (DTMFilter.SHOW_ATTRIBUTE
                    | DTMFilter.SHOW_ELEMENT
                    | DTMFilter.SHOW_PROCESSING_INSTRUCTION)))
        || (whatToShow == DTMFilter.SHOW_ALL)) initNodeTest(whatToShow);
    else {
      initNodeTest(
          whatToShow, compiler.getStepNS(firstStepPos), compiler.getStepLocalName(firstStepPos));
    }
    initPredicateInfo(compiler, firstStepPos);
  }

  /** {@inheritDoc} */
  @Override
  public DTMIterator cloneWithReset() throws CloneNotSupportedException {

    DescendantIterator clone = (DescendantIterator) super.cloneWithReset();
    clone.m_traverser = m_traverser;

    clone.resetProximityPositions();

    return clone;
  }

  /** {@inheritDoc} */
  @Override
  public int nextNode() {
    if (m_foundLast) return DTM.NULL;

    if (DTM.NULL == m_lastFetched) {
      resetProximityPositions();
    }

    int next;

    try {
      do {
        if (0 == m_extendedTypeID) {
          next =
              m_lastFetched =
                  (DTM.NULL == m_lastFetched)
                      ? m_traverser.first(m_context)
                      : m_traverser.next(m_context, m_lastFetched);
        } else {
          next =
              m_lastFetched =
                  (DTM.NULL == m_lastFetched)
                      ? m_traverser.first(m_context, m_extendedTypeID)
                      : m_traverser.next(m_context, m_lastFetched, m_extendedTypeID);
        }

        if (DTM.NULL != next) {
          if (DTMIterator.FILTER_ACCEPT == acceptNode(next)) break;
          else continue;
        } else break;
      } while (next != DTM.NULL);

      if (DTM.NULL != next) {
        m_pos++;
        return next;
      } else {
        m_foundLast = true;

        return DTM.NULL;
      }
    } finally {
    }
  }

  /** {@inheritDoc} */
  @Override
  public void setRoot(int context, Object environment) {
    super.setRoot(context, environment);
    m_traverser = m_cdtm.getAxisTraverser(m_axis);

    String localName = getLocalName();
    String namespace = getNamespace();
    int what = m_whatToShow;
    if (DTMFilter.SHOW_ALL == what
        || NodeTest.WILD.equals(localName)
        || NodeTest.WILD.equals(namespace)) {
      m_extendedTypeID = 0;
    } else {
      int type = getNodeTypeTest(what);
      m_extendedTypeID = m_cdtm.getExpandedTypeID(namespace, localName, type);
    }
  }

  /** {@inheritDoc} */
  @Override
  public int asNode(XPathContext xctxt) throws org.loboevolution.javax.xml.transform.TransformerException {
    if (getPredicateCount() > 0) return super.asNode(xctxt);

    int current = xctxt.getCurrentNode();

    DTM dtm = xctxt.getDTM(current);
    DTMAxisTraverser traverser = dtm.getAxisTraverser(m_axis);

    String localName = getLocalName();
    String namespace = getNamespace();
    int what = m_whatToShow;
    if (DTMFilter.SHOW_ALL == what || localName == NodeTest.WILD || namespace == NodeTest.WILD) {
      return traverser.first(current);
    } else {
      int type = getNodeTypeTest(what);
      int extendedType = dtm.getExpandedTypeID(namespace, localName, type);
      return traverser.first(current, extendedType);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void detach() {
    if (m_allowDetach) {
      m_traverser = null;
      m_extendedTypeID = 0;

      // Always call the superclass detach last!
      super.detach();
    }
  }

  /** {@inheritDoc} */
  @Override
  public int getAxis() {
    return m_axis;
  }

  /** The traverser to use to navigate over the descendants. */
  protected transient DTMAxisTraverser m_traverser;

  /** The axis that we are traversing. */
  protected int m_axis;

  /** The extended type ID, not set until setRoot. */
  protected int m_extendedTypeID;

  /** {@inheritDoc} */
  @Override
  public boolean deepEquals(Expression expr) {
    if (!super.deepEquals(expr)) return false;

    if (m_axis != ((DescendantIterator) expr).m_axis) return false;

    return true;
  }
}
