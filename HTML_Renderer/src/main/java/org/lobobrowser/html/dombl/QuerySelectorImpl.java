/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.dombl;

import java.util.ArrayList;

import org.lobobrowser.html.domfilter.ElementFilter;
import org.lobobrowser.html.domimpl.DOMNodeListImpl;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class QuerySelectorImpl.
 */
public class QuerySelectorImpl {

    /**
     * Document query selector.
     *
     * @param document
     *            the document
     * @param selectors
     *            the selectors
     * @return the element
     */
    public Element documentQuerySelector(Document document, String selectors) {

        HTMLDocumentImpl doc = (HTMLDocumentImpl) document;
        Element element = null;
        if (selectors.startsWith("#")) {
            element = doc.getElementById(selectors.replace("#", ""));
        } else if (selectors.contains(".")) {
            String[] str = selectors.split("\\.");
            String str0 = str[0].trim();
            String str1 = str[1].trim();
            if ("".equals(str0) || (str0 == null)) {
                element = (Element) doc.getElementsByClassName(
                        selectors.replace(".", "")).item(0);
            } else {
                NodeList node = doc.getElementsByTagName(str0);
                for (int i = 0; i < node.getLength(); i++) {
                    if (str0.equals(node.item(i).getNodeName())) {
                        NamedNodeMap attributes = node.item(i).getAttributes();
                        for (int s = 0; s < attributes.getLength(); s++) {
                            Attr attr = (Attr) attributes.item(s);
                            if (attr.getName().equals("class")
                                    && attr.getValue().equals(str1)) {
                                element = (Element) doc.getElementsByTagName(
                                        node.item(i).getNodeName()).item(i);
                                break;
                            }
                        }
                    }
                }
            }
        } else if (selectors.contains(">")) {

            String[] str = selectors.split(">");
            String str0 = str[0].trim();
            String str1 = str[1].trim();
            NodeList node = doc.getElementsByTagName(str1);

            for (int i = 0; i < node.getLength(); i++) {
                if (str0.equals(node.item(i).getParentNode().getNodeName())) {
                    element = (Element) doc.getElementsByTagName(
                            node.item(i).getNodeName()).item(i);
                    break;
                }
            }
        } else if (selectors.contains("[") && selectors.contains("]")) {

            String[] str = selectors.split("\\[");
            String str0 = str[0].trim();
            String str1 = str[1].trim();
            NodeList node = doc.getElementsByTagName(str0);

            for (int i = 0; i < node.getLength(); i++) {
                NamedNodeMap attributes = node.item(i).getAttributes();
                for (int s = 0; s < attributes.getLength(); s++) {
                    Attr attr = (Attr) attributes.item(s);
                    if (attr.getName().equals(str1.replace("]", "").trim())) {
                        element = (Element) doc.getElementsByTagName(
                                node.item(i).getNodeName()).item(i);
                        break;
                    }
                }
            }
        } else if (selectors.contains(",")) {

            String[] str = selectors.split("\\,");
            String str0 = str[0].trim();
            String str1 = str[1].trim();
            NodeList node = doc.getNodeList(new ElementFilter());
            for (int i = 0; i < node.getLength(); i++) {
                if (str0.equals(node.item(i).getNodeName())
                        || str1.equals(node.item(i).getNodeName())) {
                    element = (Element) doc.getElementsByTagName(
                            node.item(i).getNodeName()).item(0);
                    break;
                }
            }
        } else {
            element = (Element) doc.getElementsByTagName(selectors).item(0);
        }
        return element;
    }

    /**
     * Document query selector all.
     *
     * @param document
     *            the document
     * @param selectors
     *            the selectors
     * @return the node list
     */
    public NodeList documentQuerySelectorAll(Document document, String selectors) {
        HTMLDocumentImpl doc = (HTMLDocumentImpl) document;
        ArrayList<Node> listNode = new ArrayList<Node>();
        NodeList list = null;

        if (selectors.contains(".")) {

            String[] str = selectors.split("\\.");
            String str0 = str[0].trim();
            String str1 = str[1].trim();
            if ("".equals(str0) || (str0 == null)) {
                list = doc.getElementsByClassName(selectors.replace(".", ""));
            } else {

                NodeList node = doc.getElementsByTagName(str0);

                for (int i = 0; i < node.getLength(); i++) {
                    if (str0.equals(node.item(i).getNodeName())) {
                        NamedNodeMap attributes = node.item(i).getAttributes();
                        for (int s = 0; s < attributes.getLength(); s++) {
                            Attr attr = (Attr) attributes.item(s);
                            if (attr.getName().equals("class")
                                    && attr.getValue().equals(str1)) {
                                listNode.add(node.item(i));
                                list = new DOMNodeListImpl(listNode);
                            }
                        }
                    }
                }
            }
        } else if (selectors.contains("[") && selectors.contains("]")) {

            String[] str = selectors.split("\\[");
            String str0 = str[0].trim();
            String str1 = str[1].trim();
            NodeList node = doc.getElementsByTagName(str0);

            for (int i = 0; i < node.getLength(); i++) {
                NamedNodeMap attributes = node.item(i).getAttributes();
                for (int s = 0; s < attributes.getLength(); s++) {
                    Attr attr = (Attr) attributes.item(s);
                    if (attr.getName().equals(str1.replace("]", "").trim())) {
                        listNode.add(node.item(i));
                        list = new DOMNodeListImpl(listNode);
                    }
                }
            }
        } else if (selectors.contains(">")) {

            String[] str = selectors.split(">");
            String str0 = str[0].trim();
            String str1 = str[1].trim();
            NodeList node = doc.getElementsByTagName(str1);

            for (int i = 0; i < node.getLength(); i++) {
                if (str0.equals(node.item(i).getParentNode().getNodeName())) {
                    listNode.add(node.item(i));
                    list = new DOMNodeListImpl(listNode);
                }
            }
        } else if (selectors.contains(",")) {

            String[] str = selectors.split("\\,");
            NodeList node = doc.getNodeList(new ElementFilter());
            for (int i = 0; i < node.getLength(); i++) {

                for (int j = 0; j < str.length; j++) {
                    if (node.item(i).getNodeName().equals(str[j].trim())) {
                        listNode.add(node.item(i));
                    }
                }
            }
            list = new DOMNodeListImpl(listNode);
        } else {
            list = doc.getElementsByClassName(selectors);
        }
        return list;
    }
}
