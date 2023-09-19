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

/**
 * This interface declares the constants used in this API.
 * Numbers in the range 0 to 256 are reserved for the specification,
 * user defined events must use event codes outside that range.
 *
 * @since 1.6
 */

public interface XMLStreamConstants {
  /**
   * Indicates an event is a start element
   * @see org.loboevolution.javax.xml.stream.events.StartElement
   */
  public static final int START_ELEMENT=1;
  /**
   * Indicates an event is an end element
   * @see org.loboevolution.javax.xml.stream.events.EndElement
   */
  public static final int END_ELEMENT=2;
  /**
   * Indicates an event is a processing instruction
   * @see org.loboevolution.javax.xml.stream.events.ProcessingInstruction
   */
  public static final int PROCESSING_INSTRUCTION=3;

  /**
   * Indicates an event is characters
   * @see org.loboevolution.javax.xml.stream.events.Characters
   */
  public static final int CHARACTERS=4;

  /**
   * Indicates an event is a comment
   * @see org.loboevolution.javax.xml.stream.events.Comment
   */
  public static final int COMMENT=5;

  /**
   * The characters are white space
   * (see [XML], 2.10 "White Space Handling").
   * Events are only reported as SPACE if they are ignorable white
   * space.  Otherwise they are reported as CHARACTERS.
   * @see org.loboevolution.javax.xml.stream.events.Characters
   */
  public static final int SPACE=6;

  /**
   * Indicates an event is a start document
   * @see org.loboevolution.javax.xml.stream.events.StartDocument
   */
  public static final int START_DOCUMENT=7;

  /**
   * Indicates an event is an end document
   * @see org.loboevolution.javax.xml.stream.events.EndDocument
   */
  public static final int END_DOCUMENT=8;

  /**
   * Indicates an event is an entity reference
   * @see org.loboevolution.javax.xml.stream.events.EntityReference
   */
  public static final int ENTITY_REFERENCE=9;

  /**
   * Indicates an event is an attribute
   * @see org.loboevolution.javax.xml.stream.events.Attribute
   */
  public static final int ATTRIBUTE=10;

  /**
   * Indicates an event is a DTD
   * @see org.loboevolution.javax.xml.stream.events.DTD
   */
  public static final int DTD=11;

  /**
   * Indicates an event is a CDATA section
   * @see org.loboevolution.javax.xml.stream.events.Characters
   */
  public static final int CDATA=12;

  /**
   * Indicates the event is a namespace declaration
   *
   * @see org.loboevolution.javax.xml.stream.events.Namespace
   */
  public static final int NAMESPACE=13;

  /**
   * Indicates a Notation
   * @see org.loboevolution.javax.xml.stream.events.NotationDeclaration
   */
  public static final int NOTATION_DECLARATION=14;

  /**
   * Indicates a Entity Declaration
   * @see org.loboevolution.javax.xml.stream.events.NotationDeclaration
   */
  public static final int ENTITY_DECLARATION=15;
}
