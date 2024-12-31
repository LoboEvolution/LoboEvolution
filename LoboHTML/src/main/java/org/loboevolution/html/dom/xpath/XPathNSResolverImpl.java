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
        String namespace = null;

        if ("xml".equals(prefix)) {
            namespace = "http://www.w3.org/XML/1998/namespace";
        } else {
            int type;
            while (parent != null
                    && (((type = parent.getNodeType()) == Node.ELEMENT_NODE) ||
                    ((type = parent.getNodeType()) == Node.DOCUMENT_NODE) ||
                    ((type = parent.getNodeType()) == Node.ATTRIBUTE_NODE) ||
                    (type == Node.ENTITY_REFERENCE_NODE))) {

                switch (type) {
                    case Node.DOCUMENT_NODE:
                        final Document document = (Document) parent;
                        final Element docelm = document.getDocumentElement();
                        if (docelm != null && docelm.getNodeName().indexOf(prefix.toUpperCase() + ":") == 0) {
                            return docelm.getNamespaceURI();
                        }
                        break;
                    case Node.ELEMENT_NODE:
                        if (parent.getNodeName().indexOf(prefix + ":") == 0) {
                            return parent.getNamespaceURI();
                        }
                        final NamedNodeMap nnm = parent.getAttributes();
                        if (nnm != null) {
                            for (int i = 0; i < nnm.getLength(); i++) {
                                final Node attr = nnm.item(i);
                                final String aname = attr.getNodeName();
                                final boolean isPrefix = aname.startsWith("xmlns:");
                                if (isPrefix || aname.equals("xmlns")) {
                                    final int index = aname.indexOf(':');
                                    final String p = isPrefix ? aname.substring(index + 1) : "";
                                    if (p.equals(prefix)) {
                                        namespace = attr.getNodeValue();
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    case Node.ATTRIBUTE_NODE:
                        if (prefix.equals(parent.getPrefix())) {
                            return parent.getNamespaceURI();
                        }
                        break;
                    default:
                        break;
                }
                parent = parent.getParentNode();
            }
        }
        return namespace;
    }
}
