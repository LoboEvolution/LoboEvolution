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
import org.loboevolution.apache.xml.dtm.DTM;

/** Execute the Namespace() function. */
public class FuncNamespace extends FunctionDef1Arg {

  /** {@inheritDoc} */
  @Override
  public XObject execute(XPathContext xctxt) throws org.loboevolution.javax.xml.transform.TransformerException {

    int context = getArg0AsNode(xctxt);

    String s;
    if (context != DTM.NULL) {
      DTM dtm = xctxt.getDTM(context);
      int t = dtm.getNodeType(context);
      if (t == DTM.ELEMENT_NODE) {
        s = dtm.getNamespaceURI(context);
      } else if (t == DTM.ATTRIBUTE_NODE) {

        // This function always returns an empty string for namespace nodes.
        // We check for those here. Fix inspired by Davanum Srinivas.

        s = dtm.getNodeName(context);
        if (s.startsWith("xmlns:") || s.equals("xmlns")) return XString.EMPTYSTRING;

        s = dtm.getNamespaceURI(context);
      } else return XString.EMPTYSTRING;
    } else return XString.EMPTYSTRING;

    return (null == s) ? XString.EMPTYSTRING : new XString(s);
  }
}
