/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
package org.loboevolution.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleDeclaration;

/**
 * The Class Nodes.
 */
public class Nodes {
	/**
	 * Gets the common ancestor.
	 *
	 * @param node1
	 *            the node1
	 * @param node2
	 *            the node2
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
	 * @param node
	 *            the node
	 * @param child
	 *            the child
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
	
	public static Iterable<CSSRule> iterable(final CSSRuleList cssRuleList) {
	    return () -> new Iterator<CSSRule>() {

	        private int index = 0;

	        @Override
	        public boolean hasNext() {
	            return index < cssRuleList.getLength();
	        }

	        @Override
	        public CSSRule next() {
	            if (!hasNext())
	                throw new NoSuchElementException();
	            return (CSSRule) cssRuleList.item(index++); 
	        }
	    };
	}
	
}
