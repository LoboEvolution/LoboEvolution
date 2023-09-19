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

