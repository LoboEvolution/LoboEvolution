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

package org.loboevolution.javax.xml.stream;

import org.loboevolution.javax.xml.stream.events.XMLEvent;

import java.util.Iterator;

public interface XMLEventReader extends Iterator {
  /**
   * Get the next XMLEvent
   * @see XMLEvent
   * @throws XMLStreamException if there is an error with the underlying XML.
   * @return XMLEvent
   */
  public XMLEvent nextEvent() throws XMLStreamException;

  /**
   * Check if there are more events.
   * Returns true if there are more events and false otherwise.
   * @return true if the event reader has more events, false otherwise
   */
  public boolean hasNext();

  /**
   * Check the next XMLEvent without reading it from the stream.
   * Returns null if the stream is at EOF or has no more XMLEvents.
   * A call to peek() will be equal to the next return of next().
   * @see XMLEvent
   * @throws XMLStreamException
   * @return XMLEvent
   */
  public XMLEvent peek() throws XMLStreamException;

  /**
   * Reads the content of a text-only element. Precondition:
   * the current event is START_ELEMENT. Postcondition:
   * The current event is the corresponding END_ELEMENT.
   * @throws XMLStreamException if the current event is not a START_ELEMENT
   * or if a non text element is encountered
   */
  public String getElementText() throws XMLStreamException;

  /**
   * Skips any insignificant space events until a START_ELEMENT or
   * END_ELEMENT is reached. If anything other than space characters are
   * encountered, an exception is thrown. This method should
   * be used when processing element-only content because
   * the parser is not able to recognize ignorable whitespace if
   * the DTD is missing or not interpreted.
   * @throws XMLStreamException if anything other than space characters are encountered
   */
  public XMLEvent nextTag() throws XMLStreamException;

  /**
   * Get the value of a feature/property from the underlying implementation
   * @param name The name of the property
   * @return The value of the property
   * @throws IllegalArgumentException if the property is not supported
   */
  public Object getProperty(java.lang.String name) throws java.lang.IllegalArgumentException;

  /**
   * Frees any resources associated with this Reader.  This method does not close the
   * underlying input source.
   * @throws XMLStreamException if there are errors freeing associated resources
   */
  public void close() throws XMLStreamException;
}
