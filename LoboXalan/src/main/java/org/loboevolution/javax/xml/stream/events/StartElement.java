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

import org.loboevolution.javax.xml.namespace.QName;
import org.loboevolution.javax.xml.namespace.NamespaceContext;

import java.util.Map;
import java.util.Iterator;

public interface StartElement extends XMLEvent {

  /**
   * Get the name of this event
   * @return the qualified name of this event
   */
  public QName getName();

  /**
   * Returns an Iterator of non-namespace declared attributes declared on
   * this START_ELEMENT,
   * returns an empty iterator if there are no attributes.  The
   * iterator must contain only implementations of the org.loboevolution.javax.xml.stream.Attribute
   * interface.   Attributes are fundamentally unordered and may not be reported
   * in any order.
   *
   * @return a readonly Iterator over Attribute interfaces, or an
   * empty iterator
   */
  public Iterator getAttributes();

  /**
   * Returns an Iterator of namespaces declared on this element.
   * This Iterator does not contain previously declared namespaces
   * unless they appear on the current START_ELEMENT.
   * Therefore this list may contain redeclared namespaces and duplicate namespace
   * declarations. Use the getNamespaceContext() method to get the
   * current context of namespace declarations.
   *
   * <p>The iterator must contain only implementations of the
   * org.loboevolution.javax.xml.stream.Namespace interface.
   *
   * <p>A Namespace isA Attribute.  One
   * can iterate over a list of namespaces as a list of attributes.
   * However this method returns only the list of namespaces
   * declared on this START_ELEMENT and does not
   * include the attributes declared on this START_ELEMENT.
   *
   * Returns an empty iterator if there are no namespaces.
   *
   * @return a readonly Iterator over Namespace interfaces, or an
   * empty iterator
   *
   */
  public Iterator getNamespaces();

  /**
   * Returns the attribute referred to by this name
   * @param name the qname of the desired name
   * @return the attribute corresponding to the name value or null
   */
  public Attribute getAttributeByName(QName name);

  /**
   * Gets a read-only namespace context. If no context is
   * available this method will return an empty namespace context.
   * The NamespaceContext contains information about all namespaces
   * in scope for this StartElement.
   *
   * @return the current namespace context
   */
  public NamespaceContext getNamespaceContext();

  /**
   * Gets the value that the prefix is bound to in the
   * context of this element.  Returns null if
   * the prefix is not bound in this context
   * @param prefix the prefix to lookup
   * @return the uri bound to the prefix or null
   */
  public String getNamespaceURI(String prefix);
}
