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
package org.loboevolution.apache.xpath.operations;

import org.loboevolution.apache.xpath.Expression;
import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xpath.XPathVisitor;
import org.loboevolution.apache.xpath.objects.XObject;

/** The baseclass for a binary operation. */
public class Operation extends Expression {

  /**
   * The left operand expression.
   *
   * @serial
   */
  protected Expression m_left;

  /**
   * The right operand expression.
   *
   * @serial
   */
  protected Expression m_right;

  /** {@inheritDoc} */
  @Override
  public boolean canTraverseOutsideSubtree() {

    if (null != m_left && m_left.canTraverseOutsideSubtree()) return true;

    if (null != m_right && m_right.canTraverseOutsideSubtree()) return true;

    return false;
  }

  /**
   * Set the left and right operand expressions for this operation.
   *
   * @param l The left expression operand.
   * @param r The right expression operand.
   */
  public void setLeftRight(Expression l, Expression r) {
    m_left = l;
    m_right = r;
    l.exprSetParent(this);
    r.exprSetParent(this);
  }

  /** {@inheritDoc} */
  @Override
  public XObject execute(XPathContext xctxt) throws org.loboevolution.javax.xml.transform.TransformerException {

    XObject left = m_left.execute(xctxt, true);
    XObject right = m_right.execute(xctxt, true);

    XObject result = operate(left, right);
    left.detach();
    right.detach();
    return result;
  }

  /**
   * Apply the operation to two operands, and return the result.
   *
   * @param left non-null reference to the evaluated left operand.
   * @param right non-null reference to the evaluated right operand.
   * @return non-null reference to the XObject that represents the result of the operation.
   * @throws org.loboevolution.javax.xml.transform.TransformerException in case of error
   */
  public XObject operate(XObject left, XObject right)
      throws org.loboevolution.javax.xml.transform.TransformerException {
    return null; // no-op
  }

  /** {@inheritDoc} */
  @Override
  public void callVisitors(XPathVisitor visitor) {
    if (visitor.visitBinaryOperation()) {
      m_left.callVisitors(visitor);
      m_right.callVisitors(visitor);
    }
  }

  /** {@inheritDoc} */
  @Override
  public boolean deepEquals(Expression expr) {
    if (!isSameClass(expr)) return false;

    if (!m_left.deepEquals(((Operation) expr).m_left)) return false;

    if (!m_right.deepEquals(((Operation) expr).m_right)) return false;

    return true;
  }
}
