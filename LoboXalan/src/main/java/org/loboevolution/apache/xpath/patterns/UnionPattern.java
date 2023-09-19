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
package org.loboevolution.apache.xpath.patterns;

import org.loboevolution.apache.xpath.Expression;
import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xpath.XPathVisitor;
import org.loboevolution.apache.xpath.objects.XObject;

/**
 * This class represents a union pattern, which can have multiple individual StepPattern patterns.
 */
public class UnionPattern extends Expression {

  /**
   * Array of the contained step patterns to be tested.
   *
   * @serial
   */
  private StepPattern[] m_patterns;

  /** {@inheritDoc} */
  @Override
  public boolean canTraverseOutsideSubtree() {
    if (null != m_patterns) {
      for (StepPattern m_pattern : m_patterns) {
        if (m_pattern.canTraverseOutsideSubtree()) return true;
      }
    }
    return false;
  }

  /**
   * Set the contained step patterns to be tested.
   *
   * @param patterns the contained step patterns to be tested.
   */
  public void setPatterns(StepPattern[] patterns) {
    m_patterns = patterns;
    if (null != patterns) {
      for (StepPattern pattern : patterns) {
        pattern.exprSetParent(this);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public XObject execute(XPathContext xctxt) throws org.loboevolution.javax.xml.transform.TransformerException {

    XObject bestScore = null;
    for (StepPattern m_pattern : m_patterns) {
      XObject score = m_pattern.execute(xctxt);

      if (score != NodeTest.SCORE_NONE) {
        if (null == bestScore) bestScore = score;
        else if (score.num() > bestScore.num()) bestScore = score;
      }
    }

    if (null == bestScore) {
      bestScore = NodeTest.SCORE_NONE;
    }

    return bestScore;
  }

  /** {@inheritDoc} */
  @Override
  public void callVisitors(XPathVisitor visitor) {
    visitor.visitUnionPattern();
    if (null != m_patterns) {
      for (StepPattern m_pattern : m_patterns) {
        m_pattern.callVisitors(visitor);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public boolean deepEquals(Expression expr) {
    if (!isSameClass(expr)) return false;

    UnionPattern up = (UnionPattern) expr;

    if (null != m_patterns) {
      int n = m_patterns.length;
      if ((null == up.m_patterns) || (up.m_patterns.length != n)) return false;

      for (int i = 0; i < n; i++) {
        if (!m_patterns[i].deepEquals(up.m_patterns[i])) return false;
      }
    } else if (up.m_patterns != null) return false;

    return true;
  }
}
