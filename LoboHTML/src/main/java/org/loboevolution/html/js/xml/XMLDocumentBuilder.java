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

import org.loboevolution.html.node.Document;
import org.xml.sax.*;

import org.loboevolution.javax.xml.XMLConstants;
import org.loboevolution.javax.xml.parsers.ParserConfigurationException;
import org.loboevolution.javax.xml.parsers.SAXParser;
import org.loboevolution.javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * <p>XMLDocumentBuilder class.</p>
 */
public class XMLDocumentBuilder {

    /** The Constant logger. */
    protected static final Logger logger = Logger.getLogger(XMLDocumentBuilder.class.getName());

    private final SAXParserFactory parserFactory;

    public XMLDocumentBuilder() {
        this.parserFactory = SAXParserFactory.newInstance();

        try {
            parserFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            parserFactory.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
            parserFactory.setFeature("http://xml.org/sax/features/xmlns-uris", true);
            parserFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
            parserFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            parserFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            parserFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            parserFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            parserFactory.setXIncludeAware(false);
            parserFactory.setNamespaceAware(true);
        } catch (SAXNotRecognizedException | SAXNotSupportedException | ParserConfigurationException e) {
            logger.severe(e.getMessage());
        }
    }

    public Document parse(InputSource is) throws SAXException, IOException {
        try {
            SAXParser saxParser = parserFactory.newSAXParser();
            return parse(is, saxParser.getXMLReader());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Document parse(InputSource is, XMLReader xmlReader) {
        try {
            XMLContentHandler handler = new XMLContentHandler();
            xmlReader.setContentHandler(handler);
            xmlReader.setErrorHandler(handler);
            xmlReader.setProperty("http://xml.org/sax/properties/lexical-handler", handler);
            xmlReader.parse(is);
            Document document = handler.getDocument();
            document.setDocumentURI(is.getSystemId());
            return document;
        } catch (Throwable e) {
            logger.severe(e.getMessage());
        }
        return null;
    }
}

