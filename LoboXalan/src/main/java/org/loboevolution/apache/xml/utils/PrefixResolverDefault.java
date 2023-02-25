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
