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
public interface EntityDeclaration extends XMLEvent {

  /**
   * The entity's public identifier, or null if none was given
   * @return the public ID for this declaration or null
   */
  String getPublicId();

  /**
   * The entity's system identifier.
   * @return the system ID for this declaration or null
   */
  String getSystemId();

  /**
   * The entity's name
   * @return the name, may not be null
   */
  String getName();

  /**
   * The name of the associated notation.
   * @return the notation name
   */
  String getNotationName();

  /**
   * The replacement text of the entity.
   * This method will only return non-null
   * if this is an internal entity.
   * @return null or the replacment text
   */
  String getReplacementText();

  /**
   * Get the base URI for this reference
   * or null if this information is not available
   * @return the base URI or null
   */
  String getBaseURI();

}
