/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * $Id: XPathNamespaceImpl.java 1225426 2011-12-29 04:13:08Z mrglavas $
 */

package org.lobobrowser.html.xpath;

import org.lobobrowser.w3c.xpath.XPathNamespace;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

/**
 *
 *
 * The <code>XPathNamespace</code> interface is returned by
 * <code>XPathResult</code> interfaces to represent the XPath namespace node
 * type that DOM lacks. There is no public constructor for this node type.
 * Attempts to place it into a hierarchy or a NamedNodeMap result in a
 * <code>DOMException</code> with the code <code>HIERARCHY_REQUEST_ERR</code> .
 * This node is read only, so methods or setting of attributes that would mutate
 * the node result in a DOMException with the code
 * <code>NO_MODIFICATION_ALLOWED_ERR</code>.
 * <p>
 * The core specification describes attributes of the <code>Node</code>
 * interface that are different for different node node types but does not
 * describe <code>XPATH_NAMESPACE_NODE</code>, so here is a description of those
 * attributes for this node type. All attributes of <code>Node</code> not
 * described in this section have a <code>null</code> or <code>false</code>
 * value.
 * <p>
 * <code>ownerDocument</code> matches the <code>ownerDocument</code> of the
 * <code>ownerElement</code> even if the element is later adopted.
 * <p>
 * <code>prefix</code> is the prefix of the namespace represented by the node.
 * <p>
 * <code>nodeName</code> is the same as <code>prefix</code>.
 * <p>
 * <code>nodeType</code> is equal to <code>XPATH_NAMESPACE_NODE</code>.
 * <p>
 * <code>namespaceURI</code> is the namespace URI of the namespace represented
 * by the node.
 * <p>
 * <code>adoptNode</code>, <code>cloneNode</code>, and <code>importNode</code>
 * fail on this node type by raising a <code>DOMException</code> with the code
 * <code>NOT_SUPPORTED_ERR</code>.In future versions of the XPath specification,
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
 * @xsl.usage internal
 */

public class XPathNamespaceImpl implements XPathNamespace {

	/** The m_attribute node. */
	// Node that XPathNamespaceImpl wraps
	final private Node m_attributeNode;

	/**
	 * Constructor for XPathNamespaceImpl.
	 */
	XPathNamespaceImpl(Node node) {
		m_attributeNode = node;
	}

	/**
	 * @see org.apache.xalan.dom3.xpath.XPathNamespace#getOwnerElement()
	 */
	@Override
	public Element getOwnerElement() {
		return ((Attr) m_attributeNode).getOwnerElement();
	}

	/**
	 * @see org.w3c.dom.Node#getNodeName()
	 */
	@Override
	public String getNodeName() {
		return "#namespace";
	}

	/**
	 * @see org.w3c.dom.Node#getNodeValue()
	 */
	@Override
	public String getNodeValue() throws DOMException {
		return m_attributeNode.getNodeValue();
	}

	/**
	 * @see org.w3c.dom.Node#setNodeValue(String)
	 */
	@Override
	public void setNodeValue(String arg0) throws DOMException {
	}

	/**
	 * @see org.w3c.dom.Node#getNodeType()
	 */
	@Override
	public short getNodeType() {
		return XPathNamespace.XPATH_NAMESPACE_NODE;
	}

	/**
	 * @see org.w3c.dom.Node#getParentNode()
	 */
	@Override
	public Node getParentNode() {
		return m_attributeNode.getParentNode();
	}

	/**
	 * @see org.w3c.dom.Node#getChildNodes()
	 */
	@Override
	public NodeList getChildNodes() {
		return m_attributeNode.getChildNodes();
	}

	/**
	 * @see org.w3c.dom.Node#getFirstChild()
	 */
	@Override
	public Node getFirstChild() {
		return m_attributeNode.getFirstChild();
	}

	/**
	 * @see org.w3c.dom.Node#getLastChild()
	 */
	@Override
	public Node getLastChild() {
		return m_attributeNode.getLastChild();
	}

	/**
	 * @see org.w3c.dom.Node#getPreviousSibling()
	 */
	@Override
	public Node getPreviousSibling() {
		return m_attributeNode.getPreviousSibling();
	}

	/**
	 * @see org.w3c.dom.Node#getNextSibling()
	 */
	@Override
	public Node getNextSibling() {
		return m_attributeNode.getNextSibling();
	}

	/**
	 * @see org.w3c.dom.Node#getAttributes()
	 */
	@Override
	public NamedNodeMap getAttributes() {
		return m_attributeNode.getAttributes();
	}

	/**
	 * @see org.w3c.dom.Node#getOwnerDocument()
	 */
	@Override
	public Document getOwnerDocument() {
		return m_attributeNode.getOwnerDocument();
	}

	/**
	 * @see org.w3c.dom.Node#insertBefore(Node, Node)
	 */
	@Override
	public Node insertBefore(Node arg0, Node arg1) throws DOMException {
		return null;
	}

	/**
	 * @see org.w3c.dom.Node#replaceChild(Node, Node)
	 */
	@Override
	public Node replaceChild(Node arg0, Node arg1) throws DOMException {
		return null;
	}

	/**
	 * @see org.w3c.dom.Node#removeChild(Node)
	 */
	@Override
	public Node removeChild(Node arg0) throws DOMException {
		return null;
	}

	/**
	 * @see org.w3c.dom.Node#appendChild(Node)
	 */
	@Override
	public Node appendChild(Node arg0) throws DOMException {
		return null;
	}

	/**
	 * @see org.w3c.dom.Node#hasChildNodes()
	 */
	@Override
	public boolean hasChildNodes() {
		return false;
	}

	/**
	 * @see org.w3c.dom.Node#cloneNode(boolean)
	 */
	@Override
	public Node cloneNode(boolean arg0) {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, null);
	}

	/**
	 * @see org.w3c.dom.Node#normalize()
	 */
	@Override
	public void normalize() {
		m_attributeNode.normalize();
	}

	/**
	 * @see org.w3c.dom.Node#isSupported(String, String)
	 */
	@Override
	public boolean isSupported(String arg0, String arg1) {
		return m_attributeNode.isSupported(arg0, arg1);
	}

	/**
	 * @see org.w3c.dom.Node#getNamespaceURI()
	 */
	@Override
	public String getNamespaceURI() {

		// For namespace node, the namespaceURI is the namespace URI
		// of the namespace represented by the node.
		return m_attributeNode.getNodeValue();
	}

	/**
	 * @see org.w3c.dom.Node#getPrefix()
	 */
	@Override
	public String getPrefix() {
		return m_attributeNode.getPrefix();
	}

	/**
	 * @see org.w3c.dom.Node#setPrefix(String)
	 */
	@Override
	public void setPrefix(String arg0) throws DOMException {
	}

	/**
	 * @see org.w3c.dom.Node#getLocalName()
	 */
	@Override
	public String getLocalName() {

		// For namespace node, the local name is the same as the prefix
		return m_attributeNode.getPrefix();
	}

	/**
	 * @see org.w3c.dom.Node#hasAttributes()
	 */
	@Override
	public boolean hasAttributes() {
		return m_attributeNode.hasAttributes();
	}

	@Override
	public String getBaseURI() {
		return null;
	}

	@Override
	public short compareDocumentPosition(Node other) throws DOMException {
		return 0;
	}

	/** The text content. */
	private String textContent;

	@Override
	public String getTextContent() throws DOMException {
		return textContent;
	}

	@Override
	public void setTextContent(String textContent) throws DOMException {
		this.textContent = textContent;
	}

	@Override
	public boolean isSameNode(Node other) {
		return false;
	}

	@Override
	public String lookupPrefix(String namespaceURI) {
		return ""; // PENDING
	}

	@Override
	public boolean isDefaultNamespace(String namespaceURI) {
		return false;
	}

	@Override
	public String lookupNamespaceURI(String prefix) {
		return null;
	}

	@Override
	public boolean isEqualNode(Node arg) {
		return false;
	}

	@Override
	public Object getFeature(String feature, String version) {
		return null; // PENDING
	}

	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		return null; // PENDING
	}

	@Override
	public Object getUserData(String key) {
		return null;
	}
}
