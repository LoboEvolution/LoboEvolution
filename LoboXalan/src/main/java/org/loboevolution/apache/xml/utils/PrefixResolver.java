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
 * The class that implements this interface can resolve prefixes to namespaces. Examples would
 * include resolving the meaning of a prefix at a particular point in a document, or mapping the
 * prefixes used in an XPath expression.
 */
public interface PrefixResolver {

  /**
   * Given a namespace, get the corresponding prefix. This assumes that the PrefixResolver holds its
   * own namespace context, or is a namespace context itself.
   *
   * @param prefix The prefix to look up, which may be an empty string ("") for the default
   *     Namespace.
   * @return The associated Namespace URI, or null if the prefix is undeclared in this context.
   */
  String getNamespaceForPrefix(String prefix);

  /**
   * Given a namespace, get the corresponding prefix, based on the context node.
   *
   * @param prefix The prefix to look up, which may be an empty string ("") for the default
   *     Namespace.
   * @param context The node context from which to look up the URI.
   * @return The associated Namespace URI as a string, or null if the prefix is undeclared in this
   *     context.
   */
  String getNamespaceForPrefix(String prefix, org.loboevolution.html.node.Node context);

  boolean handlesNullPrefixes();
}
