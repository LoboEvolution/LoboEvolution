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
 * An iterator over the members of a list of the nodes in a subtree of the DOM.
 * The nodes will be returned in document order.
 */
public interface NodeIterator {

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
	 * @return a int.
	 */
	int getWhatToShow();

	/**
	 * <p>detach.</p>
	 */
	void detach();

	/**
	 * <p>nextNode.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node nextNode();

	/**
	 * <p>previousNode.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node previousNode();

}
