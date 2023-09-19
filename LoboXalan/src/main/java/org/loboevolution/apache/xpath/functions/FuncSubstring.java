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
import org.loboevolution.apache.xpath.objects.XObject;
import org.loboevolution.apache.xpath.objects.XString;
import org.loboevolution.apache.xpath.res.XPATHErrorResources;
import org.loboevolution.apache.xpath.res.XPATHMessages;

/** Execute the Substring() function. */
public class FuncSubstring extends Function3Args {

  /** {@inheritDoc} */
  @Override
  public XObject execute(XPathContext xctxt) throws org.loboevolution.javax.xml.transform.TransformerException {

    XString s1 = m_arg0.execute(xctxt).xstr();
    double start = m_arg1.execute(xctxt).num();
    int lenOfS1 = s1.length();
    XString substr;

    if (lenOfS1 <= 0) {
      return XString.EMPTYSTRING;
    }

    int startIndex;

    if (Double.isNaN(start)) {

      // Double.MIN_VALUE doesn't work with math below
      // so just use a big number and hope I never get caught.
      start = -1000000;
      startIndex = 0;
    } else {
      start = Math.round(start);
      startIndex = (start > 0) ? (int) start - 1 : 0;
    }

    if (null != m_arg2) {
      double len = m_arg2.num(xctxt);
      if (len < 1) {
        return XString.EMPTYSTRING;
      }

      int end = (int) (Math.round(len) + start) - 1;

      // Normalize end index.
      if (end < 0) end = 0;
      else if (end > lenOfS1) end = lenOfS1;

      if (startIndex > lenOfS1) startIndex = lenOfS1;

      substr = s1.substring(startIndex, end);
    } else {
      if (startIndex > lenOfS1) startIndex = lenOfS1;
      substr = s1.substring(startIndex);
    }

    return substr;
  }

  /** {@inheritDoc} */
  @Override
  public void checkNumberArgs(int argNum) throws WrongNumberArgsException {
    if (argNum < 2) reportWrongNumberArgs();
  }

  /** {@inheritDoc} */
  @Override
  protected void reportWrongNumberArgs() throws WrongNumberArgsException {
    throw new WrongNumberArgsException(
        XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_TWO_OR_THREE, null));
  }
}
