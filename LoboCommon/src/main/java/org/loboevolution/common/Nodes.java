package org.loboevolution.common;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSStyleDeclaration;

/**
 * <p>Nodes class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class Nodes {
	/**
	 * <p>getCommonAncestor.</p>
	 *
	 * @param node1 a {@link org.w3c.dom.Node} object.
	 * @param node2 a {@link org.w3c.dom.Node} object.
	 * @return a {@link org.w3c.dom.Node} object.
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
	 * <p>isSameOrAncestorOf.</p>
	 *
	 * @param node a {@link org.w3c.dom.Node} object.
	 * @param child a {@link org.w3c.dom.Node} object.
	 * @return a boolean.
	 */
	public static boolean isSameOrAncestorOf(Node node, Node child) {
		if (child.isSameNode(node)) {
			return true;
		}
		final Node parent = child.getParentNode();
		if (parent == null) {
			return false;
		}
		return isSameOrAncestorOf(node, parent);
	}

	/**
	 * <p>iterable.</p>
	 *
	 * @param nodeList a {@link org.w3c.dom.NodeList} object.
	 * @return a {@link java.lang.Iterable} object.
	 */
	public static Iterable<Node> iterable(final NodeList nodeList) {
	    return () -> new Iterator<>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < nodeList.getLength();
            }

            @Override
            public Node next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return nodeList.item(index++);
            }
        };
	}
	
	/**
	 * <p>iterable.</p>
	 *
	 * @param attrList a {@link org.w3c.dom.NamedNodeMap} object.
	 * @return a {@link java.lang.Iterable} object.
	 */
	public static Iterable<Attr> iterable(final NamedNodeMap attrList) {
	    return () -> new Iterator<>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < attrList.getLength();
            }

            @Override
            public Attr next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return (Attr) attrList.item(index++);
            }
        };
	}
	
	/**
	 * <p>iterable.</p>
	 *
	 * @param cssList a {@link org.w3c.dom.css.CSSStyleDeclaration} object.
	 * @return a {@link java.lang.Iterable} object.
	 */
	public static Iterable<String> iterable(final CSSStyleDeclaration cssList) {
	    return () -> new Iterator<>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < cssList.getLength();
            }

            @Override
            public String next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return cssList.item(index++);
            }
        };
	}
}
