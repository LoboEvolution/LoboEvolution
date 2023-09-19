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

package org.loboevolution.html.node;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.UserDataHandler;
import org.loboevolution.html.node.events.EventTarget;

/**
 * Node is an interface from which a number of DOM API object types inherit. It
 * allows those types to be treated similarly; for example, inheriting the same
 * set of methods, or being tested in the same way.
 */
public interface Node extends EventTarget {

	short NONE = -1;
	short ELEMENT_NODE = 1;
	short ATTRIBUTE_NODE = 2;
	short TEXT_NODE = 3;
	short CDATA_SECTION_NODE = 4;
	short ENTITY_REFERENCE_NODE = 5;
	short ENTITY_NODE  = 6;
	short PROCESSING_INSTRUCTION_NODE = 7;
	short COMMENT_NODE = 8;
	short DOCUMENT_NODE  = 9;
	short DOCUMENT_TYPE_NODE = 10;
	short DOCUMENT_FRAGMENT_NODE = 11;
	short NOTATION_NODE  = 12;
	short DOCUMENT_POSITION_DISCONNECTED = 1;
	short DOCUMENT_POSITION_PRECEDING = 2;
	short DOCUMENT_POSITION_FOLLOWING = 4;
	short DOCUMENT_POSITION_CONTAINS = 8;
	short DOCUMENT_POSITION_CONTAINED_BY = 16;
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
	 * @return a {@link java.lang.Integer} object.
	 */
	int getNodeType();

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
	 ** <p>setOwnerDocument.</p>
	 *
	 * @param document a  {@link org.loboevolution.html.node.Document} object.
	 */

	void setOwnerDocument(Document document);

	/**
	 * <p>setNodeValue.</p>
	 *
	 * @param nodeValue a {@link java.lang.String} object.
	 */
	void setNodeValue(String nodeValue) throws DOMException;

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
	 * @param handler a {@link org.loboevolution.html.dom.UserDataHandler} object.
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
	 * <p>prependChild.</p>
	 *
	 * @param newChild a {@link org.loboevolution.html.node.Node} object.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node prependChild(Node newChild);

	/**
	 * <p>prepend.</p>
	 *
	 * @param newChild a {@link org.loboevolution.html.node.Node} object.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node prepend(Node newChild);

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
	 * <p>getAttributes.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.NamedNodeMap} object.
	 */
	NamedNodeMap getAttributes();

	/**
	 * <p>compareDocumentPosition.</p>
	 *
	 * @param other a {@link org.loboevolution.html.node.Node} object.
	 * @return a short.
	 */
	short compareDocumentPosition(Node other);

	/**
	 * Returns true if element has attributes, and false otherwise.
	 *
	 * @return a boolean.
	 */
	boolean hasAttributes();

	boolean isSupported(String feature, String version);
	Node getFeature(String feature, String version);
}
