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
