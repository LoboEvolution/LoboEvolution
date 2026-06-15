package org.loboevolution.html.js.xml;

/*
 * MIT License
 *
 * Copyright (c) 2014 - 2026 LoboEvolution
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

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.gui.LocalHtmlRendererConfig;
import org.loboevolution.gui.LocalHtmlRendererContext;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.io.WritableLineReader;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.loboevolution.js.xml.XSLTProcessor;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import org.loboevolution.javax.xml.transform.dom.DOMSource;

import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

/**
 * <p>XSLTProcessorImpl class.</p>
 *
 * <p>Implementation of the XSLTProcessor interface using the standard Java
 * XSLT transformer (JAXP).</p>
 */
@Slf4j
public class XSLTProcessorImpl extends AbstractScriptableDelegate implements XSLTProcessor {

    private Transformer transformer;
    private Document stylesheetDoc;

    /**
     * {@inheritDoc}
     */
    @Override
    public void importStylesheet(final Node stylesheet) {
        try {
            this.stylesheetDoc = toW3CDocument(stylesheet);
            log.debug("Stylesheet document type: {}", this.stylesheetDoc.getClass().getName());
            log.debug("Stylesheet document element: {}", this.stylesheetDoc.getDocumentElement());
            log.debug("Stylesheet document element name: {}",
                    this.stylesheetDoc.getDocumentElement() != null ?
                            this.stylesheetDoc.getDocumentElement().getNodeName() : "null");

            final TransformerFactory factory = TransformerFactory.newInstance();
            log.debug("TransformerFactory created");

            // Check if it's an XSLT stylesheet
            Element root = this.stylesheetDoc.getDocumentElement();
            if (root == null) {
                throw new RuntimeException("XSLTProcessor: Stylesheet document has no root element");
            }
            String rootName = root.getNodeName();
            String namespace = root.getNamespaceURI();
            log.debug("Root element: localName={}, namespace={}", rootName, namespace);

            // Check for xsl:stylesheet or xsl:transform
            if (!"http://www.w3.org/1999/XSL/Transform".equals(namespace)) {
                log.warn("Document does not appear to be an XSLT stylesheet (namespace={})", namespace);
            }

            final DOMSource source = new DOMSource(this.stylesheetDoc);
            log.debug("DOMSource created");

            this.transformer = factory.newTransformer(source);
            log.debug("Transformer created successfully");

            // Set default output method based on xsl:output if present
            configureOutput();
        } catch (final TransformerConfigurationException e) {
            log.error("Error creating transformer from stylesheet", e);
            throw new RuntimeException("XSLTProcessor: Error importing stylesheet - TransformerConfigurationException", e);
        } catch (final Exception e) {
            log.error("Unexpected error in importStylesheet", e);
            throw new RuntimeException("XSLTProcessor: Error importing stylesheet - " + e.getClass().getName() + ": " + e.getMessage(), e);
        }
    }

    /**
     * Configure the transformer output based on xsl:output settings in the stylesheet.
     */
    private void configureOutput() {
        if (this.transformer == null || this.stylesheetDoc == null) {
            return;
        }

        // Check for xsl:output element
        final HTMLCollection outputNodes = this.stylesheetDoc.getElementsByTagNameNS(
                "http://www.w3.org/1999/XSL/Transform", "output");

        if (outputNodes.getLength() > 0) {
            final Element outputElem = (Element) outputNodes.item(0);

            final String method = outputElem.getAttribute("method");
            if ("xml".equals(method) || "html".equals(method) || "text".equals(method)) {
                this.transformer.setOutputProperty(OutputKeys.METHOD, method);
            }

            final String encoding = outputElem.getAttribute("encoding");
            if (encoding != null && !encoding.isEmpty()) {
                this.transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
            }

            final String indent = outputElem.getAttribute("indent");
            if ("yes".equals(indent)) {
                this.transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            } else if ("no".equals(indent)) {
                this.transformer.setOutputProperty(OutputKeys.INDENT, "no");
            }

            final String omitXmlDeclaration = outputElem.getAttribute("omit-xml-declaration");
            if ("yes".equals(omitXmlDeclaration)) {
                this.transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            } else if ("no".equals(omitXmlDeclaration)) {
                this.transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Document transformToDocument(final Node source) {
        try {
            final Document sourceDoc = toW3CDocument(source);
            final DOMSource domSource = new DOMSource(sourceDoc);

            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final StreamResult result = new StreamResult(outputStream);

            this.transformer.transform(domSource, result);

            // Parse the result back into an XMLDocument
            final String resultXml = outputStream.toString(StandardCharsets.UTF_8);

            // Create a new XMLDocument using a custom approach that handles entity references
            final XMLDocument xmlDoc = createXmlDocumentFromString(resultXml);

            return xmlDoc;
        } catch (final TransformerException e) {
            log.error("Error transforming document", e);
            throw new RuntimeException("XSLTProcessor: Error transforming document", e);
        } catch (final Exception e) {
            log.error("Unexpected error in transformToDocument", e);
            throw new RuntimeException("XSLTProcessor: Error in transformToDocument - " + e.getClass().getName() + ": " + e.getMessage(), e);
        }
    }

    /**
     * Create an XMLDocument from a string, handling entity references properly.
     */
    private XMLDocument createXmlDocumentFromString(String xml) {
        log.debug("createXmlDocumentFromString called with: {}", xml);

        String processedXml = xml.trim();

        // Strip XML declaration if present
        if (processedXml.startsWith("<?xml")) {
            int prologEnd = processedXml.indexOf("?>");
            if (prologEnd > 0) {
                processedXml = processedXml.substring(prologEnd + 2).trim();
                log.debug("Stripped XML declaration, remaining: {}", processedXml);
            }
        }

        // XSLT sometimes outputs escaped characters like &gt; and &lt; which need to be unescaped
        // This happens when xsl:text with disable-output-escaping is used
        processedXml = unescapeXmlEntities(processedXml);
        log.debug("After unescaping: {}", processedXml);

        // Try to create the XMLDocument
        XMLDocument xmlDoc = new XMLDocument();
        try {
            xmlDoc.loadXML(processedXml);
            log.debug("Parse succeeded");
            return xmlDoc;
        } catch (Exception e) {
            log.debug("First parse attempt failed: {}", e.getMessage());
            // Try wrapping in a root element if it looks like it needs it
            if (processedXml.startsWith("<") && !processedXml.startsWith("<root")) {
                String wrappedXml = "<root>" + processedXml + "</root>";
                log.debug("Wrapped for parsing: {}", wrappedXml);
                try {
                    xmlDoc.loadXML(wrappedXml);
                    // Extract inner content
                    String innerXml = wrappedXml.substring(6, wrappedXml.length() - 7);
                    XMLDocument innerDoc = new XMLDocument();
                    innerDoc.loadXML(innerXml);
                    return innerDoc;
                } catch (Exception e2) {
                    log.debug("Wrapped parse failed: {}", e2.getMessage());
                }
            }
            // Last resort - try parsing the original
            log.debug("Trying direct parse of original XML");
            try {
                xmlDoc.loadXML(xml);
                return xmlDoc;
            } catch (Exception e2) {
                log.error("All parse attempts failed", e2);
                throw new RuntimeException("XSLTProcessor: Cannot parse transform result", e2);
            }
        }
    }

    /**
     * Unescape common XML entity references.
     */
    private String unescapeXmlEntities(String xml) {
        // Replace common escaped entities that XSLT might produce
        String result = xml;
        result = result.replace("&gt;", ">");
        result = result.replace("&lt;", "<");
        result = result.replace("&amp;", "&");
        result = result.replace("&quot;", "\"");
        result = result.replace("&apos;", "'");

        // Also strip leading > characters that might appear after unescaping &gt;
        // This can happen when the original output was &gt; at the start
        while (result.startsWith(">")) {
            result = result.substring(1);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node transformToFragment(final Node source, final Document owner) {
        try {
            final Document sourceDoc = toW3CDocument(source);
            final DOMSource domSource = new DOMSource(sourceDoc);

            final StringWriter writer = new StringWriter();
            final StreamResult result = new StreamResult(writer);

            this.transformer.transform(domSource, result);

            // For fragment, we return the result as a DocumentFragment
            // We'll create a new XMLDocument with the result content
            final String resultXml = writer.toString();
            final XMLDocument xmlDoc = new XMLDocument();
            xmlDoc.loadXML(resultXml);

            // Return the document element as a fragment-like representation
            // In browsers, transformToFragment returns a DocumentFragment
            // We return the root element wrapped in a fragment-like structure
            return xmlDoc;
        } catch (final TransformerException e) {
            log.error("Error transforming to fragment", e);
            throw new RuntimeException("XSLTProcessor: Error transforming to fragment", e);
        }
    }

    /**
     * Converts a LoboEvolution Node to a W3C DOM Document.
     *
     * @param node the node to convert
     * @return the W3C DOM document
     */
    private Document toW3CDocument(final Node node) {
        log.debug("toW3CDocument called with node type: {}", node.getClass().getName());

        // Special handling for XMLDocument - use original XML if available
        if (node instanceof Document lobodoc) {
            log.debug("Node is a Document");
            if (lobodoc instanceof XMLDocument xmldoc) {
                log.debug("Node is an XMLDocument");
                return getW3CDocument(xmldoc);
            }
            // For HTMLDocument, try to get underlying W3C document
            log.debug("Node is not XMLDocument, getting underlying document");
            return getUnderlyingDocument(lobodoc);
        }

        // For non-Document nodes, try to get the owner document
        final Document ownerDoc = node.getOwnerDocument();
        log.debug("Owner document type: {}", ownerDoc.getClass().getName());

        if (ownerDoc instanceof XMLDocument xmldoc) {
            // Get the original XML from the owner document
            try {
                java.lang.reflect.Method getOriginalXmlMethod = XMLDocument.class.getMethod("getOriginalXml");
                Object xmlContent = getOriginalXmlMethod.invoke(xmldoc);
                if (xmlContent instanceof String xmlString && xmlString != null && !xmlString.isEmpty()) {
                    log.debug("Found original XML for source, length: {}", xmlString.length());
                    return parseXmlStringAsW3CDocument(xmlString);
                }
            } catch (Exception e) {
                log.debug("Could not get original XML, falling back to serialization");
            }
            // Fallback: serialize the node
            return getW3CDocument(xmldoc);
        }
        return getUnderlyingDocument(ownerDoc);
    }

    /**
     * Convert a LoboEvolution Node to a W3C DOM Node.
     */
    private Node toW3CNode(final Node node) {
        if (node == null) {
            return null;
        }
        final Document ownerDoc = node.getOwnerDocument();
        if (ownerDoc instanceof XMLDocument xmldoc) {
            try {
                Document w3cDoc = getW3CDocument(xmldoc);
                return w3cDoc;
            } catch (Exception e) {
                log.error("Error converting XMLDocument to W3C", e);
            }
        }
        // For non-XML documents or other cases, serialize and re-parse
        XMLSerializerImpl serializer = new XMLSerializerImpl();
        String xml = serializer.serializeToString(node);
        return parseXmlStringAsW3CDocument("<root>" + xml + "</root>").getDocumentElement();
    }

    /**
     * Get the underlying W3C DOM Document from XMLDocument.
     */
    private Document getW3CDocument(XMLDocument xmlDoc) {
        log.debug("getW3CDocument called on XMLDocument");
        try {
            // Use reflection to access the private 'doc' field
            java.lang.reflect.Field docField = XMLDocument.class.getDeclaredField("doc");
            docField.setAccessible(true);
            Object docObj = docField.get(xmlDoc);
            log.debug("doc field value type: {}", docObj != null ? docObj.getClass().getName() : "null");
            if (docObj == null) {
                throw new RuntimeException("XSLTProcessor: XMLDocument 'doc' field is null");
            }
            if (docObj instanceof Document w3cDoc) {
                log.debug("doc field is already a W3C Document");
                return w3cDoc;
            }

            // The LoboEvolution document loses namespace information when serialized.
            // Instead, we need to get the raw XML and parse it directly as W3C DOM.
            // Try to get the original XML string that was parsed
            try {
                java.lang.reflect.Method getOriginalXmlMethod = XMLDocument.class.getMethod("getOriginalXml");
                Object xmlContent = getOriginalXmlMethod.invoke(xmlDoc);
                if (xmlContent instanceof String xmlString && xmlString != null && !xmlString.isEmpty()) {
                    log.debug("Found original XML, length: {}", xmlString.length());
                    return parseXmlStringAsW3CDocument(xmlString);
                }
            } catch (NoSuchMethodException e) {
                log.debug("No getOriginalXml method found");
            }

            // Last resort: serialize and re-parse (but this loses namespace info)
            log.debug("Using fallback serialize/re-parse method");
            XMLSerializerImpl serializer = new XMLSerializerImpl();
            String xml = serializer.serializeToString((org.loboevolution.html.node.Node) docObj);
            log.debug("Serialized XML length: {}", xml.length());
            return parseXmlStringAsW3CDocument(xml);
        } catch (Exception e) {
            log.error("Error accessing XMLDocument underlying document", e);
            throw new RuntimeException("XSLTProcessor: Cannot access XML document", e);
        }
    }

    /**
     * Parse an XML string directly as a W3C DOM document, preserving namespace information.
     */
    private Document parseXmlStringAsW3CDocument(String xml) {
        HTMLDocumentImpl doc = null;
        try (final WritableLineReader wis = new WritableLineReader(new StringReader(xml))) {
            final HtmlRendererConfig config = new LocalHtmlRendererConfig();
            final UserAgentContext ucontext = new UserAgentContext(config, true);
            final HtmlPanel panel = new HtmlPanel();
            panel.setPreferredSize(new Dimension(800, 400));
            final HtmlRendererContext rendererContext = new LocalHtmlRendererContext(panel, ucontext);
            ucontext.setUserAgentEnabled(true);
            doc = new HTMLDocumentImpl(ucontext, rendererContext, config, wis, null);
            doc.load();
        } catch (final Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return doc;
    }

    /**
     * Get the underlying W3C DOM Document from LoboEvolution Document.
     */
    private Document getUnderlyingDocument(Document lobodoc) {
        try {
            // Most Document implementations should have a way to get W3C document
            // Try to find the underlying Document
            Field docField = null;
            Class<?> cls = lobodoc.getClass();
            while (cls != null && docField == null) {
                try {
                    docField = cls.getDeclaredField("doc");
                } catch (NoSuchFieldException e) {
                    cls = cls.getSuperclass();
                }
            }
            if (docField != null) {
                docField.setAccessible(true);
                return (Document) docField.get(lobodoc);
            }
            throw new RuntimeException("XSLTProcessor: Cannot find underlying W3C document");
        } catch (Exception e) {
            log.error("Error accessing document", e);
            throw new RuntimeException("XSLTProcessor: Cannot access document", e);
        }
    }

    @Override
    public String toString() {
        return "[object XSLTProcessor]";
    }
}
