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
import org.loboevolution.apache.xpath.objects.XObject;
import org.loboevolution.apache.xpath.patterns.NodeTest;
import org.loboevolution.apache.xml.dtm.DTMIterator;

/**
 * This class defines a simplified type of union iterator that only tests along the child axes. If
 * the conditions are right, it is much faster than using a UnionPathIterator.
 */
public class UnionChildIterator extends ChildTestIterator {

  /**
   * Even though these may hold full LocPathIterators, this array does not have to be cloned, since
   * only the node test and predicate portion are used, and these only need static information.
   * However, also note that index predicates can not be used!
   */
  private PredicatedNodeTest[] m_nodeTests = null;

  /** Constructor for UnionChildIterator */
  public UnionChildIterator() {
    super(null);
  }

  /**
   * Add a node test to the union list.
   *
   * @param test reference to a NodeTest, which will be added directly to the list of node tests (in
   *     other words, it will not be cloned). The parent of this test will be set to this object.
   */
  public void addNodeTest(PredicatedNodeTest test) {

    // Increase array size by only 1 at a time. Fix this
    // if it looks to be a problem.
    if (null == m_nodeTests) {
      m_nodeTests = new PredicatedNodeTest[1];
      m_nodeTests[0] = test;
    } else {
      PredicatedNodeTest[] tests = m_nodeTests;
      int len = m_nodeTests.length;

      m_nodeTests = new PredicatedNodeTest[len + 1];

      System.arraycopy(tests, 0, m_nodeTests, 0, len);

      m_nodeTests[len] = test;
    }
    test.exprSetParent(this);
  }

  /** {@inheritDoc} */
  @Override
  public short acceptNode(int n) {
    XPathContext xctxt = getXPathContext();
    try {
      xctxt.pushCurrentNode(n);
      for (PredicatedNodeTest pnt : m_nodeTests) {
        XObject score = pnt.execute(xctxt, n);
        if (score != NodeTest.SCORE_NONE) {
          // Note that we are assuming there are no positional predicates!
          if (pnt.getPredicateCount() > 0) {
            if (pnt.executePredicates(n, xctxt)) return DTMIterator.FILTER_ACCEPT;
          } else return DTMIterator.FILTER_ACCEPT;
        }
      }
    } catch (org.loboevolution.javax.xml.transform.TransformerException se) {

      // TODO: Fix this.
      throw new RuntimeException(se.getMessage());
    } finally {
      xctxt.popCurrentNode();
    }
    return DTMIterator.FILTER_SKIP;
  }
}
