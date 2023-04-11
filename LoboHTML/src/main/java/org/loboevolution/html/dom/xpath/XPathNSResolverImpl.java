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

package org.loboevolution.html.dom.xpath;

import org.loboevolution.common.Strings;
import org.loboevolution.html.node.*;
import org.loboevolution.html.xpath.XPathNSResolver;

/**
 * <p>XPathNSResolverImpl class.</p>
 */
public class XPathNSResolverImpl implements XPathNSResolver {

    private String
            S_XMLNAMESPACEURI = "http://www.w3.org/XML/1998/namespace",
            S_XSLNAMESPACEURL = "http://www.w3.org/1999/XSL/Transform",
            S_OLDXSLNAMESPACEURL = "http://www.w3.org/XSL/Transform/1.0";

    private Node parent;

    /**
     * <p>Constructor for XPathNSResolverImpl.</p>
     *
     * @param xpathExpressionContext a {@link org.loboevolution.html.node.Node} object.
     */
    public XPathNSResolverImpl(Node xpathExpressionContext) {
        this.parent = xpathExpressionContext;

    }

    /** {@inheritDoc} */
    @Override
    public String lookupNamespaceURI(String prefix) {
        if (Strings.isBlank(prefix)) return null;
        String namespace = null;

        if ("xml".equals(prefix)) {
            namespace = S_XMLNAMESPACEURI;
        } else {
            int type;
            while (parent != null
                    && (((type = parent.getNodeType()) == Node.ELEMENT_NODE) ||
                    ((type = parent.getNodeType()) == Node.DOCUMENT_NODE) ||
                    ((type = parent.getNodeType()) == Node.ATTRIBUTE_NODE) ||
                    (type == Node.ENTITY_REFERENCE_NODE))) {

                switch (type) {
                    case Node.DOCUMENT_NODE:
                        Document document = (Document) parent;
                        Element docelm = document.getDocumentElement();
                        if (docelm != null && docelm.getNodeName().indexOf(prefix.toUpperCase() + ":") == 0) {
                            return docelm.getNamespaceURI();
                        }
                    case Node.ELEMENT_NODE:
                        if (parent.getNodeName().indexOf(prefix.toUpperCase() + ":") == 0) {
                            return parent.getNamespaceURI();
                        }
                        NamedNodeMap nnm = parent.getAttributes();
                        if (nnm != null) {
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
                    case Node.ATTRIBUTE_NODE:
                        if (prefix.equals(parent.getPrefix())) {
                            return parent.getNamespaceURI();
                        }
                    default:
                        break;
                }
                parent = parent.getParentNode();
            }
        }
        return namespace;
    }
}
