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

/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package org.loboevolution.javax.xml.stream.events;

public interface Characters extends XMLEvent {
  /**
   * Get the character data of this event
   */
  public String getData();

  /**
   * Returns true if this set of Characters
   * is all whitespace.  Whitespace inside a document
   * is reported as CHARACTERS.  This method allows
   * checking of CHARACTERS events to see if they
   * are composed of only whitespace characters
   */
  public boolean isWhiteSpace();

  /**
   * Returns true if this is a CData section.  If this
   * event is CData its event type will be CDATA
   *
   * If org.loboevolution.javax.xml.stream.isCoalescing is set to true CDATA Sections
   * that are surrounded by non CDATA characters will be reported
   * as a single Characters event. This method will return false
   * in this case.
   */
  public boolean isCData();

  /**
   * Return true if this is ignorableWhiteSpace.  If
   * this event is ignorableWhiteSpace its event type will
   * be SPACE.
   */
  public boolean isIgnorableWhiteSpace();

}
