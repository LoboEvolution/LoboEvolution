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
package org.loboevolution.apache.xpath.functions;

import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xpath.axes.SubContextList;
import org.loboevolution.apache.xpath.compiler.Compiler;
import org.loboevolution.apache.xpath.objects.XNumber;
import org.loboevolution.apache.xpath.objects.XObject;

/** Execute the Position() function. */
public class FuncPosition extends Function {

  private boolean m_isTopLevel;

  /** {@inheritDoc} */
  @Override
  public void postCompileStep(final Compiler compiler) {
    m_isTopLevel = compiler.getLocationPathDepth() == -1;
  }

  /**
   * Get the position in the current context node list.
   *
   * @param xctxt Runtime XPath context.
   * @return The current position of the iteration in the context node list, or -1 if there is no
   *     active context node list.
   */
  public int getPositionInContextNodeList(final XPathContext xctxt) {
    final SubContextList iter = m_isTopLevel ? null : xctxt.getSubContextList();

    if (null != iter) {
      return iter.getProximityPosition(xctxt);
    }
    return -1;
  }

  /** {@inheritDoc} */
  @Override
  public XObject execute(final XPathContext xctxt) throws org.loboevolution.javax.xml.transform.TransformerException {
    final double pos = getPositionInContextNodeList(xctxt);

    return new XNumber(pos);
  }
}
