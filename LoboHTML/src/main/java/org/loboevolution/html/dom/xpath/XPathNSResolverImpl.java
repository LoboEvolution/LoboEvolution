/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2022 Lobo Evolution
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
/*
 * $Id: XPathNSResolverImpl.java 1225426 2011-12-29 04:13:08Z mrglavas $
 */

package org.loboevolution.html.dom.xpath;

import org.apache.xml.utils.Constants;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.xpath.XPathNSResolver;
import org.loboevolution.type.NodeType;

/**
 * <p>XPathNSResolverImpl class.</p>
 */
public class XPathNSResolverImpl implements XPathNSResolver {

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

        String namespace = null;

        if (prefix.equals("xml")) {
            namespace = Constants.S_XMLNAMESPACEURI;
        } else {
            NodeType type;
            while ((null != parent) && (null == namespace)
                    && (((type = parent.getNodeType()) == NodeType.ELEMENT_NODE)
                    || (type == NodeType.ENTITY_REFERENCE_NODE))) {

                if (type == NodeType.ELEMENT_NODE) {
                    if (parent.getNodeName().indexOf(prefix + ":") == 0) {
                        return parent.getNamespaceURI();
                    }
                    NamedNodeMap nnm = ((Element) parent).getAttributes();
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
}
