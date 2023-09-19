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

import org.loboevolution.apache.xml.utils.WrappedRuntimeException;
import org.loboevolution.apache.xpath.Expression;
import org.loboevolution.apache.xpath.XPathVisitor;
import org.loboevolution.apache.xpath.compiler.Compiler;
import org.loboevolution.apache.xpath.compiler.OpCodes;
import org.loboevolution.apache.xpath.compiler.OpMap;
import org.loboevolution.apache.xml.dtm.Axis;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMIterator;

/**
 * This class extends NodeSetDTM, which implements DTMIterator, and fetches nodes one at a time in
 * document order based on a XPath <a href="http://www.w3.org/TR/xpath#NT-UnionExpr">UnionExpr</a>.
 * As each node is iterated via nextNode(), the node is also stored in the NodeVector, so that
 * previousNode() can easily be done.
 */
public class UnionPathIterator extends LocPathIterator
    implements Cloneable, DTMIterator, PathComponent {

  /** {@inheritDoc} */
  @Override
  public void setRoot(int context, Object environment) {
    super.setRoot(context, environment);

    try {
      if (null != m_exprs) {
        int n = m_exprs.length;
        DTMIterator[] newIters = new DTMIterator[n];

        for (int i = 0; i < n; i++) {
          DTMIterator iter = m_exprs[i].asIterator(m_execContext, context);
          newIters[i] = iter;
          iter.nextNode();
        }
        m_iterators = newIters;
      }
    } catch (Exception e) {
      throw new WrappedRuntimeException(e);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void detach() {
    if (m_allowDetach && null != m_iterators) {
      for (DTMIterator m_iterator : m_iterators) {
        m_iterator.detach();
      }
      m_iterators = null;
    }
  }

  /**
   * Create a UnionPathIterator object, including creation of location path iterators from the
   * opcode list, and call back into the Compiler to create predicate expressions.
   *
   * @param compiler The Compiler which is creating this expression.
   * @param opPos The position of this iterator in the opcode list from the compiler.
   * @throws org.loboevolution.javax.xml.transform.TransformerException if any
   */
  public UnionPathIterator(Compiler compiler, int opPos)
      throws org.loboevolution.javax.xml.transform.TransformerException {

    super();

    opPos = OpMap.getFirstChildPos(opPos);

    loadLocationPaths(compiler, opPos, 0);
  }

  /**
   * This will return an iterator capable of handling the union of paths given.
   *
   * @param compiler The Compiler which is creating this expression.
   * @param opPos The position of this iterator in the opcode list from the compiler.
   * @return Object that is derived from LocPathIterator.
   * @throws org.loboevolution.javax.xml.transform.TransformerException if any
   */
  public static LocPathIterator createUnionIterator(Compiler compiler, int opPos)
      throws org.loboevolution.javax.xml.transform.TransformerException {
    // For the moment, I'm going to first create a full UnionPathIterator, and
    // then see if I can reduce it to a UnionChildIterator. It would obviously
    // be more effecient to just test for the conditions for a UnionChildIterator,
    // and then create that directly.
    UnionPathIterator upi = new UnionPathIterator(compiler, opPos);
    int nPaths = upi.m_exprs.length;
    boolean isAllChildIterators = true;
    for (int i = 0; i < nPaths; i++) {
      LocPathIterator lpi = upi.m_exprs[i];

      if (lpi.getAxis() != Axis.CHILD) {
        isAllChildIterators = false;
        break;
      } else {
        // check for positional predicates or position function, which won't work.
        if (HasPositionalPredChecker.check(lpi)) {
          isAllChildIterators = false;
          break;
        }
      }
    }
    if (isAllChildIterators) {
      UnionChildIterator uci = new UnionChildIterator();

      for (int i = 0; i < nPaths; i++) {
        PredicatedNodeTest lpi = upi.m_exprs[i];
        // I could strip the lpi down to a pure PredicatedNodeTest, but
        // I don't think it's worth it. Note that the test can be used
        // as a static object... so it doesn't have to be cloned.
        uci.addNodeTest(lpi);
      }
      return uci;
    }
    return upi;
  }

  /** {@inheritDoc} */
  @Override
  public int getAnalysisBits() {
    int bits = 0;

    if (m_exprs != null) {
      for (LocPathIterator m_expr : m_exprs) {
        int bit = m_expr.getAnalysisBits();
        bits |= bit;
      }
    }

    return bits;
  }

  /** {@inheritDoc} */
  @Override
  public Object clone() throws CloneNotSupportedException {

    UnionPathIterator clone = (UnionPathIterator) super.clone();
    if (m_iterators != null) {
      int n = m_iterators.length;

      clone.m_iterators = new DTMIterator[n];

      for (int i = 0; i < n; i++) {
        clone.m_iterators[i] = (DTMIterator) m_iterators[i].clone();
      }
    }

    return clone;
  }

  /**
   * Create a new location path iterator.
   *
   * @param compiler The Compiler which is creating this expression.
   * @param opPos The position of this iterator in the
   * @return New location path iterator.
   * @throws org.loboevolution.javax.xml.transform.TransformerException if any
   */
  protected LocPathIterator createDTMIterator(Compiler compiler, int opPos)
      throws org.loboevolution.javax.xml.transform.TransformerException {
    return (LocPathIterator)
        WalkerFactory.newDTMIterator(compiler, opPos, compiler.getLocationPathDepth() <= 0);
  }

  /**
   * Initialize the location path iterators. Recursive.
   *
   * @param compiler The Compiler which is creating this expression.
   * @param opPos The position of this iterator in the opcode list from the compiler.
   * @param count The insert position of the iterator.
   * @throws org.loboevolution.javax.xml.transform.TransformerException if any
   */
  protected void loadLocationPaths(Compiler compiler, int opPos, int count)
      throws org.loboevolution.javax.xml.transform.TransformerException {

    // TODO: Handle unwrapped FilterExpr
    int steptype = compiler.getOp(opPos);

    if (steptype == OpCodes.OP_LOCATIONPATH) {
      loadLocationPaths(compiler, compiler.getNextOpPos(opPos), count + 1);

      m_exprs[count] = createDTMIterator(compiler, opPos);
      m_exprs[count].exprSetParent(this);
    } else {

      // Have to check for unwrapped functions, which the LocPathIterator
      // doesn't handle.
      switch (steptype) {
        case OpCodes.OP_VARIABLE:
        case OpCodes.OP_EXTFUNCTION:
        case OpCodes.OP_FUNCTION:
        case OpCodes.OP_GROUP:
          loadLocationPaths(compiler, compiler.getNextOpPos(opPos), count + 1);

          WalkingIterator iter = new WalkingIterator(compiler.getNamespaceContext());
          iter.exprSetParent(this);

          if (compiler.getLocationPathDepth() <= 0) iter.setIsTopLevel(true);

          iter.m_firstWalker = new org.loboevolution.apache.xpath.axes.FilterExprWalker(iter);

          iter.m_firstWalker.init(compiler, opPos, steptype);

          m_exprs[count] = iter;
          break;
        default:
          m_exprs = new LocPathIterator[count];
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public int nextNode() {
    if (m_foundLast) return DTM.NULL;

    // Loop through the iterators getting the current fetched
    // node, and get the earliest occuring in document order
    int earliestNode = DTM.NULL;

    if (null != m_iterators) {
      int n = m_iterators.length;
      int iteratorUsed = -1;

      for (int i = 0; i < n; i++) {
        int node = m_iterators[i].getCurrentNode();

        if (DTM.NULL == node) continue;
        else if (DTM.NULL == earliestNode) {
          iteratorUsed = i;
          earliestNode = node;
        } else {
          if (node == earliestNode) {

            // Found a duplicate, so skip past it.
            m_iterators[i].nextNode();
          } else {
            DTM dtm = getDTM(node);

            if (dtm.isNodeAfter(node, earliestNode)) {
              iteratorUsed = i;
              earliestNode = node;
            }
          }
        }
      }

      if (DTM.NULL != earliestNode) {
        m_iterators[iteratorUsed].nextNode();

        incrementCurrentPos();
      } else m_foundLast = true;
    }

    m_lastFetched = earliestNode;

    return earliestNode;
  }

  /**
   * The location path iterators, one for each <a
   * href="http://www.w3.org/TR/xpath#NT-LocationPath">location path</a> contained in the union
   * expression.
   *
   * @serial
   */
  protected LocPathIterator[] m_exprs;

  /**
   * The location path iterators, one for each <a
   * href="http://www.w3.org/TR/xpath#NT-LocationPath">location path</a> contained in the union
   * expression.
   *
   * @serial
   */
  protected DTMIterator[] m_iterators;

  /** {@inheritDoc} */
  @Override
  public int getAxis() {
    // Could be smarter.
    return -1;
  }

  /** {@inheritDoc} */
  @Override
  public void callVisitors(XPathVisitor visitor) {
    if (visitor.visitUnionPath()) {
      if (null != m_exprs) {
        for (LocPathIterator m_expr : m_exprs) {
          m_expr.callVisitors(visitor);
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public boolean deepEquals(Expression expr) {
    if (!super.deepEquals(expr)) return false;

    UnionPathIterator upi = (UnionPathIterator) expr;

    if (null != m_exprs) {
      int n = m_exprs.length;

      if ((null == upi.m_exprs) || (upi.m_exprs.length != n)) return false;

      for (int i = 0; i < n; i++) {
        if (!m_exprs[i].deepEquals(upi.m_exprs[i])) return false;
      }
    } else if (null != upi.m_exprs) {
      return false;
    }

    return true;
  }
}
