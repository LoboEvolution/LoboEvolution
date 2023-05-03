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

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.gui.LocalHtmlRendererConfig;
import org.loboevolution.html.dom.nodeimpl.DOMImplementationImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.http.UserAgentContext;
import org.xml.sax.*;
import org.xml.sax.ext.LexicalHandler;

/**
 * <p>XMLContentHandler class.</p>
 */
public class XMLContentHandler implements ContentHandler, LexicalHandler, ErrorHandler {

    private Document document = null;

    private Node currentNode = null;

    private Locator lastLocator = null;

    /** {@inheritDoc} */
    @Override
    public void setDocumentLocator(Locator locator) {
        this.lastLocator = locator;
    }

    /** {@inheritDoc} */
    @Override
    public void startDocument() {
        document = null;
    }

    /** {@inheritDoc} */
    @Override
    public void endDocument() {
        currentNode = null;
    }

    /** {@inheritDoc} */
    @Override
    public void startPrefixMapping(String prefix, String uri) {}

    /** {@inheritDoc} */
    @Override
    public void endPrefixMapping(String prefix) {}

    /** {@inheritDoc} */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        if (document == null) {
            documentElement(uri, localName, qName, atts);
        } else {
            newElement(uri, localName, qName, atts);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void endElement(String uri, String localName, String qName) {
        currentNode = currentNode.getParentNode();
    }

    /** {@inheritDoc} */
    @Override
    public void characters(char[] ch, int start, int length) {}

    /** {@inheritDoc} */
    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) {}

    /** {@inheritDoc} */
    @Override
    public void processingInstruction(String target, String data) {}

    /** {@inheritDoc} */
    @Override
    public void skippedEntity(String name) {}

    /** {@inheritDoc} */
    @Override
    public void warning(SAXParseException exception) {}

    /** {@inheritDoc} */
    @Override
    public void error(SAXParseException exception) {}

    /** {@inheritDoc} */
    @Override
    public void fatalError(SAXParseException exception) {}

    /** {@inheritDoc} */
    @Override
    public void startDTD(String name, String publicId, String systemId) {}

    /** {@inheritDoc} */
    @Override
    public void endDTD() {}

    /** {@inheritDoc} */
    @Override
    public void startEntity(String name) {}

    /** {@inheritDoc} */
    @Override
    public void endEntity(String name) {}

    /** {@inheritDoc} */
    @Override
    public void startCDATA() {}

    /** {@inheritDoc} */
    @Override
    public void endCDATA() {}

    /** {@inheritDoc} */
    @Override
    public void comment(char[] ch, int start, int length) {}

    public Document getDocument() {
        return this.document;
    }

    private void documentElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        document = createDocument(uri, qName);
        Element element = document.getDocumentElement();
        currentNode = element;
        setAttributes(element, atts);

        if(qName != null) {
            newElement(uri, localName, qName, atts);
        }
    }

    private void newElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        Element element = document.createElementNS(uri, qName);
        setAttributes(element, atts);
        appendChild(element);
        currentNode = element;
    }

    private void setAttributes(Element element, Attributes atts) {
        int len = atts.getLength();
        for (int i = 0; i < len; i++) {
            String namespaceURI = atts.getURI(i);
            String value = atts.getValue(i);
            String attrQName = atts.getQName(i);
            Attr attr = document.createAttributeNS(namespaceURI, attrQName);
            attr.setValue(value);
            element.getAttributes().setNamedItem(attr);
            if ("ID".equals(atts.getType(i))
                    || ("id".equals(attrQName) && element.getNamespaceURI() != document.getNamespaceURI())) {
                element.setIdAttributeNode(attr, true);
            }
        }
    }

    private Document createDocument(String uri, String qName) {
        UserAgentContext context = new UserAgentContext(new LocalHtmlRendererConfig());
        context.setUserAgentEnabled(false);
        DOMImplementationImpl domImpl = new DOMImplementationImpl(context);
        DocumentType doctype = domImpl.createDocumentType("HTML", null, null);
        return domImpl.createDocument(uri, "HTML", doctype);
    }

    private void appendChild(Node node) throws SAXException {
        try {
            document.appendChild(node);
        } catch (DOMException e) {
            error("Error appending child " + node.getNodeName() + " to " + currentNode.getNodeName(), e);
        }
    }

    private void error(String message, Exception ex) throws SAXException {
        if (lastLocator == null) {
            throw new SAXException(message, ex);
        } else {
            throw new SAXParseException(message, lastLocator, ex);
        }
    }
}