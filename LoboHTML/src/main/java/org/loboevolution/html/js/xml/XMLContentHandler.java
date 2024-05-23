/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.loboevolution.gui.LocalHtmlRendererConfig;
import org.loboevolution.html.dom.domimpl.HTMLProcessingInstruction;
import org.loboevolution.html.dom.nodeimpl.CommentImpl;
import org.loboevolution.html.dom.nodeimpl.DOMImplementationImpl;
import org.loboevolution.html.dom.nodeimpl.TextImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.http.UserAgentContext;
import org.xml.sax.*;
import org.xml.sax.ext.LexicalHandler;

import java.util.Objects;

/**
 * <p>XMLContentHandler class.</p>
 */
@Slf4j
public class XMLContentHandler implements ContentHandler, LexicalHandler, ErrorHandler {

    @Getter
    private Document document = null;

    private Node currentNode = null;

    @Setter
    private Locator documentLocator ;


    @Override
    public void startDocument() {
        final UserAgentContext context = new UserAgentContext(new LocalHtmlRendererConfig());
        context.setUserAgentEnabled(false);
        final DOMImplementationImpl domImpl = new DOMImplementationImpl(context);
        document = domImpl.createDocument(null, null, null);
        currentNode = document;
    }

    @Override
    public void endDocument() {}

    @Override
    public void startPrefixMapping(String prefix, String uri) {}

    @Override
    public void endPrefixMapping(String prefix) {}

    @Override
    public void startElement(String uri, String name, String qName, Attributes atts) throws SAXException {

        if (qName == null) {
            throw new SAXException("No Element name given");
        }

        final String localName;
        int idx = qName.indexOf(':');
        if (idx >= 0) {
            localName = qName.substring(idx + 1);
        } else {
            localName = qName;
        }

        Element element = document.createElementNS(null, localName);
        setAttributes(element, atts);
        currentNode.appendChild(element);

        if(currentNode instanceof Document)
            currentNode = element;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {}

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {
        currentNode.appendChild(new TextImpl(new String(chars, start, length)));
    }

    @Override
    public void ignorableWhitespace(char[] chars, int start, int length) {}

    @Override
    public void processingInstruction(String target, String data) {
        HTMLProcessingInstruction pi = new HTMLProcessingInstruction("");
        pi.setData(data);
        pi.setTarget(target);
        currentNode.appendChild(pi);
    }

    @Override
    public void skippedEntity(String name) {}

    @Override
    public void warning(SAXParseException exception) {
        log.warn("Ignored XML validation warning", exception);
    }

    @Override
    public void error(SAXParseException exception) throws SAXParseException {
        throw exception;
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXParseException {
        throw exception;
    }

    @Override
    public void startDTD(String name, String publicId, String systemId)  {}

    @Override
    public void endDTD() {}

    @Override
    public void startEntity(String name) { }

    @Override
    public void endEntity(String name) { }

    @Override
    public void startCDATA() {}

    @Override
    public void endCDATA() {}

    @Override
    public void comment(char[] chars, int start, int length) throws SAXException {
        currentNode.appendChild(new CommentImpl(new String(chars, start, length)));
    }

    private void setAttributes(final Element element, final Attributes atts) {
        final int len = atts.getLength();
        for (int i = 0; i < len; i++) {
            final String namespaceURI = atts.getURI(i);
            final String value = atts.getValue(i);
            final String attrQName = atts.getQName(i);
            final Attr attr = document.createAttributeNS(namespaceURI, attrQName);
            attr.setValue(value);
            element.getAttributes().setNamedItem(attr);
            if ("ID".equals(atts.getType(i))
                    || ("id".equals(attrQName) && !Objects.equals(element.getNamespaceURI(), document.getNamespaceURI()))) {
                element.setIdAttributeNode(attr, true);
            }
        }
    }
}