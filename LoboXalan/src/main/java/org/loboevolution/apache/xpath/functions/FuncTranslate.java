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

/** Execute the Translate() function. */
public class FuncTranslate extends Function3Args {

  /** {@inheritDoc} */
  @Override
  public XObject execute(XPathContext xctxt) throws org.loboevolution.javax.xml.transform.TransformerException {

    String theFirstString = m_arg0.execute(xctxt).str();
    String theSecondString = m_arg1.execute(xctxt).str();
    String theThirdString = m_arg2.execute(xctxt).str();
    int theFirstStringLength = theFirstString.length();
    int theThirdStringLength = theThirdString.length();

    // A vector to contain the new characters. We'll use it to construct
    // the result string.
    StringBuilder sbuffer = new StringBuilder();

    for (int i = 0; i < theFirstStringLength; i++) {
      char theCurrentChar = theFirstString.charAt(i);
      int theIndex = theSecondString.indexOf(theCurrentChar);

      if (theIndex < 0) {

        // Didn't find the character in the second string, so it
        // is not translated.
        sbuffer.append(theCurrentChar);
      } else if (theIndex < theThirdStringLength) {

        // OK, there's a corresponding character in the
        // third string, so do the translation...
        sbuffer.append(theThirdString.charAt(theIndex));
      } else {

        // There's no corresponding character in the
        // third string, since it's shorter than the
        // second string. In this case, the character
        // is removed from the output string, so don't
        // do anything.
      }
    }

    return new XString(sbuffer.toString());
  }
}
