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
import org.loboevolution.apache.xpath.XPathVisitor;
import org.loboevolution.apache.xml.utils.XMLCharacterRecognizer;

/**
 * This class represents an XPath string object, and is capable of converting the string to other
 * types, such as a number.
 */
public class XString extends XObject {

  /** Empty string XString object */
  public static final XString EMPTYSTRING = new XString("");

  /**
   * Construct a XNodeSet object.
   *
   * @param val String object this will wrap.
   */
  public XString(String val) {
    super(val);
  }

  /** {@inheritDoc} */
  @Override
  public int getType() {
    return CLASS_STRING;
  }

  /** {@inheritDoc} */
  @Override
  public String getTypeString() {
    return "#STRING";
  }

  /**
   * Tell if this object contains a java String object.
   *
   * @return true if this XMLString can return a string without creating one.
   */
  public boolean hasString() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public double num() {
    return toDouble();
  }

  /**
   * Convert a string to a double -- Allowed input is in fixed notation ddd.fff.
   *
   * @return A double value representation of the string, or return Double.NaN if the string can not
   *     be converted.
   */
  public double toDouble() {
    /*
     * XMLCharacterRecognizer.isWhiteSpace(char c) methods treats the following
     * characters as white space characters. ht - horizontal tab, nl - newline , cr
     * - carriage return and sp - space trim() methods by default also takes care of
     * these white space characters So trim() method is used to remove leading and
     * trailing white spaces.
     */
    XString s = trim();
    double result = Double.NaN;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c != '-' && c != '.' && (c < 0X30 || c > 0x39)) {
        // The character is not a '-' or a '.' or a digit
        // then return NaN because something is wrong.
        return result;
      }
    }
    try {
      result = Double.parseDouble(s.toString());
    } catch (NumberFormatException e) {
      // ignore
    }

    return result;
  }

  /** {@inheritDoc} */
  @Override
  public boolean bool() {
    return str().length() > 0;
  }

  /** {@inheritDoc} */
  @Override
  public XString xstr() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public String str() {
    return (null != m_obj) ? ((String) m_obj) : "";
  }

  /**
   * Returns the length of this string.
   *
   * @return the length of the sequence of characters represented by this object.
   */
  public int length() {
    return str().length();
  }

  /**
   * Returns the character at the specified index. An index ranges from <code>0</code> to <code>
   * length() - 1</code>. The first character of the sequence is at index <code>0</code>, the next
   * at index <code>1</code>, and so on, as for array indexing.
   *
   * @param index the index of the character.
   * @return the character at the specified index of this string. The first character is at index
   *     <code>0</code>.
   * @exception IndexOutOfBoundsException if the <code>index</code> argument is negative or not less
   *     than the length of this string.
   */
  public char charAt(int index) {
    return str().charAt(index);
  }

  /**
   * Copies characters from this string into the destination character array.
   *
   * @param srcBegin index of the first character in the string to copy.
   * @param srcEnd index after the last character in the string to copy.
   * @param dst the destination array.
   * @param dstBegin the start offset in the destination array.
   * @exception IndexOutOfBoundsException If any of the following is true:
   *     <ul>
   *       <li><code>srcBegin</code> is negative.
   *       <li><code>srcBegin</code> is greater than <code>srcEnd</code>
   *       <li><code>srcEnd</code> is greater than the length of this string
   *       <li><code>dstBegin</code> is negative
   *       <li><code>dstBegin+(srcEnd-srcBegin)</code> is larger than <code>dst.length</code>
   *     </ul>
   *
   * @exception NullPointerException if <code>dst</code> is <code>null</code>
   */
  public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
    str().getChars(srcBegin, srcEnd, dst, dstBegin);
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(XObject obj2) {

    // In order to handle the 'all' semantics of
    // nodeset comparisons, we always call the
    // nodeset function.
    int t = obj2.getType();
    try {
      if (XObject.CLASS_NODESET == t) return obj2.equals(this);
      // If at least one object to be compared is a boolean, then each object
      // to be compared is converted to a boolean as if by applying the
      // boolean function.
      else if (XObject.CLASS_BOOLEAN == t) return obj2.bool() == bool();
      // Otherwise, if at least one object to be compared is a number, then each
      // object
      // to be compared is converted to a number as if by applying the number
      // function.
      else if (XObject.CLASS_NUMBER == t) return obj2.num() == num();
    } catch (org.loboevolution.javax.xml.transform.TransformerException te) {
      throw new WrappedRuntimeException(te);
    }

    // Otherwise, both objects to be compared are converted to strings as
    // if by applying the string function.
    return xstr().equals(obj2.xstr());
  }

  /**
   * Compares this string to the specified <code>String</code>. The result is <code>true</code> if
   * and only if the argument is not <code>null</code> and is a <code>String</code> object that
   * represents the same sequence of characters as this object.
   *
   * @param obj2 the object to compare this <code>String</code> against.
   * @return <code>true</code> if the <code>String</code>s are equal; <code>false</code> otherwise.
   * @see String#compareTo(String)
   * @see String#equalsIgnoreCase(String)
   */
  public boolean equals(String obj2) {
    return str().equals(obj2);
  }

  /**
   * Compares this string to the specified object. The result is <code>true</code> if and only if
   * the argument is not <code>null</code> and is a <code>String</code> object that represents the
   * same sequence of characters as this object.
   *
   * @param obj2 the object to compare this <code>String</code> against.
   * @return <code>true</code> if the <code>String </code>are equal; <code>false</code> otherwise.
   * @see String#compareTo(String)
   * @see String#equalsIgnoreCase(String)
   */
  public boolean equals(XString obj2) {
    if (obj2 != null) {
      if (!obj2.hasString()) {
        return obj2.equals(str());
      }
      return str().equals(obj2.toString());
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object obj2) {

    if (null == obj2) return false;

    // In order to handle the 'all' semantics of
    // nodeset comparisons, we always call the
    // nodeset function.
    else if (obj2 instanceof XNodeSet) return obj2.equals(this);
    else if (obj2 instanceof XNumber) return obj2.equals(this);
    else return str().equals(obj2.toString());
  }

  /**
   * Tests if this string starts with the specified prefix beginning a specified index.
   *
   * @param prefix the prefix.
   * @param toffset where to begin looking in the string.
   * @return <code>true</code> if the character sequence represented by the argument is a prefix of
   *     the substring of this object starting at index <code>toffset</code>; <code>false
   *     </code> otherwise. The result is <code>false</code> if <code>toffset</code> is negative or
   *     greater than the length of this <code>String</code> object; otherwise the result is the
   *     same as the result of the expression
   *     <pre>
   *         this.subString(toffset).startsWith(prefix)
   *         </pre>
   *
   * @exception NullPointerException if <code>prefix</code> is <code>null</code>.
   */
  public boolean startsWith(XString prefix, int toffset) {

    int to = toffset;
    int tlim = this.length();
    int po = 0;
    int pc = prefix.length();

    // Note: toffset might be near -1>>>1.
    if ((toffset < 0) || (toffset > tlim - pc)) {
      return false;
    }

    while (--pc >= 0) {
      if (this.charAt(to) != prefix.charAt(po)) {
        return false;
      }

      to++;
      po++;
    }

    return true;
  }

  /**
   * Tests if this string starts with the specified prefix.
   *
   * @param prefix the prefix.
   * @return <code>true</code> if the character sequence represented by the argument is a prefix of
   *     the character sequence represented by this string; <code>false</code> otherwise. Note also
   *     that <code>true</code> will be returned if the argument is an empty string or is equal to
   *     this <code>String</code> object as determined by the {@link #equals(Object)} method.
   * @exception NullPointerException if <code>prefix</code> is <code>null</code>.
   */
  public boolean startsWith(XString prefix) {
    return startsWith(prefix, 0);
  }

  /**
   * Returns the index within this string of the first occurrence of the specified substring. The
   * integer returned is the smallest value <i>k</i> such that:
   *
   * <blockquote>
   *
   * <pre>
   * this.startsWith(str, <i>k</i>)
   * </pre>
   *
   * </blockquote>
   *
   * is <code>true</code>.
   *
   * @param str any string.
   * @return if the string argument occurs as a substring within this object, then the index of the
   *     first character of the first such substring is returned; if it does not occur as a
   *     substring, <code>-1</code> is returned.
   * @exception NullPointerException if <code>str</code> is <code>null</code>.
   */
  public int indexOf(XString str) {
    return str().indexOf(str.toString());
  }

  /**
   * Returns a new string that is a substring of this string. The substring begins with the
   * character at the specified index and extends to the end of this string.
   *
   * <p>Examples:
   *
   * <blockquote>
   *
   * <pre>
   * "unhappy".substring(2) returns "happy"
   * "Harbison".substring(3) returns "bison"
   * "emptiness".substring(9) returns "" (an empty string)
   * </pre>
   *
   * </blockquote>
   *
   * @param beginIndex the beginning index, inclusive.
   * @return the specified substring.
   * @exception IndexOutOfBoundsException if <code>beginIndex</code> is negative or larger than the
   *     length of this <code>String</code> object.
   */
  public XString substring(int beginIndex) {
    return new XString(str().substring(beginIndex));
  }

  /**
   * Returns a new string that is a substring of this string. The substring begins at the specified
   * <code>beginIndex</code> and extends to the character at index <code>endIndex - 1
   * </code>. Thus the length of the substring is <code>endIndex-beginIndex</code>.
   *
   * @param beginIndex the beginning index, inclusive.
   * @param endIndex the ending index, exclusive.
   * @return the specified substring.
   * @exception IndexOutOfBoundsException if the <code>beginIndex</code> is negative, or <code>
   *     endIndex</code> is larger than the length of this <code>String</code> object, or <code>
   *     beginIndex</code> is larger than <code>endIndex</code>.
   */
  public XString substring(int beginIndex, int endIndex) {
    return new XString(str().substring(beginIndex, endIndex));
  }

  /**
   * Removes white space from both ends of this string.
   *
   * @return this string, with white space removed from the front and end.
   */
  public XString trim() {
    return new XString(str().trim());
  }

  /**
   * Returns whether the specified <var>ch</var> conforms to the XML 1.0 definition of whitespace.
   * Refer to <A href="http://www.w3.org/TR/1998/REC-xml-19980210#NT-S">the definition of <CODE>S
   * </CODE></A> for details.
   *
   * @param ch Character to check as XML whitespace.
   * @return =true if <var>ch</var> is XML whitespace; otherwise =false.
   */
  private static boolean isSpace(char ch) {
    return XMLCharacterRecognizer.isWhiteSpace(ch); // Take the easy way out for now.
  }

  /**
   * Conditionally trim all leading and trailing whitespace in the specified String. All strings of
   * white space are replaced by a single space character (#x20), except spaces after punctuation
   * which receive double spaces if doublePunctuationSpaces is true. This function may be useful to
   * a formatter, but to get first class results, the formatter should probably do it's own white
   * space handling based on the semantics of the formatting object.
   *
   * @param trimHead Trim leading whitespace?
   * @param trimTail Trim trailing whitespace?
   * @param doublePunctuationSpaces Use double spaces for punctuation?
   * @return The trimmed string.
   */
  public XString fixWhiteSpace(
      boolean trimHead, boolean trimTail, boolean doublePunctuationSpaces) {

    // %OPT% !!!!!!!
    int len = this.length();
    char[] buf = new char[len];

    this.getChars(0, len, buf, 0);

    boolean edit = false;
    int s;

    for (s = 0; s < len; s++) {
      if (isSpace(buf[s])) {
        break;
      }
    }

    /* replace S to ' '. and ' '+ -> single ' '. */
    int d = s;
    boolean pres = false;

    for (; s < len; s++) {
      char c = buf[s];

      if (isSpace(c)) {
        if (!pres) {
          if (' ' != c) {
            edit = true;
          }

          buf[d++] = ' ';

          if (doublePunctuationSpaces && (s != 0)) {
            char prevChar = buf[s - 1];

            if (!((prevChar == '.') || (prevChar == '!') || (prevChar == '?'))) {
              pres = true;
            }
          } else {
            pres = true;
          }
        } else {
          edit = true;
          pres = true;
        }
      } else {
        buf[d++] = c;
        pres = false;
      }
    }

    if (trimTail && 1 <= d && ' ' == buf[d - 1]) {
      edit = true;

      d--;
    }

    int start = 0;

    if (trimHead && 0 < d && ' ' == buf[0]) {
      edit = true;

      start++;
    }

    return edit ? new XString(new String(buf, start, d - start)) : this;
  }

  /** {@inheritDoc} */
  @Override
  public void callVisitors(XPathVisitor visitor) {
    visitor.visitStringLiteral();
  }
}
