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
package org.loboevolution.apache.xpath.functions;

import org.loboevolution.apache.xpath.Expression;
import org.loboevolution.apache.xpath.XPathVisitor;
import org.loboevolution.apache.xpath.res.XPATHErrorResources;
import org.loboevolution.apache.xpath.res.XPATHMessages;

/** Base class for functions that accept an undetermined number of multiple arguments. */
public class FunctionMultiArgs extends Function3Args {

  /**
   * Argument expressions that are at index 3 or greater.
   *
   * @serial
   */
  Expression[] m_args;

  /** {@inheritDoc} */
  @Override
  public void setArg(Expression arg, int argNum) throws WrongNumberArgsException {

    if (argNum < 3) super.setArg(arg, argNum);
    else {
      if (null == m_args) {
        m_args = new Expression[1];
        m_args[0] = arg;
      } else {

        // Slow but space conservative.
        Expression[] args = new Expression[m_args.length + 1];

        System.arraycopy(m_args, 0, args, 0, m_args.length);

        args[m_args.length] = arg;
        m_args = args;
      }
      arg.exprSetParent(this);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void checkNumberArgs(int argNum) throws WrongNumberArgsException {}

  /** {@inheritDoc} */
  @Override
  protected void reportWrongNumberArgs() throws WrongNumberArgsException {
    String fMsg =
        XPATHMessages.createXPATHMessage(
            XPATHErrorResources.ER_INCORRECT_PROGRAMMER_ASSERTION,
            new Object[] {
              "Programmer's assertion:  the method FunctionMultiArgs.reportWrongNumberArgs() should never be called."
            });

    throw new RuntimeException(fMsg);
  }

  /** {@inheritDoc} */
  @Override
  public boolean canTraverseOutsideSubtree() {

    if (super.canTraverseOutsideSubtree()) {
      return true;
    }

    for (Expression m_arg : m_args) {
      if (m_arg.canTraverseOutsideSubtree()) {
        return true;
      }
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  public void callArgVisitors(XPathVisitor visitor) {
    super.callArgVisitors(visitor);
    if (null != m_args) {
      for (Expression m_arg : m_args) {
        m_arg.callVisitors(visitor);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public boolean deepEquals(Expression expr) {
    if (!super.deepEquals(expr)) return false;

    FunctionMultiArgs fma = (FunctionMultiArgs) expr;
    if (null != m_args) {
      int n = m_args.length;
      if ((null == fma) || (fma.m_args.length != n)) return false;

      for (int i = 0; i < n; i++) {
        if (!m_args[i].deepEquals(fma.m_args[i])) return false;
      }

    } else if (null != fma.m_args) {
      return false;
    }

    return true;
  }
}
