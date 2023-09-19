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
