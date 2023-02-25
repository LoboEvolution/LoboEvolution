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

package org.loboevolution.html.js.xml;

import org.loboevolution.common.Nodes;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.html.node.js.xml.XMLSerializer;
import org.loboevolution.js.AbstractScriptableDelegate;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 * <p>XMLSerializer class.</p>
 */
public class XMLSerializerImpl extends AbstractScriptableDelegate implements XMLSerializer {

    /**
     * The Constant logger.
     */
    private static final Logger logger = Logger.getLogger(XMLSerializerImpl.class.getName());

    /**
     * {@inheritDoc}
     */
    @Override
    public String serializeToString(Node node) {
        try {
            StringBuffer buff = new StringBuffer();
			if (node instanceof Document) {
				NodeListImpl children = (NodeListImpl)node.getChildNodes();
				children.forEach(elem -> getXString((Element) elem, true, buff, true));
			} else{
				getXString((Element) node, true, buff, true);
			}
            return buff.toString();
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
        return "";
    }

    public static void getXString(Element node, boolean withoutNamespaces, StringBuffer buff, boolean endTag) {
        try {

            buff.append("<")
                    .append(namespace(node.getNodeName(), withoutNamespaces));

            if (node.hasAttributes()) {
                buff.append(" ");

				NamedNodeMap attributes = node.getAttributes();
                for (Node nodeAttr : Nodes.iterable(attributes)) {
                    Attr attrItem = (Attr) nodeAttr;
                    String name = namespace(attrItem.getNodeName(), withoutNamespaces);
                    String value = attrItem.getNodeValue();

                    buff.append(name)
                            .append("=")
                            .append("\"")
                            .append(value)
                            .append("\"");
                }
            }

            if (node.hasChildNodes()) {
                buff.append(">");

                NodeList children = node.getChildNodes();
                int childrenCount = children.getLength();

                if (childrenCount == 1) {
                    Node item = children.item(0);
                    if (item.getNodeType() == Node.TEXT_NODE) {
                        if (item.getNodeValue() == null) {
                            buff.append("/>");
                        } else {
                            buff.append(item.getNodeValue());
                            buff.append("</")
                                    .append(namespace(node.getNodeName(), withoutNamespaces))
                                    .append(">");
                        }

                        endTag = false;
                    }
                }

				NodeListImpl child = (NodeListImpl) children;
				AtomicBoolean tag = new AtomicBoolean(endTag);
				child.forEach(item -> {
					final int itemType = item.getNodeType();
					if (itemType == Node.DOCUMENT_NODE || itemType == Node.ELEMENT_NODE) {
						getXString((Element) item, withoutNamespaces, buff, tag.get());
					}
				});

            } else {
                if (node.getNodeValue() == null) {
                    buff.append("/>");
                } else {
                    buff.append(node.getNodeValue());
                    buff.append("</")
                            .append(namespace(node.getNodeName(), withoutNamespaces))
                            .append(">");
                }

                endTag = false;
            }

            if (endTag) {
                buff.append("</")
                        .append(namespace(node.getNodeName(), withoutNamespaces))
                        .append(">");
            }

        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }

    private static String namespace(String str, boolean withoutNamespace) {
        if (withoutNamespace && str.contains(":")) {
            return str.substring(str.indexOf(":") + 1);
        }

        return str;
    }

}

