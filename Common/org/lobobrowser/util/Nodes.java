package org.lobobrowser.util;

import org.w3c.dom.Node;


/**
 * The Class Nodes.
 */
public class Nodes {
	
	/**
	 * Gets the common ancestor.
	 *
	 * @param node1 the node1
	 * @param node2 the node2
	 * @return the common ancestor
	 */
	public static Node getCommonAncestor(Node node1, Node node2) {
		if (node1 == null || node2 == null) {
			return null;
		}
		Node checkNode = node1;
		while (!isSameOrAncestorOf(checkNode, node2)) {
			checkNode = checkNode.getParentNode();
			if (checkNode == null) {
				return null;
			}
		}
		return checkNode;
	}

	/**
	 * Checks if is same or ancestor of.
	 *
	 * @param node the node
	 * @param child the child
	 * @return true, if is same or ancestor of
	 */
	public static boolean isSameOrAncestorOf(Node node, Node child) {
		if (child.isSameNode(node)) {
			return true;
		}
		Node parent = child.getParentNode();
		if (parent == null) {
			return false;
		}
		return isSameOrAncestorOf(node, parent);
	}
}
