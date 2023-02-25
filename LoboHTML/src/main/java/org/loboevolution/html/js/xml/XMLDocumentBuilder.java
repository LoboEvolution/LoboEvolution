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

