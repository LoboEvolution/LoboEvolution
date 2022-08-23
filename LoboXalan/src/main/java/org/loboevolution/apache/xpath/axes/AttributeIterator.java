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
package org.loboevolution.apache.xpath.axes;

import org.loboevolution.apache.xml.dtm.Axis;
import org.loboevolution.apache.xpath.compiler.Compiler;
import org.loboevolution.apache.xml.dtm.DTM;

/** This class implements an optimized iterator for attribute axes patterns. */
public class AttributeIterator extends ChildTestIterator {

  /**
   * Create a AttributeIterator object.
   *
   * @param compiler A reference to the Compiler that contains the op map.
   * @param opPos The position within the op map, which contains the location path expression for
   *     this itterator.
   * @throws org.loboevolution.javax.xml.transform.TransformerException if any
   */
  AttributeIterator(Compiler compiler, int opPos, int analysis)
      throws org.loboevolution.javax.xml.transform.TransformerException {
    super(compiler, opPos, analysis);
  }

  /** {@inheritDoc} */
  @Override
  protected int getNextNode() {
    m_lastFetched =
        (DTM.NULL == m_lastFetched)
            ? m_cdtm.getFirstAttribute(m_context)
            : m_cdtm.getNextAttribute(m_lastFetched);
    return m_lastFetched;
  }

  /** {@inheritDoc} */
  @Override
  public int getAxis() {
    return Axis.ATTRIBUTE;
  }
}
