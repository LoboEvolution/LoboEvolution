/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
package org.loboevolution.apache.xpath.operations;

import org.loboevolution.apache.xpath.Expression;
import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xpath.XPathVisitor;
import org.loboevolution.apache.xpath.objects.XObject;

/** The unary operation base class. */
public abstract class UnaryOperation extends Expression {

  /**
   * The operand for the operation.
   *
   * @serial
   */
  protected Expression m_right;

  /** {@inheritDoc} */
  @Override
  public boolean canTraverseOutsideSubtree() {

    if (null != m_right && m_right.canTraverseOutsideSubtree()) return true;

    return false;
  }

  /**
   * Set the expression operand for the operation.
   *
   * @param r The expression operand to which the unary operation will be applied.
   */
  public void setRight(final Expression r) {
    m_right = r;
    r.exprSetParent(this);
  }

  /** {@inheritDoc} */
  @Override
  public XObject execute(final XPathContext xctxt) throws javax.xml.transform.TransformerException {

    return operate(m_right.execute(xctxt));
  }

  /**
   * Apply the operation to two operands, and return the result.
   *
   * @param right non-null reference to the evaluated right operand.
   * @return non-null reference to the XObject that represents the result of the operation.
   * @throws javax.xml.transform.TransformerException in case of error
   */
  public abstract XObject operate(XObject right) throws javax.xml.transform.TransformerException;

  /** {@inheritDoc} */
  @Override
  public void callVisitors(final XPathVisitor visitor) {
    if (visitor.visitUnaryOperation()) {
      m_right.callVisitors(visitor);
    }
  }

  /** {@inheritDoc} */
  @Override
  public boolean deepEquals(final Expression expr) {
    if (!isSameClass(expr)) return false;

    if (!m_right.deepEquals(((UnaryOperation) expr).m_right)) return false;

    return true;
  }
}
