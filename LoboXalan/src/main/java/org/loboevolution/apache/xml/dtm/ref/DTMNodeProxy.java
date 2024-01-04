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
package org.loboevolution.apache.xml.dtm.ref;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.loboevolution.apache.xpath.NodeSet;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMDOMException;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.*;
import org.loboevolution.html.node.*;
import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.js.Location;
import org.loboevolution.html.node.js.Window;
import org.loboevolution.html.node.js.geom.DOMRect;
import org.loboevolution.html.node.js.geom.DOMRectList;
import org.loboevolution.html.node.traversal.NodeFilter;
import org.loboevolution.html.node.traversal.NodeIterator;
import org.loboevolution.html.node.traversal.TreeWalker;
import org.loboevolution.html.xpath.XPathExpression;
import org.loboevolution.html.xpath.XPathNSResolver;
import org.loboevolution.html.xpath.XPathResult;
import org.loboevolution.type.DocumentReadyState;
import org.loboevolution.type.VisibilityState;
import org.mozilla.javascript.Function;
import org.w3c.dom.events.EventException;

/**
 * <code>DTMNodeProxy</code> presents a DOM Node API front-end to the DTM model.
 *
 * <p>It does _not_ attempt to address the "node identity" question; no effort is made to prevent
 * the creation of multiple proxies referring to a single DTM node. Users can create a mechanism for
 * managing this, or relinquish the use of "==" and use the .sameNodeAs() mechanism, which is under
 * consideration for future versions of the DOM.
 *
 * <p>DTMNodeProxy may be subclassed further to present specific DOM node types.
 *
 * @see org.w3c.dom
 */
public class DTMNodeProxy
        implements Node,
        Document,
        Text,
        Element,
        Attr,
        ProcessingInstruction,
        Comment,
        DocumentFragment {

    /**
     * The DTM for this node.
     */
    public final DTM dtm;

    /**
     * The DTM node handle.
     */
    final int node;

    /**
     * The return value as Empty String.
     */
    private static final String EMPTYSTRING = "";

    /**
     * The DOMImplementation object
     */
    static final DOMImplementation implementation = new DTMNodeProxyImplementation();

    /**
     * Create a DTMNodeProxy Node representing a specific Node in a DTM
     *
     * @param dtm  The DTM Reference, must be non-null.
     * @param node The DTM node handle.
     */
    public DTMNodeProxy(final DTM dtm, final int node) {
        this.dtm = dtm;
        this.node = node;
    }

    /**
     * NON-DOM: Return the DTM node number
     *
     * @return The DTM node handle.
     */
    public final int getDTMNodeNumber() {
        return node;
    }

    /**
     * Test for equality based on node number.
     *
     * @param node A DTM node proxy reference.
     * @return true if the given node has the same handle as this node.
     */
    public final boolean equals(final Node node) {

        try {
            final DTMNodeProxy dtmp = (DTMNodeProxy) node;

            // return (dtmp.node == this.node);
            // Patch attributed to Gary L Peskin <garyp@firstech.com>
            return (dtmp.node == this.node) && (dtmp.dtm == this.dtm);
        } catch (final ClassCastException cce) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object node) {

        try {

            // DTMNodeProxy dtmp = (DTMNodeProxy)node;
            // return (dtmp.node == this.node);
            // Patch attributed to Gary L Peskin <garyp@firstech.com>
            return equals((Node) node);
        } catch (final ClassCastException cce) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getNodeName() {
        return dtm.getNodeName(node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getTarget() {
        return dtm.getNodeName(node);
    } // getTarget():String

    @Override
    public DOMTokenList getClassList() {
        return null;
    }

    @Override
    public String getClassName() {
        return null;
    }

    @Override
    public void setClassName(final String className) {

    }

    @Override
    public int getClientHeight() {
        return 0;
    }

    @Override
    public int getClientLeft() {
        return 0;
    }

    @Override
    public int getClientTop() {
        return 0;
    }

    @Override
    public Integer getClientWidth() {
        return 0;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setId(final String id) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getLocalName() {
        return dtm.getLocalName(node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getPrefix() {
        return dtm.getPrefix(node);
    }

    @Override
    public double getScrollHeight() {
        return 0;
    }

    @Override
    public double getScrollLeft() {
        return 0;
    }

    @Override
    public void setScrollLeft(final double scrollLeft) {

    }

    @Override
    public double getScrollTop() {
        return 0;
    }

    @Override
    public void setScrollTop(final double scrollTop) {

    }

    @Override
    public double getScrollWidth() {
        return 0;
    }

    @Override
    public String getSlot() {
        return null;
    }

    @Override
    public void setSlot(final String slot) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setPrefix(final String prefix) throws DOMException {
        throw new DTMDOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getNamespaceURI() {
        return dtm.getNamespaceURI(node);
    }

    @Override
    public Function getOnfullscreenchange() {
        return null;
    }

    @Override
    public String getOuterHTML() {
        return null;
    }

    @Override
    public DOMRect getBoundingClientRect() {
        return null;
    }

    @Override
    public DOMRectList getClientRects() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isSupported(final String feature, final String version) {
        return implementation.hasFeature(feature, version);
        // throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getNodeValue() throws DOMException {
        return dtm.getNodeValue(node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setNodeValue(final String nodeValue) throws DOMException {
        throw new DTMDOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getNodeType() {
        return dtm.getNodeType(node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Node getParentNode() {

        if (getNodeType() == Node.ATTRIBUTE_NODE) return null;

        final int newnode = dtm.getParent(node);

        return (newnode == DTM.NULL) ? null : dtm.getNode(newnode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final NodeList getChildNodes() {

        // Annoyingly, AxisIterators do not currently implement DTMIterator, so
        // we can't just wap DTMNodeList around an Axis.CHILD iterator.
        // Instead, we've created a special-case operating mode for that object.
        return new DTMChildIterNodeList(dtm, node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Node getFirstChild() {

        final int newnode = dtm.getFirstChild(node);

        return (newnode == DTM.NULL) ? null : dtm.getNode(newnode);
    }

    @Override
    public boolean isIsConnected() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Node getLastChild() {

        final int newnode = dtm.getLastChild(node);

        return (newnode == DTM.NULL) ? null : dtm.getNode(newnode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Node getPreviousSibling() {

        final int newnode = dtm.getPreviousSibling(node);

        return (newnode == DTM.NULL) ? null : dtm.getNode(newnode);
    }

    @Override
    public Node getRootNode() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Node getNextSibling() {

        // Attr's Next is defined at DTM level, but not at DOM level.
        if (dtm.getNodeType(node) == Node.ATTRIBUTE_NODE) return null;

        final int newnode = dtm.getNextSibling(node);

        return (newnode == DTM.NULL) ? null : dtm.getNode(newnode);
    }

    // DTMNamedNodeMap m_attrs;

    /**
     * {@inheritDoc}
     */
    @Override
    public final NamedNodeMap getAttributes() {

        return new DTMNamedNodeMap(dtm, node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasAttribute(final String name) {
        return DTM.NULL != dtm.getAttributeNode(node, null, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasAttributeNS(final String namespaceURI, final String localName) {
        return DTM.NULL != dtm.getAttributeNode(node, namespaceURI, localName);
    }

    @Override
    public boolean hasPointerCapture(final int pointerId) {
        return false;
    }

    @Override
    public boolean matches(final String selectors) {
        return false;
    }

    @Override
    public void releasePointerCapture(final int pointerId) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Document getOwnerDocument() {
        // Note that this uses the DOM-compatable version of the call
        return (Document) (dtm.getNode(dtm.getOwnerDocument(node)));
    }

    @Override
    public void setOwnerDocument(final Document document) {

    }

    @Override
    public HTMLElement getParentElement() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Node insertBefore(final Node newChild, final Node refChild) throws DOMException {
        throw new DTMDOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Node replaceChild(final Node newChild, final Node oldChild) throws DOMException {
        throw new DTMDOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Node removeChild(final Node oldChild) throws DOMException {
        throw new DTMDOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Node appendChild(final Node newChild) throws DOMException {
        throw new DTMDOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR);
    }

    @Override
    public Node prependChild(final Node newChild) {
        return null;
    }

    @Override
    public Node prepend(final Node newChild) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean hasChildNodes() {
        return DTM.NULL != dtm.getFirstChild(node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Node cloneNode(final boolean deep) {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    @Override
    public Node cloneNode() {
        return null;
    }



    @Override
    public String getURL() {
        return null;
    }

    @Override
    public Element getActiveElement() {
        return null;
    }

    @Override
    public String getAlinkColor() {
        return null;
    }

    @Override
    public void setAlinkColor(final String alinkColor) {

    }

    @Override
    public HTMLAllCollection getAll() {
        return null;
    }

    @Override
    public HTMLCollection getAnchors() {
        return null;
    }

    @Override
    public HTMLCollection getApplets() {
        return null;
    }

    @Override
    public HTMLCollection getCommands() {
        return null;
    }

    @Override
    public String getBgColor() {
        return null;
    }

    @Override
    public void setBgColor(final String bgColor) {

    }

    @Override
    public HTMLElement getBody() {
        return null;
    }

    @Override
    public void setBody(final HTMLElement body) {

    }

    @Override
    public String getCharacterSet() {
        return null;
    }

    @Override
    public String getCharset() {
        return null;
    }

    @Override
    public String getCompatMode() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public String getCookie() {
        return null;
    }

    @Override
    public void setCookie(final String cookie) {

    }

    @Override
    public HTMLScriptElement getCurrentScript() {
        return null;
    }

    @Override
    public Window getDefaultView() {
        return null;
    }

    @Override
    public String getDesignMode() {
        return null;
    }

    @Override
    public void setDesignMode(final String designMode) {

    }

    @Override
    public String getDir() {
        return null;
    }

    @Override
    public void setDir(final String dir) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DocumentType getDoctype() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DOMImplementation getImplementation() {
        return implementation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Element getDocumentElement() {
        final int dochandle = dtm.getDocument();
        int elementhandle = DTM.NULL;
        for (int kidhandle = dtm.getFirstChild(dochandle);
             kidhandle != DTM.NULL;
             kidhandle = dtm.getNextSibling(kidhandle)) {
            switch (dtm.getNodeType(kidhandle)) {
                case Node.ELEMENT_NODE:
                    if (elementhandle != DTM.NULL) {
                        elementhandle = DTM.NULL; // More than one; ill-formed.
                        kidhandle = dtm.getLastChild(dochandle); // End loop
                    } else elementhandle = kidhandle;
                    break;

                // These are harmless; document is still wellformed
                case Node.COMMENT_NODE:
                case Node.PROCESSING_INSTRUCTION_NODE:
                case Node.DOCUMENT_TYPE_NODE:
                    break;

                default:
                    elementhandle = DTM.NULL; // ill-formed
                    kidhandle = dtm.getLastChild(dochandle); // End loop
                    break;
            }
        }
        if (elementhandle == DTM.NULL) throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
        else return (Element) (dtm.getNode(elementhandle));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Element createElement(final String tagName) throws DOMException {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    @Override
    public Element createElementNS(final String namespace, final String qualifiedName, final String options) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DocumentFragment createDocumentFragment() {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Text createTextNode(final String data) {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    @Override
    public Element elementFromPoint(final double x, final double y) {
        return null;
    }

    @Override
    public boolean execCommand(final String commandId, final boolean showUI, final String value) {
        return false;
    }

    @Override
    public boolean execCommand(final String commandId, final boolean showUI) {
        return false;
    }

    @Override
    public boolean execCommand(final String commandId) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Comment createComment(final String data) {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final CDATASection createCDATASection(final String data) throws DOMException {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ProcessingInstruction createProcessingInstruction(final String target, final String data)
            throws DOMException {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    @Override
    public Range createRange() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Attr createAttribute(final String name) throws DOMException {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final EntityReference createEntityReference(final String name) throws DOMException {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    @Override
    public Node renameNode(final Node node, final String namespaceURI, final String qualifiedName) {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    @Override
    public boolean isXml() {
        return false;
    }

    @Override
    public void setXml(final boolean xml) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HTMLCollection getElementsByTagName(final String tagname) {
        final List<Node> listVector = new ArrayList<>();
        final Node retNode = dtm.getNode(node);
        if (retNode != null) {
            final boolean isTagNameWildCard = "*".equals(tagname);
            if (DTM.ELEMENT_NODE == retNode.getNodeType()) {
                final NodeList nodeList = retNode.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    traverseChildren(listVector, nodeList.item(i), tagname, isTagNameWildCard);
                }
            } else if (DTM.DOCUMENT_NODE == retNode.getNodeType()) {
                traverseChildren(listVector, dtm.getNode(node), tagname, isTagNameWildCard);
            }
        }
        return new NodeSet(listVector);
    }

    private void traverseChildren(
            final List<Node> listVector, final Node tempNode, final String tagname, final boolean isTagNameWildCard) {
        if (tempNode != null) {
            if (tempNode.getNodeType() == DTM.ELEMENT_NODE
                    && (isTagNameWildCard || tempNode.getNodeName().equals(tagname))) {
                listVector.add(tempNode);
            }
            if (tempNode.hasChildNodes()) {
                final NodeList nodeList = tempNode.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    traverseChildren(listVector, nodeList.item(i), tagname, isTagNameWildCard);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Node importNode(final Node importedNode, final boolean deep) throws DOMException {
        throw new DTMDOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR);
    }

    @Override
    public Document open(final String url, final String name, final String features, final boolean replace) {
        return null;
    }

    @Override
    public Document open(final String url, final String name, final String features) {
        return null;
    }

    @Override
    public Document open(final String url, final String name) {
        return null;
    }

    @Override
    public Document open(final String url) {
        return null;
    }

    @Override
    public Document open() {
        return null;
    }

    @Override
    public boolean queryCommandEnabled(final String commandId) {
        return false;
    }

    @Override
    public boolean queryCommandIndeterm(final String commandId) {
        return false;
    }

    @Override
    public boolean queryCommandState(final String commandId) {
        return false;
    }

    @Override
    public boolean queryCommandSupported(final String commandId) {
        return false;
    }

    @Override
    public String queryCommandValue(final String commandId) {
        return null;
    }

    @Override
    public void releaseEvents() {

    }

    @Override
    public void write(final String text) {

    }

    @Override
    public void writeln(final String text) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Element createElementNS(final String namespaceURI, final String qualifiedName)
            throws DOMException {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    @Override
    public Event createEvent(final String eventInterface) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Attr createAttributeNS(final String namespaceURI, final String qualifiedName)
            throws DOMException {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HTMLCollection getElementsByTagNameNS(final String namespaceURI, final String localName) {
        final List<Node> listVector = new ArrayList<>();
        final Node retNode = dtm.getNode(node);
        if (retNode != null) {
            final boolean isNamespaceURIWildCard = "*".equals(namespaceURI);
            final boolean isLocalNameWildCard = "*".equals(localName);
            if (DTM.ELEMENT_NODE == retNode.getNodeType()) {
                final NodeList nodeList = retNode.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    traverseChildren(
                            listVector,
                            nodeList.item(i),
                            namespaceURI,
                            localName,
                            isNamespaceURIWildCard,
                            isLocalNameWildCard);
                }
            } else if (DTM.DOCUMENT_NODE == retNode.getNodeType()) {
                traverseChildren(
                        listVector,
                        dtm.getNode(node),
                        namespaceURI,
                        localName,
                        isNamespaceURIWildCard,
                        isLocalNameWildCard);
            }
        }
        return new NodeSet(listVector);
    }

    @Override
    public Selection getSelection() {
        return null;
    }

    /**
     * @param listVector
     * @param tempNode
     * @param namespaceURI
     * @param localname
     * @param isNamespaceURIWildCard
     * @param isLocalNameWildCard    <p>Private method to be used for recursive iterations to obtain elements by tag name and
     *                               namespaceURI.
     */
    private void traverseChildren(
            final List<Node> listVector,
            final Node tempNode,
            final String namespaceURI,
            final String localname,
            final boolean isNamespaceURIWildCard,
            final boolean isLocalNameWildCard) {
        if (tempNode == null) {
        } else {
            if (tempNode.getNodeType() == DTM.ELEMENT_NODE
                    && (isLocalNameWildCard || tempNode.getLocalName().equals(localname))) {
                final String nsURI = tempNode.getNamespaceURI();
                if ((namespaceURI == null && nsURI == null)
                        || isNamespaceURIWildCard
                        || (namespaceURI != null && namespaceURI.equals(nsURI))) {
                    listVector.add(tempNode);
                }
            }
            if (tempNode.hasChildNodes()) {
                final NodeList nl = tempNode.getChildNodes();
                for (int i = 0; i < nl.getLength(); i++) {
                    traverseChildren(
                            listVector,
                            nl.item(i),
                            namespaceURI,
                            localname,
                            isNamespaceURIWildCard,
                            isLocalNameWildCard);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Element getElementById(final String elementId) {
        return (Element) dtm.getNode(dtm.getElementById(elementId));
    }

    @Override
    public HTMLCollection getElementsByClassName(final String classNames) {
        return null;
    }

    @Override
    public HTMLCollection getElementsByName(final String elementName) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Text splitText(final int offset) throws DOMException {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getData() throws DOMException {
        return dtm.getNodeValue(node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setData(final String data) throws DOMException {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getLength() {
        // %OPT% This should do something smarter?
        return dtm.getNodeValue(node).length();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String substringData(final int offset, final int count) throws DOMException {
        return getData().substring(offset, offset + count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void appendData(final String arg) throws DOMException {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void insertData(final int offset, final String arg) throws DOMException {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void deleteData(final int offset, final int count) throws DOMException {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void replaceData(final int offset, final int count, final String arg) throws DOMException {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getTagName() {
        return dtm.getNodeName(node);
    }

    @Override
    public <E extends Element> E closest(final String selector) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getAttribute(final String name) {

        final DTMNamedNodeMap map = new DTMNamedNodeMap(dtm, node);
        final Node node = map.getNamedItem(name);
        return (null == node) ? EMPTYSTRING : node.getNodeValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setAttribute(final String name, final String value) throws DOMException {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void removeAttribute(final String name) throws DOMException {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Attr getAttributeNode(final String name) {

        final DTMNamedNodeMap map = new DTMNamedNodeMap(dtm, node);
        return map.getNamedItem(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Attr setAttributeNode(final Attr newAttr) throws DOMException {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Attr removeAttributeNode(final Attr oldAttr) throws DOMException {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    @Override
    public void requestPointerLock() {

    }

    @Override
    public void scroll(final int x, final int y) {

    }

    @Override
    public void scrollBy(final int x, final int y) {

    }

    @Override
    public void scrollIntoView(final boolean arg) {

    }

    @Override
    public void scrollIntoView() {

    }

    @Override
    public void scrollTo(final int x, final int y) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasAttributes() {
        return DTM.NULL != dtm.getFirstAttribute(node);
    }

    @Override
    public Node getFeature(final String name, final String version) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void normalize() {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getAttributeNS(final String namespaceURI, final String localName) {
        Node retNode = null;
        final int n = dtm.getAttributeNode(node, namespaceURI, localName);
        if (n != DTM.NULL) retNode = dtm.getNode(n);
        return (null == retNode) ? EMPTYSTRING : retNode.getNodeValue();
    }

    @Override
    public String[] getAttributeNames() {
        return new String[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setAttributeNS(final String namespaceURI, final String qualifiedName, final String value)
            throws DOMException {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void removeAttributeNS(final String namespaceURI, final String localName) throws DOMException {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Attr getAttributeNodeNS(final String namespaceURI, final String localName) {
        Attr retAttr = null;
        final int n = dtm.getAttributeNode(node, namespaceURI, localName);
        if (n != DTM.NULL) retAttr = (Attr) dtm.getNode(n);
        return retAttr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Attr setAttributeNodeNS(final Attr newAttr) throws DOMException {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getName() {
        return dtm.getNodeName(node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getValue() {
        return dtm.getNodeValue(node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setValue(final String value) {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    @Override
    public void setOwnerElement(final Node element) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Node getOwnerElement() {
        if (getNodeType() != Node.ATTRIBUTE_NODE) return null;
        // In XPath and DTM data models, unlike DOM, an Attr's parent is its
        // owner element.
        final int newnode = dtm.getParent(node);
        return (newnode == DTM.NULL) ? null : (Element) (dtm.getNode(newnode));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node adoptNode(final Node source) throws DOMException {

        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    @Override
    public void captureEvents() {

    }

    @Override
    public Range caretRangeFromPoint(final double x, final double y) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public void close() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getInputEncoding() {

        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    @Override
    public String getLastModified() {
        return null;
    }

    @Override
    public HTMLCollection getLinks() {
        return null;
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void setLocation(final Location location) {

    }

    @Override
    public String getOrigin() {
        return null;
    }

    @Override
    public HTMLCollection getPlugins() {
        return null;
    }

    @Override
    public DocumentReadyState getReadyState() {
        return null;
    }

    @Override
    public String getReferrer() {
        return null;
    }

    @Override
    public HTMLCollection getScripts() {
        return null;
    }

    @Override
    public Element getScrollingElement() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void setTitle(final String title) {

    }

    @Override
    public VisibilityState getVisibilityState() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getStrictErrorChecking() {

        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStrictErrorChecking(final boolean strictErrorChecking) {
        throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }

    @Override
    public String getInnerHTML() {
        return null;
    }

    @Override
    public String getInnerText() {
        return null;
    }

    @Override
    public void setInnerHTML(final String innerHTML) {

    }

    @Override
    public Element getNextElementSibling() {
        return null;
    }

    @Override
    public Element getPreviousElementSibling() {
        return null;
    }

    @Override
    public int getChildElementCount() {
        return 0;
    }

    @Override
    public HTMLCollection getChildren() {
        return null;
    }

    @Override
    public Element getFirstElementChild() {
        return null;
    }

    @Override
    public Element getLastElementChild() {
        return null;
    }

    @Override
    public Element querySelector(final String selectors) {
        return null;
    }

    @Override
    public NodeList querySelectorAll(final String selectors) {
        return null;
    }

    @Override
    public void addEventListener(final String type, final Function listener) {

    }

    @Override
    public void addEventListener(final String type, final Function listener, final boolean useCapture) {

    }

    @Override
    public void removeEventListener(final String type, final Function listener) {

    }

    @Override
    public void removeEventListener(final String type, final Function listener, final boolean useCapture) {

    }

    @Override
    public boolean dispatchEvent(final Node element, final Event evt) {
        return false;
    }

    @Override
    public boolean dispatchEvent(final Event evt) throws EventException {
        return false;
    }

    @Override
    public NodeIterator createNodeIterator(final Node root) throws DOMException {
        return null;
    }

    @Override
    public NodeIterator createNodeIterator(final Node root, final int whatToShow) throws DOMException {
        return null;
    }

    @Override
    public NodeIterator createNodeIterator(final Node root, final NodeFilter filter) throws DOMException {
        return null;
    }

    @Override
    public NodeIterator createNodeIterator(final Node root, final int whatToShow, final NodeFilter filter) throws DOMException {
        return null;
    }

    @Override
    public TreeWalker createTreeWalker(final Node root) throws DOMException {
        return null;
    }

    @Override
    public TreeWalker createTreeWalker(final Node root, final int whatToShow) throws DOMException {
        return null;
    }

    @Override
    public TreeWalker createTreeWalker(final Node root, final NodeFilter filter) throws DOMException {
        return null;
    }

    @Override
    public TreeWalker createTreeWalker(final Node root, final int whatToShow, final NodeFilter filter) throws DOMException {
        return null;
    }

    /**
     * Inner class to support getDOMImplementation.
     */
    static class DTMNodeProxyImplementation implements DOMImplementation {
        /**
         * {@inheritDoc}
         */
        @Override
        public DocumentType createDocumentType(final String qualifiedName, final String publicId, final String systemId) {
            throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
        }

        @Override
        public Document createHTMLDocument(final String title) {
            throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
        }

        @Override
        public Document createHTMLDocument() {
            throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Document createDocument(final String namespaceURI, final String qualfiedName, final DocumentType doctype) {
            // Could create a DTM... but why, when it'd have to be permanantly empty?
            throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasFeature(final String feature, final String version) {
            return ("CORE".equalsIgnoreCase(feature) || "XML".equalsIgnoreCase(feature))
                    && ("1.0".equals(version) || "2.0".equals(version));
        }

        @Override
        public Object getFeature(final String core, final String s) {
            throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object setUserData(final String key, final Object data, final UserDataHandler handler) {
        return getOwnerDocument().setUserData(key, data, handler);
    }

    @Override
    public boolean contains(final Node other) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getUserData(final String key) {
        return getOwnerDocument().getUserData(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEqualNode(final Node arg) {
        if (arg == this) {
            return true;
        }
        if (arg.getNodeType() != getNodeType()) {
            return false;
        }
        // in theory nodeName can't be null but better be careful
        // who knows what other implementations may be doing?...
        if (getNodeName() == null) {
            if (arg.getNodeName() != null) {
                return false;
            }
        } else if (!getNodeName().equals(arg.getNodeName())) {
            return false;
        }

        if (getLocalName() == null) {
            if (arg.getLocalName() != null) {
                return false;
            }
        } else if (!getLocalName().equals(arg.getLocalName())) {
            return false;
        }

        if (getNamespaceURI() == null) {
            if (arg.getNamespaceURI() != null) {
                return false;
            }
        } else if (!getNamespaceURI().equals(arg.getNamespaceURI())) {
            return false;
        }

        if (getPrefix() == null) {
            if (arg.getPrefix() != null) {
                return false;
            }
        } else if (!getPrefix().equals(arg.getPrefix())) {
            return false;
        }

        if (getNodeValue() == null) {
            return arg.getNodeValue() == null;
        } else return getNodeValue().equals(arg.getNodeValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String lookupNamespaceURI(final String specifiedPrefix) {
        final int type = this.getNodeType();
        switch (type) {
            case Node.ELEMENT_NODE: {
                String namespace = this.getNamespaceURI();
                final String prefix = this.getPrefix();
                if (namespace != null) {
                    // REVISIT: is it possible that prefix is empty string?
                    if (specifiedPrefix == null && Objects.equals(prefix, specifiedPrefix)) {
                        // looking for default namespace
                        return namespace;
                    } else if (prefix != null && prefix.equals(specifiedPrefix)) {
                        // non default namespace
                        return namespace;
                    }
                }
                if (this.hasAttributes()) {
                    final NamedNodeMap map = this.getAttributes();
                    final int length = map.getLength();
                    for (int i = 0; i < length; i++) {
                        final Node attr = map.item(i);
                        final String attrPrefix = attr.getPrefix();
                        final String value = attr.getNodeValue();
                        namespace = attr.getNamespaceURI();
                        if (namespace != null && namespace.equals("http://www.w3.org/2000/xmlns/")) {
                            // at this point we are dealing with DOM Level 2 nodes only
                            if (specifiedPrefix == null && attr.getNodeName().equals("xmlns")) {
                                // default namespace
                                return value;
                            } else if (attrPrefix != null
                                    && attrPrefix.equals("xmlns")
                                    && attr.getLocalName().equals(specifiedPrefix)) {
                                // non default namespace
                                return value;
                            }
                        }
                    }
                }
                return null;
            }
            case Node.ENTITY_NODE:
            case Node.NOTATION_NODE:
            case Node.DOCUMENT_FRAGMENT_NODE:
            case Node.DOCUMENT_TYPE_NODE:
                // type is unknown
                return null;
            case Node.ATTRIBUTE_NODE: {
                if (this.getOwnerElement().getNodeType() == Node.ELEMENT_NODE) {
                    return getOwnerElement().lookupNamespaceURI(specifiedPrefix);
                }
                return null;
            }
            default: {
                return null;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDefaultNamespace(final String namespaceURI) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String lookupPrefix(final String namespaceURI) {

        // REVISIT: When Namespaces 1.1 comes out this may not be true
        // Prefix can't be bound to null namespace
        if (namespaceURI == null) {
            return null;
        }

        final int type = this.getNodeType();

        switch (type) {
            case Node.ENTITY_NODE:
            case Node.NOTATION_NODE:
            case Node.DOCUMENT_FRAGMENT_NODE:
            case Node.DOCUMENT_TYPE_NODE:
                // type is unknown
                return null;
            case Node.ATTRIBUTE_NODE: {
                if (this.getOwnerElement().getNodeType() == Node.ELEMENT_NODE) {
                    return getOwnerElement().lookupPrefix(namespaceURI);
                }
                return null;
            }
            default: {
                return null;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSameNode(final Node other) {
        // we do not use any wrapper so the answer is obvious
        return this == other;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTextContent(final String textContent) throws DOMException {
        setNodeValue(textContent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTextContent() throws DOMException {
        return dtm.getStringValue(node).toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public short compareDocumentPosition(final Node other) throws DOMException {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBaseURI() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void normalizeDocument() {
    }

    @Override
    public XPathExpression createExpression() {
        return null;
    }

    @Override
    public XPathNSResolver createNSResolver(final Node nodeResolver) {
        return null;
    }

    @Override
    public XPathResult evaluate(final String expression, final Node contextNode, final XPathNSResolver resolver, final short type, final Object result) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DOMConfiguration getDomConfig() {
        return null;
    }

    protected String fDocumentURI;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDocumentURI(final String documentURI) {

        fDocumentURI = documentURI;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDocumentURI() {
        return fDocumentURI;
    }

    @Override
    public String getDomain() {
        return null;
    }

    @Override
    public void setDomain(final String domain) {

    }

    @Override
    public HTMLCollection getEmbeds() {
        return null;
    }

    @Override
    public String getFgColor() {
        return null;
    }

    @Override
    public void setFgColor(final String fgColor) {

    }

    @Override
    public HTMLCollection getForms() {
        return null;
    }

    @Override
    public boolean isFullscreen() {
        return false;
    }

    @Override
    public boolean isFullscreenEnabled() {
        return false;
    }

    @Override
    public HTMLHeadElement getHead() {
        return null;
    }

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public HTMLCollection getImages() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Text replaceWholeText(final String content) throws DOMException {
        return null; // Pending
    }

    @Override
    public HTMLSlotElement getAssignedSlot() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWholeText() {
        return null; // PENDING
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isElementContentWhitespace() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIdAttribute(final String name, final boolean makeId) {
        // PENDING
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIdAttributeNode(final Attr at, final boolean makeId) {
        // PENDING
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIdAttributeNS(final String namespaceURI, final String localName, final boolean makeId) {
        // PENDING
    }

    @Override
    public void setPointerCapture(final int pointerId) {

    }

    @Override
    public boolean toggleAttribute(final String qualifiedName, final boolean force) {
        return false;
    }

    @Override
    public boolean toggleAttribute(final String qualifiedName) {
        return false;
    }

    @Override
    public Node insertAdjacentElement(final String where, final Node insertedElement) {
        return null;
    }

    @Override
    public void insertAdjacentHTML(final String position, final String text) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeInfo getSchemaTypeInfo() {
        return null; // PENDING
    }

    @Override
    public void setOuterHTML(final String outerHTML) {

    }

    @Override
    public boolean isSpecified() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isId() {
        return false; // PENDING
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getXmlEncoding() {
        return null;
    }

    private boolean xmlStandalone;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getXmlStandalone() {
        return xmlStandalone;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setXmlStandalone(final boolean xmlStandalone) throws DOMException {
        this.xmlStandalone = xmlStandalone;
    }

    private String xmlVersion;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getXmlVersion() {
        return xmlVersion;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setXmlVersion(final String xmlVersion) throws DOMException {
        this.xmlVersion = xmlVersion;
    }

    @Override
    public boolean hasFocus() {
        return false;
    }
}
