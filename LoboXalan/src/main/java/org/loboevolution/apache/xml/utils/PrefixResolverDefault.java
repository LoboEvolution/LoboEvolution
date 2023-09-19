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

import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

/**
 * This class implements a generic PrefixResolver that can be used to perform prefix-to-namespace
 * lookup for the XPath object.
 */
public class PrefixResolverDefault implements PrefixResolver {

  /** The context to resolve the prefix from, if the context is not given. */
  final Node m_context;

  /**
   * Construct a PrefixResolverDefault object.
   *
   * @param xpathExpressionContext The context from which XPath expression prefixes will be
   *     resolved. Warning: This will not work correctly if xpathExpressionContext is an attribute
   *     node.
   */
  public PrefixResolverDefault(Node xpathExpressionContext) {
    m_context = xpathExpressionContext;
  }

  /** {@inheritDoc} */
  @Override
  public String getNamespaceForPrefix(String prefix) {
    return getNamespaceForPrefix(prefix, m_context);
  }

  /** {@inheritDoc} */
  @Override
  public String getNamespaceForPrefix(String prefix, Node namespaceContext) {

    Node parent = namespaceContext;
    String namespace = null;

    if (prefix.equals("xml")) {
      namespace = "http://www.w3.org/XML/1998/namespace";
    } else {
      int type;

      while ((null != parent)
          && (null == namespace)
          && (((type = parent.getNodeType()) == Node.ELEMENT_NODE)
              || (type == Node.ENTITY_REFERENCE_NODE))) {
        if (type == Node.ELEMENT_NODE) {
          if (parent.getNodeName().indexOf(prefix + ":") == 0) return parent.getNamespaceURI();
          NamedNodeMap nnm = parent.getAttributes();

          for (int i = 0; i < nnm.getLength(); i++) {
            Node attr = nnm.item(i);
            String aname = attr.getNodeName();
            boolean isPrefix = aname.startsWith("xmlns:");

            if (isPrefix || aname.equals("xmlns")) {
              int index = aname.indexOf(':');
              String p = isPrefix ? aname.substring(index + 1) : "";

              if (p.equals(prefix)) {
                namespace = attr.getNodeValue();

                break;
              }
            }
          }
        }

        parent = parent.getParentNode();
      }
    }

    return namespace;
  }

  /** {@inheritDoc} */
  @Override
  public boolean handlesNullPrefixes() {
    return false;
  }
}
