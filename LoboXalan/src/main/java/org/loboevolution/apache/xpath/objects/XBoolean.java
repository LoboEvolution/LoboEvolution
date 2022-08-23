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
