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
import org.loboevolution.html.node.Document;
import org.xml.sax.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

/**
 * <p>XMLDocumentBuilder class.</p>
 */
@Slf4j
public class XMLDocumentBuilder {

    private final SAXParserFactory parserFactory;

    public XMLDocumentBuilder() {
        this.parserFactory = SAXParserFactory.newInstance();

        try {
            parserFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            parserFactory.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
            parserFactory.setFeature("http://xml.org/sax/features/xmlns-uris", false);
            parserFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
            parserFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            parserFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            parserFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            parserFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false);
            parserFactory.setXIncludeAware(false);
            parserFactory.setNamespaceAware(true);
        } catch (final SAXNotRecognizedException | SAXNotSupportedException | ParserConfigurationException e) {
            log.error(e.getMessage(), e);
        }
    }

    public Document parse(final InputSource is) throws SAXException, IOException {
        try {
            final SAXParser saxParser = parserFactory.newSAXParser();
            return parse(is, saxParser.getXMLReader());
        } catch (final Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return null;
    }

    private Document parse(final InputSource is, final XMLReader xmlReader) {
        try {
            final XMLContentHandler handler = new XMLContentHandler();
            xmlReader.setContentHandler(handler);
            xmlReader.setErrorHandler(handler);
            xmlReader.setProperty("http://xml.org/sax/properties/lexical-handler", handler);
            xmlReader.parse(is);
            final Document document = handler.getDocument();
            document.setDocumentURI(is.getSystemId());
            return document;
        } catch (final Throwable e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}

