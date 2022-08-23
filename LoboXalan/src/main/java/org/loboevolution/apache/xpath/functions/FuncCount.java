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
import org.loboevolution.apache.xml.dtm.DTMIterator;

/** Execute the Count() function. */
public class FuncCount extends FunctionOneArg {

  /** {@inheritDoc} */
  @Override
  public XObject execute(XPathContext xctxt) throws org.loboevolution.javax.xml.transform.TransformerException {
    DTMIterator nl = m_arg0.asIterator(xctxt, xctxt.getCurrentNode());
    int i = nl.getLength();
    nl.detach();

    return new XNumber(i);
  }
}
