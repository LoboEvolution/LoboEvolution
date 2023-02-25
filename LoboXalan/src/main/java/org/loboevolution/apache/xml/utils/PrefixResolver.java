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
