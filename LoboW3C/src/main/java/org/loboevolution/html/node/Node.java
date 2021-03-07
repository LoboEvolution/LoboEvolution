package org.loboevolution.html.node;

import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.node.events.EventTarget;
import org.w3c.dom.UserDataHandler;

/**
 * Node is an interface from which a number of DOM API object types inherit. It
 * allows those types to be treated similarly; for example, inheriting the same
 * set of methods, or being tested in the same way.
 */
public interface Node extends EventTarget {

	public final short DOCUMENT_POSITION_DISCONNECTED = 1;
	public final short DOCUMENT_POSITION_PRECEDING = 2;
	public final short DOCUMENT_POSITION_FOLLOWING = 4;
	public final short DOCUMENT_POSITION_CONTAINS = 8;
	public final short DOCUMENT_POSITION_CONTAINED_BY = 16;
	public final short DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;

	/**
	 * Returns node's node document's document base URL.
	 */

	String getBaseURI();

	/**
	 * Returns prefix.
	 */
	String getPrefix();

	/**
	 * Returns the children.
	 */

	NodeList getChildNodes();

	/**
	 * Returns the first child.
	 */

	Node getFirstChild();

	/**
	 * Returns true if node is connected and false otherwise.
	 */

	boolean isIsConnected();

	/**
	 * Returns the last child.
	 */

	Node getLastChild();

	@Deprecated
	String getNamespaceURI();

	/**
	 * Returns the next sibling.
	 */
	Node getNextSibling();

	/**
	 * Returns a string appropriate for the type of node.
	 */
	String getNodeName();

	/**
	 * Returns the type of node.
	 */

	NodeType getNodeType();

	String getNodeValue();

	/**
	 * Returns the node document. Returns null for documents.
	 */

	Document getOwnerDocument();

	/**
	 * Returns the parent element.
	 */

	HTMLElement getParentElement();

	/**
	 * Returns the parent.
	 */

	ParentNode getParentNode();

	/**
	 * Returns the previous sibling.
	 */
	Node getPreviousSibling();

	/**
	 * Returns node's root.
	 */
	Node getRootNode();

	String getLocalName();

	String getTextContent();

	Object getUserData(String key);

	/**
	 * Returns a copy of node. If deep is true, the copy also includes the node's
	 * descendants.
	 */
	Node cloneNode(boolean deep);

	Node cloneNode();

	void setNodeValue(String nodeValue);

	void setTextContent(String textContent);
	
	void setPrefix(String prefix);
	
	Object setUserData(final String key, final Object data, final UserDataHandler handler);

	/**
	 * Returns true if other is an inclusive descendant of node, and false
	 * otherwise.
	 */
	boolean contains(Node other);

	/**
	 * Returns whether node has children.
	 */
	boolean hasChildNodes();

	Node insertBefore(Node newChild, Node refChild);

	boolean isDefaultNamespace(String namespace);

	/**
	 * Returns whether node and otherNode have the same properties.
	 */
	boolean isEqualNode(Node otherNode);

	boolean isSameNode(Node otherNode);

	String lookupNamespaceURI(String prefix);

	String lookupPrefix(String namespace);

	/**
	 * Removes empty exclusive Text nodes and concatenates the data of remaining
	 * contiguous exclusive Text nodes into the first of their nodes.
	 */
	void normalize();

	Node appendChild(Node newChild);

	Node removeChild(Node oldChild);

	Node replaceChild(Node newChild, Node oldChild);

	short compareDocumentPosition(Node other);
}
