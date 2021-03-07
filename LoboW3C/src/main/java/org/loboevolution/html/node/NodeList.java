package org.loboevolution.html.node;

/**
 * NodeList objects are collections of nodes, usually returned by properties
 * such as Node.childNodes and methods such as document.querySelectorAll().
 */
public interface NodeList {

	/**
	 * Returns the number of nodes in the collection.
	 */

	int getLength();

	/**
	 * Returns the node with index index from the collection. The nodes are sorted
	 * in tree order.
	 */

	Node item(int index);

	Node[] toArray();

}
