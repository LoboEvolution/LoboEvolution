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

/**
 * NodeList objects are collections of nodes, usually returned by properties
 * such as Node.childNodes and methods such as document.querySelectorAll().
 *
 *
 *
 */
public interface NodeList {

	/**
	 * Returns the number of nodes in the collection.
	 *
	 * @return a int.
	 */
	int getLength();

	/**
	 * Returns the node with index index from the collection. The nodes are sorted
	 * in tree order.
	 *
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node item(int index);

	/**
	 * <p>toArray.</p>
	 *
	 * @return an array of {@link org.loboevolution.html.node.Node} objects.
	 */
	Node[] toArray();

}
