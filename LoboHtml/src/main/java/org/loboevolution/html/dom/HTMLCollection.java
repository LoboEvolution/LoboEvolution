/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.dom;

import org.w3c.dom.Node;

/**
 * An HTMLCollection is a list of nodes. An individual node may be
 * accessed by either ordinal index or the node's name or
 * id attributes. Collections in the HTML DOM are assumed to be
 * live meaning that they are automatically updated when the underlying document
 * is changed.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLCollection {
	/**
	 * This attribute specifies the length or size of the list.
	 *
	 * @return a int.
	 */
	int getLength();

	/**
	 * This method retrieves a node specified by ordinal index. Nodes are numbered
	 * in tree order (depth-first traversal order).
	 *
	 * @param index The index of the node to be fetched. The index origin is
	 *              0.
	 * @return The Node at the corresponding position upon success. A
	 *         value of null is returned if the index is out of range.
	 */
	Node item(int index);

	/**
	 * This method retrieves a Node using a name. With
	 * [<a href='http://www.w3.org/TR/1999/REC-html401-19991224'>HTML 4.01</a>]
	 * documents, it first searches for a Node with a matching
	 * id attribute. If it doesn't find one, it then searches for a
	 * Node with a matching name attribute, but only on
	 * those elements that are allowed a name attribute. With
	 * [<a href='http://www.w3.org/TR/2002/REC-xhtml1-20020801'>XHTML 1.0</a>]
	 * documents, this method only searches for Nodes with a matching
	 * id attribute. This method is case insensitive in HTML documents
	 * and case sensitive in XHTML documents.
	 *
	 * @param name The name of the Node to be fetched.
	 * @return The Node with a name or id
	 *         attribute whose value corresponds to the specified string. Upon
	 *         failure (e.g., no node with this name exists), returns
	 *         null.
	 */
	Node namedItem(String name);

}
