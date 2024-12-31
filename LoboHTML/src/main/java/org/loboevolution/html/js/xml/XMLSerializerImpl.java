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

package org.loboevolution.html.js.xml;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.Nodes;
import org.loboevolution.html.HTMLTag;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.js.xml.XMLSerializer;
import org.loboevolution.js.AbstractScriptableDelegate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>XMLSerializer class.</p>
 */
@Slf4j
public class XMLSerializerImpl extends AbstractScriptableDelegate implements XMLSerializer {

    private static final Set<HTMLTag> NON_EMPTY = new HashSet<>(Arrays.asList(
            HTMLTag.HTML, HTMLTag.A, HTMLTag.AUDIO, HTMLTag.BLOCKQUOTE,
            HTMLTag.BODY, HTMLTag.B, HTMLTag.BUTTON, HTMLTag.CANVAS,
            HTMLTag.CAPTION, HTMLTag.CENTER, HTMLTag.CODE, HTMLTag.DEFS,
            HTMLTag.DIR, HTMLTag.DIV, HTMLTag.EMBED, HTMLTag.FIELDSET,
            HTMLTag.FORM,HTMLTag.H1,HTMLTag.H2, HTMLTag.H3,
            HTMLTag.H4, HTMLTag.H5,HTMLTag.H6, HTMLTag.HEAD,
            HTMLTag.IFRAME,HTMLTag.I,HTMLTag.LI, HTMLTag.LEGEND, HTMLTag.MARQUEE,
            HTMLTag.MENU,HTMLTag.NOSCRIPT, HTMLTag.OBJECT, HTMLTag.OPTGROUP,
            HTMLTag.OPTION, HTMLTag.P, HTMLTag.SCRIPT, HTMLTag.SELECT,
            HTMLTag.SMALL, HTMLTag.SPAN, HTMLTag.STRIKE, HTMLTag.STRONG,
            HTMLTag.STYLE, HTMLTag.SUB, HTMLTag.SUP, HTMLTag.TITLE,
            HTMLTag.TABLE, HTMLTag.COL, HTMLTag.COLGROUP, HTMLTag.TBODY,
            HTMLTag.TR, HTMLTag.TEXTAREA, HTMLTag.TFOOT, HTMLTag.TH,
            HTMLTag.VAR, HTMLTag.VIDEO
    ));


    /**
     * {@inheritDoc}
     */
    @Override
    public String serializeToString(final Node node) {
        try {
            final StringBuilder buff = new StringBuilder();
            if (node instanceof Document || node instanceof DocumentFragment) {
                final NodeListImpl children = (NodeListImpl) node.getChildNodes();
                children.forEach(elem -> getXString(elem, true, buff, true));
            }

            if (node instanceof CDATASection cdataSection) {
                buff.append("<![CDATA[");
                if (cdataSection.getData() != null) {
                    buff.append(cdataSection.getData());
                    buff.append("]]>");
                } else {
                    buff.append("<>&?]]>");
                }
            }
            if (node instanceof ProcessingInstruction processingInstruction) {
                buff.append("<?");
                buff.append(processingInstruction.getTarget());
                buff.append(" ");
                buff.append(processingInstruction.getData());
                buff.append("?>");
            } else if (node instanceof Element) {
                getXString(node, true, buff, true);
            }
            return buff.toString();
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    public static void getXString(final Node node, final boolean withoutNamespaces, final StringBuilder buff, boolean endTag) {
        try {

            buff.append("<")
                    .append(namespace(node.getNodeName(), withoutNamespaces))
                    .append(" ")
                    .append("xmlns=\"http://www.w3.org/1999/xhtml\"");

            if (node.hasAttributes()) {
                buff.append(" ");

                final NamedNodeMap attributes = node.getAttributes();
                if (attributes != null) {
                    for (final Node nodeAttr : Nodes.iterable(attributes)) {
                        final Attr attrItem = (Attr) nodeAttr;
                        final String name = namespace(attrItem.getNodeName(), withoutNamespaces);
                        final String value = attrItem.getNodeValue();

                        buff.append(name)
                                .append("=")
                                .append("\"")
                                .append(value)
                                .append("\"");
                    }
                }
            }

            if (node.hasChildNodes()) {
                buff.append(">");

                final NodeList children = node.getChildNodes();
                final int childrenCount = children.getLength();

                if (childrenCount == 1) {
                    final Node item = children.item(0);
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

				final NodeListImpl child = (NodeListImpl) children;
				final AtomicBoolean tag = new AtomicBoolean(endTag);
				child.forEach(item -> {
					final int itemType = item.getNodeType();
					if (itemType == Node.DOCUMENT_NODE || itemType == Node.ELEMENT_NODE) {
						getXString(item, withoutNamespaces, buff, tag.get());
					}
				});

            } else {
                if (node.getNodeValue() == null) {

                    if(NON_EMPTY.contains(HTMLTag.get(node.getNodeName().toUpperCase()))){
                        buff.append(">").append("</").append(node.getNodeName()).append(">");
                    } else{
                        buff.append(" />");
                    }

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

        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private static String namespace(final String nodeName, final boolean withoutNamespace) {
        if (withoutNamespace && nodeName.contains(":")) {
            return nodeName.substring(nodeName.indexOf(":") + 1);
        }

        return nodeName;
    }

}

