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
package org.loboevolution.apache.xpath.objects;

import org.loboevolution.apache.xml.utils.WrappedRuntimeException;

/**
 * This class represents an XPath boolean object, and is capable of converting the boolean to other
 * types, such as a string.
 */
public class XBoolean extends XObject {

  /** A true boolean object so we don't have to keep creating them. */
  public static final XBoolean S_TRUE = new XBooleanStatic(true);

  /** A true boolean object so we don't have to keep creating them. */
  public static final XBoolean S_FALSE = new XBooleanStatic(false);

  /**
   * Value of the object.
   *
   * @serial
   */
  private final boolean m_val;

  /**
   * Construct a XBoolean object.
   *
   * @param b Value of the boolean object
   */
  public XBoolean(boolean b) {

    super();

    m_val = b;
  }

  /** {@inheritDoc} */
  @Override
  public int getType() {
    return CLASS_BOOLEAN;
  }

  /** {@inheritDoc} */
  @Override
  public String getTypeString() {
    return "#BOOLEAN";
  }

  /** {@inheritDoc} */
  @Override
  public double num() {
    return m_val ? 1.0 : 0.0;
  }

  /** {@inheritDoc} */
  @Override
  public boolean bool() {
    return m_val;
  }

  /** {@inheritDoc} */
  @Override
  public String str() {
    return m_val ? "true" : "false";
  }

  /** {@inheritDoc} */
  @Override
  public Object object() {
    if (null == m_obj) setObject(m_val ? Boolean.TRUE : Boolean.FALSE);
    return m_obj;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(XObject obj2) {

    // In order to handle the 'all' semantics of
    // nodeset comparisons, we always call the
    // nodeset function.
    if (obj2.getType() == XObject.CLASS_NODESET) return obj2.equals(this);

    try {
      return m_val == obj2.bool();
    } catch (org.loboevolution.javax.xml.transform.TransformerException te) {
      throw new WrappedRuntimeException(te);
    }
  }
}
