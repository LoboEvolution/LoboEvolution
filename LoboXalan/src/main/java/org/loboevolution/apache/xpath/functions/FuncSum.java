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
import org.loboevolution.apache.xpath.objects.XNumber;
import org.loboevolution.apache.xpath.objects.XObject;
import org.loboevolution.apache.xpath.objects.XString;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMIterator;

/** Execute the Sum() function. */
public class FuncSum extends FunctionOneArg {

  /**
   * Execute the function. The function must return a valid object.
   *
   * @param xctxt The current execution context.
   * @return A valid XObject.
   * @throws org.loboevolution.javax.xml.transform.TransformerException in case of error
   */
  @Override
  public XObject execute(XPathContext xctxt) throws org.loboevolution.javax.xml.transform.TransformerException {

    DTMIterator nodes = m_arg0.asIterator(xctxt, xctxt.getCurrentNode());
    double sum = 0.0;
    int pos;

    while (DTM.NULL != (pos = nodes.nextNode())) {
      DTM dtm = nodes.getDTM(pos);
      XString s = dtm.getStringValue(pos);

      if (null != s) sum += s.toDouble();
    }
    nodes.detach();

    return new XNumber(sum);
  }
}
