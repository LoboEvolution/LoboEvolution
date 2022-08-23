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
import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xpath.XPathVisitor;

/**
 * This class represents an XPath number, and is capable of converting the number to other types,
 * such as a string.
 */
public class XNumber extends XObject {

  /**
   * Value of the XNumber object.
   *
   * @serial
   */
  final double m_val;

  /**
   * Construct a XNodeSet object.
   *
   * @param d Value of the object
   */
  public XNumber(double d) {
    super();

    m_val = d;
  }

  /** {@inheritDoc} */
  @Override
  public int getType() {
    return CLASS_NUMBER;
  }

  /** {@inheritDoc} */
  @Override
  public String getTypeString() {
    return "#NUMBER";
  }

  /** {@inheritDoc} */
  @Override
  public double num() {
    return m_val;
  }

  /** {@inheritDoc} */
  @Override
  public double num(XPathContext xctxt) throws org.loboevolution.javax.xml.transform.TransformerException {

    return m_val;
  }

  /** {@inheritDoc} */
  @Override
  public boolean bool() {
    return !Double.isNaN(m_val) && (m_val != 0.0);
  }

  /** {@inheritDoc} */
  @Override
  public String str() {

    if (Double.isNaN(m_val)) {
      return "NaN";
    } else if (Double.isInfinite(m_val)) {
      if (m_val > 0) {
        return "Infinity";
      }
      return "-Infinity";
    }

    String s = Double.toString(m_val);
    int len = s.length();

    if (s.charAt(len - 2) == '.' && s.charAt(len - 1) == '0') {
      s = s.substring(0, len - 2);

      if (s.equals("-0")) return "0";

      return s;
    }

    int e = s.indexOf('E');

    if (e < 0) {
      if (s.charAt(len - 1) == '0') {
        return s.substring(0, len - 1);
      }
      return s;
    }

    int exp = Integer.parseInt(s.substring(e + 1));
    String sign;

    if (s.charAt(0) == '-') {
      sign = "-";
      s = s.substring(1);

      --e;
    } else sign = "";

    int nDigits = e - 2;

    if (exp >= nDigits) return sign + s.substring(0, 1) + s.substring(2, e) + zeros(exp - nDigits);

    // Eliminate trailing 0's - bugzilla 14241
    while (s.charAt(e - 1) == '0') e--;

    if (exp > 0)
      return sign + s.substring(0, 1) + s.substring(2, 2 + exp) + "." + s.substring(2 + exp, e);

    return sign + "0." + zeros(-1 - exp) + s.substring(0, 1) + s.substring(2, e);
  }

  /**
   * Return a string of '0' of the given length
   *
   * @param n Length of the string to be returned
   * @return a string of '0' with the given length
   */
  private static String zeros(int n) {
    if (n < 1) return "";

    char[] buf = new char[n];

    for (int i = 0; i < n; i++) {
      buf[i] = '0';
    }

    return new String(buf);
  }

  /** {@inheritDoc} */
  @Override
  public Object object() {
    if (null == m_obj) setObject(new Double(m_val));
    return m_obj;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(XObject obj2) {

    // In order to handle the 'all' semantics of
    // nodeset comparisons, we always call the
    // nodeset function.
    int t = obj2.getType();
    try {
      if (t == XObject.CLASS_NODESET) return obj2.equals(this);
      else if (t == XObject.CLASS_BOOLEAN) return obj2.bool() == bool();
      else return m_val == obj2.num();
    } catch (org.loboevolution.javax.xml.transform.TransformerException te) {
      throw new WrappedRuntimeException(te);
    }
  }

  /** {@inheritDoc} */
  @Override
  public boolean isStableNumber() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public void callVisitors(XPathVisitor visitor) {
    visitor.visitNumberLiteral();
  }
}
