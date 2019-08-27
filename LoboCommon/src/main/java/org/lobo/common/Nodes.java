package org.lobo.common;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSStyleDeclaration;

public class Nodes {
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

	public static Iterable<Node> iterable(final NodeList nodeList) {
	    return () -> new Iterator<Node>() {

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
	
	public static Iterable<Attr> iterable(final NamedNodeMap attrList) {
	    return () -> new Iterator<Attr>() {

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
	
	public static Iterable<String> iterable(final CSSStyleDeclaration cssList) {
	    return () -> new Iterator<String>() {

	        private int index = 0;

	        @Override
	        public boolean hasNext() {
	            return index < cssList.getLength();
	        }

	        @Override
	        public String next() {
	            if (!hasNext())
	                throw new NoSuchElementException();
	            return (String) cssList.item(index++); 
	        }
	    };
	}
}
