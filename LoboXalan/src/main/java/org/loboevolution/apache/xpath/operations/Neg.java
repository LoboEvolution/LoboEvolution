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
package org.loboevolution.apache.xpath.operations;

import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xpath.objects.XNumber;
import org.loboevolution.apache.xpath.objects.XObject;

/** The unary '-' operation expression executer. */
public class Neg extends UnaryOperation {

  /** {@inheritDoc} */
  @Override
  public XObject operate(XObject right) throws org.loboevolution.javax.xml.transform.TransformerException {
    return new XNumber(-right.num());
  }

  /** {@inheritDoc} */
  @Override
  public double num(XPathContext xctxt) throws org.loboevolution.javax.xml.transform.TransformerException {

    return -(m_right.num(xctxt));
  }
}
