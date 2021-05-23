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

package org.loboevolution.html.node;

import org.loboevolution.html.dom.NodeFilter;

/**
 * An iterator over the members of a list of the nodes in a subtree of the DOM.
 * The nodes will be returned in document order.
 */
public interface NodeIterator {

	/**
	 * <p>getFilter.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.NodeFilter} object.
	 */
	NodeFilter getFilter();

	/**
	 * <p>isPointerBeforeReferenceNode.</p>
	 *
	 * @return a boolean.
	 */
	boolean isPointerBeforeReferenceNode();

	/**
	 * <p>getReferenceNode.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node getReferenceNode();

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
