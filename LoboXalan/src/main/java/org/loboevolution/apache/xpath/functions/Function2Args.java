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
package org.loboevolution.apache.xpath.functions;

import org.loboevolution.apache.xpath.Expression;
import org.loboevolution.apache.xpath.XPathVisitor;
import org.loboevolution.apache.xpath.res.XPATHMessages;

/** Base class for functions that accept two arguments. */
public class Function2Args extends FunctionOneArg {

  /**
   * The second argument passed to the function (at index 1).
   *
   * @serial
   */
  Expression m_arg1;

  /** {@inheritDoc} */
  @Override
  public void setArg(Expression arg, int argNum) throws WrongNumberArgsException {

    // System.out.println("argNum: "+argNum);
    if (argNum == 0) super.setArg(arg, argNum);
    else if (1 == argNum) {
      m_arg1 = arg;
      arg.exprSetParent(this);
    } else reportWrongNumberArgs();
  }

  /** {@inheritDoc} */
  @Override
  public void checkNumberArgs(int argNum) throws WrongNumberArgsException {
    if (argNum != 2) reportWrongNumberArgs();
  }

  /** {@inheritDoc} */
  @Override
  protected void reportWrongNumberArgs() throws WrongNumberArgsException {
    throw new WrongNumberArgsException(XPATHMessages.createXPATHMessage("two", null));
  }

  /** {@inheritDoc} */
  @Override
  public boolean canTraverseOutsideSubtree() {
    return super.canTraverseOutsideSubtree() || m_arg1.canTraverseOutsideSubtree();
  }

  /** {@inheritDoc} */
  @Override
  public void callArgVisitors(XPathVisitor visitor) {
    super.callArgVisitors(visitor);
    if (null != m_arg1) m_arg1.callVisitors(visitor);
  }

  /** {@inheritDoc} */
  @Override
  public boolean deepEquals(Expression expr) {
    if (!super.deepEquals(expr)) return false;

    if (null != m_arg1) {
      if (null == ((Function2Args) expr).m_arg1) return false;

      if (!m_arg1.deepEquals(((Function2Args) expr).m_arg1)) return false;
    } else if (null != ((Function2Args) expr).m_arg1) return false;

    return true;
  }
}
