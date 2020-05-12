/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * $Id: XPathNamespaceImpl.java 1225426 2011-12-29 04:13:08Z mrglavas $
 */

package org.loboevolution.html.dom.xpath;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.xpath.XPathNamespace;

/**
 *
 *
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
 *
 * This implementation wraps the DOM attribute node that contained the namespace
 * declaration.
 *
 * 
 * @author utente
 * @version $Id: $Id
 */
public class XPathNamespaceImpl implements XPathNamespace {

	/** The m_attribute node. */
	private final Node m_attributeNode;
	
	/** The text content. */
	private String textContent;

	/**
	 * Constructor for XPathNamespaceImpl.
	 */
	XPathNamespaceImpl(Node node) {
		m_attributeNode = node;
	}

	/** {@inheritDoc} */
	@Override
	public Element getOwnerElement() {
		return ((Attr) m_attributeNode).getOwnerElement();
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return "#namespace";
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeValue() throws DOMException {
		return m_attributeNode.getNodeValue();
	}

	/** {@inheritDoc} */
	@Override
	public void setNodeValue(String arg0) throws DOMException {
		// Method not implemented
	}

	/** {@inheritDoc} */
	@Override
	public short getNodeType() {
		return XPathNamespace.XPATH_NAMESPACE_NODE;
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
	public NamedNodeMap getAttributes() {
		return m_attributeNode.getAttributes();
	}

	/** {@inheritDoc} */
	@Override
	public Document getOwnerDocument() {
		return m_attributeNode.getOwnerDocument();
	}

	/** {@inheritDoc} */
	@Override
	public Node insertBefore(Node arg0, Node arg1) throws DOMException {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Node replaceChild(Node arg0, Node arg1) throws DOMException {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Node removeChild(Node arg0) throws DOMException {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Node appendChild(Node arg0) throws DOMException {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasChildNodes() {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public Node cloneNode(boolean arg0) {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, null);
	}

	/** {@inheritDoc} */
	@Override
	public void normalize() {
		m_attributeNode.normalize();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSupported(String arg0, String arg1) {
		return m_attributeNode.isSupported(arg0, arg1);
	}

	/** {@inheritDoc} */
	@Override
	public String getNamespaceURI() {

		// For namespace node, the namespaceURI is the namespace URI
		// of the namespace represented by the node.
		return m_attributeNode.getNodeValue();
	}

	/** {@inheritDoc} */
	@Override
	public String getPrefix() {
		return m_attributeNode.getPrefix();
	}

	/** {@inheritDoc} */
	@Override
	public void setPrefix(String arg0) throws DOMException {
		// Method not implemented
	}

	/** {@inheritDoc} */
	@Override
	public String getLocalName() {
		// For namespace node, the local name is the same as the prefix
		return m_attributeNode.getPrefix();
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasAttributes() {
		return m_attributeNode.hasAttributes();
	}

	/** {@inheritDoc} */
	@Override
	public String getBaseURI() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public short compareDocumentPosition(Node other) throws DOMException {
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public String getTextContent() throws DOMException {
		return textContent;
	}

	/** {@inheritDoc} */
	@Override
	public void setTextContent(String textContent) throws DOMException {
		this.textContent = textContent;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSameNode(Node other) {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String lookupPrefix(String namespaceURI) {
		return ""; // PENDING
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDefaultNamespace(String namespaceURI) {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String lookupNamespaceURI(String prefix) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isEqualNode(Node arg) {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public Object getFeature(String feature, String version) {
		return null; // PENDING
	}

	/** {@inheritDoc} */
	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		return null; // PENDING
	}

	/** {@inheritDoc} */
	@Override
	public Object getUserData(String key) {
		return null;
	}
}
