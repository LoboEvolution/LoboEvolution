/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.html.dom.xpath;

import org.loboevolution.common.Strings;
import org.loboevolution.html.node.*;
import org.loboevolution.html.xpath.XPathNSResolver;

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
    public XPathNSResolverImpl(final Node xpathExpressionContext) {
        this.parent = xpathExpressionContext;

    }

    /** {@inheritDoc} */
    @Override
    public String lookupNamespaceURI(final String prefix) {
        if (Strings.isBlank(prefix)) return null;

        Node current = parent;

        while (current != null) {
            final int type = current.getNodeType();

            switch (type) {
                case Node.ELEMENT_NODE:
                    final Element element = (Element) current;
                    final String nodeName = element.getNodeName();
                    final int colonIdx = nodeName.indexOf(':');
                    if (colonIdx > 0 && nodeName.substring(0, colonIdx).equals(prefix)) {
                        return element.getNamespaceURI();
                    }
                    final NamedNodeMap nnm = element.getAttributes();
                    if (nnm != null) {
                        for (int i = 0; i < nnm.getLength(); i++) {
                            final Node attr = nnm.item(i);
                            final String aname = attr.getNodeName();
                            if (aname.startsWith("xmlns:")) {
                                final int attrColonIdx = aname.indexOf(':');
                                if (attrColonIdx >= 0) {
                                    final String attrPrefix = aname.substring(attrColonIdx + 1);
                                    if (prefix.equals(attrPrefix)) {
                                        return attr.getNodeValue();
                                    }
                                }
                            } else if (aname.equals("xmlns") && prefix.isEmpty()) {
                                return attr.getNodeValue();
                            }
                        }
                    }
                    break;
                case Node.DOCUMENT_NODE:
                    final Document document = (Document) current;
                    final Element docelm = document.getDocumentElement();
                    if (docelm != null) {
                        final String docNodeName = docelm.getNodeName();
                        final int docColonIdx = docNodeName.indexOf(':');
                        if (docColonIdx > 0 && docNodeName.substring(0, docColonIdx).equals(prefix)) {
                            return docelm.getNamespaceURI();
                        }
                    }
                    break;
                case Node.ATTRIBUTE_NODE:
                    final Element attrOwner = (Element) ((Attr) current).getOwnerElement();
                    if (attrOwner != null) {
                        current = attrOwner;
                    } else {
                        current = current.getParentNode();
                    }
                    break;
                case Node.TEXT_NODE:
                case Node.CDATA_SECTION_NODE:
                    current = current.getParentNode();
                    continue;
                case Node.COMMENT_NODE:
                    if (prefix.equals(current.getPrefix())) {
                        return current.getNamespaceURI();
                    }
                    break;
                default:
                    break;
            }
            current = current.getParentNode();
        }
        return null;
    }
}
