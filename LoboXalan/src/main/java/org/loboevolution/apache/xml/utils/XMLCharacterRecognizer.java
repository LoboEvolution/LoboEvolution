/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
