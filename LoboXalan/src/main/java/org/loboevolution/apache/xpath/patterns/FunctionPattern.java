/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
import org.loboevolution.apache.xpath.objects.XNumber;
import org.loboevolution.apache.xpath.objects.XObject;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMIterator;
import org.loboevolution.javax.xml.transform.TransformerException;

/** Match pattern step that contains a function. */
public class FunctionPattern extends StepPattern {

  /**
   * Construct a FunctionPattern from a {@link org.loboevolution.apache.xpath.functions.Function
   * expression}.
   *
   * <p>NEEDSDOC @param expr
   */
  public FunctionPattern(final Expression expr, final int axis) {

    super(0, null, null, axis);

    m_functionExpr = expr;
  }

  /** {@inheritDoc} */
  @Override
  public final void calcScore() {

    m_score = SCORE_OTHER;

    if (null == m_targetString) calcTargetString();
  }

  /**
   * Should be a {@link org.loboevolution.apache.xpath.functions.Function expression}.
   *
   * @serial
   */
  final Expression m_functionExpr;

  /** {@inheritDoc} */
  @Override
  public XObject execute(final XPathContext xctxt, final int ctx)
      throws TransformerException {
    int context = ctx;
    final DTMIterator nl = m_functionExpr.asIterator(xctxt, context);
    XNumber score = SCORE_NONE;

    if (null != nl) {
      int n;

      while (DTM.NULL != (n = nl.nextNode())) {
        score = (n == context) ? SCORE_OTHER : SCORE_NONE;

        if (score == SCORE_OTHER) {
          context = n;

          break;
        }
      }

      // nl.detach();
    }
    nl.detach();

    return score;
  }

  /** {@inheritDoc} */
  @Override
  public XObject execute(final XPathContext xctxt, final int ctx, final DTM dtm, final int expType)
      throws TransformerException {
    int context = ctx;
    final DTMIterator nl = m_functionExpr.asIterator(xctxt, context);
    XNumber score = SCORE_NONE;

    if (null != nl) {
      int n;

      while (DTM.NULL != (n = nl.nextNode())) {
        score = (n == context) ? SCORE_OTHER : SCORE_NONE;

        if (score == SCORE_OTHER) {
          context = n;

          break;
        }
      }

      nl.detach();
    }

    return score;
  }

  /** {@inheritDoc} */
  @Override
  public XObject execute(final XPathContext xctxt) throws org.loboevolution.javax.xml.transform.TransformerException {

    int context = xctxt.getCurrentNode();
    final DTMIterator nl = m_functionExpr.asIterator(xctxt, context);
    XNumber score = SCORE_NONE;

    if (null != nl) {
      int n;

      while (DTM.NULL != (n = nl.nextNode())) {
        score = (n == context) ? SCORE_OTHER : SCORE_NONE;

        if (score == SCORE_OTHER) {
          context = n;

          break;
        }
      }

      nl.detach();
    }

    return score;
  }

  /** {@inheritDoc} */
  @Override
  protected void callSubtreeVisitors(final XPathVisitor visitor) {
    m_functionExpr.callVisitors(visitor);
    super.callSubtreeVisitors(visitor);
  }
}
