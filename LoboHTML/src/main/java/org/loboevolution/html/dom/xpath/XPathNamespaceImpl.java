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
/*
 * $Id: XPathNamespaceImpl.java 1225426 2011-12-29 04:13:08Z mrglavas $
 */

package org.loboevolution.html.dom.xpath;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.node.*;
import org.loboevolution.events.Event;
import org.loboevolution.html.xpath.XPathNamespace;
import org.loboevolution.traversal.NodeFilter;
import org.mozilla.javascript.Function;
import org.loboevolution.html.dom.UserDataHandler;
import org.w3c.dom.events.EventException;

import java.net.URL;
import java.util.Collection;
import java.util.List;

/**
 * The XPathNamespace interface is returned by
 * XPathResult interfaces to represent the XPath namespace node
 * type that DOM lacks. There is no public constructor for this node type.
 * Attempts to place it into a hierarchy or a NamedNodeMap result in a
 * DOMException with the code HIERARCHY_REQUEST_ERR .
 * This node is read only, so methods or setting of attributes that would mutate
 * the node result in a DOMException with the code
 * NO_MODIFICATION_ALLOWED_ERR.
 * <p>
 * The core specification describes attributes of the Node
 * interface that are different for different node node types but does not
 * describe XPATH_NAMESPACE_NODE, so here is a description of those
 * attributes for this node type. All attributes of Node not
 * described in this section have a null or false
 * value.
 * <p>
 * ownerDocument matches the ownerDocument of the
 * ownerElement even if the element is later adopted.
 * <p>
 * prefix is the prefix of the namespace represented by the node.
 * <p>
 * nodeName is the same as prefix.
 * <p>
 * nodeType is equal to XPATH_NAMESPACE_NODE.
 * <p>
 * namespaceURI is the namespace URI of the namespace represented
 * by the node.
 * <p>
 * adoptNode, cloneNode, and importNode
 * fail on this node type by raising a DOMException with the code
 * NOT_SUPPORTED_ERR.In future versions of the XPath specification,
 * the definition of a namespace node may be changed incomatibly, in which case
 * incompatible changes to field values may be required to implement versions
 * beyond XPath 1.0.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2004/NOTE-DOM-Level-3-XPath-20040226'>Document
 * Object Model (DOM) Level 3 XPath Specification</a>.
 * This implementation wraps the DOM attribute node that contained the namespace
 * declaration.
 */
public class XPathNamespaceImpl implements XPathNamespace {

	/** The m_attribute node. */
	private final Node m_attributeNode;
	
	/** The text content. */
	private String textContent;

	/**
	 * Constructor for XPathNamespaceImpl.
	 */
	XPathNamespaceImpl(final Node node) {
		m_attributeNode = node;
	}

	/** {@inheritDoc} */
	@Override
	public Node getOwnerElement() {
		return ((Attr) m_attributeNode).getOwnerElement();
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return "#namespace";
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeValue() {
		return m_attributeNode.getNodeValue();
	}

	/** {@inheritDoc} */
	@Override
	public void setNodeValue(final String arg0) {
		// Method not implemented
	}

	/** {@inheritDoc} */
	@Override
	public int getNodeType() {
		return Node.ATTRIBUTE_NODE;
	}

	/** {@inheritDoc} */
	@Override
	public Node getParentNode() {
		return m_attributeNode.getParentNode();
	}

	/** {@inheritDoc} */
	@Override
	public NodeList getChildNodes() {
		return m_attributeNode.getChildNodes();
	}

	/** {@inheritDoc} */
	@Override
	public Node getFirstChild() {
		return m_attributeNode.getFirstChild();
	}

	/** {@inheritDoc} */
	@Override
	public Node getLastChild() {
		return m_attributeNode.getLastChild();
	}

	/** {@inheritDoc} */
	@Override
	public Node getPreviousSibling() {
		return m_attributeNode.getPreviousSibling();
	}

	/** {@inheritDoc} */
	@Override
	public Node getNextSibling() {
		return m_attributeNode.getNextSibling();
	}

	/** {@inheritDoc} */
	@Override
	public Document getOwnerDocument() {
		return m_attributeNode.getOwnerDocument();
	}

	/** {@inheritDoc} */
	@Override
	public Node insertBefore(final Node arg0, final Node arg1) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Node replaceChild(final Node arg0, final Node arg1) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Node removeChild(final Node arg0) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Node appendChild(final Node arg0) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Node prependChild(final Node newChild) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Node prepend(final Node newChild) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasChildNodes() {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public Node cloneNode(final boolean arg0) {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, null);
	}

	/** {@inheritDoc} */
	@Override
	public void normalize() {
		m_attributeNode.normalize();
	}

	/** {@inheritDoc} */
	@Override
	public String getNamespaceURI() {
		return m_attributeNode.getNodeValue();
	}

	/** {@inheritDoc} */
	@Override
	public String getPrefix() {
		return m_attributeNode.getPrefix();
	}

	/** {@inheritDoc} */
	@Override
	public void setPrefix(final String arg0) {
		// Method not implemented
	}

	/** {@inheritDoc} */
	@Override
	public String getLocalName() {
		return m_attributeNode.getPrefix();
	}


	/** {@inheritDoc} */
	@Override
	public String getBaseURI() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public short compareDocumentPosition(final Node other) {
		return 0;
	}

	@Override
	public boolean isSupported(final String xml, final String s) {
		return false;
	}

	@Override
	public boolean hasAttributes() {
		return false;
	}

	@Override
	public Node getFeature(final String name, final String version) {
		return null;
	}

	@Override
	public Document getDocumentNode() {
		return null;
	}

	@Override
	public NamedNodeMap getAttributes() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getTextContent() {
		return textContent;
	}

	/** {@inheritDoc} */
	@Override
	public void setTextContent(final String textContent) {
		this.textContent = textContent;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSameNode(final Node other) {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String lookupPrefix(final String namespaceURI) {
		return ""; // PENDING
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDefaultNamespace(final String namespaceURI) {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String lookupNamespaceURI(final String prefix) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isEqualNode(final Node arg) {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public Object setUserData(final String key, final Object data, final UserDataHandler handler) {
		return null; // PENDING
	}

	@Override
	public void setParentImpl(Node parent) {

	}

	@Override
	public void setNamespaceURI(String namespaceURI) {

	}

	/** {@inheritDoc} */
	@Override
	public Object getUserData(final String key) {
		return null;
	}

	@Override
	public Node getPreviousTo(Node node) {
		return null;
	}

	@Override
	public Node getNextTo(Node node) {
		return null;
	}

	@Override
	public void forgetRenderState() {

	}

	@Override
	public void appendChildrenToCollectionImpl(NodeFilter filter, Collection<Node> collection) {

	}

	@Override
	public void extractDescendentsArrayImpl(NodeFilter filter, List<Node> al, boolean nestIntoMatchingNodes) {

	}

	@Override
	public UINode findUINode() {
		return null;
	}

	@Override
	public void visit(NodeVisitor visitor) {

	}

	/** {@inheritDoc} */
	@Override
	public boolean isIsConnected() {
		return  m_attributeNode.isIsConnected();
	}

	/** {@inheritDoc} */
	@Override
	public HTMLElement getParentElement() {
		return  m_attributeNode.getParentElement();
	}

	/** {@inheritDoc} */
	@Override
	public Node getRootNode() {
		return  m_attributeNode.getRootNode();
	}

	/** {@inheritDoc} */
	@Override
	public Node cloneNode() {
		return  m_attributeNode.cloneNode();
	}

	@Override
	public void setOwnerDocument(final Document document) {
		m_attributeNode.setOwnerDocument(document);
	}

	@Override
	public void setOwnerDocument(Document value, boolean deep) {

	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(final Node other) {
		return m_attributeNode.contains(other);
	}

	/** {@inheritDoc} */
	@Override
	public void addEventListener(final String type, final Function listener) {
		m_attributeNode.addEventListener(type, listener);
		
	}

	/** {@inheritDoc} */
	@Override
	public void addEventListener(final String type, final Function listener, final boolean useCapture) {
		m_attributeNode.addEventListener(type, listener, useCapture);
		
	}

	/** {@inheritDoc} */
	@Override
	public void removeEventListener(final String type, final Function listener) {
		m_attributeNode.removeEventListener(type, listener);
		
	}

	/** {@inheritDoc} */
	@Override
	public void removeEventListener(final String type, final Function listener, final boolean useCapture) {
		m_attributeNode.removeEventListener(type, listener, useCapture);
		
	}

	/** {@inheritDoc} */
	@Override
	public boolean dispatchEvent(final Node element, final Event evt) {
		return m_attributeNode.dispatchEvent(element, evt);
	}

	@Override
	public boolean dispatchEvent(final Event evt) throws EventException {
		return m_attributeNode.dispatchEvent(evt);
	}

	@Override
	public Object getDocumentItem(String name) {
		return null;
	}

	@Override
	public URL getFullURL(String spec) throws Exception {
		return null;
	}

	@Override
	public ModelNode getParentModelNode() {
		return null;
	}

	@Override
	public boolean isEqualOrDescendentOf(ModelNode otherNode) {
		return false;
	}

	@Override
	public Object getRenderState() {
		return null;
	}

	@Override
	public void setDocumentItem(String name, Object value) {

	}

	@Override
	public void warn(String message, Throwable err) {

	}
}
