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

package org.loboevolution.common;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

/**
 * <p>Nodes class.</p>
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
	 * @param attrList a {@link org.loboevolution.html.node.NamedNodeMap} object.
	 * @return a {@link java.lang.Iterable} object.
	 */
	public static Iterable<Node> iterable(final NamedNodeMap attrList) {
	    return () -> new Iterator<Node>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < attrList.getLength();
            }

            @Override
            public Node next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return attrList.item(index++);
            }
        };
	}
}
