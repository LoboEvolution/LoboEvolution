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
import org.loboevolution.apache.xpath.XPathVisitor;
import org.loboevolution.apache.xpath.functions.FuncLast;
import org.loboevolution.apache.xpath.functions.FuncPosition;
import org.loboevolution.apache.xpath.functions.Function;
import org.loboevolution.apache.xpath.objects.XNumber;
import org.loboevolution.apache.xpath.operations.Div;
import org.loboevolution.apache.xpath.operations.Minus;
import org.loboevolution.apache.xpath.operations.Mod;
import org.loboevolution.apache.xpath.operations.Mult;
import org.loboevolution.apache.xpath.operations.Plus;

public class HasPositionalPredChecker extends XPathVisitor {
  private boolean m_hasPositionalPred = false;
  private int m_predDepth = 0;

  /**
   * Process the LocPathIterator to see if it contains variables or functions that may make it
   * context dependent.
   *
   * @param path LocPathIterator that is assumed to be absolute, but needs checking.
   * @return true if the path is confirmed to be absolute, false if it may contain context
   *     dependencies.
   */
  public static boolean check(LocPathIterator path) {
    HasPositionalPredChecker hppc = new HasPositionalPredChecker();
    path.callVisitors(hppc);
    return hppc.m_hasPositionalPred;
  }

  /** {@inheritDoc} */
  @Override
  public boolean visitFunction(Function func) {
    if ((func instanceof FuncPosition) || (func instanceof FuncLast)) m_hasPositionalPred = true;
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public boolean visitPredicate(Expression pred) {
    m_predDepth++;

    if (m_predDepth == 1) {
      if ((pred instanceof XNumber)
          || (pred instanceof Div)
          || (pred instanceof Plus)
          || (pred instanceof Minus)
          || (pred instanceof Mod)
          || (pred instanceof Mult)
          || (pred instanceof org.loboevolution.apache.xpath.operations.Number)
          || (pred instanceof Function)) m_hasPositionalPred = true;
      else pred.callVisitors(this);
    }

    m_predDepth--;

    // Don't go have the caller go any further down the subtree.
    return false;
  }
}
