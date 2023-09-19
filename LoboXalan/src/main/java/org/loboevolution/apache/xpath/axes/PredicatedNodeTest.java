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
import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xpath.XPathVisitor;
import org.loboevolution.apache.xpath.compiler.Compiler;
import org.loboevolution.apache.xpath.objects.XObject;
import org.loboevolution.apache.xpath.patterns.NodeTest;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMIterator;

public abstract class PredicatedNodeTest extends NodeTest implements SubContextList {

  /**
   * Construct an AxesWalker using a LocPathIterator.
   *
   * @param locPathIterator non-null reference to the parent iterator.
   */
  PredicatedNodeTest(LocPathIterator locPathIterator) {
    m_lpi = locPathIterator;
  }

  /** Construct an AxesWalker. The location path iterator will have to be set before use. */
  PredicatedNodeTest() {}

  /** {@inheritDoc} */
  @Override
  public Object clone() throws CloneNotSupportedException {
    // Do not access the location path itterator during this operation!

    PredicatedNodeTest clone = (PredicatedNodeTest) super.clone();

    if ((null != this.m_proximityPositions)
        && (this.m_proximityPositions == clone.m_proximityPositions)) {
      clone.m_proximityPositions = new int[this.m_proximityPositions.length];

      System.arraycopy(
          this.m_proximityPositions,
          0,
          clone.m_proximityPositions,
          0,
          this.m_proximityPositions.length);
    }

    if (clone.m_lpi == this) clone.m_lpi = (LocPathIterator) clone;

    return clone;
  }

  // Only for clones for findLastPos. See bug4638.
  protected int m_predCount = -1;

  /**
   * Get the number of predicates that this walker has.
   *
   * @return the number of predicates that this walker has.
   */
  public int getPredicateCount() {
    if (-1 == m_predCount) return (null == m_predicates) ? 0 : m_predicates.length;
    else return m_predCount;
  }

  /**
   * Set the number of predicates that this walker has. This does more that one would think, as it
   * creates a new predicate array of the size of the count argument, and copies count predicates
   * into the new one from the old, and then reassigns the predicates value. All this to keep from
   * having to have a predicate count value.
   *
   * @param count The number of predicates, which must be equal or less than the existing count.
   */
  public void setPredicateCount(int count) {
    if (count > 0) {
      Expression[] newPredicates = new Expression[count];
      System.arraycopy(m_predicates, 0, newPredicates, 0, count);
      m_predicates = newPredicates;
    } else m_predicates = null;
  }

  /**
   * Init predicate info.
   *
   * @param compiler The Compiler object that has information about this walker in the op map.
   * @param opPos The op code position of this location step.
   * @throws org.loboevolution.javax.xml.transform.TransformerException if any
   */
  protected void initPredicateInfo(Compiler compiler, int opPos)
      throws org.loboevolution.javax.xml.transform.TransformerException {

    int pos = compiler.getFirstPredicateOpPos(opPos);

    if (pos > 0) {
      m_predicates = compiler.getCompiledPredicates(pos);
      if (null != m_predicates) {
        for (Expression m_predicate : m_predicates) {
          m_predicate.exprSetParent(this);
        }
      }
    }
  }

  /**
   * Get a predicate expression at the given index.
   *
   * @param index Index of the predicate.
   * @return A predicate expression.
   */
  public Expression getPredicate(int index) {
    return m_predicates[index];
  }

  /**
   * Get the current sub-context position.
   *
   * @return The node position of this walker in the sub-context node list.
   */
  public int getProximityPosition() {
    return getProximityPosition(m_predicateIndex);
  }

  /** {@inheritDoc} */
  @Override
  public int getProximityPosition(XPathContext xctxt) {
    return getProximityPosition();
  }

  /** {@inheritDoc} */
  @Override
  public abstract int getLastPos(XPathContext xctxt);

  /**
   * Get the current sub-context position.
   *
   * @param predicateIndex The index of the predicate where the proximity should be taken from.
   * @return The node position of this walker in the sub-context node list.
   */
  protected int getProximityPosition(int predicateIndex) {
    return (predicateIndex >= 0) ? m_proximityPositions[predicateIndex] : 0;
  }

  /** Reset the proximity positions counts. */
  public void resetProximityPositions() {
    int nPredicates = getPredicateCount();
    if (nPredicates > 0) {
      if (null == m_proximityPositions) m_proximityPositions = new int[nPredicates];

      for (int i = 0; i < nPredicates; i++) {
        try {
          initProximityPosition(i);
        } catch (Exception e) {
          // TODO: Fix this...
          throw new WrappedRuntimeException(e);
        }
      }
    }
  }

  /**
   * Init the proximity position to zero for a forward axes.
   *
   * @param i The index into the m_proximityPositions array.
   */
  public void initProximityPosition(int i) {
    m_proximityPositions[i] = 0;
  }

  /**
   * Count forward one proximity position.
   *
   * @param i The index into the m_proximityPositions array, where the increment will occur.
   */
  protected void countProximityPosition(int i) {
    // Note that in the case of a UnionChildIterator, this may be a
    // static object and so m_proximityPositions may indeed be null!
    int[] pp = m_proximityPositions;
    if ((null != pp) && (i < pp.length)) pp[i]++;
  }

  /**
   * Tells if this is a reverse axes.
   *
   * @return false, unless a derived class overrides.
   */
  public boolean isReverseAxes() {
    return false;
  }

  /**
   * Process the predicates.
   *
   * @param context The current context node.
   * @param xctxt The XPath runtime context.
   * @return the result of executing the predicate expressions.
   * @throws org.loboevolution.javax.xml.transform.TransformerException if any
   */
  boolean executePredicates(int context, XPathContext xctxt)
      throws org.loboevolution.javax.xml.transform.TransformerException {

    int nPredicates = getPredicateCount();
    // System.out.println("nPredicates: "+nPredicates);
    if (nPredicates == 0) return true;

    try {
      m_predicateIndex = 0;
      xctxt.pushSubContextList(this);
      xctxt.pushNamespaceContext(m_lpi.getPrefixResolver());
      xctxt.pushCurrentNode(context);

      for (int i = 0; i < nPredicates; i++) {
        XObject pred = m_predicates[i].execute(xctxt);
        // System.out.println("\nBack from executing predicate expression - waiting
        // count:
        // "+m_lpi.getWaitingCount());
        // System.out.println("pred.getType(): "+pred.getType());
        if (XObject.CLASS_NUMBER == pred.getType()) {
          if (DEBUG_PREDICATECOUNTING) {
            System.out.flush();
            System.out.println("\n===== start predicate count ========");
            System.out.println("m_predicateIndex: " + m_predicateIndex);
            // System.out.println("getProximityPosition(m_predicateIndex): "
            // + getProximityPosition(m_predicateIndex));
            System.out.println("pred.num(): " + pred.num());
          }

          int proxPos = this.getProximityPosition(m_predicateIndex);
          int predIndex = (int) pred.num();
          if (proxPos != predIndex) {
            if (DEBUG_PREDICATECOUNTING) {
              System.out.println("\nnode context: " + nodeToString(context));
              System.out.println("index predicate is false: " + proxPos);
              System.out.println("\n===== end predicate count ========");
            }
            return false;
          } else if (DEBUG_PREDICATECOUNTING) {
            System.out.println("\nnode context: " + nodeToString(context));
            System.out.println("index predicate is true: " + proxPos);
            System.out.println("\n===== end predicate count ========");
          }

          // If there is a proximity index that will not change during the
          // course of itteration, then we know there can be no more true
          // occurances of this predicate, so flag that we're done after
          // this.
          //
          // bugzilla 14365
          // We can't set m_foundLast = true unless we're sure that -all-
          // remaining parameters are stable, or else last() fails. Fixed so
          // only sets m_foundLast if on the last predicate
          if (m_predicates[i].isStableNumber() && i == nPredicates - 1) {
            m_foundLast = true;
          }
        } else if (!pred.bool()) return false;

        countProximityPosition(++m_predicateIndex);
      }
    } finally {
      xctxt.popCurrentNode();
      xctxt.popNamespaceContext();
      xctxt.popSubContextList();
      m_predicateIndex = -1;
    }

    return true;
  }

  /**
   * Diagnostics.
   *
   * @param n Node to give diagnostic information about, or null.
   * @return Informative string about the argument.
   */
  protected String nodeToString(int n) {
    if (DTM.NULL != n) {
      DTM dtm = m_lpi.getXPathContext().getDTM(n);
      return dtm.getNodeName(n) + "{" + (n + 1) + "}";
    } else {
      return "null";
    }
  }

  // =============== NodeFilter Implementation ===============

  /**
   * Test whether a specified node is visible in the logical view of a TreeWalker or NodeIterator.
   * This function will be called by the implementation of TreeWalker and NodeIterator; it is not
   * intended to be called directly from user code.
   *
   * @param n The node to check to see if it passes the filter or not.
   * @return a constant to determine whether the node is accepted, rejected, or skipped, as defined
   *     above .
   */
  public short acceptNode(int n) {

    XPathContext xctxt = m_lpi.getXPathContext();

    try {
      xctxt.pushCurrentNode(n);

      XObject score = execute(xctxt, n);

      // System.out.println("\n::acceptNode - score: "+score.num()+"::");
      if (score != NodeTest.SCORE_NONE) {
        if (getPredicateCount() > 0) {
          countProximityPosition(0);

          if (!executePredicates(n, xctxt)) return DTMIterator.FILTER_SKIP;
        }

        return DTMIterator.FILTER_ACCEPT;
      }
    } catch (org.loboevolution.javax.xml.transform.TransformerException se) {

      // TODO: Fix this.
      throw new RuntimeException(se.getMessage());
    } finally {
      xctxt.popCurrentNode();
    }

    return DTMIterator.FILTER_SKIP;
  }

  /**
   * Set the location path iterator owner for this walker. Besides initialization, this function is
   * called during cloning operations.
   *
   * @param li non-null reference to the owning location path iterator.
   */
  public void setLocPathIterator(LocPathIterator li) {
    m_lpi = li;
    if (this != li) li.exprSetParent(this);
  }

  /** {@inheritDoc} */
  @Override
  public boolean canTraverseOutsideSubtree() {
    int n = getPredicateCount();
    for (int i = 0; i < n; i++) {
      if (getPredicate(i).canTraverseOutsideSubtree()) return true;
    }
    return false;
  }

  /**
   * This will traverse the heararchy, calling the visitor for each member. If the called visitor
   * method returns false, the subtree should not be called.
   *
   * @param visitor The visitor whose appropriate method will be called.
   */
  public void callPredicateVisitors(XPathVisitor visitor) {
    if (null != m_predicates) {
      for (Expression m_predicate : m_predicates) {
        if (visitor.visitPredicate(m_predicate)) {
          m_predicate.callVisitors(visitor);
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public boolean deepEquals(Expression expr) {
    if (!super.deepEquals(expr)) return false;

    PredicatedNodeTest pnt = (PredicatedNodeTest) expr;
    if (null != m_predicates) {

      int n = m_predicates.length;
      if ((null == pnt.m_predicates) || (pnt.m_predicates.length != n)) return false;
      for (int i = 0; i < n; i++) {
        if (!m_predicates[i].deepEquals(pnt.m_predicates[i])) return false;
      }
    } else if (null != pnt.m_predicates) return false;

    return true;
  }

  /** This is true if nextNode returns null. */
  protected transient boolean m_foundLast = false;

  /**
   * The owning location path iterator.
   *
   * @serial
   */
  protected LocPathIterator m_lpi;

  /** Which predicate we are executing. */
  transient int m_predicateIndex = -1;

  /**
   * The list of predicate expressions. Is static and does not need to be deep cloned.
   *
   * @serial
   */
  private Expression[] m_predicates;

  /** An array of counts that correspond to the number of predicates the step contains. */
  protected transient int[] m_proximityPositions;

  /** If true, diagnostic messages about predicate execution will be posted. */
  static final boolean DEBUG_PREDICATECOUNTING = false;
}
