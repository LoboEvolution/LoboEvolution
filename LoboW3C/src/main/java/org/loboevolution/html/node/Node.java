/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.html.node;

import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.node.events.EventTarget;
import org.w3c.dom.UserDataHandler;

/**
 * Node is an interface from which a number of DOM API object types inherit. It
 * allows those types to be treated similarly; for example, inheriting the same
 * set of methods, or being tested in the same way.
 *
 *
 *
 */
public interface Node extends EventTarget {

	/** Constant <code>DOCUMENT_POSITION_DISCONNECTED=1</code> */
	short DOCUMENT_POSITION_DISCONNECTED = 1;
	/** Constant <code>DOCUMENT_POSITION_PRECEDING=2</code> */
	short DOCUMENT_POSITION_PRECEDING = 2;
	/** Constant <code>DOCUMENT_POSITION_FOLLOWING=4</code> */
	short DOCUMENT_POSITION_FOLLOWING = 4;
	/** Constant <code>DOCUMENT_POSITION_CONTAINS=8</code> */
	short DOCUMENT_POSITION_CONTAINS = 8;
	/** Constant <code>DOCUMENT_POSITION_CONTAINED_BY=16</code> */
	short DOCUMENT_POSITION_CONTAINED_BY = 16;
	/** Constant <code>DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC=32</code> */
	short DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;

	/**
	 * Returns node's node document's document base URL.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getBaseURI();

	/**
	 * Returns prefix.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getPrefix();

	/**
	 * Returns the children.
	 *
	 * @return a {@link org.loboevolution.html.node.NodeList} object.
	 */
	NodeList getChildNodes();

	/**
	 * Returns the first child.
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node getFirstChild();

	/**
	 * Returns true if node is connected and false otherwise.
	 *
	 * @return a boolean.
	 */
	boolean isIsConnected();

	/**
	 * Returns the last child.
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node getLastChild();

	/**
	 * <p>getNamespaceURI.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getNamespaceURI();

	/**
	 * Returns the next sibling.
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node getNextSibling();

	/**
	 * Returns a string appropriate for the type of node.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getNodeName();

	/**
	 * Returns the type of node.
	 *
	 * @return a {@link org.loboevolution.html.node.NodeType} object.
	 */
	NodeType getNodeType();

	/**
	 * <p>getNodeValue.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getNodeValue();

	/**
	 * Returns the node document. Returns null for documents.
	 *
	 * @return a {@link org.loboevolution.html.node.Document} object.
	 */
	Document getOwnerDocument();

	/**
	 * Returns the parent element.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLElement} object.
	 */
	HTMLElement getParentElement();

	/**
	 * Returns the parent.
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node getParentNode();

	/**
	 * Returns the previous sibling.
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node getPreviousSibling();

	/**
	 * Returns node's root.
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node getRootNode();

	/**
	 * <p>getLocalName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getLocalName();

	/**
	 * <p>getTextContent.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getTextContent();

	/**
	 * <p>getUserData.</p>
	 *
	 * @param key a {@link java.lang.String} object.
	 * @return a {@link java.lang.Object} object.
	 */
	Object getUserData(String key);

	/**
	 * Returns a copy of node. If deep is true, the copy also includes the node's
	 * descendants.
	 *
	 * @param deep a boolean.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node cloneNode(boolean deep);

	/**
	 * <p>cloneNode.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node cloneNode();

	/**
	 * <p>setNodeValue.</p>
	 *
	 * @param nodeValue a {@link java.lang.String} object.
	 */
	void setNodeValue(String nodeValue);

	/**
	 * <p>setTextContent.</p>
	 *
	 * @param textContent a {@link java.lang.String} object.
	 */
	void setTextContent(String textContent);
	
	/**
	 * <p>setPrefix.</p>
	 *
	 * @param prefix a {@link java.lang.String} object.
	 */
	void setPrefix(String prefix);
	
	/**
	 * <p>setUserData.</p>
	 *
	 * @param key a {@link java.lang.String} object.
	 * @param data a {@link java.lang.Object} object.
	 * @param handler a {@link org.w3c.dom.UserDataHandler} object.
	 * @return a {@link java.lang.Object} object.
	 */
	Object setUserData(final String key, final Object data, final UserDataHandler handler);

	/**
	 * Returns true if other is an inclusive descendant of node, and false
	 * otherwise.
	 *
	 * @param other a {@link org.loboevolution.html.node.Node} object.
	 * @return a boolean.
	 */
	boolean contains(Node other);

	/**
	 * Returns whether node has children.
	 *
	 * @return a boolean.
	 */
	boolean hasChildNodes();

	/**
	 * <p>insertBefore.</p>
	 *
	 * @param newChild a {@link org.loboevolution.html.node.Node} object.
	 * @param refChild a {@link org.loboevolution.html.node.Node} object.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node insertBefore(Node newChild, Node refChild);

	/**
	 * <p>isDefaultNamespace.</p>
	 *
	 * @param namespace a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	boolean isDefaultNamespace(String namespace);

	/**
	 * Returns whether node and otherNode have the same properties.
	 *
	 * @param otherNode a {@link org.loboevolution.html.node.Node} object.
	 * @return a boolean.
	 */
	boolean isEqualNode(Node otherNode);

	/**
	 * <p>isSameNode.</p>
	 *
	 * @param otherNode a {@link org.loboevolution.html.node.Node} object.
	 * @return a boolean.
	 */
	boolean isSameNode(Node otherNode);

	/**
	 * <p>lookupNamespaceURI.</p>
	 *
	 * @param prefix a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	String lookupNamespaceURI(String prefix);

	/**
	 * <p>lookupPrefix.</p>
	 *
	 * @param namespace a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	String lookupPrefix(String namespace);

	/**
	 * Removes empty exclusive Text nodes and concatenates the data of remaining
	 * contiguous exclusive Text nodes into the first of their nodes.
	 */
	void normalize();

	/**
	 * <p>appendChild.</p>
	 *
	 * @param newChild a {@link org.loboevolution.html.node.Node} object.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node appendChild(Node newChild);

	/**
	 * <p>removeChild.</p>
	 *
	 * @param oldChild a {@link org.loboevolution.html.node.Node} object.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node removeChild(Node oldChild);

	/**
	 * <p>replaceChild.</p>
	 *
	 * @param newChild a {@link org.loboevolution.html.node.Node} object.
	 * @param oldChild a {@link org.loboevolution.html.node.Node} object.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node replaceChild(Node newChild, Node oldChild);

	/**
	 * <p>compareDocumentPosition.</p>
	 *
	 * @param other a {@link org.loboevolution.html.node.Node} object.
	 * @return a short.
	 */
	short compareDocumentPosition(Node other);
}
