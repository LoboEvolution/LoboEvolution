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
