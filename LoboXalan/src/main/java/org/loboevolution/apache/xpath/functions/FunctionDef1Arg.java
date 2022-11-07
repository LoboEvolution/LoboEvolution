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

import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xpath.objects.XString;
import org.loboevolution.apache.xpath.res.XPATHErrorResources;
import org.loboevolution.apache.xpath.res.XPATHMessages;
import org.loboevolution.apache.xml.dtm.DTM;

/** Base class for functions that accept one argument that can be defaulted if not specified. */
public class FunctionDef1Arg extends FunctionOneArg {

  /**
   * Execute the first argument expression that is expected to return a nodeset. If the argument is
   * null, then return the current context node.
   *
   * @param xctxt Runtime XPath context.
   * @return The first node of the executed nodeset, or the current context node if the first
   *     argument is null.
   * @throws org.loboevolution.javax.xml.transform.TransformerException if an error occurs while executing the
   *     argument expression.
   */
  protected int getArg0AsNode(XPathContext xctxt) throws org.loboevolution.javax.xml.transform.TransformerException {

    return (null == m_arg0) ? xctxt.getCurrentNode() : m_arg0.asNode(xctxt);
  }

  /**
   * Execute the first argument expression that is expected to return a string. If the argument is
   * null, then get the string value from the current context node.
   *
   * @param xctxt Runtime XPath context.
   * @return The string value of the first argument, or the string value of the current context node
   *     if the first argument is null.
   * @throws org.loboevolution.javax.xml.transform.TransformerException if an error occurs while executing the
   *     argument expression.
   */
  protected XString getArg0AsString(XPathContext xctxt)
      throws org.loboevolution.javax.xml.transform.TransformerException {
    if (null == m_arg0) {
      int currentNode = xctxt.getCurrentNode();
      if (DTM.NULL == currentNode) {
        return XString.EMPTYSTRING;
      }
      DTM dtm = xctxt.getDTM(currentNode);
      return dtm.getStringValue(currentNode);
    }

    return m_arg0.execute(xctxt).xstr();
  }

  /**
   * Execute the first argument expression that is expected to return a number. If the argument is
   * null, then get the number value from the current context node.
   *
   * @param xctxt Runtime XPath context.
   * @return The number value of the first argument, or the number value of the current context node
   *     if the first argument is null.
   * @throws org.loboevolution.javax.xml.transform.TransformerException if an error occurs while executing the
   *     argument expression.
   */
  protected double getArg0AsNumber(XPathContext xctxt)
      throws org.loboevolution.javax.xml.transform.TransformerException {

    if (null == m_arg0) {
      int currentNode = xctxt.getCurrentNode();
      if (DTM.NULL == currentNode) {
        return 0;
      }
      DTM dtm = xctxt.getDTM(currentNode);
      XString str = dtm.getStringValue(currentNode);

      return str.toDouble();
    }
    return m_arg0.execute(xctxt).num();
  }

  /** {@inheritDoc} */
  @Override
  public void checkNumberArgs(int argNum) throws WrongNumberArgsException {
    if (argNum > 1) reportWrongNumberArgs();
  }

  /** {@inheritDoc} */
  @Override
  protected void reportWrongNumberArgs() throws WrongNumberArgsException {
    throw new WrongNumberArgsException(
        XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_ZERO_OR_ONE, null));
  }

  /** {@inheritDoc} */
  @Override
  public boolean canTraverseOutsideSubtree() {
    return null != m_arg0 && super.canTraverseOutsideSubtree();
  }
}
