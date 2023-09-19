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

package org.loboevolution.html.node.traversal;

import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.traversal.NodeFilter;

/**
 * The nodes of a document subtree and a position within them.
 */
public interface TreeWalker {

	/**
	 * <p>getCurrentNode.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node getCurrentNode();

	/**
	 * <p>setCurrentNode.</p>
	 *
	 * @param currentNode a {@link org.loboevolution.html.node.Node} object.
	 */
	void setCurrentNode(Node currentNode);

	/**
	 * <p>getFilter.</p>
	 *
	 * @return a {@link NodeFilter} object.
	 */
	NodeFilter getFilter();

	/**
	 * <p>getRoot.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node getRoot();

	/**
	 * <p>getWhatToShow.</p>
	 *
	 * @return a double.
	 */
	int getWhatToShow();

	/**
	 * <p>firstChild.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node firstChild();

	/**
	 * <p>lastChild.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node lastChild();

	/**
	 * <p>nextNode.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node nextNode();

	/**
	 * <p>nextSibling.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node nextSibling();

	/**
	 * <p>parentNode.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node parentNode();

	/**
	 * <p>previousNode.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node previousNode();

	/**
	 * <p>previousSibling.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node previousSibling();

}
