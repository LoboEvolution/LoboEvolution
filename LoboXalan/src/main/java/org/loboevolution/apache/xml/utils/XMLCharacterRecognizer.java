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
package org.loboevolution.apache.xml.utils;

/**
 * Class used to verify whether the specified <var>ch</var> conforms to the XML 1.0 definition of
 * whitespace.
 */
public class XMLCharacterRecognizer {

  /**
   * Returns whether the specified <var>ch</var> conforms to the XML 1.0 definition of whitespace.
   * Refer to <A href="http://www.w3.org/TR/1998/REC-xml-19980210#NT-S">the definition of <CODE>S
   * </CODE></A> for details.
   *
   * @param ch Character to check as XML whitespace.
   * @return =true if <var>ch</var> is XML whitespace; otherwise =false.
   */
  public static boolean isWhiteSpace(char ch) {
    return (ch == 0x20) || (ch == 0x09) || (ch == 0xD) || (ch == 0xA);
  }

  /**
   * Tell if the string is whitespace.
   *
   * @param chars CharSequence to check as XML whitespace.
   * @return True if characters in buffer are XML whitespace, false otherwise
   */
  public static boolean isWhiteSpace(CharSequence chars) {
    return chars.chars().allMatch(i -> XMLCharacterRecognizer.isWhiteSpace((char) i));
  }
}
