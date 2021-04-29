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

package org.loboevolution.common;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

/**
 * <p>Nodes class.</p>
 *
 *
 *
 */
public class Nodes {
	/**
	 * <p>getCommonAncestor.</p>
	 *
	 * @param node1 a {@link org.loboevolution.html.node.Node} object.
	 * @param node2 a {@link org.loboevolution.html.node.Node} object.
	 * @return a {@link org.loboevolution.html.node.Node} object.
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
	 * @param node a {@link org.loboevolution.html.node.Node} object.
	 * @param child a {@link org.loboevolution.html.node.Node} object.
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
	 * @param attrList a {@link org.w3c.dom.NamedNodeMap} object.
	 * @return a {@link java.lang.Iterable} object.
	 */
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
}
