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
package org.loboevolution.apache.xpath.functions;

import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xpath.objects.XBoolean;
import org.loboevolution.apache.xpath.objects.XObject;

/** Execute the Contains() function. */
public class FuncContains extends Function2Args {

  /** {@inheritDoc} */
  @Override
  public XObject execute(final XPathContext xctxt) throws javax.xml.transform.TransformerException {

    final String s1 = m_arg0.execute(xctxt).str();
    final String s2 = m_arg1.execute(xctxt).str();

    // Add this check for JDK consistency for empty strings.
    if (s1.length() == 0 && s2.length() == 0) return XBoolean.S_TRUE;

    final int index = s1.indexOf(s2);

    return (index > -1) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
  }
}
